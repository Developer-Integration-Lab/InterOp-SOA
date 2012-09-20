/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.dto;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class ValidationDto {

    private int validationId;
    private String executionUniqueId;
    private String serviceSet;
    private String submittedOn;
    private String assignedTo;
    private Integer labAccessFilter;

    public int getValidationId() {
        return validationId;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getExecutionUniqueId() {
        return executionUniqueId;
    }

    public void setExecutionUniqueId(String executionUniqueId) {
        this.executionUniqueId = executionUniqueId;
    }

    public String getServiceSet() {
        return serviceSet;
    }

    public void setServiceSet(String serviceSet) {
        this.serviceSet = serviceSet;
    }

    public String getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(String submittedOn) {
        this.submittedOn = submittedOn;
    }

     public Integer getLabAccessFilter() {
        return labAccessFilter;
    }

    public void setLabAccessFilter(Integer labAccessFilter) {
        this.labAccessFilter = labAccessFilter;
    }
}
