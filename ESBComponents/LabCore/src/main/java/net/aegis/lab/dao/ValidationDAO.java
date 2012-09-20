package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Validation;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Validations</p>
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ValidationDAO extends GenericDao<Validation,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildValidationDAO()
	 */
	 
	/**
 	 * Find Validation by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Validation> findByCriteria(String validationUniqueId, Integer participantId, String executionUniqueId, String status, String decisionReason, String comments, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser);
	 
	/**
 	 * Find Validation by criteria.
	 */
	public List<Validation> findByCriteria(Map criterias);

	 
	/**
	 * Find Validation by validationUniqueId
	 */
	public List<Validation> findByValidationUniqueId(String validationUniqueId);

	/**
	 * Find Validation by participantId
	 */
	public List<Validation> findByParticipantId(Integer participantId);

        /**
         * Find 'REVIEW' status records for a given nhin rep (NHIN Validator)
         */
        public List<Validation> findReviewByNhinRepId(Integer pNhinRepId);

	/**
	 * Find Validation by executionUniqueId
	 */
	public List<Validation> findByExecutionUniqueId(String executionUniqueId);

	/**
	 * Find Validation by status
	 */
	public List<Validation> findByStatus(String status);

	/**
	 * Find Validation by decisionReason
	 */
	public List<Validation> findByDecisionReason(String decisionReason);

	/**
	 * Find Validation by comments
	 */
	public List<Validation> findByComments(String comments);

	/**
	 * Find Validation by createtime
	 */
	public List<Validation> findByCreatetime(Timestamp createtime);

	/**
	 * Find Validation by createuser
	 */
	public List<Validation> findByCreateuser(String createuser);

	/**
	 * Find Validation by changedtime
	 */
	public List<Validation> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Validation by changeduser
	 */
	public List<Validation> findByChangeduser(String changeduser);

}