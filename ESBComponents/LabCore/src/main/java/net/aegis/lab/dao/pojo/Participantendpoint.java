package net.aegis.lab.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table participantendpoint</p>
 * <p></p>
 *
 * <p>Generated at Wed Apr 14 15:59:00 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Participantendpoint implements Serializable {

    /**
     * Attribute participantendpointId.
     */
    private Integer participantendpointId;
    /**
     * Attribute participant
     */
    private Participant participant;
    /**
     * Attribute messageType.
     */
    private String messageType;
    /**
     * Attribute endpoint.
     */
    private String endpoint;

    /**
     * @return participantendpointId
     */
    public Integer getParticipantendpointId() {
        return participantendpointId;
    }

    /**
     * @param participantendpointId new value for participantendpointId
     */
    public void setParticipantendpointId(Integer participantendpointId) {
        this.participantendpointId = participantendpointId;
    }

    /**
     * get participant
     */
    public Participant getParticipant() {
        return this.participant;
    }

    /**
     * set participant
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    /**
     * @return messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType new value for messageType
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint new value for endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
