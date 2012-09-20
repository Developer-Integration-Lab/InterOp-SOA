/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.parser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.util.MVConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

/**
 * @author Venkat.Keesara Apr 12, 2012
 **/

public class ParserHelper {

	private static final Log log = LogFactory.getLog(ParserHelper.class);
	private static ParserHelper instance = new ParserHelper();

	private ParserHelper() {
	}

	public static ParserHelper getInstance() {
		return instance;
	}

	/**
	 * 
	 * Returns a list of immediate child elements matching a specified tag name from a
	 * given node. Uses Local name.
	 * 
	 * @param parent
	 *            Parent node of the requested child elements.
	 * 
	 * @param childElementTagName
	 *            Tag name of the requested child elements.
	 * 
	 * @return NodeList that contains a list of child elements.
	 * 
	 */
	public List<Element> getElementsByTagNameDirect(Node parent,
			String childElementTagName) {

		// Sanity check
		if (parent == null || StringUtils.isEmpty(childElementTagName)) {
			return null;
		}

		// Get all the child nodes from the parent and filter out all the
		// elements
		// matching the given element tag name.
		NodeList allChildNodes = parent.getChildNodes();
		if (allChildNodes == null || allChildNodes.getLength() == 0) {
			return null;
		}
		List<Element> childElements = new ArrayList<Element>();
		for (int i = 0; i < allChildNodes.getLength(); i++) {
			Node childNode = allChildNodes.item(i);
			if (!(childNode instanceof Element)) {
				continue;
			}
			Element childElement = (Element) childNode;
			if (childNode.getLocalName().equalsIgnoreCase(childElementTagName)) {
				childElements.add(childElement);
			}
		}
		return childElements;
	}

	/**
	 * 
	 * Returns a unique immediate child element matching a specified tag name. Returns first
	 * element if multiple instances are found. This method should be used only
	 * for an element that is unique (0 or 1 instance) under the given parent
	 * node. Uses Local name.
	 * 
	 * @param parent
	 *            Parent node (of type Element) of the requested child elements.
	 * 
	 * @param childElementTagName
	 *            Tag name of the requested child elements.
	 *            
	 * @return Element. Unique child element matching the specified tag name.
	 * 
	 */
	public Element getElementByTagNameDirect(Node parent, String childElementTagName) {

		List<Element> childElements = getElementsByTagNameDirect(parent,
				childElementTagName);
		if (childElements == null || childElements.size() == 0) {
			return null;
		}
		return (Element) childElements.get(0);
	}

	/**
	 * Returns a list of all child elements in any level matching a specified
	 * tag name from a given parent node
	 * 
	 * @param parent
	 * @param childElementTagName
	 * @return
	 */
	public List<Element> getElementsByTagName(Node parent,
			String childElementTagName) {

		// Sanity check
		if (parent == null || StringUtils.isEmpty(childElementTagName)) {
			return null;
		}

		List<Element> childElements = new ArrayList<Element>();
		if (parent instanceof Element) {
			NodeList nodeList = ((Element) parent)
					.getElementsByTagName(childElementTagName);
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int index = 0; index < nodeList.getLength(); index++) {
					Node eachNode = nodeList.item(index);
					if (eachNode != null && eachNode instanceof Element) {
						childElements.add((Element) eachNode);
					}
				}
			}
		}
		return childElements;
	}

	/**
	 * Returns a unique child element in any level matching a specified tag name
	 * from a given parent node
	 * 
	 * @param parent
	 * @param childElementTagName
	 * @return
	 */
	public Element getUniqueElementByTagName(Node parent,
			String childElementTagName) {

		List<Element> childElements = getElementsByTagName(parent,
				childElementTagName);
		if (childElements == null || childElements.size() == 0) {
			return null;
		}
		return (Element) childElements.get(0);
	}

	/**
	 * Returns the text value of an element
	 * 
	 * @param element
	 * @return
	 */
	public String getTextValue(Element element) {
		if (element == null) {
			return "";
		}
		//log.info("TextContent==" + element.getTextContent());
		return element.getTextContent();
	}

	private Document createDocumentObject(byte[] bContent) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(bContent));
			doc.getDocumentElement().normalize();
			return doc;
		} catch (Exception ex) {
			//throw new MVRuntimeException(
			//		"Error creating Document from the message.", ex);
			ex.printStackTrace();
			return null;
		}
	}

	public Node getEnvelopeNode(Transaction transaction) {
		String contentType = transaction.getContentType();
		if (contentType.equals(MVConstants.APPLICATION_SOAP_XML)) {
			Document document = ParserHelper.getInstance()
					.createDocumentObject(transaction.getMessage());
			//printDocument(document);
			return ParserHelper.getInstance().getElementByTagNameDirect(
					document, MVConstants.SOAP_ENVELOPE);
		} else if (contentType.startsWith(MVConstants.MULTIPART)) {
			return getEnvelopeFromMultipartMessage(transaction);
		}

		return null;
	}

	private Node getEnvelopeFromMultipartMessage(Transaction transaction) {
		// Extract individual parts of the multipart message.
		List<Part> parts = AgentMultipartParser.getInstance().processMultipartMessage(transaction);

		if (parts == null) {
			return null;
		}
		
		// Find out the SOAP-envelope part.
		try {
			for (Part part : parts) {
				if (part.isParam()) {
					ParamPart paramPart = (ParamPart) part;
					
					// Stand-up the Document Object from the given part message.
					Document requestDocObject = createDocumentObject(paramPart.getValue());

					// Check if it represents the SOAP Envelope message.
					Node envelopeNode = getElementByTagNameDirect(requestDocObject,
							MVConstants.SOAP_ENVELOPE);
					if (envelopeNode != null) {
						return envelopeNode;
					}
				}
			}
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(
					"Exception parsing the message with Transaction  ID: "
							+ transaction.getId(), ex);
		}
	}
	
	/**
	 * 
	 * @param transaction
	 * @return
	 */
	public String getAttachmentFromMultipartMessage(Transaction transaction) {
		// Extract individual parts of the multipart message.
		List<Part> parts = AgentMultipartParser.getInstance().processMultipartMessage(transaction);

		if (parts == null) {
			return null;
		}

		//Collect all non-soap-envelope parts and treat them as attachment parts. They could be parts of
		//a single file or individual files themselves, We can't differentiate at this level as we lost the
		//header attributes of those parts. So, just simply gather them as one single file. 
		StringBuffer sbf = new StringBuffer();
		try {
			for (Part part : parts) {
				if (part.isParam()) {
					ParamPart paramPart = (ParamPart) part;
					
					// Check if the part is an XML. If the part is not an XML, we treat it as an attachment part.					
					Document requestDocObject = createDocumentObject(paramPart.getValue());
					if(requestDocObject == null 
							|| (getElementByTagNameDirect(requestDocObject,
									MVConstants.SOAP_ENVELOPE) == null)) {
						sbf.append(paramPart.getStringValue());
					}
				}
			}
			return (sbf.toString().length() == 0 ? null : sbf.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(
					"Exception parsing the message with Transaction  ID: "
							+ transaction.getId(), ex);
		}
	}

	public void printDocument(Document doc) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

