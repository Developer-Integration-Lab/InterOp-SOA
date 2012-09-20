package net.aegis.lab.user.service;

import java.util.List;

import net.aegis.lab.dao.UserDAO;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(UserServiceImpl.class);


    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(User newInstance) throws ServiceException {
        log.info("UserServiceImpl.create() - INFO");
        Integer id = null;
        try {
            if (userDAO != null) {
                id = userDAO.create(newInstance);                
            } else {
               log.info("USERDAO is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
        return id;
    }

    @Transactional
    @Override
    public User read(Integer id) throws ServiceException {
        log.info("UserServiceImpl.read() - INFO");
        User user = null;
        try {
            user = userDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(User transientObject) throws ServiceException {
        log.info("UserServiceImpl.update() - INFO");
        try {
            userDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(User persistentObject) throws ServiceException {
        log.info("UserServiceImpl.delete(persistentObject) - INFO");
        try {
            userDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Integer id) throws ServiceException {
        log.info("UserServiceImpl.delete(id) - INFO");
        try {
            userDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public User findByUsername(String username) throws ServiceException {
        User foundUser = null;

        try {
            List<User> users = userDAO.findByUsername(username);
            if (users != null) {
                log.info("UserServiceImpl.findByUsername() - users.size() = " + users.size());
            } else {
                log.info("UserServiceImpl.findByUsername() - users is null");
            }
            if (users != null && users.size() == 1) {
                foundUser = users.get(0);
            }
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }

        return foundUser;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws ServiceException {
        User foundUser = null;

        try {
            List<User> users = userDAO.findByUsernameAndPassword(username, password);
            if (users != null) {
                log.info("UserServiceImpl.findByUsernameAndPassword() - users.size() = " + users.size());
            } else {
                log.info("UserServiceImpl.findByUsernameAndPassword() - users is null");
            }
            if (users != null && users.size() == 1) {
                foundUser = users.get(0);
            }
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }

        return foundUser;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        //return userHibernateDao.findAll();
        return null;
    }

    @Override
    public List<User> findByRolename(String rolename) throws ServiceException {
        if (rolename==null) {
            throw new ServiceException("rolename cannot be null");
        }
        try {
            List<User> users = userDAO.findByRolename(rolename);
            return users;
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
    }
}
