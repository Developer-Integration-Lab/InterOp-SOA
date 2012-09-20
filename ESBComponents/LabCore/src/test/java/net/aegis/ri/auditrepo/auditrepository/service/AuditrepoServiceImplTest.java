package net.aegis.ri.auditrepo.auditrepository.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Abhay.Bakshi
 */

/*
     mysql> desc auditrepository;
    +---------------------------+--------------+------+-----+---------+----------------+
    | Field                     | Type         | Null | Key | Default | Extra          |
    +---------------------------+--------------+------+-----+---------+----------------+
    | id                        | bigint(20)   | NO   | PRI | NULL    | auto_increment |
    | timestamp                 | datetime     | YES  |     | NULL    |                |
    | eventId                   | bigint(20)   | NO   |     | NULL    |                |
    | userId                    | varchar(100) | YES  |     | NULL    |                |
    | participationTypeCode     | smallint(6)  | YES  |     | NULL    |                |
    | participationTypeCodeRole | smallint(6)  | YES  |     | NULL    |                |
    | participationIDTypeCode   | varchar(100) | YES  |     | NULL    |                |
    | receiverPatientId         | varchar(100) | YES  |     | NULL    |                |
    | senderPatientId           | varchar(100) | YES  |     | NULL    |                |
    | communityId               | varchar(255) | YES  |     | NULL    |                |
    | messageType               | varchar(100) | NO   |     | NULL    |                |
    | message                   | blob         | YES  |     | NULL    |                |
    +---------------------------+--------------+------+-----+---------+----------------+
    12 rows in set (0.06 sec)
 */

public class AuditrepoServiceImplTest {

    private static final Log log = LogFactory.getLog(AuditrepoServiceImplTest.class);
    private AuditrepoService auditRepoService;

    public AuditrepoServiceImplTest() {
        log.info("AuditrepoServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("AuditrepoServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("AuditrepoServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("AuditrepoServiceImplTest.setUp() - INFO");
        auditRepoService = (AuditrepoService)ContextUtil.getRIAuditRepoAppContext().getBean("auditrepoService");
    }

    @After
    public void tearDown() {
        log.info("AuditrepoServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of saveAuditrepo method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testSaveAuditrepo() {

        log.info("AuditrepoServiceImplTest.testSaveAuditrepo(): start - INFO");

        Auditrepository testAuditRepo = null;
        Integer resultID = null;

        testAuditRepo = new Auditrepository();
        testAuditRepo.setEventId(-999L);
        testAuditRepo.setTimestamp(new Timestamp(new Date().getTime()));
        testAuditRepo.setMessageType("Nhin Test Inbound");

        resultID = auditRepoService.saveAuditrepo(testAuditRepo);
        assertTrue(("Test audit repo to be created with id="+resultID), (testAuditRepo.getId()) > 0);
        log.info("AuditrepoServiceImplTest.testSaveAuditrepo(): Test harness RI Result ID --->>" + testAuditRepo.getId());
    }

    /**
     * Test of findById method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testFindById() {

        log.info("AuditrepoServiceImplTest.testFindById(): start - INFO");
        Integer id = 42;                       // a random num chosen for reading from DB
        Auditrepository testAuditRepo = null;

        testAuditRepo = auditRepoService.findById(id);
        assertNotNull(testAuditRepo);
        log.info("AuditrepoServiceImplTest.testFindById(): Audit Repo Receiver Patient Id --->>" + testAuditRepo.getReceiverPatientId());
    }

    
    /**
     * Test of deleteAuditrepo method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testDeleteAuditrepo() {

        log.info("AuditrepoServiceImplTest.testDeleteAuditrepo(): start - INFO");

        Auditrepository testAuditRepo = null;
        Integer resultID = null;

        testAuditRepo = new Auditrepository();
        testAuditRepo.setEventId(-999L);
        testAuditRepo.setTimestamp(new Timestamp(new Date().getTime()));
        testAuditRepo.setMessageType("Nhin Test Inbound");

        resultID = auditRepoService.saveAuditrepo(testAuditRepo);
        assertTrue(("Test audit repo to be created with id="+resultID), (testAuditRepo.getId()) > 0);
        log.info("AuditrepoServiceImplTest.testDeleteAuditrepo(): Test harness RI Result ID --->>" + testAuditRepo.getId());

        auditRepoService.deleteAuditrepo(resultID);
        log.info("AuditrepoServiceImplTest.testDeleteAuditrepo(): end - INFO");
    }

    /**
     * Test of getCurrentTimeOnRI method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testGetCurrentTimeOnRI() throws Exception {
        log.info("AuditrepoServiceImplTest.testGetCurrentTimeOnRI(): start - INFO");

        java.sql.Timestamp objTS = auditRepoService.getCurrentTimeOnRI(1);
        assertNotNull(objTS);

        log.info("AuditrepoServiceImplTest.testGetCurrentTimeOnRI(): objTS.toString()=" + objTS.toString());
    }

    /**
     * Test of getAuditrepoInfoByUserId method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testGetAuditrepoInfoByUserId() {
        /*
        System.out.println("getAuditrepoInfoByUserId");
        String userId = "";
        AuditrepoServiceImpl instance = new AuditrepoServiceImpl();
        List expResult = null;
        List result = instance.getAuditrepoInfoByUserId(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of findAll method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testFindAll() {
        /*
        System.out.println("findAll");
        int id = 0;
        AuditrepoServiceImpl instance = new AuditrepoServiceImpl();
        List expResult = null;
        List result = instance.findAll(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of findAllAuditRepo method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testFindAllAuditRepo() {
        /*
        System.out.println("findAllAuditRepo");
        AuditrepoServiceImpl instance = new AuditrepoServiceImpl();
        List expResult = null;
        List result = instance.findAllAuditRepo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of cleanAuditRepo method, of class AuditrepoServiceImpl.
     */
//    @Test
    public void testCleanAuditRepo() {

        //
        // NOT TO IMPLEMENT UNLESS MOCK OBJECTS INTRODUCED
        //

        /*
        System.out.println("cleanAuditRepo");
        AuditrepoServiceImpl instance = new AuditrepoServiceImpl();
        instance.cleanAuditRepo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}