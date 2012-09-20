/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.dto;

import java.io.Serializable;

/**
 *
 * @author Sree Hari Devineni
 */
public class ApplicationpropertiesDto implements Serializable{

   
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
