package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenariocaseparameters;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.scenariocaseparams.service.ScenariocaseParamsService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Venkat Keesara
 */
public class ScenariocaseParamsManager {

    private static final Log log = LogFactory.getLog(ScenariocaseParamsManager.class);
    ScenariocaseParamsService testCaseParamsService = (ScenariocaseParamsService) ContextUtil.getLabContext().getBean("scenarioCaseParamsService");
    private static ScenariocaseParamsManager instance;

    private ScenariocaseParamsManager() {
    }

    /**
     * @return ScenariocaseParamsManager
     */
    public static ScenariocaseParamsManager getInstance() {
        if (instance == null) {
            instance = new ScenariocaseParamsManager();
        }
        return instance;
    }

    public Scenariocaseparameters findByScenariocaseparamsId(int scenariocaseparamsId) throws ServiceException {
        log.info("ScenariocaseParamsManager.findByScenariocaseparamsId() - INFO");
        return testCaseParamsService.read(scenariocaseparamsId);
    }

    public List<Scenariocaseparameters> findByScenarioId(Integer scenarioId) throws ServiceException {
        log.info("ScenariocaseParamsManager.findByScenarioId() - INFO");
        return testCaseParamsService.findByScenarioId(scenarioId);
    }

    public List<Scenariocaseparameters> findByCaseId(Integer caseId) throws ServiceException {
        log.info("ScenariocaseParamsManager.findByCaseId() - INFO");
        return testCaseParamsService.findByCaseId(caseId);
    }
    public List<Scenariocaseparameters> findByScenarioIdAndCaseId(int scenarioId, int caseId) throws ServiceException {
        log.info("ScenariocaseParamsManager.findByScenarioIdAndCaseId() - INFO");
        return testCaseParamsService.findByScenarioIdAndCaseId(scenarioId, caseId);
    }


}
