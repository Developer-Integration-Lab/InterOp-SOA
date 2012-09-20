/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.labcommons.enums;


/**
 *
 * Venkat.Keesara
 * Jan 31, 2012
 */
public enum PDStatusEnum {
	
	SUCCESS_WITH_RESULTS("OK", "The query was performed and has results"),
	SUCCESS_WITHOUT_RESULTS("NF", "The query was performed, but no results were located"),
    FAILURE("QE", "An error was detected in the incoming query message");
	
    // INSTANCE VARIABLES
    private String textId;
    private String defaultDescription;

    // CONSTRUCTING
    private PDStatusEnum(String id, String defaultDescription) {
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

    public static PDStatusEnum fromTextId(String id) {
        for (PDStatusEnum anEnum : values()) {
            if (anEnum.getTextId().equalsIgnoreCase(id)) {
                return anEnum;
            }
        }
        return null;
    }
}
