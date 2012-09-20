package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Pojo mapping table participant</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Participant implements Serializable {

    /**
     * Attribute participantId.
     */
    private Integer participantId;
    /**
     * Attribute participantName.
     */
    private String participantName;
    /**
     * Attribute user
     */
    private User user;
    /**
     * Attribute communityId.
     */
    private String communityId;
    /**
     * Attribute assigningAuthorityId.
     */
    private String assigningAuthorityId;
    /**
     * Attribute ipAddress.
     */
    private String ipAddress;
    /**
     * Attribute version.
     */
    private String version;
    /**
     * Attribute nhinrep
     */
    private Integer nhinRepId;
    /**
     * Attribute nhinRepName
     */
    private String nhinRepName;
    /**
     * Attribute contactName.
     */
    private String contactName;
    /**
     * Attribute contactPhone.
     */
    private String contactPhone;
    /**
     * Attribute contactEmail.
     */
    private String contactEmail;
    /**
     * Attribute initiatorInd.
     */
    private String initiatorInd;
    /**
     * Attribute responderInd.
     */
    private String responderInd;
    /**
     * Attribute ssnHandlingInd.
     */
    private String ssnHandlingInd;
    /**
     * Attribute dynamicContentInd.
     * Requirement specific to Story ID #9. Task 27 on the Sp2 tab.
     */
    private String dynamicContentInd;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute commVerifyStatus.
     */
    private String commVerifyStatus;
    /**
     * Attribute commVerifyTimestamp.
     */
    private Timestamp commVerifyTimestamp;
    /**
     * Attribute createtime.
     */
    private Timestamp createtime;
    /**
     * Attribute createuser.
     */
    private String createuser;
    /**
     * Attribute changedtime.
     */
    private Timestamp changedtime;
    /**
     * Attribute changeduser.
     */
    private String changeduser;
    /**
     * Used to differentiate between lab and conformance.
     * Story 51: Integrate Conformance Tests - Core Library changes
     */
    private Integer labAccessFilter;

    private String resouceIdSendInd ;

    /**
     * List of Participantaudithistory
     */
    private List<Participantaudithistory> participantaudithistorys = null;
    /**
     * List of Participantendpoint
     */
    private List<Participantendpoint> participantendpoints = null;
    /**
     * List of Participantpatientmap
     */
    private List<Participantpatientmap> participantpatientmaps = null;

     /**
     * List of Questionnaireexecution
     */
    private List<Questionnaireresponse> questionnaireresponses = null;
    /**
     * List of Scenarioexecution
     */
    private List<Scenarioexecution> scenarioexecutions = null;
    /**
     * List of Servicesetexecution
     */
    private List<Servicesetexecution> servicesetexecutions = null;

    /**
     * @return participantId
     */
    public Integer getParticipantId() {
        return participantId;
    }

    /**
     * @param participantId new value for participantId
     */
    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    /**
     * @return participantName
     */
    public String getParticipantName() {
        return participantName;
    }

    /**
     * @param participantName new value for participantName
     */
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    /**
     * get user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * set user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return communityId
     */
    public String getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId new value for communityId
     */
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * @return assigningAuthorityId
     */
    public String getAssigningAuthorityId() {
        return assigningAuthorityId;
    }

    /**
     * @param assigningAuthorityId new value for assigningAuthorityId
     */
    public void setAssigningAuthorityId(String assigningAuthorityId) {
        this.assigningAuthorityId = assigningAuthorityId;
    }

    /**
     * @return ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress new value for ipAddress
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
     * get nhinrep
     */
//    public Nhinrep getNhinrep() {
//        if (this.nhinrep == null) {
//            this.nhinrep = new Nhinrep();
//        }
//        return this.nhinrep;
//    }
    /**
     * set nhinrep
     */
//    public void setNhinrep(Nhinrep nhinrep) {
//        this.nhinrep = nhinrep;
//    }
    /**
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName new value for contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone new value for contactPhone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail new value for contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return initiatorInd
     */
    public String getInitiatorInd() {
        return initiatorInd;
    }

    /**
     * @param initiatorInd new value for initiatorInd
     */
    public void setInitiatorInd(String initiatorInd) {
        this.initiatorInd = initiatorInd;
    }

    /**
     * @return responderInd
     */
    public String getResponderInd() {
        return responderInd;
    }

    /**
     * @param responderInd new value for responderInd
     */
    public void setResponderInd(String responderInd) {
        this.responderInd = responderInd;
    }

    /**
     * @return ssnHandlingInd
     */
    public String getSsnHandlingInd() {
        return ssnHandlingInd;
    }

    /**
     * @param ssnHandlingInd new value for ssnHandlingInd
     */
    public void setSsnHandlingInd(String ssnHandlingInd) {
        this.ssnHandlingInd = ssnHandlingInd;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status new value for status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return commVerifyStatus
     */
    public String getCommVerifyStatus() {
        return commVerifyStatus;
    }

    /**
     * @param commVerifyStatus new value for commVerifyStatus
     */
    public void setCommVerifyStatus(String commVerifyStatus) {
        this.commVerifyStatus = commVerifyStatus;
    }

    /**
     * @return commVerifyTimestamp
     */
    public Timestamp getCommVerifyTimestamp() {
        return commVerifyTimestamp;
    }

    /**
     * @param commVerifyTimestamp new value for commVerifyTimestamp
     */
    public void setCommVerifyTimestamp(Timestamp commVerifyTimestamp) {
        this.commVerifyTimestamp = commVerifyTimestamp;
    }

    /**
     * @return createtime
     */
    public Timestamp getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime new value for createtime
     */
    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    /**
     * @return createuser
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser new value for createuser
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    /**
     * @return changedtime
     */
    public Timestamp getChangedtime() {
        return changedtime;
    }

    /**
     * @param changedtime new value for changedtime
     */
    public void setChangedtime(Timestamp changedtime) {
        this.changedtime = changedtime;
    }

    /**
     * @return changeduser
     */
    public String getChangeduser() {
        return changeduser;
    }

    /**
     * @param changeduser new value for changeduser
     */
    public void setChangeduser(String changeduser) {
        this.changeduser = changeduser;
    }

    public String getResouceIdSendInd() {
        return resouceIdSendInd;
    }

    public void setResouceIdSendInd(String resouceIdSendInd) {
        this.resouceIdSendInd = resouceIdSendInd;
    }

    /**
     * Get the list of Participantaudithistory
     */
    public List<Participantaudithistory> getParticipantaudithistorys() {
        return this.participantaudithistorys;
    }

    /**
     * Set the list of Participantaudithistory
     */
    public void setParticipantaudithistorys(List<Participantaudithistory> participantaudithistorys) {
        this.participantaudithistorys = participantaudithistorys;
    }

    /**
     * Get the list of Participantendpoint
     */
    public List<Participantendpoint> getParticipantendpoints() {
        return this.participantendpoints;
    }

    /**
     * Set the list of Participantendpoint
     */
    public void setParticipantendpoints(List<Participantendpoint> participantendpoints) {
        this.participantendpoints = participantendpoints;
    }

    /**
     * Get the list of Participantpatientmap
     */
    public List<Participantpatientmap> getParticipantpatientmaps() {
        return this.participantpatientmaps;
    }

    /**
     * Set the list of Participantpatientmap
     */
    public void setParticipantpatientmaps(List<Participantpatientmap> participantpatientmaps) {
        this.participantpatientmaps = participantpatientmaps;
    }

    /**
     * Get the list of Scenarioexecution
     */
    public List<Scenarioexecution> getScenarioexecutions() {
        return this.scenarioexecutions;
    }

    /**
     * Set the list of Scenarioexecution
     */
    public void setScenarioexecutions(List<Scenarioexecution> scenarioexecutions) {
        this.scenarioexecutions = scenarioexecutions;
    }

    public List<Questionnaireresponse> getQuestionnaireresponses() {
        return questionnaireresponses;
    }

    public void setQuestionnaireresponses(List<Questionnaireresponse> questionnaireresponses) {
        this.questionnaireresponses = questionnaireresponses;
    }

    /**
     * Get the list of Servicesetexecution
     */
    public List<Servicesetexecution> getServicesetexecutions() {
        return this.servicesetexecutions;
    }

    /**
     * Set the list of Servicesetexecution
     */
    public void setServicesetexecutions(List<Servicesetexecution> servicesetexecutions) {
        this.servicesetexecutions = servicesetexecutions;
    }

    /**
     * @return the nhinRepId
     */
    public Integer getNhinRepId() {
        return nhinRepId;
    }

    /**
     * @param nhinRepId the nhinRepId to set
     */
    public void setNhinRepId(Integer nhinRepId) {
        this.nhinRepId = nhinRepId;
    }

    /**
     * @return the nhinRepName
     */
    public String getNhinRepName() {
        return nhinRepName;
    }

    /**
     * @param nhinRepName the nhinRepName to set
     */
    public void setNhinRepName(String nhinRepName) {
        this.nhinRepName = nhinRepName;
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

    public Integer getLabAccessFilter() {
        return labAccessFilter;
    }

    public void setLabAccessFilter(Integer labAccessFilter) {
        this.labAccessFilter = labAccessFilter;
    }


}
