package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Altscenariocase;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Altscenariocases</p>
 * <p>Generated at Fri Oct 15 17:02:46 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface AltscenariocaseDAO extends GenericDao<Altscenariocase, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildAltscenariocaseDAO()
     */
    /**
     * Find Altscenariocase by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Altscenariocase> findByCriteria(Integer scenarioId, Integer caseId, String alternateName, String description, String alternateCriteria, byte[] config);

    /**
     * Find Altscenariocase by criteria.
     */
    public List<Altscenariocase> findByCriteria(Map criterias);

    /**
     * Find Altscenariocase by scenarioId
     */
    public List<Altscenariocase> findByScenarioId(Integer scenarioId);

    /**
     * Find Altscenariocase by caseId
     */
    public List<Altscenariocase> findByCaseId(Integer caseId);

    /**
     * Find Altscenariocase by alternateName
     */
    public List<Altscenariocase> findByAlternateName(String alternateName);

    /**
     * Find Altscenariocase by description
     */
    public List<Altscenariocase> findByDescription(String description);

    /**
     * Find Altscenariocase by alternateCriteria
     */
    public List<Altscenariocase> findByAlternateCriteria(String alternateCriteria);

    /**
     * Find Altscenariocase by config
     */
    public List<Altscenariocase> findByConfig(byte[] config);

    /**
     * Find All Altscenariocase
     */
    public List<Altscenariocase> findAll();

}
