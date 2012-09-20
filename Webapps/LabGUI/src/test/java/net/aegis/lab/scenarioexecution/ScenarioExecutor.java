package net.aegis.lab.scenarioexecution;

import java.util.HashMap;
import java.util.List;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.util.AutoTestLabSetup;
import net.aegis.lab.util.AutoTestLogger;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants.LabType;
import net.aegis.lab.web.action.participant.ExecuteTestCase;

import net.aegis.ri.auditrepo.dao.pojo.Auditextension;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;
import static org.junit.Assert.*;

/**
 * This class is used by automated tests to execute scenarios and verify results.
 *
 * @author Tareq.Nabeel
 */
public class ScenarioExecutor {

    private static AutoTestLogger logger = AutoTestLogger.instance();

    public static void executeConfScenario(int scenarioNumber) throws Exception {
        executeScenario(scenarioNumber, LabType.CONFORMANCE);
    }
    /**
     * Will execute the entire scenario including all the test cases.
     * This method will also verify successful execution of the test cases.
     *
     * @param scenarioNumber
     * @throws Exception
     */
    public static void executeScenario(int scenarioNumber) throws Exception {
        executeScenario(scenarioNumber, null);
    }
    
    /**
     *
     * @param scenarioNumber
     * @param labType
     * @throws Exception
     */
    public static void executeScenario(int scenarioNumber,LabType labType) throws Exception {
        Scenarioexecution scenarioexecution = AutoTestLabSetup.instance(labType).getScenarioExecution(scenarioNumber);
        executeScenario(scenarioexecution);
    }


    /**
     * Will execute the entire scenario including all the test cases.
     * This method will also verify successful execution of the test cases.
     * 
     * @param scenarioexecution
     * @throws Exception
     */
    public static void executeScenario(Scenarioexecution scenarioexecution) throws Exception {
        assertNotNull(scenarioexecution);
        Scenario scenario = scenarioexecution.getScenario();
        assertNotNull(scenario);
        String scenarioLevelExecution = "Y";

        ExecuteTestCase executeTestCase = createExecuteTestCase(scenarioexecution, scenarioLevelExecution);
        boolean allTestCasesPassed = true;
        String status = executeExecuteTestCase(executeTestCase, scenarioexecution);
        allTestCasesPassed &= ("success".equals(status));
        allTestCasesPassed &= (executeTestCase.getActionErrors()!=null?executeTestCase.getActionErrors().size()==0:true);

        List<Caseexecution> caseExecutions = scenarioexecution.getCaseexecutions();
        assertNotNull(caseExecutions);
        assertTrue(caseExecutions.size()>0);
        for (Caseexecution caseexecution : caseExecutions) {
            allTestCasesPassed &= verifyCaseExecutionResults(caseexecution, allTestCasesPassed);
        }
        logger.info((allTestCasesPassed?">>>>>":"<<<<<")+scenarioexecution.getScenario().getScenarioName() + (allTestCasesPassed?" passed!!":" failed!!"));
        assertTrue(allTestCasesPassed);
    }

    private static ExecuteTestCase createExecuteTestCase(Scenarioexecution scenarioexecution, String scenarioLevelExecution) {
        ExecuteTestCase executeTestCase = new ExecuteTestCase();
        executeTestCase.setSession(new HashMap<String,Object>());
        executeTestCase.setParameters(new HashMap<String,String[]>());
        executeTestCase.setRequest(new HashMap<String,Object>());

        executeTestCase.getSessionAttributes().put("testScenario", scenarioexecution);

        executeTestCase.setProfile(AutoTestLabSetup.instance().getProfile());
        executeTestCase.setParticipant(scenarioexecution.getParticipant());
        executeTestCase.setSelectedScenarioExecutionId(scenarioexecution.getScenarioExecutionId());
        executeTestCase.setSelectedScenarioId(scenarioexecution.getScenario().getScenarioId());
        executeTestCase.setScenarioLevelExecution(scenarioLevelExecution);
        executeTestCase.setTestScenario(scenarioexecution);

        return executeTestCase;
    }

    private static String executeExecuteTestCase(ExecuteTestCase executeTestCase, Scenarioexecution scenarioexecution) throws Exception {
        logger.info("Executing scenario....."+scenarioexecution.getScenario().getScenarioName());
        long timeStarted = System.currentTimeMillis();
        String status = executeTestCase.execute();
        logger.info(scenarioexecution.getScenario().getScenarioName() + " execution completed in "+DateUtil.getElapsedTime(timeStarted, System.currentTimeMillis(), false)+" action messages="+executeTestCase.getActionMessages());
        return status;
    }

    private static boolean verifyCaseExecutionResults(Caseexecution caseexecution, boolean allTestCasesPassed) {
        List<Caseresult> caseResults = caseexecution.getCaseresults();
        if (caseResults!=null && caseResults.size()>0) {
            Caseresult caseresult = caseResults.get(caseResults.size()-1);
            logger.info("\t\t"+caseexecution.getScenarioexecution().getScenario().getScenarioName()+ " " + caseexecution.getTestcase().getName()+ " result="+caseresult.getResult() + " resultId="+caseresult.getResultId() + " resultMessage="+caseresult.getMessage()+ "\n\t\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            allTestCasesPassed &= (caseresult.getResult()!=null && caseresult.getResult().equals("PASS"));
        } else {
            logger.info("\t\tNo caseResults for "+caseexecution.getScenarioexecution().getScenario().getScenarioName() + " " + caseexecution.getTestcase().getName()+ "\n\t\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
        }
        return allTestCasesPassed;
    }

}
