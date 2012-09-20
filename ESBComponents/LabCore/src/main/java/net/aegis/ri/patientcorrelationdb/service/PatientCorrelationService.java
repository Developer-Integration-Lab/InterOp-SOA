package net.aegis.ri.patientcorrelationdb.service;

import java.util.List;

import net.aegis.ri.patientcorrelationdb.dao.pojo.Correlatedidentifiers;

/**
 *
 * @author Ram Ghattu
 */
public interface PatientCorrelationService {
    public List<Correlatedidentifiers> findAllCorrelation();
    public void cleanCorrelationIdentifiers();
    public boolean addCorrelationPatientId(String patientAssigningAuthorityId, String patientId, String correlatedPatientAssignAuthId);
}
