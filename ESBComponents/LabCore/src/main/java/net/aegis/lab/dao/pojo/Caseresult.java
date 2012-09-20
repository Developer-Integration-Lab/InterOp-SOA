package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.util.LabConstants;

/**
 * <p>Pojo mapping table caseresult</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Caseresult implements Serializable {

    public static final String RESULT_PASS = "PASS";
    public static final String RESULT_PROGRESS = "PROGRESS";
    public static final String RESULT_FAIL = "FAIL";
    public static final String RESULT_COMPLETED = "COMPLETED";
    public static final String RESULT_INCOMPLETE = "INCOMPLETE";
    public static final String RESULT_ERROR = "ERROR";
    public static final String RESULT_MANUAL = "MANUAL";
    public static final String RESULT_CLEARED = "CLEARED";
    public static final String RESULT_INITIATED = "INITIATED";
    
    
    /**
     * Attribute resultId.
     */
    private Integer resultId;
    /**
     * Attribute caseexecution
     */
    private Caseexecution caseexecution;
    /**
     * Attribute executeTime.
     */
    private Timestamp executeTime;
    /**
     * Attribute labInd.
     */
    private String labInd;
    /**
     * Attribute result.
     */
    private String result;
    /**
     * Attribute resultInfo.
     */
    private String resultInfo;
    /**
     * Attribute documentIds.
     */
    private String documentIds;
    /**
     * Attribute labDocumentIds.
     */
    private String labDocumentIds;
    /**
     * Attribute submissionInd.
     */
    private String submissionInd;
    /**
     * Attribute message.
     */
    private String message;
    
    /**
	 * Attribute errorCode.
	 */
	private String errorCode;
	
	/**
	 * Attribute stacktrace.
	 */
	private String stacktrace;
	
	/**
	 * Attribute processedRIsCount.
	 */
	private Integer processedRIsCount;
	
	/**
	 * Attribute createdAt.
	 */
	private Timestamp createdAt;
	
	/**
	 * Attribute createdBy.
	 */
	private String createdBy;
	
	/**
	 * Attribute updatedAt.
	 */
	private Timestamp updatedAt;
	
	/**
	 * Attribute updatedBy.
	 */
	private String updatedBy;
	
    /**
     * List of Attachment
     */
    private List<Attachment> attachments = null;
    /**
     * List of Auditsummary
     */
    private List<Auditsummary> auditsummarys = null;
    /**
	 * List of Caseresultparameters
	 */
    private List<Caseresultparameters> caseresultparameterss = null;
    
    /**
	 * List of Patientcorrelation
	 */
	private List<Patientcorrelation> patientcorrelations = null;
	
    /**
     * List of Resultdocument
     */
    private List<Resultdocument> resultdocuments = null;
    
    private List<Resultsummary> resultsummarys = null;
    
    public Caseresult(){
    	// set up required default values
    	this.setSubmissionInd(LabConstants.NO_INDICATOR);
    	this.setResult(RESULT_PROGRESS);
    }

	/**
     * @return resultId
     */
    public Integer getResultId() {
        return resultId;
    }

    /**
     * @param resultId new value for resultId
     */
    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    /**
     * get caseexecution
     */
    public Caseexecution getCaseexecution() {
        return this.caseexecution;
    }

    /**
     * set caseexecution
     */
    public void setCaseexecution(Caseexecution caseexecution) {
        this.caseexecution = caseexecution;
    }

    /**
     * @return executeTime
     */
    public Timestamp getExecuteTime() {
        return executeTime;
    }

    /**
     * @param executeTime new value for executeTime
     */
    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
    }

    /**
     * @return labInd
     */
    public String getLabInd() {
        return labInd;
    }

    /**
     * @param labInd new value for labInd
     */
    public void setLabInd(String labInd) {
        this.labInd = labInd;
    }

    /**
     * @return result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result new value for result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return resultInfo
     */
    public String getResultInfo() {
        return resultInfo;
    }

    /**
     * @param resultInfo new value for resultInfo
     */
    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    /**
     * Get the documentIds
     */
    public String getDocumentIds() {
        return documentIds;
    }

    /**
     * Set the documentIds
     */
    public void setDocumentIds(String documentIds) {
        this.documentIds = documentIds;
    }

    /**
     * Get the labDocumentIds
     */
    public String getLabDocumentIds() {
        return labDocumentIds;
    }

    /**
     * Set the labDocumentIds
     */
    public void setLabDocumentIds(String labDocumentIds) {
        this.labDocumentIds = labDocumentIds;
    }

    /**
     * @return submissionInd
     */
    public String getSubmissionInd() {
        return submissionInd;
    }

    /**
     * @param submissionInd new value for submissionInd
     */
    public void setSubmissionInd(String submissionInd) {
        this.submissionInd = submissionInd;
    }

    /**
     * get message
     */
    public String getMessage() {
        return message;
    }

    /**
     * set message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode new value for errorCode 
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * @return stacktrace
	 */
	public String getStacktrace() {
		return stacktrace;
	}

	/**
	 * @param stacktrace new value for stacktrace 
	 */
	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}
	
	/**
	 * @return processedRIsCount
	 */
	public Integer getProcessedRIsCount() {
		return processedRIsCount;
	}

	/**
	 * @param processedRIsCount new value for processedRIsCount 
	 */
	public void setProcessedRIsCount(Integer processedRIsCount) {
		this.processedRIsCount = processedRIsCount;
	}
	/**
	 * @return createdAt
	 */
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt new value for createdAt 
	 */
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * @return createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy new value for createdBy 
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * @return updatedAt
	 */
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt new value for updatedAt 
	 */
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	/**
	 * @return updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy new value for updatedBy 
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
    /**
     * Get the list of Attachment
     */
    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    /**
     * Set the list of Attachment
     */
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * Get the list of Auditsummary
     */
    public List<Auditsummary> getAuditsummarys() {
        return this.auditsummarys;
    }

    /**
     * Set the list of Auditsummary
     */
    public void setAuditsummarys(List<Auditsummary> auditsummarys) {
        this.auditsummarys = auditsummarys;
    }

    /**
     * Get the list of Caseresultparameters
     */
     public List<Caseresultparameters> getCaseresultparameterss() {
            return this.caseresultparameterss;
     }

    /**
     * Set the list of Caseresultparameters
     */
     public void setCaseresultparameterss(List<Caseresultparameters> caseresultparameterss) {
            this.caseresultparameterss = caseresultparameterss;
     }
     

     /**
 	 * Get the list of Patientcorrelation
 	 */
 	 public List<Patientcorrelation> getPatientcorrelations() {
 	 	return this.patientcorrelations;
 	 }
 	 
 	/**
 	 * Set the list of Patientcorrelation
 	 */
 	 public void setPatientcorrelations(List<Patientcorrelation> patientcorrelations) {
 	 	this.patientcorrelations = patientcorrelations;
 	 }

    /**
     * Get the list of Resultdocument
     */
    public List<Resultdocument> getResultdocuments() {
        return this.resultdocuments;
    }

    /**
     * Set the list of Resultdocument
     */
    public void setResultdocuments(List<Resultdocument> resultdocuments) {
        this.resultdocuments = resultdocuments;
    }
    
    /**
	 * @return the resultsummarys
	 */
	public List<Resultsummary> getResultsummarys() {
		return resultsummarys;
	}

	/**
	 * @param resultsummarys the resultsummarys to set
	 */
	public void setResultsummarys(List<Resultsummary> resultsummarys) {
		this.resultsummarys = resultsummarys;
	}

    /**
     * A case result is clearable if it's in any of the PROGRESS states.
     * @return true if clearable; false otherwise
     */
    public boolean isClearable() {
        String result = getResult();
        return result!=null && result.startsWith("PROGRESS");
    }

    @Override
    public String toString() {
        return "\n\tCaseResult: "+ "\n\t\tresult="+result+"\n\t\tmessage="+message+"\n\t\tresultId="+resultId+"\n\t\tdocumentIds="+documentIds+"\n\t\tcaseexecution="+caseexecution + "\n\t\tauditsummarys="+(auditsummarys!=null?auditsummarys.size():null);
        //this.executeTime
        //this.attachments
        //this.auditsummarys
        //this.resultdocuments
        //this.labDocumentIds // always null
        //this.labInd  // always null
        //this.resultInfo // always null
        //this.submissionInd // always N
    }


}
