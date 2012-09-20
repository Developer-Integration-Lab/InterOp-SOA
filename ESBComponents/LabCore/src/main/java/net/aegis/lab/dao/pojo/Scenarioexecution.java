package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.util.Format;

/**
 * <p>Pojo mapping table scenarioexecution</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Scenarioexecution implements Serializable {

    public static final String STATUS_ACTIVE  = "ACTIVE";

    /**
     * Attribute scenarioExecutionId.
     */
    private Integer scenarioExecutionId;
    /**
     * Attribute participant
     */
    private Participant participant;
    /**
     * Attribute scenario
     */
    private Scenario scenario;
    /**
     * Attribute executionUniqueId.
     */
    private String executionUniqueId;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute beginTime.
     */
    private Timestamp beginTime;
    /**
     * Attribute endTime.
     */
    private Timestamp endTime;
    /**
     * Attribute submitComments.
     */
    private String submitComments;
    /**
     * Attribute validationComments.
     */
    private String validationComments;
    /**
     * List of Caseexecution
     */
    private List<Caseexecution> caseexecutions = null;

    /**
     * @return scenarioExecutionId
     */
    public Integer getScenarioExecutionId() {
        return scenarioExecutionId;
    }

    // Display properties [not populated directly from the db]
    private int passed;
    private int progress;
    private int manual;
    private int failed;
    private int pending;
    private int cleared;
    private int completed;
    private int incomplete;
    private int error;
    private boolean scenarioHidden  ; 


	/**
     * @param scenarioExecutionId new value for scenarioExecutionId
     */
    public void setScenarioExecutionId(Integer scenarioExecutionId) {
        this.scenarioExecutionId = scenarioExecutionId;
    }

    /**
     * get participant
     */
    public Participant getParticipant() {
        return this.participant;
    }

    /**
     * set participant
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    /**
     * get scenario
     */
    public Scenario getScenario() {
        return this.scenario;
    }

    /**
     * set scenario
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * @return executionUniqueId
     */
    public String getExecutionUniqueId() {
        return executionUniqueId;
    }

    /**
     * @param executionUniqueId new value for executionUniqueId
     */
    public void setExecutionUniqueId(String executionUniqueId) {
        this.executionUniqueId = executionUniqueId;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status new value for status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return beginTime
     */
    public Timestamp getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime new value for beginTime
     */
    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return endTime
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * @param endTime new value for endTime
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    /**
     * @return submitComments
     */
    public String getSubmitComments() {
        return submitComments;
    }

    /**
     * @param submitComments new value for submitComments
     */
    public void setSubmitComments(String submitComments) {
        this.submitComments = submitComments;
    }

    /**
     * @return validationComments
     */
    public String getValidationComments() {
        return validationComments;
    }

    /**
     * @param validationComments new value for validationComments
     */
    public void setValidationComments(String validationComments) {
        this.validationComments = validationComments;
    }

    /**
     * Get the list of Caseexecution
     */
    public List<Caseexecution> getCaseexecutions() {
        return this.caseexecutions;
    }

    /**
     * Set the list of Caseexecution
     */
    public void setCaseexecutions(List<Caseexecution> caseexecutions) {
        this.caseexecutions = caseexecutions;
    }

    /**
     * Get the current Caseresult from the list of Caseresults
     */
    public Caseresult getCurrentCaseresult() {
        return this.getCurrentCaseresult(null);
    }
    public Caseresult getCurrentCaseresult(String messageType) {
        Caseresult current = null;
        if (this.caseexecutions != null && this.caseexecutions.size() > 0) {
            //current = this.caseexecutions.get(0).getCurrentCaseresult();
            for (Caseexecution caseexecution : this.caseexecutions) {
                if (messageType != null && !messageType.equals(caseexecution.getMessageType())) {
                    continue;
                }
                if (current == null && caseexecution.getCurrentCaseresult() != null) {
                    current = caseexecution.getCurrentCaseresult();
                }
                else if (current != null && caseexecution.getCurrentCaseresult() == null) {
                    // no-op
                }
                else if (current == null && caseexecution.getCurrentCaseresult() == null) {
                    // no-op
                }
                else {
                    // current != null && caseexecution.getCurrentCaseresult() != null
                    if (caseexecution.getCurrentCaseresult().getResultId().intValue() > current.getResultId().intValue()) {
                        current = caseexecution.getCurrentCaseresult();
                    }
                }
            }
        }
        return current;
    }

    // Display properties [not populated directly from the db]
    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getManual() {
        return manual;
    }

    public void setManual(int manual) {
        this.manual = manual;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCleared() {
        return cleared;
    }

    public void setCleared(int cleared) {
        this.cleared = cleared;
    }
    
    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(int incomplete) {
        this.incomplete = incomplete;
    }
    
    /**
	 * @return the error
	 */
	public int getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(int error) {
		this.error = error;
	}
	
	public boolean isScenarioHidden() {
		return scenarioHidden;
	}

	public void setScenarioHidden(boolean scenarioHidden) {
		this.scenarioHidden = scenarioHidden;
	}
	

    // Display helper methods

    public String getResultsMeterHtml() {
    	 // Call getResultsString() to force population of case results
        this.getResultsString();
        
    	int totalCount = passed + progress + manual + failed + error + pending + cleared + completed + incomplete;
        int passedPercent = 0;
        int progressPercent = 0;
        int manualPercent = 0;
        int failedPercent = 0;
        int errorPercent = 0;
        int clearedPercent = 0;
        int completedPercent = 0;
        int incompletePercent = 0;
        int pendingPercent = 100;

        if (totalCount > 0) {
            passedPercent = ((passed * 100) / totalCount);
            progressPercent = ((progress * 100) / totalCount);
            manualPercent = ((manual * 100) / totalCount);
            failedPercent = ((failed * 100) / totalCount);
            errorPercent = ((error * 100) / totalCount);
            pendingPercent = ((pending * 100) / totalCount);
            clearedPercent = ((cleared * 100) / totalCount);
            completedPercent = ((completed * 100) / totalCount);
            incompletePercent = ((incomplete * 100) / totalCount);
        }

        StringBuffer resultsMeterHtml = new StringBuffer("<table class=\"test-meter\"><tr>");
        if (passedPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + passed + " Passed\" width=\"" + passedPercent + "%\" class=\"test-pass\">&nbsp;</td>");
        }
        if (progressPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + progress + " Progress\" width=\"" + progressPercent + "%\" class=\"test-in-progress\">&nbsp;</td>");
        }
        if (failedPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + failed + " Failed\" width=\"" + failedPercent + "%\" class=\"test-fail\">&nbsp;</td>");
        }
        if (errorPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + error + " Error\" width=\"" + errorPercent + "%\" class=\"test-fail\">&nbsp;</td>");
        }
        if (clearedPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + cleared + " Cleared\" width=\"" + clearedPercent + "%\" class=\"test-cleared\">&nbsp;</td>");
        }
        if (completedPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + completed + " Completed\" width=\"" + completedPercent + "%\" class=\"test-completed\">&nbsp;</td>");
        }
        if (incompletePercent > 0) {
            resultsMeterHtml.append("<td title=\"" + incomplete + " Incomplete\" width=\"" + incompletePercent + "%\" class=\"test-incomplete\">&nbsp;</td>");
        }
        if (pendingPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + pending + " Pending\" width=\"" + pendingPercent + "%\" class=\"test-pending\">&nbsp;</td>");
        }
        if (manualPercent > 0) {
            resultsMeterHtml.append("<td title=\"" + manual + " Manual\" width=\"" + manualPercent + "%\" class=\"test-manual\">&nbsp;</td>");
        }
       
        resultsMeterHtml.append("</tr></table>");

        return resultsMeterHtml.toString();
    }

    public String getResultsString() {
    	 StringBuffer resultsString = new StringBuffer("");
         int passCount = 0;
         int progressCount = 0;
         int manualCount = 0;
         int failCount = 0;
         int pendCount = 0;
         int clearedCount = 0;
         int completedCount = 0;
         int incompleteCount = 0;
         int errorCount=0;

         if (caseexecutions != null && caseexecutions.size() > 0) {
             for (Caseexecution caseexecution : caseexecutions) {
                 passCount += caseexecution.getPassCount();
                 progressCount += caseexecution.getProgressCount();
                 manualCount += caseexecution.getManualCount();
                 failCount += caseexecution.getFailCount();
                 pendCount += caseexecution.getPendCount();
                 clearedCount += caseexecution.getClearedCount();
                 completedCount += caseexecution.getCompletedCount();
                 incompleteCount += caseexecution.getInCompleteCount();
                 errorCount+=caseexecution.getErrorCount();
             }

             if (pendCount == caseexecutions.size()) {
                 resultsString.append("Pending");
             }
             else {
                 if (completedCount > 0) resultsString.append(completedCount).append(" Completed; ");
                 if (incompleteCount > 0) resultsString.append(incompleteCount).append(" Incomplete; ");
                 if (passCount > 0) resultsString.append(passCount).append(" Passed; ");
                 if (progressCount > 0) resultsString.append(progressCount).append(" Progress; ");
                 if (manualCount > 0) resultsString.append(manualCount).append(" Manual; ");
                 if (failCount > 0) resultsString.append(failCount).append(" Failed; ");
                 if (errorCount > 0) resultsString.append(errorCount).append(" Error; ");
                 if (clearedCount > 0) resultsString.append(clearedCount).append(" Cleared; ");
                 if (pendCount > 0) resultsString.append(pendCount).append(" Pending ");
                 
             }
         }
         else {
             resultsString.append("No Test Cases Defined");
         }

         this.setPassed(passCount);
         this.setProgress(progressCount);
         this.setManual(manualCount);
         this.setFailed(failCount);
         this.setPending(pendCount);
         this.setCleared(clearedCount);
         this.setCompleted(completedCount);
         this.setIncomplete(incompleteCount);
         this.setError(errorCount);

         return resultsString.toString();
    }

    public String getScheduleString() {
        String scheduleString = "";

        if (beginTime != null) {
            scheduleString = Format.getFormattedDate(Format.DATETIME_DISPLAYFORMAT, beginTime);
        }
        else {
            scheduleString = "Pending";
        }

        return scheduleString;
    }

    public String getLastExecutionString() {
        String lastExecutionString = "";

        if (this.getCurrentCaseresult() != null && this.getCurrentCaseresult().getExecuteTime() != null) {
            lastExecutionString = Format.getFormattedDate(Format.DATETIME_DISPLAYFORMAT, this.getCurrentCaseresult().getExecuteTime());
        }

        return lastExecutionString;
    }

    public String getCompletedString() {
        String completedString = "";

        if (endTime != null) {
            completedString = Format.getFormattedDate(Format.DATETIME_WITH_SECONDS_DISPLAYFORMAT, endTime);
        }
        else {
            completedString = "In Progress";
        }

        return completedString;
    }

    public Boolean isExecuteOk() {
        Boolean executeOk = new Boolean(false);

        if (caseexecutions != null && caseexecutions.size() > 0) {
            String messageType = "";
            for (Caseexecution caseexecution : caseexecutions) {
                messageType = "";
                if (caseexecution.getTestcase() != null) {
                    messageType = caseexecution.getTestcase().getMessageType();
                }
                if (messageType.equals("PD") || messageType.equals("QD") || messageType.equals("RD")) {
                    executeOk = new Boolean(true);
                    break;
                }
            }
        }

        return executeOk;
    }

    public Boolean isProgressPending() {
        Boolean progressPending = new Boolean(false);
        Caseresult currentCaseresult = null;

        // Check current PD case result
        currentCaseresult = this.getCurrentCaseresult("PD");
        if (currentCaseresult != null && currentCaseresult.getResult() != null && currentCaseresult.getResult().startsWith(Caseresult.RESULT_PROGRESS)) {
            progressPending = new Boolean(true);
        }

        if (!progressPending) {
            // Check current QD case result
            currentCaseresult = this.getCurrentCaseresult("QD");
            if (currentCaseresult != null && currentCaseresult.getResult() != null && currentCaseresult.getResult().startsWith(Caseresult.RESULT_PROGRESS)) {
                progressPending = new Boolean(true);
            }
        }

        if (!progressPending) {
            // Check current RD case result
            currentCaseresult = this.getCurrentCaseresult("RD");
            if (currentCaseresult != null && currentCaseresult.getResult() != null && currentCaseresult.getResult().startsWith(Caseresult.RESULT_PROGRESS)) {
                progressPending = new Boolean(true);
            }
        }

        return progressPending;
    }
}
