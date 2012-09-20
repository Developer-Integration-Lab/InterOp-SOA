package net.aegis.gateway.client.connect32.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import net.aegis.gateway.client.connect32.qd.PatientQD;
import net.aegis.gateway.client.exception.WSClient32RuntimeException;
import net.aegis.labcommons.jaxb.qd.AdhocQueryRespType;
import net.aegis.labcommons.jaxb.qd.DocumentInfoType;
import net.aegis.labcommons.jaxb.qd.ExtrinObjectType;
import net.aegis.labcommons.jaxb.qd.QDEntityResponseType;
import net.aegis.labcommons.jaxb.qd.RegObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.IdentifiableType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QDUtil {
	private static final Log log = LogFactory.getLog(QDUtil.class);
	
	public static final int QD_DOC_EXO_STATUS = 0;
	public static final int QD_DOC_REP_ID = 1;
	public static final int QD_DOC_HASH = 2;
	public static final int QD_DOC_SIZE = 3;
	public static final String QUERY_TYPE_ID = "urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d";
	public static final String PATIENT_ID_SLOT_NAME = "$XDSDocumentEntryPatientId";
	public static final String CREATION_TIME_FROM_SLOT_NAME = "$XDSDocumentEntryCreationTimeFrom";
	public static final String CREATION_TIME_TO_SLOT_NAME = "$XDSDocumentEntryCreationTimeTo";
	public static final String HL7_DATE_FORMAT = "yyyyMMddHHmmss";
	public static final String SERVICE_START_TIME_FROM_SLOT_NAME = "$XDSDocumentEntryServiceStartTimeFrom";
	public static final String SERVICE_START_TIME_TO_SLOT_NAME = "$XDSDocumentEntryServiceStartTimeTo";
	public static final String SERVICE_STOP_TIME_FROM_SLOT_NAME = "$XDSDocumentEntryServiceStopTimeFrom";
	public static final String SERVICE_STOP_TIME_TO_SLOT_NAME = "$XDSDocumentEntryServiceStopTimeTo";
	public static final String DOCUMENT_ENTRY_CLASS_CODE_SLOT_NAME = "$XDSDocumentEntryClassCode";
	public static final String DOCUMENT_ENTRY_STATUS_SLOT_NAME = "$XDSDocumentEntryStatus";
	public static final String ROLE_CODE = "46255001";
	public static final String ROLE_CODE_SYSTEM = "2.16.840.1.113883.6.96";
	public static final String ROLE_CODE_SYSTEM_NAME = "SNOMED_CT";
	public static final String ROLE_DISPLAY_NAME = "Pharmacist";
	public static final String PURPOSE_OF_DISCLOSURE_CODE = "TREATMENT";
	public static final String PURPOSE_OF_DISCLOSURE_CODE_SYSTEM = "2.16.840.1.113883.3.18.7.1";
	public static final String PURPOSE_OF_DISCLOSURE_CODE_SYSTEM_NAME = "nhin-purpose";
	public static final String PURPOSE_OF_DISCLOSURE_DISPLAY_NAME = "Treatment";
	public static final String REPOSITORY_UNIQUE_ID_SLOT_NAME = "repositoryUniqueId";
	public static final String HASH_SLOT_NAME = "hash";
	public static final String SIZE_SLOT_NAME = "size";
	public static final String EXTERNAL_IDENTIFIER_IDENTIFICATION_SCHEME = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";
	public static final String EXTERNAL_IDENTIFIER_PATIENTID_SCHEME = "urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427";
	
	public static final String ENTITY_DOC_QUERY_PORT_TYPE_PROXY = "entityDocQueryPortType32Proxy";

	/**
	 * 
	 * @param fileContent
	 * @return
	 */
	
	public static PatientQD unmarshallQDConfig(String fileContent) {
		PatientQD patient = new PatientQD();
	    try {
	    	Class clazz = net.aegis.gateway.client.connect32.qd.ObjectFactory.class;
			ClassLoader cl = clazz.getClassLoader();
	        JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getPackage().getName(),cl);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        StringReader sr = new StringReader(fileContent);
	        
	        Object o = unmarshaller.unmarshal(sr);
	        patient = (PatientQD) o;
	    } catch (Exception ex) {
	        FormatUtil.log.error(ex.getMessage());
	        ex.printStackTrace();
	        throw new WSClient32RuntimeException(ex);
	    }
	    return patient;
	}
	
	 public static QDEntityResponseType getQDEntityResponseFromAdhocQueryResponse(oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse result) {

		 QDEntityResponseType entityResponse  = new QDEntityResponseType(); 
		 RegObjectListType registryObjectListInternal = null;
		 AdhocQueryRespType adhocQueryResponseTypeInternal = new AdhocQueryRespType();
		 entityResponse.setAdhocQueryResp(adhocQueryResponseTypeInternal);
		 List<ExternalIdentifierType> identifierItems = new ArrayList<ExternalIdentifierType>();
		
	        if (result != null &&
	                result.getRegistryObjectList() != null &&
	                result.getRegistryObjectList().getIdentifiable() != null &&
	                result.getRegistryObjectList().getIdentifiable().size() > 0) {
	        	registryObjectListInternal = new RegObjectListType();

	            List<JAXBElement<? extends IdentifiableType>> extrinsicObjects = result.getRegistryObjectList().getIdentifiable();
	            ExtrinsicObjectType extrinsicObject = new ExtrinsicObjectType();
	            ExtrinObjectType extrinsicObjectInternal = null;
	            String docId = "";  String patId = "";  String repId = ""; String hashValue =""; String sizeValue ="";
	            if (extrinsicObjects != null && extrinsicObjects.size() > 0) {
	                for (JAXBElement<? extends IdentifiableType> jaxb : extrinsicObjects) {
	                    if (jaxb.getValue() instanceof ExtrinsicObjectType) {
	                        extrinsicObject = (ExtrinsicObjectType) jaxb.getValue();

	                        if (extrinsicObject != null &&
	                                extrinsicObject.getExternalIdentifier() != null &&
	                                extrinsicObject.getExternalIdentifier().size() > 0) {
	                        	
	                        	extrinsicObjectInternal = new ExtrinObjectType();
	                        	DocumentInfoType documentInfoType =  new DocumentInfoType();
	                            // extract document id and patient id from ExternalIdentifiers //
	                        	// TODO : Need to add break in the for loop
	                        	for (ExternalIdentifierType identifierItem : extrinsicObject.getExternalIdentifier()) {
	                            	
	                                if (identifierItem != null && identifierItem.getIdentificationScheme().contentEquals(EXTERNAL_IDENTIFIER_IDENTIFICATION_SCHEME)) {
	                                	docId = identifierItem.getValue();
	                                	
	                                }else if (identifierItem != null && identifierItem.getIdentificationScheme().contentEquals(EXTERNAL_IDENTIFIER_PATIENTID_SCHEME)) {
	                                	patId = identifierItem.getValue();
	                                }
	                                
	                            }
	                        	// extract hash , size  and repo-id from Slots . Extract slot list only if document id is found 
	                        	for (SlotType1 slot : extrinsicObject.getSlot()) {
	                                if (slot != null && slot.getName().equalsIgnoreCase(REPOSITORY_UNIQUE_ID_SLOT_NAME)) {
	                                    if((slot.getValueList().getValue() != null) && (slot.getValueList().getValue().size() > 0)){
	                                         repId = slot.getValueList().getValue().get(0);
	                                    } else  repId="";
	                                } else if (slot != null && slot.getName().equalsIgnoreCase(HASH_SLOT_NAME)) {
	                                    if((slot.getValueList().getValue() != null) && (slot.getValueList().getValue().size() > 0)){
	                                        hashValue = slot.getValueList().getValue().get(0);
	                                    } else hashValue = "";
	                                } else if (slot != null && slot.getName().equalsIgnoreCase(SIZE_SLOT_NAME)) {
	                                    if((slot.getValueList().getValue() != null) && (slot.getValueList().getValue().size() > 0)){
	                                        sizeValue = slot.getValueList().getValue().get(0);
	                                    } else sizeValue = "";
	                                }
	                            }
	                        	
	                        	documentInfoType.setDocumentUniqueId(docId);
	                        	documentInfoType.setPatientId(patId);
	                        	documentInfoType.setRepositoryUniqueId(repId);
	                        	documentInfoType.setHash(hashValue);
	                        	documentInfoType.setSize(sizeValue);
	                        	
	                        	extrinsicObjectInternal.setDocumentInfo(documentInfoType);
	                        }
	                    }
	                    
	                    registryObjectListInternal.getExtrinObject().add(extrinsicObjectInternal);
	                }
	                
	            }
	        }
	        
	        adhocQueryResponseTypeInternal.setRegObjectList(registryObjectListInternal);
	        
	        return entityResponse;
	    }
}
