package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Auditsummarys</p>
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface AuditsummaryDAO extends GenericDao<Auditsummary, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildAuditsummaryDAO()
     */
    /**
     * Find Auditsummary by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Auditsummary> findByCriteria(Long repositoryId, Integer testharnessId, Integer resultId, Integer participantId, String type, String typeCode, Integer outcomeIndicator, String userId, String userName, String messageType, String actionCode, String networkAccessPointId, String patientId, String enterpriseSourceSite, String enterpriseSourceId, Timestamp executeTime, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);

    /**
     * Find Auditsummary by criteria.
     */
    public List<Auditsummary> findByCriteria(Map criterias);

    /**
     * Find Auditsummary by repositoryId
     */
    public List<Auditsummary> findByRepositoryId(Integer repositoryId);

    /**
     * Find Auditsummary by testharnessId
     */
    public List<Auditsummary> findByTestharnessId(Integer testharnessId);

    /**
     * Find Auditsummary by resultId
     */
    public List<Auditsummary> findByResultId(Integer resultId);

    /**
     * Find Auditsummary by participantId
     */
    public List<Auditsummary> findByParticipantId(Integer participantId);

    /**
     * Find Auditsummary by type
     */
    public List<Auditsummary> findByType(String type);

    /**
     * Find Auditsummary by typeCode
     */
    public List<Auditsummary> findByTypeCode(String typeCode);

    /**
     * Find Auditsummary by outcomeIndicator
     */
    public List<Auditsummary> findByOutcomeIndicator(Integer outcomeIndicator);

    /**
     * Find Auditsummary by userId
     */
    public List<Auditsummary> findByUserId(String userId);

    /**
     * Find Auditsummary by userName
     */
    public List<Auditsummary> findByUserName(String userName);

    /**
     * Find Auditsummary by messageType
     */
    public List<Auditsummary> findByMessageType(String messageType);

    /**
     * Find Auditsummary by actionCode
     */
    public List<Auditsummary> findByActionCode(String actionCode);

    /**
     * Find Auditsummary by networkAccessPointId
     */
    public List<Auditsummary> findByNetworkAccessPointId(String networkAccessPointId);

    /**
     * Find Auditsummary by patientId
     */
    public List<Auditsummary> findByPatientId(String patientId);

    /**
     * Find Auditsummary by enterpriseSourceSite
     */
    public List<Auditsummary> findByEnterpriseSourceSite(String enterpriseSourceSite);

    /**
     * Find Auditsummary by enterpriseSourceId
     */
    public List<Auditsummary> findByEnterpriseSourceId(String enterpriseSourceId);

    /**
     * Find Auditsummary by executeTime
     */
    public List<Auditsummary> findByExecuteTime(Timestamp executeTime);

    /**
     * Find Auditsummary by createtime
     */
    public List<Auditsummary> findByCreatetime(Timestamp createtime);

    /**
     * Find Auditsummary by createuser
     */
    public List<Auditsummary> findByCreateuser(String createuser);

    /**
     * Find Auditsummary by changedtime
     */
    public List<Auditsummary> findByChangedtime(Timestamp changedtime);

    /**
     * Find Auditsummary by changeduser
     */
    public List<Auditsummary> findByChangeduser(String changeduser);

    /**
     * Find Auditsummary by repositoryId
     */
    public List<Auditsummary> findByMaxRepositoryId(int testharnessId);

    /**
     * Find Case results by resultId
     */
    public List<Caseresult> findByDistinctResultId();

    /**
     * Find all Auditsummaries
     */
    public List<Auditsummary> findAll();

    /**
     * Find Auditsummary by executeTime range
     */
    public List<Auditsummary> findByExecuteTimeRange(Timestamp endTime, Timestamp startTime);

}
