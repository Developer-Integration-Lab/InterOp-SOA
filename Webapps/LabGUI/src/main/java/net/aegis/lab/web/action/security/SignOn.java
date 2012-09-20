package net.aegis.lab.web.action.security;

import net.aegis.lab.web.action.BaseAction;

public class SignOn extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String timeOut;

    @Override
    public String execute() throws Exception {

        log.info("SignOn.execute() - INFO");

        if (timeOut != null && !timeOut.equals("")) {
            // timeOut indicator is true
            addActionError(getText("errors.session.timeout"));
        }

        return SUCCESS;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

}
