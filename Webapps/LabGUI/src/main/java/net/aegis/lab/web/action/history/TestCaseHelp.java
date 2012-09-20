package net.aegis.lab.web.action.history;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.web.action.BaseAction;

public class TestCaseHelp extends BaseAction {

    private static final long serialVersionUID = 1L;
    int iTheRoleOfTheLoggedInUser = -1;
    private Nhinrep nhinrep;
    private Participant participant;
    private String scenarioExecutionId = "";
    private Scenarioexecution submittedScenario;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("history.TestCaseHelp.execute() - INFO");
        log.info("history.TestCaseHelp.execute() - scenarioExecutionId="+scenarioExecutionId);

        try {
            if (this.getProfile() != null) {
                iTheRoleOfTheLoggedInUser = this.getProfile().getPrimaryRole();
                log.info("history.TestCaseHelp.execute(): role of the logged in user = " + iTheRoleOfTheLoggedInUser + " - INFO");
            }
            submittedScenario = ValidationManager.getInstance().readScenarioExecution(scenarioExecutionId);
            this.setSubmittedScenario(submittedScenario);
            
            // support for navigation
            this.getSession().setAttribute("sessionobj.submittedScenario", submittedScenario);  // used only by history.TestCaseSpec.java
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
        return SUCCESS;
    }

    public Nhinrep getNhinrep() {
        return nhinrep;
    }

    public void setNhinrep(Nhinrep nhinrep) {
        this.nhinrep = nhinrep;
    }

    /**
     * @return the participant
     */
    public Participant getParticipant() {
        return participant;
    }

    /**
     * @param participant the participant to set
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    /**
     * @return the scenarioExecutionId
     */
    public String getScenarioExecutionId() {
        return scenarioExecutionId;
    }

    /**
     * @param scenarioExecutionId the scenarioExecutionId to set
     */
    public void setScenarioExecutionId(String scenarioExecutionId) {
        this.scenarioExecutionId = scenarioExecutionId;
    }

    /**
     * @return the submittedScenario
     */
    public Scenarioexecution getSubmittedScenario() {
        return submittedScenario;
    }

    /**
     * @param submittedScenario the submittedScenario to set
     */
    public void setSubmittedScenario(Scenarioexecution submittedScenario) {
        this.submittedScenario = submittedScenario;
    }
}
