/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.parser;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.util.MVConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Venkat.Keesara
 * Apr 11, 2012
 **/

public class SOAPFaultParser {

private static final Log log = LogFactory.getLog(SOAPFaultParser.class);
	
	public SOAPFaultParser() {
        log.info("SOAPFaultParser empty constructor ");
    }
	
	/**
	 * Used to parse soap fault message with/without MTOM parts 
	 * @param transaction
	 * @return
	 */
	public String parseSOAPFaultMessage(Transaction transaction) {			
		Node envelopeNode = ParserHelper.getInstance().getEnvelopeNode(transaction); 
		Node headerNode = ParserHelper.getInstance().getElementByTagNameDirect(envelopeNode, MVConstants.HEADER);
		return parseHeader(headerNode); 
	}
	
	private String parseHeader(Node  header ){
		String relatesTo ="";
		log.info("Parsing " + header.getLocalName());
		Element relatesToElement = ParserHelper.getInstance().getUniqueElementByTagName(header, MVConstants.RELATES_TO);
		relatesTo = ParserHelper.getInstance().getTextValue(relatesToElement);
		return relatesTo ; 
	}	
}
