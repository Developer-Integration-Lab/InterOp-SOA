package net.aegis.lab.web.action.participant;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.aegis.lab.dao.pojo.Attachment;
import net.aegis.lab.manager.AttachmentManager;
import net.aegis.lab.web.action.BaseAction;

/**
 *
 * @author ram.ghattu
 */
public class DownloadAttachment extends BaseAction{

    private static final long serialVersionUID = 1L;
    private String attachmentId;
    private String filename;
    private String contentType;    
    private InputStream fileStream;
    private Attachment attachment;
    @Override
    public String execute() throws Exception {

        log.info("DownloadAttachment.execute() - INFO"+filename);
        attachment = AttachmentManager.getInstance().getAttachmentById(Integer.parseInt(attachmentId));
        fileStream = new ByteArrayInputStream(attachment.getContent());
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

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
    
}
