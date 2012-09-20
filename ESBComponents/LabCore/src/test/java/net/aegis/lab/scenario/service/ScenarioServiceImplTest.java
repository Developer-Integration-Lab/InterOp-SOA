package net.aegis.lab.scenario.service;

import java.util.List;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.serviceset.service.ServiceSetService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author Abhay.Bakshi
 */
public class ScenarioServiceImplTest {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ScenarioServiceImplTest.class);
    
    private ScenarioService scenarioService;
    private ServiceSetService serviceSetService;

    public ScenarioServiceImplTest() {
        log.info("ScenarioServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ScenarioServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ScenarioServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ScenarioServiceImplTest.setUp() - INFO");
        scenarioService = (ScenarioService)ContextUtil.getLabContext().getBean("scenarioService");
        serviceSetService = (ServiceSetService)ContextUtil.getLabContext().getBean("serviceSetService");
    }

    @After
    public void tearDown() {
        log.info("ScenarioServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ScenarioServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ScenarioServiceImplTest.testCreate(): start - INFO");

        Integer resultID = -999;
        Integer serviceSetID = 1;           // at least one service set expected in DB
        Scenario testScenario = null;
        Serviceset testServiceSet = null;

        testScenario = new Scenario();

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);

        testScenario.setServiceset(testServiceSet);
        testScenario.setScenarioName("Test Scenario");
        testScenario.setDescription("Test Description");
        testScenario.setInitiatorInd("Y");
        testScenario.setResponderInd("N");
        testScenario.setSsnHandlingInd("Y");
        testScenario.setCreateuser("Test create user");
        testScenario.setChangeduser("Test changed user");

        resultID = scenarioService.create(testScenario);
        assertTrue(("Test scenario to be created with id="+resultID), (testScenario.getScenarioId()) > 0);
        log.info("ScenarioServiceImplTest.testCreate(): Scenario Result ID --->>" + testScenario.getScenarioId());
    }

    /**
     * Test of update method, of class ScenarioServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ScenarioServiceImplTest.testUpdate(): start - INFO");

        Integer resultID = -999;
        Integer serviceSetID = 1;           // at least one service set expected in DB
        Scenario testScenarioToCreate = null;
        Scenario testScenarioToFind = null;
        Serviceset testServiceSet = null;

        testScenarioToCreate = new Scenario();

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);

        testScenarioToCreate.setServiceset(testServiceSet);
        testScenarioToCreate.setScenarioName("Test Scenario");
        testScenarioToCreate.setDescription("Test Description");
        testScenarioToCreate.setInitiatorInd("Y");
        testScenarioToCreate.setResponderInd("N");
        testScenarioToCreate.setSsnHandlingInd("Y");
        testScenarioToCreate.setCreateuser("Test create user");
        testScenarioToCreate.setChangeduser("Test changed user");

        resultID = scenarioService.create(testScenarioToCreate);
        assertTrue(("Test scenario to be created with id="+resultID), (testScenarioToCreate.getScenarioId()) > 0);
        log.info("ScenarioServiceImplTest.testUpdate(): Scenario Result ID --->>" + testScenarioToCreate.getScenarioId());

        // change a parameter in order to update the scenario created
        testScenarioToCreate.setScenarioName("Test Updated Scenario");
        scenarioService.update(testScenarioToCreate);

        // find the updated participant audit history record and display changed information
        testScenarioToFind = scenarioService.read(resultID);
        log.info("ScenarioServiceImplTest.testUpdate(): Changed Result --->>" + testScenarioToFind.getScenarioName());
    }

    /**
     * Test of read method, of class ScenarioServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("ScenarioServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Scenario testScenario = null;
        testScenario = scenarioService.read(id);
        assertNotNull(testScenario);
        log.info("ScenarioServiceImplTest.testRead(): Scenario Name --->>" + testScenario.getScenarioName());
    }

    /**
     * Test of deleteById method, of class ScenarioServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("ScenarioServiceImplTest.testDeleteById(): start - INFO");

        Integer resultID = -999;
        Integer serviceSetID = 1;           // at least one service set expected in DB
        Scenario testScenarioToCreate = null;
        Serviceset testServiceSet = null;

        testScenarioToCreate = new Scenario();

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);

        testScenarioToCreate.setServiceset(testServiceSet);
        testScenarioToCreate.setScenarioName("Test Scenario");
        testScenarioToCreate.setDescription("Test Description");
        testScenarioToCreate.setInitiatorInd("Y");
        testScenarioToCreate.setResponderInd("N");
        testScenarioToCreate.setSsnHandlingInd("Y");
        testScenarioToCreate.setCreateuser("Test create user");
        testScenarioToCreate.setChangeduser("Test changed user");

        // create a scenario so that it can be deleted
        resultID = scenarioService.create(testScenarioToCreate);
        assertTrue(("Test scenario to be created with id="+resultID), (testScenarioToCreate.getScenarioId()) > 0);
        log.info("ScenarioServiceImplTest.testDeleteById(): Scenario Result ID --->>" + testScenarioToCreate.getScenarioId());

        scenarioService.deleteById(resultID);
        log.info("ScenarioServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of delete method, of class ScenarioServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ScenarioServiceImplTest.testDelete(): start - INFO");

        Integer resultID = -999;
        Integer serviceSetID = 1;           // at least one service set expected in DB
        Scenario testScenarioToCreate = null;
        Serviceset testServiceSet = null;

        testScenarioToCreate = new Scenario();

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);

        testScenarioToCreate.setServiceset(testServiceSet);
        testScenarioToCreate.setScenarioName("Test Scenario");
        testScenarioToCreate.setDescription("Test Description");
        testScenarioToCreate.setInitiatorInd("Y");
        testScenarioToCreate.setResponderInd("N");
        testScenarioToCreate.setSsnHandlingInd("Y");
        testScenarioToCreate.setCreateuser("Test create user");
        testScenarioToCreate.setChangeduser("Test changed user");

        // create a scenario so that it can be deleted
        resultID = scenarioService.create(testScenarioToCreate);
        assertTrue(("Test scenario to be created with id="+resultID), (testScenarioToCreate.getScenarioId()) > 0);
        log.info("ScenarioServiceImplTest.testDelete(): Scenario Result ID --->>" + testScenarioToCreate.getScenarioId());

        scenarioService.delete(testScenarioToCreate);
        log.info("ScenarioServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of findByScenarioName method, of class ScenarioServiceImpl.
     */
//    @Test
    public void testFindByScenarioName() throws Exception {

        log.info("ScenarioServiceImplTest.testFindByScenarioName(): start - INFO");
        Scenario testSampleScenarioToReadFirst = null;
        List<Scenario> testScenariosToFind = null;

        Integer id = 1;
        String testSampleScenarioToReadFirst_ScenarioName = null;

        testSampleScenarioToReadFirst = scenarioService.read(id);
        assertNotNull(testSampleScenarioToReadFirst);

        testSampleScenarioToReadFirst_ScenarioName = testSampleScenarioToReadFirst.getScenarioName();
        log.info("ScenarioServiceImplTest.testFindByScenarioName(): Scenario To Read First - Name --->>" + testSampleScenarioToReadFirst_ScenarioName);

        // now go and find the scenario (again) in the DB using the "name"
        testScenariosToFind = scenarioService.findByScenarioName(testSampleScenarioToReadFirst_ScenarioName);
        assertNotNull(testScenariosToFind);

        for(Scenario testScenario : testScenariosToFind)
        {
            log.info("ScenarioServiceImplTest.testFindByScenarioName(): Scenario ID --->>" + testScenario.getScenarioId());
            log.info("ScenarioServiceImplTest.testFindByScenarioName(): Scenario Name --->>" + testScenario.getScenarioName());
        }
    }

}