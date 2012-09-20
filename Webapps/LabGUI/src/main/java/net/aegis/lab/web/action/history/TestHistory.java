package net.aegis.lab.web.action.history;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

/**
 * Requirement - Nhin Rep and Participant users need to be able to see and drill
 *               down the historical service set execution records.
 */
public class TestHistory extends BaseAction {

    private static final long serialVersionUID = 1L;
    int iTheRoleOfTheLoggedInUser = -1;
    private Nhinrep nhinrep;
    private Participant participant;
    private List<Servicesetexecution> previousServiceSets = new ArrayList<Servicesetexecution>();   // avoid unnecessary NPE
    private String userAction;

    @Override
    public String execute() throws Exception {

        log.info("TestHistory.execute() - INFO");

        try {
            if (this.getProfile() != null) {
                iTheRoleOfTheLoggedInUser = this.getProfile().getPrimaryRole();
                log.info("TestHistory.execute(): role of the logged in user = " + iTheRoleOfTheLoggedInUser + " - INFO");
            }

            // *****************************************************************
            //                      PARTICIPANT
            // *****************************************************************
            if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_PARTICIPANT) {
                 if (userAction != null) {
                    if (userAction.equalsIgnoreCase("refresh")) {
                        log.info("TestHistory.execute() - participant - refresh submit button clicked - INFO");
                        setUpThisJSPPageForDisplayForParticipant();
                    }
                 }
                 else {
                    // clicked on the menu item -- need to populate the list in that case too.
                    log.info("TestHistory.execute() - participant - menu item clicked - INFO");
                    setUpThisJSPPageForDisplayForParticipant();
                 }
            } // end PARTICIPANT PART
            // *****************************************************************


            // *****************************************************************
            //                      NHIN REP
            // *****************************************************************
            if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {
                if (userAction != null) {
                    if (userAction.equalsIgnoreCase("refresh")) {
                        log.info("TestHistory.execute() - nhin rep - refresh submit button clicked - INFO");
                        setUpThisJSPPageForDisplayForNhinRep();
                    }
                }
                else {
                    // clicked on the menu item -- need to populate the list in that case too.
                    log.info("TestHistory.execute() - nhin rep - menu item clicked - INFO");
                    setUpThisJSPPageForDisplayForNhinRep();
                }
            }  // end NHIN REP PART
            // *****************************************************************

        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }


    /**
     * Purpose - A simple refactoring method for code reuse. Gets all scenario
     *           executions whose status is 'closed' AND 'validated' AND
     *           'failed' etc among other things.
     *
     * @throws Exception
     */
    private void setUpThisJSPPageForDisplayForParticipant() throws Exception {

        log.info("TestHistory.setUpThisJSPPageForDisplayForParticipant() - INFO");

        if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
            setParticipant(this.getProfile().getParticipants().get(0));
            log.warn("TestHistory.setUpThisJSPPageForDisplayForParticipant() - Participant is " + participant.getParticipantName());

            // If the Nhin Rep does not work with a participant, previousServiceSets will be EMPTY. Just a warning.
            if (null == participant) {
                log.warn("TestHistory.setUpThisJSPPageForDisplayForParticipant(): Serious warning: The logged in Participant is null ??? - WARN");
            }

            // /////////////////////////////////////////////////////////////////
            // Note - we are getting service set executions for the participant
            // that is logged in.  Avoiding 'ACTIVE' service set executions.
            // /////////////////////////////////////////////////////////////////
            if (null != participant) {
                List<Servicesetexecution> closedServiceSetExecutions = null;
                List<Servicesetexecution> submittedServiceSetExecutions = null;
                List<Servicesetexecution> reviewServiceSetExecutions = null;
                List<Servicesetexecution> pendingServiceSetExecutions = null;
                LabConstants.LabType labType = this.getProfile().getLabType();

                closedServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(participant.getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_CLOSE,labType);
                submittedServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(participant.getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_SUBMITTED,labType);
                reviewServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(participant.getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_REVIEW,labType);
                pendingServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(participant.getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_PENDING,labType);

                if(null != closedServiceSetExecutions) {
                    for(Servicesetexecution csse : closedServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForParticipant() - closed status service set execution Participant name: " + csse.getParticipant().getParticipantName() +
                                " and execution status: " + csse.getStatus() +
                                " and execution unique id: " + csse.getExecutionUniqueId());
                        previousServiceSets.add(csse);
                    }
                }
                if(null != submittedServiceSetExecutions) {
                    for(Servicesetexecution ssse : submittedServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForParticipant() - submitted status service set execution Participant name: " + ssse.getParticipant().getParticipantName() +
                                " and execution status: " + ssse.getStatus() +
                                " and execution unique id: " + ssse.getExecutionUniqueId());
                        previousServiceSets.add(ssse);
                    }
                }
                if(null != reviewServiceSetExecutions) {
                    for(Servicesetexecution rsse : reviewServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForParticipant() - review status service set execution Participant name: " + rsse.getParticipant().getParticipantName() +
                                " and execution status: " + rsse.getStatus() +
                                " and execution unique id: " + rsse.getExecutionUniqueId());
                        previousServiceSets.add(rsse);
                    }
                }
                if(null != pendingServiceSetExecutions) {
                    for(Servicesetexecution psse : pendingServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForParticipant() - pending status service set execution Participant name: " + psse.getParticipant().getParticipantName() +
                                " and execution status: " + psse.getStatus() +
                                " and execution unique id: " + psse.getExecutionUniqueId());
                        previousServiceSets.add(psse);
                    }
                }
            }
            
            this.setPreviousServiceSets(previousServiceSets);   // it can be EMPTY but not null !!!  Good.
            this.getSession().setAttribute("previousServiceSets", previousServiceSets);
        }
    }


    /**
     * Purpose - A simple refactoring method for code reuse. Gets all scenario
     *           executions whose status is 'closed' AND 'validated' AND
     *           'failed' etc among other things.
     *
     * @throws Exception
     */
    private void setUpThisJSPPageForDisplayForNhinRep() throws Exception {
        
        log.info("TestHistory.setUpThisJSPPageForDisplayForNhinRep() - INFO");

        if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
            setNhinrep(this.getProfile().getNhinreps().get(0));
            log.warn("TestHistory.setUpThisJSPPageForDisplayForNhinRep() - NHIN Rep is " + nhinrep.getName());

            // Populate NHIN Rep Participants
            nhinrep.setParticipants(ParticipantManager.getInstance().findByNhinRepId(nhinrep.getNhinRepId()));

            // Find if this nhin rep is currently working with a participant
            Participant objParticipantThatNhinRepWorksWith = null;
            objParticipantThatNhinRepWorksWith = (Participant)this.getSession().getAttribute(LabConstants.PARTICIPANT_THAT_NHINREP_WORKS_WITH);
            nhinrep.setWorkingParticipant(objParticipantThatNhinRepWorksWith);

            // If the Nhin Rep does not work with a participant, previousServiceSets will be EMPTY. Just a warning.
            if (null == nhinrep.getWorkingParticipant()) {
                log.warn("TestHistory.setUpThisJSPPageForDisplayForNhinRep(): Note that the Nhin Rep does not yet work with a participant. - WARN");
            }

            // /////////////////////////////////////////////////////////////////
            // Note - we are getting service set executions ONLY for the participant
            // that the logged in Nhin Rep is currently working with.
            // /////////////////////////////////////////////////////////////////
            if (null != nhinrep.getWorkingParticipant()) {
                this.setParticipant(nhinrep.getWorkingParticipant());
                
                List<Servicesetexecution> closedServiceSetExecutions = null;
                List<Servicesetexecution> submittedServiceSetExecutions = null;
                List<Servicesetexecution> reviewServiceSetExecutions = null;
                List<Servicesetexecution> pendingServiceSetExecutions = null;

                closedServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(nhinrep.getWorkingParticipant().getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_CLOSE);
                submittedServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(nhinrep.getWorkingParticipant().getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_SUBMITTED);
                reviewServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(nhinrep.getWorkingParticipant().getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_REVIEW);
                pendingServiceSetExecutions = ServiceSetExecutionManager.getInstance().getServiceSetExecutionsByStatus(nhinrep.getWorkingParticipant().getParticipantId(),
                                                LabConstants.STATUS_SERVICESETEXEC_PENDING);
                
                if(null != closedServiceSetExecutions) {
                    for(Servicesetexecution csse : closedServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForNhinRep() - closed status service set execution Participant name: " + csse.getParticipant().getParticipantName() +
                                " and execution status: " + csse.getStatus() +
                                " and execution unique id: " + csse.getExecutionUniqueId());
                        previousServiceSets.add(csse);
                    }
                }
                if(null != submittedServiceSetExecutions) {
                    for(Servicesetexecution ssse : submittedServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForNhinRep() - submitted status service set execution Participant name: " + ssse.getParticipant().getParticipantName() +
                                " and execution status: " + ssse.getStatus() +
                                " and execution unique id: " + ssse.getExecutionUniqueId());
                        previousServiceSets.add(ssse);
                    }
                }
                if(null != reviewServiceSetExecutions) {
                    for(Servicesetexecution rsse : reviewServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForNhinRep() - review status service set execution Participant name: " + rsse.getParticipant().getParticipantName() +
                                " and execution status: " + rsse.getStatus() +
                                " and execution unique id: " + rsse.getExecutionUniqueId());
                        previousServiceSets.add(rsse);
                    }
                }
                if(null != pendingServiceSetExecutions) {
                    for(Servicesetexecution psse : pendingServiceSetExecutions) {
                        log.info("TestHistory.setUpThisJSPPageForDisplayForNhinRep() - pending status service set execution Participant name: " + psse.getParticipant().getParticipantName() +
                                " and execution status: " + psse.getStatus() +
                                " and execution unique id: " + psse.getExecutionUniqueId());
                        previousServiceSets.add(psse);
                    }
                }
            }

            this.setPreviousServiceSets(previousServiceSets);   // it can be EMPTY but not null !!!  Good.
            this.getSession().setAttribute("previousServiceSets", previousServiceSets);
        }
    }



    public Nhinrep getNhinrep() {
        return nhinrep;
    }

    public void setNhinrep(Nhinrep nhinrep) {
        this.nhinrep = nhinrep;
    }

    /**
     * @return the participant
     */
    public Participant getParticipant() {
        return participant;
    }

    /**
     * @param participant the participant to set
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    /**
     * @return the previousServiceSets
     */
    public List<Servicesetexecution> getPreviousServiceSets() {
        return previousServiceSets;
    }

    /**
     * @param previousServiceSets the previousServiceSets to set
     */
    public void setPreviousServiceSets(List<Servicesetexecution> previousServiceSets) {
        this.previousServiceSets = previousServiceSets;
    }

    /**
     * @return the userAction
     */
    public String getUserAction() {
        return userAction;
    }

    /**
     * @param userAction the userAction to set
     */
    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

}
