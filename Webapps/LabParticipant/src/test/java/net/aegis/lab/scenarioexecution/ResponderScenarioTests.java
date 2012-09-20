package net.aegis.lab.scenarioexecution;

/**
 * This class can be used to execute all of the responder scenarios as if they were being executed on LabParticipant UI.
 *
 * @author Tareq.Nabeel
 */
public class ResponderScenarioTests extends TestlabTestCase {

    public void testScenario3() throws Exception {
        ScenarioExecutor.executeScenario(3);
    }

    public void testScenario5() throws Exception {
        ScenarioExecutor.executeScenario(5);
    }

    public void testScenario18() throws Exception {
        ScenarioExecutor.executeScenario(18);
    }

    public void testScenario30() throws Exception {
        ScenarioExecutor.executeScenario(30);
    }

    public void testScenario45() throws Exception {
        ScenarioExecutor.executeScenario(45);
    }

    /*
    public void testScenario51() throws Exception {
        ScenarioExecutor.executeScenario(51);
    }

    public void testScenario52() throws Exception {
        ScenarioExecutor.executeScenario(52);
    }
    */

    public void testScenario60() throws Exception {
        ScenarioExecutor.executeScenario(60);
    }

}
