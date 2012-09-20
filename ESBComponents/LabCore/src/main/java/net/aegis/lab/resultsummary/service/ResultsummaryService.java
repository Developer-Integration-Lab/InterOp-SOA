/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.lab.resultsummary.service;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * Venkat.Keesara
 * Feb 22, 2012
 */
public interface ResultsummaryService {

	public Integer create(Resultsummary resultsummary) throws ServiceException;

    public void update(Resultsummary resultsummary) throws ServiceException;

    public void delete(Resultsummary resultsummary) throws ServiceException;

    public void deleteById(int id) throws ServiceException;
	    
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

	public Resultsummary findByResultsummaryId(int resultsummaryId)
			throws ServiceException;
}
