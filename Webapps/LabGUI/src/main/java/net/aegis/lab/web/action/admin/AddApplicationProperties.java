package net.aegis.lab.web.action.admin;




import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

public class AddApplicationProperties extends BaseAction {

    /**private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private Participant participant;
    private User user;*/


    private Applicationproperties  applicationproperties;
    private String propertykey;                    // form param


    private String propertyvalue;                    // form param
    private String addPropertyAction;


    /*private String participantName;               // form param
    private String communityId;                 // form param
    private String assigningAuthorityId;        // form param
    private String ipAddress;                   // form param
    private String contactName;                 // form param
    private String contactPhone;                // form param
    private String contactEmail;                // form param


    // checkbox handling
    private String initiatorInd = "false";
    private String responderInd = "false";
    // radio button handling
    private String ssnHandlingInd = "false";
*/

    @Override
    public String execute() throws Exception {

        log.info("AddApplicationProperties.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
               // setNhinrep(this.getProfile().getNhinreps().get(0));
                log.warn("AddApplicationProperties.execute() -  is " );

                // Check add properties  Action
                if (addPropertyAction != null) {
                    if (addPropertyAction.equalsIgnoreCase("clear")) {
                        applicationproperties = new Applicationproperties();
                    }
                    if (addPropertyAction.equalsIgnoreCase("save")) {

                        log.info("addPropertyAction.execute() - save button clicked - INFO");

                        Applicationproperties participantThatNhinRepWorksWith = null;

                        // now register the participant and save in session
                        participantThatNhinRepWorksWith = AddNewProperty();
                        this.getSession().setAttribute(LabConstants.PARTICIPANT_THAT_NHINREP_WORKS_WITH, participantThatNhinRepWorksWith);

                        return INPUT;   // nhin dashboard
                    }
                }
                else {
                    applicationproperties = new Applicationproperties();
                }
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }


    private Applicationproperties AddNewProperty() throws Exception {
        ApplicatiopropertiesManager apppropManager = ApplicatiopropertiesManager.getInstance();

        // AddApplicationProperties (Save) New property
        log.info("AddApplicationProperties.AddNewProperty() - INFO");

       Applicationproperties newlyCreatedProperty = null;


        applicationproperties = new Applicationproperties();

        applicationproperties.setPropertykey(this.getPropertykey());
        applicationproperties.setPropertyvalue(this.getPropertyvalue());

       /**
        participant.setParticipantName(this.getParticipantName());
        participant.setUser(user);
        participant.setCommunityId(this.getCommunityId());
        participant.setAssigningAuthorityId(this.getAssigningAuthorityId());
        participant.setIpAddress(this.getIpAddress());
        //participant.setNhinrep(this.getNhinrep());
        participant.setNhinRepId(this.getNhinrep().getNhinRepId());
        participant.setContactName(this.getContactName());
        participant.setContactPhone(this.getContactPhone());
        participant.setContactEmail(this.getContactEmail());
        participant.setInitiatorInd(this.getInitiatorInd());
        participant.setResponderInd(this.getResponderInd());
        participant.setSsnHandlingInd(this.getSsnHandlingInd());
        participant.setStatus(LabConstants.STATUS_ACTIVE);
        participant.setCreateuser(this.getNhinrep().getName());
        participant.setChangeduser(this.getNhinrep().getName());*/

        newlyCreatedProperty = apppropManager.AddProperties(this.getPropertykey(), this.getPropertyvalue());

        return newlyCreatedProperty;
    }

    public String getAddPropertyAction() {
        return addPropertyAction;
    }

    public void setAddPropertyAction(String addPropertyAction) {
        this.addPropertyAction = addPropertyAction;
    }

    public Applicationproperties getApplicationproperties() {
        return applicationproperties;
    }

    public void setApplicationproperties(Applicationproperties applicationproperties) {
        this.applicationproperties = applicationproperties;
    }


 public String getPropertykey() {
        return propertykey;
    }

    public void setPropertykey(String propertykey) {
        this.propertykey = propertykey;
    }

    public String getPropertyvalue() {
        return propertyvalue;
    }

    public void setPropertyvalue(String propertyvalue) {
        this.propertyvalue = propertyvalue;
    }


}
