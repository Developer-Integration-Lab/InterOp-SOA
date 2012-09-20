package net.aegis.lab.participant.web.action.test;

import java.util.List;

import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.participant.web.action.BaseAction;
import net.aegis.lab.util.LabConstants;
import net.aegis.ri.ws.scenario.ServiceProcessor;
import net.aegis.tp.ws.ProcessTestCaseRequestType;
import net.aegis.tp.ws.ProcessTestCaseResponseType;

public class ExecuteTestCase extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Participant participant;
    private Integer selectedScenarioExecutionId;
    private Integer selectedScenarioId;
    private Integer selectedCaseId;
    private Integer selectedAltScenarioCaseId;
    private String scenarioLevelExecution;
    private Scenarioexecution testScenario;
    private String targetVersion;

    public ExecuteTestCase() {
        this.participant = new Participant();
    }

    public ExecuteTestCase(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String execute() throws Exception {
        log.info("ExecuteTestCase.execute() - INFO");
        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("ExecuteTestCase.execute() - participant is " + participant.getParticipantName());

                this.setTestScenario((Scenarioexecution) this.getSession().getAttribute("testScenario"));
                ApplicatiopropertiesManager apppropManager = ApplicatiopropertiesManager.getInstance();
                if (selectedScenarioId != null && selectedCaseId != null && testScenario != null) {
                    ProcessTestCaseResponseType result = new ProcessTestCaseResponseType();
                    ServiceProcessor serviceProcessor = new ServiceProcessor();
                    String serviceEndpointURL = null;
                    List<Applicationproperties> appPropList = apppropManager.findByKey(LabConstants.TEST_CASE_PROCESSOR_ENDPOINT_URL_Key);
                    if (appPropList != null && appPropList.size() > 0) {
                        if (appPropList.get(0).getPropertykey().equalsIgnoreCase(LabConstants.TEST_CASE_PROCESSOR_ENDPOINT_URL_Key)) {
                            serviceEndpointURL = appPropList.get(0).getPropertyvalue();
                        }
                    }
                    ProcessTestCaseRequestType requestType = new ProcessTestCaseRequestType();
//                    int labAccessFilter = testScenario.getScenario().getServiceset().getLabAccessFilter();
//                    String labType = LabConstants.LabType.CONFORMANCE.getType(labAccessFilter);
                    if(testScenario.getScenario().isInitiator()){
						targetVersion = participant.getVersion();
					}else{
						targetVersion = testScenario.getCaseexecutions().get(0).getVersion();						
					}
                    requestType.setTargetVersion(targetVersion);
                    requestType.setCandidateId(String.valueOf(participant.getParticipantId()));
                    requestType.setScenarioExecutionId(String.valueOf(selectedScenarioExecutionId));
                    requestType.setScenarioLevelExecution(scenarioLevelExecution);
                    requestType.setLabType(LabConstants.LabType.LAB.toString());              
                    if (scenarioLevelExecution != null && scenarioLevelExecution.equalsIgnoreCase("Y")) {
                        // Call the TestCaseProcessor Web Service Client
                        result = serviceProcessor.executeRequest(requestType, serviceEndpointURL);
                        if (result != null && result.getStatus() != null && result.getStatus().equalsIgnoreCase(LabConstants.RESPONSE_SUCCESS)) {
                            addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_SUCESS_MSG);
                        } else {
                            addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_FAILURE_MSG);
                        }
                    } else {
                        Caseexecution findCase = this.findTestCase(testScenario);

                        if (findCase != null) {
                            String messageType = findCase.getMessageType();
                            /*
                             * ===== ATACH DOCUMENT =====
                             */
                            if (messageType.equalsIgnoreCase("AD")) {
                                // SHOULD NEVER GET THIS TYPE IN THE PARTICIPANT APPLICATION
                            } /*
                             * ===== SUPPLEMENTAL =====
                             */ /*else if (messageType.equalsIgnoreCase("SP")) {
                                String resultMessage = executeManager.executeSupplemental(participant, selectedScenarioId.intValue(), selectedCaseId.intValue(), findCase.getCaseExecutionId());
                                if (resultMessage != null && resultMessage.equalsIgnoreCase(Caseresult.RESULT_PASS)) {
                                    addActionMessage("Supplemental discovery successful!");
                                } else if (resultMessage != null && resultMessage.equalsIgnoreCase(Caseresult.RESULT_PROGRESS)) {
                                    addActionMessage("Supplemental discovery returned PROGRESS result!");
                                } else {
                                    addActionMessage("Supplemental discovery returned zero(0) matches!");
                                }
                            } */else if (messageType.equalsIgnoreCase("PD") || messageType.equalsIgnoreCase("QD") || messageType.equalsIgnoreCase("RD")) {
                            	requestType.setCaseExecutionId(String.valueOf(findCase.getCaseExecutionId()));
                            	result = serviceProcessor.executeRequest(requestType, serviceEndpointURL);
                                if (result != null && result.getStatus() != null && result.getStatus().equalsIgnoreCase(LabConstants.RESPONSE_SUCCESS)) {
                                    addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_SUCESS_MSG);
                                } else {
                                    addActionMessage(LabConstants.JMS_REQUEST_SUBMISSION_FAILURE_MSG);
                                }
                            } else {
                                addActionMessage("Invalid Message Type!");
                            }
                        } else {
                            addActionMessage("Selected Case Not Valid!");
                        }
                    }
                } else {
                    addActionMessage("Selected Scenario and Case Not Valid!");
                }
            }
        } catch (Exception e) {
            log.error("Exception", e);
            addActionError(e.getMessage());
        }

        return SUCCESS;
    }

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

    public Integer getSelectedAltScenarioCaseId() {
        return selectedAltScenarioCaseId;
    }

    public void setSelectedAltScenarioCaseId(Integer selectedAltScenarioCaseId) {
        this.selectedAltScenarioCaseId = selectedAltScenarioCaseId;
    }

    public String getScenarioLevelExecution() {
        return scenarioLevelExecution;
    }

    public void setScenarioLevelExecution(String scenarioLevelExecution) {
        this.scenarioLevelExecution = scenarioLevelExecution;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    private Caseexecution findTestCase(Scenarioexecution testScenario) {
        Caseexecution returnCase = null;

        if (selectedCaseId > 0) {
            log.warn("ExecuteTestCase.findTestCase() - selectedCaseId > 0");
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

//    private void addAllActionMessages(List<String> actionMessagesList) {
//        if ((actionMessagesList != null) && (actionMessagesList.size() > 0)) {
//            for (int i = 0; i < actionMessagesList.size(); i++) {
//                addActionMessage(actionMessagesList.get(i));
//            }
//        }
//    }
    public class RetrievedDocument {

        private String documentUniqueId;
        private byte[] documentText;

        public byte[] getDocumentText() {
            return documentText;
        }

        public void setDocumentText(byte[] documentText) {
            this.documentText = documentText;
        }

        public String getDocumentUniqueId() {
            return documentUniqueId;
        }

        public void setDocumentUniqueId(String documentUniqueId) {
            this.documentUniqueId = documentUniqueId;
        }
    }
}
