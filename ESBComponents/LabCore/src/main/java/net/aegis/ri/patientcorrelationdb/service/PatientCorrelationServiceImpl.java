package net.aegis.ri.patientcorrelationdb.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import net.aegis.lab.exception.ServiceException;
import net.aegis.ri.patientcorrelationdb.dao.CorrelatedidentifiersDAO;
import net.aegis.ri.patientcorrelationdb.dao.pojo.Correlatedidentifiers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ram Ghattu
 */
@Service("correlationService")
public class PatientCorrelationServiceImpl implements PatientCorrelationService{

    @Autowired
    CorrelatedidentifiersDAO correlationDao;

    @Override
    public List<Correlatedidentifiers> findAllCorrelation() {
        return correlationDao.findAllCorrelation();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void cleanCorrelationIdentifiers() {
        for(Correlatedidentifiers correlationIdentifier:findAllCorrelation()){
            correlationDao.delete(correlationIdentifier);
        }
    }

    @Override
    public boolean addCorrelationPatientId(String patientAssigningAuthorityId, String patientId, String correlatedPatientAssignAuthId) {

        try {
            List<Correlatedidentifiers> correlatedIds = correlationDao.findByCriteria(patientAssigningAuthorityId, patientId, correlatedPatientAssignAuthId, patientId);
            if ((correlatedIds!=null || !correlatedIds.isEmpty()) && correlatedIds.size()>0) {
                return true;
            } else {
                Correlatedidentifiers ci = new Correlatedidentifiers();
                ci.setCorrelatedPatientAssignAuthId(correlatedPatientAssignAuthId);
                ci.setCorrelatedPatientId(patientId);
                ci.setPatientAssigningAuthorityId(patientAssigningAuthorityId);
                ci.setPatientId(patientId);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, 1);
                ci.setCorrelationExpirationDate(new Timestamp(cal.getTime().getTime()));
                correlationDao.create(ci);
            }            
        } catch (Exception ex) {
            return false;
        }
        return true;
    }



}
