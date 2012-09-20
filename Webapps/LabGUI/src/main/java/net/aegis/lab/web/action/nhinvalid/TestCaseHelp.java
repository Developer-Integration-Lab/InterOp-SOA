package net.aegis.lab.web.action.nhinvalid;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.web.action.BaseAction;

public class TestCaseHelp extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinValidator;
    private String scenarioExecutionId = "";
    private Scenarioexecution submittedScenario;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("nhinvalid.TestCaseHelp.execute() - INFO");
        log.info("nhinvalid.TestCaseHelp.execute() - scenarioExecutionId="+scenarioExecutionId);

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.info("nhinvalid.TestCaseHelp.execute() - NHIN Validator is " + nhinValidator.getName());
                submittedScenario = ValidationManager.getInstance().readScenarioExecution(scenarioExecutionId);

                // support for navigation
                this.getSession().setAttribute("sessionobj.submittedScenario", submittedScenario);  // used only by nhnvalid.TestCaseSpec.java
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
        return SUCCESS;
    }

    public Nhinrep getNhinrep() {
        return nhinValidator;
    }

    public void setNhinrep(Nhinrep nhinValidator) {
        this.nhinValidator = nhinValidator;
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
