package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.util.Format;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Pojo mapping table servicesetexecution</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Servicesetexecution implements Serializable {

    public static final String STATUS_ACTIVE  = "ACTIVE";
    public static final String STATUS_NEW  = "NEW";

    private static final Log log = LogFactory.getLog(Servicesetexecution.class);
    /**
     * Attribute setExecutionId.
     */
    private Integer setExecutionId;
    /**
     * Attribute participant
     */
    private Participant participant;
    /**
     * Attribute serviceset
     */
    private Serviceset serviceset;
    /**
     * Attribute executionUniqueId.
     */
    private String executionUniqueId;
    /**
     * Attribute initiatorInd.
     */
    private String initiatorInd;
    /**
     * Attribute responderInd.
     */
    private String responderInd;
    /**
     * Attribute ssnHandlingInd.
     */
    private String ssnHandlingInd;
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
     * Attribute version.
     */
    private String version;
   /**
     * Attribute submitComments.
     */
    private String submitComments;
    /**
     * Attribute validationComments.
     */
    private String validationComments;

    /**
     * List of Questionnaireresponse
     */
    private List<Questionnaireresponse> questionnaireresponses = null;

    // used for UI layer only
    private List<Questionnaireresponse> pdQuestionnaireresponses = null;
    private List<Questionnaireresponse> qdQuestionnaireresponses = null;
    private List<Questionnaireresponse> rdQuestionnaireresponses = null;
    private List<Questionnaireresponse> cdaQuestionnaireresponses = null;
    private int cdaQuestionnaireresponsesSize;
    private int pdQuestionnaireresponsesSize;
    private int qdQuestionnaireresponsesSize;
    private int rdQuestionnaireresponsesSize;

    // Display properties [not populated directly from the db]
    private int passed;
    private int progress;
    private int completed;
    private int incomplete;
    private int manual;
    private int failed;
    private int pending;
    private int cleared;
    private int error;
   
	private List<Caseexecution> caseexecutions = null;
    private Boolean serviceSetInd = null;

    /**
     * Attribute initiatorInd.
     */
    private Boolean initiatorIndBoolean;
    /**
     * Attribute responderInd.
     */
    private Boolean responderIndBoolean;

    /**
     * @return setExecutionId
     */
    public Integer getSetExecutionId() {
        return setExecutionId;
    }

    /**
     * @param setExecutionId new value for setExecutionId
     */
    public void setSetExecutionId(Integer setExecutionId) {
        this.setExecutionId = setExecutionId;

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
     * get serviceset
     */
    public Serviceset getServiceset() {
        return this.serviceset;
    }

    /**
     * set serviceset
     */
    public void setServiceset(Serviceset serviceset) {
        this.serviceset = serviceset;
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
     * @return initiatorInd
     */
    public String getInitiatorInd() {
        return initiatorInd;
    }

    /**
     * @param initiatorInd new value for initiatorInd
     */
    public void setInitiatorInd(String initiatorInd) {
        this.initiatorInd = initiatorInd;
    }

    /**
     * @return initiatorIndSet - CHECKBOX HANDLING
     */
    public String getInitiatorIndSet() {
        String initiatorIndSet = "false";
        if (initiatorInd != null && initiatorInd.equals("Y")) {
            initiatorIndSet = "true";
        }
        return initiatorIndSet;
    }

    /**
     * @return set initiatorIndSet - CHECKBOX HANDLING
     */
    public void setInitiatorIndSet() {
       
         //   this.initiatorIndSet = "true";       
        //return initiatorIndSet;
    }

   /* public String getCheckedServiceSetInd() {
    
        return checkedServiceSetInd;
         
    }
    
    public void setCheckedServiceSetInd(String checkedServiceSetInd) {
        this.checkedServiceSetInd = checkedServiceSetInd;
        log.info("checkedServiceSetInd**********************"+checkedServiceSetInd);
    }*/
    

    /**
     * @return responderInd
     */
    public String getResponderInd() {
        return responderInd;
    }

    /**
     * @param responderInd new value for responderInd
     */
    public void setResponderInd(String responderInd) {
        this.responderInd = responderInd;
    }

    /**
     * @return responderIndSet - CHECKBOX HANDLING
     */
    public String getResponderIndSet() {
        String responderIndSet = "false";
        if (responderInd != null && responderInd.equals("Y")) {
            responderIndSet = "true";
        }
        return responderIndSet;
    }

    /**
     * @return ssnHandlingInd
     */
    public String getSsnHandlingInd() {
        return ssnHandlingInd;
    }

    /**
     * @param ssnHandlingInd new value for ssnHandlingInd
     */
    public void setSsnHandlingInd(String ssnHandlingInd) {
        this.ssnHandlingInd = ssnHandlingInd;
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
    
    /**
	 * @return the cleared
	 */
	public int getCleared() {
		return cleared;
	}

	/**
	 * @param cleared the cleared to set
	 */
	public void setCleared(int cleared) {
		this.cleared = cleared;
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

    public List<Caseexecution> getCaseexecutions() {
        return this.caseexecutions;
    }

    public void setCaseexecutions(List<Caseexecution> caseexecutions) {
        this.caseexecutions = caseexecutions;
    }

    public List<Questionnaireresponse> getQuestionnaireresponses() {
        return questionnaireresponses;
    }

    public void setQuestionnaireresponses(List<Questionnaireresponse> questionnaireresponses) {
        this.questionnaireresponses = questionnaireresponses;
    }

    // display methods only start

    public List<Questionnaireresponse> getCdaQuestionnaireresponses() {
        return cdaQuestionnaireresponses;
    }

    public void setCdaQuestionnaireresponses(List<Questionnaireresponse> cdaQuestionnaireresponses) {
        this.cdaQuestionnaireresponses = cdaQuestionnaireresponses;
    }

    public List<Questionnaireresponse> getPdQuestionnaireresponses() {
        return pdQuestionnaireresponses;
    }
    public void setPdQuestionnaireresponses(List<Questionnaireresponse> pdQuestionnaireresponses) {
        this.pdQuestionnaireresponses = pdQuestionnaireresponses;
    }

    public List<Questionnaireresponse> getQdQuestionnaireresponses() {
        return qdQuestionnaireresponses;
    }

    public void setQdQuestionnaireresponses(List<Questionnaireresponse> qdQuestionnaireresponses) {
        this.qdQuestionnaireresponses = qdQuestionnaireresponses;
    }

    public List<Questionnaireresponse> getRdQuestionnaireresponses() {
        return rdQuestionnaireresponses;
    }

    public void setRdQuestionnaireresponses(List<Questionnaireresponse> rdQuestionnaireresponses) {
        this.rdQuestionnaireresponses = rdQuestionnaireresponses;
    }


 

    public Boolean getInitiatorIndBoolean() {
        if(StringUtils.isNotEmpty(this.getInitiatorInd()) && this.getInitiatorInd().equalsIgnoreCase("Y")){
            initiatorIndBoolean = true;
        }else if(StringUtils.isNotEmpty(this.getInitiatorInd()) && this.getInitiatorInd().equalsIgnoreCase("N")){
            initiatorIndBoolean = false;
        }
        return initiatorIndBoolean;
    }

    public void setInitiatorIndBoolean(Boolean initiatorIndBoolean) {
        if(initiatorIndBoolean){
            this.setInitiatorInd("Y");
        }else{
            this.setInitiatorInd("N");
        }
        this.initiatorIndBoolean = initiatorIndBoolean;
    }

    public Boolean getResponderIndBoolean() {
         if(StringUtils.isNotEmpty(this.getResponderInd()) && this.getResponderInd().equalsIgnoreCase("Y")){
            this.responderIndBoolean = true;
        }else if(StringUtils.isNotEmpty(this.getResponderInd()) && this.getResponderInd().equalsIgnoreCase("N")){
            this.responderIndBoolean = false;
        }
        return responderIndBoolean;
    }

    public void setResponderIndBoolean(Boolean responderIndBoolean) {
        if(responderIndBoolean){
            this.setResponderInd("Y");
        }else{
            this.setResponderInd("N");
        }

        this.responderIndBoolean = responderIndBoolean;
    }

    public Boolean getServiceSetInd() {
        return serviceSetInd;
    }

    public void setServiceSetInd(Boolean serviceSetInd) {
        this.serviceSetInd = serviceSetInd;
    }
    public int getCdaQuestionnaireresponsesSize() {
        return cdaQuestionnaireresponsesSize;
    }

    public void setCdaQuestionnaireresponsesSize(int cdaQuestionnaireresponsesSize) {
        this.cdaQuestionnaireresponsesSize = cdaQuestionnaireresponsesSize;
    }

    public int getPdQuestionnaireresponsesSize() {
        return pdQuestionnaireresponsesSize;
    }

    public void setPdQuestionnaireresponsesSize(int pdQuestionnaireresponsesSize) {
        this.pdQuestionnaireresponsesSize = pdQuestionnaireresponsesSize;
    }

    public int getQdQuestionnaireresponsesSize() {
        return qdQuestionnaireresponsesSize;
    }

    public void setQdQuestionnaireresponsesSize(int qdQuestionnaireresponsesSize) {
        this.qdQuestionnaireresponsesSize = qdQuestionnaireresponsesSize;
    }

    public int getRdQuestionnaireresponsesSize() {
        return rdQuestionnaireresponsesSize;
    }

    public void setRdQuestionnaireresponsesSize(int rdQuestionnaireresponsesSize) {
        this.rdQuestionnaireresponsesSize = rdQuestionnaireresponsesSize;
    }
    
    // display methods only End
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

    public String getInitiatedString() {
        String initiatedString = "";

        if (beginTime != null) {
            initiatedString = Format.getFormattedDate(Format.DATETIME_DISPLAYFORMAT, beginTime);
        }
        else {
            initiatedString = "Pending";
        }

        return initiatedString;
    }

    public String getCompletedString() {
        String completedString = "";

        if (endTime != null) {
            completedString = Format.getFormattedDate(Format.DATETIME_DISPLAYFORMAT, endTime);
        }
        else {
            completedString = "In Progress";
        }

        return completedString;
    }

}
