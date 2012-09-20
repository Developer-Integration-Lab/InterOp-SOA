package net.aegis.lab.web.action.admin;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.manager.ScenarioManager;
import net.aegis.lab.web.action.BaseAction;

/**
 * This class manages the actions related to scenarios.  It currently provides fetchScenarios action to support listing of scenarios
 * on the UI.
 *
 * @author Tareq.Nabeel
 */
public class ManageScenarios extends BaseAction {

    private static final long serialVersionUID = 1L;

    // List of scenarios retrieved from database
    private List<Scenario> scenarios;

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    /**
     * This method would get called when user clicks on Manage Scenarios
     *
     * @return success if all went well; error otherwise
     */
    public String fetchScenarios() {
        log.info("ManageScenarios.fetchScenarios()");
        try {
            setScenarios(ScenarioManager.getInstance().findAllScenarios());
            log.info("ManageServiceSets.fetchScenarios() - scenarios.size=" + getScenarios()!=null?getScenarios().size():null);
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }

}
