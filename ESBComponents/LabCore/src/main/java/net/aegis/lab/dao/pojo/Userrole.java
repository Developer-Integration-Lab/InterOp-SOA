package net.aegis.lab.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table userrole</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Userrole implements Serializable {

    /**
     * Attribute user
     */
    private User user;
    /**
     * Attribute role
     */
    private Role role;
    /**
     * Attribute userrolePK
     */
    private UserrolePK userrolePK;

    /**
     * get user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * set user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * get role
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * set role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * get userrolePK
     */
    public UserrolePK getUserrolePK() {
        return userrolePK;
    }

    /**
     * set userrolePK
     */
    public void setUserrolePK(UserrolePK userrolePK) {
        this.userrolePK = userrolePK;
    }

    /**
     * <p>Composite primary key for table userrole</p>
     *
     * <p>Generated at Sat Feb 06 09:46:38 EST 2010</p>
     * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
     */
    public static class UserrolePK implements Serializable {

        /**
         * Attribute userId
         */
        private int userId;
        /**
         * Attribute roleId
         */
        private int roleId;

        /**
         * Return userId
         */
        public int getUserId() {
            return userId;
        }

        /**
         * @param userId new value for userId
         */
        public void setUserId(int userId) {
            this.userId = userId;
        }

        /**
         * Return roleId
         */
        public int getRoleId() {
            return roleId;
        }

        /**
         * @param roleId new value for roleId
         */
        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        /**
         * calculate hashcode
         */
        public int hashCode() {
            //TODO : implement this method
            return super.hashCode();
        }

        /**
         * equals method
         */
        public boolean equals(Object object) {
            //TODO : implement this method
            return super.equals(object);
        }
    }
}
