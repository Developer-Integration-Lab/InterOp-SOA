package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Questionnairetestplan;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Questionnairetestplans</p>
 * <p>Generated at Thu Jun 16 10:48:01 EDT 2011</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface QuestionnairetestplanDAO extends GenericDao<Questionnairetestplan,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildQuestionnairetestplanDAO()
	 */
	 
	/**
 	 * Find Questionnairetestplan by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Questionnairetestplan> findByCriteria(Integer questionnaireresponseId, Integer caseId, String status);
	 
	/**
 	 * Find Questionnairetestplan by criteria.
	 */
	public List<Questionnairetestplan> findByCriteria(Map criterias);

	 
	/**
	 * Find Questionnairetestplan by questionnaireresponseId
	 */
	public List<Questionnairetestplan> findByQuestionnaireresponseId(Integer questionnaireresponseId);

	/**
	 * Find Questionnairetestplan by caseId
	 */
	public List<Questionnairetestplan> findByCaseId(Integer caseId);

	/**
	 * Find Questionnairetestplan by status
	 */
	public List<Questionnairetestplan> findByStatus(String status);

}