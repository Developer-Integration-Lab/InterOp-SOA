/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.web.util;

/**
 * This utility class is used to perform the html-to-model mapping for columns that hold "Y" and "N"
 * values in database
 * 
 * @author Tareq.Nabeel
 */
public class BooleanMapper {

    public static Boolean convertYesNoToBoolean(String value) {
        return "Y".equalsIgnoreCase(value) || "Yes".equalsIgnoreCase(value);
    }

    public static String convertYesNoToBooleanString(String value) {
        return convertYesNoToBoolean(value).toString();
    }

    public static String convertBooleanToYN(boolean value) {
        return value ? "Y" : "N";
    }

    public static String convertBooleanStringToYN(String value) {
        return convertBooleanToYN(Boolean.valueOf(value));
    }
}
