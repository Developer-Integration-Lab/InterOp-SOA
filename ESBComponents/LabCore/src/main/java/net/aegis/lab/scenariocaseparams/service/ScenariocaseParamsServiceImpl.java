/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.scenariocaseparams.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.ScenariocaseparametersDAO;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Scenariocase.ScenariocasePK;
import net.aegis.lab.dao.pojo.Scenariocaseparameters;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Venkat Keesara
 */
@Service("scenarioCaseParamsService")
public class ScenariocaseParamsServiceImpl implements ScenariocaseParamsService{

    @Autowired
    private ScenariocaseparametersDAO scenariocaseParamsDAO;

    private static final Log log = LogFactory.getLog(ScenariocaseParamsServiceImpl.class);

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Scenariocaseparameters newInstance) throws ServiceException {
        log.info("ScenariocaseParamsServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = scenariocaseParamsDAO.create(newInstance);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return id;
    }

    @Override
    public Scenariocaseparameters read(Integer id) throws ServiceException {
        log.info("ScenariocaseParamsServiceImpl.read() - INFO");
        Scenariocaseparameters Scenariocaseparameters = null;
        try {
            Scenariocaseparameters = scenariocaseParamsDAO.read(id);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return Scenariocaseparameters;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Scenariocaseparameters transientObject) throws ServiceException {
        log.info("ScenariocaseParamsServiceImpl.update() - INFO");
        try {
            scenariocaseParamsDAO.update(transientObject);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Scenariocaseparameters persistentObject) throws ServiceException {
        log.info("ScenariocaseParamsServiceImpl.delete(persistentObject) - INFO");
        try {
            scenariocaseParamsDAO.delete(persistentObject);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("ScenariocaseParamsServiceImpl.delete(id) - INFO");
        try {
            scenariocaseParamsDAO.delete(read(id));
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }


    /*
     * Finder methods
     */
    @Override
    public Scenariocaseparameters findByScenariocaseparamsId(int scenariocaseparamsId) throws ServiceException {
        log.info("ScenariocaseParamsServiceImpl.findByScenariocaseparamsId() - INFO");
        return scenariocaseParamsDAO.read(scenariocaseparamsId);
    }

    @Override
     public List<Scenariocaseparameters> findByScenarioId(Integer scenarioId) throws ServiceException{
        log.info("ScenariocaseParamsServiceImpl.findByScenarioId() - INFO");
        return scenariocaseParamsDAO.findByScenarioId(scenarioId);
    }

    @Override
     public List<Scenariocaseparameters> findByCaseId(Integer caseId) throws ServiceException{
        log.info("ScenariocaseParamsServiceImpl.findByCaseId() - INFO");
        return scenariocaseParamsDAO.findByCaseId(caseId);
    }

     @Override
    public List<Scenariocaseparameters> findByScenarioIdAndCaseId(int scenarioId, int caseId) throws ServiceException {
        log.info("ScenariocaseParamsServiceImpl.findByScenarioIdAndCaseId() - INFO");
        Scenariocaseparameters scenarioCaseParams = new Scenariocaseparameters();
        Scenariocase scenarioCase = new Scenariocase();
        ScenariocasePK scenaioCasePK = new ScenariocasePK();
        scenaioCasePK.setCaseId(caseId);
        scenaioCasePK.setScenarioId(scenarioId);
        scenarioCase.setScenariocasePK(scenaioCasePK);
        scenarioCaseParams.setScenariocase(scenarioCase);
        Map<String, String> restrictions = new HashMap<String, String>();
        restrictions.put("caseId", "eq");
        restrictions.put("scenarioId", "eq");
        List<Scenariocaseparameters> scenariocasesParamsList = scenariocaseParamsDAO.searchByCriteria(scenarioCaseParams, restrictions);
        return scenariocasesParamsList ;
    }

}
