package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Questionnaire;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Questionnaires</p>
 * <p>Generated at Thu Jun 16 10:48:01 EDT 2011</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface QuestionnaireDAO extends GenericDao<Questionnaire,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildQuestionnaireDAO()
	 */
	 
	/**
 	 * Find Questionnaire by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Questionnaire> findByCriteria(Integer setId, String name, String serviceType, String description, String value, String initiatorInd, String responderInd, Integer order, String render);
	 
	/**
 	 * Find Questionnaire by criteria.
	 */
	public List<Questionnaire> findByCriteria(Map criterias);

	 
	/**
	 * Find Questionnaire by setId
	 */
	public List<Questionnaire> findBySetId(Integer setId);

	/**
	 * Find Questionnaire by name
	 */
	public List<Questionnaire> findByName(String name);

	/**
	 * Find Questionnaire by serviceType
	 */
	public List<Questionnaire> findByServiceType(String serviceType);

	/**
	 * Find Questionnaire by description
	 */
	public List<Questionnaire> findByDescription(String description);

	/**
	 * Find Questionnaire by answer
	 */
	public List<Questionnaire> findByAnswer(String answer);

	/**
	 * Find Questionnaire by initiatorInd
	 */
	public List<Questionnaire> findByInitiatorInd(String initiatorInd);

	/**
	 * Find Questionnaire by responderInd
	 */
	public List<Questionnaire> findByResponderInd(String responderInd);

	/**
	 * Find Questionnaire by displayOrder
	 */
	public List<Questionnaire> findByDisplayOrder(Integer displayOrder);

	/**
	 * Find Questionnaire by uiDisplay
	 */
	public List<Questionnaire> findByUiDisplay(String uiDisplay);

}