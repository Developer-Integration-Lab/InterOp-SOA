package net.aegis.lab.scenarioexecution;


/**
 * You can use this to execute a single scenario for debugging purposes.  Don't forget to change the scenarioNumber in testScenario method if need be.
 *
 * @author Tareq.Nabeel
 */
public class ScenarioTest extends TestlabTestCase {


    public void testScenario() throws Exception {
        ScenarioExecutor.executeScenario(2);
    }

}
