package net.aegis.gateway.agent.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import net.aegis.lab.util.Format;

/**
 * <p>Pojo mapping table document</p>
 * <p></p>
 *
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
/**
 * ILT-300
 * @author Sunil.Bhaskarla
 */

public class Transaction implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9036342932764774480L;
	private Integer id;
	private Integer statusCode;
	private String messageType;
	private Timestamp time;
	private String rule;
	private String method;
	private String path;
	private String client;
	private String server;
	private String senderHCID;
	private String receiverHCID;
	private Gateway sender;
	private Gateway receiver;
	private String contentType;
	private Integer contentLength;
	private Integer duration;
	private String messageFilePath;
	private byte[] messageHeader;
	private byte[] message;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public String getMessageFilePath() {
        return messageFilePath;
    }

    public void setMessageFilePath(String messageFilePath) {
        this.messageFilePath = messageFilePath;
    }

    public byte[] getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(byte[] messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

	/**
	 * @return the sender
	 */
	public Gateway getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(Gateway sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public Gateway getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(Gateway receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the senderHCID
	 */
	public String getSenderHCID() {
		return senderHCID;
	}

	/**
	 * @param senderHCID the senderHCID to set
	 */
	public void setSenderHCID(String senderHCID) {
		this.senderHCID = senderHCID;
	}

	/**
	 * @return the receiverHCID
	 */
	public String getReceiverHCID() {
		return receiverHCID;
	}

	/**
	 * @param receiverHCID the receiverHCID to set
	 */
	public void setReceiverHCID(String receiverHCID) {
		this.receiverHCID = receiverHCID;
	}
	
	// utility method to return error 
	public Boolean isError(){
		if(getStatusCode()!=null && getStatusCode()>=400 && getStatusCode() <=500){
			return true;
		}else{
			return false ;
		}
	}

}
