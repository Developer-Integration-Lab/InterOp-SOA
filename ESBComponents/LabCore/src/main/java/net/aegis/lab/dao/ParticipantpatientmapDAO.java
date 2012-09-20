package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Participantpatientmap;
import net.aegis.lab.genericdao.GenericDao;

/**
 * <p>Hibernate DAO layer for Participantpatientmaps</p>
 * <p>Generated at Sat May 01 22:11:04 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ParticipantpatientmapDAO extends GenericDao<Participantpatientmap, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildParticipantpatientmapDAO()
     */
    /**
     * Find Participantpatientmap by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Participantpatientmap> findByCriteria(Integer participantId, String patientId, String participantPatientId, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);

    /**
     * Find Participantpatientmap by criteria.
     */
    public List<Participantpatientmap> findByCriteria(Map criterias);

    /**
     * Find Participantpatientmap by participantId
     */
    public List<Participantpatientmap> findByParticipantId(Integer participantId);

    /**
     * Find Participantpatientmap by patientId
     */
    public List<Participantpatientmap> findByPatientId(String patientId);

    /**
     * Find Participantpatientmap by participantPatientId
     */
    public List<Participantpatientmap> findByParticipantPatientId(String participantPatientId);

    /**
     * Find Participantpatientmap by createtime
     */
    public List<Participantpatientmap> findByCreatetime(Timestamp createtime);

    /**
     * Find Participantpatientmap by createuser
     */
    public List<Participantpatientmap> findByCreateuser(String createuser);

    /**
     * Find Participantpatientmap by changedtime
     */
    public List<Participantpatientmap> findByChangedtime(Timestamp changedtime);

    /**
     * Find Participantpatientmap by changeduser
     */
    public List<Participantpatientmap> findByChangeduser(String changeduser);
}
