package net.aegis.ri.docrepository.manager;

import java.util.List;

import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;
import net.aegis.ri.docrepository.dao.pojo.Document;
import net.aegis.ri.docrepository.service.DocumentService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

public class DocumentManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);

    private static DocumentManager instance;
    
    private ApplicationContext RI1DocRepositoryAppContext = ContextUtil.getRIDocRepositoryAppContext(); 
//    private DocumentService documentService = (DocumentService) ContextUtil.getRIDocRepositoryAppContext().getBean("documentService");

    private DocumentManager() {
    }

    /**
     * @return DocumentManager
     */
    public static DocumentManager getInstance() {
        if (instance == null) {
            instance = new DocumentManager();
        }
        return instance;
    }

    public List<Document> getDocumentsForPatient(String patientId) throws ServiceException {
        if (log.isErrorEnabled())
            log.error("DocumentManager.getDocumentsForPatient() - ERROR");

        if (log.isWarnEnabled())
            log.warn("DocumentManager.getDocumentsForPatient() - WARN");

        if (log.isInfoEnabled())
            log.info("DocumentManager.getDocumentsForPatient() - INFO");

        if (log.isDebugEnabled())
            log.debug("DocumentManager.getDocumentsForPatient() - DEBUG");

        List<Document> results = null;

        try {
        	if(RI1DocRepositoryAppContext!=null){
        		DocumentService documentService = (DocumentService) RI1DocRepositoryAppContext.getBean("documentService");
        		if(documentService!=null){
        			results = documentService.getDocumentsForPatient(patientId);
        		}
        	}
        }
        catch (Exception e) {
            log.error("ERROR: failure finding documents.", e);
            ServiceException se = new ServiceException("Failure finding documents", "document.for.patient.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }

}
