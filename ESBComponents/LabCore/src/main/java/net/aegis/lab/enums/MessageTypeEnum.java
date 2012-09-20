/* Copyright (c) 2011 - AEGIS.net - All Rights Reserved */
package net.aegis.lab.enums;

/**
 * MessageTypeEnum
 *
 * @author Venkat Keesara
 * @since Jun 15, 2011
 * @copyright Copyright (c) 2011 - AEGIS.net - All Rights Reserved
 */
public enum MessageTypeEnum {

    MESSAGE_PAYLOAD("CDA", "CDA/Message Payload"),
    PATIENT_DISCOVERY("PD", "Patient Discovery"),
    QUERY_FOR_DOCUMENTS("QD", "Query for Documents"),
    RETRIEVE_DOCUMENTS("RD", "Retrieve Documents");
    // INSTANCE VARIABLES
    private String textId;
    private String defaultDescription;

    // CONSTRUCTING
    private MessageTypeEnum(String id, String defaultDescription) {
        setTextId(id);
        setDefaultDescription(defaultDescription);
    }

// ACCESSING
    public String getTextId() {
        return textId;
    }

    public void setTextId(String id) {
        this.textId = id;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }

    public void setDefaultDescription(String defaultDescription) {
        this.defaultDescription = defaultDescription;
    }

    // MARSHALLING
    @Override
    public String toString() {
        return getTextId();
    }

    public static MessageTypeEnum fromTextId(String id) {
        for (MessageTypeEnum anEnum : values()) {
            if (anEnum.getTextId().equalsIgnoreCase(id)) {
                return anEnum;
            }
        }
        return null;
    }
}
