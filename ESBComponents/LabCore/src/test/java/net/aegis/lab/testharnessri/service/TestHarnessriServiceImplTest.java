package net.aegis.lab.testharnessri.service;

import java.util.List;
import java.util.Map;
import net.aegis.lab.dao.pojo.Testharnessri;
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
public class TestHarnessriServiceImplTest {

    private static final Log log = LogFactory.getLog(TestHarnessriServiceImplTest.class);
    private TestHarnessriService testHarnessRIService;
    private UserService userService;

    public TestHarnessriServiceImplTest() {
        log.info("TestHarnessriServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("TestHarnessriServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("TestHarnessriServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("TestHarnessriServiceImplTest.setUp() - INFO");
        testHarnessRIService = (TestHarnessriService)ContextUtil.getLabContext().getBean("testHarnessriService");
        userService = (UserService)ContextUtil.getLabContext().getBean("userService");
    }

    @After
    public void tearDown() {
        log.info("TestHarnessriServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class TestHarnessriServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("TestHarnessriServiceImplTest.testCreate(): start - INFO");

        Testharnessri testTestHarnessRI = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestHarnessriServiceImplTest.testCreate(): User name --->>" + testUser.getUsername());

        testTestHarnessRI = new Testharnessri();
        testTestHarnessRI.setName("Test name");
        testTestHarnessRI.setVersion("Test NHINConnect2.3");
        testTestHarnessRI.setCommunityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRI.setAssigningAuthorityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRI.setIpAddress("Test 10.0.3.115");
        testTestHarnessRI.setCreateuser(testUser.getUsername());
        testTestHarnessRI.setChangeduser(testUser.getUsername());

        resultID = testHarnessRIService.create(testTestHarnessRI);
        assertTrue(("Test test case to be created with id="+resultID), (testTestHarnessRI.getTestharnessId()) > 0);
        log.info("TestHarnessriServiceImplTest.testCreate(): Test harness RI Result ID --->>" + testTestHarnessRI.getTestharnessId());
    }

    /**
     * Test of read method, of class TestHarnessriServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("TestHarnessriServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Testharnessri testTestHarnessRI = null;
        testTestHarnessRI = testHarnessRIService.read(id);
        assertNotNull(testTestHarnessRI);
        log.info("TestHarnessriServiceImplTest.testRead(): Test harness RI Name --->>" + testTestHarnessRI.getName());
    }

    /**
     * Test of update method, of class TestHarnessriServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("TestHarnessriServiceImplTest.testUpdate(): start - INFO");

        Testharnessri testTestHarnessRIToCreate = null;
        Testharnessri testTestHarnessRIToFind = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestHarnessriServiceImplTest.testUpdate(): User name --->>" + testUser.getUsername());

        testTestHarnessRIToCreate = new Testharnessri();
        testTestHarnessRIToCreate.setName("Test harness name");
        testTestHarnessRIToCreate.setVersion("Test NHINConnect2.3");
        testTestHarnessRIToCreate.setCommunityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRIToCreate.setAssigningAuthorityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRIToCreate.setIpAddress("Test 10.0.3.115");
        testTestHarnessRIToCreate.setCreateuser(testUser.getUsername());
        testTestHarnessRIToCreate.setChangeduser(testUser.getUsername());

        resultID = testHarnessRIService.create(testTestHarnessRIToCreate);
        assertTrue(("Test test case to be created with id="+resultID), (testTestHarnessRIToCreate.getTestharnessId()) > 0);
        log.info("TestHarnessriServiceImplTest.testUpdate(): Test harness RI Result ID --->>" + testTestHarnessRIToCreate.getTestharnessId());

        // change a parameter in order to update the test harness ri created
        testTestHarnessRIToCreate.setName("Test updated test harness name");
        testHarnessRIService.update(testTestHarnessRIToCreate);

        // find the updated scenario execution record and display changed information
        testTestHarnessRIToFind = testHarnessRIService.read(resultID);
        log.info("TestHarnessriServiceImplTest.testUpdate(): Changed test harness ri Name --->>" + testTestHarnessRIToFind.getName());
    }

    /**
     * Test of delete method, of class TestHarnessriServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("TestHarnessriServiceImplTest.testDelete(): start - INFO");

        Testharnessri testTestHarnessRI = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestHarnessriServiceImplTest.testDelete(): User name --->>" + testUser.getUsername());

        testTestHarnessRI = new Testharnessri();
        testTestHarnessRI.setName("Test name");
        testTestHarnessRI.setVersion("Test NHINConnect2.3");
        testTestHarnessRI.setCommunityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRI.setAssigningAuthorityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRI.setIpAddress("Test 10.0.3.115");
        testTestHarnessRI.setCreateuser(testUser.getUsername());
        testTestHarnessRI.setChangeduser(testUser.getUsername());

        resultID = testHarnessRIService.create(testTestHarnessRI);
        assertTrue(("Test test case to be created with id="+resultID), (testTestHarnessRI.getTestharnessId()) > 0);
        log.info("TestHarnessriServiceImplTest.testDelete(): Test harness RI Result ID --->>" + testTestHarnessRI.getTestharnessId());

        testHarnessRIService.delete(testTestHarnessRI);
        log.info("TestHarnessriServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class TestHarnessriServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("TestHarnessriServiceImplTest.testDeleteById(): start - INFO");

        Testharnessri testTestHarnessRI = null;
        Integer resultID = null;
        User testUser = null;
        Integer userID = 1;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("TestHarnessriServiceImplTest.testDeleteById(): User name --->>" + testUser.getUsername());

        testTestHarnessRI = new Testharnessri();
        testTestHarnessRI.setName("Test name");
        testTestHarnessRI.setVersion("Test NHINConnect2.3");
        testTestHarnessRI.setCommunityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRI.setAssigningAuthorityId("Test 2.16.840.1.113883.0.101");
        testTestHarnessRI.setIpAddress("Test 10.0.3.115");
        testTestHarnessRI.setCreateuser(testUser.getUsername());
        testTestHarnessRI.setChangeduser(testUser.getUsername());

        resultID = testHarnessRIService.create(testTestHarnessRI);
        assertTrue(("Test test case to be created with id="+resultID), (testTestHarnessRI.getTestharnessId()) > 0);
        log.info("TestHarnessriServiceImplTest.testDeleteById(): Test harness RI Result ID --->>" + testTestHarnessRI.getTestharnessId());

        testHarnessRIService.deleteById(resultID);
        log.info("TestHarnessriServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of getCommunityIds method, of class TestHarnessriServiceImpl.
     */
//    @Test
    public void testGetCommunityIds() throws Exception {

        /*
        System.out.println("getCommunityIds");
        TestHarnessriServiceImpl instance = new TestHarnessriServiceImpl();
        Map expResult = null;
        Map result = instance.getCommunityIds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of findAll method, of class TestHarnessriServiceImpl.
     */
//    @Test
    public void testFindAll() throws Exception {

        /*
        System.out.println("findAll");
        TestHarnessriServiceImpl instance = new TestHarnessriServiceImpl();
        List expResult = null;
        List result = instance.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}