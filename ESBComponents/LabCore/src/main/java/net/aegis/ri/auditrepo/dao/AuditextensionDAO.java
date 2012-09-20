package net.aegis.ri.auditrepo.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.genericdao.GenericDao;
import net.aegis.ri.auditrepo.dao.pojo.Auditextension;

/**
 * <p>Hibernate DAO layer for Auditextensions</p>
 * <p>Generated at Wed Sep 29 17:57:03 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface AuditextensionDAO extends GenericDao<Auditextension, Long> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildAuditextensionDAO()
     */
    /**
     * Find Auditextension by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Auditextension> findByCriteria(Long auditextensionId, Long auditrepositoryId, String eventOutcomeIndicator, byte[] requestMessage, byte[] responseMessage);

    /**
     * Find Auditextension by criteria.
     */
    public List<Auditextension> findByCriteria(Map criterias);

    /**
     * Find Auditextension by id
     */
    public List<Auditextension> findById(Long auditextensionId);

    /**
     * Find Auditextension by auditrepositoryId
     */
    public List<Auditextension> findByAuditrepositoryId(Long auditrepositoryId);

    /**
     * Find Auditextension by eventOutcomeIndicator
     */
    public List<Auditextension> findByEventOutcomeIndicator(String eventOutcomeIndicator);

    /**
     * Find Auditextension by requestMessage
     */
    public List<Auditextension> findByRequestMessage(byte[] requestMessage);

    /**
     * Find Auditextension by responseMessage
     */
    public List<Auditextension> findByResponseMessage(byte[] responseMessage);

    /**
     * Find all Auditextension
     */
    public List<Auditextension> findAll();
}
