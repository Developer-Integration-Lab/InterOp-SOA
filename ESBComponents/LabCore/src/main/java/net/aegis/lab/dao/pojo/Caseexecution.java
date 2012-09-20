package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.util.LabConstants;

/**
 * <p>Pojo mapping table caseexecution</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Caseexecution implements Serializable {

    public static final String STATUS_ACTIVE = "ACTIVE";
    /**
     * Attribute caseExecutionId.
     */
    private Integer caseExecutionId;
    /**
     * Attribute scenarioexecution
     */
    private Scenarioexecution scenarioexecution;
    /**
     * Attribute testcase
     */
    private Testcase testcase;
    /**
     * Attribute userName.
     */
    private String userName;
    /**
     * Attribute patientId.
     */
    private String patientId;
    /**
     * Attribute participantpatientId.
     */
    private String participantpatientId;
    /**
     * Attribute documentIds.
     */
    private String documentIds;
    /**
     * Attribute participatingRIs.
     */
    private String participatingRIs;
    /**
     * Attribute successCriteria.
     */
    private String successCriteria;
     /**
     * Attribute pdSuccessCriteria.
     */
    private String pdSuccessCriteria;
     /**
     * Attribute pdSuccessCriteriaDesc.
     */
    private String pdSuccessCriteriaDesc;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute version.
     */
    private String version;    
    /**
     * Attribute beginTime.
     */
    private Timestamp beginTime;
    /**
     * Attribute messageType.
     */
    private String messageType;
    /**
     * Attribute dependentScenarioId.
     */
    private Integer dependentScenarioId;
    /**
     * Attribute dependentCaseId.
     */
    private Integer dependentCaseId;
    
    private String displayByDependentCaseStatus ; 

	/**
     * Attribute startTimer.
     */
    private Timestamp startTimer;
    /**
     * Attribute endTimer.
     */
    private Timestamp endTimer;

    private String resouceIdSendInd ;

    /**
     * List of Caseresult
     */
    private List<Caseresult> caseresults = null;
    /**
     * List of Caseschedule
     */
    private List<Caseschedule> caseschedules = null;
    
    // Display properties [not populated directly from the db]
    /**
     * List of Altscenariocase
     */
    private List<Altscenariocase> altscenariocases = null;

    private boolean dependentCaseExecuted  ;
    
    private boolean caseHiddenByDependentCaseStatus  ;    

	private Caseexecution depedentCaseexecution ;

    /**
     * List of rdrResultdocument to hold repository id and document unique id for each case execution
     */
    private List<Resultdocument> rdrResultdocuments = null;

    /**
     * @return caseExecutionId
     */
    public Integer getCaseExecutionId() {
        return caseExecutionId;
    }

    /**
     * @param caseExecutionId new value for caseExecutionId
     */
    public void setCaseExecutionId(Integer caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }

    /**
     * get scenarioexecution
     */
    public Scenarioexecution getScenarioexecution() {
        return this.scenarioexecution;
    }

    /**
     * set scenarioexecution
     */
    public void setScenarioexecution(Scenarioexecution scenarioexecution) {
        this.scenarioexecution = scenarioexecution;
    }

    /**
     * get testcase
     */
    public Testcase getTestcase() {
        return this.testcase;
    }

    /**
     * set testcase
     */
    public void setTestcase(Testcase testcase) {
        this.testcase = testcase;
    }

    /**
     * get userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId new value for patientId
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return participantpatientId
     */
    public String getParticipantpatientId() {
        return participantpatientId;
    }

    /**
     * @param participantpatientId new value for participantpatientId
     */
    public void setParticipantpatientId(String participantpatientId) {
        this.participantpatientId = participantpatientId;
    }

    /**
     * @return documentIds
     */
    public String getDocumentIds() {
        return documentIds;
    }

    /**
     * @param documentIds new value for documentIds
     */
    public void setDocumentIds(String documentIds) {
        this.documentIds = documentIds;
    }

    /**
     * @return participatingRIs
     */
    public String getParticipatingRIs() {
        return participatingRIs;
    }

    /**
     * @param participatingRIs new value for participatingRIs
     */
    public void setParticipatingRIs(String participatingRIs) {
        this.participatingRIs = participatingRIs;
    }

    /**
     * @return successCriteria
     */
    public String getSuccessCriteria() {
        return successCriteria;
    }

    /**
     * @param successCriteria new value for successCriteria
     */
    public void setSuccessCriteria(String successCriteria) {
        this.successCriteria = successCriteria;
    }

    /**
     * @return pdSuccessCriteria
     */
    public String getPdSuccessCriteria() {
        return pdSuccessCriteria;
    }

    /**
     * @param successCriteria new value for pdSuccessCriteria
     */
    public void setPdSuccessCriteria(String pdSuccessCriteria) {
        this.pdSuccessCriteria = pdSuccessCriteria;
    }

    /**
     * @return pdSuccessCriteriaDesc
     */
    public String getPdSuccessCriteriaDesc() {
        return pdSuccessCriteriaDesc;
    }

    /**
     * @param successCriteria new value for pdSuccessCriteriaDesc
     */
    public void setPdSuccessCriteriaDesc(String pdSuccessCriteriaDesc) {
        this.pdSuccessCriteriaDesc = pdSuccessCriteriaDesc;
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
     * @return messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType new value for messageType
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return dependentCaseId
     */
    public Integer getDependentCaseId() {
        return dependentCaseId;
    }

    /**
     * @param dependentCaseId new value for dependentCaseId
     */
    public void setDependentCaseId(Integer dependentCaseId) {
        this.dependentCaseId = dependentCaseId;
    }
    
    /**
     * 
     * @return
     */
    public String getDisplayByDependentCaseStatus() {
		return displayByDependentCaseStatus;
	}
    /**
     * 
     * @param displayByDependentCaseStatus
     */
    
	public void setDisplayByDependentCaseStatus(String displayByDependentCaseStatus) {
		this.displayByDependentCaseStatus = displayByDependentCaseStatus;
	}
	

    /**
     * @return dependentScenarioId
     */
    public Integer getDependentScenarioId() {
        return dependentScenarioId;
    }

     /**
     * @param dependentScenarioId new value for dependentScenarioId
     */
   public void setDependentScenarioId(Integer dependentScenarioId) {
        this.dependentScenarioId = dependentScenarioId;
    }

   /**
    *
    * @return
    */
   public Timestamp getEndTimer() {
        return endTimer;
    }
    /**
     *
     * @param endTimer
     */
    public void setEndTimer(Timestamp endTimer) {
        this.endTimer = endTimer;
    }

    /**
     *
     * @return
     */
    public Timestamp getStartTimer() {
        return startTimer;
    }

    /**
     *
     * @param startTimer
     */
    public void setStartTimer(Timestamp startTimer) {
        this.startTimer = startTimer;
    }

    public String getResouceIdSendInd() {
        return resouceIdSendInd;
    }

    public void setResouceIdSendInd(String resouceIdSendInd) {
        this.resouceIdSendInd = resouceIdSendInd;
    }
    
    /**
     * Get the current Caseresult from the list of Caseresults
     */
    public Caseresult getCurrentCaseresult() {
        Caseresult current = null;
        if (this.caseresults != null && this.caseresults.size() > 0) {
            current = this.caseresults.get(0);
            for (Caseresult caseresult : this.caseresults) {
                if (caseresult.getResultId().intValue() > current.getResultId().intValue()) {
                    current = caseresult;
                }
            }
        }
        return current;
    }

    /**
     * Get the list of Caseresult
     */
    public List<Caseresult> getCaseresults() {
        return this.caseresults;
    }

    /**
     * Set the list of Caseresult
     */
    public void setCaseresults(List<Caseresult> caseresults) {
        this.caseresults = caseresults;
    }

    /**
     * Get the list of Caseschedule
     */
    public List<Caseschedule> getCaseschedules() {
        return this.caseschedules;
    }

    /**
     * Set the list of Caseschedule
     */
    public void setCaseschedules(List<Caseschedule> caseschedules) {
        this.caseschedules = caseschedules;
    }
    
    /**
     * Get the list of Altscenariocase
     */
    public List<Altscenariocase> getAltscenariocases() {
        return this.altscenariocases;
    }

    /**
     * Set the list of Altscenariocase
     */
    public void setAltscenariocases(List<Altscenariocase> altscenariocases) {
        this.altscenariocases = altscenariocases;
    }

    // Display helper methods
    public int getPassCount() {
        int passCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(LabConstants.STATUS_PASS)) {
                passCount++;
            }
        }

        return passCount;
    }

    public int getProgressCount() {
        int progressCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().startsWith(LabConstants.STATUS_PROGRESS)) {
                progressCount++;
            }
        }

        return progressCount;
    }

    public int getManualCount() {
        int manualCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(LabConstants.STATUS_MANUAL)) {
                manualCount++;
            }
        }

        return manualCount;
    }

    public int getFailCount() {
        int failCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(LabConstants.STATUS_FAIL)) {
                failCount++;
            }
        }

        return failCount;
    }
    
    public int getErrorCount() {
        int errorCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(LabConstants.STATUS_ERROR)) {
            	errorCount++;
            }
        }

        return errorCount;
    }

    public int getPendCount() {
        int pendCount = 0;

        if (this.getCurrentCaseresult() == null || (this.getCurrentCaseresult().getResult()!=null && this.getCurrentCaseresult().getResult().equals(LabConstants.STATUS_INITIATED))) {
            pendCount++;
        }

        return pendCount;
    }

    public int getClearedCount() {
        int clearedCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(LabConstants.STATUS_CLEARED)) {
                clearedCount++;
            }
        }
        return clearedCount;
    }

     public int getCompletedCount() {
        int completedCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(LabConstants.STATUS_COMPLETED)) {
                completedCount++;
            }
        }
        return completedCount;
    }

     public int getInCompleteCount() {
        int inCompleteCount = 0;
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(LabConstants.STATUS_INCOMPLETE)) {
                inCompleteCount++;
            }
        }
        return inCompleteCount;
    }

    public String getCaseResultHtml() {
        String caseResultHtml = "";
        Caseresult caseresult = getCurrentCaseresult();
        if (caseresult != null) {
            if (caseresult.getResult().equals(Caseresult.RESULT_PASS)) {
                caseResultHtml = "<span class=\"status-good\">" + caseresult.getResult() + "</span>";
            } else if (caseresult.getResult().startsWith(Caseresult.RESULT_PROGRESS)) {
                caseResultHtml = "<span class=\"status-in-progress\">" + caseresult.getResult() + "</span>";
            } else if (caseresult.getResult().equals(Caseresult.RESULT_MANUAL)) {
                caseResultHtml = "<span class=\"status-manual\">" + caseresult.getResult() + "</span>";
            } else if (caseresult.getResult().equals(Caseresult.RESULT_CLEARED)) {
                caseResultHtml = "<span class=\"status-cleared\">" + caseresult.getResult() + "</span>";
            } else if (caseresult.getResult().equals(Caseresult.RESULT_COMPLETED)) {
                caseResultHtml = "<span class=\"status-completed\">" + caseresult.getResult() + "</span>";
            }else if (caseresult.getResult().equals(Caseresult.RESULT_INCOMPLETE)) {
                caseResultHtml = "<span class=\"status-incomplete\">" + caseresult.getResult() + "</span>";
            }else if (caseresult.getResult().equals(Caseresult.RESULT_ERROR)) {
                caseResultHtml = "<span class=\"status-error\">" + caseresult.getResult() + "</span>";
            }else if (caseresult.getResult().equals(Caseresult.RESULT_INITIATED)) {
            	 caseResultHtml = "Pending";
            }else {
                caseResultHtml = "<span class=\"status-bad\">" + caseresult.getResult() + "</span>";
            }
        } else {
            caseResultHtml = "Pending";
        }

        return caseResultHtml;
    }

    /**
     * A case execution is "clearable" if its case result is clearable
     * @return true if clearable; false otherwise
     */
    public boolean isClearable() {
        Caseresult caseresult = getCurrentCaseresult();
        return caseresult != null && caseresult.isClearable();
    }

    // Functional helper methods
    public boolean isDependencyExists() {
        boolean dependencyExists = false;

        if (dependentScenarioId != null && dependentCaseId != null) {
            dependencyExists = true;
        }

        return dependencyExists;
    }

    public boolean isDependentCaseExecuted() {
        return dependentCaseExecuted;
    }

    public void setDependentCaseExecuted(boolean dependentCaseExecuted) {
        this.dependentCaseExecuted = dependentCaseExecuted;
    }
    
    public Caseexecution getDepedentCaseexecution() {
    	return depedentCaseexecution;
    }
    public void setDepedentCaseexecution(Caseexecution depedentCaseexecution) {
    	this.depedentCaseexecution = depedentCaseexecution;
    }
    public boolean isCaseHiddenByDependentCaseStatus() {
		return caseHiddenByDependentCaseStatus;
	}

	public void setCaseHiddenByDependentCaseStatus(
			boolean caseHiddenByDependentCaseStatus) {
		this.caseHiddenByDependentCaseStatus = caseHiddenByDependentCaseStatus;
	}

    public List<Resultdocument> getRdrResultdocuments() {
        return rdrResultdocuments;
    }

    public void setRdrResultdocuments(List<Resultdocument> rdrResultdocuments) {
        this.rdrResultdocuments = rdrResultdocuments;
    }

    @Override
    public String toString() {
        return "\n\tCaseexecution: "+"\n\t\tcaseExecutionId="+caseExecutionId+ "\n\t\tmessageType="+messageType+"\n\t\tsuccessCriteria="+successCriteria+"\n\t\ttestcase="+(testcase!=null?testcase.getName():null) + "\n\t\tpatientId="+patientId + "\n\t\tparticipantpatientId="+participantpatientId + "\n\t\tdocumentIds="+documentIds + "\n\t\tparticipatingRIs="+participatingRIs;
        //this.scenarioexecution
        //this.beginTime
        //this.dependentScenarioId
        //this.dependentCaseId
        //this.caseresults
        //this.caseschedules
        //this.userName // always null
        //this.status // always ACTIVE
    }


}
