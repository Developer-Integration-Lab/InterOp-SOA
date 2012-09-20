package net.aegis.gateway.client.connect248.pd;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;
import gov.hhs.fha.nhinc.entitypatientdiscovery.EntityPatientDiscoveryPortType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingProvider;

import net.aegis.gateway.client.connect248.util.ApplicationContextProvider;
import net.aegis.gateway.client.connect248.util.PDUtil;
import net.aegis.gateway.client.exception.WSClient248RuntimeException;
import net.aegis.labcommons.jaxb.pd.PDEntityResponseType;
import net.aegis.labcommons.service.PatientDiscovery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.ADExplicit;
import org.hl7.v3.ActClassControlAct;
import org.hl7.v3.AdxpExplicitCity;
import org.hl7.v3.AdxpExplicitPostalCode;
import org.hl7.v3.AdxpExplicitState;
import org.hl7.v3.AdxpExplicitStreetAddressLine;
import org.hl7.v3.BinaryDataEncoding;
import org.hl7.v3.CD;
import org.hl7.v3.CE;
import org.hl7.v3.COCTMT090300UV01AssignedDevice;
import org.hl7.v3.CS;
import org.hl7.v3.CommunicationFunctionType;
import org.hl7.v3.ENExplicit;
import org.hl7.v3.EnExplicitFamily;
import org.hl7.v3.EnExplicitGiven;
import org.hl7.v3.EnExplicitPrefix;
import org.hl7.v3.EnExplicitSuffix;
import org.hl7.v3.EntityClassDevice;
import org.hl7.v3.II;
import org.hl7.v3.IVLTSExplicit;
import org.hl7.v3.MCCIMT000100UV01Agent;
import org.hl7.v3.MCCIMT000100UV01Device;
import org.hl7.v3.MCCIMT000100UV01Organization;
import org.hl7.v3.MCCIMT000100UV01Receiver;
import org.hl7.v3.MCCIMT000100UV01Sender;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201305UV02QUQIMT021001UV01ControlActProcess;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectAdministrativeGender;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectBirthTime;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectId;
import org.hl7.v3.PRPAMT201306UV02LivingSubjectName;
import org.hl7.v3.PRPAMT201306UV02ParameterList;
import org.hl7.v3.PRPAMT201306UV02PatientAddress;
import org.hl7.v3.PRPAMT201306UV02PatientTelecom;
import org.hl7.v3.PRPAMT201306UV02QueryByParameter;
import org.hl7.v3.QUQIMT021001UV01AuthorOrPerformer;
import org.hl7.v3.RespondingGatewayPRPAIN201306UV02ResponseType;
import org.hl7.v3.ST;
import org.hl7.v3.TELExplicit;
import org.hl7.v3.TSExplicit;
import org.hl7.v3.XActMoodIntentEvent;
import org.hl7.v3.XParticipationAuthorPerformer;

/**
 * 
 * @author Venkat.Keesara
 * 
 */
public class PatientDiscoveryImpl implements PatientDiscovery {

	private static final Log log = LogFactory.getLog(PatientDiscoveryImpl.class);
	private static org.hl7.v3.ObjectFactory hl7ObjFactory = new org.hl7.v3.ObjectFactory();

	private static final String HL7_DATE_FORMAT = "yyyyMMddHHmmss";

	private static final String ROLE_CODE = "46255001";
	private static final String ROLE_CODE_SYSTEM = "2.16.840.1.113883.6.96";
	private static final String ROLE_CODE_SYSTEM_NAME = "SNOMED_CT";
	private static final String ROLE_DISPLAY_NAME = "Pharmacist";

	// private static final String PURPOSE_OF_DISCLOSURE_CODE = "OPERATIONS";
	private static final String PURPOSE_OF_DISCLOSURE_CODE = "TREATMENT";
	private static final String PURPOSE_OF_DISCLOSURE_CODE_SYSTEM = "2.16.840.1.113883.3.18.7.1";
	private static final String PURPOSE_OF_DISCLOSURE_CODE_SYSTEM_NAME = "nhin-purpose";
	// private static final String PURPOSE_OF_DISCLOSURE_DISPLAY_NAME =
	// "Healthcare Operations";
	private static final String PURPOSE_OF_DISCLOSURE_DISPLAY_NAME = "Treatment";

	private static final String CONTROL_ACCOUNT_PROCESS_CODE = "PRPA_TE201305UV02";
	private static final String CONTROL_ACCOUNT_PROCESS_CODE_SYSTEM = "2.16.840.1.113883.1.6";
	private static final String LIVING_SUBJECT_SSN_CODE = "2.16.840.1.113883.4.1";

	public PatientDiscoveryImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.aegis.gateway.clients.connect248.pd.PatientDiscovery#correlatePatient
	 * (java.lang.String, java.lang.String, java.util.List, java.lang.String)
	 */
	public PDEntityResponseType correlatePatient(String endpointURL, String localHCID,
			List remoteHCIDList1, String testcaseConfig) {
		return correlatePatient(endpointURL, localHCID, remoteHCIDList1, testcaseConfig, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.aegis.gateway.clients.connect248.pd.PatientDiscovery#correlatePatient
	 * (java.lang.String, java.lang.String, java.util.List, java.lang.String,
	 * java.lang.String)
	 */
	public PDEntityResponseType correlatePatient(String endpointURL, String localHCID,
			List remoteHCIDList1, String testcaseConfig, String initiatorPatientId) {
		RespondingGatewayPRPAIN201306UV02ResponseType result = new RespondingGatewayPRPAIN201306UV02ResponseType();
		PDEntityResponseType entityResponseType = null;
		try {
			org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType respondingGatewayPRPAIN201305UV02Request = new org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType();

			PatientPD patient = new PatientPD();
			patient = getPatientInfo(testcaseConfig);
			String patientId = patient.getPatientId(); // responder patient is
														// passed from config
			log.info("Responder patientId From File: " + patientId);
			// this is for initiator scenarios
			if (isNotEmpty(initiatorPatientId)) {
				patientId = initiatorPatientId;
				log.info("InitiatorPatientId: " + patientId);
			}
			
			String patientGender = patient.getPatientGender();
			String patientDateOfBirth = patient.getPatientDateOfBirth();
			String patientSSN = patient.getPatientSsn();
			String userFamilyName = patient.getUserFamilyName();
			String userGivenName = patient.getUserGivenName();
			String userName = patient.getUserName();
			String userInitials = patient.getUserInitials();

			log.info("patientGender: " + patientGender);
			log.info("patientDateOfBirth :" + patientDateOfBirth);
			log.info("userFamilyName: " + userFamilyName);
			log.info("userGivenName: " + userGivenName);

			// newly added by Venkat Keesara
			PatientNamesType patientNames = patient.getPatientNames();
			PatientAddressesType addresses = patient.getPatientAddresses();
			PatientTelsType patientTels = patient.getPatientTels();
			String demographicQueryOnly = patient.getDemographicQueryOnly();

			String localAA = localHCID;
			String uniquePatientId = patientId + "^^^&" + localAA + "&ISO";

			// Check endpoint for valid 'http' prefix. If not, then use as
			// ipAddress
			String ipAddress = endpointURL;
			if (ipAddress != null && !ipAddress.startsWith("http")) {
				// Default is NHIN Connect 2.3.1 End Point (unsecured)
				endpointURL = "http://" + ipAddress + ":8080/NhinConnect/EntityPatientDiscovery";
			}

			log.info("PD - uniquePatientId: " + uniquePatientId);
			log.info("PD - endpointURL: " + endpointURL);
			log.info("PD - localHCID: " + localHCID);
			log.info("PD - patientSSN: " + patientSSN);

			// Dynamic proxy
			// ************************************************************************************************
			EntityPatientDiscoveryPortType entityPatientDiscoveryPortType248 = getServiceEndPoint(endpointURL);
			// End - Dynamic proxy
			// ********************************************************************************************

			// Set
			// PRPAIN201305UV02*********************************************************************************************
			PRPAIN201305UV02 pRPAIN201305UV02 = new PRPAIN201305UV02();

			pRPAIN201305UV02.setITSVersion("XML_1.0");

			II ii = new II();
			ii.setRoot(localAA);
			ii.setExtension("-5a3e95b1:11d1fa33d45:-7f9b");
			pRPAIN201305UV02.setId(ii);

			TSExplicit tSExplicit = new TSExplicit();
			Date creationTime = new Date();
			// tSExplicit.setValue("20091116084800");
			tSExplicit.setValue(formatDate(creationTime, HL7_DATE_FORMAT));
			pRPAIN201305UV02.setCreationTime(tSExplicit);

			ii = new II();
			ii.setRoot("2.16.840.1.113883.1.6");
			ii.setExtension("PRPA_IN201305UV02");
			pRPAIN201305UV02.setInteractionId(ii);

			CS cS = new CS();
			cS.setCode("T");
			pRPAIN201305UV02.setProcessingCode(cS);

			pRPAIN201305UV02.setProcessingModeCode(cS);

			cS = new CS();
			cS.setCode("AL");
			pRPAIN201305UV02.setAcceptAckCode(cS);

			// Receiver
			MCCIMT000100UV01Receiver mCCIMT000100UV01Receiver = new MCCIMT000100UV01Receiver();
			mCCIMT000100UV01Receiver.setTypeCode(CommunicationFunctionType.RCV);
			MCCIMT000100UV01Device mCCIMT000100UV01Device = new MCCIMT000100UV01Device();
			mCCIMT000100UV01Device.setClassCode(EntityClassDevice.DEV);
			mCCIMT000100UV01Device.setDeterminerCode("INSTANCE");
			ii = new II();
			ii.setRoot(localHCID);
			mCCIMT000100UV01Device.getId().add(ii);
			mCCIMT000100UV01Receiver.setDevice(mCCIMT000100UV01Device);
			pRPAIN201305UV02.getReceiver().add(mCCIMT000100UV01Receiver);

			// Sender
			MCCIMT000100UV01Sender mCCIMT000100UV01Sender = new MCCIMT000100UV01Sender();
			mCCIMT000100UV01Sender.setTypeCode(CommunicationFunctionType.SND);
			mCCIMT000100UV01Device = new MCCIMT000100UV01Device();
			mCCIMT000100UV01Device.setClassCode(EntityClassDevice.DEV);
			mCCIMT000100UV01Device.setDeterminerCode("INSTANCE");

			// Story 145: Include the device id in the messages the lab
			// initiates
			II idII = new II();
			idII.setRoot(localHCID);
			mCCIMT000100UV01Device.getId().add(idII);
			//

			MCCIMT000100UV01Agent mCCIMT000100UV01Agent = new MCCIMT000100UV01Agent();
			JAXBElement<MCCIMT000100UV01Agent> jaxbMCCIMT000100UV01Agent = hl7ObjFactory
					.createMCCIMT000100UV01DeviceAsAgent(mCCIMT000100UV01Agent);

			mCCIMT000100UV01Agent.getClassCode().add("AGENT");

			MCCIMT000100UV01Organization mCCIMT000100UV01Organization = new MCCIMT000100UV01Organization();
			JAXBElement<MCCIMT000100UV01Organization> jaxbMCCIMT000100UV01Organization = hl7ObjFactory
					.createMCCIMT000100UV01AgentRepresentedOrganization(mCCIMT000100UV01Organization);
			mCCIMT000100UV01Organization.setClassCode("ORG");
			mCCIMT000100UV01Organization.setDeterminerCode("INSTANCE");
			ii = new II();
			ii.setRoot(localHCID);
			mCCIMT000100UV01Organization.getId().add(ii);
			jaxbMCCIMT000100UV01Organization.setValue(mCCIMT000100UV01Organization);
			mCCIMT000100UV01Agent.setRepresentedOrganization(jaxbMCCIMT000100UV01Organization);
			jaxbMCCIMT000100UV01Agent.setValue(mCCIMT000100UV01Agent);

			// CHK
			mCCIMT000100UV01Device.setAsAgent(jaxbMCCIMT000100UV01Agent);
			mCCIMT000100UV01Sender.setDevice(mCCIMT000100UV01Device);
			pRPAIN201305UV02.setSender(mCCIMT000100UV01Sender);

			// ControlActProcess
			PRPAIN201305UV02QUQIMT021001UV01ControlActProcess cActProcess = new PRPAIN201305UV02QUQIMT021001UV01ControlActProcess();
			cActProcess.setClassCode(ActClassControlAct.CACT);
			cActProcess.setMoodCode(XActMoodIntentEvent.EVN);
			CD cD = new CD();
			cD.setCode(CONTROL_ACCOUNT_PROCESS_CODE);
			cD.setCodeSystem(CONTROL_ACCOUNT_PROCESS_CODE_SYSTEM);
			cActProcess.setCode(cD);
			QUQIMT021001UV01AuthorOrPerformer authorOrPerformer = new QUQIMT021001UV01AuthorOrPerformer();
			authorOrPerformer.setTypeCode(XParticipationAuthorPerformer.AUT);
			COCTMT090300UV01AssignedDevice assignedDevice = new COCTMT090300UV01AssignedDevice();
			ii = new II();
			ii.setRoot(localAA);
			assignedDevice.getId().add(ii);
			javax.xml.namespace.QName xmlqname1 = new javax.xml.namespace.QName("urn:hl7-org:v3", "assignedDevice");
			JAXBElement<COCTMT090300UV01AssignedDevice> jaxbAssignedDevice = new JAXBElement<COCTMT090300UV01AssignedDevice>(
					xmlqname1, COCTMT090300UV01AssignedDevice.class, assignedDevice);
			jaxbAssignedDevice.setValue(assignedDevice);
			authorOrPerformer.setAssignedDevice(jaxbAssignedDevice);
			cActProcess.getAuthorOrPerformer().add(authorOrPerformer);

			// queryByParameter
			PRPAMT201306UV02QueryByParameter queryByParameter = new PRPAMT201306UV02QueryByParameter();
			ii = new II();
			ii.setRoot(localHCID);
			ii.setExtension("-abd3453dcd24wkkks545");
			queryByParameter.setQueryId(ii);
			cS = new CS();
			cS.setCode("new");
			queryByParameter.setStatusCode(cS);
			cS = new CS();
			cS.setCode("R");
			queryByParameter.setResponseModalityCode(cS);
			cS = new CS();
			cS.setCode("I");
			queryByParameter.setResponsePriorityCode(cS);

			PRPAMT201306UV02ParameterList parameterList = new PRPAMT201306UV02ParameterList();

			PRPAMT201306UV02LivingSubjectAdministrativeGender livingSubjectAdministrativeGender = new PRPAMT201306UV02LivingSubjectAdministrativeGender();
			CE cE = new CE();
			cE.setCode(patientGender);
			livingSubjectAdministrativeGender.getValue().add(cE);
			ST sT = new ST();
			sT.setRepresentation(BinaryDataEncoding.TXT);
			livingSubjectAdministrativeGender.setSemanticsText(sT);
			parameterList.getLivingSubjectAdministrativeGender().add(livingSubjectAdministrativeGender);

			PRPAMT201306UV02LivingSubjectBirthTime livingSubjectBirthTime = new PRPAMT201306UV02LivingSubjectBirthTime();
			IVLTSExplicit iVLTSExplicit = new IVLTSExplicit();
			iVLTSExplicit.setValue(patientDateOfBirth);
			livingSubjectBirthTime.getValue().add(iVLTSExplicit);
			sT = new ST();
			sT.setRepresentation(BinaryDataEncoding.TXT);
			livingSubjectBirthTime.setSemanticsText(sT);
			parameterList.getLivingSubjectBirthTime().add(livingSubjectBirthTime);

			// addPatientTelephones
			addPatientTelephones(parameterList, patientTels, patient);
			// addPatientNames
			addPatientNames(parameterList, patientNames, patient);
			PRPAMT201306UV02LivingSubjectId livingSubjectId = new PRPAMT201306UV02LivingSubjectId();
			// Patient Id
			if (isNotEmpty(patientId) && isNotEmpty(localAA)) {
				ii = new II();
				ii.setRoot(localAA);
				ii.setExtension(patientId);
				livingSubjectId.getValue().add(ii);
				livingSubjectId.setSemanticsText(sT);
				// demographicQueryOnly meaning that don,t pass patient id as
				// part of request
				if (isNotEmpty(demographicQueryOnly) && demographicQueryOnly.equalsIgnoreCase("Y")) {

				} else {
					parameterList.getLivingSubjectId().add(livingSubjectId);
				}
			}

			// SSN
			if (isNotEmpty(patientSSN)) {
				livingSubjectId = new PRPAMT201306UV02LivingSubjectId();
				ii = new II();
				ii.setRoot(LIVING_SUBJECT_SSN_CODE);
				ii.setExtension(patientSSN);
				livingSubjectId.getValue().add(ii);
				livingSubjectId.setSemanticsText(sT);
				parameterList.getLivingSubjectId().add(livingSubjectId);
			}

			// add patient addresess
			addPatientAddresses(parameterList, addresses, patient);

			queryByParameter.setParameterList(parameterList);
			javax.xml.namespace.QName xmlqname = new javax.xml.namespace.QName("urn:hl7-org:v3", "queryByParameter");
			JAXBElement<PRPAMT201306UV02QueryByParameter> jaxbQueryByParameter = new JAXBElement<PRPAMT201306UV02QueryByParameter>(
					xmlqname, PRPAMT201306UV02QueryByParameter.class, queryByParameter);
			jaxbQueryByParameter.setValue(queryByParameter);
			cActProcess.setQueryByParameter(jaxbQueryByParameter);
			pRPAIN201305UV02.setControlActProcess(cActProcess);
			respondingGatewayPRPAIN201305UV02Request.setPRPAIN201305UV02(pRPAIN201305UV02);
			// **************************************************************

			AssertionType assertionType = new AssertionType();
			assertionType.setDateOfBirth("19800516");
			// assertionType.setDateOfSignature("20080520");
			// assertionType.setExpirationDate("20100520");
			HomeCommunityType homeCommunityType1 = new HomeCommunityType();
			homeCommunityType1.setDescription(localHCID);
			homeCommunityType1.setHomeCommunityId(localHCID);
			homeCommunityType1.setName(localHCID);
			assertionType.setHomeCommunity(homeCommunityType1);

			assertionType.getUniquePatientId().add(uniquePatientId);

			// Purpose of use
			CeType ceType = new CeType();
			ceType.setCode(PURPOSE_OF_DISCLOSURE_CODE);
			ceType.setCodeSystem(PURPOSE_OF_DISCLOSURE_CODE_SYSTEM);
			ceType.setCodeSystemName(PURPOSE_OF_DISCLOSURE_CODE_SYSTEM_NAME);
			ceType.setDisplayName(PURPOSE_OF_DISCLOSURE_DISPLAY_NAME);
			assertionType.setPurposeOfDisclosureCoded(ceType);

			UserType userType = new UserType();
			PersonNameType personNameType = new PersonNameType();
			personNameType.setFamilyName(userFamilyName);
			personNameType.setGivenName(userGivenName);
			personNameType.setSecondNameOrInitials(userInitials);
			userType.setPersonName(personNameType);
			userType.setUserName(userName);

			// setRole
			ceType = new CeType();
			ceType.setCode(ROLE_CODE);
			ceType.setCodeSystem(ROLE_CODE_SYSTEM);
			ceType.setCodeSystemName(ROLE_CODE_SYSTEM_NAME);
			ceType.setDisplayName(ROLE_DISPLAY_NAME);
			userType.setRoleCoded(ceType);

			HomeCommunityType homeCommunityType2 = new HomeCommunityType();
			homeCommunityType2.setDescription(localHCID);
			homeCommunityType2.setHomeCommunityId(localHCID);
			homeCommunityType2.setName(localHCID);
			userType.setOrg(homeCommunityType2);
			assertionType.setUserInfo(userType);

			assertionType.setAuthorized(true);
			// assertionType.setClaimFormRef("claimFormRef");
			String bt = "cid:610957193055";
			byte[] btVal = (byte[]) bt.getBytes();
			// assertionType.setClaimFormRaw(btVal);

			respondingGatewayPRPAIN201305UV02Request.setAssertion(assertionType);

			// NhinTargetCommunities***********************************************************
			NhinTargetCommunitiesType nhinTargetCommunities = new NhinTargetCommunitiesType();
			NhinTargetCommunityType nhinTargetCommunityType = new NhinTargetCommunityType();
			HomeCommunityType homeCommunityType = new HomeCommunityType();
			List<String> remoteHCIDList = new ArrayList<String>();
			remoteHCIDList = remoteHCIDList1;
			String remoteHCID = "";
			for (int i = 0; i < remoteHCIDList.size(); i++) {
				nhinTargetCommunityType = new NhinTargetCommunityType();
				homeCommunityType = new HomeCommunityType();
				remoteHCID = remoteHCIDList.get(i);
				log.info("remoteHCID***" + (i + 1) + " :" + remoteHCID);
				homeCommunityType.setDescription("Doc Query for " + remoteHCID);
				homeCommunityType.setHomeCommunityId(remoteHCID);
				homeCommunityType.setName(remoteHCID);
				// homeCommunityType.setName("HCID2");
				nhinTargetCommunityType.setHomeCommunity(homeCommunityType);
				nhinTargetCommunities.getNhinTargetCommunity().add(nhinTargetCommunityType);
			}

			respondingGatewayPRPAIN201305UV02Request.setNhinTargetCommunities(nhinTargetCommunities);
			// End
			// NhinTargetCommunities***************************************************************

			result = entityPatientDiscoveryPortType248
					.respondingGatewayPRPAIN201305UV02(respondingGatewayPRPAIN201305UV02Request);
			log.debug("PD-Response messsage===" + result);
			entityResponseType = PDUtil.getPDEntityResponseTypeFromRespGatewayPRPAIN201306UV02(result);

		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new WSClient248RuntimeException("Problem occured while sending message to Gateway " , ex);
		}
		return entityResponseType;
	}

	/**
	 * 
	 * @param fileContent
	 * @return
	 */

	private PatientPD getPatientInfo(String fileContent) {
	 	return PDUtil.unmarshallPDConfig(fileContent);
	}

	private String formatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 
	 * @param parameterList
	 * @param patientNames
	 * @param patient
	 */
	private void addPatientNames(PRPAMT201306UV02ParameterList parameterList, PatientNamesType patientNames,
			PatientPD patient) {
		List<String> patientGivenNames = null;

		if (patientNames != null && patientNames.getPatientName() != null && patientNames.getPatientName().size() > 0) {
			log.info("############## - Patient Names List found" + patientNames);

			// Patient Names
			for (PatientNameType ptname : patientNames.getPatientName()) {
				PatientGivenNamesType givenames = ptname.getPatientGivenNames();
				String patientFamilyName = ptname.getPatientLastName();
				String patientPrefix = ptname.getPatientPrefix();
				String patientSuffix = ptname.getPatientSuffix();
				// Patient Given Names
				if (givenames != null && givenames.getGivenName() != null && givenames.getGivenName().size() > 0) {
					patientGivenNames = new ArrayList();
					for (String patientGivenName : givenames.getGivenName()) {
						patientGivenNames.add(patientGivenName);
					}
				}
				addSinglePatientName(parameterList, patientFamilyName, patientGivenNames, patientPrefix, patientSuffix);
			}

		} else {
			// note ::: the prefix is not supported previously in web service
			// cleint code .
			log.info("############## - Single Patient Name found");
			patientGivenNames = new ArrayList();
			String patientFamilyName = patient.getPatientLastName();
			// String patientPrefix = patient.getPatientPrefix();
			String patientGivenName = patient.getPatientGivenName();
			patientGivenNames.add(patientGivenName);
			addSinglePatientName(parameterList, patientFamilyName, patientGivenNames, null, null);
		}
	}

	/**
	 * 
	 * @param parameterList
	 * @param patientFamilyName
	 * @param patientGivenNames
	 * @param patientPrefix
	 * @param patientSuffix
	 */
	private void addSinglePatientName(PRPAMT201306UV02ParameterList parameterList, String patientFamilyName,
			List<String> patientGivenNames, String patientPrefix, String patientSuffix) {
		log.info("***************************");
		log.info("PD - patientPrefix: " + patientPrefix);
		log.info("PD - patientFamilyName: " + patientFamilyName);
		log.info("PD - patientSuffix: " + patientSuffix);

		PRPAMT201306UV02LivingSubjectName livingSubjectName = new PRPAMT201306UV02LivingSubjectName();
		ST sT = null;
		ENExplicit eNExplicit = new ENExplicit();
		if (isNotEmpty(patientPrefix)) {
			EnExplicitPrefix prefix = new EnExplicitPrefix();
			prefix.setPartType("PFX");
			prefix.setContent(patientPrefix);
			eNExplicit.getContent().add(hl7ObjFactory.createENExplicitPrefix(prefix));
		}
		if (isNotEmpty(patientFamilyName)) {
			EnExplicitFamily familyName = new EnExplicitFamily();
			familyName.setPartType("FAM");
			familyName.setContent(patientFamilyName);
			eNExplicit.getContent().add(hl7ObjFactory.createENExplicitFamily(familyName));
		}
		if (isNotEmpty(patientSuffix)) {
			EnExplicitSuffix suffix = new EnExplicitSuffix();
			suffix.setPartType("SFX");
			suffix.setContent(patientSuffix);
			eNExplicit.getContent().add(hl7ObjFactory.createENExplicitSuffix(suffix));
		}
		// add given names
		if (patientGivenNames != null && patientGivenNames.size() > 0) {
			for (String patientGivenName : patientGivenNames) {
				addSinglePatientGivenName(eNExplicit, patientGivenName);
			}
		}

		livingSubjectName.getValue().add(eNExplicit);

		sT = new ST();
		sT.setRepresentation(BinaryDataEncoding.TXT);
		livingSubjectName.setSemanticsText(sT);
		parameterList.getLivingSubjectName().add(livingSubjectName);
	}

	/**
	 * 
	 * @param eNExplicit
	 * @param patientGivenName
	 */
	private void addSinglePatientGivenName(ENExplicit eNExplicit, String patientGivenName) {

		if (isNotEmpty(patientGivenName)) {
			log.info("PD - patientGivenName: " + patientGivenName);
			EnExplicitGiven givenName = new EnExplicitGiven();
			givenName.setPartType("GIV");
			givenName.setContent(patientGivenName);
			eNExplicit.getContent().add(hl7ObjFactory.createENExplicitGiven(givenName));
		}
	}

	/**
	 * 
	 * @param parameterList
	 * @param addresses
	 * @param patient
	 */
	private void addPatientAddresses(PRPAMT201306UV02ParameterList parameterList, PatientAddressesType addresses,
			PatientPD patient) {
		String streetName = null;
		String city = null;
		String state = null;
		String zip = null;

		if (addresses != null && addresses.getPatientAddress() != null && addresses.getPatientAddress().size() > 0) {
			log.info("addresses List found : " + addresses);
			// Address
			for (PatientAddressType addlist : addresses.getPatientAddress()) {
				streetName = addlist.getPatientStreetAddress1();
				city = addlist.getPatientCity();
				state = addlist.getPatientState();
				zip = addlist.getPatientZip();
				addSinglePatientAddress(parameterList, streetName, city, state, zip);
			}
		} else {
			log.info("Signle address found : ");
			streetName = patient.getPatientStreetAddress();
			city = patient.getPatientCity();
			state = patient.getPatientState();
			zip = patient.getPatientZip();
			addSinglePatientAddress(parameterList, streetName, city, state, zip);
		}

	}

	/**
	 * 
	 * @param parameterList
	 * @param streetName
	 * @param city
	 * @param state
	 * @param zip
	 */
	private void addSinglePatientAddress(PRPAMT201306UV02ParameterList parameterList, String streetName, String city,
			String state, String zip) {
		log.info("PD - streetName: " + streetName);
		log.info("PD - city: " + city);
		log.info("PD - state: " + state);
		log.info("PD - zip: " + zip);
		// Address
		PRPAMT201306UV02PatientAddress patientAddress = new PRPAMT201306UV02PatientAddress();
		ADExplicit aDExplicit = new ADExplicit();

		if (isNotEmpty(streetName)) {
			AdxpExplicitStreetAddressLine streetAddrLine = new AdxpExplicitStreetAddressLine();
			streetAddrLine.setContent(streetName);
			aDExplicit.getContent().add(hl7ObjFactory.createADExplicitStreetAddressLine(streetAddrLine));
		}

		if (isNotEmpty(city)) {
			AdxpExplicitCity aCity = new AdxpExplicitCity();
			aCity.setContent(city);
			aDExplicit.getContent().add(hl7ObjFactory.createADExplicitCity(aCity));
		}

		if (isNotEmpty(state)) {
			AdxpExplicitState aState = new AdxpExplicitState();
			aState.setContent(state);
			aDExplicit.getContent().add(hl7ObjFactory.createADExplicitState(aState));
		}

		if (isNotEmpty(zip)) {
			AdxpExplicitPostalCode postalCode = new AdxpExplicitPostalCode();
			postalCode.setContent(zip);
			aDExplicit.getContent().add(hl7ObjFactory.createADExplicitPostalCode(postalCode));
		}

		patientAddress.getValue().add(aDExplicit);
		parameterList.getPatientAddress().add(patientAddress);
	}

	/**
	 * 
	 * @param parameterList
	 * @param patientTels
	 * @param patient
	 */
	private void addPatientTelephones(PRPAMT201306UV02ParameterList parameterList, PatientTelsType patientTels,
			PatientPD patient) {
		if (patientTels != null && patientTels.getPatientTel() != null && patientTels.getPatientTel().size() > 0) {
			log.info("patient telephone List found : " + patientTels);
			for (PatientTelType eachPatientTelType : patientTels.getPatientTel()) {
				String patientTel = null;
				String useCode = null;
				if (eachPatientTelType != null) {
					patientTel = eachPatientTelType.getValue();
					TelephoneUseType telephoneUseType = eachPatientTelType.getUse();
					if (telephoneUseType != null) {
						useCode = telephoneUseType.value();
					}
				}
				addSinglePatientTelephone(parameterList, patientTel, useCode);
			}
		} else {
			log.info(" signle patient telephone found : ");
			String patientTel = patient.getPatientTel();
			addSinglePatientTelephone(parameterList, patientTel);
		}
	}

	/**
	 * 
	 * @param parameterList
	 * @param patientTel
	 */
	private void addSinglePatientTelephone(PRPAMT201306UV02ParameterList parameterList, String patientTel) {
		addSinglePatientTelephone(parameterList, patientTel, null);
	}

	/**
	 * 
	 * @param parameterList
	 * @param patientTel
	 * @param useCode
	 */
	private void addSinglePatientTelephone(PRPAMT201306UV02ParameterList parameterList, String patientTel,
			String useCode) {
		ST sT = null;
		if (isNotEmpty(patientTel)) {
			PRPAMT201306UV02PatientTelecom pRPAMT201306UV02patientTelecom = new PRPAMT201306UV02PatientTelecom();
			TELExplicit telEx = new TELExplicit();
			telEx.setValue(patientTel);
			// pass use type as attribute
			if (isNotEmpty(useCode)) {
				telEx.getUse().add(useCode);
			}
			pRPAMT201306UV02patientTelecom.getValue().add(telEx);
			sT = new ST();
			sT.setRepresentation(BinaryDataEncoding.TXT);
			pRPAMT201306UV02patientTelecom.setSemanticsText(sT);
			parameterList.getPatientTelecom().add(pRPAMT201306UV02patientTelecom);
		}
	}

	private static boolean isNotEmpty(String str) {
		return (str != null && str.length() > 0);
	}

	private EntityPatientDiscoveryPortType getServiceEndPoint(String endpointURL) {
		EntityPatientDiscoveryPortType entityPatientDiscoveryPortType248 = null;
		try {
			entityPatientDiscoveryPortType248 = getPatientDiscovery248FromContext();

			log.debug("getServiceEndPoint() called ");

			// Use Proxy Instance as BindingProvider
			BindingProvider bp = (BindingProvider) entityPatientDiscoveryPortType248;

			// (Optional) Configure RequestContext with endpoint's URL
			Map<String, Object> rc = bp.getRequestContext();
			log.debug("Default Endpoint URL :::" + rc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
			rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
			log.debug("Modified Endpoint URL :::" + rc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new WSClient248RuntimeException(ex);
		}
		return entityPatientDiscoveryPortType248;
	}

	private EntityPatientDiscoveryPortType getPatientDiscovery248FromContext() {
		return (EntityPatientDiscoveryPortType) ApplicationContextProvider
				.getBean(PDUtil.ENTITY_PATIENT_DISCOVERY_PORT_TYPE_PROXY);
	}

}
