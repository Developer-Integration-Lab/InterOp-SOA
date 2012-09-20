package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Pojo mapping table role</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Role implements Serializable {

    /**
     * Attribute roleId.
     */
    private Integer roleId;
    /**
     * Attribute rolename.
     */
    private String rolename;
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
    /**
     * List of Userrole
     */
    private List<Userrole> userroles = null;

    /**
     * @return roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId new value for roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename new value for rolename
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
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
     * Get the list of Userrole
     */
    public List<Userrole> getUserroles() {
        return this.userroles;
    }

    /**
     * Set the list of Userrole
     */
    public void setUserroles(List<Userrole> userroles) {
        this.userroles = userroles;
    }

    public Integer getLabAccessFilter() {
        return labAccessFilter;
    }

    public void setLabAccessFilter(Integer labAccessFilter) {
        this.labAccessFilter = labAccessFilter;
    }
}
