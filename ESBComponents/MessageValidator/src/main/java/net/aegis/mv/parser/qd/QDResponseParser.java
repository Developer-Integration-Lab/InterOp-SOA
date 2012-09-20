package net.aegis.mv.parser.qd;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.jaxb.qd.DocumentInfo;
import net.aegis.mv.jaxb.qd.QDNhinResponse;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.util.MVConstants;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * Naresh.Jaganathan
 * March 20, 2012
 */
public class QDResponseParser implements INhinMsgParser
{

	private static final Log log = LogFactory.getLog(QDResponseParser.class);

	private static QDResponseParser instance = new QDResponseParser();
	private QDResponseParser() {}
		
	public static QDResponseParser getInstance() {
		return instance;
	}

	public QDNhinResponse parse(Transaction transaction) {
		byte[] message = transaction.getMessage();
		QDNhinResponse qDNhinResponse = new QDNhinResponse();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(message));
			doc.getDocumentElement().normalize();

			NodeList headersList = doc.getElementsByTagNameNS(MVConstants.URI_SOAP12_ENV ,  MVConstants.HEADER);
			if(headersList!=null && headersList.item(0)!=null){
				processHeader(headersList.item(0), qDNhinResponse);
			}
			NodeList bodyList = doc.getElementsByTagNameNS(MVConstants.URI_SOAP12_ENV ,  MVConstants.BODY);
			if(bodyList!=null && bodyList.item(0)!=null){
				processBody(bodyList.item(0), qDNhinResponse);
			}
		} catch (ParserConfigurationException e) {
			log.error(e.getStackTrace());
		} catch (SAXException e) {
			log.error(e.getStackTrace());
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}

		log.info("\n\n************ QDNhinResponse Parameters Start ******************");
		log.info("ResponderHCID : "+ qDNhinResponse.getResponderHCID());
		log.info("ResponderAAID : "+ qDNhinResponse.getResponderAAID());
		log.info("RelatesToId : "+ qDNhinResponse.getRelatesTo());
		log.info("ResponderPatientId : "+ qDNhinResponse.getResponderPatientID());
		log.info("ResponseStatusType : "+ qDNhinResponse.getResponseStatusType());
		log.info("DocumentList Size : "+ qDNhinResponse.getDocumentList().size());
		for(DocumentInfo doc : qDNhinResponse.getDocumentList()){
			log.info("\n******************************\n");
			log.info("DocumentId : "+ doc.getDocumentId());
			log.info("RepositoryUniqueId : "+ doc.getRepositoryUniqueId());
			log.info("ClassCode : "+ doc.getClassCode());
			log.info("Hash : "+ doc.getHash());
			log.info("Size : "+ doc.getSize());
			log.info("ExtrinsicObjectStatus : "+ doc.getExtrinsicObjectStatus());
			log.info("\n******************************\n");
		}
		log.info("************** QDNhinResponse Parameters End ****************\n\n");

		return qDNhinResponse;	
	}

	private void processHeader(Node  header , QDNhinResponse qDNhinResponse){

		NodeList nl = header.getChildNodes();
		log.info( header.getNodeName()+ " depth::" + nl.getLength());
		for(int i=0, cnt=nl.getLength(); i<cnt; i++)
		{
			Node eachNode = nl.item(i);
			log.info("===========["+eachNode+"]=============");
			log.info("eachNode.getNodeType():::" + eachNode.getNodeType());
			log.info("eachNode.getNodeValue():::" + eachNode.getNodeValue());
			log.info("eachNode.getNodeName():::" + eachNode.getNodeName());
			log.info("eachNode.getLocalName():::" + eachNode.getLocalName());
			if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
					&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.RELATES_TO)){

				String textValue = getElementTextValue((Element)eachNode);
				log.info("textValue==" + textValue);
				qDNhinResponse.setRelatesTo(textValue);
				break ; 
			}

		}
	}

	private void processBody(Node  body , QDNhinResponse qDNhinResponse){

		NodeList nl = body.getChildNodes();
		log.info( body.getNodeName()+ " depth::" + nl.getLength());
		for(int i=0, cnt=nl.getLength(); i<cnt; i++)
		{
			Node eachNode = nl.item(i);
			log.info("===========["+eachNode+"]=============");
			log.info("eachNode.getNodeType():::" + eachNode.getNodeType());
			log.info("eachNode.getNodeValue():::" + eachNode.getNodeValue());
			log.info("eachNode.getNodeName():::" + eachNode.getNodeName());
			log.info("eachNode.getLocalName():::" + eachNode.getLocalName());
			if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
					&& eachNode.getLocalName()!=null
					&& eachNode.getLocalName().equalsIgnoreCase(MVConstants.QD_RESPONSE_BODY_ROOT_TAG)){
					//&& eachNode.getLocalName().equalsIgnoreCase(MsgTypeEnum.QD_RESPONSE.getTextValue())){

				Element mainElement = (Element)eachNode;
				processAdhocQueryResponse((Element)mainElement, qDNhinResponse);
			}
		}
		log.info("pDNhinResponse====" + qDNhinResponse);
	}



	private void processAdhocQueryResponse(Element  element, QDNhinResponse qDNhinResponse){

		log.info("Processing " + element.getLocalName());
		//"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success"
		String [] status = element.getAttribute(MVConstants.STATUS).split(LabConstants.SPLITTER_COLON);
		qDNhinResponse.setResponseStatusType(status[(status.length) - 1]);
		if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
			NodeList nl = element.getChildNodes();
			if(nl!=null && nl.getLength() > 0){
				for(int i=0, cnt=nl.getLength(); i<cnt; i++){
					Node eachNode = nl.item(i);
					if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
							&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.REGISTRYOBJECTLIST)){

						Element elementNode = (Element)eachNode;
						processRegistryObjectList((Element)elementNode, qDNhinResponse);
					}
				}
			}
		}
	}

	private void processRegistryObjectList(Element  element, QDNhinResponse qDNhinResponse){

		log.info("Processing " + element.getLocalName());
		if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
			NodeList nl = element.getChildNodes();
			if(nl!=null && nl.getLength() > 0){
				for(int i=0, cnt=nl.getLength(); i<cnt; i++){
					Node eachNode = nl.item(i);
					if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
							&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.EXTRINSICOBJECT)){

						Element elementNode = (Element)eachNode;
						processExtrinsicObject((Element)elementNode, qDNhinResponse);
					}
				}
			}
		}
	}

	private void processExtrinsicObject(Element  element , QDNhinResponse qDNhinResponse){
		log.info("Processing " + element.getLocalName());
		String hcid = element.getAttribute(MVConstants.HOME);
		qDNhinResponse.setResponderHCID(MessageValidatorUtil.getInstance().trimPrefixForHomeCommunityId(hcid));
		DocumentInfo documentInfo = new DocumentInfo();
		documentInfo.setExtrinsicObjectStatus(element.getAttribute(MVConstants.STATUS));
		if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
			NodeList nl = element.getChildNodes();
			if(nl!=null && nl.getLength() > 0){
				for(int i=0, cnt=nl.getLength(); i<cnt; i++){
					Node eachNode = nl.item(i);
					if(eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE && eachNode.getLocalName()!=null){
						if(eachNode.getLocalName().equalsIgnoreCase(MVConstants.SLOT)){
							Element elementNode = (Element)eachNode;
							processSlot((Element)elementNode, qDNhinResponse, documentInfo);
						}else if(eachNode.getLocalName().equalsIgnoreCase(MVConstants.CLASSIFICATION)){						
							Element elementNode = (Element)eachNode;
							if(elementNode.getAttribute(MVConstants.CLASSIFICATIONSCHEME).equals(MVConstants.CLASSCODESCHEME)){
								documentInfo.setClassCode(elementNode.getAttribute(MVConstants.NODEREPRESENTATION));
							}
						}else if(eachNode.getLocalName().equalsIgnoreCase(MVConstants.EXTERNALIDENTIFIER)){
							Element elementNode = (Element)eachNode;
							if(elementNode.getAttribute(MVConstants.IDENTIFICATIONSCHEME).equals(MVConstants.DOCUNIQUEID_IDENTIFICATIONSCHEME)){
									documentInfo.setDocumentId(elementNode.getAttribute(MVConstants.VALUE));
							}
							if(elementNode.getAttribute(MVConstants.IDENTIFICATIONSCHEME).equals(MVConstants.PATIENTID_IDENTIFICATIONSCHEME)){
								String uniquePatientId = elementNode.getAttribute(MVConstants.VALUE);
								if(uniquePatientId.contains(LabConstants.SPLITTER_AMPERSAND) && uniquePatientId.contains(LabConstants.TRIPPLE_CARET)){
									setPatientIdAndAAId(qDNhinResponse, uniquePatientId);
								}else{
									qDNhinResponse.setResponderPatientID(uniquePatientId);
								}
							}
						}
					}
				}
				qDNhinResponse.getDocumentList().add(documentInfo);
			}
		}
	}

	private void processSlot(Element  element, QDNhinResponse qDNhinResponse, DocumentInfo documentInfo){
		log.info("Processing " + element.getLocalName());
		String type = element.getAttribute(MVConstants.NAME);
		if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
			NodeList nl = element.getChildNodes();
			if(nl!=null && nl.getLength() > 0){
				for(int i=0, cnt=nl.getLength(); i<cnt; i++){
					Node eachNode = nl.item(i);
					if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
							&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.VALUELIST)){

						Element elementNode = (Element)eachNode;
						processValueList((Element)elementNode, qDNhinResponse, documentInfo, type);
					}
				}
			}
		}
	}

	private  void processValueList(Element  element , QDNhinResponse qDNhinResponse, DocumentInfo documentInfo, String type){
		log.info("Processing " + element.getLocalName());
		String value ="";
		if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
			NodeList nl = element.getChildNodes();
			if(nl!=null && nl.getLength() > 0){
				for(int i=0, cnt=nl.getLength(); i<cnt; i++){
					Node eachNode = nl.item(i);
					if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
							&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.VALUE)){
						value = eachNode.getTextContent();
						if(type.equalsIgnoreCase(MVConstants.HASH)){
							documentInfo.setHash(value);
						}else if(type.equalsIgnoreCase(MVConstants.SIZE)){
							documentInfo.setSize(value);
						}else if(type.equalsIgnoreCase(MVConstants.SOURCEPATIENTID)){
							if(qDNhinResponse.getResponderPatientID() == null){
								if(value.contains(LabConstants.SPLITTER_AMPERSAND) && value.contains(LabConstants.TRIPPLE_CARET)){
									setPatientIdAndAAId(qDNhinResponse, value);
								}else{
									qDNhinResponse.setResponderPatientID(value);
								}
							}
						}else if(type.equalsIgnoreCase(MVConstants.REPOSITORYUNIQUEID)){
							documentInfo.setRepositoryUniqueId(value);
						}
					}
				}				
			}
		}
	}

	private String getElementTextValue(Element ele) {
		String textVal = null;

		if(ele != null ) {
			Node firstChild = ele.getFirstChild();
			if(firstChild!=null){
				textVal = firstChild.getNodeValue();
				log.info( ele.getNodeName() + " textVal===" + textVal);
			}
		}

		return textVal;
	}

	public void setPatientIdAndAAId(QDNhinResponse qDNhinResponse, String uniquePatientId){
		log.info("setPatientIdAndAAId - uniquePatientId : " + uniquePatientId);
		String patientId = "";
		String AAID = "";
		//uniquePatientId format: 'NAR20700042^^^&2.16.840.1.113883.0.207&ISO'
		if(uniquePatientId != null && !uniquePatientId.isEmpty()){
			if(uniquePatientId.contains(LabConstants.SINGLE_QUOTE)){
				uniquePatientId = uniquePatientId.split(LabConstants.SPLITTER_QUOTE)[1];
			}
			patientId = uniquePatientId.split(LabConstants.SPLITTER_TRIPPLE_CARET)[0];
			if(uniquePatientId.split(LabConstants.SPLITTER_AMPERSAND).length > 1){
				AAID = uniquePatientId.split(LabConstants.SPLITTER_AMPERSAND)[1];
			}
			log.info("PatientId: " + patientId);
			log.info("LocalHCID : "+ AAID);
			if(qDNhinResponse.getResponderPatientID() == null || "".equals(qDNhinResponse.getResponderPatientID())){
				qDNhinResponse.setResponderPatientID(patientId);
			}
			if(qDNhinResponse.getResponderAAID() == null || "".equals(qDNhinResponse.getResponderAAID())){
				qDNhinResponse.setResponderAAID(AAID);
			}
		}
	}
}