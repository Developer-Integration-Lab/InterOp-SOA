package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Pojo mapping table participantpatientmap</p>
 * <p></p>
 *
 * <p>Generated at Sat May 01 22:11:04 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Participantpatientmap implements Serializable {

    /**
     * Attribute participantPatientMapId.
     */
    private Integer participantPatientMapId;
    /**
     * Attribute participant
     */
    private Participant participant;
    /**
     * Attribute patient
     */
    private Patient patient;
    /**
     * Attribute participantPatientId.
     */
    private String participantPatientId;
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
     * @return participantPatientMapId
     */
    public Integer getParticipantPatientMapId() {
        return participantPatientMapId;
    }

    /**
     * @param participantPatientMapId new value for participantPatientMapId
     */
    public void setParticipantPatientMapId(Integer participantPatientMapId) {
        this.participantPatientMapId = participantPatientMapId;
    }

    /**
     * get participant
     */
    public Participant getParticipant() {
        return this.participant;
    }

    /**
     * set participant
     */
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    /**
     * get patient
     */
    public Patient getPatient() {
        return this.patient;
    }

    /**
     * set patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * @return participantPatientId
     */
    public String getParticipantPatientId() {
        return participantPatientId;
    }

    /**
     * @param participantPatientId new value for participantPatientId
     */
    public void setParticipantPatientId(String participantPatientId) {
        this.participantPatientId = participantPatientId;
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
}
