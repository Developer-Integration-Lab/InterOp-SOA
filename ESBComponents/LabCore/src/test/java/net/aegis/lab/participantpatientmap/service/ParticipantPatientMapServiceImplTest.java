package net.aegis.lab.participantpatientmap.service;

import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Participantpatientmap;
import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.patient.service.PatientService;
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
 * mysql> desc participantpatientmap;
 * +-----------------------+------------------+------+-----+---------+----------------+
 * | Field                 | Type             | Null | Key | Default | Extra          |
 * +-----------------------+------------------+------+-----+---------+----------------+
 * | participantPatientMapId | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
 * | participantId           | int(10) unsigned | NO   | MUL | NULL    |                |
 * | patientId             | varchar(64)      | NO   | MUL | NULL    |                |
 * | participantPatientId    | varchar(64)      | YES  |     | NULL    |                |
 * | createtime            | datetime         | YES  |     | NULL    |                |
 * | createuser            | varchar(45)      | NO   |     |         |                |
 * | changedtime           | datetime         | YES  |     | NULL    |                |
 * | changeduser           | varchar(45)      | NO   |     |         |                |
 * +-----------------------+------------------+------+-----+---------+----------------+
 * 
 * @author Abhay.Bakshi
 */
public class ParticipantPatientMapServiceImplTest {

    private static final Log log = LogFactory.getLog(ParticipantPatientMapServiceImplTest.class);
    private ParticipantPatientMapService participantPatientMapService;
    private PatientService patientService;
    private ParticipantService participantService;

    public ParticipantPatientMapServiceImplTest() {
        log.info("ParticipantPatientMapServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ParticipantPatientMapServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ParticipantPatientMapServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ParticipantPatientMapServiceImplTest.setUp() - INFO");
        participantPatientMapService = (ParticipantPatientMapService) ContextUtil.getLabContext().getBean("participantPatientMapService");
        patientService = (PatientService)ContextUtil.getLabContext().getBean("patientService");
        participantService = (ParticipantService)ContextUtil.getLabContext().getBean("participantService");
    }

    @After
    public void tearDown() {
        log.info("ParticipantPatientMapServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ParticipantPatientMapServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ParticipantPatientMapServiceImplTest.testCreate(): start - INFO");

        Participantpatientmap testParticipantPatientMap = null;
        Integer resultID = null;
        Integer participantID = 1;                    // at least one participant expected in DB
        String patientID = "00001";                 // at least one patient expected in DB
        Participant testParticipant = null;
        Patient testPatient = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ParticipantPatientMapServiceImplTest.testCreate(): Participant name --->>" + testParticipant.getParticipantName());

        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ParticipantPatientMapServiceImplTest.testCreate(): Patient full name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());

        testParticipantPatientMap = new Participantpatientmap();
        testParticipantPatientMap.setParticipant(testParticipant);
        testParticipantPatientMap.setPatient(testPatient);
        testParticipantPatientMap.setCreateuser("test create user");
        testParticipantPatientMap.setChangeduser("test changed user");

        resultID = participantPatientMapService.create(testParticipantPatientMap);
        assertTrue(("Test participant patient map to be created with id="+resultID), (testParticipantPatientMap.getParticipantPatientMapId()) > 0);
        log.info("ParticipantPatientMapServiceImplTest.testCreate(): Participant Patient Map Result ID --->>" + testParticipantPatientMap.getParticipantPatientMapId());
    }


    /**
     * Test of update method, of class ParticipantPatientMapServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ParticipantPatientMapServiceImplTest.testUpdate(): start - INFO");

        Participantpatientmap testParticipantPatientMapToCreate = null;
        Participantpatientmap testParticipantPatientMapToFind = null;
        Integer resultID = null;
        Integer participantID = 1;                    // at least one participant expected in DB
        String patientID = "00001";                 // at least one patient expected in DB
        Participant testParticipant = null;
        Patient testPatient = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ParticipantPatientMapServiceImplTest.testUpdate(): Participant name --->>" + testParticipant.getParticipantName());

        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ParticipantPatientMapServiceImplTest.testUpdate(): Patient full name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());

        testParticipantPatientMapToCreate = new Participantpatientmap();
        testParticipantPatientMapToCreate.setParticipant(testParticipant);
        testParticipantPatientMapToCreate.setPatient(testPatient);
        testParticipantPatientMapToCreate.setCreateuser("test create user");
        testParticipantPatientMapToCreate.setChangeduser("test changed user");

        resultID = participantPatientMapService.create(testParticipantPatientMapToCreate);
        assertTrue(("Test participant patient map to be created with id="+resultID), (testParticipantPatientMapToCreate.getParticipantPatientMapId()) > 0);
        log.info("ParticipantPatientMapServiceImplTest.testUpdate(): Participant Patient Map Result ID --->>" + testParticipantPatientMapToCreate.getParticipantPatientMapId());


        // change a parameter in order to update the participant patient map created
        testParticipantPatientMapToCreate.setCreateuser("updated test create user");
        participantPatientMapService.update(testParticipantPatientMapToCreate);

        // find the updated participant patient map record and display changed information
        testParticipantPatientMapToFind = participantPatientMapService.read(resultID);
        log.info("ParticipantPatientMapServiceImplTest.testUpdate(): Changed create user --->>" + testParticipantPatientMapToFind.getCreateuser());
    }


    /**
     * Test of delete method, of class ParticipantPatientMapServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ParticipantPatientMapServiceImplTest.testDelete(): start - INFO");

        Participantpatientmap testParticipantPatientMapToCreate = null;
        Integer resultID = null;
        Integer participantID = 1;                    // at least one participant expected in DB
        String patientID = "00001";                 // at least one patient expected in DB
        Participant testParticipant = null;
        Patient testPatient = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ParticipantPatientMapServiceImplTest.testDelete(): Participant name --->>" + testParticipant.getParticipantName());

        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ParticipantPatientMapServiceImplTest.testDelete(): Patient full name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());

        testParticipantPatientMapToCreate = new Participantpatientmap();
        testParticipantPatientMapToCreate.setParticipant(testParticipant);
        testParticipantPatientMapToCreate.setPatient(testPatient);
        testParticipantPatientMapToCreate.setCreateuser("test create user");
        testParticipantPatientMapToCreate.setChangeduser("test changed user");

        resultID = participantPatientMapService.create(testParticipantPatientMapToCreate);
        assertTrue(("Test participant patient map to be created with id="+resultID), (testParticipantPatientMapToCreate.getParticipantPatientMapId()) > 0);
        log.info("ParticipantPatientMapServiceImplTest.testDelete(): Participant Patient Map Result ID --->>" + testParticipantPatientMapToCreate.getParticipantPatientMapId());

        participantPatientMapService.delete(testParticipantPatientMapToCreate);
        log.info("ParticipantPatientMapServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class ParticipantPatientMapServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("ParticipantPatientMapServiceImplTest.testDeleteById(): start - INFO");

        Participantpatientmap testParticipantPatientMapToCreate = null;
        Integer resultID = null;
        Integer participantID = 1;                    // at least one participant expected in DB
        String patientID = "00001";                 // at least one patient expected in DB
        Participant testParticipant = null;
        Patient testPatient = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ParticipantPatientMapServiceImplTest.testDeleteById(): Participant name --->>" + testParticipant.getParticipantName());

        testPatient = patientService.read(patientID);
        assertNotNull(testPatient);
        log.info("ParticipantPatientMapServiceImplTest.testDeleteById(): Patient full name --->>" + testPatient.getFirstName() + " " + testPatient.getLastName());

        testParticipantPatientMapToCreate = new Participantpatientmap();
        testParticipantPatientMapToCreate.setParticipant(testParticipant);
        testParticipantPatientMapToCreate.setPatient(testPatient);
        testParticipantPatientMapToCreate.setCreateuser("test create user");
        testParticipantPatientMapToCreate.setChangeduser("test changed user");

        resultID = participantPatientMapService.create(testParticipantPatientMapToCreate);
        assertTrue(("Test participant patient map to be created with id="+resultID), (testParticipantPatientMapToCreate.getParticipantPatientMapId()) > 0);
        log.info("ParticipantPatientMapServiceImplTest.testDeleteById(): Participant Patient Map Result ID --->>" + testParticipantPatientMapToCreate.getParticipantPatientMapId());

        participantPatientMapService.deleteById(resultID);
        log.info("ParticipantPatientMapServiceImplTest.testDeleteById(): end - INFO");
    }


    /**
     * Test of read method, of class ParticipantPatientMapServiceImpl.
     */
    @Test
    public void testRead() throws Exception {

        log.info("ParticipantPatientMapServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Participantpatientmap testParticipantPatientMap = null;
        testParticipantPatientMap = participantPatientMapService.read(id);
        assertNotNull(testParticipantPatientMap);
        log.info("ParticipantPatientMapServiceImplTest.testRead(): Participant Patient Map: Patient full name  --->>" +
                    testParticipantPatientMap.getPatient().getFirstName() + " " +
                    testParticipantPatientMap.getPatient().getLastName());
    }
}