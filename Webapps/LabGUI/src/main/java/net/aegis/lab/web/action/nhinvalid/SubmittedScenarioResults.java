package net.aegis.lab.web.action.nhinvalid;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.web.action.BaseAction;

/**
 * Requirement - Nhin validator, after logging in, can view his/her dashboard
 *               in order to make decisions on submitted service set executions at the
 *               individual scenario levels.  At this time, s/he can drill-down on the scenario
 *               in order to get more information on the submitted scenario.  This class
 *               supports this particular use case.
 * 
 */
public class SubmittedScenarioResults extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinValidator;
    private String scenarioExecutionId = "";
    private Scenarioexecution submittedScenario;
    private List<Auditsummary> auditSummaryList = new ArrayList<Auditsummary>();

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        
        log.info("SubmittedScenarioResults.execute() - INFO");
        log.info("SubmittedScenarioResults.execute() - scenarioExecutionId="+scenarioExecutionId);


        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.info("SubmittedScenarioResults.execute() - NHIN Validator is " + nhinValidator.getName());

                // Populate NHIN Rep(Validator) Participants
                nhinValidator.setParticipants(ParticipantManager.getInstance().findByNhinRepId(nhinValidator.getNhinRepId()));

                submittedScenario = ValidationManager.getInstance().readScenarioExecution(scenarioExecutionId);

                // To support the Export files feature in nhinvalid
                if (null != submittedScenario) {
                     fetchDetailedAuditSummaryList();
                }

                // support for navigation
                this.getSession().removeAttribute("sessionobj.selectedCaseName");
                this.getSession().setAttribute("sessionobj.scenarioExecutionId", scenarioExecutionId);
                this.getSession().setAttribute("sessionobj.submittedScenario", submittedScenario);  // used only by nhinvalid.TestCaseSpec.java

            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }


    /**
     * Fetch Audit Summary List to support the export file feature in nhinvalid.
     *
     * @throws Exception
     */
    public void fetchDetailedAuditSummaryList() throws Exception {
        List<Caseexecution> caseExecutions = submittedScenario.getCaseexecutions();
        for (Caseexecution caseExec : caseExecutions) {
             log.info("SubmittedScenarioResults.fetchDetailedAuditSummaryList() - CaseExecution TestCase Name: " + caseExec.getTestcase().getName());
            if (null != caseExec.getTestcase().getName()) {
                List<Auditsummary> auditSummarys = ServiceSetExecutionManager.getInstance().getAuditSummariesByScenarioExecIdAndCaseName(submittedScenario, caseExec.getTestcase().getName());
                for (Auditsummary auditSum : auditSummarys) {
                    auditSummaryList.add(auditSum);
                }
            }
        }
        if (auditSummaryList != null && auditSummaryList.size() > 0) {
             log.info("SubmittedScenarioResults.fetchDetailedAuditSummaryList() - auditSummaryList size : "+auditSummaryList.size());
            this.getSession().setAttribute("sessionobj.detailedAuditLogList", auditSummaryList);
        } else {
            this.getSession().removeAttribute("sessionobj.detailedAuditLogList");
        }
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
