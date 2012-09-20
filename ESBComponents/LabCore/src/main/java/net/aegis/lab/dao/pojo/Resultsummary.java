package net.aegis.lab.dao.pojo;

import java.util.List;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * <p>Pojo mapping table resultsummary</p>
 * <p></p>
 *
 * <p>Generated at Tue Feb 28 17:37:46 EST 2012</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Resultsummary implements Serializable {

	/**
	 * Attribute resultsummaryId.
	 */
	private Integer resultsummaryId;
	
	/**
	 * Attribute caseresult
	 */
	 private Caseresult caseresult;	

	/**
	 * Attribute messageId.
	 */
	private String messageId;
	
	/**
	 * Attribute relatesTo.
	 */
	private String relatesTo;
	
	/**
	 * Attribute responseFlag.
	 */
	private String responseFlag;
	
	/**
	 * Attribute reqTransactionid.
	 */
	private Integer reqTransactionid;
	
	/**
	 * Attribute resTransactionid.
	 */
	private Integer resTransactionid;
	
	/**
	 * Attribute reqSenderHcid.
	 */
	private String reqSenderHcid;
	
	/**
	 * Attribute reqReceiverHcid.
	 */
	private String reqReceiverHcid;
	
	/**
	 * Attribute resSenderHcid.
	 */
	private String resSenderHcid;
	
	/**
	 * Attribute resReceiverHcid.
	 */
	private String resReceiverHcid;
	
	
	/**
	 * @return resultsummaryId
	 */
	public Integer getResultsummaryId() {
		return resultsummaryId;
	}

	/**
	 * @param resultsummaryId new value for resultsummaryId 
	 */
	public void setResultsummaryId(Integer resultsummaryId) {
		this.resultsummaryId = resultsummaryId;
	}
	
	/**
	 * get caseresult
	 */
	public Caseresult getCaseresult() {
		return this.caseresult;
	}
	
	/**
	 * set caseresult
	 */
	public void setCaseresult(Caseresult caseresult) {
		this.caseresult = caseresult;
	}

	/**
	 * @return messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId new value for messageId 
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	/**
	 * @return relatesTo
	 */
	public String getRelatesTo() {
		return relatesTo;
	}

	/**
	 * @param relatesTo new value for relatesTo 
	 */
	public void setRelatesTo(String relatesTo) {
		this.relatesTo = relatesTo;
	}
	
	/**
	 * @return the responseFlag
	 */
	public String getResponseFlag() {
		return responseFlag;
	}

	/**
	 * @param responseFlag the responseFlag to set
	 */
	public void setResponseFlag(String responseFlag) {
		this.responseFlag = responseFlag;
	}
	
	/**
	 * @return reqTransactionid
	 */
	public Integer getReqTransactionid() {
		return reqTransactionid;
	}

	/**
	 * @param reqTransactionid new value for reqTransactionid 
	 */
	public void setReqTransactionid(Integer reqTransactionid) {
		this.reqTransactionid = reqTransactionid;
	}
	
	/**
	 * @return resTransactionid
	 */
	public Integer getResTransactionid() {
		return resTransactionid;
	}

	/**
	 * @param resTransactionid new value for resTransactionid 
	 */
	public void setResTransactionid(Integer resTransactionid) {
		this.resTransactionid = resTransactionid;
	}
	
	/**
	 * @return reqSenderHcid
	 */
	public String getReqSenderHcid() {
		return reqSenderHcid;
	}

	/**
	 * @param reqSenderHcid new value for reqSenderHcid 
	 */
	public void setReqSenderHcid(String reqSenderHcid) {
		this.reqSenderHcid = reqSenderHcid;
	}
	
	/**
	 * @return reqReceiverHcid
	 */
	public String getReqReceiverHcid() {
		return reqReceiverHcid;
	}

	/**
	 * @param reqReceiverHcid new value for reqReceiverHcid 
	 */
	public void setReqReceiverHcid(String reqReceiverHcid) {
		this.reqReceiverHcid = reqReceiverHcid;
	}
	
	/**
	 * @return resSenderHcid
	 */
	public String getResSenderHcid() {
		return resSenderHcid;
	}

	/**
	 * @param resSenderHcid new value for resSenderHcid 
	 */
	public void setResSenderHcid(String resSenderHcid) {
		this.resSenderHcid = resSenderHcid;
	}
	
	/**
	 * @return resReceiverHcid
	 */
	public String getResReceiverHcid() {
		return resReceiverHcid;
	}

	/**
	 * @param resReceiverHcid new value for resReceiverHcid 
	 */
	public void setResReceiverHcid(String resReceiverHcid) {
		this.resReceiverHcid = resReceiverHcid;
	}
	


}