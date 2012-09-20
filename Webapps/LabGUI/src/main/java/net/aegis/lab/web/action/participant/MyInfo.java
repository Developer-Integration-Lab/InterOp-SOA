package net.aegis.lab.web.action.participant;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dto.ParticipantPatientMapDto;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ParticipantPatientMapManager;
import net.aegis.lab.web.action.BaseAction;

public class MyInfo extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Participant participant;
    // checkbox handling
    private boolean initiatorIndSet = false;
    private boolean responderIndSet = false;
    private String buttonName;
    List<ParticipantPatientMapDto> participantPatientMapList = null;
    String changedPatientInfo;
    String changedParticipantInfo;
    List<String> participantPatientId;
    String participantName;
    String contactName;
    String contactPhone;
    String contactEmail;
    String initiatorInd;
    String responderInd;
    String ssnHandlingInd;
    private String dynamicContentInd;   // Requirement specific to Story ID #9. Task 27 on the Sp2 tab.
    String ipAddress;
    String version;
    String communityId;
    private String resouceIdSendInd ;

    @Override
    public String execute() throws Exception {

        log.info("MyInfo.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("MyInfo.execute() - participant is " + participant.getParticipantName());
                // Set checkbox handling

                if (this.getParticipant().getInitiatorInd().equals("Y")) {
                    initiatorIndSet = true;
                }
                if (this.getParticipant().getResponderInd().equals("Y")) {
                    responderIndSet = true;
                }

                setParticipantPatientMapList(ParticipantPatientMapManager.getInstance().createAndGetParticipantPatientMap(participant));
                log.info("MyInfo the size of participantPatientMapList" + participantPatientMapList.size());
                if (changedPatientInfo != null) {
                    for (ParticipantPatientMapDto patinetInfo : participantPatientMapList) {
                        patinetInfo.setParticipantPatientId(participantPatientId.get(participantPatientMapList.indexOf(patinetInfo)));
                    }
                }

                log.info(" Printing participantInfo-->>>>>");
                log.info("ParticipantName --> " + participantName);

                if ("Save Changes".equals(buttonName)) {
                    log.info("In Save Changes--->>>" + changedPatientInfo);
                    if (changedPatientInfo.equalsIgnoreCase("true")) {
                        if (participantPatientId != null && participantPatientId.size() != 0) {
                            if (checkDuplicates(participantPatientId)) {
                                log.info("I am in check input ");
                                return INPUT;
                            }
                            for (String textboxvalue : participantPatientId) {
                                log.info("--->>participantPatientId" + textboxvalue);
                                if (textboxvalue.equals("") || !textboxvalue.isEmpty()) {
                                    log.info("--->>participantPatientId" + textboxvalue);
                                    log.info("-->>>" + participantPatientMapList.get(participantPatientId.indexOf(textboxvalue)).getPatientId());
                                    participantPatientMapList.get(participantPatientId.indexOf(textboxvalue)).setParticipantPatientId(textboxvalue);
                                }
                            }
                            ParticipantPatientMapManager.getInstance().updateParticipantPatientMapId(participantPatientMapList);
                        }
                    }
                    if (changedParticipantInfo.equalsIgnoreCase("true")) {
                        if (checkParticipantInfo()) {
                            return INPUT;
                        }

                        if (!participant.getParticipantName().equals(participantName)) {
                            participant.setParticipantName(participantName);
                        }
                        if (!participant.getContactName().equals(contactName)) {
                            participant.setContactName(contactName);
                        }
                        if (!participant.getContactPhone().equals(contactPhone)) {
                            participant.setContactPhone(contactPhone);
                        }
                        if (!participant.getContactEmail().equals(contactEmail)) {
                            participant.setContactEmail(contactEmail);
                        }
                        if (initiatorInd.equals("Y")) {
                            participant.setInitiatorInd("Y");
                            initiatorIndSet = true;
                        } else {
                            participant.setResponderInd("N");
                            initiatorIndSet = false;
                        }
                        if (responderInd.equals("Y")) {
                            participant.setResponderInd("Y");
                            responderIndSet = true;
                        } else {
                            participant.setResponderInd("N");
                            responderIndSet = false;
                        }
                        if (!participant.getSsnHandlingInd().equals(ssnHandlingInd)) {
                            participant.setSsnHandlingInd(ssnHandlingInd);
                        }

                        if (!participant.getDynamicContentInd().equals(dynamicContentInd)) {
                            participant.setDynamicContentInd(dynamicContentInd);
                        }

                        if (!participant.getIpAddress().equals(ipAddress)) {
                            participant.setIpAddress(ipAddress);
                        }
                        
                        if (!participant.getIpAddress().equals(ipAddress)) {
                            participant.setIpAddress(ipAddress);
                        }
                        
                        if (!participant.getVersion().equals(version)) {
                            participant.setVersion(version);
                        }

                        if (!participant.getCommunityId().equals(communityId)) {
                            log.info("community id (and AAID) changed from "+ participant.getCommunityId() + " to " + communityId);
                            participant.setCommunityId(communityId);
                            participant.setAssigningAuthorityId(communityId);
                        }
                        
                        if (!participant.getResouceIdSendInd().equals(resouceIdSendInd)) {
                            participant.setResouceIdSendInd(resouceIdSendInd);
                        }

                        // updating participant in database as well as session

                        ParticipantManager.getInstance().updateParticipant(participant);
                        List<Participant> participants = new ArrayList<Participant>();
                        participants.add(participant);
                        User profile = this.getProfile();
                        profile.setParticipants(participants);
                        this.getSession().setAttribute("userProfile", profile);
                        setParticipant(participant);
                    }
                }

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

    public boolean getInitiatorIndSet() {
        return initiatorIndSet;
    }

    public boolean getResponderIndSet() {
        return responderIndSet;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public List<ParticipantPatientMapDto> getParticipantPatientMapList() {
        return participantPatientMapList;
    }

    public void setParticipantPatientMapList(List<ParticipantPatientMapDto> participantPatientMapList) {
        this.participantPatientMapList = participantPatientMapList;
    }

    public String getChangedPatientInfo() {
        return changedPatientInfo;
    }

    public void setChangedPatientInfo(String changedPatientInfo) {
        this.changedPatientInfo = changedPatientInfo;
    }

    public List<String> getParticipantPatientId() {
        return participantPatientId;
    }

    public void setParticipantPatientId(List<String> participantPatientId) {
        this.participantPatientId = participantPatientId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getChangedParticipantInfo() {
        return changedParticipantInfo;
    }

    public void setChangedParticipantInfo(String changedParticipantInfo) {
        this.changedParticipantInfo = changedParticipantInfo;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getInitiatorInd() {
        return initiatorInd;
    }

    public void setInitiatorInd(String initiatorInd) {
        this.initiatorInd = initiatorInd;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
  	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

    public String getResponderInd() {
        return responderInd;
    }

    public void setResponderInd(String responderInd) {
        this.responderInd = responderInd;
    }

    public String getSsnHandlingInd() {
        return ssnHandlingInd;
    }

    public void setSsnHandlingInd(String ssnHandlingInd) {
        this.ssnHandlingInd = ssnHandlingInd;
    }

    /**
     * @return the dynamicContentInd
     * Requirement specific to Story ID #9. Task 27 on the Sp2 tab.
     */
    public String getDynamicContentInd() {
        return dynamicContentInd;
    }

    /**
     * @param dynamicContentInd the dynamicContentInd to set
     * Requirement specific to Story ID #9. Task 27 on the Sp2 tab.
     */
    public void setDynamicContentInd(String dynamicContentInd) {
        this.dynamicContentInd = dynamicContentInd;
    }

//check for duplicate patientIdList
    public boolean checkDuplicates(List<String> participantPatientIdList) {
         for (int k = 0; k < participantPatientIdList.size(); k++) {
            String sValueToCheck = participantPatientIdList.get(k);
            if (sValueToCheck == null || sValueToCheck.equals("")) {
                this.addActionError("The ParticipantPatientId cannot be empty");
                return true;
            }
            for (int l = 0; l < participantPatientIdList.size(); l++) {
                if (k == l) {
                    continue;
                }
                String sValuesTosCompare = participantPatientIdList.get(l);
                if (sValueToCheck.equals(sValuesTosCompare)) {
                    this.addActionError("The ParticipantPatientId cannot be duplicate");
                    return true;
                }
            }

        }
        return false;
    }

    // check nulls for participant information
    public boolean checkParticipantInfo() {
        if (participantName == null || contactPhone == null || contactEmail == null || initiatorInd == null || responderInd == null || ipAddress == null
                || communityId == null) {
            this.addActionError("Please Supply All the Values");
            return true;
        } else if (participantName.isEmpty() || contactPhone.isEmpty() || contactEmail.isEmpty() || initiatorInd.isEmpty() || responderInd.isEmpty() || ipAddress.isEmpty()
                || communityId.isEmpty()  ) {
            this.addActionError("Please Supply Proper Values");
            return true;
        } else if (initiatorInd.equalsIgnoreCase("false") && responderInd.equalsIgnoreCase("false")) {
            this.addActionError("Please Select Atleast One Initiator Or Responder");
            return true;
        } else if (ssnHandlingInd == null || ssnHandlingInd.isEmpty()) {
            this.addActionError("Please Select SSNHandling");
            return true;
        } else if (dynamicContentInd == null || dynamicContentInd.isEmpty()) {
            this.addActionError("Please Select Dynamic Content.");
            return true;
        }

        return false;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

     public String getResouceIdSendInd() {
        return resouceIdSendInd;
    }

    public void setResouceIdSendInd(String resouceIdSendInd) {
        this.resouceIdSendInd = resouceIdSendInd;
    }
}


