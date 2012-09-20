/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.handler;

import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.manager.TransactionsManager;
import net.aegis.mv.handler.MsgHandlerDelegator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * Venkat.Keesara Feb 22, 2012
 */
public class MsgHandlerDelegatorTest {

	private static final Log log = LogFactory.getLog(MsgHandlerDelegatorTest.class);

	public MsgHandlerDelegatorTest() {
		log.info("MsgHandlerDelegatorTest empty constructor - INFO");
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		log.info("MsgHandlerDelegatorTest.setUpClass() - INFO");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		log.info("MsgHandlerDelegatorTest.tearDownClass() - INFO");
	}

	@Before
	public void setUp() {
		log.info("MsgHandlerDelegatorTest.setUp() - INFO");
	}

	@After
	public void tearDown() {
		log.info("MsgHandlerDelegatorTest.tearDown() - INFO");
	}

	@Test
	public void testProcessQueueMessage() {

		TransactionsManager transactionsManager = TransactionsManager
				.getInstance();
		List<Transaction> transactions = transactionsManager
				.findAllTransaction();

		for (Transaction transaction : transactions) {
			MsgHandlerDelegator.getInstance().sendMessageToHandler(
					String.valueOf(transaction.getId()));
		}
	}
	
	@Test
	public void testProcessQueueMessageForTransactionId() {

		int beginTransactionId = 2131;
		int endTransactionId = 2131;
		for( ; beginTransactionId<=endTransactionId;beginTransactionId++ ){
			log.info("processing TransactioId==" + beginTransactionId);
			MsgHandlerDelegator.getInstance().sendMessageToHandler(
					String.valueOf(beginTransactionId));
		}
		
	}

}
