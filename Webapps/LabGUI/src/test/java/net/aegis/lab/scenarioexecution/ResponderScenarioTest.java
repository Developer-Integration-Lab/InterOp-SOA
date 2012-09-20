package net.aegis.lab.scenarioexecution;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.aegis.lab.util.AutoTestLogger;

import org.apache.struts2.StrutsTestCase;

/**
 * You can use this to execute a single test case for debugging purposes.  Don't forget to change the scenarioNumber in testScenario method if need be.
 * 
 * @author Tareq.Nabeel
 */
public class ResponderScenarioTest extends StrutsTestCase {

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

    public void testScenario() throws Exception {
        ScenarioExecutor.executeScenario(3);
    }

}
