package net.aegis.mv.util;

import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.manager.TransactionsManager;
import net.aegis.mv.enums.MsgTypeEnum;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;

public class MessageValidatorUtilTest {
	
	@Test
	public void testFindMsgType() {
		TransactionsManager transactionsManager = TransactionsManager.getInstance();
		List<Transaction> transactions = transactionsManager.findAllTransaction();
		
		MessageValidatorUtil util = MessageValidatorUtil.getInstance();
		for(Transaction transaction : transactions) {
			if(transaction.getContentType().equalsIgnoreCase("application/soap+xml")
					|| transaction.getContentType().startsWith("multipart/")) {
				MsgTypeEnum messageType = util.findMessageType(transaction);
				/*assertNotNull("Message type for the transaction with ID: " 
						+ transaction.getId()
						+ " can not be null",  messageType);*/

				System.out.println("Message Type: " + messageType + ". Transaction ID: " + transaction.getId());
			}
		}
	}

}
