package net.aegis.gateway.client.internal;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.client.connect248.pd.PatientPD;
import net.aegis.gateway.client.connect248.util.FormatUtil;
import net.aegis.gateway.client.connect248.util.PDUtil;
import net.aegis.labcommons.jaxb.pd.CommunityResponseType;
import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;
import net.aegis.labcommons.jaxb.pd.ReceiverType;
import net.aegis.labcommons.service.PatientDiscovery;

public class PD248TestData {

	public void testPD() {
		PDEntityResponseType result = new PDEntityResponseType();
		PrintStream out = System.out;
		out.println("<h2>Patient Discovery Query Results for 248:</h2>");
		PatientDiscovery patientDiscoveryClient = ServiceFactory.getInstance().getPDServiceFor248();
//		PatientDiscoveryImpl patientDiscoveryClient = new PatientDiscoveryImpl();
		System.out.println("patientDiscoveryClient===" + patientDiscoveryClient.hashCode());
		String requestMessage= "";
		try {
			InputStream is = this.getClass().getResourceAsStream(
					"PatientDataPD.xml");
			requestMessage = FormatUtil.getContentsFromInputStream(is);
		} catch (Exception ex) {
			out.print("Exception from file read :::: " + ex);
		}
		out.println("Before calling correlate patient ");
		result = correlatePDForInitiator(patientDiscoveryClient, requestMessage);
		// ******************************************************************************************************************
		out.println("After calling correlate patient ");
		if (result == null) {
			out.println("Query Result = Failed");
			out.println("Query Result = Failed");
		} else {
			out.println("Query Result  = Success");
		}
		out.println("<br/>");
		out.println("<br/>");

		PatientPD patient = new PatientPD();
		patient = PDUtil.unmarshallPDConfig(requestMessage);
		String patientGivenName = patient.getPatientGivenName();
		String patientFamilyName = patient.getPatientLastName();

		// Need to refactor this later
		if ((patientFamilyName == null) || (patientGivenName == null)) {
			out.println("Patient Last Name or First Name is Null");
		} else {
			out.println("Patient Last Name: " + patientFamilyName);
			out.println("<br/>");
			out.println("Patient Given Name: " + patientGivenName);
			out.println("<br/>");
		}

		printPDResponse(result);
		

	}
	
	private void printPDResponse(PDEntityResponseType result){
	       
			PrintStream out = System.out;
	        String correlatedPatientId = null;
	        String assigningAuthId = null;
	            if (result != null) {
	                if (result.getCommunityResponse() != null && result.getCommunityResponse().size() > 0) {
	                    boolean correlated = false;
	                    for (int i = 0; i < result.getCommunityResponse().size(); i++) {
	                        correlatedPatientId = null;
	                        assigningAuthId = null;
	                        correlated = true;
	                        // Test response for any null values
	                        if (result.getCommunityResponse().get(i) != null ) {
	                        	CommunityResponseType communityResponse = result.getCommunityResponse().get(i);
	                    		ReceiverType receiver =  communityResponse.getReceiver();
	                    		if(receiver!=null && receiver.getPatient()!=null){
	                    			correlatedPatientId = receiver.getPatient().getPatientId();
	                    			assigningAuthId = receiver.getPatient().getAssigningAuthority();
		                            if (correlatedPatientId != null && !correlatedPatientId.trim().equals("")) {
		                            	out.println("Correlated Patient Id: " + correlatedPatientId);
		                                
		                            }
		                            if (assigningAuthId != null && !assigningAuthId.trim().equals("")) {
		                            	out.println("Correlated Patient Assigning Authority Id: " + assigningAuthId);
		                                
		                            }
	                    		}
	                        	
	                        }else{
	                        	out.println("Patient correlation not found.");
	                        }
	                        // status = LabConstants.STATUS_COMPLETED;
	                    }
	                }else{
	                	out.println("Patient response not populated!");
	                }
	            } else {
	                out.println("The Test case failed due to error in cross gateway communication.");
	            }
	}
	    
	
	
	private PDEntityResponseType  correlatePDForInitiator(PatientDiscovery patientDiscoveryClient,String requestMessage){
		PDEntityResponseType result = null;
		String localHCID = "2.16.840.1.113883.0.210";
		String endpointURL =
		 "http://localhost:8080/CONNECTAdapter/EntityPatientDiscovery";
		List<String> remoteHCIDList = new ArrayList<String>();
		remoteHCIDList.add("2.16.840.1.113883.0.101");
		String initiatorPatientId = "VEN21000037";
		result = patientDiscoveryClient.correlatePatient(endpointURL,
				localHCID, remoteHCIDList, requestMessage,initiatorPatientId);
		return result; 
	}
	private PDEntityResponseType  correlatePDForResponder(PatientDiscovery patientDiscoveryClient,String requestMessage){
		PDEntityResponseType result = null;
		String localHCID = "2.16.840.1.113883.0.101";
		 String endpointURL =
		 "http://d33RKGH1.aegis.net:8080/CONNECTAdapter/EntityPatientDiscovery";
		List<String> remoteHCIDList = new ArrayList<String>();
		remoteHCIDList.add("2.16.840.1.113883.0.210");
		result = patientDiscoveryClient.correlatePatient(endpointURL,
				localHCID, remoteHCIDList, requestMessage);
		return result; 
	}
	
	public static void main(String[] args) {
		PD248TestData activator = new PD248TestData();

		activator.testPD();
	}

	

}
