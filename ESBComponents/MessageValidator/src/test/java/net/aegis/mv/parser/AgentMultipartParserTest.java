package net.aegis.mv.parser;

import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.manager.TransactionsManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

public class AgentMultipartParserTest {
	private static final Log log = LogFactory.getLog(AgentMultipartParserTest.class);
	
	public AgentMultipartParserTest() {
        log.info("AgentMultipartParserTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("AgentMultipartParserTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("AgentMultipartParserTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("AgentMultipartParserTest.setUp() - INFO");
    }

    @After
    public void tearDown() {
        log.info("AgentMultipartParserTest.tearDown() - INFO");
    }
    
    @Test	
	public  void testMultiPartMessages() {
    	
		TransactionsManager transactionsManager = TransactionsManager.getInstance();
		List<Transaction> transactions = transactionsManager.findAllTransaction();
		
		for(Transaction transaction : transactions) {
			//Skip contenet types other than multipart.
			if(!transaction.getContentType().toLowerCase().startsWith("multipart/")) {
				continue;
			}
			
			AgentMultipartParser parser = AgentMultipartParser.getInstance();
			List<Part> parts = parser.processMultipartMessage(transaction);
			printParts(parts, transaction.getId());
		}
				
		/*String msgType = new String(transaction.getMessageHeader());
		System.out.println("\n============ Message type -START==========================");
		System.out.println(msgType);
		System.out.println("============ Message type -END==========================\n");
		
		String msg = new String(transaction.getMessage());		
		System.out.println("\n============ Message -START==========================");
		System.out.println(msg);
		System.out.println("============ Message -END==========================\n");*/		
	}
	
	private  void printParts(List<Part> parts, int id) {
		if(parts == null || parts.isEmpty()) {
			log.warn("No parts found for the message with ID: " + id);
			return;
		}
		
		log.info("\n=================Parsed Parts for the message with ID: " + id + " - START===================");
		for(Part part : parts) {
			log.info("\n==================\n======>Next Part:\n==================");
			log.info("Multipart part is " + (part.isFile() ? "a filePart" : part.isParam() ? "a ParamPart" : "Neither a FilePart nor a ParamPart"));
			if(part.isFile()) {
				FilePart filePart = (FilePart)part;
				log.info("File Name: " + filePart.getName());
			} else if (part.isParam()){
				ParamPart paramPart = (ParamPart)part;
				log.info(new String(paramPart.getValue()));
			}			
		}
		log.info("=================Parsed Parts for the message with ID: " + id + " - END===================\n");
	}

}
