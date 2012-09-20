/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.handler.pd;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.exception.MVRuntimeException;
import net.aegis.mv.handler.INhinMsgHandler;
import net.aegis.mv.jaxb.pd.PDNhinRequest;
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
public class PDRequestMsgHandler implements INhinMsgHandler {

	private static final Log log = LogFactory.getLog(PDRequestMsgHandler.class);
			
	@Override
	public void setResponseError(Transaction transaction,
			MsgTypeEnum msgTypeEnum, Caseexecution caseExecution, String relatesTo) {
		throw new MVRuntimeException("Method not implemented.");		
	}
	
	/**
	 * 
	 */
	@Override
	public void processMessage(Transaction transaction , INhinMsgParser parser, MsgTypeEnum msgTypeEnum){		
		PDNhinRequest pDNhinRequest = (PDNhinRequest)parser.parse(transaction);
		ClientServerDTO clientServerDTO = MessageValidatorUtil.getInstance().getClientServerDTO(transaction);
		// get patientID 
		String patientId = null;
		Sender sender = pDNhinRequest.getSender();
		
		// retrieve  patient id from sender  
		if(sender!=null && sender.getPatient()!=null){
			patientId = sender.getPatient().getPatientId();
		}
		Boolean labPatientId = null;
		if(clientServerDTO.isInitiatorFlag()){
			labPatientId = false;
		}else{
			labPatientId = true;
		}
		
		if (clientServerDTO.getParticipantGatewayInfo() != null &&  StringUtils.isNotEmpty(patientId)) {
            
			// Get the all Scenario Executions			
				Caseexecution matchedCaseExecution  = MessageValidatorUtil.getInstance().findCaseExecutionByParticipantAndPatientId(transaction, msgTypeEnum, clientServerDTO, patientId,labPatientId);
				// if matched case execution found 
				if(matchedCaseExecution!=null && matchedCaseExecution.getCaseExecutionId()!=null){
					log.info("Case execution Found for Patient Id: " + patientId);
					new PDRequestCaseResultHandler().updateCaseResult(matchedCaseExecution, transaction, msgTypeEnum, pDNhinRequest, clientServerDTO);
				}else{
					// need to do some thing 
					log.info("No Case execution Found for Patient Id: " + patientId);
				}
		}		
	}		
}
