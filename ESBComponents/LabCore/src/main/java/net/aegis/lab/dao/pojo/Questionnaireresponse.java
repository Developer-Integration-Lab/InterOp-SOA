package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * <p>Pojo mapping table questionnaireresponse</p>
 * <p></p>
 *
 * <p>Generated at Thu Jun 16 10:48:01 EDT 2011</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Questionnaireresponse implements Serializable {

        public static final String STATUS_ACTIVE  = "ACTIVE";
    /**
	 * Attribute questionnaireresponseId.
	 */
	private Integer questionnaireresponseId;
	
	/**
	 * Attribute servicesetexecution
	 */
	 private Servicesetexecution servicesetexecution;	

	/**
	 * Attribute questionnaire
	 */
	 private Questionnaire questionnaire;	

	/**
	 * Attribute participant
	 */
	 private Participant participant;

	/**
	 * Attribute executionUniqueId.
	 */
	private String executionUniqueId;
	
	/**
	 * Attribute status.
	 */
	private String status;
	
	/**
	 * Attribute value.
	 */
	private String answer;
	
	/**
	 * Attribute beginTime.
	 */
	private Timestamp beginTime;
	
	/**
	 * Attribute endTime.
	 */
	private Timestamp endTime;
	
	/**
	 * List of Questionnairetestplan
	 */
	private List<Questionnairetestplan> questionnairetestplans = null;

	
	/**
	 * @return questionnaireresponseId
	 */
	public Integer getQuestionnaireresponseId() {
		return questionnaireresponseId;
	}

	/**
	 * @param questionnaireresponseId new value for questionnaireresponseId 
	 */
	public void setQuestionnaireresponseId(Integer questionnaireresponseId) {
		this.questionnaireresponseId = questionnaireresponseId;
	}
	
	/**
	 * get servicesetexecution
	 */
	public Servicesetexecution getServicesetexecution() {
		return this.servicesetexecution;
	}
	
	/**
	 * set servicesetexecution
	 */
	public void setServicesetexecution(Servicesetexecution servicesetexecution) {
		this.servicesetexecution = servicesetexecution;
	}

	/**
	 * get questionnaire
	 */
	public Questionnaire getQuestionnaire() {
		return this.questionnaire;
	}
	
	/**
	 * set questionnaire
	 */
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
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
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param value new value for answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
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
	 * Get the list of Questionnairetestplan
	 */
	 public List<Questionnairetestplan> getQuestionnairetestplans() {
	 	return this.questionnairetestplans;
	 }
	 
	/**
	 * Set the list of Questionnairetestplan
	 */
	 public void setQuestionnairetestplans(List<Questionnairetestplan> questionnairetestplans) {
	 	this.questionnairetestplans = questionnairetestplans;
	 }


}