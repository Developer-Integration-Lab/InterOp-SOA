/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.lab.resultsummary.service;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.ResultsummaryDAO;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Venkat.Keesara
 * Feb 22, 2012
 */
@Service("resultsummaryService")
public class ResultsummaryServiceImpl implements ResultsummaryService{
	
	private static final Log log = LogFactory.getLog(ResultsummaryServiceImpl.class);
	
	@Autowired 
	private ResultsummaryDAO resultsummaryDAO;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Resultsummary resultsummary) throws ServiceException {
        log.info("saving Resultsummary..");
        try {
            return resultsummaryDAO.create(resultsummary);
        } catch (Exception ex) {
            throw new ServiceException("Exception occured while creating  Resultsummary record" , ex);
        }
    }
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Resultsummary resultsummary) throws ServiceException {
        log.info("updating Resultsummary..");
        try {
            resultsummaryDAO.update(resultsummary);
        } catch (Exception ex) {
            throw new ServiceException("Exception occured while updating  Resultsummary record" , ex);
        }
    }
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	@Override
	public void delete(Resultsummary resultsummary) throws ServiceException {
		 log.info("delete Resultsummary..");
	        try {
	            resultsummaryDAO.delete(resultsummary);
	        } catch (Exception ex) {
	            throw new ServiceException("Exception occured while delete  Resultsummary record" , ex);
	        }
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	@Override
	public void deleteById(int id) throws ServiceException {
		 log.info("deleteById Resultsummary..");
	        try {
	            resultsummaryDAO.delete(findByResultsummaryId(id));
	        } catch (Exception ex) {
	            throw new ServiceException("Exception occured while delete  Resultsummary record" , ex);
	        }
	}
	 
	@Override
	public List<Resultsummary> findByCriteria(Map criterias) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public Resultsummary findByResultsummaryId(int resultsummaryId) throws ServiceException {
        log.info("findByResultsummaryId ..");
        try {
            return resultsummaryDAO.read(resultsummaryId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }
	
	@Override
	public List<Resultsummary> findByResultId(Integer resultId) {
		return resultsummaryDAO.findByResultId(resultId);
	}

	@Override
	public List<Resultsummary> findByMessageId(String messageId) {
		return resultsummaryDAO.findByMessageId(messageId);
	}

	@Override
	public List<Resultsummary> findByRelatesTo(String relatesTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Resultsummary> findByReqTransactionid(Integer reqTransactionid) {
		return resultsummaryDAO.findByReqTransactionid(reqTransactionid);
	}

	@Override
	public List<Resultsummary> findByResTransactionid(Integer resTransactionid) {
		return resultsummaryDAO.findByResTransactionid(resTransactionid);
	}

	

}
