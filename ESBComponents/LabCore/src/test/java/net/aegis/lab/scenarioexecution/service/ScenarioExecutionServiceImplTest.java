package net.aegis.lab.scenarioexecution.service;

import java.util.List;
import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.scenario.service.ScenarioService;
import org.hibernate.criterion.Criterion;
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
public class ScenarioExecutionServiceImplTest {

    private static final Log log = LogFactory.getLog(ScenarioExecutionServiceImplTest.class);
    private ScenarioExecutionService scenarioExecutionService;
    private ScenarioService scenarioService;
    private ParticipantService participantService;


    public ScenarioExecutionServiceImplTest() {
        log.info("ScenarioExecutionServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ScenarioExecutionServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ScenarioExecutionServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ScenarioExecutionServiceImplTest.setUp() - INFO");
        scenarioExecutionService = (ScenarioExecutionService) ContextUtil.getLabContext().getBean("scenarioExecutionService");
        scenarioService = (ScenarioService)ContextUtil.getLabContext().getBean("scenarioService");
        participantService = (ParticipantService)ContextUtil.getLabContext().getBean("participantService");
    }

    @After
    public void tearDown() {
        log.info("ScenarioExecutionServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ScenarioExecutionServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ScenarioExecutionServiceImplTest.testCreate(): start - INFO");

        Scenarioexecution testScenarioExecution = null;
        Integer resultID = null;
        Integer candidteID = 1;             // at least one participant expected in DB
        Integer scenarioID = 1;             // at least one scenario expected in DB
        Participant testParticipant = null;
        Scenario testScenario = null;
        
        testParticipant = participantService.read(candidteID);
        assertNotNull(testParticipant);
        log.info("ScenarioExecutionServiceImplTest.testCreate(): Participant name --->>" + testParticipant.getParticipantName());

        testScenario = scenarioService.read(scenarioID);
        assertNotNull(testScenario);
        log.info("ScenarioExecutionServiceImplTest.testCreate(): Scenario Name --->>" + testScenario.getScenarioName());

        testScenarioExecution = new Scenarioexecution();
        testScenarioExecution.setParticipant(testParticipant);
        testScenarioExecution.setScenario(testScenario);
        testScenarioExecution.setExecutionUniqueId("9.99999999.99999.9");
        testScenarioExecution.setStatus("Test ACTIVE");

        resultID = scenarioExecutionService.create(testScenarioExecution);
        assertTrue(("Test scenario execution to be created with id="+resultID), (testScenarioExecution.getScenarioExecutionId()) > 0);
        log.info("ScenarioExecutionServiceImplTest.testCreate(): Scenario execution Result ID --->>" + testScenarioExecution.getScenarioExecutionId());
    }

    /**
     * Test of update method, of class ScenarioExecutionServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ScenarioExecutionServiceImplTest.testUpdate(): start - INFO");

        Scenarioexecution testScenarioExecutionToCreate = null;
        Scenarioexecution testScenarioExecutionToFind = null;
        Integer resultID = null;
        Integer candidteID = 1;             // at least one participant expected in DB
        Integer scenarioID = 1;             // at least one scenario expected in DB
        Participant testParticipant = null;
        Scenario testScenario = null;

        testParticipant = participantService.read(candidteID);
        assertNotNull(testParticipant);
        log.info("ScenarioExecutionServiceImplTest.testUpdate(): Participant name --->>" + testParticipant.getParticipantName());

        log.info("ScenarioExecutionServiceImplTest.testUpdate(): start - INFO");
        testScenario = scenarioService.read(scenarioID);
        assertNotNull(testScenario);
        log.info("ScenarioExecutionServiceImplTest.testUpdate(): Scenario Name --->>" + testScenario.getScenarioName());

        testScenarioExecutionToCreate = new Scenarioexecution();
        testScenarioExecutionToCreate.setParticipant(testParticipant);
        testScenarioExecutionToCreate.setScenario(testScenario);
        testScenarioExecutionToCreate.setExecutionUniqueId("9.99999999.99999.9");
        testScenarioExecutionToCreate.setStatus("Test ACTIVE");

        resultID = scenarioExecutionService.create(testScenarioExecutionToCreate);
        assertTrue(("Test scenario execution to be created with id="+resultID), (testScenarioExecutionToCreate.getScenarioExecutionId()) > 0);
        log.info("ScenarioExecutionServiceImplTest.testUpdate(): Scenario execution Result ID --->>" + testScenarioExecutionToCreate.getScenarioExecutionId());

        // change a parameter in order to update the scenario execution created
        testScenarioExecutionToCreate.setStatus("Updated Test ACTIVE");
        scenarioExecutionService.update(testScenarioExecutionToCreate);

        // find the updated scenario execution record and display changed information
        testScenarioExecutionToFind = scenarioExecutionService.read(resultID);
        log.info("ScenarioExecutionServiceImplTest.testUpdate(): Changed Status Result --->>" + testScenarioExecutionToFind.getStatus());
    }

    /**
     * Test of delete method, of class ScenarioExecutionServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ScenarioExecutionServiceImplTest.testDelete(): start - INFO");

        Scenarioexecution testScenarioExecutionToCreate = null;
        Integer resultID = null;
        Integer candidteID = 1;             // at least one participant expected in DB
        Integer scenarioID = 1;             // at least one scenario expected in DB
        Participant testParticipant = null;
        Scenario testScenario = null;

        testParticipant = participantService.read(candidteID);
        assertNotNull(testParticipant);
        log.info("ScenarioExecutionServiceImplTest.testDelete(): Participant name --->>" + testParticipant.getParticipantName());

        log.info("ScenarioExecutionServiceImplTest.testDelete(): start - INFO");
        testScenario = scenarioService.read(scenarioID);
        assertNotNull(testScenario);
        log.info("ScenarioExecutionServiceImplTest.testDelete(): Scenario Name --->>" + testScenario.getScenarioName());

        testScenarioExecutionToCreate = new Scenarioexecution();
        testScenarioExecutionToCreate.setParticipant(testParticipant);
        testScenarioExecutionToCreate.setScenario(testScenario);
        testScenarioExecutionToCreate.setExecutionUniqueId("9.99999999.99999.9");
        testScenarioExecutionToCreate.setStatus("Test ACTIVE");

        resultID = scenarioExecutionService.create(testScenarioExecutionToCreate);
        assertTrue(("Test scenario execution to be created with id="+resultID), (testScenarioExecutionToCreate.getScenarioExecutionId()) > 0);
        log.info("ScenarioExecutionServiceImplTest.testDelete(): Scenario execution Result ID --->>" + testScenarioExecutionToCreate.getScenarioExecutionId());

        scenarioExecutionService.delete(testScenarioExecutionToCreate);
        log.info("ScenarioExecutionServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class ScenarioExecutionServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("ScenarioExecutionServiceImplTest.testDeleteById(): start - INFO");

        Scenarioexecution testScenarioExecutionToCreate = null;
        Integer resultID = null;
        Integer candidteID = 1;             // at least one participant expected in DB
        Integer scenarioID = 1;             // at least one scenario expected in DB
        Participant testParticipant = null;
        Scenario testScenario = null;

        testParticipant = participantService.read(candidteID);
        assertNotNull(testParticipant);
        log.info("ScenarioExecutionServiceImplTest.testDeleteById(): Participant name --->>" + testParticipant.getParticipantName());

        log.info("ScenarioExecutionServiceImplTest.testDeleteById(): start - INFO");
        testScenario = scenarioService.read(scenarioID);
        assertNotNull(testScenario);
        log.info("ScenarioExecutionServiceImplTest.testDeleteById(): Scenario Name --->>" + testScenario.getScenarioName());

        testScenarioExecutionToCreate = new Scenarioexecution();
        testScenarioExecutionToCreate.setParticipant(testParticipant);
        testScenarioExecutionToCreate.setScenario(testScenario);
        testScenarioExecutionToCreate.setExecutionUniqueId("9.99999999.99999.9");
        testScenarioExecutionToCreate.setStatus("Test ACTIVE");

        resultID = scenarioExecutionService.create(testScenarioExecutionToCreate);
        assertTrue(("Test scenario execution to be created with id="+resultID), (testScenarioExecutionToCreate.getScenarioExecutionId()) > 0);
        log.info("ScenarioExecutionServiceImplTest.testDeleteById(): Scenario execution Result ID --->>" + testScenarioExecutionToCreate.getScenarioExecutionId());

        scenarioExecutionService.deleteById(resultID);
        log.info("ScenarioExecutionServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of read method, of class ScenarioExecutionServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("ScenarioExecutionServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Scenarioexecution testScenarioExecution = null;
        testScenarioExecution = scenarioExecutionService.read(id);
        assertNotNull(testScenarioExecution);
        log.info("ScenarioExecutionServiceImplTest.testRead(): Scenario Execution Participant Name --->>" + testScenarioExecution.getParticipant().getParticipantName());
    }

    /**
     * Test of findByScenarioExecutionId method, of class ScenarioExecutionServiceImpl.
     */
//    @Test
    public void testFindByScenarioExecutionId() throws Exception {

        log.info("ScenarioExecutionServiceImplTest.testFindByScenarioExecutionId(): start - INFO");
        Integer id = 1;
        // Only one scenario execution expected in DB.  scenario exeucution id is PK.
        List<Scenarioexecution> testScenarioExecutions = null;
        
        testScenarioExecutions = scenarioExecutionService.findByScenarioExecutionId(id);
        assertNotNull(testScenarioExecutions);

        for(Scenarioexecution se : testScenarioExecutions) {    // only one se
            log.info("ScenarioExecutionServiceImplTest.testFindByScenarioExecutionId(): Scenario Execution Participant Name --->>" +
                    se.getParticipant().getParticipantName());
        }
    }

    /**
     * Test of searchByCriteria method, of class ScenarioExecutionServiceImpl.
     */
//    @Test
    public void testSearchByCriteria() throws Exception {


        /*
        System.out.println("searchByCriteria");
        Scenarioexecution criteria = null;
        List<Criterion> criterion = null;
        ScenarioExecutionServiceImpl instance = new ScenarioExecutionServiceImpl();
        List expResult = null;
        List result = instance.searchByCriteria(criteria, criterion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}