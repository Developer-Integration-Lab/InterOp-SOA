package net.aegis.gateway.client.internal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.client.connect248.util.FormatUtil;
import net.aegis.labcommons.jaxb.qd.DocumentInfoType;
import net.aegis.labcommons.jaxb.qd.ExtrinObjectType;
import net.aegis.labcommons.jaxb.qd.QDEntityResponseType;
import net.aegis.labcommons.service.QueryDocument;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;

public class QD248TestData {

	public void testQD() {

		PrintStream out = System.out;
		out.println("<h2>Query Document Results for 248::</h2>");
		QDEntityResponseType result = new QDEntityResponseType();

		QueryDocument queryDocumentClient = ServiceFactory.getInstance()
				.getQDServiceFor248();
		// PatientDiscoveryImpl patientDiscoveryClient = new
		// PatientDiscoveryImpl();
		System.out.println("queryDocumentClient==="
				+ queryDocumentClient.hashCode());
		String requestMessage = "";
		try {
			InputStream is = this.getClass().getResourceAsStream(
					"PatientDataQD.xml");
			requestMessage = FormatUtil.getContentsFromInputStream(is);
		} catch (Exception ex) {
			out.print("Exception from file read :::: " + ex);
		}
		result = queryForDocumentsForInitiator(queryDocumentClient, requestMessage);
		// *****************************************************************************************************************

		out.println("<br/>");
		// *********************************************************************************************
		List<DocumentInfoType> documents =  new ArrayList<DocumentInfoType>();
		if(result.getAdhocQueryResp()!=null && result.getAdhocQueryResp().getRegObjectList()!=null){
			List<ExtrinObjectType> extrinsicObjectsList = result.getAdhocQueryResp().getRegObjectList().getExtrinObject();
		if (extrinsicObjectsList != null && extrinsicObjectsList.size() > 0) {
			
			
			for(ExtrinObjectType extrinsicObjectType :extrinsicObjectsList){
				if(extrinsicObjectType.getDocumentInfo()!=null ){
					documents.add(extrinsicObjectType.getDocumentInfo());
				}
			}
			
			for (DocumentInfoType document : documents) {
				out.println("<br>Document Id: " + document.getDocumentUniqueId());
			}
		} else {
			out.println( " returned zero(0) documents!");
			
		}
		}
		// *********************************************************************************************

	}
	
	private QDEntityResponseType queryForDocumentsForInitiator(QueryDocument queryDocumentClient,String requestMessage){
		String localHCID = "2.16.840.1.113883.0.210";
		String endpointURL = "http://localhost:8080/CONNECTAdapter/EntityDocQuery";
		List<String> remoteHCIDList = new ArrayList<String>();
		remoteHCIDList.add("2.16.840.1.113883.0.101");
		// *****************************************************************************************************************
		String initiatorPatientId = "VEN21000037";
		QDEntityResponseType result = queryDocumentClient.queryForDocuments(endpointURL, localHCID,
				remoteHCIDList, requestMessage,initiatorPatientId);
		return result;
	}
	private QDEntityResponseType queryForDocumentsForResponder(QueryDocument queryDocumentClient,String requestMessage){
		String localHCID = "2.16.840.1.113883.0.101";
		String endpointURL = "http://d33RKGH1.aegis.net:8080/CONNECTAdapter/EntityDocQuery";
		List<String> remoteHCIDList = new ArrayList<String>();
		remoteHCIDList.add("2.16.840.1.113883.0.210");
		// *****************************************************************************************************************
		QDEntityResponseType result = queryDocumentClient.queryForDocuments(endpointURL, localHCID,
				remoteHCIDList, requestMessage);
		return result;
	}
}
