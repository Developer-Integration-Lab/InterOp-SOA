package net.aegis.mv.handler.rd;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.CaseResultHandler;
import net.aegis.mv.jaxb.msg.NhinMessage;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class RDRequestCaseResultHandler extends CaseResultHandler {
	private static final Log log = LogFactory.getLog(RDRequestCaseResultHandler.class);

	protected void updateSingleRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
		
		updateCaseresult(caseexecution, transaction, nhinMessage, clientServerDTO, caseresult);
	}

	
	protected void updateMultiRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
			updateCaseresult(caseexecution, transaction, nhinMessage, clientServerDTO, caseresult);
	}
	
	private void updateCaseresult(Caseexecution caseexecution,  Transaction transaction,NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult){
		RdNhinDocumentSetRequest docsetRequest = (RdNhinDocumentSetRequest)nhinMessage;
		if (caseresult != null	&& (isCaseResultInFinalStatus(caseresult.getResult()))) {
			log.info("<--- caseResult with pass or fail found --->"
					+ caseresult);
			caseresult = null;
		}
		//
		if(caseresult!=null){
			if(caseresult.getResult()!=null && (caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_COMPLETED) || 
					caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_INITIATED) || caseresult.getResult().equalsIgnoreCase(LabConstants.STATUS_PROGRESS))){
				// update to progress as it is executed by our Lab or participant 
				updateCaseResultAndCreateResultsummary(transaction, docsetRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_PROGRESS, "[One RI Scenario] Retrieve Documents - Waiting for RD response");

			}else{
				// update caseResult with failure saying that received the request again .
				updateCaseResult(clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_FAIL,  "[One RI Scenario] Retrieve Documents - Received RD request again ");
				createCaseResultAndResultsummary(caseexecution, transaction, docsetRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), LabConstants.STATUS_PROGRESS,"[One RI Scenario] Retrieve Documents - Waiting for RD response");
			}
		}else{
			// create case result 
			createCaseResultAndResultsummary(caseexecution, transaction, docsetRequest.getMessageID(), clientServerDTO.getParticipantGatewayInfo(), LabConstants.STATUS_PROGRESS,"[One RI Scenario] Retrieve Documents - Waiting for RD response");
		}
	}

}
