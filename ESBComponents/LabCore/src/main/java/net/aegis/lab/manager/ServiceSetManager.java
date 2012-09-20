package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.serviceset.service.ServiceSetService;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants.LabType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author richard.ettema
 */
public class ServiceSetManager {

    private static final Log log = LogFactory.getLog(ServiceSetManager.class);
    private static ServiceSetManager instance;
    private ServiceSetService serviceSetService = (ServiceSetService) ContextUtil.getLabContext().getBean("serviceSetService");

    private ServiceSetManager() {
    }

    /**
     * @return ServiceSetManager
     */
    public static ServiceSetManager getInstance() {
        if (instance == null) {
            instance = new ServiceSetManager();
        }
        return instance;
    }

    public List<Serviceset> getServicesets() throws ServiceException {
        return getServicesets(null);
    }

    /**
     * Return all Serviceset records
     * @return List<Serviceset>
     * @throws ServiceException
     */
    public List<Serviceset> getServicesets(LabType labType) throws ServiceException {
        log.info("ServiceSetManager.getServicesets() - INFO");

        List<Serviceset> results = null;

        try { 
            //results = serviceSetService.getServicesets();
            results = serviceSetService.findAll(labType);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding service sets.", se);
            se.setErrorCode("errors.find.servicesets.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding service sets.", e);
            ServiceException se = new ServiceException("Failure finding service sets", "errors.find.servicesets.failed", e);
            se.setLogged();
            throw se;
        }

        return results;
    }

        /**
     * Return ServiceSet by name
     * @return Serviceset
     * @throws ServiceException
     */
    public Serviceset findServiceSetByName(String setName) throws ServiceException {
        log.info("ServiceSetManager.getServicesetByName() setName="+setName);
        try {
            List<Serviceset> serviceSets = serviceSetService.findBySetName(setName);
            if (serviceSets.size()==0) {
                throw new ServiceException("ERROR: could not find service set: '"+setName+"'");
            } else {
                return serviceSets.get(0);
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding service set: '"+setName+"'", se);
            se.setErrorCode("errors.find.servicesets.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding service set: '"+setName+"'", e);
            ServiceException se = new ServiceException("Failure finding service set: '"+setName+"'", "errors.find.serviceset.failed", e);
            se.setLogged();
            throw se;
        }
    }

    public Serviceset findServiceSetById(Integer setId) throws ServiceException {
        log.info("ServiceSetManager.getServicesetByName() setId="+setId);
        try {
            return serviceSetService.read(setId);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding service set: '"+setId+"'", se);
            se.setErrorCode("errors.find.servicesets.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding service set: '"+setId+"'", e);
            ServiceException se = new ServiceException("Failure finding service set: '"+setId+"'", "errors.find.serviceset.failed", e);
            se.setLogged();
            throw se;
        }
    }

    public void updateServiceSet(Serviceset serviceSet) throws ServiceException  {
        String setName = serviceSet!=null?serviceSet.getSetName():null;
        log.info("ServiceSetManager.updateServiceSet() service set="+setName);
        try {
            serviceSetService.update(serviceSet);
        } catch (ServiceException se) {
            log.error("ERROR: failure updating service set: '"+setName+"'", se);
            se.setErrorCode("errors.update.servicesets.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure updating service set: '"+setName+"'", e);
            ServiceException se = new ServiceException("Failure updating service set: '"+setName+"'", "errors.update.serviceset.failed", e);
            se.setLogged();
            throw se;
        }
    }

}
