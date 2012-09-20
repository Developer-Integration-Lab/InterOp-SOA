package net.aegis.lab.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table testharnessendpoint</p>
 * <p></p>
 *
 * <p>Generated at Wed Apr 14 15:59:01 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Testharnessendpoint implements Serializable {

    /**
     * Attribute testharnessendpointId.
     */
    private Integer testharnessendpointId;
    /**
     * Attribute testharnessri
     */
    private Testharnessri testharnessri;
    /**
     * Attribute messageType.
     */
    private String messageType;
    /**
     * Attribute endpoint.
     */
    private String endpoint;

    /**
     * @return testharnessendpointId
     */
    public Integer getTestharnessendpointId() {
        return testharnessendpointId;
    }

    /**
     * @param testharnessendpointId new value for testharnessendpointId
     */
    public void setTestharnessendpointId(Integer testharnessendpointId) {
        this.testharnessendpointId = testharnessendpointId;
    }

    /**
     * get testharnessri
     */
    public Testharnessri getTestharnessri() {
        return this.testharnessri;
    }

    /**
     * set testharnessri
     */
    public void setTestharnessri(Testharnessri testharnessri) {
        this.testharnessri = testharnessri;
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

    @Override
    public String toString() {
        return this.messageType+":"+this.endpoint;
    }


}
