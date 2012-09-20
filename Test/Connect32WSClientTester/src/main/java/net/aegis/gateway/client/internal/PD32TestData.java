package net.aegis.gateway.client.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.client.connect32.pd.PatientDiscoveryImpl;
import net.aegis.gateway.client.connect32.pd.PatientPD;
import net.aegis.gateway.client.connect32.util.PDUtil;
import net.aegis.labcommons.jaxb.pd.CommunityResponseType;
import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;
import net.aegis.labcommons.jaxb.pd.ReceiverType;

public class PD32TestData {

	public void testPD() {
		PDEntityResponseType result = new PDEntityResponseType();
		PrintStream out = System.out;
		out.println("<h2>Patient Discovery Query Results for 32:</h2>");
		PatientDiscoveryImpl patientDiscoveryClient = new PatientDiscoveryImpl();
		System.out.println("patientDiscoveryClient===" + patientDiscoveryClient.hashCode());
		String requestMessage= "";
		try {
			InputStream is = this.getClass().getResourceAsStream(
					"PatientDataPD.xml");
			requestMessage = getContentsFromInputStream(is);
		} catch (Exception ex) {
			out.print("Exception from file read :::: " + ex);
		}
		// Soademo103 hcid 
		String localHCID = "2.16.840.1.113883.3.1259.0.103";
		// String endpointURL =
		// "http://d3btptl1.aegis.net:8080/NhinConnect/EntityPatientDiscovery";
		//String endpointURL = "http://localhost:8080/CONNECTAdapter/EntityPatientDiscovery";
		 String endpointURL =
		 "http://soademo103.dil.aegis.net:8080/CONNECTGateway/EntityPatientDiscovery";
		 // dil soa demo participant 
		 List<String> remoteHCIDList = new ArrayList<String>();
		remoteHCIDList.add("2.16.840.1.113883.3.1259.0.104");
		// remoteHCIDList.add("2.16.840.1.113883.0.102");

		// result = patientDiscoveryClient.correlatePatient(endpointURL,
		// localHCID, remoteHCIDList, f);
		// ******************************************************************************************************************
		String initiatorPatientId = "VEN21000037";
		out.println("Before calling correlate patient ");
		result = patientDiscoveryClient.correlatePatient(endpointURL,
				localHCID, remoteHCIDList, requestMessage);
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

	public static String getContentsFromInputStream(InputStream is) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader input = new BufferedReader(reader);
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}

}
