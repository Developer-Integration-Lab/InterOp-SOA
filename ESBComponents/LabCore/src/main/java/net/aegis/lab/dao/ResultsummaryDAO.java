package net.aegis.lab.dao;

import java.util.Map;
import java.util.List;
import java.sql.Timestamp;

import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Resultsummarys</p>
 * <p>Generated at Tue Feb 28 16:15:00 EST 2012</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface ResultsummaryDAO extends GenericDao<Resultsummary,Integer> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildResultsummaryDAO()
	 */
	 
	/**
 	 * Find Resultsummary by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Resultsummary> findByCriteria(Integer resultId, String messageId, String relatesTo, Integer reqTransactionid, Integer resTransactionid, String reqSenderHcid, String reqReceiverHcid, String resSenderHcid);
	 
	/**
 	 * Find Resultsummary by criteria.
	 */
	public List<Resultsummary> findByCriteria(Map criterias);

	 
	/**
	 * Find Resultsummary by resultId
	 */
	public List<Resultsummary> findByResultId(Integer resultId);

	/**
	 * Find Resultsummary by messageId
	 */
	public List<Resultsummary> findByMessageId(String messageId);

	/**
	 * Find Resultsummary by relatesTo
	 */
	public List<Resultsummary> findByRelatesTo(String relatesTo);

	/**
	 * Find Resultsummary by reqTransactionid
	 */
	public List<Resultsummary> findByReqTransactionid(Integer reqTransactionid);

	/**
	 * Find Resultsummary by resTransactionid
	 */
	public List<Resultsummary> findByResTransactionid(Integer resTransactionid);

	/**
	 * Find Resultsummary by reqSenderHcid
	 */
	public List<Resultsummary> findByReqSenderHcid(String reqSenderHcid);

	/**
	 * Find Resultsummary by reqReceiverHcid
	 */
	public List<Resultsummary> findByReqReceiverHcid(String reqReceiverHcid);

	/**
	 * Find Resultsummary by resSenderHcid
	 */
	public List<Resultsummary> findByResSenderHcid(String resSenderHcid);

	/**
	 * Find Resultsummary by resReceiverHcid
	 */
	public List<Resultsummary> findByResReceiverHcid(String resReceiverHcid);

}