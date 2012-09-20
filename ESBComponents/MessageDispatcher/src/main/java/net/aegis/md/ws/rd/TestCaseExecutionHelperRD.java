package net.aegis.md.ws.rd;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ScenarioCaseManager;
import net.aegis.lab.manager.TestHarnessriManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.jaxb.rd.DocResponseType;
import net.aegis.labcommons.jaxb.rd.RDEntityResponseType;
import net.aegis.labcommons.jaxb.rd.RetrieveDocSetResponseType;
import net.aegis.labcommons.service.RetrieveDocument;
import net.aegis.md.exception.MDRuntimeException;
import net.aegis.md.util.ServiceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
public class TestCaseExecutionHelperRD {

    private static final Log log = LogFactory.getLog(TestCaseExecutionHelperRD.class);

    public TestCaseExecutionHelperRD() {
    }

    public String executeTestCaseRD(Participant participant, Caseexecution caseExecution, int scenarioId, int caseId, Map<String, String> passedDocAndRepIds) throws MDRuntimeException {
        log.info("TestCaseExecutionHelperRD - executeTestCaseRD : BEGIN");
        log.info("ScenarioCaseManager.executeTestCaseRD(): parameters passed are as below:\n" +
                "scenarioId=" + scenarioId + " caseId=" + caseId + " passedDocAndRepIds = "+passedDocAndRepIds);

        RDEntityResponseType docResult = null;
        String rd_result = "";
        StringBuffer resultMessage = new StringBuffer();
		String indicator = null;
        try {
            ScenarioCaseManager executeManager = ScenarioCaseManager.getInstance();
            CaseResultManager caseResultManager = CaseResultManager.getInstance();
            TestHarnessriManager testHarnessriManager = TestHarnessriManager.getInstance();
            Map<Object, Object> requestData = (HashMap<Object, Object>) executeManager.fetchRequestData(participant, caseExecution, scenarioId, caseId);
            String localHCID = requestData.get("localHCID").toString();
            Map<String, String> endpoints = (Map<String, String>) requestData.get("endpoints");
            Map<String, String> remoteHCID = testHarnessriManager.getTestHarnessriCommIdsByVersion(caseExecution.getVersion());
            String endpointURL = endpoints.get("RD");
            String version = caseExecution.getVersion();
            if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
            	version = participant.getVersion();
            }

            if (remoteHCID == null) {
                log.info("Remote HCID list is null");
            } else {
                // ADD CURRENT CANDIDATE TO remoteHCID MAP WITH ID = 99 -- THIS SUPPORTS RESPONDER SCENARIO CASES
                remoteHCID.put("99", participant.getCommunityId());
            }
            if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
                resultMessage.append("[Initiator Scenario] Retrieve Document ");
            } else {
                indicator = LabConstants.RESPONDER_IND_Y;
                resultMessage.append("[Responder Scenario] Retrieve Document ");
            }

            /*
             * Determine whether to use "happy path" config file or "alternate rainy path" config file
             */
            String config = executeManager.getScenarioCaseConfig(scenarioId, caseId); // happy path
            RetrieveDocument retrieveDoc = ServiceFactory.getInstance().getRDService(version);
    		int caseResultId = -1;
            //Create a case result for the test case execution
            caseResultId = caseResultManager.saveCaseResult(caseExecution, indicator, LabConstants.STATUS_INITIATED, null, LabConstants.SUBMISSION_IND_N, resultMessage +"Initiated.", LabConstants.MD_USER);
            if (caseResultId > 0) {
                log.info("Retrieve Document Result : Case result has been created.");
            } else {
                log.info("Retrieve Document Result : Case result has not created.");
            }
            
            /*
             * Determine RD call based on passedDocAndRepIds value
             */
            if (passedDocAndRepIds != null && passedDocAndRepIds.size() > 0) {
                docResult = retrieveDoc.retrieveDocuments(endpointURL, localHCID, remoteHCID, config, passedDocAndRepIds,null);
            } else {
                docResult = retrieveDoc.retrieveDocuments(endpointURL, localHCID, remoteHCID, config);
            }        

            rd_result = updateCaseResultRD(docResult, caseExecution, caseResultId);

        } catch (ServiceException ex) {
            log.error("Exception", ex);
            throw new MDRuntimeException(ex);
        }
        log.info("TestCaseExecutionHelperRD - executeTestCaseRD : END");
        return rd_result;
    }

    public String updateCaseResultRD(RDEntityResponseType docResult, Caseexecution caseExecution, int caseResultId)  throws ServiceException{
        log.info("TestCaseExecutionHelperRD - updateCaseResultRD : BEGIN");
        CaseResultManager caseResultManager = CaseResultManager.getInstance();
        String status = LabConstants.STATUS_PROGRESS_PENDING;
        StringBuffer resultMessage = new StringBuffer();
        
//        try {
            // Get the start time based on participating test harness ri
            Timestamp timestampStart = caseResultManager.getStartTimeForCaseExecution(caseExecution);
            if (timestampStart == null) {
                // Default to current time on InteropLab server if RI time cannot be determined
                timestampStart = new Timestamp(new Date().getTime());
            }

            String participantPatientId = caseExecution.getParticipantpatientId();
            log.info("updateCaseResultRD - participantPatientId: " + participantPatientId);

            if (docResult != null && docResult.getRetrieveDocSetResponse() != null && docResult.getRetrieveDocSetResponse().getDocResponse()!=null) {
                status = LabConstants.STATUS_COMPLETED;
            } else {
                // Create a case result and update the status as failed if a exception occurs in cross gateway communication.
                status = LabConstants.STATUS_INCOMPLETE;
                resultMessage.append(" The Test case failed due to error in cross gateway communication.");
            }
            
            //Update a case result for the test case execution
            Caseresult caseresult = caseResultManager.getCaseResult(caseResultId);
            if(caseresult != null && caseresult.getResult().equals(LabConstants.STATUS_INITIATED)){
            	caseresult.setResult(status);
            	caseresult.setMessage(resultMessage.toString());
            	caseresult.setUpdatedAt(DateUtil.getTodayDateTime());
             	caseresult.setUpdatedBy(LabConstants.MD_USER);
            	caseResultManager.update(caseresult);
            }
            
            if (caseResultId > 0 &&  caseresult.getResult().equals(LabConstants.STATUS_COMPLETED)) {
	            if (docResult != null && docResult.getRetrieveDocSetResponse() != null && docResult.getRetrieveDocSetResponse().getDocResponse()!=null) {
	            	
	            	String saveMsg = saveDocuments(caseResultManager, docResult.getRetrieveDocSetResponse(), caseresult);
	                if (saveMsg != null && !saveMsg.equals("")) {
	                	caseresult.setMessage(saveMsg);
	                    caseResultManager.update(caseresult);
	                }
	                resultMessage.append(saveMsg);
	            }
            }
       /* } catch (Exception ex) {
            throw new MDRuntimeException(ex);
        }*/
        log.info("TestCaseExecutionHelperRD - updateCaseResultRD : END");
        return resultMessage.toString();
    }

    private String saveDocuments(CaseResultManager caseResultManager, RetrieveDocSetResponseType docResult, Caseresult caseResult) throws ServiceException {
        log.info("TestCaseExecutionHelperRD - saveDocuments : BEGIN");
        StringBuffer resultMsg = new StringBuffer();
//        try {
            if (docResult.getDocResponse() != null && docResult.getDocResponse().size() > 0) {
                resultMsg.append(" Retrieve Document returned " + docResult.getDocResponse().size() + " documents!");
                for (DocResponseType documentResponse : docResult.getDocResponse()) {
                 //   resultMsg.append("<br>Document Id: " + documentResponse.getDocumentUniqueId());
                    if (documentResponse.getDocument() != null) {
                        int docResultId = -1;
                        if (caseResult != null) {
                            docResultId = caseResultManager.saveResultDocument(caseResult, documentResponse.getDocumentUniqueId(),
                                    documentResponse.getRepositoryUniqueId(), documentResponse.getHomeCommunityId(), documentResponse.getDocument(),null,null,0, LabConstants.LAB_DOCRETRIEVE, LabConstants.MD_USER);
                            if (docResultId < 0) {
                                log.warn("Error: ExecuteTestCase RD - ResultDocument for " + documentResponse.getDocumentUniqueId() + " not saved!");
                                resultMsg.append(" ResultDocument (RD) for " + documentResponse.getDocumentUniqueId() + " not saved!");
                            }
                        }
                    } else {
                        log.warn("Error: ExecuteTestCase RD - ResultDocument content for " + documentResponse.getDocumentUniqueId() + " is null and was not saved!");
                        resultMsg.append(" ResultDocument (RD) content for " + documentResponse.getDocumentUniqueId() + " is not defined and was not saved!");
                    }
                }
                //  resultMsg.append("<br>Click to display retrieved document(s)&nbsp;<input type=\"button\" class=\"inputButton\" name=\"docs\" id=\"docs\" value=\"Display Documents\" onclick=\"displayDocuments();\"\\>");
            } else {
                resultMsg.append(" Retrieve Document returned zero(0) documents!");
            }
        /*} catch (Exception ex) {
        	  throw new MDRuntimeException(ex);
        }*/
        log.info("TestCaseExecutionHelperRD - saveDocuments : END");
        return resultMsg.toString();
    }

    /**
     *
     * @param candidate
     * @param rdCaseExecution
     * @param dependentScenarioId
     * @param dependentCaseId
     * @return Map<String, String>
     * @throws Exception
     */
    public Map<String, String> findResultDocumentsForDependentTestCase(Participant participant, Integer dependentScenarioId, Integer dependentCaseId) throws ServiceException {
        log.info("TestCaseExecutionHelperRD - findResultDocumentsForDependentTestCase: BEGIN" );
        log.info("dependentScenarioId: "+ dependentScenarioId +" \n dependentCaseId: " + dependentCaseId);
        Map<String, String> docsAndRepIds = null;
        if (participant != null && dependentScenarioId != null && dependentCaseId != null) {
            // Find most recent Case Result Documents for Dependent QD Case Execution based on dependent scenario and case ids
        	//TODO: Make sure this method returns the results of a successful QD execution(i.e., with status 'PASS' in CaseResult table).
            List<Resultdocument> resultDocuments = CaseResultManager.getInstance().getDocumentsForTestCase(participant.getParticipantId(), dependentScenarioId, dependentCaseId);
            if (resultDocuments != null && resultDocuments.size() > 0) {
            	docsAndRepIds = new HashMap<String, String>();
            	for (Resultdocument resultDocument : resultDocuments) {
                	if(!LabConstants.PRIVACYPOLICY_CLASSCODE_LIST.contains(resultDocument.getClassCode())){
	                    docsAndRepIds.put(resultDocument.getDocumentUniqueId(), resultDocument.getRepositoryId());
	                    log.info("findResultDocumentsForDependentTestCase: DocId :" + resultDocument.getDocumentUniqueId());
	                    log.info("findResultDocumentsForDependentTestCase: Repository Id : "+  resultDocument.getRepositoryId());
                	}
                }
            }else{
                log.info("TestCaseExecutionHelperRD - findResultDocumentsForDependentTestCase: ResultDocuments not found!!");
            }
        }
        log.info("TestCaseExecutionHelperRD - findResultDocumentsForDependentTestCase: END");
        return docsAndRepIds;
    }
}
