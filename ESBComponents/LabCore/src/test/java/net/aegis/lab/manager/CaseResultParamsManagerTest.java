package net.aegis.lab.manager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author Venkat Keesara
 */
public class CaseResultParamsManagerTest {

    private static final Log log = LogFactory.getLog(CaseResultParamsManagerTest.class);
    CaseResultParamsManager caseResultParamsManager = null;

    public CaseResultParamsManagerTest() {
        log.info("CaseResultParamsManagerTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("CaseResultParamsManagerTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("CaseResultParamsManagerTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("CaseResultParamsManagerTest.setUp() - INFO");
        caseResultParamsManager = CaseResultParamsManager.getInstance();
    }

    @After
    public void tearDown() {
        log.info("CaseResultParamsManagerTest.tearDown() - INFO");
    }

  
    @Test
     public void updateResultIdByResultId()throws Exception {
        Integer newCaseResultId = 1 ;
        Integer oldCaseResultId = 4 ;
         caseResultParamsManager.updateResultIdByResultId(newCaseResultId, oldCaseResultId);
     }



}