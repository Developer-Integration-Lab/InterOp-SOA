package net.aegis.lab.web.action.nhinvalid;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.Validation;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.web.action.BaseAction;

/**
 * This class supports Nhin Validator functionality.
 *
 * @author Abhay.Bakshi
 */
public class NhinvalidDashboard extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Nhinrep nhinValidator;
    private List<Servicesetexecution> serviceSetExecutions = new ArrayList<Servicesetexecution>();

    @Override
    public String execute() throws Exception {

        log.info("NhinvalidDashboard.execute() - INFO");

        try {
            if (this.getProfile() != null && this.getProfile().getNhinreps() != null && this.getProfile().getNhinreps().size() > 0) {
                setNhinrep(this.getProfile().getNhinreps().get(0));
                log.info("NhinvalidDashboard.execute() - NHIN Validator is " + nhinValidator.getName());

                List<Validation> objListValidationRecordsWithReviewStatus = null;
                objListValidationRecordsWithReviewStatus = ValidationManager.getInstance().getValidationRecordsUnderReviewThatBelongToAnNhinValidator(this.getNhinrep().getNhinRepId());

                if (null != objListValidationRecordsWithReviewStatus) {
                    for(Validation tmpValidationUnderReview : objListValidationRecordsWithReviewStatus) {
                        if (null != tmpValidationUnderReview) {
                            Servicesetexecution servicesetexecution = tmpValidationUnderReview.getServicesetexecution();
                            if (null != servicesetexecution) {
                               List<Caseexecution> caseExecutions = ServiceSetExecutionManager.getInstance().getServiceSetCaseExecutionsByExecUniqueId(servicesetexecution.getExecutionUniqueId());
                               servicesetexecution.setCaseexecutions(caseExecutions);
                               serviceSetExecutions.add(servicesetexecution);
                            }
                        }
                    }
                }

//                serviceSetExecutions = ServiceSetExecutionManager.getInstance().getAllServiceSetExecutionsByStatus(LabConstants.STATUS_SERVICESETEXEC_REVIEW);
                this.setServiceSetExecutions(serviceSetExecutions);

                // support for navigation
                this.getSession().removeAttribute("sessionobj.servicesetExecutionUniqueId");
                this.getSession().removeAttribute("sessionobj.scenarioExecutionId");
                this.getSession().removeAttribute("sessionobj.selectedCaseName");
            }
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }

        return SUCCESS;
    }

    public Nhinrep getNhinrep() {
        return nhinValidator;
    }

    public void setNhinrep(Nhinrep nhinValidator) {
        this.nhinValidator = nhinValidator;
    }

    /**
     * @return the serviceSetExecutions
     */
    public List<Servicesetexecution> getServiceSetExecutions() {
        return serviceSetExecutions;
    }

    /**
     * @param serviceSetExecutions the serviceSetExecutions to set
     */
    public void setServiceSetExecutions(List<Servicesetexecution> serviceSetExecutions) {
        this.serviceSetExecutions = serviceSetExecutions;
    }

}

