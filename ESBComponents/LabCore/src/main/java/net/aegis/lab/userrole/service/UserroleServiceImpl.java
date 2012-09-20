package net.aegis.lab.userrole.service;

import net.aegis.lab.dao.UserroleDAO;
import net.aegis.lab.dao.pojo.Userrole;
import net.aegis.lab.dao.pojo.Userrole.UserrolePK;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Abhay.Bakshi
 */
@Service("userroleService")
public class UserroleServiceImpl implements UserroleService {

    @Autowired
    private UserroleDAO userroleDAO;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(UserroleServiceImpl.class);

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public UserrolePK create(Userrole newInstance) throws ServiceException {
        log.info("UserroleServiceImpl.create() - INFO");
        UserrolePK userrolePK = null;
        try {
            userrolePK = userroleDAO.create(newInstance);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
        return userrolePK;
    }

    @Override
    public Userrole read(UserrolePK scenarioCasepk) throws ServiceException {
        log.info("UserroleServiceImpl.read() - INFO");
        Userrole scenariocase = null;
        try {
            scenariocase = userroleDAO.read(scenarioCasepk);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return scenariocase;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Userrole transientObject) throws ServiceException {
        log.info("UserroleServiceImpl.update() - INFO");
        try {
            userroleDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Userrole persistentObject) throws ServiceException {
        log.info("UserroleServiceImpl.delete() - INFO");
        try {
            userroleDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteByPK(UserrolePK userrolePK) throws ServiceException {
        log.info("UserroleServiceImpl.deleteByPK() - INFO");
        try {
            userroleDAO.delete(read(userrolePK));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

}
