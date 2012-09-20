package net.aegis.lab.web.action.participant;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ScenarioCaseManager;
import net.aegis.lab.manager.TestHarnessriManager;
import net.aegis.lab.parser.message.MessageHandler;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.ri.ws.scenario.ServiceProcessor;
import net.aegis.tp.ws.ProcessTestCaseRequestType;
import net.aegis.tp.ws.ProcessTestCaseResponseType;

public class ExecuteTestCase extends BaseAction {

	public static final String QDR_KEY = LabConstants.DOCQUERY + LabConstants.SPLITTER_CARET;
	private static final long serialVersionUID = 1L;
	private Participant participant;
	private Integer selectedScenarioExecutionId;
	private Integer selectedScenarioId;
	private Integer selectedCaseId;
	private String scenarioLevelExecution;
	private Scenarioexecution testScenario;
	private Scenarioexecution submittedTestScenario;
	private String altScenarioConfigId;
	private String responderPatientId;
	private String targetVersion;
	// TODO : this may be removed once we add more than one test case in conformance

	@Override
	public String execute() throws Exception {

		log.info("ExecuteTestCase.execute() - INFO");

		boolean isConformance = true;

		try {
			if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
				setParticipant(this.getProfile().getParticipants().get(0));
				log.warn("ExecuteTestCase.execute() - participant is " + participant.getParticipantName());
				ApplicatiopropertiesManager apppropManager = ApplicatiopropertiesManager.getInstance();
				Scenarioexecution sessionTestScenario = (Scenarioexecution) this.getSession().getAttribute("testScenario");
				// update the scenarioexecution with user submitted values
				prepareRDRConformanceData(sessionTestScenario);
				this.setTestScenario(sessionTestScenario);
				if (selectedScenarioId != null && testScenario != null) {
					ScenarioCaseManager executeManager = ScenarioCaseManager.getInstance();
					//                    String initiatorName = "Participant";
					//                    String responderName = "Lab";

					ProcessTestCaseResponseType result = new ProcessTestCaseResponseType();
					ServiceProcessor serviceProcessor = new ServiceProcessor();
					String serviceEndpointURL = null;
					List<Applicationproperties> appPropList = apppropManager.findByKey(LabConstants.TEST_CASE_PROCESSOR_ENDPOINT_URL_Key);
					if (appPropList != null && appPropList.size() > 0) {
						if (appPropList.get(0).getPropertykey().equalsIgnoreCase(LabConstants.TEST_CASE_PROCESSOR_ENDPOINT_URL_Key)) {
							serviceEndpointURL = appPropList.get(0).getPropertyvalue();
						}
					}
					User user = (User) this.getProfile();
					isConformance = (user != null && LabConstants.LabType.CONFORMANCE == user.getLabType());
					//      boolean isTestCaseExecution = (scenarioLevelExecution.equals("N") && (user!=null && LabConstants.LabType.CONFORMANCE==user.getLabType()));

					ProcessTestCaseRequestType requestType = new ProcessTestCaseRequestType();
//					if(targetVersion == null || "".equals(targetVersion)){
//						targetVersion = LabConstants.Connect_Version_248;
//					}
					
					if(testScenario.getScenario().isInitiator()){
						targetVersion = participant.getVersion();
					}else{
						targetVersion = testScenario.getCaseexecutions().get(0).getVersion();						
					}
					requestType.setTargetVersion(targetVersion);
					requestType.setCandidateId(String.valueOf(participant.getParticipantId()));
					requestType.setScenarioExecutionId(String.valueOf(selectedScenarioExecutionId));
					requestType.setScenarioLevelExecution(scenarioLevelExecution);
					requestType.setLabType(user.getLabType().toString());                    

					if (scenarioLevelExecution != null && (scenarioLevelExecution.equals("Y"))) { // || (isTestCaseExecution))) {
						// Call the TestCaseProcessor Web Service Client                    	
						result = serviceProcessor.executeRequest(requestType, serviceEndpointURL);
						if (result != null && result.getStatus() != null && result.getStatus().equalsIgnoreCase(LabConstants.RESPONSE_SUCCESS)) {
							addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_SUCESS_MSG);
						} else {
							addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_FAILURE_MSG);
						}
					} else {
						Caseexecution findCase = findTestCase(testScenario);
						if (findCase != null) {
							String messageType = findCase.getMessageType();
							//                            if (findCase.getTestcase() != null && findCase.getTestcase().getResponderInd().equalsIgnoreCase("Y")) {
							//                                initiatorName = "Lab";
							//                                responderName = "Participant";
							//                            }
							//===== ATACH DOCUMENT =====
							if (messageType.equalsIgnoreCase("AD")) {
							} //===== SUPPLEMENTAL =====
							/*else if (messageType.equalsIgnoreCase("SP")) {
                                String resultMsg = executeManager.executeSupplemental(participant, selectedScenarioId.intValue(), selectedCaseId.intValue(), findCase.getCaseExecutionId());
                                if (resultMsg != null && resultMsg.equals(Caseresult.RESULT_PASS)) {
                                    addActionMessage("Supplemental discovery successful!");
                                } else if (resultMsg != null && resultMsg.equals(Caseresult.RESULT_PROGRESS)) {
                                    addActionMessage("Supplemental discovery returned PROGRESS result!");
                                } else {
                                    addActionMessage("Supplemental discovery returned zero(0) matches!");
                                }
                            }*/ else if (messageType.equalsIgnoreCase("PD") || messageType.equalsIgnoreCase("QD") || messageType.equalsIgnoreCase("RD")) {
                            	requestType.setCaseExecutionId(String.valueOf(findCase.getCaseExecutionId()));
                            /*	if(isConformance && findCase.getTestcase().isResponder()){
                            		if(messageType.equalsIgnoreCase("QD")){
                            			if (responderPatientId!=null && !"".equals(responderPatientId)
                            					&& responderPatientId.contains(LabConstants.TRIPPLE_CARET)
                            					&& responderPatientId.contains(LabConstants.SPLITTER_AMPERSAND)) {
                            				
                            				prepareForConformanceQDRExecution(participant, findCase, responderPatientId);            	
                            			}
                            			requestType.setUniquePatientId(responderPatientId);
                            		}else if(messageType.equalsIgnoreCase("RD")){
                            			List<Document> documentList = new ArrayList<Document>();
                            			Document docObj = null;
                            			if(findCase.getRdrResultdocuments() != null && findCase.getRdrResultdocuments().size() > 0){
                            				for(Resultdocument rdrDocs : findCase.getRdrResultdocuments()){
                            					docObj = new Document();
                            					docObj.setRepoID(rdrDocs.getRepositoryId());
                            					docObj.setDocumentID(rdrDocs.getDocumentUniqueId());
                            					log.debug("Repository ID : "+ rdrDocs.getRepositoryId());
                            					log.debug("DocumentUniqueId : " + rdrDocs.getDocumentUniqueId());
                            					documentList.add(docObj);
                            				}
                            			}
                            			requestType.getDocumentList().addAll(documentList);
                            		}
                            	}*/
                            	result = serviceProcessor.executeRequest(requestType, serviceEndpointURL);
                            	if (result != null && result.getStatus() != null && result.getStatus().equalsIgnoreCase(LabConstants.RESPONSE_SUCCESS)) {
                            		addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_SUCESS_MSG);
                            	} else {
                            		addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_FAILURE_MSG);
                            	}
                            } else {
                            	addActionMessage("Selected Case Not Valid!");
                            }
						} else {
							addActionMessage("Selected Case Not Valid!");
						}
					}
					//    addActionMessage((isTestCaseExecution?"Test case":"Scenario")+" execution is complete.");
					Scenarioexecution scenarioExecution = ParticipantManager.getInstance().returnScenarioExecution(selectedScenarioExecutionId);
					updateScenarioExecutionWithFormSubmittedScenarioExecution(scenarioExecution, submittedTestScenario);
					this.setTestScenario(scenarioExecution);
					this.getSession().setAttribute("testScenario", this.getTestScenario());
				} else {
					addActionMessage("Selected Scenario Not Valid!");
				}
			}
		} catch (Throwable e) {
			log.error("Throwable", e);
			return this.processException(e);
		}
		 // return (!isConformance) ? SUCCESS : "confsuccess";
		return SUCCESS;
	}

	//    private void addAllActionMessages(List<String> actionMessagesList) {
	//        if ((actionMessagesList != null) && (actionMessagesList.size() > 0)) {
	//            for (int i = 0; i < actionMessagesList.size(); i++) {
	//                addActionMessage(actionMessagesList.get(i));
	//            }
	//        }
	//    }
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Integer getSelectedScenarioExecutionId() {
		return selectedScenarioExecutionId;
	}

	public void setSelectedScenarioExecutionId(Integer selectedScenarioExecutionId) {
		this.selectedScenarioExecutionId = selectedScenarioExecutionId;
	}

	public Integer getSelectedCaseId() {
		return selectedCaseId;
	}

	public void setSelectedCaseId(Integer selectedCaseId) {
		this.selectedCaseId = selectedCaseId;
	}

	public Integer getSelectedScenarioId() {
		return selectedScenarioId;
	}

	public void setSelectedScenarioId(Integer selectedScenarioId) {
		this.selectedScenarioId = selectedScenarioId;
	}

	public Scenarioexecution getTestScenario() {
		return testScenario;
	}

	public void setTestScenario(Scenarioexecution testScenario) {
		this.testScenario = testScenario;
	}

	public Scenarioexecution getSubmittedTestScenario() {
		return submittedTestScenario;
	}

	public void setSubmittedTestScenario(Scenarioexecution submittedTestScenario) {
		this.submittedTestScenario = submittedTestScenario;
	}


	public String getScenarioLevelExecution() {
		return scenarioLevelExecution;
	}

	public void setScenarioLevelExecution(String scenarioLevelExecution) {
		this.scenarioLevelExecution = scenarioLevelExecution;
	}

	private String filterDocList(String[] classCodeList, Map<String, String> docsAndRepIds) {
		String filteredDocList = "";
		if (classCodeList != null) {
			if (docsAndRepIds != null) {
				Set es = docsAndRepIds.entrySet();
				if (es != null) {
					Iterator it = es.iterator();
					int cx = 0;
					if (classCodeList.length == es.size()) {
						while (it.hasNext()) {
							Map.Entry m = (Map.Entry) it.next();
							if (!MessageHandler.CLASSCODE_PRIVACYPOLICY.equals(classCodeList[cx]) && !MessageHandler.CLASSCODE_PRIVACYPOLICY_ACK.equals(classCodeList[cx])) {
								filteredDocList += (String) m.getKey() + ((cx < es.size()) ? LabConstants.SPLITTER_DOUBLE_TILDE : "");
							}
							cx++;
						}
					}
				}
			}
		}
		return filteredDocList;
	}

	private Caseexecution findTestCase(Scenarioexecution testScenario) {
		Caseexecution returnCase = null;
		if (selectedCaseId > 0) {
			log.info("ExecuteTestCase.findTestCase() - selectedCaseId > 0");
			for (Caseexecution findCase : testScenario.getCaseexecutions()) {
				if (findCase != null && findCase.getTestcase().getCaseId().intValue() == selectedCaseId) {
					log.warn("ExecuteTestCase.findTestCase() - returnCase eq findCase");
					returnCase = findCase;
					break;
				}
			}
		}
		return returnCase;
	}

	/*
	 *
	 */
	private void prepareRDRConformanceData(Scenarioexecution sessionTestScenario) {
		Scenarioexecution formSubmittedTestScenario = this.getTestScenario();
		this.setSubmittedTestScenario(formSubmittedTestScenario);
		updateScenarioExecutionWithFormSubmittedScenarioExecution(sessionTestScenario, formSubmittedTestScenario);

	}

	private void updateScenarioExecutionWithFormSubmittedScenarioExecution(Scenarioexecution sessionTestScenario, Scenarioexecution formSubmittedTestScenario) {
		User user = (User) this.getProfile();
		if (formSubmittedTestScenario != null && LabConstants.LabType.CONFORMANCE == user.getLabType()) {
			List<Caseexecution> submittedCaseexecutionList = formSubmittedTestScenario.getCaseexecutions();
			int submittedCaseexecutionSize = -1;
			if (submittedCaseexecutionList != null) {
				submittedCaseexecutionSize = submittedCaseexecutionList.size();
			}
			List<Caseexecution> sessionCaseexecutionList = sessionTestScenario.getCaseexecutions();
			if (sessionCaseexecutionList != null && sessionCaseexecutionList.size() > 0) {
				for (int sessionCaseExeIndex = 0; sessionCaseExeIndex < sessionCaseexecutionList.size(); sessionCaseExeIndex++) {
					Caseexecution eachSessionCaseexecution = sessionCaseexecutionList.get(sessionCaseExeIndex);

					if (eachSessionCaseexecution.getMessageType().equals(LabConstants.LAB_DOCRETRIEVE) && sessionCaseExeIndex <= submittedCaseexecutionSize) {
						Caseexecution submittedCaseexecution = submittedCaseexecutionList.get(sessionCaseExeIndex);
						List<Resultdocument> rdrResultdocuments = submittedCaseexecution.getRdrResultdocuments();
						eachSessionCaseexecution.setRdrResultdocuments(rdrResultdocuments);
					}
				}
			}
		}
	}
	
	public void prepareForConformanceQDRExecution(Participant participant, Caseexecution caseExecution, String uniquePatientId){
		boolean testCasePrecursor = false;
		try {
			//uniquePatientID format : CAN20700002^^^&2.16.840.1.113883.0.201&ISO
			String patientId = uniquePatientId.split(LabConstants.SPLITTER_TRIPPLE_CARET)[0];
			String pAA = uniquePatientId.split(LabConstants.SPLITTER_AMPERSAND)[1];
			//    	remoteHCIDList.clear();
			//    	remoteHCIDList.add(pAA);
			Map<String, String> riHCID = TestHarnessriManager.getInstance().getTestHarnessriCommIds();
			CaseResultManager caseResultManager = CaseResultManager.getInstance();
			String participatingRIs = caseExecution.getParticipatingRIs();
			if(participatingRIs.contains(LabConstants.SPLITTER_CARET)){
				for(String ri : participatingRIs.split(LabConstants.SPLITTER)){
					testCasePrecursor = caseResultManager.addCaseResultPatientId(pAA, patientId, riHCID.get(ri));
				}
			}else{
				testCasePrecursor = caseResultManager.addCaseResultPatientId(pAA, patientId, riHCID.get(participatingRIs));
			}
			
			if (testCasePrecursor) {
               // String responderName = "Candidate" + LabConstants.SPLITTER_COLON + patientId + LabConstants.SPLITTER_COLON + pAA;
				ApplicatiopropertiesManager auditProcessor = ApplicatiopropertiesManager.getInstance();
                if (caseExecution.getParticipantpatientId().equalsIgnoreCase(patientId)) {
                	auditProcessor.addEvent(participant.getParticipantId()+LabConstants.SPLITTER_CARET+QDR_KEY + caseExecution.getDependentCaseId(), uniquePatientId, LabConstants.PATIENTDISCOVERY);
                } else {
                	auditProcessor.addEvent(participant.getParticipantId()+LabConstants.SPLITTER_CARET+QDR_KEY + caseExecution.getDependentCaseId(), uniquePatientId, null);
                }
            }
		} catch (Exception ex) {
			log.error(ex.getMessage());
			//return this.processException(ex.getMessage());
		}
	}
	
	public String getAltScenarioConfigId() {
		return altScenarioConfigId;
	}

	public void setAltScenarioConfigId(String altScenarioConfigId) {
		this.altScenarioConfigId = altScenarioConfigId;
	}

	public String getResponderPatientId() {
		return responderPatientId;
	}

	public void setResponderPatientId(String responderPatientId) {
		this.responderPatientId = responderPatientId;
	}

	public ExecuteTestCase() {
		this.participant = new Participant();
	}

	public ExecuteTestCase(Participant participant) {
		this.participant = participant;
	}

	public String getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(String targetVersion) {
		this.targetVersion = targetVersion;
	}
}
