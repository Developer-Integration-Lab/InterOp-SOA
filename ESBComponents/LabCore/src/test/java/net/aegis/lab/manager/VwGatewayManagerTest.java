/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.lab.manager;

import net.aegis.lab.dao.pojo.VwGateway;

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
 * Feb 19, 2012
 */
public class VwGatewayManagerTest {

	 private static final Log log = LogFactory.getLog(VwGatewayManagerTest.class);
	    VwGatewayManager vwGatewayManager = null;

	    public VwGatewayManagerTest() {
	        log.info("VwGatewayManagerTest empty constructor - INFO");
	    }

	    @BeforeClass
	    public static void setUpClass() throws Exception {
	        log.info("VwGatewayManagerTest.setUpClass() - INFO");
	    }

	    @AfterClass
	    public static void tearDownClass() throws Exception {
	        log.info("VwGatewayManagerTest.tearDownClass() - INFO");
	    }

	    @Before
	    public void setUp() {
	        log.info("VwGatewayManagerTest.setUp() - INFO");
	        vwGatewayManager = VwGatewayManager.getInstance();
	    }

	    @After
	    public void tearDown() {
	        log.info("VwGatewayManagerTest.tearDown() - INFO");
	    }

	  
	    @Test
	     public void findByGatewayAddress()throws Exception {
	    	VwGateway vwGateway = vwGatewayManager.findByGatewayAddress("d9yf7zn1.aegis.net");
	    	log.info("HCID : " + vwGateway.getHcid());
	    	log.info("LabNode (1-true , 0-false): " + vwGateway.getLabNode());
	    	
	     }
	
}
