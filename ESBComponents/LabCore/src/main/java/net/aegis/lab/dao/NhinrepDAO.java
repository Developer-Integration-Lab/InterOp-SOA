package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Nhinreps</p>
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface NhinrepDAO extends GenericDao<Nhinrep,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildNhinrepDAO()
	 */
	 
	/**
 	 * Find Nhinrep by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Nhinrep> findByCriteria(String name, Integer userId, String contactName, String contactPhone, String contactEmail, String status, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find Nhinrep by criteria.
	 */
	public List<Nhinrep> findByCriteria(Map criterias);

	 
	/**
	 * Find Nhinrep by name
	 */
	public List<Nhinrep> findByName(String name);

	/**
	 * Find Nhinrep by userId
	 */
	public List<Nhinrep> findByUserId(Integer userId);

	/**
	 * Find Nhinrep by contactName
	 */
	public List<Nhinrep> findByContactName(String contactName);

	/**
	 * Find Nhinrep by contactPhone
	 */
	public List<Nhinrep> findByContactPhone(String contactPhone);

	/**
	 * Find Nhinrep by contactEmail
	 */
	public List<Nhinrep> findByContactEmail(String contactEmail);

	/**
	 * Find Nhinrep by status
	 */
	public List<Nhinrep> findByStatus(String status);

	/**
	 * Find Nhinrep by createtime
	 */
	public List<Nhinrep> findByCreatetime(Timestamp createtime);

	/**
	 * Find Nhinrep by createuser
	 */
	public List<Nhinrep> findByCreateuser(String createuser);

	/**
	 * Find Nhinrep by changedtime
	 */
	public List<Nhinrep> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Nhinrep by changeduser
	 */
	public List<Nhinrep> findByChangeduser(String changeduser);

}