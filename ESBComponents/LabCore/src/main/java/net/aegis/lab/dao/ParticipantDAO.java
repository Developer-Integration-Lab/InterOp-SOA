package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.genericdao.GenericDao;
import net.aegis.lab.util.LabConstants.LabType;
/**
 * <p>Hibernate DAO layer for Participants</p>
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ParticipantDAO extends GenericDao<Participant,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildParticipantDAO()
	 */
	 
	/**
 	 * Find Participant by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Participant> findByCriteria(String participantName, Integer userId, String communityId, String ipAddress, Integer nhinRepId, String contactName, String contactPhone, String contactEmail, String initiatorInd, String responderInd, String ssnHandlingInd, String status, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser,String resouceIdSendInd);
	 
	/**
 	 * Find Participant by criteria.
	 */
	public List<Participant> findByCriteria(Map criterias);

	 
	/**
	 * Find Participant by participantName
	 */
	public List<Participant> findByParticipantName(String participantName);

	/**
	 * Find Participant by userId
	 */
	public List<Participant> findByUserId(Integer userId);

	/**
	 * Find Participant by communityId
         * @param cId
         * @param labType
         * @return
         */
        public List<Participant> findByCommunityId(String cId, LabType labType);


	/**
	 * Find Participant by communityId
	 */
	public List<Participant> findByAssigningAuthorityId(String assigningAuthorityId);

	/**
	 * Find Participant by ipAddress
	 */
	public List<Participant> findByIpAddress(String ipAddress);

	/**
	 * Find Participant by nhinRepId
	 */
	public List<Participant> findByNhinRepId(Integer nhinRepId);

	/**
	 * Find Participant by nhinRepId and status
	 */
        public List<Participant> findByNhinRepIdAndStatus(Integer nhinRepId, String pstrStatus);

	/**
	 * Find Participant by contactName
	 */
	public List<Participant> findByContactName(String contactName);

	/**
	 * Find Participant by contactPhone
	 */
	public List<Participant> findByContactPhone(String contactPhone);

	/**
	 * Find Participant by contactEmail
	 */
	public List<Participant> findByContactEmail(String contactEmail);

	/**
	 * Find Participant by initiatorInd
	 */
	public List<Participant> findByInitiatorInd(String initiatorInd);

	/**
	 * Find Participant by responderInd
	 */
	public List<Participant> findByResponderInd(String responderInd);

	/**
	 * Find Participant by ssnHandlingInd
	 */
	public List<Participant> findBySsnHandlingInd(String ssnHandlingInd);

	/**
	 * Find Participant by status
	 */
	public List<Participant> findByStatus(String status);

	/**
	 * Find Participant by createtime
	 */
	public List<Participant> findByCreatetime(Timestamp createtime);

	/**
	 * Find Participant by createuser
	 */
	public List<Participant> findByCreateuser(String createuser);

	/**
	 * Find Participant by changedtime
	 */
	public List<Participant> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Participant by changeduser
	 */
	public List<Participant> findByChangeduser(String changeduser);

        /**
	 * Find Participant by community id and ip address
	 */
        public List<Participant> findByCommunityIdAndIpAddress(String communityId,String ipAddress);

        /**
	 * Find Participant by resouceIdSendInd
	 */
	public List<Participant> findByResouceIdSendInd(String resouceIdSendInd);
 
}