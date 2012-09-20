/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.dto;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.dao.pojo.Userrole;
/**
 *
 * @author Sree Hari Devineni
 */
public class UsersDto implements Serializable{

   
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_LOCKED = "L";
    public static final String STATUS_TERMINATED = "T";

    /**
     * Attribute userId.
     */
    private Integer userId;
    /**
     * Attribute username.
     */
    private String username;
    /**
     * Attribute password.
     */
    private String password;
    /**
     * Attribute status.
     */
    private String status;
    /**
     * Attribute expirationTime.
     */
    private Timestamp expirationTime;
    /**
     * Attribute invalidAttempts.
     */
    private Integer invalidAttempts;
    /**
     * Attribute securityReminder.
     */
    private String securityReminder;
    /**
     * Attribute comments.
     */
    private String comments;
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
   
    private List<Userrole> userroles = null;

    // Helper properties
    private boolean invalidLoginAttempt;

    /**
     * @return userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId new value for userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username new value for username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password new value for password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return expirationTime
     */
    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    /**
     * @param expirationTime new value for expirationTime
     */
    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * @return invalidAttempts
     */
    public Integer getInvalidAttempts() {
        return invalidAttempts;
    }

    /**
     * @param invalidAttempts new value for invalidAttempts
     */
    public void setInvalidAttempts(Integer invalidAttempts) {
        this.invalidAttempts = invalidAttempts;
    }

    /**
     * @return securityReminder
     */
    public String getSecurityReminder() {
        return securityReminder;
    }

    /**
     * @param securityReminder new value for securityReminder
     */
    public void setSecurityReminder(String securityReminder) {
        this.securityReminder = securityReminder;
    }

    /**
     * @return comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments new value for comments
     */
    public void setComments(String comments) {
        this.comments = comments;
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

    // Helper property methods
    public boolean isInvalidLoginAttempt() {
        return invalidLoginAttempt;
    }

    public void setInvalidLoginAttempt(boolean invalidLoginAttempt) {
        this.invalidLoginAttempt = invalidLoginAttempt;
    }

    public int getPrimaryRole() {
        int primaryRole = 0;

        if (this.userroles != null && this.userroles.size() > 0) {
            primaryRole = this.userroles.get(0).getRole().getRoleId().intValue();
        }

        return primaryRole;
    }

   
   
   


}
