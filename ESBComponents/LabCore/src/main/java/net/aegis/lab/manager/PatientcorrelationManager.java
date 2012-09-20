/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.lab.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.aegis.lab.dao.pojo.Patientcorrelation;
import net.aegis.lab.exception.ServiceException;

import net.aegis.lab.patientcorrelation.service.PatientcorrelationService;
import net.aegis.lab.util.ContextUtil;

/**
 * Venkat.Keesara
 * Apr 5, 2012
 **/

public class PatientcorrelationManager {

	private static final Log log = LogFactory.getLog(PatientcorrelationManager.class);

    private static PatientcorrelationManager instance;

    private PatientcorrelationService patientcorrelationService = (PatientcorrelationService)ContextUtil.getLabContext().getBean("patientcorrelationService");

    private PatientcorrelationManager() {
    	
    }

    /**
     * @return PatientcorrelationManager
     */
    public static PatientcorrelationManager getInstance() {
        if (instance == null) {
            instance = new PatientcorrelationManager();
        }
        return instance;
    }
    
	public Integer create(Patientcorrelation newInstance) throws ServiceException
	{
		log.debug("PatientcorrelationManager|create()" );
		return patientcorrelationService.create(newInstance);
	}

    public Patientcorrelation read(Integer patientcorrelationId) throws ServiceException{
    	log.debug("PatientcorrelationManager|read()" );
    	return patientcorrelationService.read(patientcorrelationId);
    }

    public void update(Patientcorrelation transientObject) throws ServiceException{
    	log.debug("PatientcorrelationManager|update()" );
    	patientcorrelationService.update(transientObject);
    }

    public void delete(Patientcorrelation persistentObject) throws ServiceException{
    	log.debug("PatientcorrelationManager|delete()" );
    	patientcorrelationService.delete(persistentObject);
    }

    public List<Patientcorrelation> search(Patientcorrelation criteria) throws ServiceException{
    	log.debug("PatientcorrelationManager|search()" );
    	return patientcorrelationService.search(criteria);
    }
}
