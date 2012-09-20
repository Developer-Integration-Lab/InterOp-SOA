package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Caseexecutions</p>
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface CaseexecutionDAO extends GenericDao<Caseexecution, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildCaseexecutionDAO()
     */
    /**
     * Find Caseexecution by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Caseexecution> findByCriteria(Integer scenarioExecutionId, Integer caseId, String patientId, String status, String messageType);

    /**
     * Find Caseexecution by criteria.
     */
    public List<Caseexecution> findByCriteria(Map criterias);

    /**
     * Find Caseexecution by scenarioExecutionId
     */
    public List<Caseexecution> findByScenarioExecutionId(int scenarioExecutionId);

    /**
     * Find Caseexecution by caseId
     */
    public List<Caseexecution> findByCaseId(Integer caseId);

    /**
     * Find Caseexecution by patientId
     */
    public List<Caseexecution> findByPatientId(String patientId);

    /**
     * Find Caseexecution by status
     */
    public List<Caseexecution> findByStatus(String status);

    /**
     * Find Caseexecution by messageType
     */
    public List<Caseexecution> findByMessageType(String messageType);

    /**
     * Find Caseexecution by Participant and Active
     */
    public List<Object[]> findByParticipantForActive(Integer participantId);

    /**
     * Find Max start timer in Caseexecution
     */
    public java.sql.Timestamp findByMaxStartTimer();
    
    public List<Object[]> findByParticipantIdAndCommIdAndDocUniqueId(Integer participantId , String communityId,String documentUniqueId);
    
}
