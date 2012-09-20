package net.aegis.lab.web.action.help;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.web.action.BaseAction;

public class TestCaseSpec extends BaseAction {

    private static final long serialVersionUID = 1L;

    private Integer selectedCaseExecutionId;
    private Scenarioexecution testScenario;
    private Testcase testcase;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("TestCaseSpec.execute() - INFO");

        try {
            testScenario = (Scenarioexecution) this.getSession().getAttribute("testScenario");

            if (selectedCaseExecutionId == null) {
                String selectedCaseExecutionIdString = this.getRequest().getParameter("selectedCaseExecutionId");
                if (selectedCaseExecutionIdString != null) {
                    selectedCaseExecutionId = Integer.parseInt(selectedCaseExecutionIdString);
                    log.warn("TestCaseSpec.execute() - selectedCaseExecutionId parameter is " + selectedCaseExecutionIdString);
                }
                else {
                    log.warn("TestCaseHelp.execute() - selectedCaseExecutionId parameter IS NOT DEFINED");
                }
            }
            this.setTestcase(this.findTestCase());

            this.getSession().setAttribute("testcase", this.getTestcase());
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    public Integer getSelectedCaseExecutionId() {
        return selectedCaseExecutionId;
    }

    public void setSelectedCaseExecutionId(Integer selectedCaseExecutionId) {
        this.selectedCaseExecutionId = selectedCaseExecutionId;
    }

    public Scenarioexecution getTestScenario() {
        return testScenario;
    }

    public void setTestScenario(Scenarioexecution testScenario) {
        this.testScenario = testScenario;
    }

    public Testcase getTestcase() {
        return testcase;
    }

    public void setTestcase(Testcase testcase) {
        this.testcase = testcase;
    }

    private Testcase findTestCase() {
        Testcase returnCase = null;

        if (selectedCaseExecutionId > 0) {
            log.warn("TestCaseSpec.findTestCase() - selectedCaseExecutionId > 0");
            for (Caseexecution findCase : testScenario.getCaseexecutions()) {
                if (findCase != null && findCase.getCaseExecutionId().intValue() == selectedCaseExecutionId) {
                    log.warn("TestCaseSpec.findTestCase() - returnCase eq findCase");
                    returnCase = findCase.getTestcase();
                    this.setSelectedCaseExecutionId(findCase.getCaseExecutionId());
                    break;
                }
            }
        }

        return returnCase;
    }

}
