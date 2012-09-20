package net.aegis.mv.parser.qd;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.jaxb.qd.QDNhinRequest;
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
public class QDRequestParser implements INhinMsgParser 
{

	private static final Log log = LogFactory.getLog(QDRequestParser.class);

	private static QDRequestParser instance = new QDRequestParser();
	private QDRequestParser() {}

	public static QDRequestParser getInstance() {
		return instance;
	}

	public QDNhinRequest parse(Transaction transaction) {
		byte[] message = transaction.getMessage();
		QDNhinRequest qDNhinRequest = new QDNhinRequest();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(message));
			doc.getDocumentElement().normalize();

			NodeList headersList = doc.getElementsByTagNameNS(MVConstants.URI_SOAP12_ENV ,  MVConstants.HEADER);
			if(headersList!=null && headersList.item(0)!=null){
				processHeader(headersList.item(0), qDNhinRequest);
			}
			NodeList bodyList = doc.getElementsByTagNameNS(MVConstants.URI_SOAP12_ENV ,  MVConstants.BODY);
			if(bodyList!=null && bodyList.item(0)!=null){
				processBody(bodyList.item(0), qDNhinRequest);

			}
		} catch (ParserConfigurationException e) {
			log.error(e.getStackTrace());
		} catch (SAXException e) {
			log.error(e.getStackTrace());
		} catch (IOException e) {
			log.error(e.getStackTrace());
		}

		log.info("\n\n************ QDNhinRequest Parameters Start ******************");
		log.info("ResponderHCID : "+ qDNhinRequest.getResponderHCID());
		log.info("ResponderAAID : "+ qDNhinRequest.getResponderAAID());
		log.info("MessageID : "+ qDNhinRequest.getMessageID());
		log.info("ResponderPatientID : "+ qDNhinRequest.getResponderPatientID());
		log.info("DocEntryStatus : "+ qDNhinRequest.getDocEntryStatusList());
		log.info("ReturnType : "+ qDNhinRequest.getReturnType());
		log.info("EntryCreationTimeFrom : "+ qDNhinRequest.getEntryCreationTimeFrom());
		log.info("EntryCreationTimeTo : "+ qDNhinRequest.getEntryCreationTimeTo());
		log.info("ServiceStartTimeFrom : "+ qDNhinRequest.getServiceStartTimeFrom());
		log.info("ServiceStartTimeTo : "+ qDNhinRequest.getServiceStartTimeTo());
		log.info("ServiceStopTimeFrom : "+ qDNhinRequest.getServiceStopTimeFrom());
		log.info("ServiceStopTimeTo : "+ qDNhinRequest.getServiceStopTimeTo());
		log.info("************** QDNhinRequest Parameters End ****************\n\n");

		return qDNhinRequest;	
	}

	private void processHeader(Node  header , QDNhinRequest qDNhinRequest){

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
					&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.MESSAGE_ID)){

				String textValue = getElementTextValue((Element)eachNode);
				log.info("textValue==" + textValue);
				qDNhinRequest.setMessageID(textValue);
				break ; 
			}

		}
	}

	private void processBody(Node  body , QDNhinRequest qDNhinRequest){

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
					&& eachNode.getLocalName().equalsIgnoreCase(MVConstants.QD_REQUEST_BODY_ROOT_TAG)){
					//&& eachNode.getLocalName().equalsIgnoreCase(MsgTypeEnum.QD_REQUEST.getTextValue())){

				Element mainElement = (Element)eachNode;
				processAdhocQueryRequest((Element)mainElement, qDNhinRequest);
			}
		}
		log.info("pDNhinResponse====" + qDNhinRequest);
	}

	private void processAdhocQueryRequest(Element  element, QDNhinRequest qDNhinRequest){

		log.info("Processing " + element.getLocalName());
		if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
			NodeList nl = element.getChildNodes();
			if(nl!=null && nl.getLength() > 0){
				for(int i=0, cnt=nl.getLength(); i<cnt; i++){
					Node eachNode = nl.item(i);
					if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
							&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.RESPONSEOPTION)){

						Element elementNode = (Element)eachNode;
						qDNhinRequest.setReturnType(elementNode.getAttribute(MVConstants.RETURNTYPE));
					}else if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
							&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.ADHOCQUERY)){

						Element elementNode = (Element)eachNode;
						processAdhocQuery((Element)elementNode, qDNhinRequest);
					}
				}
			}
		}
	}

	private void processAdhocQuery(Element  element , QDNhinRequest qDNhinRequest){
		log.info("Processing " + element.getLocalName());
		String hcid = element.getAttribute(MVConstants.HOME);
		qDNhinRequest.setResponderHCID(MessageValidatorUtil.getInstance().trimPrefixForHomeCommunityId(hcid));
		if(element!=null && element.getNodeType()== Node.ELEMENT_NODE){
			NodeList nl = element.getChildNodes();
			if(nl!=null && nl.getLength() > 0){
				for(int i=0, cnt=nl.getLength(); i<cnt; i++){
					Node eachNode = nl.item(i);
					if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
							&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.SLOT)){

						Element elementNode = (Element)eachNode;
						processSlot((Element)elementNode, qDNhinRequest);
					}
				}
			}
		}
	}

	private void processSlot(Element  element, QDNhinRequest qDNhinRequest){
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
						processValueList((Element)elementNode, qDNhinRequest, type);
					}
				}
			}
		}
	}

	private  void processValueList(Element  element , QDNhinRequest qDNhinRequest, String type){
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
						if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYPATIENTID)){
							setPatientIdAndAAId(qDNhinRequest, value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYSTATUS)){
							qDNhinRequest.getDocEntryStatusList().add(value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYCLASSCODE)){
							qDNhinRequest.getEntryClassCodeList().add(value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYCREATIONTIMEFROM)){
							qDNhinRequest.setEntryCreationTimeFrom(value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYCREATIONTIMETO)){
							qDNhinRequest.setEntryCreationTimeTo(value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYSERVICESTARTTIMEFROM)){
							qDNhinRequest.setServiceStartTimeFrom(value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYSERVICESTARTTIMETO)){
							qDNhinRequest.setServiceStartTimeTo(value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYSERVICESTOPTIMEFROM)){
							qDNhinRequest.setServiceStopTimeFrom(value);
						}else if(type.equalsIgnoreCase(MVConstants.XDSDOCUMENTENTRYSERVICESTOPTIMETO)){
							qDNhinRequest.setServiceStopTimeTo(value);
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

	public void setPatientIdAndAAId(QDNhinRequest qDNhinRequest, String uniquePatientId){
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
			qDNhinRequest.setResponderPatientID(patientId);
			qDNhinRequest.setResponderAAID(AAID);
		}
	}

	//	public void setDocumentEntryStatus(QDNhinRequest qDNhinRequest, String status){
	//		String entryStatus = "";
	//		if(status != null && !status.isEmpty()){
	//			entryStatus = status.split(LabConstants.SPLITTER_QUOTE)[1];
	//			log.info("EntryStatus : "+ entryStatus);
	//			qDNhinRequest.setDocEntryStatus(entryStatus);
	//		}
	//	}
}