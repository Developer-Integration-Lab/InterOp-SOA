package net.aegis.lab.participant.web.action.test;

import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.participant.web.action.BaseAction;
import net.aegis.lab.util.LabConstants.LabType;

public class ActiveScenarios extends BaseAction {

    private static final long serialVersionUID = 1L;

    private Participant participant;
    private List<Scenarioexecution> testScenarios;

    public ActiveScenarios() {
        //
    }

    @Override
    public String execute() throws Exception {

        log.info("ActiveScenarios.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("ActiveScenarios.execute() - participant is " + participant.getParticipantName());

                this.setTestScenarios(ParticipantManager.getInstance().activeScenarioExecutions(participant.getParticipantId(),LabType.OPTION_ACTIVE_LAB_ONLY));

                //Set the headerType value as "Scenario" for lab / "Test Group" for conformance mode in session to display in jsp page
               if(this.getTestScenarios() != null && this.getTestScenarios().size() > 0){
                   int labMode = this.getTestScenarios().get(0).getScenario().getServiceset().getLabAccessFilter();
                   this.getSession().setAttribute("headerType",LabType.getType(labMode));
               }else{
                    this.getSession().setAttribute("headerType",LabType.getType(LabType.LAB.getId()));
               }

                this.getSession().setAttribute("testScenarios", this.getTestScenarios());
            }
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

    public List<Scenarioexecution> getTestScenarios() {
        return testScenarios;
    }

    public void setTestScenarios(List<Scenarioexecution> testScenarios) {
        this.testScenarios = testScenarios;
    }

}
