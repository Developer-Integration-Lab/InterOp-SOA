package net.aegis.lab.participant.web.action;

import java.util.Map;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware, RequestAware, ParameterAware {

    protected final Log log = LogFactory.getLog(getClass().getName());

    protected static final String ERROR = "error";

    private Map<String, Object> sessionAttributes;
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> parameters;

    private MockRequest request;
    private MockSession session;
    private User profile;


    public BaseAction() {
        session = new MockSession(this); 
        request = new MockRequest(this);
    }

    public User getProfile() {
        if (profile == null) {
            profile = (User) this.getSession().getAttribute("userProfile");
        }
        return profile;
    }

    public void setProfile(User profile) {
        this.profile = profile;
    }

    public MockRequest getRequest() {
        return request;
    }

    public MockSession getSession() {
        return session;
    }


    // HELPER METHODS

    public String processException(Exception e) {
        StringBuffer errMsg = new StringBuffer("");
        StackTraceElement stackTrace[] = e.getStackTrace();

        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement element : stackTrace) {
                errMsg.append(element.toString()).append("<br>");
            }
        }
        addActionError(errMsg.toString());

        if (e instanceof ServiceException) {
            ServiceException se = (ServiceException)e;
            if (se.getExceptionMessage() != null && se.getExceptionMessage().length() > 0) {
                addActionMessage(se.getExceptionMessage());
            }
            else {
                addActionMessage(se.getMessage());
            }
        }
        else {
            addActionMessage(e.getMessage());
        }

        return ERROR;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public Map<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    @Override
    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    @Override
    public void setRequest(Map<String, Object> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    @Override
    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }
}
