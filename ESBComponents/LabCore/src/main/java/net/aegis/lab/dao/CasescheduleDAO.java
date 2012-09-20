package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseschedule;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Caseschedules</p>
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface CasescheduleDAO extends GenericDao<Caseschedule,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildCasescheduleDAO()
	 */
	 
	/**
 	 * Find Caseschedule by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Caseschedule> findByCriteria(Integer caseExecutionId, String executed, String status, Timestamp scheduledTime, Timestamp executedTime);
	 
	/**
 	 * Find Caseschedule by criteria.
	 */
	public List<Caseschedule> findByCriteria(Map criterias);

	 
	/**
	 * Find Caseschedule by caseExecutionId
	 */
	public List<Caseschedule> findByCaseExecutionId(Integer caseExecutionId);

	/**
	 * Find Caseschedule by executed
	 */
	public List<Caseschedule> findByExecuted(String executed);

	/**
	 * Find Caseschedule by status
	 */
	public List<Caseschedule> findByStatus(String status);

	/**
	 * Find Caseschedule by scheduledTime
	 */
	public List<Caseschedule> findByScheduledTime(Timestamp scheduledTime);

	/**
	 * Find Caseschedule by executedTime
	 */
	public List<Caseschedule> findByExecutedTime(Timestamp executedTime);

}