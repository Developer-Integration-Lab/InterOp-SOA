package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Pojo mapping table attachment</p>
 * <p></p>
 *
 * <p>Generated at Fri Apr 02 19:40:03 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Attachment implements Serializable {

    /**
     * Attribute attachmentId.
     */
    private Integer attachmentId;
    /**
     * Attribute caseresult
     */
    private Caseresult caseresult;
    /**
     * Attribute filename.
     */
    private String filename;
    /**
     * Attribute description.
     */
    private String description;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute contentType.
     */
    private String contentType;
    /**
     * Attribute createtime.
     */
    private Timestamp createtime;
    /**
     * Attribute createuser.
     */
    private String createuser;
    /**
     * Attribute changedtime.
     */
    private Timestamp changedtime;
    /**
     * Attribute changeduser.
     */
    private String changeduser;
    /**
     * Attribute content.
     */
    private byte[] content;
    /**
     * Attrib Serviceset. Story 42.
     */
    private Servicesetexecution serviceSetExecution;


    /**
     * @return attachmentId
     */
    public Integer getAttachmentId() {
        return attachmentId;
    }

    /**
     * @param attachmentId new value for attachmentId
     */
    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    /**
     * get caseresult
     */
    public Caseresult getCaseresult() {
        return this.caseresult;
    }

    /**
     * set caseresult
     */
    public void setCaseresult(Caseresult caseresult) {
        this.caseresult = caseresult;
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename new value for filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description new value for description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status new value for status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType new value for contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return createtime
     */
    public Timestamp getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime new value for createtime
     */
    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    /**
     * @return createuser
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser new value for createuser
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    /**
     * @return changedtime
     */
    public Timestamp getChangedtime() {
        return changedtime;
    }

    /**
     * @param changedtime new value for changedtime
     */
    public void setChangedtime(Timestamp changedtime) {
        this.changedtime = changedtime;
    }

    /**
     * @return changeduser
     */
    public String getChangeduser() {
        return changeduser;
    }

    /**
     * @param changeduser new value for changeduser
     */
    public void setChangeduser(String changeduser) {
        this.changeduser = changeduser;
    }

    /**
     * @return content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content new value for content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }
    public Servicesetexecution getServiceSetExecution() {
        return serviceSetExecution;
    }

    public void setServiceSetExecution(Servicesetexecution serviceSetExecution) {
        this.serviceSetExecution = serviceSetExecution;
    }
 
}
