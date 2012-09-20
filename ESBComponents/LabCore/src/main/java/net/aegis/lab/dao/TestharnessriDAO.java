package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Testharnessris</p>
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface TestharnessriDAO extends GenericDao<Testharnessri,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildTestharnessriDAO()
	 */
	 
	/**
 	 * Find Testharnessri by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Testharnessri> findByCriteria(String name, String version, String communityId, String ipAddress, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find Testharnessri by criteria.
	 */
	public List<Testharnessri> findByCriteria(Map criterias);

	 
	/**
	 * Find Testharnessri by name
	 */
	public List<Testharnessri> findByName(String name);

	/**
	 * Find Testharnessri by version
	 */
	public List<Testharnessri> findByVersion(String version);

	/**
	 * Find Testharnessri by communityId
	 */
	public List<Testharnessri> findByCommunityId(String communityId);

	/**
	 * Find Testharnessri by ipAddress
	 */
	public List<Testharnessri> findByIpAddress(String ipAddress);

	/**
	 * Find Testharnessri by createtime
	 */
	public List<Testharnessri> findByCreatetime(Timestamp createtime);

	/**
	 * Find Testharnessri by createuser
	 */
	public List<Testharnessri> findByCreateuser(String createuser);

	/**
	 * Find Testharnessri by changedtime
	 */
	public List<Testharnessri> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Testharnessri by changeduser
	 */
	public List<Testharnessri> findByChangeduser(String changeduser);

	/**
	 * Find all Testharnessri
	 */
	public List<Testharnessri> findAll();

}