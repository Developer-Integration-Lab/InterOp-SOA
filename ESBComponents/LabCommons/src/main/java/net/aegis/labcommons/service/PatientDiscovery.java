package net.aegis.labcommons.service;

import java.util.List;

import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;


public interface PatientDiscovery {

	/**
	 * Patient Discovery - method for correlating patients for Responder scenarios 
	 * @param endpointURL
	 * @param localHCID
	 * @param remoteHCIDList1
	 * @param testcaseConfig
	 * @return
	 */
	public abstract PDEntityResponseType correlatePatient(String endpointURL,
			String localHCID, List remoteHCIDList1, String testcaseConfig);

	/**
	 * Patient Discovery - method for correlating patients for initiator scenarios (Candidate as Initiator)
	 * @param endpointURL
	 * @param localHCID
	 * @param remoteHCIDList1
	 * @param testcaseConfig
	 * @param initiatorPatientId
	 * @return
	 */
	public abstract PDEntityResponseType correlatePatient(String endpointURL,
			String localHCID, List remoteHCIDList1, String testcaseConfig, String initiatorPatientId);

	

}