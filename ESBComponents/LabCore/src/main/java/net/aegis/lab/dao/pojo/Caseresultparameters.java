package net.aegis.lab.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table caseresultparameters</p>
 * <p></p>
 *
 * <p>Generated at Fri Mar 25 21:45:45 EDT 2011</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Caseresultparameters implements Serializable {

    /**
     * Attribute caseresultParamsId.
     */
    private Integer caseresultParamsId;
    /**
     * Attribute caseresult
     */
    private Caseresult caseresult;
    /**
     * Attribute paramName.
     */
    private String paramName;
    /**
     * Attribute displayParamName.
     */
    private String displayParamName;
    /**
     * Attribute paramValue.
     */
    private String paramValue;
    /**
     * Attribute required.
     */
    private String required;

    /**
     * @return caseresultParamsId
     */
    public Integer getCaseresultParamsId() {
        return caseresultParamsId;
    }

    /**
     * @param caseresultParamsId new value for caseresultParamsId
     */
    public void setCaseresultParamsId(Integer caseresultParamsId) {
        this.caseresultParamsId = caseresultParamsId;
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
     * @return paramName
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @param paramName new value for paramName
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * @return displayParamName
     */
    public String getDisplayParamName() {
        return displayParamName;
    }

    /**
     * @param displayParamName new value for displayParamName
     */
    public void setDisplayParamName(String displayParamName) {
        this.displayParamName = displayParamName;
    }

    /**
     * @return paramValue
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * @param paramValue new value for paramValue
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * @return required
     */
    public String getRequired() {
        return required;
    }

    /**
     * @param required new value for required
     */
    public void setRequired(String required) {
        this.required = required;
    }
}
