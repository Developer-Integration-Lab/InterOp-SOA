package net.aegis.ri.auditrepo.dao;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.genericdao.GenericDao;
import net.aegis.ri.auditrepo.dao.pojo.AuditLablog;
/**
 * <p>Hibernate DAO layer for Auditlablogs</p>
 * <p>Generated at Mon Mar 08 19:15:22 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface AuditLablogDAO extends GenericDao<AuditLablog, Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return
	 * an instance of the new implemenation in buildAuditLablogDAO()
	 */

	/**
 	 * Find AuditLablog by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<AuditLablog> findByCriteria(Timestamp timestamp, Long eventId, String userId, Short participationTypeCode, Short participationTypeCodeRole, String participationIDTypeCode, String receiverPatientId, String senderPatientId, String communityId, String messageType, String eventCodeSystemName, String eventDisplayName, Long eventOutcomeIndicator, String eventActionCode, String eventCode, Timestamp eventDateTime, String auditSourceID, String auditEnterpriseSiteID, String networkAccessPointID, String userName, byte[] message);


	/**
	 * Find AuditLablog by timestamp
	 */
	public List<AuditLablog> findByTimestamp(Timestamp timestamp);

	/**
	 * Find AuditLablog by eventId
	 */
	public List<AuditLablog> findByEventId(Long eventId);

	/**
	 * Find AuditLablog by userId
	 */
	public List<AuditLablog> findByUserId(String userId);

	/**
	 * Find AuditLablog by participationTypeCode
	 */
	public List<AuditLablog> findByParticipationTypeCode(Short participationTypeCode);

	/**
	 * Find AuditLablog by participationTypeCodeRole
	 */
	public List<AuditLablog> findByParticipationTypeCodeRole(Short participationTypeCodeRole);

	/**
	 * Find AuditLablog by participationIDTypeCode
	 */
	public List<AuditLablog> findByParticipationIDTypeCode(String participationIDTypeCode);

	/**
	 * Find AuditLablog by receiverPatientId
	 */
	public List<AuditLablog> findByReceiverPatientId(String receiverPatientId);

	/**
	 * Find AuditLablog by senderPatientId
	 */
	public List<AuditLablog> findBySenderPatientId(String senderPatientId);

	/**
	 * Find AuditLablog by communityId
	 */
	public List<AuditLablog> findByCommunityId(String communityId);

	/**
	 * Find AuditLablog by messageType
	 */
	public List<AuditLablog> findByMessageType(String messageType);

	/**
	 * Find AuditLablog by eventCodeSystemName
	 */
	public List<AuditLablog> findByEventCodeSystemName(String eventCodeSystemName);

	/**
	 * Find AuditLablog by eventDisplayName
	 */
	public List<AuditLablog> findByEventDisplayName(String eventDisplayName);

	/**
	 * Find AuditLablog by eventOutcomeIndicator
	 */
	public List<AuditLablog> findByEventOutcomeIndicator(Long eventOutcomeIndicator);

	/**
	 * Find AuditLablog by eventActionCode
	 */
	public List<AuditLablog> findByEventActionCode(String eventActionCode);

	/**
	 * Find AuditLablog by eventCode
	 */
	public List<AuditLablog> findByEventCode(String eventCode);

	/**
	 * Find AuditLablog by eventDateTime
	 */
	public List<AuditLablog> findByEventDateTime(Timestamp eventDateTime);

	/**
	 * Find AuditLablog by auditSourceID
	 */
	public List<AuditLablog> findByAuditSourceID(String auditSourceID);

	/**
	 * Find AuditLablog by auditEnterpriseSiteID
	 */
	public List<AuditLablog> findByAuditEnterpriseSiteID(String auditEnterpriseSiteID);

	/**
	 * Find AuditLablog by networkAccessPointID
	 */
	public List<AuditLablog> findByNetworkAccessPointID(String networkAccessPointID);

	/**
	 * Find AuditLablog by userName
	 */
	public List<AuditLablog> findByUserName(String userName);

	/**
	 * Find AuditLablog by message
	 */
	public List<AuditLablog> findByMessage(byte[] message);

}