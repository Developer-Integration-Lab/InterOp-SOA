package net.aegis.ri.patientcorrelationdb.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * <p>Pojo mapping table correlatedidentifiers</p>
 * <p></p>
 *
 * <p>Generated at Wed Feb 10 22:33:41 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Correlatedidentifiers implements Serializable {

	/**
	 * Attribute correlationId.
	 */
	private Integer correlationId;
	
	/**
	 * Attribute patientAssigningAuthorityId.
	 */
	private String patientAssigningAuthorityId;
	
	/**
	 * Attribute patientId.
	 */
	private String patientId;
	
	/**
	 * Attribute correlatedPatientAssignAuthId.
	 */
	private String correlatedPatientAssignAuthId;
	
	/**
	 * Attribute correlatedPatientId.
	 */
	private String correlatedPatientId;
	
	/**
	 * Attribute correlationExpirationDate.
	 */
	private Timestamp correlationExpirationDate;
	
	
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
	 * @return correlationExpirationDate
	 */
	public Timestamp getCorrelationExpirationDate() {
		return correlationExpirationDate;
	}

	/**
	 * @param correlationExpirationDate new value for correlationExpirationDate 
	 */
	public void setCorrelationExpirationDate(Timestamp correlationExpirationDate) {
		this.correlationExpirationDate = correlationExpirationDate;
	}
	


}