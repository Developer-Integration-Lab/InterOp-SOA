/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.lab.web.action.common;

import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.TransactionsManager;
import net.aegis.lab.web.action.BaseAction;

/**
 * This class contains all common methods for case result page.
 *
 * @author Venkat.Keesara
 */
public class BaseLogEntries  extends BaseAction{

	protected List<Transaction> transactions ;
	protected Caseresult currentCaseResult;
	
	public Caseresult getCurrentCaseResult() {
		return currentCaseResult;
	}

	public void setCurrentCaseResult(Caseresult currentCaseResult) {
		this.currentCaseResult = currentCaseResult;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	protected void getLogMessages(Caseexecution caseexecution){
		currentCaseResult = getMostRecentCaseResult(caseexecution.getCaseExecutionId());
        if(currentCaseResult!=null){
			List<Resultsummary> resultsummarys = currentCaseResult.getResultsummarys();
	        List<Integer> transactionIds= new ArrayList<Integer>();
	        if(resultsummarys !=null && resultsummarys.size() > 0 ){
	        	for(Resultsummary eachResultsummary : resultsummarys ){
	        		Integer reqTransactionid = eachResultsummary.getReqTransactionid();
	        		Integer resTransactionid = eachResultsummary.getResTransactionid();
	        		if(reqTransactionid!=null && reqTransactionid!=-1){
	        			transactionIds.add(reqTransactionid);
	        		}
	        		if(resTransactionid!=null && resTransactionid!=-1){
	        			transactionIds.add(resTransactionid);
	        		}
	        	}
	        	transactions = TransactionsManager.getInstance().findTransactionsByIds(transactionIds);
	        }
        }
	}
	// utility methods 
	protected Caseresult getMostRecentCaseResult(int caseExecId) {
		Caseresult caseResult = null;
		try {
			CaseResultManager caseResultManager = CaseResultManager.getInstance();
			caseResult = caseResultManager.findByExecIdAndMaxResultId(caseExecId);
		} catch (ServiceException ex) {
			ex.printStackTrace();
		}
		return caseResult;
	}
	
}
