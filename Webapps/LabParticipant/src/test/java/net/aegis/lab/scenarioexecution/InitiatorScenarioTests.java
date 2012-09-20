package net.aegis.lab.scenarioexecution;

/**
 * This class can be used to execute all of the initiator scenarios as if they were being executed on LabParticipant UI.
 *
 * @author Tareq.Nabeel
 */
public class InitiatorScenarioTests extends TestlabTestCase {

    public void testScenario1() throws Exception {
        ScenarioExecutor.executeScenario(1);
    }

    public void testScenario2() throws Exception {
        ScenarioExecutor.executeScenario(2);
    }

    public void testScenario4() throws Exception {
        ScenarioExecutor.executeScenario(4);
    }

    public void testScenario6() throws Exception {
        ScenarioExecutor.executeScenario(6);
    }

    public void testScenario29() throws Exception {
        ScenarioExecutor.executeScenario(29);
    }

    public void testScenario44() throws Exception {
        ScenarioExecutor.executeScenario(44);
    }

    public void testScenario61() throws Exception {
        ScenarioExecutor.executeScenario(61);
    }

    public void testScenario62() throws Exception {
        ScenarioExecutor.executeScenario(62);
    }
}
