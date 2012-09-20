/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.md.jms.listener;
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
import net.aegis.md.exception.MDRuntimeException;
import net.aegis.md.ws.pd.ExecuteScenario;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
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
		//		Map<String, String> docsAndRepIds = null;
		//		String uniquePatientId = null;
				
				int scenarioExecutionId = Integer.parseInt((String) mapMessage.getObject("scenarioExecutionId"));
				if(mapMessage.getObject("caseExecutionId") != null){
					caseExecutionId = Integer.parseInt((String) mapMessage.getObject("caseExecutionId"));
				}
				int candidateId = Integer.parseInt((String) mapMessage.getObject("candidateId"));
				String scenarioLevelExecution = (String) mapMessage.getObject("scenarioLevelExecution");
				String targetVersion = (String) mapMessage.getObject("targetVersion");
				String labType = (String) mapMessage.getObject("labType");
//				if(labType.equalsIgnoreCase(LabConstants.LabType.CONFORMANCE.toString())){
//					if(mapMessage.getObject("uniquePatientId") != null){
//						uniquePatientId = (String) mapMessage.getObject("uniquePatientId");
//					}
//					if(mapMessage.getObject("docsAndRepIds") != null){
//						docsAndRepIds = (Map<String, String>) mapMessage.getObject("docsAndRepIds");
//					}
//				}
				System.out.println("\n****************************************\n");
                System.out.println("CandidateId : " + candidateId);
                System.out.println("ScenarioExecutionId : " + scenarioExecutionId);
                System.out.println("CaseExecutionId : " + caseExecutionId);
                System.out.println("ScenarioLevelExecution : " + scenarioLevelExecution);
				System.out.println("Lab Type : " + labType);                
				System.out.println("TargetVersion : " + targetVersion);
                System.out.println("\n****************************************\n");
                ExecuteScenario msgProc = new ExecuteScenario();
                String result = msgProc.processRequestMsg(candidateId, scenarioExecutionId, caseExecutionId, scenarioLevelExecution, targetVersion, labType);
              //  String result = msgProc.processRequestMsg(candidateId, scenarioExecutionId, caseExecutionId, scenarioLevelExecution, targetVersion, labType, uniquePatientId, docsAndRepIds);
                long endtime = System.currentTimeMillis() - start;
                System.out.println("Processing Time in seconds : " + endtime / 1000);
                System.out.println("\n Result : " + result);
            }
            catch (LabRuntimeException ex) {
            	log.error(ex);    	
			} catch (JMSException ex) {
				 throw new MDRuntimeException("JMS exception occured in MD component" , ex);
			}
			catch (Throwable ex) {
            	log.error(ex);
			}
        } else {
            throw new MDRuntimeException("Message must be of type MapMessage");
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
}
