package net.aegis.lab.scenarioexecution;


/**
 * Allows you to execute an individual test case for debugging purposes.  You can specify messageType or testCase name.
 * 
 * @author Tareq.Nabeel
 */
public class TestCaseTest extends TestlabTestCase {


    public void testTestCase() throws Exception {
        //ScenarioExecutor.executeScenario(1);
        ScenarioExecutor.executeScenario(1, MessageType.QD);
        //ScenarioExecutor.executeScenario(1, "PDI-2.1a");
    }

}
