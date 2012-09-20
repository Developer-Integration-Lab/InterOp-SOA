/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.parser;

import java.io.InputStream;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.manager.TransactionsManager;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Venkat.Keesara
 * Apr 11, 2012
 **/

public class SOAPFaultParserTest {

	private static final Log log = LogFactory.getLog(SOAPFaultParserTest.class);
	
	public SOAPFaultParserTest() {
        log.info("SOAPFaultParserTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("SOAPFaultParserTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("SOAPFaultParserTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("SOAPFaultParserTest.setUp() - INFO");
    }

    @After
    public void tearDown() {
        log.info("SOAPFaultParserTest.tearDown() - INFO");
    }
    
    /*@Test	
	public void testSOAPFaultXML()
    {
		//int id = 1294 ; // PD soap fault
		//int id =538 ; // RD soap Fault with MTOM
		int id = 536 ; // QD soap fault 
    	try
		{
			InputStream stream   = null;
			stream = this.getClass().getResourceAsStream("/SOAP_Fault.xml");	
			String xmlContent = MessageValidatorUtil.getContentsFromInputStream(stream);
			SOAPFaultParser parser = new SOAPFaultParser();
			TransactionsManager transactionsManager = TransactionsManager.getInstance();
			Transaction transaction = transactionsManager.findById(Integer.valueOf(id));
			String relatesTo = parser.parseSOAPFaultMessage(transaction);
			
			log.info("relatesTo==" + relatesTo);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
    }*/
}
