package net.aegis.lab.participant.web.action.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.participant.web.action.BaseAction;

import org.apache.struts2.ServletActionContext;

/**
 *
 * @author richard.ettema
 */
public class Logoff extends BaseAction {

    private static final long serialVersionUID = -1369433190752275555L;

    @Override
    public String execute() throws Exception {

        log.info("Logoff.execute() - INFO");

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        try {
            User profile = (User) session.getAttribute("userProfile");
            if (profile != null) {
                if (log.isInfoEnabled()) {
                    log.info("Calling logoff for user " + profile.getUsername());
                }
            }
            session.removeAttribute("userProfile");

            addActionMessage("Log off successful.");
        } catch (Exception e) {
            log.error("Exception", e);
            addActionError(e.getMessage());
        }

        // Invalidate HTTP session
        session.invalidate();

        return SUCCESS;
    }
}
