/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.util;

/**
 * 
 * Venkat.Keesara Feb 15, 2012
 */
public class MVConstants {

	public static final String MULTIPART_RELATED = "multipart/related";
	public static final String MULTIPART = "multipart/";
	public static final String APPLICATION_SOAP_XML = "application/soap+xml";
	public static final String URI_SOAP12_ENV="http://www.w3.org/2003/05/soap-envelope";
	public static final String URI_ADDRESSING="http://www.w3.org/2005/08/addressing";
	
	public static final String NS_IHE_ITI_XDS_B_2007 = "urn:ihe:iti:xds-b:2007"; 

	public static final String SOAP_ENVELOPE = "Envelope";
	public static final String MSG_TYPE_REQUEST = "REQUEST";
	public static final String MSG_TYPE_RESPONSE = "RESPONSE";
	public static final String HEADER = "Header";
	public static final String BODY = "Body";
	
	// PD Request & Response message extracting elements constants
	public static final String LIVING_SUBJECT_ID_ROOT_FOR_SSN = "2.16.840.1.113883.4.1";
	public static final String LIVING_SUBJECT_ID_SSN = "SSN";
	public static final String ORGANIZATION = "organization";
	public static final String LIVING_SUBJECT_ADMINISTRATIVE_GENDER = "livingSubjectAdministrativeGender";
	public static final String LIVING_SUBJECT_BIRTH_TIME = "livingSubjectBirthTime";
	public static final String LIVING_SUBJECT_NAME = "livingSubjectName";
	public static final String LIVING_SUBJECT_ID = "livingSubjectId";
	public static final String PATIENT_TELECOM = "patientTelecom";
	public static final String SENDER = "sender";
	public static final String RECEIVER = "receiver";
	public static final String ROOT = "root";
	public static final String VALUE = "value";
	public static final String ACTION = "Action";
	public static final String MESSAGE_ID = "MessageID";
	public static final String RELATES_TO = "RelatesTo";
	public static final String ID = "id";	
	public static final String EXTENSION = "extension";
	public static final String CODE = "code";
	public static final String FAMILY = "family";
	public static final String GIVEN = "given";
	public static final String REGISTRATIONEVENT = "registrationEvent";
	public static final String SUBJECT = "subject";
	public static final String QUERY_ACK="queryAck";
	public static final String QUERY_RESPONSE_CODE="queryResponseCode";
	
	public static final String SUBJECT1 = "subject1";
	public static final String PATIENT = "patient";
	
	public static final String QUERY_BY_PARAMETER="queryByParameter";
	public static final String PARAMETER_LIST="parameterList";
	
	// QD Request & Response message extracting elements constants
	public static final String HOME = "home";
	public static final String SLOT = "Slot";
	public static final String NAME = "name";
	public static final String STATUS = "status";
	public static final String VALUELIST = "ValueList";

	
	//Request:
	public static final String ADHOCQUERY = "AdhocQuery";
	public static final String RESPONSEOPTION = "ResponseOption";
	public static final String RETURNTYPE = "returnType";
	public static final String XDSDOCUMENTENTRYPATIENTID = "$XDSDocumentEntryPatientId";
	public static final String XDSDOCUMENTENTRYSTATUS = "$XDSDocumentEntryStatus";
	public static final String XDSDOCUMENTENTRYCLASSCODE = "$XDSDocumentEntryClassCode";
	public static final String XDSDOCUMENTENTRYCREATIONTIMEFROM = "$XDSDocumentEntryCreationTimeFrom";
	public static final String XDSDOCUMENTENTRYCREATIONTIMETO = "$XDSDocumentEntryCreationTimeTo";
	public static final String XDSDOCUMENTENTRYSERVICESTARTTIMEFROM = "$XDSDocumentEntryServiceStartTimeFrom";
	public static final String XDSDOCUMENTENTRYSERVICESTARTTIMETO = "$XDSDocumentEntryServiceStartTimeTO";
	public static final String XDSDOCUMENTENTRYSERVICESTOPTIMEFROM = "$XDSDocumentEntryServiceStopTimeFrom";
	public static final String XDSDOCUMENTENTRYSERVICESTOPTIMETO = "$XDSDocumentEntryServiceStopTimeTo";
		
	//Response:
	public static final String REGISTRYOBJECTLIST = "RegistryObjectList";
	public static final String EXTRINSICOBJECT = "ExtrinsicObject";
	public static final String HASH = "hash";
	public static final String SIZE = "size";
	public static final String SOURCEPATIENTID = "sourcePatientId";
	public static final String URI = "URI";
	public static final String REPOSITORYUNIQUEID = "repositoryUniqueId";
	public static final String CLASSIFICATION = "Classification";
	public static final String CLASSIFICATIONSCHEME = "classificationScheme";
	public static final String NODEREPRESENTATION = "nodeRepresentation";	
	public static final String CLASSCODESCHEME = "urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a";	
	public static final String EXTERNALIDENTIFIER = "ExternalIdentifier";
	public static final String IDENTIFICATIONSCHEME = "identificationScheme";
	public static final String DOCUNIQUEID_IDENTIFICATIONSCHEME = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";
	public static final String PATIENTID_IDENTIFICATIONSCHEME = "urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427";
	
	//Body root tags
	public static final String PD_REQUEST_BODY_ROOT_TAG = "PRPA_IN201305UV02";
	public static final String PD_RESPONSE_BODY_ROOT_TAG = "PRPA_IN201306UV02";
	
	public static final String QD_REQUEST_BODY_ROOT_TAG = "AdhocQueryRequest";
	public static final String QD_RESPONSE_BODY_ROOT_TAG = "AdhocQueryResponse";
	
	public static final String RD_REQUEST_BODY_ROOT_TAG = "RetrieveDocumentSetRequest";
	public static final String RD_RESPONSE_BODY_ROOT_TAG = "RetrieveDocumentSetResponse";
	
	//RDRequest
	public static final String RD_DOCUMENTSET_REQUEST = "RetrieveDocumentSetRequest";
	public static final String RD_DOCUMENT_REQUEST = "DocumentRequest";
	public static final String RD_HCID = "HomeCommunityId";
	public static final String RD_REPO_ID = "RepositoryUniqueId";
	public static final String RD_DOCUMENT_ID = "DocumentUniqueId";
	public static final String RD_MIME_TYPE = "mimeType";
	public static final String RD_DOCUMENT = "Document";
	
	//RD Response
	public static final String RD_DOCUMENTSET_RESPONSE = "RetrieveDocumentSetResponse";
	public static final String RD_DOCUMENT_RESPONSE = "DocumentResponse";
	public static final String RD_REGISTRY_RESPONSE = "RegistryResponse";
	public static final String RD_REGISTRY_ERROR_LIST = "RegistryErrorList";
	public static final String RD_REGISTRY_ERROR = "RegistryError";
	public static final String RD_REGISTRY_RESPONSE_STATUS_NAME = "status";
	public static final String RD_REGISTRY_RESPONSE_STATUS_SUCCESS = "Success"; //"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success";
	public static final String RD_REGISTRY_RESPONSE_STATUS_FAILURE = "Failure"; //"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure";
	public static final String RD_REGISTRY_ERROR_ATTRIBUTE_SEVERITY = "severity";
	public static final String RD_REGISTRY_ERROR_ATTRIBUTE_LOCATION = "location";
	public static final String RD_REGISTRY_ERROR_ATTRIBUTE_CODECONTEXT = "codeContext";
	public static final String RD_REGISTRY_ERROR_ATTRIBUTE_ERRORCODDE = "errorCode";
}
