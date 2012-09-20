package net.aegis.lab.manager;

import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.gateway.agent.service.TransactionService;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransactionsManager {
	private static final Log log = LogFactory.getLog(TransactionsManager.class);
	private static TransactionService transactionService = (TransactionService) ContextUtil.getLabContext().getBean("transactionService");
	private static TransactionsManager instance;

	private TransactionsManager() {
	}

	/**
	 * @return TransactionManager
	 */
	public static TransactionsManager getInstance() {
		if (instance == null) {
			instance = new TransactionsManager();
		}
		return instance;
	}
	
	
    /**
     * @param id
     * @return
     * @throws ServiceException
     */
    public Transaction findById(Integer id) throws ServiceException {
        return transactionService.findById(id);
    }
    

    /**
     * @return
     * @throws ServiceException
     */
    public List<Transaction> findAllTransaction() throws ServiceException {
        return transactionService.findAllTransaction();
    }
    
    /**
     * 
     * @param transactionIds
     * @return
     * @throws ServiceException
     */
    public List<Transaction> findTransactionsByIds(List<Integer> transactionIds) throws ServiceException {
        return transactionService.findTransactionsByIds(transactionIds);
    }

}
