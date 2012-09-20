/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.testcase.service.TestCaseService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Tareq.Nabeel
 */
public class TestcaseManager {

    private static final Log log = LogFactory.getLog(TestcaseManager.class);
    private static TestcaseManager instance;
    private TestCaseService testcaseService = (TestCaseService) ContextUtil.getLabContext().getBean("testCaseService");

    private TestcaseManager() {
    }

    /**
     * @return TestcaseManager
     */
    public static TestcaseManager getInstance() {
        if (instance == null) {
            instance = new TestcaseManager();
        }
        return instance;
    }

    public List<Testcase> findAllTestcases() throws ServiceException {
        log.info("TestcaseManager.getTestcases() - INFO");
        try { 
            return testcaseService.findAll();
        } catch (ServiceException se) {
            log.error("ERROR: failure finding testcases.", se);
            se.setErrorCode("errors.find.testcases.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding testcases.", e);
            ServiceException se = new ServiceException("Failure finding testcases", "errors.find.testcases.failed", e);
            se.setLogged();
            throw se;
        }
    }

    public Testcase findTestcaseById(Integer id) throws ServiceException {
        log.info("TestcaseManager.findTestcaseById() id="+id);
        try {
            return testcaseService.read(id);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding testcase id: '"+id+"'", se);
            se.setErrorCode("errors.find.testcases.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding testcase id: '"+id+"'", e);
            ServiceException se = new ServiceException("Failure finding testcase: '"+id+"'", "errors.find.testcase.failed", e);
            se.setLogged();
            throw se;
        }
    }
        /**
     * Return Testcase by name
     * @return Testcase
     * @throws ServiceException
     */
    public Testcase findTestcaseByName(String testcaseName) throws ServiceException {
        log.info("TestcaseManager.findTestcaseByName() testcaseName="+testcaseName);
        try {
            List<Testcase> testcases = testcaseService.findByName(testcaseName);
            if (testcases.size()==0) {
                throw new ServiceException("ERROR: could not find testcase: '"+testcaseName+"'");
            } else {
                return testcases.get(0);
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding testcase: '"+testcaseName+"'", se);
            se.setErrorCode("errors.find.testcases.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding testcase: '"+testcaseName+"'", e);
            ServiceException se = new ServiceException("Failure finding testcase: '"+testcaseName+"'", "errors.find.testcase.failed", e);
            se.setLogged();
            throw se;
        }
    }

    public void updateTestcase(Testcase testcase) throws ServiceException  {
        String testcaseName = testcase!=null?testcase.getName():null;

        log.info("TestcaseManager.updateTestcase() testcase="+testcaseName);
        try {
            testcaseService.update(testcase);
        } catch (ServiceException se) {
            log.error("ERROR: failure updating testcase: '"+testcaseName+"'", se);
            se.setErrorCode("errors.update.testcase.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure updating testcase: '"+testcaseName+"'", e);
            ServiceException se = new ServiceException("Failure updating testcase: '"+testcaseName+"'", "errors.update.testcase.failed", e);
            se.setLogged();
            throw se;
        }
    }

    /**
     * This method updates the test case and all its scenario cases in one transaction
     * @param scenario
     * @throws ServiceException
     */
    public void updateTestcaseAndScenarioCases(Testcase testcase) throws ServiceException  {
        String testCaseName = testcase!=null?testcase.getName():null;
        log.info("TestcaseManager.updateTestcaseAndScenarioCases() test case="+testCaseName);
        try {
            testcaseService.updateTestCaseAndScenarioCases(testcase);
        } catch (ServiceException se) {
            log.error("ERROR: failure updating test case: '"+testCaseName+"'", se);
            se.setErrorCode("errors.update.testcase.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure updating test case: '"+testCaseName+"'", e);
            ServiceException se = new ServiceException("Failure updating test case: '"+testCaseName+"'", "errors.update.testcase.failed", e);
            se.setLogged();
            throw se;
        }
    }

    public Integer createTestcase(Testcase testcase) throws ServiceException  {
        String testcaseName = testcase!=null?testcase.getName():null;
        log.info("TestcaseManager.createTestcase() testcase="+testcaseName);
        try {
            Integer primaryKey = testcaseService.create(testcase);
            return primaryKey;
        } catch (ServiceException se) {
            log.error("ERROR: failure creating testcase: '"+testcaseName+"'", se);
            se.setErrorCode("errors.create.testcases.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure creating testcase: '"+testcaseName+"'", e);
            ServiceException se = new ServiceException("Failure creating testcase: '"+testcaseName+"'", "errors.create.testcase.failed", e);
            se.setLogged();
            throw se;
        }
    }
}
