package net.aegis.lab.participantaudithistory.service;

import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Participantaudithistory;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.nhinrep.service.NhinrepService;
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
public class ParticipantAuditHistoryServiceImplTest {

    private static final Log log = LogFactory.getLog(ParticipantAuditHistoryServiceImplTest.class);
    private ParticipantAuditHistoryService participantAuditHistoryService;
    private ParticipantService participantService;
    private UserService userService;
    private NhinrepService nhinrepService;


    public ParticipantAuditHistoryServiceImplTest() {
        log.info("ParticipantAuditHistoryServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ParticipantAuditHistoryServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ParticipantAuditHistoryServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        participantAuditHistoryService = (ParticipantAuditHistoryService)ContextUtil.getLabContext().getBean("candiateAuditHistService");
        participantService = (ParticipantService)ContextUtil.getLabContext().getBean("participantService");
        userService = (UserService)ContextUtil.getLabContext().getBean("userService");
        nhinrepService = (NhinrepService)ContextUtil.getLabContext().getBean("nhinrepService");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class ParticipantAuditHistoryServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ParticipantAuditHistoryServiceImplTest.testCreate(): start - INFO");
        Integer resultID = -999;
        Participantaudithistory participantAuditHistory = null;
        Participant testParticipant = null;
        User testUser = null;
        Nhinrep testNhinrep = null;

        participantAuditHistory = new Participantaudithistory();
        
        Integer participantID = 1;            // at least one participant expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantAuditHistoryServiceImplTest.testCreate(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testCreate(): user not found", testUser);

        testNhinrep = nhinrepService.read(1);
        log.info("ParticipantAuditHistoryServiceImplTest.testCreate(): testNhinrep Name --->>" + testNhinrep.getName());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testCreate(): Nhin rep not found", testNhinrep);

        participantAuditHistory.setParticipant(testParticipant);
        participantAuditHistory.setParticipantName(testParticipant.getParticipantName());
        participantAuditHistory.setUserId(testUser.getUserId());
        participantAuditHistory.setCommunityId(testParticipant.getCommunityId());
        participantAuditHistory.setIpAddress(testParticipant.getIpAddress());
        participantAuditHistory.setNhinRepId(testNhinrep.getNhinRepId());
        participantAuditHistory.setContactName(testParticipant.getContactName());
        participantAuditHistory.setContactPhone(testParticipant.getContactPhone());
        participantAuditHistory.setContactEmail(testParticipant.getContactEmail());
        participantAuditHistory.setInitiatorInd(testParticipant.getInitiatorInd());
        participantAuditHistory.setReposnderInd(testParticipant.getResponderInd());
        participantAuditHistory.setSsnHandlingInd(testParticipant.getSsnHandlingInd());
        participantAuditHistory.setAuditcreateuser(testParticipant.getUser().getCreateuser());
        participantAuditHistory.setAuditchangeduser(testParticipant.getChangeduser());
        participantAuditHistory.setCreateuser(testParticipant.getCreateuser());

        // create a participant audit history record
        resultID = participantAuditHistoryService.create(participantAuditHistory);

        assertTrue(("Test participant audit history to be created with id="+resultID), (participantAuditHistory.getAuditId() > 0));
        log.info("ParticipantAuditHistoryServiceImplTest.testCreate(): Participant Audit history Participant name --->>" + participantAuditHistory.getParticipantName());
    }


    /**
     * Test of delete method, of class ParticipantAuditHistoryServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ParticipantAuditHistoryServiceImplTest.testDelete(): start - INFO");
        Integer resultID = -999;
        Participantaudithistory participantAuditHistory = null;
        Participant testParticipant = null;
        User testUser = null;
        Nhinrep testNhinrep = null;

        participantAuditHistory = new Participantaudithistory();

        Integer participantID = 1;            // at least one participant expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantAuditHistoryServiceImplTest.testDelete(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testDelete(): user not found", testUser);

        testNhinrep = nhinrepService.read(1);
        log.info("ParticipantAuditHistoryServiceImplTest.testDelete(): testNhinrep Name --->>" + testNhinrep.getName());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testDelete(): Nhin rep not found", testNhinrep);

        participantAuditHistory.setParticipant(testParticipant);
        participantAuditHistory.setParticipantName(testParticipant.getParticipantName());
        participantAuditHistory.setUserId(testUser.getUserId());
        participantAuditHistory.setCommunityId(testParticipant.getCommunityId());
        participantAuditHistory.setIpAddress(testParticipant.getIpAddress());
        participantAuditHistory.setNhinRepId(testNhinrep.getNhinRepId());
        participantAuditHistory.setContactName(testParticipant.getContactName());
        participantAuditHistory.setContactPhone(testParticipant.getContactPhone());
        participantAuditHistory.setContactEmail(testParticipant.getContactEmail());
        participantAuditHistory.setInitiatorInd(testParticipant.getInitiatorInd());
        participantAuditHistory.setReposnderInd(testParticipant.getResponderInd());
        participantAuditHistory.setSsnHandlingInd(testParticipant.getSsnHandlingInd());
        participantAuditHistory.setAuditcreateuser(testParticipant.getUser().getCreateuser());
        participantAuditHistory.setAuditchangeduser(testParticipant.getChangeduser());
        participantAuditHistory.setCreateuser(testParticipant.getCreateuser());

        // create a participant audit history record
        resultID = participantAuditHistoryService.create(participantAuditHistory);

        assertTrue(("Test participant audit history to be created with id="+resultID), (participantAuditHistory.getAuditId() > 0));
        log.info("ParticipantAuditHistoryServiceImplTest.testDelete(): Participant Audit history Participant name --->>" + participantAuditHistory.getParticipantName());

        participantAuditHistoryService.delete(participantAuditHistory);
        log.info("ParticipantSeParticipantAuditHistoryServiceImplTestrviceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class ParticipantAuditHistoryServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("ParticipantAuditHistoryServiceImplTest.testDeleteById(): start - INFO");
        Integer resultID = -999;
        Participantaudithistory participantAuditHistory = null;
        Participant testParticipant = null;
        User testUser = null;
        Nhinrep testNhinrep = null;

        participantAuditHistory = new Participantaudithistory();

        Integer participantID = 1;            // at least one participant expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantAuditHistoryServiceImplTest.testDeleteById(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testDeleteById(): user not found", testUser);

        testNhinrep = nhinrepService.read(1);
        log.info("ParticipantAuditHistoryServiceImplTest.testDeleteById(): testNhinrep Name --->>" + testNhinrep.getName());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testDeleteById(): Nhin rep not found", testNhinrep);

        participantAuditHistory.setParticipant(testParticipant);
        participantAuditHistory.setParticipantName(testParticipant.getParticipantName());
        participantAuditHistory.setUserId(testUser.getUserId());
        participantAuditHistory.setCommunityId(testParticipant.getCommunityId());
        participantAuditHistory.setIpAddress(testParticipant.getIpAddress());
        participantAuditHistory.setNhinRepId(testNhinrep.getNhinRepId());
        participantAuditHistory.setContactName(testParticipant.getContactName());
        participantAuditHistory.setContactPhone(testParticipant.getContactPhone());
        participantAuditHistory.setContactEmail(testParticipant.getContactEmail());
        participantAuditHistory.setInitiatorInd(testParticipant.getInitiatorInd());
        participantAuditHistory.setReposnderInd(testParticipant.getResponderInd());
        participantAuditHistory.setSsnHandlingInd(testParticipant.getSsnHandlingInd());
        participantAuditHistory.setAuditcreateuser(testParticipant.getUser().getCreateuser());
        participantAuditHistory.setAuditchangeduser(testParticipant.getChangeduser());
        participantAuditHistory.setCreateuser(testParticipant.getCreateuser());

        // create a participant audit history record
        resultID = participantAuditHistoryService.create(participantAuditHistory);

        assertTrue(("Test participant audit history to be created with id="+resultID), (participantAuditHistory.getAuditId() > 0));
        log.info("ParticipantAuditHistoryServiceImplTest.testDeleteById(): Participant Audit history Participant name --->>" + participantAuditHistory.getParticipantName());

        participantAuditHistoryService.deleteById(resultID);
        log.info("ParticipantAuditHistoryServiceImplTest.testDeleteById(): end - INFO");
    }


    /**
     * Test of read method, of class ParticipantAuditHistoryServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("ParticipantAuditHistoryServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Participantaudithistory participantAuditHistory = null;
        participantAuditHistory = participantAuditHistoryService.read(id);
        assertNotNull(participantAuditHistory);
        log.info("ParticipantAuditHistoryServiceImplTest.testRead(): Case Result ID --->>" + participantAuditHistory.getAuditId());
    }

    /**
     * Test of update method, of class ParticipantAuditHistoryServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ParticipantAuditHistoryServiceImplTest.testUpdate(): start - INFO");
        Integer resultID = -999;
        Participantaudithistory participantAuditHistoryToCreate = null;
        Participantaudithistory participantAuditHistoryToFind = null;
        Participant testParticipant = null;
        User testUser = null;
        Nhinrep testNhinrep = null;

        participantAuditHistoryToCreate = new Participantaudithistory();

        Integer participantID = 1;            // at least one participant expected in DB

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);

        testUser = userService.findByUsername("abhay");
        log.info("ParticipantAuditHistoryServiceImplTest.testUpdate(): testUser Name --->>" + testUser.getUsername());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testUpdate(): user not found", testUser);

        testNhinrep = nhinrepService.read(1);
        log.info("ParticipantAuditHistoryServiceImplTest.testUpdate(): testNhinrep Name --->>" + testNhinrep.getName());
        assertNotNull("ParticipantAuditHistoryServiceImplTest.testUpdate(): Nhin rep not found", testNhinrep);

        participantAuditHistoryToCreate.setParticipant(testParticipant);
        participantAuditHistoryToCreate.setParticipantName(testParticipant.getParticipantName());
        participantAuditHistoryToCreate.setUserId(testUser.getUserId());
        participantAuditHistoryToCreate.setCommunityId(testParticipant.getCommunityId());
        participantAuditHistoryToCreate.setIpAddress(testParticipant.getIpAddress());
        participantAuditHistoryToCreate.setNhinRepId(testNhinrep.getNhinRepId());
        participantAuditHistoryToCreate.setContactName(testParticipant.getContactName());
        participantAuditHistoryToCreate.setContactPhone(testParticipant.getContactPhone());
        participantAuditHistoryToCreate.setContactEmail(testParticipant.getContactEmail());
        participantAuditHistoryToCreate.setInitiatorInd(testParticipant.getInitiatorInd());
        participantAuditHistoryToCreate.setReposnderInd(testParticipant.getResponderInd());
        participantAuditHistoryToCreate.setSsnHandlingInd(testParticipant.getSsnHandlingInd());
        participantAuditHistoryToCreate.setAuditcreateuser(testParticipant.getUser().getCreateuser());
        participantAuditHistoryToCreate.setAuditchangeduser(testParticipant.getChangeduser());
        participantAuditHistoryToCreate.setCreateuser(testParticipant.getCreateuser());

        // create a participant audit history record
        resultID = participantAuditHistoryService.create(participantAuditHistoryToCreate);

        assertTrue(("Test participant audit history to be created with id="+resultID), (participantAuditHistoryToCreate.getAuditId() > 0));
        log.info("ParticipantAuditHistoryServiceImplTest.testUpdate(): Participant Audit history Participant name --->>" + participantAuditHistoryToCreate.getParticipantName());

        // change a parameter in order to update the case result created
        participantAuditHistoryToCreate.setAuditcreateuser("ParticipantAuditHistoryServiceTest.testUpdate");    // VARCHAR(45) limit
        participantAuditHistoryService.update(participantAuditHistoryToCreate);

        // find the updated participant audit history record and display changed information
        participantAuditHistoryToFind = participantAuditHistoryService.read(resultID);
        log.info("ParticipantAuditHistoryServiceImplTest.testUpdate(): Changed Result --->>" + participantAuditHistoryToFind.getAuditcreateuser());

    }

}