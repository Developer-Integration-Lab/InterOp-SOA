/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.dto;

import net.aegis.lab.dao.pojo.Participant;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class ParticipantPatientMapDto {

    String participantPatientId;
    int participantPatientMapId;
    Participant participant;

    String patientId;

    String patientName;

    String patientAddress;

    String gender;

    String dateOfBirth;

    public String getParticipantPatientId() {
        return participantPatientId;
    }

    public void setParticipantPatientId(String participantPatientId) {
        this.participantPatientId = participantPatientId;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public int getParticipantPatientMapId() {
        return participantPatientMapId;
    }

    public void setParticipantPatientMapId(int participantPatientMapId) {
        this.participantPatientMapId = participantPatientMapId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
