package net.aegis.lab.participant.web.action.security;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.SecurityManager;
import net.aegis.lab.participant.web.action.BaseAction;

public class Login extends BaseAction {

    private static final long serialVersionUID = -1369433190752273744L;

    @Override
    public String execute() throws Exception {

        log.info("Login.execute() - INFO");

        if (isInvalid(getUsername())) return INPUT;

        if (isInvalid(getPassword())) return INPUT;

        try {
            User profile = SecurityManager.getInstance().authenticate(getUsername(), getPassword());

            if (profile == null) {
                // user not found; report error and keep user on login
                addActionError("User/password not found!");
                return INPUT;
            }

            this.getSession().setAttribute("userProfile", profile);
            this.getSession().setAttribute("applicationVersionNumber", getText("application.version.number"));
            this.getSession().setAttribute("applicationBuildNumber", getText("application.build.number"));
            this.getSession().setAttribute("applicationBuildTimestamp", getText("application.build.timestamp"));
        }
        catch (ServiceException e) {
            log.error("ServiceException", e);
            /*SQLException se = e.getSQLException();
            if (se != null) {
                addActionError(se.getMessage());
            }
            else {*/
                addActionError(e.getMessage());
//            }
            return INPUT;
        }
        catch (Exception e) {
            log.error("Exception", e);
            addActionError(e.getMessage());
            return INPUT;
        }

        return SUCCESS;
    }

    private boolean isInvalid(String value) {
        return (value == null || value.length() == 0);
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
