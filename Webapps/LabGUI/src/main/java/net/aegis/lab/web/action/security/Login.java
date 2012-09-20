package net.aegis.lab.web.action.security;

import java.util.List;

import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.SecurityManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.util.LabConstants.LabType;
import net.aegis.lab.web.action.BaseAction;

public class Login extends BaseAction {

    private static final long serialVersionUID = -1369433190752273744L;

    private static final String ADMINISTRATOR_DASHBOARD = "admin_dashboard";
    //private static final String PARTICIPANT_DASHBOARD = "participant_dashboard";
    public static final String PARTICIPANT = "/participant";
    public static final String PARTICIPANT_DASHBOARD = "ParticipantDashboard";
    public static final String CONFORMANCE_PARTICIPANT = "/conformance/participant";
    public static final String SETUPTEST = "SetUpTest";
    public static final String TESTLAB_SECURITY = "/security";
    public static final String TESTLAB_SIGN_ON = "SignOn";
    private static final String LANDINGPAGE = "landingPage";
    private static final String NHIN_ADMINISTRATOR_DASHBOARD = "nhinadmin_dashboard";
    private static final String NHIN_REP_DASHBOARD = "nhinrep_dashboard";
    private static final String NHIN_VALID_BODY_DASHBOARD = "nhinvalid_dashboard";
    private String dashboard;
    private String ns;

    @Override
    public String execute() throws Exception {

        log.info("Login.execute() - INFO");

        if (isInvalid(getUsername())) {
            return INPUT;
        }

        if (isInvalid(getPassword())) {
            return INPUT;
        }

        try {
            // This action class assigns the user profile - DO NOT USE this.getProfile()
            User profile = SecurityManager.getInstance().authenticate(getUsername(), getPassword());

            if (profile == null) {
                // user not found; report error and keep user on login
                addActionError(getText("errors.login.failed.invalid.username"));
                return INPUT;
            }

            this.getSession().setAttribute("userProfile", profile);
            this.getSession().setAttribute("applicationVersionNumber", getText("application.version.number"));
            this.getSession().setAttribute("applicationBuildNumber", getText("application.build.number"));
            this.getSession().setAttribute("applicationBuildTimestamp", getText("application.build.timestamp"));

            return this.determineRoleForward(profile);
        } catch (ServiceException e) {
            if (!e.isLogged()) {
                log.error("ServiceException", e);
            }
            /*SQLException se = e.getSQLException();
            if (se != null) {
                addActionError(getText(e.getErrorCode()));
                addActionMessage(se.getMessage());
            }*/ else {
                addActionError(getText(e.getErrorCode()));
            }
            return INPUT;
        }
    }

    private boolean isInvalid(String value) {
        return (value == null || value.length() == 0);
    }
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String determineRoleForward(User profile) throws ServiceException {
        String forward = INPUT;

        if (profile != null) {
            int roleId = profile.getPrimaryRole();
            switch (roleId) {
                case 1:
                    forward = ADMINISTRATOR_DASHBOARD;
                    break;
                case 2:
                    forward = NHIN_ADMINISTRATOR_DASHBOARD;
                    break;
                case 3:
                    forward = NHIN_REP_DASHBOARD;
                    break;
                case 4:
                    forward = NHIN_VALID_BODY_DASHBOARD;
                    break;
                case 5:
                    Integer participantId = profile.getParticipant().getParticipantId();
                    /* 1. get active sevice sets in conformance and if found apply Global conformance labtype filter first
                     * 2. if none found go to conformance set up test
                     * 3. if step 1 is false and if participant has IOP lab access, and then an active service is found goto IOP participant dash else participant setup test.
                    */
                    /* Conformance code */
                    List<Servicesetexecution> activeServiceSets = ServiceSetExecutionManager.getInstance().participantActiveServiceSets(participantId,LabType.CONFORMANCE);
                    if (activeServiceSets != null && activeServiceSets.size() > 0) {
                        setLandingPage(PARTICIPANT_DASHBOARD, CONFORMANCE_PARTICIPANT);
                    }
                    else {
                        if (ParticipantManager.getInstance().isParticipantOfTestLab(profile.getParticipant().getCommunityId(), LabType.LAB)) {
                            activeServiceSets = ServiceSetExecutionManager.getInstance().participantActiveServiceSets(participantId,LabType.LAB);
                            if (activeServiceSets != null && activeServiceSets.size() > 0) {
                                    setLandingPage(PARTICIPANT_DASHBOARD,PARTICIPANT);
                                    // gives the Participant a nice overview of where they are.
                                    // P.S. added this comment after talking to Pete.
                            } else {
                                setLandingPage(SETUPTEST, CONFORMANCE_PARTICIPANT);
                            }
                        } else {
                                setLandingPage(SETUPTEST, CONFORMANCE_PARTICIPANT);
                        }                            
                    }
                    forward = LANDINGPAGE;
                    break;
                default:
                    // user role is invalid
                    addActionError(getText("errors.login.failed.invalid.userrole"));
                    break;
            }
        }

        return forward;
    }

    public String getDashboard() {
        return dashboard;
    }

    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    private void setLandingPage(String dashboard, String ns) {
        setDashboard(dashboard);
        setNs(ns);
    }
}
