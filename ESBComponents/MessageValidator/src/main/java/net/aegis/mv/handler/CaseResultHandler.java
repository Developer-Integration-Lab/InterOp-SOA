/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.handler;


import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.dao.pojo.VwGateway;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ResultsummaryManager;
import net.aegis.lab.manager.TestHarnessriManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.exception.MVRuntimeException;
import net.aegis.mv.jaxb.msg.NhinMessage;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class contains the base methods needed by SingleRICaseResultHandler and MultiRICaseResultHandler classes
 * 
 * @author Venkat Keesara
 */
public abstract class CaseResultHandler {

	private static final Log log = LogFactory.getLog(CaseResultHandler.class);	

	public void updateCaseResult(Caseexecution caseExecution, Transaction transaction,
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO) {
		int caseExecId = caseExecution.getCaseExecutionId();
		Caseresult caseresult = getMostRecentCaseResult(caseExecId);
		if(MessageValidatorUtil.getInstance().isOneRI(caseExecId))
		{	
			updateSingleRICaseResult(caseExecution, transaction, msgTypeEnum, nhinMessage, clientServerDTO, caseresult);			
		}else {			
			updateMultiRICaseResult(caseExecution, transaction, msgTypeEnum, nhinMessage, clientServerDTO, caseresult);			
		}
	}
	
	protected abstract void updateSingleRICaseResult(Caseexecution caseExecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult);
	
	protected abstract void updateMultiRICaseResult(Caseexecution caseExecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult);

	// get most recent case result by execution id
	private Caseresult getMostRecentCaseResult(int caseExecId) {
		Caseresult caseResult = null;
		try {
			CaseResultManager caseResultManager = CaseResultManager.getInstance();
			caseResult = caseResultManager.findByExecIdAndMaxResultId(caseExecId);
			if(caseResult!=null){
				log.info("Most recent caseresultId==" + caseResult.getResultId() + " found for caseExecutionId==" +  caseExecId );
			}else{
				log.info("Most recent caseresultId not found for caseExecutionId==" +  caseExecId );
			}
		} catch (ServiceException ex) {
			log.error("Exception occured while retrieving latest caseResult record from database" , ex);
			throw new MVRuntimeException("Exception occured while retrieving latest caseResult record from database" , ex);
		}
		return caseResult;
	}

	protected boolean isCaseResultInFinalStatus(String currentStatus){
		if(StringUtils.isNotEmpty(currentStatus) && LabConstants.STATUS_PASS.equals(currentStatus)
				|| LabConstants.STATUS_FAIL.equals(currentStatus) || LabConstants.STATUS_CLEARED.equals(currentStatus) ||
				LabConstants.STATUS_INCOMPLETE.equals(currentStatus) || LabConstants.STATUS_ERROR.equals(currentStatus)){
			log.info("isCaseResultInFinalStatus: True"   );
			return true;
		}else {
			log.info("isCaseResultInFinalStatus: False"   );
			return false;
		}
	}

	protected void createCaseResultAndResultsummary(Caseexecution caseexecution,
			Transaction transaction,
			String messageId, VwGateway participantGatewayInfo,
			String resultStatus,String message){
		// make these as single transactional by moving to service layer 

		CaseResultManager caseResultManager = CaseResultManager.getInstance();
		ResultsummaryManager resultsummaryManager = ResultsummaryManager.getInstance();
		// create case result with status 
		Caseresult caseresult = new Caseresult(); 
		caseresult.setCaseexecution(caseexecution);
		caseresult.setExecuteTime(transaction.getTime());
		if(participantGatewayInfo!=null && participantGatewayInfo.getLabNode()==LabConstants.LAB_NODE){
			caseresult.setLabInd(LabConstants.LAB_INDICATOR_Y);
		}else{
			caseresult.setLabInd(LabConstants.LAB_INDICATOR_N);
		}
		caseresult.setSubmissionInd(LabConstants.NO_INDICATOR);

		caseresult.setMessage(message);
		caseresult.setResult(resultStatus);
		caseresult.setCreatedAt(DateUtil.getTodayDateTime());
		caseresult.setCreatedBy(LabConstants.MV_USER);
		Integer caseresultId = caseResultManager.saveCaseResult(caseresult);

		// create resultSummary 
		caseresult.setResultId(caseresultId);
		Resultsummary resultsummary = new Resultsummary();
		resultsummary.setCaseresult(caseresult);
		resultsummary.setMessageId(messageId);
		resultsummary.setReqTransactionid(transaction.getId());
		if(transaction.getSender()!=null && StringUtils.isNotEmpty(transaction.getSender().getHCID())){
			resultsummary.setReqSenderHcid(transaction.getSender().getHCID());
		}else{
			resultsummary.setReqSenderHcid(transaction.getSenderHCID());
		}
		if(transaction.getReceiver()!=null && StringUtils.isNotEmpty(transaction.getReceiver().getHCID())){
			resultsummary.setReqReceiverHcid(transaction.getReceiver().getHCID());
		}
		else{
			resultsummary.setReqReceiverHcid(transaction.getReceiverHCID());
		}
		resultsummaryManager.saveResultsummary(resultsummary);


	}

	protected void updateCaseResultAndCreateResultsummary(Transaction transaction,
			String messageId,
			VwGateway participantGatewayInfo, Caseresult caseresult,
			String resultStatus,String message){
		// make these as single transactional by moving to service layer 

		CaseResultManager caseResultManager = CaseResultManager.getInstance();
		ResultsummaryManager resultsummaryManager = ResultsummaryManager.getInstance();
		// create case result with status 
		if(StringUtils.isEmpty(caseresult.getLabInd())){
			if(participantGatewayInfo!=null && participantGatewayInfo.getLabNode()==LabConstants.LAB_NODE){
				caseresult.setLabInd(LabConstants.LAB_INDICATOR_Y);
			}else{
				caseresult.setLabInd(LabConstants.LAB_INDICATOR_N);
			}
		}
		if(StringUtils.isEmpty(caseresult.getSubmissionInd())){
			caseresult.setSubmissionInd(LabConstants.NO_INDICATOR);
		}

		caseresult.setMessage(message);
		caseresult.setResult(resultStatus);
		caseresult.setUpdatedAt(DateUtil.getTodayDateTime());
		caseresult.setUpdatedBy(LabConstants.MV_USER);
		caseResultManager.update(caseresult);

		// create resultSummary 

		Resultsummary resultsummary = new Resultsummary();
		resultsummary.setCaseresult(caseresult);
		resultsummary.setMessageId(messageId);
		resultsummary.setReqTransactionid(transaction.getId());
		if(transaction.getSender()!=null && StringUtils.isNotEmpty(transaction.getSender().getHCID())){
			resultsummary.setReqSenderHcid(transaction.getSender().getHCID());
		}else{
			resultsummary.setReqSenderHcid(transaction.getSenderHCID());
		}
		if(transaction.getReceiver()!=null && StringUtils.isNotEmpty(transaction.getReceiver().getHCID())){
			resultsummary.setReqReceiverHcid(transaction.getReceiver().getHCID());
		}
		else{
			resultsummary.setReqReceiverHcid(transaction.getReceiverHCID());
		}
		resultsummaryManager.saveResultsummary(resultsummary);
	}

	protected void updateCaseResult(VwGateway participantGatewayInfo,
			Caseresult caseresult,
			String resultStatus, 
			String message){
		// make these as single transactional by moving to service layer 

		CaseResultManager caseResultManager = CaseResultManager.getInstance();
		// create case result with status 
		if(StringUtils.isEmpty(caseresult.getLabInd())){
			if(participantGatewayInfo!=null && participantGatewayInfo.getLabNode()==LabConstants.LAB_NODE){
				caseresult.setLabInd(LabConstants.LAB_INDICATOR_Y);
			}else{
				caseresult.setLabInd(LabConstants.LAB_INDICATOR_N);
			}
		}
		if(StringUtils.isEmpty(caseresult.getSubmissionInd())){
			caseresult.setSubmissionInd(LabConstants.NO_INDICATOR);
		}

		caseresult.setMessage(message);
		caseresult.setResult(resultStatus);
		caseresult.setUpdatedAt(DateUtil.getTodayDateTime());
		caseresult.setUpdatedBy(LabConstants.MV_USER);
		caseResultManager.update(caseresult);

	}
	protected void updateCaseResultAndResultsummary(Transaction transaction,
			String relatesTo,
			VwGateway participantGatewayInfo, Caseresult caseresult,
			Resultsummary matchedResultsummary,String resultStatus,String message,Integer noOfRIsProcessed){

		//	String relatesTo = pDNhinResponse.getRelatesTo();
		CaseResultManager caseResultManager = CaseResultManager.getInstance();
		ResultsummaryManager resultsummaryManager = ResultsummaryManager.getInstance();
		if (matchedResultsummary != null && StringUtils.isNotEmpty(relatesTo)) {
			// resultMessage = "[One RI Scenario] Patient Discovery Passed";
			// update caseResult with success && update result summary record
			// with relatesTo Info
			caseresult.setResult(resultStatus);
			caseresult.setMessage(message);
			caseresult.setProcessedRIsCount(noOfRIsProcessed);
			caseresult.setUpdatedAt(DateUtil.getTodayDateTime());
			caseresult.setUpdatedBy(LabConstants.MV_USER);
			caseResultManager.update(caseresult);
			// update result summary record with relatesTo Info
			matchedResultsummary.setRelatesTo(relatesTo);
			String senderHCID = (transaction.getSender()!= null && StringUtils.isNotEmpty(transaction.getSender().getHCID()))? transaction.getSender().getHCID() : transaction.getSenderHCID();
			String receiverHCID = (transaction.getReceiver()!= null && StringUtils.isNotEmpty(transaction.getReceiver().getHCID()))? transaction.getReceiver().getHCID() : transaction.getReceiverHCID();
			
			if(transaction.getMessageType().equalsIgnoreCase(LabConstants.REQUEST)){
				matchedResultsummary.setReqTransactionid(transaction.getId());
			}else{
			matchedResultsummary.setResTransactionid(transaction.getId());
			}
			if(transaction.getMessageType().equalsIgnoreCase(LabConstants.REQUEST)){
				matchedResultsummary.setReqSenderHcid(senderHCID);
			}else{
				matchedResultsummary.setResSenderHcid(senderHCID);
			}

			if(transaction.getMessageType().equalsIgnoreCase(LabConstants.REQUEST)){
				matchedResultsummary.setReqReceiverHcid(receiverHCID);
			}else{
				matchedResultsummary.setResReceiverHcid(receiverHCID);
			}
			resultsummaryManager.update(matchedResultsummary);
		}
	}

	protected Resultsummary getResultSummaryByRelatesToSenderHCIDAndReceiverHCID(String relatesTo ,String senderHCID, String receiverHCID){
		
		Resultsummary matchedResultsummary = null;
		log.info("getResultSummaryByRelatesToSenderHCIDAndReceiverHCID() | relatesTo==" + relatesTo + "| senderHCID==" + senderHCID + " | receiverHCID==" + receiverHCID);
		List<Resultsummary> resultsummarys = ResultsummaryManager.getInstance().findByMessageId(relatesTo);
		if(resultsummarys!=null && resultsummarys.size() > 0){
			
			if(resultsummarys.size()==1){
				matchedResultsummary = resultsummarys.get(0);
			}else{
				for(Resultsummary eachResultsummary : resultsummarys){
					/* Validate the list of result summarys with the senderHCID and receiverHCID to get a single matching result summary.
					 * Since the logic is used for the response message the response message receiverHCID is compared with the request message senderHCID and vice versa to get the correct resultsummary
					 */
					if((eachResultsummary.getReqSenderHcid().equalsIgnoreCase(receiverHCID)) && (eachResultsummary.getReqReceiverHcid().equalsIgnoreCase(senderHCID))){
						matchedResultsummary = eachResultsummary;
						break;
					}
				}	
			}
			
		}
		if(matchedResultsummary!=null){
			log.info("Matched Resultsummary found for RelatesTo==" + relatesTo   );
		}
		else{
			log.info("No matched Resultsummary found for RelatesTo==" + relatesTo  );
		}
		return matchedResultsummary;
	}

	protected boolean isRequestFromSameRI(Caseresult caseresult, Transaction transaction){

		String sender = null;;
		String receiver = null;
		if(transaction.getSender()!=null && StringUtils.isNotEmpty(transaction.getSender().getHCID())){
			sender = transaction.getSender().getHCID();
		}else{
			sender = transaction.getSenderHCID();
		}
		if(transaction.getReceiver()!=null && StringUtils.isNotEmpty(transaction.getReceiver().getHCID())){
			receiver = transaction.getReceiver().getHCID();
		}else{
			receiver = transaction.getReceiverHCID();
		}
		boolean isRequestFromSameRI = false;
		// validate whether  request comes from same  RI  by populating sender and receiver info in resultsummary 

		List<Resultsummary> resultsummarys = caseresult.getResultsummarys();
		if(resultsummarys!=null && resultsummarys.size() > 0){

			for(Resultsummary eachResultsummary : resultsummarys ){
				if(eachResultsummary!=null){
					String reqSenderHcid = eachResultsummary.getReqSenderHcid();
					String reqReceiverHcid = eachResultsummary.getReqReceiverHcid();

					if(StringUtils.isNotEmpty(reqSenderHcid) && StringUtils.isNotEmpty(reqReceiverHcid) &&
							StringUtils.isNotEmpty(sender) && StringUtils.isNotEmpty(receiver) 
							&& reqSenderHcid.equals(sender) && reqReceiverHcid.equals(receiver)){
						isRequestFromSameRI= true;							
						break ; 
					}
				}
			}
		}
		return isRequestFromSameRI ; 
	}

	protected int getNumberOfRIs(String participatingRIs){
		if(StringUtils.isNotEmpty(participatingRIs)){
			String[] participatingRIsArray = participatingRIs.split(LabConstants.SPLITTER);
			return participatingRIsArray.length;
		}
		return 0 ;
	}


	/*
	 * Method returns the successCriteria for the corresponding RI in Two RIs scenario
	 */
	protected String getcaseExecutionSuccessCriteria(Caseexecution caseexecution, Transaction transaction) {
		String successCriteria = caseexecution.getSuccessCriteria();
		log.info("<-- start getcaseExecutionSuccessCriteria --> successCriteria = " + successCriteria);
		if (successCriteria != null && !successCriteria.equalsIgnoreCase(LabConstants.SUCCESS_CRITERIA_EMPTY)) {
			TestHarnessriManager testHarnessriManager = TestHarnessriManager.getInstance();
			Testharnessri testharnessRI = testHarnessriManager.getTestHarnessriByIpAddress(transaction.getServer());
			String[] ris = caseexecution.getParticipatingRIs().split(LabConstants.SPLITTER); 
			String[] splits = successCriteria.split(LabConstants.SPLITTER);
			int currentRI = testharnessRI.getParticipatingId();
			for(int i=0;i<ris.length ;i++){
				if(currentRI == Integer.parseInt(ris[i])){
					successCriteria = (LabConstants.SUCCESS_CRITERIA_EMPTY.equalsIgnoreCase(splits[i]))?splits[i]:null;
					break;
				}
			}		
		}
		log.info("<-- Exit getcaseExecutionSuccessCriteria --> successCriteria = " + successCriteria);
		return successCriteria;
	}
	
	protected boolean validateEmptyCriteria(Caseexecution caseExec, List<String> actualDocsList, List<String> expectedDocs){
		boolean result = false;
		ResultsummaryManager resultsummaryManager = ResultsummaryManager.getInstance();
		List<Resultsummary> resultSummarys = resultsummaryManager.findByResultId(caseExec.getCurrentCaseresult().getResultId());
		TestHarnessriManager testHarnessriManager = TestHarnessriManager.getInstance();
		String[] ris = caseExec.getParticipatingRIs().split(LabConstants.SPLITTER); 
		String[] splits = caseExec.getSuccessCriteria().split(LabConstants.SPLITTER);
		int currentRI = 0;
		for(int i=0;i<splits.length ;i++){
			if(LabConstants.SUCCESS_CRITERIA_EMPTY.equalsIgnoreCase(splits[i])){
				currentRI = Integer.valueOf(ris[i]);
				break;
			}
		}
		for(Resultsummary rs : resultSummarys){
			log.info("ReqReceiverHcid : "+ rs.getReqReceiverHcid() + " ResponseFlag : "+ rs.getResponseFlag());
			Testharnessri testharnessRI = testHarnessriManager.getTestharnessByCommunityId(rs.getReqReceiverHcid());
			if((int)testharnessRI.getParticipatingId() == currentRI && rs.getResponseFlag().equalsIgnoreCase(LabConstants.SUCCESS_CRITERIA_EMPTY)){
				actualDocsList.add(LabConstants.SUCCESS_CRITERIA_EMPTY);
				if(actualDocsList.size() == expectedDocs.size())
					result = true;
			}
		}
		return result;
	}
	
	protected boolean validateForErrorResponse(Caseexecution caseExec){
		boolean hasError = false;
		ResultsummaryManager resultsummaryManager = ResultsummaryManager.getInstance();
		List<Resultsummary> resultSummarys = resultsummaryManager.findByResultId(caseExec.getCurrentCaseresult().getResultId());
		for(Resultsummary rs : resultSummarys){
			if(rs.getResponseFlag().equalsIgnoreCase(LabConstants.STATUS_ERROR)){
				hasError = true;
				break;
			}
		}
		return hasError;
	}
	
	protected void updateResponseFlag(Resultsummary resultsummary, String successCriteria, String status){
		log.info("<-- start updateResponseFlag --> ResponseFlag = " + status);
		ResultsummaryManager resultsummaryManager = ResultsummaryManager.getInstance();
		if(status != null && status.equalsIgnoreCase(LabConstants.RESPONSE_SUCCESS)){
			resultsummary.setResponseFlag(LabConstants.STATUS_PASS);
		}else if(status != null && status.equalsIgnoreCase(LabConstants.RESPONSE_FAILURE)){
			if(successCriteria != null && successCriteria.equalsIgnoreCase(LabConstants.SUCCESS_CRITERIA_EMPTY)){
				resultsummary.setResponseFlag(LabConstants.SUCCESS_CRITERIA_EMPTY);
			}else{
				resultsummary.setResponseFlag(LabConstants.STATUS_FAIL);
			}
		}else{
			resultsummary.setResponseFlag(LabConstants.STATUS_ERROR);
		}
		resultsummaryManager.update(resultsummary);
	}
	
	
}
