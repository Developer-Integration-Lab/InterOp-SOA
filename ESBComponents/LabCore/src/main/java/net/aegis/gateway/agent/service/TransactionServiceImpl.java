package net.aegis.gateway.agent.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.gateway.agent.dao.TransactionDAO;
import net.aegis.gateway.agent.dao.pojo.Gateway;
import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {



	private static final int UI_TRANSACTION_DAY_LIMIT = 3;

	@Autowired
    private TransactionDAO transaction;

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(TransactionServiceImpl.class);

    @Override
    public List<Transaction> findAllTransaction() {
        return transaction.findAllTransaction();
    }

    
    
    @Override
	public List<Transaction> findLimitedTransaction(User user, String limit) {
		
    	int days = UI_TRANSACTION_DAY_LIMIT;
		
    	if (limit!=null && !"".equals(limit)) {
    	
    		/* if (limit.trim().toLowerCase().endsWith(LabConstants.DAY)) {
    			limit = limit.replace(LabConstants.DAY, "");
    			try {
    				days = Integer.parseInt(limit);    				
    			} catch (NumberFormatException nfe) {
    				log.error("invalid limit for day(s) specified: " + limit);
    			}    			
    		} */
			try {
				days = Integer.parseInt(limit);    				
			} catch (Exception ex) {//expect NumberFormatException
				days = UI_TRANSACTION_DAY_LIMIT;
				log.error("days reset to "+ days +"invalid limit for days(s) specified: " + limit);
			}
    	}

    	
    	
        Calendar cal = Calendar.getInstance();
        Timestamp endDtTm = new Timestamp(cal.getTime().getTime());
        
        cal.add(Calendar.DAY_OF_MONTH, -(days));
        Timestamp startDtTm = new Timestamp(cal.getTime().getTime());

    	return findAllTransaction(user, startDtTm, endDtTm, null, null);
 
	}



	@Override
    public List<Transaction> findAllTransaction(final User user, Timestamp startDtTm, Timestamp endDtTm, String[] sender, String[] receiver) {
    	List<Transaction> results = null;
    	Transaction criteria = new Transaction();
    	
		List<Criterion> criterionList = new ArrayList<Criterion>();	
    	
    	if (user!=null) {
    		if (startDtTm!=null && endDtTm!=null) {
    			criterionList.add(Restrictions.between("time", startDtTm, endDtTm));
    		} else {
    			log.info("Start or end time is NOT part of criteria.");
    		}
    		

			Disjunction dj = null;
    		if (sender!=null && sender.length>0 && receiver!=null && receiver.length>0){
    			dj = Restrictions.disjunction();
    			
    			for (String s : sender) {
    				for (String r : receiver) {
    					dj.add(Restrictions.and(Restrictions.eq("senderHCID",s), Restrictions.eq("receiverHCID",r)));
    					dj.add(Restrictions.and(Restrictions.eq("receiverHCID",s), Restrictions.eq("senderHCID",r)));
    				}
    			}
    		} 
    		
    		if (LabConstants.ROLE_PARTICIPANT==user.getPrimaryRole()) {
    			String hcid = user.getParticipant().getCommunityId();
	    		if (dj==null) {
	    			criteria.setSender(new Gateway());
	    			criteria.setReceiver(new Gateway());	    			
	    			criterionList.add(Restrictions.or(Restrictions.eq("senderHCID",hcid), Restrictions.eq("receiverHCID",hcid)));
	    		} else {
	    			criterionList.add(dj);
	    		}
    		} else {
    			if (dj!=null) {
    				criterionList.add(dj);
    			}
    		}
        	
    	} else 
    		return null;

    	List<Order> orderByList = null;
        orderByList = new ArrayList<Order>();
        orderByList.add(Order.desc("time"));

        
    	results = transaction.searchByCriteriaAndSort(criteria, criterionList, orderByList);

    	return results;
    }
    
    @Override
    public Transaction findById(Integer id) {
        return transaction.read(id);
    }
    
    @Override
    public List<Transaction> findTransactionsByIds(List<Integer> transactionIds){
    	
    	List<Transaction> transList = new ArrayList<Transaction>();
    	List<Criterion> criterionList = getCriterionList(transactionIds);
    	Transaction criteria = new Transaction();
    	List<Order> orderByList = null;
    	orderByList = new ArrayList<Order>();
        orderByList.add(Order.asc("time"));
        
        transList = transaction.searchByCriteriaAndSort(criteria, criterionList, orderByList);
    	return transList;
    }
    
    private List<Criterion> getCriterionList(List<Integer> transactionIds) {

        List<Criterion> criterionList = new ArrayList<Criterion>();

        if (transactionIds != null) {
        	criterionList.add(Restrictions.in("id", transactionIds));
        }
        return criterionList ; 
    }




}
