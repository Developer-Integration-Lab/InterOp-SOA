package net.aegis.lab.dao;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.genericdao.GenericDao;
import net.aegis.lab.util.LabConstants.LabType;

/**
 * <p>Hibernate DAO layer for Scenarioexecutions</p>
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ScenarioexecutionDAO extends GenericDao<Scenarioexecution, Integer> {

    /**
     * Find Scenarioexecution by setExecutionId
     */
    public List<Scenarioexecution> findByScenarioExecutionId(Integer scenarioExecutionId);


    public List<Scenarioexecution> findByStatusAndParticipantId(Integer participantId,String status);

    /**
     * Find Scenarioexecution by participantId
     */
    public List<Scenarioexecution> findByStatusAndParticipantId(Integer participantId,String status,LabType labType);

    /**
     * Find Scenarioexecution by executionUniqueId
     */
    public List<Scenarioexecution> findByExecutionUniqueId(String executionUniqueId);

    /**
     * Find Scenarioexecution by participantId
     */
    public List<Scenarioexecution> findByParticipantId(Integer participantId);
}
