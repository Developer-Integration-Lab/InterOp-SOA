package net.aegis.lab.applicatioproperties.service;

import java.util.List;

import net.aegis.lab.dao.ApplicationpropertiesDAO;
import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author        : Devineni Sree Hari SAi
 * @Create Date   : May-05-2010
 *
 */
@Service("applicationpropertiesService")
public class ApplicationpropertiesServiceImpl implements ApplicationpropertiesService {

    @Autowired
    private ApplicationpropertiesDAO applicationpropertiesDAO;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ApplicationpropertiesServiceImpl.class);


    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Applicationproperties applicationproperties) throws ServiceException {
        log.info("saving / creating applicationproperties...");
        try {
            //return applicationpropertiesDAO.create(applicationproperties);
            return 1;
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Applicationproperties transientObject) throws ServiceException {
        log.info("ApplicationpropertiesServiceImpl.update() - INFO");
        try {
            applicationpropertiesDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /*
     * Finder methods
     */
    @Override
    public List<Applicationproperties> findByKey(String key) {
        List<Applicationproperties> applicationpropertieslist = null;
        log.info("ApplicationpropertiesServiceImpl.findByParticipantId() - INFO");
        applicationpropertieslist = applicationpropertiesDAO.findByKey(key);
        return applicationpropertieslist;
    }

    /**
     * This method returns list of attachments by case execution id
     * @param cseExecId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Applicationproperties> findAll() throws ServiceException {
        List<Applicationproperties> applicationproperties = null;
        log.info("ApplicationpropertiesServiceImpl findAll......");
        // get case result by caseExecutionId

        applicationproperties = applicationpropertiesDAO.findAll();

        return applicationproperties;
    }

    /**
     * Requirement: An Admin need to add new application property with key value as input.
     *
     * @param key - property key.
     * @param value - property value;
     * @return Applicationproperties - the newly created Applicationproperties
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Applicationproperties AppProperty(String key, String value) throws ServiceException {


        Integer appPropertyID = -999;

        log.info("ApplicationpropertiesServiceImpl.AppProperty(key, value) - INFO");



        if (null == key || null == value) {
            throw new ServiceException("ApplicationpropertiesServiceImpl.AppProperty(key , value): parameter is null",
                    ServiceException.ERROR_MESSAGE_INVALID_ARGUMENT);
        }


        Applicationproperties applicationproperties;
        try {
            // step 1: create a user first
            applicationproperties = new Applicationproperties();
            applicationproperties.setPropertykey(key);
            applicationproperties.setPropertyvalue(value);         // step 3: now, register the participant;

            appPropertyID = this.create(applicationproperties);
        } catch (Exception e) {
            log.error("ERROR: failure adding new property for admin.", e);
            ServiceException se = new ServiceException("Failure adding new property for admin ", "errors.admin.addproperty.failed", e);
            se.setLogged();
            throw se;
        }

        log.info("ParticipantServiceImpl.registerParticipant(user, participant): Property created for the newly added key value::: :" + appPropertyID);


        return applicationproperties;
    }

    @Override
    public List<Applicationproperties> searchByCriteria(Applicationproperties criteria, List<Criterion> criterion) throws ServiceException {
                log.info("Applicationproperties.searchByCriteria() - INFO");
        try {
            return applicationpropertiesDAO.searchByCriteria(criteria, criterion);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public boolean remove(Applicationproperties ap) throws ServiceException {
        log.info("Applicationproperties.remove() - INFO");
        try {
            applicationpropertiesDAO.delete(ap);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return true;
    }



}
