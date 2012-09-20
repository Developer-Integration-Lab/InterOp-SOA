package net.aegis.lab.nhinrep.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.aegis.lab.dao.NhinrepDAO;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Role;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dao.pojo.Userrole;
import net.aegis.lab.dao.pojo.Userrole.UserrolePK;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.user.service.UserService;
import net.aegis.lab.userrole.service.UserroleService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Abhay.Bakshi
 * @modified  Sree Hari Devineni
 * @dt        May-10-2010
 *  // Added  registerNhinrep()
 */
@Service("nhinrepService")
public class NhinrepServiceImpl implements NhinrepService {

    @Autowired
    private NhinrepDAO nhinrepDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private UserroleService userRoleService;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(NhinrepServiceImpl.class);


    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Nhinrep newInstance) throws ServiceException {
        log.info("NhinrepServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = nhinrepDAO.create(newInstance);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return id;
    }

    @Override
    public Nhinrep read(Integer id) throws ServiceException {
        log.info("NhinrepServiceImpl.read() - INFO");
        Nhinrep nhinrep = null;
        try {
            nhinrep = nhinrepDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return nhinrep;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Nhinrep transientObject) throws ServiceException {
        log.info("NhinrepServiceImpl.update() - INFO");
        try {
            nhinrepDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Nhinrep persistentObject) throws ServiceException {
        log.info("NhinrepServiceImpl.delete(persistentObject) - INFO");
        try {
            nhinrepDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("NhinrepServiceImpl.delete(id) - INFO");
        try {
            nhinrepDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /*
     * Finder methods
     */
    @Override
    public Nhinrep findByNhinRepId(Integer nhinrepId) throws ServiceException {
        log.info("NhinrepServiceImpl.findByNhinRepId() - INFO");
        return nhinrepDAO.read(nhinrepId);
    }

    /**
     * Requirement: An NHIN Rep needs to choose a participant from the list of participants and
     * associate that participant with himself (a.k.a. herself).
     *
     * @param pstrStatus    For ex - status='ACTIVE'
     * @return List<Nhinrep> Nhin reps whose status match with the one queried for.
     * @throws ServiceException
     */
    @Override
    public List<Nhinrep> findByStatus(String pstrStatus) throws ServiceException {
        log.info("NhinrepServiceImpl.findByStatus(pstrStatus) - INFO");
        List<Nhinrep> objListNhinreps = null;

        objListNhinreps = nhinrepDAO.findByStatus(pstrStatus);
        if (null != objListNhinreps) {
            log.info("NhinrepServiceImpl.findByStatus(pstrStatus): Number of Nhinreps with status " + pstrStatus + " found=" + objListNhinreps.size());
        }
        return objListNhinreps;
    }

    @Override
    public List<Nhinrep> getAllNhinReps() throws ServiceException {
        List<Nhinrep> nhinreps ;
        try {
            Nhinrep criteria = new Nhinrep();
            nhinreps = nhinrepDAO.searchByCriteria(criteria);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return nhinreps;
    }

    /**
     * Purpose - An Nhin Rep, who is logged in, can access his/her contact
     *           information and make changes.
     * Note:     The view must pass all the parameters.
     *
     * @param pnhinRepId        Id of the Nhin rep to update
     * @param pName             Nhin rep's updated name
     * @param pContactName      Nhin rep's updated contact name
     * @param pContactPhone     Nhin rep's updated contact phone
     * @param pContactEmail     Nhin rep's updated contact e-mail
     * @return                  Nhin rep in database that got updated
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Nhinrep updateContactInformation(Integer pnhinRepId, String pName, String pContactName, String pContactPhone, String pContactEmail)
            throws ServiceException {
        log.info("NhinrepServiceImpl.updateContactInformation() - INFO");

        Nhinrep testNhinrep = null;

        if ((null == pnhinRepId) || (null == pName) || (null == pContactName) ||
                (null == pContactPhone) || (null == pContactEmail)) {
            log.error("NhinrepServiceImpl.updateContactInformation() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("NhinrepServiceImpl.updateContactInformation() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        // find the nhin rep in the database first.
        try {
            testNhinrep = this.read(pnhinRepId);
        } catch (ServiceException se) {
            log.error("ERROR: failure finding nhin rep.", se);
            se.setErrorCode("errors.find.nhinrep.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding nhin rep.", e);
            ServiceException se = new ServiceException("Failure finding nhin rep", "errors.find.nhinrep.failed", e);
            se.setLogged();
            throw se;
        }

        // The nhin rep must be available in the database, otherwise throw error.
        if(null == testNhinrep) {
            log.error("NhinrepServiceImpl.updateContactInformation() - Nhin rep is not available in the database. - ERROR");
            ServiceException se = new ServiceException("NhinrepServiceImpl.updateContactInformation() - Nhin rep is not available in the database.");
            se.setLogged();
            throw se;
        }

        // use the information passed in the parameters and other useful info.
        String strTempChangedUserName = testNhinrep.getUser().getUsername();
        testNhinrep.setName(pName);                         // passed by view
        testNhinrep.setContactName(pContactName);           // passed by view
        testNhinrep.setContactPhone(pContactPhone);         // passed by view
        testNhinrep.setContactEmail(pContactEmail);         // passed by view
        testNhinrep.setChangeduser(strTempChangedUserName);
        testNhinrep.setChangedtime(new Timestamp(new Date().getTime()));

        // now save the information update
        this.update(testNhinrep);

        return testNhinrep;
    }


        /**
     * Requirement: An Admin  needs to be able to register a new NHIN REP.
     *
     * @param pUser - a corresponding user for the newly created NHIN REP.
     * @param pNhinrep - Nhin Rep object that contains all the relevant information to save NHIN REP in database.
     * @param pReprole - Nhin Rep Role id as defined in the roles table
     * @return Nhinrep - the newly created Nhinrep
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Nhinrep registerNhinrep(User pUser, Nhinrep pNhinrep, int pReprole)throws ServiceException {

        log.info("NhinrepServiceImpl.registerNhinrep(user, nhinrep, reprole) - INFO");

        User user = null;
        Integer userId = -999;
        Role role = null;
        Userrole userrole = null;
        UserrolePK userrolePK = new Userrole.UserrolePK();
        Nhinrep nhinrep = null;
        Integer nhinRepID = -999;
        int reprole=pReprole;

        // check if parameters passed by the view layer are ok.
        if (null == pUser || null == pNhinrep ) {
            throw new ServiceException("NhinrepServiceImpl.registerNhinrep(user, nhinrep, reprole): parameter is null",
                    ServiceException.ERROR_MESSAGE_INVALID_ARGUMENT);
        }

        // Lab should not have an existing user with the same name in the database for registration to succeed.
        User tempObjUser = null;
        tempObjUser = userService.findByUsername(pUser.getUsername());
        if (null != tempObjUser) {
            log.error("NhinrepServiceImpl.registerNhinrep(user, nhinrep, reprole): User with user name=" + pUser.getUsername() + " already exists in the database.");
            return null;
        }

        // now, proceed to register a participant
        user = pUser;               // not null
        nhinrep = pNhinrep;     // not null

        try {
            // step 1: create a user first
            userId = userService.create(user);

            // step 2: create a userrole
            role = new Role();
            role.setRoleId(reprole); // 5 - Participant role
            userrole = new Userrole();
            userrolePK.setRoleId(role.getRoleId());
            userrolePK.setUserId(userId);                   // newly created user
            userrole.setUserrolePK(userrolePK);
            userrolePK = userRoleService.create(userrole);

            // step 3: now, register the participant
            nhinRepID = this.create(nhinrep);
        }
        catch (Exception e) {
            log.error("ERROR: failure registering a new nhin rep for Admin.", e);
            ServiceException se = new ServiceException("Failure registering new nhin rep by  Adminby", "errors.register.nhinrep.failed", e);
            se.setLogged();
            throw se;
        }

        log.info("NhinrepServiceImpl.registerNhinrep(user, nhinrep, reprole)): User created for the newly registering nhinrep: user id=" + userId);
        log.info("NhinrepServiceImpl.registerNhinrep(user, nhinrep, reprole): Newly registered nhinrep : nhinRepID=" + nhinRepID);

        return nhinrep;
    }

}
