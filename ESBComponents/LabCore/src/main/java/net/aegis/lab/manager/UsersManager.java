package net.aegis.lab.manager;
/**
 * @author        : Devineni Sree Hari SAi
 * @Create Date   : May-11-2010
 *
 */
import java.util.List;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manageusers.service.ManageUsersService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UsersManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);
    private static UsersManager instance;
    private ManageUsersService manageuserService = (ManageUsersService) ContextUtil.getLabContext().getBean("manageusersService");

    private UsersManager() {
    }

    /**
     * @return UsersManager
     */
    public static UsersManager getInstance() {
        if (instance == null) {
            instance = new UsersManager();
        }
        return instance;
    }



    /**
     * Requirement: 
     *
     * @param 
     * @return User list with all user columns.
     * @throws ServiceException
     */
   // public UsersManager findAll() throws ServiceException {
    public List<User> findAll() throws ServiceException {
        log.info("UsersManager.findAll() - INFO");
        List<User> objListUsers = null;

        try {
            objListUsers = manageuserService.findAll();
        } catch (ServiceException se) {
            log.error("ERROR: failure finding ALL Users :", se);
            se.setErrorCode("errors.finding Users.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding ALL Users: ", e);
            ServiceException se = new ServiceException("Failure finding ALL USers ", "errors.find.users.failed", e);
            se.setLogged();
            throw se;
        }

        return objListUsers;
    }

     /**
     *
     */
    public List<User> findByUsername(String key) throws ServiceException {
       log.info("UsersManager.findByKey() - INFO");
      List<User> objListApplicationproperties = null;


        try {
            objListApplicationproperties = manageuserService.findByUsername(key);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding user for given username .", se);
            se.setErrorCode("errors.find.participants.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: ffailure finding users for given username.", e);
            ServiceException se = new ServiceException("Failure failure finding user for given username", "errors.find.user.failed", e);
            se.setLogged();
            throw se;
        }

        return objListApplicationproperties;
    }



    public void update(User user, String value) throws ServiceException {
        log.info("UsersManager.update-->>");
        try {
             log.info("UsersManager.update-->>Updating..");
            manageuserService.update(user);
            log.info("UsersManager.update-->>Updated..");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }




    
}
