package net.aegis.gateway.client.connect32.util;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.aegis.gateway.client.connect32.pd.PatientPD;
import net.aegis.gateway.client.exception.WSClient32RuntimeException;
import net.aegis.labcommons.jaxb.pd.CommunityResponseType;
import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;
import net.aegis.labcommons.jaxb.pd.PatientType;
import net.aegis.labcommons.jaxb.pd.ReceiverType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.MFMIMT700711UV01QueryAck;
import org.hl7.v3.PRPAIN201306UV02MFMIMT700711UV01ControlActProcess;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02ResponseType;

public class PDUtil {

	private static final Log log = LogFactory.getLog(PDUtil.class);

	/**
	 * 
	 * @param fileContent
	 * @return
	 */

	public static PatientPD unmarshallPDConfig(String fileContent) {
		PatientPD patient = new PatientPD();
		try {
			Class clazz = net.aegis.gateway.client.connect32.pd.ObjectFactory.class;
			ClassLoader cl = clazz.getClassLoader();
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getPackage().getName(), cl);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader sr = new StringReader(fileContent);

			Object o = unmarshaller.unmarshal(sr);
			patient = (PatientPD) o;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw new WSClient32RuntimeException(ex);
		}
		return patient;
	}

	public static final String ENTITY_PATIENT_DISCOVERY_PORT_TYPE_PROXY = "entityPatientDiscoveryPortType32Proxy";
	
	/**
	 *  <queryResponseCode code=' '/>
		The queryResponseCode element indicates at a high level the results of performing the query. 
		It may have the value 'OK' to indicate that the query was performed and has results. 
		It may have the value 'NF' to indicate that the query was performed, but no results were located. 
		Finally, it may have the value 'QE' to indicate that an error was detected in the incoming query message.
	 * @param result
	 * @return
	 */
	public static PDEntityResponseType getPDEntityResponseTypeFromRespGatewayPRPAIN201306UV02(RespondingGatewayPRPAIN201306UV02ResponseType result){
		PDEntityResponseType PDEntityResponseType = new PDEntityResponseType();
		
		CommunityResponseType communityResponseType = null;
		ReceiverType receiverType = null;
		PatientType patientType = null;
		String correlatedPatientId = null;
		String assigningAuthId = null;
		String statusCode = null;
		if (result != null) {
			if (result.getCommunityResponse() != null && result.getCommunityResponse().size() > 0) {
				for (int i = 0; i < result.getCommunityResponse().size(); i++) {
					communityResponseType = new CommunityResponseType();

					correlatedPatientId = null;
					assigningAuthId = null;
					// Test response for any null values
					
					if (result.getCommunityResponse().get(i) != null
							&& result.getCommunityResponse().get(i).getPRPAIN201306UV02() != null
							&& result.getCommunityResponse().get(i).getPRPAIN201306UV02().getControlActProcess() != null)
							{
							PRPAIN201306UV02MFMIMT700711UV01ControlActProcess controlActProcess = result.getCommunityResponse().get(i).getPRPAIN201306UV02().getControlActProcess();
							// get subject and its sub elements
							if(controlActProcess
									.getSubject() != null
							&& controlActProcess
									.getSubject().size() > 0
							&& controlActProcess
									.getSubject().get(0).getRegistrationEvent() != null
							&& controlActProcess
									.getSubject().get(0).getRegistrationEvent().getSubject1() != null
							&& controlActProcess
									.getSubject().get(0).getRegistrationEvent().getSubject1().getPatient() != null
							&& controlActProcess
									.getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getId() != null
							&& controlActProcess.getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getId().size() > 0) {
									correlatedPatientId = controlActProcess.getSubject().get(0).getRegistrationEvent().getSubject1()
											.getPatient().getId().get(0).getExtension();
									assigningAuthId = controlActProcess.getSubject().get(0).getRegistrationEvent().getSubject1()
											.getPatient().getId().get(0).getRoot();
									receiverType = new ReceiverType();
									patientType = new PatientType();
									if (correlatedPatientId != null && !correlatedPatientId.trim().equals("")) {
										log.info("Correlated Patient Id: " + correlatedPatientId);
										patientType.setPatientId(correlatedPatientId);
									}
									if (assigningAuthId != null && !assigningAuthId.trim().equals("")) {
										log.info("Correlated Patient Assigning Authority Id: " + assigningAuthId);
										patientType.setAssigningAuthority(assigningAuthId);
									}
									receiverType.setPatient(patientType);
								}
							
								
								MFMIMT700711UV01QueryAck queryAck = controlActProcess.getQueryAck();
								if(queryAck != null && queryAck.getQueryResponseCode()!=null && queryAck.getQueryResponseCode().getCode()!=null){
									statusCode = queryAck.getQueryResponseCode().getCode();
								}
							
							
							}
					communityResponseType.setReceiver(receiverType);
					communityResponseType.setStatus(statusCode);
					PDEntityResponseType.getCommunityResponse().add(communityResponseType);
				}
			}
		}
		return PDEntityResponseType ; 
	}

}
