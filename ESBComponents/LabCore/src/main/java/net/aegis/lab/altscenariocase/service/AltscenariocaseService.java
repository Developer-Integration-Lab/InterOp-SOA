package net.aegis.lab.altscenariocase.service;

import java.io.File;
import java.util.List;

import net.aegis.lab.dao.pojo.Altscenariocase;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author richard.ettema
 */
public interface AltscenariocaseService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Altscenariocase newInstance) throws ServiceException;

    public Altscenariocase read(Integer id) throws ServiceException;

    public void update(Altscenariocase transientObject) throws ServiceException;

    public void delete(Altscenariocase persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public List<Altscenariocase> findAll() throws ServiceException;

    public List<Altscenariocase> findByScenarioId(int scenarioId) throws ServiceException;

    public List<Altscenariocase> findByScenarioIdCaseId(int scenarioId, int caseId) throws ServiceException;

    /*
     * Utility methods
     */

    public List<Altscenariocase> getSelectionByScenarioIdCaseId(int scenarioId, int caseId) throws ServiceException;
    public File getPatientData(int scenarioId, int caseId, int altScenarioCaseId) throws ServiceException;

	String getPatientDataFromConfig(int pScenarioId, int pCaseId,int pAltScenarioCaseId) throws ServiceException;
}
