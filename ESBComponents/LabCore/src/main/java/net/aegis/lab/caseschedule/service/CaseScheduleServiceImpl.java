/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.caseschedule.service;

import net.aegis.lab.dao.CasescheduleDAO;
import net.aegis.lab.dao.pojo.Caseschedule;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("caseScheduleService")
public class CaseScheduleServiceImpl implements CaseScheduleService{


    @Autowired
    private CasescheduleDAO caseScheduleDAO;


    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(CaseScheduleServiceImpl.class);


     /*
     * Standard CRUD Methods
     */

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Caseschedule newInstance) throws ServiceException {
        log.info("CaseScheduleServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = caseScheduleDAO.create(newInstance);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
        return id;
    }


    @Override
    public Caseschedule read(Integer id) throws ServiceException {
        log.info("CaseScheduleServiceImpl.read() - INFO");
        Caseschedule caseschedule = null;
        try {
            caseschedule = caseScheduleDAO.read(id);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
        return caseschedule;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Caseschedule transientObject) throws ServiceException {
        log.info("CaseScheduleServiceImpl.update() - INFO");
        try {
            caseScheduleDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Caseschedule persistentObject) throws ServiceException {
        log.info("CaseScheduleServiceImpl.delete(persistentObject) - INFO");
        try {
            caseScheduleDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("CaseScheduleServiceImpl.delete(id) - INFO");
        try {
            caseScheduleDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }


    /*
     * Finder methods
     */





    


}
