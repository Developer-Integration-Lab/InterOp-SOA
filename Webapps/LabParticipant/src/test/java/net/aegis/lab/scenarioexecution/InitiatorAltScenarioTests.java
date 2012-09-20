package net.aegis.lab.scenarioexecution;

/**
 * This class can be used to execute all of the initiator scenarios as if they were being executed on LabParticipant UI.
 *
 * @author Venkat.Keesara
 */
public class InitiatorAltScenarioTests extends TestlabTestCase {

    public void testAltScenario1() throws Exception {
        AltScenarioExecutor.executeScenario(1);
    }

//    public void testAltScenario2() throws Exception {
//        AltScenarioExecutor.executeScenario(2);
//    }

    
}
