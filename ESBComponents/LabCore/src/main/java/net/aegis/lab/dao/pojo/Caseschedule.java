package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * <p>Pojo mapping table caseschedule</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Caseschedule implements Serializable {

	/**
	 * Attribute caseScheduleId.
	 */
	private Integer caseScheduleId;
	
	/**
	 * Attribute caseexecution
	 */
	 private Caseexecution caseexecution;	

	/**
	 * Attribute executed.
	 */
	private String executed;
	
	/**
	 * Attribute status.
	 */
	private String status;
	
	/**
	 * Attribute scheduledTime.
	 */
	private Timestamp scheduledTime;
	
	/**
	 * Attribute executedTime.
	 */
	private Timestamp executedTime;
	
	
	/**
	 * @return caseScheduleId
	 */
	public Integer getCaseScheduleId() {
		return caseScheduleId;
	}

	/**
	 * @param caseScheduleId new value for caseScheduleId 
	 */
	public void setCaseScheduleId(Integer caseScheduleId) {
		this.caseScheduleId = caseScheduleId;
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
	 * @return executed
	 */
	public String getExecuted() {
		return executed;
	}

	/**
	 * @param executed new value for executed 
	 */
	public void setExecuted(String executed) {
		this.executed = executed;
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
	 * @return scheduledTime
	 */
	public Timestamp getScheduledTime() {
		return scheduledTime;
	}

	/**
	 * @param scheduledTime new value for scheduledTime 
	 */
	public void setScheduledTime(Timestamp scheduledTime) {
		this.scheduledTime = scheduledTime;
	}
	
	/**
	 * @return executedTime
	 */
	public Timestamp getExecutedTime() {
		return executedTime;
	}

	/**
	 * @param executedTime new value for executedTime 
	 */
	public void setExecutedTime(Timestamp executedTime) {
		this.executedTime = executedTime;
	}
	


}