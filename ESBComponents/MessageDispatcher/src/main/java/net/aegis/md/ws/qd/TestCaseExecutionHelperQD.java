package net.aegis.md.ws.qd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ScenarioCaseManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.jaxb.qd.DocumentInfoType;
import net.aegis.labcommons.jaxb.qd.ExtrinObjectType;
import net.aegis.labcommons.jaxb.qd.QDEntityResponseType;
import net.aegis.labcommons.service.QueryDocument;
import net.aegis.md.exception.MDRuntimeException;
import net.aegis.md.util.ServiceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
public class TestCaseExecutionHelperQD {

	private static final Log log = LogFactory.getLog(TestCaseExecutionHelperQD.class);

	public TestCaseExecutionHelperQD() {
	}

	/**
	 *
	 * @param candidate
	 * @param caseExecution
	 * @param scenarioId
	 * @param caseId
	 * @param rawData
	 * @return <code>TestCaseExecutionResultQD</code>
	 * @throws Exception
	 */
	public String executeTestCaseQD(Participant participant, Caseexecution caseExecution, int scenarioId, int caseId) {
		log.info("TestCaseExecutionHelperQD - executeTestCaseQD : BEGIN");
		QDEntityResponseType result = null;
		String qd_result = "";
		StringBuffer resultMsg = new StringBuffer();
		String indicator = null;
		try {
			ScenarioCaseManager executeManager = ScenarioCaseManager.getInstance();
			CaseResultManager caseResultManager = CaseResultManager.getInstance();
			String participantPatientId = (caseExecution.getParticipantpatientId() == null) ? "" : caseExecution.getParticipantpatientId();
			Map<Object, Object> requestData = (HashMap<Object, Object>) executeManager.fetchRequestData(participant, caseExecution, scenarioId, caseId);
			String ipAddress = requestData.get("ipAddress").toString();
			String localHCID = requestData.get("localHCID").toString();
			Map<String, String> endpoints = (Map<String, String>) requestData.get("endpoints");
			List<String> remoteHCIDList = (List<String>) requestData.get("remoteHCID");
			String config = null;
			String version = caseExecution.getVersion();
			if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
				version = participant.getVersion();
			}
			if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")) {
				resultMsg.append("[Initiator Scenario] Query for Document ");
			} else {
				indicator = LabConstants.RESPONDER_IND_Y;
				resultMsg.append("[Responder Scenario] Query for Document ");
			}
			config = executeManager.getScenarioCaseConfig(scenarioId, caseId); // happy path
			//			if (uniquePatientId!=null && !"".equals(uniquePatientId)
			//					&& uniquePatientId.contains(LabConstants.TRIPPLE_CARET)
			//					&& uniquePatientId.contains(LabConstants.SPLITTER_AMPERSAND)) {
			//				//uniquePatientID format : CAN20700002^^^&2.16.840.1.113883.0.201&ISO
			//				participantPatientId = uniquePatientId.split(LabConstants.SPLITTER_TRIPPLE_CARET)[0];
			//				String pAA = uniquePatientId.split(LabConstants.SPLITTER_AMPERSAND)[1];
			//				remoteHCIDList.clear();
			//				remoteHCIDList.add(pAA);         	
			//			}
			// Check for valid config xml
			if (config == null) {
				log.warn("Scenario Case Configuration file is null");
			} else {
				log.info("Query For Document XML: " + config.toString());
				if (endpoints != null) {
					// Locate QD endpoint
					String endpoint = endpoints.get("QD");
					if (endpoint != null && !endpoint.equals("")) {
						ipAddress = endpoint;
					}
				}
				QueryDocument queryDocument = ServiceFactory.getInstance().getQDService(version);
				int caseResultId = -1;
				//Create a case result for the testcase execution
				caseResultId = caseResultManager.saveCaseResult(caseExecution, indicator, LabConstants.STATUS_INITIATED, null, LabConstants.SUBMISSION_IND_N, resultMsg +"Initiated.", LabConstants.MD_USER);
				if (caseResultId > 0) {
					log.info("Query for Document Result : Case result has been created.");
				} else {
					log.info("Query for Document Result : Case result has not created.");
				}

				if (caseExecution.getTestcase().getInitiatorInd().equalsIgnoreCase("Y")){ // || (uniquePatientId != null && !uniquePatientId.isEmpty())) {
					result = queryDocument.queryForDocuments(ipAddress, localHCID, remoteHCIDList, config, participantPatientId);
				} else {
					result = queryDocument.queryForDocuments(ipAddress, localHCID, remoteHCIDList, config);
				}
				qd_result = updateCaseResultQD(result, caseExecution,remoteHCIDList, participantPatientId, caseResultId);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage()); 
			throw new MDRuntimeException(ex);
		}
		log.info("TestCaseExecutionHelperQD - executeTestCaseQD : END");
		return qd_result;
	} 

	/**
	 * @param result
	 * @param caseExecution
	 * @return String
	 * @throws Exception
	 */
	public String updateCaseResultQD(QDEntityResponseType result, Caseexecution caseExecution, List<String> remoteHCIDs, String participantPatientId, int caseResultId) {
		log.info("TestCaseExecutionHelperQD - updateCaseResultQD : BEGIN");
		CaseResultManager caseResultManager = CaseResultManager.getInstance();
		StringBuffer resultMsg = new StringBuffer();
		String status = LabConstants.STATUS_PROGRESS;
		try {
			if(participantPatientId == null || "".equals(participantPatientId)){
				participantPatientId = caseExecution.getParticipantpatientId();
			}
			List<DocumentInfoType> documents =  new ArrayList<DocumentInfoType>();
			if (result != null) {
				log.info("queryDocument.queryForDocuments() result is null");
				if(result.getAdhocQueryResp()!=null && result.getAdhocQueryResp().getRegObjectList()!=null){
					List<ExtrinObjectType> extrinsicObjectsList = result.getAdhocQueryResp().getRegObjectList().getExtrinObject();
					if (extrinsicObjectsList != null && extrinsicObjectsList.size() > 0) {					
						for(ExtrinObjectType extrinsicObjectType :extrinsicObjectsList){
							if(extrinsicObjectType.getDocumentInfo()!=null ){
								documents.add(extrinsicObjectType.getDocumentInfo());
							}
						}
						resultMsg.append(" Patient ID " + participantPatientId + " returned " + documents.size() + " documents!");
						for (DocumentInfoType document : documents) {
							resultMsg.append("<br>Document Id: " + document.getDocumentUniqueId());
						}
						log.info("Initiator scenario QD testcase " + resultMsg.toString());
					} else {
						resultMsg.append(" Patient ID " + participantPatientId + " returned zero(0) documents!");
						log.info(" Patient ID " + participantPatientId + " Query for Document returned zero(0) documents!");
					}
					status = LabConstants.STATUS_COMPLETED;
					log.info("This is a initiator scenario QD testcase >>>>>>>>>>>>>>" + resultMsg.toString());
				}else{
					status = LabConstants.STATUS_INCOMPLETE;
					resultMsg.append(" AdhocQuery Response not found.");
				}
			} else {
				// Create a case result and update the status as failed if a exception occurs in cross gateway communication.
				status = LabConstants.STATUS_INCOMPLETE;
				resultMsg.append(" The Test case failed due to error in cross gateway communication.");
			}
			//int caseResultId = caseResultManager.saveCaseResult(caseExecution, indicator, status, null, LabConstants.SUBMISSION_IND_N, resultMsg.toString());
			//Update a case result for the testcase execution
			Caseresult caseresult = caseResultManager.getCaseResult(caseResultId);
			if(caseresult != null && caseresult.getResult().equals(LabConstants.STATUS_INITIATED)){
				caseresult.setResult(status);
				caseresult.setMessage(resultMsg.toString());
				caseresult.setUpdatedAt(DateUtil.getTodayDateTime());
				caseresult.setUpdatedBy(LabConstants.MD_USER);
				caseResultManager.update(caseresult);
			}
			if (caseResultId > 0 && caseresult.getResult().equals(LabConstants.STATUS_COMPLETED)) {
				saveResultDocument(documents, caseResultManager, caseResultId, remoteHCIDs.get(0));
				log.info("Query Result : Case result created and has been updated.");

			} else {
				log.info("Query Result :Case result has not been created.");
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new MDRuntimeException(ex);
		}
		log.info("TestCaseExecutionHelperQD - updateCaseResultQD : END");
		return resultMsg.toString();
	}

	public void saveResultDocument(List<DocumentInfoType> documents, CaseResultManager caseResultManager, int caseResultId, String remoteHCID) {
		log.info("TestCaseExecutionHelperQD - saveResultDocument : BEGIN");
		int docSize = LabConstants.INVALID_SIZE;
		int docResultId = -1;
		try {
			Caseresult caseResult = caseResultManager.getCaseResult(caseResultId);
			if (documents != null && documents.size() > 0) {
				for(DocumentInfoType eachDocument :  documents ) {
					String documentUniqueId = eachDocument.getDocumentUniqueId();
					String repositoryUniqueId = eachDocument.getRepositoryUniqueId();
					String hash = eachDocument.getHash();
					docSize = extractNumValue(eachDocument.getSize(),docSize);
					//String patientId = eachDocument.getPatientId();
					log.info("The document Id is >>>>>>>" + documentUniqueId);
					log.info("The document repo Id is >>>>>>>" + repositoryUniqueId);
					log.info("The document size is >>>>>>>" + docSize);
					docResultId = caseResultManager.saveResultDocument(caseResult, documentUniqueId, repositoryUniqueId, remoteHCID, null, null, hash, docSize, LabConstants.LAB_DOCQUERY, LabConstants.MD_USER);
					if (docResultId < 0) {
						log.error("Error: TestCaseExecutionHelperQD - saveResultDocument - ResultDocument not saved!!");
					}
				}
			}
			log.info("TestCaseExecutionHelperQD - saveResultDocument : END");
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new MDRuntimeException(ex);
		}
	}

	private int extractNumValue(String s, int defInt) {
		int intVal = 0;
		try {
			intVal = Integer.parseInt(s);
		} catch (Exception ex) {
			intVal = defInt;
		}
		return intVal;
	}
}
