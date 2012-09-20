package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.util.LabConstants;

/**
 * <p>Pojo mapping table testcase</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Testcase implements Serializable, Comparable<Testcase> {

    /**
     * Attribute caseId.
     */
    private Integer caseId;
    /**
     * Attribute name.
     */
    private String name;
    /**
     * Attribute description.
     */
    private String description;
    /**
     * Attribute messageType.
     */
    private String messageType;
    /**
     * Attribute messageName.
     */
    private String messageName;
    /**
     * Attribute testType.
     */
    private String testType;
    /**
     * Attribute executeType.
     */
    private String executeType;
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
     * Attribute expectedTestResults.
     */
    private String expectedTestResults;
    /**
     * Attribute createtime.
     */
    private Timestamp createtime;
    /**
     * Attribute createuser.
     */
    private String createuser;
    /**
     * Attribute changedtime.
     */
    private Timestamp changedtime;
    /**
     * Attribute changeduser.
     */
    private String changeduser;
    /**
     * Attribute nhinSpecHtml.
     */
    private String nhinSpecHtml;
    /**
     * List of Caseexecution
     */
    private List<Caseexecution> caseexecutions = null;
    /**
     * List of Questionnairecase
     */
    private List<Questionnairecase> questionnairecases = null;
    /**
     * List of Questionnairetestplan
     */
    private List<Questionnairetestplan> questionnairetestplans = null;

    /**
     * List of Scenariocase
     */
    private List<Scenariocase> scenariocases = null;

    private boolean initiator;

    private boolean responder;
    
    /**
     * @return caseId
     */
    public Integer getCaseId() {
        return caseId;
    }

    /**
     * @param caseId new value for caseId
     */
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new value for name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description new value for description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return messageName
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * @param messageName new value for messageName
     */
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    /**
     * @return testType
     */
    public String getTestType() {
        return testType;
    }

    /**
     * @param testType new value for testType
     */
    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * @return executeType
     */
    public String getExecuteType() {
        return executeType;
    }

    /**
     * @param executeType new value for executeType
     */
    public void setExecuteType(String executeType) {
        this.executeType = executeType;
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
        if (LabConstants.INITIATOR_IND_Y.equals(initiatorInd)) {
            setInitiator(true);
        } else {
            setInitiator(false);
        }
    }

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
        if (LabConstants.RESPONDER_IND_Y.equals(responderInd)) {
            setResponder(true);
        } else {
            setResponder(false);
        }
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
     * @return expectedTestResults
     */
    public String getExpectedTestResults() {
        return expectedTestResults;
    }

    /**
     * @param expectedTestResults new value for expectedTestResults
     */
    public void setExpectedTestResults(String expectedTestResults) {
        this.expectedTestResults = expectedTestResults;
    }

    /**
     * @return createtime
     */
    public Timestamp getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime new value for createtime
     */
    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    /**
     * @return createuser
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser new value for createuser
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    /**
     * @return changedtime
     */
    public Timestamp getChangedtime() {
        return changedtime;
    }

    /**
     * @param changedtime new value for changedtime
     */
    public void setChangedtime(Timestamp changedtime) {
        this.changedtime = changedtime;
    }

    /**
     * @return changeduser
     */
    public String getChangeduser() {
        return changeduser;
    }

    /**
     * @param changeduser new value for changeduser
     */
    public void setChangeduser(String changeduser) {
        this.changeduser = changeduser;
    }

    /**
     * @return nhinSpecHtml
     */
    public String getNhinSpecHtml() {
        return nhinSpecHtml;
    }

    /**
     * @param nhinSpecHtml new value for nhinSpecHtml
     */
    public void setNhinSpecHtml(String nhinSpecHtml) {
        this.nhinSpecHtml = nhinSpecHtml;
    }

    /**
     * Get the list of Caseexecution
     */
    public List<Caseexecution> getCaseexecutions() {
        return this.caseexecutions;
    }

    /**
     * Set the list of Caseexecution
     */
    public void setCaseexecutions(List<Caseexecution> caseexecutions) {
        this.caseexecutions = caseexecutions;
    }

    /**
     * Get the list of Questionnairecase
     */
    public List<Questionnairecase> getQuestionnairecases() {
        return this.questionnairecases;
    }

    /**
     * Set the list of Questionnairecase
     */
    public void setQuestionnairecases(List<Questionnairecase> questionnairecases) {
        this.questionnairecases = questionnairecases;
    }

    public List<Questionnairetestplan> getQuestionnairetestplans() {
        return questionnairetestplans;
    }

    public void setQuestionnairetestplans(List<Questionnairetestplan> questionnairetestplans) {
        this.questionnairetestplans = questionnairetestplans;
    }

    /**
     * Get the list of Scenariocase
     */
    public List<Scenariocase> getScenariocases() {
        return this.scenariocases;
    }

    /**
     * Set the list of Scenariocase
     */
    public void setScenariocases(List<Scenariocase> scenariocases) {
        this.scenariocases = scenariocases;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Testcase other = (Testcase) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Testcase o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean isInitiator() {
        return initiator;
    }

    public void setInitiator(boolean initiator) {
        this.initiator = initiator;
    }

    public boolean isResponder() {
        return responder;
    }

    public void setResponder(boolean responder) {
        this.responder = responder;
    }
}
