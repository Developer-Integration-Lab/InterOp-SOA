/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.lab.manager;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.VwGateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Venkat.Keesara
 * Apr 10, 2012
 **/

public class TransactionsManagerTest {

	 private static final Log log = LogFactory.getLog(TransactionsManagerTest.class);
	 	TransactionsManager transactionsManager = null;

	    public TransactionsManagerTest() {
	        log.info("TransactionsManagerTest empty constructor - INFO");
	    }

	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        log.info("TransactionsManagerTest.setUpClass() - INFO");
	    }

	    @AfterClass
	    public static void tearDownClass() throws Exception {
	        log.info("TransactionsManagerTest.tearDownClass() - INFO");
	    }

	    @Before
	    public void setUp() {
	        log.info("TransactionsManagerTest.setUp() - INFO");
	        transactionsManager = TransactionsManager.getInstance();
	    }

	    @After
	    public void tearDown() {
	        log.info("TransactionsManagerTest.tearDown() - INFO");
	    }

	  
	    @Test
	     public void findById()throws Exception {
	    	Transaction transaction = transactionsManager.findById(new Integer(831));
	    	log.info("status code : " + transaction.getStatusCode());
	    	log.info("MessageHeader : " + new String(transaction.getMessageHeader()));
	    	
	     }
}
