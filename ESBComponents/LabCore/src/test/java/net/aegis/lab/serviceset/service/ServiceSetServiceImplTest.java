/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.serviceset.service;

import java.util.List;
import net.aegis.lab.dao.pojo.Serviceset;
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
public class ServiceSetServiceImplTest {

    private static final Log log = LogFactory.getLog(ServiceSetServiceImplTest.class);
    private ServiceSetService serviceSetService;
    private UserService userService;

    public ServiceSetServiceImplTest() {
        log.info("ServiceSetServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ServiceSetServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ServiceSetServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ScenarioExecutionServiceImplTest.setUp() - INFO");
        serviceSetService = (ServiceSetService)ContextUtil.getLabContext().getBean("serviceSetService");
        userService = (UserService)ContextUtil.getLabContext().getBean("userService");
    }

    @After
    public void tearDown() {
        log.info("ServiceSetServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ServiceSetServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ServiceSetServiceImplTest.testCreate(): start - INFO");

        Serviceset testServiceSet = null;
        User testUser = null;
        Integer userID = 1;
        Integer resultID = null;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("ServiceSetServiceImplTest.testCreate(): User name --->>" + testUser.getUsername());

        testServiceSet = new Serviceset();
        testServiceSet.setSetName("Test Set Name");
        testServiceSet.setInitiatorAllowedInd("Y");
        testServiceSet.setResponderAllowedInd("Y");
        testServiceSet.setSsnHandlingInd("Y");
        testServiceSet.setCreateuser(testUser.getUsername());
        testServiceSet.setChangeduser(testUser.getUsername());

        resultID = serviceSetService.create(testServiceSet);
        assertTrue(("Test service set to be created with id="+resultID), (testServiceSet.getSetId()) > 0);
        log.info("ServiceSetServiceImplTest.testCreate(): Service set Result ID --->>" + testServiceSet.getSetId());
    }

    /**
     * Test of read method, of class ServiceSetServiceImpl.
     */
    @Test
    public void testRead() throws Exception {

        log.info("ServiceSetServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Serviceset testServiceSet = null;
        testServiceSet = serviceSetService.read(id);
        assertNotNull(testServiceSet);
        log.info("ServiceSetServiceImplTest.testRead(): Service set Name --->>" + testServiceSet.getSetName());
    }

    @Test
    public void testReadLabAccessFilter() throws Exception {
         // We could this to call API that filters based on LabAccessFilter
        Serviceset testServiceSet = serviceSetService.read(1);
        assertNotNull(testServiceSet);

        //Story 51: Integrate Conformance Tests - Core Library changes
        assertNotNull(testServiceSet.getLabAccessFilter());
        log.info("ServiceSetServiceImplTest.testRead(): labAccessFilter--->>" + testServiceSet.getLabAccessFilter());
    }

    /**
     * Test of delete method, of class ServiceSetServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ServiceSetServiceImplTest.testDelete(): start - INFO");

        Serviceset testServiceSetToCreate = null;
        User testUser = null;
        Integer userID = 1;
        Integer resultID = null;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("ServiceSetServiceImplTest.testDelete(): User name --->>" + testUser.getUsername());

        testServiceSetToCreate = new Serviceset();
        testServiceSetToCreate.setSetName("Test Set Name");
        testServiceSetToCreate.setInitiatorAllowedInd("Y");
        testServiceSetToCreate.setResponderAllowedInd("Y");
        testServiceSetToCreate.setSsnHandlingInd("Y");
        testServiceSetToCreate.setCreateuser(testUser.getUsername());
        testServiceSetToCreate.setChangeduser(testUser.getUsername());

        resultID = serviceSetService.create(testServiceSetToCreate);
        assertTrue(("Test service set to be created with id="+resultID), (testServiceSetToCreate.getSetId()) > 0);
        log.info("ServiceSetServiceImplTest.testDelete(): Service set Result ID --->>" + testServiceSetToCreate.getSetId());

        serviceSetService.delete(testServiceSetToCreate);
        log.info("ServiceSetServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class ServiceSetServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("ServiceSetServiceImplTest.testDeleteById(): start - INFO");

        Serviceset testServiceSetToCreate = null;
        User testUser = null;
        Integer userID = 1;
        Integer resultID = null;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("ServiceSetServiceImplTest.testDeleteById(): User name --->>" + testUser.getUsername());

        testServiceSetToCreate = new Serviceset();
        testServiceSetToCreate.setSetName("Test Set Name");
        testServiceSetToCreate.setInitiatorAllowedInd("Y");
        testServiceSetToCreate.setResponderAllowedInd("Y");
        testServiceSetToCreate.setSsnHandlingInd("Y");
        testServiceSetToCreate.setCreateuser(testUser.getUsername());
        testServiceSetToCreate.setChangeduser(testUser.getUsername());

        resultID = serviceSetService.create(testServiceSetToCreate);
        assertTrue(("Test service set to be created with id="+resultID), (testServiceSetToCreate.getSetId()) > 0);
        log.info("ServiceSetServiceImplTest.testDeleteById(): Service set Result ID --->>" + testServiceSetToCreate.getSetId());

        serviceSetService.deleteById(resultID);
        log.info("ServiceSetServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of update method, of class ServiceSetServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ServiceSetServiceImplTest.testUpdate(): start - INFO");

        Serviceset testServiceSetToCreate = null;
        Serviceset testServiceSetToFind = null;
        User testUser = null;
        Integer userID = 1;
        Integer resultID = null;

        testUser = userService.read(userID);        // at least one user expected in DB
        assertNotNull(testUser);
        log.info("ServiceSetServiceImplTest.testUpdate(): User name --->>" + testUser.getUsername());

        testServiceSetToCreate = new Serviceset();
        testServiceSetToCreate.setSetName("Test Set Name");
        testServiceSetToCreate.setInitiatorAllowedInd("Y");
        testServiceSetToCreate.setResponderAllowedInd("Y");
        testServiceSetToCreate.setSsnHandlingInd("Y");
        testServiceSetToCreate.setCreateuser(testUser.getUsername());
        testServiceSetToCreate.setChangeduser(testUser.getUsername());

        resultID = serviceSetService.create(testServiceSetToCreate);
        assertTrue(("Test service set to be created with id="+resultID), (testServiceSetToCreate.getSetId()) > 0);
        log.info("ServiceSetServiceImplTest.testUpdate(): Service set Result ID --->>" + testServiceSetToCreate.getSetId());

        // change a parameter in order to update the service set created
        testServiceSetToCreate.setSetName("Test Update Set Name");
        serviceSetService.update(testServiceSetToCreate);

        // find the updated scenario execution record and display changed information
        testServiceSetToFind = serviceSetService.read(resultID);
        log.info("ServiceSetServiceImplTest.testUpdate(): Changed Service set Name --->>" + testServiceSetToFind.getSetName());
    }

    /**
     * Test of getServicesets method, of class ServiceSetServiceImpl.
     */
//    @Test
    public void testGetServicesets() throws Exception {

        log.info("ServiceSetServiceImplTest.testGetServicesets(): start - INFO");
        List<Serviceset> testServiceSets = null;
        
        testServiceSets = serviceSetService.getServicesets();
        assertNotNull(testServiceSets);
        
        for(Serviceset ss : testServiceSets) {
            log.info("ServiceSetServiceImplTest.testGetServicesets(): Service set info: --->>" +
                    ss.getSetId() + ". " + ss.getSetName());
        }
    }

}