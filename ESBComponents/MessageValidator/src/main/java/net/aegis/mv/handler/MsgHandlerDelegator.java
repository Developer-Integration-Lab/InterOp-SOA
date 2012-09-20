/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.handler;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.manager.ResultsummaryManager;
import net.aegis.lab.manager.TransactionsManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.pd.PDRequestMsgHandler;
import net.aegis.mv.handler.pd.PDResponseMsgHandler;
import net.aegis.mv.handler.qd.QDRequestMsgHandler;
import net.aegis.mv.handler.qd.QDResponseMsgHandler;
import net.aegis.mv.handler.rd.RDRequestMsgHandler;
import net.aegis.mv.handler.rd.RDResponseMsgHandler;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.parser.SOAPFaultParser;
import net.aegis.mv.parser.pd.PDRequestParser;
import net.aegis.mv.parser.pd.PDResponseParser;
import net.aegis.mv.parser.qd.QDRequestParser;
import net.aegis.mv.parser.qd.QDResponseParser;
import net.aegis.mv.parser.rd.RDRequestParser;
import net.aegis.mv.parser.rd.RDResponseParser;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Venkat.Keesara
 * Apr 18, 2012
 **/
/**
 * This class is helper class to send message to a particular handler class like
 * PDMsgHandler/QDMsgHandler/RDMsgHandler/ErrorMsgMsgHandler
 */
public class MsgHandlerDelegator {

	private static final Log log = LogFactory.getLog(MsgHandlerDelegator.class);

	private static MsgHandlerDelegator instance = new MsgHandlerDelegator();

	private MsgHandlerDelegator() {
		log.debug("MsgHandlerDelegator() constructor");
	}

	public static MsgHandlerDelegator getInstance() {
		return instance;
	}

	/**
	 * send message to a particular hanlder class
	 * 
	 * @param id
	 */
	public void sendMessageToHandler(String id) {
		// Get the transaction that contains the message from the database using
		// the given unique transaction identifier.
		TransactionsManager transactionsManager = TransactionsManager
		.getInstance();
		Transaction transaction = transactionsManager.findById(Integer
				.valueOf(id));

		// look for 400 and 500 errors
		if (transaction != null && transaction.isError()) {
			processErrorResponse(transaction);
		} else {
			// Find out the message type so we can handle it accordingly.
			if(transaction != null){
				MsgTypeEnum findMsgType = MessageValidatorUtil.getInstance().findMessageType(transaction);
				INhinMsgHandler msgHandler = null;
				INhinMsgParser parser = null;

				switch (findMsgType) {
				case PD_REQUEST:
					msgHandler = new PDRequestMsgHandler();
					parser = PDRequestParser.getInstance();
					break;
				case PD_RESPONSE:
					msgHandler = new PDResponseMsgHandler();
					parser = PDResponseParser.getInstance();
					break;
				case QD_REQUEST:
					msgHandler = new QDRequestMsgHandler();
					parser = QDRequestParser.getInstance();
					break;
				case QD_RESPONSE:
					msgHandler = new QDResponseMsgHandler();
					parser = QDResponseParser.getInstance();
					break;
				case RD_REQUEST:
					msgHandler = new RDRequestMsgHandler();
					parser = RDRequestParser.getInstance();
					break;
				case RD_RESPONSE:
					msgHandler = new RDResponseMsgHandler();
					parser = RDResponseParser.getInstance();
					break;
				default:
					log.error("Message Handler for message type: " + findMsgType
							+ " not found.");
				}

				// Process the message.
				if (msgHandler != null) {
					msgHandler.processMessage(transaction, parser, findMsgType);
				}
			}else{
				log.error("Transaction object is null.");
			}
		}		
	}

	/**
	 * This method to process all error messages
	 * 
	 * @param transaction
	 */
	private void processErrorResponse(Transaction transaction) {

		String relatesTo = getRelatesTo(transaction);
		Caseexecution caseExecution = findCaseExecutionForErrorResponse(
				transaction, relatesTo);
		MsgTypeEnum msgTypeEnum = null;
		INhinMsgHandler msgHandler = null;
		if (caseExecution != null
				&& StringUtils.isNotEmpty(caseExecution.getMessageType())
				&& caseExecution.getMessageType().equals(
						LabConstants.PATIENTDISCOVERY)) {
			msgHandler = new PDResponseMsgHandler();
			msgTypeEnum = MsgTypeEnum.PD_RESPONSE;
		} else if (caseExecution != null
				&& StringUtils.isNotEmpty(caseExecution.getMessageType())
				&& caseExecution.getMessageType().equals(
						LabConstants.LAB_DOCQUERY)) {
			msgHandler = new QDResponseMsgHandler();
			msgTypeEnum = MsgTypeEnum.QD_RESPONSE;
		} else if (caseExecution != null
				&& StringUtils.isNotEmpty(caseExecution.getMessageType())
				&& caseExecution.getMessageType().equals(
						LabConstants.LAB_DOCRETRIEVE)) {
			msgHandler = new RDResponseMsgHandler();
			msgTypeEnum = MsgTypeEnum.RD_RESPONSE;
		}
		if (msgHandler != null) {

			msgHandler.setResponseError(transaction, msgTypeEnum,
					caseExecution, relatesTo);
		} else {
			log.error("Message Handler for error message not found.");
		}
	}

	/**
	 * 
	 * @param transaction
	 * @return
	 */
	private Caseexecution findCaseExecutionForErrorResponse(
			Transaction transaction, String relatesTo) {

		log.info("findCaseExecutionForErrorResponse() | relatesTo==" + relatesTo);
		Caseexecution caseexecution = null;
		if (transaction != null && transaction.isError()) {
			log.info("findCaseExecutionForErrorResponse() transaction==" + transaction.getId());
			caseexecution = ResultsummaryManager.getInstance()
			.findCaseExecutionByRelatesToAndReceiverHCID(relatesTo,
					transaction.getSenderHCID(),
					transaction.getReceiverHCID());
			if (caseexecution != null
					&& caseexecution.getCaseExecutionId() != null) {
				log.info("caseexecutionId==="+ caseexecution.getCaseExecutionId());
			}
		}
		return caseexecution;
	}

	/**
	 * @param transaction
	 * @return
	 */
	private String getRelatesTo(Transaction transaction) {
		String relatesTo = null;
		if (transaction != null && transaction.isError()) {
			if (StringUtils.isNotEmpty(transaction.getMessageType())
					&& transaction.getMessageType().equals(
							LabConstants.RESPONSE)
							&& transaction.getMessage() != null) {
				SOAPFaultParser sOAPFaultParser = new SOAPFaultParser();
				relatesTo = sOAPFaultParser.parseSOAPFaultMessage(transaction);
			}
		}
		return relatesTo;
	}

}
