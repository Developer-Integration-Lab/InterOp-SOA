/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.dto;

import java.sql.Timestamp;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class TMWindowMsgsDto {

    public Timestamp timestamp;

    public String service;

    public String direction;

    public String fromHCID;

    public String toHCID;

    public Integer testHarnessId;

    public Integer auditRepoId;

    public String status;

    public String serviceSetId;

    public boolean hcidFlag;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFromHCID() {
        return fromHCID;
    }

    public void setFromHCID(String fromHCID) {
        this.fromHCID = fromHCID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getToHCID() {
        return toHCID;
    }

    public void setToHCID(String toHCID) {
        this.toHCID = toHCID;
    }

     public Integer getAuditRepoId() {
        return auditRepoId;
    }

    public void setAuditRepoId(Integer auditRepoId) {
        this.auditRepoId = auditRepoId;
    }

    public Integer getTestHarnessId() {
        return testHarnessId;
    }

    public void setTestHarnessId(Integer testHarnessId) {
        this.testHarnessId = testHarnessId;
    }

    public boolean isHcidFlag() {
        return hcidFlag;
    }

    public void setHcidFlag(boolean hcidFlag) {
        this.hcidFlag = hcidFlag;
    }

    public String getServiceSetId() {
        return serviceSetId;
    }

    public void setServiceSetId(String serviceSetId) {
        this.serviceSetId = serviceSetId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
