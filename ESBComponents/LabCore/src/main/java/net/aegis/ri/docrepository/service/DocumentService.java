package net.aegis.ri.docrepository.service;

import java.util.List;

import net.aegis.ri.docrepository.dao.pojo.Document;

public interface DocumentService {

    List<Document> getDocumentsForPatient(String patientId);
    List<Document> findAllDocRepo();
    public void cleanDocRepo();
    public Document findDocumentByDocumentUniqueId(String documentUniqueId);
}
