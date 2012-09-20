package net.aegis.mv.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import net.aegis.mv.handler.MsgHandlerDelegator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author Venkat.Keesara
 */

public class MVQueueListener implements MessageListener{

	private static final Log log = LogFactory.getLog(MVQueueListener.class);
	public void onMessage(Message msg) {
		log.info("\n\n************onMessage() Start**************\n");
		if (msg instanceof MapMessage) {
			try {
				MapMessage mapMessage = (MapMessage) msg;
				String id = (String) mapMessage.getString("transactionId");
				log.info("TransactionId : "+ id);
				MsgHandlerDelegator.getInstance().sendMessageToHandler(id);

			}catch (JMSException ex) {
				ex.printStackTrace();
			}
			log.info("\n\n************onMessage() End**************\n");
		}
	}
}
