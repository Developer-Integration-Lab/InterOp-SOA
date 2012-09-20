package net.aegis.lab.web.action.nhinvalid;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

public class DisplayComments extends BaseAction {

    private static final long serialVersionUID = 1L;
    private String executionUniqueId = "";
    private List<Scenarioexecution> scenarioexecutions = null;

    @Override
    public String execute() throws Exception {

        log.info("DisplayComments.execute() - INFO");

        String executionUniqueId = this.getRequest().getParameter("executionUniqueId");
        log.info("DisplayComments.execute() - executionUniqueId="+executionUniqueId);

        this.setExecutionUniqueId(executionUniqueId);

        // get and save scenario executions that belong to the service set execution -- these scenario executions will be of 'closed' AND 'validated' AND 'failed' status
        List<Scenarioexecution> togetherSEsForThisPage = new ArrayList<Scenarioexecution>();
        List<Scenarioexecution> closedSEs = null;
        List<Scenarioexecution> validatedSEs = null;
        List<Scenarioexecution> failedSEs = null;

        closedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_CLOSED);
        validatedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_VALIDATED);
        failedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_FAILED);

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

        return SUCCESS;
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

}