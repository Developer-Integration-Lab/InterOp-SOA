package net.aegis.lab.patient.service;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.PatientDAO;
import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("patientService")
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDAO patientDAO;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(PatientServiceImpl.class);


    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public String create(Patient newInstance) throws ServiceException {
        log.info("PatientServiceImpl.create() - INFO");
        String patientId = null;
        try {
            patientId = patientDAO.create(newInstance);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return patientId;
    }

    @Override
    public Patient read(String patientId) throws ServiceException {
        log.info("PatientServiceImpl.read() - INFO");
        Patient patient = null;
        try {
            patient = patientDAO.read(patientId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return patient;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Patient transientObject) throws ServiceException {
        log.info("PatientServiceImpl.update() - INFO");
        try {
            patientDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Patient persistentObject) throws ServiceException {
        log.info("PatientServiceImpl.delete() - INFO");
        try {
            patientDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public List<Patient> search(Patient criteria) throws ServiceException {
        log.info("PatientServiceImpl.search() - INFO");

        List<Patient> results = null;

        try {
            if (criteria != null) {
                this.processCriteria(criteria);
                if (results == null) {
                    log.info("PatientServiceImpl.search() - Search by Criteria");
                    results = patientDAO.searchByCriteria(criteria, this.processCriteria(criteria));
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return results;
    }

    private List<Criterion> processCriteria(Patient criteria) {
        List<Criterion> criterionList = new ArrayList<Criterion>();

        if (criteria != null) {
            // Check each property for empty value and set to null
            if (criteria.getPatientId() != null && !criteria.getPatientId().equals("")) {
                criterionList.add(Restrictions.like("patientId", criteria.getPatientId(), MatchMode.START));
            }

            if (criteria.getFirstName() != null && !criteria.getFirstName().equals("")) {
                criterionList.add(Restrictions.like("firstName", criteria.getFirstName(), MatchMode.START));
            }

            if (criteria.getLastName() != null && !criteria.getLastName().equals("")) {
                criterionList.add(Restrictions.like("lastName", criteria.getLastName(), MatchMode.START));
            }

            if (criteria.getAddressLine1() != null && !criteria.getAddressLine1().equals("")) {
                criterionList.add(Restrictions.like("addressLine1", criteria.getAddressLine1(), MatchMode.START));
            }

            if (criteria.getAddressLine2() != null && !criteria.getAddressLine2().equals("")) {
                criterionList.add(Restrictions.like("addressLine2", criteria.getAddressLine2(), MatchMode.START));
            }

            if (criteria.getCity() != null && !criteria.getCity().equals("")) {
                criterionList.add(Restrictions.like("city", criteria.getCity(), MatchMode.START));
            }

            if (criteria.getState() != null && !criteria.getState().equals("")) {
                criterionList.add(Restrictions.like("state", criteria.getState(), MatchMode.START));
            }

            if (criteria.getZipCode() != null && !criteria.getZipCode().equals("")) {
                criterionList.add(Restrictions.like("zipCode", criteria.getZipCode(), MatchMode.START));
            }

            if (criteria.getHomePhone() != null && !criteria.getHomePhone().equals("")) {
                criterionList.add(Restrictions.like("homePhone", criteria.getHomePhone(), MatchMode.START));
            }

            if (criteria.getWorkPhone() != null && !criteria.getWorkPhone().equals("")) {
                criterionList.add(Restrictions.like("workPhone", criteria.getWorkPhone(), MatchMode.START));
            }

            if (criteria.getDateOfBirthStr() != null && !criteria.getDateOfBirthStr().equals("")) {
                criterionList.add(Restrictions.eq("dateOfBirth", criteria.getDateOfBirth()));
            }

            if (criteria.getSsn() != null && !criteria.getSsn().equals("")) {
                criterionList.add(Restrictions.eq("ssn", criteria.getSsn()));
            }

            if (criteria.getGender() != null && !criteria.getGender().equals("")) {
                criterionList.add(Restrictions.eq("gender", criteria.getGender()));
            }
        }

        return criterionList;
    }
}
