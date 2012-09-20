/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.tp.jms.provider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.DeliveryMode;

import net.aegis.tp.exception.TPRuntimeException;
import net.aegis.tp.ws.ProcessTestCaseRequestType;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
public class MsgProvider {

    private static final Log log = LogFactory.getLog(MsgProvider.class);
    private static String DEFAULT_BROKER_NAME = "tcp://localhost:61616";
    private static String DEFAULT_USER_NAME = ActiveMQConnection.DEFAULT_USER;
    private static String DEFAULT_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static String DEFAULT_Queue = "Queue-2.4.8";
    private javax.jms.QueueConnection connect = null;
    private javax.jms.QueueSession session = null;
    private javax.jms.QueueSender sender = null;
    private String result = "Success";

    /**
     * Create JMS client for sending messages.
    */
    private void sendMessage(String broker, String username, String password, String sQueue, ProcessTestCaseRequestType requestType){
       log.info("\n ***************Entered the sendMessage() *************** \n");
        try {
            // Create a JMS connection
            javax.jms.QueueConnectionFactory factory;
            factory = new ActiveMQConnectionFactory(username, password, broker);
            connect = factory.createQueueConnection(username, password);
            session = connect.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            // Create the Queue and QueueSender for sending requests.
            javax.jms.Queue queue = null;
            queue = session.createQueue(sQueue);
            sender = session.createSender(queue);

            // Now that all setup is complete, start the Connection and send the message.
            connect.start();
            log.info("\n ***************Before inserting into the queue *************** \n");
            if (requestType != null) {
                javax.jms.MapMessage mapMsg = session.createMapMessage();
                mapMsg.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
                mapMsg.setObject("candidateId", requestType.getCandidateId());
                mapMsg.setObject("scenarioExecutionId", requestType.getScenarioExecutionId());
                mapMsg.setObject("caseExecutionId", requestType.getCaseExecutionId());
                mapMsg.setObject("scenarioLevelExecution", requestType.getScenarioLevelExecution());
                mapMsg.setObject("labType", requestType.getLabType());
              //  mapMsg.setObject("uniquePatientId", requestType.getUniquePatientId());
             //   mapMsg.setObject("docsAndRepIds", getDocsAndRepIds(requestType));
                mapMsg.setObject("targetVersion", requestType.getTargetVersion());
                sender.send(mapMsg);
            }
            log.info("\n ***************Exiting the sendMessage() *************** \n");
        } catch (javax.jms.JMSException jmse) {
            result = "Failed";
            log.error("Error: JMS exception - "  + jmse.getMessage());
            exit();
            throw new  TPRuntimeException("Exception Occurred while sending message to Queue :" + sQueue ,"JMS-ERROR" , jmse);
        } 
    }

    /** Cleanup resources and exit. */
    private void exit() {
        try {
            if(sender != null){
                sender.close();
            }
            if(connect != null){
                connect.close();
            }
        } catch (javax.jms.JMSException jmse) {
           throw new  TPRuntimeException("Exception Occurred while closing connection to Queue" ,"JMS-ERROR" ,  jmse);
        }
    }



/*
 * The method reads the jms queue parameters from the property file and
 * selects the queue based on the targetVersion passed in the request parameters.
 * If targetVersion parameter is null or empty the default queue is selected.
 */
    public String processMessage(ProcessTestCaseRequestType requestMsg) {
        log.info("\n ***************Entered the processMessage() *************** \n");
        //create an instance of properties class
        Properties props = new Properties();
        String broker = DEFAULT_BROKER_NAME;
        String username = DEFAULT_USER_NAME;
        String password = DEFAULT_PASSWORD;
        String queue = DEFAULT_Queue;
        try {
            InputStream is = this.getClass().getResourceAsStream("/Application.properties");
            if (null == is) {
                log.info("Can't locate file: Application.properties");
            } else {
                props.load(is);
                broker = props.getProperty("acitvemq_broker_name");
                username = props.getProperty("activemq_username");
                password = props.getProperty("activemq_password");
                log.info("The Target Version selected by the candidate : "+requestMsg.getTargetVersion());
               //Check for the targetVersion and assign the queue name
                if(requestMsg.getTargetVersion() != null){
                  String propQueue = props.getProperty(requestMsg.getTargetVersion()) ;
                  if(propQueue != null && propQueue.length() > 0){
                      queue = propQueue;
                  }
                }
            }
           log.info("\n****************************************\n");
           log.info("Selected borker name for activemq: "+ broker);
           log.info("Selected username for activemq : "+username);
           log.info("Selected password for activemq : "+ password);
           log.info("Selected queue : "+ queue);
           log.info("\n****************************************\n");

            // Start the JMS client for sending requests.
            sendMessage(broker, username, password, queue, requestMsg);
            
        } catch (IOException ex) {
        	 result = "Failed";
        	 throw new TPRuntimeException("Error occured while reading Application.properties" , ex);
		} finally {
            exit();
        }
        log.info("\n ***************Exiting the processMessage() *************** \n");
        return result;
    }
    
//    private Map<String, String> getDocsAndRepIds(ProcessTestCaseRequestType req){
//    	Map<String, String> docsAndRepIds = new HashMap<String, String>();
//    	if(req.getDocumentList()!= null && req.getDocumentList().size() > 0){
//    		for(Document doclist : req.getDocumentList()){
//    			docsAndRepIds.put(doclist.getDocumentID(), doclist.getRepoID());
//                log.info("getDocsAndRepIds: DocId :" + doclist.getDocumentID());
//                log.info("getDocsAndRepIds: Repository Id : "+  doclist.getRepoID());
//    		}
//    	}
//    	return docsAndRepIds;
//    }
}

