package net.aegis.lab.manager;

import java.util.List;
import net.aegis.lab.dao.pojo.Scenariocaseparameters;
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
public class ScenariocaseParamsManagerTest {

    private static final Log log = LogFactory.getLog(ScenariocaseParamsManagerTest.class);
    ScenariocaseParamsManager scenariocaseParamsManager = null;

    public ScenariocaseParamsManagerTest() {
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
        scenariocaseParamsManager = ScenariocaseParamsManager.getInstance();
    }

    @After
    public void tearDown() {
        log.info("CaseResultParamsManagerTest.tearDown() - INFO");
    }

  
   // @Test
    public void findByScenariocaseparamsId() throws Exception {
        Integer scenariocaseparamsId = 1;
        scenariocaseParamsManager.findByScenariocaseparamsId(scenariocaseparamsId);
    }

//    @Test
    public void findByScenarioId() throws Exception {
        Integer scenarioId = 1;
        List<Scenariocaseparameters> scenariocaseParamsList = scenariocaseParamsManager.findByScenarioId(scenarioId);
        log.info("scenariocaseParamsList Size=== " + scenariocaseParamsList.size());
    }

   // @Test
    public void findByCaseId() throws Exception {
        Integer caseId = 1;
       List<Scenariocaseparameters> scenariocaseParamsList =  scenariocaseParamsManager.findByCaseId(caseId);
       log.info("scenariocaseParamsList Size=== " + scenariocaseParamsList.size());
    }

      @Test
    public void findByScenarioIdAndCaseId() throws Exception {
        Integer scenarioId = 1 ;
        Integer caseId = 1 ;
        List<Scenariocaseparameters> scenariocaseParamsList = scenariocaseParamsManager.findByScenarioIdAndCaseId( scenarioId,  caseId);
        log.info("scenariocaseParamsList Size=== " + scenariocaseParamsList.size());
    }




}