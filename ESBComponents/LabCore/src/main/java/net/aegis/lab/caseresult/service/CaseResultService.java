package net.aegis.lab.caseresult.service;

import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Ram Ghattu
 */
public interface CaseResultService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Caseresult newInstance) throws ServiceException;

    public Caseresult read(Integer id) throws ServiceException;

    public void update(Caseresult transientObject) throws ServiceException;

    public void delete(Caseresult persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;


    /*
     * Finder methods
     */
    public Caseresult findByResultId(int resultId) throws ServiceException;

    /**
     * This is not a reliable way to get latest case result as we sometimes have two records with the same executedTime (tracked up to the second only)
     * for the same caseExecutionId.  Use findByExecIdAndMaxResultId instead
     *
     * @deprecated
     */
    public Caseresult findByExecIdAndMaxExecTime(int caseExecutionId) throws ServiceException;

    public Caseresult findByExecIdAndMaxResultId(int caseExecutionId) throws ServiceException;

    public Integer saveCaseResultAndSummary(Caseresult caseResult, Auditsummary auditSummary) throws ServiceException;

    public void updateCaseResultAndSaveSummary(Caseresult caseResult, Auditsummary auditSummary) throws ServiceException;

    public Caseresult findByParticipantForActivePass(int participantId, String initiatorInd, String responderInd) throws ServiceException;

    public Caseresult findByParticipantForActivePassPD(int participantId, String initiatorInd, String responderInd) throws ServiceException;

    public Caseresult findByParticipantForActivePassQD(int participantId, String initiatorInd, String responderInd) throws ServiceException;

    public Caseresult findByParticipantForActivePassRD(int participantId, String initiatorInd, String responderInd) throws ServiceException;

	Caseresult findMaxCaseresultForTestCase(Integer participantId, Integer scenarioId, Integer caseId) throws ServiceException;

}
