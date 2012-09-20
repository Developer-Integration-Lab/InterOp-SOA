/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.lab.patientcorrelation.service;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.PatientcorrelationDAO;
import net.aegis.lab.dao.pojo.Patientcorrelation;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Venkat.Keesara
 * Apr 5, 2012
 **/

@Service("patientcorrelationService")
public class PatientcorrelationServiceImpl implements PatientcorrelationService {

    @Autowired
    private PatientcorrelationDAO patientcorrelationDAO;
    
    private static final Log log = LogFactory.getLog(PatientcorrelationServiceImpl.class);


    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Patientcorrelation newInstance) throws ServiceException {
        log.info("PatientcorrelationServiceImpl.create() - INFO");
        Integer patientcorrelationId = null;
        try {
            patientcorrelationId = patientcorrelationDAO.create(newInstance);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return patientcorrelationId;
    }

    @Override
    public Patientcorrelation read(Integer patientcorrelationId) throws ServiceException {
        log.info("PatientcorrelationServiceImpl.read() - INFO");
        Patientcorrelation patientcorrelation = null;
        try {
            patientcorrelation = patientcorrelationDAO.read(patientcorrelationId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return patientcorrelation;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Patientcorrelation transientObject) throws ServiceException {
        log.info("PatientcorrelationServiceImpl.update() - INFO");
        try {
            patientcorrelationDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Patientcorrelation persistentObject) throws ServiceException {
        log.info("PatientcorrelationServiceImpl.delete() - INFO");
        try {
            patientcorrelationDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public List<Patientcorrelation> search(Patientcorrelation criteria) throws ServiceException {
        log.info("PatientcorrelationServiceImpl.search() - INFO");
        List<Patientcorrelation> results = null;
        try {
            if (criteria != null) {
             results = patientcorrelationDAO.searchByCriteria(criteria, this.processCriteria(criteria));
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return results;
    }
        
	private List<Criterion> processCriteria(Patientcorrelation criteria) {
		
		List<Criterion> criterionList = new ArrayList<Criterion>();

		
	
		if (criteria.getPatientId() != null) {
			criterionList.add(Restrictions.eq("patientId", criteria.getPatientId()));
		}
	
		if (criteria.getPatientAssigningAuthorityId() != null) {
			criterionList.add(Restrictions.eq("patientAssigningAuthorityId", criteria.getPatientAssigningAuthorityId()));
		}
	
		if (criteria.getPatientHomeCommunityId() != null) {
			criterionList.add(Restrictions.eq("patientHomeCommunityId", criteria.getPatientHomeCommunityId()));
		}
	
		if (criteria.getCorrelatedPatientId() != null) {
			criterionList.add(Restrictions.eq("correlatedPatientId", criteria.getCorrelatedPatientId()));
		}
	
		if (criteria.getCorrelatedPatientAssignAuthId() != null) {
			criterionList.add(Restrictions.eq("correlatedPatientAssignAuthId", criteria.getCorrelatedPatientAssignAuthId()));
		}
	
		if (criteria.getCorrelatedPatientHomeCommunityId() != null) {
			criterionList.add(Restrictions.eq("correlatedPatientHomeCommunityId", criteria.getCorrelatedPatientHomeCommunityId()));
		}
	
		
	
		return criterionList;
	}
}
