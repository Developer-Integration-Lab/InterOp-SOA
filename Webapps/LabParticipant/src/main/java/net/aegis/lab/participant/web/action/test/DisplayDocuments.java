package net.aegis.lab.participant.web.action.test;

import net.aegis.lab.participant.web.action.BaseAction;

public class DisplayDocuments extends BaseAction {

    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {

        log.info("DisplayDocuments.execute() - INFO");

        return SUCCESS;
    }

}
