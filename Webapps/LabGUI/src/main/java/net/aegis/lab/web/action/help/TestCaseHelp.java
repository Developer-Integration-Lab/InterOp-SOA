package net.aegis.lab.web.action.help;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.web.action.BaseAction;

public class TestCaseHelp extends BaseAction {

    private static final long serialVersionUID = 1L;

    private int selectedScenarioExecutionId;
    private List<Scenarioexecution> testScenarios;
    private Scenarioexecution testScenario;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("TestCaseHelp.execute() - INFO");

        try {
            testScenarios = (List<Scenarioexecution>) this.getSession().getAttribute("testScenarios");

            String selectedScenarioExecutionIdString = this.getRequest().getParameter("scenarioExecutionId");
            if (selectedScenarioExecutionIdString != null) {
                selectedScenarioExecutionId = Integer.parseInt(selectedScenarioExecutionIdString);
                log.warn("TestCaseHelp.execute() - selectedScenarioExecutionId parameter is " + selectedScenarioExecutionId);
                this.setTestScenario(this.findTestScenario());
            }
            else {
                log.warn("TestCaseHelp.execute() - selectedScenarioExecutionId parameter IS NOT DEFINED");
            }

            this.getSession().setAttribute("testScenario", this.getTestScenario());
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    public int getSelectedScenarioExecutionId() {
        return selectedScenarioExecutionId;
    }

    public void setSelectedScenarioExecutionId(int selectedScenarioExecutionId) {
        this.selectedScenarioExecutionId = selectedScenarioExecutionId;
    }

    public Scenarioexecution getTestScenario() {
        return testScenario;
    }

    public void setTestScenario(Scenarioexecution testScenario) {
        this.testScenario = testScenario;
    }

    private Scenarioexecution findTestScenario() {
        Scenarioexecution returnScenario = null;

        if (selectedScenarioExecutionId > 0) {
            log.warn("TestCaseHelp.findTestScenario() - scenarioId > 0");
            for (Scenarioexecution findScenario : testScenarios) {
                if (findScenario != null && findScenario.getScenarioExecutionId() == selectedScenarioExecutionId) {
                    log.warn("TestCaseHelp.findTestScenario() - returnScenario eq findScenario");
                    returnScenario = findScenario;
                    this.setSelectedScenarioExecutionId(returnScenario.getScenarioExecutionId());
                    break;
                }
            }
        }

        return returnScenario;
    }

}
