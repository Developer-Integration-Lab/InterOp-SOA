package net.aegis.lab.dao.pojo;

import java.io.Serializable;


/**
 * <p>Pojo mapping table vw_gateway</p>
 * <p></p>
 *
 * <p>Generated at Wed Feb 15 20:19:40 EST 2012</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class VwGateway implements Serializable {

	/**
	 * Attribute hcid.
	 */
	private String hcid;
	
	/**
	 * Attribute gatewayAddress.
	 */
	private String gatewayAddress;
	
	/**
	 * Attribute hostedBy.
	 */
	private String hostedBy;
	
	/**
	 * Attribute labNode.
	 */
	private Long labNode;
	
	
	/**
	 * @return hcid
	 */
	public String getHcid() {
		return hcid;
	}

	/**
	 * @param hcid new value for hcid 
	 */
	public void setHcid(String hcid) {
		this.hcid = hcid;
	}
	
	/**
	 * @return gatewayAddress
	 */
	public String getGatewayAddress() {
		return gatewayAddress;
	}

	/**
	 * @param gatewayAddress new value for gatewayAddress 
	 */
	public void setGatewayAddress(String gatewayAddress) {
		this.gatewayAddress = gatewayAddress;
	}
	
	/**
	 * @return hostedBy
	 */
	public String getHostedBy() {
		return hostedBy;
	}

	/**
	 * @param hostedBy new value for hostedBy 
	 */
	public void setHostedBy(String hostedBy) {
		this.hostedBy = hostedBy;
	}
	
	/**
	 * @return labNode
	 */
	public Long getLabNode() {
		return labNode;
	}

	/**
	 * @param labNode new value for labNode 
	 */
	public void setLabNode(Long labNode) {
		this.labNode = labNode;
	}
	


}