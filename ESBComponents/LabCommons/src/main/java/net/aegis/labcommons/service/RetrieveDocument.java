/**
 * 
 */
package net.aegis.labcommons.service;

import java.util.Map;

import net.aegis.labcommons.jaxb.rd.RDEntityResponseType;

/**
 * @author Venkat.Keesara
 *
 */
public interface RetrieveDocument {

	/**
	 * Generic doc retrieve method for Aegis internal execution only(both initiator and responder scenarios)
	 * @param endpointURL
	 * @param localHCID
	 * @param remoteHCIDs
	 * @param testcaseConfig
	 * @return
	 */
	public abstract RDEntityResponseType retrieveDocuments(String endpointURL,
			String localHCID, Map<String, String> remoteHCIDs, String testcaseConfig);

	/**
	 * doc retrieve method for initiator scenarios (Candidate as Initiator)
	 * @param endpointURL
	 * @param localHCID
	 * @param remoteHCIDs
	 * @param testcaseConfig
	 * @param initiatorPatientId
	 * @return
	 */
	public abstract RDEntityResponseType retrieveDocuments(String endpointURL,
			String localHCID, Map<String, String> remoteHCIDs, String testcaseConfig, String initiatorPatientId);

	/**
	 * Doc retieve method for responder scenarios (RI as Initiator)
	 * @param endpointURL
	 * @param localHCID
	 * @param remoteHCIDs
	 * @param testcaseConfig
	 * @param docAndRepIds
	 * @param initiatorPatientId
	 * @return
	 */
	public abstract RDEntityResponseType retrieveDocuments(String endpointURL,
			String localHCID, Map<String, String> remoteHCIDs, String testcaseConfig, Map<String, String> docAndRepIds,
			String initiatorPatientId);

}