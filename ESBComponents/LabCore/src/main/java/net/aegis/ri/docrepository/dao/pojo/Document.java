package net.aegis.ri.docrepository.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import net.aegis.lab.util.Format;

/**
 * <p>Pojo mapping table document</p>
 * <p></p>
 *
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Document implements Serializable {

    /**
     * Attribute documentid.
     */
    private Integer documentid;
    /**
     * Attribute documentUniqueId.
     */
    private String documentUniqueId;
    /**
     * Attribute documentTitle.
     */
    private String documentTitle;
    /**
     * Attribute authorPerson.
     */
    private String authorPerson;
    /**
     * Attribute authorInstitution.
     */
    private String authorInstitution;
    /**
     * Attribute authorRole.
     */
    private String authorRole;
    /**
     * Attribute authorSpecialty.
     */
    private String authorSpecialty;
    /**
     * Attribute availabilityStatus.
     */
    private String availabilityStatus;
    /**
     * Attribute classCode.
     */
    private String classCode;
    /**
     * Attribute classCodeScheme.
     */
    private String classCodeScheme;
    /**
     * Attribute classCodeDisplayName.
     */
    private String classCodeDisplayName;
    /**
     * Attribute confidentialityCode.
     */
    private String confidentialityCode;
    /**
     * Attribute confidentialityCodeScheme.
     */
    private String confidentialityCodeScheme;
    /**
     * Attribute confidentialityCodeDisplayName.
     */
    private String confidentialityCodeDisplayName;
    /**
     * Attribute creationTime.
     */
    private Timestamp creationTime;
    /**
     * Attribute formatCode.
     */
    private String formatCode;
    /**
     * Attribute formatCodeScheme.
     */
    private String formatCodeScheme;
    /**
     * Attribute formatCodeDisplayName.
     */
    private String formatCodeDisplayName;
    /**
     * Attribute patientId.
     */
    private String patientId;
    /**
     * Attribute serviceStartTime.
     */
    private Timestamp serviceStartTime;
    /**
     * Attribute serviceStopTime.
     */
    private Timestamp serviceStopTime;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute comments.
     */
    private String comments;
    /**
     * Attribute hash.
     */
    private String hash;
    /**
     * Attribute facilityCode.
     */
    private String facilityCode;
    /**
     * Attribute facilityCodeScheme.
     */
    private String facilityCodeScheme;
    /**
     * Attribute facilityCodeDisplayName.
     */
    private String facilityCodeDisplayName;
    /**
     * Attribute intendedRecipientPerson.
     */
    private String intendedRecipientPerson;
    /**
     * Attribute intendedRecipientOrganization.
     */
    private String intendedRecipientOrganization;
    /**
     * Attribute languageCode.
     */
    private String languageCode;
    /**
     * Attribute legalAuthenticator.
     */
    private String legalAuthenticator;
    /**
     * Attribute mimeType.
     */
    private String mimeType;
    /**
     * Attribute parentDocumentId.
     */
    private String parentDocumentId;
    /**
     * Attribute parentDocumentRelationship.
     */
    private String parentDocumentRelationship;
    /**
     * Attribute practiceSetting.
     */
    private String practiceSetting;
    /**
     * Attribute practiceSettingScheme.
     */
    private String practiceSettingScheme;
    /**
     * Attribute practiceSettingDisplayName.
     */
    private String practiceSettingDisplayName;
    /**
     * Attribute size.
     */
    private Integer size;
    /**
     * Attribute sourcePatientId.
     */
    private String sourcePatientId;
    /**
     * Attribute pid3.
     */
    private String pid3;
    /**
     * Attribute pid5.
     */
    private String pid5;
    /**
     * Attribute pid7.
     */
    private String pid7;
    /**
     * Attribute pid8.
     */
    private String pid8;
    /**
     * Attribute pid11.
     */
    private String pid11;
    /**
     * Attribute typeCode.
     */
    private String typeCode;
    /**
     * Attribute typeCodeScheme.
     */
    private String typeCodeScheme;
    /**
     * Attribute typeCodeDisplayName.
     */
    private String typeCodeDisplayName;
    /**
     * Attribute documentUri.
     */
    private String documentUri;
    /**
     * Attribute rawData.
     */
    private byte[] rawData;
    /**
     * Attribute persistent.
     */
    private Integer persistent;

    // Helper properties
    private String creationTimeStr;

    /**
     * @return documentid
     */
    public Integer getDocumentid() {
        return documentid;
    }

    /**
     * @param documentid new value for documentid
     */
    public void setDocumentid(Integer documentid) {
        this.documentid = documentid;
    }

    /**
     * @return documentUniqueId
     */
    public String getDocumentUniqueId() {
        return documentUniqueId;
    }

    /**
     * @param documentUniqueId new value for documentUniqueId
     */
    public void setDocumentUniqueId(String documentUniqueId) {
        this.documentUniqueId = documentUniqueId;
    }

    /**
     * @return documentTitle
     */
    public String getDocumentTitle() {
        return documentTitle;
    }

    /**
     * @param documentTitle new value for documentTitle
     */
    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    /**
     * @return authorPerson
     */
    public String getAuthorPerson() {
        return authorPerson;
    }

    /**
     * @param authorPerson new value for authorPerson
     */
    public void setAuthorPerson(String authorPerson) {
        this.authorPerson = authorPerson;
    }

    /**
     * @return authorInstitution
     */
    public String getAuthorInstitution() {
        return authorInstitution;
    }

    /**
     * @param authorInstitution new value for authorInstitution
     */
    public void setAuthorInstitution(String authorInstitution) {
        this.authorInstitution = authorInstitution;
    }

    /**
     * @return authorRole
     */
    public String getAuthorRole() {
        return authorRole;
    }

    /**
     * @param authorRole new value for authorRole
     */
    public void setAuthorRole(String authorRole) {
        this.authorRole = authorRole;
    }

    /**
     * @return authorSpecialty
     */
    public String getAuthorSpecialty() {
        return authorSpecialty;
    }

    /**
     * @param authorSpecialty new value for authorSpecialty
     */
    public void setAuthorSpecialty(String authorSpecialty) {
        this.authorSpecialty = authorSpecialty;
    }

    /**
     * @return availabilityStatus
     */
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    /**
     * @param availabilityStatus new value for availabilityStatus
     */
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    /**
     * @return classCode
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * @param classCode new value for classCode
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    /**
     * @return classCodeScheme
     */
    public String getClassCodeScheme() {
        return classCodeScheme;
    }

    /**
     * @param classCodeScheme new value for classCodeScheme
     */
    public void setClassCodeScheme(String classCodeScheme) {
        this.classCodeScheme = classCodeScheme;
    }

    /**
     * @return classCodeDisplayName
     */
    public String getClassCodeDisplayName() {
        return classCodeDisplayName;
    }

    /**
     * @param classCodeDisplayName new value for classCodeDisplayName
     */
    public void setClassCodeDisplayName(String classCodeDisplayName) {
        this.classCodeDisplayName = classCodeDisplayName;
    }

    /**
     * @return confidentialityCode
     */
    public String getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * @param confidentialityCode new value for confidentialityCode
     */
    public void setConfidentialityCode(String confidentialityCode) {
        this.confidentialityCode = confidentialityCode;
    }

    /**
     * @return confidentialityCodeScheme
     */
    public String getConfidentialityCodeScheme() {
        return confidentialityCodeScheme;
    }

    /**
     * @param confidentialityCodeScheme new value for confidentialityCodeScheme
     */
    public void setConfidentialityCodeScheme(String confidentialityCodeScheme) {
        this.confidentialityCodeScheme = confidentialityCodeScheme;
    }

    /**
     * @return confidentialityCodeDisplayName
     */
    public String getConfidentialityCodeDisplayName() {
        return confidentialityCodeDisplayName;
    }

    /**
     * @param confidentialityCodeDisplayName new value for confidentialityCodeDisplayName
     */
    public void setConfidentialityCodeDisplayName(String confidentialityCodeDisplayName) {
        this.confidentialityCodeDisplayName = confidentialityCodeDisplayName;
    }

    /**
     * @return creationTime
     */
    public Timestamp getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime new value for creationTime
     */
    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return formatCode
     */
    public String getFormatCode() {
        return formatCode;
    }

    /**
     * @param formatCode new value for formatCode
     */
    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    /**
     * @return formatCodeScheme
     */
    public String getFormatCodeScheme() {
        return formatCodeScheme;
    }

    /**
     * @param formatCodeScheme new value for formatCodeScheme
     */
    public void setFormatCodeScheme(String formatCodeScheme) {
        this.formatCodeScheme = formatCodeScheme;
    }

    /**
     * @return formatCodeDisplayName
     */
    public String getFormatCodeDisplayName() {
        return formatCodeDisplayName;
    }

    /**
     * @param formatCodeDisplayName new value for formatCodeDisplayName
     */
    public void setFormatCodeDisplayName(String formatCodeDisplayName) {
        this.formatCodeDisplayName = formatCodeDisplayName;
    }

    /**
     * @return patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId new value for patientId
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return serviceStartTime
     */
    public Timestamp getServiceStartTime() {
        return serviceStartTime;
    }

    /**
     * @param serviceStartTime new value for serviceStartTime
     */
    public void setServiceStartTime(Timestamp serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    /**
     * @return serviceStopTime
     */
    public Timestamp getServiceStopTime() {
        return serviceStopTime;
    }

    /**
     * @param serviceStopTime new value for serviceStopTime
     */
    public void setServiceStopTime(Timestamp serviceStopTime) {
        this.serviceStopTime = serviceStopTime;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status new value for status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments new value for comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash new value for hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return facilityCode
     */
    public String getFacilityCode() {
        return facilityCode;
    }

    /**
     * @param facilityCode new value for facilityCode
     */
    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    /**
     * @return facilityCodeScheme
     */
    public String getFacilityCodeScheme() {
        return facilityCodeScheme;
    }

    /**
     * @param facilityCodeScheme new value for facilityCodeScheme
     */
    public void setFacilityCodeScheme(String facilityCodeScheme) {
        this.facilityCodeScheme = facilityCodeScheme;
    }

    /**
     * @return facilityCodeDisplayName
     */
    public String getFacilityCodeDisplayName() {
        return facilityCodeDisplayName;
    }

    /**
     * @param facilityCodeDisplayName new value for facilityCodeDisplayName
     */
    public void setFacilityCodeDisplayName(String facilityCodeDisplayName) {
        this.facilityCodeDisplayName = facilityCodeDisplayName;
    }

    /**
     * @return intendedRecipientPerson
     */
    public String getIntendedRecipientPerson() {
        return intendedRecipientPerson;
    }

    /**
     * @param intendedRecipientPerson new value for intendedRecipientPerson
     */
    public void setIntendedRecipientPerson(String intendedRecipientPerson) {
        this.intendedRecipientPerson = intendedRecipientPerson;
    }

    /**
     * @return intendedRecipientOrganization
     */
    public String getIntendedRecipientOrganization() {
        return intendedRecipientOrganization;
    }

    /**
     * @param intendedRecipientOrganization new value for intendedRecipientOrganization
     */
    public void setIntendedRecipientOrganization(String intendedRecipientOrganization) {
        this.intendedRecipientOrganization = intendedRecipientOrganization;
    }

    /**
     * @return languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * @param languageCode new value for languageCode
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * @return legalAuthenticator
     */
    public String getLegalAuthenticator() {
        return legalAuthenticator;
    }

    /**
     * @param legalAuthenticator new value for legalAuthenticator
     */
    public void setLegalAuthenticator(String legalAuthenticator) {
        this.legalAuthenticator = legalAuthenticator;
    }

    /**
     * @return mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType new value for mimeType
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return parentDocumentId
     */
    public String getParentDocumentId() {
        return parentDocumentId;
    }

    /**
     * @param parentDocumentId new value for parentDocumentId
     */
    public void setParentDocumentId(String parentDocumentId) {
        this.parentDocumentId = parentDocumentId;
    }

    /**
     * @return parentDocumentRelationship
     */
    public String getParentDocumentRelationship() {
        return parentDocumentRelationship;
    }

    /**
     * @param parentDocumentRelationship new value for parentDocumentRelationship
     */
    public void setParentDocumentRelationship(String parentDocumentRelationship) {
        this.parentDocumentRelationship = parentDocumentRelationship;
    }

    /**
     * @return practiceSetting
     */
    public String getPracticeSetting() {
        return practiceSetting;
    }

    /**
     * @param practiceSetting new value for practiceSetting
     */
    public void setPracticeSetting(String practiceSetting) {
        this.practiceSetting = practiceSetting;
    }

    /**
     * @return practiceSettingScheme
     */
    public String getPracticeSettingScheme() {
        return practiceSettingScheme;
    }

    /**
     * @param practiceSettingScheme new value for practiceSettingScheme
     */
    public void setPracticeSettingScheme(String practiceSettingScheme) {
        this.practiceSettingScheme = practiceSettingScheme;
    }

    /**
     * @return practiceSettingDisplayName
     */
    public String getPracticeSettingDisplayName() {
        return practiceSettingDisplayName;
    }

    /**
     * @param practiceSettingDisplayName new value for practiceSettingDisplayName
     */
    public void setPracticeSettingDisplayName(String practiceSettingDisplayName) {
        this.practiceSettingDisplayName = practiceSettingDisplayName;
    }

    /**
     * @return size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size new value for size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return sourcePatientId
     */
    public String getSourcePatientId() {
        return sourcePatientId;
    }

    /**
     * @param sourcePatientId new value for sourcePatientId
     */
    public void setSourcePatientId(String sourcePatientId) {
        this.sourcePatientId = sourcePatientId;
    }

    /**
     * @return pid3
     */
    public String getPid3() {
        return pid3;
    }

    /**
     * @param pid3 new value for pid3
     */
    public void setPid3(String pid3) {
        this.pid3 = pid3;
    }

    /**
     * @return pid5
     */
    public String getPid5() {
        return pid5;
    }

    /**
     * @param pid5 new value for pid5
     */
    public void setPid5(String pid5) {
        this.pid5 = pid5;
    }

    /**
     * @return pid7
     */
    public String getPid7() {
        return pid7;
    }

    /**
     * @param pid7 new value for pid7
     */
    public void setPid7(String pid7) {
        this.pid7 = pid7;
    }

    /**
     * @return pid8
     */
    public String getPid8() {
        return pid8;
    }

    /**
     * @param pid8 new value for pid8
     */
    public void setPid8(String pid8) {
        this.pid8 = pid8;
    }

    /**
     * @return pid11
     */
    public String getPid11() {
        return pid11;
    }

    /**
     * @param pid11 new value for pid11
     */
    public void setPid11(String pid11) {
        this.pid11 = pid11;
    }

    /**
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode new value for typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return typeCodeScheme
     */
    public String getTypeCodeScheme() {
        return typeCodeScheme;
    }

    /**
     * @param typeCodeScheme new value for typeCodeScheme
     */
    public void setTypeCodeScheme(String typeCodeScheme) {
        this.typeCodeScheme = typeCodeScheme;
    }

    /**
     * @return typeCodeDisplayName
     */
    public String getTypeCodeDisplayName() {
        return typeCodeDisplayName;
    }

    /**
     * @param typeCodeDisplayName new value for typeCodeDisplayName
     */
    public void setTypeCodeDisplayName(String typeCodeDisplayName) {
        this.typeCodeDisplayName = typeCodeDisplayName;
    }

    /**
     * @return documentUri
     */
    public String getDocumentUri() {
        return documentUri;
    }

    /**
     * @param documentUri new value for documentUri
     */
    public void setDocumentUri(String documentUri) {
        this.documentUri = documentUri;
    }

    /**
     * @return rawData
     */
    public byte[] getRawData() {
        return rawData;
    }

    /**
     * @param rawData new value for rawData
     */
    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    /**
     * @return persistent
     */
    public Integer getPersistent() {
        return persistent;
    }

    /**
     * @param persistent new value for persistent
     */
    public void setPersistent(Integer persistent) {
        this.persistent = persistent;
    }

    // Helper properties
    public String getCreationTimeStr() {
        if (creationTime != null) {
            creationTimeStr = Format.getFormattedDate(Format.MMDDYYYYHHMMSS_DATEFORMAT, creationTime);
        }
        return creationTimeStr;
    }

    public void setCreationTimeStr(String creationTimeStr) {
        this.creationTimeStr = creationTimeStr;

        if (creationTimeStr != null) {
            this.creationTime = Format.getTimestampInstance(Format.MMDDYYYYHHMMSS_DATEFORMAT, creationTimeStr);
        }
        else {
            this.creationTime = null;
        }
    }

}
