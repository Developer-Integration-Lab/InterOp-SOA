/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.jms.provider;

import javax.jms.DeliveryMode;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Venkat Keesara
 */
public class MsgProviderTest {

	private static final Log log = LogFactory.getLog(MsgProviderTest.class);
	public MsgProviderTest() {
        log.info("MsgProviderTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("MsgProviderTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("MsgProviderTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("MsgProviderTest.setUp() - INFO");
    }

    @After
    public void tearDown() {
        log.info("MsgProviderTest.tearDown() - INFO");
    }
    
    @Test    
	public void testMsgProvider(){
    	String DEFAULT_BROKER_NAME = "tcp://localhost:61616";
    	String DEFAULT_USER_NAME = ActiveMQConnection.DEFAULT_USER;
    	String DEFAULT_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    	String DEFAULT_Queue = "Queue-Agent-MessageSink";
    	try {
    		// Create a JMS connection
    		javax.jms.QueueConnectionFactory factory;
    		factory = new ActiveMQConnectionFactory(DEFAULT_USER_NAME, DEFAULT_PASSWORD, DEFAULT_BROKER_NAME);
    		javax.jms.QueueConnection connect = factory.createQueueConnection(DEFAULT_USER_NAME, DEFAULT_PASSWORD);
    		javax.jms.QueueSession session = connect.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
    		// Create the Queue and QueueSender for sending requests.
    		javax.jms.Queue queue = null;
    		queue = session.createQueue(DEFAULT_Queue);
    		javax.jms.QueueSender sender = session.createSender(queue);

    		// Now that all setup is complete, start the Connection and send the message.
    		connect.start();
    		for(int i=1265; i<=1268; i++){
        		javax.jms.MapMessage mapMsg = session.createMapMessage();
    			mapMsg.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
    			mapMsg.setString("transactionId", String.valueOf(i));
    			log.info("sending message: " + i + " to Queue");
    			sender.send(mapMsg);
    		}
    	} catch (javax.jms.JMSException jmse) {
    		log.error("Error: JMS exception - "  + jmse.getMessage());          
    	} 
    }
}
