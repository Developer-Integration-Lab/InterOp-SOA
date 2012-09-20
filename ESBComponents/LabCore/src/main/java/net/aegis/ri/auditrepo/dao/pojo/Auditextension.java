package net.aegis.ri.auditrepo.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table auditextension</p>
 * <p></p>
 *
 * <p>Generated at Mon Oct 04 19:30:23 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Auditextension implements Serializable {

    /**
     * Attribute auditextensionId.
     */
    private Long auditextensionId;
    /**
     * Attribute auditrepository
     */
    private Auditrepository auditrepository;
    /**
     * Attribute eventOutcomeIndicator.
     */
    private String eventOutcomeIndicator;
    /**
     * Attribute requestMessage.
     */
    private byte[] requestMessage;
    /**
     * Attribute responseMessage.
     */
    private byte[] responseMessage;

    /**
     * @return auditextensionId
     */
    public Long getAuditextensionId() {
        return auditextensionId;
    }

    /**
     * @param auditextensionId new value for auditextensionId
     */
    public void setAuditextensionId(Long auditextensionId) {
        this.auditextensionId = auditextensionId;
    }

    /**
     * get auditrepository
     */
    public Auditrepository getAuditrepository() {
        return this.auditrepository;
    }

    /**
     * set auditrepository
     */
    public void setAuditrepository(Auditrepository auditrepository) {
        this.auditrepository = auditrepository;
    }

    /**
     * @return eventOutcomeIndicator
     */
    public String getEventOutcomeIndicator() {
        return eventOutcomeIndicator;
    }

    /**
     * @param eventOutcomeIndicator new value for eventOutcomeIndicator
     */
    public void setEventOutcomeIndicator(String eventOutcomeIndicator) {
        this.eventOutcomeIndicator = eventOutcomeIndicator;
    }

    /**
     * @return requestMessage
     */
    public byte[] getRequestMessage() {
        return requestMessage;
    }

    /**
     * @param requestMessage new value for requestMessage
     */
    public void setRequestMessage(byte[] requestMessage) {
        this.requestMessage = requestMessage;
    }

    /**
     * @return responseMessage
     */
    public byte[] getResponseMessage() {
        return responseMessage;
    }

    /**
     * @param responseMessage new value for responseMessage
     */
    public void setResponseMessage(byte[] responseMessage) {
        this.responseMessage = responseMessage;
    }
}
