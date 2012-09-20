/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.handler.pd;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.INhinMsgHandler;
import net.aegis.mv.jaxb.pd.PDNhinResponse;
import net.aegis.mv.jaxb.pd.Receiver;
import net.aegis.mv.jaxb.pd.Sender;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * Venkat.Keesara
 * Feb 15, 2012
 */
public class PDResponseMsgHandler implements INhinMsgHandler {

	private static final Log log = LogFactory.getLog(PDResponseMsgHandler.class);
	
	@Override
	public void setResponseError(Transaction transaction,
			MsgTypeEnum msgTypeEnum, Caseexecution caseExecution, String relatesTo) {
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
		PDNhinResponse pDNhinResponse = new PDNhinResponse(); 
		pDNhinResponse.setRelatesTo(relatesTo);
		new PDResponseCaseResultHandler().updateCaseResult(caseExecution, transaction, msgTypeEnum, pDNhinResponse, clientServerDTO);
	}
		
	// PD response message related methods Start
	@Override
	public void processMessage(Transaction transaction , INhinMsgParser parser, MsgTypeEnum msgTypeEnum){
		PDNhinResponse pDNhinResponse = (PDNhinResponse)parser.parse(transaction);
		// get client,server , Participant information 
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
				
		// get patientID 
		String patientId = null;
		Sender sender = pDNhinResponse.getSender();
		Receiver receiver = pDNhinResponse.getReceiver();

		// Find Lab (RI,s) or participant patient id  
		Boolean labPatientId = null; 
		// PD response : Correlated patientId i.e RI patient Id in sender(Ex : RI1.201.00009) or participant patient Id in receiver(Ex : CAN20000009)
		if(clientServerDTO.isInitiatorFlag()){
			//	Correlated patientId in sender(Ex : RI1.201.00009)
			if(sender!=null && sender.getPatient()!=null){
				patientId = sender.getPatient().getPatientId();
				labPatientId=true;
				// participant patient Id in receiver (Ex : CAN20000009)	
			}else if(receiver!=null && receiver.getPatient()!=null){
				patientId = receiver.getPatient().getPatientId();
				labPatientId=false;
			}
			// responder scenarios 
		}else{
			// Participant correlated patientId in sender (Ex : CAN20000009) 
			if(sender!=null && sender.getPatient()!=null){
				patientId = sender.getPatient().getPatientId();
				labPatientId=false;
			// Lab patient Id in receiver (Ex : RI1.201.00009)
			}else if(receiver!=null && receiver.getPatient()!=null){
				patientId = receiver.getPatient().getPatientId();
				labPatientId=true;
			}
		}
		
		Caseexecution matchedCaseExecution = null;
		// if correlated patient id found in PD response, then update test case status 
		if (clientServerDTO.getParticipantGatewayInfo() != null && StringUtils.isNotEmpty(patientId)) {
			// Get the all Scenario Executions			
			matchedCaseExecution = MessageValidatorUtil.getInstance().findCaseExecutionByParticipantAndPatientId(transaction, msgTypeEnum, clientServerDTO, patientId,labPatientId);
		}else if(pDNhinResponse.getRelatesTo() != null && StringUtils.isNotEmpty(pDNhinResponse.getRelatesTo())){
			matchedCaseExecution  = MessageValidatorUtil.getInstance().findCaseExecutionByRelatesToAndReceiverHCID(pDNhinResponse.getRelatesTo(), transaction.getSenderHCID(), transaction.getReceiverHCID());
		}
		
		// if matched case execution found 
		if(matchedCaseExecution!=null && matchedCaseExecution.getCaseExecutionId()!=null){
			log.info("Case execution Found =="  + matchedCaseExecution.getCaseExecutionId() + " for Patient Id: " + patientId);
			new PDResponseCaseResultHandler().updateCaseResult(matchedCaseExecution, transaction, msgTypeEnum, pDNhinResponse, clientServerDTO);
		}else{
			// need to do some thing 
			log.info("No Case execution Found for Patient Id: " + patientId);
		}
	}
}
