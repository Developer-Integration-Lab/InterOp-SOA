/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.handler.qd;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.INhinMsgHandler;
import net.aegis.mv.jaxb.qd.QDNhinResponse;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * Naresh.Jaganathan
 * March 20, 2012
 */
public class QDResponseMsgHandler implements INhinMsgHandler {

	private static final Log log = LogFactory.getLog(QDResponseMsgHandler.class);
		
	@Override
	public void setResponseError(Transaction transaction,
			MsgTypeEnum msgTypeEnum, Caseexecution matchedCaseExecution, String relatesTo) {
		// TODO Auto-generated method stub
		QDNhinResponse qDNhinResponse = new QDNhinResponse();
		qDNhinResponse.setRelatesTo(relatesTo);
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
		//handleCaseExecutionForQD(matchedCaseExecution, transaction, msgTypeEnum, qDNhinResponse, clientServerDTO);
		new QDResponseCaseResultHandler().updateCaseResult(matchedCaseExecution, transaction, msgTypeEnum, qDNhinResponse, clientServerDTO);
	}

	@Override
	public void processMessage(Transaction transaction , INhinMsgParser parser, MsgTypeEnum msgTypeEnum){
		QDNhinResponse qDNhinResponse = (QDNhinResponse)parser.parse(transaction);
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
		//Reverse the Initiator Flag since the QD request and response message has the responder patientId
		Boolean labPatientId = null; 
		if(clientServerDTO.isInitiatorFlag()){
			labPatientId = true;
		}else{
			labPatientId = false;
		}
		
		Participant participant = MessageValidatorUtil.getInstance().getParticipantInfo(transaction,clientServerDTO.getParticipantGatewayInfo());
		// get patientID 
		String patientId = null;
		Caseexecution matchedCaseExecution = null;
		if(qDNhinResponse.getResponderPatientID()!=null){
			patientId = qDNhinResponse.getResponderPatientID();
		}

		if (participant != null &&  StringUtils.isNotEmpty(patientId)) {
			// Get the all Scenario Executions			
			matchedCaseExecution  = MessageValidatorUtil.getInstance().findCaseExecutionByParticipantAndPatientId(transaction, msgTypeEnum, clientServerDTO, patientId, labPatientId);
		}else if(qDNhinResponse.getRelatesTo() != null && StringUtils.isNotEmpty(qDNhinResponse.getRelatesTo())){
			matchedCaseExecution  = MessageValidatorUtil.getInstance().findCaseExecutionByRelatesToAndReceiverHCID(qDNhinResponse.getRelatesTo(), transaction.getSenderHCID(), transaction.getReceiverHCID());
		}

		// if matched case execution found 
		if(matchedCaseExecution!=null && matchedCaseExecution.getCaseExecutionId()!=null){
			log.info("Case execution Found for Patient Id: " + patientId);
			new QDResponseCaseResultHandler().updateCaseResult(matchedCaseExecution, transaction, msgTypeEnum, qDNhinResponse, clientServerDTO);
		}else{
			// need to do some thing 
			log.info("No Case execution Found for Patient Id: " + patientId);
		}
	}	
}
