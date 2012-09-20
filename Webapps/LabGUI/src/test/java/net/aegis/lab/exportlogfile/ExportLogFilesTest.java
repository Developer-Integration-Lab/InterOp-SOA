package net.aegis.lab.exportlogfile;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.aegis.lab.util.AutoTestLogger;

import org.apache.struts2.StrutsTestCase;

/**
 * You can use this to execute a single test case for testing purposes.  
 * Don't forget to change the scenarioNumber in testExportLogFiles method if need be.
 * 
 * @author Naresh.Jaganathan
 */
public class ExportLogFilesTest extends StrutsTestCase {

    private AutoTestLogger autoTestLogger = AutoTestLogger.instance();

    @Override
    public void setUp() throws Exception {
        autoTestLogger.info("********************************************************************************************************************************************************************************************************************************************");
        autoTestLogger.info("Executing junit test "+this.getName() + " at "+new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date()));
    }

    @Override
    protected void tearDown() throws Exception {
        autoTestLogger.close();
    }

    
 }
