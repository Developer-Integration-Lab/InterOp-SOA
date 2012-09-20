package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Codetype;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Codetypes</p>
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface CodetypeDAO extends GenericDao<Codetype,String> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildCodetypeDAO()
	 */
	 
	/**
 	 * Find Codetype by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Codetype> findByCriteria(String description);
	 
	/**
 	 * Find Codetype by criteria.
	 */
	public List<Codetype> findByCriteria(Map criterias);

	 
	/**
	 * Find Codetype by description
	 */
	public List<Codetype> findByDescription(String description);

}