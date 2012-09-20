package net.aegis.lab.web.action.security;

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

import net.aegis.lab.dao.pojo.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An intercepting filter that checks whether user session still valid. If
 * session not valid, redirects the user to the signon page.
 *
 * @see web.xml - for url mapping patterns.
 * @author Abhay.Bakshi
 */
public class SessionFilter implements Filter {

    private FilterConfig config;
    private final Log log = LogFactory.getLog(getClass().getName());

    /**
     * empty constructor
     */
    public SessionFilter() {
        log.info("SessionFilter.empty constructor - INFO");
    }

    /**
     * Called only when the web container loads the application.
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        log.info("SessionFilter.init() - INFO");
        this.config = filterConfig;
    }

    /**
     * Performs the job of checking the user session validity and response redirect.
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("SessionFilter.doFilter() - INFO");

        boolean bSessionValid = false;
        User profile = null;
        HttpSession session = null;
        HttpServletRequest hreq = (HttpServletRequest)request;
        HttpServletResponse hres = (HttpServletResponse)response;

        session = hreq.getSession(false);
        if (session != null) {
            profile = (User) session.getAttribute("userProfile");
        }

        if (profile != null) {
           bSessionValid = true;
        }

        if (bSessionValid) {
            // proceed with the requested page
            log.info("SessionFilter.doFilter() - session is valid.");
            chain.doFilter( request, response );
            return;
        }
        else {
            // take them to the login page
            log.error("SessionFilter.doFilter() - session not valid. redirecting to re-login..");
            hres.sendRedirect(config.getServletContext().getContextPath() + "/security/SignOn");
            return;
        }
        
        

    }

    /**
     * Called only when the web container unloads the web application.
     */
    public void destroy() {
        log.info("SessionFilter.destroy() - INFO");
    }


}
