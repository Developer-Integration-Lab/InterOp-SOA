package net.aegis.lab.web.action.labfacade;


import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.LabConstants.LabType;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.lab.web.action.security.Login;


/**
 * Conformance Code
 * @author Sunil.Bhaskarla
 */
public class SwitchDashboard extends BaseAction {

    private static final long serialVersionUID = 1L;
    private String dashboard;
    private String ns;



    @Override
    public String execute() throws Exception {

        log.info("SwitchDashboard.execute().");

        //transient setLabType for now
        //For persistence
        //1. save user
        //2. reload on login - modify SecurityManager.getInstance().authenticate

        try {
            if (this.getProfile().getLabType()==null)
                log.debug("invalid labType!");
        } catch (Exception ex) {
            setNs(Login.TESTLAB_SECURITY);
            setDashboard(Login.TESTLAB_SIGN_ON);
            return SUCCESS;
        }

        LabType labType = this.getProfile().getLabType()==null?LabType.LAB:this.getProfile().getLabType();
        if (!"".equals(getNs()) && getNs()!=null) {
            if (getNs().indexOf(LabType.CONFORMANCE.name().toLowerCase())>-1) {
                labType = LabType.CONFORMANCE;
            } else
                labType = LabType.LAB;
            this.getProfile().setLabType(labType);            
        } else {
            switch (labType) {
                case LAB:
                    if (LabConstants.ROLE_PARTICIPANT==this.getProfile().getPrimaryRole())
                        setDashboard(Login.PARTICIPANT_DASHBOARD);
                   this.getProfile().setLabType(LabType.CONFORMANCE);
                   setNs(Login.CONFORMANCE_PARTICIPANT);
                break;
                case CONFORMANCE:
                    if (LabConstants.ROLE_PARTICIPANT==this.getProfile().getPrimaryRole())
                        setDashboard(Login.PARTICIPANT_DASHBOARD);
                    this.getProfile().setLabType(LabType.LAB);
                    setNs(Login.PARTICIPANT);
                break;
            }
        }

        this.setProfile(this.getProfile());


        return SUCCESS;
    
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

}
