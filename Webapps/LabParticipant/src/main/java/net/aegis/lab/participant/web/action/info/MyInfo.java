package net.aegis.lab.participant.web.action.info;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.participant.web.action.BaseAction;

public class MyInfo extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Participant participant;
    // checkbox handling
    private String initiatorIndSet = "false";
    private String responderIndSet = "false";

    public MyInfo() {
        this.participant = new Participant();
    }

    public MyInfo(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String execute() throws Exception {

        log.info("MyInfo.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("MyInfo.execute() - participant is " + participant.getParticipantName());
                // Set checkbox handling
                if (this.getParticipant().getInitiatorInd().equals("Y")) {
                    initiatorIndSet = "true";
                }
                if (this.getParticipant().getResponderInd().equals("Y")) {
                    responderIndSet = "true";
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

    public String getInitiatorIndSet() {
        return initiatorIndSet;
    }

    public String getResponderIndSet() {
        return responderIndSet;
    }
}
