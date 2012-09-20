package net.aegis.lab.web.action.nhinvalid;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.web.action.BaseAction;

public class TestCaseSpec extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinValidator;
    private Integer selectedCaseExecutionId;
    private Scenarioexecution submittedScenario;
    private Testcase submittedTestcase;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("nhinvalid.TestCaseSpec.execute() - INFO");
        log.info("nhinvalid.TestCaseSpec.execute() - selectedCaseExecutionId="+selectedCaseExecutionId);

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.info("nhinvalid.TestCaseSpec.execute() - NHIN Validator is " + nhinValidator.getName());
                submittedScenario = (Scenarioexecution) this.getSession().getAttribute("sessionobj.submittedScenario");     // set by SubmittedScenarioResults.java
                submittedTestcase = this.findSubmittedTestCase();
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
                    log.info("nhinvalid.TestCaseSpec.findSubmittedTestCase(): Test Case Name = " + returnCase.getName());
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
