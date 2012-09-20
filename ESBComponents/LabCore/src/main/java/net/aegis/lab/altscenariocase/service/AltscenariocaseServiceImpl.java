package net.aegis.lab.altscenariocase.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.aegis.lab.dao.AltscenariocaseDAO;
import net.aegis.lab.dao.pojo.Altscenariocase;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richard.ettema
 */
@Service("altscenariocaseService")
public class AltscenariocaseServiceImpl implements AltscenariocaseService {

    @Autowired
    private AltscenariocaseDAO altscenariocaseDAO;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(AltscenariocaseServiceImpl.class);

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Altscenariocase newInstance) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = altscenariocaseDAO.create(newInstance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return id;
    }

    @Override
    public Altscenariocase read(Integer id) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.read() - INFO");
        Altscenariocase altscenariocase = null;
        try {
            altscenariocase = altscenariocaseDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return altscenariocase;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Altscenariocase transientObject) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.update() - INFO");
        try {
            altscenariocaseDAO.update(transientObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Altscenariocase persistentObject) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.delete(persistentObject) - INFO");
        try {
            altscenariocaseDAO.delete(persistentObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.delete(id) - INFO");
        try {
            altscenariocaseDAO.delete(read(id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /*
     * Finder methods
     */
    @Override
    public List<Altscenariocase> findAll() throws ServiceException {
        log.info("AltscenariocaseServiceImpl.findAll() - INFO");
        return altscenariocaseDAO.findAll();
    }

    @Override
    public List<Altscenariocase> findByScenarioId(int scenarioId) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.findByScenarioId() - INFO");
        return altscenariocaseDAO.findByScenarioId(scenarioId);
    }

    @Override
    public List<Altscenariocase> findByScenarioIdCaseId(int scenarioId, int caseId) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.findByScenarioId() - INFO");
        List<Altscenariocase> result = new ArrayList<Altscenariocase>();

        List<Altscenariocase> byScenarioId = altscenariocaseDAO.findByScenarioId(scenarioId);
        if (byScenarioId != null) {
            for (Altscenariocase altscenariocase : byScenarioId) {
                if (altscenariocase.getScenariocase() != null &&
                        altscenariocase.getScenariocase().getTestcase() != null &&
                        altscenariocase.getScenariocase().getTestcase().getCaseId() != null &&
                        altscenariocase.getScenariocase().getTestcase().getCaseId().intValue() == caseId) {
                    result.add(altscenariocase);
                }
            }
        }

        return result;
    }

    /*
     * Utility methods
     */
    @Override
    public List<Altscenariocase> getSelectionByScenarioIdCaseId(int scenarioId, int caseId) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.getSelectionByScenarioIdCaseId() - INFO");
        List<Altscenariocase> result = new ArrayList<Altscenariocase>();

        Altscenariocase happyPath = new Altscenariocase();
        happyPath.setAltscenariocaseId(new Integer(0));
        happyPath.setAlternateName("Happy Path (Default)");
        result.add(happyPath);

        List<Altscenariocase> byScenarioId = this.findByScenarioIdCaseId(scenarioId, caseId);
        if (byScenarioId != null) {
            for (Altscenariocase altscenariocase : byScenarioId) {
                if (altscenariocase.getScenariocase() != null &&
                        altscenariocase.getScenariocase().getTestcase() != null &&
                        altscenariocase.getScenariocase().getTestcase().getCaseId() != null &&
                        altscenariocase.getScenariocase().getTestcase().getCaseId().intValue() == caseId) {
                    result.add(altscenariocase);
                }
            }
        }

        return result;
    }

    /**
     * Returns the config file as saved in the altscenariocase table for a given altscenariocase record.
     *
     * @param pScenarioId           A parameter used to confirm whether correct config file is being read.
     * @param pCaseId               A parameter used to confirm whether correct config file is being read.
     * @param pAltScenarioCaseId    The primary key value of which the config file will be read off.
     * @return File                 Patient data file - saved as a blob in the altscenariocase table
     * @throws ServiceException
     */
    @Override
    public File getPatientData(int pScenarioId, int pCaseId, int pAltScenarioCaseId) throws ServiceException {
        log.info("AltscenariocaseServiceImpl.getPatientData() - INFO");

        File patientDataFile = null;
        String config = getPatientDataFromConfig(pScenarioId, pCaseId, pAltScenarioCaseId);
        try {
            patientDataFile = File.createTempFile("temp", ".xml");
            FileOutputStream outStream = new FileOutputStream(patientDataFile);
            log.info("config: *********************\n" + config);
            if (null != config) {
                outStream.write(config.getBytes());
                outStream.close();
            }
            log.info("AltscenariocaseServiceImpl.getPatientData(): Patient file length --- >>" + patientDataFile.length());
            patientDataFile.deleteOnExit();            
        }
        catch (IOException ex) {
            Logger.getLogger(AltscenariocaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patientDataFile;
    }
    /**
     * 
     */
    
	@Override
    public String getPatientDataFromConfig(int pScenarioId, int pCaseId,
			int pAltScenarioCaseId) throws ServiceException {

		log.info("AltscenariocaseServiceImpl.getPatientData() - INFO");

		Altscenariocase objAltScenarioCase = null;
		String configData = null;

		/*
		 * Check if parameters passed by the view layer are ok.
		 */
		if (pScenarioId <= 0 || pCaseId <= 0 || pAltScenarioCaseId <= 0) {
			throw new ServiceException(
					"AltscenariocaseServiceImpl.getPatientData(): At least one or all parameters are invalid. scenarioId="
							+ pScenarioId
							+ " caseId="
							+ pCaseId
							+ " altScenarioCaseId=" + pAltScenarioCaseId,
					ServiceException.ERROR_MESSAGE_INVALID_ARGUMENT);
		}

		/*
		 * Get the unique row from the altscenariocase table.
		 */
		objAltScenarioCase = this.read(pAltScenarioCaseId);

		/*
		 * check whether a valid record exists in the altscenariocase table.
		 */
		if (null == objAltScenarioCase) {
			throw new ServiceException(
					"AltscenariocaseServiceImpl.getPatientData(): Cannot find record with pAltScenarioCaseId="
							+ pAltScenarioCaseId);
		}

		/*
		 * Now confirm if scenarioId and caseId passed by the view layer are
		 * valid values or not, corresponding to the altScenarioCaseId
		 */
		int iTempScenarioId = objAltScenarioCase.getScenariocase()
				.getScenario().getScenarioId(); // Won't be NPE here, probably
												// ever
		int iTempCaseId = objAltScenarioCase.getScenariocase().getTestcase()
				.getCaseId(); // Won't be NPE here, probably ever
		if (!((iTempScenarioId == pScenarioId) && (iTempCaseId == pCaseId))) {
			throw new ServiceException(
					"AltscenariocaseServiceImpl.getPatientData(): The scenarioid and caseid passed do NOT match "
							+ " with the those of the record where pAltScenarioCaseId="
							+ pAltScenarioCaseId
							+ " Note: scenarioId="
							+ pScenarioId + " caseId=" + pCaseId,
					ServiceException.ERROR_MESSAGE_INVALID_ARGUMENT);
		}

		/*
		 * Now, proceed to create the patient data file
		 */
		log.info("AltscenariocaseServiceImpl.getPatientData(): scenarioId="
				+ pScenarioId + " caseId=" + pCaseId + " altScenarioCaseId="
				+ pAltScenarioCaseId);
		byte[] arrConfigPatientDataDetails = null;
		arrConfigPatientDataDetails = objAltScenarioCase.getConfig();
		log.info("AltscenariocaseServiceImpl.getPatientData(): *********************\n"
				+ arrConfigPatientDataDetails);
		if (null != arrConfigPatientDataDetails) {
			configData = new String(arrConfigPatientDataDetails);
		}
		return configData;
	}

}
