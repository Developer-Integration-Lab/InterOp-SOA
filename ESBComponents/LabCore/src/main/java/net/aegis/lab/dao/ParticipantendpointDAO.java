package net.aegis.lab.dao;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Participantendpoint;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Participantendpoints</p>
 * <p>Generated at Wed Apr 14 15:59:00 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ParticipantendpointDAO extends GenericDao<Participantendpoint, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildParticipantendpointDAO()
     */
    /**
     * Find Participantendpoint by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Participantendpoint> findByCriteria(Integer participantId, String messageType, String endpoint);

    /**
     * Find Participantendpoint by criteria.
     */
    public List<Participantendpoint> findByCriteria(Map criterias);

    /**
     * Find Participantendpoint by participantId
     */
    public List<Participantendpoint> findByParticipantId(Integer participantId);

    /**
     * Find Participantendpoint by messageType
     */
    public List<Participantendpoint> findByMessageType(String messageType);

    /**
     * Find Participantendpoint by endpoint
     */
    public List<Participantendpoint> findByEndpoint(String endpoint);
}
