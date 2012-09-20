package net.aegis.lab.patient.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.exception.ServiceException;

public interface PatientService {

    /*
     * Standard CRUD Methods
     */
    public String create(Patient newInstance) throws ServiceException;

    public Patient read(String patientId) throws ServiceException;

    public void update(Patient transientObject) throws ServiceException;

    public void delete(Patient persistentObject) throws ServiceException;

    public List<Patient> search(Patient criteria) throws ServiceException;
}
