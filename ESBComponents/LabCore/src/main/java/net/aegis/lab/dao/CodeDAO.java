package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Code;
import net.aegis.lab.dao.pojo.Code.CodePK;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Codes</p>
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface CodeDAO extends GenericDao<Code,CodePK> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildCodeDAO()
	 */
	 
	/**
 	 * Find Code by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Code> findByCriteria(String description);
	 
	/**
 	 * Find Code by criteria.
	 */
	public List<Code> findByCriteria(Map criterias);

	 
	/**
	 * Find Code by type
	 */
	public List<Code> findByType(String type);

	/**
	 * Find Code by value
	 */
	public List<Code> findByValue(String value);

	/**
	 * Find Code by description
	 */
	public List<Code> findByDescription(String description);

}