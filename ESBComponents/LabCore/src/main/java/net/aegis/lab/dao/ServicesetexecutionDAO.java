package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.genericdao.GenericDao;
import net.aegis.lab.util.LabConstants.LabType;
/**
 * <p>Hibernate DAO layer for Servicesetexecutions</p>
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ServicesetexecutionDAO extends GenericDao<Servicesetexecution,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildServicesetexecutionDAO()
	 */
	 
	/**
 	 * Find Servicesetexecution by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Servicesetexecution> findByCriteria(Integer participantId, Integer setId, String executionUniqueId, String initiatorInd, String responderInd, String status, Timestamp beginTime, Timestamp endTime);
	 
	/**
 	 * Find Servicesetexecution by criteria.
	 */
	public List<Servicesetexecution> findByCriteria(Map criterias);

	 
	/**
	 * Find Servicesetexecution by participantId
	 */
	public List<Servicesetexecution> findActiveByParticipantId(Integer participantId, LabType labType);

	/**
	 * Find Servicesetexecution by setExecutionId
	 */
	//public List<Servicesetexecution> findBySetExecutionId(Integer setExecutionId);

	/**
	 * Find Servicesetexecution by participantId
	 */
	public List<Servicesetexecution> findByParticipantId(Integer participantId);

	/**
	 * Find Servicesetexecution by participantId and status
	 */
	public List<Servicesetexecution> findByParticipantIdAndStatus(Integer participantId, String status);

	/**
	 * Find Servicesetexecution by setId
	 */
	public List<Servicesetexecution> findBySetId(Integer setId);

	/**
	 * Find Servicesetexecution by executionUniqueId
	 */
	public List<Servicesetexecution> findByExecutionUniqueId(String executionUniqueId);

	/**
	 * Find Servicesetexecution by initiatorInd
	 */
	public List<Servicesetexecution> findByInitiatorInd(String initiatorInd);

	/**
	 * Find Servicesetexecution by responderInd
	 */
	public List<Servicesetexecution> findByResponderInd(String responderInd);

	/**
	 * Find Servicesetexecution by status
	 */
	public List<Servicesetexecution> findByStatus(String status);

	/**
	 * Find Servicesetexecution by beginTime
	 */
	public List<Servicesetexecution> findByBeginTime(Timestamp beginTime);

	/**
	 * Find Servicesetexecution by endTime
	 */
	public List<Servicesetexecution> findByEndTime(Timestamp endTime);

}