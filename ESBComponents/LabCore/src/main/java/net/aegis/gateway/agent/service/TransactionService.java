package net.aegis.gateway.agent.service;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.User;

        
public interface TransactionService {

    List<Transaction> findAllTransaction();
    List<Transaction> findAllTransaction(User user, Timestamp startDtTm, Timestamp endDtTm, String[] sender, String[] receiver);
    List<Transaction> findLimitedTransaction(User user, String limit);
    public Transaction findById(Integer id);
	public List<Transaction> findTransactionsByIds(List<Integer> transactionIds);
    
}
