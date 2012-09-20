package net.aegis.mv.parser.rd;

import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.rd.RdNhinDocumentResponse;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetResponse;
import net.aegis.mv.jaxb.rd.RdNhinRegistryError;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.util.MVConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class RDResponseParser extends RDParserBase implements INhinMsgParser {
	
	private static final Log log = LogFactory.getLog(RDResponseParser.class);
	
	private static RDResponseParser instance = new RDResponseParser();
	private RDResponseParser() {}
	
	public static RDResponseParser getInstance() {
		return instance;
	}
	
	/**
	 * 
	 * Parses the message and message-header binary messages from a given <code>Transaction</code> object and 
	 * stands up a <code>RdNhinDocumentResponse</code> that represents the RD response.
	 *  
	 * @param transaction
	 * 
	 * @return RdNhinDocumentSetResponse
	 * 
	 */
	public RdNhinDocumentSetResponse parse(Transaction transaction) {
		//Sanity check(s) to make sure the transaction represents a multipart RD 
		if(!transaction.getMessageType().equalsIgnoreCase(MVConstants.MSG_TYPE_RESPONSE)) {
			throw new RuntimeException("Transaction ID:" + transaction.getId() + " is not a response message. Exiting from processing the transaction...");
		}
		
		//Get envelope node
		Node envelopeNode = parserHelper.getEnvelopeNode(transaction);
		
		return (envelopeNode != null  
				? parseResponse(envelopeNode, transaction)
				: null);
		
	}
	
	/**
	 * Parses the envelope node for HEADER (for message ID) and BODY (for actual response data).
	 * 
	 * @param envelopeNode
	 * @return
	 */
	private RdNhinDocumentSetResponse parseResponse(Node envelopeNode, Transaction transaction) {
		RdNhinDocumentSetResponse response = new RdNhinDocumentSetResponse();
		
		//Get header and extract messageId and resource ID from it
		Node headerNode = parserHelper.getElementByTagNameDirect(envelopeNode, MVConstants.HEADER);
		parseHeader(headerNode, response);
				
		//Get header and extract messageId and resource ID from it
		Node bodyNode = parserHelper.getElementByTagNameDirect(envelopeNode, MVConstants.BODY);
		parseBody(bodyNode, response, transaction);
		
		//Now, add the document object in case of a successful response.
		//if(response.getResponseStatusType().toLowerCase().endsWith("success")) {
			
		//}
		
		return response;
	}
	
	/**
	 * populates the response object with messageID and resourceID.
	 * 
	 * @param header
	 * 			SOAP header that contains messageID and resourceID
	 * 
	 * @param docsetResponse
	 * 			Java object representation of RD response.
	 * 
	 */
	private void parseHeader(Node header, RdNhinDocumentSetResponse docsetResponse){
		
		//Set message ID
		docsetResponse.setMessageID(getMessageId(header));
		
		//Set relates-to
		docsetResponse.setRelatesTo(getRelatesTo(header));		
	}
	
	private void parseBody(Node body, RdNhinDocumentSetResponse docsetResponse, Transaction transaction){
		Node docsetResponseNode = parserHelper.getElementByTagNameDirect(body, MVConstants.RD_DOCUMENTSET_RESPONSE);
		
		//Get the status of the response to check the Fail or Success.
		Element regisrtyResponseElement = parserHelper.getElementByTagNameDirect(docsetResponseNode, MVConstants.RD_REGISTRY_RESPONSE);
		if(regisrtyResponseElement != null) {
			String registryResponse = regisrtyResponseElement.getAttribute(MVConstants.RD_REGISTRY_RESPONSE_STATUS_NAME);
			docsetResponse.setResponseStatusType(registryResponse);
			if(docsetResponse.getResponseStatusType().endsWith(MVConstants.RD_REGISTRY_RESPONSE_STATUS_FAILURE)) {
				List<RdNhinRegistryError> registryErrors = processErrorResponse(regisrtyResponseElement);
				docsetResponse.getRegistryErrors().addAll(registryErrors);
			}
		}
		
		//Now get the list of all responses
		List<Element> docResponseElements = parserHelper.getElementsByTagNameDirect(docsetResponseNode, MVConstants.RD_DOCUMENT_RESPONSE);
		if(docResponseElements == null || docResponseElements.size() == 0) {
			return;
		}
		
		for(Element docResponseElement : docResponseElements) {
			//Create Response object
			RdNhinDocumentResponse docResponse = new RdNhinDocumentResponse();
			
			//Extract HCID
			docResponse.setHomeCommunityId(getHomeCommunityId(docResponseElement));
			
			//Extract RepositoryID			
			docResponse.setRepositoryId(getRepositoryId(docResponseElement));
			
			//Extract Document Unique ID
			docResponse.setDocumentUniqueId(getDocuemntId(docResponseElement));
						
			//Extract Document MIME type
			docResponse.setMimeType(getMimeType(docResponseElement));
						
			//Extract the Document
			docResponse.setDocument(getDocument(docResponseElement, transaction));
			
			//Now add it to the list
			docsetResponse.getResponseSet().add(docResponse);
		}
	}
	
	private String getDocument(Element docResponseElement, Transaction transaction) {
		Element documentElement = parserHelper.getElementByTagNameDirect(docResponseElement, MVConstants.RD_DOCUMENT);
		
		//The Document could be an attachment separated by multipart boundary (OR)
		//could be an in-line text. 
		if(documentElement != null) {
			//In-line text.
			Element includeElement = parserHelper.getElementByTagNameDirect(documentElement, "Include");
			if(includeElement == null) {
				return documentElement.getTextContent();
			} 
			//attachment
			else {
				if(includeElement.getAttribute("href") != null) {
					return parserHelper.getAttachmentFromMultipartMessage(transaction);
				}
			} 
		}
		return null;
	}
	
	private List<RdNhinRegistryError> processErrorResponse(
			Element regisrtyResponseElement) {
		Element errorListElement = parserHelper.getElementByTagNameDirect(regisrtyResponseElement, MVConstants.RD_REGISTRY_ERROR_LIST);
		if(errorListElement != null) {
			List<Element> errorElements = parserHelper.getElementsByTagNameDirect(errorListElement, MVConstants.RD_REGISTRY_ERROR);
			if(errorElements != null) {
				List<RdNhinRegistryError> errors = new ArrayList<RdNhinRegistryError>();
				for(Element errorElement : errorElements) {
					RdNhinRegistryError error = new RdNhinRegistryError(); 
					error.setSeverity(errorElement.getAttribute(MVConstants.RD_REGISTRY_ERROR_ATTRIBUTE_SEVERITY));
					error.setErrorCode(errorElement.getAttribute(MVConstants.RD_REGISTRY_ERROR_ATTRIBUTE_ERRORCODDE));
					error.setCodeContext(errorElement.getAttribute(MVConstants.RD_REGISTRY_ERROR_ATTRIBUTE_CODECONTEXT));
					error.setLocation(errorElement.getAttribute(MVConstants.RD_REGISTRY_ERROR_ATTRIBUTE_LOCATION));
					error.setDescription(errorElement.getTextContent());
					errors.add(error);
				}
				return errors;
			}
		}
		return null;
	}

	private String getRelatesTo(Node node) {
		Element relatesToElement = parserHelper.getElementByTagNameDirect(node, MVConstants.RELATES_TO);
		return parserHelper.getTextValue(relatesToElement);
	}
	
	protected String getMimeType(Node node) {
		Element mimeTypeElement = parserHelper.getElementByTagNameDirect(node, MVConstants.RD_MIME_TYPE);
		return parserHelper.getTextValue(mimeTypeElement);
	}
	
	protected boolean hasDocument(Node node) {
		Element documentElement = parserHelper.getElementByTagNameDirect(node, MVConstants.RD_DOCUMENT);
		return (documentElement != null);
	}
}
