package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Testcases</p>
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface TestcaseDAO extends GenericDao<Testcase,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildTestcaseDAO()
	 */

        /**
         * Find all test cases
         */
        public List<Testcase> findAll();
	/**
 	 * Find Testcase by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Testcase> findByCriteria(String name, String description, String messageType, String testType, String executeType, String initiatorInd, String responderInd, String ssnHandlingInd, String expectedTestResults, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find Testcase by criteria.
	 */
	public List<Testcase> findByCriteria(Map criterias);

	 
	/**
	 * Find Testcase by name
	 */
	public List<Testcase> findByName(String name);

	/**
	 * Find Testcase by description
	 */
	public List<Testcase> findByDescription(String description);

	/**
	 * Find Testcase by messageType
	 */
	public List<Testcase> findByMessageType(String messageType);

	/**
	 * Find Testcase by testType
	 */
	public List<Testcase> findByTestType(String testType);

	/**
	 * Find Testcase by executeType
	 */
	public List<Testcase> findByExecuteType(String executeType);

	/**
	 * Find Testcase by initiatorInd
	 */
	public List<Testcase> findByInitiatorInd(String initiatorInd);

	/**
	 * Find Testcase by responderInd
	 */
	public List<Testcase> findByResponderInd(String responderInd);

	/**
	 * Find Testcase by ssnHandlingInd
	 */
	public List<Testcase> findBySsnHandlingInd(String ssnHandlingInd);

	/**
	 * Find Testcase by expectedTestResults
	 */
	public List<Testcase> findByExpectedTestResults(String expectedTestResults);

	/**
	 * Find Testcase by createtime
	 */
	public List<Testcase> findByCreatetime(Timestamp createtime);

	/**
	 * Find Testcase by createuser
	 */
	public List<Testcase> findByCreateuser(String createuser);

	/**
	 * Find Testcase by changedtime
	 */
	public List<Testcase> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Testcase by changeduser
	 */
	public List<Testcase> findByChangeduser(String changeduser);

}