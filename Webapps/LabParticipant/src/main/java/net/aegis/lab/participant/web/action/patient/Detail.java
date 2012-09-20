package net.aegis.lab.participant.web.action.patient;

import java.util.List;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.participant.web.action.BaseAction;
import net.aegis.ri.docrepository.dao.pojo.Document;
import net.aegis.ri.docrepository.manager.DocumentManager;

public class Detail extends BaseAction {

    private static final long serialVersionUID = 1L;

    private Participant participant;
    private String selectedPatientId;
    private Patient patient;
    private List<Patient> patients;
    private List<Document> documents;

    public Detail() {
        this.participant = new Participant();
    }

    public Detail(Participant participant) {
        this.participant = participant;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {

        log.info("Detail.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("Detail.execute() - participant is " + participant.getParticipantName());
            }

            selectedPatientId = this.getRequest().getParameter("selectedPatientId");
            log.info("Detail.execute() - selectedPatientId = " + selectedPatientId);
            patients = (List<Patient>)this.getSession().getAttribute("patients");

            this.getSession().removeAttribute("documents");
            if (selectedPatientId != null && selectedPatientId.length() > 0) {
                this.setPatient(this.findSelectedPatient(selectedPatientId));
                this.setDocuments(DocumentManager.getInstance().getDocumentsForPatient(selectedPatientId));
                this.getSession().setAttribute("documents", this.getDocuments());
            }
        } catch (ServiceException se) {
            log.error("ServiceException", se);
            addActionError(se.getMessage());
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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    private Patient findSelectedPatient(String selectedPatientId) {
        Patient returnPatient = null;

        if (selectedPatientId != null) {
            log.warn("Detail.findPatient() - selectedPatientId is " + selectedPatientId);
            for (Patient findPatient : patients) {
                if (findPatient != null && findPatient.getPatientId().equals(selectedPatientId)) {
                    log.warn("Detail.findPatient() - returnPatient = findPatient");
                    returnPatient = findPatient;
                    break;
                }
            }
        }

        return returnPatient;
    }

}
