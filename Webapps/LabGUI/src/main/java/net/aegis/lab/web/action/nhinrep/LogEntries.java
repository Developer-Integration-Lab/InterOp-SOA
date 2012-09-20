package net.aegis.lab.web.action.nhinrep;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Caseresultparameters;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenariocaseparameters;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.manager.CaseResultParamsManager;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.manager.ScenariocaseParamsManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.lab.web.action.common.BaseLogEntries;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;

import org.apache.commons.lang.StringUtils;

public class LogEntries extends BaseLogEntries {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private Participant objParticipantThatNhinRepWorksWith = null;
    private int selectedCaseExecutionId;
    private String selectedjspCaseName;
    private int selectedScenarioExecutionId;
    private List<Scenarioexecution> testScenarios;
    private Scenarioexecution testScenario;
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
    public String execute() throws Exception {

        if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
            setNhinrep(this.getProfile().getNhinreps().get(0));
            log.warn("nhinrep.LogEntries.execute() - NHIN Rep is " + nhinrep.getName());

            // JSP shows some information about the participant.That's why set it here.
            objParticipantThatNhinRepWorksWith = ((User) this.getRequest().getSession().getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
            this.setParticipant(objParticipantThatNhinRepWorksWith);    // JSP shows some information about the participant.  That's why set it here.
            if (null != objParticipantThatNhinRepWorksWith) {
                log.warn("nhinrep.LogEntries.execute() - participant that NHIN Rep works with is " + objParticipantThatNhinRepWorksWith.getParticipantName());
            }

            if (null == objParticipantThatNhinRepWorksWith) {
                this.addActionError(getText("errors.nhinrep.no.working.participant"));
                return INPUT;       // back to nhinrep dashboard for them to choose a participant
            }
        }
        //End-JSP shows some....

        testScenario = (Scenarioexecution) this.getSession().getAttribute("testScenario");

        log.debug("nhinrep.LogEntries.execute() - testScenario is -->>" + testScenario);

        selectedjspCaseName = this.getRequest().getParameter("selectedCaseName");
        if (selectedjspCaseName == null) {
            selectedjspCaseName = (String) this.getSession().getAttribute("selectedCaseName");
        }

        log.debug("nhinrep.LogEntries.execute() - selectedjspCaseName is -->>" + selectedjspCaseName);

        this.getSession().setAttribute("selectedCaseName", selectedjspCaseName);

        if (testScenario != null) {
            log.info("nhinrep.LogEntries.execute()....selectedjspCaseName" + selectedjspCaseName);
            log.info("nhinrep.LogEntries.execute()....selectedCaseExecutionId:" + selectedCaseExecutionId);
            log.info("nhinrep.LogEntries.execute()....testScenario.getScenarioExecutionId():" + testScenario.getScenarioExecutionId() + selectedScenarioExecutionId);
            testCase = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(selectedCaseExecutionId);
            
            // TODO : need to clean up 
            auditSummaryList = ServiceSetExecutionManager.getInstance().getAuditSummariesByScenarioExecIdAndCaseName(testScenario, selectedjspCaseName);
            getLogMessages(testCase);
            
            log.info("nhinrep.LogEntries.execute().... testCaseId = " + testCase.getTestcase().getCaseId());
            log.info("participant.LogEntries.execute().... ScenarioId = " + testScenario.getScenario().getScenarioId());
             // allways show expected results
            scenariocaseparamList = ScenariocaseParamsManager.getInstance().findByScenarioIdAndCaseId(testScenario.getScenario().getScenarioId(),testCase.getTestcase().getCaseId());
            if (testCase != null && currentCaseResult != null && testScenario.getScenario().isInitiator()) {
                caseResultParamList = CaseResultParamsManager.getInstance().findByResultId(currentCaseResult.getResultId());
            }
            log.info("nhinrep.LogEntries.execute().... scenariocaseparamList = " + scenariocaseparamList);
            log.info("nhinrep.LogEntries.execute().... caseResultParamList = " + caseResultParamList);

        } else {
            log.debug("nhinrep.LogEntries.execute() - testScenario is null");
        }

        // /////////////////////////////////////////////////////////////////
        // Support for story id 13: Case Log Summary page to fetch actual
        // RI Audit Log records too.
        // Data Source: auditrepo.auditrepository table on the RI database server
        // /////////////////////////////////////////////////////////////////

        return SUCCESS;
    }


    public Nhinrep getNhinrep() {
        return nhinrep;
    }

    public void setNhinrep(Nhinrep nhinrep) {
        this.nhinrep = nhinrep;
    }

    public Participant getParticipant() {
        return this.objParticipantThatNhinRepWorksWith;
    }

    public void setParticipant(Participant pobjParticipantThatNhinRepWorksWith) {
        this.objParticipantThatNhinRepWorksWith = pobjParticipantThatNhinRepWorksWith;
    }

    public int getSelectedCaseExecutionId() {
        return selectedCaseExecutionId;
    }

    public void setSelectedCaseExecutionId(int selectedCaseExecutionId) {
        this.selectedCaseExecutionId = selectedCaseExecutionId;
    }

    public String getSelectedjspCaseName() {
        return selectedjspCaseName;
    }

    public void setSelectedjspCaseName(String selectedjspCaseName) {
        this.selectedjspCaseName = selectedjspCaseName;
    }

    public int getSelectedScenarioExecutionId() {
        return selectedScenarioExecutionId;
    }

    public void setSelectedScenarioExecutionId(int selectedScenarioExecutionId) {
        this.selectedScenarioExecutionId = selectedScenarioExecutionId;
    }

    public Scenarioexecution getTestScenario() {
        return testScenario;
    }

    public void setTestScenario(Scenarioexecution testScenario) {
        this.testScenario = testScenario;
    }

    public List<Scenarioexecution> getTestScenarios() {
        return testScenarios;
    }

    public void setTestScenarios(List<Scenarioexecution> testScenarios) {
        this.testScenarios = testScenarios;
    }

    public Caseexecution getTestCase() {
        return testCase;
    }

    public void setTestCase(Caseexecution testCase) {
        this.testCase = testCase;
    }

   /* public Caseresult getCurrentCaseResult() {
        if (testCase != null) {
            currentCaseResult = currentCaseResult;
        }
        return currentCaseResult;
    }

    public void setCurrentCaseResult(Caseresult currentCaseResult) {
        this.currentCaseResult = currentCaseResult;
    }*/

    public String getResultMessage() {
        if (testCase != null) {
            String fromName = "Participant";
            String toName = "Lab";
            if (testCase.getTestcase() != null &&
                    testCase.getTestcase().getResponderInd() != null &&
                    testCase.getTestcase().getResponderInd().equals("Y")) {
                fromName = "Lab";
                toName = "Participant";
            }
            if (currentCaseResult != null &&
                    currentCaseResult.getResult() != null) {

                if (testCase.getMessageType().equals("PD")) {
                    if (currentCaseResult.getResult().equals(Caseresult.RESULT_PASS)) {
                        resultMessage = "Result of Patient Correlation between " + fromName + " and " + toName + " was successful.";
                    } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_COMPLETED)) {
                        resultMessage = "Result of Patient Correlation between " + fromName + " and " + toName + " has been completed.";
                    } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_INCOMPLETE)){
                        resultMessage = "Result of Patient Correlation between " + fromName + " and " + toName + " was incomplete.";
                    } else {
                        resultMessage = "";
                    }
                } else if (testCase.getMessageType().equals("QD")) {
                    // Initiator Scenarios do capture resultdocument records, so display generic message
                    if (testCase.getTestcase().getInitiatorInd().equals("Y")) {
                        if (currentCaseResult.getResult().equals(Caseresult.RESULT_PASS)) {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " passed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_COMPLETED)) {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " has been completed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_INCOMPLETE)) {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " was incomplete.";
                        } else {
                            resultMessage = "Result of Query for Documents from " + fromName + " to " + toName + " failed.";
                        }
                    } else {
                        if (currentCaseResult.getResultdocuments() != null &&
                                currentCaseResult.getResultdocuments().size() > 0) {
                            resultMessage = "Query for Documents from " + fromName + " to " + toName + " found " + currentCaseResult.getResultdocuments().size() + " documents.";
                        } else {
                            resultMessage = "Query for Documents from " + fromName + " to " + toName + " found 0 documents.";
                        }
                    }
                } else if (testCase.getMessageType().equals("RD")) {
                    // Initiator Scenarios do capture resultdocument records, so display generic message
                    if (testCase.getTestcase().getInitiatorInd().equals("Y")) {
                        if (currentCaseResult.getResult().equals(Caseresult.RESULT_PASS)) {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " passed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_COMPLETED)) {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " has been completed.";
                        } else if (currentCaseResult.getResult().equals(Caseresult.RESULT_INCOMPLETE)) {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " was incomplete.";
                        } else {
                            resultMessage = "Result of Retrieve Documents from " + fromName + " to " + toName + " failed.";
                        }
                    } else {
                        if (currentCaseResult.getResultdocuments() != null &&
                                currentCaseResult.getResultdocuments().size() > 0) {
                            resultMessage = "Retrieve Documents from " + fromName + " to " + toName + " returned " + currentCaseResult.getResultdocuments().size() + " documents.";
                        } else {
                            resultMessage = "Retrieve Documents from " + fromName + " to " + toName + " returned 0 documents.";
                        }
                    }
                }else {
                    resultMessage = currentCaseResult.getMessage();
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

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public List<Auditsummary> getAuditSummaryList() {
        return auditSummaryList;
    }

    public void setAuditSummaryList(List<Auditsummary> auditSummaryList) {
        this.auditSummaryList = auditSummaryList;
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
