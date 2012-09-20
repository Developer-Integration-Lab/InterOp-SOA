package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Pojo mapping table testharnessri</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Testharnessri implements Serializable {

    /**
     * Attribute testharnessId.
     */
    private Integer testharnessId;
    /**
     * Attribute participatingId.
     */
    private Integer participatingId;
    /**
     * Attribute name.
     */
    private String name;
   	/**
     * Attribute version.
     */
    private String version;
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
     * Attribute lastAuditRepositoryId.
     */
    private Integer lastAuditRepositoryId;
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
     * List of Auditsummary
     */
    private List<Auditsummary> auditsummarys = null;
    /**
     * List of Testharnessendpoint
     */
    private List<Testharnessendpoint> testharnessendpoints = null;

    /**
     * @return testharnessId
     */
    public Integer getTestharnessId() {
        return testharnessId;
    }

    /**
     * @param testharnessId new value for testharnessId
     */
    public void setTestharnessId(Integer testharnessId) {
        this.testharnessId = testharnessId;
    }
    
    /**
	 * @return the participatingId
	 */
	public Integer getParticipatingId() {
		return participatingId;
	}

	/**
	 * @param participatingId the participatingId to set
	 */
	public void setParticipatingId(Integer participatingId) {
		this.participatingId = participatingId;
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
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version new value for version
     */
    public void setVersion(String version) {
        this.version = version;
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
     * @return lastAuditRepositoryId
     */
    public Integer getLastAuditRepositoryId() {
        return lastAuditRepositoryId;
    }

    /**
     * @param lastAuditRepositoryId new value for lastAuditRepositoryId
     */
    public void setLastAuditRepositoryId(Integer lastAuditRepositoryId) {
        this.lastAuditRepositoryId = lastAuditRepositoryId;
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
     * Get the list of Auditsummary
     */
    public List<Auditsummary> getAuditsummarys() {
        return this.auditsummarys;
    }

    /**
     * Set the list of Auditsummary
     */
    public void setAuditsummarys(List<Auditsummary> auditsummarys) {
        this.auditsummarys = auditsummarys;
    }

    /**
     * Get the list of Testharnessendpoint
     */
    public List<Testharnessendpoint> getTestharnessendpoints() {
        return this.testharnessendpoints;
    }

    /**
     * Set the list of Testharnessendpoint
     */
    public void setTestharnessendpoints(List<Testharnessendpoint> testharnessendpoints) {
        this.testharnessendpoints = testharnessendpoints;
    }

    @Override
    public String toString() {
        return "name="+name+" communityId="+communityId+" ipAddress="+ipAddress+" testharnessendpoints="+testharnessendpoints;
    }


}
