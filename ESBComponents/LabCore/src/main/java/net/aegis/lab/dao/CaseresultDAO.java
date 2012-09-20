package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Caseresults</p>
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface CaseresultDAO extends GenericDao<Caseresult, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildCaseresultDAO()
     */
    /**
     * Find Caseresult by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Caseresult> findByCriteria(Integer caseExecutionId, Timestamp executeTime, String result, String submissionInd, Integer validationId);

    /**
     * Find Caseresult by criteria.
     */
    public List<Caseresult> findByCriteria(Map criterias);

    /**
     * Find Caseresult by caseExecutionId
     */
    public List<Caseresult> findByCaseExecutionId(Integer caseExecutionId);

    /**
     * Find Caseresult by executeTime
     */
    public List<Caseresult> findByExecuteTime(Timestamp executeTime);

    /**
     * Find Caseresult by result
     */
    public List<Caseresult> findByResult(String result);

    /**
     * Find Caseresult by submissionInd
     */
    public List<Caseresult> findBySubmissionInd(String submissionInd);

    /**
     *
     * Find Caseresult by findByExecIdAndMaxExecTime
     * This is not a reliable way to get latest case result as we sometimes have two records with the same executeTime (tracked up to the second only)
     * for the same caseExecutionId.  Use findByExecIdAndMaxResultId instead
     *
     * @deprecated 
     */
    public List<Caseresult> findByExecIdAndMaxExecTime(Integer caseExecutionId);

    /**
     * Find Caseresult by ExecIdAndMaxResultId
     */
    public List<Caseresult> findByExecIdAndMaxResultId(Integer caseExecutionId);

    /**
     * Find Caseresult by findByParticipantForActivePassInitiator
     */
    public List<Object[]> findByParticipantForActivePassInitiator(Integer participantId);

    /**
     * Find Caseresult by findByParticipantForActivePassResponder
     */
    public List<Object[]> findByParticipantForActivePassResponder(Integer participantId);

    /**
     * Find Caseresult by findByParticipantForActivePassPDI
     */
    public List<Object[]> findByParticipantForActivePassPDI(Integer participantId);

    /**
     * Find Caseresult by findByParticipantForActivePassPDR
     */
    public List<Object[]> findByParticipantForActivePassPDR(Integer participantId);

    /**
     * Find Caseresult by findByParticipantForActivePassQDI
     */
    public List<Object[]> findByParticipantForActivePassQDI(Integer participantId);

    /**
     * Find Caseresult by findByParticipantForActivePassQDR
     */
    public List<Object[]> findByParticipantForActivePassQDR(Integer participantId);

    /**
     * Find Caseresult by findByParticipantForActivePassRDI
     */
    public List<Object[]> findByParticipantForActivePassRDI(Integer participantId);

    /**
     * Find Caseresult by findByParticipantForActivePassRDR
     */
    public List<Object[]> findByParticipantForActivePassRDR(Integer participantId);

	public List<Object[]> findMaxCaseresultForTestCase(
			Integer scenarioId, int participantId, Integer caseId);

}
