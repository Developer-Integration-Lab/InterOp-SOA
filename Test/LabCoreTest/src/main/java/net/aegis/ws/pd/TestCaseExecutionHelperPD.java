/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.ws.pd;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ScenarioCaseManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.jaxb.pd.CommunityResponseType;
import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;
import net.aegis.labcommons.jaxb.pd.ReceiverType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * Venkat.Keesara
 * Nov 15, 2011
 */
public class TestCaseExecutionHelperPD {
	private static final Log log = LogFactory.getLog(TestCaseExecutionHelperPD.class);

    public TestCaseExecutionHelperPD() {
    	System.out.println("PDHelper   Contructor called ");
    }

    public String executeTestCasePD(Participant participant, Caseexecution caseExecution, int scenarioId, int caseId) throws Exception {
        String resultValue = null;
        PDEntityResponseType result = null;
        try {
        	 System.out.println("PDHelper - Before ScenarioCaseManager call.");
            ScenarioCaseManager executeManager = ScenarioCaseManager.getInstance();
            System.out.println("PDHelper - After ScenarioCaseManager call.");
            System.out.println("PDHelper - Before CaseResultManager call.");
            CaseResultManager caseResultManager = CaseResultManager.getInstance();
            System.out.println("PDHelper - After CaseResultManager call.");
            System.out.println("PDHelper - executeTestCaseHelperPD Begin.");

            //**************************************************************************************************

            Map<Object, Object> requestData = (HashMap<Object, Object>) executeManager.fetchRequestData(participant,caseExecution, scenarioId, caseId);
            String ipAddress = requestData.get("ipAddress").toString();
            String localHCID = requestData.get("localHCID").toString();
            Map<String, String> endpoints = (Map<String, String>) requestData.get("endpoints");
            List<String> remoteHCIDList = (List<String>) requestData.get("remoteHCID");
            String endpointURL = endpoints.get("PD");

            //**************************************************************************************************

            // Get the start time based on participating test harness ri
            Timestamp timestampStart = caseResultManager.getStartTimeForCaseExecution(caseExecution);
            if (timestampStart == null) {
                // Default to current time on InteropLab server if RI time cannot be determined
                timestampStart = new Timestamp(new Date().getTime());
            }
            String successCriteria = (caseExecution.getSuccessCriteria() == null) ? "" : caseExecution.getSuccessCriteria();
            String participatingRI = (caseExecution.getParticipatingRIs() == null) ? "" : caseExecution.getParticipatingRIs();
            String candidatePatientId = (caseExecution.getParticipantpatientId() == null) ? "" : caseExecution.getParticipantpatientId();
            Timestamp correlationExpirationDate = caseResultManager.getCorrelationExpirationDate(participatingRI, localHCID, candidatePatientId);
            System.out.println("PDHelper - successCriteria: " + successCriteria);
            System.out.println("PDHelper - participatingRI: " + participatingRI);
            System.out.println("PDHelper - candidatePatientId: " + candidatePatientId);
            System.out.println("PDHelper - correlationExpirationDate: " + correlationExpirationDate);

            //**************************************************************************************************
            result = executeManager.executePatientCorrelation(participant, scenarioId, caseId, candidatePatientId);
            System.out.println("\n\n*********** Patient Discovery Query Results ************\n\n");

            //Read the result and update the test case status in the testlab database
            if (result != null && result.getCommunityResponse().size() > 0) {
                System.out.println("Query Result  = Success");
                resultValue = updateCaseResultPD(result, caseResultManager, caseExecution);
                System.out.println(resultValue);
            } else {
                // Create a case result and update the status as failed if a exception occurs in cross gateway communication.
                String indicator = null;
                String resultMsg = "The Test case failed due to error in cross gateway communication.";
                if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
                    resultMsg = "[Initiator Scenario] " + resultMsg;
                } else {
                    indicator = LabConstants.RESPONDER_IND_Y;
                    resultMsg = "[Responder Scenario] " + resultMsg;
                }
                int caseResultId = caseResultManager.saveCaseResult(caseExecution, indicator, LabConstants.STATUS_FAIL, null, LabConstants.SUBMISSION_IND_N, resultMsg, null);
                resultValue = LabConstants.STATUS_FAIL;
                if (caseResultId > 0) {
                    System.out.println("Query Result : Failed - Case result created and has been updated as Failed.");
                } else {
                    System.out.println("Query Result : Failed - Case result has not been created.");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultValue;
    }

    public String updateCaseResultPD(PDEntityResponseType result, CaseResultManager caseResultManager, Caseexecution caseExecution) {
        log.info("TestCaseExecutionHelperPD - updateCaseResultPD : BEGIN");
        String status = LabConstants.STATUS_PROGRESS_PENDING;
        StringBuffer resultMessage = new StringBuffer();
        String indicator = null;
        if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
            resultMessage.append("[Initiator Scenario] Patient Discovery ");
        } else {
            indicator = LabConstants.RESPONDER_IND_Y;
            resultMessage.append("[Responder Scenario] Patient Discovery ");
        }

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
                    		if(receiver!=null && receiver.getPatient()!=null){
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
                    		}
                        	
                        }else{
                        	resultMessage.append("Patient correlation not found.");
                        }
                        // status = LabConstants.STATUS_COMPLETED;
                    }
                }else{
                     resultMessage.append("Patient response not populated!");
                }
//                } else {
//                    status = LabConstants.STATUS_FAIL;
//                    resultMessage.append("Failed - Patient response not populated!");
//                    log.info("Failed - Patient response not populated!");
//                }
                status = LabConstants.STATUS_COMPLETED;
            } else {
                status = LabConstants.STATUS_INCOMPLETE;
                resultMessage.append("The Test case failed due to error in cross gateway communication.");
                log.info("Failed - Patient response not found!");
            }
            //**************************************************************************************************
            int caseResultId = -1;
            //Create a case result for the testcase execution
            caseResultId = caseResultManager.saveCaseResult(caseExecution, indicator, status, null, LabConstants.SUBMISSION_IND_N, resultMessage.toString(), null);
            if (caseResultId > 0) {
                log.info("Patient Discovery Result : Case result has been created and updated.");
            } else {
                log.info("Patient Discovery Result : Case result has not created.");
            }
        /* } 
        catch (ServiceException ex) {
        	log.error(ex.getMessage());
        	throw new MDRuntimeException(ex);
        	
		} catch (RuntimeException ex) {
			log.error("Exception occured in resultResponderTestCase() while processing the responder testcase result - " + ex.getMessage());
			throw new MDRuntimeException(ex);

		}*/
        log.info("TestCaseExecutionHelperPD - updateCaseResultPD : BEGIN" + resultMessage.toString());
        return resultMessage.toString();
    }
}
