package net.aegis.lab.scenarioexecution;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.aegis.lab.util.AutoTestLogger;


import org.apache.struts2.StrutsTestCase;

/**
 * This class can be used to execute all of the responder scenarios as if they were being executed on Lab UI.
 * 
 * @author Tareq.Nabeel
 */
public class ResponderScenarioTests extends StrutsTestCase {

    private AutoTestLogger autoTestLogger = AutoTestLogger.instance();

    @Override
    public void setUp() throws Exception {
        autoTestLogger.open("./scenarioTestsOutput.txt");
        autoTestLogger.info("********************************************************************************************************************************************************************************************************************************************");
        autoTestLogger.info("Executing junit test "+this.getName() + " at "+new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date()));
    }

    @Override
    protected void tearDown() throws Exception {
        autoTestLogger.close();
    }

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

    public void testScenario51() throws Exception {
        ScenarioExecutor.executeScenario(51);
    }

    public void testScenario52() throws Exception {
        ScenarioExecutor.executeScenario(52);
    }

    public void testScenario60() throws Exception {
        ScenarioExecutor.executeScenario(60);
    }

}
