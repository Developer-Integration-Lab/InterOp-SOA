package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Questionnaireresponse;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Questionnaireresponses</p>
 * <p>Generated at Thu Jun 16 10:48:01 EDT 2011</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface QuestionnaireresponseDAO extends GenericDao<Questionnaireresponse,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildQuestionnaireresponseDAO()
	 */
	 
	/**
 	 * Find Questionnaireresponse by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Questionnaireresponse> findByCriteria(Integer setExecutionId, Integer questionId, Integer participantId, String executionUniqueId, String status, String value, Timestamp beginTime, Timestamp endTime);
	 
	/**
 	 * Find Questionnaireresponse by criteria.
	 */
	public List<Questionnaireresponse> findByCriteria(Map criterias);

	 
	/**
	 * Find Questionnaireresponse by setExecutionId
	 */
	public List<Questionnaireresponse> findBySetExecutionId(Integer setExecutionId);

	/**
	 * Find Questionnaireresponse by questionId
	 */
	public List<Questionnaireresponse> findByQuestionId(Integer questionId);

	/**
	 * Find Questionnaireresponse by participantId
	 */
	public List<Questionnaireresponse> findByParticipantId(Integer participantId);

	/**
	 * Find Questionnaireresponse by executionUniqueId
	 */
	public List<Questionnaireresponse> findByExecutionUniqueId(String executionUniqueId);

	/**
	 * Find Questionnaireresponse by status
	 */
	public List<Questionnaireresponse> findByStatus(String status);

	/**
	 * Find Questionnaireresponse by answer
	 */
	public List<Questionnaireresponse> findByAnswer(String answer);

	/**
	 * Find Questionnaireresponse by beginTime
	 */
	public List<Questionnaireresponse> findByBeginTime(Timestamp beginTime);

	/**
	 * Find Questionnaireresponse by endTime
	 */
	public List<Questionnaireresponse> findByEndTime(Timestamp endTime);

}