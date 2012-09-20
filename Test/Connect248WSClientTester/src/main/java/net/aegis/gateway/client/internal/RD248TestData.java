package net.aegis.gateway.client.internal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import net.aegis.gateway.client.connect248.util.FormatUtil;
import net.aegis.labcommons.jaxb.rd.RDEntityResponseType;
import net.aegis.labcommons.service.RetrieveDocument;

public class RD248TestData {

	public void testRD(){
		PrintStream out = System.out;
	        RDEntityResponseType result = new RDEntityResponseType();
	            out.println("<h2>Retrieve Document Results for 248 :</h2>");
	            //gov.hhs.fha.nhinc.entitydocretrieve.EntityDocRetrievePortType port = service.getEntityDocRetrievePortSoap11();
				RetrieveDocument retrieveDocumentClient = ServiceFactory.getInstance()
						.getRDServiceFor248();
				System.out.println("retrieveDocumentClient==="
						+ retrieveDocumentClient.hashCode());
				result= retrieveDocumentsForInitiator(retrieveDocumentClient);
	            //Responder only scenarios *************************************************************************************
				/*Map<String, String> docAndRepIds = new HashMap<String,String>();
	            docAndRepIds.put("1.VEN21000037.11111", "1");
	            docAndRepIds.put("1.VEN21000037.22222", "1");
	            docAndRepIds.put("1.RI1.101.00037.11111", "1");
	            docAndRepIds.put("1.RI1.112.00037.22222", "1");*/
	            //result = retrieveDocumentClient.retrieveDocuments(endpointURL, localHCID, remoteHCIDCodes, f, docAndRepIds);
	            //*************************************************************************************************************

	            if (result == null) {
	                out.println("Query Result =  Filed");
	            } else {
	                out.println("Query Result  = Success");

	                out.println("</br>");
	                int docCount = result.getRetrieveDocSetResponse().getDocResponse().size();
	                out.println("DocCount: " + docCount);
	                out.println("DocCount: " + docCount);
	                for (int i = 0; i < docCount; i++) {
	                    out.println("</br>");
	                    out.println("</br>");
	                    out.println("<b>");
	                    out.println("Document " + (i + 1) + " - " + result.getRetrieveDocSetResponse().getDocResponse().get(i).getDocumentUniqueId() + ":");
	                    out.println("</br>");
	                    out.println("Document byte Size: " + result.getRetrieveDocSetResponse().getDocResponse().get(i).getDocument().length);
	                    out.println("</b>");
	                    out.println("</br>");
	                    String docTxt = new String(result.getRetrieveDocSetResponse().getDocResponse().get(i).getDocument());
	                    out.println(docTxt);
	                }
	            }
	        
	}
	
	private RDEntityResponseType retrieveDocumentsForResponder(RetrieveDocument retrieveDocumentClient){
		RDEntityResponseType result= null;
		String requestMessage = "";
		try {
			InputStream is = this.getClass().getResourceAsStream(
					"PatientDataRDForResponder.xml");
			requestMessage = FormatUtil.getContentsFromInputStream(is);
		} catch (Exception ex) {
			System.out.print("Exception from file read :::: " + ex);
		}
		String localHCID = "2.16.840.1.113883.0.101";
		String endpointURL = "http://d33RKGH1.aegis.net:8080/CONNECTAdapter/EntityDocRetrieve";

        Map<String, String> remoteHCIDCodes = new HashMap<String, String>();
        remoteHCIDCodes.put("1", "2.16.840.1.113883.0.210");

        result = retrieveDocumentClient.retrieveDocuments(endpointURL, localHCID, remoteHCIDCodes, requestMessage);
        return result;
	}
	private RDEntityResponseType retrieveDocumentsForInitiator(RetrieveDocument retrieveDocumentClient){
		RDEntityResponseType result= null;
		String requestMessage = "";
		try {
			InputStream is = this.getClass().getResourceAsStream(
					"PatientDataRDForInitiator.xml");
			requestMessage = FormatUtil.getContentsFromInputStream(is);
		} catch (Exception ex) {
			System.out.print("Exception from file read :::: " + ex);
		}
		String localHCID = "2.16.840.1.113883.0.210";
		String endpointURL = "http://localhost:8080/CONNECTAdapter/EntityDocRetrieve";
        Map<String, String> remoteHCIDCodes = new HashMap<String, String>();
        remoteHCIDCodes.put("1", "2.16.840.1.113883.0.101");

        String initiatorPatientId = "VEN21000037";
        result = retrieveDocumentClient.retrieveDocuments(endpointURL, localHCID, remoteHCIDCodes, requestMessage,initiatorPatientId);
        return result;
	}

}
