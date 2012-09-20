/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.util;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is used to during automated testing to output automated test results.  It should be arguably part of the test directory instead of src.
 *
 * @author Tareq.Nabeel
 */
public class AutoTestLogger {

    private static final Log log = LogFactory.getLog(AutoTestLogger.class);

    private PrintWriter pw;

    private boolean logEnabled;
    private static final AutoTestLogger me = new AutoTestLogger();

    private AutoTestLogger() {}

    public static AutoTestLogger instance() {
        return me;
    }

    public void close() {
        if (pw!=null) {
            pw.close();
        }
    }

    public void info(String message, boolean logIf) {
        if (pw!=null && logIf) {
            pw.println(message);
            pw.flush();
            log.info(message);
        }
    }
    
    public void info(String message) {
        info(message,true);
    }

    public void open(String filePath) {
        try {
            pw = new PrintWriter(new java.io.FileWriter(new java.io.File(filePath), true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void error(String string, Exception ex) {
        if (pw!=null) {
            log.error(string, ex);
            ex.printStackTrace(pw);
        }
    }

    public void error(String message) {
        if (pw!=null) {
            log.error(message);
            pw.println(message);
        }
    }

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }


}
