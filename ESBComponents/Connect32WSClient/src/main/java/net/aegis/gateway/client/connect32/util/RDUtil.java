package net.aegis.gateway.client.connect32.util;

import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType.DocumentResponse;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.aegis.gateway.client.connect32.rd.PatientRD;
import net.aegis.gateway.client.exception.WSClient32RuntimeException;
import net.aegis.labcommons.jaxb.rd.DocResponseType;
import net.aegis.labcommons.jaxb.rd.RDEntityResponseType;
import net.aegis.labcommons.jaxb.rd.RetrieveDocSetResponseType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RDUtil {

	private static final Log log = LogFactory.getLog(RDUtil.class);
	public static final String ENTITY_DOC_RETRIEVE_PORT_TYPE_PROXY = "entityDocRetrievePortType32Proxy";

	/**
	 * 
	 * @param fileContent
	 * @return
	 */
	public static PatientRD unmarshallRDConfig(String fileContent) {
		PatientRD patient = new PatientRD();
		try {
			Class clazz = net.aegis.gateway.client.connect32.rd.ObjectFactory.class;
			ClassLoader cl = clazz.getClassLoader();
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getPackage().getName(), cl);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader sr = new StringReader(fileContent);

			Object o = unmarshaller.unmarshal(sr);
			patient = (PatientRD) o;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw new WSClient32RuntimeException(ex);
		}
		return patient;
	}
	
	public static RDEntityResponseType getRDEntityResponseFromRetrieveDocSetResponse(RetrieveDocumentSetResponseType docResult){
		RDEntityResponseType entityResponseType = new RDEntityResponseType();
		RetrieveDocSetResponseType retrieveDocSetResponseType = new  RetrieveDocSetResponseType();
		DocResponseType docResponseType = null;
		if (docResult.getDocumentResponse() != null && docResult.getDocumentResponse().size() > 0) {
            log.info(" Retrieve Document returned " + docResult.getDocumentResponse().size() + " documents!");
            for (DocumentResponse documentResponse : docResult.getDocumentResponse()) {
            	docResponseType = new DocResponseType();
            	log.info("<br>Document Id: " + documentResponse.getDocumentUniqueId());
                if (documentResponse.getDocument() != null) {
                	docResponseType.setDocument(documentResponse.getDocument());
                	docResponseType.setDocumentUniqueId(documentResponse.getDocumentUniqueId());
                	docResponseType.setHomeCommunityId(documentResponse.getHomeCommunityId());
                	docResponseType.setMimeType(documentResponse.getMimeType());
                	docResponseType.setRepositoryUniqueId(documentResponse.getRepositoryUniqueId());
                } 
                retrieveDocSetResponseType.getDocResponse().add(docResponseType);
            }
            
            entityResponseType.setRetrieveDocSetResponse(retrieveDocSetResponseType);
        }
		return entityResponseType;
	}

}
