package net.aegis.lab.testcase.service;

import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.user.service.UserService;
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
public class TestCaseServiceImplTest {

    private static final Log log = LogFactory.getLog(TestCaseServiceImplTest.class);
    private TestCaseService testCaseService;
    private UserService userService;

    public TestCaseServiceImplTest() {
        log.info("TestCaseServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("TestCaseServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("TestCaseServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("TestCaseServiceImplTest.setUp() - INFO");
        testCaseService = (TestCaseService)ContextUtil.getLabContext().getBean("testCaseService");
        userService = (UserService)ContextUtil.getLabContext().getBean("userService");
    }

    @After
    public void tearDown() {
        log.info("TestCaseServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class TestCaseServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("TestCaseServiceImplTest.testCreate(): start - INFO");

        Testcase testTestCase = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestCaseServiceImplTest.testCreate(): User name --->>" + testUser.getUsername());

        testTestCase = new Testcase();
        testTestCase.setName("Test test case name");
        testTestCase.setDescription("Test test case description");
        testTestCase.setMessageType("Test PD");
        testTestCase.setMessageName("Test Patient Discovery");
        testTestCase.setTestType("I");
        testTestCase.setExecuteType("Test Execute Type");
        testTestCase.setInitiatorInd("Y");
        testTestCase.setResponderInd("N");
        testTestCase.setSsnHandlingInd("Y");
        testTestCase.setExpectedTestResults("Test Expected Test Results");
        testTestCase.setCreateuser(testUser.getUsername());
        testTestCase.setChangeduser(testUser.getUsername());

        resultID = testCaseService.create(testTestCase);
        assertTrue(("Test test case to be created with id="+resultID), (testTestCase.getCaseId()) > 0);
        log.info("TestCaseServiceImplTest.testCreate(): Test case execution Result ID --->>" + testTestCase.getCaseId());
    }

    /**
     * Test of read method, of class TestCaseServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("TestCaseServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Testcase testTestCase = null;
        testTestCase = testCaseService.read(id);
        assertNotNull(testTestCase);
        log.info("TestCaseServiceImplTest.testRead(): Test case Name --->>" + testTestCase.getName());
    }

    /**
     * Test of update method, of class TestCaseServiceImpl.
     */
    @Test
    public void testUpdate() throws Exception {

        log.info("TestCaseServiceImplTest.testUpdate(): start - INFO");

        Testcase testTestCaseToCreate = null;
        Testcase testTestCaseToFind = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestCaseServiceImplTest.testUpdate(): User name --->>" + testUser.getUsername());

        testTestCaseToCreate = new Testcase();
        testTestCaseToCreate.setName("Test test case name");
        testTestCaseToCreate.setDescription("Test test case description");
        testTestCaseToCreate.setMessageType("Test PD");
        testTestCaseToCreate.setMessageName("Test Patient Discovery");
        testTestCaseToCreate.setTestType("I");
        testTestCaseToCreate.setExecuteType("Test Execute Type");
        testTestCaseToCreate.setInitiatorInd("Y");
        testTestCaseToCreate.setResponderInd("N");
        testTestCaseToCreate.setSsnHandlingInd("Y");
        testTestCaseToCreate.setExpectedTestResults("Test Expected Test Results");
        testTestCaseToCreate.setCreateuser(testUser.getUsername());
        testTestCaseToCreate.setChangeduser(testUser.getUsername());

        resultID = testCaseService.create(testTestCaseToCreate);
        assertTrue(("Test test case to be created with id="+resultID), (testTestCaseToCreate.getCaseId()) > 0);
        log.info("TestCaseServiceImplTest.testUpdate(): Test case execution Result ID --->>" + testTestCaseToCreate.getCaseId());

        // change a parameter in order to update the service set created
        testTestCaseToCreate.setName("Test updated test case name");
        testCaseService.update(testTestCaseToCreate);

        // find the updated scenario execution record and display changed information
        testTestCaseToFind = testCaseService.read(resultID);
        log.info("TestCaseServiceImplTest.testUpdate(): Changed Service set Name --->>" + testTestCaseToFind.getName());
    }

    /**
     * Test of delete method, of class TestCaseServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("TestCaseServiceImplTest.testDelete(): start - INFO");

        Testcase testTestCase = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestCaseServiceImplTest.testDelete(): User name --->>" + testUser.getUsername());

        testTestCase = new Testcase();
        testTestCase.setName("Test test case name");
        testTestCase.setDescription("Test test case description");
        testTestCase.setMessageType("Test PD");
        testTestCase.setMessageName("Test Patient Discovery");
        testTestCase.setTestType("I");
        testTestCase.setExecuteType("Test Execute Type");
        testTestCase.setInitiatorInd("Y");
        testTestCase.setResponderInd("N");
        testTestCase.setSsnHandlingInd("Y");
        testTestCase.setExpectedTestResults("Test Expected Test Results");
        testTestCase.setCreateuser(testUser.getUsername());
        testTestCase.setChangeduser(testUser.getUsername());

        resultID = testCaseService.create(testTestCase);
        assertTrue(("Test test case to be created with id="+resultID), (testTestCase.getCaseId()) > 0);
        log.info("TestCaseServiceImplTest.testDelete(): Test case execution Result ID --->>" + testTestCase.getCaseId());

        testCaseService.delete(testTestCase);
        log.info("TestCaseServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class TestCaseServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("TestCaseServiceImplTest.testDeleteById(): start - INFO");

        Testcase testTestCase = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestCaseServiceImplTest.testDeleteById(): User name --->>" + testUser.getUsername());

        testTestCase = new Testcase();
        testTestCase.setName("Test test case name");
        testTestCase.setDescription("Test test case description");
        testTestCase.setMessageType("Test PD");
        testTestCase.setMessageName("Test Patient Discovery");
        testTestCase.setTestType("I");
        testTestCase.setExecuteType("Test Execute Type");
        testTestCase.setInitiatorInd("Y");
        testTestCase.setResponderInd("N");
        testTestCase.setSsnHandlingInd("Y");
        testTestCase.setExpectedTestResults("Test Expected Test Results");
        testTestCase.setCreateuser(testUser.getUsername());
        testTestCase.setChangeduser(testUser.getUsername());

        resultID = testCaseService.create(testTestCase);
        assertTrue(("Test test case to be created with id="+resultID), (testTestCase.getCaseId()) > 0);
        log.info("TestCaseServiceImplTest.testDeleteById(): Test case execution Result ID --->>" + testTestCase.getCaseId());

        testCaseService.deleteById(resultID);
        log.info("TestCaseServiceImplTest.testDeleteById(): end - INFO");
    }

}