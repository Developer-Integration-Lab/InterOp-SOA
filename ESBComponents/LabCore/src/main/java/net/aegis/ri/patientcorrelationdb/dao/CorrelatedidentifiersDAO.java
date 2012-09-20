package net.aegis.ri.patientcorrelationdb.dao;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.genericdao.GenericDao;
import net.aegis.ri.patientcorrelationdb.dao.pojo.Correlatedidentifiers;

/**
 * <p>Hibernate DAO layer for Correlatedidentifierss</p>
 * <p>Generated at Wed Feb 10 22:33:42 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface CorrelatedidentifiersDAO extends GenericDao<Correlatedidentifiers, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildCorrelatedidentifiersDAO()
     */
    /**
     * Find Correlatedidentifiers by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Correlatedidentifiers> findByCriteria(String patientAssigningAuthorityId, String patientId, String correlatedPatientAssignAuthId, String correlatedPatientId);

    /**
     * Find Correlatedidentifiers by patientAssigningAuthorityId
     */
    public List<Correlatedidentifiers> findByPatientAssigningAuthorityId(String patientAssigningAuthorityId);

    /**
     * Find Correlatedidentifiers by patientId
     */
    public List<Correlatedidentifiers> findByPatientId(String patientId);

    /**
     * Find Correlatedidentifiers by correlatedPatientAssignAuthId
     */
    public List<Correlatedidentifiers> findByCorrelatedPatientAssignAuthId(String correlatedPatientAssignAuthId);

    /**
     * Find Correlatedidentifiers by correlatedPatientId
     */
    public List<Correlatedidentifiers> findByCorrelatedPatientId(String correlatedPatientId);

    /**
     * Find Correlatedidentifiers by correlationExpirationDate
     */
    public List<Correlatedidentifiers> findByCorrelationExpirationDate(Timestamp correlationExpirationDate);

    /**
     * Find all Correlatedidentifiers
     */
    public List<Correlatedidentifiers> findAllCorrelation();
}
