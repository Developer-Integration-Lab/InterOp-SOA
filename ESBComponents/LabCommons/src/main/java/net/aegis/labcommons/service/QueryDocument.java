/**
 * 
 */
package net.aegis.labcommons.service;

import java.util.List;

import net.aegis.labcommons.jaxb.qd.QDEntityResponseType;

/**
 * @author Venkat.Keesara
 *
 */
public interface QueryDocument {


	/**
	 * Query for document method - responder scenarios (RI as Initiator)
	 * @param endpointURL
	 * @param localHCID
	 * @param remoteHCIDList1
	 * @param testcaseConfig
	 * @return
	 */
	public abstract QDEntityResponseType queryForDocuments(String endpointURL,
			String localHCID, List remoteHCIDList1, String testcaseConfig);

	/**
	 * Query document method for initiator scenarios (Candidate as Initiator)
	 * @param endpointURL
	 * @param localHCID
	 * @param remoteHCIDList1
	 * @param testcaseConfig
	 * @param initiatorPatientId
	 * @return
	 */
	public abstract QDEntityResponseType queryForDocuments(String endpointURL,
			String localHCID, List remoteHCIDList1, String testcaseConfig, String initiatorPatientId);

}