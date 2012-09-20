package net.aegis.lab.manager;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.auditsummary.service.AuditSummaryService;
import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.scenarioexecution.service.ScenarioExecutionService;
import net.aegis.lab.servicesetexecution.service.ServiceSetExecutionService;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants.LabType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author richard.ettema
 */
public class ServiceSetExecutionManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);
    private static ServiceSetExecutionManager instance;
    private ServiceSetExecutionService serviceSetExecutionService = (ServiceSetExecutionService) ContextUtil.getLabContext().getBean("serviceSetExecutionService");
    private ScenarioExecutionService scenarioExecutionService = (ScenarioExecutionService) ContextUtil.getLabContext().getBean("scenarioExecutionService");
    private AuditSummaryService auditSummaryService = (AuditSummaryService) ContextUtil.getLabContext().getBean("auditSummaryService");

    private ServiceSetExecutionManager() {
    }

    /**
     * @return ServiceSetExecutionManager
     */
    public static ServiceSetExecutionManager getInstance() {
        if (instance == null) {
            instance = new ServiceSetExecutionManager();
        }
        return instance;
    }

    /**
     * @return List<Servicesetexecution>
     */
    public List<Servicesetexecution> participantActiveServiceSets(Integer participantId) throws ServiceException {
        return participantActiveServiceSets(participantId, null);
    }

    /**
     * @return List<Servicesetexecution>
     */
    public List<Servicesetexecution> participantActiveServiceSets(Integer participantId, LabType labType) throws ServiceException {
        log.info("ServiceSetExecutionManager.participantActiveServiceSets() - INFO");

        List<Servicesetexecution> results = null;

        try {
            if (labType == null) {
                // If labType is null, attempt to retrieve each labType until results are found
                results = serviceSetExecutionService.findActiveByParticipantId(participantId, LabType.CONFORMANCE);
                if (results == null || results.isEmpty()) {
                    results = serviceSetExecutionService.findActiveByParticipantId(participantId, LabType.LAB);
                }
            }
            else {
                // Retrieve specified labType results
                results = serviceSetExecutionService.findActiveByParticipantId(participantId, labType);
            }

            if (results != null && results.size() > 0) {
                // Populate the Caseexecutions for each participant's active service set
                for (Servicesetexecution setExecution : results) {
                    List<Caseexecution> caseExecutions = this.getServiceSetCaseExecutionsByExecUniqueId(setExecution.getExecutionUniqueId());
                    setExecution.setCaseexecutions(caseExecutions);
                }
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding active service sets.", se);
            se.setErrorCode("errors.active.service.sets.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding active service sets.", e);
            ServiceException se = new ServiceException("Failure active service sets", "errors.active.service.sets.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }

    /**
     * Purpose: Find service set execution(s) given a participant id and execution status.
     *
     * @param participantId          Participant that the service set executions belong to.
     * @param pstrStatus            status of the service set. For ex - Submitted
     * @return all the service set executions that belong to the participant and those whose status matches with the given status.
     * @throws ServiceException
     */
    public List<Servicesetexecution> getServiceSetExecutionsByStatus(Integer participantId, String pstrStatus) throws ServiceException {
        log.info("ServiceSetExecutionManager.getServiceSetExecutionsByStatus(2) - INFO");
        return getServiceSetExecutionsByStatus(participantId, pstrStatus, null);
    }

    /**
     * Purpose: Find service set execution(s) given a participant id, execution status, and labType.
     * @param participantId
     * @param pstrStatus
     * @param labType
     * @return
     * @throws ServiceException
     */
    public List<Servicesetexecution> getServiceSetExecutionsByStatus(Integer participantId, String pstrStatus, LabType labType) throws ServiceException {
        log.info("ServiceSetExecutionManager.getServiceSetExecutionsByStatus(3) - INFO");

        List<Servicesetexecution> results = null;

        try {
            results = serviceSetExecutionService.findByParticipantIdAndStatus(participantId, pstrStatus, labType);
            if (results != null && results.size() > 0) {
                // Populate the Caseexecutions for each participant's active service set
                for (Servicesetexecution setExecution : results) {
                    List<Caseexecution> caseExecutions = this.getServiceSetCaseExecutionsByExecUniqueId(setExecution.getExecutionUniqueId());
                    setExecution.setCaseexecutions(caseExecutions);
                }
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding service set executions by participant id and status.", se);
            se.setErrorCode("errors.status.service.sets.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding service set executions by participant id and status.", e);
            ServiceException se = new ServiceException("Failure finding service set executions by participant id and status", "errors.status.service.sets.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }

    /**
     * Purpose: Participants should never be assigned to an NHIN Validating Body user; 
     *          only to the NHIN Representative.  The NHIN Validating Body's 
     *          dashboard should show the assigned submissions.
     * 
     * @param pstrStatus                    status to query for
     * @return List<Servicesetexecution>    all matching service set executions
     * @throws ServiceException
     */
    public List<Servicesetexecution> getAllServiceSetExecutionsByStatus(String pstrStatus) throws ServiceException {
        log.info("ServiceSetExecutionManager.getAllServiceSetExecutionsByStatus() - INFO");

        List<Servicesetexecution> results = null;

        try {
            results = serviceSetExecutionService.getAllServiceSetExecutionsByStatus(pstrStatus);
            if (results != null && results.size() > 0) {
                // Populate the Caseexecutions for each participant's active service set
                for (Servicesetexecution setExecution : results) {
                    List<Caseexecution> caseExecutions = this.getServiceSetCaseExecutionsByExecUniqueId(setExecution.getExecutionUniqueId());
                    setExecution.setCaseexecutions(caseExecutions);
                }
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding service set executions by participant id and status.", se);
            se.setErrorCode("errors.status.service.sets.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding service set executions by participant id and status.", e);
            ServiceException se = new ServiceException("Failure finding service set executions by participant id and status", "errors.status.service.sets.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }


    public void startServiceSetExecution(List<Servicesetexecution> needToBeActiveServiceSets) throws ServiceException {
        log.info("ServiceSetExecutionManager.closeServiceSetExecution() - INFO");
        try {
            serviceSetExecutionService.startServiceSetExecution(needToBeActiveServiceSets);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException("Failure in start service set Executions", ex);
        }

    }

    public void closeServiceSetExecution(List<Servicesetexecution> needTobeClosedServiceSets) throws ServiceException {
        log.info("ServiceSetExecutionManager.closeServiceSetExecution() - INFO");
        try {
            serviceSetExecutionService.closeServiceSetExecution(needTobeClosedServiceSets);
        } catch (Exception ex) {
            throw new ServiceException("Failure to close service set Executions", ex);
        }
    }

    public List<Caseexecution> getServiceSetCaseExecutionsByExecUniqueId(String executionUniqueId) throws ServiceException {
        List<Caseexecution> results = new ArrayList<Caseexecution>();

        Scenarioexecution criteria = new Scenarioexecution();
        List<Criterion> criterionList = new ArrayList<Criterion>();
        criterionList.add(Restrictions.like("executionUniqueId", executionUniqueId, MatchMode.START));

        List<Scenarioexecution> scenarioExecutions = scenarioExecutionService.searchByCriteria(criteria, criterionList);

        if (scenarioExecutions != null && scenarioExecutions.size() > 0) {
            for (Scenarioexecution scenarioExecution : scenarioExecutions) {
                if (scenarioExecution.getCaseexecutions() != null) {
                    results.addAll(scenarioExecution.getCaseexecutions());
                }
            }
        }

        return results;
    }

    public List<Auditsummary> getAuditSummariesByScenarioExecIdAndCaseName(Scenarioexecution scenarioExec, String caseName) throws ServiceException {
        List<Auditsummary> auditSummaries = null;
        try {
            auditSummaries = auditSummaryService.getAuditSummaryByScenarioExecIdAndCaseName(scenarioExec, caseName);
        } catch (Exception ex) {
            throw new ServiceException("Failure to get AuditSummaries", ex);
        }
        return auditSummaries;
    }

    public Servicesetexecution findBySetExecutionId(Integer pExecutionUniqueId) throws ServiceException {

        log.info("ServiceSetExecutionManager.findBySetExecutionId() - INFO");

        Servicesetexecution sse = null;

        // now, invoke the service layer code to find the service set execution
        sse = serviceSetExecutionService.findBySetExecutionId(pExecutionUniqueId);

        return sse;
    }

    public List<Servicesetexecution> findByExecutionUniqueId(String pExecutionUniqueId) throws ServiceException {

        log.info("ServiceSetExecutionManager.findByExecutionUniqueId() - INFO");

        List<Servicesetexecution> sse = null;       // note - there will be only one in DB !!

        // now, invoke the service layer code to find the service set execution
        sse = serviceSetExecutionService.findByExecutionUniqueId(pExecutionUniqueId);

        if(null != sse) {
            int iNumServiceSetExecutions = -1;      // note - there will be only one in DB !!
            iNumServiceSetExecutions = sse.size();
            if (iNumServiceSetExecutions > 1) {
                log.error("ServiceSetExecutionManager.findByExecutionUniqueId() - Bad results obtained. Multiple service set executions in database for ExecutionUniqueId - ERROR");
                ServiceException se = new ServiceException("ServiceSetExecutionManager.findByExecutionUniqueId() - Bad results obtained. Multiple service set executions in database for ExecutionUniqueId.");
                se.setLogged();
                throw se;                
            }
            log.info("ServiceSetExecutionManager.findByExecutionUniqueId() - number of service set executions found in database: "+iNumServiceSetExecutions);
        }

        return sse;
    }
}
