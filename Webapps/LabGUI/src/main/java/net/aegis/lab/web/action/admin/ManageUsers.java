package net.aegis.lab.web.action.admin;


import java.util.List;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.manager.UsersManager;
import net.aegis.lab.web.action.BaseAction;

public class ManageUsers extends BaseAction {

    private static final long serialVersionUID = 1L;
    private List<User> userMapList;
    private User user;
    private String changedPropertyInfo;

    public String getChangedPropertyInfo() {
        return changedPropertyInfo;
    }

    public void setChangedPropertyInfo(String changedPropertyInfo) {
        this.changedPropertyInfo = changedPropertyInfo;
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



    
   
   

    @Override
    public String execute() throws Exception {

       log.info("ManageUsers.execute() - INFO");


       try{
          

              setUserMapList(UsersManager.getInstance().findAll());
              log.info("ManageUsers.execute() - ManageUsers " + userMapList.size());

                // this.addActionMessage("ManageUsers list is populated successfully ");
                   return SUCCESS;
                                                  

                                        }
                
                  
  
       catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        //return SUCCESS;
 
    
   
  
}}