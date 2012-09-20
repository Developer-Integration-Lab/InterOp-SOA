/**
 * 
 */
package com.predic8.membrane.core.startup.dto;

/**
 * @author Sunil.Bhaskarla
 *
 */
public class GatewayTag {
	

	private String gatewayAddress;
	private String HCID;
	private String hostedBy;	
	
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

	
		
}
