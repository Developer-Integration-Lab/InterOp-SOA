package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Pojo mapping table auditsummary</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:35 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Auditsummary implements Serializable {

    /**
     * Attribute summaryId.
     */
    private Integer summaryId;
    /**
     * Attribute repositoryId.
     */
    private Integer repositoryId;
    /**
     * Attribute testharnessri
     */
    private Testharnessri testharnessri;
    /**
     * Attribute caseresult
     */
    private Caseresult caseresult;
    /**
     * Attribute type.
     */
    private String type;
    /**
     * Attribute typeCode.
     */
    private String typeCode;
    /**
     * Attribute outcomeIndicator.
     */
    private Integer outcomeIndicator;
    /**
     * Attribute userId.
     */
    private String userId;
    /**
     * Attribute userName.
     */
    private String userName;
    /**
     * Attribute messageType.
     */
    private String messageType;
    /**
     * Attribute actionCode.
     */
    private String actionCode;
    /**
     * Attribute networkAccessPointId.
     */
    private String networkAccessPointId;
    /**
     * Attribute patientId.
     */
    private String patientId;
    /**
     * Attribute enterpriseSourceSite.
     */
    private String enterpriseSourceSite;
    /**
     * Attribute enterpriseSourceId.
     */
    private String enterpriseSourceId;
    /**
     * Attribute executeTime.
     */
    private Timestamp executeTime;
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
     * @return summaryId
     */
    public Integer getSummaryId() {
        return summaryId;
    }

    /**
     * @param summaryId new value for summaryId
     */
    public void setSummaryId(Integer summaryId) {
        this.summaryId = summaryId;
    }

    /**
     * @return repositoryId
     */
    public Integer getRepositoryId() {
        return repositoryId;
    }

    /**
     * @param repositoryId new value for repositoryId
     */
    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * get testharnessri
     */
    public Testharnessri getTestharnessri() {
        return this.testharnessri;
    }

    /**
     * set testharnessri
     */
    public void setTestharnessri(Testharnessri testharnessri) {
        this.testharnessri = testharnessri;
    }

    /**
     * get caseresult
     */
    public Caseresult getCaseresult() {
        return this.caseresult;
    }

    /**
     * set caseresult
     */
    public void setCaseresult(Caseresult caseresult) {
        this.caseresult = caseresult;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type new value for type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * @param typeCode new value for typeCode
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * @return outcomeIndicator
     */
    public Integer getOutcomeIndicator() {
        return outcomeIndicator;
    }

    /**
     * @param outcomeIndicator new value for outcomeIndicator
     */
    public void setOutcomeIndicator(Integer outcomeIndicator) {
        this.outcomeIndicator = outcomeIndicator;
    }

    /**
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId new value for userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName new value for userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType new value for messageType
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return actionCode
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * @param actionCode new value for actionCode
     */
    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * @return networkAccessPointId
     */
    public String getNetworkAccessPointId() {
        return networkAccessPointId;
    }

    /**
     * @param networkAccessPointId new value for networkAccessPointId
     */
    public void setNetworkAccessPointId(String networkAccessPointId) {
        this.networkAccessPointId = networkAccessPointId;
    }

    /**
     * @return patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId new value for patientId
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return enterpriseSourceSite
     */
    public String getEnterpriseSourceSite() {
        return enterpriseSourceSite;
    }

    /**
     * @param enterpriseSourceSite new value for enterpriseSourceSite
     */
    public void setEnterpriseSourceSite(String enterpriseSourceSite) {
        this.enterpriseSourceSite = enterpriseSourceSite;
    }

    /**
     * @return enterpriseSourceId
     */
    public String getEnterpriseSourceId() {
        return enterpriseSourceId;
    }

    /**
     * @param enterpriseSourceId new value for enterpriseSourceId
     */
    public void setEnterpriseSourceId(String enterpriseSourceId) {
        this.enterpriseSourceId = enterpriseSourceId;
    }

    /**
     * @return executeTime
     */
    public Timestamp getExecuteTime() {
        return executeTime;
    }

    /**
     * @param executeTime new value for executeTime
     */
    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
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

    @Override
    public String toString() {
        return "\n\tAuditSummary: "+"\n\t\ttype="+type + "\n\t\ttypeCode="+typeCode+"\n\t\tmessageType="+messageType+"\n\t\tactionCode="+actionCode+"\n\t\toutcomeIndicator="+outcomeIndicator + "\n\t\tnetworkAccessPointId="+networkAccessPointId+"\n\t\tpatientId="+patientId+ "\n\t\tenterpriseSourceSite="+enterpriseSourceSite + "\n\t\tenterpriseSourceId="+enterpriseSourceId;
        //this.userId
        //this.executeTime
        //this.createtime
        //this.createuser
        //this.changedtime
        //this.changeduser
        //this.repositoryId
        //this.userName;
        //this.testharnessri  // ALWAYS empty state (null values)
        //this.summaryId  // always null
    }


}
