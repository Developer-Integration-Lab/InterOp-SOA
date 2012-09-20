/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.handler.rd;

import java.util.List;
import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.exception.MVRuntimeException;
import net.aegis.mv.handler.INhinMsgHandler;
import net.aegis.mv.jaxb.rd.RdNhinDocumentRequest;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetRequest;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.util.MessageValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
*
* Venkat.Keesara
* May 10, 2012
*/
public class RDRequestMsgHandler implements INhinMsgHandler {
	private static final Log log = LogFactory.getLog(RDRequestMsgHandler.class);
	
	@Override
	public void processMessage(Transaction transaction , INhinMsgParser parser, MsgTypeEnum msgTypeEnum){			
		log.debug(this.getClass().getSimpleName() + ".processMessage(): Processing transaction ID: " + transaction.getId());		
		RdNhinDocumentSetRequest docsetRequest = (RdNhinDocumentSetRequest)parser.parse(transaction);
		List<RdNhinDocumentRequest> requests = docsetRequest.getRequestSet();
		if(requests == null || requests.size() == 0) {
			return ;
		}
		processRDRequest(transaction, msgTypeEnum, docsetRequest);
		
	}
	
	@Override
	public void setResponseError(Transaction transaction,
			MsgTypeEnum msgTypeEnum, Caseexecution caseExecution, String relatesTo) {
			throw new MVRuntimeException("Method not implemented.");
		
	}
	
	/**
	 * 1) Get document id and HCID from the RD request message 2) Use document
	 * id and HCID in the ResultDocument table to find out the dependent QD
	 * execution 3) Identify the patient id from the latest RD / QD execution //
	 * Note : 2,3 can be combined 4) Identify RD test case using the patient id
	 * 5) Update case execution to PROGRESS
	 * 
	 * @param transaction
	 * @param msgTypeEnum
	 * @param nhindocsetRequest
	 */
	
	private void processRDRequest(Transaction transaction,MsgTypeEnum msgTypeEnum , RdNhinDocumentSetRequest nhindocsetRequest){
		
		String participantPatientId = null;	
		Boolean isPatientIdFound = false; 
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
		
		List<RdNhinDocumentRequest> requestSet = nhindocsetRequest.getRequestSet();
		RdNhinDocumentRequest matchedRdNhinDocumentRequest = null;
		for(RdNhinDocumentRequest rdNhinDocumentRequest :  requestSet){
			log.debug("Home Community ID: " + rdNhinDocumentRequest.getHomeCommunityId());
			log.debug("Repository ID: " + rdNhinDocumentRequest.getRepositoryId());
			log.debug("Document Unique ID: " + rdNhinDocumentRequest.getDocumentUniqueId());
			
			participantPatientId = getParticipantPatientId(rdNhinDocumentRequest, clientServerDTO);
			if(StringUtils.isNotEmpty(participantPatientId)){
				isPatientIdFound = true;
				matchedRdNhinDocumentRequest= rdNhinDocumentRequest;
				break;
			}
		}
		
		if(isPatientIdFound){
			getCaseExecutionAndSendToCaseresultHandler(transaction, msgTypeEnum, participantPatientId, nhindocsetRequest, matchedRdNhinDocumentRequest, clientServerDTO);
		}else{
			// find patientId in resource Id 
		}
		
	}
	
	/**
	 * 
	 * @param rdNhinDocumentResponse
	 * @param clientServerDTO
	 * @return
	 */
	private String getParticipantPatientId(RdNhinDocumentRequest rdNhinDocumentResponse , ClientServerDTO clientServerDTO){
		// get patientId by using Home Community ID , Document Unique ID
		if(StringUtils.isNotEmpty(rdNhinDocumentResponse.getHomeCommunityId()) && StringUtils.isNotEmpty(rdNhinDocumentResponse.getDocumentUniqueId())){
			// get active participant by HCID
			List<Participant> participantList = ParticipantManager.getInstance().getActiveCandiateByCommunityId(clientServerDTO.getParticipantGatewayInfo().getHcid(), LabConstants.STATUS_ACTIVE);
			Participant participant =null;
			if (participantList != null && participantList.size() > 0) {
				participant = participantList.get(0);
				log.info("participant is --- >> " + participant.getParticipantId());
			}
			String documentUniqueId = rdNhinDocumentResponse.getDocumentUniqueId();
			String homeCommunityId = MessageValidatorUtil.getInstance().trimPrefixForHomeCommunityId(rdNhinDocumentResponse.getHomeCommunityId());
			// get patientID using manager  
			return ParticipantCaseExecutionManager.getInstance().getParticipantPatientIdForRD(participant.getParticipantId()  ,homeCommunityId , documentUniqueId);
		}
		return null;
	}
	
	/**
	 * 
	 * @param transaction
	 * @param msgTypeEnum
	 * @param participantPatientId
	 * @param nhinDocumentSetRequest
	 * @param rdNhinDocumentRequest
	 * @param clientServerDTO
	 */
	private void getCaseExecutionAndSendToCaseresultHandler(Transaction transaction,MsgTypeEnum msgTypeEnum ,String participantPatientId,
			RdNhinDocumentSetRequest nhinDocumentSetRequest, RdNhinDocumentRequest rdNhinDocumentRequest , 
			ClientServerDTO clientServerDTO){
		Caseexecution matchedCaseExecution = null;
		// if patient id found , then update test case status 
		if (StringUtils.isNotEmpty(participantPatientId)) {
			// Get the all Scenario Executions			
			matchedCaseExecution = MessageValidatorUtil.getInstance().findCaseExecutionByParticipantAndPatientId(transaction, msgTypeEnum, clientServerDTO, participantPatientId,false);
		}
		
		// if matched case execution found 
		if(matchedCaseExecution!=null && matchedCaseExecution.getCaseExecutionId()!=null){
			log.info("Case execution Found for Patient Id: " + participantPatientId);
			new RDRequestCaseResultHandler().updateCaseResult(matchedCaseExecution, transaction, msgTypeEnum, nhinDocumentSetRequest, clientServerDTO);
		}else{
			// need to do some thing 
			log.info("No Case execution Found for Patient Id: " + participantPatientId);
		}
	}
	
	
}
