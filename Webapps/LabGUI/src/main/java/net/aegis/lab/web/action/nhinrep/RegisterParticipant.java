package net.aegis.lab.web.action.nhinrep;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dto.ParticipantPatientMapDto;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ParticipantPatientMapManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

public class RegisterParticipant extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private Participant participant;
    private User user;
    private String userName;                    // form param
    private String password;                    // form param
    private String participantName;               // form param
    private String communityId;                 // form param
    private String assigningAuthorityId;        // form param
    private String ipAddress;                   // form param
    private String version;
    private String contactName;                 // form param
    private String contactPhone;                // form param
    private String contactEmail;                // form param
    private String registerAction;
    // checkbox handling
    private String initiatorInd = "false";
    private String responderInd = "false";
    // radio button handling
    private String ssnHandlingInd = "false";
    private String dynamicContentInd = "false"; // Requirement specific to Story ID #9. Task 27 on the Sp2 tab.

    List<ParticipantPatientMapDto> participantPatientMapList = null;
    String changedPatientInfo;
    List<String> participantPatientText;

    @Override
    public String execute() throws Exception {
        log.info("RegisterParticipant.execute() - INFO");
        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.warn("RegisterParticipant.execute() - NHIN Rep is " + nhinrep.getName());
                setParticipantPatientMapList(ParticipantPatientMapManager.getInstance().getPatientListForRegisterParticipant(nhinrep));
                // Check Register Action
                if (registerAction != null) {
                    if (registerAction.equalsIgnoreCase("clear")) {
                        participant = new Participant();
                    }
                    if (registerAction.equalsIgnoreCase("save")) {

                        log.info("RegisterParticipant.execute() - save button clicked - INFO");

                        Participant participantThatNhinRepWorksWith = null;

                        if (changedPatientInfo.equalsIgnoreCase("true")) {
                            if (participantPatientText != null && participantPatientText.size() != 0) {
                                if (checkDuplicatesAndNulls(participantPatientText)) {
                                    log.info("validation Error Occurred..");
                                    return SUCCESS;
                                }
                            }
                        }

                        // now register the participant and save in session
                        participantThatNhinRepWorksWith = registerParticipant();
                        // Lab may have an existing user that has the requested name in the database.
                        if (null == participantThatNhinRepWorksWith) {
                            this.addActionError(getText("errors.registration.failed.duplicate.username", new String[]{this.userName}));
                            return SUCCESS;   // register participant page with action error
                        }

                        // if the candiate entered the participantPatientMap information
                        if (changedPatientInfo.equalsIgnoreCase("true")) {
                            if (participantPatientText != null && participantPatientText.size() != 0) {
                                for (String textboxvalue : participantPatientText) {
                                    log.info("--->>participantPatientId" + textboxvalue);
                                    if (textboxvalue.equals("") || !textboxvalue.isEmpty()) {
                                        log.info("--->>participantPatientId" + textboxvalue);
                                        log.info("-->>>" + participantPatientMapList.get(participantPatientText.indexOf(textboxvalue)).getPatientId());
                                        participantPatientMapList.get(participantPatientText.indexOf(textboxvalue)).setParticipantPatientId(textboxvalue);
                                        participantPatientMapList.get(participantPatientText.indexOf(textboxvalue)).setParticipant(participantThatNhinRepWorksWith);
                                    }
                                }
                                ParticipantPatientMapManager.getInstance().createParticipantPatientMap(participantPatientMapList, nhinrep);
                            }
                        }

                        // update some necessary session information
                        this.getSession().setAttribute(LabConstants.PARTICIPANT_THAT_NHINREP_WORKS_WITH, participantThatNhinRepWorksWith);
                        return INPUT;   // nhin dashboard
                    }

                } else {
                    participant = new Participant();
                }
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    /**
     * Requirement :This method to support the Nhinrep to register participant which
     * saves the participant data for the first time.
     *
     *
     * @return Participant
     * @throws Exception
     */
    private Participant registerParticipant() throws Exception {
        ParticipantManager participantManager = ParticipantManager.getInstance();
        // Register (Save) New Participant
        log.info("RegisterParticipant.registerParticipant() - INFO");

        Participant newlyRegisteredParticipant = null;

        user = new User();
        user.setUsername(this.getUserName());
        user.setPassword(this.getPassword());
        user.setStatus("A");
        user.setExpirationTime(null);
        user.setInvalidAttempts(LabConstants.USER_DEFAULT_INVALID_ATTEMPTS);    // 0
        user.setComments(this.getParticipantName() + " Registration Comments.");
        user.setCreatetime(new Timestamp(new Date().getTime()));
        user.setCreateuser(this.getNhinrep().getName());
        user.setChangedtime(null);
        user.setChangeduser(this.getNhinrep().getName());

        participant = new Participant();
        participant.setParticipantName(this.getParticipantName());
        participant.setUser(user);
        participant.setCommunityId(this.getCommunityId());
        participant.setAssigningAuthorityId(this.getAssigningAuthorityId());
        participant.setIpAddress(this.getIpAddress());
        participant.setVersion(this.getVersion());
        //participant.setNhinrep(this.getNhinrep());
        participant.setNhinRepId(this.getNhinrep().getNhinRepId());
        participant.setContactName(this.getContactName());
        participant.setContactPhone(this.getContactPhone());
        participant.setContactEmail(this.getContactEmail());
        participant.setInitiatorInd(this.getInitiatorInd());
        participant.setResponderInd(this.getResponderInd());
        participant.setSsnHandlingInd(this.getSsnHandlingInd());
        participant.setDynamicContentInd(this.getDynamicContentInd());
        participant.setStatus(LabConstants.STATUS_ACTIVE);
        participant.setCommVerifyStatus(LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_NOT);
        participant.setCreateuser(this.getNhinrep().getName());
        participant.setChangeduser(this.getNhinrep().getName());

        newlyRegisteredParticipant = participantManager.registerParticipant(user, participant);

        return newlyRegisteredParticipant;
    }

    /**
     *
     * @return String User Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param String userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param String password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return nhinrep
     */
    public Nhinrep getNhinrep() {
        return nhinrep;
    }

    /**
     * @return  void
     *
     * @param nhinrep
     */
    public void setNhinrep(Nhinrep nhinrep) {
        this.nhinrep = nhinrep;
    }

    /**
     *
     * @return participant
     */
    public Participant getParticipant() {
        return participant;
    }

    /**
     *
     * @return void
     * @param participant
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    /**
     *
     *
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     *
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     *
     * @return String
     */
    public String getRegisterAction() {
        return registerAction;
    }

    /**
     * To know which action user wants to execute
     * @param registerAction
     */
    public void setRegisterAction(String registerAction) {
        this.registerAction = registerAction;
    }

    // checkbox handling
    public String getInitiatorInd() {
        if ("false".equalsIgnoreCase(this.initiatorInd)) {
            return "N";
        }
        return initiatorInd;
    }

    /**
     * @param initiatorInd the initiatorInd to set
     */
    public void setInitiatorInd(String initiatorInd) {
        if ("false".equalsIgnoreCase(initiatorInd)) {
            this.initiatorInd = "N";
        } else {
            this.initiatorInd = "Y";
        }
    }

    /**
     *  ResponderInd from the jsp page
     *
     * @return String
     */
    public String getResponderInd() {
        if ("false".equalsIgnoreCase(this.responderInd)) {
            return "N";
        }
        return responderInd;
    }

    /**
     * @param responderInd the responderInd to set
     */
    public void setResponderInd(String responderInd) {
        if ("false".equalsIgnoreCase(responderInd)) {
            this.responderInd = "N";
        } else {
            this.responderInd = "Y";
        }
    }

    /**
     * @return the participantName
     */
    public String getParticipantName() {
        return participantName;
    }

    /**
     * @param participantName the participantName to set
     */
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    /**
     * @return the communityId
     */
    public String getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId the communityId to set
     */
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * @return the assigningAuthorityId
     */
    public String getAssigningAuthorityId() {
        return assigningAuthorityId;
    }

    /**
     * @param assigningAuthorityId the assigningAuthorityId to set
     */
    public void setAssigningAuthorityId(String assigningAuthorityId) {
        this.assigningAuthorityId = assigningAuthorityId;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    /**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
     * @return the ssnHandlingInd
     */
    public String getSsnHandlingInd() {
        return ssnHandlingInd;
    }

    /**
     * @param ssnHandlingInd the ssnHandlingInd to set
     */
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

    /**
     *
     * @return the participantPatientMapList
     */
    public List<ParticipantPatientMapDto> getParticipantPatientMapList() {
        return participantPatientMapList;
    }

    /**
     * @param participantPatientMapList to set the participantPatientMapList
     */
    public void setParticipantPatientMapList(List<ParticipantPatientMapDto> participantPatientMapList) {
        this.participantPatientMapList = participantPatientMapList;
    }

    /**
     *
     * @return the changedPatientInfo
     */
    public String getChangedPatientInfo() {
        return changedPatientInfo;
    }

    /**
     * setting ChangedPatientInfo
     * @param  changedPatientInfo
     */
    public void setChangedPatientInfo(String changedPatientInfo) {
        this.changedPatientInfo = changedPatientInfo;
    }

    /**
     * purpose : check for duplicate and nulls in participantPatientIdList coming from jsp page
     *
     * @param participantPatientIdList
     * @return boolean
     */
    public boolean checkDuplicatesAndNulls(List<String> participantPatientIdList) {
        log.info("In checkDuplicatesAndNulls..");
        for (int k = 0; k < participantPatientIdList.size(); k++) {
            String sValueToCheck = participantPatientIdList.get(k);
            if (sValueToCheck == null || sValueToCheck.equals("")) {
                this.addActionError("Please fill all ParticipantPatientIds ");
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

    /**
     *
     *
     * @return List of Strings i.e participantPatientId text
     */
    public List<String> getParticipantPatientText() {
        return participantPatientText;
    }

    /**
     *
     *
     * @param List of participantPatientText
     */
    public void setParticipantPatientText(List<String> participantPatientText) {
        this.participantPatientText = participantPatientText;
    }
}
