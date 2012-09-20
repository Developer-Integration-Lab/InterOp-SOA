package net.aegis.lab.web.action.admin;
/**
 * Requirement: An Admin needs to register a NHIN Representative
 * create/insert records to the tables User, Nhinrep, userroletable
 *
 * @author SreeHari.Devineni
 */
//import net.aegis.lab.web.action.admin.*;
import java.sql.Timestamp;
import java.util.Date;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.manager.NhinrepManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

public class RegisterNhinRep extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private User user;
    private String userName;                    // form param
    private String password;                    // form param
    private String name;                        // form param
    private String contactName;                 // form param
    private String contactPhone;                // form param
    private String contactEmail;                // form param
    private String registerAction;

    // radio button handling
    private String selectedreprole;

    public String getSelectedreprole() {
        return selectedreprole;
    }

    public void setSelectedreprole(String selectedreprole) {
        this.selectedreprole = selectedreprole;
    }




    public String getRegisterAction() {
        return registerAction;
    }

    public void setRegisterAction(String registerAction) {
        this.registerAction = registerAction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nhinrep getNhinrep() {
        return nhinrep;
    }

    public void setNhinrep(Nhinrep nhinrep) {
        this.nhinrep = nhinrep;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUserobj() {
        return user;
    }

    public void setUserobj(User userobj) {
        this.user = userobj;
    }



    @Override
    public String execute() throws Exception {

        log.info("RegisterNhinRep.execute() - INFO");

        try {
             log.info("RegisterNhinRep.execute() - INFO 1"+ this.getProfile() );


          if (this.getProfile() != null ) {


                // Check Save/Clear Action
                if (registerAction != null) {
                    if (registerAction.equalsIgnoreCase("clear")) {
                        nhinrep = new Nhinrep();
                    }
                    if (registerAction.equalsIgnoreCase("save")) {

                        log.info("RegisterNhinRep.execute() - save button clicked - INFO");
                        log.info("RegisterNhinRep.execute() - Radio value: - INFO:::"+selectedreprole);
                        Nhinrep nhinrepThatAdminWorksWith = null;

                        log.info("RegisterNhinRep.execute() - calling registerNhinrep() - INFO");
                        // now register the participant and save in session
                        nhinrepThatAdminWorksWith = registerNhinrep();

                        // Lab may have an existing user that has the requested name in the database.
                        if (null == nhinrepThatAdminWorksWith) {
                            this.addActionError(getText("errors.registration.failed.duplicate.username", new String[] {this.userName}));
                            return SUCCESS;   // register participant page with action error
                        }else{
                            this.addActionMessage("Nhin representative  " +this.userName+ " created successfully ");
                       // return SUCCESS;
                        }


                        // update some necessary session information
                        // TO-DO to be added to constants //nhinrepThatAdminWorksWith
                        this.getSession().setAttribute("nhinrepThatAdminWorksWith", nhinrepThatAdminWorksWith);
                         nhinrep = new Nhinrep();
                        return SUCCESS;   // admin dashboard
                    }
                }
                else {
                    nhinrep = new Nhinrep();
                }
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }


    private Nhinrep registerNhinrep() throws Exception {
        NhinrepManager nhinrepManager = NhinrepManager.getInstance();
        //int selectedreprole=3;

        // Register (Save) New Participant
        log.info("RegisterNhinRep.registerParticipant() - INFO");

        Nhinrep newlyRegisteredNhinrep = null;

        user = new User();
        user.setUsername(this.getUserName());
        user.setPassword(this.getPassword());
        user.setStatus("A");
        user.setExpirationTime(null);
        user.setInvalidAttempts(LabConstants.USER_DEFAULT_INVALID_ATTEMPTS);    // 0
        user.setComments("Register NHIN rep - Name: " + this.getName());
        user.setCreatetime(new Timestamp(new Date().getTime()));
        user.setCreateuser(this.getProfile().getUsername());
        user.setChangedtime(null);
        user.setChangeduser(this.getProfile().getUsername());

        nhinrep = new Nhinrep();
        nhinrep.setName(this.getName());
        nhinrep.setUser(user);
        nhinrep.setContactName(this.getContactName());
        nhinrep.setContactPhone(this.getContactPhone());
        nhinrep.setContactEmail(this.getContactEmail());
        nhinrep.setStatus(LabConstants.STATUS_ACTIVE);
        nhinrep.setCreateuser(this.getNhinrep().getName());
        nhinrep.setChangeduser(this.getNhinrep().getName());
       // this.setReproleind(this.getReproleind());



        newlyRegisteredNhinrep = nhinrepManager.registerNhinrep(user,nhinrep,Integer.parseInt(selectedreprole.toString()));
        return newlyRegisteredNhinrep;
    }


    /**
     * @param reproleInd the reproleInd to set
     */

}
