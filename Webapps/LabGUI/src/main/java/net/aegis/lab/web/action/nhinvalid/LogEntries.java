package net.aegis.lab.web.action.nhinvalid;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Caseresultparameters;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Scenariocaseparameters;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.CaseResultParamsManager;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ScenariocaseParamsManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.lab.web.action.common.BaseLogEntries;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;

import org.apache.commons.lang.StringUtils;

public class LogEntries extends BaseLogEntries {

    private Nhinrep nhinValidator;
    private int selectedCaseExecutionId;
    private String scenarioExecutionId = "";
    private Scenarioexecution submittedScenario;
    private int selectedScenarioExecutionId;
    private String selectedjspCaseName;
    private Caseexecution testCase;
    //private Caseresult currentCaseResult;
    private List<Caseresultparameters> caseResultParamList = new ArrayList<Caseresultparameters>();
    private List<Scenariocaseparameters> scenariocaseparamList = new ArrayList<Scenariocaseparameters>();
    private String resultMessage;
    private List<Auditsummary> auditSummaryList;
    private List<Auditrepository> ri1DetailedAuditLogList = new ArrayList<Auditrepository>();
    private List<Auditrepository> ri2DetailedAuditLogList = new ArrayList<Auditrepository>();
    private List<Auditrepository> ri3DetailedAuditLogList = new ArrayList<Auditrepository>();

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("nhinvalid.LogEntries.execute() - INFO");
        log.info("nhinvalid.LogEntries.execute() - scenarioExecutionId=^" + scenarioExecutionId + "^");
        log.info("nhinvalid.LogEntries.execute() - selectedjspCaseName=" + selectedjspCaseName);

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.info("nhinvalid.LogEntries.execute() - NHIN Validator is " + nhinValidator.getName());

                // Populate NHIN Rep(Validator) Participants
                nhinValidator.setParticipants(ParticipantManager.getInstance().findByNhinRepId(nhinValidator.getNhinRepId()));

                selectedjspCaseName = this.getRequest().getParameter("selectedCaseName");
                submittedScenario = ValidationManager.getInstance().readScenarioExecution(scenarioExecutionId);

                if (null != submittedScenario) {
                    log.info("nhinvalid.LogEntries.execute()....selectedjspCaseName" + selectedjspCaseName);
                    log.info("nhinvalid.LogEntries.execute()....selectedCaseExecutionId:" + selectedCaseExecutionId);
                    log.info("participant.LogEntries.execute()....testScenario.getScenarioExecutionId():" + submittedScenario.getScenarioExecutionId() + selectedScenarioExecutionId);

                    testCase = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(selectedCaseExecutionId);
                 // TODO : need to clean up 
                    auditSummaryList = ServiceSetExecutionManager.getInstance().getAuditSummariesByScenarioExecIdAndCaseName(submittedScenario, selectedjspCaseName);
                    getLogMessages(testCase);
                    
                    log.info("nhinvalid.LogEntries.execute().... testCaseId = " + testCase.getTestcase().getCaseId());
                    log.info("participant.LogEntries.execute().... ScenarioId = " + submittedScenario.getScenario().getScenarioId());
                     // allways show expected results
                    scenariocaseparamList = ScenariocaseParamsManager.getInstance().findByScenarioIdAndCaseId(submittedScenario.getScenario().getScenarioId(),testCase.getTestcase().getCaseId());
                    if (testCase != null && currentCaseResult != null && submittedScenario.getScenario().isInitiator()) {
                         caseResultParamList = CaseResultParamsManager.getInstance().findByResultId(currentCaseResult.getResultId());
                    }
                    log.info("nhinvalid.LogEntries.execute().... scenariocaseparamList = " + scenariocaseparamList);
                    log.info("nhinvalid.LogEntries.execute().... caseResultParamList = " + caseResultParamList);


                } else {
                    log.debug("nhinvalid.LogEntries.execute() - submittedScenario is null");
                }

                // /////////////////////////////////////////////////////////////////
                // Support for story id 13: Case Log Summary page to fetch actual
                // RI Audit Log records too.
                // Data Source: auditrepo.auditrepository table on the RI database server
                // /////////////////////////////////////////////////////////////////
                
                // support for navigation
                this.getSession().setAttribute("sessionobj.selectedCaseName", selectedjspCaseName);
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    

    public Nhinrep getNhinrep() {
        return nhinValidator;
    }

    public void setNhinrep(Nhinrep nhinValidator) {
        this.nhinValidator = nhinValidator;
    }

    public String getSelectedjspCaseName() {
        return selectedjspCaseName;
    }

    public void setSelectedjspCaseName(String selectedjspCaseName) {
        this.selectedjspCaseName = selectedjspCaseName;
    }

    public List<Auditsummary> getAuditSummaryList() {
        return auditSummaryList;
    }

    public void setAuditSummaryList(List<Auditsummary> auditSummaryList) {
        this.auditSummaryList = auditSummaryList;
    }

    /**
     * @return the scenarioExecutionId
     */
    public String getScenarioExecutionId() {
        return scenarioExecutionId;
    }

    /**
     * @param scenarioExecutionId the scenarioExecutionId to set
     */
    public void setScenarioExecutionId(String scenarioExecutionId) {
        this.scenarioExecutionId = scenarioExecutionId;
    }

    /**
     * @return the submittedScenario
     */
    public Scenarioexecution getSubmittedScenario() {
        return submittedScenario;
    }

    /**
     * @param submittedScenario the submittedScenario to set
     */
    public void setSubmittedScenario(Scenarioexecution submittedScenario) {
        this.submittedScenario = submittedScenario;
    }

    /*public Caseresult getCurrentCaseResult() {
        if (testCase != null) {
            currentCaseResult = currentCaseResult;
        }
        return currentCaseResult;
    }

    public void setCurrentCaseResult(Caseresult currentCaseResult) {
        this.currentCaseResult = currentCaseResult;
    }*/

    public String getResultMessage() {
        if (getTestCase() != null) {
            String fromName = "Participant";
            String toName = "Lab";
            if (getTestCase().getTestcase() != null &&
                    getTestCase().getTestcase().getResponderInd() != null &&
                    getTestCase().getTestcase().getResponderInd().equals("Y")) {
                fromName = "Lab";
                toName = "Participant";
            }
            if (getTestCase().getCurrentCaseresult() != null &&
                    getTestCase().getCurrentCaseresult().getResult() != null) {

                if (getTestCase().getMessageType().equals("PD")) {
                    if (getTestCase().getCurrentCaseresult().getResult().equals(Caseresult.RESULT_PASS)) {
                        resultMessage = "Result of Patient Correlation between " + fromName + " and " + toName + " was successful.";
                    } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_COMPLETED)) {
                        resultMessage = "Result of Patient Correlation between " + fromName + " and " + toName + " has been completed.";
                    } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_INCOMPLETE)){
                        resultMessage = "Result of Patient Correlation between " + fromName + " and " + toName + " was incomplete.";
                    } else {
                        resultMessage = "";
                    }
                } else if (getTestCase().getMessageType().equals("QD")) {
                    // Initiator Scenarios do capture resultdocument records, so display generic message
                    if (getTestCase().getTestcase().getInitiatorInd().equals("Y")) {
                        if (getTestCase().getCurrentCaseresult().getResult().equals(Caseresult.RESULT_PASS)) {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " passed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_COMPLETED)) {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " has been completed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_INCOMPLETE)) {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " was incomplete.";
                        } else {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " failed.";
                        }
                    } else {
                        if (getTestCase().getCurrentCaseresult().getResultdocuments() != null &&
                                getTestCase().getCurrentCaseresult().getResultdocuments().size() > 0) {
                            resultMessage = "Query for Documents from " + fromName + " to " + toName + " found " + getTestCase().getCurrentCaseresult().getResultdocuments().size() + " documents.";
                        } else {
                            resultMessage = "Query for Documents from " + fromName + " to " + toName + " found 0 documents.";
                        }
                    }
                } else if (getTestCase().getMessageType().equals("RD")) {
                    // Initiator Scenarios do capture resultdocument records, so display generic message
                    if (getTestCase().getTestcase().getInitiatorInd().equals("Y")) {
                        if (getTestCase().getCurrentCaseresult().getResult().equals(Caseresult.RESULT_PASS)) {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " passed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_COMPLETED)) {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " has been completed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_INCOMPLETE)) {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " was incomplete.";
                        } else {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " failed.";
                        }
                    } else {
                        if (getTestCase().getCurrentCaseresult().getResultdocuments() != null &&
                                getTestCase().getCurrentCaseresult().getResultdocuments().size() > 0) {
                            resultMessage = "Retrieve Documents from " + fromName + " to " + toName + " returned " + getTestCase().getCurrentCaseresult().getResultdocuments().size() + " documents.";
                        } else {
                            resultMessage = "Retrieve Documents from " + fromName + " to " + toName + " returned 0 documents.";
                        }
                    }
                } else {
                    resultMessage = getTestCase().getCurrentCaseresult().getMessage();
                    String testcaseStatus  = getTestCase().getCurrentCaseresult().getResult();
                    if(StringUtils.isNotEmpty(testcaseStatus) && testcaseStatus.equalsIgnoreCase(Caseresult.RESULT_ERROR)){
                    	
                    	resultMessage = "Error occured";
                    }
                    else if (resultMessage == null || resultMessage.isEmpty()) {
                        resultMessage = "No Case Result Details found.";
                    }
                   
                }
            } else {
                resultMessage = "No Case Result Details found.";
            }
        }
        return resultMessage;
    }

    /**
     * @return the selectedCaseExecutionId
     */
    public int getSelectedCaseExecutionId() {
        return selectedCaseExecutionId;
    }

    /**
     * @param selectedCaseExecutionId the selectedCaseExecutionId to set
     */
    public void setSelectedCaseExecutionId(int selectedCaseExecutionId) {
        this.selectedCaseExecutionId = selectedCaseExecutionId;
    }

    /**
     * @return the testCase
     */
    public Caseexecution getTestCase() {
        return testCase;
    }

    /**
     * @param testCase the testCase to set
     */
    public void setTestCase(Caseexecution testCase) {
        this.testCase = testCase;
    }

    public List<Auditrepository> getRi1DetailedAuditLogList() {
        return ri1DetailedAuditLogList;
    }

    public void setRi1DetailedAuditLogList(List<Auditrepository> ri1DetailedAuditLogList) {
        this.ri1DetailedAuditLogList = ri1DetailedAuditLogList;
    }

    public List<Auditrepository> getRi2DetailedAuditLogList() {
        return ri2DetailedAuditLogList;
    }

    public void setRi2DetailedAuditLogList(List<Auditrepository> ri2DetailedAuditLogList) {
        this.ri2DetailedAuditLogList = ri2DetailedAuditLogList;
    }

    public List<Auditrepository> getRi3DetailedAuditLogList() {
        return ri3DetailedAuditLogList;
    }

    public void setRi3DetailedAuditLogList(List<Auditrepository> ri3DetailedAuditLogList) {
        this.ri3DetailedAuditLogList = ri3DetailedAuditLogList;
    }

    public List<Scenariocaseparameters> getScenariocaseparamList() {
        return scenariocaseparamList;
    }

    public void setScenariocaseparamList(List<Scenariocaseparameters> scenariocaseparamList) {
        this.scenariocaseparamList = scenariocaseparamList;
    }

    public List<Caseresultparameters> getCaseResultParamList() {
        return caseResultParamList;
    }

    public void setCaseResultParamList(List<Caseresultparameters> caseResultParamList) {
        this.caseResultParamList = caseResultParamList;
    }
}

