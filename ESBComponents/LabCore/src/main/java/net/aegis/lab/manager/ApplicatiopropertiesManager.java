package net.aegis.lab.manager;
/**
 * @author        : Devineni Sree Hari SAi
 * @Create Date   : May-05-2010
 *
 */
import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.applicatioproperties.service.ApplicationpropertiesService;
import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class ApplicatiopropertiesManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);
    private static ApplicatiopropertiesManager instance;
    private ApplicationpropertiesService applicationpropertiesService = (ApplicationpropertiesService) ContextUtil.getLabContext().getBean("applicationpropertiesService");

    private ApplicatiopropertiesManager() {
    }

    /**
     * @return ApplicatiopropertiesManager
     */
    public static ApplicatiopropertiesManager getInstance() {
        if (instance == null) {
            instance = new ApplicatiopropertiesManager();
        }
        return instance;
    }



    /**
     * Requirement: 
     *
     * @param 
     * @return Applicationproperties list with all key value pairs.
     * @throws ServiceException
     */
   // public Applicationproperties findAll() throws ServiceException {
    public List<Applicationproperties> findAll() throws ServiceException {
        log.info("ApplicatiopropertiesManager.findAll() - INFO");
        List<Applicationproperties> objListApplicationproperties = null;

        try {
            objListApplicationproperties = applicationpropertiesService.findAll();
        } catch (ServiceException se) {
            log.error("ERROR: failure finding ALL applicationproperties :", se);
            se.setErrorCode("errors.finding applicationproperties.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding ALL applicationproperties: ", e);
            ServiceException se = new ServiceException("Failure finding ALL applicationproperties ", "errors.find.applicationproperties.failed", e);
            se.setLogged();
            throw se;
        }

        return objListApplicationproperties;
    }

     /**
     *
     */
    public List<Applicationproperties> findByKey(String key) throws ServiceException {
       log.info("ApplicatiopropertiesManager.findByKey() - INFO");
      List<Applicationproperties> objListApplicationproperties = null;


        try {
            objListApplicationproperties = applicationpropertiesService.findByKey(key);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding applicationproperties for given key.", se);
            se.setErrorCode("errors.find.participants.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: ffailure finding applicationproperties for given key.", e);
            ServiceException se = new ServiceException("Failure failure finding applicationproperties for given key", "errors.find.applicationproperties.failed", e);
            se.setLogged();
            throw se;
        }

        return objListApplicationproperties;
    }



    public void update(Applicationproperties applicationproperties, String value) throws ServiceException {
        log.info("ApplicatiopropertiesManager.update-->>");
        try {
             log.info("ApplicatiopropertiesManager.update-->>Updating..");
            applicationpropertiesService.update(applicationproperties);
            log.info("ApplicatiopropertiesManager.update-->>Updated..");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

     public Applicationproperties AddProperties(String key, String value) throws ServiceException {

        log.info("ApplicatiopropertiesManager.AddProperties() - INFO");

      Applicationproperties app=null;
        // now, invoke the service layer code to register new participant and create a user
        app = applicationpropertiesService.AppProperty(key, value);

        return app;
    }


     public String getPropertyvalueByKey(String propertykey) throws ServiceException {

     String propertyvalue="";

        List<Applicationproperties> objListApplicationproperties = null;
        if(null!=propertykey){
                  try {
                        objListApplicationproperties = applicationpropertiesService.findByKey(propertykey);
                         Applicationproperties app=null;
                        for(int i=0; i<objListApplicationproperties.size(); i++){
                        app=objListApplicationproperties.get(i);
                            if(app.getPropertykey().equals(propertykey)){
                                 propertyvalue=app.getPropertyvalue();

                            }

                        }
                    } catch (ServiceException se) {
                        log.error("ERROR: failure finding applicationproperties for given key.", se);
                        se.setErrorCode("errors.find.participants.failed");
                        se.setLogged();
                        throw se;
                    } catch (Exception e) {
                        log.error("ERROR: ffailure finding applicationproperties for given key.", e);
                        ServiceException se = new ServiceException("Failure failure finding applicationproperties for given key", "errors.find.applicationproperties.failed", e);
                        se.setLogged();
                        throw se;
                    }
        }

     return propertyvalue;
     }

     public List<Applicationproperties> getEditableKeys() throws ServiceException {

        Applicationproperties criteria = new Applicationproperties();
        List<Criterion> criterionList = new ArrayList<Criterion>();
        criterionList.add(Restrictions.not(Restrictions.like("propertykey", LabConstants.SPLITTER_CARET, MatchMode.ANYWHERE)));

        List<Applicationproperties> results = applicationpropertiesService.searchByCriteria(criteria, criterionList);

        return results;
    }


    public List<Applicationproperties> getKeysLike(String key) throws ServiceException {

        Applicationproperties criteria = new Applicationproperties();
        List<Criterion> criterionList = new ArrayList<Criterion>();
        criterionList.add(Restrictions.like("propertykey", key, MatchMode.START));

        List<Applicationproperties> results = applicationpropertiesService.searchByCriteria(criteria, criterionList);

        return results;
    }


    public boolean removeKeys(List<Applicationproperties> keyList) throws ServiceException {
        if (keyList!=null && !keyList.isEmpty()) {
            try {
                for (Applicationproperties ap : keyList) {
                    log.info("removing key:"+ap.getPropertykey()+"value:"+ap.getPropertyvalue());
                    applicationpropertiesService.remove(ap);
                }

            } catch (Exception ex) {
                log.info(ex.toString());
                return false;
            }
        }
        return true;
    }
    
    public void addEvent(String apKey, String eventType, java.lang.String desc) {
        log.info("addEvent: "+eventType);
        List<Applicationproperties> apList = null;
        try {
            apList = findByKey(apKey);
            Applicationproperties ap = null;
            if (apList != null && !apList.isEmpty()) {
                ap = apList.get(0);
            } else {
                ap = new Applicationproperties();
                ap.setPropertykey(apKey);
            }
            ap.setPropertyvalue(eventType);
            if (desc!=null || !"".equals(desc)) {
                ap.setDescription(desc);
            } else  {
                ap.setDescription(new java.util.Date().toString());
            }
           update(ap, null);
        } catch (Exception ex) {
            log.info(ex.toString());
        }
    }

}
