package net.aegis.lab.auditsummary.service;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.exception.ServiceException;
/**
 *
 * @author Ram Ghattu
 */
public interface AuditSummaryService {

    public Integer create(Auditsummary auditSummary) throws ServiceException;
    public void update(Auditsummary auditSummary) throws ServiceException;
    public Auditsummary read(Integer id) throws ServiceException;
    public void delete(Auditsummary persistentObject) throws ServiceException;
    public void deleteById(Integer id) throws ServiceException;
    public List<Auditsummary> findByUserId(String userId) throws ServiceException;
    public Auditsummary findBySummaryId(Integer summaryId) throws ServiceException;
    public List<Auditsummary> findByRepositoryId(Integer repoId) throws ServiceException;
    public List<Auditsummary> findByRepositoryIdAndTestharnessId(Integer repoId, Integer testharnessId) throws ServiceException;
    public List<Auditsummary> findAuditLogsByPatient(String patientId) throws ServiceException;
    public int findByMaxRepositoryId(int testharnessId) throws ServiceException;
    public List<Auditsummary> findByResultId(int resultId) throws ServiceException;
    public List<Integer> findUniqueCaseResults();
    public List<Auditsummary> findAll() throws ServiceException;
    public void deleteAll() throws ServiceException;
    public List<Auditsummary> getAuditSummaryByScenarioExecIdAndCaseName(Scenarioexecution scenarioExec, String caseName) throws ServiceException;
    public List<Auditsummary> getAuditSummaryByExecuteTimeRange(Timestamp endTime, Timestamp startTime) throws ServiceException;

}
