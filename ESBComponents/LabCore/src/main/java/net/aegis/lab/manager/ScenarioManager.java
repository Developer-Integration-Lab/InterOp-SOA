/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.scenario.service.ScenarioService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Tareq.Nabeel
 */
public class ScenarioManager {

    private static final Log log = LogFactory.getLog(ScenarioManager.class);
    private static ScenarioManager instance;
    private ScenarioService scenarioService = (ScenarioService) ContextUtil.getLabContext().getBean("scenarioService");

    private ScenarioManager() {
    }

    /**
     * @return ScenarioManager
     */
    public static ScenarioManager getInstance() {
        if (instance == null) {
            instance = new ScenarioManager();
        }
        return instance;
    }

    public List<Scenario> findAllScenarios() throws ServiceException {
        log.info("ScenarioManager.getScenarios() - INFO");
        try { 
            return scenarioService.findAll();
        } catch (ServiceException se) {
            log.error("ERROR: failure finding scenarios.", se);
            se.setErrorCode("errors.find.scenarios.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenarios.", e);
            ServiceException se = new ServiceException("Failure finding scenarios", "errors.find.scenarios.failed", e);
            se.setLogged();
            throw se;
        }
    }

        /**
     * Return Scenario by name
     * @return Scenario
     * @throws ServiceException
     */
    public Scenario findScenarioByName(String scenarioName) throws ServiceException {
        log.info("ScenarioManager.findScenarioByName() scenarioName="+scenarioName);
        try {
            List<Scenario> scenarios = scenarioService.findByScenarioName(scenarioName);
            if (scenarios.size()==0) {
                throw new ServiceException("ERROR: could not find scenario: '"+scenarioName+"'");
            } else {
                return scenarios.get(0);
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding scenario: '"+scenarioName+"'", se);
            se.setErrorCode("errors.find.scenarios.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenario: '"+scenarioName+"'", e);
            ServiceException se = new ServiceException("Failure finding scenario: '"+scenarioName+"'", "errors.find.scenario.failed", e);
            se.setLogged();
            throw se;
        }
    }

    /**
     * Return scenario by id
     * @param id
     * @return
     * @throws ServiceException
     */
    public Scenario findScenarioById(Integer id) throws ServiceException {
        log.info("ScenarioManager.findScenarioById() id="+id);
        try {
            return scenarioService.read(id);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding scenario: '"+id+"'", se);
            se.setErrorCode("errors.find.scenarios.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenario: '"+id+"'", e);
            ServiceException se = new ServiceException("Failure finding scenario: '"+id+"'", "errors.find.scenario.failed", e);
            se.setLogged();
            throw se;
        }
    }

    /**
     * Updates the scenario
     * @param scenario
     * @throws ServiceException
     */
    public void updateScenario(Scenario scenario) throws ServiceException  {
        String scenarioName = scenario!=null?scenario.getScenarioName():null;

        log.info("ScenarioManager.updateScenario() scenario="+scenarioName);
        try {
            scenarioService.update(scenario);
        } catch (ServiceException se) {
            log.error("ERROR: failure updating scenario: '"+scenarioName+"'", se);
            se.setErrorCode("errors.update.scenarios.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure updating scenario: '"+scenarioName+"'", e);
            ServiceException se = new ServiceException("Failure updating scenario: '"+scenarioName+"'", "errors.update.scenario.failed", e);
            se.setLogged();
            throw se;
        }
    }

    /**
     * This method updates the scenario and all its scenario cases in one transaction
     * @param scenario
     * @throws ServiceException
     */
    public void updateScenarioAndScenarioCases(Scenario scenario) throws ServiceException  {
        String scenarioName = scenario!=null?scenario.getScenarioName():null;
        log.info("ScenarioManager.updateScenarioAndScenarioCases() scenario="+scenarioName);
        try {
            scenarioService.updateScenarioAndScenarioCases(scenario);
        } catch (ServiceException se) {
            log.error("ERROR: failure updating scenario: '"+scenarioName+"'", se);
            se.setErrorCode("errors.update.scenarios.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure updating scenario: '"+scenarioName+"'", e);
            ServiceException se = new ServiceException("Failure updating scenario: '"+scenarioName+"'", "errors.update.scenario.failed", e);
            se.setLogged();
            throw se;
        }
    }

    public Integer createScenario(Scenario scenario) throws ServiceException  {
        String scenarioName = scenario!=null?scenario.getScenarioName():null;
        log.info("ScenarioManager.createScenario() scenario="+scenarioName);
        try {
            Integer primaryKey = scenarioService.create(scenario);
            return primaryKey;
        } catch (ServiceException se) {
            log.error("ERROR: failure creating scenario: '"+scenarioName+"'", se);
            se.setErrorCode("errors.create.scenarios.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure creating scenario: '"+scenarioName+"'", e);
            ServiceException se = new ServiceException("Failure creating scenario: '"+scenarioName+"'", "errors.create.scenario.failed", e);
            se.setLogged();
            throw se;
        }
    }
}
