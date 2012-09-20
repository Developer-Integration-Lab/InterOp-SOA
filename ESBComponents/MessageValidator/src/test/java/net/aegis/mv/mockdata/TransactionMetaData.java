package net.aegis.mv.mockdata;

public class TransactionMetaData {
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getContentLength() {
		return contentLength;
	}
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getMessageFilePath() {
		return messageFilePath;
	}
	public void setMessageFilePath(String messageFilePath) {
		this.messageFilePath = messageFilePath;
	}
	public String getMessageHeader() {
		return messageHeader;
	}
	public void setMessageHeader(String messageHeader) {
		this.messageHeader = messageHeader;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	public boolean isInitiatorScenario() {
		return initiatorScenario;
	}
	public void setInitiatorScenario(boolean initiatorScenario) {
		this.initiatorScenario = initiatorScenario;
	}	
	public boolean isSuccessScenario() {
		return successScenario;
	}
	public void setSuccessScenario(boolean successScenario) {
		this.successScenario = successScenario;
	}
	
	private String caseId;
	private int statusCode;
	private String messageType;
	private String time;
	private String rule;
	private String method;
	private String path;
	private String client;
	private String server;
	private String sender;
	private String receiver;
	private String contentType;
	private int contentLength;
	private int duration;
	private String messageFilePath;
	private String messageHeader;
	private String message;
	private boolean initiatorScenario;
	private boolean successScenario;
}
