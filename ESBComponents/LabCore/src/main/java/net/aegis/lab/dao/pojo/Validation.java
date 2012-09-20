package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Pojo mapping table validation</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Validation implements Serializable {

    /**
     * Attribute validationId.
     */
    private Integer validationId;
    /**
     * Attribute servicesetexecution
     */
    private Servicesetexecution servicesetexecution;
    /**
     * Attribute nhinrep
     */
    private Integer nhinRepId;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute decision.
     */
    private String decision;
    /**
     * Attribute decisionReason.
     */
    private String decisionReason;
    /**
     * Attribute comments.
     */
    private String comments;
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
     * @return validationId
     */
    public Integer getValidationId() {
        return validationId;
    }

    /**
     * @param validationId new value for validationId
     */
    public void setValidationId(Integer validationId) {
        this.validationId = validationId;
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
     * @return the nhinRepId
     */
    public Integer getNhinRepId() {
        return nhinRepId;
    }

    /**
     * @param nhinRepId the nhinRepId to set
     */
    public void setNhinRepId(Integer nhinRepId) {
        this.nhinRepId = nhinRepId;
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
     * @return decision
     */
    public String getDecision() {
        return decision;
    }

    /**
     * @param decision new value for decision
     */
    public void setDecision(String decision) {
        this.decision = decision;
    }

    /**
     * @return decisionReason
     */
    public String getDecisionReason() {
        return decisionReason;
    }

    /**
     * @param decisionReason new value for decisionReason
     */
    public void setDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }

    /**
     * @return comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments new value for comments
     */
    public void setComments(String comments) {
        this.comments = comments;
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
}
