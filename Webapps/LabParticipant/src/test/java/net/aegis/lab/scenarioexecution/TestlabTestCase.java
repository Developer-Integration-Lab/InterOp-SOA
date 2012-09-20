package net.aegis.lab.scenarioexecution;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.aegis.lab.util.AutoTestLogger;
import org.apache.struts2.StrutsTestCase;

/**
 *
 * @author Tareq.Nabeel
 */
public class TestlabTestCase extends StrutsTestCase {

    private AutoTestLogger autoTestLogger = AutoTestLogger.instance();

    @Override
    public void setUp() throws Exception {
        autoTestLogger.setLogEnabled(true);
        autoTestLogger.open("./scenarioTestsOutput.txt");
        autoTestLogger.info("********************************************************************************************************************************************************************************************************************************************");
        autoTestLogger.info("Executing junit test "+this.getName() + " at "+new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date()));
        //AutoTestLabSetup.instance().reload();
    }

    @Override
    protected void tearDown() throws Exception {
        autoTestLogger.close();
    }

}
