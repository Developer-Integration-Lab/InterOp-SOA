/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.handler;

import java.io.InputStream;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.util.IOUtil;
import net.aegis.mv.util.MVConstants;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 *
 * Venkat.Keesara
 * Feb 15, 2012
 */
public class MsgHandlerTest {

	 private static final Log log = LogFactory.getLog(MsgHandlerTest.class);
	 
	public MsgHandlerTest() {
        log.info("MsgHandlerTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("MsgHandlerTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("MsgHandlerTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("MsgHandlerTest.setUp() - INFO");
    }

    @After
    public void tearDown() {
        log.info("MsgHandlerTest.tearDown() - INFO");
    }
    
    @Test
    public void testFindMsgType(){
    	InputStream req = this.getClass().getResourceAsStream("/Scenario1_PD_Request.xml");
    	printMessageType(req,MVConstants.APPLICATION_SOAP_XML);
    	InputStream res = this.getClass().getResourceAsStream("/Scenario1_PD_Response.xml");
    	printMessageType(res,MVConstants.APPLICATION_SOAP_XML);
    	
    	req = this.getClass().getResourceAsStream("/Scenario1_QD_Request.xml");
    	printMessageType(req,MVConstants.APPLICATION_SOAP_XML);
    	res = this.getClass().getResourceAsStream("/Scenario1_QD_Response.xml");
    	printMessageType(res,MVConstants.APPLICATION_SOAP_XML);
    	
    	req = this.getClass().getResourceAsStream("/Scenario1_RD_Request.xml");
    	printMessageType(req,MVConstants.MULTIPART_RELATED);
    	res = this.getClass().getResourceAsStream("/Scenario1_RD_Response.xml");
    	printMessageType(res,MVConstants.MULTIPART_RELATED);
    	
    	
    }
    
	private void printMessageType(InputStream is,String contentType) {
		String xmlContent = IOUtil.getContentsFromInputStream(is);

		//Find the message Type
		Transaction transaction = new Transaction();
		transaction.setContentType(contentType);
		MsgTypeEnum findMsgType = MessageValidatorUtil.getInstance().findMessageType(transaction);
		
		if (findMsgType != null && findMsgType.getTextId() != null) {			
			log.info("MesssageType::::" + findMsgType.getDefaultDescription());			
		}
	}

}
