package net.aegis.lab.web.action.participant;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dto.TMWindowMsgsDto;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ScenarioCaseManager;
import net.aegis.lab.manager.TestcaseManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.StringUtils;
import net.aegis.lab.web.action.BaseAction;

import org.apache.commons.lang.Validate;

public class ActiveScenarioResults extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Participant participant;
    private int selectedScenarioExecutionId;
    private int caseExecutionId;
    private Scenarioexecution testScenario;
    public String resultsAvailable;
    public Timestamp startWindowTime;
    public Timestamp endWindowTime;
    public String timerWindowKey = "test.case.execution.timer.window.milli";
    public List<TMWindowMsgsDto> messagesbtwTMWindow = null;
    public String delay;
    public String caseName;
    public Map<Integer, String> testCaseNameMap = new HashMap<Integer, String>();
    public String responderPatientId;
    public boolean isCustomizedResponderPatientId;
    // TODO : this may be removed once we add more than one test case in conformance
    private Caseresult depedentCaseresult;

    private String showRefreshWindowMsg;


    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        log.info("ActiveScenarioResults.execute() - scenarioExecutionId=" + selectedScenarioExecutionId);
        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.info("ActiveScenarioResults.execute() - participant is " + participant.getParticipantName());
            }
            this.setTestScenario(ParticipantManager.getInstance().returnScenarioExecution(selectedScenarioExecutionId));
            this.prepareDepedentTestcaseExecution();
            this.getSession().setAttribute("testScenario", this.getTestScenario());

            //the value should be read from the application properties table
            this.setDelay(ApplicatiopropertiesManager.getInstance().getPropertyvalueByKey(timerWindowKey).trim());

            List<Testcase> testcaseList = TestcaseManager.getInstance().findAllTestcases();

            for (Testcase tcaseList : testcaseList) {
                testCaseNameMap.put(tcaseList.getCaseId(), tcaseList.getName());
            }

        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
        return SUCCESS;
    }

    /**
     * This method clears the case result
     * @return
     * @throws Exception
     */
    public String clearCaseResult() throws Exception {
        log.info("ActiveScenarioResults.clearCaseResult() - selectedScenarioExecutionId=" + selectedScenarioExecutionId + " caseExecutionId=" + caseExecutionId);
        try {
        	if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.info("ActiveScenarioResults.execute() - participant is " + participant.getParticipantName());
            }
            Validate.notNull(selectedScenarioExecutionId);
            Validate.notNull(caseExecutionId);
            CaseResultManager caseResultManager = CaseResultManager.getInstance();
            Caseresult caseResult = caseResultManager.findByExecIdAndMaxResultId(caseExecutionId);
            if (caseResult != null) {
                log.info("Clearing case result='" + caseResult.getResultId() + "'");
                if (caseResult.getCaseexecution().isClearable()) {
                    caseResult.setResult(LabConstants.STATUS_CLEARED);
                    caseResult.setUpdatedAt(DateUtil.getTodayDateTime());
                    caseResult.setUpdatedBy(participant.getParticipantName());
                    caseResultManager.update(caseResult);
                } else {
                    log.error("caseExecutionId '" + caseExecutionId + "' is not clearable");
                }
            } else {
                log.error("caseExecutionId '" + caseExecutionId + "' could not be found");
            }
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Exception", e);
            return this.processException(e);
        }
    }

   /*  This the method triggered when the user clicks startTimer button in screen and the corresponding action called
     * This method updates the start and end time in caseexecution table for that particular timed window.
     * @Author Jyoti.devarakonda
     
    public String startTimedWindow() throws Exception {
        log.info("ActiveScenarioResults.startTimedWindow() Entered....");
        Caseexecution caseExecs = null;
        messagesbtwTMWindow = new ArrayList<TMWindowMsgsDto>();

        try {
            this.setDelay(ApplicatiopropertiesManager.getInstance().getPropertyvalueByKey(timerWindowKey).trim());
            log.info("The " + this.getDelay());
            if (this.getProfile() != null) {
                if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                    setParticipant(this.getProfile().getParticipants().get(0));
                    log.info("ActiveScenarioResults.startTimedWindow() - participant is " + participant.getParticipantName());
                }
            } else {
                log.info("ActiveScenarioResults.startTimedWindow()......No Participant profile found");
            }
            log.info("ActiveScenarioResults.startTimedWindow()........testScenario" + testScenario);
            String caseExecId = this.getRequest().getParameter("selectedCaseExecutionId");
            this.setCaseExecutionId(Integer.parseInt(caseExecId));
            this.setCaseName(this.getRequest().getParameter("selectedCaseName"));
            if (caseExecId != null) {
                caseExecs = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(Integer.parseInt(caseExecId));
                //  this.updateCaseExecutionTimer(caseExecs);
                //  log.info("ActiveScenarioResults.startTimedWindow()........The CaseExecution is " + caseExecs);
            } else {
                log.info("ActiveScenarioResults.startTimedWindow()......The Case Execution Id cannot be null");
            }
            // Check if the result is Pass or Fail
            CaseResultManager caseResultManager = CaseResultManager.getInstance();
            Caseresult caseResult = caseResultManager.findByExecIdAndMaxResultId(caseExecutionId);
            if (caseResult == null) {
                this.setResultsAvailable("true");
                this.setShowRefreshWindowMsg("Please refresh the page to view updated Test results.");
            } else if (caseResult.getResult().equalsIgnoreCase(LabConstants.STATUS_FAIL) || (caseResult.getResult().equalsIgnoreCase(LabConstants.STATUS_PROGRESS_PENDING)) || caseResult.getResult().startsWith(LabConstants.STATUS_PROGRESS)) {
                this.setResultsAvailable("true");
                this.setShowRefreshWindowMsg("Please refresh the page to view updated Test results.");
            } else {
                this.setResultsAvailable("false");
            }

            // Get the messages received during the timer window to display on the screen
           // this.setMessagesbtwTMWindow(AuditLogManager.getInstance().getAuditRepositoryList(caseExecs));

            //duplicate remove later
            this.setCaseExecutionId(caseExecutionId);
            this.setCaseName(caseName);
            this.setTestScenario(ParticipantManager.getInstance().returnScenarioExecution(selectedScenarioExecutionId));
            log.info("ActiveScenarioResults.startTimedWindow() Exit....");
        } catch (Throwable ex) {
            log.error("Throwable", ex);
            return this.processException(ex);
        }
        return SUCCESS;
    }*/

   /*  This the method triggered when the user clicks startTimer button in screen and after the timed window gets expired
     * and gets the failure messages in a pop-up
     * This method gets the messages from Audit repository table for that particular timed window.
     * @Author Jyoti.devarakonda
     
    public String popTimedWindowResults() throws Exception {
        log.info("ActiveScenarioResults.popTimedWindowResults()........... Entered");
        Caseexecution caseExecs = null;
        try {
            if (this.getProfile() != null) {
                if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                    setParticipant(this.getProfile().getParticipants().get(0));
                    log.info("ActiveScenarioResults.popTimedWindowResults() - participant is " + participant.getParticipantName());
                }
            } else {
                log.info("ActiveScenarioResults.popTimedWindowResults()......No Participant profile found");
            }
            // testScenario from session
            if (this.getSession().getAttribute("testScenario") != null) {
                this.setTestScenario((Scenarioexecution) this.getSession().getAttribute("testScenario"));
            } else {
                log.info("ActiveScenarioResults.startTimedWindow()......No TestScenario Found");
            }

            String caseExecId = this.getRequest().getParameter("selectedCaseExecutionId");
            log.info("ActiveScenarioResults.popTimedWindowResults()......The Case Execution Id" + caseExecId);
            this.setCaseName(this.getRequest().getParameter("selectedCaseName"));
            if (caseExecId != null) {
                caseExecs = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(Integer.parseInt(caseExecId));
            } else {
                log.info("ActiveScenarioResults.popTimedWindowResults()......The Case Execution Id cannot be null");
            }
            //Set start and end timer to display on the screen.
            if (caseExecs != null) {
                if (caseExecs.getStartTimer() != null && caseExecs.getEndTimer() != null) {
                    this.setStartWindowTime(caseExecs.getStartTimer());
                    this.setEndWindowTime(caseExecs.getEndTimer());
                }
               
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
        return SUCCESS;
    }*/

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Scenarioexecution getTestScenario() {
        return testScenario;
    }

    public void setTestScenario(Scenarioexecution testScenario) {
        this.testScenario = testScenario;
    }

    public int getSelectedScenarioExecutionId() {
        return selectedScenarioExecutionId;
    }

    public void setSelectedScenarioExecutionId(int selectedScenarioExecutionId) {
        this.selectedScenarioExecutionId = selectedScenarioExecutionId;
    }

    public int getScenarioExecutionId() {
        return selectedScenarioExecutionId;
    }

    public void setScenarioExecutionId(int selectedScenarioExecutionId) {
        this.selectedScenarioExecutionId = selectedScenarioExecutionId;
    }

    public int getCaseExecutionId() {
        return caseExecutionId;
    }

    public void setCaseExecutionId(int caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }

    public String getResultsAvailable() {
        return resultsAvailable;
    }

    public void setResultsAvailable(String resultsAvailable) {
        this.resultsAvailable = resultsAvailable;
    }

    public void validateShowTimerResults() {
    }

    public List<TMWindowMsgsDto> getMessagesbtwTMWindow() {
        return messagesbtwTMWindow;
    }

    public void setMessagesbtwTMWindow(List<TMWindowMsgsDto> messagesbtwTMWindow) {
        this.messagesbtwTMWindow = messagesbtwTMWindow;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * This method is used to update the caseexecution with starttimer and endtimer values.
     */
    /*public void updateCaseExecutionTimer(Caseexecution caseexecution) throws ServiceException {
    log.info("<<< Entering ActiveScenarioResults.updateCaseExecutionTimers() >>>" + caseexecution.getCaseExecutionId());
    try {
    log.info("APP prop TM value######################################" + this.getDelay() + delay);
    java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
    long startTime = timestamp.getTime() - Long.parseLong(delay);
    // long startTime =  timestamp.getTime();
    caseexecution.setStartTimer(new Timestamp(startTime));
    //long endTime = startTime + Long.parseLong(delay);
    // caseexecution.setEndTimer(new Timestamp(endTime));
    caseexecution.setEndTimer(timestamp);

    log.info("APP prop TM value###################################### caseexecution.getStartTimer() : " + caseexecution.getStartTimer());
    log.info("APP prop TM value###################################### caseexecution.getEndTimer() : " + caseexecution.getEndTimer());

    this.setStartWindowTime(new Timestamp(startTime).toString());
    this.setEndWindowTime(timestamp.toString());
    ParticipantCaseExecutionManager.getInstance().updateCaseexecution(caseexecution);
    } catch (Exception e) {
    throw new ServiceException(e);
    }
    log.info("<<< Exit ActiveScenarioResults.updateCaseExecutionTimers() >>>");
    }*/
    /**
     * This method is used to get the consolidated auditrepository list from RI's for the caseexecution
     * based on the timestamp values (starttimer and endtimer).
     */
    /*  public List<TMWindowMsgsDto> getAuditRepositoryList(Caseexecution caseexecution, String caseName) throws ServiceException, IOException {
    log.info("<<< Entering ActiveScenarioResults.getAuditRepositoryList() >>>" + caseexecution.getCaseExecutionId());
    String participatingRIs = caseexecution.getParticipatingRIs();
    Map<String, String> riList = TestHarnessriManager.getInstance().getTestHarnessriCommIds();
    AuditMessageType auditMessageType = null;
    List<TMWindowMsgsDto> auditTMWindowList = new ArrayList<TMWindowMsgsDto>();
    try {
    List<Auditrepository> auditRepoList = null;
    String[] RIs = participatingRIs.split(LabConstants.SPLITTER);
    for (int i = 0; i < RIs.length; i++) {
    if (LabConstants.RI1_ID == Integer.parseInt(RIs[i])) {
    auditRepoList = AuditLogManager.getInstance().getAuditRepositoryByTimeRange(LabConstants.RI1_ID, caseexecution.getEndTimer(), caseexecution.getStartTimer());
    } else if ((LabConstants.RI2_ID == Integer.parseInt(RIs[i]))) {
    auditRepoList = AuditLogManager.getInstance().getAuditRepositoryByTimeRange(LabConstants.RI2_ID, caseexecution.getEndTimer(), caseexecution.getStartTimer());
    } else if ((LabConstants.RI3_ID == Integer.parseInt(RIs[i]))) {
    auditRepoList = AuditLogManager.getInstance().getAuditRepositoryByTimeRange(LabConstants.RI3_ID, caseexecution.getEndTimer(), caseexecution.getStartTimer());
    } else {
    continue;
    }
    //Populate the messagesbtwTMWindow list with the
    //TMWindowMsgsDto object containing the message info retrieved from the RI's

    if (auditRepoList != null) {
    for (Auditrepository repolist : auditRepoList) {
    TMWindowMsgsDto msg = new TMWindowMsgsDto();
    msg.setTimestamp(repolist.getTimestamp());
    // msg.setService(caseName);
    auditMessageType = AuditMessageExtract.extractMessage(repolist);
    String messageType = AuditMessageUtil.getMessageType(auditMessageType);
    msg.setService(messageType);
    if (repolist.getCommunityId() != null && repolist.getCommunityId().equals(riList.get(RIs[i]))) {
    continue;
    }
    if (repolist.getMessageType() != null && repolist.getMessageType().equalsIgnoreCase(LabConstants.MESSAGE_TYPE_INBOUND)) {
    msg.setFromHCID(repolist.getCommunityId());
    msg.setToHCID(riList.get(RIs[i]));
    if (msg.getFromHCID() != null && msg.getFromHCID().equals(participant.getCommunityId())) {
    msg.setHcidFlag(true);
    } else {
    msg.setHcidFlag(false);
    }
    } else if (repolist.getMessageType() != null && repolist.getMessageType().equalsIgnoreCase(LabConstants.MESSAGE_TYPE_OUTBOUND)) {
    msg.setToHCID(repolist.getCommunityId());
    msg.setFromHCID(riList.get(RIs[i]));
    if (msg.getToHCID() != null && msg.getToHCID().equals(participant.getCommunityId())) {
    msg.setHcidFlag(true);
    } else {
    msg.setHcidFlag(false);
    }
    } else {
    continue;
    }

    msg.setDirection(repolist.getMessageType());
    msg.setTestHarnessId(Integer.parseInt(RIs[i]));
    msg.setAuditRepoId(repolist.getId());

    log.debug("**************************");
    log.debug("TMWindowMsgsDto  msg.getTimestamp() - " + msg.getTimestamp());
    log.debug("TMWindowMsgsDto msg.getService() - " + msg.getService());
    log.debug("TMWindowMsgsDto msg.getToHCID() - " + msg.getToHCID());
    log.debug("TMWindowMsgsDto msg.getFromHCID() - " + msg.getFromHCID());
    log.debug("TMWindowMsgsDto msg.getTestHarnessId() - " + msg.getTestHarnessId());
    log.debug("TMWindowMsgsDto msg.getAuditRepoId() - " + msg.getAuditRepoId());
    log.debug("**************************");
    auditTMWindowList.add(msg);
    }
    }
    }
    // sort consolidated list by timestamp
    Collections.sort(auditTMWindowList, new sortByTimeStamp());
    log.info("<<< Exit ActiveScenarioResults.getAuditRepositoryList() messages between timed window >>>");
    } catch (Exception e) {
    throw new ServiceException(e);
    }

    return auditTMWindowList;
    }*/
   /* public String showAuditExtensionMsg() throws Exception {
        Caseexecution caseExecs = null;
        if (this.getProfile() != null) {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                this.setDelay(ApplicatiopropertiesManager.getInstance().getPropertyvalueByKey(timerWindowKey).trim());
                setParticipant(this.getProfile().getParticipants().get(0));
                log.info("ActiveScenarioResults.showAuditExtensionMsg() - participant is " + participant.getParticipantName());
                // get the case execution id and case name request parameters
                String caseExecId = this.getRequest().getParameter("selectedCaseExecutionId");
                Scenarioexecution scenarioExec = (Scenarioexecution) this.getSession().getAttribute("testScenario");
                this.setTestScenario(ParticipantManager.getInstance().returnScenarioExecution(scenarioExec.getScenarioExecutionId()));

                this.setCaseExecutionId(Integer.parseInt(caseExecId));
                this.setCaseName(this.getRequest().getParameter("selectedCaseName"));

                if (caseExecId != null) {
                    caseExecs = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(Integer.parseInt(caseExecId));
                } else {
                    log.info("ActiveScenarioResults.popTimedWindowResults()......The Case Execution Id cannot be null");
                }
            }
        } else {
            log.info("ActiveScenarioResults.showAuditExtensionMsg()......No Participant profile found");
        }
       // this.setMessagesbtwTMWindow(AuditLogManager.getInstance().getAuditRepositoryList(caseExecs));
        return SUCCESS;
    }*/

    public Timestamp getEndWindowTime() {
        return endWindowTime;
    }

    public void setEndWindowTime(Timestamp endWindowTime) {
        this.endWindowTime = endWindowTime;
    }

    public Timestamp getStartWindowTime() {
        return startWindowTime;
    }

    public void setStartWindowTime(Timestamp startWindowTime) {
        this.startWindowTime = startWindowTime;
    }

    public Map<Integer, String> getDependentCaseNameMap() {
        return testCaseNameMap;
    }

    public void setDependentCaseNameMap(Map<Integer, String> dependentCaseNameMap) {
        this.testCaseNameMap = dependentCaseNameMap;
    }

    public String getResponderPatientId() {
        return responderPatientId;
    }

    public void setResponderPatientId(String responderPatientId) {
        this.responderPatientId = responderPatientId;
    }

    private void prepareDepedentTestcaseExecution() throws ServiceException {
        User user = (User) this.getProfile();
        if (LabConstants.LabType.CONFORMANCE == user.getLabType()) {
            Scenarioexecution scenarioexecution = this.getTestScenario();
            List<Caseexecution> caseExecutions = scenarioexecution.getCaseexecutions();
            if (caseExecutions != null && caseExecutions.size() > 0) {
                for (Caseexecution eachCaseExecution : caseExecutions) {
                    if(eachCaseExecution.getMessageType().equals(LabConstants.LAB_DOCQUERY)){
                        // prepare QDR depedent test case info
                        populateQDRDependentCaseDetailsForConformance(eachCaseExecution);
                      // prapare RDR depedent test case info
                    }else if(eachCaseExecution.getMessageType().equals(LabConstants.LAB_DOCRETRIEVE)){
                          populateRDRDependentCaseDetailsForConformance(eachCaseExecution);
                    }
                }
            }
            // ILT-439 fix :
        }else  if (LabConstants.LabType.LAB == user.getLabType()) {
        	Scenarioexecution scenarioexecution = this.getTestScenario();
            List<Caseexecution> caseExecutions = scenarioexecution.getCaseexecutions();
            if (caseExecutions != null && caseExecutions.size() > 0) {
                for (Caseexecution eachCaseExecution : caseExecutions) {
                	Caseresult dependentCaseResult = null;
                    Integer dependentCaseId = eachCaseExecution.getDependentCaseId();
                    Integer dependentScenarioId = eachCaseExecution.getDependentScenarioId();
                    
                    if (dependentScenarioId != null && dependentCaseId != null) {
                        Caseexecution dependentCaseexecution = ScenarioCaseManager.getInstance().findByParticipantForActiveScenarioCase(participant, dependentScenarioId, dependentCaseId);
                        if (dependentCaseexecution != null) {
                        	eachCaseExecution.setDepedentCaseexecution(dependentCaseexecution);
                            dependentCaseResult = CaseResultManager.getInstance().findByExecIdAndMaxResultId(dependentCaseexecution.getCaseExecutionId());
                            if (dependentCaseResult != null && dependentCaseResult.getResultId() != null) {
                                    eachCaseExecution.setDependentCaseExecuted(true);
                            } else {
                                eachCaseExecution.setDependentCaseExecuted(false);
                            }
                            
                            // if the test case is displaying based on successful execution of  dependent test case 
                            if(!(StringUtils.isNullOrEmpty(eachCaseExecution.getDisplayByDependentCaseStatus())) 
                            		&& eachCaseExecution.getDisplayByDependentCaseStatus().equalsIgnoreCase(LabConstants.YES_INDICATOR)){
                            	if (!eachCaseExecution.isDependentCaseExecuted() || !dependentCaseResult.getResult().equalsIgnoreCase(LabConstants.STATUS_PASS)) {
                            			eachCaseExecution.setCaseHiddenByDependentCaseStatus(true);
                            	}
                            }
                        }
                    }
                }
               // logic to hide scenario 
                boolean isAllCasesAreHidden = true; 
                for(Caseexecution eachCaseExecution  : caseExecutions){
                	isAllCasesAreHidden = isAllCasesAreHidden && eachCaseExecution.isCaseHiddenByDependentCaseStatus();
                	if(!isAllCasesAreHidden){
                		scenarioexecution.setScenarioHidden(false);
                		break;
                	}
                }
                if(isAllCasesAreHidden){
                	scenarioexecution.setScenarioHidden(true);
                }
            }
        }
    }

    private void populateQDRDependentCaseDetailsForConformance(Caseexecution eachCaseExecution) throws ServiceException {
    	String uniquePatientId;
    	// prepare QDR depedent test case info
    	if ("QDR-4.1b".equals(eachCaseExecution.getTestcase().getName())) {
    		List<Applicationproperties> apList = ApplicatiopropertiesManager.getInstance().findByKey(participant.getParticipantId() + LabConstants.SPLITTER_CARET + ExecuteTestCase.QDR_KEY + eachCaseExecution.getDependentCaseId().toString());
    		Applicationproperties ap = null;
    		if (apList != null && !apList.isEmpty()) {
    			ap = apList.get(0);
    			this.setResponderPatientId(ap.getPropertyvalue());
    			if (LabConstants.PATIENTDISCOVERY.equals(ap.getDescription())) {
    				eachCaseExecution.setDependentCaseExecuted(true);
    			} else {
    				eachCaseExecution.setDependentCaseExecuted(false);
    			}
    		}
    	}
    	Integer dependentCaseId = eachCaseExecution.getDependentCaseId();
    	Integer dependentScenarioId = eachCaseExecution.getDependentScenarioId();
    	Caseresult dependentCaseResult = null;
    	if (dependentScenarioId != null && dependentCaseId != null) {
    		Caseexecution dependentCaseexecution = ScenarioCaseManager.getInstance().findByParticipantForActiveScenarioCase(participant, dependentScenarioId, dependentCaseId);
    		if (dependentCaseexecution != null) {
    			dependentCaseResult = CaseResultManager.getInstance().findByExecIdAndMaxResultId(dependentCaseexecution.getCaseExecutionId());
    			if (dependentCaseResult != null && dependentCaseResult.getResultId() != null) {
    				eachCaseExecution.setDepedentCaseexecution(dependentCaseexecution);
    				log.debug("**************************");
    				log.debug("prepareDepedentTestcaseExecution  dependentCaseexecution.getParticipantpatientId() - " + dependentCaseexecution.getParticipantpatientId());
    				log.debug("prepareDepedentTestcaseExecution dependentCaseexecution.getPatientId() - " + dependentCaseexecution.getPatientId());
    				log.debug("prepareDepedentTestcaseExecution dependentCaseexecution.getTestcase().getName() - " + dependentCaseexecution.getTestcase().getName());
    				log.debug("prepareDepedentTestcaseExecution dependentCaseexecution.getParticipantpatientId() - " + dependentCaseexecution.getParticipantpatientId());
    				log.debug("prepareDepedentTestcaseExecution dependentCaseexecution.getMessageType() - " + dependentCaseexecution.getMessageType());
    				log.debug("prepareDepedentTestcaseExecution participant.getAssigningAuthorityId() - " + participant.getAssigningAuthorityId());
    				log.debug("**************************");
    				uniquePatientId = dependentCaseexecution.getParticipantpatientId() + "^^^&" + participant.getAssigningAuthorityId() + "&ISO";
    				if (getResponderPatientId() == null || "".equals(getResponderPatientId())) {
    					this.setResponderPatientId(uniquePatientId);
    				}
    			}
    			else {
    				eachCaseExecution.setDependentCaseExecuted(false);
    			}

    		}
    	}
    }

    /*
     *  ILT- 138 implementation
     *  Description :
     *  Read the test case and look at the test data. It should be clear how many documents are to be retrieved.     *
     *  If the participant's system generates documents dynamically (i.e. on their My Information screen,
     *  if they answered the question "Does your system return patient information (documents) in a single file, or
     *  does your system return patient information (documents) in multiple files?" as "single"),
     *  then every RDR test case that asks for >=1 documents should ask for one document instead.
     *  This expectation will be documented in the test case.
     *  If we have a special case (for example, intentionally asking a dynamic gateway for > 1 docs) that again will be documented in the test case.
     */
    private void populateRDRDependentCaseDetailsForConformance(Caseexecution eachCaseExecution)throws ServiceException {

        String dynamicContentInd = (participant.getDynamicContentInd() == null) ? DocumentTypeEnum.STATIC_DOCUMENT.getDynamicDocInd() : participant.getDynamicContentInd();
        int expectedDocIdsNum =0 ;
        /*  DocumentTypeEnum DynamicDocInd is used to verify whether it's dynamic or static doc type
            DocumentTypeEnum id is used to specify the number of documents received if it's dynamic (set to one currently)*/
        if (dynamicContentInd.equalsIgnoreCase(DocumentTypeEnum.DYNAMIC_DOCUMENT.getDynamicDocInd())) {
            expectedDocIdsNum = (DocumentTypeEnum.DYNAMIC_DOCUMENT.getId()).intValue();
        } // read test case definition for expected documents
        else if (eachCaseExecution.getDocumentIds() != null && !eachCaseExecution.getDocumentIds().isEmpty() ) {
            List expectedDocsList = Arrays.asList(eachCaseExecution.getDocumentIds().split(LabConstants.SPLITTER_DOUBLE_TILDE));
            expectedDocIdsNum = expectedDocsList.size();
        }
        // to hold depedent caseresults info
        Caseresult dependentCaseResult = null;
        // get existing caseresults if the test case is already ran from ui
      //  Caseresult currentCaseResult = CaseResultManager.getInstance().findByExecIdAndMaxResultId(eachCaseExecution.getCaseExecutionId());
        // if the test case is never executed at all , then we may not have records in case results and find out the dependent case "case results"
      //  if (currentCaseResult == null || currentCaseResult.getResultId() == null) {
            Integer dependentCaseId = eachCaseExecution.getDependentCaseId();
            Integer dependentScenarioId = eachCaseExecution.getDependentScenarioId();
            
            if (dependentScenarioId != null && dependentCaseId != null) {
                Caseexecution dependentCaseexecution = ScenarioCaseManager.getInstance().findByParticipantForActiveScenarioCase(participant, dependentScenarioId, dependentCaseId);
                if (dependentCaseexecution != null) {
                    dependentCaseResult = CaseResultManager.getInstance().findByExecIdAndMaxResultId(dependentCaseexecution.getCaseExecutionId());
                    if (dependentCaseResult != null && dependentCaseResult.getResultId() != null) {
                            eachCaseExecution.setDependentCaseExecuted(true);
                        List<Resultdocument> resultDocuments = null;
                        if (dependentCaseResult.getResultdocuments() == null || dependentCaseResult.getResultdocuments().isEmpty()) {
                            resultDocuments = CaseResultManager.getInstance().getDocumentsForTestCase(participant.getParticipantId(), dependentScenarioId, dependentCaseId);
                            dependentCaseResult.setResultdocuments(resultDocuments);
                        }
                        eachCaseExecution.setDepedentCaseexecution(dependentCaseexecution);
                    } else {
                        eachCaseExecution.setDependentCaseExecuted(false);
                    }
                }
            }
      //  }
        // TODO : find out the case result details(new child table for caseresult table) for RD based on caseresult id :
     //   else{

     //   }
        // prepare the number of text boxes display for RDR
        if(expectedDocIdsNum > 0){
            int loopIndex = expectedDocIdsNum;
            List<Resultdocument> rdrResultdocuments  = new ArrayList<Resultdocument>();
            Resultdocument newResultdocument = null;
            int depResultdocumentsSize = 0;
            List<Resultdocument> depResultdocuments = null;
            if (dependentCaseResult != null) {
                depResultdocuments = dependentCaseResult.getResultdocuments();
                depResultdocumentsSize = depResultdocuments.size();
            }
            if(depResultdocumentsSize >  expectedDocIdsNum){
                loopIndex =depResultdocumentsSize;
            }
            for (int i = 0; i < loopIndex; i++) {
                newResultdocument = new Resultdocument();
                // if depedent test case executed , then apply those values to display on UI and ignore Policy document
                if (eachCaseExecution.isDependentCaseExecuted()) {
                    if (depResultdocumentsSize > 0 && i <= depResultdocumentsSize - 1) {
                        Resultdocument depResultdocument = depResultdocuments.get(i);
                        newResultdocument.setRepositoryId(depResultdocument.getRepositoryId());
                        newResultdocument.setDocumentUniqueId(depResultdocument.getDocumentUniqueId());
//                        if (StringUtils.isNotEmpty(depResultdocument.getClassCode()) && !LabConstants.PRIVACYPOLICY_CLASSCODE_LIST.contains(depResultdocument.getClassCode())) {
//                            newResultdocument.setRepositoryId(depResultdocument.getRepositoryId());
//                            newResultdocument.setDocumentUniqueId(depResultdocument.getDocumentUniqueId());
//                        } else {
//                            continue;
//                        }

                    }
                    else{
                        newResultdocument.setRepositoryId("");
                        newResultdocument.setDocumentUniqueId("");
                    }
                } else {
                    newResultdocument.setRepositoryId("");
                    newResultdocument.setDocumentUniqueId("");
                }
                rdrResultdocuments.add(newResultdocument);
            }
            eachCaseExecution.setRdrResultdocuments(rdrResultdocuments);
        }

    }

    public boolean isIsCustomizedResponderPatientId() {
        return isCustomizedResponderPatientId;
    }

    public void setIsCustomizedResponderPatientId(boolean isCustomizedResponderPatientId) {
        this.isCustomizedResponderPatientId = isCustomizedResponderPatientId;
    }

    public String getShowRefreshWindowMsg() {
        return showRefreshWindowMsg;
    }

    public Caseresult getDepedentCaseresult() {
        return depedentCaseresult;
    }

    public void setShowRefreshWindowMsg(String showRefreshWindowMsg) {
        this.showRefreshWindowMsg = showRefreshWindowMsg;
    }

    public void setDepedentCaseresult(Caseresult depedentCaseresult) {
        this.depedentCaseresult = depedentCaseresult;
    }
}
