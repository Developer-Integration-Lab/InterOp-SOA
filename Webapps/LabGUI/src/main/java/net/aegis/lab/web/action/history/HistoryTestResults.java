package net.aegis.lab.web.action.history;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.LabConstants.LabType;
import net.aegis.lab.web.action.BaseAction;


/**
 * Requirement - Nhin Rep or Participant can view historical test results (scenarios).
 *               This is a view-only ability of the Nhin Rep or Participant.
 */

public class HistoryTestResults extends BaseAction {

    private static final long serialVersionUID = 1L;
    private String executionUniqueId = "";
    private List<Servicesetexecution> serviceSetExecution = null;   // note - there will be only one in DB !!
    private List<Scenarioexecution> scenarioexecutions = null;
    private String validateAction;

    @Override
    public String execute() throws Exception {

        log.info("HistoryTestResults.execute() - INFO");
        log.info("HistoryTestResults.execute() - executionUniqueId="+executionUniqueId);

        try {
            if (null != validateAction) {
                if (validateAction.equalsIgnoreCase("refresh")) {
                    log.info("HistoryTestResults.execute() - refresh submit button clicked - INFO");
                    setUpThisJSPPageForDisplay();

                    // support for navigation
                    this.getSession().removeAttribute("sessionobj.scenarioExecutionId");
                    this.getSession().removeAttribute("sessionobj.submittedScenario");  // used only by history.TestCaseSpec.java
                    this.getSession().setAttribute("sessionobj.servicesetExecutionUniqueId", executionUniqueId);
                }
            }
            else {
                log.info("HistoryTestResults.execute() - none of the submit buttons clicked on history test results screen - INFO");
                setUpThisJSPPageForDisplay();

                // support for navigation
                this.getSession().removeAttribute("sessionobj.scenarioExecutionId");
                this.getSession().removeAttribute("sessionobj.submittedScenario");  // used only by history.TestCaseSpec.java
                this.getSession().setAttribute("sessionobj.servicesetExecutionUniqueId", executionUniqueId);
            }
        } catch (Throwable e) {
            log.error("HistoryTestResults.execute() - Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    /**
     * Purpose - A simple refactoring method for code reuse. Gets all scenario
     *           executions whose status is 'closed' AND 'validated' AND 'failed'
     *
     * @throws Exception
     */
    private void setUpThisJSPPageForDisplay() throws Exception {

        // get and save the complete service set execution object anyway. Note - there will be only one in DB !!
        setServiceSetExecution(ServiceSetExecutionManager.getInstance().findByExecutionUniqueId(executionUniqueId));

        // set the headerType value as "Scenario" for lab / "Test Group" for conformance mode in session to display in jsp page
         User user = ((User)this.getSession().getAttribute("userProfile"));
         LabType labType = user.getLabType();
            if (labType == null) {
                setHeaderType();
            }

        // get and save scenario executions that belong to the service set execution -- these scenario executions will be of 'closed' AND 'validated' AND 'failed' status
        List<Scenarioexecution> togetherSEsForThisPage = new ArrayList<Scenarioexecution>();
        List<Scenarioexecution> closedSEs = null;
        List<Scenarioexecution> submittedSEs = null;
        List<Scenarioexecution> validatedSEs = null;
        List<Scenarioexecution> failedSEs = null;

        submittedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_SUBMITTED);
        closedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_CLOSED);
        validatedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_VALIDATED);
        failedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_FAILED);

        for(Scenarioexecution ss : submittedSEs) {
            togetherSEsForThisPage.add(ss);
        }
        for(Scenarioexecution vs : validatedSEs) {
            togetherSEsForThisPage.add(vs);
        }
        for(Scenarioexecution fs : failedSEs) {
            togetherSEsForThisPage.add(fs);
        }
        for(Scenarioexecution cs : closedSEs) {
            togetherSEsForThisPage.add(cs);
        }

        this.setScenarioexecutions(togetherSEsForThisPage);
    }

    //Method used to set the headerType value as "Scenario" for lab / "Test Group" for conformance mode in session to display in jsp page
    private void setHeaderType() {
         String labType ="";
         if (this.getServiceSetExecution() != null && this.getServiceSetExecution().size() > 0) {
            int labMode = getServiceSetExecution().get(0).getServiceset().getLabAccessFilter();
            labType = LabType.getType(labMode);
            if (labType != null && !labType.equals("")) {
                this.getSession().setAttribute("nhinrepHeaderType", labType);
            }else {
                labType = LabType.getType(LabType.LAB.getId());
                this.getSession().setAttribute("nhinrepHeaderType", labType);
            }
        } else {
                labType = LabType.getType(LabType.LAB.getId());
                this.getSession().setAttribute("nhinrepHeaderType", labType);
          }
    }

    /**
     * @return the executionUniqueId
     */
    public String getExecutionUniqueId() {
        return executionUniqueId;
    }

    /**
     * @param executionUniqueId the executionUniqueId to set
     */
    public void setExecutionUniqueId(String executionUniqueId) {
        this.executionUniqueId = executionUniqueId;
    }

    /**
     * @return the scenarioexecutions
     */
    public List<Scenarioexecution> getScenarioexecutions() {
        return scenarioexecutions;
    }

    /**
     * @param scenarioexecutions the scenarioexecutions to set
     */
    public void setScenarioexecutions(List<Scenarioexecution> scenarioexecutions) {
        this.scenarioexecutions = scenarioexecutions;
    }

    /**
     * @return the serviceSetExecution
     */
    public List<Servicesetexecution> getServiceSetExecution() {
        return serviceSetExecution;
    }

    /**
     * @param serviceSetExecution the serviceSetExecution to set
     */
    public void setServiceSetExecution(List<Servicesetexecution> serviceSetExecution) {
        this.serviceSetExecution = serviceSetExecution;
    }

    /**
     * @return the validateAction
     */
    public String getValidateAction() {
        return validateAction;
    }

    /**
     * @param validateAction the validateAction to set
     */
    public void setValidateAction(String validateAction) {
        this.validateAction = validateAction;
    }
}
