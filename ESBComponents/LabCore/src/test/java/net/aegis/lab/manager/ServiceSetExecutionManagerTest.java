/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.manager;

import java.util.Map;
import net.aegis.lab.auditsummary.service.AuditSummaryServiceImplTest;
import net.aegis.lab.testharnessri.service.TestHarnessriService;
import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class ServiceSetExecutionManagerTest {

    private TestHarnessriService testHarnessriService = null;

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ServiceSetExecutionManagerTest.class);
    public ServiceSetExecutionManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {

       testHarnessriService = (TestHarnessriService)ContextUtil.getLabContext().getBean("testHarnessriService");
       
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class ServiceSetExecutionManager.
     */
  /*  @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        ServiceSetExecutionManager expResult = new ServiceSetExecutionManager();
       
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of participantActiveServiceSets method, of class ServiceSetExecutionManager.
     */
  /*  @Test
    public void testParticipantActiveServiceSets() throws Exception {
        System.out.println("participantActiveServiceSets");
        Integer participantId = null;
        ServiceSetExecutionManager instance = null;
        List expResult = null;
        List result = instance.participantActiveServiceSets(participantId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of startServiceSetExecution method, of class ServiceSetExecutionManager.
     */
    @Test
    public void testStartServiceSetExecution() throws Exception {
        log.info("startServiceSetExecution");
        Integer participantId = 8;
        Integer serviceSetId = 1;
        String intiatorInd = "Y";
        String responderInd = "N";
        String ssHandlingInd = "N";
        log.info("I am here");
        Map<String,String> harnessIds = testHarnessriService.getCommunityIds();
        if( harnessIds != null){
           log.info("the size of harnessIds "+ harnessIds.size());
        }
        else{

             log.info("the size of harnessIds is null");
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}