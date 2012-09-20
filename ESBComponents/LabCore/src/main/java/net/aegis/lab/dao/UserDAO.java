package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Users</p>
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface UserDAO extends GenericDao<User,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildUserDAO()
	 */
	 
	/**
 	 * Find User by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<User> findByCriteria(String username, String password, String status, Timestamp expirationTime, String comments, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find User by criteria.
	 */
	public List<User> findByCriteria(Map criterias);

	 
	/**
	 * Find User by username
	 */
	public List<User> findByUsername(String username);

	/**
	 * Find User by username and
	 */
	public List<User> findByUsernameAndPassword(String username, String password);

	/**
	 * Find User by password
	 */
	public List<User> findByPassword(String password);

	/**
	 * Find User by status
	 */
	public List<User> findByStatus(String status);

	/**
	 * Find User by expirationTime
	 */
	public List<User> findByExpirationTime(Timestamp expirationTime);

	/**
	 * Find User by comments
	 */
	public List<User> findByComments(String comments);

	/**
	 * Find User by createtime
	 */
	public List<User> findByCreatetime(Timestamp createtime);

	/**
	 * Find User by createuser
	 */
	public List<User> findByCreateuser(String createuser);

	/**
	 * Find User by changedtime
	 */
	public List<User> findByChangedtime(Timestamp changedtime);

	/**
	 * Find User by changeduser
	 */
	public List<User> findByChangeduser(String changeduser);

        public List<User> findAll();

        /**
         * Find users by rolename
         * @param rolename
         * @return
         */
        public List<User> findByRolename(String rolename);

}