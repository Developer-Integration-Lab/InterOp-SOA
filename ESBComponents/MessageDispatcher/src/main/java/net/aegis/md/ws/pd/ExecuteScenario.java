/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.md.ws.pd;

import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.manager.ScenarioExecutionManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.exception.LabRuntimeException;
import net.aegis.md.exception.MDRuntimeException;
import net.aegis.md.ws.qd.TestCaseExecutionHelperQD;
import net.aegis.md.ws.rd.TestCaseExecutionHelperRD;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
public class ExecuteScenario {

	private static final Log log = LogFactory.getLog(ExecuteScenario.class);
	private static final String pdqddelaykey = "test.case.execution.delay.pd.qd.milli";
	private static final String qdrddelaykey = "test.case.execution.delay.qd.rd.milli";
	public static final String QDR_KEY = LabConstants.DOCQUERY + LabConstants.SPLITTER_CARET;

	public String processRequestMsg(int candidateId, int scenarioExecutionId, int caseExecutionId, String scenarioLevelExecution, String targetVersion, String labType) throws MDRuntimeException{
		log.info("***************Entered the processRequestMsg()***************");
		StringBuffer result = new StringBuffer();
		String msg = "";
		Caseexecution caseExecution = null;
		ScenarioExecutionManager scenarioExecutionManager = ScenarioExecutionManager.getInstance();
		ParticipantCaseExecutionManager caseExecutionManager = ParticipantCaseExecutionManager.getInstance();
		// Get the scenario configuration information from the testlab database
		Scenarioexecution scenarioExecution = scenarioExecutionManager.findByScenarioExecutionId(scenarioExecutionId);
		int scenarioId = scenarioExecution.getScenario().getScenarioId();
		Participant participant = scenarioExecution.getParticipant();
		if (scenarioLevelExecution != null && scenarioLevelExecution.equalsIgnoreCase("N")) {
			caseExecution = caseExecutionManager.getCaseExecutionById(caseExecutionId);
			msg = processTestCase(participant, caseExecution, scenarioId, labType);
			result.append(msg);
		} else {
			for (int i = 0; i < scenarioExecution.getCaseexecutions().size(); i++) {
				caseExecution = scenarioExecution.getCaseexecutions().get(i);
				msg = processTestCase(participant, caseExecution, scenarioId, labType);
				result.append(msg);
			}
		}
		log.info("\n\n*********** End of Patient Discovery Query Results ************\n\n");

		log.info("***************Exiting the processRequestMsg()***************");
		return result.toString();
	}

	public String processTestCase(Participant participant, Caseexecution caseExecution, int scenarioId, String labType) throws MDRuntimeException {
		StringBuffer resultMessage = new StringBuffer();
		Integer caseExecutionId = caseExecution.getCaseExecutionId();
		try {
			String messageType = caseExecution.getTestcase().getMessageType();
			int caseId = caseExecution.getTestcase().getCaseId();
			log.info("Executing testCase: " + caseId);
			//Execute PD, QD and RD
			if (messageType.equals(LabConstants.PATIENTDISCOVERY)) {
				TestCaseExecutionHelperPD testCaseExecutionHelperPD = new TestCaseExecutionHelperPD();
				String pdresult = testCaseExecutionHelperPD.executeTestCasePD(participant, caseExecution, scenarioId, caseId);
				resultMessage.append(pdresult);
				//                
				//                if(labType.equalsIgnoreCase(LabConstants.LabType.CONFORMANCE.toString()) && caseExecution.getTestcase().isResponder()) {
				//                   addEvent(participant.getParticipantId()+LabConstants.SPLITTER_CARET+QDR_KEY+caseExecution.getTestcase().getCaseId().toString(),  caseExecution.getParticipantpatientId()+ "^^^&" + participant.getAssigningAuthorityId() + "&ISO" , LabConstants.PATIENTDISCOVERY);
				//                }             
				//                
				long qdrdDelayLong = getDelayTime(pdqddelaykey);
				log.info("ExecuteTestCase - QD RD Delay is " + qdrdDelayLong);
				Thread.sleep(qdrdDelayLong);
				log.info("Result of Executing PD testcase : " + resultMessage);
			} else if (messageType.equals(LabConstants.LAB_DOCQUERY)) {
				TestCaseExecutionHelperQD testCaseExecutionHelperQD = new TestCaseExecutionHelperQD();
				//       String qdresult = testCaseExecutionHelperQD.executeTestCaseQD(participant, caseExecution, scenarioId, caseId, uniquePatientId);
				String qdresult = testCaseExecutionHelperQD.executeTestCaseQD(participant, caseExecution, scenarioId, caseId);
				resultMessage.append(qdresult);
				log.info(" QD Result : " + resultMessage);
				long qdrdDelayLong = getDelayTime(qdrddelaykey);
				log.info("ExecuteTestCase - QD RD Delay is " + qdrdDelayLong);
				Thread.sleep(qdrdDelayLong);
			} else if (messageType.equals(LabConstants.LAB_DOCRETRIEVE)) {
				log.info("Its a RD testcase >>>>>>>>>>>>>>>>>>>");
				TestCaseExecutionHelperRD testCaseExecutionHelperRD = new TestCaseExecutionHelperRD();
				Map<String, String> passedDocAndRepIds = null;
				if ((caseExecution.getDependentScenarioId() != null) && (caseExecution.getDependentCaseId() != null)) {
					log.info("LabType : "+ labType);
					// prepare documents for RD from resultdocuments table instead of  scenariocase blob . 
					if(caseExecution.getTestcase().isResponder()){
						passedDocAndRepIds = testCaseExecutionHelperRD.findResultDocumentsForDependentTestCase(participant, caseExecution.getDependentScenarioId(), caseExecution.getDependentCaseId());
						if(passedDocAndRepIds==null || passedDocAndRepIds.size() ==0){
							
							// create caseresult record with fail status saying that  " QD  should  executed successfully before executing RD "
							Caseresult caseResult = new Caseresult();
							caseResult.setCaseexecution(caseExecution);
							caseResult.setResult(LabConstants.STATUS_FAIL);
							caseResult.setMessage(LabConstants.RD_DEPENDENT_VALIDATION);
							caseResult.setCreatedAt(DateUtil.getTodayDateTime());
							caseResult.setCreatedBy(LabConstants.MD_USER);
							CaseResultManager.getInstance().saveCaseResult(caseResult);
							
							resultMessage.append(LabConstants.RD_DEPENDENT_VALIDATION);
							return resultMessage.toString();
						}
						
					}
					String rdresult = testCaseExecutionHelperRD.executeTestCaseRD(participant, caseExecution, scenarioId, caseId, passedDocAndRepIds);
					resultMessage.append(rdresult);
				}
				
				
			}
		}catch (LabRuntimeException ex) {
			log.error(ex.getMessage());
			logErrorMessage(caseExecutionId, null, ex.getErrorCode() , ex.getMessage(),getStackTrace(ex));
		}catch (InterruptedException ex) {
			log.error(ex.getMessage());
			throw new MDRuntimeException(ex);         			
		}catch (Throwable ex) {
			log.error(ex);
			logErrorMessage(caseExecutionId, null, null , ex.getMessage(),getStackTrace(ex));
		}
		return resultMessage.toString();
	}

	/**
	 *  used to log error message in to database 
	 * @param caseExecId
	 * @param status
	 * @param message
	 */
	private void logErrorMessage(  int caseExecId , String status, String errorCode , String message,String errorStackTrace){

		Caseresult caseResult = new Caseresult();	
		if(StringUtils.isEmpty(status)){
			caseResult.setResult(LabConstants.STATUS_ERROR);
		}	
		// set case execution for case result
		Caseexecution caseExec = new Caseexecution();
		caseExec.setCaseExecutionId(caseExecId);
		caseResult.setCaseexecution(caseExec);
		if(StringUtils.isNotEmpty(errorCode)){
			caseResult.setErrorCode(errorCode);
		}
		caseResult.setMessage(message);
		caseResult.setStacktrace(errorStackTrace);
		caseResult.setSubmissionInd("N");	        
		CaseResultManager.getInstance().saveCaseResult(caseResult);
	}

	private String getStackTrace(Throwable ex ){
		String stackTrace = ExceptionUtils.getStackTrace(ex);
		return stackTrace;
	}

	public long getDelayTime(String delaykey) throws MDRuntimeException {
		long testcaseExecDelaytime = 10000;
		try {
			ApplicatiopropertiesManager apppropManager = ApplicatiopropertiesManager.getInstance();
			String delayMillidb = apppropManager.getPropertyvalueByKey(delaykey).trim();
			log.info("ExecuteTestCase - Delay from db is " + delayMillidb);
			if (delayMillidb != null && !delayMillidb.equals("")) {
				testcaseExecDelaytime = Long.parseLong(delayMillidb);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new MDRuntimeException(ex);
		}
		return testcaseExecDelaytime;
	}

	//    public void addEvent(String apKey, String eventType, java.lang.String desc) {
	//        log.info("addEvent: "+eventType);
	//        List<Applicationproperties> apList = null;
	//        try {
	//            apList = ApplicatiopropertiesManager.getInstance().findByKey(apKey);
	//            Applicationproperties ap = null;
	//            if (apList != null && !apList.isEmpty()) {
	//                ap = apList.get(0);
	//            } else {
	//                ap = new Applicationproperties();
	//                ap.setPropertykey(apKey);
	//            }
	//            ap.setPropertyvalue(eventType);
	//            if (desc!=null || !"".equals(desc)) {
	//                ap.setDescription(desc);
	//            } else  {
	//                ap.setDescription(new java.util.Date().toString());
	//            }
	//            ApplicatiopropertiesManager.getInstance().update(ap, null);
	//        } catch (Exception ex) {
	//            log.info(ex.toString());
	//        }
	//    }
}
