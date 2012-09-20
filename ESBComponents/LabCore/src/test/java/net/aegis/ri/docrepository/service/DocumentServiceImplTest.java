package net.aegis.ri.docrepository.service;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import net.aegis.ri.docrepository.dao.pojo.Document;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Abhay.Bakshi
 */

/*
    mysql> desc document;
    +--------------------------------+---------------+------+-----+---------+-------+
    | Field                          | Type          | Null | Key | Default | Extra |
    +--------------------------------+---------------+------+-----+---------+-------+
    | documentid                     | int(11)       | NO   | PRI | NULL    |       |
    | DocumentUniqueId               | varchar(64)   | NO   |     | NULL    |       |
    | DocumentTitle                  | varchar(128)  | YES  |     | NULL    |       |
    | authorPerson                   | varchar(64)   | YES  |     | NULL    |       |
    | authorInstitution              | varchar(64)   | YES  |     | NULL    |       |
    | authorRole                     | varchar(64)   | YES  |     | NULL    |       |
    | authorSpecialty                | varchar(64)   | YES  |     | NULL    |       |
    | AvailabilityStatus             | varchar(64)   | YES  |     | NULL    |       |
    | ClassCode                      | varchar(64)   | YES  |     | NULL    |       |
    | ClassCodeScheme                | varchar(64)   | YES  |     | NULL    |       |
    | ClassCodeDisplayName           | varchar(64)   | YES  |     | NULL    |       |
    | ConfidentialityCode            | varchar(64)   | YES  |     | NULL    |       |
    | ConfidentialityCodeScheme      | varchar(64)   | YES  |     | NULL    |       |
    | ConfidentialityCodeDisplayName | varchar(64)   | YES  |     | NULL    |       |
    | CreationTime                   | datetime      | YES  |     | NULL    |       |
    | FormatCode                     | varchar(64)   | YES  |     | NULL    |       |
    | FormatCodeScheme               | varchar(64)   | YES  |     | NULL    |       |
    | FormatCodeDisplayName          | varchar(64)   | YES  |     | NULL    |       |
    | PatientId                      | varchar(64)   | YES  |     | NULL    |       |
    | ServiceStartTime               | datetime      | YES  |     | NULL    |       |
    | ServiceStopTime                | datetime      | YES  |     | NULL    |       |
    | Status                         | varchar(64)   | YES  |     | NULL    |       |
    | Comments                       | varchar(256)  | YES  |     | NULL    |       |
    | Hash                           | varchar(1028) | YES  |     | NULL    |       |
    | FacilityCode                   | varchar(64)   | YES  |     | NULL    |       |
    | FacilityCodeScheme             | varchar(64)   | YES  |     | NULL    |       |
    | FacilityCodeDisplayName        | varchar(64)   | YES  |     | NULL    |       |
    | IntendedRecipientPerson        | varchar(128)  | YES  |     | NULL    |       |
    | IntendedRecipientOrganization  | varchar(128)  | YES  |     | NULL    |       |
    | LanguageCode                   | varchar(64)   | YES  |     | NULL    |       |
    | LegalAuthenticator             | varchar(128)  | YES  |     | NULL    |       |
    | MimeType                       | varchar(32)   | YES  |     | NULL    |       |
    | ParentDocumentId               | varchar(64)   | YES  |     | NULL    |       |
    | ParentDocumentRelationship     | varchar(64)   | YES  |     | NULL    |       |
    | PracticeSetting                | varchar(64)   | YES  |     | NULL    |       |
    | PracticeSettingScheme          | varchar(64)   | YES  |     | NULL    |       |
    | PracticeSettingDisplayName     | varchar(64)   | YES  |     | NULL    |       |
    | Size                           | int(11)       | YES  |     | NULL    |       |
    | SourcePatientId                | varchar(128)  | YES  |     | NULL    |       |
    | Pid3                           | varchar(128)  | YES  |     | NULL    |       |
    | Pid5                           | varchar(128)  | YES  |     | NULL    |       |
    | Pid7                           | varchar(128)  | YES  |     | NULL    |       |
    | Pid8                           | varchar(128)  | YES  |     | NULL    |       |
    | Pid11                          | varchar(128)  | YES  |     | NULL    |       |
    | TypeCode                       | varchar(64)   | YES  |     | NULL    |       |
    | TypeCodeScheme                 | varchar(64)   | YES  |     | NULL    |       |
    | TypeCodeDisplayName            | varchar(64)   | YES  |     | NULL    |       |
    | DocumentUri                    | varchar(128)  | YES  |     | NULL    |       |
    | RawData                        | longblob      | YES  |     | NULL    |       |
    | Persistent                     | int(11)       | NO   |     | NULL    |       |
    +--------------------------------+---------------+------+-----+---------+-------+
    50 rows in set (0.00 sec)
 */

public class DocumentServiceImplTest {

    private static final Log log = LogFactory.getLog(DocumentServiceImplTest.class);
    private DocumentService documentService;

    public DocumentServiceImplTest() {
        log.info("DocumentServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("DocumentServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("DocumentServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("DocumentServiceImplTest.setUp() - INFO");
        documentService = (DocumentService)ContextUtil.getRIDocRepositoryAppContext().getBean("documentService");
    }

    @After
    public void tearDown() {
        log.info("DocumentServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of getDocumentsForPatient method, of class DocumentServiceImpl.
     */
//    @Test
    public void testGetDocumentsForPatient() {

        log.info("DocumentServiceImplTest.testGetDocumentsForPatient(): start  - INFO");
        List<Document> testDocs = null;
        String testPatientID = null;


        testPatientID = "1234567";          // random patient chosen
        testDocs = documentService.getDocumentsForPatient(testPatientID);
        assertNotNull(testDocs);
        
        for(Document doc: testDocs) {
            log.info("AuditrepoServiceImplTest.testGetDocumentsForPatient(): Document Unique ID --->>" + 
                    doc.getDocumentUniqueId() + " and Document Title --->>" +
                    doc.getDocumentTitle());
        }
    }

    /**
     * Test of findAllDocRepo method, of class DocumentServiceImpl.
     */
//    @Test
    public void testFindAllDocRepo() {
        /*
        System.out.println("findAllDocRepo");
        DocumentServiceImpl instance = new DocumentServiceImpl();
        List expResult = null;
        List result = instance.findAllDocRepo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of cleanDocRepo method, of class DocumentServiceImpl.
     */
//    @Test
    public void testCleanDocRepo() {

        //
        // NOT TO IMPLEMENT UNLESS MOCK OBJECTS INTRODUCED
        //

        /*
        System.out.println("cleanDocRepo");
        DocumentServiceImpl instance = new DocumentServiceImpl();
        instance.cleanDocRepo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}