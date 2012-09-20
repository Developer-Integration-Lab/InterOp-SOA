package net.aegis.lab.dao;

import java.util.Map;
import java.util.List;
import java.sql.Timestamp;

import net.aegis.lab.dao.pojo.VwGateway;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for VwGateways</p>
 * <p>Generated at Wed Feb 15 20:19:40 EST 2012</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface VwGatewayDAO extends GenericDao<VwGateway,Long> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildVwGatewayDAO()
	 */
	 
	/**
 	 * Find VwGateway by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<VwGateway> findByCriteria(String hcid, String gatewayAddress, String hostedBy, Long labNode);
	 
	/**
 	 * Find VwGateway by criteria.
	 */
	public List<VwGateway> findByCriteria(Map criterias);

	 
	/**
	 * Find VwGateway by hcid
	 */
	public List<VwGateway> findByHcid(String hcid);

	/**
	 * Find VwGateway by gatewayAddress
	 */
	public List<VwGateway> findByGatewayAddress(String gatewayAddress);

	/**
	 * Find VwGateway by hostedBy
	 */
	public List<VwGateway> findByHostedBy(String hostedBy);

	/**
	 * Find VwGateway by labNode
	 */
	public List<VwGateway> findByLabNode(Long labNode);

}