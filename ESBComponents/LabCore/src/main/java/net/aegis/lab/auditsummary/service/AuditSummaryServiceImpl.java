package net.aegis.lab.auditsummary.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.caseexecution.service.CaseExecutionService;
import net.aegis.lab.dao.AuditsummaryDAO;
import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ram Ghattu
 */
@Service("auditSummaryService")
public class AuditSummaryServiceImpl implements AuditSummaryService {

    @Autowired
    private AuditsummaryDAO auditSummaryDao;
    @Autowired
    private CaseExecutionService caseExecutionService;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(AuditSummaryServiceImpl.class);

    @Override
    public List<Auditsummary> findByUserId(String userId) throws ServiceException {
        log.info("findByUserId......");
        List<Auditsummary> auditSummaries = null;
        try {
            auditSummaries = auditSummaryDao.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return auditSummaries;
    }

    @Override
    public Auditsummary findBySummaryId(Integer summaryId) throws ServiceException {
        Auditsummary auditSummary = null;
        log.info("findBySummaryId......");
        try {
            auditSummary = auditSummaryDao.read(summaryId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return auditSummary;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Auditsummary auditSummary) throws ServiceException {
        log.info("create......");
        try {
            return auditSummaryDao.create(auditSummary);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("deleteById......");
        try {
            auditSummaryDao.delete(findBySummaryId(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Auditsummary persistentObject) throws ServiceException {
        log.info("delete......");
        try {
            auditSummaryDao.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public Auditsummary read(Integer id) throws ServiceException {
        log.info("read......");
        Auditsummary auditSummary = null;
        try {
            auditSummary = auditSummaryDao.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return auditSummary;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Auditsummary auditSummary) throws ServiceException {
        log.info("update......");
        try {
            auditSummaryDao.update(auditSummary);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public List<Auditsummary> findAuditLogsByPatient(String patientId) throws ServiceException {
        log.info("findAuditLogsByPatient......");
        List<Auditsummary> auditSummaries = null;
        try {
            auditSummaries = auditSummaryDao.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return auditSummaries;
    }

    @Override
    public List<Auditsummary> findByRepositoryId(Integer repoId) throws ServiceException {
        log.info("findByRepositoryId......");
        List<Auditsummary> auditSummaries = null;
        try {
            auditSummaries = auditSummaryDao.findByRepositoryId(repoId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return auditSummaries;
    }

    @Override
    public int findByMaxRepositoryId(int testharnessId) throws ServiceException{
        int repoId = -1;
        List<Auditsummary> auditSummaryList = auditSummaryDao.findByMaxRepositoryId(testharnessId);
        for (Auditsummary summary : auditSummaryList) {
            repoId = summary.getRepositoryId();
        }
        return repoId;
    }

    @Override
    public List<Integer> findUniqueCaseResults() {
        List<Integer> resultIds = new ArrayList<Integer>();
        for (Caseresult result : auditSummaryDao.findByDistinctResultId()) {
            resultIds.add(result.getResultId());
        }
        return resultIds;
    }

    @Override
    public List<Auditsummary> findAll() throws ServiceException {
        return auditSummaryDao.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteAll() throws ServiceException {
        for (Auditsummary auditSummary : findAll()) {
            this.deleteById(auditSummary.getSummaryId());
        }
    }

    @Override
    public List<Auditsummary> findByResultId(int resultId) throws ServiceException {
        return auditSummaryDao.findByResultId(resultId);
    }

    @Override
    public List<Auditsummary> getAuditSummaryByScenarioExecIdAndCaseName(Scenarioexecution scenarioExec, String caseName) throws ServiceException {

        List<Auditsummary> objIndividualAuditSummaryRecords = null;
        Caseresult objCaseResultToGetCumulativeAuditSummaryFor = null;
        List<Auditsummary> cumlativeAuditSummayList = new ArrayList<Auditsummary>();
        try {
            Caseexecution caseExecutions = caseExecutionService.getCaseExecByScenarioExecIdAndCaseName(scenarioExec, caseName);
            if(caseExecutions != null) {
                log.debug(">>>>>>>AuditSummaryServiceImpl.getAuditSummaryByScenarioExecIdAndCaseName>>>>>>>>>>>>>>>>"+caseExecutions.getCaseExecutionId()  +"**********************");
                List<Caseresult> objAllCaseResults = caseExecutions.getCaseresults();

                // *************************************************************
                // Now that we have ALL the case results for a given
                // scenario execution+ case execution combination, pick up
                // the audit summary logs of *only* the latest case result.
                // *************************************************************
                java.sql.Timestamp objTimestampToCompareWith = null;

                if ((null != objAllCaseResults) && (objAllCaseResults.size() >= 1)) {
                    if (objAllCaseResults.size() == 1) {
                        // The first-time case execution and case result can yield
                        // only one case result record.  That's the ONLY case result
                        // to get cumulative audit summary for.
                        objCaseResultToGetCumulativeAuditSummaryFor = objAllCaseResults.get(0);
                        log.info("AuditSummaryServiceImpl.getAuditSummaryByScenarioExecIdAndCaseName(): objAllCaseResults.size()=" + objAllCaseResults.size());
                    }
                    else {
                        // Now, certainly there are more than 1 case results.
                        // The latest case result and its audit summary need to
                        // be found out.

                        // get the timestamp of the first case result for further comparison.
                        if ((null != objAllCaseResults) && (null != objAllCaseResults.get(0))) {
                            objTimestampToCompareWith = objAllCaseResults.get(0).getExecuteTime();
                        }
                        // compare the timestamps of the case results one by one.
                        if (null != objAllCaseResults) {
                            for (Caseresult objTmpCR : objAllCaseResults) {
                                if (null != objTmpCR) {
                                    Timestamp executeTime = objTmpCR.getExecuteTime();
									if (executeTime!=null && objTimestampToCompareWith!=null && executeTime.getTime() > objTimestampToCompareWith.getTime()) {
                                        objCaseResultToGetCumulativeAuditSummaryFor = objTmpCR;     // keep comparing timestamp until we find the latest case result.
                                        objTimestampToCompareWith = executeTime;
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    SimpleDateFormat formatter =  new SimpleDateFormat("yyyyMMdd.HHmmss");
                    String strTimeNow = formatter.format(new java.util.Date().getTime());
                    String strInfoForReference = "AuditSummaryServiceImpl.getAuditSummaryByScenarioExecIdAndCaseName(): Case results are not available yet. ";
                    if (null != scenarioExec) {
                        strInfoForReference += "Scenario Execution ID queried for="+scenarioExec.getScenarioExecutionId()+", ";
                    }
                    if (null != caseName) {
                        strInfoForReference += "Case name queried for="+caseName+", ";
                    }
                    strInfoForReference += "Time now=" +strTimeNow;
                    log.info(strInfoForReference);
                }

                // now that the latest case result is figured out, get its
                // audit summary records.
                if(null != objCaseResultToGetCumulativeAuditSummaryFor) {
                    log.info("AuditSummaryServiceImpl.getAuditSummaryByScenarioExecIdAndCaseName(): objCaseResultToGetCumulativeAuditSummaryFor.getResultId() = " + objCaseResultToGetCumulativeAuditSummaryFor.getResultId());
                    objIndividualAuditSummaryRecords = objCaseResultToGetCumulativeAuditSummaryFor.getAuditsummarys();
                    if (null != objIndividualAuditSummaryRecords) {
                        for (Auditsummary objTmpAS : objIndividualAuditSummaryRecords) {
                            cumlativeAuditSummayList.add(objTmpAS);
                        }
                    }
                }
                else {
                    log.info("AuditSummaryServiceImpl.getAuditSummaryByScenarioExecIdAndCaseName(): objCaseResultToGetCumulativeAuditSummaryFor is null. - INFO");
                }

                /*
                log.debug("caseResults+*******"+caseResults);
                for (Caseresult caseResult : caseResults) {
                    auditSummay = caseResult.getAuditsummarys();
                    log.debug("auditSummay********"+auditSummay);
                    for (Auditsummary auditSummary : auditSummay) {
                        cumlativeAuditSummayList.add(auditSummary);
                    }
                }
                */
          }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return cumlativeAuditSummayList;
    }

    @Override
    public List<Auditsummary> getAuditSummaryByExecuteTimeRange(Timestamp endTime, Timestamp startTime) throws ServiceException {
        return auditSummaryDao.findByExecuteTimeRange(endTime, startTime);
    }

    @Override
    public List<Auditsummary> findByRepositoryIdAndTestharnessId(Integer repoId, Integer testharnessId) throws ServiceException {
            log.info("start AuditSummaryServiceImpl.findByRepositoryIdAndTestharnessId() - INFO");
            List<Auditsummary> auditsummaryList = new ArrayList<Auditsummary>();
            try {
                Auditsummary auditSummary = new Auditsummary();
                auditSummary.setRepositoryId(repoId);
                Testharnessri testharness = new Testharnessri();
                testharness.setTestharnessId(testharnessId);
                auditSummary.setTestharnessri(testharness);
                List<Criterion> criterionList = new ArrayList<Criterion>();
                criterionList.add(Restrictions.eq("repositoryId", repoId));
                criterionList.add(Restrictions.eq("testharnessri.testharnessId", testharnessId));
                auditsummaryList = auditSummaryDao.searchByCriteria(auditSummary, criterionList);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ServiceException(ex , ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            }
            log.info("end AuditSummaryServiceImpl.findByRepositoryIdAndTestharnessId() - INFO audit summary list size "+auditsummaryList.size());
            return auditsummaryList;
        }    
}
