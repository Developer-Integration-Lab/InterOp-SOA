package net.aegis.ri.docrepository.dao;

import java.util.List;

import net.aegis.lab.genericdao.GenericDao;
import net.aegis.ri.docrepository.dao.pojo.Eventcode;

/**
 * <p>Hibernate DAO layer for Eventcodes</p>
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface EventcodeDAO extends GenericDao<Eventcode, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildEventcodeDAO()
     */
    /**
     * Find Eventcode by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Eventcode> findByCriteria(Integer documentid, String eventCode, String eventCodeScheme, String eventCodeDisplayName);

    /**
     * Find Eventcode by documentid
     */
    public List<Eventcode> findByDocumentid(Integer documentid);

    /**
     * Find Eventcode by eventCode
     */
    public List<Eventcode> findByEventCode(String eventCode);

    /**
     * Find Eventcode by eventCodeScheme
     */
    public List<Eventcode> findByEventCodeScheme(String eventCodeScheme);

    /**
     * Find Eventcode by eventCodeDisplayName
     */
    public List<Eventcode> findByEventCodeDisplayName(String eventCodeDisplayName);
}
