package net.aegis.lab.manager;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.nhinrep.service.NhinrepService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Requirement - An Nhin Rep, who is logged in, can access his/her contact
 *               information and make changes.  He/she can further save
 *               those changes.  This class supports such functionality.
 *
 * @author Abhay.Bakshi
 * @modified  Sree Hari Devineni
 * @dt        May-10-2010
 *  // Added  registerNhinrep()
 *
 */

public class NhinrepManager {

    private static final Log log = LogFactory.getLog(NhinrepManager.class);
    private static NhinrepManager instance;
    private NhinrepService nhinrepService = (NhinrepService) ContextUtil.getLabContext().getBean("nhinrepService");

    private NhinrepManager() {
        // singleton
    }

    /**
     * @return NhinrepManager
     */
    public static NhinrepManager getInstance() {
        if (instance == null) {
            instance = new NhinrepManager();
        }
        return instance;
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
     * @return                  Nhin rep in the database that got updated
     * @throws ServiceException
     */
    public Nhinrep updateContactInformation(Integer pnhinRepId, String pName, String pContactName, String pContactPhone, String pContactEmail)
            throws ServiceException {

        log.info("NhinrepManager.updateContactInformation() - INFO");

        Nhinrep testNhinrep = null;

        if ((null == pnhinRepId) || (null == pName) || (null == pContactName)
                || (null == pContactPhone) || (null == pContactEmail)) {
            log.error("NhinrepManager.updateContactInformation() - Insufficient number of parameters passed. - ERROR");
            ServiceException se = new ServiceException("NhinrepManager.updateContactInformation() - Insufficient number of parameters passed.");
            se.setLogged();
            throw se;
        }

        // now invoke service layer code to perform the job of information update
        testNhinrep = nhinrepService.updateContactInformation(pnhinRepId, pName, pContactName, pContactPhone, pContactEmail);

        return testNhinrep;
    }


     /**
     * Requirement: An Admin  needs to be able to register a new NHIN Rep.
     *
     * @param pUser - a corresponding user for the newly created NHIN Rep.
     * @param pNhinRep - NHIN Rep object that contains all the relevant information to save NHIN Rep in database.
     * @return Nhinrep - the newly created Nhinrep
     * @throws ServiceException
     */
    public Nhinrep registerNhinrep(User pUser, Nhinrep pNhinrep, int pReprole) throws ServiceException {

        log.info("NhinrepManager.registerNhinrep() - INFO");

        User user = null;
        Nhinrep nhinrep = null;
        int reprole=pReprole;

        user = pUser;               // null check done at the service layer
        nhinrep = pNhinrep   ;     // null check done at the service layer

        // now, invoke the service layer code to register new participant and create a user
        nhinrep = nhinrepService.registerNhinrep(user, nhinrep,reprole);

        return nhinrep;
    }

}
