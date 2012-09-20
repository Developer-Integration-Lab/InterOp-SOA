package net.aegis.lab.patientcorrelation.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Patientcorrelation;
import net.aegis.lab.exception.ServiceException;

/**
 * Venkat.Keesara
 * Apr 5, 2012
 **/
public interface PatientcorrelationService {

    
	/*
     * Standard CRUD Methods
     */
    public Integer create(Patientcorrelation newInstance) throws ServiceException;

    public Patientcorrelation read(Integer patientcorrelationId) throws ServiceException;

    public void update(Patientcorrelation transientObject) throws ServiceException;

    public void delete(Patientcorrelation persistentObject) throws ServiceException;

    public List<Patientcorrelation> search(Patientcorrelation criteria) throws ServiceException;
}
