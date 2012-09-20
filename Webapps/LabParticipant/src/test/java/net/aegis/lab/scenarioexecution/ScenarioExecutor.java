package net.aegis.lab.scenarioexecution;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import net.aegis.lab.dao.pojo.Altscenariocase;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.participant.web.action.test.ExecuteTestCase;
import net.aegis.lab.util.AutoTestLabSetup;
import net.aegis.lab.util.AutoTestLogger;
import net.aegis.lab.util.DateUtil;

/**
 * This class is used by automated tests to execute scenarios and verify results
 *
 * @author Tareq.Nabeel
 */
public class ScenarioExecutor {

    private static AutoTestLogger autoTestLogger = AutoTestLogger.instance();
    // globar variable to turn on alternative scenarios
    protected static boolean  testAltScenarioCase = false;



    /**
     * Will execute the entire scenario including all the test cases.
     * This method will also verify successful execution of the test cases.
     *
     * @param scenarioNumber
     * @throws Exception
     */
    public static void executeScenario(int scenarioNumber) throws Exception {
        Scenarioexecution scenarioexecution = AutoTestLabSetup.instance().getScenarioExecution(scenarioNumber);
        executeScenario(scenarioexecution);
    }

    /**
     * Will execute only the testcase specified within a given scenario.
     * This method will also verify successful execution of the test case.
     *
     * @param scenarioNumber
     * @param testCaseName
     * @throws Exception
     */
    public static void executeScenario(int scenarioNumber, String testCaseName) throws Exception {
        Scenarioexecution scenarioexecution = AutoTestLabSetup.instance().getScenarioExecution(scenarioNumber);
        executeScenario(scenarioexecution, testCaseName, null);
    }

    /**
     * Will execute all testcases of the specified messageType (e.g. PD) for a given scenario.
     * This method will also verify successful execution of the test cases.
     *
     * @param scenarioNumber
     * @param messageType
     * @throws Exception
     */
    public static void executeScenario(int scenarioNumber, MessageType messageType) throws Exception {
        Scenarioexecution scenarioexecution = AutoTestLabSetup.instance().getScenarioExecution(scenarioNumber);
        executeScenario(scenarioexecution, null, messageType);
    }

    /**
     * Will execute the entire scenario including all the test cases.
     * This method will also verify successful execution of the test cases.
     *
     * @param scenarioexecution
     * @throws Exception
     */
    public static void executeScenario(Scenarioexecution scenarioexecution) throws Exception {
        executeScenario(scenarioexecution, null, null);
    }

    private static void executeScenario(Scenarioexecution scenarioexecution, String testCaseName, MessageType messageType) throws Exception {
        assertNotNull(scenarioexecution);
        Scenario scenario = scenarioexecution.getScenario();
        assertNotNull(scenario);
        List<Caseexecution> caseExecutions = scenarioexecution.getCaseexecutions();
        assertNotNull(caseExecutions);
        assertTrue(caseExecutions.size()>0);
        boolean allTestCasesPassed = true;
        for (Caseexecution caseexecution : caseExecutions) {
            if (testCaseName!=null) {
                if (!testCaseName.equals(caseexecution.getTestcase().getName())) {
                    continue;
                }
            }
            if (messageType!=null) {
                if (MessageType.valueOf(caseexecution.getTestcase().getMessageType())!=messageType) {
                    continue;
                }
            }            
            allTestCasesPassed &= executeAndVerifyCaseExecution(scenarioexecution, caseexecution, allTestCasesPassed);
        }
        autoTestLogger.info((allTestCasesPassed?">>>>>":"<<<<<")+scenarioexecution.getScenario().getScenarioName() + (allTestCasesPassed?" passed!!":" failed!!"));
        assertTrue(allTestCasesPassed);
    }

    private static boolean executeAndVerifyCaseExecution(Scenarioexecution scenarioexecution, Caseexecution caseexecution, boolean allTestCasesPassed) throws Exception {
        autoTestLogger.info("testAltScenarioCase===" + testAltScenarioCase);
        if (!testAltScenarioCase) {
            // happy path testing which will have altScenarioCaseId = 0 
            ExecuteTestCase executeTestCase = createExecuteTestCase(scenarioexecution, caseexecution, 0);
            String status = executeExecuteTestCase(executeTestCase, caseexecution);
            allTestCasesPassed &= ("success".equals(status));
            allTestCasesPassed &= (executeTestCase.getActionErrors() != null ? executeTestCase.getActionErrors().size() == 0 : true);

            /*if (scenarioexecution.getScenario().isInitiator() && AutoTestLabSetup.instance().isExecuteCoordinator()) {
                AuditMessageProcessor auditProcessor = new AuditMessageProcessorImpl();
                auditProcessor.processMessages();
            }*/
            caseexecution = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(caseexecution.getCaseExecutionId());
            allTestCasesPassed &= verifyCaseExecutionResults(caseexecution, allTestCasesPassed);
            return allTestCasesPassed;

        } else {
            List<Altscenariocase> altscenariocases = caseexecution.getAltscenariocases();
            if (altscenariocases != null && altscenariocases.size() > 1) {
                for (Altscenariocase eachAltscenariocase : altscenariocases) {
                    Integer altscenariocaseId = eachAltscenariocase.getAltscenariocaseId();
                    ExecuteTestCase executeTestCase = createExecuteTestCase(scenarioexecution, caseexecution, altscenariocaseId);
                    String status = executeExecuteTestCase(executeTestCase, caseexecution);
                    allTestCasesPassed &= ("success".equalsIgnoreCase(status));
                    allTestCasesPassed &= (executeTestCase.getActionErrors() != null ? executeTestCase.getActionErrors().size() == 0 : true);
                   /*  if (scenarioexecution.getScenario().isInitiator() && AutoTestLabSetup.instance().isExecuteCoordinator()) {
                        AuditMessageProcessor auditProcessor = new AuditMessageProcessorImpl();
                        auditProcessor.processMessages();
                    }*/
                    caseexecution = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(caseexecution.getCaseExecutionId());
                    allTestCasesPassed &= verifyCaseExecutionResultsForFAIL(caseexecution, allTestCasesPassed);
                }
            }
        }
       
        return allTestCasesPassed;
    }

   

    private static ExecuteTestCase createExecuteTestCase(Scenarioexecution scenarioexecution, Caseexecution caseexecution,Integer altScenarioCaseId) {
        ExecuteTestCase executeTestCase = new ExecuteTestCase();
        executeTestCase.setSession(new HashMap<String,Object>());
        executeTestCase.setParameters(new HashMap<String,String[]>());
        executeTestCase.setRequest(new HashMap<String,Object>());

        executeTestCase.getSessionAttributes().put("testScenario", scenarioexecution);

        executeTestCase.setProfile(AutoTestLabSetup.instance().getProfile());
        executeTestCase.setParticipant(scenarioexecution.getParticipant());
        executeTestCase.setSelectedScenarioExecutionId(scenarioexecution.getScenarioExecutionId());
        executeTestCase.setSelectedScenarioId(scenarioexecution.getScenario().getScenarioId());
        executeTestCase.setTestScenario(scenarioexecution);
        executeTestCase.setSelectedCaseId(caseexecution.getTestcase().getCaseId());
       
        executeTestCase.setSelectedAltScenarioCaseId(altScenarioCaseId); //Happy Path
        return executeTestCase;
    }

    private static String executeExecuteTestCase(ExecuteTestCase executeTestCase, Caseexecution caseexecution) throws Exception {
        String testCaseFullName = caseexecution.getScenarioexecution().getScenario().getScenarioName() + " " + caseexecution.getTestcase().getName();
        autoTestLogger.info("Executing test case....."+testCaseFullName);
        long timeStarted = System.currentTimeMillis();
        String status = executeTestCase.execute();
        autoTestLogger.info(testCaseFullName + " execution completed in "+DateUtil.getElapsedTime(timeStarted, System.currentTimeMillis(), false)+" action messages="+executeTestCase.getActionMessages() + " actionErrors="+executeTestCase.getActionErrors());
        return status;
    }

    private static boolean verifyCaseExecutionResults(Caseexecution caseexecution, boolean allTestCasesPassed) {
        List<Caseresult> caseResults = caseexecution.getCaseresults();
        if (caseResults!=null && caseResults.size()>0) {
            Caseresult caseresult = caseResults.get(caseResults.size()-1);
            autoTestLogger.info("\t\t"+caseexecution.getScenarioexecution().getScenario().getScenarioName()+ " " + caseexecution.getTestcase().getName()+ " result="+caseresult.getResult() + " resultId="+caseresult.getResultId() + " resultMessage="+caseresult.getMessage()+ "\n\t\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            allTestCasesPassed &= (caseresult.getResult()!=null && caseresult.getResult().equals("PASS"));
        } else {
            autoTestLogger.info("\t\tNo caseResults for "+caseexecution.getScenarioexecution().getScenario().getScenarioName() + " " + caseexecution.getTestcase().getName()+ "\n\t\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
        }
        return allTestCasesPassed;
    }
    private static boolean verifyCaseExecutionResultsForFAIL(Caseexecution caseexecution, boolean allTestCasesPassed) {
        List<Caseresult> caseResults = caseexecution.getCaseresults();
        if (caseResults!=null && caseResults.size()>0) {
            Caseresult caseresult = caseResults.get(caseResults.size()-1);
            autoTestLogger.info("\t\t"+caseexecution.getScenarioexecution().getScenario().getScenarioName()+ " " + caseexecution.getTestcase().getName()+ " result="+caseresult.getResult() + " resultId="+caseresult.getResultId() + " resultMessage="+caseresult.getMessage()+ "\n\t\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            allTestCasesPassed &= (caseresult.getResult()!=null && caseresult.getResult().equals("FAIL"));
        } else {
            autoTestLogger.info("\t\tNo caseResults for "+caseexecution.getScenarioexecution().getScenario().getScenarioName() + " " + caseexecution.getTestcase().getName()+ "\n\t\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
        }
        return allTestCasesPassed;
    }

     
}
