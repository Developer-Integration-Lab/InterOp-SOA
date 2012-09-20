package net.aegis.ri.auditrepo.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * <p>Pojo mapping table auditLablog</p>
 * <p></p>
 *
 * <p>Generated at Mon Mar 08 19:15:22 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class AuditLablog implements Serializable {

	/**
	 * Attribute id.
	 */
	private Long id;

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
	 * Attribute eventCodeSystemName.
	 */
	private String eventCodeSystemName;

	/**
	 * Attribute eventDisplayName.
	 */
	private String eventDisplayName;

	/**
	 * Attribute eventOutcomeIndicator.
	 */
	private Long eventOutcomeIndicator;

	/**
	 * Attribute eventActionCode.
	 */
	private String eventActionCode;

	/**
	 * Attribute eventCode.
	 */
	private String eventCode;

	/**
	 * Attribute eventDateTime.
	 */
	private Timestamp eventDateTime;

	/**
	 * Attribute auditSourceID.
	 */
	private String auditSourceID;

	/**
	 * Attribute auditEnterpriseSiteID.
	 */
	private String auditEnterpriseSiteID;

	/**
	 * Attribute networkAccessPointID.
	 */
	private String networkAccessPointID;

	/**
	 * Attribute userName.
	 */
	private String userName;

	/**
	 * Attribute message.
	 */
	private byte[] message;


	/**
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id new value for id
	 */
	public void setId(Long id) {
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
	 * @return eventCodeSystemName
	 */
	public String getEventCodeSystemName() {
		return eventCodeSystemName;
	}

	/**
	 * @param eventCodeSystemName new value for eventCodeSystemName
	 */
	public void setEventCodeSystemName(String eventCodeSystemName) {
		this.eventCodeSystemName = eventCodeSystemName;
	}

	/**
	 * @return eventDisplayName
	 */
	public String getEventDisplayName() {
		return eventDisplayName;
	}

	/**
	 * @param eventDisplayName new value for eventDisplayName
	 */
	public void setEventDisplayName(String eventDisplayName) {
		this.eventDisplayName = eventDisplayName;
	}

	/**
	 * @return eventOutcomeIndicator
	 */
	public Long getEventOutcomeIndicator() {
		return eventOutcomeIndicator;
	}

	/**
	 * @param eventOutcomeIndicator new value for eventOutcomeIndicator
	 */
	public void setEventOutcomeIndicator(Long eventOutcomeIndicator) {
		this.eventOutcomeIndicator = eventOutcomeIndicator;
	}

	/**
	 * @return eventActionCode
	 */
	public String getEventActionCode() {
		return eventActionCode;
	}

	/**
	 * @param eventActionCode new value for eventActionCode
	 */
	public void setEventActionCode(String eventActionCode) {
		this.eventActionCode = eventActionCode;
	}

	/**
	 * @return eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode new value for eventCode
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	/**
	 * @return eventDateTime
	 */
	public Timestamp getEventDateTime() {
		return eventDateTime;
	}

	/**
	 * @param eventDateTime new value for eventDateTime
	 */
	public void setEventDateTime(Timestamp eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

	/**
	 * @return auditSourceID
	 */
	public String getAuditSourceID() {
		return auditSourceID;
	}

	/**
	 * @param auditSourceID new value for auditSourceID
	 */
	public void setAuditSourceID(String auditSourceID) {
		this.auditSourceID = auditSourceID;
	}

	/**
	 * @return auditEnterpriseSiteID
	 */
	public String getAuditEnterpriseSiteID() {
		return auditEnterpriseSiteID;
	}

	/**
	 * @param auditEnterpriseSiteID new value for auditEnterpriseSiteID
	 */
	public void setAuditEnterpriseSiteID(String auditEnterpriseSiteID) {
		this.auditEnterpriseSiteID = auditEnterpriseSiteID;
	}

	/**
	 * @return networkAccessPointID
	 */
	public String getNetworkAccessPointID() {
		return networkAccessPointID;
	}

	/**
	 * @param networkAccessPointID new value for networkAccessPointID
	 */
	public void setNetworkAccessPointID(String networkAccessPointID) {
		this.networkAccessPointID = networkAccessPointID;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName new value for userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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



}