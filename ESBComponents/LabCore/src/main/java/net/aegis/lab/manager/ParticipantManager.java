package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants.LabType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParticipantManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);
    private static ParticipantManager instance;
    private ParticipantService participantService = (ParticipantService) ContextUtil.getLabContext().getBean("participantService");

    private ParticipantManager() {
    }

    /**
     * @return ParticipantManager
     */
    public static ParticipantManager getInstance() {
        if (instance == null) {
            instance = new ParticipantManager();
        }
        return instance;
    }

    /**
     * Return all Participants for the NHIN Rep
     * @param nhinRepId
     * @return List<Participant> found for NHIN Rep
     * @throws ServiceException
     */
    public List<Participant> findByNhinRepId(Integer nhinRepId) throws ServiceException {
        log.info("ParticipantManager.findByNhinRepId() - INFO");

        List<Participant> results = null;

        try {
            results = participantService.findByNhinRepId(nhinRepId);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding participants for nhin rep.", se);
            se.setErrorCode("errors.find.participants.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding participants for nhin rep.", e);
            ServiceException se = new ServiceException("Failure finding participants for nhin rep", "errors.find.participants.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }

    /**
     * Requirement: An NHIN Rep needs to choose a participant from the list of participants and
     * associate that participant with himself (a.k.a. herself).
     *
     * @param pstrStatus. For ex - status='A' (active)
     * @return List<Participant> Participants whose status match with the one queried for.
     * @throws ServiceException
     */
    public List<Participant> findByStatus(String pstrStatus) throws ServiceException {
        log.info("ParticipantManager.findByStatus(pstrStatus) - INFO");
        List<Participant> objListParticipants = null;

        try {
            objListParticipants = participantService.findByStatus(pstrStatus);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding ALL participants with the requested status.", se);
            se.setErrorCode("errors.find.participants.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding ALL participants with the requested status.", e);
            ServiceException se = new ServiceException("Failure finding ALL participants with the requested status", "errors.find.participants.failed", e);
            se.setLogged();
            throw se;
        }

        return objListParticipants;
    }

    /**
     * Return active scenario executions for the Participant
     * @param participantId
     * @return List<Scenarioexecution> found for Participant
     * @throws ServiceException
     */
    public List<Scenarioexecution> activeScenarioExecutions(Integer participantId) throws ServiceException {
        return activeScenarioExecutions(participantId, LabType.OPTION_ALL);
    }

    /**
     *
     * @param participantId
     * @param labType
     * @return
     * @throws ServiceException
     */
    public List<Scenarioexecution> activeScenarioExecutions(Integer participantId, LabType labType) throws ServiceException {
        log.info("ParticipantManager.activeScenarios() - INFO");

        List<Scenarioexecution> results = null;

        try {
            if (labType == LabType.OPTION_ACTIVE_LAB_ONLY) {
                // If labType is ACTIVE_LAB_ONLY, attempt to retrieve each labType until results are found
                results = participantService.findScenarioExecutionByParticipantIdAndStatus(participantId, "ACTIVE", LabType.CONFORMANCE);
                if (results == null || results.isEmpty()) {
                    results = participantService.findScenarioExecutionByParticipantIdAndStatus(participantId, "ACTIVE", LabType.LAB);
                }
            }
            else {
                // Retrieve specified labType results
                results = participantService.findScenarioExecutionByParticipantIdAndStatus(participantId, "ACTIVE", labType);
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding active scenarios.", se);
            se.setErrorCode("errors.active.scenarios.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding active scenarios.", e);
            ServiceException se = new ServiceException("Failure finding active scenarios", "errors.active.scenarios.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }

    /**
     * Return the specific scenario execution for the Participant
     * @param scenarioExecutionId
     * @return Scenarioexecution found for Participant
     * @throws ServiceException
     */
    public Scenarioexecution returnScenarioExecution(Integer scenarioExecutionId) throws ServiceException {
        log.info("ParticipantManager.returnScenarioExecution() - INFO");

        List<Scenarioexecution> results = null;
        Scenarioexecution result = null;

        try {
            results = participantService.findScenarioExecutionByScenarioExecutionId(scenarioExecutionId);
            if (results != null && results.size() > 0) {
                result = results.get(0);
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding scenario.", se);
            se.setErrorCode("errors.return.scenario.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding active scenarios.", e);
            ServiceException se = new ServiceException("Failure finding scenario", "errors.return.scenario.failed", e);
            se.setLogged();
            throw se;
        }

        return result;
    }

    /**
     * Requirement: An NHIN REP needs to be able to register a new participant.
     *
     * @param pUser - a corresponding user for the newly created participant.
     * @param pParticipant - participant object that contains all the relevant information to save participant in database.
     * @return Participant - the newly created participant
     * @throws ServiceException
     */
    public Participant registerParticipant(User pUser, Participant pParticipant) throws ServiceException {

        log.info("ParticipantManager.registerParticipant() - INFO");

        User user = null;
        Participant participant = null;

        user = pUser;               // null check done at the service layer
        participant = pParticipant;     // null check done at the service layer

        // now, invoke the service layer code to register new participant and create a user
        participant = participantService.registerParticipant(user, participant);

        return participant;
    }

    /**
     * Requirement: The logged in Nhin Rep is able to choose participants from participant list
     *              and assign them to himself (a.k.a. herself).
     *
     * @param pnhinRepId           Nhin Rep ID from JSP. Must match the nhin rep id of this instace
     * @param parrParticipantIds     Participant list from JSP.
     * @return int                 Number of participants assigned to the nhin rep
     * @throws ServiceException    Thrown when a business rule violated and other causes.
     */
    public int assignParticipants(Integer pnhinRepId, String[] parrParticipantIds) throws ServiceException {

        log.info("ParticipantManager.assignParticipants() - INFO");

        int iNumParticipantsAssigned = 0;

        // now, invoke the service layer code to assign participants to the nuin rep
        iNumParticipantsAssigned = participantService.assignParticipants(pnhinRepId, parrParticipantIds);

        return iNumParticipantsAssigned;
    }

    /**
     * Requirement: An NHIN REP chooses a participant to work with.
     *
     * @param pParticipantId - id to use in order to find the participant
     * @return Participant - participant found in the DB that will work with the nhin rep.
     * @throws ServiceException
     */
    public Participant findByParticipantId(Integer pParticipantId) throws ServiceException {

        log.info("ParticipantManager.findByParticipantId() - INFO");

        Participant participant = null;

        // now, invoke the service layer code to find the participant
        participant = participantService.findByParticipantId(pParticipantId);

        return participant;
    }

    public void updateParticipant(Participant participant) throws ServiceException {
        log.info("ParticipantManager.updateParticipantCommVerifyStatus-->>");
        try {
             log.info("ParticipantManager.updateParticipantCommVerifyStatus-->>Updating..");
            participantService.update(participant);
            log.info("ParticipantManager.updateParticipantCommVerifyStatus-->>Updated..");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    /**
     *
     * @param communityId
     * @param labType
     * @return
     * @throws ServiceException
     */
    public boolean isParticipantOfTestLab(String communityId, LabType labType) throws ServiceException {
        final List<Participant> cList = participantService.findByCommunityId(communityId, labType);
        if (cList != null && cList.size()>0)
            return true;
        return false;
    }
    
    
    /**
     * @param communityId
     * @param status
     * @return
     * @throws ServiceException
     */
    public List<Participant> getActiveCandiateByCommunityId(String communityId, String status) throws ServiceException {
        List<Participant> results = null;
        try {
                log.info("getActiveCandiateByIPAddrAndAssignAutId - Search by Criteria");
                results = participantService.getActiveCandiateByCommunityId(communityId, status);

        } catch (Exception e) {
            // throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            e.printStackTrace();
        }
        return results;
    }

}
