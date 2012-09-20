package net.aegis.mv.dto;

import java.io.Serializable;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.VwGateway;

public class ClientServerDTO implements Serializable {

	private VwGateway clientGatewayInfo;
	private VwGateway serverGatewayInfo= null;
	private VwGateway participantGatewayInfo = null;
	private Transaction transaction;
	boolean initiatorFlag= false ;
		
	public VwGateway getClientGatewayInfo() {
		return clientGatewayInfo;
	}
	public void setClientGatewayInfo(VwGateway clientGatewayInfo) {
		this.clientGatewayInfo = clientGatewayInfo;
	}
	public VwGateway getServerGatewayInfo() {
		return serverGatewayInfo;
	}
	public void setServerGatewayInfo(VwGateway serverGatewayInfo) {
		this.serverGatewayInfo = serverGatewayInfo;
	}
	public VwGateway getParticipantGatewayInfo() {
		return participantGatewayInfo;
	}
	public void setParticipantGatewayInfo(VwGateway participantGatewayInfo) {
		this.participantGatewayInfo = participantGatewayInfo;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	public boolean isInitiatorFlag() {
		return initiatorFlag;
	}
	public void setInitiatorFlag(boolean initiatorFlag) {
		this.initiatorFlag = initiatorFlag;
	}
}
