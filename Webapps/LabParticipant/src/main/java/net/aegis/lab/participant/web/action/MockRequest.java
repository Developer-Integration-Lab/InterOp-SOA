package net.aegis.lab.participant.web.action;

/**
 * Unfortunately, we were using HttpRequest and HttpSession APIs directly in our code base in too many places.  This class was created to offer the same
 * APIs but allow us to test our web-tier actions from junit in an automated fashion.
 *
 * @author Tareq.Nabeel
 */
public class MockRequest {

    private BaseAction baseAction;

    protected MockRequest(BaseAction baseAction) {
        this.baseAction = baseAction;
    }

    public String getParameter(String name) {
        String[] params = baseAction.getParameters().get(name);
        if (params != null && params.length > 0) {
            return params[0];
        } else {
            return null;
        }
    }

    public MockSession getSession() {
        return baseAction.getSession();
    }

    public Object getAttribute(String attributeName) {
        return baseAction.getRequestAttributes().get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        baseAction.getRequestAttributes().put(attributeName, attributeValue);
    }
}
