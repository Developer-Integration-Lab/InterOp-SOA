package net.aegis.gateway.agent.dao;

import net.aegis.lab.genericdao.GenericDao;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;

/**
 * <p>Hibernate DAO layer for Transaction</p>
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
/**
 * ILT-300
 * @author Sunil.Bhaskarla
 */

public interface TransactionDAO extends GenericDao<Transaction, Integer> {

    
    public List<Transaction> findAllTransaction();
    
    public Transaction findById(Integer id);

}
