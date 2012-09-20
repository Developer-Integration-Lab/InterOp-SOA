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
