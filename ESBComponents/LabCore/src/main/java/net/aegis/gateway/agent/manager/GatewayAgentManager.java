package net.aegis.gateway.agent.manager;


import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.gateway.agent.dao.pojo.Gateway;
import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.gateway.agent.service.GatewayService;
import net.aegis.gateway.agent.service.TransactionService;
import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ILT-300 
 * @author Sunil.Bhaskarla
 */
public class GatewayAgentManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);

    private static GatewayAgentManager instance;

    private TransactionService transactionService = (TransactionService) ContextUtil.getLabContext().getBean("transactionService");
    private GatewayService gatewayService = (GatewayService) ContextUtil.getLabContext().getBean("gatewayService");

    private GatewayAgentManager() {
    }

    
    
    /**
     * @return GatewayAgentManager
     */
    public static GatewayAgentManager getInstance() {
        if (instance == null) {
            instance = new GatewayAgentManager();
        }
        return instance;
    }

    public List<Transaction> getAllTransaction() throws ServiceException {

        List<Transaction> results = null;

        try {
            results = transactionService.findAllTransaction();
        }
        catch (Exception ex) {
            log.error("ERROR: failure finding transaction."+ ex.toString());
            ServiceException se = new ServiceException("Failure finding transaction", "findAll", ex);
            se.setLogged();
            throw se;
        }

        return results;
    }

    public List<Transaction> getLimitedTransaction(final User user, String limit) throws ServiceException {

        List<Transaction> results = null;

        try {
        	results = transactionService.findLimitedTransaction(user, limit);
        }
        catch (Exception ex) {
            log.error("ERROR: failure finding transaction by user."+ ex.toString());
            ServiceException se = new ServiceException("Failure finding transaction by limit", "getLimited", ex);
            se.setLogged();
            throw se;
        }

        return results;
    }    
    
    public List<Transaction> getAllTransaction(final User user, Timestamp startDtTm, Timestamp endDtTm, String[] sender, String[] receiver) throws ServiceException {

        List<Transaction> results = null;

        try {
        	results = transactionService.findAllTransaction(user,startDtTm,endDtTm,sender,receiver);
        }
        catch (Exception ex) {
            log.error("ERROR: failure finding transaction by user."+ ex.toString());
            ServiceException se = new ServiceException("Failure finding transaction by user", "findAll", ex);
            se.setLogged();
            throw se;
        }

        return results;
    }
    
    public Transaction findById(Integer id) {
        try {
            return transactionService.findById(id);
        }
        catch (Exception ex) {
            log.error("ERROR: failure finding transaction by id."+id+". "+ ex.toString());
        }
        return null;
    }
    
    /**
     * 
     * @param user
     * @return
     * @throws ServiceException
     */
    public List<Gateway> getAllGateway(final User user) throws ServiceException {

        List<Gateway> results = null;

        try {
        	results = gatewayService.findAllGateway(user);
        }
        catch (Exception ex) {
            log.error("ERROR: failure finding Gateway by user."+ ex.toString());
            ServiceException se = new ServiceException("Failure finding Gateway by user", "findAll", ex);
            se.setLogged();
            throw se;
        }

        return results;
    }
}
