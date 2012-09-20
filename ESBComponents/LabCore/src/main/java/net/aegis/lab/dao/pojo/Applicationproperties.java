package net.aegis.lab.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table applicationproperties</p>
 * <p></p>
 *
 * <p>Generated at Tue May 04 18:33:57 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Applicationproperties implements Serializable {

    /**
     * Attribute key.
     */
    private String propertykey;
    private String propertyvalue;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPropertykey() {
        return propertykey;
    }

    public void setPropertykey(String propertykey) {
        this.propertykey = propertykey;
    }

    public String getPropertyvalue() {
        return propertyvalue;
    }

    public void setPropertyvalue(String propertyvalue) {
        this.propertyvalue = propertyvalue;
    }



}
