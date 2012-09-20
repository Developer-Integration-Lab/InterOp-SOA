package net.aegis.mv.handler.pd;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.CaseResultHandler;
import net.aegis.mv.jaxb.msg.NhinMessage;
import net.aegis.mv.jaxb.pd.PDNhinRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class PDRequestCaseResultHandler extends CaseResultHandler {
	private static final Log log = LogFactory.getLog(PDRequestCaseResultHandler.class);

	protected void updateSingleRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
		
		PDNhinRequest pDNhinRequest = (PDNhinRequest)nhinMessage;
		if (caseresult != null	&& (isCaseResultInFinalStatus(caseresult.getResult()))) {
			log.info("<--- caseResult with pass or fail found --->"
					+ caseresult);
			caseresult = null;
		}
		//
		if(caseresult!=null){
			if(caseresult.getResult()!=null && (caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_COMPLETED) || caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_INITIATED))){
				// update to progress as it is executed by our Lab or participant 
				updateCaseResultAndCreateResultsummary(transaction, pDNhinRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_PROGRESS, "[One RI Scenario] Patient Discovery - Waiting for outbound PD response");

			}else{
				// update caseResult with failure saying that received the request again .
				updateCaseResult(clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_FAIL,  "[One RI Scenario] Patient Discovery - Received PD request again ");
				createCaseResultAndResultsummary(caseexecution, transaction, pDNhinRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), LabConstants.STATUS_PROGRESS,"[One RI Scenario] Patient Discovery - Waiting for outbound PD response");
			}
		}else{
			// create case result 
			createCaseResultAndResultsummary(caseexecution, transaction, pDNhinRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), LabConstants.STATUS_PROGRESS,"[One RI Scenario] Patient Discovery - Waiting for outbound PD response");
		}
	}
	
	protected void updateMultiRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {

		PDNhinRequest pDNhinRequest = (PDNhinRequest)nhinMessage;
		if (caseresult != null	&& (isCaseResultInFinalStatus(caseresult.getResult()))) {
			log.info("<--- caseResult with pass | fail | Incomplete | clear found  --->"
					+ caseresult);
			caseresult = null;
		}
		String statusMessage = "";
		if(caseresult!=null && caseresult.getResult()!=null){
			log.info("case result status==" + caseresult.getResult());
			// if case result exist with completed or initiated status ( for responder scenarios)
			if( caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_COMPLETED) || caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_INITIATED)){
				// update to progress as it is executed by our Lab or participant 
				statusMessage="[Multi RI Scenario] Patient Discovery - Waiting for outbound PD response";
				log.info(statusMessage);
				updateCaseResultAndCreateResultsummary(transaction, pDNhinRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_PROGRESS, statusMessage);

			}else{

				if(caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_PROGRESS)){

					boolean requestFromSameRI = isRequestFromSameRI(caseresult, transaction);
					if(requestFromSameRI){
						statusMessage ="[Multi RI Scenario] Patient Discovery - Request received again ";	
						// update caseResult with failure saying that received the request again .
						log.info(statusMessage);
						updateCaseResult(clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_FAIL,  statusMessage );
						statusMessage="[Multi RI Scenario] Patient Discovery - Request received again and waiting for outbound PD response";
						log.info(statusMessage);
						createCaseResultAndResultsummary(caseexecution, transaction, pDNhinRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), LabConstants.STATUS_PROGRESS,statusMessage);
					}else{
						statusMessage="[Multi RI Scenario] Patient Discovery - Request received from second RI and waiting for outbound PD response";
						log.info(statusMessage);
						updateCaseResultAndCreateResultsummary(transaction, pDNhinRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_PROGRESS,statusMessage);
					}
				}

			}
		}else{
			log.info("case result status== NULL " );
			statusMessage="[Multi RI Scenario] Patient Discovery - Request received from first RI and waiting for outbound PD response";
			log.info(statusMessage);
			// create case result 
			createCaseResultAndResultsummary(caseexecution, transaction, pDNhinRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), LabConstants.STATUS_PROGRESS,statusMessage);
		}
	}

	
}
