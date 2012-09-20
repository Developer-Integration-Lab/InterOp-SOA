package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Testharnessendpoint;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Testharnessendpoints</p>
 * <p>Generated at Wed Apr 14 15:59:01 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface TestharnessendpointDAO extends GenericDao<Testharnessendpoint, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildTestharnessendpointDAO()
     */
    /**
     * Find Testharnessendpoint by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Testharnessendpoint> findByCriteria(Integer testharnessId, String messageType, String endpoint);

    /**
     * Find Testharnessendpoint by criteria.
     */
    public List<Testharnessendpoint> findByCriteria(Map criterias);

    /**
     * Find Testharnessendpoint by testharnessId
     */
    public List<Testharnessendpoint> findByTestharnessId(Integer testharnessId);

    /**
     * Find Testharnessendpoint by messageType
     */
    public List<Testharnessendpoint> findByMessageType(String messageType);

    /**
     * Find Testharnessendpoint by endpoint
     */
    public List<Testharnessendpoint> findByEndpoint(String endpoint);
}
