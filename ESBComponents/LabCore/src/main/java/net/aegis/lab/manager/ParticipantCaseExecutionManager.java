/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.manager;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.caseexecution.service.CaseExecutionService;
import net.aegis.lab.caseexecution.service.CaseExecutionServiceImpl;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.resultdocument.service.ResultDocumentService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author Jyoti.Devarakonda
 */
public class ParticipantCaseExecutionManager {

    private static ParticipantCaseExecutionManager instance;
    private CaseExecutionService caseExecutionService = (CaseExecutionService) ContextUtil.getLabContext().getBean("caseExecutionService");
    private ParticipantService participantService = (ParticipantService) ContextUtil.getLabContext().getBean("participantService");
    private ResultDocumentService resultDocumentService = (ResultDocumentService) ContextUtil.getLabContext().getBean("resultDocumentService");
 
    /**
     * @return ParticipantCaseExecutionManager
     */
    public static ParticipantCaseExecutionManager getInstance() {
        if (instance == null) {
            instance = new ParticipantCaseExecutionManager();
        } 
        // For testing Exception handling 
        /*if(1==1){
        	throw new ServiceException("ParticipantCaseExecutionManager exception occured", "LC_0001");
        }*/
        return instance;
    }

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(CaseExecutionServiceImpl.class);

    public int getParticipantCaseExecutionIdByCommId(String messageType, String communityId, String participantObjectID, String status, String userName, Timestamp execTime, Integer testHarnessId) {
        log.info("CaseExecutionServiceImpl.findByMessageTypeAndPatientId() - INFO");
        int caseExecId = -1;
        try {
            if ((StringUtils.isEmpty(messageType)) || (messageType == null) || (communityId == null) ||
                    participantObjectID == null || status == null) {

                log.error("Message/communityId/ipAddress/patientId value is null");
                caseExecId = -1;

            } else {
                log.info("assigning authority id --->> " + communityId);
                List<Participant> participantList = participantService.getActiveCandiateByCommunityId(communityId, status);
                log.info("participant list --- >> " + participantList.size());
                if (participantList != null && participantList.size() > 0) {
                    // Issue: Multiple RI request processing on single RI test case --> Logic now includes check for participating RIs
                    log.info("participant is --- >> " + participantList.get(0).getParticipantId());
                    Caseexecution caseExec = caseExecutionService.getCaseExecByMsgTypeAndPatientId(messageType, participantObjectID, participantList.get(0), userName, execTime,testHarnessId);
                    if (caseExec != null) {
                        // Check testHarnessId against caseExecution participating RIs
                        if (testHarnessId != null) {
                            String testHarness = testHarnessId.toString();
                            if (caseExec.getParticipatingRIs().indexOf(testHarness) >= 0) {
                                // MATCH
                                caseExecId = caseExec.getCaseExecutionId();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("ERROR: failure getting participant case execution id.", ex);
        }
        return caseExecId;
    }

    public Caseexecution getCaseExecutionById(Integer caseExecutionId) throws ServiceException {
        log.info("CaseExecutionServiceImpl.getCaseExecutionById() - INFO");

        Caseexecution caseexecution = null;
        try {
            if (caseExecutionId != null) {
                caseexecution = caseExecutionService.findByCaseExecutionId(caseExecutionId);
            }
        } catch (Exception ex) {
            log.error("ERROR: failure getting participant case execution.", ex);
            throw new ServiceException(ex);
        }

        return caseexecution;
    }

    public Resultdocument getResultDocumentById(Integer resultDocumentId) throws ServiceException {
        log.info("CaseExecutionServiceImpl.getResultDocumentById() - INFO");

        Resultdocument resultdocument = null;
        try {
            if (resultDocumentId != null) {
                resultdocument = resultDocumentService.read(resultDocumentId);
            }
        } catch (Exception ex) {
            log.error("ERROR: failure getting result document.", ex);
            throw new ServiceException(ex);
        }

        return resultdocument;
    }

     public List<Caseexecution> findByScenarioExecutionId(int scenarioExecutionId) throws ServiceException {
        log.info("CaseExecutionServiceImpl.findByScenarioExecutionId() - INFO");

        List<Caseexecution> caseExecutions = null;
        try {
                caseExecutions = caseExecutionService.findByScenarioExecutionId(scenarioExecutionId);

        } catch (Exception ex) {
            log.error("ERROR: failure getting caseexecution list for the given scenarioExecutionId.", ex);
            throw new ServiceException(ex);
        }

        return caseExecutions;
    }
  
     public void updateCaseexecution(Caseexecution caseExecution) throws ServiceException  {
        log.info("ParticipantCaseExecutionManager.updateCaseexecution()");
        try {
            caseExecutionService.update(caseExecution);
        } catch (ServiceException se) {
            log.error("ERROR: failure updating caseExecution", se);
            se.setErrorCode("errors.update.caseExecution.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure updating caseExecution", e);
            ServiceException se = new ServiceException("Failure updating caseExecution", "errors.update.caseExecution.failed", e);
            se.setLogged();
            throw se;
        }
    }

      public java.sql.Timestamp getMaxStartTimer() throws ServiceException {
        log.info("CaseExecutionServiceImpl.getMaxStartTimer() - INFO");

       java.sql.Timestamp startTimer = null;
        try {
                startTimer = caseExecutionService.findByMaxStartTimer();

        } catch (Exception ex) {
            log.error("ERROR: failure getting max start timer from case execution.", ex);
            throw new ServiceException(ex);
        }

        return startTimer;
    }
      
	public String getParticipantPatientIdForRD(Integer participantId, String communityId,String documentUniqueId) throws ServiceException {
		log.info("getParticipantPatientIdForRD()");
		return caseExecutionService.getParticipantPatientIdForRD(participantId,communityId, documentUniqueId);

	}
}

 



