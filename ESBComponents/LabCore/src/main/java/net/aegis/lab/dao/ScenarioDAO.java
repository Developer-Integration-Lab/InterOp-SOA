package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Scenarios</p>
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ScenarioDAO extends GenericDao<Scenario,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildScenarioDAO()
	 */
	 
	/**
 	 * Find Scenario by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Scenario> findByCriteria(Integer setId, String scenarioName, String description, String condition, String initiatorInd, String responderInd, String ssnHandlingInd, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find Scenario by criteria.
	 */
	public List<Scenario> findByCriteria(Map criterias);

	 
	/**
	 * Find Scenario by setId
	 */
	public List<Scenario> findBySetId(Integer setId);

	/**
	 * Find Scenario by scenarioName
	 */
	public List<Scenario> findByScenarioName(String scenarioName);

	/**
	 * Find Scenario by description
	 */
	public List<Scenario> findByDescription(String description);

	/**
	 * Find Scenario by condition
	 */
	public List<Scenario> findByCondition(String condition);

	/**
	 * Find Scenario by initiatorInd
	 */
	public List<Scenario> findByInitiatorInd(String initiatorInd);

	/**
	 * Find Scenario by responderInd
	 */
	public List<Scenario> findByResponderInd(String responderInd);

	/**
	 * Find Scenario by ssnHandlingInd
	 */
	public List<Scenario> findBySsnHandlingInd(String ssnHandlingInd);

	/**
	 * Find Scenario by createtime
	 */
	public List<Scenario> findByCreatetime(Timestamp createtime);

	/**
	 * Find Scenario by createuser
	 */
	public List<Scenario> findByCreateuser(String createuser);

	/**
	 * Find Scenario by changedtime
	 */
	public List<Scenario> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Scenario by changeduser
	 */
	public List<Scenario> findByChangeduser(String changeduser);

        /**
         * Find all scenarios
         */
        public List<Scenario> findAll();

}