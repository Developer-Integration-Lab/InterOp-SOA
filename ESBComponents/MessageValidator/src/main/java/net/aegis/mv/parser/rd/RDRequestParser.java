package net.aegis.mv.parser.rd;

import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.rd.RdNhinDocumentRequest;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetRequest;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.util.MVConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Parser that parses the messages representing RetrieveDocuement requests and 
 * stands up the associated java data object.
 * 
 * @author padmanabha.ketha
 *
 */
public class RDRequestParser extends RDParserBase implements INhinMsgParser {
	private static final Log log = LogFactory.getLog(RDRequestParser.class);
	
	private static RDRequestParser instance = new RDRequestParser();
	private RDRequestParser() {}
		
	public static RDRequestParser getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * Parses the message and message-header binary messages from a given <code>Transaction</code> object and 
	 * stands up a <code>RdNhinDocumentSetRequest</code> that represents the RD request.
	 *  
	 * @param transaction
	 * 
	 * @return RdNhinDocumentSetRequest
	 * 
	 */
	public RdNhinDocumentSetRequest parse(Transaction transaction) {
		//Sanity check(s) to make sure the transaction represents a multipart RD 
		if(!transaction.getMessageType().equalsIgnoreCase(MVConstants.MSG_TYPE_REQUEST)) {
			throw new RuntimeException("Transaction ID:" + transaction.getId() + " is not a request message. Exiting from processing the transaction...");
		}
		
		//Get envelope node
		Node envelopeNode = parserHelper.getEnvelopeNode(transaction);
		
		return (envelopeNode != null  
				? parseRequest(envelopeNode)
				: null);
	}
	
	/**
	 * Parses the envelope node for HEADER (for message ID) and BODY (for actual request data).
	 * 
	 * @param envelopeNode
	 * @return
	 */
	private RdNhinDocumentSetRequest parseRequest(Node envelopeNode) {
		RdNhinDocumentSetRequest docsetRequest = new RdNhinDocumentSetRequest();
		
		//Get header and extract messageId and resource ID from it
		Node headerNode = parserHelper.getElementByTagNameDirect(envelopeNode, MVConstants.HEADER);
		parseHeader(headerNode, docsetRequest);
				
		//Get header and extract messageId and resource ID from it
		Node bodyNode = parserHelper.getElementByTagNameDirect(envelopeNode, MVConstants.BODY);
		parseBody(bodyNode, docsetRequest);
		
		return docsetRequest;
	}
	
	/**
	 * populates the Request object with messageID and resourceID.
	 * 
	 * @param header
	 * 			SOAP header that contains messageID and resourceID
	 * 
	 * @param docsetRequest
	 * 			Java object representation of RD request.
	 * 
	 */
	private void parseHeader(Node header, RdNhinDocumentSetRequest docsetRequest){
		//Set message ID
		docsetRequest.setMessageID(getMessageId(header));
		
		//TODO: Set resource ID
	}
	
	private void parseBody(Node body, RdNhinDocumentSetRequest docsetRequest){
		Node docsetRequestNode = parserHelper.getElementByTagNameDirect(body, MVConstants.RD_DOCUMENTSET_REQUEST);
		
		//Now get the list of all requests
		List<Element> docRequestNodes = parserHelper.getElementsByTagNameDirect(docsetRequestNode, MVConstants.RD_DOCUMENT_REQUEST);
		if(docRequestNodes == null || docRequestNodes.size() == 0) {
			return;
		}
		
		for(Element element : docRequestNodes) {
			//Create the request object
			RdNhinDocumentRequest docRequest = new RdNhinDocumentRequest();
			
			//Set HCID
			docRequest.setHomeCommunityId(getHomeCommunityId(element));
			
			//Extract RepositoryID			
			docRequest.setRepositoryId(getRepositoryId(element));
			
			//Extract Document Unique ID
			docRequest.setDocumentUniqueId(getDocuemntId(element));
						
			//Now add it to the list
			docsetRequest.getRequestSet().add(docRequest);
		}		
	}	
}
