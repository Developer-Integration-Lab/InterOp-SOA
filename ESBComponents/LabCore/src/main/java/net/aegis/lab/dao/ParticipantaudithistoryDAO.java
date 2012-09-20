package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Participantaudithistory;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Participantaudithistorys</p>
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ParticipantaudithistoryDAO extends GenericDao<Participantaudithistory,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildParticipantaudithistoryDAO()
	 */
	 
	/**
 	 * Find Participantaudithistory by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Participantaudithistory> findByCriteria(Integer participantId, String participantName, Integer userId, String communityId, String ipAddress, Integer nhinRepId, String contactName, String contactPhone, String contactEmail, String initiatorInd, String reposnderInd, String ssnHandlingInd, Timestamp auditcreatetime, String auditcreateuser, Timestamp auditchangedtime, String auditchangeduser, Timestamp createtime, String createuser);
	 
	/**
 	 * Find Participantaudithistory by criteria.
	 */
	public List<Participantaudithistory> findByCriteria(Map criterias);

	 
	/**
	 * Find Participantaudithistory by participantId
	 */
	public List<Participantaudithistory> findByParticipantId(Integer participantId);

	/**
	 * Find Participantaudithistory by participantName
	 */
	public List<Participantaudithistory> findByParticipantName(String participantName);

	/**
	 * Find Participantaudithistory by userId
	 */
	public List<Participantaudithistory> findByUserId(Integer userId);

	/**
	 * Find Participantaudithistory by communityId
	 */
	public List<Participantaudithistory> findByCommunityId(String communityId);

	/**
	 * Find Participantaudithistory by ipAddress
	 */
	public List<Participantaudithistory> findByIpAddress(String ipAddress);

	/**
	 * Find Participantaudithistory by nhinRepId
	 */
	public List<Participantaudithistory> findByNhinRepId(Integer nhinRepId);

	/**
	 * Find Participantaudithistory by contactName
	 */
	public List<Participantaudithistory> findByContactName(String contactName);

	/**
	 * Find Participantaudithistory by contactPhone
	 */
	public List<Participantaudithistory> findByContactPhone(String contactPhone);

	/**
	 * Find Participantaudithistory by contactEmail
	 */
	public List<Participantaudithistory> findByContactEmail(String contactEmail);

	/**
	 * Find Participantaudithistory by initiatorInd
	 */
	public List<Participantaudithistory> findByInitiatorInd(String initiatorInd);

	/**
	 * Find Participantaudithistory by reposnderInd
	 */
	public List<Participantaudithistory> findByReposnderInd(String reposnderInd);

	/**
	 * Find Participantaudithistory by ssnHandlingInd
	 */
	public List<Participantaudithistory> findBySsnHandlingInd(String ssnHandlingInd);

	/**
	 * Find Participantaudithistory by auditcreatetime
	 */
	public List<Participantaudithistory> findByAuditcreatetime(Timestamp auditcreatetime);

	/**
	 * Find Participantaudithistory by auditcreateuser
	 */
	public List<Participantaudithistory> findByAuditcreateuser(String auditcreateuser);

	/**
	 * Find Participantaudithistory by auditchangedtime
	 */
	public List<Participantaudithistory> findByAuditchangedtime(Timestamp auditchangedtime);

	/**
	 * Find Participantaudithistory by auditchangeduser
	 */
	public List<Participantaudithistory> findByAuditchangeduser(String auditchangeduser);

	/**
	 * Find Participantaudithistory by createtime
	 */
	public List<Participantaudithistory> findByCreatetime(Timestamp createtime);

	/**
	 * Find Participantaudithistory by createuser
	 */
	public List<Participantaudithistory> findByCreateuser(String createuser);

}