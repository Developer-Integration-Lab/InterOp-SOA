package net.aegis.lab.patient.service;

import org.junit.Test;
import net.aegis.lab.dao.pojo.Patient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Abhay.Bakshi
 */
public class PatientServiceImplTest {

    private static final Log log = LogFactory.getLog(PatientServiceImplTest.class);
    private PatientService patientService;

    public PatientServiceImplTest() {
        log.info("PatientServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("PatientServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("PatientServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("PatientServiceImplTest.setUp() - INFO");
        patientService = (PatientService) ContextUtil.getLabContext().getBean("patientService");
    }

    @After
    public void tearDown() {
        log.info("PatientServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class PatientServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("PatientServiceImplTest.testCreate(): start - INFO");

        String resultID = "-999";
        Patient testPatient = null;

        testPatient = new Patient();

        testPatient.setPatientId("88888");
        testPatient.setFirstName("First Name Test");
        testPatient.setLastName("Last Name Test");

        resultID = patientService.create(testPatient);
        assertTrue(("Test case schedule to be created with id=" + resultID), (Integer.parseInt(testPatient.getPatientId())) > 0);
        log.info("PatientServiceImplTest.testCreate(): Patient Result ID --->>" + testPatient.getPatientId());
    }

    /**
     * Test of read method, of class PatientServiceImpl.
     */
    @Test
    public void testRead() throws Exception {

        log.info("PatientServiceImplTest.testRead(): start - INFO");
        String id = "00001";
        Patient testPatient = null;
        testPatient = patientService.read(id);
        assertNotNull(testPatient);
        log.info("PatientServiceImplTest.testRead(): Patient Result ID --->>" + testPatient.getPatientId());

    }

    @Test
    public void testReadLabAccessFilter() throws Exception {
        // We could this to call API that filters based on LabAccessFilter
        Patient testPatient = patientService.read("00001");
        assertNotNull(testPatient);

        //Story 51: Integrate Conformance Tests - Core Library changes
        assertNotNull(testPatient.getLabAccessFilter());
        log.info("PatientServiceImplTest.testRead(): labAccessFilter--->>" + testPatient.getLabAccessFilter());
    }

    /**
     * Test of update method, of class PatientServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("PatientServiceImplTest.testUpdate(): start - INFO");

        String resultID = "-999";
        Patient testPatientToCreate = null;
        Patient testPatientToFind = null;

        testPatientToCreate = new Patient();

        testPatientToCreate.setPatientId("99999");
        testPatientToCreate.setFirstName("First Name Test");
        testPatientToCreate.setLastName("Last Name Test");

        resultID = patientService.create(testPatientToCreate);
        assertTrue(("Test case schedule to be created with id=" + resultID), (Integer.parseInt(testPatientToCreate.getPatientId())) > 0);
        log.info("PatientServiceImplTest.testUpdate(): Patient Result ID --->>" + testPatientToCreate.getPatientId());

        // change a parameter in order to update the patient created
        testPatientToCreate.setFirstName("Changed_First_Name_Test");
        patientService.update(testPatientToCreate);

        // find the updated participant and display his/her name
        testPatientToFind = patientService.read(resultID);
        log.info("PatientServiceImplTest.testUpdate(): Changed Patient Result --->>" + testPatientToFind.getFirstName());
    }

    /**
     * Test of delete method, of class PatientServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("PatientServiceImplTest.testDelete(): start - INFO");

        String resultID = "-999";
        Patient testPatient = null;

        testPatient = new Patient();
        testPatient.setPatientId("101010");
        testPatient.setFirstName("First Name Test");
        testPatient.setLastName("Last Name Test");

        resultID = patientService.create(testPatient);
        assertTrue(("Test case schedule to be created with id=" + resultID), (Integer.parseInt(testPatient.getPatientId())) > 0);
        log.info("PatientServiceImplTest.testDelete(): Patient Result ID --->>" + testPatient.getPatientId());

        patientService.delete(testPatient);
        log.info("PatientServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of search method, of class PatientServiceImpl.
     */
//    @Test
    public void testSearch() throws Exception {
        /*
        System.out.println("search");
        Patient criteria = null;
        PatientServiceImpl instance = new PatientServiceImpl();
        List expResult = null;
        List result = instance.search(criteria);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         */
    }
}
