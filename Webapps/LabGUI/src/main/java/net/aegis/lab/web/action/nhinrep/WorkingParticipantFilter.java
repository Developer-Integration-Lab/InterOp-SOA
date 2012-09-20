package net.aegis.lab.web.action.nhinrep;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An intercepting filter that first checks whether user session still valid. If
 * session not valid, redirects the user to the signon page.  Later performs the
 * job of checking and setting a working participant for the nhin rep that is
 * logged in.
 *
 * @see web.xml - for url mapping patterns.
 * @author Abhay.Bakshi
 */
public class WorkingParticipantFilter implements Filter {

    private FilterConfig config;
    private final Log log = LogFactory.getLog(getClass().getName());

    /**
     * empty constructor
     */
    public WorkingParticipantFilter() {
        log.info("WorkingParticipantFilter.empty constructor - INFO");
    }

    /**
     * Called only when the web container loads the application.
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        log.info("WorkingParticipantFilter.init() - INFO");
        this.config = filterConfig;
    }

    /**
     * Performs the job of checking and setting a working participant for the nhin rep that is logged in.
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("WorkingParticipantFilter.doFilter() - INFO");

        boolean bSessionValid = false;
        User profile = null;
        HttpSession hsession = null;
        HttpServletRequest hreq = (HttpServletRequest)request;
        HttpServletResponse hres = (HttpServletResponse)response;

        hsession = hreq.getSession(false);
        if (hsession != null) {
            profile = (User) hsession.getAttribute("userProfile");
        }

        if (profile != null) {
           bSessionValid = true;
        }

        if (bSessionValid) {
            // proceed with the requested page
            log.info("WorkingParticipantFilter.doFilter() - session is valid.");

            // Requirement: An NHIN REP chooses a participant to work with.
            fixparticipantThatNhinRepWorksWith(hreq, hres, hsession);

            hres.sendRedirect(config.getServletContext().getContextPath() + "/nhinrep/NhinrepDashboard");
            return;
        }
        else {
            // take them to the login page
            log.error("WorkingParticipantFilter.doFilter() - session not valid. redirecting to re-login..");
            hres.sendRedirect(config.getServletContext().getContextPath() + "/security/SignOn");
            return;
        }

    }

    /**
     * Called only when the web container unloads the web application.
     */
    public void destroy() {
        log.info("WorkingParticipantFilter.destroy() - INFO");
    }

    /**
     * Requirement: An NHIN REP chooses a participant to work with.
     * 
     * @param hreq
     * @param hres
     * @param hsession
     */
    private void fixparticipantThatNhinRepWorksWith(HttpServletRequest hreq, HttpServletResponse hres, HttpSession hsession)
        throws IOException, ServletException {

        Participant objParticipantThatNhinRepWorksWith = null;
        String strParticipantId = null;
        Integer iParticipantId = null;

        strParticipantId = hreq.getParameter("participantId");

        // check whether correct parameters passed in the http request
        if((null == strParticipantId) || (strParticipantId.trim().equalsIgnoreCase(""))) {
            log.error("WorkingParticipantFilter.fixparticipantThatNhinRepWorksWith() - unknown parameter passed.. returning..");
            return;
        }

        // parse the parameter passed
        try {
            iParticipantId = Integer.parseInt(strParticipantId);
        }
        catch(Exception e) {
            log.error("WorkingParticipantFilter.fixparticipantThatNhinRepWorksWith() - strange parameter passed.. non-parsable.. strParticipantId="+strParticipantId);
            log.error("WorkingParticipantFilter.fixparticipantThatNhinRepWorksWith() - returning..");
            return;
        }

        // good parameter, now search database
        try {
            objParticipantThatNhinRepWorksWith = ParticipantManager.getInstance().findByParticipantId(iParticipantId);
        } catch (ServiceException ex) {
            log.error("WorkingParticipantFilter.fixparticipantThatNhinRepWorksWith() - cannot find participant by id="+iParticipantId);
            log.error("WorkingParticipantFilter.fixparticipantThatNhinRepWorksWith() - returning..");
            return;
        }

        // participant must be present in the database, otherwise log error and simply return
        if(null == objParticipantThatNhinRepWorksWith) {
            log.error("WorkingParticipantFilter.fixparticipantThatNhinRepWorksWith() - participant to find is null in database.. returning..");
            return;
        }

        // participant found in database, now attach it to the nhin rep
        hsession.setAttribute(LabConstants.PARTICIPANT_THAT_NHINREP_WORKS_WITH, objParticipantThatNhinRepWorksWith);
        log.info("WorkingParticipantFilter.fixparticipantThatNhinRepWorksWith() - participant " +
                objParticipantThatNhinRepWorksWith.getParticipantName() + " is attached to session..");
        return;
    }

}

