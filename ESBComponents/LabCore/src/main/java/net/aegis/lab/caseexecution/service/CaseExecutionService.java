/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.caseexecution.service;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.exception.ServiceException;

import org.hibernate.criterion.Criterion;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface CaseExecutionService {

    public Integer create(Caseexecution caseExecution) throws ServiceException;

    public void update(Caseexecution caseExecution) throws ServiceException;

    public void delete(Caseexecution persistentObject) throws ServiceException;

    public void deleteById(int id) throws ServiceException;

    public Caseexecution read(int caseExecutionId) throws ServiceException;

    public Caseexecution getCaseExecByMsgTypeAndPatientId(String messageType, String participantObjectID, Participant participant, String userName, Timestamp execTime, Integer testHarnessId)
            throws ServiceException;

    public Caseexecution getCaseExecByMsgTypeAndPatientId(String messageType, String patientId, Participant participant, String userName, Timestamp execTime) throws ServiceException;

    public List<Caseexecution> findByScenarioExecutionId(int scenarioExecutionId) throws ServiceException;

    public Caseexecution findByCaseExecutionId(int id) throws ServiceException;

    public int[] getParticipatingRIs(int caseExecId) throws ServiceException;

    public Caseexecution getCaseExecByScenarioExecIdAndCaseName(Scenarioexecution scenarioExec, String caseName) throws ServiceException;

    public List<Caseexecution> searchByCriteria(Caseexecution criteria, List<Criterion> criterion) throws ServiceException;

    public String getParticipantPatientId(int scenarioExecId, int caseId) throws ServiceException;

    public Caseexecution findByParticipantForActiveScenarioCase(Integer participantId, Integer scenarioId, Integer caseId) throws ServiceException;

     public java.sql.Timestamp findByMaxStartTimer() throws ServiceException;
     public Caseexecution getCaseExecutionForRD(Integer participantid , String communityId,String documentuniqueid)throws ServiceException;

	public String getParticipantPatientIdForRD(Integer participantId, String communityId,
			String documentUniqueId) throws ServiceException;
}
