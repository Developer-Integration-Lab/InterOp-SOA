package net.aegis.mv.mockdata;


public class RDExpectedMessage {
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getHcid() {
		return hcid;
	}
	public void setHcid(String hcid) {
		this.hcid = hcid;
	}
	
	/**
	 * Returns messageType: 'REQUEST' or 'RESPONSE'
	 * 
	 * @return
	 */
	public String getMessageType() {
		return messageType;
	}
	
	/**
	 * Sets messageType: 'REQUEST' or 'RESPONSE'
	 * @param messageType
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public String getDocList() {
		return docList;
	}
	public void setDocList(String docList) {
		this.docList = docList;
	}
	
	public String getRegistryStatus() {
		return registryStatus;
	}
	public void setRegistryStatus(String registryStatus) {
		this.registryStatus = registryStatus;
	}
	
	private String caseId;
	private String messageId;
	private String hcid;	
	private String messageType;
	private String registryStatus;
	private String docList;	
}
