/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.parser.pd;

import java.io.BufferedInputStream;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.util.IOUtil;

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

public class PDRequestParserTest {

	private static final Log log = LogFactory.getLog(PDRequestParserTest.class);
	 
	public PDRequestParserTest() {
		log.info("PDRequestParserTest empty constructor - INFO");
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		log.info("PDRequestParserTest.setUpClass() - INFO");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		log.info("PDRequestParserTest.tearDownClass() - INFO");
	}

	@Before
	public void setUp() {
		log.info("PDRequestParserTest.setUp() - INFO");
	}

	@After
	public void tearDown() {
		log.info("PDRequestParserTest.tearDown() - INFO");
	}

	@Test	
	public void parsePDRequestXML()
    {
		BufferedInputStream stream   = null;
		try
		{	
			stream = new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Scenario1_PD_Request.xml"));	
			String xmlContent = IOUtil.getContentsFromInputStream(stream);
			Transaction transaction = new Transaction();
			transaction.setMessage(xmlContent.getBytes());
			PDRequestParser parser = PDRequestParser.getInstance();
			parser.parse(transaction);		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try { if (stream != null) { stream.close(); } }catch(Exception ex) {}
		}
    }
	
	/*@Test	
	public void testTransactionalPDRequest()
    {
		log.info("testTransactionalPDRequest() start" );
		try
		{
			Integer transactioId = 1293;
			Transaction request = TransactionsManager.getInstance().findById(transactioId);	
			PDRequestParserNew parser = new PDRequestParserNew();
			PDNhinRequest pDNhinRequest = parser.parsePDRequestMessage(request.getMessage());
			if(pDNhinRequest!=null ){
				log.info("MessageId:" + pDNhinRequest.getMessageID());
			}
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
    }*/
	
	
	
}
