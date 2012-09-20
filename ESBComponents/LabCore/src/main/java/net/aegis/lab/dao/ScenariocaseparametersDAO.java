package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Scenariocaseparameters;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Scenariocaseparameterss</p>
 * <p>Generated at Fri Apr 08 11:54:49 EDT 2011</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ScenariocaseparametersDAO extends GenericDao<Scenariocaseparameters,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildScenariocaseparametersDAO()
	 */
	 
	/**
 	 * Find Scenariocaseparameters by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Scenariocaseparameters> findByCriteria(Integer scenarioId, Integer caseId, String paramName, String displayParamName, String paramValue, String required);
	 
	/**
 	 * Find Scenariocaseparameters by criteria.
	 */
	public List<Scenariocaseparameters> findByCriteria(Map criterias);

	 
	/**
	 * Find Scenariocaseparameters by scenarioId
	 */
	public List<Scenariocaseparameters> findByScenarioId(Integer scenarioId);

	/**
	 * Find Scenariocaseparameters by caseId
	 */
	public List<Scenariocaseparameters> findByCaseId(Integer caseId);

	/**
	 * Find Scenariocaseparameters by paramName
	 */
	public List<Scenariocaseparameters> findByParamName(String paramName);

	/**
	 * Find Scenariocaseparameters by displayParamName
	 */
	public List<Scenariocaseparameters> findByDisplayParamName(String displayParamName);

	/**
	 * Find Scenariocaseparameters by paramValue
	 */
	public List<Scenariocaseparameters> findByParamValue(String paramValue);

	/**
	 * Find Scenariocaseparameters by required
	 */
	public List<Scenariocaseparameters> findByRequired(String required);

}