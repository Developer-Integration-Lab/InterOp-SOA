package net.aegis.lab.web.action.gatewayagent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.validator.routines.CalendarValidator;



import net.aegis.gateway.agent.dao.pojo.Gateway;
import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.gateway.agent.manager.GatewayAgentManager;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dao.pojo.Userrole.UserrolePK;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.util.LabConstants;



/**
 * ILT-300
 * @author Sunil.Bhaskarla
 */
public class TransactionList extends BaseAction {

    private static final String CMD_SEARCH = "Search";
	private static final long serialVersionUID = 1L;
    private List<Transaction> transactionList;
    private String startTime;
    private String endTime;
    private Timestamp startDtTm;
    private Timestamp endDtTm;
    private String[] senderSel;
    private String[] receiverSel;    
    List<Gateway> sender;
    List<Gateway> receiver;
    String cmdButton;
    String userMessage;
    Integer maxLimit = 50;
    
    
    @Override
    public String execute() throws Exception {

        log.info("TransactionList.execute() - INFO");

        
        final User user = this.getProfile();
        if (user!=null) {
        	if (CMD_SEARCH.equals(cmdButton)) {
        		transactionList = GatewayAgentManager.getInstance().getAllTransaction(user,startDtTm,endDtTm,senderSel,receiverSel);
        		if (transactionList==null || (transactionList!=null && transactionList.size()==0) ) {
        			userMessage = "The lab transaction history does not contain any records for the search criteria.";
        		}
        	} else {
                /*
                 * WBS ILT-423.II:
                 * Retreive app property
                 */
                List<Applicationproperties> apList = null;
            	Applicationproperties ap = null;
            	String limit = null;        		
        		
            	try {
                    apList = ApplicatiopropertiesManager.getInstance().findByKey(LabConstants.UI_TRANSACTION_DISPLAYLIMIT);
                    if (apList!=null) {
                    	ap = apList.get(0);
                    	if (ap!=null) {
                			limit = ap.getPropertyvalue();
                		}
                    }

                } catch (Exception ex) {
                	log.info("No property found for " + LabConstants.UI_TRANSACTION_DISPLAYLIMIT);
                }
               
                try {
                    apList = ApplicatiopropertiesManager.getInstance().findByKey(LabConstants.UI_TRANSACTION_DISPLAYMAX);
                    if (apList!=null) {
                    	ap = apList.get(0);
                    	if (ap!=null) {
                			maxLimit = new Integer(ap.getPropertyvalue());
                		}
                    }
                } catch (Exception ex) {
                	log.info("No property found for " + LabConstants.UI_TRANSACTION_DISPLAYMAX);
                } 
        		
        		transactionList = GatewayAgentManager.getInstance().getLimitedTransaction(user, limit);
        		if (transactionList==null || (transactionList!=null && transactionList.size()==0) ) {
        			userMessage = "No recent messages found within " + limit + " days.";
        		} else {
        			userMessage = "Results were limited to the last " + limit + " days.";
        		}
        	}
            
            sender = GatewayAgentManager.getInstance().getAllGateway(user);
            receiver = GatewayAgentManager.getInstance().getAllGateway(user);                   
            
        } else {
        	log.error("No user object in TransactionList action!");
        }


        return SUCCESS;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
		try {
            CalendarValidator calValidator = CalendarValidator.getInstance();
            Calendar startCalendar = calValidator.validate(startTime, "yyyy-MM-dd HH:mm:ss");
            if (startCalendar != null) {
            	startDtTm = Timestamp.valueOf(startTime);
            } else {
            	startDtTm = null;
            }
        } catch (Exception ex) {
        	log.info("startDtTm exception:" +ex.toString());
        	startDtTm = null;
        }
	}



	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;

		try {
            CalendarValidator calValidator = CalendarValidator.getInstance();
            Calendar startCalendar = calValidator.validate(endTime, "yyyy-MM-dd HH:mm:ss");
            if (startCalendar != null) {
            	endDtTm = Timestamp.valueOf(endTime);
            } else {
            	endDtTm = null;
            }
        } catch (Exception ex) {
        	log.info("endDtTm exception:" +ex.toString());
        	endDtTm = null;
        }
	}

	/**
	 * @return the sender
	 */
	public List<Gateway> getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(List<Gateway> sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public List<Gateway> getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(List<Gateway> receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the startDtTm
	 */
	public Timestamp getStartDtTm() {
		return startDtTm;
	}

	/**
	 * @param startDtTm the startDtTm to set
	 */
	public void setStartDtTm(Timestamp startDtTm) {
		this.startDtTm = startDtTm;
	}

	/**
	 * @return the endDtTm
	 */
	public Timestamp getEndDtTm() {
		return endDtTm;
	}

	/**
	 * @param endDtTm the endDtTm to set
	 */
	public void setEndDtTm(Timestamp endDtTm) {
		this.endDtTm = endDtTm;
	}

	/**
	 * @return the senderSel
	 */
	public String[] getSenderSel() {
		return senderSel;
	}

	/**
	 * @param senderSel the senderSel to set
	 */
	public void setSenderSel(String[] senderSel) {
		this.senderSel = senderSel;
	}

	/**
	 * @return the receiverSel
	 */
	public String[] getReceiverSel() {
		return receiverSel;
	}

	/**
	 * @param receiverSel the receiverSel to set
	 */
	public void setReceiverSel(String[] receiverSel) {
		this.receiverSel = receiverSel;
	}

	/**
	 * @return the cmdButton
	 */
	public String getCmdButton() {
		return cmdButton;
	}

	/**
	 * @param cmdButton the cmdButton to set
	 */
	public void setCmdButton(String cmdButton) {
		this.cmdButton = cmdButton;
	}

	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}

	/**
	 * @param userMessage the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	/**
	 * @return the maxLimit
	 */
	public Integer getMaxLimit() {
		return maxLimit;
	}

	/**
	 * @param maxLimit the maxLimit to set
	 */
	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
	}




}


