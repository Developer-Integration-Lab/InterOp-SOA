package net.aegis.lab.scenarioexecution;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.aegis.lab.util.AutoTestLogger;


import org.apache.struts2.StrutsTestCase;

/**
 * This class can be used to execute all of the responder test cases for Conformance  as if they were being executed on Lab UI.
 * 
 * @author Venkat Keesara
 */
public class ResponderConfTest extends StrutsTestCase {

    private AutoTestLogger autoTestLogger = AutoTestLogger.instance();

    @Override
    public void setUp() throws Exception {
        autoTestLogger.open("./confTestsOutput.txt");
        autoTestLogger.info("********************************************************************************************************************************************************************************************************************************************");
        autoTestLogger.info("Executing junit test "+this.getName() + " at "+new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date()));
    }

    @Override
    protected void tearDown() throws Exception {
        autoTestLogger.close();
    }

    public void testScenario1004() throws Exception {
        ScenarioExecutor.executeConfScenario(1004);
    }

    
}
