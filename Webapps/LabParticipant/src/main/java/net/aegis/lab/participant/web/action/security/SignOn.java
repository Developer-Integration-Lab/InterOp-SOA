package net.aegis.lab.participant.web.action.security;

import net.aegis.lab.participant.web.action.BaseAction;

public class SignOn extends BaseAction {

    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {

        log.info("SignOn.execute() - INFO");

//        HttpServletRequest request = ServletActionContext.getRequest();

        return SUCCESS;
    }
}
