/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.scenario.service;

import java.util.List;

import net.aegis.lab.dao.ScenarioDAO;
import net.aegis.lab.dao.ScenariocaseDAO;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("scenarioService")
public class ScenarioServiceImpl implements ScenarioService {

    @Autowired
    private ScenarioDAO scenarioDAO;
    @Autowired
    private ScenariocaseDAO scenarioCaseDAO;
    
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ScenarioServiceImpl.class);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Scenario scenarioExecution) throws ServiceException {
        log.info("Saving Scenario..........");
        try {
            return scenarioDAO.create(scenarioExecution);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Scenario scenario) throws ServiceException {
        log.info("Saving Scenario..........");
        try {
            scenarioDAO.update(scenario);
        } catch (Exception e) {
            log.error("ERROR: failure saving scenario: '"+scenario.getScenarioName()+"'", e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void updateScenarioAndScenarioCases(Scenario scenario) throws ServiceException {
        log.info("Saving Scenario..........");
        try {
            for (Scenariocase scenarioCase : scenario.getScenariocases()) {
                scenarioCaseDAO.update(scenarioCase);
            }
            scenarioDAO.update(scenario);
        } catch (Exception e) {
            log.error("ERROR: failure saving scenario: '"+scenario.getScenarioName()+"'", e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public Scenario read(Integer id) throws ServiceException {
        log.info("Reading Scenario..........");
        try {
            return scenarioDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("DeletingByID Scenario..........");
        try {
            scenarioDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Scenario persistentObject) throws ServiceException {
        log.info("Deleting Scenario..........");
        try {
            scenarioDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public List<Scenario> findByScenarioName(String scenarioName) throws ServiceException {
        log.info("FindBy Scenario Name..........");
        try {
            return scenarioDAO.findByScenarioName(scenarioName);
        } catch (Exception e) {
            log.error("ERROR: failure finding scenario: '"+scenarioName+"'", e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Override
    public List<Scenario> findAll() throws ServiceException {
        log.info("findAll..........");
        try {
            return scenarioDAO.findAll();
        } catch (Exception e) {
            log.error("ERROR: failure finding scenarios", e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

}
