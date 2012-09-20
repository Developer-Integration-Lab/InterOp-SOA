package net.aegis.lab.participant.service;

import java.util.List;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.user.service.UserService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants.LabType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Abhay.Bakshi
 */
public class ParticipantServiceImplTest {

    private static final Log log = LogFactory.getLog(ParticipantServiceImplTest.class);
    private UserService userService;
    private ParticipantService participantService;

    public ParticipantServiceImplTest() {
        log.info("ParticipantServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ParticipantServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ParticipantServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ParticipantServiceImplTest.setUp() - INFO");
        userService = (UserService) ContextUtil.getLabContext().getBean("userService");
        participantService = (ParticipantService) ContextUtil.getLabContext().getBean("participantService");
    }

    @After
    public void tearDown() {
        log.info("ParticipantServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {
        log.info("ParticipantServiceImplTest.testCreate(): start - INFO");
        Integer resultID = -999;
        Participant testParticipant = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantServiceImplTest.testCreate(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantServiceImplTest.testCreate(): user not found", testUser);

        testParticipant = new Participant();
        testParticipant.setParticipantName("Participant_To_Delete");
        testParticipant.setUser(testUser);
        testParticipant.setCommunityId("999.999");
        testParticipant.setIpAddress("");
        testParticipant.setContactName("POC at Participant_To_Delete");
        testParticipant.setContactPhone("999-999-9999");
        testParticipant.setContactEmail("poc@Participant_To_Delete.com");
        testParticipant.setInitiatorInd("Y");
        testParticipant.setResponderInd("Y");
        testParticipant.setSsnHandlingInd("Y");
        testParticipant.setAssigningAuthorityId("9.99.999.9.999999.9.999");
        testParticipant.setStatus("A");
        testParticipant.setCreateuser("ParticipantServiceImpl.Test");
        testParticipant.setChangeduser("ParticipantServiceImpl.Test");

        // create a participant
        resultID = participantService.create(testParticipant);

        assertTrue(("Test participant to be created with id=" + resultID), (testParticipant.getParticipantId() > 0));
        log.info("ParticipantServiceImplTest.testCreate(): Participant name --->>" + testParticipant.getParticipantName());
    }

    /**
     * Test of read method, of class ParticipantServiceImpl.
     */
    @Test
    public void testRead() throws Exception {
        log.info("ParticipantServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Participant testParticipant = null;
        testParticipant = participantService.read(id);
        assertNotNull(testParticipant);
        log.info("ParticipantServiceImplTest.testRead(): Participant name --->>" + testParticipant.getParticipantName());
    }

    @Test
    public void testReadLabAccessFilter() throws Exception {
        Integer id = 1;
        Participant testParticipant = null;
        // We'll change this to call API that filters based on LabAccessFilter
        testParticipant = participantService.read(id);
        assertNotNull(testParticipant);

        //Story 51: Integrate Conformance Tests - Core Library changes
        assertNotNull(testParticipant.getLabAccessFilter());
        log.info("ParticipantServiceImplTest.testRead(): labAccessFilter--->>" + testParticipant.getLabAccessFilter());
    }

    /**
     * Test of update method, of class ParticipantServiceImpl.
     */
    //@Test
    public void testUpdate() throws Exception {

        log.info("ParticipantServiceImplTest.testUpdate(): start - INFO");
        Integer resultID = -999;
        Participant testParticipantToCreate = null;
        Participant testParticipantToFind = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantServiceImplTest.testCreate(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantServiceImplTest.testCreate(): user not found", testUser);

        testParticipantToCreate = new Participant();
        testParticipantToCreate.setParticipantName("Participant_To_Delete");
        testParticipantToCreate.setUser(testUser);
        testParticipantToCreate.setCommunityId("999.999");
        testParticipantToCreate.setIpAddress("");
        testParticipantToCreate.setContactName("POC at Participant_To_Delete");
        testParticipantToCreate.setContactPhone("999-999-9999");
        testParticipantToCreate.setContactEmail("poc@Participant_To_Delete.com");
        testParticipantToCreate.setInitiatorInd("Y");
        testParticipantToCreate.setResponderInd("Y");
        testParticipantToCreate.setSsnHandlingInd("Y");
        testParticipantToCreate.setAssigningAuthorityId("9.99.999.9.999999.9.999");
        testParticipantToCreate.setStatus("A");
        testParticipantToCreate.setCreateuser("ParticipantServiceImpl.Test");
        testParticipantToCreate.setChangeduser("ParticipantServiceImpl.Test");

        resultID = participantService.create(testParticipantToCreate);
        assertTrue(("Test participant to be created with id=" + resultID), (testParticipantToCreate.getParticipantId() > 0));
        log.info("ParticipantServiceImplTest.testUpdate(): Participant name --->>" + testParticipantToCreate.getParticipantName());

        // change a parameter in order to update participant created
        testParticipantToCreate.setParticipantName("Changed_Name_Participant_To_Delete");
        participantService.update(testParticipantToCreate);

        // find the updated participant and display his/her name
        testParticipantToFind = participantService.findByParticipantId(resultID);
        log.info("ParticipantServiceImplTest.testUpdate(): Changed Participant name --->>" + testParticipantToFind.getParticipantName());
    }

    /**
     * Test of delete method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {
        log.info("ParticipantServiceImplTest.testDelete(): start - INFO");
        Integer resultID = -999;
        Participant testParticipant = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantServiceImplTest.testDelete(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantServiceImplTest.testDelete(): user not found", testUser);

        testParticipant = new Participant();
        testParticipant.setParticipantName("Participant_To_Delete");
        testParticipant.setUser(testUser);
        testParticipant.setCommunityId("999.999");
        testParticipant.setIpAddress("");
        testParticipant.setContactName("POC at Participant_To_Delete");
        testParticipant.setContactPhone("999-999-9999");
        testParticipant.setContactEmail("poc@Participant_To_Delete.com");
        testParticipant.setInitiatorInd("Y");
        testParticipant.setResponderInd("Y");
        testParticipant.setSsnHandlingInd("Y");
        testParticipant.setStatus("A");
        testParticipant.setCreateuser("ParticipantServiceImpl.Test");
        testParticipant.setChangeduser("ParticipantServiceImpl.Test");

        resultID = participantService.create(testParticipant);
        assertTrue(("Test participant to be created with id=" + resultID), (testParticipant.getParticipantId() > 0));

        participantService.delete(testParticipant);
        log.info("ParticipantServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {
        log.info("ParticipantServiceImplTest.testDeleteById(): start - INFO");
        Integer resultID = -999;
        Participant testParticipant = null;
        User testUser = null;

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantServiceImplTest.testDeleteById(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantServiceImplTest.testDeleteById(): user not found", testUser);

        testParticipant = new Participant();
        testParticipant.setParticipantName("Participant_To_Delete");
        testParticipant.setUser(testUser);
        testParticipant.setCommunityId("999.999");
        testParticipant.setIpAddress("");
        testParticipant.setContactName("POC at Participant_To_Delete");
        testParticipant.setContactPhone("999-999-9999");
        testParticipant.setContactEmail("poc@Participant_To_Delete.com");
        testParticipant.setInitiatorInd("Y");
        testParticipant.setResponderInd("Y");
        testParticipant.setSsnHandlingInd("Y");
        testParticipant.setStatus("A");
        testParticipant.setCreateuser("ParticipantServiceImpl.Test");
        testParticipant.setChangeduser("ParticipantServiceImpl.Test");

        resultID = participantService.create(testParticipant);
        assertTrue(("Test participant to be created with id=" + resultID), (testParticipant.getParticipantId() > 0));

        participantService.deleteById(resultID);
        log.info("ParticipantServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of findByParticipantId method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testFindByParticipantId() throws Exception {
        log.info("ParticipantServiceImplTest.testFindByParticipantId(): start - INFO");
        Integer id = 1;
        Participant testParticipant = null;
        testParticipant = participantService.read(id);
        assertNotNull(testParticipant);
        log.info("ParticipantServiceImplTest.testFindByParticipantId(): Participant name --->>" + testParticipant.getParticipantName());
    }

    /**
     * Test of findByNhinRepId method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testFindByNhinRepId() throws Exception {

        log.info("ParticipantServiceImplTest.testFindByNhinRepId(): start - INFO");
        Integer nhinRepId = 1;
        List<Participant> participantsToFind = null;
        participantsToFind = participantService.findByNhinRepId(nhinRepId);
        assertNotNull(participantsToFind);
        log.info("ParticipantServiceImplTest.testFindByNhinRepId(): Number of participants --->>" + participantsToFind.size());
    }

    /**
     * Test of findByNhinRepIdAndStatus method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testFindByNhinRepIdAndStatus() throws Exception {

        log.info("ParticipantServiceImplTest.testFindByNhinRepIdAndStatus(): start - INFO");
        int nhinRepId = 1;
        String status = "A";
        List<Participant> participantsToFind = null;
        participantsToFind = participantService.findByNhinRepIdAndStatus(nhinRepId, status);
        assertNotNull(participantsToFind);
        log.info("ParticipantServiceImplTest.testFindByNhinRepIdAndStatus(): Number of participants --->>" + participantsToFind.size());
    }

    /**
     * Test of getActiveCandiateByIPAddrAndCommunityId method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testGetActiveCandiateByIPAddrAndCommunityId() throws Exception {
        /*
        System.out.println("getActiveCandiateByIPAddrAndCommunityId");
        String communityId = "";
        String ipAddress = "";
        String status = "";
        ParticipantServiceImpl instance = new ParticipantServiceImpl();
        List expResult = null;
        List result = instance.getActiveCandiateByIPAddrAndCommunityId(communityId, ipAddress, status);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         */
    }

    /**
     * Test of findScenarioExecutionByScenarioExecutionId method, of class ParticipantServiceImpl.
     */
//    @Test
    public void testFindScenarioExecutionByScenarioExecutionId() throws Exception {
        /*
        System.out.println("findScenarioExecutionByScenarioExecutionId");
        Integer scenarioExecutionId = null;
        ParticipantServiceImpl instance = new ParticipantServiceImpl();
        List expResult = null;
        List result = instance.findScenarioExecutionByScenarioExecutionId(scenarioExecutionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         */
    }

    /**
     * Test of findScenarioExecutionByParticipantIdAndStatus method, of class ParticipantServiceImpl.
     */
    @Test
    public void testFindScenarioExecutionByParticipantIdAndStatus() throws Exception {
        System.out.println("findScenarioExecutionByParticipantIdAndStatus");
        int participantId = 7;
        String status = "ACTIVE";
        List result = participantService.findScenarioExecutionByParticipantIdAndStatus(participantId, status, null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
