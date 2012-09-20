package net.aegis.lab.web.action.participant;

import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.web.action.BaseAction;

public class ActiveTestResults extends BaseAction {

    private static final long serialVersionUID = 1L;

    private Participant participant;
    private List<Scenarioexecution> testScenarios;

    @Override
    public String execute() throws Exception {

        log.info("ActiveTestResults.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("ActiveTestResults.execute() - participant is " + participant.getParticipantName());

                this.setTestScenarios(ParticipantManager.getInstance().activeScenarioExecutions(participant.getParticipantId(),this.getProfile().getLabType()));

                this.getSession().setAttribute("testScenarios", this.getTestScenarios());
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Scenarioexecution> getTestScenarios() {
        return testScenarios;
    }

    public void setTestScenarios(List<Scenarioexecution> testScenarios) {
        this.testScenarios = testScenarios;
    }

}
