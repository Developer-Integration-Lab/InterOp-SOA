package net.aegis.lab.participant.service;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.altscenariocase.service.AltscenariocaseService;
import net.aegis.lab.dao.ParticipantDAO;
import net.aegis.lab.dao.ScenarioexecutionDAO;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Role;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dao.pojo.Userrole;
import net.aegis.lab.dao.pojo.Userrole.UserrolePK;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.nhinrep.service.NhinrepService;
import net.aegis.lab.user.service.UserService;
import net.aegis.lab.userrole.service.UserroleService;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.LabConstants.LabType;

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
 * @author Ram Ghattu
 *
 */
@Service("participantService")
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantDAO participantDAO;
    @Autowired
    private ScenarioexecutionDAO scenarioexecutionDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private NhinrepService nhinrepService;
    @Autowired
    private UserroleService userroleService;
    @Autowired
    private AltscenariocaseService altscenariocaseService;

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ParticipantServiceImpl.class);


    /**
     * Requirement: An NHIN REP needs to be able to register a new participant.
     *
     * @param pUser - a corresponding user for the newly created participant.
     * @param pParticipant - participant object that contains all the relevant information to save participant in database.
     * @return Participant - the newly created participant
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Participant registerParticipant(User pUser, Participant pParticipant) throws ServiceException {

        log.info("ParticipantServiceImpl.registerParticipant(user, participant) - INFO");

        User user = null;
        Integer userId = -999;
        Role role = null;
        Userrole userrole = null;
        UserrolePK userrolePK = new Userrole.UserrolePK();
        Participant participant = null;
        Integer participantID = -999;

        // check if parameters passed by the view layer are ok.
        if (null == pUser || null == pParticipant) {
            throw new ServiceException("ParticipantServiceImpl.registerParticipant(user, participant): parameter is null",
                    ServiceException.ERROR_MESSAGE_INVALID_ARGUMENT);
        }

        // Lab should not have an existing user with the same name in the database for registration to succeed.
        User tempObjUser = null;
        tempObjUser = userService.findByUsername(pUser.getUsername());
        if (null != tempObjUser) {
            log.error("ParticipantServiceImpl.registerParticipant(user, participant): User with user name=" + pUser.getUsername() + " already exists in the database.");
            return null;
        }

        // now, proceed to register a participant
        user = pUser;               // not null
        participant = pParticipant;     // not null

        try {
            // step 1: create a user first
            userId = userService.create(user);

            // step 2: create a userrole
            role = new Role();
            role.setRoleId(LabConstants.ROLE_PARTICIPANT); // 5 - Participant role
            userrole = new Userrole();
            userrolePK.setRoleId(role.getRoleId());
            userrolePK.setUserId(userId);                   // newly created user
            userrole.setUserrolePK(userrolePK);
            userrolePK = userroleService.create(userrole);

            // step 3: now, register the participant
            participantID = this.create(participant);
        }
        catch (Exception e) {
            log.error("ERROR: failure registering a new participant for nhin rep.", e);
            ServiceException se = new ServiceException("Failure registering new participant by nhin rep", "errors.register.participant.failed", e);
            se.setLogged();
            throw se;
        }

        log.info("ParticipantServiceImpl.registerParticipant(user, participant): User created for the newly registering participant: user id=" + userId);
        log.info("ParticipantServiceImpl.registerParticipant(user, participant): Newly registered participant: participant id=" + participantID);

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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public int assignParticipants(Integer pnhinRepId, String[] parrParticipantIds) throws ServiceException {

        log.info("ParticipantServiceImpl.assignParticipants() - INFO");

        int iNumParticipantsAssigned = 0;
        int iNumParticipantsDeAssigned = 0;
        Nhinrep objNhinRep = null;              // expected: logged-in Nhin Rep
        List<Participant> objListParticipantsThatWereAlreadyAssignedToNhinRep = null;
        Participant objParticipantForAssigningNhinrep = null;

        // check nominal parameter validity
        if ((null == pnhinRepId) || (null == parrParticipantIds)) {
            log.error("ParticipantServiceImpl.assignParticipants() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ParticipantServiceImpl.assignParticipants() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        // read Nhin Rep from database and keep it ready for assignment with participant(s)
        objNhinRep = nhinrepService.findByNhinRepId(pnhinRepId);
        // check whether nhin rep id passed in was indeed valid.
        if (null == objNhinRep) {
            log.error("ParticipantServiceImpl.assignParticipants() - Nhin Rep Id passed is not found at the service layer. - ERROR");
            ServiceException se = new ServiceException("ParticipantServiceImpl.assignParticipants() - Nhin Rep Id passed is not found at the service layer. Will not process further.");
            se.setLogged();
            throw se;
        }


        // first find all the participants that were already assigned to this Nhin Rep
        // and set their nhin rep ids to null (a shortcut method)
        objListParticipantsThatWereAlreadyAssignedToNhinRep = this.findByNhinRepIdAndStatus(objNhinRep.getNhinRepId(), LabConstants.STATUS_ACTIVE);

        log.info("ParticipantServiceImpl.assignParticipants() - starting to process participants...");

        if(null != objListParticipantsThatWereAlreadyAssignedToNhinRep) {
             for(Participant objParticipantThatWasAlreadyAssigned : objListParticipantsThatWereAlreadyAssignedToNhinRep) {
                try {
                    if (null != objParticipantThatWasAlreadyAssigned) {
                        // update the participant's Nhin rep id to null first (a shortcut method)
                        objParticipantThatWasAlreadyAssigned.setNhinRepId(null);
                        iNumParticipantsDeAssigned++;
                    }
                }
                catch (Exception e) {
                    log.error("ERROR: failure during de-assigning participant to the nhin rep.", e);
                    ServiceException se = new ServiceException("Failure during de-assigning participant to the nhin rep", "errors.assign.participants.failed", e);
                    se.setLogged();
                    throw se;
                }
             }

             if ((null != parrParticipantIds) && (parrParticipantIds.length == 0)) {    // the view (JSP) did not select any.
                 log.info("ParticipantServiceImpl.assignParticipants(): De-assigned ALL participants because the VIEW (JSP) did not select any. " +
                          "Exact number of de-assigned participants=" + iNumParticipantsDeAssigned + ". - INFO");
                 return iNumParticipantsDeAssigned;
             }

        }

        // Second, assign all participants passed in to the Nhin Rep
        for(int i=0; i<parrParticipantIds.length; i++) {

            try {
                 // first find the participant
                Integer id = Integer.parseInt(parrParticipantIds[i]);
                objParticipantForAssigningNhinrep = this.findByParticipantId(id);
                if (null != objParticipantForAssigningNhinrep) {
                    // update the participant's Nhin rep id
                    objParticipantForAssigningNhinrep.setNhinRepId(objNhinRep.getNhinRepId());
                    iNumParticipantsAssigned++;
                }
            }
            catch (Exception e) {
                log.error("ERROR: failure during assigning participant to the nhin rep.", e);
                ServiceException se = new ServiceException("Failure during assigning participant to the nhin rep", "errors.assign.participants.failed", e);
                se.setLogged();
                throw se;
            }
        }


        log.info("ParticipantServiceImpl.assignParticipants(): first de-assigned " + iNumParticipantsDeAssigned + " participants. - INFO");
        log.info("ParticipantServiceImpl.assignParticipants(): assigned " + iNumParticipantsAssigned + " participants. - INFO");

        return iNumParticipantsAssigned;
    }

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Participant newInstance) throws ServiceException {
        log.info("ParticipantServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = participantDAO.create(newInstance);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return id;
    }

    @Override
    public Participant read(Integer id) throws ServiceException {
        log.info("ParticipantServiceImpl.read() - INFO");
        Participant participant = null;
        try {
            participant = participantDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
           }
        return participant;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Participant transientObject) throws ServiceException {
        log.info("ParticipantServiceImpl.update() - INFO");
        try {
            participantDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Participant persistentObject) throws ServiceException {
        log.info("ParticipantServiceImpl.delete(persistentObject) - INFO");
        try {
            participantDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("ParticipantServiceImpl.delete(id) - INFO");
        try {
            participantDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /*
     * Finder methods
     */
    @Override
    public Participant findByParticipantId(int participantId) {
        log.info("ParticipantServiceImpl.findByParticipantId() - INFO");
        return participantDAO.read(participantId);
    }

    @Override
    public List<Participant> findByNhinRepId(Integer nhinRepId) throws ServiceException {
        log.info("ParticipantServiceImpl.findByNhinRepId() - INFO");
        List<Participant> objListParticipants = null;

        // first get a list of all participants given the nhin rep id to query with.
        objListParticipants = participantDAO.findByNhinRepId(nhinRepId);
        // assign Nhin rep name to individual Participant (reason: we removed nullable foreign key relationship in the database)
        objListParticipants = this.establishParticipantInstancesWithNhinrepNames(objListParticipants);

        return objListParticipants;
    }

    @Override
    public List<Participant> findByNhinRepIdAndStatus(Integer pnhinRepId, String pstrStatus) throws ServiceException {

        log.info("ParticipantServiceImpl.findByNhinRepIdAndStatus() - INFO");
        List<Participant> results = null;
        Participant criteria = null;

        if (null == pnhinRepId) {
            log.error("ParticipantServiceImpl.findByNhinRepIdAndStatus() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ParticipantServiceImpl.findByNhinRepIdAndStatus() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        criteria = new Participant();
        criteria.setNhinRepId(pnhinRepId);
        criteria.setStatus(pstrStatus);

        try {
            if (criteria != null) {
                log.info("ParticipantServiceImpl.findByNhinRepIdAndStatus() - Search by Criteria");
                results = participantDAO.searchByCriteria(criteria, this.executeCriteria(criteria));
            }
        } catch (Exception e) {
            // throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            e.printStackTrace();
        }

        // assign Nhin rep name to individual Participant (reason: we removed nullable foreign key relationship in the database)
        results = this.establishParticipantInstancesWithNhinrepNames(results);

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
    @Override
    public List<Participant> findByStatus(String pstrStatus) throws ServiceException {
        log.info("ParticipantServiceImpl.findByStatus(pstrStatus) - INFO");
        List<Participant> objListParticipants = null;

        // first get a list of all participants given a status to query with.
        objListParticipants = participantDAO.findByStatus(pstrStatus);

        // assign Nhin rep name to individual Participant (reason: we removed nullable foreign key relationship in the database)
        objListParticipants = this.establishParticipantInstancesWithNhinrepNames(objListParticipants);

        if (null != objListParticipants) {
            log.info("ParticipantServiceImpl.findByStatus(pstrStatus): Number of participants with status " + pstrStatus + " found="+objListParticipants.size());
        }

        return objListParticipants;
    }



    @Override
    public List<Participant> getActiveCandiateByIPAddrAndCommunityId(String communityId, String ipAddress, String status) throws ServiceException {
        log.info("PatientServiceImpl.search() - INFO");
        Participant criteria = new Participant();
        criteria.setIpAddress(ipAddress);
        criteria.setCommunityId(communityId);
        criteria.setStatus(status);
        List<Participant> results = null;

        try {
            if (criteria != null) {
                log.info("PatientServiceImpl.search() - Search by Criteria");
                results = participantDAO.searchByCriteria(criteria, this.executeCriteria(criteria));

            }
        } catch (Exception e) {
            // throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            e.printStackTrace();
        }

        // assign Nhin rep name to individual Participant (reason: we removed nullable foreign key relationship in the database)
        results = this.establishParticipantInstancesWithNhinrepNames(results);

        return results;
    }

    @Override
    public List<Scenarioexecution> findScenarioExecutionByScenarioExecutionId(Integer scenarioExecutionId) throws ServiceException {
        log.info("ParticipantServiceImpl.findScenarioExecutionByScenarioExecutionId() - INFO");

        List<Scenarioexecution> scenarios = null;

        try {
            scenarios = scenarioexecutionDAO.findByScenarioExecutionId(scenarioExecutionId);
            for (Scenarioexecution scenarioExec : scenarios) {
                List<Caseexecution> cases = scenarioExec.getCaseexecutions();
                for (Caseexecution caseExec : cases) {
                    caseExec.setAltscenariocases(altscenariocaseService.getSelectionByScenarioIdCaseId(scenarioExec.getScenario().getScenarioId(), caseExec.getTestcase().getCaseId()));
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return scenarios;
    }

    @Override
    public List<Scenarioexecution> findScenarioExecutionByParticipantIdAndStatus(Integer participantId, String status, LabType labType) throws ServiceException {
        log.info("ParticipantServiceImpl.findScenarioExecutionByParticipantIdAndStatus() - INFO");

        List<Scenarioexecution> scenarios = null;

        try {
            if (labType==null || labType==labType.OPTION_ALL)
                scenarios = scenarioexecutionDAO.findByStatusAndParticipantId(participantId,status);
            else
                scenarios = scenarioexecutionDAO.findByStatusAndParticipantId(participantId,status,labType);
            for (Scenarioexecution scenarioExec : scenarios) {
                List<Caseexecution> cases = scenarioExec.getCaseexecutions();
                for (Caseexecution caseExec : cases) {
                    caseExec.setAltscenariocases(altscenariocaseService.getSelectionByScenarioIdCaseId(scenarioExec.getScenario().getScenarioId(), caseExec.getTestcase().getCaseId()));
                }
            }
        } catch (Exception e) {
            log.error("findScenarioExecutionByParticipantIdAndStatus failed", e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return scenarios;
    }

    private List<Criterion> executeCriteria(Participant criteria) {
        List<Criterion> criterionList = new ArrayList<Criterion>();

        if (criteria != null) {
            // Check each property for empty value and set to null
            if (criteria.getNhinRepId() != null) {
                criterionList.add(Restrictions.eq("nhinRepId", criteria.getNhinRepId()));
            }

            if (criteria.getCommunityId() != null && !criteria.getCommunityId().equals("")) {
                criterionList.add(Restrictions.eq("communityId", criteria.getCommunityId()));
            }

            if (criteria.getAssigningAuthorityId() != null && !criteria.getAssigningAuthorityId().equals("")) {
                criterionList.add(Restrictions.eq("assigningAuthorityId", criteria.getAssigningAuthorityId()));
            }

            if (criteria.getIpAddress() != null && !criteria.getIpAddress().equals("")) {
                criterionList.add(Restrictions.eq("ipAddress", criteria.getIpAddress()));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().equals("")) {
                criterionList.add(Restrictions.eq("status", criteria.getStatus()));
            }

            if (criteria.getParticipantName() != null && !criteria.getParticipantName().equals("")) {
                criterionList.add(Restrictions.like("participantName", criteria.getParticipantName(), MatchMode.START));
            }

            if (criteria.getContactName() != null && !criteria.getContactName().equals("")) {
                criterionList.add(Restrictions.like("contactName", criteria.getContactName(), MatchMode.START));
            }

            if (criteria.getContactEmail() != null && !criteria.getContactEmail().equals("")) {
                criterionList.add(Restrictions.like("contactEmail", criteria.getContactEmail(), MatchMode.START));
            }

            if (criteria.getContactPhone() != null && !criteria.getContactPhone().equals("")) {
                criterionList.add(Restrictions.like("state", criteria.getContactPhone(), MatchMode.START));
            }

            if (criteria.getCreatetime() != null && !criteria.getCreatetime().toString().equals("")) {
                criterionList.add(Restrictions.like("Createtime", criteria.getCreatetime().toString(), MatchMode.START));
            }

            if (criteria.getCreateuser() != null && !criteria.getCreateuser().toString().equals("")) {
                criterionList.add(Restrictions.like("createUser", criteria.getCreateuser().toString(), MatchMode.START));
            }

            if (criteria.getSsnHandlingInd() != null && !criteria.getSsnHandlingInd().equals("")) {
                criterionList.add(Restrictions.like("ssnHandlingInd", criteria.getSsnHandlingInd(), MatchMode.START));
            }

            if (criteria.getInitiatorInd() != null && !criteria.getInitiatorInd().equals("")) {
                criterionList.add(Restrictions.eq("initiatorInd", criteria.getInitiatorInd()));
            }

            if (criteria.getResponderInd() != null && !criteria.getResponderInd().equals("")) {
                criterionList.add(Restrictions.eq("responderInd", criteria.getResponderInd()));
            }

            if (criteria.getChangedtime() != null && !criteria.getChangedtime().toString().equals("")) {
                criterionList.add(Restrictions.eq("changedTime", criteria.getChangedtime()));
            }

            if (criteria.getChangeduser() != null && !criteria.getChangeduser().equals("")) {
                criterionList.add(Restrictions.eq("changeduser", criteria.getChangeduser()));
            }
        }

        return criterionList;
    }

    @Override
    public List<Participant> getActiveCandiateByIPAddrAndAssignAutId(String assignAuthId, String ipAddress, String status) throws ServiceException {
        log.info("getActiveCandiateByIPAddrAndAssignAutId - INFO");
        Participant criteria = new Participant();
        criteria.setIpAddress(ipAddress);
        criteria.setAssigningAuthorityId(assignAuthId);
        criteria.setStatus(status);
        List<Participant> results = null;
        try {
            if (criteria != null) {
                log.info("getActiveCandiateByIPAddrAndAssignAutId - Search by Criteria");
                results = participantDAO.searchByCriteria(criteria, this.executeCriteria(criteria));

            }
        } catch (Exception e) {
            // throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            e.printStackTrace();
        }

        // assign Nhin rep name to individual Participant (reason: we removed nullable foreign key relationship in the database)
        results = this.establishParticipantInstancesWithNhinrepNames(results);

        return results;
    }

    @Override
    public List<Participant> getActiveCandiateByCommunityId(String communityId, String status) throws ServiceException {
        log.info("getActiveCandiateByIPAddrAndAssignAutId - INFO");
        Participant criteria = new Participant();
        criteria.setCommunityId(communityId);
        criteria.setStatus(status);
        List<Participant> results = null;
        try {
            if (criteria != null) {
                log.info("getActiveCandiateByIPAddrAndAssignAutId - Search by Criteria");
                results = participantDAO.searchByCriteria(criteria, this.executeCriteria(criteria));

            }
        } catch (Exception e) {
            // throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            e.printStackTrace();
        }

        // assign Nhin rep name to individual Participant (reason: we removed nullable foreign key relationship in the database)
        results = this.establishParticipantInstancesWithNhinrepNames(results);

        return results;
    }


    /**
     * Purpose: This method, privately accessible, reads Nhin reps id of each Participant
     * instance passed,and then reads the corresponding Nhin rep name from the database
     * and attaches that Nhin rep name to the Participant instance.
     *
     * Note: This process was necessary to do because we took care of nullable foreign key
     * relationships in Lab Core's database by removing relationship between
     * Participant and Nhinrep tables.
     *
     * @param pobjListParticipants
     * @return
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private List<Participant> establishParticipantInstancesWithNhinrepNames(List<Participant> pobjListParticipants) throws ServiceException {

        log.info("ParticipantServiceImpl.establishParticipantInstancesWithNhinrepNames() - INFO");
        List<Participant> objListParticipants = null;
        Nhinrep objNhinRep = null;

        if (null == pobjListParticipants) {
            log.error("ParticipantServiceImpl.establishParticipantInstancesWithNhinrepNames()- Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("ParticipantServiceImpl.establishParticipantInstancesWithNhinrepNames() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        objListParticipants = pobjListParticipants;     // redundant but ok
        if (null != objListParticipants) {
            for(Participant validParticipant : objListParticipants) {
                Integer tempNhinRepId = -9;     // some error value to start with
                if(null != validParticipant) {
                    tempNhinRepId = validParticipant.getNhinRepId();
                    if(null == tempNhinRepId) {
                        validParticipant.setNhinRepName("");  // set to empty string
                    }
                    else {
                        objNhinRep = nhinrepService.findByNhinRepId(tempNhinRepId);
                        if(null != objNhinRep) {
                            validParticipant.setNhinRepName(objNhinRep.getName());
                        }
                    }
                }
            }
        }

        return objListParticipants;
    }

    @Override
    public List<Participant> findByCommunityId(String communityId, LabType labType) {
        return participantDAO.findByCommunityId(communityId, labType);
    }
    public List<Participant> findByCommunityId(String communityId) {
        return participantDAO.findByCommunityId(communityId, LabType.CONFORMANCE);
    }
}
