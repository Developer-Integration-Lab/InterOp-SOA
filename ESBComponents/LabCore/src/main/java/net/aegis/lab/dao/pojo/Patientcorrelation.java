package net.aegis.lab.dao.pojo;

import java.util.List;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * <p>Pojo mapping table patientcorrelation</p>
 * <p></p>
 *
 * <p>Generated at Fri Apr 06 16:31:02 EDT 2012</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Patientcorrelation implements Serializable {

	/**
	 * Attribute correlationId.
	 */
	private Integer correlationId;
	
	/**
	 * Attribute caseresult
	 */
	 private Caseresult caseresult;	

	/**
	 * Attribute patientId.
	 */
	private String patientId;
	
	/**
	 * Attribute patientAssigningAuthorityId.
	 */
	private String patientAssigningAuthorityId;
	
	/**
	 * Attribute patientHomeCommunityId.
	 */
	private String patientHomeCommunityId;
	
	/**
	 * Attribute correlatedPatientId.
	 */
	private String correlatedPatientId;
	
	/**
	 * Attribute correlatedPatientAssignAuthId.
	 */
	private String correlatedPatientAssignAuthId;
	
	/**
	 * Attribute correlatedPatientHomeCommunityId.
	 */
	private String correlatedPatientHomeCommunityId;
	
	/**
	 * Attribute correlated.
	 */
	private String correlated;
	
	/**
	 * Attribute error.
	 */
	private String error;
	
	/**
	 * Attribute status.
	 */
	private String status;
	
	/**
     * Attribute message.
     */
    private String message;
	
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
	 * @return correlationId
	 */
	public Integer getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId new value for correlationId 
	 */
	public void setCorrelationId(Integer correlationId) {
		this.correlationId = correlationId;
	}
	
	/**
	 * get caseresult
	 */
	public Caseresult getCaseresult() {
		return this.caseresult;
	}
	
	/**
	 * set caseresult
	 */
	public void setCaseresult(Caseresult caseresult) {
		this.caseresult = caseresult;
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
	 * @return patientAssigningAuthorityId
	 */
	public String getPatientAssigningAuthorityId() {
		return patientAssigningAuthorityId;
	}

	/**
	 * @param patientAssigningAuthorityId new value for patientAssigningAuthorityId 
	 */
	public void setPatientAssigningAuthorityId(String patientAssigningAuthorityId) {
		this.patientAssigningAuthorityId = patientAssigningAuthorityId;
	}
	
	/**
	 * @return patientHomeCommunityId
	 */
	public String getPatientHomeCommunityId() {
		return patientHomeCommunityId;
	}

	/**
	 * @param patientHomeCommunityId new value for patientHomeCommunityId 
	 */
	public void setPatientHomeCommunityId(String patientHomeCommunityId) {
		this.patientHomeCommunityId = patientHomeCommunityId;
	}
	
	/**
	 * @return correlatedPatientId
	 */
	public String getCorrelatedPatientId() {
		return correlatedPatientId;
	}

	/**
	 * @param correlatedPatientId new value for correlatedPatientId 
	 */
	public void setCorrelatedPatientId(String correlatedPatientId) {
		this.correlatedPatientId = correlatedPatientId;
	}
	
	/**
	 * @return correlatedPatientAssignAuthId
	 */
	public String getCorrelatedPatientAssignAuthId() {
		return correlatedPatientAssignAuthId;
	}

	/**
	 * @param correlatedPatientAssignAuthId new value for correlatedPatientAssignAuthId 
	 */
	public void setCorrelatedPatientAssignAuthId(String correlatedPatientAssignAuthId) {
		this.correlatedPatientAssignAuthId = correlatedPatientAssignAuthId;
	}
	
	/**
	 * @return correlatedPatientHomeCommunityId
	 */
	public String getCorrelatedPatientHomeCommunityId() {
		return correlatedPatientHomeCommunityId;
	}

	/**
	 * @param correlatedPatientHomeCommunityId new value for correlatedPatientHomeCommunityId 
	 */
	public void setCorrelatedPatientHomeCommunityId(String correlatedPatientHomeCommunityId) {
		this.correlatedPatientHomeCommunityId = correlatedPatientHomeCommunityId;
	}
	
	/**
	 * @return correlated
	 */
	public String getCorrelated() {
		return correlated;
	}

	/**
	 * @param correlated new value for correlated 
	 */
	public void setCorrelated(String correlated) {
		this.correlated = correlated;
	}
	
	/**
	 * @return error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error new value for error 
	 */
	public void setError(String error) {
		this.error = error;
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
	


}