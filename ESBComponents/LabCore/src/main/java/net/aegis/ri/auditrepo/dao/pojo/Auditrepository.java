package net.aegis.ri.auditrepo.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Pojo mapping table auditrepository</p>
 * <p></p>
 *
 * <p>Generated at Wed Feb 10 20:24:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Auditrepository implements Serializable {

    /**
     * Attribute id.
     */
    private Integer id;
    /**
     * Attribute timestamp.
     */
    private Timestamp timestamp;
    /**
     * Attribute eventId.
     */
    private Long eventId;
    /**
     * Attribute userId.
     */
    private String userId;
    /**
     * Attribute participationTypeCode.
     */
    private Short participationTypeCode;
    /**
     * Attribute participationTypeCodeRole.
     */
    private Short participationTypeCodeRole;
    /**
     * Attribute participationIDTypeCode.
     */
    private String participationIDTypeCode;
    /**
     * Attribute receiverPatientId.
     */
    private String receiverPatientId;
    /**
     * Attribute senderPatientId.
     */
    private String senderPatientId;
    /**
     * Attribute communityId.
     */
    private String communityId;
    /**
     * Attribute messageType.
     */
    private String messageType;
    /**
     * Attribute message.
     */
    private byte[] message;
    /**
     * List of Auditextension
     */
    private List<Auditextension> auditextensions = null;
    /**
     * Attribute testHarnessId -- NOT A DATABASE COLUMN, MANUALLY POPULATED
     */
    private Integer testHarnessId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id new value for id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp new value for timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return eventId
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * @param eventId new value for eventId
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId new value for userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return participationTypeCode
     */
    public Short getParticipationTypeCode() {
        return participationTypeCode;
    }

    /**
     * @param participationTypeCode new value for participationTypeCode
     */
    public void setParticipationTypeCode(Short participationTypeCode) {
        this.participationTypeCode = participationTypeCode;
    }

    /**
     * @return participationTypeCodeRole
     */
    public Short getParticipationTypeCodeRole() {
        return participationTypeCodeRole;
    }

    /**
     * @param participationTypeCodeRole new value for participationTypeCodeRole
     */
    public void setParticipationTypeCodeRole(Short participationTypeCodeRole) {
        this.participationTypeCodeRole = participationTypeCodeRole;
    }

    /**
     * @return participationIDTypeCode
     */
    public String getParticipationIDTypeCode() {
        return participationIDTypeCode;
    }

    /**
     * @param participationIDTypeCode new value for participationIDTypeCode
     */
    public void setParticipationIDTypeCode(String participationIDTypeCode) {
        this.participationIDTypeCode = participationIDTypeCode;
    }

    /**
     * @return receiverPatientId
     */
    public String getReceiverPatientId() {
        return receiverPatientId;
    }

    /**
     * @param receiverPatientId new value for receiverPatientId
     */
    public void setReceiverPatientId(String receiverPatientId) {
        this.receiverPatientId = receiverPatientId;
    }

    /**
     * @return senderPatientId
     */
    public String getSenderPatientId() {
        return senderPatientId;
    }

    /**
     * @param senderPatientId new value for senderPatientId
     */
    public void setSenderPatientId(String senderPatientId) {
        this.senderPatientId = senderPatientId;
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
     * @return message
     */
    public byte[] getMessage() {
        return message;
    }

    /**
     * @param message new value for message
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }

    /**
     * Get the list of Auditextension
     */
    public List<Auditextension> getAuditextensions() {
        return this.auditextensions;
    }

    /**
     * Set the list of Auditextension
     */
    public void setAuditextensions(List<Auditextension> auditextensions) {
        this.auditextensions = auditextensions;
    }

    // NOT A DATABASE COLUMN, MANUALLY POPULATED
    public Integer getTestHarnessId() {
        return testHarnessId;
    }

    public void setTestHarnessId(Integer testHarnessId) {
        this.testHarnessId = testHarnessId;
    }

    public Boolean isExtensionMessagePresent() {
        Boolean result = null;

        if (this.auditextensions != null && !this.auditextensions.isEmpty()) {
            byte[] requestMessage = this.auditextensions.get(0).getRequestMessage();
            byte[] responseMessage = this.auditextensions.get(0).getResponseMessage();

            if ((requestMessage != null && requestMessage.length > 0)
                    || (responseMessage != null && responseMessage.length > 0)) {
                result = new Boolean(true);
            }
            else {
                result = new Boolean(false);
            }
        }
        else {
            result = new Boolean(false);
        }

        return result;
    }


    @Override
    public String toString() {
        return "\n\tAuditRepository: "+ "\n\t\tid="+id + "\n\t\tmessageType="+messageType + "\n\t\ttestHarnessId="+testHarnessId+"\n\t\tuserId="+userId+"\n\t\treceiverPatientId="+receiverPatientId;
        //return "\n\tAuditRepository: "+ "\n\t\tid="+id + "\n\t\tmessageType="+messageType + "\n\t\ttestHarnessId="+testHarnessId+"\n\t\tuserId="+userId+"\n\t\treceiverPatientId="+receiverPatientId+ "\n\t\teventId="+eventId+"\n\t\tparticipationTypeCode="+participationTypeCode+"\n\t\tparticipationTypeCodeRole="+participationTypeCodeRole+"\n\t\tparticipationIDTypeCode="+participationIDTypeCode+"\n\t\tcommunityId="+communityId + "\n\t\tsenderPatientId="+senderPatientId + "\n\t\tmessage="+message;
        //this.timestamp
        //this.auditextensions
        //this.communityId + // always participant community id
        //this.senderPatientId + // always null (initiator scenario)
        //this.eventId+ // always 0
        //this.message="+(message!=null?new String(message):null)
    }



}
