package net.aegis.lab.validation.service;

import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.Validation;
import net.aegis.lab.servicesetexecution.service.ServiceSetExecutionService;
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
 * mysql> desc validation;
 * +----------------+------------------+------+-----+-----------+----------------+
 * | Field          | Type             | Null | Key | Default   | Extra          |
 * +----------------+------------------+------+-----+-----------+----------------+
 * | validationId   | int(10) unsigned | NO   | PRI | NULL      | auto_increment |
 * | setExecutionId | int(10) unsigned | NO   | MUL | NULL      |                |
 * | nhinRepId      | int(10) unsigned | YES  |     | NULL      |                |
 * | status         | varchar(45)      | NO   |     | SUBMITTED |                |
 * | decision       | varchar(45)      | YES  |     | NULL      |                |
 * | decisionReason | varchar(2000)    | YES  |     | NULL      |                |
 * | comments       | varchar(2000)    | YES  |     | NULL      |                |
 * | createtime     | datetime         | YES  |     | NULL      |                |
 * | createuser     | varchar(45)      | NO   |     |           |                |
 * | changedtime    | datetime         | YES  |     | NULL      |                |
 * | changeduser    | varchar(45)      | NO   |     |           |                |
 * +----------------+------------------+------+-----+-----------+----------------+
 *
 * @author Abhay.Bakshi
 */

public class ValidationServiceImplTest {

    private static final Log log = LogFactory.getLog(ValidationServiceImplTest.class);
    private ValidationService validationService;
    private ServiceSetExecutionService serviceSetExecutionService;

    public ValidationServiceImplTest() {
        log.info("ValidationServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ValidationServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ValidationServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ValidationServiceImplTest.setUp() - INFO");
        validationService = (ValidationService)ContextUtil.getLabContext().getBean("validationService");
        serviceSetExecutionService = (ServiceSetExecutionService)ContextUtil.getLabContext().getBean("serviceSetExecutionService");
    }

    @After
    public void tearDown() {
        log.info("ValidationServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ValidationServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ValidationServiceImplTest.testCreate(): start - INFO");

        Validation testValidation = null;
        Integer resultID = null;
        Integer serviceSetExecutionID = 1;             // at least one service set execution expected in DB
        Servicesetexecution testServiceSetExecution = null;

        testServiceSetExecution = serviceSetExecutionService.read(serviceSetExecutionID);
        assertNotNull(testServiceSetExecution);
        log.info("ValidationServiceImplTest.testCreate(): Service set execution: execution unique Id --->>" + testServiceSetExecution.getExecutionUniqueId());

        testValidation = new Validation();
        testValidation.setServicesetexecution(testServiceSetExecution);
        testValidation.setStatus("test pass");
        testValidation.setCreateuser("test create user");
        testValidation.setChangeduser("test changed user");

        resultID = validationService.create(testValidation);
        assertTrue(("Validation to be created with id="+resultID), (testValidation.getValidationId()) > 0);
        log.info("ValidationServiceImplTest.testCreate(): Validation Result ID --->>" + testValidation.getValidationId());
    }

    /**
     * Test of update method, of class ValidationServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ValidationServiceImplTest.testUpdate(): start - INFO");

        Validation testValidationToCreate = null;
        Validation testValidationToFind = null;
        Integer resultID = null;
        Integer serviceSetExecutionID = 1;             // at least one service set execution expected in DB
        Servicesetexecution testServiceSetExecution = null;

        testServiceSetExecution = serviceSetExecutionService.read(serviceSetExecutionID);
        assertNotNull(testServiceSetExecution);
        log.info("ValidationServiceImplTest.testUpdate(): Service set execution: execution unique Id --->>" + testServiceSetExecution.getExecutionUniqueId());

        testValidationToCreate = new Validation();
        testValidationToCreate.setServicesetexecution(testServiceSetExecution);
        testValidationToCreate.setStatus("test pass");
        testValidationToCreate.setCreateuser("test create user");
        testValidationToCreate.setChangeduser("test changed user");

        resultID = validationService.create(testValidationToCreate);
        assertTrue(("Validation to be created with id="+resultID), (testValidationToCreate.getValidationId()) > 0);
        log.info("ValidationServiceImplTest.testUpdate(): Validation Result ID --->>" + testValidationToCreate.getValidationId());

        // change a parameter in order to update the validation created
        testValidationToCreate.setStatus("Updated test status");
        validationService.update(testValidationToCreate);

        // find the updated scenario execution record and display changed information
        testValidationToFind = validationService.read(resultID);
        log.info("ValidationServiceImplTest.testUpdate(): Changed Status --->>" + testValidationToFind.getStatus());
    }


    /**
     * Test of delete method, of class ValidationServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ValidationServiceImplTest.testDelete(): start - INFO");

        Validation testValidationToCreate = null;
        Integer resultID = null;
        Integer serviceSetExecutionID = 1;             // at least one service set execution expected in DB
        Servicesetexecution testServiceSetExecution = null;

        testServiceSetExecution = serviceSetExecutionService.read(serviceSetExecutionID);
        assertNotNull(testServiceSetExecution);
        log.info("ValidationServiceImplTest.testDelete(): Service set execution: execution unique Id --->>" + testServiceSetExecution.getExecutionUniqueId());

        testValidationToCreate = new Validation();
        testValidationToCreate.setServicesetexecution(testServiceSetExecution);
        testValidationToCreate.setStatus("test pass");
        testValidationToCreate.setCreateuser("test create user");
        testValidationToCreate.setChangeduser("test changed user");

        resultID = validationService.create(testValidationToCreate);
        assertTrue(("Validation to be created with id="+resultID), (testValidationToCreate.getValidationId()) > 0);
        log.info("ValidationServiceImplTest.testDelete(): Validation Result ID --->>" + testValidationToCreate.getValidationId());

        validationService.delete(testValidationToCreate);
        log.info("ValidationServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class ValidationServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("ValidationServiceImplTest.testDeleteById(): start - INFO");

        Validation testValidationToCreate = null;
        Integer resultID = null;
        Integer serviceSetExecutionID = 1;             // at least one service set execution expected in DB
        Servicesetexecution testServiceSetExecution = null;

        testServiceSetExecution = serviceSetExecutionService.read(serviceSetExecutionID);
        assertNotNull(testServiceSetExecution);
        log.info("ValidationServiceImplTest.testDeleteById(): Service set execution: execution unique Id --->>" + testServiceSetExecution.getExecutionUniqueId());

        testValidationToCreate = new Validation();
        testValidationToCreate.setServicesetexecution(testServiceSetExecution);
        testValidationToCreate.setStatus("test pass");
        testValidationToCreate.setCreateuser("test create user");
        testValidationToCreate.setChangeduser("test changed user");

        resultID = validationService.create(testValidationToCreate);
        assertTrue(("Validation to be created with id="+resultID), (testValidationToCreate.getValidationId()) > 0);
        log.info("ValidationServiceImplTest.testDeleteById(): Validation Result ID --->>" + testValidationToCreate.getValidationId());

        validationService.deleteById(resultID);
        log.info("ValidationServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of read method, of class ValidationServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("ValidationServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Validation testValidation = null;

        testValidation = validationService.read(id);
        assertNotNull(testValidation);

        log.info("ValidationServiceImplTest.testRead(): Validation ID --->>" + testValidation.getValidationId() +
                    ", and Validation status --->>" + testValidation.getStatus());
    }


    /**
     * Test of createValidationServiceSetRecords method, of class ValidationServiceImpl.
     */
//    @Test
    public void testCreateValidationServiceSetRecords() throws Exception {

        // a business method

        /*
        System.out.println("createValidationServiceSetRecords");
        List<Servicesetexecution> serviceSetExecs = null;
        ValidationServiceImpl instance = new ValidationServiceImpl();
        instance.createValidationServiceSetRecords(serviceSetExecs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of searchByCriteria method, of class ValidationServiceImpl.
     */
//    @Test
    public void testSearchByCriteria() throws Exception {

        // a business method

        /*
        System.out.println("searchByCriteria");
        Validation criteria = null;
        List<Criterion> criterion = null;
        ValidationServiceImpl instance = new ValidationServiceImpl();
        List expResult = null;
        List result = instance.searchByCriteria(criteria, criterion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}