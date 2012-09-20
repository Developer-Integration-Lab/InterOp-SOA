package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.genericdao.GenericDao;
import net.aegis.lab.util.LabConstants.LabType;
/**
 * <p>Hibernate DAO layer for Servicesets</p>
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ServicesetDAO extends GenericDao<Serviceset,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildServicesetDAO()
	 */
	
        /**
         * Find all service sets
         */
        public List<Serviceset> findAll(LabType labType);

	/**
 	 * Find Serviceset by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Serviceset> findByCriteria(String setName, String initiatorAllowedInd, String responderAllowedInd, String ssnHandlingInd, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find Serviceset by criteria.
	 */
	public List<Serviceset> findByCriteria(Map criterias);

	 
	/**
	 * Find Serviceset by setName
	 */
	public List<Serviceset> findBySetName(String setName);

	/**
	 * Find Serviceset by initiatorAllowedInd
	 */
	public List<Serviceset> findByInitiatorAllowedInd(String initiatorAllowedInd);

	/**
	 * Find Serviceset by responderAllowedInd
	 */
	public List<Serviceset> findByResponderAllowedInd(String responderAllowedInd);

	/**
	 * Find Serviceset by ssnHandlingInd
	 */
	public List<Serviceset> findBySsnHandlingInd(String ssnHandlingInd);

	/**
	 * Find Serviceset by createtime
	 */
	public List<Serviceset> findByCreatetime(Timestamp createtime);

	/**
	 * Find Serviceset by createuser
	 */
	public List<Serviceset> findByCreateuser(String createuser);

	/**
	 * Find Serviceset by changedtime
	 */
	public List<Serviceset> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Serviceset by changeduser
	 */
	public List<Serviceset> findByChangeduser(String changeduser);

}