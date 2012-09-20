package net.aegis.lab.nhinrep.service;

import java.util.List;
import net.aegis.lab.dao.pojo.Nhinrep;
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
public class NhinrepServiceImplTest {

    private static final Log log = LogFactory.getLog(NhinrepServiceImplTest.class);
    private UserService userService;
    private NhinrepService nhinrepService;

    public NhinrepServiceImplTest() {
        log.info("NhinrepServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("NhinrepServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("NhinrepServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("NhinrepServiceImplTest.setUp() - INFO");
        userService = (UserService)ContextUtil.getLabContext().getBean("userService");
        nhinrepService = (NhinrepService)ContextUtil.getLabContext().getBean("nhinrepService");
    }

    @After
    public void tearDown() {
        log.info("NhinrepServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class NhinrepServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("NhinrepServiceImplTest.testCreate(): start - INFO");
        Integer resultID = -999;
        Nhinrep testNhinrep = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("NhinrepServiceImplTest.testCreate(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("NhinrepServiceImplTest.testCreate(): user not found", testUser);
        
        testNhinrep = new Nhinrep();
        testNhinrep.setName("Test NHIN Rep To Delete");
        testNhinrep.setUser(testUser);
        testNhinrep.setContactName("POC at Test NHIN Rep To Delete");
        testNhinrep.setContactPhone("999-999-9999");
        testNhinrep.setContactEmail("poc@Nhinrep_To_Delete.com");
        testNhinrep.setCreateuser("NhinrepServiceImpl.Test");
        testNhinrep.setChangeduser("NhinrepServiceImpl.Test");
        testNhinrep.setStatus("ACTIVE");

        // create a participant
        resultID = nhinrepService.create(testNhinrep);

        assertTrue(("Test Nhin rep to be created with id="+resultID), (testNhinrep.getNhinRepId() > 0));
        log.info("NhinrepServiceImplTest.testCreate(): Nhin Rep name --->>" + testNhinrep.getName());
    }

    /**
     * Test of read method, of class NhinrepServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("NhinrepServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Nhinrep testNhinrep = null;
        testNhinrep = nhinrepService.read(id);
        assertNotNull(testNhinrep);
        log.info("NhinrepServiceImplTest.testRead(): Nhin Rep name --->>" + testNhinrep.getName());
    }

    /**
     * Test of update method, of class NhinrepServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("NhinrepServiceImplTest.testUpdate(): start - INFO");
        Integer resultID = -999;
        Nhinrep testNhinrepToCreate = null;
        Nhinrep testNhinrepToFind = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("NhinrepServiceImplTest.testUpdate(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("NhinrepServiceImplTest.testUpdate(): user not found", testUser);
        
        testNhinrepToCreate = new Nhinrep();
        testNhinrepToCreate.setName("Test NHIN Rep To Delete");
        testNhinrepToCreate.setUser(testUser);
        testNhinrepToCreate.setContactName("POC at Test NHIN Rep To Delete");
        testNhinrepToCreate.setContactPhone("999-999-9999");
        testNhinrepToCreate.setContactEmail("poc@Nhinrep_To_Delete.com");
        testNhinrepToCreate.setCreateuser("NhinrepServiceImpl.Test");
        testNhinrepToCreate.setChangeduser("NhinrepServiceImpl.Test");
        testNhinrepToCreate.setStatus("ACTIVE");

        // create a participant
        resultID = nhinrepService.create(testNhinrepToCreate);

        assertTrue(("Test Nhin rep to be created with id="+resultID), (testNhinrepToCreate.getNhinRepId() > 0));
        log.info("NhinrepServiceImplTest.testUpdate(): Nhin Rep name --->>" + testNhinrepToCreate.getName());

        // change a parameter in order to update participant created
        testNhinrepToCreate.setName("Changed_Name_TestNhinRep_To_Delete");
        nhinrepService.update(testNhinrepToCreate);
        
        // find the updated participant and display his/her name
        testNhinrepToFind = nhinrepService.findByNhinRepId(resultID);
        log.info("NhinrepServiceImplTest.testUpdate(): Changed Nhin Rep name --->>" + testNhinrepToFind.getName());
    }

    /**
     * Test of delete method, of class NhinrepServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("NhinrepServiceImplTest.testDelete(): start - INFO");
        Integer resultID = -999;
        Nhinrep testNhinrep = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("NhinrepServiceImplTest.testDelete(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("NhinrepServiceImplTest.testDelete(): user not found", testUser);

        testNhinrep = new Nhinrep();
        testNhinrep.setName("Test NHIN Rep To Delete");
        testNhinrep.setUser(testUser);
        testNhinrep.setContactName("POC at Test NHIN Rep To Delete");
        testNhinrep.setContactPhone("999-999-9999");
        testNhinrep.setContactEmail("poc@Nhinrep_To_Delete.com");
        testNhinrep.setCreateuser("NhinrepServiceImpl.Test");
        testNhinrep.setChangeduser("NhinrepServiceImpl.Test");
        testNhinrep.setStatus("ACTIVE");

        // create a participant
        resultID = nhinrepService.create(testNhinrep);

        assertTrue(("Test Nhin rep to be created with id="+resultID), (testNhinrep.getNhinRepId() > 0));
        log.info("NhinrepServiceImplTest.testDelete(): Nhin Rep name --->>" + testNhinrep.getName());

        nhinrepService.delete(testNhinrep);
        log.info("NhinrepServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class NhinrepServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("NhinrepServiceImplTest.testDeleteById(): start - INFO");
        Integer resultID = -999;
        Nhinrep testNhinrep = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("NhinrepServiceImplTest.testDeleteById(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("NhinrepServiceImplTest.testDeleteById(): user not found", testUser);

        testNhinrep = new Nhinrep();
        testNhinrep.setName("Test NHIN Rep To Delete");
        testNhinrep.setUser(testUser);
        testNhinrep.setContactName("POC at Test NHIN Rep To Delete");
        testNhinrep.setContactPhone("999-999-9999");
        testNhinrep.setContactEmail("poc@Nhinrep_To_Delete.com");
        testNhinrep.setCreateuser("NhinrepServiceImpl.Test");
        testNhinrep.setChangeduser("NhinrepServiceImpl.Test");
        testNhinrep.setStatus("ACTIVE");

        // create a participant
        resultID = nhinrepService.create(testNhinrep);

        assertTrue(("Test Nhin rep to be created with id="+resultID), (testNhinrep.getNhinRepId() > 0));
        log.info("NhinrepServiceImplTest.testDeleteById(): Nhin Rep name --->>" + testNhinrep.getName());

        nhinrepService.deleteById(resultID);
        log.info("NhinrepServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of findByNhinRepId method, of class NhinrepServiceImpl.
     */
//    @Test
    public void testFindByNhinRepId() throws Exception {

        log.info("NhinrepServiceImplTest.testFindByNhinRepId(): start - INFO");
        Integer id = 1;
        Nhinrep testNhinrep = null;
        testNhinrep = nhinrepService.read(id);
        assertNotNull(testNhinrep);
        log.info("NhinrepServiceImplTest.testFindByNhinRepId():  Nhin Rep name --->>" + testNhinrep.getName());
    }

    /**
     * Test of findByStatus method, of class NhinrepServiceImpl.
     */
//    @Test
    public void testFindByStatus() throws Exception {

        log.info("NhinrepServiceImplTest.testFindByStatus(): start - INFO");
        String statusToSearch = "ACTIVE";
        List<Nhinrep> testListNhinreps = null;
        testListNhinreps = nhinrepService.findByStatus(statusToSearch);
        assertNotNull(testListNhinreps);
        log.info("NhinrepServiceImplTest.testFindByStatus():  Number of Nhin Reps found --->>" + testListNhinreps.size());
    }

}