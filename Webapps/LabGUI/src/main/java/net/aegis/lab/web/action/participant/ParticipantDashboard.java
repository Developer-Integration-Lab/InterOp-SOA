package net.aegis.lab.web.action.participant;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.web.action.BaseAction;

public class ParticipantDashboard extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Participant participant;
    private List<Servicesetexecution> activeServiceSets;
    private List<Servicesetexecution> serviceSetForValidation;
    // checkbox handling
    private List<Boolean> serviceSetIndicator;
    private String buttonName;

    @Override
    public String execute() throws Exception {

        log.info("ParticipantDashboard.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("ParticipantDashboard.execute() - participant is " + participant.getParticipantName());

                //log.info("ParticipantDashboard.execute() - buttonName=~" + buttonName + "~");

                if ("Submit for Validation".equals(buttonName)) {
                    if (serviceSetIndicator != null) {
                        activeServiceSets = (List<Servicesetexecution>) this.getSession().getAttribute("activeServiceSets");
                        this.setActiveServiceSets(activeServiceSets);
                        if (validate(serviceSetIndicator)) {
                            log.debug("I am in validator");
                            this.addActionError("Please Check At Least One Service Set To Submit");
                            return INPUT;
                        }
                        log.debug("<<<<<<serviceSetInd is not null in Dashboard-->>");
                        serviceSetForValidation = new ArrayList<Servicesetexecution>();

                        for (Boolean servicesetInd : serviceSetIndicator) {
                            if (servicesetInd == true) {
                                serviceSetForValidation.add(activeServiceSets.get(serviceSetIndicator.indexOf(servicesetInd)));
                                log.debug("activeServiceSets.get(serviceSetInd.indexOf(servicesetInd)In dashboard-->>" + activeServiceSets.get(serviceSetIndicator.indexOf(servicesetInd)));
                            }
                        }
                        if (serviceSetForValidation.size() == 0) {
                            log.debug("serviceSetForValidation.size() in Dashboard-->>:" + serviceSetForValidation.size());
                        } else {
                            log.debug("serviceSetForValidation.size() in Dashboard-->>:" + serviceSetForValidation.size());
                            ValidationManager.getInstance().submitServiceSetForValidation(serviceSetForValidation);
                            
                            if ((null != serviceSetForValidation.get(0))) {
                                String strActionMsg = "";
                                String svcSetUniqueId = serviceSetForValidation.get(0).getExecutionUniqueId();
                                strActionMsg = "Service Set " + svcSetUniqueId + " successfully submitted for Validation review.";
                                this.addActionMessage(strActionMsg);                                
                            }

                        }
                    }
                }

                this.setActiveServiceSets(ServiceSetExecutionManager.getInstance().participantActiveServiceSets(participant.getParticipantId(),this.getProfile().getLabType()));

                this.getSession().setAttribute("activeServiceSets", activeServiceSets);
            }
        } catch (Throwable e) {
            log.error("Exception", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Servicesetexecution> getActiveServiceSets() {
        return activeServiceSets;
    }

    public void setActiveServiceSets(List<Servicesetexecution> activeServiceSets) {
        this.activeServiceSets = activeServiceSets;
    }

    public List<Boolean> getServiceSetIndicator() {
        return serviceSetIndicator;
    }

    public void setServiceSetIndicator(List<Boolean> serviceSetIndicator) {
        this.serviceSetIndicator = serviceSetIndicator;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    private boolean validate(List<Boolean> serviceSetIndicator) {
        Boolean toBeSubmitted = true;

        for (Boolean setInd : serviceSetIndicator) {

            if (setInd == true) {
                log.debug("----->>>" + setInd);
                toBeSubmitted = false;
                break;
            }
        }

        return toBeSubmitted;

    }
}
