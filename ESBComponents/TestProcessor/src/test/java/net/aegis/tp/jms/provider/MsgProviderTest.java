/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.tp.jms.provider;

import net.aegis.tp.ws.ProcessTestCaseRequestType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Venkat Keesara
 */
public class MsgProviderTest {

	private static final Log log = LogFactory.getLog(MsgProviderTest.class);
	public MsgProviderTest() {
        log.info("MsgProviderTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("MsgProviderTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("MsgProviderTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("MsgProviderTest.setUp() - INFO");
    }

    @After
    public void tearDown() {
        log.info("MsgProviderTest.tearDown() - INFO");
    }
    
    @Test    
	public  void testMsgProvider() {
        MsgProvider msgprovider = new MsgProvider();
        ProcessTestCaseRequestType requestType = new ProcessTestCaseRequestType();
        
        for(int i=964; i<=974; i++){

        //Initiator Scenario
        requestType.setCandidateId("8");
        requestType.setTargetVersion("Connect_2.4.8");
        requestType.setScenarioExecutionId(String.valueOf(i));
        requestType.setLabType("Lab");
        requestType.setScenarioLevelExecution("Y");
       // requestType.setCaseExecutionId();
        System.out.println("result from msgProvider : "+ msgprovider.processMessage(requestType));
        try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }    
//        
//        
//        requestType.setCandidateId("8");
//        requestType.setTargetVersion("Connect_2.4.8");
//        requestType.setScenarioExecutionId("691");
//        requestType.setLabType("Lab");
//        requestType.setScenarioLevelExecution("N");
//        requestType.setCaseExecutionId(String.valueOf(1881));
//        System.out.println("result from msgProvider : "+ msgprovider.processMessage(requestType));

    }
    
}
