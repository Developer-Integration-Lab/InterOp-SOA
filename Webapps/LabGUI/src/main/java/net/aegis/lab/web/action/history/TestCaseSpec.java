package net.aegis.lab.web.action.history;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.web.action.BaseAction;

public class TestCaseSpec extends BaseAction {

    private static final long serialVersionUID = 1L;
    int iTheRoleOfTheLoggedInUser = -1;
    private Nhinrep nhinrep;
    private Participant participant;
    private Integer selectedCaseExecutionId;
    private Scenarioexecution submittedScenario;
    private Testcase submittedTestcase;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("history.TestCaseSpec.execute() - INFO");
        log.info("history.TestCaseSpec.execute() - selectedCaseExecutionId="+selectedCaseExecutionId);

        try {
            if (this.getProfile() != null) {
                iTheRoleOfTheLoggedInUser = this.getProfile().getPrimaryRole();
                log.info("history.TestCaseSpec.execute(): role of the logged in user = " + iTheRoleOfTheLoggedInUser + " - INFO");
            }
                submittedScenario = (Scenarioexecution) this.getSession().getAttribute("sessionobj.submittedScenario");     // set by TestHistoryScenarioResults.java
            this.setSubmittedScenario(submittedScenario);
            submittedTestcase = this.findSubmittedTestCase();
            this.setSubmittedTestcase(submittedTestcase);
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
    
    public Integer getSelectedCaseExecutionId() {
        return selectedCaseExecutionId;
    }

    public void setSelectedCaseExecutionId(Integer selectedCaseExecutionId) {
        this.selectedCaseExecutionId = selectedCaseExecutionId;
    }

    private Testcase findSubmittedTestCase() {
        Testcase returnCase = null;
        if (selectedCaseExecutionId > 0) {
            for (Caseexecution findCase : submittedScenario.getCaseexecutions()) {
                if (findCase != null && findCase.getCaseExecutionId().intValue() == selectedCaseExecutionId) {
                    returnCase = findCase.getTestcase();
                    log.info("history.TestCaseSpec.findSubmittedTestCase(): Test Case Name = " + returnCase.getName());
                    break;
                }
            }
        }
        return returnCase;
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

    /**
     * @return the submittedTestcase
     */
    public Testcase getSubmittedTestcase() {
        return submittedTestcase;
    }

    /**
     * @param submittedTestcase the submittedTestcase to set
     */
    public void setSubmittedTestcase(Testcase submittedTestcase) {
        this.submittedTestcase = submittedTestcase;
    }

}
