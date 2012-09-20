package net.aegis.lab.attachment.service;

import net.aegis.lab.caseresult.service.CaseResultService;
import net.aegis.lab.dao.pojo.Attachment;
import net.aegis.lab.dao.pojo.Caseresult;
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
 * mysql> desc attachment;
 *        +--------------+------------------+------+-----+---------+----------------+
 *        | Field        | Type             | Null | Key | Default | Extra          |
 *        +--------------+------------------+------+-----+---------+----------------+
 *        | attachmentId | int(11)          | NO   | PRI | NULL    | auto_increment |
 *        | resultId     | int(10) unsigned | NO   | MUL | NULL    |                |
 *        | filename     | varchar(255)     | NO   |     |         |                |
 *        | description  | varchar(2000)    | YES  |     | NULL    |                |
 *        | status       | varchar(45)      | NO   |     | ACTIVE  |                |
 *        | contentType  | varchar(45)      | NO   |     |         |                |
 *        | createtime   | datetime         | YES  |     | NULL    |                |
 *        | createuser   | varchar(45)      | NO   |     |         |                |
 *        | changedtime  | datetime         | YES  |     | NULL    |                |
 *        | changeduser  | varchar(45)      | NO   |     |         |                |
 *        | content      | longblob         | YES  |     | NULL    |                |
 *        +--------------+------------------+------+-----+---------+----------------+
 *
 * @author Abhay.Bakshi
 */
public class AttachmentServiceImplTest {

    private static final Log log = LogFactory.getLog(AttachmentServiceImplTest.class);
    private AttachmentService attachmentService;
    private CaseResultService caseResultService;

    public AttachmentServiceImplTest() {
        log.info("AttachmentServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("AttachmentServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("AttachmentServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("AttachmentServiceImplTest.setUp() - INFO");
        attachmentService = (AttachmentService)ContextUtil.getLabContext().getBean("attachmentService");
        caseResultService = (CaseResultService)ContextUtil.getLabContext().getBean("caseResultService");
    }

    @After
    public void tearDown() {
        log.info("AttachmentServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class AttachmentServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("AttachmentServiceImplTest.testCreate(): start - INFO");

        Attachment testAttachment = null;
        Integer resultID = null;
        Integer caseresultID = 1;             // at least one case result expected in DB
        Caseresult testCaseresult = null;

        testCaseresult = caseResultService.read(caseresultID);
        assertNotNull(testCaseresult);
        log.info("AttachmentServiceImplTest.testCreate(): Case result --->>" + testCaseresult.getResult());

        testAttachment = new Attachment();
        testAttachment.setCaseresult(testCaseresult);
        testAttachment.setFilename("test file name.xyz");
        testAttachment.setStatus("test status");
        testAttachment.setContentType("test xyz content type");
        testAttachment.setCreateuser("test create user");
        testAttachment.setChangeduser("test changed user");

        resultID = attachmentService.create(testAttachment);
        assertTrue(("Attachment to be created with id="+resultID), (testAttachment.getAttachmentId()) > 0);
        log.info("AttachmentServiceImplTest.testCreate(): Attachment Result ID --->>" + testAttachment.getAttachmentId());
    }

    /**
     * Test of update method, of class AttachmentServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {


        log.info("AttachmentServiceImplTest.testUpdate(): start - INFO");

        Attachment testAttachmentToCreate = null;
        Attachment testAttachmentToFind = null;
        Integer resultID = null;
        Integer caseresultID = 1;             // at least one case result expected in DB
        Caseresult testCaseresult = null;

        testCaseresult = caseResultService.read(caseresultID);
        assertNotNull(testCaseresult);
        log.info("AttachmentServiceImplTest.testUpdate(): Case result --->>" + testCaseresult.getResult());

        testAttachmentToCreate = new Attachment();
        testAttachmentToCreate.setCaseresult(testCaseresult);
        testAttachmentToCreate.setFilename("test file name.xyz");
        testAttachmentToCreate.setStatus("test status");
        testAttachmentToCreate.setContentType("test xyz content type");
        testAttachmentToCreate.setCreateuser("test create user");
        testAttachmentToCreate.setChangeduser("test changed user");

        resultID = attachmentService.create(testAttachmentToCreate);
        assertTrue(("Attachment to be created with id="+resultID), (testAttachmentToCreate.getAttachmentId()) > 0);
        log.info("AttachmentServiceImplTest.testUpdate(): Attachment Result ID --->>" + testAttachmentToCreate.getAttachmentId());

        // change a parameter in order to update the scenario execution created
        testAttachmentToCreate.setStatus("Updated test status");
        attachmentService.update(testAttachmentToCreate);

        // find the updated scenario execution record and display changed information
        testAttachmentToFind = attachmentService.read(resultID);
        log.info("AttachmentServiceImplTest.testUpdate(): Changed Status --->>" + testAttachmentToFind.getStatus());
    }


    /**
     * Test of delete method, of class AttachmentServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("AttachmentServiceImplTest.testDelete(): start - INFO");

        Attachment testAttachmentToCreate = null;
        Integer resultID = null;
        Integer caseresultID = 1;             // at least one case result expected in DB
        Caseresult testCaseresult = null;

        testCaseresult = caseResultService.read(caseresultID);
        assertNotNull(testCaseresult);
        log.info("AttachmentServiceImplTest.testDelete(): Case result --->>" + testCaseresult.getResult());

        testAttachmentToCreate = new Attachment();
        testAttachmentToCreate.setCaseresult(testCaseresult);
        testAttachmentToCreate.setFilename("test file name.xyz");
        testAttachmentToCreate.setStatus("test status");
        testAttachmentToCreate.setContentType("test xyz content type");
        testAttachmentToCreate.setCreateuser("test create user");
        testAttachmentToCreate.setChangeduser("test changed user");

        resultID = attachmentService.create(testAttachmentToCreate);
        assertTrue(("Attachment to be created with id="+resultID), (testAttachmentToCreate.getAttachmentId()) > 0);
        log.info("AttachmentServiceImplTest.testDelete(): Attachment Result ID --->>" + testAttachmentToCreate.getAttachmentId());

        attachmentService.delete(testAttachmentToCreate);
        log.info("AttachmentServiceImplTest.testDelete(): end - INFO");
    }


    /**
     * Test of deleteById method, of class AttachmentServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("AttachmentServiceImplTest.testDeleteById(): start - INFO");

        Attachment testAttachmentToCreate = null;
        Integer resultID = null;
        Integer caseresultID = 1;             // at least one case result expected in DB
        Caseresult testCaseresult = null;

        testCaseresult = caseResultService.read(caseresultID);
        assertNotNull(testCaseresult);
        log.info("AttachmentServiceImplTest.testDeleteById(): Case result --->>" + testCaseresult.getResult());

        testAttachmentToCreate = new Attachment();
        testAttachmentToCreate.setCaseresult(testCaseresult);
        testAttachmentToCreate.setFilename("test file name.xyz");
        testAttachmentToCreate.setStatus("test status");
        testAttachmentToCreate.setContentType("test xyz content type");
        testAttachmentToCreate.setCreateuser("test create user");
        testAttachmentToCreate.setChangeduser("test changed user");

        resultID = attachmentService.create(testAttachmentToCreate);
        assertTrue(("Attachment to be created with id="+resultID), (testAttachmentToCreate.getAttachmentId()) > 0);
        log.info("AttachmentServiceImplTest.testDeleteById(): Attachment Result ID --->>" + testAttachmentToCreate.getAttachmentId());

        attachmentService.deleteById(resultID);
        log.info("AttachmentServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of read method, of class AttachmentServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {


        log.info("AttachmentServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Attachment testAttachment = null;

        testAttachment = attachmentService.read(id);
        assertNotNull(testAttachment);

        log.info("AttachmentServiceImplTest.testRead(): Attachment ID --->>" + testAttachment.getAttachmentId() +
                    ", and Attachment status --->>" + testAttachment.getStatus());
    }



    /**
     * Test of createAttachmentByExecId method, of class AttachmentServiceImpl.
     */
//    @Test
    public void testCreateAttachmentByExecId() throws Exception {

        // a business method

        /*
        System.out.println("createAttachmentByExecId");
        AttachmentDto attachmentDto = null;
        AttachmentServiceImpl instance = new AttachmentServiceImpl();
        instance.createAttachmentByExecId(attachmentDto);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of getAttachmentsByExecId method, of class AttachmentServiceImpl.
     */
//    @Test
    public void testGetAttachmentsByExecId() throws Exception {

        // a business method

        /*
        System.out.println("getAttachmentsByExecId");
        Integer cseExecId = null;
        AttachmentServiceImpl instance = new AttachmentServiceImpl();
        List expResult = null;
        List result = instance.getAttachmentsByExecId(cseExecId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}