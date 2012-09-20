/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.parser.pd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.pd.PDNhinRequest;
import net.aegis.mv.jaxb.pd.Patient;
import net.aegis.mv.jaxb.pd.Personname;
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

public class PDRequestParser implements INhinMsgParser {

	private static final Log log = LogFactory.getLog(PDRequestParser.class);

	private static PDRequestParser instance = new PDRequestParser();

	private PDRequestParser() {
	}

	public static PDRequestParser getInstance() {
		return instance;
	}

	/**
	 * parse NHIN PD request message
	 * 
	 * @param message
	 * @return
	 */
	public PDNhinRequest parse(Transaction transaction) {
		log.info("<---  parsePDRequestMessage() Start");

		byte[] message = transaction.getMessage();
		PDNhinRequest pDNhinRequest = new PDNhinRequest();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(message));
			doc.getDocumentElement().normalize();

			NodeList headersList = doc.getElementsByTagNameNS(
					MVConstants.URI_SOAP12_ENV, MVConstants.HEADER);
			if (headersList != null && headersList.item(0) != null) {
				parseHeader(headersList.item(0), pDNhinRequest);
			}
			NodeList bodyList = doc.getElementsByTagNameNS(
					MVConstants.URI_SOAP12_ENV, MVConstants.BODY);
			if (bodyList != null && bodyList.item(0) != null) {
				parseBody(bodyList.item(0), pDNhinRequest);
			}

		} catch (ParserConfigurationException e) {
			log.error(e.getStackTrace());
		} catch (SAXException e) {
			log.error(e.getStackTrace());
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}

		return pDNhinRequest;

	}

	private void parseHeader(Node header, PDNhinRequest pDNhinRequest) {

		log.info("Parsing " + header.getLocalName());
		Element relatesToElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(header, MVConstants.MESSAGE_ID);
		String textValue = ParserHelper.getInstance().getTextValue(
				relatesToElement);
		pDNhinRequest.setMessageID(textValue);
	}

	private void parseBody(Node body, PDNhinRequest pDNhinRequest) {
		log.info("Parsing " + body.getLocalName());

		Element pDRequestElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(body,
						MVConstants.PD_REQUEST_BODY_ROOT_TAG);
						//MsgTypeEnum.PD_REQUEST.getTextValue());
		if (pDRequestElement != null) {
			Element receiverElement = ParserHelper.getInstance()
					.getUniqueElementByTagName(body, MVConstants.RECEIVER);
			Element senderElement = ParserHelper.getInstance()
					.getUniqueElementByTagName(body, MVConstants.SENDER);

			Element queryByParameterElement = ParserHelper.getInstance()
					.getUniqueElementByTagName(pDRequestElement,
							MVConstants.QUERY_BY_PARAMETER);
			if (queryByParameterElement != null) {
				parseQueryByParameter(queryByParameterElement, pDNhinRequest);
			}
		}
		log.info("PDNhinRequest====" + pDNhinRequest);
	}

	private void parseQueryByParameter(Element element,
			PDNhinRequest pDNhinRequest) {
		log.info("Parsing " + element.getLocalName());
		Element paramsListElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.PARAMETER_LIST);
		if (paramsListElement != null) {
			parseParameterList(paramsListElement, pDNhinRequest);
		}
	}

	private void parseParameterList(Element element, PDNhinRequest pDNhinRequest) {
		log.info("Parsing " + element.getLocalName());
		Patient senderPatient = new Patient();
		Sender sender = new Sender();
		Element genderElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element,
						MVConstants.LIVING_SUBJECT_ADMINISTRATIVE_GENDER);
		if (genderElement != null) {
			parseLivingSubjectAdministrativeGender(genderElement,
					pDNhinRequest, senderPatient);
		}
		Element birthTimeElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element,
						MVConstants.LIVING_SUBJECT_BIRTH_TIME);
		if (birthTimeElement != null) {
			parseLivingSubjectBirthTime(birthTimeElement, pDNhinRequest,
					senderPatient);
		}

		List<Element> livingSubIDList = ParserHelper.getInstance()
				.getElementsByTagName(element, MVConstants.LIVING_SUBJECT_ID);
		if (livingSubIDList != null && livingSubIDList.size() > 0) {
			for (Element livingSubIDElement : livingSubIDList) {
				if (livingSubIDElement != null) {
					parseLivingSubjectIdForPatientIdOrSSN(livingSubIDElement,
							pDNhinRequest, senderPatient);

				}

			}
		}
		Element nameElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element,
						MVConstants.LIVING_SUBJECT_NAME);
		Personname personName = new Personname();
		if (nameElement != null) {
			parseLivingSubjectName(nameElement, pDNhinRequest, personName);
			senderPatient.getPersonnames().add(personName);
		}

		sender.setPatient(senderPatient);
		pDNhinRequest.setSender(sender);

	}

	private void parseLivingSubjectAdministrativeGender(Element element,
			PDNhinRequest pDNhinRequest, Patient senderPatient) {
		log.info("Parsing " + element.getLocalName());

		Element valueElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.VALUE);
		if (valueElement != null) {
			String genderCode = valueElement.getAttribute(MVConstants.CODE);
			senderPatient.setGender(genderCode);
		}
	}

	private void parseLivingSubjectBirthTime(Element element,
			PDNhinRequest pDNhinRequest, Patient senderPatient) {
		log.info("Parsing " + element.getLocalName());

		Element valueElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.VALUE);
		if (valueElement != null) {
			String birthTime = valueElement.getAttribute(MVConstants.VALUE);
			// senderPatient.setDateOfBirth(birthTime);
		}
	}

	private void parseLivingSubjectIdForPatientIdOrSSN(Element element,
			PDNhinRequest pDNhinRequest, Patient senderPatient) {
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

				senderPatient.setAssigningAuthority(assigningAuthorityOrSSN);
				if (StringUtils.isNotEmpty(patientID)) {
					senderPatient.setPatientId(patientID);
				}

				/*
				 * sender.setPatient(senderPatient);
				 * pDNhinRequest.setSender(sender);
				 */

			} else {
				senderPatient.setSsn(assigningAuthorityOrSSN);
			}
		}
	}

	private void parseLivingSubjectName(Element element,
			PDNhinRequest pDNhinRequest, Personname personName) {
		log.info("Parsing " + element.getLocalName());

		Element valueElement = ParserHelper.getInstance()
				.getUniqueElementByTagName(element, MVConstants.VALUE);
		if (valueElement != null) {
			Element familyElement = ParserHelper
					.getInstance()
					.getUniqueElementByTagName(valueElement, MVConstants.FAMILY);
			if (familyElement != null) {
				String lastName = ParserHelper.getInstance().getTextValue(
						familyElement);
				personName.setLastName(lastName);
			}
			Element givenElement = ParserHelper.getInstance()
					.getUniqueElementByTagName(valueElement, MVConstants.GIVEN);
			if (givenElement != null) {
				String firstName = ParserHelper.getInstance().getTextValue(
						givenElement);
				personName.setFirstName(firstName);
			}

			Element middleElement = ParserHelper.getInstance()
					.getUniqueElementByTagName(valueElement, MVConstants.GIVEN);
			if (middleElement != null) {
				String middleName = ParserHelper.getInstance().getTextValue(
						middleElement);
				personName.setMiddleName(middleName);
			}
		}
	}

}
