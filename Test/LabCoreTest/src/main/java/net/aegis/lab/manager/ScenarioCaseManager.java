package net.aegis.lab.manager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.altscenariocase.service.AltscenariocaseService;
import net.aegis.lab.caseexecution.service.CaseExecutionService;
import net.aegis.lab.caseresult.service.CaseResultService;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Participantendpoint;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.dao.pojo.Testharnessendpoint;
import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.scenariocase.service.ScenarioCaseService;
import net.aegis.lab.scenarioexecution.service.ScenarioExecutionService;
import net.aegis.lab.testcase.service.TestCaseService;
import net.aegis.lab.testharnessri.service.TestHarnessriService;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;
import net.aegis.labcommons.jaxb.qd.QDEntityResponseType;
import net.aegis.labcommons.jaxb.rd.RDEntityResponseType;
import net.aegis.labcommons.service.PatientDiscovery;
import net.aegis.labcommons.service.QueryDocument;
import net.aegis.labcommons.service.RetrieveDocument;
import net.aegis.md.util.ServiceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class ScenarioCaseManager {

    private static final Log log = LogFactory.getLog(ScenarioCaseManager.class);
    CaseResultService caseResultService = (CaseResultService) ContextUtil.getLabContext().getBean("caseResultService");
    ScenarioCaseService scenariocaseService = (ScenarioCaseService) ContextUtil.getLabContext().getBean("scenarioCaseService");
    AltscenariocaseService altscenariocaseService = (AltscenariocaseService) ContextUtil.getLabContext().getBean("altscenariocaseService");
    ScenarioExecutionService scenarioExecutionService = (ScenarioExecutionService) ContextUtil.getLabContext().getBean("scenarioExecutionService");
    TestCaseService testCaseService = (TestCaseService) ContextUtil.getLabContext().getBean("testCaseService");
    TestHarnessriService testHarnessriService = (TestHarnessriService) ContextUtil.getLabContext().getBean("testHarnessriService");
    CaseExecutionService caseExecutionService = (CaseExecutionService) ContextUtil.getLabContext().getBean("caseExecutionService");
    
    private static ScenarioCaseManager instance;
    private ScenarioCaseManager() {
    }

    /**
     * @return ScenarioCaseManager
     */
    public static ScenarioCaseManager getInstance() {
        if (instance == null) {
            instance = new ScenarioCaseManager();
        }
        return instance; 
    }

    /**
     * Execute the Patient Discovery service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>initiatorPatientId = null</li>
     * <li>selectedAltScenarioCaseId = 0</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
    public PDEntityResponseType executePatientCorrelation(Participant participant, int scenarioId, int caseId) throws ServiceException {
        return this.executePatientCorrelation(participant, scenarioId, caseId, 0, null);
    }


    /**
     * Execute the Patient Discovery service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>initiatorPatientId = null</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param selectedAltScenarioCaseId
     * @return
     * @throws ServiceException
     */
    public PDEntityResponseType executePatientCorrelation(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId) throws ServiceException {
        return this.executePatientCorrelation(participant, scenarioId, caseId, selectedAltScenarioCaseId, null);
    }

    /**
     * Execute the Patient Discovery service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>selectedAltScenarioCaseId = 0</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param initiatorPatientId
     * @return
     * @throws ServiceException
     */
    public PDEntityResponseType executePatientCorrelation(Participant participant, int scenarioId, int caseId, String initiatorPatientId) throws ServiceException {
        return this.executePatientCorrelation(participant, scenarioId, caseId, 0, initiatorPatientId);
    }

    /**
     * Execute the Patient Discovery service
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
    //Overloaded executePatientCorrelation method with extra initiatorPatientId argument - MK
    public PDEntityResponseType executePatientCorrelation(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId, String initiatorPatientId) throws ServiceException {
        log.info("ScenarioCaseManager.executePatientCorrelation(): parameters passed are as below:\n" +
                   "scenarioId=" + scenarioId + " caseId=" + caseId + " " +
                   "selectedAltScenarioCaseId=" + selectedAltScenarioCaseId + " initiatorPatientId=" + initiatorPatientId);

        String ipAddress = null;
        String localHCID = null;
        Map<String, String> endpoints = null;
        List<String> remoteHCID = null;
        Map<Object, Object> requestData = null;

        PDEntityResponseType result = null;

        try {
         //   requestData = fetchRequestData(participant, scenarioId, caseId);

            ipAddress = requestData.get("ipAddress").toString();
            localHCID = requestData.get("localHCID").toString();
            endpoints = (Map<String, String>) requestData.get("endpoints");
            remoteHCID = (List<String>) requestData.get("remoteHCID");

            /*
             * Determine whether to use "happy path" config file or "alternate rainy path" config file
             */
            String configFile = null;
            if (0 == selectedAltScenarioCaseId) {
                configFile = scenariocaseService.getPatientDataFromConfig(scenarioId, caseId); // happy path
            }
            else {
                configFile = altscenariocaseService.getPatientDataFromConfig(scenarioId, caseId, selectedAltScenarioCaseId); // rainy path
            }

            // Check for valid config xml
            if (configFile == null) {
                log.warn("Scenario Case Configuration file is null");
            } else {
                log.info("Patient Discovery XML: " + configFile.toString());
                // Check for valid endpoints
                if (endpoints != null) {
                    // Locate PD endpoint
                    String endpoint = endpoints.get("PD");
                    // If endpoint not empty, assign to ipAddress
                    if (endpoint != null && !endpoint.equals("")) {
                        ipAddress = endpoint;
                    }
                }

                result = new PDEntityResponseType();
                PatientDiscovery patientDiscovery = ServiceFactory.getInstance().getPDService(LabConstants.Connect_Version_248);
                /*
                 * Determine PD call based on Initiator Patient value
                 */
                if (initiatorPatientId == null || initiatorPatientId.isEmpty()) {
                    result = patientDiscovery.correlatePatient(ipAddress, localHCID, remoteHCID, configFile);
                }
                else {
                    result = patientDiscovery.correlatePatient(ipAddress, localHCID, remoteHCID, configFile, initiatorPatientId);
                }

            }
        } catch (Exception ex) {
            log.error("Exception", ex);
            throw new ServiceException(ex, ServiceException.ERROR_CODE_GLOBAL_DEFAULT);
        }
        return result;
    }


    /**
     * Execute the Query For Document service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>selectedAltScenarioCaseId = 0</li>
     * <li>initiatorPatientId = null</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
    public QDEntityResponseType executeQueryForDoc(Participant participant, int scenarioId, int caseId) throws ServiceException {
        return this.executeQueryForDoc(participant, scenarioId, caseId, 0, null);
    }

    /**
     * Execute the Query For Document service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>initiatorPatientId = null</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param selectedAltScenarioCaseId
     * @return
     * @throws ServiceException
     */
    public QDEntityResponseType executeQueryForDoc(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId) throws ServiceException {
        return this.executeQueryForDoc(participant, scenarioId, caseId, selectedAltScenarioCaseId, null);
    }

    /**
     * Execute the Query For Document service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>selectedAltScenarioCaseId = 0</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param initiatorPatientId
     * @return
     * @throws ServiceException
     */
    public QDEntityResponseType executeQueryForDoc(Participant participant, int scenarioId, int caseId, String initiatorPatientId) throws ServiceException {
        return this.executeQueryForDoc(participant, scenarioId, caseId, 0, initiatorPatientId);
    }

    /**
     * Execute the Query For Document service
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param selectedAltScenarioCaseId
     * @param initiatorPatientId
     * @return
     * @throws ServiceException
     */
    public QDEntityResponseType executeQueryForDoc(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId, String initiatorPatientId) throws ServiceException {
        log.info("ScenarioCaseManager.executeQueryForDoc(): parameters passed are as below:\n" +
                   "scenarioId=" + scenarioId + " caseId=" + caseId + " " +
                   "selectedAltScenarioCaseId=" + selectedAltScenarioCaseId + " initiatorPatientId=" + initiatorPatientId);

        QDEntityResponseType result = null;

        try {
            Map<Object, Object> requestData = null; //fetchRequestData(participant, scenarioId, caseId);

            String ipAddress = requestData.get("ipAddress").toString();
            String localHCID = requestData.get("localHCID").toString();
            Map<String, String> endpoints = (Map<String, String>) requestData.get("endpoints");
            List<String> remoteHCID = (List) requestData.get("remoteHCID");
            if (initiatorPatientId!=null && !"".equals(initiatorPatientId) && initiatorPatientId.contains(LabConstants.SPLITTER_COLON)) {
                 String[] responderPack = initiatorPatientId.split(LabConstants.SPLITTER_COLON);
                 if ((remoteHCID != null) && (remoteHCID.size() >= 0)) {
                    remoteHCID.remove(0);
                 }
                 initiatorPatientId = responderPack[1];
                 remoteHCID.add(responderPack[2]);
            }


            /*
             * Determine whether to use "happy path" config file or "alternate rainy path" config file
             */
            String configFile = null;
            if (0 == selectedAltScenarioCaseId) {
                configFile = scenariocaseService.getPatientDataFromConfig(scenarioId, caseId); // happy path
            }
            else {
                configFile = altscenariocaseService.getPatientDataFromConfig(scenarioId, caseId, selectedAltScenarioCaseId); // rainy path
            }

            // Check for valid config xml
            if (configFile == null) {
                log.warn("Scenario Case Configuration file is null");
            } else {
                log.info("Query For Document XML: " + configFile.toString());
                // Check for valid endpoints
                if (endpoints != null) {
                    // Locate QD endpoint
                    String endpoint = endpoints.get("QD");
                    // If endpoint not empty, assign to ipAddress
                    if (endpoint != null && !endpoint.equals("")) {
                        ipAddress = endpoint;
                    }
                }

                result = new QDEntityResponseType();
                QueryDocument queryDocument = ServiceFactory.getInstance().getQDService(LabConstants.Connect_Version_248);;
                /*
                 * Determine QFD call based on Initiator Patient value
                 */
                if (initiatorPatientId == null || initiatorPatientId.isEmpty()) {
                    result = queryDocument.queryForDocuments(ipAddress, localHCID, remoteHCID, configFile);
                }
                else {
                    result = queryDocument.queryForDocuments(ipAddress, localHCID, remoteHCID, configFile, initiatorPatientId);
                }

                if (result == null) {
                    log.info("queryDocument.queryForDocuments() result is null");
                }
            }
        } catch (Exception ex) {
            log.error("Exception", ex);
            throw new ServiceException(ex, ServiceException.ERROR_CODE_GLOBAL_DEFAULT);
        }
        return result;
    }


    /**
     * Execute the Retrieve Document service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>selectedAltScenarioCaseId = 0</li>
     * <li>initiatorPatientId = null</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
    public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId) throws ServiceException {
        return this.executeRetrieveDoc(participant, scenarioId, caseId, 0, ((String)null));
    }

    /**
     * Execute the Retrieve Document service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>initiatorPatientId = null</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
    public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId) throws ServiceException {
        return this.executeRetrieveDoc(participant, scenarioId, caseId, selectedAltScenarioCaseId, ((String)null));
    }

    /**
     * Execute the Retrieve Document service
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>selectedAltScenarioCaseId = 0</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param initiatorPatientId
     * @return
     * @throws ServiceException
     */
    public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId, String initiatorPatientId) throws ServiceException {
        return this.executeRetrieveDoc(participant, scenarioId, caseId, 0, initiatorPatientId);
    }

    /**
     * Execute the Retrieve Document service using initiatorPatientId
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
    //Overloaded executeRetrieveDoc method with extra initiatorPatientId argument - MK
    public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId, String initiatorPatientId) throws ServiceException {
        log.info("ScenarioCaseManager.executeRetrieveDoc(): parameters passed are as below:\n" +
                   "scenarioId=" + scenarioId + " caseId=" + caseId + " " +
                   "selectedAltScenarioCaseId=" + selectedAltScenarioCaseId + " initiatorPatientId=" + initiatorPatientId);

        String ipAddress = null;
        String localHCID = null;
        Map<String, String> endpoints = null;
        Map<String, String> remoteHCID = null;
        Map<Object, Object> requestData = null;
        String version = LabConstants.Connect_Version_248;

        RDEntityResponseType docResult = null;

        try {
            //requestData = fetchRequestData(participant, scenarioId, caseId);

            ipAddress = requestData.get("ipAddress").toString();
            localHCID = requestData.get("localHCID").toString();
            endpoints = (Map<String, String>) requestData.get("endpoints");
            remoteHCID = testHarnessriService.getCommunityIdsByVersion(version);

            if (remoteHCID == null) {
                log.info("Remote HCID list is null");
            } else {
                // ADD CURRENT PARTICIPANT TO remoteHCID MAP WITH ID = 99 -- THIS SUPPORTS RESPONDER SCENARIO CASES
                remoteHCID.put("99", participant.getCommunityId());
            }

            /*
             * Determine whether to use "happy path" config file or "alternate rainy path" config file
             */
            String configFile = null;
            if (0 == selectedAltScenarioCaseId) {
                configFile = scenariocaseService.getPatientDataFromConfig(scenarioId, caseId); // happy path
            }
            else {
                configFile = altscenariocaseService.getPatientDataFromConfig(scenarioId, caseId, selectedAltScenarioCaseId); // rainy path
            }

            if (configFile == null) {
                log.warn("Scenario Case Configuration file is null");
            } else {
                log.info("Retrieve Document XML: " + configFile.toString());
                // Check for valid endpoints
                if (endpoints != null) {
                    // Locate RD endpoint
                    String endpoint = endpoints.get("RD");
                    // If endpoint not empty, assign to ipAddress
                    if (endpoint != null && !endpoint.equals("")) {
                        ipAddress = endpoint;
                    }
                }

                docResult = new RDEntityResponseType();
                RetrieveDocument retrieveDoc =ServiceFactory.getInstance().getRDService(LabConstants.Connect_Version_248);;
                /*
                 * Determine RD call based on Initiator Patient value
                 */
                if (initiatorPatientId == null || initiatorPatientId.isEmpty()) {
                    docResult = retrieveDoc.retrieveDocuments(ipAddress, localHCID, remoteHCID, configFile);
                }
                else {
                    docResult = retrieveDoc.retrieveDocuments(ipAddress, localHCID, remoteHCID, configFile, initiatorPatientId);
                }

                if (docResult == null) {
                    log.info("retrieveDoc.retrieveDocuments() is null");
                }
            }
        } catch (Exception ex) {
            log.error("Exception", ex);
            throw new ServiceException(ex, ServiceException.ERROR_CODE_GLOBAL_DEFAULT);
        }

        return docResult;
    }


     /**
     * Execute the Retrieve Document service using map of docAndRepIds with "happy" path
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>selectedAltScenarioCaseId = 0</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param initiatorPatientId
     * @return
     * @throws ServiceException
     */
    public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId, Map<String, String> docAndRepIds,Map<String, String> passedDocAndRepIds) throws ServiceException {
        return this.executeRetrieveDoc(participant, scenarioId, caseId, 0, docAndRepIds,passedDocAndRepIds);
    }
    /**
     * Execute the Retrieve Document service using map of docAndRepIds with "happy" path
     * <p>Overloadded to assign the following default values:
     * <ul>
     * <li>selectedAltScenarioCaseId = 0</li>
     * </ul>
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param initiatorPatientId
     * @return
     * @throws ServiceException
     */
    public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId, Map<String, String> docAndRepIds) throws ServiceException {
        return this.executeRetrieveDoc(participant, scenarioId, caseId, 0, docAndRepIds);
    }

     /**
     * Execute the Retrieve Document service using map of docAndRepIds with "happy" or "rainy" path both.
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
     public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId, Map<String, String> docAndRepIds) throws ServiceException {

         return executeRetrieveDoc(participant, scenarioId, caseId, selectedAltScenarioCaseId, docAndRepIds, null);
     }
//
    /**
     * Execute the Retrieve Document service using map of docAndRepIds with "happy" or "rainy" path both.
     * @param participant
     * @param scenarioId
     * @param caseId
     * @param Map<String, String> passedDocAndRepIds
     * @return
     * @throws ServiceException
     */
    //Overloaded executeRetrieveDoc method for lab responder scenarios - MK
    public RDEntityResponseType executeRetrieveDoc(Participant participant, int scenarioId, int caseId, int selectedAltScenarioCaseId, Map<String, String> docAndRepIds,Map<String, String> passedDocAndRepIds) throws ServiceException {
        log.info("ScenarioCaseManager.executeRetrieveDoc(): parameters passed are as below:\n" +
                   "scenarioId=" + scenarioId + " caseId=" + caseId + " " +
                   "selectedAltScenarioCaseId=" + selectedAltScenarioCaseId + " " +
                   "docAndRepIds=" + ((null == docAndRepIds) ? null : "NOT NULL"));

        String ipAddress = null;
        String localHCID = null;
        Map<String, String> endpoints = null;
        Map<String, String> remoteHCID = null;
        Map<Object, Object> requestData = null;
        String version = LabConstants.Connect_Version_248;

        RDEntityResponseType docResult = null;

        try {
           // requestData = fetchRequestData(participant, scenarioId, caseId);

            ipAddress = requestData.get("ipAddress").toString();
            localHCID = requestData.get("localHCID").toString();
            endpoints = (Map<String, String>) requestData.get("endpoints");
            remoteHCID = testHarnessriService.getCommunityIdsByVersion(version);

            if (remoteHCID == null) {
                log.info("Remote HCID list is null");
            } else {
                // ADD CURRENT PARTICIPANT TO remoteHCID MAP WITH ID = 99 -- THIS SUPPORTS RESPONDER SCENARIO CASES
                remoteHCID.put("99", participant.getCommunityId());
            }

            /*
             * Determine whether to use "happy path" config file or "alternate rainy path" config file
             */
            String configFile = null;
            if (0 == selectedAltScenarioCaseId) {
                configFile = scenariocaseService.getPatientDataFromConfig(scenarioId, caseId); // happy path
            }
            else {
                configFile = altscenariocaseService.getPatientDataFromConfig(scenarioId, caseId, selectedAltScenarioCaseId); // rainy path
            }

            if (configFile == null) {
                log.warn("Scenario Case Configuration file is null");
            } else {
                log.info("Retrieve Document XML: " + configFile.toString());
                // Check for valid endpoints
                if (endpoints != null) {
                    // Locate RD endpoint
                    String endpoint = endpoints.get("RD");
                    // If endpoint not empty, assign to ipAddress
                    if (endpoint != null && !endpoint.equals("")) {
                        ipAddress = endpoint;
                    }
                }

                docResult = new RDEntityResponseType();
                RetrieveDocument retrieveDoc = ServiceFactory.getInstance().getRDService(LabConstants.Connect_Version_248);
                if(passedDocAndRepIds!=null && passedDocAndRepIds.size() > 0){
                    docAndRepIds = passedDocAndRepIds ;
                }
                docResult = retrieveDoc.retrieveDocuments(ipAddress, localHCID, remoteHCID, configFile, docAndRepIds,null);
                if (docResult == null) {
                    log.info("retrieveDoc.retrieveDocuments() is null");
                }
            }
        } catch (Exception ex) {
            log.error("Exception", ex);
            throw new ServiceException(ex, ServiceException.ERROR_CODE_GLOBAL_DEFAULT);
        }

        return docResult;
    }


    /**
     * Execute (discover) the result(s) related to the Supplemental Test Case
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     *//*
    public String executeSupplemental(Participant participant, int scenarioId, int caseId, int caseExecutionId) throws ServiceException {
        log.info("ScenarioCaseManager.executeSupplemental() - INFO");

        String result = null;

        try {
            // Patient data (config) contains supplemental result criteria
            File configFile = scenariocaseService.getPatientData(scenarioId, caseId);

            // Call Supplemental to get Patient Data (Config) Java Class
            Supplemental supplemental = new Supplemental();
            PatientSP patientConfig = supplemental.getPatientInfo(configFile);

            int passCount = 0;
            Caseresult caseResult = null;
            if (patientConfig != null && patientConfig.getCheckCases() != null && patientConfig.getCheckCases().size() > 0) {
                for (CheckCase checkCase : patientConfig.getCheckCases()) {
                    if (checkCase != null && checkCase.getMessageType() != null && checkCase.getMessageType().equals("PD")) {
                        log.info("checkCase.messageType is PD");
                        caseResult = caseResultService.findByParticipantForActivePassPD(participant.getParticipantId(), checkCase.getInitiatorInd(), checkCase.getResponderInd());
                    } else if (checkCase != null && checkCase.getMessageType() != null && checkCase.getMessageType().equals("QD")) {
                        log.info("checkCase.messageType is QD");
                        caseResult = caseResultService.findByParticipantForActivePassQD(participant.getParticipantId(), checkCase.getInitiatorInd(), checkCase.getResponderInd());
                    } else if (checkCase != null && checkCase.getMessageType() != null && checkCase.getMessageType().equals("RD")) {
                        log.info("checkCase.messageType is RD");
                        caseResult = caseResultService.findByParticipantForActivePassRD(participant.getParticipantId(), checkCase.getInitiatorInd(), checkCase.getResponderInd());
                    } else if (checkCase != null && checkCase.getMessageType() != null && checkCase.getMessageType().equals("ANY")) {
                        log.info("checkCase.messageType is ANY");
                        caseResult = caseResultService.findByParticipantForActivePass(participant.getParticipantId(), checkCase.getInitiatorInd(), checkCase.getResponderInd());
                    }

                    if (caseResult != null && caseResult.getResult() != null && caseResult.getResult().equals(Caseresult.RESULT_PASS)) {
                        passCount++;
                    }
                }

                // Compare passCount to checkCase.size()
                if (passCount > 0 && passCount == patientConfig.getCheckCases().size()) {
                    result = Caseresult.RESULT_PASS;
                }
                if (passCount > 0 && passCount < patientConfig.getCheckCases().size()) {
                    result = Caseresult.RESULT_PROGRESS;
                } else if (passCount == 0) {
                    result = Caseresult.RESULT_FAIL;
                }

                // If valid result value, save new Case Result for the Supplemental Test Case
                if (result != null) {
                    log.info("result is " + result);
                    // Write new Case Result
                    Caseresult newCaseResult = new Caseresult();
                    // set case execution for case result
                    Caseexecution caseExec = new Caseexecution();
                    caseExec.setCaseExecutionId(caseExecutionId);
                    newCaseResult.setCaseexecution(caseExec);

                    newCaseResult.setExecuteTime(new Timestamp(System.currentTimeMillis()));

                    newCaseResult.setResult(result);
                    String message = null;
                    if (result.equals(Caseresult.RESULT_PASS)) {
                        message = "Supplemental discovery successful";
                    }
                    if (result.equals(Caseresult.RESULT_PROGRESS)) {
                        message = "Supplemental discovery returned PROGRESS result - " + passCount + " out of " + patientConfig.getCheckCases().size() + " required test cases PASSED";
                    }
                    if (result.equals(Caseresult.RESULT_FAIL)) {
                        message = "Supplemental discovery returned zero(0) matches";
                    }
                    newCaseResult.setMessage(message);

                    newCaseResult.setSubmissionInd("N");

                    caseResultService.create(newCaseResult);
                }
            }
        } catch (Exception ex) {
            log.error("Exception", ex);
            throw new ServiceException(ex, ServiceException.ERROR_CODE_GLOBAL_DEFAULT);
        }
        return result;
    }*/
    
    
    /**
     * Return mapped Participant and Test Harness information
     * @param participant
     * @param scenarioId
     * @param caseId
     * @return
     * @throws ServiceException
     */
    public Map<Object, Object> fetchRequestData(Participant participant, Caseexecution caseExecution, int scenarioId, int caseId) throws ServiceException {
        log.info("ScenarioCaseManager.fetchParticipantData() - INFO");

        String ipAddress = null;
        String localHCID = null;
        Map<String, String> endpoints = new HashMap<String, String>();
        List<String> remoteHCID = new ArrayList<String>();
        Map<Object, Object> requestData = new HashMap<Object, Object>();
        String[] participantRIs = caseExecution.getParticipatingRIs().split(LabConstants.SPLITTER); //scenariocaseService.getParticipatingRIs(scenarioId, caseId);

        try {
            Testcase testcase = testCaseService.read(caseId);
            Testharnessri testHarnessri = null;
            if (testcase != null) {
                if ("Y".equalsIgnoreCase(testcase.getInitiatorInd())) {
                    // Initiator Test Case - populate endpoints and local HCID from participant; remote HCID(s) from participating test harness RIs
                    ipAddress = participant.getIpAddress();
                    localHCID = participant.getCommunityId();
                    log.info("Initiator Test Case - Participant IP: " + ipAddress + "; Local HCID: " + localHCID);
                    List<Participantendpoint> participantendpoints = participant.getParticipantendpoints();
                    for (Participantendpoint endpoint : participantendpoints) {
                        endpoints.put(endpoint.getMessageType(), endpoint.getEndpoint());
                        log.info("Initiator Test Case - Participant Endpoint[" + endpoint.getMessageType() + "]: " + endpoint.getEndpoint());
                    }
                    for (String ri : participantRIs) {
                        testHarnessri = new Testharnessri();
                        testHarnessri = testHarnessriService.read(Integer.valueOf(ri));
                        remoteHCID.add(testHarnessri.getCommunityId());
                        log.info("Initiator Test Case - Test Harness IP: " + testHarnessri.getIpAddress() + "; Remote HCID: " + testHarnessri.getCommunityId());
                    }
                } else if ("Y".equalsIgnoreCase(testcase.getResponderInd())) {
                    // Responder Test Case - populate endpoints and local HCID from participating test harness RI; remote HCID(s) from participant
                    testHarnessri = new Testharnessri();
                    testHarnessri = testHarnessriService.read(Integer.valueOf(participantRIs[0]));
                    ipAddress = testHarnessri.getIpAddress();
                    localHCID = testHarnessri.getCommunityId();
                    log.info("Responder Test Case - Test Harness IP: " + ipAddress + "; Local HCID: " + localHCID);
                    List<Testharnessendpoint> testharnessendpoints = testHarnessri.getTestharnessendpoints();
                    for (Testharnessendpoint endpoint : testharnessendpoints) {
                        endpoints.put(endpoint.getMessageType(), endpoint.getEndpoint());
                        log.info("Initiator Test Case - Test Harness Endpoint[" + endpoint.getMessageType() + "]: " + endpoint.getEndpoint());
                    }
                    remoteHCID.add(participant.getCommunityId());
                    log.info("Responder Test Case - Participant IP: " + participant.getIpAddress() + "; Remote HCID: " + participant.getCommunityId());
                }
            }
        } catch (Exception ex) {
            throw new ServiceException(ex, ServiceException.ERROR_CODE_GLOBAL_DEFAULT);
        }
        requestData.put("ipAddress", ipAddress);
        requestData.put("localHCID", localHCID);
        requestData.put("endpoints", endpoints);
        requestData.put("remoteHCID", remoteHCID);

        return requestData;
    }

    public void updateScenarioCase(Scenariocase scenarioCase) throws ServiceException  {
        log.info("ScenarioCaseManager.updateTestcase()");
        try {
            scenariocaseService.update(scenarioCase);
        } catch (ServiceException se) {
            log.error("ERROR: failure updating scenarioCase", se);
            se.setErrorCode("errors.update.scenariocase.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure updating scenarioCase", e);
            ServiceException se = new ServiceException("Failure updating scenarioCase", "errors.update.scenariocase.failed", e);
            se.setLogged();
            throw se;
        }
    }

    /**
     *
     * @param participant
     * @param dependentScenarioId
     * @param dependentCaseId
     * @return
     * @throws ServiceException
     */
    public  Caseexecution findByParticipantForActiveScenarioCase(Participant participant, Integer dependentScenarioId, Integer dependentCaseId) throws  ServiceException
    {
        log.info("ScenarioCaseManager.findByParticipantForActiveScenarioCase()");
        Caseexecution caseexecution = null;
        try {
             caseexecution = caseExecutionService.findByParticipantForActiveScenarioCase(participant.getParticipantId(), dependentScenarioId, dependentCaseId);
        } catch (ServiceException se) {
            log.error("ERROR: failure findByParticipantForActiveScenarioCase", se);
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure findByParticipantForActiveScenarioCase", e);
            ServiceException se = new ServiceException("Failure findByParticipantForActiveScenarioCase", e);
            se.setLogged();
            throw se;
        }
        return caseexecution ;
     }

	public String getScenarioCaseConfig(int scenarioId, int caseId) throws ServiceException {
		return this.scenariocaseService.getPatientDataFromConfig(scenarioId, caseId);
	}
}
