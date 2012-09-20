package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Userrole;
import net.aegis.lab.dao.pojo.Userrole.UserrolePK;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Userroles</p>
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface UserroleDAO extends GenericDao<Userrole,UserrolePK> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildUserroleDAO()
	 */
	 
	/**
 	 * Find Userrole by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Userrole> findByCriteria();
	 
	/**
 	 * Find Userrole by criteria.
	 */
	public List<Userrole> findByCriteria(Map criterias);

	 
	/**
	 * Find Userrole by userId
	 */
	public List<Userrole> findByUserId(Integer userId);

	/**
	 * Find Userrole by roleId
	 */
	public List<Userrole> findByRoleId(Integer roleId);

}