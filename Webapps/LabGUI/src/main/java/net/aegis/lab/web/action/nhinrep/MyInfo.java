package net.aegis.lab.web.action.nhinrep;

import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.manager.NhinrepManager;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

/**
 * Purpose - An Nhin Rep, who is logged in, can access his/her contact
 *           information and make changes.
 * 
 * @author Abhay.Bakshi
 */
public class MyInfo extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private String name;                        // form param
    private String contactName;                 // form param
    private String contactPhone;                // form param
    private String contactEmail;                // form param
    private String saveAction;

    @Override
    public String execute() throws Exception {

        log.info("nhinrep.MyInfo.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.info("nhinrep.MyInfo.execute() - NHIN Rep is " + nhinrep.getName());

                if (saveAction != null) {

                    if (saveAction.equalsIgnoreCase("save")) {
                        // now invoke delegate code to perform the job of information update
                        Nhinrep updatedNhinrep = NhinrepManager.getInstance().updateContactInformation(nhinrep.getNhinRepId(), name, contactName, contactPhone, contactEmail);
                        List<Nhinrep> tempListNhinReps = this.getProfile().getNhinreps();
                        tempListNhinReps.set(0, updatedNhinrep);        // must update information at the session level !!
                        setNhinrep(this.getProfile().getNhinreps().get(0));

                        // Populate NHIN Rep Participants
                        nhinrep.setParticipants(ParticipantManager.getInstance().findByNhinRepId(nhinrep.getNhinRepId()));

                        // Find if this nhin rep is currently working with a participant
                        Participant objParticipantThatNhinRepWorksWith = null;
                        objParticipantThatNhinRepWorksWith = (Participant)this.getSession().getAttribute(LabConstants.PARTICIPANT_THAT_NHINREP_WORKS_WITH);
                        nhinrep.setWorkingParticipant(objParticipantThatNhinRepWorksWith);
                    }
                }
                else {
                    // Populate NHIN Rep Participants
                    nhinrep.setParticipants(ParticipantManager.getInstance().findByNhinRepId(nhinrep.getNhinRepId()));

                    // Find if this nhin rep is currently working with a participant
                    Participant objParticipantThatNhinRepWorksWith = null;
                    objParticipantThatNhinRepWorksWith = (Participant)this.getSession().getAttribute(LabConstants.PARTICIPANT_THAT_NHINREP_WORKS_WITH);
                    nhinrep.setWorkingParticipant(objParticipantThatNhinRepWorksWith);
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return the saveAction
     */
    public String getSaveAction() {
        return saveAction;
    }

    /**
     * @param saveAction the saveAction to set
     */
    public void setSaveAction(String saveAction) {
        this.saveAction = saveAction;
    }
}
