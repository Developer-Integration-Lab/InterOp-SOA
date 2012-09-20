/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.scenarioexecution.service;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.exception.ServiceException;

import org.hibernate.criterion.Criterion;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface ScenarioExecutionService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Scenarioexecution scenarioExecution) throws ServiceException;

    public void update(Scenarioexecution scenarioExecution) throws ServiceException;

    public Scenarioexecution read(int scenarioId) throws ServiceException;

    public void delete(Scenarioexecution persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Business methods
     */
    public List<Scenarioexecution> findByScenarioExecutionId(int scenarioExecutionId) throws ServiceException;
    
    public List<Scenarioexecution> findByParticipantId(int participantId) throws ServiceException;   

    public List<Scenarioexecution> findByUniqueExecutionId(String uniqueExecId) throws ServiceException;

    public List<Scenarioexecution> searchByCriteria(Scenarioexecution criteria, List<Criterion> criterion) throws ServiceException;

    public List<Scenarioexecution> getScenarExecsByServiceSetUniqueExecId(String executionUniqueId) throws ServiceException ;

    public List<Scenarioexecution> findByUniqueExecutionIdAndStatus(String pstrUniqueExecId, String pstrStatus) throws  ServiceException;

    public void updateScenarioExecutionsWithValidOrFailStatusAndValidationComments(String pExecutionUniqueId, String[] parrValidatedScenarios,String[] parrFailedScenarios,Map<String,String> pobjScenarioIdCommentMap) throws  ServiceException;

    /**
     * Find service set execution(s) given a participant id and execution status
     */
	List<Scenarioexecution> findByParticipantIdAndStatus(int participantId, String status) throws ServiceException;

	
}
