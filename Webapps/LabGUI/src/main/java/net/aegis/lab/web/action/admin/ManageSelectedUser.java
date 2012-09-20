package net.aegis.lab.web.action.admin;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.manager.UsersManager;
import net.aegis.lab.web.action.BaseAction;

public class ManageSelectedUser extends BaseAction {

    private static final long serialVersionUID = 1L;
    private List<User> userMapList;
    private User user;
    private String userOption;
    private String userStatus;
    private String username;
    private String userupdatedComments;
     private String upduserName;

    public String getUpduserName() {
        return upduserName;
    }

    public void setUpduserName(String upduserName) {
        this.upduserName = upduserName;
    }



    public String getUserOption() {
        return userOption;
    }

    public void setUserOption(String userOption) {
        this.userOption = userOption;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserMapList() {
        return userMapList;
    }

    public void setUserMapList(List<User> userMapList) {
        this.userMapList = userMapList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserupdatedComments() {
        return userupdatedComments;
    }

    public void setUserupdatedComments(String userupdatedComments) {
        this.userupdatedComments = userupdatedComments;
    }



    
   
   

    @Override
    public String execute() throws Exception {

        log.info("ManageUsers.execute() - INFO");

        Date currentTime = new Date();
        Long time = currentTime.getTime();
        try {


            setUserMapList(UsersManager.getInstance().findByUsername(username));
            log.info("ManageUsers.execute() - ManageUsers " + userMapList.size());

            if (userOption != null) {
                if (userOption.equals("resetpass")) {
                    log.info("ManageUsers.execute() - ManageUsers  resetPass option selected ");
                    setUserMapList(UsersManager.getInstance().findByUsername(upduserName));
                if (getUserMapList().size() > 0) {
                        log.info("ManageUsers.execute() - ManageUsers resetPass  size ::::" + getUserMapList().size());
                        User resetuser = getUserMapList().get(0);
                        resetuser.setPassword("password1!");
                        resetuser.setInvalidAttempts(0);
                        UsersManager.getInstance().update(resetuser, "1");
                        
                }
                    this.addActionMessage("User password reset completed successfully ");
                }

                if (userOption.equals("update")) {

                    log.info("ManageUsers.execute() - ManageUsers  update option selected upduserNameupduserName" + upduserName);
                    setUserMapList(UsersManager.getInstance().findByUsername(upduserName));

                    if (getUserMapList().size() > 0) {
                        log.info("ManageUsers.execute() - ManageUsers  update size ::::" + getUserMapList().size());
                        User updateduser = getUserMapList().get(0);
                        if (userStatus != null) {
                            if (userStatus.equals("T")) {
                                if (userupdatedComments != null) {
                                    log.info("ManageUsers.execute() - ManageUsers  " + updateduser.getUsername());
                                    updateduser.setComments(userupdatedComments);
                                    updateduser.setStatus(userStatus);
                                    updateduser.setExpirationTime(new Timestamp(time));
                                    updateduser.setChangedtime(new Timestamp(time));
                                    updateduser.setChangeduser("Admin");
                                    UsersManager.getInstance().update(updateduser, "1");
                                }

                            } else {  // update user only with status column update
                                    updateduser.setStatus(userStatus);
                                    UsersManager.getInstance().update(updateduser, "1");
                                    updateduser.setChangedtime(new Timestamp(time));
                                    updateduser.setChangeduser("Admin");
                            }

                        }

                        this.addActionMessage("User updated successfully ");

                    }
                }

            }




            return SUCCESS;


        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }


 
    
   
  
}}