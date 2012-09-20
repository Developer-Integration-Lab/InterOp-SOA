package net.aegis.ri.docrepository.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.genericdao.GenericDao;
import net.aegis.ri.docrepository.dao.pojo.Document;

/**
 * <p>Hibernate DAO layer for Documents</p>
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface DocumentDAO extends GenericDao<Document, Integer> {

    /*
     * TODO : Add specific businesses daos here.
     * These methods will be overwrited if you re-generate this interface.
     * You might want to extend this interface and to change the dao factory to return
     * an instance of the new implemenation in buildDocumentDAO()
     */
    /**
     * Find Document by criteria.
     * If a parameter is null it is not used in the query.
     */
    public List<Document> findByCriteria(String documentId, String documentUniqueId, String documentTitle, String authorPerson, String authorInstitution, String authorRole, String authorSpecialty, String availabilityStatus, String classCode, String classCodeScheme, String classCodeDisplayName, String confidentialityCode, String confidentialityCodeScheme, String confidentialityCodeDisplayName, Timestamp creationTime, String formatCode, String formatCodeScheme, String formatCodeDisplayName, String patientId, Timestamp serviceStartTime, Timestamp serviceStopTime, String status, String comments, String hash, String facilityCode, String facilityCodeScheme, String facilityCodeDisplayName, String intendedRecipientPerson, String intendedRecipientOrganization, String languageCode, String legalAuthenticator, String mimeType, String parentDocumentId, String parentDocumentRelationship, String practiceSetting, String practiceSettingScheme, String practiceSettingDisplayName, Integer size, String sourcePatientId, String pid3, String pid5, String pid7, String pid8, String pid11, String typeCode, String typeCodeScheme, String typeCodeDisplayName, String documentUri, Integer persistent);

    /**
     * Find Document by criteria.
     */
    public List<Document> findByCriteria(Map criterias);

    /**
     * Find Document by documentUniqueId
     */
    public List<Document> findByDocumentUniqueId(String documentUniqueId);

    /**
     * Find Document by documentTitle
     */
    public List<Document> findByDocumentTitle(String documentTitle);

    /**
     * Find Document by authorPerson
     */
    public List<Document> findByAuthorPerson(String authorPerson);

    /**
     * Find Document by authorInstitution
     */
    public List<Document> findByAuthorInstitution(String authorInstitution);

    /**
     * Find Document by authorRole
     */
    public List<Document> findByAuthorRole(String authorRole);

    /**
     * Find Document by authorSpecialty
     */
    public List<Document> findByAuthorSpecialty(String authorSpecialty);

    /**
     * Find Document by availabilityStatus
     */
    public List<Document> findByAvailabilityStatus(String availabilityStatus);

    /**
     * Find Document by classCode
     */
    public List<Document> findByClassCode(String classCode);

    /**
     * Find Document by classCodeScheme
     */
    public List<Document> findByClassCodeScheme(String classCodeScheme);

    /**
     * Find Document by classCodeDisplayName
     */
    public List<Document> findByClassCodeDisplayName(String classCodeDisplayName);

    /**
     * Find Document by confidentialityCode
     */
    public List<Document> findByConfidentialityCode(String confidentialityCode);

    /**
     * Find Document by confidentialityCodeScheme
     */
    public List<Document> findByConfidentialityCodeScheme(String confidentialityCodeScheme);

    /**
     * Find Document by confidentialityCodeDisplayName
     */
    public List<Document> findByConfidentialityCodeDisplayName(String confidentialityCodeDisplayName);

    /**
     * Find Document by creationTime
     */
    public List<Document> findByCreationTime(Timestamp creationTime);

    /**
     * Find Document by formatCode
     */
    public List<Document> findByFormatCode(String formatCode);

    /**
     * Find Document by formatCodeScheme
     */
    public List<Document> findByFormatCodeScheme(String formatCodeScheme);

    /**
     * Find Document by formatCodeDisplayName
     */
    public List<Document> findByFormatCodeDisplayName(String formatCodeDisplayName);

    /**
     * Find Document by patientId
     */
    public List<Document> findByPatientId(String patientId);

    /**
     * Find Document by serviceStartTime
     */
    public List<Document> findByServiceStartTime(Timestamp serviceStartTime);

    /**
     * Find Document by serviceStopTime
     */
    public List<Document> findByServiceStopTime(Timestamp serviceStopTime);

    /**
     * Find Document by status
     */
    public List<Document> findByStatus(String status);

    /**
     * Find Document by comments
     */
    public List<Document> findByComments(String comments);

    /**
     * Find Document by hash
     */
    public List<Document> findByHash(String hash);

    /**
     * Find Document by facilityCode
     */
    public List<Document> findByFacilityCode(String facilityCode);

    /**
     * Find Document by facilityCodeScheme
     */
    public List<Document> findByFacilityCodeScheme(String facilityCodeScheme);

    /**
     * Find Document by facilityCodeDisplayName
     */
    public List<Document> findByFacilityCodeDisplayName(String facilityCodeDisplayName);

    /**
     * Find Document by intendedRecipientPerson
     */
    public List<Document> findByIntendedRecipientPerson(String intendedRecipientPerson);

    /**
     * Find Document by intendedRecipientOrganization
     */
    public List<Document> findByIntendedRecipientOrganization(String intendedRecipientOrganization);

    /**
     * Find Document by languageCode
     */
    public List<Document> findByLanguageCode(String languageCode);

    /**
     * Find Document by legalAuthenticator
     */
    public List<Document> findByLegalAuthenticator(String legalAuthenticator);

    /**
     * Find Document by mimeType
     */
    public List<Document> findByMimeType(String mimeType);

    /**
     * Find Document by parentDocumentId
     */
    public List<Document> findByParentDocumentId(String parentDocumentId);

    /**
     * Find Document by parentDocumentRelationship
     */
    public List<Document> findByParentDocumentRelationship(String parentDocumentRelationship);

    /**
     * Find Document by practiceSetting
     */
    public List<Document> findByPracticeSetting(String practiceSetting);

    /**
     * Find Document by practiceSettingScheme
     */
    public List<Document> findByPracticeSettingScheme(String practiceSettingScheme);

    /**
     * Find Document by practiceSettingDisplayName
     */
    public List<Document> findByPracticeSettingDisplayName(String practiceSettingDisplayName);

    /**
     * Find Document by size
     */
    public List<Document> findBySize(Integer size);

    /**
     * Find Document by sourcePatientId
     */
    public List<Document> findBySourcePatientId(String sourcePatientId);

    /**
     * Find Document by pid3
     */
    public List<Document> findByPid3(String pid3);

    /**
     * Find Document by pid5
     */
    public List<Document> findByPid5(String pid5);

    /**
     * Find Document by pid7
     */
    public List<Document> findByPid7(String pid7);

    /**
     * Find Document by pid8
     */
    public List<Document> findByPid8(String pid8);

    /**
     * Find Document by pid11
     */
    public List<Document> findByPid11(String pid11);

    /**
     * Find Document by typeCode
     */
    public List<Document> findByTypeCode(String typeCode);

    /**
     * Find Document by typeCodeScheme
     */
    public List<Document> findByTypeCodeScheme(String typeCodeScheme);

    /**
     * Find Document by typeCodeDisplayName
     */
    public List<Document> findByTypeCodeDisplayName(String typeCodeDisplayName);

    /**
     * Find Document by documentUri
     */
    public List<Document> findByDocumentUri(String documentUri);

    /**
     * Find Document by rawData
     */
    public List<Document> findByRawData(byte[] rawData);

    /**
     * Find Document by persistent
     */
    public List<Document> findByPersistent(Integer persistent);

    /**
     * Find All Documents
     */
    public List<Document> findAllDocRepo();
}
