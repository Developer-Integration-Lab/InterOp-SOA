package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.patient.service.PatientService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PatientManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);

    private static PatientManager instance;

    private PatientService patientService = (PatientService)ContextUtil.getLabContext().getBean("patientService");

    private PatientManager() {
    }

    /**
     * @return PatientManager
     */
    public static PatientManager getInstance() {
        if (instance == null) {
            instance = new PatientManager();
        }
        return instance;
    }

    /**
     * Search for patients
     * @param criteria
     * @return List<Patient> found for criteria
     * @throws ServiceException
     */
    public List<Patient> search(Patient criteria) throws ServiceException {
        log.info("PatientManager.search() - INFO");

        List<Patient> results = null;

        try {
            results = patientService.search(criteria);
        }
        catch (ServiceException se) {
            log.error("ERROR: failure finding patients.", se);
            se.setErrorCode("errors.patient.search.failed");
            se.setLogged();
            throw se;
        }
        catch (Exception e) {
            log.error("ERROR: failure finding patients.", e);
            ServiceException se = new ServiceException("Failure finding patients", "errors.patient.search.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }

}
