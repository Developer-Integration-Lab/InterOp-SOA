/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.ws.qd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ScenarioCaseManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.jaxb.qd.DocumentInfoType;
import net.aegis.labcommons.jaxb.qd.ExtrinObjectType;
import net.aegis.labcommons.jaxb.qd.QDEntityResponseType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
*
* Venkat.Keesara
* Nov 16, 2011
*/
public class TestCaseExecutionHelperQD {

    private static final Log log = LogFactory.getLog(TestCaseExecutionHelperQD.class);

    public TestCaseExecutionHelperQD() {
    }

    /**
     *
     * @param candidate
     * @param caseExecution
     * @param scenarioId
     * @param caseId
     * @return <code>TestCaseExecutionResultQD</code>
     * @throws Exception
     */
    public String executeTestCaseHelperQD(Participant participant, Caseexecution caseExecution, int scenarioId, int caseId) {
        QDEntityResponseType result = null;
        String qd_result = "";
        try {
            ScenarioCaseManager executeManager = ScenarioCaseManager.getInstance();
            log.info("TestCaseExecutionHelperQD - executeTestCaseHelperQD Begin.");
            String candidatePatientId = (caseExecution.getParticipantpatientId() == null) ? "" : caseExecution.getParticipantpatientId();
            Map<Object, Object> requestData = (HashMap<Object, Object>) executeManager.fetchRequestData(participant,caseExecution, scenarioId, caseId);
            String ipAddress = requestData.get("ipAddress").toString();
            String localHCID = requestData.get("localHCID").toString();
            Map<String, String> endpoints = (Map<String, String>) requestData.get("endpoints");
            List<String> remoteHCIDList = (List<String>) requestData.get("remoteHCID");


                if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
                    result = executeManager.executeQueryForDoc(participant, scenarioId, caseId, 0, candidatePatientId);
                } else {
                	result = executeManager.executeQueryForDoc(participant, scenarioId, caseId, 0, null);
                }

                qd_result = updateCaseResultQD(result, caseExecution);
            
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        log.info("executeTestCaseHelperQD: END");
        return qd_result;
    }

    /**
     *
     * @param candidate
     * @param rdCaseExecution
     * @param dependentScenarioId
     * @param dependentCaseId
     * @return <code>TestCaseExecutionResultQD</code>
     * @throws Exception
     */
    public TestCaseExecutionResultQD findDependentTestCaseHelperQD(Participant participant, Integer dependentScenarioId, Integer dependentCaseId) throws Exception {
        log.info("findDependentTestCaseHelperQD: START");

        TestCaseExecutionResultQD dependentTestCaseExecutionResultQD = new TestCaseExecutionResultQD();

        if (participant != null && dependentScenarioId != null && dependentCaseId != null) {
            List<String> actionMessagesList = new ArrayList<String>();
            Map<String, String> docsAndRepIds = new HashMap<String, String>();
            String docIds = "";
            String tilde = "~~";

            // Find most recent Case Result Documents for Dependent QD Case Execution based on dependent scenario and case ids
            List<Resultdocument> resultDocuments = CaseResultManager.getInstance().getDocumentsForTestCase(participant.getParticipantId(), dependentScenarioId, dependentCaseId);
            if (resultDocuments != null && resultDocuments.size() > 0) {
                for (Resultdocument resultDocument : resultDocuments) {
                    docsAndRepIds.put(resultDocument.getDocumentUniqueId(), resultDocument.getRepositoryId());
                    if (!docIds.isEmpty()) {
                        docIds += tilde;
                    }
                    docIds += resultDocument.getDocumentUniqueId();
                }
            }

            dependentTestCaseExecutionResultQD.setActionMessagesList(actionMessagesList);
            dependentTestCaseExecutionResultQD.setDocsAndRepIds(docsAndRepIds);
            dependentTestCaseExecutionResultQD.setDocIds(docIds);
        }

        log.info("findDependentTestCaseHelperQD: END");

        return dependentTestCaseExecutionResultQD;
    }

     /**
     * @param result
     * @param caseExecution
     * @return String
     * @throws Exception
     */
    public String updateCaseResultQD(QDEntityResponseType result, Caseexecution caseExecution) {
        CaseResultManager caseResultManager = CaseResultManager.getInstance();
        StringBuffer resultMsg = new StringBuffer();
        String status = LabConstants.STATUS_PROGRESS_PENDING;
        String indicator = null;
        List<DocumentInfoType> documents =  new ArrayList<DocumentInfoType>();
        try {
            if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
            	resultMsg.append("[Initiator Scenario]");
            } else {
                indicator = LabConstants.RESPONDER_IND_Y;
                resultMsg.append("[Responder Scenario]");
            }
            if (result != null) {
				log.info("queryDocument.queryForDocuments() result is null");
				
				if(result.getAdhocQueryResp()!=null && result.getAdhocQueryResp().getRegObjectList()!=null){
					List<ExtrinObjectType> extrinsicObjectsList = result.getAdhocQueryResp().getRegObjectList().getExtrinObject();
				if (extrinsicObjectsList != null && extrinsicObjectsList.size() > 0) {
					
					
					for(ExtrinObjectType extrinsicObjectType :extrinsicObjectsList){
						if(extrinsicObjectType.getDocumentInfo()!=null ){
							documents.add(extrinsicObjectType.getDocumentInfo());
						}
					}
					
					for (DocumentInfoType document : documents) {
						resultMsg.append(" Patient ID " + caseExecution.getPatientId() + " Query for Doocument returned " + documents.size() + " documents!");
						log.info("<br>Document Id: " + document.getDocumentUniqueId());
					}
				} else {
					resultMsg.append(" Patient ID " + caseExecution.getPatientId() + " Query for Doocument returned 0 documents!");
					log.info("  Query for Document returned zero(0) documents!");
				}
				status = LabConstants.STATUS_COMPLETED;
				log.info("This is a initiator scenario QD testcase >>>>>>>>>>>>>>" );
				}
			} else {
				// Create a case result and update the status as failed if a exception occurs in cross gateway communication.
				status = LabConstants.STATUS_INCOMPLETE;
				log.info(" The Test case failed due to error in cross gateway communication.");
			}
            int caseResultId = caseResultManager.saveCaseResult(caseExecution, indicator, status, null, LabConstants.SUBMISSION_IND_N, resultMsg.toString(), null);
            if (caseResultId > 0) {
                log.info("Query Result : Failed - Case result created and has been updated as Failed.");
            } else {
                log.info("Query Result : Failed - Case result has not been created.");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return resultMsg.toString();
    }
}
