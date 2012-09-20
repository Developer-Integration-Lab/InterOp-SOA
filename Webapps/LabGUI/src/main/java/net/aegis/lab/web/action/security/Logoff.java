package net.aegis.lab.web.action.security;

import net.aegis.lab.web.action.BaseAction;

/**
 *
 * @author richard.ettema
 */
public class Logoff extends BaseAction {

    private static final long serialVersionUID = -1369433190752275555L;

    @Override
    public String execute() throws Exception {

        log.info("Logoff.execute() - INFO");

        try {
            if (this.getProfile() != null) {
                if (log.isInfoEnabled()) {
                    log.info("Calling logoff for user " + this.getProfile().getUsername());
                }
            }
            this.getSession().removeAttribute("userProfile");

            addActionMessage("Log off successful.");
        } catch (Exception e) {
            log.error("Exception", e);
            addActionError(e.getMessage());
        }

        // Invalidate HTTP session
        this.getSession().invalidate();

        return SUCCESS;
    }
}
