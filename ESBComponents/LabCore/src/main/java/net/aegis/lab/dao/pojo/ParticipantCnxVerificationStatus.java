package net.aegis.lab.dao.pojo;

import java.io.Serializable;

import net.aegis.lab.util.LabConstants;

/**
 * <p>Pojo</p>
 * <p></p>
 *
 * <p>TODO: Map to table</p>
 * @author bhaskarlas
 *
 */
public class ParticipantCnxVerificationStatus implements Serializable {
    public static final String CNX_FAILED = "Failed";
    public static final String CNX_NOT_VERIFIED = "Not Verified";
    public static final String CNX_VERIFIED = "Verified";
    public static final String PATIENT_DISCOVERY = "Patient Discovery";
    public static final String QUERY_FOR_DOCUMENT = "Query for Document";
    public static final String RETRIEVE_DOCUMENT = "Retrieve Document";

    private Integer participantId;
    private String requestType;
    private String requestTypeCode;
    private String status;
    private String datetime;
    private String errorMessage;
    transient String classCode;
    transient String statusUFStr;
    transient String wsdl;


    public ParticipantCnxVerificationStatus() {
    }

    public ParticipantCnxVerificationStatus(String requestTypeCode, String status, String wsdl) {
        setRequestTypeCode(requestTypeCode);
        setStatus(status);
        this.datetime = "";
        this.errorMessage = "";
        this.wsdl = wsdl;
    }

    public String getLongName(String requestType) {
          if (LabConstants.PATIENTDISCOVERY.equals(requestType))
              return PATIENT_DISCOVERY;
          else if (LabConstants.LAB_DOCQUERY.equals(requestType))
              return QUERY_FOR_DOCUMENT;
          else if (LabConstants.LAB_DOCRETRIEVE.equals(requestType))
              return RETRIEVE_DOCUMENT;
          return requestType;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        if (datetime!=null)
            this.datetime = datetime;
        else
            this.datetime = "";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        if (errorMessage!=null)
            this.errorMessage = errorMessage;
        else
            this.errorMessage = "";
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
        this.requestType = getLongName(requestTypeCode);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if (LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_GOOD.equals(status))
            this.setStatusUFStr(CNX_VERIFIED);
        else if (LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_FAILED.equals(status))
            this.setStatusUFStr(CNX_FAILED);
        else if (LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_NOT.equals(status))
            this.setStatusUFStr(CNX_NOT_VERIFIED);
    }

    public String getClassCode() {
        return this.status.replace(" ", "").toLowerCase();
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getStatusUFStr() {
        return statusUFStr;
    }

    public void setStatusUFStr(String statusUFStr) {
        this.statusUFStr = statusUFStr;
    }

    public String getWsdl() {
        return wsdl;
    }

    public void setWsdl(String wsdl) {
        this.wsdl = wsdl;
    }

}
