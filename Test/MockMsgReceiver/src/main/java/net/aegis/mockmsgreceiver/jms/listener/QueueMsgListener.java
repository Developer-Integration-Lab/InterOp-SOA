/**
 *
 * @author Sunil.Bhaskarla
 */

package net.aegis.mockmsgreceiver.jms.listener;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.exception.LabRuntimeException;
import net.aegis.mockmsgreceiver.exception.MMRRuntimeException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class QueueMsgListener implements MessageListener {

    private static final Log log = LogFactory.getLog(QueueMsgListener.class);

    /**
    This method is called asynchronously by JMS when a message arrives
    at the queue. Client applications must not throw any exceptions in
    the onMessage method.
    @param message A JMS message.
     */
    @Override
    public void onMessage(Message message) {
        log.info("\n ***************Entered the onMessage() *************** \n");
        int caseExecutionId = 0;
        if (message instanceof MapMessage) {
            try {
                long start = System.currentTimeMillis();
                // System.out.println("start time : " + start);
             	MapMessage mapMessage = (MapMessage) message;
				Map<String, String> docsAndRepIds = null;
				String uniquePatientId = null;
				
				//int scenarioExecutionId = Integer.parseInt((String) mapMessage.getObject("scenarioExecutionId"));
				
				System.out.println("\n****************************************\n");               
            }
            catch (LabRuntimeException ex) {
				System.out.println("Captured LabRuntimeException in MMR Start>>>>>");            	
            	
            	System.out.println("Message:");
            	System.out.println(ex.getMessage());
            	
            	System.out.println("RootCause:");
            	System.out.println(ex.getCause());
            	         	
            	System.out.println("getErrorCode:");
            	System.out.println(ex.getErrorCode());
            	
            	System.out.println("****************Print Stack Trace of LabRuntimeException Start********************");
            	ex.printStackTrace();
            	System.out.println("****************Print Stack Trace of LabRuntimeException End********************");   	
            	
            	/* omit database persistence for this bundle */
			}
            catch (RuntimeException ex) {
            	log.error(ex);
				// for any runtime exception. Log the error message in to database or console for now 
            	
            	
               	System.out.println("Message:");
            	System.out.println(ex.getMessage());
            	
            	System.out.println("RootCause:");
            	System.out.println(ex.getCause());
            	         	
            	
            	System.out.println("****************Print Stack Trace of RuntimeException Start********************");
            	ex.printStackTrace();
            	System.out.println("****************Print Stack Trace of RuntimeException End********************");   	
            	
            	
//			} catch (JMSException ex) {
//				 throw new MMRRuntimeException("JMS exception occured in MMR component" , ex);
			}
			catch (Throwable ex) {
            	log.error(ex);
				// for any runtime exception . Log the error message in to database or console for now 
             	
               	System.out.println("Message:");
            	System.out.println(ex.getMessage());
            	
            	System.out.println("RootCause:");
            	System.out.println(ex.getCause());
            	         	
            	
            	System.out.println("****************Print Stack Trace of RuntimeException Start********************");
            	ex.printStackTrace();
            	System.out.println("****************Print Stack Trace of RuntimeException End********************");   	
            	
			}
        } else {
            throw new MMRRuntimeException("Message must be of type MapMessage");
        }
        log.info("\n ***************Exiting the onMessage() *************** \n");
    }

    /**
    This method is called asynchronously by JMS when some error occurs.
    When using an asynchronous message listener it is recommended to use
    an exception listener also since JMS have no way to report errors
    otherwise.
    @param exception A JMS exception.
     */
    public void onException(JMSException exception) {
        log.error("JMS error occurred: " + exception.getMessage());
    }
    
  
    private String getStackTrace(Throwable ex ){
    	String stackTrace = ExceptionUtils.getStackTrace(ex);
//    	System.out.println("stackTrace storing in database===" + stackTrace);
		return stackTrace;
    }
}
