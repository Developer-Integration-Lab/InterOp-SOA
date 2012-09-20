package net.aegis.mv.handler;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.parser.INhinMsgParser;

public interface INhinMsgHandler {
	
	/**
	 * 
	 * Processes the messages of type PD, QD and RD for both Request and Responses.
	 * 
	 * @param transaction
	 * 			Transaction object that contains the message.
	 * 
	 * @param parser
	 * 			NHin message parser.
	 * 
	 * @param msgTypeEnum
	 * 			Enumerated constant that represents the message type.
	 * 
	 */
	public void processMessage(Transaction transaction , INhinMsgParser parser, MsgTypeEnum msgTypeEnum);
	
	/**
	 * 
	 * Handles the error response. 
	 * 
	 * @param transaction
	 * 
	 * @param msgTypeEnum
	 * 
	 * @param caseExecution
	 * 
	 * @param relatesTo
	 * 
	 */
	public void setResponseError(Transaction transaction , MsgTypeEnum msgTypeEnum,Caseexecution caseExecution, String relatesTo); 

}
