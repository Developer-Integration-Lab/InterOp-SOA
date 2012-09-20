package net.aegis.lab.dao.pojo;

import java.io.Serializable;


/**
 * <p>Pojo mapping table scenariocaseparameters</p>
 * <p></p>
 *
 * <p>Generated at Fri Apr 08 11:54:48 EDT 2011</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Scenariocaseparameters implements Serializable {

	/**
	 * Attribute scenariocaseparametersId.
	 */
	private Integer scenariocaseparametersId;
	
	/**
	 * Attribute scenariocase
	 */
	 private Scenariocase scenariocase;	

	/**
	 * Attribute paramName.
	 */
	private String paramName;
	
	/**
	 * Attribute displayParamName.
	 */
	private String displayParamName;
	
	/**
	 * Attribute paramValue.
	 */
	private String paramValue;
	
	/**
	 * Attribute required.
	 */
	private String required;
	
	
	/**
	 * @return scenariocaseparametersId
	 */
	public Integer getScenariocaseparametersId() {
		return scenariocaseparametersId;
	}

	/**
	 * @param scenariocaseparametersId new value for scenariocaseparametersId 
	 */
	public void setScenariocaseparametersId(Integer scenariocaseparametersId) {
		this.scenariocaseparametersId = scenariocaseparametersId;
	}
	
	/**
	 * get scenariocase
	 */
	public Scenariocase getScenariocase() {
		return this.scenariocase;
	}
	
	/**
	 * set scenariocase
	 */
	public void setScenariocase(Scenariocase scenariocase) {
		this.scenariocase = scenariocase;
	}

	/**
	 * @return paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName new value for paramName 
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	/**
	 * @return displayParamName
	 */
	public String getDisplayParamName() {
		return displayParamName;
	}

	/**
	 * @param displayParamName new value for displayParamName 
	 */
	public void setDisplayParamName(String displayParamName) {
		this.displayParamName = displayParamName;
	}
	
	/**
	 * @return paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue new value for paramValue 
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	/**
	 * @return required
	 */
	public String getRequired() {
		return required;
	}

	/**
	 * @param required new value for required 
	 */
	public void setRequired(String required) {
		this.required = required;
	}
	


}