/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.scenario.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface ScenarioService {

      /*
     * Standard CRUD Methods
     */

    public Integer create(Scenario scenarioExecution)throws ServiceException;
    public void update(Scenario scenarioExecution) throws ServiceException;
    public void updateScenarioAndScenarioCases(Scenario scenario) throws ServiceException;
    public Scenario read(Integer id) throws ServiceException;
    public void delete(Scenario persistentObject) throws ServiceException;
    public void deleteById(Integer Id) throws ServiceException;
    public List<Scenario> findByScenarioName(String scenarioName)throws ServiceException;
    public List<Scenario> findAll() throws ServiceException;

}
