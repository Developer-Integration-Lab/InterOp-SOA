package net.aegis.lab.web.action.nhinrep;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dto.TMWindowMsgsDto;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.web.action.BaseAction;

public class ActiveScenarioResults extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private Participant objParticipantThatNhinRepWorksWith = null;
    private int selectedScenarioExecutionId;
    private List<Scenarioexecution> testScenarios;
    private Scenarioexecution testScenario;
    public List<TMWindowMsgsDto> messagesbtwTMWindow = new ArrayList<TMWindowMsgsDto>();
    public String caseName;
    private int caseExecutionId;

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("nhinrep.ActiveScenarioResults.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.warn("nhinrep.ActiveScenarioResults.execute() - NHIN Rep is " + nhinrep.getName());

                objParticipantThatNhinRepWorksWith = ((User) this.getRequest().getSession().getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
                this.setParticipant(objParticipantThatNhinRepWorksWith);    // JSP shows some information about the participant.  That's why set it here.
                if (null != objParticipantThatNhinRepWorksWith) {
                    log.warn("nhinrep.ActiveScenarioResults.execute() - participant that NHIN Rep works with is " + objParticipantThatNhinRepWorksWith.getParticipantName());
                }

                if (null == objParticipantThatNhinRepWorksWith) {
                    this.addActionError(getText("errors.nhinrep.no.working.participant"));
                    return INPUT;       // back to nhinrep dashboard for them to choose a participant
                }
            }

            testScenarios = (List<Scenarioexecution>) this.getSession().getAttribute("testScenarios");

            if (selectedScenarioExecutionId == 0) {
                String selectedScenarioExecutionIdString = this.getRequest().getParameter("scenarioExecutionId");
                if (selectedScenarioExecutionIdString != null) {
                    selectedScenarioExecutionId = Integer.parseInt(selectedScenarioExecutionIdString);
                    log.warn("nhinrep.ActiveScenarioResults.execute() - selectedScenarioExecutionId parameter is " + selectedScenarioExecutionId);
                    this.setTestScenario(this.findTestScenario());
                } else {
                    log.warn("nhinrep.ActiveScenarioResults.execute() - selectedScenarioExecutionId parameter IS NOT DEFINED");
                }
            } else {

                if (null != objParticipantThatNhinRepWorksWith) {
                    this.setTestScenario(ParticipantManager.getInstance().returnScenarioExecution(selectedScenarioExecutionId));
                    log.warn("nhinrep.ActiveScenarioResults.execute() - Refreshed selectedScenarioExecutionId " + selectedScenarioExecutionId);
                }
            }

            this.getSession().setAttribute("testScenario", this.getTestScenario());

        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    public Nhinrep getNhinrep() {
        return nhinrep;
    }

    public void setNhinrep(Nhinrep nhinrep) {
        this.nhinrep = nhinrep;
    }

    public Participant getParticipant() {
        return this.objParticipantThatNhinRepWorksWith;
    }

    public void setParticipant(Participant pobjParticipantThatNhinRepWorksWith) {
        this.objParticipantThatNhinRepWorksWith = pobjParticipantThatNhinRepWorksWith;
    }

    public int getSelectedScenarioExecutionId() {
        return selectedScenarioExecutionId;
    }

    public void setSelectedScenarioExecutionId(int selectedScenarioExecutionId) {
        this.selectedScenarioExecutionId = selectedScenarioExecutionId;
    }

    public Scenarioexecution getTestScenario() {
        return testScenario;
    }

    public void setTestScenario(Scenarioexecution testScenario) {
        this.testScenario = testScenario;
    }

    private Scenarioexecution findTestScenario() {
        Scenarioexecution returnScenario = null;

        if (selectedScenarioExecutionId > 0) {
            log.warn("nhinrep.ActiveScenarioResults.findTestScenario() - scenarioId > 0");
            for (Scenarioexecution findScenario : testScenarios) {
                if (findScenario != null && findScenario.getScenarioExecutionId() == selectedScenarioExecutionId) {
                    log.warn("nhinrep.ActiveScenarioResults.findTestScenario() - returnScenario eq findScenario");
                    returnScenario = findScenario;
                    this.setSelectedScenarioExecutionId(returnScenario.getScenarioExecutionId());
                    break;
                }
            }
        }

        return returnScenario;
    }

    public String showAuditExtensionMsg() throws Exception {
        Caseexecution caseExecs = null;
        if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
            setNhinrep(this.getProfile().getNhinreps().get(0));
            log.warn("nhinrep.ActiveScenarioResults.showAuditExtensionMsg() - NHIN Rep is " + nhinrep.getName());
            objParticipantThatNhinRepWorksWith = ((User) this.getRequest().getSession().getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
            this.setParticipant(objParticipantThatNhinRepWorksWith);    // JSP shows some information about the participant.  That's why set it here.
            if (null != objParticipantThatNhinRepWorksWith) {
                log.warn("nhinrep.ActiveScenarioResults.execute() - participant that NHIN Rep works with is " + objParticipantThatNhinRepWorksWith.getParticipantName());
            }

            if (null == objParticipantThatNhinRepWorksWith) {
                this.addActionError(getText("errors.nhinrep.no.working.participant"));
                return INPUT;       // back to nhinrep dashboard for them to choose a participant
                }
            // get the case execution id and case name request parameters
            String caseExecId = this.getRequest().getParameter("selectedCaseExecutionId");
            this.setCaseExecutionId(Integer.parseInt(caseExecId));
            this.setCaseName(this.getRequest().getParameter("selectedCaseName"));
            Scenarioexecution scenarioExec = (Scenarioexecution) this.getSession().getAttribute("testScenario");
            this.setTestScenario(ParticipantManager.getInstance().returnScenarioExecution(scenarioExec.getScenarioExecutionId()));
            if (caseExecId != null) {
                caseExecs = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(Integer.parseInt(caseExecId));
            } else {
                log.info("ActiveScenarioResults.showAuditExtensionMsg()......The Case Execution Id cannot be null");
            }

            log.info("ActiveScenarioResults.showAuditExtensionMsg()........The CaseExecution is " + caseExecs);
//            this.setMessagesbtwTMWindow(this.getAuditRepositoryList(caseExecs, caseName));

        } else {
            log.info("ActiveScenarioResults.showAuditExtensionMsg()......No Participant profile found");
        }          
        

        return SUCCESS;
    }

    class sortByTimeStamp implements Comparator<TMWindowMsgsDto> {

        @Override
        public int compare(TMWindowMsgsDto o1, TMWindowMsgsDto o2) {
            return o1.getTimestamp().compareTo(o2.getTimestamp());
        }
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public List<TMWindowMsgsDto> getMessagesbtwTMWindow() {
        return messagesbtwTMWindow;
    }

    public void setMessagesbtwTMWindow(List<TMWindowMsgsDto> messagesbtwTMWindow) {
        this.messagesbtwTMWindow = messagesbtwTMWindow;
    }

    public List<Scenarioexecution> getTestScenarios() {
        return testScenarios;
    }

    public void setTestScenarios(List<Scenarioexecution> testScenarios) {
        this.testScenarios = testScenarios;
    }

    public int getCaseExecutionId() {
        return caseExecutionId;
    }

    public void setCaseExecutionId(int caseExecutionId) {
        this.caseExecutionId = caseExecutionId;
    }
}
