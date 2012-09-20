/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Resultdocument;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Venkat.Keesara
 * Apr 30, 2012
 **/

public class CaseResultManagerTest {

	 private static final Log log = LogFactory.getLog(CaseResultManagerTest.class);
	    CaseResultManager caseResultManager = null;

	    public CaseResultManagerTest() {
	        log.info("CaseResultManagerTest empty constructor - INFO");
	    }

	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        log.info("CaseResultManagerTest.setUpClass() - INFO");
	    }

	    @AfterClass
	    public static void tearDownClass() throws Exception {
	        log.info("CaseResultManagerTest.tearDownClass() - INFO");
	    }

	    @Before
	    public void setUp() {
	        log.info("CaseResultManagerTest.setUp() - INFO");
	        caseResultManager = CaseResultManager.getInstance();
	    }

	    @After
	    public void tearDown() {
	        log.info("CaseResultManagerTest.tearDown() - INFO");
	    }

	  
	    @Test
	     public void getDocumentsForTestcase()throws Exception {
	        Integer participantId = 11 ;
	        // scenario #3 RDR  , 
	        Integer dependentScenarioId = 5 ;
	        Integer dependentCaseId =72 ; 
	        List<Resultdocument> resultdocumentList = caseResultManager.getDocumentsForTestCase(participantId, dependentScenarioId, dependentCaseId);
	        
	        if(resultdocumentList!=null && resultdocumentList.size() > 0){
	        	for(Resultdocument eachResultdocument : resultdocumentList){
	        		log.info("DocumentUniqueId== " + eachResultdocument.getDocumentUniqueId());
	        		log.info("MessageType== " + eachResultdocument.getMessageType());
	        		log.info("ClassCode== " + eachResultdocument.getClassCode());
	        		
	        	}
	        }
	     }
}
