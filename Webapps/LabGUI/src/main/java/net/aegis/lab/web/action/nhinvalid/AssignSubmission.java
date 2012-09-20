/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.web.action.nhinvalid;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dto.ValidationDto;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.web.action.BaseAction;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class AssignSubmission extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinValidator;
    private List<ValidationDto> submittedValidationRecs;
    private List<Boolean> validationRecs;
    private String buttonName;
    private List<ValidationDto> toBeReviewdValidationRecs;
    private List<ValidationDto> toBeUassinedValidationRecs;

    @Override
    public String execute() throws Exception {

        log.info("AssignSubmission.execute() - INFO");
        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinValidator(this.getProfile().getNhinreps().get(0));
                log.info("AssignSubmission.execute() - NHIN Validator is " + nhinValidator.getName());
                // clicked on the menu item -- need to populate the list in that case too.
                log.info("AssignSubmission.execute() - menu item clicked - INFO");
                this.setSubmittedValidationRecs(ValidationManager.getInstance().getServiceSetForValidation());
                // Check Assign Action
                if ("Assign".equals(buttonName)) {
                    log.info("AssignSubmission.execute() - save changes submit button clicked - INFO");
                    if (validationRecs != null) {
//                        if (validValidtionRecs(validationRecs)) {
//                            this.addActionError("Please Select At Least One Validation to Assign");
//                            return INPUT;
//                        } else
                        {
                            toBeReviewdValidationRecs = new ArrayList<ValidationDto>();
                            toBeUassinedValidationRecs = new ArrayList<ValidationDto>();
                            int i = 0;
                            for (Boolean valstatus : validationRecs) {
                                log.info("The val Status is-->> " + valstatus + "," + validationRecs.indexOf(valstatus) + "," + i);
                                if ((null != valstatus) && (valstatus == true)) {
                                    toBeReviewdValidationRecs.add(submittedValidationRecs.get(i));
                                } else {
                                    toBeUassinedValidationRecs.add(submittedValidationRecs.get(i));
                                }
                                i++;
                            }
                            // Assign the selected participants to this instance of Nhin Representative
                            if (toBeReviewdValidationRecs.size() > 0) {
                                ValidationManager.getInstance().setValidationRecsForReview(toBeReviewdValidationRecs, nhinValidator.getNhinRepId());
                            }
                            // unAssign the unselected Participants from the validator who is logged in.
                            if (toBeUassinedValidationRecs.size() > 0) {
                                ValidationManager.getInstance().unAssignValidationRecsForReviewFromValidator(toBeUassinedValidationRecs, nhinValidator.getNhinRepId());
                            }
                            this.setSubmittedValidationRecs(ValidationManager.getInstance().getServiceSetForValidation());
                        }

                    }
                    return SUCCESS;   // back to assign participants screen

                } //else if ("Refresh".equals(buttonName)) {
                // clicked on the menu item -- need to populate the list in that case too.
                //log.info("AssignSubmission.execute() - menu item clicked - INFO");
                //this.setSubmittedValidationRecs(ValidationManager.getInstance().getServiceSetForValidation());
                //}
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
        return SUCCESS;
    }

    public Nhinrep getNhinValidator() {
        return nhinValidator;
    }

    public void setNhinValidator(Nhinrep nhinValidator) {
        this.nhinValidator = nhinValidator;
    }

    public List<ValidationDto> getSubmittedValidationRecs() {
        return submittedValidationRecs;
    }

    public void setSubmittedValidationRecs(List<ValidationDto> submittedValidationRecs) {
        this.submittedValidationRecs = submittedValidationRecs;
    }

    public List<Boolean> getValidationRecs() {
        return validationRecs;
    }

    public void setValidationRecs(List<Boolean> validationRecs) {
        this.validationRecs = validationRecs;
    }

    private boolean validValidtionRecs(List<Boolean> validationRecs) {
        boolean checked = true;

        for (Boolean valrecs : validationRecs) {
            if (null != valrecs) {
                if (valrecs) {
                    checked = false;
                    break;
                }
            }
        }

        if (checked == true) {
            int i = 0;
            for (Boolean valstatus : validationRecs) {
                submittedValidationRecs.get(i).setAssignedTo(null);
                i++;
            }

        }
        return checked;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
