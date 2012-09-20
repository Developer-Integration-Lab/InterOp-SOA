/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.scenariocaseparams.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenariocaseparameters;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Venkat Keesara
 */
public interface ScenariocaseParamsService {


  /*
     * Standard CRUD Methods
     */
    public Integer create(Scenariocaseparameters newInstance) throws ServiceException;

    public Scenariocaseparameters read(Integer id) throws ServiceException;

    public void update(Scenariocaseparameters transientObject) throws ServiceException;

    public void delete(Scenariocaseparameters persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public Scenariocaseparameters findByScenariocaseparamsId(int scenariocaseparamsId) throws ServiceException;

    /**
     * Find Scenariocaseparameters by scenarioId
     */
    public List<Scenariocaseparameters> findByScenarioId(Integer scenarioId) throws ServiceException;

    /**
     * Find Scenariocaseparameters by caseId
     */
    public List<Scenariocaseparameters> findByCaseId(Integer caseId) throws ServiceException;

    public List<Scenariocaseparameters> findByScenarioIdAndCaseId(int scenarioId, int caseId) throws ServiceException;

}
