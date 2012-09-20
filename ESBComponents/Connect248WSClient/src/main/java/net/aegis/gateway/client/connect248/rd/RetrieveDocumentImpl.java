package net.aegis.gateway.client.connect248.rd;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;
import gov.hhs.fha.nhinc.entitydocretrieve.EntityDocRetrievePortType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType.DocumentRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.ws.BindingProvider;

import net.aegis.gateway.client.connect248.util.ApplicationContextProvider;
import net.aegis.gateway.client.connect248.util.FormatUtil;
import net.aegis.gateway.client.connect248.util.RDUtil;
import net.aegis.gateway.client.exception.WSClient248RuntimeException;
import net.aegis.labcommons.jaxb.rd.RDEntityResponseType;
import net.aegis.labcommons.service.RetrieveDocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mastan.ketha
 */
public class RetrieveDocumentImpl implements RetrieveDocument {

    private static final Log log = LogFactory.getLog(RetrieveDocumentImpl.class);

    private static final String ROLE_CODE = "46255001";
    private static final String ROLE_CODE_SYSTEM = "2.16.840.1.113883.6.96";
    private static final String ROLE_CODE_SYSTEM_NAME = "SNOMED_CT";
    private static final String ROLE_DISPLAY_NAME = "Pharmacist";

//    private static final String PURPOSE_OF_DISCLOSURE_CODE = "OPERATIONS";
    private static final String PURPOSE_OF_DISCLOSURE_CODE = "TREATMENT";
    private static final String PURPOSE_OF_DISCLOSURE_CODE_SYSTEM = "2.16.840.1.113883.3.18.7.1";
    private static final String PURPOSE_OF_DISCLOSURE_CODE_SYSTEM_NAME = "nhin-purpose";
//    private static final String PURPOSE_OF_DISCLOSURE_DISPLAY_NAME = "Healthcare Operations";
    private static final String PURPOSE_OF_DISCLOSURE_DISPLAY_NAME = "Treatment";
    private static final String ENTITY_DOC_RETRIEVE_PORT_TYPE_PROXY = "entityDocRetrievePortType248Proxy";
    public RetrieveDocumentImpl(){}

    /* (non-Javadoc)
	 * @see net.aegis.gateway.clients.connect248.rd.RetrieveDocument#retrieveDocuments(java.lang.String, java.lang.String, java.util.Map, java.lang.String)
	 */
    public RDEntityResponseType retrieveDocuments(String endpointURL, String localHCID, Map<String, String> remoteHCIDs, String testcaseConfig) {
    	
    	String initiatorPatientId=null;
    	Map<String, String> docAndRepIds = null;
    	return retrieveDocuments(endpointURL, localHCID, remoteHCIDs, testcaseConfig, docAndRepIds,initiatorPatientId);
    }

    /* (non-Javadoc)
	 * @see net.aegis.gateway.clients.connect248.rd.RetrieveDocument#retrieveDocuments(java.lang.String, java.lang.String, java.util.Map, java.lang.String, java.lang.String)
	 */
    public RDEntityResponseType retrieveDocuments(String endpointURL, String localHCID, Map<String, String> remoteHCIDs, String testcaseConfig, String initiatorPatientId) {

        Map<String, String> docAndRepIds = null;
        return retrieveDocuments(endpointURL, localHCID, remoteHCIDs, testcaseConfig, docAndRepIds,initiatorPatientId);
    }

   /* (non-Javadoc)
 * @see net.aegis.gateway.clients.connect248.rd.RetrieveDocument#retrieveDocuments(java.lang.String, java.lang.String, java.util.Map, java.lang.String, java.util.Map, java.lang.String)
 */
    public RDEntityResponseType retrieveDocuments(String endpointURL, String localHCID, Map<String, String> remoteHCIDs, String testcaseConfig, Map<String, String> docAndRepIds, String initiatorPatientId) {
        ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType result = new ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType();
        RDEntityResponseType entityResponseType = null;
        try {
            gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayCrossGatewayRetrieveRequestType respondingGatewayCrossGatewayRetrieveRequest = new gov.hhs.fha.nhinc.common.nhinccommonentity.RespondingGatewayCrossGatewayRetrieveRequestType();

            PatientRD patient = new PatientRD();
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
            List<DocumentRD> patientDocuments = patient.getPatientDocument();

            String localAA = localHCID;
            String uniquePatientId = patientId + "^^^&" + localAA + "&ISO";
            log.info("uniquePatientId: " + uniquePatientId);
            log.info("endpointURL: " + endpointURL);
            log.info("localHCID: " + localHCID);

            //Dynamic proxy ************************************************************************************************
            EntityDocRetrievePortType entityDocQueryPortType248 = getServiceEndPoint(endpointURL);
            //End - Dynamic proxy ***************************************************************************

            //RetrieveDocumentSetRequest*********************************************************************
            RetrieveDocumentSetRequestType retrieveDocumentSetRequest = new RetrieveDocumentSetRequestType();
            
            if(docAndRepIds==null || docAndRepIds.size()==0){
            	// initiator scenario 
            	log.info("Calling Initiator scenario | prepareInitiatorScenarioData(): " );
            	prepareInitiatorScenarioData(patientDocuments, remoteHCIDs, retrieveDocumentSetRequest);
            }
            else{
            	//responder scenario 
            	log.info("Calling Responder scenario | prepareResponderScenarioData(): " );
            	prepareResponderScenarioData(patientDocuments, remoteHCIDs, docAndRepIds, retrieveDocumentSetRequest);
            }
            respondingGatewayCrossGatewayRetrieveRequest.setRetrieveDocumentSetRequest(retrieveDocumentSetRequest);
            //End RetrieveDocumentSetRequest*************************************************************************

            //Assertion *********************************************************************************************
            AssertionType assertionType = new AssertionType();
            assertionType.setDateOfBirth("06/04/1959 05:21:00");
            //assertionType.setDateOfSignature("02/03/2010");
            //assertionType.setExpirationDate("03/05/2010 15:56:56");
            HomeCommunityType homeCommunityType1 = new HomeCommunityType();
            homeCommunityType1.setDescription(localHCID);
            homeCommunityType1.setHomeCommunityId(localHCID);
            homeCommunityType1.setName(localHCID);
            assertionType.setHomeCommunity(homeCommunityType1);
            //assertionType.setPurposeOfDisclosure("treatment");
            assertionType.getUniquePatientId().add(uniquePatientId);

            //Purpose of use
            CeType ceType = new CeType();
            ceType.setCode(PURPOSE_OF_DISCLOSURE_CODE);
            ceType.setCodeSystem(PURPOSE_OF_DISCLOSURE_CODE_SYSTEM);
            ceType.setCodeSystemName(PURPOSE_OF_DISCLOSURE_CODE_SYSTEM_NAME);
            ceType.setDisplayName(PURPOSE_OF_DISCLOSURE_DISPLAY_NAME);
            assertionType.setPurposeOfDisclosureCoded(ceType);

            //user name
            UserType userType = new UserType();
            PersonNameType personNameType = new PersonNameType();
            personNameType.setFamilyName(userFamilyName);
            personNameType.setGivenName(userGivenName);
            personNameType.setSecondNameOrInitials(userInitials);
            userType.setPersonName(personNameType);
            userType.setUserName(userName);

            //setRole
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
            respondingGatewayCrossGatewayRetrieveRequest.setAssertion(assertionType);
            //End Assertion

            result = entityDocQueryPortType248.respondingGatewayCrossGatewayRetrieve(respondingGatewayCrossGatewayRetrieveRequest);
            entityResponseType = RDUtil.getRDEntityResponseFromRetrieveDocSetResponse(result);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new WSClient248RuntimeException(ex);
        }

        return entityResponseType;
    }
    
    private void  prepareInitiatorScenarioData(List<DocumentRD> patientDocuments,Map<String, String> remoteHCIDs, RetrieveDocumentSetRequestType retrieveDocumentSetRequest){
    	
    	log.info(" prepareInitiatorScenarioData() Start: " );
    	DocumentRequest documentRequest = new DocumentRequest();
         String remoteHCIDCode = "";
         String remoteHCID = "";
         String remoteHCIDMappingCode = "";
         for (DocumentRD documentRd : patientDocuments) {
             documentRequest = new DocumentRequest();
             remoteHCIDCode = documentRd.getResponderHCIDCode();
             remoteHCID = "";
             remoteHCIDMappingCode = "";
             if ((remoteHCIDCode == null) || remoteHCIDCode.equals("")) {
                 log.warn("remoteHCIDCode is null");
                 return ;
             } else {
                 Set s = remoteHCIDs.entrySet();
                 Iterator it = s.iterator();
                 while (it.hasNext()) {
                     Map.Entry m = (Map.Entry) it.next();
                     remoteHCIDMappingCode = (String) (m.getKey());
                     if (remoteHCIDMappingCode.equals(remoteHCIDCode)) {
                         remoteHCID = (String) m.getValue();
                     }
                 }
             }
             log.info("retrieveDocuments - remoteHCID  :" + remoteHCID);
             log.info("retrieveDocuments - DocumentUniqueId  :" + documentRd.getDocumentUniqueId());
             log.info("retrieveDocuments - RepositoryUniqueId  :" + documentRd.getRepositoryUniqueId());
             if ((remoteHCID == null) || remoteHCID.equals("")) {
                 log.warn("remoteHCID is null");
             } else {
                 documentRequest.setHomeCommunityId(FormatUtil.appendPrefixForHomeCommunityId(remoteHCID));
                 documentRequest.setRepositoryUniqueId(documentRd.getRepositoryUniqueId());
                 documentRequest.setDocumentUniqueId(documentRd.getDocumentUniqueId());
                 retrieveDocumentSetRequest.getDocumentRequest().add(documentRequest);
             }
             
         }
         log.info(" prepareInitiatorScenarioData() End: " );
         return ;
    }
    
    private void prepareResponderScenarioData(List<DocumentRD> patientDocuments,Map<String, String> remoteHCIDs, Map<String, String> docAndRepIds ,  RetrieveDocumentSetRequestType retrieveDocumentSetRequest){
    	log.info(" prepareResponderScenarioData() Start: " );
    	DocumentRequest documentRequest = new DocumentRequest();
        String remoteHCIDCode = "";
        String remoteHCID = "";
        String remoteHCIDMappingCode = "";
    	//For Responder scenarios there will be only one remote HCID
        for (DocumentRD documentRd : patientDocuments) {
            remoteHCIDCode = documentRd.getResponderHCIDCode();
            remoteHCID = "";
            remoteHCIDMappingCode = "";
            if ((remoteHCIDCode == null) || remoteHCIDCode.equals("")) {
                log.warn("remoteHCIDCode is null");
                return ;
            } else {
                Set s = remoteHCIDs.entrySet();
                Iterator it = s.iterator();
                while (it.hasNext()) {
                    Map.Entry m = (Map.Entry) it.next();
                    remoteHCIDMappingCode = (String) (m.getKey());
                    if (remoteHCIDMappingCode.equals(remoteHCIDCode)) {
                        remoteHCID = (String) m.getValue();
                    }
                }
            }
            log.debug("retrieveDocuments - remoteHCID  :" + remoteHCID);
        }

        if ((docAndRepIds == null) || (docAndRepIds.size() <= 0)) {
            log.warn("doc And repository Ids null");
            return ;
        } else if ((remoteHCID == null) || remoteHCID.equals("")) {
            log.warn("remoteHCID is null");
            return ;
        } else {
            //**************************************************************
            Set s = docAndRepIds.entrySet();
            /*int patientDocumentsFromConfigNum = 0;
            if((patientDocuments != null) && (patientDocuments.size() >= 0)){
                patientDocumentsFromConfigNum = patientDocuments.size();
            }
            log.info("patientDocuments from config file - size: " + patientDocumentsFromConfigNum);
            
            /*int patientDocumentsFromDbNum = 0;
            if((s != null) && (s.size() >= 0)){
                patientDocumentsFromDbNum = s.size();
            }
            log.info("All patientDocuments from database - size: " + patientDocumentsFromDbNum);

            if(patientDocumentsFromDbNum > patientDocumentsFromConfigNum){
                patientDocumentsFromDbNum = patientDocumentsFromConfigNum;
            }
            log.info("Numer of documents to be retrieved: " + patientDocumentsFromDbNum);*/
            //**************************************************************
            Iterator it = s.iterator();
            String docId = "";
            String repId = "";
            int iter = 0;
            while (it.hasNext()) {
                Map.Entry m = (Map.Entry) it.next();
                docId = (String) m.getKey();
                repId = (String) m.getValue();
                documentRequest = new DocumentRequest();
                log.info("retrieveDocuments - iter  :" + iter);
                log.info("retrieveDocuments - remoteHCID  :" + remoteHCID);
                log.info("retrieveDocuments - DocumentUniqueId  :" + docId);
                log.info("retrieveDocuments - RepositoryUniqueId  :" + repId);
                if ((docId == null) || docId.equals("")) {
                    log.warn("docId is null");
                    return ;
                } else if ((repId == null) || repId.equals("")) {
                    log.warn("repId is null");
                    return ;
                } else {
                    //set request for only as many documents as we need to retrieve (though we got more documents form doc query)
                    //if(iter < patientDocumentsFromDbNum){
                        documentRequest.setHomeCommunityId(FormatUtil.appendPrefixForHomeCommunityId(remoteHCID));
                        documentRequest.setRepositoryUniqueId(repId);
                        documentRequest.setDocumentUniqueId(docId);
                        retrieveDocumentSetRequest.getDocumentRequest().add(documentRequest);
                    //}
                }
                //iter++;
            }
        }
        log.info(" prepareResponderScenarioData() End: " );
    }

    /**
     *    
     * @param fileContent
     * @return
     */
    public PatientRD getPatientInfo(String fileContent) {
    	return RDUtil.unmarshallRDConfig(fileContent);
    }
    
    private static boolean isNotEmpty(String str) {
		return (str != null && str.length() > 0);
	}
    
    private EntityDocRetrievePortType getServiceEndPoint(String endpointURL) {
    	EntityDocRetrievePortType entityDocQueryPortType248 =null;
    	try {
			entityDocQueryPortType248 = getEntityDocQueryPortTypeFromContext();
			
			log.debug("getServiceEndPoint() called ");
			
			// Use Proxy Instance as BindingProvider
			BindingProvider bp = (BindingProvider) entityDocQueryPortType248;
			
			// (Optional) Configure RequestContext with endpoint's URL
			Map<String, Object> rc = bp.getRequestContext();
			log.debug("Default Endpoint URL :::" + rc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
			rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
			log.debug("Modified Endpoint URL :::" + rc.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new WSClient248RuntimeException(ex);
		}
		return entityDocQueryPortType248 ; 
	}
    
    private EntityDocRetrievePortType getEntityDocQueryPortTypeFromContext() {
		return (EntityDocRetrievePortType)ApplicationContextProvider.getBean(ENTITY_DOC_RETRIEVE_PORT_TYPE_PROXY);
	}
}
