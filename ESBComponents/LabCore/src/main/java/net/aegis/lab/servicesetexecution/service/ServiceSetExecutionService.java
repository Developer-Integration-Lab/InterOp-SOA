/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.servicesetexecution.service;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.LabConstants.LabType;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface ServiceSetExecutionService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Servicesetexecution newInstance)throws ServiceException;

    public Servicesetexecution read(int setExecutionId) throws ServiceException;

    public void update(Servicesetexecution transientObject) throws ServiceException;

    public void delete(Servicesetexecution persistentObject) throws ServiceException;

    public void deleteById(Integer Id) throws ServiceException;

    //business logic methods

    public void startServiceSetExecution(List<Servicesetexecution> neekToBeActiveServiceSets) throws ServiceException;

    //Update servicesetExecutions to CLOSE
    public void closeServiceSetExecution(List<Servicesetexecution> needToBeCloseActiveServiceSets) throws ServiceException;

    //
    public void updateServiceSetExecutionStatusToSubmitted(List<Servicesetexecution> needToBeSubmittedServiceSets) throws ServiceException ;


    public void updateScenarioExecsAndCaseExecsToCloseForValidationRecs(String executionId) throws ServiceException ;

    public Map<String,String> populateCandidtePatientIdMap(int participantId) throws ServiceException;
    /**
     * Finder methods
     *
     */
    /**
     * Find Active Servicesetexecution by participantId and LabType
     */
    public List<Servicesetexecution> findActiveByParticipantId(Integer participantId, LabType labType) throws ServiceException;

    /**
     * Find Servicesetexecution by setExecutionId
     */
    public Servicesetexecution findBySetExecutionId(Integer setExecutionId) throws ServiceException;

    /**
     * Find Servicesetexecution by pExecutionUniqueId
     */
    public List<Servicesetexecution> findByExecutionUniqueId(String pExecutionUniqueId) throws ServiceException;

    /**
     * Find service set execution(s) given a participant id and execution status
     */
    public List<Servicesetexecution> findByParticipantIdAndStatus(Integer participantId, String pstrStatus) throws ServiceException;

    /**
     * Find service set execution(s) given a participant id and execution status and labType
     */
    public List<Servicesetexecution> findByParticipantIdAndStatus(Integer participantId, String pstrStatus, LabType labType) throws ServiceException;

    /**
     * Find service set execution(s) given an execution status
     */
    public List<Servicesetexecution> getAllServiceSetExecutionsByStatus(String pstrStatus) throws ServiceException;
}
