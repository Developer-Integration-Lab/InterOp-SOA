package net.aegis.lab.dao.pojo;

import java.io.Serializable;


/**
 * <p>Pojo mapping table questionnairetestplan</p>
 * <p></p>
 *
 * <p>Generated at Thu Jun 16 10:48:01 EDT 2011</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Questionnairetestplan implements Serializable {

        public static final String STATUS_ACTIVE  = "ACTIVE";
    /**
	 * Attribute questionnairetestplanId.
	 */
	private Integer questionnairetestplanId;
	
	/**
	 * Attribute questionnaireresponse
	 */
	 private Questionnaireresponse questionnaireresponse;	

	/**
	 * Attribute testcase
	 */
	 private Testcase testcase;	

	/**
	 * Attribute status.
	 */
	private String status;
	
	
	/**
	 * @return questionnairetestplanId
	 */
	public Integer getQuestionnairetestplanId() {
		return questionnairetestplanId;
	}

	/**
	 * @param questionnairetestplanId new value for questionnairetestplanId 
	 */
	public void setQuestionnairetestplanId(Integer questionnairetestplanId) {
		this.questionnairetestplanId = questionnairetestplanId;
	}
	
	/**
	 * get questionnaireresponse
	 */
	public Questionnaireresponse getQuestionnaireresponse() {
		return this.questionnaireresponse;
	}
	
	/**
	 * set questionnaireresponse
	 */
	public void setQuestionnaireresponse(Questionnaireresponse questionnaireresponse) {
		this.questionnaireresponse = questionnaireresponse;
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
	


}