/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.scenariocase.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.aegis.lab.dao.ScenariocaseDAO;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Scenariocase.ScenariocasePK;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("scenarioCaseService")
public class ScenarioCaseServiceImpl implements ScenarioCaseService {

    @Autowired
    private ScenariocaseDAO scenarioCaseDAO;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ScenarioCaseServiceImpl.class);

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public ScenariocasePK create(Scenariocase newInstance) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.create() - INFO");
        ScenariocasePK scenarioCasePK = null;
        try {
            scenarioCasePK = scenarioCaseDAO.create(newInstance);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
        return scenarioCasePK;
    }

    @Override
    public Scenariocase read(ScenariocasePK scenarioCasepk) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.read() - INFO");
        Scenariocase scenariocase = null;
        try {
            scenariocase = scenarioCaseDAO.read(scenarioCasepk);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return scenariocase;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Scenariocase transientObject) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.update() - INFO");
        try {
            scenarioCaseDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Scenariocase persistentObject) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.delete() - INFO");
        try {
            scenarioCaseDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteByPK(ScenariocasePK scenarioCasePK) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.delete() - INFO");
        try {
            scenarioCaseDAO.delete(read(scenarioCasePK));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public File getPatientData(int scenarioId, int caseId) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.delete() - INFO");
        String config = getPatientDataFromConfig(scenarioId, caseId) ; 
        File patientDataFile = null;
        try {
            if (config!=null) {
                patientDataFile = File.createTempFile("temp", ".xml");
                FileOutputStream outStream = new FileOutputStream(patientDataFile);
                log.info("config *********************" + config);
                    outStream.write(config.getBytes());
                    outStream.close();
                log.info("Patient file length --- >>" + patientDataFile.length());
                patientDataFile.deleteOnExit();
            }
        } catch (IOException ex) {
            Logger.getLogger(ScenarioCaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return patientDataFile;
    }
    
    @Override
    public String getPatientDataFromConfig(int scenarioId, int caseId) throws ServiceException {
        log.info("ScenarioCaseServiceImpl.getPatientDataFromConfig() - INFO");
        Scenariocase scenarioCase = new Scenariocase();
        ScenariocasePK scenaioCasePK = new ScenariocasePK();
        scenaioCasePK.setCaseId(caseId);
        scenaioCasePK.setScenarioId(scenarioId);
        scenarioCase.setScenariocasePK(scenaioCasePK);
        String configData = null;
        Map<String, String> restrictions = new HashMap<String, String>();
        restrictions.put("caseId", "eq");
        restrictions.put("scenarioId", "eq");
        restrictions.put("scenaiocasePK", "eq");

        List<Scenariocase> scenariocases = scenarioCaseDAO.searchByCriteria(scenarioCase, restrictions);
            if (scenariocases != null && scenariocases.size() > 0) {
                log.info("*********************" + scenariocases.get(0).getConfig());
                if (scenariocases.get(0).getConfig() != null) {
                	configData = new String(scenariocases.get(0).getConfig());
                }
            }
        return configData;
    }

    @Override
    public int[] getParticipatingRIs(int scenarioId, int caseId) throws ServiceException {
        log.info("getParticipatingRIs CaseExecution..");
        int[] participatingRIArr = null;
        String[] participatingRIs = null;
        Scenariocase scenarioCase = null;

        scenarioCase = new Scenariocase();
        ScenariocasePK scenaioCasePK = new ScenariocasePK();
        scenaioCasePK.setCaseId(caseId);
        scenaioCasePK.setScenarioId(scenarioId);

        try {
            scenarioCase = this.read(scenaioCasePK);
            if (scenarioCase != null && scenarioCase.getParticipatingRIs() != null) {
                participatingRIs = scenarioCase.getParticipatingRIs().split(LabConstants.SPLITTER);
                if (participatingRIs != null && participatingRIs.length > 0) {
                    participatingRIArr = new int[participatingRIs.length];
                    for (int i = 0; i < participatingRIs.length; i++) {
                        participatingRIArr[i] = Integer.parseInt(participatingRIs[i]);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return participatingRIArr;
    }

    @Override
    public List<Scenariocase> getScenarioCasesByScenarioId(int scenarioId)throws ServiceException {
        log.info("ScenarioCaseServiceImpl.delete() - INFO");
        List<Scenariocase> scenariocases = new ArrayList<Scenariocase>();
        try {
            Scenariocase scenarioCase = new Scenariocase();
            List<Criterion> criterionList = new ArrayList<Criterion>();
            ScenariocasePK scenarioCasePk = new ScenariocasePK();
            scenarioCasePk.setScenarioId(scenarioId);

            criterionList.add(Restrictions.eq("scenariocasePK.scenarioId", scenarioId));
            scenariocases = scenarioCaseDAO.searchByCriteria(scenarioCase, criterionList);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex , ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return scenariocases;
    }
}
