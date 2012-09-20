package net.aegis.lab.web.action.admin;

import java.util.List;

import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.manager.TestcaseManager;
import net.aegis.lab.web.action.BaseAction;

/**
 * This class manages the actions related to test cases.  It currently provides fetchTestCases action to support listing of test cases
 * on the UI.
 *
 * @author Tareq.Nabeel
 */
public class ManageTestCases extends BaseAction {

    private static final long serialVersionUID = 1L;
    private List<Testcase> testCases;

    public List<Testcase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<Testcase> testCases) {
        this.testCases = testCases;
    }

    public String fetchTestCases() throws Exception {
        log.info("ManageTestCases.fetchTestCases()");
        try {
            setTestCases(TestcaseManager.getInstance().findAllTestcases());
            log.info("ManageTestCases.fetchTestCases() - testCases.size=" + testCases.size());
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }
}
