package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Role;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Roles</p>
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface RoleDAO extends GenericDao<Role,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildRoleDAO()
	 */
	 
	/**
 	 * Find Role by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Role> findByCriteria(String rolename, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find Role by criteria.
	 */
	public List<Role> findByCriteria(Map criterias);

	 
	/**
	 * Find Role by rolename
	 */
	public List<Role> findByRolename(String rolename);

	/**
	 * Find Role by createtime
	 */
	public List<Role> findByCreatetime(Timestamp createtime);

	/**
	 * Find Role by createuser
	 */
	public List<Role> findByCreateuser(String createuser);

	/**
	 * Find Role by changedtime
	 */
	public List<Role> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Role by changeduser
	 */
	public List<Role> findByChangeduser(String changeduser);

}