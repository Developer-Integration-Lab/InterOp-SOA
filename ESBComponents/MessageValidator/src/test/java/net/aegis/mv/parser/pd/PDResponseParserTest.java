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

public class PDResponseParserTest
{
	
	private static final Log log = LogFactory.getLog(PDResponseParserTest.class);
	
	public PDResponseParserTest() {
        log.info("PDResponseParserTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("PDResponseParserTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("PDResponseParserTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("PDResponseParserTest.setUp() - INFO");
    }

    @After
    public void tearDown() {
        log.info("PDResponseParserTest.tearDown() - INFO");
    }
    
    @Test	
	public void testParsePDResponseXML()
    {
		BufferedInputStream stream   = null;
		try
		{	
			stream = new BufferedInputStream(getClass().getResourceAsStream("/Scenario1_PD_Response.xml"));	
			String xmlContent = IOUtil.getContentsFromInputStream(stream);
			Transaction transaction = new Transaction();
			transaction.setMessage(xmlContent.getBytes());
			PDResponseParser parser = PDResponseParser.getInstance();
			parser.parse(transaction);		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try { if (stream != null) { stream.close(); } }catch(Exception ex) {}
		}		
    }	
	
	/*public void visit(Node node, PDNhinResponse pDNhinResponse)
	{
		NodeList nl = node.getChildNodes();
		log.info( node.getNodeName()+ " depth::" + nl.getLength());
		for(int i=0, cnt=nl.getLength(); i<cnt; i++)
		{
			Node eachNode = nl.item(i);
			log.info("===========["+eachNode+"]=============");
			log.info("eachNode.getNodeType():::" + eachNode.getNodeType());
			log.info("eachNode.getNodeValue():::" + eachNode.getNodeValue());
			log.info("eachNode.getNodeName():::" + eachNode.getNodeName());
			log.info("eachNode.getLocalName():::" + eachNode.getLocalName());
			if( eachNode!=null && eachNode.getNodeType()== Node.ELEMENT_NODE 
					&& eachNode.getLocalName()!=null && eachNode.getLocalName().equalsIgnoreCase(MVConstants.RELATES_TO)){
				
					String textValue = getElementTextValue((Element)eachNode);
					log.info("textValue==" + textValue);
					pDNhinResponse.setMessageID(textValue);
			}
			
			visit(eachNode,pDNhinResponse);
		}
	}
	*/
	
	
}