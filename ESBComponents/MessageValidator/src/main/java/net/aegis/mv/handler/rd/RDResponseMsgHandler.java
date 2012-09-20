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
import net.aegis.mv.handler.INhinMsgHandler;
import net.aegis.mv.jaxb.rd.RdNhinDocumentResponse;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetResponse;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.parser.rd.RDResponseParser;
import net.aegis.mv.util.MessageValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
*
* Venkat.Keesara
* May 10, 2012
*/
public class RDResponseMsgHandler implements INhinMsgHandler {
	private static final Log log = LogFactory.getLog(RDResponseMsgHandler.class);
	
	@Override
	public void processMessage(Transaction transaction, INhinMsgParser parser, MsgTypeEnum msgTypeEnum) {
		log.debug(this.getClass().getSimpleName() + ".processMessage(): Processing transaction ID: " + transaction.getId());
		RdNhinDocumentSetResponse docsetResponse = RDResponseParser.getInstance().parse(transaction);
		List<RdNhinDocumentResponse> responseSet = docsetResponse.getResponseSet();
		if(responseSet == null || responseSet.size() == 0) {
			return ;
		}
		processRDResponse(transaction, msgTypeEnum, docsetResponse);
	}

	@Override
	public void setResponseError(Transaction transaction,
			MsgTypeEnum msgTypeEnum, Caseexecution caseExecution,
			String relatesTo) {
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
		RdNhinDocumentSetResponse docsetResponse = new RdNhinDocumentSetResponse(); 
		docsetResponse.setRelatesTo(relatesTo);
		new RDResponseCaseResultHandler().updateCaseResult(caseExecution, transaction, msgTypeEnum, docsetResponse, clientServerDTO);
		
	}
	
	/**
	 *  1) Get document id and HCID from the RD request message
		2) Use document id and HCID in the ResultDocument table to find out the dependent QD execution
		3) Identify the patient id from the latest RD / QD execution // Note : 2,3 can be combined
		4) Identify RD test case using the patient id
		5) Update case execution to PROGRESS
	 * @param transaction
	 * @param msgTypeEnum
	 * @param nhinDocumentSetResponse
	 */
	private void processRDResponse(Transaction transaction,MsgTypeEnum msgTypeEnum , RdNhinDocumentSetResponse nhinDocumentSetResponse){
		
		String participantPatientId = null;	
		Boolean isPatientIdFound = false; 
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
		
		List<RdNhinDocumentResponse> responseSet = nhinDocumentSetResponse.getResponseSet();
		String relatesTo = nhinDocumentSetResponse.getRelatesTo();
		RdNhinDocumentResponse matchedRdNhinDocumentResponse = null;
		for(RdNhinDocumentResponse rdNhinDocumentResponse :  responseSet){
			log.debug("Home Community ID: " + rdNhinDocumentResponse.getHomeCommunityId());
			log.debug("Repository ID: " + rdNhinDocumentResponse.getRepositoryId());
			log.debug("Document Unique ID: " + rdNhinDocumentResponse.getDocumentUniqueId());
			
			participantPatientId = getParticipantPatientId(rdNhinDocumentResponse, clientServerDTO);
			if(StringUtils.isNotEmpty(participantPatientId)){
				isPatientIdFound = true;
				matchedRdNhinDocumentResponse= rdNhinDocumentResponse;
				break;
			}
		}
		
		if(isPatientIdFound){
			getCaseExecutionAndSendToCaseresultHandler(transaction, msgTypeEnum, participantPatientId, nhinDocumentSetResponse, matchedRdNhinDocumentResponse, clientServerDTO, relatesTo);
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
	private String getParticipantPatientId(RdNhinDocumentResponse rdNhinDocumentResponse , ClientServerDTO clientServerDTO){
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
	 * @param nhinDocumentSetResponse
	 * @param rdNhinDocumentResponse
	 * @param clientServerDTO
	 * @param relatesTo
	 */
	private void getCaseExecutionAndSendToCaseresultHandler(Transaction transaction,MsgTypeEnum msgTypeEnum ,String participantPatientId,
			RdNhinDocumentSetResponse nhinDocumentSetResponse, RdNhinDocumentResponse rdNhinDocumentResponse , 
			ClientServerDTO clientServerDTO, String relatesTo){
		Caseexecution matchedCaseExecution = null;
		// if patient id found , then update test case status 
		if (StringUtils.isNotEmpty(participantPatientId)) {
			// Get the all Scenario Executions			
			matchedCaseExecution = MessageValidatorUtil.getInstance().findCaseExecutionByParticipantAndPatientId(transaction, msgTypeEnum, clientServerDTO, participantPatientId,false);
		}else if(relatesTo != null && StringUtils.isNotEmpty(relatesTo)){
			matchedCaseExecution  = MessageValidatorUtil.getInstance().findCaseExecutionByRelatesToAndReceiverHCID(relatesTo, transaction.getSenderHCID(), transaction.getReceiverHCID());
		}
		
		// if matched case execution found 
		if(matchedCaseExecution!=null && matchedCaseExecution.getCaseExecutionId()!=null){
			log.info("Case execution Found for Patient Id: " + participantPatientId);
			new RDResponseCaseResultHandler().updateCaseResult(matchedCaseExecution, transaction, msgTypeEnum, nhinDocumentSetResponse, clientServerDTO);
		}else{
			// need to do some thing 
			log.info("No Case execution Found for Patient Id: " + participantPatientId);
		}
	}

}
