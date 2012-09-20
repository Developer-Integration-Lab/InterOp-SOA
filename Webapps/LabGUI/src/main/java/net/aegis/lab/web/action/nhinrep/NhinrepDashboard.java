package net.aegis.lab.web.action.nhinrep;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

public class NhinrepDashboard extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;

    @Override
    public String execute() throws Exception {

        log.info("NhinrepDashboard.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.warn("NhinrepDashboard.execute() - NHIN Rep is " + nhinrep.getName());

                // Populate NHIN Rep Participants
                nhinrep.setParticipants(ParticipantManager.getInstance().findByNhinRepId(nhinrep.getNhinRepId()));

                // Find if this nhin rep is currently working with a participant
                Participant objParticipantThatNhinRepWorksWith = null;
                objParticipantThatNhinRepWorksWith = (Participant)this.getSession().getAttribute(LabConstants.PARTICIPANT_THAT_NHINREP_WORKS_WITH);
                nhinrep.setWorkingParticipant(objParticipantThatNhinRepWorksWith);

                // Find active scenarios for each participant
                for(Participant objParticipant : nhinrep.getParticipants()) {
                    objParticipant.setServicesetexecutions(ServiceSetExecutionManager.getInstance().participantActiveServiceSets(objParticipant.getParticipantId()));
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

}
