package net.aegis.lab.servicesetexecution.service;

import java.util.List;
import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.dao.pojo.Servicesetexecution;
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

/**
 *
 * @author Abhay.Bakshi
 */
public class ServiceSetExecutionServiceImplTest {

    private static final Log log = LogFactory.getLog(ServiceSetExecutionServiceImplTest.class);
    private ServiceSetExecutionService serviceSetExecutionService;
    private ServiceSetService serviceSetService;
    private ParticipantService participantService;

    public ServiceSetExecutionServiceImplTest() {
        log.info("ServiceSetExecutionServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("ServiceSetExecutionServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("ServiceSetExecutionServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("ServiceSetExecutionServiceImplTest.setUp() - INFO");
        serviceSetExecutionService = (ServiceSetExecutionService)ContextUtil.getLabContext().getBean("serviceSetExecutionService");
        serviceSetService = (ServiceSetService)ContextUtil.getLabContext().getBean("serviceSetService");
        participantService = (ParticipantService)ContextUtil.getLabContext().getBean("participantService");
    }

    @After
    public void tearDown() {
        log.info("ServiceSetExecutionServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("ServiceSetExecutionServiceImplTest.testCreate(): start - INFO");

        Servicesetexecution testServiceSetExecution = null;
        Integer resultID = null;
        Integer participantID = 1;                // at least one participant expected in DB
        Integer serviceSetID = 1;               // at least one service set expected in DB
        Participant testParticipant = null;
        Serviceset testServiceSet = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ServiceSetExecutionServiceImplTest.testCreate(): Participant name --->>" + testParticipant.getParticipantName());

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);
        log.info("ServiceSetExecutionServiceImplTest.testRead(): Service set Name --->>" + testServiceSet.getSetName());

        testServiceSetExecution = new Servicesetexecution();
        testServiceSetExecution.setParticipant(testParticipant);
        testServiceSetExecution.setServiceset(testServiceSet);
        testServiceSetExecution.setExecutionUniqueId("9.99999999.99999.9");
        testServiceSetExecution.setInitiatorInd("Y");
        testServiceSetExecution.setResponderInd("N");
        testServiceSetExecution.setSsnHandlingInd("Y");
        testServiceSetExecution.setStatus("Test ACTIVE");

        resultID = serviceSetExecutionService.create(testServiceSetExecution);
        assertTrue(("Test service set execution to be created with id="+resultID), (testServiceSetExecution.getSetExecutionId()) > 0);
        log.info("ServiceSetExecutionServiceImplTest.testCreate(): Service set execution Result ID --->>" + testServiceSetExecution.getSetExecutionId());
    }

    /**
     * Test of read method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("ServiceSetExecutionServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Servicesetexecution testServiceSetExecution = null;
        testServiceSetExecution = serviceSetExecutionService.read(id);
        assertNotNull(testServiceSetExecution);
        log.info("ServiceSetExecutionServiceImplTest.testRead(): Service set exeuction set Name --->>" + 
                testServiceSetExecution.getServiceset().getSetName());
    }

    /**
     * Test of update method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("ServiceSetExecutionServiceImplTest.testUpdate(): start - INFO");

        Servicesetexecution testServiceSetExecutionToCreate = null;
        Servicesetexecution testServiceSetExecutionToFind = null;
        Integer resultID = null;
        Integer participantID = 1;                // at least one participant expected in DB
        Integer serviceSetID = 1;               // at least one service set expected in DB
        Participant testParticipant = null;
        Serviceset testServiceSet = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ServiceSetExecutionServiceImplTest.testUpdate(): Participant name --->>" + testParticipant.getParticipantName());

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);
        log.info("ServiceSetExecutionServiceImplTest.testUpdate(): Service set Name --->>" + testServiceSet.getSetName());

        testServiceSetExecutionToCreate = new Servicesetexecution();
        testServiceSetExecutionToCreate.setParticipant(testParticipant);
        testServiceSetExecutionToCreate.setServiceset(testServiceSet);
        testServiceSetExecutionToCreate.setExecutionUniqueId("9.99999999.99999.9");
        testServiceSetExecutionToCreate.setInitiatorInd("Y");
        testServiceSetExecutionToCreate.setResponderInd("N");
        testServiceSetExecutionToCreate.setSsnHandlingInd("Y");
        testServiceSetExecutionToCreate.setStatus("Test ACTIVE");

        resultID = serviceSetExecutionService.create(testServiceSetExecutionToCreate);
        assertTrue(("Test service set execution to be created with id="+resultID), (testServiceSetExecutionToCreate.getSetExecutionId()) > 0);
        log.info("ServiceSetExecutionServiceImplTest.testUpdate(): Service set execution Result ID --->>" + testServiceSetExecutionToCreate.getSetExecutionId());

        // change a parameter in order to update the service set execution created
        testServiceSetExecutionToCreate.setStatus("Test Updated ACTIVE");
        serviceSetExecutionService.update(testServiceSetExecutionToCreate);

        // find the updated scenario execution record and display changed information
        testServiceSetExecutionToFind = serviceSetExecutionService.read(resultID);
        log.info("ServiceSetExecutionServiceImplTest.testUpdate(): Changed Service set execution status --->>" + testServiceSetExecutionToFind.getStatus());
    }

    /**
     * Test of delete method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("ServiceSetExecutionServiceImplTest.testDelete(): start - INFO");

        Servicesetexecution testServiceSetExecution = null;
        Integer resultID = null;
        Integer participantID = 1;                // at least one participant expected in DB
        Integer serviceSetID = 1;               // at least one service set expected in DB
        Participant testParticipant = null;
        Serviceset testServiceSet = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ServiceSetExecutionServiceImplTest.testDelete(): Participant name --->>" + testParticipant.getParticipantName());

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);
        log.info("ServiceSetExecutionServiceImplTest.testDelete(): Service set Name --->>" + testServiceSet.getSetName());

        testServiceSetExecution = new Servicesetexecution();
        testServiceSetExecution.setParticipant(testParticipant);
        testServiceSetExecution.setServiceset(testServiceSet);
        testServiceSetExecution.setExecutionUniqueId("9.99999999.99999.9");
        testServiceSetExecution.setInitiatorInd("Y");
        testServiceSetExecution.setResponderInd("N");
        testServiceSetExecution.setSsnHandlingInd("Y");
        testServiceSetExecution.setStatus("Test ACTIVE");

        resultID = serviceSetExecutionService.create(testServiceSetExecution);
        assertTrue(("Test service set execution to be created with id="+resultID), (testServiceSetExecution.getSetExecutionId()) > 0);
        log.info("ServiceSetExecutionServiceImplTest.testDelete(): Service set execution Result ID --->>" + testServiceSetExecution.getSetExecutionId());

        serviceSetExecutionService.delete(testServiceSetExecution);
        log.info("ServiceSetExecutionServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("ServiceSetExecutionServiceImplTest.testDeleteById(): start - INFO");

        Servicesetexecution testServiceSetExecution = null;
        Integer resultID = null;
        Integer participantID = 1;                // at least one participant expected in DB
        Integer serviceSetID = 1;               // at least one service set expected in DB
        Participant testParticipant = null;
        Serviceset testServiceSet = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ServiceSetExecutionServiceImplTest.testDeleteById(): Participant name --->>" + testParticipant.getParticipantName());

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);
        log.info("ServiceSetExecutionServiceImplTest.testDeleteById(): Service set Name --->>" + testServiceSet.getSetName());

        testServiceSetExecution = new Servicesetexecution();
        testServiceSetExecution.setParticipant(testParticipant);
        testServiceSetExecution.setServiceset(testServiceSet);
        testServiceSetExecution.setExecutionUniqueId("9.99999999.99999.9");
        testServiceSetExecution.setInitiatorInd("Y");
        testServiceSetExecution.setResponderInd("N");
        testServiceSetExecution.setSsnHandlingInd("Y");
        testServiceSetExecution.setStatus("Test ACTIVE");

        resultID = serviceSetExecutionService.create(testServiceSetExecution);
        assertTrue(("Test service set execution to be created with id="+resultID), (testServiceSetExecution.getSetExecutionId()) > 0);
        log.info("ServiceSetExecutionServiceImplTest.testDeleteById(): Service set execution Result ID --->>" + testServiceSetExecution.getSetExecutionId());

        serviceSetExecutionService.deleteById(resultID);
        log.info("ServiceSetExecutionServiceImplTest.testDeleteById(): end - INFO");
    }

    /**
     * Test of findActiveByParticipantId method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testFindActiveByParticipantId() throws Exception {

        
        /*
        System.out.println("findActiveByParticipantId");
        Integer participantId = null;
        ServiceSetExecutionServiceImpl instance = new ServiceSetExecutionServiceImpl();
        List expResult = null;
        List result = instance.findActiveByParticipantId(participantId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of findBySetExecutionId method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testFindBySetExecutionId() throws Exception {

        
        /*
        System.out.println("findBySetExecutionId");
        Integer setExecutionId = null;
        ServiceSetExecutionServiceImpl instance = new ServiceSetExecutionServiceImpl();
        List expResult = null;
        List result = instance.findBySetExecutionId(setExecutionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of startServiceSetExecution method, of class ServiceSetExecutionServiceImpl.
     */
//    @Test
    public void testStartServiceSetExecution() throws Exception {

        log.info("ServiceSetExecutionServiceImplTest.testStartServiceSetExecution(): start - INFO");

        Integer participantID = 1;                // at least one participant expected in DB
        Integer serviceSetID = 1;               // at least one service set expected in DB
        Participant testParticipant = null;
        Serviceset testServiceSet = null;

        testParticipant = participantService.read(participantID);
        assertNotNull(testParticipant);
        log.info("ServiceSetExecutionServiceImplTest.testStartServiceSetExecution(): Participant name --->>" + testParticipant.getParticipantName());

        testServiceSet = serviceSetService.read(serviceSetID);
        assertNotNull(testServiceSet);
        log.info("ServiceSetExecutionServiceImplTest.testStartServiceSetExecution(): Service set Name --->>" + testServiceSet.getSetName());

      //  serviceSetExecutionService.startServiceSetExecution(testParticipant.getParticipantId(),
       //         testServiceSet.getSetId(), "Test Y", "Test N", "Test Y");
        //log.info("ServiceSetExecutionServiceImplTest.testStartServiceSetExecution(): end - INFO");
    }

}