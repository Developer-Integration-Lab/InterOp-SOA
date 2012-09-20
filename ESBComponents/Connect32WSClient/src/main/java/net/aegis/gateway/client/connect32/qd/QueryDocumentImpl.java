package net.aegis.gateway.client.connect32.qd;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunitiesType;
import gov.hhs.fha.nhinc.common.nhinccommon.NhinTargetCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;
import gov.hhs.fha.nhinc.entitydocquery.EntityDocQueryPortType;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import net.aegis.gateway.client.connect32.util.ApplicationContextProvider;
import net.aegis.gateway.client.connect32.util.QDUtil;
import net.aegis.gateway.client.exception.WSClient32RuntimeException;
import net.aegis.labcommons.jaxb.qd.QDEntityResponseType;
import net.aegis.labcommons.service.QueryDocument;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author Venkat.Keesara
 */
public class QueryDocumentImpl implements QueryDocument {
	private static final Log log = LogFactory.getLog(QueryDocumentImpl.class);

    public QueryDocumentImpl(){}

    /* (non-Javadoc)
	 * @see net.aegis.gateway.clients.connect32.qd.QueryDocument#queryForDocuments(java.lang.String, java.lang.String, java.util.List, java.lang.String)
	 */
    public QDEntityResponseType queryForDocuments(String endpointURL, String localHCID, List remoteHCIDList1, String testcaseConfig) {
      
        return queryForDocuments(endpointURL, localHCID, remoteHCIDList1, testcaseConfig, null);
    }

    /* (non-Javadoc)
	 * @see net.aegis.gateway.clients.connect32.qd.QueryDocument#queryForDocuments(java.lang.String, java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 */
    public QDEntityResponseType queryForDocuments(String endpointURL, String localHCID, List remoteHCIDList1, String testcaseConfig, String initiatorPatientId) {
        oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse result = new oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse();
        QDEntityResponseType entityResponseType = null;
        try {
            gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayCrossGatewayQueryRequestType respondingGatewayCrossGatewayQueryRequest = new gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayCrossGatewayQueryRequestType();
            
            PatientQD patient = new PatientQD();
            patient = getPatientInfo(testcaseConfig);
            String patientId = patient.getInitiatorPatientId(); // responder patient is passed from config 
            log.info("Responder patientId From File: " + patientId);
            // this is for initiator scenarios 
            if(isNotEmpty(initiatorPatientId)){
            	patientId = initiatorPatientId;
            	log.info("InitiatorPatientId: " + patientId);
            }
            String userFamilyName = patient.getUserFamilyName();
            String userGivenName = patient.getUserGivenName();
            String userName = patient.getUserName();
            String userInitials = patient.getUserInitials();

            String creationTimeInd = patient.getDocumentParams().getCreationTimeInd();
            String serviceStartTimeInd = patient.getDocumentParams().getServiceStartTimeInd();
            String serviceStopTimeInd = patient.getDocumentParams().getServiceStopTimeInd();
            String classCodeInd = patient.getDocumentParams().getClassCodeInd();
            String statusInd = patient.getDocumentParams().getStatusInd();

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date creationFromDate = df.parse(patient.getDocumentParams().getCreationTimeFrom());
            Date creationToDate = df.parse(patient.getDocumentParams().getCreationTimeTo());
            Date serviceStartTimeFrom = df.parse(patient.getDocumentParams().getServiceStartTimeFrom());
            Date serviceStartTimeTo = df.parse(patient.getDocumentParams().getServiceStartTimeTo());
            Date serviceStopTimeFrom = df.parse(patient.getDocumentParams().getServiceStopTimeFrom());
            Date serviceStopTimeTo = df.parse(patient.getDocumentParams().getServiceStopTimeTo());
            List<ClassCodeListQD> classCodeList = patient.getDocumentParams().getClassCodeList();
            List<StatusListQD> statusList = patient.getDocumentParams().getStatusList();

            String localAA = localHCID;
            //String ipAddress = initiatorIpAddress;
            //String endpointURL = "http://"+ ipAddress + ":8080/NhinConnect/EntityDocQuery";
            String uniquePatientId = patientId + "^^^&" + localHCID + "&ISO";
            log.info("uniquePatientId: " + uniquePatientId);
            log.info("endpointURL: " + endpointURL);
            log.info("localHCID: " + localHCID);

            //Dynamic proxy ************************************************************************************************
            EntityDocQueryPortType entityDocQueryPortType = getServiceEndPoint(endpointURL);
            //End - Dynamic proxy ********************************************************************************************

            //AdhocQueryRequest*************************************************
            AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
            adhocQueryRequest.setFederated(Boolean.FALSE);
            adhocQueryRequest.setStartIndex(BigInteger.ZERO);
            adhocQueryRequest.setMaxResults(BigInteger.valueOf(-1));

            ResponseOptionType responseOptionType = new ResponseOptionType();
            responseOptionType.setReturnType("RegistryObject");
            responseOptionType.setReturnComposedObjects(Boolean.FALSE);
            adhocQueryRequest.setResponseOption(responseOptionType);

            AdhocQueryType adhocQueryType = new AdhocQueryType();
            //String adQueryHome = "urn:oid:" + localHCID;
            //adhocQueryType.setHome(adQueryHome);
            adhocQueryType.setId(QDUtil.QUERY_TYPE_ID);

            //AdhocQuery - set PatientId -- Begin
            SlotType1 slot = new SlotType1();
            slot.setName(QDUtil.PATIENT_ID_SLOT_NAME);
            ValueListType valueList = new ValueListType();
            valueList.getValue().add(uniquePatientId);
            slot.setValueList(valueList);
            adhocQueryType.getSlot().add(slot);
            //AdhocQuery - PatientId - End

            //************************************************************************************************************************************
            //AdhocQuery - DOCUMENT_ENTRY_CLASS_CODE - Begin
            if ((classCodeInd != null) && (classCodeInd.equalsIgnoreCase("Y")) &&
                    (classCodeList != null) && (classCodeList.size() > 0)) {
                log.info("---------- CONFIG - CLASS CODE SIZE = " + classCodeList.size() + " ----------");
                // Iterate over classCodeList and build classCodeValue string - e.g. ('classCode1','classCode2')
                StringBuffer classCodeValue = new StringBuffer("('");
                for (ClassCodeListQD classCodeListQD : classCodeList) {
                    log.info("---------- CONFIG - CLASS CODE = " + classCodeListQD.getClassCode() + " ----------");
                    if (classCodeListQD != null && classCodeListQD.getClassCode() != null) {
                        if (classCodeValue.length() > 3) {
                            classCodeValue.append("','");
                        }
                        classCodeValue.append(classCodeListQD.getClassCode());
                    }
                }
                classCodeValue.append("')");

                SlotType1 classCodeSlot = new SlotType1();
                classCodeSlot.setName(QDUtil.DOCUMENT_ENTRY_CLASS_CODE_SLOT_NAME);
                ValueListType classCodeValueList = new ValueListType();
                classCodeValueList.getValue().add(classCodeValue.toString());
                classCodeSlot.setValueList(classCodeValueList);
                adhocQueryType.getSlot().add(classCodeSlot);
            }
            //AdhocQuery - DOCUMENT_ENTRY_CLASS_CODE - End

            //************************************************************************************************************************************
            //AdhocQuery - DOCUMENT_ENTRY_STATUS - Begin
            if ((statusInd != null) && (statusInd.equalsIgnoreCase("Y")) &&
                    (statusList != null) && (statusList.size() > 0)) {
                log.info("---------- CONFIG - STATUS SIZE = " + statusList.size() + " ----------");
                // Iterate over statusList and build statusValue string - e.g. ('status1','status2')
                StringBuffer statusValue = new StringBuffer("('");
                for (StatusListQD statusListQD : statusList) {
                    log.info("---------- CONFIG - STATUS = " + statusListQD.getStatus() + " ----------");
                    if (statusListQD != null && statusListQD.getStatus() != null) {
                        if (statusValue.length() > 3) {
                            statusValue.append("','");
                        }
                        statusValue.append(statusListQD.getStatus());
                    }
                }
                statusValue.append("')");

                SlotType1 statusSlot = new SlotType1();
                statusSlot.setName(QDUtil.DOCUMENT_ENTRY_STATUS_SLOT_NAME);
                ValueListType statusValueList = new ValueListType();
                statusValueList.getValue().add(statusValue.toString());
                statusSlot.setValueList(statusValueList);
                adhocQueryType.getSlot().add(statusSlot);
            }
            //AdhocQuery - DOCUMENT_ENTRY_STATUS - End

            //**************************************************************************************************************************************
            if ((creationTimeInd != null) && (creationTimeInd.equalsIgnoreCase("Y"))) {
                //AdhocQuery - Set Creation From Date - Begin
                SlotType1 creationStartTimeSlot = new SlotType1();
                creationStartTimeSlot.setName(QDUtil.CREATION_TIME_FROM_SLOT_NAME);
                ValueListType creationStartTimeValueList = new ValueListType();
                creationStartTimeValueList.getValue().add(formatDate(creationFromDate, QDUtil.HL7_DATE_FORMAT));
                creationStartTimeSlot.setValueList(creationStartTimeValueList);
                adhocQueryType.getSlot().add(creationStartTimeSlot);
                log.info("creationFromDate***: " + formatDate(creationFromDate, QDUtil.HL7_DATE_FORMAT));
                //AdhocQuery - Set Creation From Date - End

                //AdhocQuery - Set Creation To Date - Begin
                SlotType1 creationEndTimeSlot = new SlotType1();
                creationEndTimeSlot.setName(QDUtil.CREATION_TIME_TO_SLOT_NAME);
                ValueListType creationEndTimeSlotValueList = new ValueListType();
                creationEndTimeSlotValueList.getValue().add(formatDate(creationToDate, QDUtil.HL7_DATE_FORMAT));
                creationEndTimeSlot.setValueList(creationEndTimeSlotValueList);
                adhocQueryType.getSlot().add(creationEndTimeSlot);
                log.info("creationToDate***: " + formatDate(creationToDate, QDUtil.HL7_DATE_FORMAT));
                //AdhocQuery - Set Creation To Date - End
            }

            //******************************************************************************************************************************************
            if ((serviceStartTimeInd != null) && (serviceStartTimeInd.equalsIgnoreCase("Y"))) {
                //AdhocQuery - Set SERVICE_START_TIME_FROM - Begin
                SlotType1 serviceStartTimeFromSlot = new SlotType1();
                serviceStartTimeFromSlot.setName(QDUtil.SERVICE_START_TIME_FROM_SLOT_NAME);
                ValueListType serviceStartTimeFromValueList = new ValueListType();
                serviceStartTimeFromValueList.getValue().add(formatDate(serviceStartTimeFrom, QDUtil.HL7_DATE_FORMAT));
                serviceStartTimeFromSlot.setValueList(serviceStartTimeFromValueList);
                adhocQueryType.getSlot().add(serviceStartTimeFromSlot);
                log.info("serviceStartTimeFrom***: " + formatDate(serviceStartTimeFrom, QDUtil.HL7_DATE_FORMAT));
                //AdhocQuery - Set SERVICE_START_TIME_FROM - End

                //AdhocQuery - Set SERVICE_START_TIME_TO - Begin
                SlotType1 serviceStartTimeToSlot = new SlotType1();
                serviceStartTimeToSlot.setName(QDUtil.SERVICE_START_TIME_TO_SLOT_NAME);
                ValueListType serviceStartTimeToValueList = new ValueListType();
                serviceStartTimeToValueList.getValue().add(formatDate(serviceStartTimeTo, QDUtil.HL7_DATE_FORMAT));
                serviceStartTimeToSlot.setValueList(serviceStartTimeToValueList);
                adhocQueryType.getSlot().add(serviceStartTimeToSlot);
                log.info("serviceStartTimeTo***: " + formatDate(serviceStartTimeTo, QDUtil.HL7_DATE_FORMAT));
                //AdhocQuery - Set SERVICE_START_TIME_TO - End
            }

            //******************************************************************************************************************************************
            if ((serviceStopTimeInd != null) && (serviceStopTimeInd.equalsIgnoreCase("Y"))) {
                //AdhocQuery - Set SERVICE_STOP_TIME_FROM - Begin
                SlotType1 serviceStopTimeFromSlot = new SlotType1();
                serviceStopTimeFromSlot.setName(QDUtil.SERVICE_STOP_TIME_FROM_SLOT_NAME);
                ValueListType serviceStopTimeFromValueList = new ValueListType();
                serviceStopTimeFromValueList.getValue().add(formatDate(serviceStopTimeFrom, QDUtil.HL7_DATE_FORMAT));
                serviceStopTimeFromSlot.setValueList(serviceStopTimeFromValueList);
                adhocQueryType.getSlot().add(serviceStopTimeFromSlot);
                //AdhocQuery - Set SERVICE_STOP_TIME_FROM - End

                //AdhocQuery - Set SERVICE_STOP_TIME_TO - Begin
                SlotType1 serviceStopTimeToSlot = new SlotType1();
                serviceStopTimeToSlot.setName(QDUtil.SERVICE_STOP_TIME_TO_SLOT_NAME);
                ValueListType serviceStopTimeToValueList = new ValueListType();
                serviceStopTimeToValueList.getValue().add(formatDate(serviceStopTimeTo, QDUtil.HL7_DATE_FORMAT));
                serviceStopTimeToSlot.setValueList(serviceStopTimeToValueList);
                adhocQueryType.getSlot().add(serviceStopTimeToSlot);
                //AdhocQuery - Set SERVICE_STOP_TIME_TO - End
            }

            //******************************************************************************************************************************************

            ResponseOptionType responseOption = new ResponseOptionType();
            responseOption.setReturnType("LeafClass");
            responseOption.setReturnComposedObjects(Boolean.FALSE);
            adhocQueryRequest.setResponseOption(responseOption);
            adhocQueryRequest.setAdhocQuery(adhocQueryType);
            respondingGatewayCrossGatewayQueryRequest.setAdhocQueryRequest(adhocQueryRequest);
            //End AdhocQueryRequest***********************************************************

            //Assertion **********************************************************************
            AssertionType assertionType = new AssertionType();
            assertionType.setDateOfBirth("06/04/1959 05:21:00");
            //assertionType.setDateOfSignature("02/03/2010");
            //assertionType.setExpirationDate("03/05/2010 15:56:56");
            HomeCommunityType homeCommunityType1 = new HomeCommunityType();
            homeCommunityType1.setDescription(localHCID);
            homeCommunityType1.setHomeCommunityId(localHCID);
            homeCommunityType1.setName(localHCID);
            assertionType.setHomeCommunity(homeCommunityType1);
            String assertUniquePatientId = patientId + "^^^&" + localAA + "&ISO";
            assertionType.getUniquePatientId().add(assertUniquePatientId);

             //Purpose of use
            CeType ceType = new CeType();
            ceType.setCode(QDUtil.PURPOSE_OF_DISCLOSURE_CODE);
            ceType.setCodeSystem(QDUtil.PURPOSE_OF_DISCLOSURE_CODE_SYSTEM);
            ceType.setCodeSystemName(QDUtil.PURPOSE_OF_DISCLOSURE_CODE_SYSTEM_NAME);
            ceType.setDisplayName(QDUtil.PURPOSE_OF_DISCLOSURE_DISPLAY_NAME);
            assertionType.setPurposeOfDisclosureCoded(ceType);

            //user name
            UserType userType= new UserType();
            PersonNameType personNameType = new PersonNameType();
            personNameType.setFamilyName(userFamilyName);
            personNameType.setGivenName(userGivenName);
            personNameType.setSecondNameOrInitials(userInitials);
            userType.setPersonName(personNameType);
            userType.setUserName(userName);

            //setRole
            ceType = new CeType();
            ceType.setCode(QDUtil.ROLE_CODE);
            ceType.setCodeSystem(QDUtil.ROLE_CODE_SYSTEM);
            ceType.setCodeSystemName(QDUtil.ROLE_CODE_SYSTEM_NAME);
            ceType.setDisplayName(QDUtil.ROLE_DISPLAY_NAME);
            userType.setRoleCoded(ceType);

            HomeCommunityType homeCommunityType2 = new HomeCommunityType();
            homeCommunityType2.setDescription(localHCID);
            homeCommunityType2.setHomeCommunityId(localHCID);
            homeCommunityType2.setName(localHCID);
            userType.setOrg(homeCommunityType2);
            assertionType.setUserInfo(userType);

            assertionType.setAuthorized(true);
            respondingGatewayCrossGatewayQueryRequest.setAssertion(assertionType);
            //End Assertion

            //NhinTargetCommunities***********************************************************
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
                homeCommunityType.setDescription(remoteHCID);
                homeCommunityType.setHomeCommunityId(remoteHCID);
                homeCommunityType.setName(remoteHCID);
                //homeCommunityType.setName("HCID2");
                nhinTargetCommunityType.setHomeCommunity(homeCommunityType);
                nhinTargetCommunities.getNhinTargetCommunity().add(nhinTargetCommunityType);
            }

            respondingGatewayCrossGatewayQueryRequest.setNhinTargetCommunities(nhinTargetCommunities);
            //End NhinTargetCommunities***************************************************************

            result = entityDocQueryPortType.respondingGatewayCrossGatewayQuery(respondingGatewayCrossGatewayQueryRequest);
            
            entityResponseType = QDUtil.getQDEntityResponseFromAdhocQueryResponse(result);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new WSClient32RuntimeException(ex);
        }
        return entityResponseType;
    }

   

    /**
     * 
     * @param fileContent
     * @return
     */
    
    public PatientQD getPatientInfo(String fileContent) {
    	return QDUtil.unmarshallQDConfig(fileContent);
    }

    private String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    private static boolean isNotEmpty(String str) {
		return (str != null && str.length() > 0);
	}
    
    private EntityDocQueryPortType getServiceEndPoint(String endpointURL) {
    	EntityDocQueryPortType entityDocQueryPortType32 =null;
    	try {
			entityDocQueryPortType32 = getEntityDocQueryPortTypeFromContext();
			
			log.debug("getServiceEndPoint() called ");
			
			// Use Proxy Instance as BindingProvider
			BindingProvider bp = (BindingProvider) entityDocQueryPortType32;
			
			// (Optional) Configure RequestContext with endpoint's URL
			Map<String, Object> rc = bp.getRequestContext();
			log.debug("Default Endpoint URL :::" + rc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
			rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
			log.debug("Modified Endpoint URL :::" + rc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new WSClient32RuntimeException(ex);
		}
		return entityDocQueryPortType32 ; 
	}
    
    private EntityDocQueryPortType getEntityDocQueryPortTypeFromContext() {
		return (EntityDocQueryPortType)ApplicationContextProvider.getBean(QDUtil.ENTITY_DOC_QUERY_PORT_TYPE_PROXY);
	}
}
