package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Pojo mapping table nhinrep</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Nhinrep implements Serializable {

    /**
     * Attribute nhinRepId.
     */
    private Integer nhinRepId;
    /**
     * Attribute name.
     */
    private String name;
    /**
     * Attribute user
     */
    private User user;
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
     * Attribute status.
     */
    private String status;
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
     * List of Participant
     */
    private List<Participant> participants = null;
    /**
     * Participant that NhinRep chooses to work with.
     */
    private Participant workingParticipant = null;

    /**
     * @return nhinRepId
     */
    public Integer getNhinRepId() {
        return nhinRepId;
    }

    /**
     * @param nhinRepId new value for nhinRepId
     */
    public void setNhinRepId(Integer nhinRepId) {
        this.nhinRepId = nhinRepId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new value for name
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * Get the list of Participant
     */
    public List<Participant> getParticipants() {
        return this.participants;
    }

    /**
     * Set the list of Participant
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    /**
     * @return Participant that NhinRep chooses to work with.
     */
    public Participant getWorkingParticipant() {
        return workingParticipant;
    }

    /**
     * @param Participant that NhinRep chooses to work with.
     */
    public void setWorkingParticipant(Participant workingParticipant) {
        this.workingParticipant = workingParticipant;
    }
}
