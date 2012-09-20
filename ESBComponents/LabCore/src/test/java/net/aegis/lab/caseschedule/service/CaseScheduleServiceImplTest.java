package net.aegis.lab.caseschedule.service;

import net.aegis.lab.caseexecution.service.CaseExecutionService;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseschedule;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author Abhay.Bakshi
 */
public class CaseScheduleServiceImplTest {

    private static final Log log = LogFactory.getLog(CaseScheduleServiceImplTest.class);
    private CaseScheduleService caseScheduleService;
    private CaseExecutionService caseExecutionService;

    public CaseScheduleServiceImplTest() {
        log.info("CaseScheduleServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("CaseScheduleServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("CaseScheduleServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("CaseScheduleServiceImplTest.setUp() - INFO");
        caseExecutionService = (CaseExecutionService)ContextUtil.getLabContext().getBean("caseExecutionService");
        caseScheduleService = (CaseScheduleService)ContextUtil.getLabContext().getBean("caseScheduleService");
    }

    @After
    public void tearDown() {
        log.info("CaseScheduleServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class CaseScheduleServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("CaseScheduleServiceImplTest.testCreate(): start - INFO");

        Integer resultID = -999;
        Caseschedule testCaseSchedule = null;
        Caseexecution testCaseexecution = null;

        Integer testCaseExecutionID = 1;        // at least one test case execution expected in DB

        testCaseexecution = caseExecutionService.read(testCaseExecutionID);
        assertNotNull(testCaseexecution);

        testCaseSchedule = new Caseschedule();
        testCaseSchedule.setCaseexecution(testCaseexecution);
        testCaseSchedule.setExecuted("YES TEST");
        testCaseSchedule.setStatus("COMPLETED TEST");

        resultID = caseScheduleService.create(testCaseSchedule);
        assertTrue(("Test case schedule to be created with id="+resultID), (testCaseSchedule.getCaseScheduleId()) > 0);
        log.info("CaseScheduleServiceImplTest.testCreate(): Case schedule Result ID --->>" + testCaseSchedule.getCaseScheduleId());
    }

    /**
     * Test of read method, of class CaseScheduleServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("CaseScheduleServiceImplTest.testRead(): start - INFO");
        Integer id = 1;
        Caseschedule testCaseSchedule = null;
        testCaseSchedule = caseScheduleService.read(id);
        assertNotNull(testCaseSchedule);
        log.info("CaseScheduleServiceImplTest.testRead(): Case schedule Result ID --->>" + testCaseSchedule.getCaseScheduleId());
    }

    /**
     * Test of update method, of class CaseScheduleServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {

        log.info("CaseScheduleServiceImplTest.testUpdate(): start - INFO");

        Integer resultID = -999;
        Caseschedule testCaseScheduleToCreate = null;
        Caseschedule testCaseScheduleToFind = null;
        Caseexecution testCaseexecution = null;

        Integer testCaseExecutionID = 1;        // at least one test case execution expected in DB

        testCaseexecution = caseExecutionService.read(testCaseExecutionID);
        assertNotNull(testCaseexecution);

        testCaseScheduleToCreate = new Caseschedule();
        testCaseScheduleToCreate.setCaseexecution(testCaseexecution);
        testCaseScheduleToCreate.setExecuted("YES TEST");
        testCaseScheduleToCreate.setStatus("COMPLETED TEST");

        resultID = caseScheduleService.create(testCaseScheduleToCreate);
        assertTrue(("Test case schedule to be created with id="+resultID), (testCaseScheduleToCreate.getCaseScheduleId()) > 0);
        log.info("CaseScheduleServiceImplTest.testCreate(): Case schedule Result ID --->>" + testCaseScheduleToCreate.getCaseScheduleId());

        // change a parameter in order to update the case schedule created
        testCaseScheduleToCreate.setStatus("COMPLETED UPDATED TEST STATUS");
        caseScheduleService.update(testCaseScheduleToCreate);

        // find the updated participant and display his/her name
        testCaseScheduleToFind = caseScheduleService.read(resultID);
        log.info("CaseScheduleServiceImplTest.testUpdate(): Changed Result --->>" + testCaseScheduleToFind.getStatus());

    }

    /**
     * Test of delete method, of class CaseScheduleServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("CaseScheduleServiceImplTest.testDelete(): start - INFO");

        Integer resultID = -999;
        Caseschedule testCaseSchedule = null;
        Caseexecution testCaseexecution = null;

        Integer testCaseExecutionID = 1;        // at least one test case execution expected in DB

        testCaseexecution = caseExecutionService.read(testCaseExecutionID);
        assertNotNull(testCaseexecution);

        testCaseSchedule = new Caseschedule();
        testCaseSchedule.setCaseexecution(testCaseexecution);
        testCaseSchedule.setExecuted("YES TEST");
        testCaseSchedule.setStatus("COMPLETED TEST");

        resultID = caseScheduleService.create(testCaseSchedule);
        assertTrue(("Test case schedule to be created with id="+resultID), (testCaseSchedule.getCaseScheduleId()) > 0);
        log.info("CaseScheduleServiceImplTest.testDelete(): Case schedule Result ID --->>" + testCaseSchedule.getCaseScheduleId());

        caseScheduleService.delete(testCaseSchedule);
        log.info("CaseScheduleServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteById method, of class CaseScheduleServiceImpl.
     */
//    @Test
    public void testDeleteById() throws Exception {

        log.info("CaseScheduleServiceImplTest.testDeleteById(): start - INFO");

        Integer resultID = -999;
        Caseschedule testCaseSchedule = null;
        Caseexecution testCaseexecution = null;

        Integer testCaseExecutionID = 1;        // at least one test case execution expected in DB

        testCaseexecution = caseExecutionService.read(testCaseExecutionID);
        assertNotNull(testCaseexecution);

        testCaseSchedule = new Caseschedule();
        testCaseSchedule.setCaseexecution(testCaseexecution);
        testCaseSchedule.setExecuted("YES TEST");
        testCaseSchedule.setStatus("COMPLETED TEST");

        resultID = caseScheduleService.create(testCaseSchedule);
        assertTrue(("Test case schedule to be created with id="+resultID), (testCaseSchedule.getCaseScheduleId()) > 0);
        log.info("CaseScheduleServiceImplTest.testDeleteById(): Case schedule Result ID --->>" + testCaseSchedule.getCaseScheduleId());

        caseScheduleService.deleteById(resultID);
        log.info("CaseScheduleServiceImplTest.testDeleteById(): end - INFO");
    }

}