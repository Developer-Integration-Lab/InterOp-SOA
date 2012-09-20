/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.scenariocase.service;

import java.io.File;
import java.util.List;

import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Scenariocase.ScenariocasePK;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface ScenarioCaseService {

   /*
    * Standard CRUD Methods
    */
    public ScenariocasePK create(Scenariocase newInstance) throws ServiceException;

    public Scenariocase read(ScenariocasePK scenarioCasePK) throws ServiceException;

    public void update(Scenariocase transientObject) throws ServiceException;

    public void delete(Scenariocase persistentObject) throws ServiceException;

    public void deleteByPK(ScenariocasePK scenarioCasePK) throws ServiceException;

    public File getPatientData(int scenarioId, int caseId) throws ServiceException;

    public int[] getParticipatingRIs(int scenarioId, int caseId) throws ServiceException;

    public List<Scenariocase> getScenarioCasesByScenarioId(int scenarioId)throws ServiceException;
    public String getPatientDataFromConfig(int scenarioId, int caseId) throws ServiceException ;
}
