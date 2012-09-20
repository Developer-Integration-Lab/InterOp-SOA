package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Pojo mapping table serviceset</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Serviceset implements Serializable {

    /**
     * Attribute setId.
     */
    private Integer setId;
    /**
     * Attribute setName.
     */
    private String setName;
    /**
     * Attribute initiatorAllowedInd.
     */
    private String initiatorAllowedInd;
    /**
     * Attribute responderAllowedInd.
     */
    private String responderAllowedInd;
    /**
     * Attribute ssnHandlingInd.
     */
    private String ssnHandlingInd;
    /**
     * Attribute status.
     */
    private String status;
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
     * List of Questionnaire
     */
    private List<Questionnaire> questionnaires = null;

    /**
     * Used to differentiate between lab and conformance.
     * Story 51: Integrate Conformance Tests - Core Library changes
     */
    private Integer labAccessFilter;
    /**
     * List of Scenario
     */
    private List<Scenario> scenarios = null;
    /**
     * List of Servicesetexecution
     */
    private List<Servicesetexecution> servicesetexecutions = null;

    /**
     * @return setId
     */
    public Integer getSetId() {
        return setId;
    }

    /**
     * @param setId new value for setId
     */
    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    /**
     * @return setName
     */
    public String getSetName() {
        return setName;
    }

    /**
     * @param setName new value for setName
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

    /**
     * @return initiatorAllowedInd
     */
    public String getInitiatorAllowedInd() {
        return initiatorAllowedInd;
    }

    /**
     * @param initiatorAllowedInd new value for initiatorAllowedInd
     */
    public void setInitiatorAllowedInd(String initiatorAllowedInd) {
        this.initiatorAllowedInd = initiatorAllowedInd;
    }

    /**
     * @return responderAllowedInd
     */
    public String getResponderAllowedInd() {
        return responderAllowedInd;
    }

    /**
     * @param responderAllowedInd new value for responderAllowedInd
     */
    public void setResponderAllowedInd(String responderAllowedInd) {
        this.responderAllowedInd = responderAllowedInd;
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
     * Get the list of Questionnaire
     */
    public List<Questionnaire> getQuestionnaires() {
        return this.questionnaires;
    }

    /**
     * Set the list of Questionnaire
     */
    public void setQuestionnaires(List<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }

    /**
     * Get the list of Scenario
     */
    public List<Scenario> getScenarios() {
        return this.scenarios;
    }

    /**
     * Set the list of Scenario
     */
    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    /**
     * Get the list of Servicesetexecution
     */
    public List<Servicesetexecution> getServicesetexecutions() {
        return this.servicesetexecutions;
    }

    /**
     * Set the list of Servicesetexecution
     */
    public void setServicesetexecutions(List<Servicesetexecution> servicesetexecutions) {
        this.servicesetexecutions = servicesetexecutions;
    }

    @Override
    public String toString() {
        return "setId="+setId+" setName="+setName + " initiatorAllowedInd="+initiatorAllowedInd + " responderAllowedInd="+responderAllowedInd + " ssnHandlingInd="+ssnHandlingInd + " status="+status + " createtime="+createtime + " createuser="+createuser + " changedtime="+changedtime + " changedtime="+changedtime + " changeduser="+changeduser;
    }

    public Integer getLabAccessFilter() {
        return labAccessFilter;
    }

    public void setLabAccessFilter(Integer labAccessFilter) {
        this.labAccessFilter = labAccessFilter;
    }

}
