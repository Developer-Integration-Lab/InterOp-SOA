package net.aegis.lab.caseexecution.service;

import java.util.List;

import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.scenarioexecution.service.ScenarioExecutionService;
import net.aegis.lab.testcase.service.TestCaseService;
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
public class CaseExecutionServiceImplTest {

    private static final Log log = LogFactory.getLog(CaseExecutionServiceImplTest.class);
    private CaseExecutionService caseExecutionService;
    private ScenarioExecutionService scenarioExecutionService;
    private ParticipantService participantService;
    private TestCaseService testCaseService;

    public CaseExecutionServiceImplTest() {
        log.info("CaseExecutionServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("CaseExecutionServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("CaseExecutionServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("CaseExecutionServiceImplTest.setUp() - INFO");
        caseExecutionService = (CaseExecutionService)ContextUtil.getLabContext().getBean("caseExecutionService");
        scenarioExecutionService = (ScenarioExecutionService)ContextUtil.getLabContext().getBean("scenarioExecutionService");
        participantService = (ParticipantService)ContextUtil.getLabContext().getBean("participantService");
        testCaseService = (TestCaseService)ContextUtil.getLabContext().getBean("testCaseService");
    }

    @After
    public void tearDown() {
        log.info("CaseExecutionServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("CaseExecutionServiceImplTest.testCreate(): start - INFO");

        Integer resultID = -999;
        Testcase testCase = null;
        Caseexecution testCaseexecution = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        Integer testCaseID = 1;             // at least one test case expected in DB
        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB

        testCase = testCaseService.read(testCaseID);
        assertNotNull(testCase);

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);
        testScenarioExecution.setParticipant(testParticipant);


        testCaseexecution = new Caseexecution();
        testCaseexecution.setScenarioexecution(testScenarioExecution);
        testCaseexecution.setTestcase(testCase);
        testCaseexecution.setPatientId("00037-testCreate");
        testCaseexecution.setParticipatingRIs("1^2-testCreate");
        testCaseexecution.setStatus("ACTIVE TEST");
        testCaseexecution.setMessageType("PD TEST");


        resultID = caseExecutionService.create(testCaseexecution);
        assertTrue(("Test case execution to be created with id="+resultID), (testCaseexecution.getCaseExecutionId()) > 0);
        log.info("CaseExecutionServiceImplTest.testCreate(): Case execution Result ID --->>" + testCaseexecution.getCaseExecutionId());
    }

    /**
     * Test of read method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("CaseExecutionServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Caseexecution testCaseexecution = null;
        testCaseexecution = caseExecutionService.read(id);
        assertNotNull(testCaseexecution);
        log.info("CaseExecutionServiceImplTest.testRead(): Case execution Result ID --->>" + testCaseexecution.getCaseExecutionId());
    }


    /**
     * Test of update method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("CaseExecutionServiceImplTest.testUpdate(): start - INFO");

        Integer resultID = -999;
        Testcase testCase = null;
        Caseexecution testCaseexecutionToCreate = null;
        Caseexecution testCaseexecutionToFind = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        Integer testCaseID = 1;             // at least one test case expected in DB
        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB

        testCase = testCaseService.read(testCaseID);
        assertNotNull(testCase);

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);
        testScenarioExecution.setParticipant(testParticipant);

        testCaseexecutionToCreate = new Caseexecution();
        testCaseexecutionToCreate.setScenarioexecution(testScenarioExecution);
        testCaseexecutionToCreate.setTestcase(testCase);
        testCaseexecutionToCreate.setPatientId("00037-testCreate");
        testCaseexecutionToCreate.setParticipatingRIs("1^2-testCreate");
        testCaseexecutionToCreate.setStatus("ACTIVE TEST");
        testCaseexecutionToCreate.setMessageType("PD TEST");


        resultID = caseExecutionService.create(testCaseexecutionToCreate);
        assertTrue(("Test case execution to be created with id="+resultID), (testCaseexecutionToCreate.getCaseExecutionId()) > 0);
        log.info("CaseExecutionServiceImplTest.testUpdate(): Case execution Result ID --->>" + testCaseexecutionToCreate.getCaseExecutionId());

        // change a parameter in order to update the case result created
        testCaseexecutionToCreate.setPatientId("00037-testUpdate CaseExecutionServiceImplTest");
        caseExecutionService.update(testCaseexecutionToCreate);

        // find the updated participant and display his/her name
        testCaseexecutionToFind = caseExecutionService.findByCaseExecutionId(resultID);
        log.info("CaseExecutionServiceImplTest.testUpdate(): Changed Result --->>" + testCaseexecutionToFind.getPatientId());

    }

    /**
     * Test of delete method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("CaseExecutionServiceImplTest.testDelete(): start - INFO");

        Integer resultID = -999;
        Testcase testCase = null;
        Caseexecution testCaseexecutionToCreate = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        Integer testCaseID = 1;             // at least one test case expected in DB
        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB

        testCase = testCaseService.read(testCaseID);
        assertNotNull(testCase);

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);
        testScenarioExecution.setParticipant(testParticipant);


        testCaseexecutionToCreate = new Caseexecution();
        testCaseexecutionToCreate.setScenarioexecution(testScenarioExecution);
        testCaseexecutionToCreate.setTestcase(testCase);
        testCaseexecutionToCreate.setPatientId("00037-testCreate");
        testCaseexecutionToCreate.setParticipatingRIs("1^2-testCreate");
        testCaseexecutionToCreate.setStatus("ACTIVE TEST");
        testCaseexecutionToCreate.setMessageType("PD TEST");


        resultID = caseExecutionService.create(testCaseexecutionToCreate);
        assertTrue(("Test case execution to be created with id="+resultID), (testCaseexecutionToCreate.getCaseExecutionId()) > 0);
        log.info("CaseExecutionServiceImplTest.testDelete(): Case execution Result ID --->>" + testCaseexecutionToCreate.getCaseExecutionId());

        caseExecutionService.delete(testCaseexecutionToCreate);
        log.info("CaseExecutionServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("CaseExecutionServiceImplTest.testDeleteById(): start - INFO");

        Integer resultID = -999;
        Testcase testCase = null;
        Caseexecution testCaseexecutionToCreate = null;
        Scenarioexecution testScenarioExecution = null;
        Participant testParticipant = null;

        Integer testCaseID = 1;             // at least one test case expected in DB
        Integer participantID = 1;            // at least one participant expected in DB
        Integer scenarioExecutionID = 1;    // at least one scenario expected in DB

        testCase = testCaseService.read(testCaseID);
        assertNotNull(testCase);

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testScenarioExecution = scenarioExecutionService.read(scenarioExecutionID);
        assertNotNull(testScenarioExecution);
        testScenarioExecution.setParticipant(testParticipant);


        testCaseexecutionToCreate = new Caseexecution();
        testCaseexecutionToCreate.setScenarioexecution(testScenarioExecution);
        testCaseexecutionToCreate.setTestcase(testCase);
        testCaseexecutionToCreate.setPatientId("00037-testCreate");
        testCaseexecutionToCreate.setParticipatingRIs("1^2-testCreate");
        testCaseexecutionToCreate.setStatus("ACTIVE TEST");
        testCaseexecutionToCreate.setMessageType("PD TEST");


        resultID = caseExecutionService.create(testCaseexecutionToCreate);
        assertTrue(("Test case execution to be created with id="+resultID), (testCaseexecutionToCreate.getCaseExecutionId()) > 0);
        log.info("CaseExecutionServiceImplTest.testDeleteById(): Case execution Result ID --->>" + testCaseexecutionToCreate.getCaseExecutionId());

        caseExecutionService.deleteById(resultID);
        log.info("CaseExecutionServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of getCaseExecIdByMsgTypeAndPatientId method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testGetCaseExecIdByMsgTypeAndPatientId() throws Exception {
        /*
        System.out.println("getCaseExecIdByMsgTypeAndPatientId");
        String messageType = "";
        String patientId = "";
        Participant participant = null;
        CaseExecutionServiceImpl instance = new CaseExecutionServiceImpl();
        int expResult = 0;
        int result = instance.getCaseExecIdByMsgTypeAndPatientId(messageType, patientId, participant);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of findByScenarioExecutionId method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testFindByScenarioExecutionId() throws Exception {
        /*
        System.out.println("findByScenarioExecutionId");
        int scenarioExecutionId = 0;
        CaseExecutionServiceImpl instance = new CaseExecutionServiceImpl();
        List expResult = null;
        List result = instance.findByScenarioExecutionId(scenarioExecutionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of findByCaseExecutionId method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testFindByCaseExecutionId() throws Exception {
        /*
        System.out.println("findByCaseExecutionId");
        int caseExecutionId = 0;
        CaseExecutionServiceImpl instance = new CaseExecutionServiceImpl();
        Caseexecution expResult = null;
        Caseexecution result = instance.findByCaseExecutionId(caseExecutionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         */
}

    /**
     * Test of getParticipatingRIs method, of class CaseExecutionServiceImpl.
     */
//    @Test
    public void testGetParticipatingRIs() throws Exception {
        /*
        System.out.println("getParticipatingRIs");
        int caseExecId = 0;
        CaseExecutionServiceImpl instance = new CaseExecutionServiceImpl();
        int[] expResult = null;
        int[] result = instance.getParticipatingRIs(caseExecId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }
    
    /*@Test
    public void findByParticipantForActiveScenarioCase(){
    	Integer participantid =11;
    	int scenarioId= 1;
    	int caseId=1;
    	caseExecutionService.findByParticipantForActiveScenarioCase(participantid,scenarioId, caseId);
    }
    */
    @Test
    public void getPatientIdForRD(){
    	try {
			String documentuniqueid = "%1.VEN21000008.22222";
			String communityId = "%2.16.840.1.113883.0.210"; 
			Integer participantid =11;
			
			String patientId = 
				caseExecutionService.getParticipantPatientIdForRD(participantid, communityId,documentuniqueid);
			if(patientId!=null){
				log.info("PatientId=== " + patientId);
			}
			log.trace("Trace is enabled");
			log.debug("Debug is enabled");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}