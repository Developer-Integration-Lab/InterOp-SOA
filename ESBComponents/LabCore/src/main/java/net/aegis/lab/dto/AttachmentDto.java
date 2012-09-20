package net.aegis.lab.dto;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Synopsis: This class is used as a attachment data transfer object
 * between various tiers of iterop application.
 * Refer data transfer object pattern.
 * @author ram.ghattu
 */
public class AttachmentDto implements Serializable {

    private Integer caseExecId;
    private Integer attachmentId;
    private File file;
    private String fileName;
    private String user;
    private String description;
    private String status;
    private String contentType;
    private InputStream contentStream;
    private Integer setExecutionId;



    public Integer getCaseExecId() {
        return caseExecId;
    }

    public void setCaseExecId(Integer caseExecId) {
        this.caseExecId = caseExecId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getContentStream() {
        return contentStream;
    }

    public void setContentStream(InputStream contentStream) {
        this.contentStream = contentStream;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getSetExecutionId() {
        return setExecutionId;
    }

    public void setSetExecutionId(Integer setExecutionId) {
        this.setExecutionId = setExecutionId;
    }

}
