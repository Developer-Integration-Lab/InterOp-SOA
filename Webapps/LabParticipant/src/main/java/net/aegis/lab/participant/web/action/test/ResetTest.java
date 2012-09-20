package net.aegis.lab.participant.web.action.test;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.manager.ApplicationCleanupManager;
import net.aegis.lab.participant.web.action.BaseAction;

public class ResetTest extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Participant participant;
    private String executeResetInd;
    private String executeResetMessage;

    public ResetTest() {
        this.participant = new Participant();
        this.executeResetInd = "";
    }

    public ResetTest(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String execute() throws Exception {

        log.info("ResetTest.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("ResetTest.execute() - participant is " + participant.getParticipantName());
            }

            if (executeResetInd != null && executeResetInd.length() > 0) {
                ApplicationCleanupManager cleanupManager = new ApplicationCleanupManager();
                cleanupManager.cleanParticipantData();
                cleanupManager.cleanLabData();
                cleanupManager.cleanRIData();
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

    public String getExecuteResetInd() {
        return executeResetInd;
    }

    public void setExecuteResetInd(String executeResetInd) {
        this.executeResetInd = executeResetInd;
    }

    public String getExecuteResetMessage() {
        return executeResetMessage;
    }

    public void setExecuteResetMessage(String executeResetMessage) {
        this.executeResetMessage = executeResetMessage;
    }

}
