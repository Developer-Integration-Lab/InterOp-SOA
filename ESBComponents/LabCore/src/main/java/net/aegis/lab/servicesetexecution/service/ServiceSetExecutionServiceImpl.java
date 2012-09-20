/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.servicesetexecution.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.caseexecution.service.CaseExecutionService;
import net.aegis.lab.dao.ParticipantpatientmapDAO;
import net.aegis.lab.dao.ServicesetexecutionDAO;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.ParticipantCnxVerificationStatus;
import net.aegis.lab.dao.pojo.Participantpatientmap;
import net.aegis.lab.dao.pojo.Questionnaire;
import net.aegis.lab.dao.pojo.Questionnairecase;
import net.aegis.lab.dao.pojo.Questionnaireresponse;
import net.aegis.lab.dao.pojo.Questionnairetestplan;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.helper.UniqueExecutionIdHelper;
import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.scenarioexecution.service.ScenarioExecutionService;
import net.aegis.lab.serviceset.service.ServiceSetService;
import net.aegis.lab.testharnessri.service.TestHarnessriService;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.LabConstants.LabType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("serviceSetExecutionService")
public class ServiceSetExecutionServiceImpl implements ServiceSetExecutionService {

    @Autowired
    private ServicesetexecutionDAO serviceSetExecDAO;
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private ScenarioExecutionService scenarioExecutionService;
    @Autowired
    private CaseExecutionService caseExecutionService;
    @Autowired
    private ParticipantpatientmapDAO participantPatientMapDao;

    @Autowired
    private ServiceSetService serviceSetService;
    
    @Autowired
    private TestHarnessriService testharnessriService;

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ServiceSetExecutionServiceImpl.class);

    /**
     * this method to create a new record of servicesetexecution in Servicesetexecution table
     * @param newInstance
     * @return Integer
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Servicesetexecution newInstance) throws ServiceException {
        log.info("Creating Servicesetexecution....");
        try {
            return serviceSetExecDAO.create(newInstance);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * this method to read one record from Servicesetexecution table for given setExecutionId
     * @param setExecutionId
     * @return Servicesetexecution record
     * @throws ServiceException
     */
    @Override
    public Servicesetexecution read(int setExecutionId) throws ServiceException {
        log.info("Find by setExecutionId.................");
        try {
            return serviceSetExecDAO.read(setExecutionId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method is to update one record of Servicesetexecution in Servicesetexecution table
     * @return void
     * @param Servicesetexecution object transientObject
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Servicesetexecution transientObject)
            throws ServiceException {
        log.info("updating ServiceSetExecution.............");
        try {
            serviceSetExecDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method is to delete one record of Servicesetexecution from Servicesetexecution table
     * @return void
     * @param Servicesetexecution object persistentObject
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Servicesetexecution persistentObject) throws ServiceException {
        log.info("Deleting Servicesetexecution....");
        try {
            serviceSetExecDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method to delete a Servicesetexection record for the given id
     * @return void
     * @param Integer Id
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer Id) throws ServiceException {
        log.info("Deleting Servicesetexecution by Id .... ");
        try {
            serviceSetExecDAO.delete(read(Id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method is a finder to get a Active participant by participantId
     *
     * @param participantId
     * @return List of Servicesetexecution
     * @throws ServiceException
     */

    public List<Servicesetexecution> findActiveByParticipantId(Integer participantId) throws ServiceException {        
        return findActiveByParticipantId(participantId, null);
    }

    @Override
    public List<Servicesetexecution> findActiveByParticipantId(Integer participantId, LabType labType) throws ServiceException {
        log.info("Find active by participantId.................");
        List<Servicesetexecution> servicesetExecution = null;
        try {
            servicesetExecution = serviceSetExecDAO.findActiveByParticipantId(participantId, labType);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return servicesetExecution;
    }
        

    /**
     * This method a finder to get a Servicesetexecution record given the setExecutionId
     * @param Integere setExecutionId
     * @return Servicesetexecution
     * @throws ServiceException
     */
    @Override
    public Servicesetexecution findBySetExecutionId(Integer setExecutionId) throws ServiceException {
        log.info("ServiceSetExecutionServiceImpl.findBySetExecutionId() - INFO");
        Servicesetexecution servicesetExecution = null;
        try {
            servicesetExecution = serviceSetExecDAO.read(setExecutionId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return servicesetExecution;
    }

    /**
     * Find Servicesetexecution by pExecutionUniqueId
     * @param String ExecutionUniqueId
     * @return List of Servicesetexecution
     * @throws ServiceException
     */
    @Override
    public List<Servicesetexecution> findByExecutionUniqueId(String pExecutionUniqueId) throws ServiceException {
        log.info("ServiceSetExecutionServiceImpl.findByExecutionUniqueId() - INFO");
        List<Servicesetexecution> servicesetExecution = null;   // note - there will be only one in DB !!

        if (null == pExecutionUniqueId) {
            log.error("ServiceSetExecutionServiceImpl.findByExecutionUniqueId() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ServiceSetExecutionServiceImpl.findByExecutionUniqueId() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        try {
            servicesetExecution = serviceSetExecDAO.findByExecutionUniqueId(pExecutionUniqueId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        if (null != servicesetExecution) {
            int iNumServiceSetExecutions = -1;  // note - there will be only one in DB !!
            iNumServiceSetExecutions = servicesetExecution.size();
            if (iNumServiceSetExecutions > 1) {
                log.error("ServiceSetExecutionServiceImpl.findByExecutionUniqueId() - Bad results obtained. Multiple service set executions in database for ExecutionUniqueId - ERROR");
                ServiceException se = new ServiceException("ServiceSetExecutionServiceImpl.findByExecutionUniqueId() - Bad results obtained. Multiple service set executions in database for ExecutionUniqueId.");
                se.setLogged();
                throw se;
            }
        }

        return servicesetExecution;
    }

    /**
     * this method to is to create a servicesetexecution record while user does a setup a service to execute its scenarios
     * @return void
     * @param List of Active Servicesets needToBeActiveServiceSets
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void startServiceSetExecution(List<Servicesetexecution> needToBeActiveServiceSets) throws ServiceException {
        log.info("startServiceSetExecution Begin***");

        try {
            if (needToBeActiveServiceSets != null) {

                Map<String, String> participantPatientIdMaps = null;
                String participantPatientId = null;
                for (Servicesetexecution serviceSetExecution : needToBeActiveServiceSets) {
                    Long time = System.currentTimeMillis();
                    Participant participant = serviceSetExecution.getParticipant();
                    if (participantPatientIdMaps == null) {
                        participantPatientIdMaps = populateCandidtePatientIdMap(participant.getParticipantId());
                    }
                    String serviceSetUniqueExecId = UniqueExecutionIdHelper.getServiceSetUniqueExecutionId(participant.getParticipantId(), time);
                    //ServiceSetExecution record
                    serviceSetExecution.setBeginTime(new Timestamp(time));
                    serviceSetExecution.setExecutionUniqueId(serviceSetUniqueExecId);
                    serviceSetExecution.setStatus(Servicesetexecution.STATUS_ACTIVE);

                    /* add questionarie response
                    prepare Questionnairetestplan data and ignore un answered questions based on answer column as empty
                    Current impl :  the questions and its test plans are submitted for hidden questions to server side with answer for each question as empty and need to ignore
                    TODO : In future  : we will implement  questions loading using ajax and the question  never be in the form at all .
                     *
                     */
                    List<Questionnaireresponse> questionnaireresponseList = new ArrayList<Questionnaireresponse>();
                    if (serviceSetExecution.getCdaQuestionnaireresponses() != null && !serviceSetExecution.getCdaQuestionnaireresponses().isEmpty()) {
                        for (Questionnaireresponse eachQuestionnaireresponse : serviceSetExecution.getCdaQuestionnaireresponses()) {
                            if (StringUtils.isNotEmpty(eachQuestionnaireresponse.getAnswer())) {
                                questionnaireresponseList.add(eachQuestionnaireresponse);
                            }
                        }

                    }
                    if (serviceSetExecution.getPdQuestionnaireresponses() != null && !serviceSetExecution.getPdQuestionnaireresponses().isEmpty()) {
                        for (Questionnaireresponse eachQuestionnaireresponse : serviceSetExecution.getPdQuestionnaireresponses()) {
                            if (StringUtils.isNotEmpty(eachQuestionnaireresponse.getAnswer())) {
                                questionnaireresponseList.add(eachQuestionnaireresponse);
                            }
                        }
                    }
                    if (serviceSetExecution.getQdQuestionnaireresponses() != null && !serviceSetExecution.getQdQuestionnaireresponses().isEmpty()) {
                        for (Questionnaireresponse eachQuestionnaireresponse : serviceSetExecution.getQdQuestionnaireresponses()) {
                            if (StringUtils.isNotEmpty(eachQuestionnaireresponse.getAnswer())) {
                                questionnaireresponseList.add(eachQuestionnaireresponse);
                            }
                        }
                    }
                    if (serviceSetExecution.getRdQuestionnaireresponses() != null && !serviceSetExecution.getRdQuestionnaireresponses().isEmpty()) {
                        for (Questionnaireresponse eachQuestionnaireresponse : serviceSetExecution.getRdQuestionnaireresponses()) {
                            if (StringUtils.isNotEmpty(eachQuestionnaireresponse.getAnswer())) {
                                questionnaireresponseList.add(eachQuestionnaireresponse);
                            }
                        }
                    }
                   
                    for (Questionnaireresponse eachQuestionnaireresponse : questionnaireresponseList) {
                        eachQuestionnaireresponse.setServicesetexecution(serviceSetExecution);
                        eachQuestionnaireresponse.setParticipant(participant);
                        eachQuestionnaireresponse.setExecutionUniqueId(serviceSetUniqueExecId);
                        eachQuestionnaireresponse.setStatus(Questionnaireresponse.STATUS_ACTIVE);
                        eachQuestionnaireresponse.setBeginTime(new Timestamp(time));
                        Questionnaire eachQuestionnaire = eachQuestionnaireresponse.getQuestionnaire();

                        // prapare child object "Questionnairetestplan"
                        List<Questionnairetestplan> questionnairetestplanList = new ArrayList<Questionnairetestplan>();

                        List<Questionnairecase> questionnairecases = eachQuestionnaire.getQuestionnairecases();
                        if (questionnairecases != null && !questionnairecases.isEmpty()) {
                            for (Questionnairecase eachQuestionnairecase : questionnairecases) {
                                Questionnairetestplan questionnairetestplan = new Questionnairetestplan();
                                questionnairetestplan.setTestcase(eachQuestionnairecase.getTestcase());
                                questionnairetestplan.setQuestionnaireresponse(eachQuestionnaireresponse);
                                questionnairetestplan.setStatus(Questionnairetestplan.STATUS_ACTIVE);
                                questionnairetestplanList.add(questionnairetestplan);
                            }
                        }
                        // associate to parent object "Questionnaireresponse"
                        eachQuestionnaireresponse.setQuestionnairetestplans(questionnairetestplanList);

                    }
                    serviceSetExecution.setQuestionnaireresponses(questionnaireresponseList);
                    //extract service set List by set id ( which is passed from ui using hidden value)
                    Serviceset serviceSet = serviceSetService.read(serviceSetExecution.getServiceset().getSetId());
                    serviceSetExecution.setServiceset(serviceSet);

                    List<Scenario> scenarioList = serviceSetExecution.getServiceset().getScenarios();
                    List<Scenarioexecution> scenarioExecutionList = new ArrayList<Scenarioexecution>();
                    List<Caseexecution> caseExecutionList = new ArrayList<Caseexecution>();
                    Scenarioexecution scenarioExecution = null;
                    Caseexecution caseExecution = null;
                    Testcase testCase = null;
                    String version = serviceSetExecution.getVersion();

                    // stuff the scenarioExecution
                    for (Scenario scenario : scenarioList) {
                        // ========================================
                        // Version 1.2 - 9/13/2010 - richard.ettema
                        // Add logic to include ACTIVE scenarios only
                        // ========================================
                        if (scenario.getStatus().equals(LabConstants.STATUS_SCENARIO_ACTIVE)) {

                            // Retrieve the scenarioCaseList for both intiator and responder

                            List<Scenariocase> scenarioCaseList = scenario.getScenariocases();
                            List<Scenariocase> filteredscenarioCaseList = filterScenarioCase(scenario, serviceSetExecution, scenarioCaseList);

                         // if initiator checked and SSN Handling matches
                            if (scenario.getInitiatorInd().equals("Y") && scenario.getInitiatorInd().equals(serviceSetExecution.getInitiatorInd()) && (scenario.getSsnHandlingInd().equals("N") || (scenario.getSsnHandlingInd().equals("Y") && serviceSetExecution.getSsnHandlingInd().equals("Y")))) {
                                scenarioExecution = new Scenarioexecution();
                                scenarioExecution.setParticipant(participant);
                                scenarioExecution.setScenario(scenario);
                                scenarioExecution.setExecutionUniqueId(UniqueExecutionIdHelper.getScenarioExeUniqueExecutionId(serviceSetUniqueExecId, scenario.getScenarioId()));
                                scenarioExecution.setStatus(Scenarioexecution.STATUS_ACTIVE);
                                scenarioExecution.setBeginTime(new Timestamp(time));
                                // creating list of ScenarioExecutionList
                                scenarioExecutionList.add(scenarioExecution);
                                // filling the CaseExecution itrating through the ScenarioCases
                                for (Scenariocase scenarioCase : filteredscenarioCaseList) {
                                    log.info("New caseExecution creation");

                                    if (participantPatientIdMaps != null) {
                                        participantPatientId = participantPatientIdMaps.get(scenarioCase.getPatientId());
                                    }
                                    log.info("The PatientId is [" + scenarioCase.getPatientId() + "] ParticipantPatientId is [" + participantPatientId + "]");
                                    caseExecution = new Caseexecution();
                                    testCase = scenarioCase.getTestcase();
                                    caseExecution.setScenarioexecution(scenarioExecution);
                                    caseExecution.setTestcase(testCase);
                                    caseExecution.setUserName(scenarioCase.getUserName());
                                    caseExecution.setPatientId(scenarioCase.getPatientId());
                                    caseExecution.setParticipatingRIs(getParticipatingRIsByVersion(scenarioCase.getParticipatingRIs(), version));
                                    caseExecution.setDocumentIds(scenarioCase.getDocumentIds());
                                    caseExecution.setSuccessCriteria(scenarioCase.getSuccessCriteria());
                                    caseExecution.setPdSuccessCriteria(scenarioCase.getPdSuccessCriteria());
                                    caseExecution.setPdSuccessCriteriaDesc(scenarioCase.getPdSuccessCriteriaDesc());
                                    caseExecution.setMessageType(testCase.getMessageType());
                                    caseExecution.setParticipantpatientId(participantPatientId);
                                    caseExecution.setStatus(Caseexecution.STATUS_ACTIVE);
                                    caseExecution.setVersion(version);
                                    caseExecution.setBeginTime(scenarioExecution.getBeginTime());
                                    caseExecution.setDependentScenarioId(scenarioCase.getDependentScenarioId());
                                    caseExecution.setDependentCaseId(scenarioCase.getDependentCaseId());
                                    caseExecution.setDisplayByDependentCaseStatus(scenarioCase.getDisplayByDependentCaseStatus());
                                    caseExecution.setResouceIdSendInd(participant.getResouceIdSendInd());
                                    caseExecutionList.add(caseExecution);
                                }
                            }
                            // if responder is checked and SSN Handling matches
                            if (scenario.getResponderInd().equals("Y") && scenario.getResponderInd().equals(serviceSetExecution.getResponderInd()) && (scenario.getSsnHandlingInd().equals("N") || (scenario.getSsnHandlingInd().equals("Y") && serviceSetExecution.getSsnHandlingInd().equals("Y")))) {
                                scenarioExecution = new Scenarioexecution();
                                scenarioExecution.setParticipant(participant);
                                scenarioExecution.setScenario(scenario);
                                scenarioExecution.setExecutionUniqueId(UniqueExecutionIdHelper.getScenarioExeUniqueExecutionId(serviceSetUniqueExecId, scenario.getScenarioId()));
                                scenarioExecution.setStatus(Scenarioexecution.STATUS_ACTIVE);
                                scenarioExecution.setBeginTime(new Timestamp(time));

                                // creating list of ScenarioExecutionList
                                scenarioExecutionList.add(scenarioExecution);
                                // filling the CaseExecution itrating through the ScenarioCases
                                for (Scenariocase scenarioCase : filteredscenarioCaseList) {
                                    log.info("New caseExecution creation");
                                    if (participantPatientIdMaps != null) {
                                        participantPatientId = participantPatientIdMaps.get(scenarioCase.getPatientId());
                                    }
                                    caseExecution = new Caseexecution();
                                    testCase = scenarioCase.getTestcase();
                                    caseExecution.setScenarioexecution(scenarioExecution);
                                    caseExecution.setTestcase(testCase);
                                    caseExecution.setUserName(scenarioCase.getUserName());
                                    caseExecution.setPatientId(scenarioCase.getPatientId());
                                    caseExecution.setParticipatingRIs(getParticipatingRIsByVersion(scenarioCase.getParticipatingRIs(), version));
                                    caseExecution.setDocumentIds(scenarioCase.getDocumentIds());
                                    caseExecution.setSuccessCriteria(scenarioCase.getSuccessCriteria());
                                    caseExecution.setPdSuccessCriteria(scenarioCase.getPdSuccessCriteria());
                                    caseExecution.setPdSuccessCriteriaDesc(scenarioCase.getPdSuccessCriteriaDesc());
                                    caseExecution.setMessageType(testCase.getMessageType());
                                    caseExecution.setParticipantpatientId(participantPatientId);
                                    caseExecution.setStatus(Caseexecution.STATUS_ACTIVE);
                                    caseExecution.setVersion(version);
                                    caseExecution.setBeginTime(scenarioExecution.getBeginTime());
                                    caseExecution.setDependentScenarioId(scenarioCase.getDependentScenarioId());
                                    caseExecution.setDependentCaseId(scenarioCase.getDependentCaseId());
                                    caseExecution.setDisplayByDependentCaseStatus(scenarioCase.getDisplayByDependentCaseStatus());
                                    caseExecution.setResouceIdSendInd(participant.getResouceIdSendInd());
                                    caseExecutionList.add(caseExecution);
                                }
                            }
                        }
                    }
                    // add serviceSetExecution to database
                    create(serviceSetExecution);

                    log.info("The ScenarioExecutionList" + scenarioExecutionList.size());
                    for (Scenarioexecution scenarioExecution1 : scenarioExecutionList) {
                        log.info(scenarioExecution1.getParticipant().getParticipantId() + "," + scenarioExecution1.getScenario().getScenarioId() + "," +
                                scenarioExecution1.getExecutionUniqueId() + "," + scenarioExecution1.getStatus() + "," + scenarioExecution1.getBeginTime() + "," + scenarioExecution1.getEndTime());
                        // add scenarioExecution to database
                        scenarioExecutionService.create(scenarioExecution1);
                    }

                    log.info("The caseExecutionList" + caseExecutionList.size());
                    for (Caseexecution caseExecution1 : caseExecutionList) {
                        log.info(caseExecution1.getCaseExecutionId() + "," + caseExecution1.getScenarioexecution().getExecutionUniqueId() + "," +
                                caseExecution1.getPatientId() + "," + caseExecution1.getStatus() + "," + caseExecution1.getVersion()+ "," + caseExecution1.getMessageType());
                        // add caseExecution to database
                        System.out.println(caseExecution1.getCaseExecutionId() + "," + caseExecution1.getScenarioexecution().getExecutionUniqueId() + "," +
                                caseExecution1.getPatientId() + "," + caseExecution1.getStatus() + "," + caseExecution1.getVersion()+ "," + caseExecution1.getMessageType());
                        caseExecutionService.create(caseExecution1);
                    }
                }
            } else {

                log.info("The List to be active is null");

            }
        } catch (ServiceException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This is to close the active service sets where the status is set to CLOSE
     * @return void
     * @param List of Active Servicesets need to be close needToBeCloseActiveServiceSets
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void closeServiceSetExecution(List<Servicesetexecution> needToBeCloseActiveServiceSets) throws ServiceException {
        Date currentTime = new Date();
        Long time = currentTime.getTime();
        for (Servicesetexecution serviceSetExec : needToBeCloseActiveServiceSets) {
            serviceSetExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_CLOSE);
            serviceSetExec.setEndTime(new Timestamp(time));
            update(serviceSetExec);
            //get ScenarioExecutions

            Scenarioexecution criteria = new Scenarioexecution();
            List<Criterion> criterionList = new ArrayList<Criterion>();
            criterionList.add(Restrictions.like("executionUniqueId", serviceSetExec.getExecutionUniqueId(), MatchMode.START));
            List<Scenarioexecution> scenarioExecs = scenarioExecutionService.searchByCriteria(criteria, criterionList);

            // String serviceSetExecUniqueId = "%" + serviceSetExec.getExecutionUniqueId() + "%";

            //List<Scenarioexecution> scenarioExecs = scenarioExecutionService.findByUniqueExecutionId(serviceSetExecUniqueId);

            for (Scenarioexecution scenarioExec : scenarioExecs) {
                if (scenarioExec.getStatus().equals(LabConstants.STATUS_SERVICESETEXEC_ACTIVE)) {
                    scenarioExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_CLOSE);
                    scenarioExec.setEndTime(new Timestamp(time));
                    scenarioExecutionService.update(scenarioExec);
                }
                //get caseExecutions for scenarioExecution
                List<Caseexecution> caseExecs = scenarioExec.getCaseexecutions();
                for (Caseexecution caseExec : caseExecs) {
                    if (caseExec.getStatus().equals(LabConstants.STATUS_SERVICESETEXEC_ACTIVE)) {
                        caseExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_CLOSE);
                        caseExecutionService.update(caseExec);
                    }
                }
            }
        }
    }

    /**
     * This method is to update the status to CLOSE Scenario execution and caseexecution while closing Serviceset
     * @return void
     * @param executionId
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void updateScenarioExecsAndCaseExecsToCloseForValidationRecs(String executionId) throws ServiceException {
        Date currentTime = new Date();
        Long time = currentTime.getTime();
        try {
            Scenarioexecution criteria = new Scenarioexecution();
            List<Criterion> criterionList = new ArrayList<Criterion>();
            criterionList.add(Restrictions.like("executionUniqueId", executionId, MatchMode.START));
            List<Scenarioexecution> scenarioExecs = scenarioExecutionService.searchByCriteria(criteria, criterionList);

            // String serviceSetExecUniqueId = "%" + serviceSetExec.getExecutionUniqueId() + "%";

            //List<Scenarioexecution> scenarioExecs = scenarioExecutionService.findByUniqueExecutionId(serviceSetExecUniqueId);

            for (Scenarioexecution scenarioExec : scenarioExecs) {
                if (scenarioExec.getStatus().equals(LabConstants.STATUS_SERVICESETEXEC_SUBMITTED)) {
                    scenarioExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_CLOSE);
                    scenarioExec.setEndTime(new Timestamp(time));
                    scenarioExecutionService.update(scenarioExec);
                }
                //get caseExecutions for scenarioExecution
                List<Caseexecution> caseExecs = scenarioExec.getCaseexecutions();
                for (Caseexecution caseExec : caseExecs) {
                    if (caseExec.getStatus().equals(LabConstants.STATUS_SERVICESETEXEC_SUBMITTED)) {
                        caseExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_CLOSE);
                        caseExecutionService.update(caseExec);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
    }

    /**
     * This method updates the status of servicesetexecutions to Submitted while submitted for validation
     * @return void
     * @param needToBeSubmittedServiceSets the list of Servicesetexecutions
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void updateServiceSetExecutionStatusToSubmitted(List<Servicesetexecution> needToBeSubmittedServiceSets) throws ServiceException {
        log.info("updateServiceSetExecutionStatusToSubmitted");
        try {
            Date currentTime = new Date();
            Long time = currentTime.getTime();
            for (Servicesetexecution serviceSetExec : needToBeSubmittedServiceSets) {
                serviceSetExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_SUBMITTED);
                serviceSetExec.setEndTime(new Timestamp(time));
                this.update(serviceSetExec);
                //get ScenarioExecutions
                Scenarioexecution criteria = new Scenarioexecution();
                List<Criterion> criterionList = new ArrayList<Criterion>();
                criterionList.add(Restrictions.like("executionUniqueId", serviceSetExec.getExecutionUniqueId(), MatchMode.START));
                List<Scenarioexecution> scenarioExecs = scenarioExecutionService.searchByCriteria(criteria, criterionList);

                // String serviceSetExecUniqueId = "%" + serviceSetExec.getExecutionUniqueId() + "%";

                // List<Scenarioexecution> scenarioExecs = scenarioExecutionService.findByUniqueExecutionId(serviceSetExecUniqueId);

                for (Scenarioexecution scenarioExec : scenarioExecs) {

                    scenarioExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_SUBMITTED);
                    scenarioExec.setEndTime(new Timestamp(time));
                    scenarioExecutionService.update(scenarioExec);
                    //get caseExecutions for scenarioExecution
                    List<Caseexecution> caseExecs = scenarioExec.getCaseexecutions();
                    for (Caseexecution caseExec : caseExecs) {
                        caseExec.setStatus(LabConstants.STATUS_SERVICESETEXEC_SUBMITTED);
                        caseExecutionService.update(caseExec);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     *
     * Find service set execution(s) given a participant id and execution status (w/o using LabAccessFilter)
     * @param participantId and pstrStatus
     * @return List of servicesetexecutions
     */
    @Override
    public List<Servicesetexecution> findByParticipantIdAndStatus(Integer participantId, String pstrStatus) throws ServiceException {
        return findByParticipantIdAndStatus(participantId, pstrStatus, null);
    }

    /**
     * Find service set execution(s) given a participant id and execution status (w/ the use of LabAccessFilter)
     * @param participantId
     * @param pstrStatus
     * @param labType
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Servicesetexecution> findByParticipantIdAndStatus(Integer participantId, String pstrStatus, LabType labType) throws ServiceException {

        log.info("ServiceSetExecutionServiceImpl.findByParticipantIdAndStatus() - INFO");
        List<Servicesetexecution> servicesetexecutionsResults = null;
        Participant objParticipantToAssociate = null;
        Servicesetexecution criteria = null;
        List<Order> orderByList = null;

        if ((null == participantId) || (null == pstrStatus)) {
            log.error("ServiceSetExecutionServiceImpl.findByParticipantIdAndStatus() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ServiceSetExecutionServiceImpl.findByParticipantIdAndStatus() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        // get Participant object to associate with the criteria object
        objParticipantToAssociate = participantService.findByParticipantId(participantId);
        if (null == objParticipantToAssociate) {
            log.error("ServiceSetExecutionServiceImpl.findByParticipantIdAndStatus() - Participant Id passed may not be valid. Cannot find participant. - ERROR");
            ServiceException se = new ServiceException("ServiceSetExecutionServiceImpl.findByParticipantIdAndStatus() - Participant Id passed may not be valid. Cannot find participant.");
            se.setLogged();
            throw se;
        }

        criteria = new Servicesetexecution();
        criteria.setParticipant(objParticipantToAssociate);
        criteria.setStatus(pstrStatus);

        
        if (labType!=null) {
            Serviceset s = new Serviceset();
            s.setLabAccessFilter(labType.getId());
            criteria.setServiceset(s);
        }
         

        orderByList = new ArrayList<Order>();
        orderByList.add(Order.desc("endTime"));

        servicesetexecutionsResults = serviceSetExecDAO.searchByCriteriaAndSort(criteria, this.executeCriteria(criteria), orderByList);

        return servicesetexecutionsResults;
    }

    /**
     * Find service set execution(s) given an execution status
     * @return List of Servicesetexection
     * @param  pstrStatus
     * @throws ServiceException
     */
    @Override
    public List<Servicesetexecution> getAllServiceSetExecutionsByStatus(String pstrStatus) throws ServiceException {
        log.info("ServiceSetExecutionServiceImpl.getAllServiceSetExecutionsByStatus() - INFO");
        List<Servicesetexecution> servicesetexecutionsResults = null;
        Servicesetexecution criteria = null;
        List<Order> orderByList = null;

        // parameter validity quickly.
        if (null == pstrStatus) {
            log.error("ServiceSetExecutionServiceImpl.getAllServiceSetExecutionsByStatus() - Null parameter passed. - ERROR");
            ServiceException se = new ServiceException("ServiceSetExecutionServiceImpl.getAllServiceSetExecutionsByStatus() - Null parameter passed.");
            se.setLogged();
            throw se;
        }

        criteria = new Servicesetexecution();
        criteria.setStatus(pstrStatus);

        orderByList = new ArrayList<Order>();
        orderByList.add(Order.desc("endTime"));

        servicesetexecutionsResults = serviceSetExecDAO.searchByCriteriaAndSort(criteria, this.executeCriteria(criteria), orderByList);
        if (servicesetexecutionsResults != null && servicesetexecutionsResults.size() > 0) {
            log.info("ServiceSetExecutionServiceImpl.getAllServiceSetExecutionsByStatus(): servicesetexecutionsResults size = " + servicesetexecutionsResults.size());
        }

        return servicesetexecutionsResults;
    }

    /**
     * This is to populate a map with patientId and corresponding participantPatientId given a participantId
     *
     * @param participantId
     * @return Map<String ,String>
     * @throws ServiceException
     */
    @Override
    public Map<String, String> populateCandidtePatientIdMap(int participantId) throws ServiceException {
        log.info("ServiceSetExecutionServiceImpl.getCandidtePatientId");
        List<Participantpatientmap> participantPatientMap = null;
        Map<String, String> participantPatientIdMaps = null;
        try {
            participantPatientMap = participantPatientMapDao.findByParticipantId(participantId);
            if (participantPatientMap.size() != 0) {
                participantPatientIdMaps = new HashMap<String, String>();
                for (Participantpatientmap canPatientMap : participantPatientMap) {
                    participantPatientIdMaps.put(canPatientMap.getPatient().getPatientId(), canPatientMap.getParticipantPatientId());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
        return participantPatientIdMaps;
    }

    /**
     *
     * This method is to pass the criteria list to the method searchByCriteria
     * @param criteria
     * @return List<Criterion>
     */
    private List<Criterion> executeCriteria(Servicesetexecution criteria) {
        List<Criterion> criterionList = new ArrayList<Criterion>();

        if (criteria != null) {
            // Check each property for empty value and set to null
            if (criteria.getParticipant() != null) {   // should really never be null -- it's a hack for now
                if (criteria.getParticipant().getParticipantId() != null && !criteria.getParticipant().getParticipantId().toString().equals("")) {
                    criterionList.add(Restrictions.eq("participant.participantId", criteria.getParticipant().getParticipantId()));
                }
            }

            if (criteria.getServiceset() != null) {  // should really never be null -- it's a hack for now
                if (criteria.getServiceset().getSetId() != null && !criteria.getServiceset().getSetId().toString().equals("")) {
                    criterionList.add(Restrictions.eq("serviceset.setId", criteria.getServiceset().getSetId()));
                }
                if ((criteria.getServiceset().getLabAccessFilter()>=LabType.CONFORMANCE.getId())) {//Story 69
                    criterionList.add(Restrictions.eq(criteria.getClass().getSimpleName()+".serviceset.labAccessFilter", criteria.getServiceset().getLabAccessFilter()));
                }
            }

            if (criteria.getExecutionUniqueId() != null && !criteria.getExecutionUniqueId().equals("")) {
                criterionList.add(Restrictions.eq("executionUniqueId", criteria.getExecutionUniqueId()));
            }

            if (criteria.getInitiatorInd() != null && !criteria.getInitiatorInd().equals("")) {
                criterionList.add(Restrictions.eq("initiatorInd", criteria.getInitiatorInd()));
            }

            if (criteria.getResponderInd() != null && !criteria.getResponderInd().equals("")) {
                criterionList.add(Restrictions.eq("responderInd", criteria.getResponderInd()));
            }

            if (criteria.getSsnHandlingInd() != null && !criteria.getSsnHandlingInd().equals("")) {
                criterionList.add(Restrictions.like("ssnHandlingInd", criteria.getSsnHandlingInd(), MatchMode.START));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().equals("")) {
                criterionList.add(Restrictions.eq("status", criteria.getStatus()));
            }

            if (criteria.getBeginTime() != null && !criteria.getBeginTime().toString().equals("")) {
                criterionList.add(Restrictions.like("beginTime", criteria.getBeginTime().toString(), MatchMode.START));
            }

            if (criteria.getEndTime() != null && !criteria.getEndTime().toString().equals("")) {
                criterionList.add(Restrictions.like("endTime", criteria.getEndTime().toString(), MatchMode.START));
            }
        }
        return criterionList;
    }

    private List<Scenariocase> filterScenarioCase(Scenario scenario, Servicesetexecution serviceSetExecution, List<Scenariocase> scenarioCaseList) {

        List<Scenariocase> filteredscenarioCaseList = new ArrayList<Scenariocase>();
        List<Questionnaireresponse> qr = null;


        //filter based on serviceType and Q response
        if (scenario.getScenarioName().contains(ParticipantCnxVerificationStatus.PATIENT_DISCOVERY)) {
            if (serviceSetExecution.getPdQuestionnaireresponses() != null && !serviceSetExecution.getPdQuestionnaireresponses().isEmpty()) {
                qr = serviceSetExecution.getPdQuestionnaireresponses();
            }
        } else if (scenario.getScenarioName().contains(ParticipantCnxVerificationStatus.QUERY_FOR_DOCUMENT)) {
            if (serviceSetExecution.getQdQuestionnaireresponses() != null && !serviceSetExecution.getQdQuestionnaireresponses().isEmpty()) {
                qr = serviceSetExecution.getQdQuestionnaireresponses();
            }
        } else if (scenario.getScenarioName().contains(ParticipantCnxVerificationStatus.RETRIEVE_DOCUMENT)) {
            if (serviceSetExecution.getRdQuestionnaireresponses() != null && !serviceSetExecution.getRdQuestionnaireresponses().isEmpty()) {
                qr = serviceSetExecution.getRdQuestionnaireresponses();
            }
        }

        if (qr != null && !qr.isEmpty()) {
            for (Questionnaireresponse eachQuestionnaireresponse : qr) {
                if (StringUtils.isNotEmpty(eachQuestionnaireresponse.getAnswer()) && "N".equals(eachQuestionnaireresponse.getAnswer())) {
                        for (Questionnairecase qc : eachQuestionnaireresponse.getQuestionnaire().getQuestionnairecases()) {
                            for (Scenariocase scenarioCase : scenarioCaseList) {
                                if (scenarioCase.getTestcase().getCaseId().equals(qc.getTestcase().getCaseId())) {
                                    filteredscenarioCaseList.add(scenarioCase);
                                }
                            }
                        }
                    
                }
            }
            scenarioCaseList.removeAll(filteredscenarioCaseList);
        } 
        return scenarioCaseList;
    }

    public String getParticipatingRIsByVersion(String participatingRIs, String version) throws ServiceException{
    	String [] ris = null;
    	int id;
    	String newRIs = null;
    	if(participatingRIs.contains(LabConstants.SPLITTER_CARET)){
    		ris = participatingRIs.split(LabConstants.SPLITTER);
    		for(String participatingId : ris){
    			id = testharnessriService.getTestharnessIdByVersion(participatingId, version);
    			if(newRIs == null)
    				newRIs = String.valueOf(id);
    			else
    				newRIs += LabConstants.SPLITTER_CARET + String.valueOf(id);
    		}
    	}else{
    		id = testharnessriService.getTestharnessIdByVersion(participatingRIs, version);
    		newRIs = String.valueOf(id);
    	}
    	return newRIs;
    }

}
