package net.aegis.lab.web.action.admin;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.TestcaseManager;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.lab.web.util.BooleanMapper;

import org.apache.commons.lang.Validate;

/**
 * This class manages actions related to a test cases.  It currently provides fetchTestCase and updateTestCase actions
 * to support editing of a test case on the UI.
 *
 * @author Tareq.Nabeel
 */
public class ManageTestCase extends BaseAction {

    private static final long serialVersionUID = 1L;

    // Used to retrieve the requested testcase via url
    private Integer id;

    // Used to store request values posted via form controls
    private Testcase testCase;

    // Each element in the list corresponds to one scenario case row
    public List<String> userName;

    // Each element in the list corresponds to one scenario case row
    public List<String> patientId;

    // Each element in the list corresponds to one scenario case row
    public List<String> documentIds;

    // Each element in the list corresponds to one scenario case row
    public List<String> participatingRIs;

    // We don't want to update changedTime and changeUser columns in database when there were no changes.
    // We'll use this to conditionally update the test case depending on whether or not there were changes on the UI.
    private boolean testCaseModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Testcase getTestCase() {
        return testCase;
    }

    public void setTestCase(Testcase testCase) {
        this.testCase = testCase;
    }

    public List<String> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public List<String> getParticipatingRIs() {
        return participatingRIs;
    }

    public void setParticipatingRIs(List<String> participatingRIs) {
        this.participatingRIs = participatingRIs;
    }

    public List<String> getPatientId() {
        return patientId;
    }

    public void setPatientId(List<String> patientId) {
        this.patientId = patientId;
    }

    public List<String> getUserName() {
        return userName;
    }

    public void setUserName(List<String> userName) {
        this.userName = userName;
    }
    
    public boolean isTestCaseModified() {
        return testCaseModified;
    }

    public void setTestCaseModified(boolean testCaseModified) {
        this.testCaseModified = testCaseModified;
    }

    /**
     * This method retrieves the test case corresponding to the requested test case id
     * @return
     * @throws ServiceException
     */
    private Testcase retrieveTestCase() throws ServiceException {
        log.info("ManageTestCase.retrieveTestCase()...id=" + getId());
        // Retrieve the service set using the name parameter
        Validate.notNull(getId(), "Cannot retrieve test case id null");
        Testcase retrievedTestCase = TestcaseManager.getInstance().findTestcaseById(getId());
        log.info("ManageTestCase.retrieveTestCase() - " + (retrievedTestCase != null ? " found TestCase '" : " cound not find TestCase '") + getId() + "'");
        return retrievedTestCase;
    }

    /**
     * This method would get called when user navigates to Test Case e.g. via a link on the test case list or scenario case list
     *
     * @return success if all went well; error otherwise
     */
    public String fetchTestCase() {
        log.info("ManageTestCase.fetchTestCase()...");
        try {

            Testcase retrievedTestCase = retrieveTestCase();
            setTestCase(retrievedTestCase);
            log.info("scenariocase size="+retrievedTestCase.getScenariocases().size());
            // Retrieved testCase will have "Y"/"N" for initiatorInd and responderInd.
            // Checkbox will not be checked if value is "Y" (Using fieldValue attribute would solve submission for "Y" but not for "N").
            // So we need to convert "Y"/"N" to true/false before page is rendered.  Since these attributes are strings, we'll store as boolean string.
            // Boolean strings get correctly evaulated to booleans by Struts
            retrievedTestCase.setInitiatorInd(BooleanMapper.convertYesNoToBooleanString(retrievedTestCase.getInitiatorInd()));
            retrievedTestCase.setResponderInd(BooleanMapper.convertYesNoToBooleanString(retrievedTestCase.getResponderInd()));

            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }

    /**
     * This method gets called when user clicks on "Save Changes"
     *
     * @return success if all went well; error otherwise
     */
    public String updateTestCase() {
        log.info("ManageTestCase.updateTestCase() testCaseModified=" + isTestCaseModified() + "...");
        try {
            // Retrieve the session-managed testCase from database
            Testcase testCaseToUpdate = retrieveTestCase();
            if (isTestCaseModified()) {
                // This is the testCase request created and initialized by Struts based on request params.
                Testcase testCaseRequest = getTestCase();
                // Update the testCase based on submitted request parameters
                transferTestCaseDataFromRequest(testCaseToUpdate, testCaseRequest);
                transferScenarioCaseDataFromRequest(testCaseToUpdate);
                TestcaseManager.getInstance().updateTestcaseAndScenarioCases(testCaseToUpdate);

                this.addActionMessage(getText("admin.testcase.updated.successfully"));
            }
            setTestCase(testCaseToUpdate);
            // We need to convert Y/N to true/false so UI can display the values correctly
            testCaseToUpdate.setInitiatorInd(BooleanMapper.convertYesNoToBooleanString(testCaseToUpdate.getInitiatorInd()));
            testCaseToUpdate.setResponderInd(BooleanMapper.convertYesNoToBooleanString(testCaseToUpdate.getResponderInd()));
            setTestCaseModified(false);
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }

    private void transferTestCaseDataFromRequest(Testcase testCaseToUpdate, Testcase testCaseRequest) {
        // Retrieved testCase will have "Y"/"N" for initiatorInd and responderInd.
        // Checkbox will not be checked if value is "Y" (Using fieldValue attribute would solve submission for "Y" but not for "N").
        // So we need to convert "Y"/"N" to true/false before page is rendered.  Since these attributes are strings, we'll store as boolean string.
        // Boolean strings get correctly evaulated to booleans by Struts
        testCaseToUpdate.setInitiatorInd(BooleanMapper.convertBooleanStringToYN(testCaseRequest.getInitiatorInd()));
        testCaseToUpdate.setResponderInd(BooleanMapper.convertBooleanStringToYN(testCaseRequest.getResponderInd()));

        // Set the messageName based on messageType as they must not be allowed to go out-of-sync
        String messageLookupKey = "admin.testcase.field.messageType." + testCaseRequest.getMessageType();
        String messageName = getText(messageLookupKey);
        Validate.isTrue(!(messageLookupKey.equals(messageName)), "Could not find resource bundle key '" + messageLookupKey+"'");
        testCaseToUpdate.setMessageName(messageName);

        // Transfer other basic attributes of testCase from request to session-managed object
        testCaseToUpdate.setChangedtime(new Timestamp(System.currentTimeMillis()));
        testCaseToUpdate.setChangeduser(getProfile().getUsername());
        testCaseToUpdate.setDescription(testCaseRequest.getDescription());
        testCaseToUpdate.setExecuteType(testCaseRequest.getExecuteType());
        testCaseToUpdate.setExpectedTestResults(testCaseRequest.getExpectedTestResults());
        testCaseToUpdate.setMessageType(testCaseRequest.getMessageType());
        testCaseToUpdate.setName(testCaseRequest.getName());
        testCaseToUpdate.setSsnHandlingInd(testCaseRequest.getSsnHandlingInd());
        testCaseToUpdate.setTestType(testCaseRequest.getTestType());

    }

    private void transferScenarioCaseDataFromRequest(Testcase testCaseToUpdate) {
        if (this.userName==null || testCaseToUpdate.getScenariocases()==null || testCaseToUpdate.getScenariocases().size()==0) {
            log.info("No scenariocases to update for '" + testCaseToUpdate.getName() + "' test case");
            return;
        }
        List<Scenariocase> scenarioCases = testCaseToUpdate.getScenariocases();
        if (scenarioCases.size()!=this.userName.size()) {
            // TODO Tareq: Handle this better.  This should happen only when another user added/deleted scenarios cases
            // between user requests.  We don't want to set the data for the wrong row!!
            throw new IllegalStateException("Unexpected scenario case count");
        }
        for (int i = 0; i < scenarioCases.size(); i++) {
            Scenariocase scenariocase = scenarioCases.get(i);
            scenariocase.setUserName(this.userName.get(i));
            scenariocase.setPatientId(this.patientId.get(i));
            scenariocase.setDocumentIds(this.documentIds.get(i));
            scenariocase.setParticipatingRIs(this.participatingRIs.get(i));
        }
    }
}
