package net.aegis.lab.serviceset.service;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.ServicesetDAO;
import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.LabConstants.LabType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("serviceSetService")
public class ServiceSetServiceImpl implements ServiceSetService {

    @Autowired
    private ServicesetDAO serviceSetDAO;
    public static final Log log = LogFactory.getLog(ServiceSetServiceImpl.class);

    /**
     *
     *  this method to create a new record of serviceset in Serviceset table
     * @param serviceSet
     * @return Integer
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Serviceset serviceSet) throws ServiceException {
        log.info("saving Serviceset..");
        try {
            return serviceSetDAO.create(serviceSet);
        } catch (Exception e) {
            log.error("create failed ",e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    /**
     * This is the crud method delete to delete the serviceset from table Serviceset
     * @return void
     * @param persistentObject 
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Serviceset persistentObject) throws ServiceException {
        log.info("Deleting Serviceset..");
        try {
            serviceSetDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     * This is a crud method delete to delete the serviceset from table Serviceset given the Id
     * @return void
     * @param id
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("DeletingBYId Serviceset..");
        try {
            serviceSetDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     * Thsi is the crud method to read a serviceset record from Serviceset table given the id
     * @param setId
     * @return Serviceset
     * @throws ServiceException
     */
    @Override
    public Serviceset read(Integer setId) throws ServiceException {
        log.info("ServiceSetServiceImpl.read() - INFO");
        Serviceset serviceSet = null;
        try {
            serviceSet = serviceSetDAO.read(setId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return serviceSet;
    }

    /**
     *  This method is to update the Serviceset record in Serviceset table
     * @param serviceSet
     * @return void
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Serviceset serviceSet) throws ServiceException {
        log.info("updating ServiceSet..");
        try {
            serviceSetDAO.update(serviceSet);
        } catch (Exception e) {
            log.error("update failed ",e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    /**
     *  This is to get List of servicesets with given criteria from Serviceset table
     * @return List<Serviceset>
     * @throws ServiceException
     */
    @Override
    public List<Serviceset> getServicesets() throws ServiceException {
        log.info("getServicesets() ...");
        Serviceset serviceset = new Serviceset();
        List<Criterion> criteria = new ArrayList<Criterion>();
        List<Serviceset> results = new ArrayList<Serviceset>();
        try {
            results = serviceSetDAO.searchByCriteria(serviceset, criteria);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return results;
    }

    @Override
    public List<Serviceset> findAll(LabType labType) throws ServiceException {
        try {
            if (labType==null) {
                return getServicesets();
            } else {
                return serviceSetDAO.findAll(labType);
            }
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
    }

    @Override
    public List<Serviceset> findBySetName(String setName) throws ServiceException {
        try {
           return serviceSetDAO.findBySetName(setName);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
    }
}
