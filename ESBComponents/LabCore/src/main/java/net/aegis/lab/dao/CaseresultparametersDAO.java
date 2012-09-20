package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseresultparameters;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Caseresultparameterss</p>
 * <p>Generated at Fri Mar 25 21:45:45 EDT 2011</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface CaseresultparametersDAO extends GenericDao<Caseresultparameters, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildCaseresultparametersDAO()
     */
    /**
     * Find Caseresultparameters by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Caseresultparameters> findByCriteria(Integer resultId, String paramName, String displayParamName, String paramValue, String required);

    /**
     * Find Caseresultparameters by criteria.
     */
    public List<Caseresultparameters> findByCriteria(Map criterias);

    /**
     * Find Caseresultparameters by resultId
     */
    public List<Caseresultparameters> findByResultId(Integer resultId);

    /**
     * Find Caseresultparameters by paramName
     */
    public List<Caseresultparameters> findByParamName(String paramName);

    /**
     * Find Caseresultparameters by displayParamName
     */
    public List<Caseresultparameters> findByDisplayParamName(String displayParamName);

    /**
     * Find Caseresultparameters by paramValue
     */
    public List<Caseresultparameters> findByParamValue(String paramValue);

    /**
     * Find Caseresultparameters by required
     */
    public List<Caseresultparameters> findByRequired(String required);
}
