package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Scenariocase.ScenariocasePK;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Scenariocases</p>
 * <p>Generated at Fri Apr 08 11:54:48 EDT 2011</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ScenariocaseDAO extends GenericDao<Scenariocase,ScenariocasePK> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildScenariocaseDAO()
	 */
	 
	/**
 	 * Find Scenariocase by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Scenariocase> findByCriteria(String userName, String patientId, String documentIds, String participatingRIs, String successCriteria, String autoAttachInd, Integer dependentScenarioId, Integer dependentCaseId, byte[] config);
	 
	/**
 	 * Find Scenariocase by criteria.
	 */
	public List<Scenariocase> findByCriteria(Map criterias);

	 
	/**
	 * Find Scenariocase by scenarioId
	 */
	public List<Scenariocase> findByScenarioId(Integer scenarioId);

	/**
	 * Find Scenariocase by caseId
	 */
	public List<Scenariocase> findByCaseId(Integer caseId);

	/**
	 * Find Scenariocase by userName
	 */
	public List<Scenariocase> findByUserName(String userName);

	/**
	 * Find Scenariocase by patientId
	 */
	public List<Scenariocase> findByPatientId(String patientId);

	/**
	 * Find Scenariocase by documentIds
	 */
	public List<Scenariocase> findByDocumentIds(String documentIds);

	/**
	 * Find Scenariocase by participatingRIs
	 */
	public List<Scenariocase> findByParticipatingRIs(String participatingRIs);

	/**
	 * Find Scenariocase by successCriteria
	 */
	public List<Scenariocase> findBySuccessCriteria(String successCriteria);

	/**
	 * Find Scenariocase by autoAttachInd
	 */
	public List<Scenariocase> findByAutoAttachInd(String autoAttachInd);

	/**
	 * Find Scenariocase by dependentScenarioId
	 */
	public List<Scenariocase> findByDependentScenarioId(Integer dependentScenarioId);

	/**
	 * Find Scenariocase by dependentCaseId
	 */
	public List<Scenariocase> findByDependentCaseId(Integer dependentCaseId);

	/**
	 * Find Scenariocase by config
	 */
	public List<Scenariocase> findByConfig(byte[] config);

}