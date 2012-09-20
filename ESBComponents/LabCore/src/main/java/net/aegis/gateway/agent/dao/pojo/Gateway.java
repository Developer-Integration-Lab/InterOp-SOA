package net.aegis.gateway.agent.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.util.Format;

/**
 * <p>Pojo mapping table Gateway</p>
 * <p></p>
 *
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
/**
 * ILT-346
 * @author Sunil.Bhaskarla
 */

public class Gateway implements Serializable {
	public static final int LAB_NODE = 1;
    /**
	 * 
	 */
	private static final long serialVersionUID = 9036342972764774470L;		
	private String HCID;
	private String gatewayAddress;
	private String hostedBy;
	private Integer labNode;
	private List<Transaction> senderTransactions = null;
	private List<Transaction> receiverTransactions = null;
	
	/**
	 * @return the hCID
	 */
	public String getHCID() {
		return HCID;
	}
	/**
	 * @param hCID the hCID to set
	 */
	public void setHCID(String hCID) {
		HCID = hCID;
	}
	/**
	 * @return the gatewayAddress
	 */
	public String getGatewayAddress() {
		return gatewayAddress;
	}
	/**
	 * @param gatewayAddress the gatewayAddress to set
	 */
	public void setGatewayAddress(String gatewayAddress) {
		this.gatewayAddress = gatewayAddress;
	}
	/**
	 * @return the hostedBy
	 */
	public String getHostedBy() {
		return hostedBy;
	}
	/**
	 * @param hostedBy the hostedBy to set
	 */
	public void setHostedBy(String hostedBy) {
		this.hostedBy = hostedBy;
	}
	/**
	 * @return the senderTransactions
	 */
	public List<Transaction> getSenderTransactions() {
		return senderTransactions;
	}
	/**
	 * @param senderTransactions the senderTransactions to set
	 */
	public void setSenderTransactions(List<Transaction> senderTransactions) {
		this.senderTransactions = senderTransactions;
	}
	/**
	 * @return the receiverTransactions
	 */
	public List<Transaction> getReceiverTransactions() {
		return receiverTransactions;
	}
	/**
	 * @param receiverTransactions the receiverTransactions to set
	 */
	public void setReceiverTransactions(List<Transaction> receiverTransactions) {
		this.receiverTransactions = receiverTransactions;
	}
	/**
	 * @return the labNode
	 */
	public Integer getLabNode() {
		return labNode;
	}
	/**
	 * @param labNode the labNode to set
	 */
	public void setLabNode(Integer labNode) {
		this.labNode = labNode;
	}

	



}
