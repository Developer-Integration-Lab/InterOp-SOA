package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Applicationpropertiess</p>
 * <p>Generated at Tue May 04 18:33:57 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ApplicationpropertiesDAO extends GenericDao<Applicationproperties, String> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildApplicationpropertiesDAO()
     */
    /**
     * Find Applicationproperties by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Applicationproperties> findByCriteria(String value);

    /**
     * Find Applicationproperties by criteria.
     */
    public List<Applicationproperties> findByCriteria(Map criterias);

    /**
     * Find Applicationproperties by value
     */
    public List<Applicationproperties> findByKey(String value);

    /**
     * Find All Applicationproperties
     */
    public List<Applicationproperties> findAll();
}
