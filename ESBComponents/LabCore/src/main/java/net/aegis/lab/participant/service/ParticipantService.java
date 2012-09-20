package net.aegis.lab.participant.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.LabConstants.LabType;

/**
 *
 * @author Ram Ghattu
 */
public interface ParticipantService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Participant newInstance) throws ServiceException;

    public Participant read(Integer id) throws ServiceException;

    public void update(Participant transientObject) throws ServiceException;

    public void delete(Participant persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public Participant findByParticipantId(int id) throws ServiceException;

    public List<Participant> findByNhinRepId(Integer nhinRepId) throws ServiceException;

    public List<Participant> findByNhinRepIdAndStatus(Integer pnhinRepId, String pstrStatus) throws ServiceException;

    public List<Participant> findByStatus(String status) throws ServiceException;

    public List<Participant> getActiveCandiateByIPAddrAndCommunityId(String communityId, String ipAddress, String status) throws ServiceException;

    public List<Participant> getActiveCandiateByCommunityId(String communityId, String status) throws ServiceException;

    public List<Participant> getActiveCandiateByIPAddrAndAssignAutId(String assignAuthId, String ipAddress, String status) throws ServiceException;

    public List<Scenarioexecution> findScenarioExecutionByScenarioExecutionId(Integer scenarioExecutionId) throws ServiceException;

    public List<Scenarioexecution> findScenarioExecutionByParticipantIdAndStatus(Integer participantId, String status, LabType labType) throws ServiceException;

    public List<Participant> findByCommunityId(String cId, LabType labType) throws ServiceException;

    /*
     * Business methods
     */

    // Requirement: An NHIN REP needs to be able to register a new participant.
    public Participant registerParticipant(User user, Participant participant) throws ServiceException;
    // Requirement: The logged in Nhin Rep is able to choose participants from participant list
    public int assignParticipants(Integer pnhinRepId, String[] parrParticipantIds) throws ServiceException;

}
