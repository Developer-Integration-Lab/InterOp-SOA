package net.aegis.lab.web.action.security;

import java.util.List;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.UsersManager;
import net.aegis.lab.web.action.BaseAction;

public class ChangePassword extends BaseAction {

    private static final long serialVersionUID = -1369433190752275555L;
    private String userOption;
    private String currentpassword;
    private String newpassword;
    private String confirmpassword;
    private List<User> userMapList;
    private String username;
    private List<User> allUsers;

    public String getUserOption() {
        return userOption;
    }

    public void setUserOption(String userOption) {
        this.userOption = userOption;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getCurrentpassword() {
        return currentpassword;
    }

    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String execute() throws Exception {

        log.info("Logoff.execute() - INFO");
        loadAllUsers();
        try {
            log.info("  userOption selected" + userOption);
            if (this.getProfile() != null) {
                if (log.isInfoEnabled()) {
                    log.info("Calling change password  for user " + this.getProfile().getUsername());
                }
                if (userOption != null && userOption.equalsIgnoreCase("submit")) {
                    if (!loggedInUserSameAsSelectedUsername() && !getProfile().isAdministrator()) {
                        addActionError("You are not authorized to change password for '"+getUsername()+"'");
                        return SUCCESS;
                    }
                    log.info("ChangePassword userOption submit:'" + userOption+"'");
                    log.info("ChangePassword username:'" + this.getUsername()+"'");
                    // DO NOT PRINT PASSWORDS TO FILE IN PRODUCTION!!
                    //log.info("ChangePassword currentpassword:'" + currentpassword+"'");
                    //log.info("ChangePassword newpassword:'" + newpassword+"'");
                    //log.info("ChangePassword confirmpassword:'" + confirmpassword+"'");

                    if ((newpassword != null) && (confirmpassword != null)) {
                        if ((!currentpassword.trim().equals(newpassword.trim())) && (newpassword.trim().equals(confirmpassword.trim()))) {
                            userMapList = UsersManager.getInstance().findByUsername(this.getUsername());
                            if (userMapList.size() > 0) {
                                User changepassuser = userMapList.get(0);
                                log.info("ChangePassword.execute() - ChangePassword Change Password user size ::::" + userMapList.size());
                                // check if current password matches
                                if (changepassuser.getPassword().trim().equalsIgnoreCase(currentpassword.trim())) {
                                    log.info("ChangePassword.execute() - ChangePassword matched with current password:");
                                    changepassuser.setPassword(newpassword);
                                    UsersManager.getInstance().update(changepassuser, "1");
                                    if (loggedInUserSameAsSelectedUsername()) {
                                        addActionMessage("Your password has been changed successfully.");
                                    } else {
                                        addActionMessage("Password for user '"+getUsername() + "' has been changed successfully.");
                                    }
                                } else {
                                    log.info("ChangePassword.execute() - Current Password does not match password on file.");
                                    addActionError("Current Password does not match password on file.");
                                }
                            } else {
                                addActionError("Username '"+this.getUsername() + "' could not be found");
                            }
                        } else if (currentpassword.trim().equals(newpassword.trim())) {
                            addActionError("'Current Password' and 'New Password' need to be different");
                        } else if (newpassword.trim().equals(confirmpassword.trim())) {
                            addActionError("'New Password' and 'Confirm Password' do not match");
                        }
                    }
                } else {
                    // Set the username to the logged-in username
                    setSelectedUsername();
                }
            } else {
                log.info("Failed to change password as userProfile is null");
            }
            return SUCCESS;
        } catch (Exception e) {
            log.error("Failed to change password for user '"+getUsername() + "' "+ getExceptionMessage(e), e);
            addActionError("Failed to change password for user '"+getUsername() + "' "+ getExceptionMessage(e));
        }

        return SUCCESS;
    }

    private void loadAllUsers()  {
        if (this.getProfile() == null || !this.getProfile().isAdministrator()) {
            return;
        }
        try {
            this.setAllUsers(UsersManager.getInstance().findAll());
        } catch (ServiceException e) {
            log.error("loadUsers() failed", e);
            addActionError("Failed to load all users" + getExceptionMessage(e));
        }
    }

    private void setSelectedUsername() {
        if (this.getProfile()!=null) {
            this.setUsername(this.getProfile().getUsername());
        }
    }

    private String getExceptionMessage(Exception e) {
        return (e.getMessage()!=null ? ": "+e.getMessage() : "");
    }

    private boolean loggedInUserSameAsSelectedUsername() {
        return getProfile().getUsername().equals(getUsername());
    }
}
