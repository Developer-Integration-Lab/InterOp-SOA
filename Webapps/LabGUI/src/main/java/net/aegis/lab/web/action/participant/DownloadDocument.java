package net.aegis.lab.web.action.participant;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.web.action.BaseAction;

/**
 *
 * @author ram.ghattu
 */
public class DownloadDocument extends BaseAction{

    private static final long serialVersionUID = 1L;
    private String resultDocumentId;
    private String filename;
    private String contentType = "text/xml";
    private InputStream fileStream;
    private Resultdocument resultdocument;
    @Override
    public String execute() throws Exception {

        log.info("DownloadDocument.execute() - INFO"+filename);
        resultdocument = ParticipantCaseExecutionManager.getInstance().getResultDocumentById(Integer.parseInt(resultDocumentId));
        fileStream = new ByteArrayInputStream(resultdocument.getRawData());
        return SUCCESS;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getResultDocumentId() {
        return resultDocumentId;
    }

    public void setResultDocumentId(String resultDocumentId) {
        this.resultDocumentId = resultDocumentId;
    }

    public Resultdocument getResultdocument() {
        return resultdocument;
    }

    public void setResultdocument(Resultdocument resultdocument) {
        this.resultdocument = resultdocument;
    }

}
