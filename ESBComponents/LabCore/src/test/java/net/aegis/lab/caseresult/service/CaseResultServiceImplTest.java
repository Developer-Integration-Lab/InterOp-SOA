package net.aegis.lab.caseresult.service;

import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.caseexecution.service.CaseExecutionService;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.scenarioexecution.service.ScenarioExecutionService;
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
public class CaseResultServiceImplTest {

    private static final Log log = LogFactory.getLog(CaseResultServiceImplTest.class);
    private CaseResultService caseResultService;
    private CaseExecutionService caseExecutionService;
    private ScenarioExecutionService scenarioExecutionService;
    private ParticipantService participantService;

    public CaseResultServiceImplTest() {
        log.info("CaseResultServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("CaseResultServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("CaseResultServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("CaseResultServiceImplTest.setUp() - INFO");
        caseResultService = (CaseResultService)ContextUtil.getLabContext().getBean("caseResultService");
        caseExecutionService = (CaseExecutionService)ContextUtil.getLabContext().getBean("caseExecutionService");
        scenarioExecutionService = (ScenarioExecutionService)ContextUtil.getLabContext().getBean("scenarioExecutionService");
        participantService = (ParticipantService)ContextUtil.getLabContext().getBean("participantService");
    }

    @After
    public void tearDown() {
        log.info("CaseResultServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class CaseResultServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("CaseResultServiceImplTest.testCreate(): start - INFO");

        Integer resultID = -999;
        Caseresult testCaseresult = null;
        Caseexecution testCaseexecution = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        testCaseresult = new Caseresult();

        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB
        Integer caseExecutionID = 1;        // at least one caseexecution expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);

        testCaseexecution = caseExecutionService.read(caseExecutionID);
        assertNotNull(testCaseexecution);

        testScenarioExecution.setParticipant(testParticipant);
        testCaseexecution.setScenarioexecution(testScenarioExecution);
        testCaseresult.setCaseexecution(testCaseexecution);
        testCaseresult.setResult("PASS TEST CREATE");
        testCaseresult.setSubmissionInd("N");

        // create a case result
        resultID = caseResultService.create(testCaseresult);

        assertTrue(("Test case result to be created with id="+resultID), (testCaseresult.getResultId()) > 0);
        log.info("CaseResultServiceImplTest.testCreate(): Case Result ID --->>" + testCaseresult.getResultId());
    }

    /**
     * Test of read method, of class CaseResultServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {
        log.info("CaseResultServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Caseresult testCaseresult = null;
        testCaseresult = caseResultService.read(id);
        assertNotNull(testCaseresult);
        log.info("CaseResultServiceImplTest.testRead(): Case Result ID --->>" + testCaseresult.getResultId());
    }

    /**
     * Test of update method, of class CaseResultServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("CaseResultServiceImplTest.testUpdate(): start - INFO");

        Integer resultID = -999;
        Caseresult testCaseResultToCreate = null;
        Caseresult testCaseResultToFind = null;
        Caseexecution testCaseexecution = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        testCaseResultToCreate = new Caseresult();

        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB
        Integer caseExecutionID = 1;        // at least one caseexecution expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);

        testCaseexecution = caseExecutionService.read(caseExecutionID);
        assertNotNull(testCaseexecution);

        testScenarioExecution.setParticipant(testParticipant);
        testCaseexecution.setScenarioexecution(testScenarioExecution);
        testCaseResultToCreate.setCaseexecution(testCaseexecution);
        testCaseResultToCreate.setResult("PASS TEST CREATE");
        testCaseResultToCreate.setSubmissionInd("N");

        // create a case result so that it can be updated
        resultID = caseResultService.create(testCaseResultToCreate);
        assertTrue(("Test case result to be created with id="+resultID), (testCaseResultToCreate.getResultId()) > 0);
        log.info("CaseResultServiceImplTest.testUpdate(): Case Result ID --->>" + testCaseResultToCreate.getResultId());

        // change a parameter in order to update the case result created
        testCaseResultToCreate.setResult("PASS TEST UPDATE");
        caseResultService.update(testCaseResultToCreate);

        // find the updated participant and display his/her name
        testCaseResultToFind = caseResultService.findByResultId(resultID);
        log.info("CaseResultServiceImplTest.testUpdate(): Changed Result --->>" + testCaseResultToFind.getResult());
    }

    /**
     * Test of delete method, of class CaseResultServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("CaseResultServiceImplTest.testDelete(): start - INFO");

        Integer resultID = -999;
        Caseresult testCaseResultToCreate = null;
        Caseexecution testCaseexecution = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        testCaseResultToCreate = new Caseresult();

        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB
        Integer caseExecutionID = 1;        // at least one caseexecution expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);

        testCaseexecution = caseExecutionService.read(caseExecutionID);
        assertNotNull(testCaseexecution);

        testScenarioExecution.setParticipant(testParticipant);
        testCaseexecution.setScenarioexecution(testScenarioExecution);
        testCaseResultToCreate.setCaseexecution(testCaseexecution);
        testCaseResultToCreate.setResult("PASS TEST CREATE");
        testCaseResultToCreate.setSubmissionInd("N");

        // create a case result so that it can be deleted
        resultID = caseResultService.create(testCaseResultToCreate);
        assertTrue(("Test case result to be created with id="+resultID), (testCaseResultToCreate.getResultId()) > 0);
        log.info("CaseResultServiceImplTest.testDelete(): Case Result ID --->>" + testCaseResultToCreate.getResultId());

        caseResultService.delete(testCaseResultToCreate);
        log.info("CaseResultServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class CaseResultServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("CaseResultServiceImplTest.testDeleteById(): start - INFO");

        Integer resultID = -999;
        Caseresult testCaseResultToCreate = null;
        Caseexecution testCaseexecution = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        testCaseResultToCreate = new Caseresult();

        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB
        Integer caseExecutionID = 1;        // at least one caseexecution expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);

        testCaseexecution = caseExecutionService.read(caseExecutionID);
        assertNotNull(testCaseexecution);

        testScenarioExecution.setParticipant(testParticipant);
        testCaseexecution.setScenarioexecution(testScenarioExecution);
        testCaseResultToCreate.setCaseexecution(testCaseexecution);
        testCaseResultToCreate.setResult("PASS TEST CREATE");
        testCaseResultToCreate.setSubmissionInd("N");

        // create a case result so that it can be deleted
        resultID = caseResultService.create(testCaseResultToCreate);
        assertTrue(("Test case result to be created with id="+resultID), (testCaseResultToCreate.getResultId()) > 0);
        log.info("CaseResultServiceImplTest.testDeleteById(): Case Result ID --->>" + testCaseResultToCreate.getResultId());

        caseResultService.deleteById(resultID);
        log.info("CaseResultServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of findByResultId method, of class CaseResultServiceImpl.
     */
//    @Test
    public void testFindByResultId() throws Exception {

        log.info("CaseResultServiceImplTest.testFindByResultId(): start - INFO");
        Integer id = 1;
        Caseresult testCaseresult = null;
        testCaseresult = caseResultService.read(id);
        assertNotNull(testCaseresult);
        log.info("CaseResultServiceImplTest.testFindByResultId(): Case Result ID --->>" + testCaseresult.getResultId());
    }
    
    @Test
    public void testFindByParticipantForActivePassQD(){
    	
    	Caseresult caseresult = caseResultService.findByParticipantForActivePassQD(11, null, "Y");
    	log.info("caseResultId==" + caseresult.getResultId());
    	
    }

}