package net.aegis.lab.participant.web.action.patient;

import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.manager.PatientManager;
import net.aegis.lab.participant.web.action.BaseAction;

public class Search extends BaseAction {

    private static final long serialVersionUID = 1L;

    private Participant participant;
    private String doSearch;
    private Patient patient;
    private List<Patient> patients;

    public Search() {
        this.participant = new Participant();
    }

    public Search(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String execute() throws Exception {

        log.info("Search.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("Search.execute() - participant is " + participant.getParticipantName());
            }

            // Check for doSearch
            if (doSearch != null && doSearch.length() > 0) {
                this.processCriteria();
                this.setPatients(PatientManager.getInstance().search(patient));
                this.getSession().setAttribute("patients", this.getPatients());
            }
            else {
                this.getSession().removeAttribute("patients");
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

    public String getDoSearch() {
        return doSearch;
    }

    public void setDoSearch(String doSearch) {
        this.doSearch = doSearch;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    private void processCriteria() {
        if (patient != null) {
            // Check each property for empty value and set to null
            if (patient.getPatientId() != null && patient.getPatientId().equals("")) {
                patient.setPatientId(null);
            }

            if (patient.getFirstName() != null && patient.getFirstName().equals("")) {
                patient.setFirstName(null);
            }

            if (patient.getLastName() != null && patient.getLastName().equals("")) {
                patient.setLastName(null);
            }

            if (patient.getAddressLine1() != null && patient.getAddressLine1().equals("")) {
                patient.setAddressLine1(null);
            }

            if (patient.getAddressLine2() != null && patient.getAddressLine2().equals("")) {
                patient.setAddressLine2(null);
            }

            if (patient.getCity() != null && patient.getCity().equals("")) {
                patient.setCity(null);
            }

            if (patient.getState() != null && patient.getState().equals("")) {
                patient.setState(null);
            }

            if (patient.getZipCode() != null && patient.getZipCode().equals("")) {
                patient.setZipCode(null);
            }

            if (patient.getHomePhone() != null && patient.getHomePhone().equals("")) {
                patient.setHomePhone(null);
            }

            if (patient.getWorkPhone() != null && patient.getWorkPhone().equals("")) {
                patient.setWorkPhone(null);
            }

            if (patient.getDateOfBirthStr() != null && patient.getDateOfBirthStr().equals("")) {
                patient.setDateOfBirthStr(null);
            }

            if (patient.getSsn() != null && patient.getSsn().equals("")) {
                patient.setSsn(null);
            }

            if (patient.getGender() != null && patient.getGender().equals("")) {
                patient.setGender(null);
            }

        }
    }

}
