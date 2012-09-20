package net.aegis.ri.docrepository.service;

import java.util.List;

import net.aegis.lab.exception.ServiceException;
import net.aegis.ri.docrepository.dao.DocumentDAO;
import net.aegis.ri.docrepository.dao.pojo.Document;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDAO documentDAO;

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(DocumentServiceImpl.class);

    @Override
    public List<Document> getDocumentsForPatient(String patientId) {
        log.info("DocumentServiceImpl.getDocumentsForPatient() - INFO");

        List<Document> results = null;

        if (patientId != null) {
            results = documentDAO.findByPatientId(patientId);
        }
        return results;
    }

    @Override
    public List<Document> findAllDocRepo() {
        return documentDAO.findAllDocRepo();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void cleanDocRepo() {
        for(Document doc:findAllDocRepo()){
            documentDAO.delete(doc);
        }
    }

    @Override
    public Document findDocumentByDocumentUniqueId(String documentUniqueId){
        List<Document> documents = documentDAO.findByDocumentUniqueId(documentUniqueId);
        if(documents!=null && documents.size() > 0)
        {
           return  documents.get(0);
        }
        return null;
    }
}
