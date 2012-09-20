package net.aegis.ri.auditrepo.dao;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.genericdao.GenericDao;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;

/**
 * <p>Hibernate DAO layer for Auditrepositorys</p>
 * <p>Generated at Wed Feb 10 20:24:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface AuditrepositoryDAO extends GenericDao<Auditrepository, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildAuditrepositoryDAO()
     */
    /**
     * Find Auditrepository by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Auditrepository> findByCriteria(Timestamp timestamp, Long eventId, String userId, Short participationTypeCode, Short participationTypeCodeRole, String participationIDTypeCode, String receiverPatientId, String senderPatientId, String communityId, String messageType, byte[] message);

    /**
     * Find Auditrepository by timestamp
     */
    public List<Auditrepository> findByTimestamp(Timestamp timestamp);

    /**
     * Find Auditrepository by eventId
     */
    public List<Auditrepository> findByEventId(Long eventId);

    /**
     * Find Auditrepository by userId
     */
    public List<Auditrepository> findByUserId(String userId);

    /**
     * Find Auditrepository by participationTypeCode
     */
    public List<Auditrepository> findByParticipationTypeCode(Short participationTypeCode);

    /**
     * Find Auditrepository by participationTypeCodeRole
     */
    public List<Auditrepository> findByParticipationTypeCodeRole(Short participationTypeCodeRole);

    /**
     * Find Auditrepository by participationIDTypeCode
     */
    public List<Auditrepository> findByParticipationIDTypeCode(String participationIDTypeCode);

    /**
     * Find Auditrepository by receiverPatientId
     */
    public List<Auditrepository> findByReceiverPatientId(String receiverPatientId);

    /**
     * Find Auditrepository by senderPatientId
     */
    public List<Auditrepository> findBySenderPatientId(String senderPatientId);

    /**
     * Find Auditrepository by communityId
     */
    public List<Auditrepository> findByCommunityId(String communityId);

    /**
     * Find Auditrepository by messageType
     */
    public List<Auditrepository> findByMessageType(String messageType);

    /**
     * Find Auditrepository by message
     */
    public List<Auditrepository> findByMessage(byte[] message);

    /**
     * Find all Auditrepository greater than id
     */
    public List<Auditrepository> findAll(int id);

    /**
     * Find all Auditrepository
     */
    public List<Auditrepository> findAllAuditRepo();

    /**
     * Find Auditrepository by participant HCID and timestamp range
     */
    public List<Auditrepository> findByParticipantTimeRange(Timestamp startTime, Timestamp endTime, String communityId);

    /**
     * Find Auditrepository by timestamp range
     */
    public List<Auditrepository> findByTimeRange(Timestamp endTime, Timestamp startTime);

}
