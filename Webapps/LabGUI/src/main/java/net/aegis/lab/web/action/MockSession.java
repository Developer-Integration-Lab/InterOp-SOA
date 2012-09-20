package net.aegis.lab.web.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Unfortunately, we were using HttpRequest and HttpSession APIs directly in our code base in too many places.  This class was created to offer the same
 * APIs but allow us to test our web-tier actions from junit in an automated fashion.
 * 
 * @author Tareq.Nabeel
 */
public class MockSession {

    protected final static Log logger = LogFactory.getLog(MockSession.class.getName());
    private BaseAction baseAction;

    protected MockSession(BaseAction baseAction) {
        this.baseAction = baseAction;
    }

    public Object getAttribute(String attributeName) {
        return baseAction.getSessionAttributes().get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        baseAction.getSessionAttributes().put(attributeName, attributeValue);
    }

    public void removeAttribute(String attributeName) {
        baseAction.getSessionAttributes().remove(attributeName);
    }

    public void invalidate() {
        if (baseAction.getSessionAttributes() instanceof org.apache.struts2.dispatcher.SessionMap) {
            try {
                ((org.apache.struts2.dispatcher.SessionMap) baseAction.getSessionAttributes()).invalidate();
            } catch (IllegalStateException e) {
                logger.error("session invalidate failed", e);
            }
        }
    }
}
