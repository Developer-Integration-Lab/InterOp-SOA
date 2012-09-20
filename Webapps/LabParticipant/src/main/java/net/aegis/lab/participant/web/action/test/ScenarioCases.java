package net.aegis.lab.participant.web.action.test;

import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.participant.web.action.BaseAction;

public class ScenarioCases extends BaseAction {

    private static final long serialVersionUID = 1L;

    private Participant participant;
    private int selectedScenarioExecutionId;
    private List<Scenarioexecution> testScenarios;
    private Scenarioexecution testScenario;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("ScenarioCases.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("ScenarioCases.execute() - participant is " + participant.getParticipantName());
            }

            testScenarios = (List<Scenarioexecution>) this.getSession().getAttribute("testScenarios");

            if (selectedScenarioExecutionId == 0) {
                String selectedScenarioExecutionIdString = this.getRequest().getParameter("scenarioExecutionId");
                if (selectedScenarioExecutionIdString != null) {
                    selectedScenarioExecutionId = Integer.parseInt(selectedScenarioExecutionIdString);
                    log.warn("ScenarioCases.execute() - selectedScenarioExecutionId parameter is " + selectedScenarioExecutionId);
                    this.setTestScenario(this.findTestScenario());
                }
                else {
                    log.warn("ScenarioCases.execute() - selectedScenarioExecutionId parameter IS NOT DEFINED");
                }
            }
            else {
                this.setTestScenario(ParticipantManager.getInstance().returnScenarioExecution(selectedScenarioExecutionId));
                log.warn("ScenarioCases.execute() - Refreshed selectedScenarioExecutionId " + selectedScenarioExecutionId);
            }

            this.getSession().setAttribute("testScenario", this.getTestScenario());
        } catch (Exception e) {
            log.error("Exception", e);
            addActionError(e.getMessage());
        }

        return SUCCESS;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
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
            log.warn("ScenarioResults.findTestScenario() - scenarioId > 0");
            for (Scenarioexecution findScenario : testScenarios) {
                if (findScenario != null && findScenario.getScenarioExecutionId() == selectedScenarioExecutionId) {
                    log.warn("ScenarioResults.findTestScenario() - returnScenario eq findScenario");
                    returnScenario = findScenario;
                    this.setSelectedScenarioExecutionId(returnScenario.getScenarioExecutionId());
                    break;
                }
            }
        }

        return returnScenario;
    }

}
