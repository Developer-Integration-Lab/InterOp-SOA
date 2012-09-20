/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.scenarioexecution.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.aegis.lab.dao.ScenarioexecutionDAO;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("scenarioExecutionService")
public class ScenarioExecutionServiceImpl implements ScenarioExecutionService {



    @Autowired
    private ScenarioexecutionDAO scenarioExectionDAO;
    @Autowired
    private ParticipantService participantService;
//    @Autowired
//    private ServiceSetExecutionService serviceSetExecutionService;

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ScenarioExecutionServiceImpl.class);
    /**
     * this method to create a new record of Scenarioexecution
     * @param scenarioExecution
     * @return Integer
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Scenarioexecution scenarioExecution) throws ServiceException {
        log.info("saving ScenarioExecution..");
        try {
            return scenarioExectionDAO.create(scenarioExecution);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     * This is the crud method update to update a Scenarioexecution
     * @param scenarioExecution
     * @return void
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Scenarioexecution scenarioExecution) throws ServiceException {
        log.info("updating ScenarioExecution..");
        try {
            scenarioExectionDAO.update(scenarioExecution);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     * This is the crud method delete to delete a Scenarioexecution
     * @param scenarioExecution
     * @return void
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Scenarioexecution persistentObject) throws ServiceException {
        log.info("Deleting ScenarioExecution..");
        try {
            scenarioExectionDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     * This is the crud method delete to delete a Scenarioexecution given a id
     * @param scenarioExecution
     * @return void
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("DeletingBYId ScenarioExecution..");
        try {
            scenarioExectionDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     *  This to read a Scenarioexecution based on scenarioId
     * @param scenarioId
     * @return Scenarioexecution
     * @throws ServiceException
     */
    @Override
    public Scenarioexecution read(int scenarioId) throws ServiceException {
        log.info("ScenarioExecutionServiceImpl.read() - INFO");
        Scenarioexecution scenariocase = null;
        try {
            scenariocase = scenarioExectionDAO.read(scenarioId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return scenariocase;
    }

    /**
     *  This to finder to get a Scenarioexecution based on scenarioExecutionId
     * @param scenarioExecutionId
     * @return List<Scenarioexecution>
     * @throws ServiceException
     */
    @Override
    public List<Scenarioexecution> findByScenarioExecutionId(int scenarioExecutionId) throws ServiceException {
        log.info("ScenarioExecutionServiceImpl.findByScenarioExecutionId() - INFO");
        try {
            return scenarioExectionDAO.findByScenarioExecutionId(scenarioExecutionId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     *
     * This is a finder of scenarioexecution based on participant
     * @param participantId
     * @return List<ScenarioExecution>
     * @throws ServiceException
     */
    @Override
    public List<Scenarioexecution> findByParticipantId(int participantId) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.findByParticipantId() - INFO");
        try {
            return scenarioExectionDAO.findByParticipantId(participantId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     * this a finder of Scenarioexecution based on uniqueExecutionId
     * @param uniqueExecId
     * @return List<Scenarioexection>
     * @throws ServiceException
     */
    @Override
    public List<Scenarioexecution> findByUniqueExecutionId(String uniqueExecId) throws ServiceException {
        log.info("Find by setExecutionId.................");
        List<Scenarioexecution> servicesetExecution = null;
        try {
            servicesetExecution = scenarioExectionDAO.findByExecutionUniqueId(uniqueExecId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return servicesetExecution;
    }

    /**
     * Requirement: Nhin validator, on his/her dashboard, can choose to read the comments that
     * were added to each associated scenario when a service set execution was 'submitted'
     * for validation by the associated participant.
     *
     * @param pstrUniqueExecId  parameter that will use 'like' sql clause to find associated scenario execs
     * @param pstrStatus        parameter that will find scenario execs with a given status (mainly, non-active scenario execs)
     * @return                  all matching scenario executions
     * @throws ServiceException
     */
    @Override
    public List<Scenarioexecution> findByUniqueExecutionIdAndStatus(String pstrUniqueExecId, String pstrStatus) throws ServiceException {

        log.info("ScenarioExecutionServiceImpl.findByUniqueExecutionIdAndStatus() - INFO");
        List<Scenarioexecution> scenarioexecutionResults = null;
        Scenarioexecution criteria = null;
        List<Criterion> criterionList = new ArrayList<Criterion>();

        if ((null == pstrUniqueExecId) || (null == pstrStatus)) {
            log.error("ScenarioExecutionServiceImpl.findByUniqueExecutionIdAndStatus() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ScenarioExecutionServiceImpl.findByUniqueExecutionIdAndStatus() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        criteria = new Scenarioexecution();
        criteria.setExecutionUniqueId(pstrUniqueExecId);
        criteria.setStatus(pstrStatus);

        try {
            if (criteria != null) {
                log.info("ScenarioExecutionServiceImpl.findByUniqueExecutionIdAndStatus() - Search by Criteria");
                criterionList.add(Restrictions.like("executionUniqueId", pstrUniqueExecId, MatchMode.START));
                criterionList.add(Restrictions.like("status", pstrStatus, MatchMode.START));
                scenarioexecutionResults = this.searchByCriteria(criteria, criterionList);
            }
        } catch (Exception e) {
            // throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            e.printStackTrace();
        }

        if (null != scenarioexecutionResults) {
            log.info("ScenarioExecutionServiceImpl.findByUniqueExecutionIdAndStatus(): scenarioexecutionResults.size()=" + scenarioexecutionResults.size());
        }
        return scenarioexecutionResults;
    }

    /**
     * Requirement -    For a given service set execution, the logged in nhin validator
     *                  decides whether to validate or fail the scenarios.  Optionally,
     *                  he/she can introduce validation comments.
     * 
     * @param pExecutionUniqueId            service set execution id
     * @param parrValidatedScenarios        scenarios validated by nhin validator
     * @param parrFailedScenarios           scenarios failed by nhin validator
     * @param pobjScenarioIdCommentMap      validation comments for scenarios where
     *                                      nhin validator has made a decision
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void updateScenarioExecutionsWithValidOrFailStatusAndValidationComments(
            String pExecutionUniqueId,
            String[] parrValidatedScenarios,
            String[] parrFailedScenarios,
            Map<String, String> pobjScenarioIdCommentMap)
            throws ServiceException {

        log.info("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - INFO");

        // check nominal parameter validity
        if (null == pExecutionUniqueId) {
            log.error("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Unknown service set execution id. - ERROR");
            ServiceException se = new ServiceException("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Unknown service set execution id.");
            se.setLogged();
            throw se;
        }
        // Now, the view must pass either validated scenarios or failed scenarios.
        if ((null == parrValidatedScenarios) & (null == parrFailedScenarios)) {
            log.error("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        // *********************************************************************
        // now update the scenario execution records in the database
        // *********************************************************************

        //
        // step 1 of 4: A short-cut method.  First, replace VALIDATED or FAILED
        // status' of existing scenario executions of a given servicesetexecution
        // to CLOSED.  Then, subsequent steps 2 and 3 take care of updating
        // the appropriate status' of the scenario executions selected by an
        // NHIN validator.
        //
        List<Scenarioexecution> togetherSEsForNhinValidatorPage = new ArrayList<Scenarioexecution>();
        List<Scenarioexecution> objListTmpClosedSEs = null;
        List<Scenarioexecution> objListTmpValidatedSEs = null;
        List<Scenarioexecution> objListTmpFailedSEs = null;

        objListTmpClosedSEs = this.findByUniqueExecutionIdAndStatus(pExecutionUniqueId, LabConstants.STATUS_SCENARIOEXEC_CLOSED);
        objListTmpValidatedSEs = this.findByUniqueExecutionIdAndStatus(pExecutionUniqueId, LabConstants.STATUS_SCENARIOEXEC_VALIDATED);
        objListTmpFailedSEs = this.findByUniqueExecutionIdAndStatus(pExecutionUniqueId, LabConstants.STATUS_SCENARIOEXEC_FAILED);

        for(Scenarioexecution vs : objListTmpValidatedSEs) {
            togetherSEsForNhinValidatorPage.add(vs);
        }
        for(Scenarioexecution fs : objListTmpFailedSEs) {
            togetherSEsForNhinValidatorPage.add(fs);
        }
        for(Scenarioexecution cs : objListTmpClosedSEs) {
            togetherSEsForNhinValidatorPage.add(cs);
        }
        for(Scenarioexecution seToUpdateToClosedStatusFirst : togetherSEsForNhinValidatorPage) {
            if (null != seToUpdateToClosedStatusFirst) {
                // closed status below implies a decision of VALIDATED or FAILED for a scenario execution
                // is not done by the Nhin Validator (who is logged in).
                seToUpdateToClosedStatusFirst.setStatus(LabConstants.STATUS_SCENARIOEXEC_CLOSED);   // kind of risky, but it's all one transaction.
                log.info("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - scenario execution " +
                        seToUpdateToClosedStatusFirst.getExecutionUniqueId() + " is CLOSED now temporarily.");
            }
        }

        //
        // step 2 of 4: update validated records
        //
        for (int i = 0; i < parrValidatedScenarios.length; i++) {
            String validatedScenarioId = parrValidatedScenarios[i];
            log.info("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - parrValidatedScenarios["+i+"]="+parrValidatedScenarios[i]);
            Scenarioexecution seToFind = this.read(Integer.parseInt(validatedScenarioId));
            if (null == seToFind) {
                log.error("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - invalid validated scenario id=" + validatedScenarioId + " passed. - ERROR");
                ServiceException se = new ServiceException("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - invalid validated scenario id passed.");
                se.setLogged();
                throw se;
            }
            seToFind.setStatus(LabConstants.STATUS_SCENARIOEXEC_VALIDATED);     // validated now
            this.update(seToFind);

            /*Story 89 Grant participant access to test phases based on Validation criteria*/
            if (i==0) {
                try {
                    Participant c = seToFind.getParticipant();
                    List<Servicesetexecution> sse =  null; //serviceSetExecutionService.findByExecutionUniqueId(pExecutionUniqueId);
                    if (c!=null && sse!=null && sse.size()==1 && sse.get(0).getServiceset().getLabAccessFilter()!=null
                            && ((sse.get(0).getServiceset().getLabAccessFilter().intValue() == LabConstants.LabType.CONFORMANCE.getId()))) {
                         final List<Scenarioexecution> seList = this.findByUniqueExecutionId(pExecutionUniqueId+LabConstants.LIKE_OPERATOR);
                         if (seList!=null) {
                             Integer oldLabAccessFilter  = c.getLabAccessFilter();
                             Integer newLabAccessFilter  = new Integer(LabConstants.LabType.CONFORMANCE.getId());
                             if (seList.size()==parrValidatedScenarios.length) {
                                 newLabAccessFilter = new Integer(LabConstants.LabType.CONFORMANCE.getId() | LabConstants.LabType.LAB.getId());
                                log.info("Validation has granted test phases.");
                             } else {
                                 log.info("Validation did not grant test phases; seList:" + seList.size() + "parrValid:" + parrValidatedScenarios.length);
                             }
                            if (oldLabAccessFilter!=newLabAccessFilter) {
                                c.setLabAccessFilter(newLabAccessFilter);
                                participantService.update(c);
                            }
                         }
                    }
                } catch (Exception ex) {
                    log.error("Test phases grant validation proc failed" + ex.toString());
                }
            }
             
        }



        //
        // step 3 of 4: update failed records
        //
        for (int i = 0; i < parrFailedScenarios.length; i++) {
            String failedScenarioId = parrFailedScenarios[i];
            Scenarioexecution seToFind = this.read(Integer.parseInt(failedScenarioId));
            if (null == seToFind) {
                log.error("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - invalid failed scenario id=" + failedScenarioId + " passed. - ERROR");
                ServiceException se = new ServiceException("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - invalid failed scenario id passed.");
                se.setLogged();
                throw se;
            }
            seToFind.setStatus(LabConstants.STATUS_SCENARIOEXEC_FAILED);        // failed now
            this.update(seToFind);
        }

        //
        // step 4 of 4: update validation comments, if any
        //
        if (null == pobjScenarioIdCommentMap) {
            log.info("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - no validation comments for any scenario ids");
            return;
        }

        Set s = pobjScenarioIdCommentMap.entrySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            Map.Entry m = (Map.Entry) it.next();
            String keyScenarioId = (String) m.getKey();
            String valueValidationComment = (String) m.getValue();
            log.info("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Key=*" + keyScenarioId + "* and Value=*" + valueValidationComment + "*");

            Scenarioexecution seToFind = this.read(Integer.parseInt(keyScenarioId));
            if (null == seToFind) {
                log.error("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - invalid key scenario id=" + keyScenarioId + " passed. - ERROR");
                ServiceException se = new ServiceException("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - invalid key scenario id passed.");
                se.setLogged();
                throw se;
            }
            seToFind.setValidationComments(valueValidationComment);
            this.update(seToFind);
        }

        log.info("ScenarioExecutionServiceImpl.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - returning now - INFO");
    }

    /**
     *
     * To search Scenarioexecution based on criteria defined
     * @param criteria
     * @param criterion
     * @return List<Scenarioexecution> for all matching criteria
     * @throws ServiceException
     */
    @Override
    public List<Scenarioexecution> searchByCriteria(Scenarioexecution criteria, List<Criterion> criterion) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.searchByCriteria() - INFO");
        try {
            return scenarioExectionDAO.searchByCriteria(criteria, criterion);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }
    
    /**
    *
    * This is a finder of scenarioexecution based on participant and status
    * @param participantId
    * @param status
    * @return List<ScenarioExecution>
    * @throws ServiceException
    */
   @Override
   public List<Scenarioexecution> findByParticipantIdAndStatus(int participantId,String status) throws ServiceException {
       log.info("ScenarioCaseServiceImpl.findByParticipantId() - INFO");
       try {
           return scenarioExectionDAO.findByStatusAndParticipantId(participantId,status);
       } catch (Exception e) {
           throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
       }

   }


    /**
     * This method is get Scenarioexecutions for given executionUniqueId
     * @param executionUniqueId
     * @return List<Scenarioexecution>
     * @throws ServiceException
     */
    @Override
    public List<Scenarioexecution> getScenarExecsByServiceSetUniqueExecId(String executionUniqueId) throws ServiceException {
        List<Scenarioexecution> scenarioExecutions = new ArrayList<Scenarioexecution>();

        Scenarioexecution criteria = new Scenarioexecution();
        List<Criterion> criterionList = new ArrayList<Criterion>();
        criterionList.add(Restrictions.like("executionUniqueId", executionUniqueId, MatchMode.START));

        scenarioExecutions = this.searchByCriteria(criteria, criterionList);
        return scenarioExecutions;
    }
}
