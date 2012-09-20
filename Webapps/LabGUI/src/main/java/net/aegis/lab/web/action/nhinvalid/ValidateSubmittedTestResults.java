package net.aegis.lab.web.action.nhinvalid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.LabConstants.LabType;
import net.aegis.lab.web.action.BaseAction;


/**
 * Requirement - Nhin Validator can validate submitted test results (scenarios).
 *               This is a decision making ability of the Nhin Validator.
 */

public class ValidateSubmittedTestResults extends BaseAction {

    private static final long serialVersionUID = 1L;
    private String executionUniqueId = "";
    private List<Servicesetexecution> serviceSetExecution = null;   // note - there will be only one in DB !!
    private List<Scenarioexecution> scenarioexecutions = null;
    private String validateAction;
    private String entireValidationCommentsString;
    private String checkedListOfScenariosValidatedByNhinValidator;
    private String checkedListOfScenariosFailedByNhinValidator;

    @Override
    public String execute() throws Exception {

        log.info("ValidateSubmittedTestResults.execute() - INFO");
        log.info("ValidateSubmittedTestResults.execute() - executionUniqueId="+executionUniqueId);

        try {
            if (null != validateAction) {
                if (validateAction.equalsIgnoreCase("refresh")) {
                    log.info("ValidateSubmittedTestResults.execute() - refresh submit button clicked - INFO");
                    setUpThisJSPPageForDisplay();

                    // support for navigation
                    this.getSession().removeAttribute("sessionobj.scenarioExecutionId");
                    this.getSession().setAttribute("sessionobj.servicesetExecutionUniqueId", executionUniqueId);
                }
                else if (validateAction.equalsIgnoreCase("save_changes")) {
                    log.info("ValidateSubmittedTestResults.execute() - 'save changes' (validate) submit button clicked - INFO");

                    // perform the update
                    updateScenarioExecutionsWithValidOrFailStatusAndValidationComments(executionUniqueId,
                                                                                       checkedListOfScenariosValidatedByNhinValidator,
                                                                                       checkedListOfScenariosFailedByNhinValidator,
                                                                                       entireValidationCommentsString);

                    // finally set up the page for display with all the data
                    setUpThisJSPPageForDisplay();

                    // support for navigation
                    this.getSession().removeAttribute("sessionobj.scenarioExecutionId");
                    this.getSession().removeAttribute("sessionobj.submittedScenario");  // used only by nhnvalid.TestCaseSpec.java
                    this.getSession().setAttribute("sessionobj.servicesetExecutionUniqueId", executionUniqueId);
                }
            }
            else {
                log.info("ValidateSubmittedTestResults.execute() - none of the submit buttons clicked on validate submit results screen - INFO");
                setUpThisJSPPageForDisplay();

                // support for navigation
                this.getSession().removeAttribute("sessionobj.scenarioExecutionId");
                this.getSession().setAttribute("sessionobj.servicesetExecutionUniqueId", executionUniqueId);
            }
        } catch (Throwable e) {
            log.error("ValidateSubmittedTestResults.execute() - Exception", e);
            return this.processException(e);
        }

        return SUCCESS;
    }


    /**
     * Requirement -    For a given service set execution, the logged in nhin validator
     *                  decides whether to validate or fail the scenarios.  Optionally,
     *                  he/she can introduce validation comments.
     *
     * @param pExecutionUniqueId                                service set execution id
     * @param pCheckedListOfScenariosValidatedByNhinValidator   scenarios validated by nhin validator
     * @param pCheckedListOfScenariosFailedByNhinValidator      scenarios failed by nhin validator
     * @param pEntireValidationCommentsString                   validation comments for scenarios where
     *                                                          nhin validator has made a decision
     * @throws Exception
     */
    private void updateScenarioExecutionsWithValidOrFailStatusAndValidationComments(
                    String pExecutionUniqueId,
                    String pCheckedListOfScenariosValidatedByNhinValidator,
                    String pCheckedListOfScenariosFailedByNhinValidator,
                    String pEntireValidationCommentsString) throws Exception {

        log.info("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - INFO");

        String[] arrValidatedScenarios = new String[0];
        String[] arrFailedScenarios = new String[0];
        StringTokenizer stValidationComments = null;
        Map<String,String> objScenarioIdCommentMap = new HashMap<String,String>();


        // check nominal parameter validity
        if (null == pExecutionUniqueId) {
            log.error("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Unknown service set execution id. - ERROR");
            throw new Exception("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Unknown service set execution id.");
        }
        // Now, the JSP must pass either validated scenarios or failed scenarios.
        if ((null == pCheckedListOfScenariosValidatedByNhinValidator) & (null == pCheckedListOfScenariosFailedByNhinValidator)) {
            log.error("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Bad parameter(s) passed. - ERROR");
            throw new Exception("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Bad parameter(s) passed.");
        }

        // log.info("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - " +
        //             "pCheckedListOfScenariosValidatedByNhinValidator="+pCheckedListOfScenariosValidatedByNhinValidator);
        // log.info("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - " +
        //             "pCheckedListOfScenariosFailedByNhinValidator="+pCheckedListOfScenariosFailedByNhinValidator);
        // log.info("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - " +
        //             "pEntireValidationCommentsString="+pEntireValidationCommentsString);

        // Sort out validated scenarios and save them in an array.
        if ((null != pCheckedListOfScenariosValidatedByNhinValidator) && !("".equalsIgnoreCase(pCheckedListOfScenariosValidatedByNhinValidator.trim())) ) {
            arrValidatedScenarios = processDelimitedStringOfScenarioIds(pCheckedListOfScenariosValidatedByNhinValidator);
        }
        // Sort out failed scenarios and save them in an array.
        if ((null != pCheckedListOfScenariosFailedByNhinValidator) && !("".equalsIgnoreCase(pCheckedListOfScenariosFailedByNhinValidator.trim())) ) {
            arrFailedScenarios = processDelimitedStringOfScenarioIds(pCheckedListOfScenariosFailedByNhinValidator);
        }

        // now, make sure that valid scenario ids are completely distinct from failed scenario ids
        boolean isOverlapInScenarioIds = false;
        isOverlapInScenarioIds = areAnySameValueElements(arrValidatedScenarios, arrFailedScenarios);
        if(true == isOverlapInScenarioIds) {
            log.error("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Some overlap between validated and failed scenario ids. - ERROR");
            throw new Exception("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - Some overlap between validated and failed scenario ids.");
        }

        // Sort out any entire validation comments string
        if ((null != pEntireValidationCommentsString) && !("".equalsIgnoreCase(pEntireValidationCommentsString.trim())) ) {
            // sample of pEntireValidationCommentsString -
            // validation comments of scenario 1~707|validation comments of scenario 2~708|validation comments of scenario 3~709|
            stValidationComments = new StringTokenizer(pEntireValidationCommentsString, "|");
            if (null != stValidationComments) {
                for(int i=0; stValidationComments.hasMoreTokens(); i++) {
                    String tmpValidationComment = null;
                    String[] tmpValidCmnt_ScnId = null;

                    tmpValidationComment = stValidationComments.nextToken();     // sample - "validation comments of scenario 1~707"
//                    log.info("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - tmpValidationComment=^"+tmpValidationComment+"^");

                    if(null != tmpValidationComment) {
                        tmpValidCmnt_ScnId = tmpValidationComment.split("~");    // StringTokenizer empty token limitation addressed.
                    }
                    if(null != tmpValidCmnt_ScnId) {
//                        log.info("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - tmpValidCmnt_ScnId[0]=^"+tmpValidCmnt_ScnId[0]+"^");
//                        log.info("ValidateSubmittedTestResults.updateScenarioExecutionsWithValidOrFailStatusAndValidationComments() - tmpValidCmnt_ScnId[1]=^"+tmpValidCmnt_ScnId[1]+"^");
                        objScenarioIdCommentMap.put(tmpValidCmnt_ScnId[1], tmpValidCmnt_ScnId[0]);  // sorry for the apparent reverser order.
                    }
                }
            }
        }

        // finally perform the update
        ValidationManager.getInstance().updateScenarioExecutionsWithValidOrFailStatusAndValidationComments(pExecutionUniqueId, arrValidatedScenarios, arrFailedScenarios, objScenarioIdCommentMap);
    }

    /**
     * Purpose - A simple utility method for this class.  It processes a string
     *           object of scenario ids and returns the ids back in an array.
     *
     * @param pstrDelimitedString   delimited string of scenario ids ~707~709
     * @return String[]             scenario ids
     */
    private String[] processDelimitedStringOfScenarioIds(String pstrDelimitedString) {
        StringTokenizer stScenarios = null;
        String[] arrScenarioIds = new String[0];

        if ((null == pstrDelimitedString) || ("".equalsIgnoreCase(pstrDelimitedString.trim()))) {
            log.warn("ValidateSubmittedTestResults.processDelimitedStringOfScenarioIds() - empty string passed. Returning null.");
            return null;
        }
        // sample of pstrDelimitedString - ~707~709
        stScenarios = new StringTokenizer(pstrDelimitedString, "~");
        if (null != stScenarios) {
            arrScenarioIds = new String[stScenarios.countTokens()];
            for(int i=0; stScenarios.hasMoreTokens(); i++) {
                String strNextToken = null;
                strNextToken = stScenarios.nextToken();
                if (null != strNextToken) {
                    strNextToken = strNextToken.trim();         // not necessary, but does not hurt.
                    arrScenarioIds[i] = strNextToken;
                    log.info("ValidateSubmittedTestResults.processDelimitedStringOfScenarios() - " +
                                " saved scenario " + arrScenarioIds[i]);
                }
            }
        }
        return arrScenarioIds;
    }

    /**
     * Purpose - Compares two arrays of say, scenario ids.  Tells if there is
     *           any element that has the same value between the two arrays.
     *
     * @param parrOne   array to compare
     * @param parrTwo   array to compare
     * @return boolean  true if any same value element in two arrays
     */
    private boolean areAnySameValueElements(String[] parrOne, String[] parrTwo) {
        boolean isAnySameValueElement = false;
        ArrayList<String> objListOne = null;
        ArrayList<String> objListTwo = null;

        if ((null == parrOne) || (null == parrTwo)) {
            return false;       // no element of same value between arrays
        }

        if (null != parrOne) {
            objListOne = new ArrayList<String>(Arrays.asList(parrOne));
            objListOne.trimToSize();
        }
        if (null != parrTwo) {
            objListTwo = new ArrayList<String>(Arrays.asList(parrTwo));
            objListTwo.trimToSize();
        }

        // now find elements that have the same value
        objListTwo.retainAll(objListOne);
        log.info("ValidateSubmittedTestResults.areAnySameValueElements() - After comparing: objListTwo="+objListTwo);
        if(objListTwo.size() > 0) {
            isAnySameValueElement = true;
        }
        return isAnySameValueElement;
    }

    /**
     * Purpose - A simple refactoring method for code reuse. Gets all scenario
     *           executions whose status is 'closed' AND 'validated' AND 'failed'
     *
     * @throws Exception
     */
    private void setUpThisJSPPageForDisplay() throws Exception {

        // get and save the complete service set execution object anyway. Note - there will be only one in DB !!
        setServiceSetExecution(ServiceSetExecutionManager.getInstance().findByExecutionUniqueId(executionUniqueId));

       //Set the headerType value as "Scenario" for lab / "Test Group" for conformance mode in session to display in jsp page
        setHeaderType();

        // get and save scenario executions that belong to the service set execution -- these scenario executions will be of 'closed' AND 'validated' AND 'failed' status
        List<Scenarioexecution> togetherSEsForThisPage = new ArrayList<Scenarioexecution>();
        List<Scenarioexecution> closedSEs = null;
        List<Scenarioexecution> validatedSEs = null;
        List<Scenarioexecution> failedSEs = null;

        closedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_CLOSED);
        validatedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_VALIDATED);
        failedSEs = ValidationManager.getInstance().getScenarioExecutionsThatBelongToAServiceSetExecutionUnderReview(executionUniqueId, LabConstants.STATUS_SCENARIOEXEC_FAILED);

        for(Scenarioexecution vs : validatedSEs) {
            togetherSEsForThisPage.add(vs);
        }
        for(Scenarioexecution fs : failedSEs) {
            togetherSEsForThisPage.add(fs);
        }
        for(Scenarioexecution cs : closedSEs) {
            togetherSEsForThisPage.add(cs);
        }

        this.setScenarioexecutions(togetherSEsForThisPage);

        // To support the Export files feature in nhinvalid
        if(scenarioexecutions != null && scenarioexecutions.size() > 0){
             fetchDetailedAuditSummaryList();
        }
    }


    /**
     * Fetches Audit Summary List to support the export files feature in nhinvalid.
     *
     * @throws Exception
     */
    public void fetchDetailedAuditSummaryList() throws Exception {
        List<Auditsummary> auditSummaryList = new ArrayList<Auditsummary>();
        for(Scenarioexecution seExecs :scenarioexecutions){
        List<Caseexecution> caseExecutions = seExecs.getCaseexecutions();
        for (Caseexecution caseExec : caseExecutions) {
             log.info("ValidateSubmittedTestResults.fetchDetailedAuditSummaryList() - CaseExecution TestCase Name: " + caseExec.getTestcase().getName());
            if (null != caseExec.getTestcase().getName()) {
                List<Auditsummary> auditSummarys = ServiceSetExecutionManager.getInstance().getAuditSummariesByScenarioExecIdAndCaseName(seExecs, caseExec.getTestcase().getName());
                for (Auditsummary auditSum : auditSummarys) {
                    auditSummaryList.add(auditSum);
                }
            }
           }
        }
        if (auditSummaryList != null && auditSummaryList.size() > 0) {
             log.info("ValidateSubmittedTestResults.fetchDetailedAuditSummaryList() - auditSummaryList size : "+auditSummaryList.size());
            this.getSession().setAttribute("sessionobj.detailedAuditLogList", auditSummaryList);//naresh
        } else {
            this.getSession().removeAttribute("sessionobj.detailedAuditLogList");
        }
    }

    //Method used to set the headerType value as "Scenario" for lab / "Test Group" for conformance mode in session to display in jsp page
    private void setHeaderType() {
        String labType ="";
         if (this.getServiceSetExecution() != null && this.getServiceSetExecution().size() > 0) {
            int labMode = getServiceSetExecution().get(0).getServiceset().getLabAccessFilter();
            labType = LabType.getType(labMode);
            if (labType != null && !labType.equals("")) {
                this.getSession().setAttribute("nhinvalidHeaderType", labType);
            }else {
                labType = LabType.getType(LabType.LAB.getId());
                this.getSession().setAttribute("nhinvalidHeaderType", labType);
            }
        } else {
                labType = LabType.getType(LabType.LAB.getId());
                this.getSession().setAttribute("nhinvalidHeaderType", labType);
            }
    }

    /**
     * @return the executionUniqueId
     */
    public String getExecutionUniqueId() {
        return executionUniqueId;
    }

    /**
     * @param executionUniqueId the executionUniqueId to set
     */
    public void setExecutionUniqueId(String executionUniqueId) {
        this.executionUniqueId = executionUniqueId;
    }

    /**
     * @return the scenarioexecutions
     */
    public List<Scenarioexecution> getScenarioexecutions() {
        return scenarioexecutions;
    }

    /**
     * @param scenarioexecutions the scenarioexecutions to set
     */
    public void setScenarioexecutions(List<Scenarioexecution> scenarioexecutions) {
        this.scenarioexecutions = scenarioexecutions;
    }

    /**
     * @return the serviceSetExecution
     */
    public List<Servicesetexecution> getServiceSetExecution() {
        return serviceSetExecution;
    }

    /**
     * @param serviceSetExecution the serviceSetExecution to set
     */
    public void setServiceSetExecution(List<Servicesetexecution> serviceSetExecution) {
        this.serviceSetExecution = serviceSetExecution;
    }

    /**
     * @return the validateAction
     */
    public String getValidateAction() {
        return validateAction;
    }

    /**
     * @param validateAction the validateAction to set
     */
    public void setValidateAction(String validateAction) {
        this.validateAction = validateAction;
    }

    /**
     * @return the entireValidationCommentsString
     */
    public String getEntireValidationCommentsString() {
        return entireValidationCommentsString;
    }

    /**
     * @param entireValidationCommentsString the entireValidationCommentsString to set
     */
    public void setEntireValidationCommentsString(String entireValidationCommentsString) {
        this.entireValidationCommentsString = entireValidationCommentsString;
    }

    /**
     * @return the checkedListOfScenariosValidatedByNhinValidator
     */
    public String getCheckedListOfScenariosValidatedByNhinValidator() {
        return checkedListOfScenariosValidatedByNhinValidator;
    }

    /**
     * @param checkedListOfScenariosValidatedByNhinValidator the checkedListOfScenariosValidatedByNhinValidator to set
     */
    public void setCheckedListOfScenariosValidatedByNhinValidator(String checkedListOfScenariosValidatedByNhinValidator) {
        this.checkedListOfScenariosValidatedByNhinValidator = checkedListOfScenariosValidatedByNhinValidator;
    }

    /**
     * @return the checkedListOfScenariosFailedByNhinValidator
     */
    public String getCheckedListOfScenariosFailedByNhinValidator() {
        return checkedListOfScenariosFailedByNhinValidator;
    }

    /**
     * @param checkedListOfScenariosFailedByNhinValidator the checkedListOfScenariosFailedByNhinValidator to set
     */
    public void setCheckedListOfScenariosFailedByNhinValidator(String checkedListOfScenariosFailedByNhinValidator) {
        this.checkedListOfScenariosFailedByNhinValidator = checkedListOfScenariosFailedByNhinValidator;
    }

}
