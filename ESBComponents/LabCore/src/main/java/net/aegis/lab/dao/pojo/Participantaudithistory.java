package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Pojo mapping table participantaudithistory</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Participantaudithistory implements Serializable {

    /**
     * Attribute auditId.
     */
    private Integer auditId;
    /**
     * Attribute participant
     */
    private Participant participant;
    /**
     * Attribute participantName.
     */
    private String participantName;
    /**
     * Attribute userId.
     */
    private Integer userId;
    /**
     * Attribute communityId.
     */
    private String communityId;
    /**
     * Attribute ipAddress.
     */
    private String ipAddress;
    /**
     * Attribute nhinRepId.
     */
    private Integer nhinRepId;
    /**
     * Attribute contactName.
     */
    private String contactName;
    /**
     * Attribute contactPhone.
     */
    private String contactPhone;
    /**
     * Attribute contactEmail.
     */
    private String contactEmail;
    /**
     * Attribute initiatorInd.
     */
    private String initiatorInd;
    /**
     * Attribute reposnderInd.
     */
    private String reposnderInd;
    /**
     * Attribute ssnHandlingInd.
     */
    private String ssnHandlingInd;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute commVerifyStatus.
     */
    private String commVerifyStatus;
    /**
     * Attribute commVerifyTimestamp.
     */
    private Timestamp commVerifyTimestamp;
    /**
     * Attribute auditcreatetime.
     */
    private Timestamp auditcreatetime;
    /**
     * Attribute auditcreateuser.
     */
    private String auditcreateuser;
    /**
     * Attribute auditchangedtime.
     */
    private Timestamp auditchangedtime;
    /**
     * Attribute auditchangeduser.
     */
    private String auditchangeduser;
    /**
     * Attribute createtime.
     */
    private Timestamp createtime;
    /**
     * Attribute createuser.
     */
    private String createuser;

    /**
     * @return auditId
     */
    public Integer getAuditId() {
        return auditId;
    }

    /**
     * @param auditId new value for auditId
     */
    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
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
     * @return participantName
     */
    public String getParticipantName() {
        return participantName;
    }

    /**
     * @param participantName new value for participantName
     */
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    /**
     * @return userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId new value for userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return communityId
     */
    public String getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId new value for communityId
     */
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * @return ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress new value for ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return nhinRepId
     */
    public Integer getNhinRepId() {
        return nhinRepId;
    }

    /**
     * @param nhinRepId new value for nhinRepId
     */
    public void setNhinRepId(Integer nhinRepId) {
        this.nhinRepId = nhinRepId;
    }

    /**
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName new value for contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone new value for contactPhone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail new value for contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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
    }

    /**
     * @return reposnderInd
     */
    public String getReposnderInd() {
        return reposnderInd;
    }

    /**
     * @param reposnderInd new value for reposnderInd
     */
    public void setReposnderInd(String reposnderInd) {
        this.reposnderInd = reposnderInd;
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
     * @return commVerifyStatus
     */
    public String getCommVerifyStatus() {
        return commVerifyStatus;
    }

    /**
     * @param commVerifyStatus new value for commVerifyStatus
     */
    public void setCommVerifyStatus(String commVerifyStatus) {
        this.commVerifyStatus = commVerifyStatus;
    }

    /**
     * @return commVerifyTimestamp
     */
    public Timestamp getCommVerifyTimestamp() {
        return commVerifyTimestamp;
    }

    /**
     * @param commVerifyTimestamp new value for commVerifyTimestamp
     */
    public void setCommVerifyTimestamp(Timestamp commVerifyTimestamp) {
        this.commVerifyTimestamp = commVerifyTimestamp;
    }

    /**
     * @return auditcreatetime
     */
    public Timestamp getAuditcreatetime() {
        return auditcreatetime;
    }

    /**
     * @param auditcreatetime new value for auditcreatetime
     */
    public void setAuditcreatetime(Timestamp auditcreatetime) {
        this.auditcreatetime = auditcreatetime;
    }

    /**
     * @return auditcreateuser
     */
    public String getAuditcreateuser() {
        return auditcreateuser;
    }

    /**
     * @param auditcreateuser new value for auditcreateuser
     */
    public void setAuditcreateuser(String auditcreateuser) {
        this.auditcreateuser = auditcreateuser;
    }

    /**
     * @return auditchangedtime
     */
    public Timestamp getAuditchangedtime() {
        return auditchangedtime;
    }

    /**
     * @param auditchangedtime new value for auditchangedtime
     */
    public void setAuditchangedtime(Timestamp auditchangedtime) {
        this.auditchangedtime = auditchangedtime;
    }

    /**
     * @return auditchangeduser
     */
    public String getAuditchangeduser() {
        return auditchangeduser;
    }

    /**
     * @param auditchangeduser new value for auditchangeduser
     */
    public void setAuditchangeduser(String auditchangeduser) {
        this.auditchangeduser = auditchangeduser;
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
}
