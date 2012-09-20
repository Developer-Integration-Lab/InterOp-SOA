package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.util.LabConstants;

/**
 * <p>Pojo mapping table scenario</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Scenario implements Serializable, Comparable<Scenario> {

    /**
     * Attribute scenarioId.
     */
    private Integer scenarioId;
    /**
     * Attribute serviceset
     */
    private Serviceset serviceset;
    /**
     * Attribute scenarioName.
     */
    private String scenarioName;
    /**
     * Attribute quickName;
     */
    private String quickName;
    /**
     * Attribute description.
     */
    private String description;
    /**
     * Attribute conditionDescription.
     */
    private String conditionDescription;
    /**
     * Attribute status.
     */
    private String status;
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
     * List of Scenariocase
     */
    private List<Scenariocase> scenariocases = null;
    /**
     * List of Scenarioexecution
     */
    private List<Scenarioexecution> scenarioexecutions = null;

    private boolean initiator;

    private boolean responder;

    /**
     * @return scenarioId
     */
    public Integer getScenarioId() {
        return scenarioId;
    }

    /**
     * @param scenarioId new value for scenarioId
     */
    public void setScenarioId(Integer scenarioId) {
        this.scenarioId = scenarioId;
    }

    /**
     * get serviceset
     */
    public Serviceset getServiceset() {
        return this.serviceset;
    }

    /**
     * set serviceset
     */
    public void setServiceset(Serviceset serviceset) {
        this.serviceset = serviceset;
    }

    /**
     * @return scenarioName
     */
    public String getScenarioName() {
        return scenarioName;
    }

    /**
     * @param scenarioName new value for scenarioName
     */
    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getQuickName() {
        return quickName;
    }

    public void setQuickName(String quickName) {
        this.quickName = quickName;
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
     * @return conditionDescription
     */
    public String getConditionDescription() {
        return conditionDescription;
    }

    /**
     * @param condition new value for conditionDescription
     */
    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
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

    /**
     * Get the list of Scenarioexecution
     */
    public List<Scenarioexecution> getScenarioexecutions() {
        return this.scenarioexecutions;
    }

    /**
     * Set the list of Scenarioexecution
     */
    public void setScenarioexecutions(List<Scenarioexecution> scenarioexecutions) {
        this.scenarioexecutions = scenarioexecutions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Scenario other = (Scenario) obj;
        if ((this.scenarioName == null) ? (other.scenarioName != null) : !this.scenarioName.equals(other.scenarioName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.scenarioName != null ? this.scenarioName.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Scenario o) {
        return this.getScenarioName().compareTo(o.getScenarioName());
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
