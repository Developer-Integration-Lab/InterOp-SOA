/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.enums;

/**
 * 
 * Venkat.Keesara Feb 15, 2012
 */
public enum MsgTypeEnum {

	PD_REQUEST("PDREQ", "urn:hl7-org:v3:PRPA_IN201305UV02:CrossGatewayPatientDiscovery" ,"PD", "Patient Discovery Request"), 
	PD_RESPONSE("PDRES","urn:hl7-org:v3:PRPA_IN201306UV02:CrossGatewayPatientDiscovery", "PD", "Patient Discovery Response"), 
	QD_REQUEST("QDREQ", "urn:ihe:iti:2007:CrossGatewayQuery" ,"QD", "Query For Document Request"), 
	QD_RESPONSE("QDRES","urn:ihe:iti:2007:CrossGatewayQueryResponse","QD", "Query For Document Response"), 
	RD_REQUEST("RDREQ", "urn:ihe:iti:2007:CrossGatewayRetrieve","RD","Document Retrieve Request"), 
	RD_RESPONSE("RDRES","urn:ihe:iti:2007:CrossGatewayRetrieveResponse", "RD","Document Retrieve Response");
	
	// INSTANCE VARIABLES
	private String textId;
	private String actionUrn;
	private String msgType;
	private String defaultDescription;

	private MsgTypeEnum(String textId, String actionUrn, String msgType,
			String defaultDescription) {
		this.textId = textId;
		this.actionUrn = actionUrn;
		this.msgType = msgType;
		this.defaultDescription = defaultDescription;
	}

	// ACCESSING
	public String getTextId() {
		return textId;
	}

	

	/**
	 * @return the textValue
	 */
	public String getActionUrn() {
		return actionUrn;
	}

	
	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}

	
	
	public String getDefaultDescription() {
		return defaultDescription;
	}

	
	// MARSHALLING
	@Override
	public String toString() {
		return getTextId();
	}
	
	public static MsgTypeEnum getByActionUrn(String urn) {
		for (MsgTypeEnum anEnum : values()) {
			if (anEnum.getActionUrn().equalsIgnoreCase(urn)) {
				return anEnum;
			}
		}
		return null;
	}

	public static MsgTypeEnum fromTextId(String id) {
		for (MsgTypeEnum anEnum : values()) {
			if (anEnum.getTextId().equalsIgnoreCase(id)) {
				return anEnum;
			}
		}
		return null;
	}
}
