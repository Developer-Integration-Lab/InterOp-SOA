/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.Validation;
import net.aegis.lab.dto.ValidationDto;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.nhinrep.service.NhinrepService;
import net.aegis.lab.scenarioexecution.service.ScenarioExecutionService;
import net.aegis.lab.servicesetexecution.service.ServiceSetExecutionService;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.validation.service.ValidationService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class ValidationManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);
    private static ValidationManager instance;
    private ValidationService validationService = (ValidationService) ContextUtil.getLabContext().getBean("validationService");
    private ServiceSetExecutionService serviceSetExecService = (ServiceSetExecutionService) ContextUtil.getLabContext().getBean("serviceSetExecutionService");
    private NhinrepService nhinRepService = (NhinrepService) ContextUtil.getLabContext().getBean("nhinrepService");
    private ScenarioExecutionService scenarioExecutionService = (ScenarioExecutionService) ContextUtil.getLabContext().getBean("scenarioExecutionService");

    private ValidationManager() {
    }

    /**
     * @return PatientManager
     */
    public static ValidationManager getInstance() {
        if (instance == null) {
            instance = new ValidationManager();
        }
        return instance;
    }

    public void submitServiceSetForValidation(List<Servicesetexecution> serviceSetExecs) throws ServiceException {
        try {

            log.info("submitServiceSetForValidation---->>>>>>>>" + serviceSetExecs.size());
            // before submitting the service set update the status to submitted
            serviceSetExecService.updateServiceSetExecutionStatusToSubmitted(serviceSetExecs);
            log.info("After update  and before create------>>>>>>");
            validationService.createValidationServiceSetRecords(serviceSetExecs);
            log.info("After create createValidationServiceSetRecords------>>>>>>");

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);

        }
    }

    public List<ValidationDto> getServiceSetForValidation() throws ServiceException {
        log.info("getServiceSetForValidation......");
        Validation criteria = new Validation();
        List<ValidationDto> validatioDtoList = new ArrayList<ValidationDto>();
        List<Criterion> criterionList = new ArrayList<Criterion>();
        List<Validation> newValdiation = new ArrayList<Validation>();
        //criterionList.add(Restrictions.eq("status", LabConstants.STATUS_VALIDATION_SUBMITTED));
        try {
            // before submitting the service set update the status to submitted
            newValdiation = validationService.searchByCriteria(criteria, criterionList);
            List<Nhinrep> nhinreps = nhinRepService.getAllNhinReps();
            for (Validation validation : newValdiation) {
                ValidationDto valDto = new ValidationDto();
                valDto.setValidationId(validation.getValidationId());
                valDto.setExecutionUniqueId(validation.getServicesetexecution().getExecutionUniqueId());
                valDto.setServiceSet(validation.getServicesetexecution().getServiceset().getSetName());
                valDto.setSubmittedOn(validation.getCreatetime().toString());
                valDto.setLabAccessFilter(validation.getServicesetexecution().getServiceset().getLabAccessFilter());
                for (Nhinrep nhinrep : nhinreps) {
                    log.debug("I am in nhinrep-->>" + validation.getNhinRepId() + "-->>" + nhinrep.getNhinRepId());
                    if (nhinrep.getNhinRepId().equals(validation.getNhinRepId())) {
                        valDto.setAssignedTo(nhinrep.getName());
                        log.debug("I am in nhinrep-->>" + nhinrep.getName());
                        break;
                    }
                }
                validatioDtoList.add(valDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
        log.info("getServiceSetForValidation......" + validatioDtoList.size());
        return validatioDtoList;
    }

    public void setValidationRecsForReview(List<ValidationDto> valForReview, int nhinRepId) throws ServiceException {
        try {
            validationService.setValidationRecordsForReview(valForReview, nhinRepId);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
    }

    public void unAssignValidationRecsForReviewFromValidator(List<ValidationDto> valForReview, int nhinRepId) throws ServiceException {
        try {
            validationService.unAssignValidationRecords(valForReview, nhinRepId);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);

        }


    }


    /**
     * Requirement: Nhin validator, on his/her dashboard, need to see validation
     * records that belong to him/her only.  For this use case under consideration,
     * the status of those validation records would be 'REVIEW'.
     *
     * @param pNhinRepId            Nhin rep Id of the logged in Nhin Validator
     * @param pValidationStatus     Mostly, 'REVIEW' status for this use case.
     * @return List<Validation>     Validation Records That Belong To the NhinValidator
     * @throws ServiceException
     */
    public List<Validation> getValidationRecordsUnderReviewThatBelongToAnNhinValidator(int pNhinRepId) throws ServiceException {

        log.info("ValidationManager.getValidationRecordsUnderReviewThatBelongToAnNhinValidator() - INFO");

        List<Validation> objListValidationRecordsWithReviewStatus = null;

        // check for parameters validity
        if (pNhinRepId <= 0) {
            log.error("ValidationManager.getValidationRecordsUnderReviewThatBelongToAnNhinValidator() - Invalid Nhin Rep Id pNhinRepId=" + pNhinRepId + " passed. - ERROR");
            ServiceException se = new ServiceException("ValidationManager.getValidationRecordsUnderReviewThatBelongToAnNhinValidator() - Invalid Nhin Rep Id pNhinRepId=" + pNhinRepId + " passed.");
            se.setLogged();
            throw se;
        }

        try {
            objListValidationRecordsWithReviewStatus = validationService.findReviewByNhinRepId(pNhinRepId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return objListValidationRecordsWithReviewStatus;
    }


    /**
     * Requirement: Nhin validator, on his/her dashboard, can choose to read the comments that
     * were added when a service set execution was 'submitted' for validation by the
     * associated participant.
     *
     * @param serviceSetExecutionUniqueId - service set execution under 'review'
     * @param scenarioExecutionStatus - scenarios pertaining to the "under-review" service set execution, whose status is expected to be 'closed'
     *                                  (LabConstants.STATUS_SCENARIOEXEC_CLOSED)
     * @return List of Scenario executions as expected
     * @throws ServiceException
     */
    public List<Scenarioexecution> getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(String serviceSetExecutionUniqueId, String scenarioExecutionStatus)
            throws ServiceException {

        log.info("ValidationManager.getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview() - INFO");

        List<Scenarioexecution> servicesetExecution = null;

        try {
            servicesetExecution = scenarioExecutionService.findByUniqueExecutionIdAndStatus(serviceSetExecutionUniqueId, scenarioExecutionStatus);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return servicesetExecution;
    }

    /**
     * Requirement - Nhin validator, after logging in, can view his/her dashboard
     * in order to make decisions on submitted service set executions at the
     * individual scenario levels.  At this time, s/he can drill-down on the scenario
     * in order to get more information on the submitted scenario.  This class
     * supports this particular use case.
     *
     * @param pscenarioExecutionUniqueId
     * @return
     * @throws ServiceException
     */
    public Scenarioexecution readScenarioExecution(String pscenarioExecutionUniqueId)
            throws ServiceException {

        log.info("ValidationManager.readScenarioExecution() - INFO");
        Scenarioexecution scenarioExecutionInDatabase = null;
        Integer iScenarioExecutionUniqueId = null;

        // check for parameters validity
        if (null == pscenarioExecutionUniqueId) {
            log.error("ValidationManager.readScenarioExecution() - Null parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ValidationManager.readScenarioExecution() - Null parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        try {
            iScenarioExecutionUniqueId = Integer.parseInt(pscenarioExecutionUniqueId);
        } catch (Exception e) {
            log.error("ValidationManager.readScenarioExecution() - Non-parsable parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ValidationManager.readScenarioExecution() - Non-parsable parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        // find the submitted scenario
        try {
            scenarioExecutionInDatabase = scenarioExecutionService.read(iScenarioExecutionUniqueId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        // it is expected always that the scenario execution be available in the database
        if (null == scenarioExecutionInDatabase) {
            log.error("ValidationManager.readScenarioExecution() - Scenario execution not available in database. - ERROR");
            ServiceException se = new ServiceException("ValidationManager.readScenarioExecution() - Scenario execution not available in database.");
            se.setLogged();
            throw se;
        }

        return scenarioExecutionInDatabase;
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
    public void updateScenarioExecutionsWithValidOrFailStatusAndValidationComments(
            String pExecutionUniqueId,
            String[] parrValidatedScenarios,
            String[] parrFailedScenarios,
            Map<String, String> pobjScenarioIdCommentMap)
            throws ServiceException {

        log.info("ValidationManager.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - INFO");

        // check nominal parameter validity
        if (null == pExecutionUniqueId) {
            log.error("ValidationManager.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Unknown service set execution id. - ERROR");
            ServiceException se = new ServiceException("ValidationManager.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Unknown service set execution id.");
            se.setLogged();
            throw se;
        }
        // Now, the view must pass either validated scenarios or failed scenarios.
        if ((null == parrValidatedScenarios) & (null == parrFailedScenarios)) {
            log.error("ValidationManager.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ValidationManager.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        // go ahead and process
        try {
            scenarioExecutionService.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments(pExecutionUniqueId, parrValidatedScenarios, parrFailedScenarios, pobjScenarioIdCommentMap);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }
}
