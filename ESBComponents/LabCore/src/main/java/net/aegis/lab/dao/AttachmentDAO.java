package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Attachment;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Attachments</p>
 * <p>Generated at Fri Apr 02 19:40:03 EDT 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface AttachmentDAO extends GenericDao<Attachment,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildAttachmentDAO()
	 */
	 
	/**
 	 * Find Attachment by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Attachment> findByCriteria(Integer resultId, String description, String status, Timestamp createtime, String createuser, Timestamp changedtime, String changeduser, byte[] content);
	 
	/**
 	 * Find Attachment by criteria.
	 */
	public List<Attachment> findByCriteria(Map criterias);

	 
	/**
	 * Find Attachment by resultId
	 */
	public List<Attachment> findByResultId(Integer resultId);

        /**
         * findByServiceSetId
         * @param serviceSetId
         * @return
         */
        public List<Attachment> findBySetExecutionId(Integer setExecutionId);
	/**
	 * Find Attachment by description
	 */
	public List<Attachment> findByDescription(String description);

	/**
	 * Find Attachment by status
	 */
	public List<Attachment> findByStatus(String status);

	/**
	 * Find Attachment by createtime
	 */
	public List<Attachment> findByCreatetime(Timestamp createtime);

	/**
	 * Find Attachment by createuser
	 */
	public List<Attachment> findByCreateuser(String createuser);

	/**
	 * Find Attachment by changedtime
	 */
	public List<Attachment> findByChangedtime(Timestamp changedtime);

	/**
	 * Find Attachment by changeduser
	 */
	public List<Attachment> findByChangeduser(String changeduser);

	/**
	 * Find Attachment by content
	 */
	public List<Attachment> findByContent(byte[] content);

}