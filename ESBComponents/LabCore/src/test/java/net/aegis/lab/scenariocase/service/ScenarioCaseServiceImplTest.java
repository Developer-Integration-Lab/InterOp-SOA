package net.aegis.lab.scenariocase.service;

import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Scenariocase.ScenariocasePK;
import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.patient.service.PatientService;
import net.aegis.lab.scenario.service.ScenarioService;
import net.aegis.lab.serviceset.service.ServiceSetService;
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
public class ScenarioCaseServiceImplTest {

    private static final Log log = LogFactory.getLog(ScenarioCaseServiceImplTest.class);
    private ScenarioCaseService testScenarioCaseService;
    private TestCaseService testCaseService;
    private ScenarioService scenarioService;
    private PatientService patientService;
    private ServiceSetService serviceSetService;    // to create new Scenario

    public ScenarioCaseServiceImplTest() {
        log.info("ScenarioCaseServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ScenarioCaseServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ScenarioCaseServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ScenarioCaseServiceImplTest.setUp() - INFO");
        testScenarioCaseService = (ScenarioCaseService)ContextUtil.getLabContext().getBean("scenarioCaseService");
        testCaseService = (TestCaseService)ContextUtil.getLabContext().getBean("testCaseService");
        scenarioService = (ScenarioService)ContextUtil.getLabContext().getBean("scenarioService");
        patientService = (PatientService) ContextUtil.getLabContext().getBean("patientService");
        serviceSetService = (ServiceSetService)ContextUtil.getLabContext().getBean("serviceSetService");
    }

    @After
    public void tearDown() {
        log.info("ScenarioCaseServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ScenarioCaseServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ScenarioCaseServiceImplTest.testCreate(): start - INFO");

        Scenariocase testScenarioCase = null;
        ScenariocasePK testScenariocasePK = null;
        Testcase testTestCase = null;               // part of composite key
        Scenario testScenario = null;               // part of composite key
        Patient testPatient = null;

        testScenarioCase = new Scenariocase();
        testScenariocasePK = new Scenariocase.ScenariocasePK();

        //
        // read existing test case
        //
        log.info("ScenarioCaseServiceImplTest.testCreate(): Reading a Test Case from DB - INFO");
        Integer testCaseID = 1;              // at least one test case expected in DB
        testTestCase = testCaseService.read(testCaseID);
        assertNotNull(testTestCase);
        log.info("ScenarioCaseServiceImplTest.testCreate(): Test Case Name --->>" + testTestCase.getName());

        //
        // create new scenario
        //
        log.info("ScenarioCaseServiceImplTest.testCreate(): Creating a new Scenario from DB - INFO");
        Integer scenarioID = -999;          // at least one scenario expected in DB
        Integer serviceSetID = 1;           // at least one service set expected in DB
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

        scenarioID = scenarioService.create(testScenario);
        assertTrue(("Test scenario to be created with id="+scenarioID), (testScenario.getScenarioId()) > 0);
        log.info("ScenarioCaseServiceImplTest.testCreate(): Scenario Name --->>" + testScenario.getScenarioName());

        //
        // read existing patient
        //
        log.info("ScenarioCaseServiceImplTest.testCreate(): Reading a patient from DB - INFO");
        String patientID = "00001";
        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ScenarioCaseServiceImplTest.testCreate(): Patient Name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());


        //
        // now proceed to create a test scenario case using composite primary key and patient
        //
        testScenariocasePK.setCaseId(testCaseID);       // existing test case
        testScenariocasePK.setScenarioId(scenarioID);   // newly created scenario
        testScenarioCase.setScenariocasePK(testScenariocasePK);
        testScenarioCase.setPatientId(testPatient.getPatientId());
        testScenarioCase.setParticipatingRIs("test participating RIs");

        testScenariocasePK = testScenarioCaseService.create(testScenarioCase);
        assertTrue(("Test scenario case to be created with Primary Key="+testScenariocasePK), (testScenarioCase.getScenariocasePK().getCaseId()>0));
        log.info("ScenarioCaseServiceImplTest.testCreate(): Scenario Case : Case Result ID --->>" + testScenarioCase.getScenariocasePK().getCaseId());
        log.info("ScenarioCaseServiceImplTest.testCreate(): Scenario Case : Scenario Result ID --->>" + testScenarioCase.getScenariocasePK().getScenarioId());
    }

    /**
     * Test of read method, of class ScenarioCaseServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("ScenarioCaseServiceImplTest.testRead(): start - INFO");
        Scenariocase testScenarioCase = null;
        Testcase testTestCase = null;               // part of composite key
        Scenario testScenario = null;               // part of composite key
        ScenariocasePK testScenariocasePK = null;

        testScenarioCase = new Scenariocase();
        testScenariocasePK = new Scenariocase.ScenariocasePK();
        
        //
        // read existing test case
        //
        log.info("ScenarioCaseServiceImplTest.testRead(): Reading a Test Case from DB - INFO");
        Integer testCaseID = 1;              // at least one test case expected in DB
        testTestCase = testCaseService.read(testCaseID);
        assertNotNull(testTestCase);
        log.info("ScenarioCaseServiceImplTest.testRead(): Test Case Name --->>" + testTestCase.getName());

        //
        // read existing scenario
        //
        log.info("ScenarioCaseServiceImplTest.testRead(): Reading a Scenario from DB - INFO");
        Integer scenarioID = 1;              // at least one scenario expected in DB
        testScenario = scenarioService.read(scenarioID);
        assertNotNull(testScenario);
        log.info("ScenarioCaseServiceImplTest.testRead(): Scenario Name --->>" + testScenario.getScenarioName());


        testScenariocasePK.setCaseId(testTestCase.getCaseId());
        testScenariocasePK.setScenarioId(testScenario.getScenarioId());
        testScenarioCase.setScenariocasePK(testScenariocasePK);

        //
        // now read test scenario case from DB
        //
        testScenarioCase = testScenarioCaseService.read(testScenariocasePK);
        assertNotNull(testScenarioCase);
        log.info("ScenarioCaseServiceImplTest.testRead(): Scenario Case: Scenario Name --->>" 
                    + testScenarioCase.getScenario().getScenarioName() +
                    " and Test Case Name --->>"
                    + testScenarioCase.getTestcase().getName());
    }

    /**
     * Test of update method, of class ScenarioCaseServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ScenarioCaseServiceImplTest.testUpdate(): start - INFO");

        Scenariocase testScenarioCaseToCreate = null;
        Scenariocase testScenarioCaseToFind = null;
        ScenariocasePK testScenariocasePK = null;
        Testcase testTestCase = null;               // part of composite key
        Scenario testScenario = null;               // part of composite key
        Patient testPatient = null;

        testScenarioCaseToCreate = new Scenariocase();
        testScenariocasePK = new Scenariocase.ScenariocasePK();

        //
        // read existing test case
        //
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Reading a Test Case from DB - INFO");
        Integer testCaseID = 1;              // at least one test case expected in DB
        testTestCase = testCaseService.read(testCaseID);
        assertNotNull(testTestCase);
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Test Case Name --->>" + testTestCase.getName());

        //
        // create new scenario
        //
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Creating a new Scenario from DB - INFO");
        Integer scenarioID = -999;          // at least one scenario expected in DB
        Integer serviceSetID = 1;           // at least one service set expected in DB
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

        scenarioID = scenarioService.create(testScenario);
        assertTrue(("Test scenario to be created with id="+scenarioID), (testScenario.getScenarioId()) > 0);
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Scenario Name --->>" + testScenario.getScenarioName());

        //
        // read existing patient
        //
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Reading a patient from DB - INFO");
        String patientID = "00001";
        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Patient Name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());


        //
        // now proceed to create a test scenario case using composite primary key and patient
        //
        testScenariocasePK.setCaseId(testCaseID);       // existing test case
        testScenariocasePK.setScenarioId(scenarioID);   // newly created scenario
        testScenarioCaseToCreate.setScenariocasePK(testScenariocasePK);
        testScenarioCaseToCreate.setPatientId(testPatient.getPatientId());
        testScenarioCaseToCreate.setParticipatingRIs("test participating RIs");

        testScenariocasePK = testScenarioCaseService.create(testScenarioCaseToCreate);
        assertTrue(("Test scenario case to be created with Primary Key="+testScenariocasePK), (testScenarioCaseToCreate.getScenariocasePK().getCaseId()>0));
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Scenario Case : Case Result ID --->>" + testScenarioCaseToCreate.getScenariocasePK().getCaseId());
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Scenario Case : Scenario Result ID --->>" + testScenarioCaseToCreate.getScenariocasePK().getScenarioId());


        // change a parameter in order to update the scenario created
        testScenarioCaseToCreate.setParticipatingRIs("test updated participating RIs");
        testScenarioCaseService.update(testScenarioCaseToCreate);

        // find the updated participant audit history record and display changed information
        testScenarioCaseToFind = testScenarioCaseService.read(testScenariocasePK);
        log.info("ScenarioCaseServiceImplTest.testUpdate(): Updated Participating RIs --->>" + testScenarioCaseToFind.getParticipatingRIs());
    }

    /**
     * Test of delete method, of class ScenarioCaseServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ScenarioCaseServiceImplTest.testDelete(): start - INFO");

        Scenariocase testScenarioCase = null;
        ScenariocasePK testScenariocasePK = null;
        Testcase testTestCase = null;               // part of composite key
        Scenario testScenario = null;               // part of composite key
        Patient testPatient = null;

        testScenarioCase = new Scenariocase();
        testScenariocasePK = new Scenariocase.ScenariocasePK();

        //
        // read existing test case
        //
        log.info("ScenarioCaseServiceImplTest.testDelete(): Reading a Test Case from DB - INFO");
        Integer testCaseID = 1;              // at least one test case expected in DB
        testTestCase = testCaseService.read(testCaseID);
        assertNotNull(testTestCase);
        log.info("ScenarioCaseServiceImplTest.testDelete(): Test Case Name --->>" + testTestCase.getName());

        //
        // create new scenario
        //
        log.info("ScenarioCaseServiceImplTest.testDelete(): Creating a new Scenario from DB - INFO");
        Integer scenarioID = -999;          // at least one scenario expected in DB
        Integer serviceSetID = 1;           // at least one service set expected in DB
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

        scenarioID = scenarioService.create(testScenario);
        assertTrue(("Test scenario to be created with id="+scenarioID), (testScenario.getScenarioId()) > 0);
        log.info("ScenarioCaseServiceImplTest.testDelete(): Scenario Name --->>" + testScenario.getScenarioName());

        //
        // read existing patient
        //
        log.info("ScenarioCaseServiceImplTest.testDelete(): Reading a patient from DB - INFO");
        String patientID = "00001";
        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ScenarioCaseServiceImplTest.testDelete(): Patient Name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());


        //
        // now proceed to create a test scenario case using composite primary key and patient
        //
        testScenariocasePK.setCaseId(testCaseID);       // existing test case
        testScenariocasePK.setScenarioId(scenarioID);   // newly created scenario
        testScenarioCase.setScenariocasePK(testScenariocasePK);
        testScenarioCase.setPatientId(testPatient.getPatientId());
        testScenarioCase.setParticipatingRIs("test participating RIs");

        testScenariocasePK = testScenarioCaseService.create(testScenarioCase);
        assertTrue(("Test scenario case to be created with Primary Key="+testScenariocasePK), (testScenarioCase.getScenariocasePK().getCaseId()>0));
        log.info("ScenarioCaseServiceImplTest.testDelete(): Scenario Case : Case Result ID --->>" + testScenarioCase.getScenariocasePK().getCaseId());
        log.info("ScenarioCaseServiceImplTest.testDelete(): Scenario Case : Scenario Result ID --->>" + testScenarioCase.getScenariocasePK().getScenarioId());

        testScenarioCaseService.delete(testScenarioCase);
        scenarioService.deleteById(scenarioID);     // also detele newly created test scenario
        log.info("ScenarioCaseServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteByPK method, of class ScenarioCaseServiceImpl.
     */
//    @Test
    public void testDeleteByPK() throws Exception {

        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): start - INFO");

        Scenariocase testScenarioCase = null;
        ScenariocasePK testScenariocasePK = null;
        Testcase testTestCase = null;               // part of composite key
        Scenario testScenario = null;               // part of composite key
        Patient testPatient = null;

        testScenarioCase = new Scenariocase();
        testScenariocasePK = new Scenariocase.ScenariocasePK();

        //
        // read existing test case
        //
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Reading a Test Case from DB - INFO");
        Integer testCaseID = 1;              // at least one test case expected in DB
        testTestCase = testCaseService.read(testCaseID);
        assertNotNull(testTestCase);
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Test Case Name --->>" + testTestCase.getName());

        //
        // create new scenario
        //
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Creating a new Scenario from DB - INFO");
        Integer scenarioID = -999;          // at least one scenario expected in DB
        Integer serviceSetID = 1;           // at least one service set expected in DB
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

        scenarioID = scenarioService.create(testScenario);
        assertTrue(("Test scenario to be created with id="+scenarioID), (testScenario.getScenarioId()) > 0);
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Scenario Name --->>" + testScenario.getScenarioName());

        //
        // read existing patient
        //
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Reading a patient from DB - INFO");
        String patientID = "00001";
        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Patient Name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());


        //
        // now proceed to create a test scenario case using composite primary key and patient
        //
        testScenariocasePK.setCaseId(testCaseID);       // existing test case
        testScenariocasePK.setScenarioId(scenarioID);   // newly created scenario
        testScenarioCase.setScenariocasePK(testScenariocasePK);
        testScenarioCase.setPatientId(testPatient.getPatientId());
        testScenarioCase.setParticipatingRIs("test participating RIs");

        testScenariocasePK = testScenarioCaseService.create(testScenarioCase);
        assertTrue(("Test scenario case to be created with Primary Key="+testScenariocasePK), (testScenarioCase.getScenariocasePK().getCaseId()>0));
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Scenario Case : Case Result ID --->>" + testScenarioCase.getScenariocasePK().getCaseId());
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): Scenario Case : Scenario Result ID --->>" + testScenarioCase.getScenariocasePK().getScenarioId());

        testScenarioCaseService.deleteByPK(testScenariocasePK);
        scenarioService.deleteById(scenarioID);     // also detele newly created test scenario
        log.info("ScenarioCaseServiceImplTest.testDeleteByPK(): end - INFO");
    }

    /**
     * Test of getPatientData method, of class ScenarioCaseServiceImpl.
     */
//    @Test
    public void testGetPatientData() throws Exception {


        /*
        System.out.println("getPatientData");
        int scenarioId = 0;
        int caseId = 0;
        ScenarioCaseServiceImpl instance = new ScenarioCaseServiceImpl();
        File expResult = null;
        File result = instance.getPatientData(scenarioId, caseId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of getParticipatingRIs method, of class ScenarioCaseServiceImpl.
     */
//    @Test
    public void testGetParticipatingRIs() throws Exception {


        /*
        System.out.println("getParticipatingRIs");
        int scenarioId = 0;
        int caseId = 0;
        ScenarioCaseServiceImpl instance = new ScenarioCaseServiceImpl();
        int[] expResult = null;
        int[] result = instance.getParticipatingRIs(scenarioId, caseId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}