package net.aegis.md.ws.pd;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ScenarioCaseManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.enums.PDStatusEnum;
import net.aegis.labcommons.jaxb.pd.CommunityResponseType;
import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;
import net.aegis.labcommons.jaxb.pd.ReceiverType;
import net.aegis.labcommons.service.PatientDiscovery;
import net.aegis.md.exception.MDRuntimeException;
import net.aegis.md.util.ServiceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
public class TestCaseExecutionHelperPD {

    private static final Log log = LogFactory.getLog(TestCaseExecutionHelperPD.class);

    public TestCaseExecutionHelperPD() {
    }

    public String executeTestCasePD(Participant participant, Caseexecution caseExecution, int scenarioId, int caseId) throws MDRuntimeException {
        log.info("TestCaseExecutionHelperPD - executeTestCasePD : BEGIN");
        String resultValue = null;
        StringBuffer message = new StringBuffer();
        PDEntityResponseType result = null;
//        try {
            ScenarioCaseManager executeManager = ScenarioCaseManager.getInstance();
            CaseResultManager caseResultManager = CaseResultManager.getInstance();
            Map<Object, Object> requestData = (HashMap<Object, Object>) executeManager.fetchRequestData(participant, caseExecution, scenarioId, caseId);
            String ipAddress = requestData.get("ipAddress").toString();
            String localHCID = requestData.get("localHCID").toString();
            Map<String, String> endpoints = (Map<String, String>) requestData.get("endpoints");
            List<String> remoteHCIDList = (List<String>) requestData.get("remoteHCID");
            String endpointURL = endpoints.get("PD");
            String version = caseExecution.getVersion();
            if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
            	version = participant.getVersion();
            }
            String indicator = null;
            if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
            	message.append("[Initiator Scenario] Patient Discovery ");
            } else {
                indicator = LabConstants.RESPONDER_IND_Y;
                message.append("[Responder Scenario] Patient Discovery ");
            }

            // Get the start time based on participating test harness ri
            Timestamp timestampStart = caseResultManager.getStartTimeForCaseExecution(caseExecution);
            if (timestampStart == null) {
                // Default to current time on InteropLab server if RI time cannot be determined
                timestampStart = new Timestamp(new Date().getTime());
            }
            String successCriteria = (caseExecution.getSuccessCriteria() == null) ? "" : caseExecution.getSuccessCriteria();
            String participatingRI = (caseExecution.getParticipatingRIs() == null) ? "" : caseExecution.getParticipatingRIs();
            String participantPatientId = (caseExecution.getParticipantpatientId() == null) ? "" : caseExecution.getParticipantpatientId();
            Timestamp correlationExpirationDate = caseResultManager.getCorrelationExpirationDate(participatingRI, localHCID, participantPatientId);
            log.info("LabParticipant: TestCaseExecutionHelperPD - successCriteria: " + successCriteria);
            log.info("LabParticipant: TestCaseExecutionHelperPD - participatingRI: " + participatingRI);
            log.info("LabParticipant: TestCaseExecutionHelperPD - candidatePatientId: " + participantPatientId);
            log.info("LabParticipant: TestCaseExecutionHelperPD - correlationExpirationDate: " + correlationExpirationDate);

            //Read the config file from lab database
            String config = executeManager.getScenarioCaseConfig(scenarioId, caseId);
            PatientDiscovery patientDiscovery = ServiceFactory.getInstance().getPDService(version);
            
            int caseResultId = -1;
            //Create a case result for the testcase execution
            caseResultId = caseResultManager.saveCaseResult(caseExecution, indicator, LabConstants.STATUS_INITIATED, null, LabConstants.SUBMISSION_IND_N, message +"Initiated.", LabConstants.MD_USER);
            if (caseResultId > 0) {
                log.info("Patient Discovery Result : Case result has been created and updated.");
            } else {
                log.info("Patient Discovery Result : Case result has not created.");
            }
            //Start the cross gateway communication by calling the method in the gateway client (InteropWS_C2.4.8)
            if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
                //Initiator Scenarios
                result = patientDiscovery.correlatePatient(endpointURL, localHCID, remoteHCIDList, config, participantPatientId);
            } else {
                //Responder Scenarios
                result = patientDiscovery.correlatePatient(endpointURL, localHCID, remoteHCIDList, config);
            }
            log.info("\n\n*********** Patient Discovery Query Results ************\n\n");

            resultValue = updateCaseResultPD(result, caseResultManager, caseExecution, caseResultId);
            log.info(resultValue);
     
	        log.info("TestCaseExecutionHelperPD - executeTestCaseHelperPD : END");
	        return resultValue;
    }

    public String updateCaseResultPD(PDEntityResponseType result, CaseResultManager caseResultManager, Caseexecution caseExecution, int caseResultId) throws MDRuntimeException{
        log.info("TestCaseExecutionHelperPD - updateCaseResultPD : BEGIN");
        String status = LabConstants.STATUS_PROGRESS;
        StringBuffer resultMessage = new StringBuffer();
        String correlatedPatientId = null;
        String assigningAuthId = null;
//        try {
            if (result != null) {
                if (result.getCommunityResponse() != null && result.getCommunityResponse().size() > 0) {
                    boolean correlated = false;
                    for (int i = 0; i < result.getCommunityResponse().size(); i++) {
                        correlatedPatientId = null;
                        assigningAuthId = null;
                        correlated = true;
                        // Test response for any null values
                        if (result.getCommunityResponse().get(i) != null ) {
                        	CommunityResponseType communityResponse = result.getCommunityResponse().get(i);
                    		ReceiverType receiver =  communityResponse.getReceiver();
                    		if(result.getCommunityResponse().get(i).getStatus()!=null && result.getCommunityResponse().get(i).getStatus().equals(PDStatusEnum.FAILURE.getTextId())){
                    			resultMessage.append("Incomplete:<br>" + PDStatusEnum.FAILURE.getDefaultDescription());
                    			status = LabConstants.STATUS_INCOMPLETE;
                    		}else if(receiver!=null && receiver.getPatient()!=null){
                    			correlatedPatientId = receiver.getPatient().getPatientId();
                    			assigningAuthId = receiver.getPatient().getAssigningAuthority();
	                            if (correlatedPatientId != null && !correlatedPatientId.trim().equals("")) {
	                                log.info("Correlated Patient Id: " + correlatedPatientId);
	                                resultMessage.append("<br>Correlated Patient Id: " + correlatedPatientId);
	                            }
	                            if (assigningAuthId != null && !assigningAuthId.trim().equals("")) {
	                                log.info("Correlated Patient Assigning Authority Id: " + assigningAuthId);
	                                resultMessage.append("<br>Correlated Patient Assigning Authority Id: " + assigningAuthId);
	                            }
	                            status = LabConstants.STATUS_COMPLETED;
                    		}else{
                            	resultMessage.append("Patient correlation not found.");
                            	status = LabConstants.STATUS_COMPLETED;
                            }
                    		
                        }/*else{
                        	resultMessage.append("Patient response messaage not found.");
                        	status = LabConstants.STATUS_INCOMPLETE;
                        }*/
                       
                    }
                }else{
                     resultMessage.append("Patient response not populated!");
                     status = LabConstants.STATUS_INCOMPLETE;
                }
                
            } else {
                status = LabConstants.STATUS_INCOMPLETE;
                resultMessage.append("The Test case failed due to error in cross gateway communication.");
                log.info("Failed - Patient response not found!");
            }
            //**************************************************************************************************
            //Update a case result for the testcase execution
            Caseresult caseresult = caseResultManager.getCaseResult(caseResultId);
            if(caseresult != null && caseresult.getResult().equals(LabConstants.STATUS_INITIATED)){
            	caseresult.setResult(status);
            	caseresult.setMessage(resultMessage.toString());
            	caseresult.setUpdatedAt(DateUtil.getTodayDateTime());
             	caseresult.setUpdatedBy(LabConstants.MD_USER);
            	caseResultManager.update(caseresult);
            }
        log.info("TestCaseExecutionHelperPD - updateCaseResultPD : BEGIN" + resultMessage.toString());
        return resultMessage.toString();
    }
}
