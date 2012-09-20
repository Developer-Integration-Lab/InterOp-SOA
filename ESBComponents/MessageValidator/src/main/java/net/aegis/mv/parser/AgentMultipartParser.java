package net.aegis.mv.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;

public class AgentMultipartParser {
	private static final Log log = LogFactory.getLog(AgentMultipartParser.class);
	
	private static AgentMultipartParser instance = new AgentMultipartParser();
	private AgentMultipartParser() {}
	public static AgentMultipartParser getInstance() {
		return instance;
	}

	/**
	 * Parses the multipart message from the Transaction object that represents a content
	 * of type 'multipart/related'.
	 * 
	 * @param transaction
	 * 		Transaction object that represents a message 
	 * @return
	 * 		List of parsed parts of the message.
	 */
	public List<Part> processMultipartMessage(Transaction transaction) {
		
		if(!transaction.getContentType().toLowerCase().startsWith("multipart")) {
			log.warn("Transaction object [ID:" + transaction.getId() + "] doesn't represent a multipart message. Exiting from processing.");
			return null;
		}
        
    	byte[] messageHeader = transaction.getMessageHeader();
    	String boundary = getBoundary(messageHeader);

        //System.out.println(getClass().getSimpleName() + ".processMultipartMessage(): Multipart Boundary: " + boundary);

        if (boundary == null) {
            throw new RuntimeException("Boundary missing form the message header.");
        }
        //Create Multipart Request
        MockMultipartHttpServletRequest multipartRequest = new MockMultipartHttpServletRequest();
        multipartRequest.setContentType("multipart/form-data;" + boundary);

        byte[] message = transaction.getMessage();
        multipartRequest.setContent(message);
        
        //System.out.println(getClass().getName() + ": Message: " + new String(message));
        
        try {
	        //Standup multipart parser with the request, and read the parts
	        MultipartParser mparser = new MultipartParser(multipartRequest, (int) message.length);
	
	        List<Part> parts = new ArrayList<Part>();
	        
	        Part part = null;
	        do {             
	            part = mparser.readNextPart();
	            if (part != null) {
	                parts.add(part);
	            }
	        }while(part != null);
	        
	        return parts;
	        
	    } catch (IOException ex) {
	       ex.printStackTrace();
	       return null;
	   }
    }
	
	private String getBoundary(byte[] bMsgHeader) {
        String strMsgHeader = new String(bMsgHeader);
        String[] headerLines = strMsgHeader.split("\n");
        for (String header : headerLines) {
            if (header.toLowerCase().startsWith("content-type: ")) {
                String[] tokens = header.split(";");
                for (String token : tokens) {
                    if (token.trim().startsWith("boundary=")) {
                        return token;
                    }
                }
            }
        }
        return strMsgHeader;
    }

}
