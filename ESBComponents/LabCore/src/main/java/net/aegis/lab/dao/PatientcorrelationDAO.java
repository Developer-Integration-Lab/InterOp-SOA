package net.aegis.lab.dao;

import java.util.Map;
import java.util.List;
import java.sql.Timestamp;

import net.aegis.lab.dao.pojo.Patientcorrelation;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Patientcorrelations</p>
 * <p>Generated at Thu Apr 05 12:02:17 EDT 2012</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface PatientcorrelationDAO extends GenericDao<Patientcorrelation,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildPatientcorrelationDAO()
	 */
	 
	/**
 	 * Find Patientcorrelation by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Patientcorrelation> findByCriteria(Integer resultId, String patientId, String patientAssigningAuthorityId, String patientHomeCommunityId, String correlatedPatientId, String correlatedPatientAssignAuthId, String correlatedPatientHomeCommunityId, Timestamp createdAt, String createdBy, Timestamp updatedAt, String updatedBy);
	 
	/**
 	 * Find Patientcorrelation by criteria.
	 */
	public List<Patientcorrelation> findByCriteria(Map criterias);

	 
	/**
	 * Find Patientcorrelation by resultId
	 */
	public List<Patientcorrelation> findByResultId(Integer resultId);

	/**
	 * Find Patientcorrelation by patientId
	 */
	public List<Patientcorrelation> findByPatientId(String patientId);

	/**
	 * Find Patientcorrelation by patientAssigningAuthorityId
	 */
	public List<Patientcorrelation> findByPatientAssigningAuthorityId(String patientAssigningAuthorityId);

	/**
	 * Find Patientcorrelation by patientHomeCommunityId
	 */
	public List<Patientcorrelation> findByPatientHomeCommunityId(String patientHomeCommunityId);

	/**
	 * Find Patientcorrelation by correlatedPatientId
	 */
	public List<Patientcorrelation> findByCorrelatedPatientId(String correlatedPatientId);

	/**
	 * Find Patientcorrelation by correlatedPatientAssignAuthId
	 */
	public List<Patientcorrelation> findByCorrelatedPatientAssignAuthId(String correlatedPatientAssignAuthId);

	/**
	 * Find Patientcorrelation by correlatedPatientHomeCommunityId
	 */
	public List<Patientcorrelation> findByCorrelatedPatientHomeCommunityId(String correlatedPatientHomeCommunityId);

	/**
	 * Find Patientcorrelation by createdAt
	 */
	public List<Patientcorrelation> findByCreatedAt(Timestamp createdAt);

	/**
	 * Find Patientcorrelation by createdBy
	 */
	public List<Patientcorrelation> findByCreatedBy(String createdBy);

	/**
	 * Find Patientcorrelation by updatedAt
	 */
	public List<Patientcorrelation> findByUpdatedAt(Timestamp updatedAt);

	/**
	 * Find Patientcorrelation by updatedBy
	 */
	public List<Patientcorrelation> findByUpdatedBy(String updatedBy);

}