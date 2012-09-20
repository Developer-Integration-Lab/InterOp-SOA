/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.util.ContextUtil;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.resultsummary.service.ResultsummaryService;

/**
 *
 * Venkat.Keesara
 * Feb 22, 2012
 */
public class ResultsummaryManager {

	private static final Log log = LogFactory.getLog(ResultsummaryManager.class);
	public static ResultsummaryManager instance = null;
	ResultsummaryService resultsummaryService = (ResultsummaryService) ContextUtil.getLabContext().getBean("resultsummaryService");
	
    public ResultsummaryManager() {
    }
     /**
     * @return ResultsummaryManager
     */
    public static ResultsummaryManager getInstance(){
        if(instance == null){
            instance = new ResultsummaryManager();
        }
        
        return instance;
    }
    
    public int saveResultsummary(Resultsummary resultsummary) throws ServiceException {
		int id = -1;
		try {
			id = resultsummaryService.create(resultsummary);
		} catch (ServiceException se) {
			log.error("ERROR: Failed to create CaseResult.", se);
			throw se;
		}
		return id;
	}
    
    public void update(Resultsummary resultsummary) throws ServiceException {
        Validate.notNull(resultsummary);
        log.info("Updating resultsummary="+resultsummary.getResultsummaryId()+"'");
        resultsummaryService.update(resultsummary);
    }
	
	public List<Resultsummary> findByResultId(Integer resultId) {
		log.info("ResultsummaryManager|findByResultId() Start");
		return resultsummaryService.findByResultId(resultId);
	}

	
	public List<Resultsummary> findByMessageId(String messageId) {
		log.info("ResultsummaryManager|findByMessageId() Start");
		return resultsummaryService.findByMessageId(messageId);
	}
	
	public List<Resultsummary> findByReqTransactionid(Integer reqTransactionid) {
		log.info("ResultsummaryManager|findByReqTransactionid() Start");
		return resultsummaryService.findByReqTransactionid(reqTransactionid);
	}

	
	public List<Resultsummary> findByResTransactionid(Integer resTransactionid) {
		log.info("ResultsummaryManager|findByResTransactionid() Start");
		return resultsummaryService.findByResTransactionid(resTransactionid);
	}
	
	public Caseexecution findCaseExecutionByRelatesToAndReceiverHCID(String relatesTo, String senderHCID, String receiverHCID){
		log.info("ResultsummaryManager|findCaseExecutionByRelatesToAndReceiverHCID() | relatesTo==" + relatesTo + "| senderHCID==" + senderHCID + " | receiverHCID==" + receiverHCID);
		Caseexecution caseExecution = null;
		// Get result summarys using the relatesTo value to match with the messageId column.
		ResultsummaryManager resultsummaryManager = ResultsummaryManager.getInstance();
		List<Resultsummary> resultsummarys = resultsummaryManager.findByMessageId(relatesTo);
		if(resultsummarys!=null && resultsummarys.size() > 0){
			for(Resultsummary eachResultsummary : resultsummarys){
				/* Validate the list of result summarys with the senderHCID and receiverHCID to get a single matching result summary.
				 * Since the logic is used for the response message the response message receiverHCID is compared with the request message senderHCID and vice versa to get the correct resultsummary
				 */
				if((eachResultsummary.getReqSenderHcid().equalsIgnoreCase(receiverHCID)) && (eachResultsummary.getReqReceiverHcid().equalsIgnoreCase(senderHCID))){
					caseExecution = eachResultsummary.getCaseresult().getCaseexecution();
					log.info("caseExecutionId found ==" + caseExecution.getCaseExecutionId() + " for caseresultId== "  + eachResultsummary.getCaseresult().getResultId() + " | relatesTo==" + relatesTo + "| senderHCID==" + senderHCID + " | receiverHCID==" + receiverHCID);
					break;
				}
			}
		}
		return caseExecution;
	}

}
