/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.parser.pd;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.pd.PDNhinResponse;
import net.aegis.mv.jaxb.pd.Patient;
import net.aegis.mv.jaxb.pd.Receiver;
import net.aegis.mv.jaxb.pd.Sender;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.parser.ParserHelper;
import net.aegis.mv.util.MVConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Venkat.Keesara Apr 10, 2012
 **/
public class PDResponseParser implements INhinMsgParser {

	private static final Log log = LogFactory.getLog(PDResponseParser.class);

	private static PDResponseParser instance = new PDResponseParser();

	private PDResponseParser() {
	}

	public static PDResponseParser getInstance() {
		return instance;
	}

	/**
	 * parse HNIN PD response message
	 * 
	 * @param message
	 * @return
	 */
	public PDNhinResponse parse(Transaction transaction) {
		log.info("<---  parsePDResponseMessage() Start");

		byte[] message = transaction.getMessage();
		PDNhinResponse pDNhinResponse = new PDNhinResponse();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		// log.info("message====" + new String(message));
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(message));
			doc.getDocumentElement().normalize();

			NodeList headersList = doc.getElementsByTagNameNS(
					MVConstants.URI_SOAP12_ENV, MVConstants.HEADER);
			if (headersList != null && headersList.item(0) != null) {
				parseHeader(headersList.item(0), pDNhinResponse);
			}
			NodeList bodyList = doc.getElementsByTagNameNS(
					MVConstants.URI_SOAP12_ENV, MVConstants.BODY);
			if (bodyList != null && bodyList.item(0) != null) {
				parseBody(bodyList.item(0), pDNhinResponse);

			}

		} catch (ParserConfigurationException e) {
			log.error(e.getStackTrace());
		} catch (SAXException e) {
			log.error(e.getStackTrace());
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}

		return pDNhinResponse;
	}

	private void parseHeader(Node header, PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + header.getLocalName());
		Element relatesToElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(header, MVConstants.RELATES_TO);
		String textValue = ParserHelper.getInstance().getTextValue(
				relatesToElement);
		pDNhinResponse.setRelatesTo(textValue);

		/*
		 * NodeList nl = header.getChildNodes(); log.info( header.getNodeName()+
		 * " depth::" + nl.getLength()); for(int i=0, cnt=nl.getLength(); i<cnt;
		 * i++) { Node eachNode = nl.item(i);
		 * log.debug("===========["+eachNode+"]============="); //
		 * log.debug("eachNode.getNodeType():::" + eachNode.getNodeType());
		 * //log.debug("eachNode.getNodeValue():::" + eachNode.getNodeValue());
		 * log.debug("eachNode.getNodeName():::" + eachNode.getNodeName()); //
		 * log.debug("eachNode.getLocalName():::" + eachNode.getLocalName());
		 * if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE &&
		 * eachNode.getLocalName()!=null &&
		 * eachNode.getLocalName().equalsIgnoreCase(MVConstants.RELATES_TO)){
		 * 
		 * String textValue =
		 * ParserHelper.getInstance().getTextValue((Element)eachNode);
		 * log.info("textValue==" + textValue);
		 * pDNhinResponse.setRelatesTo(textValue); break ; }
		 * 
		 * }
		 */
	}

	private void parseBody(Node body, PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + body.getLocalName());
		Element pDResponseElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(body,
						MVConstants.PD_RESPONSE_BODY_ROOT_TAG);
						//MsgTypeEnum.PD_RESPONSE.getTextValue());
		if (pDResponseElement != null) {
			// process subject
			Element subjectElement = ParserHelper.getInstance()
					.getUniqueElementByTagName(pDResponseElement,
							MVConstants.SUBJECT);
			if (subjectElement != null) {
				parseSubject(subjectElement, pDNhinResponse);
			}
			// process queryack
			Element queryAckElement = ParserHelper.getInstance()
					.getUniqueElementByTagName(pDResponseElement,
							MVConstants.QUERY_ACK);
			if (queryAckElement != null) {
				parseQueryAck(queryAckElement, pDNhinResponse);
			}
			if (!pDNhinResponse.isCorrelationFound()
					&& !pDNhinResponse.isPatientIdFound()) {

				Element queryByParameterElement = ParserHelper.getInstance()
						.getUniqueElementByTagName(pDResponseElement,
								MVConstants.QUERY_BY_PARAMETER);
				if (queryByParameterElement != null) {
					parseQueryByParameter((Element) queryByParameterElement,
							pDNhinResponse);
				}

			}
		}
		log.info("pDNhinResponse====" + pDNhinResponse);
	}

	private void parseQueryByParameter(Element element,
			PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());
		Element paramsListElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.PARAMETER_LIST);
		if (paramsListElement != null) {
			parseParameterList(paramsListElement, pDNhinResponse);
		}
	}

	private void parseParameterList(Element element,
			PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());

		Element livingSubIDElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element,
						MVConstants.LIVING_SUBJECT_ID);
		if (livingSubIDElement != null) {
			parseLivingSubjectId(livingSubIDElement, pDNhinResponse);
		}
		if (pDNhinResponse.isPatientIdFound()) {
			return;
		}

	}

	private void parseLivingSubjectId(Element element,
			PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());

		Element valueElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.VALUE);
		if (valueElement != null) {
			String patientID = valueElement.getAttribute(MVConstants.EXTENSION);
			String assigningAuthorityOrSSN = valueElement
					.getAttribute(MVConstants.ROOT);
			if (StringUtils.isNotEmpty(patientID)
					&& StringUtils.isNotEmpty(assigningAuthorityOrSSN)
					&& !assigningAuthorityOrSSN
							.equalsIgnoreCase(MVConstants.LIVING_SUBJECT_ID_ROOT_FOR_SSN)) {

				Receiver responseReceiver = new Receiver();
				Patient patient = new Patient();
				patient.setAssigningAuthority(assigningAuthorityOrSSN);
				if (!pDNhinResponse.isPatientIdFound()
						&& StringUtils.isNotEmpty(patientID)) {
					patient.setPatientId(patientID);
					pDNhinResponse.setPatientIdFound(true);
				} else {
					pDNhinResponse.setPatientIdFound(false);
				}
				responseReceiver.setPatient(patient);
				pDNhinResponse.setReceiver(responseReceiver);

			}
		}

		/*
		 * if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
		 * NodeList nl = element.getChildNodes(); if(nl!=null && nl.getLength()
		 * > 0){ for(int i=0, cnt=nl.getLength(); i<cnt; i++){ Node eachNode =
		 * nl.item(i);
		 * 
		 * if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE &&
		 * eachNode.getLocalName()!=null &&
		 * eachNode.getLocalName().equalsIgnoreCase(MVConstants.VALUE)){
		 * 
		 * Element elementNode = (Element)eachNode ; String patientID =
		 * elementNode.getAttribute(MVConstants.EXTENSION); String
		 * assigningAuthorityOrSSN = elementNode.getAttribute(MVConstants.ROOT);
		 * if(StringUtils.isNotEmpty(patientID) &&
		 * StringUtils.isNotEmpty(assigningAuthorityOrSSN) &&
		 * !assigningAuthorityOrSSN
		 * .equalsIgnoreCase(MVConstants.LIVING_SUBJECT_ID_ROOT_FOR_SSN)){
		 * 
		 * Receiver responseReceiver = new Receiver(); Patient patient = new
		 * Patient(); patient.setAssigningAuthority(assigningAuthorityOrSSN);
		 * if(!pDNhinResponse.isPatientIdFound() &&
		 * StringUtils.isNotEmpty(patientID)){ patient.setPatientId(patientID);
		 * pDNhinResponse.setPatientIdFound(true); }else{
		 * pDNhinResponse.setPatientIdFound(false); }
		 * responseReceiver.setPatient(patient);
		 * pDNhinResponse.setReceiver(responseReceiver); break; }
		 * 
		 * }
		 * 
		 * } } }
		 */
	}

	private void parseQueryAck(Element element, PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());
		Element queryResponseCodeElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element,
						MVConstants.QUERY_RESPONSE_CODE);
		if (queryResponseCodeElement != null) {
			String statusCode = queryResponseCodeElement
					.getAttribute(MVConstants.CODE);
			pDNhinResponse.setStatus(statusCode);

		}
		/*
		 * if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
		 * NodeList nl = element.getChildNodes(); if(nl!=null && nl.getLength()
		 * > 0){ for(int i=0, cnt=nl.getLength(); i<cnt; i++){ Node eachNode =
		 * nl.item(i);
		 * 
		 * if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE &&
		 * eachNode.getLocalName()!=null &&
		 * eachNode.getLocalName().equalsIgnoreCase
		 * (MVConstants.QUERY_RESPONSE_CODE)){
		 * 
		 * Element elementNode = (Element)eachNode ; String statusCode =
		 * elementNode.getAttribute(MVConstants.CODE);
		 * pDNhinResponse.setStatus(statusCode);
		 * 
		 * break; }
		 * 
		 * } } }
		 */
	}

	private void parseSubject(Element element, PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());
		Element registrationEventElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element,
						MVConstants.REGISTRATIONEVENT);
		if (registrationEventElement != null) {
			parseRegistrationEvent(registrationEventElement, pDNhinResponse);
		}
	}

	private void parseRegistrationEvent(Element element,
			PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());
		Element subject1 = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.SUBJECT1);
		if (subject1 != null) {
			parseSubject1(subject1, pDNhinResponse);
		}

	}

	private void parseSubject1(Element element, PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());
		Element patientElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.PATIENT);
		if (patientElement != null) {
			parsePatient(patientElement, pDNhinResponse);
		}
	}

	private void parsePatient(Element element, PDNhinResponse pDNhinResponse) {
		log.info("Parsing " + element.getLocalName());
		Element idElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.ID);
		if (idElement != null) {

			String patientID = idElement.getAttribute(MVConstants.EXTENSION);
			String assigningAuthority = idElement
					.getAttribute(MVConstants.ROOT);
			Sender responseSender = new Sender();
			Patient patient = new Patient();
			patient.setAssigningAuthority(assigningAuthority);
			if (StringUtils.isNotEmpty(patientID)) {
				patient.setPatientId(patientID);
				pDNhinResponse.setPatientIdFound(true);
				pDNhinResponse.setCorrelationFound(true);
			} else {
				pDNhinResponse.setPatientIdFound(false);
				pDNhinResponse.setCorrelationFound(false);
			}
			responseSender.setPatient(patient);
			pDNhinResponse.setSender(responseSender);
		}

		/*
		 * if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
		 * NodeList nl = element.getChildNodes(); if(nl!=null && nl.getLength()
		 * > 0){ for(int i=0, cnt=nl.getLength(); i<cnt; i++){ Node eachNode =
		 * nl.item(i);
		 * 
		 * if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE &&
		 * eachNode.getLocalName()!=null &&
		 * eachNode.getLocalName().equalsIgnoreCase(MVConstants.ID)){
		 * 
		 * Element elementNode = (Element)eachNode ; String patientID =
		 * elementNode.getAttribute(MVConstants.EXTENSION); String
		 * assigningAuthority = elementNode.getAttribute(MVConstants.ROOT);
		 * Sender responseSender = new Sender(); Patient patient = new
		 * Patient(); patient.setAssigningAuthority(assigningAuthority);
		 * if(StringUtils.isNotEmpty(patientID)){
		 * patient.setPatientId(patientID);
		 * pDNhinResponse.setPatientIdFound(true);
		 * pDNhinResponse.setCorrelationFound(true); }else{
		 * pDNhinResponse.setPatientIdFound(false);
		 * pDNhinResponse.setCorrelationFound(false); }
		 * responseSender.setPatient(patient);
		 * pDNhinResponse.setSender(responseSender);
		 * 
		 * break; }
		 * 
		 * } } }
		 */
	}

}