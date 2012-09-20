package net.aegis.lab.web.action.nhinrep;

import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.util.LabConstants.LabType;
import net.aegis.lab.web.action.BaseAction;

public class ActiveTestResults extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private String executionUniqueId = "";
    private List<Servicesetexecution> serviceSetExecution = null;
    private Participant objParticipantThatNhinRepWorksWith = null;
    private List<Scenarioexecution> testScenarios;

    @Override
    public String execute() throws Exception {

        log.info("nhinrep.ActiveTestResults.execute() - INFO");
         log.info("nhinrep.ActiveTestResults.execute() - executionUniqueId="+executionUniqueId);

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.warn("nhinrep.ActiveTestResults.execute() - NHIN Rep is " + nhinrep.getName());

                objParticipantThatNhinRepWorksWith = ((User)this.getRequest().getSession().getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
                this.setParticipant(objParticipantThatNhinRepWorksWith);
                if (null != objParticipantThatNhinRepWorksWith) {
                    log.warn("nhinrep.ActiveScenarioResults.execute() - participant that NHIN Rep works with is " + objParticipantThatNhinRepWorksWith.getParticipantName());
                }

                if (null == objParticipantThatNhinRepWorksWith) {
                    this.addActionError(getText("errors.nhinrep.no.working.participant"));
                    return INPUT;       // back to nhinrep dashboard for them to choose a participant
                }

                if (null != objParticipantThatNhinRepWorksWith) {
                    this.setTestScenarios(ParticipantManager.getInstance().activeScenarioExecutions(objParticipantThatNhinRepWorksWith.getParticipantId()));
                    this.getSession().setAttribute("testScenarios", this.getTestScenarios());
                    this.getSession().setAttribute("sessionobj.servicesetExecUniqueId", executionUniqueId);
                   // get and save the complete service set execution object anyway. Note - there will be only one in DB !!
                    setServiceSetExecution(ServiceSetExecutionManager.getInstance().findByExecutionUniqueId(executionUniqueId));

                    //Set the headerType value as "Scenario" for lab / "Test Group" for conformance mode in session to display in jsp page
                    if (this.getServiceSetExecution() != null && this.getServiceSetExecution().size() > 0) {
                        int labMode = getServiceSetExecution().get(0).getServiceset().getLabAccessFilter();
                        String labType = LabType.getType(labMode);
                        if (labType != null && !labType.equals("")) {
                            this.getSession().setAttribute("nhinrepHeaderType", labType);
                        } else {
                            labType = LabType.getType(LabType.LAB.getId());
                            this.getSession().setAttribute("nhinrepHeaderType", labType);
                        }
                    }
                }

            }
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

    public List<Scenarioexecution> getTestScenarios() {
        return testScenarios;
    }

    public void setTestScenarios(List<Scenarioexecution> testScenarios) {
        this.testScenarios = testScenarios;
    }

    public String getExecutionUniqueId() {
        return executionUniqueId;
    }

    public void setExecutionUniqueId(String executionUniqueId) {
        this.executionUniqueId = executionUniqueId;
    }

    public List<Servicesetexecution> getServiceSetExecution() {
        return serviceSetExecution;
    }

    public void setServiceSetExecution(List<Servicesetexecution> serviceSetExecution) {
        this.serviceSetExecution = serviceSetExecution;
    }

}
