package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Pojo mapping table scenariocase</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Scenariocase implements Serializable {

    /**
     * Attribute scenario
     */
    private Scenario scenario;
    /**
     * Attribute testcase
     */
    private Testcase testcase;
    /**
     * Attribute scenariocasePK
     */
    private ScenariocasePK scenariocasePK;
    /**
     * Attribute userName.
     */
    private String userName;
    /**
     * Attribute patientId.
     */
    private String patientId;
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
     * Attribute dependentScenarioId.
     */
    private Integer dependentScenarioId;
    /**
     * Attribute dependentCaseId.
     */
    private Integer dependentCaseId;
   
    private String displayByDependentCaseStatus ;
    
    /**
     * Attribute config.
     */
    private byte[] config;
    /**
     * List of Altscenariocase
     */
    private List<Altscenariocase> altscenariocases = null;

    /**
	 * List of Scenariocaseparameters
	 */
	private List<Scenariocaseparameters> scenariocaseparameterss = null;

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
     * get scenariocasePK
     */
    public ScenariocasePK getScenariocasePK() {
        return scenariocasePK;
    }

    /**
     * set scenariocasePK
     */
    public void setScenariocasePK(ScenariocasePK scenariocasePK) {
        this.scenariocasePK = scenariocasePK;
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
     * @return config
     */
    public byte[] getConfig() {
        return config;
    }

    /**
     * @param config new value for config
     */
    public void setConfig(byte[] config) {
        this.config = config;
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

    /**
     * Get the list of Scenariocaseparameters
     */
     public List<Scenariocaseparameters> getScenariocaseparameterss() {
            return this.scenariocaseparameterss;
     }

    /**
     * Set the list of Scenariocaseparameters
     */
     public void setScenariocaseparameterss(List<Scenariocaseparameters> scenariocaseparameterss) {
            this.scenariocaseparameterss = scenariocaseparameterss;
     }

    @Override
    public String toString() {
        return "scenariodId=" + (scenario!=null && scenario.getScenarioId()!=null?scenario.getScenarioId(): "") +
                " caseId=" + (testcase!=null && testcase.getCaseId()!=null?testcase.getCaseId(): "") +
                " userName=" + (userName!=null?userName: "")  +
                " patientId=" + (patientId!=null?patientId: "") +
                " documentIds=" + (documentIds!=null?documentIds: "") +
                " participatingRIs=" + (participatingRIs!=null?participatingRIs: "");
    }



    /**
     * <p>Composite primary key for table scenariocase</p>
     *
     * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
     * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
     */
    public static class ScenariocasePK implements Serializable {

        /**
         * Attribute scenarioId
         */
        private int scenarioId;
        /**
         * Attribute caseId
         */
        private int caseId;

        /**
         * Return scenarioId
         */
        public int getScenarioId() {
            return scenarioId;
        }

        /**
         * @param scenarioId new value for scenarioId
         */
        public void setScenarioId(int scenarioId) {
            this.scenarioId = scenarioId;
        }

        /**
         * Return caseId
         */
        public int getCaseId() {
            return caseId;
        }

        /**
         * @param caseId new value for caseId
         */
        public void setCaseId(int caseId) {
            this.caseId = caseId;
        }

        /**
         * calculate hashcode
         */
        public int hashCode() {
            //TODO : implement this method
            return super.hashCode();
        }

        /**
         * equals method
         */
        public boolean equals(Object object) {
            //TODO : implement this method
            return super.equals(object);
        }
    }
}
