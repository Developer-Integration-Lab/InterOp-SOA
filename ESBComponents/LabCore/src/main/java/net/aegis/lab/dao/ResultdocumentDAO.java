package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Resultdocuments</p>
 * <p>Generated at Thu May 06 08:54:23 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ResultdocumentDAO extends GenericDao<Resultdocument, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildResultdocumentDAO()
     */
    /**
     * Find Resultdocument by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Resultdocument> findByCriteria(Integer resultId, String documentUniqueId, String repositoryId, byte[] rawData);

    /**
     * Find Resultdocument by criteria.
     */
    public List<Resultdocument> findByCriteria(Map criterias);

    /**
     * Find Resultdocument by resultId
     */
    public List<Resultdocument> findByResultId(Integer resultId);

    /**
     * Find Resultdocument by documentUniqueId
     */
    public List<Resultdocument> findByDocumentUniqueId(String documentUniqueId);

    /**
     * Find Resultdocument by repositoryId
     */
    public List<Resultdocument> findByRepositoryId(String repositoryId);

    /**
     * Find Resultdocument by rawData
     */
    public List<Resultdocument> findByRawData(byte[] rawData);
}
