package net.aegis.lab.web.action.nhinrep;

import java.util.StringTokenizer;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.web.action.BaseAction;

/**
 * Requirement: An NHIN Rep needs to choose a participant from the list of participants and
 * associate that participant with himself (a.k.a. herself).
 *
 * @author Abhay.Bakshi
 */
public class AssignParticipants extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinrep;
    private Integer nhinRepId;              // passed by JSP
    private String checkedParticipantsList;   // passed by JSP
    private String assignAction;

    @Override
    public String execute() throws Exception {

        log.info("AssignParticipants.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.warn("AssignParticipants.execute() - NHIN Rep is " + nhinrep.getName());

                // Check Assign Action
                if (assignAction != null) {
                    if (assignAction.equalsIgnoreCase("refresh")) {
                        log.info("AssignParticipants.execute() - refresh submit button clicked - INFO");
                        nhinrep.setParticipants(ParticipantManager.getInstance().findByStatus(LabConstants.STATUS_ACTIVE));
                    }
                    else if (assignAction.equalsIgnoreCase("assign")) {

                        log.info("AssignParticipants.execute() - save changes submit button clicked - INFO");
                        int iNumParticipantsAssigned=0;

                        // Assign the selected participants to this instance of Nhin Representative
                        iNumParticipantsAssigned = assignParticipants(nhinRepId, checkedParticipantsList);
                        log.info("AssignParticipants.execute() - Number of participants assigned="+iNumParticipantsAssigned+" - INFO");

                        // make sure to get the latest list of participants from database for JSP display
                        nhinrep.setParticipants(ParticipantManager.getInstance().findByStatus(LabConstants.STATUS_ACTIVE));
                        return SUCCESS;   // back to assign participants screen
                    }
                }
                else {
                    // clicked on the menu item -- need to populate the list in that case too.
                    log.info("AssignParticipants.execute() - menu item clicked - INFO");
                    nhinrep.setParticipants(ParticipantManager.getInstance().findByStatus(LabConstants.STATUS_ACTIVE));
                }

            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }



    /**
     * Requirement: The logged in Nhin Rep is able to choose participants from participant list
     *              and assign them to himself (a.k.a. herself).
     * 
     * @param pnhinRepId                    Nhin Rep ID from JSP. Must match the nhin rep id of this instace
     * @param pstrcheckedParticipantsList     Participant list from JSP.
     *                                      example - ~1_AEGIS.net, Inc.~2_Test One, Inc.~3_Test Two, Inc.~10NewParticipant
     * @return int                          Number of participants assigned to the nhin rep
     * @throws Exception                    Kept generic for now. Thrown when a business rule violated.
     */
    private int assignParticipants(Integer pnhinRepId, String pstrcheckedParticipantsList)
            throws Exception {

        log.info("AssignParticipants.assignParticipants() - INFO");

        String[] arrParticipantIds = new String[0];
        int iNumParticipantsAssigned=0;

        // check nominal parameter validity
        if ((null == pnhinRepId) || (null == pstrcheckedParticipantsList)) {
            log.error("AssignParticipants.assignParticipants() - Bad parameter(s) passed. - ERROR");
            throw new Exception("AssignParticipants.assignParticipants() - Bad parameter(s) passed.");
        }

        // check whether nhin rep id of this instance matches with the one passed in.
        if (!(pnhinRepId.equals(this.getNhinrep().getNhinRepId()))) {
            log.error("AssignParticipants.assignParticipants() - Nhin Rep Ids do not match. Will not process further. - ERROR");
            throw new Exception("AssignParticipants.assignParticipants() - Nhin Rep Ids do not match. Will not process further.");
        }

        // obtain the participant ids and save them in an array.
        if ((null != pstrcheckedParticipantsList) && !("".equalsIgnoreCase(pstrcheckedParticipantsList.trim())) ) {
            // sample - ~1_AEGIS.net, Inc.~2_Test One, Inc.~3_Test Two, Inc.~4_Richard~5_Mastan~6_Ram~7_Jyoti~9_Sree Hari~11_NewParticipant
            StringTokenizer st = new StringTokenizer(pstrcheckedParticipantsList, "~");
            if (null != st) {
                arrParticipantIds = new String[st.countTokens()];
                for(int i=0; st.hasMoreTokens(); i++) {
                    String strNextToken = null;
                    String[] strParticipantID_ParticipantName = null;
                    strNextToken = st.nextToken();
                    if (null != strNextToken) {
                        strParticipantID_ParticipantName = strNextToken.split("_"); // sample - 1_AEGIS.net, Inc.
                    }
                    if(null != strParticipantID_ParticipantName) {
                        arrParticipantIds[i] = strParticipantID_ParticipantName[0];   // get the 1st element which is participant id
                    }
                }
            }
        }

        //if ((null == arrParticipantIds) || (arrParticipantIds.length <=0)) {
        //    return 0;       // no participant assigned
        //}
        
        // call the business method at the service layer to assign the participants
        iNumParticipantsAssigned = ParticipantManager.getInstance().assignParticipants(pnhinRepId, arrParticipantIds);

        return iNumParticipantsAssigned;
    }


    //
    // GETTER/SETTER METHODS
    //

    public Nhinrep getNhinrep() {
        return nhinrep;
    }

    public void setNhinrep(Nhinrep nhinrep) {
        this.nhinrep = nhinrep;
    }

    /**
     * @return the assignAction
     */
    public String getAssignAction() {
        return assignAction;
    }

    /**
     * @param assignAction the assignAction to set
     */
    public void setAssignAction(String assignAction) {
        this.assignAction = assignAction;
    }

    /**
     * @return the nhinRepId
     */
    public Integer getNhinRepId() {
        return nhinRepId;
    }

    /**
     * @param nhinRepId the nhinRepId to set
     */
    public void setNhinRepId(Integer nhinRepId) {
        this.nhinRepId = nhinRepId;
    }

    /**
     * @return the checkedParticipantsList
     */
    public String getCheckedParticipantsList() {
        return checkedParticipantsList;
    }

    /**
     * @param checkedParticipantsList the checkedParticipantsList to set
     */
    public void setCheckedParticipantsList(String checkedParticipantsList) {
        this.checkedParticipantsList = checkedParticipantsList;
    }


}

