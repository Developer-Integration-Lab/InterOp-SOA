package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Questionnairecase;
import net.aegis.lab.dao.pojo.Questionnairecase.QuestionnairecasePK;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Questionnairecases</p>
 * <p>Generated at Thu Jun 16 10:48:01 EDT 2011</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface QuestionnairecaseDAO extends GenericDao<Questionnairecase,QuestionnairecasePK> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildQuestionnairecaseDAO()
	 */
	 
	/**
 	 * Find Questionnairecase by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Questionnairecase> findByCriteria();
	 
	/**
 	 * Find Questionnairecase by criteria.
	 */
	public List<Questionnairecase> findByCriteria(Map criterias);

	 
	/**
	 * Find Questionnairecase by questionId
	 */
	public List<Questionnairecase> findByQuestionId(Integer questionId);

	/**
	 * Find Questionnairecase by caseId
	 */
	public List<Questionnairecase> findByCaseId(Integer caseId);

}