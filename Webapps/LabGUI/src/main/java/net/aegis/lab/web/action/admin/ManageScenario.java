package net.aegis.lab.web.action.admin;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ScenarioManager;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.lab.web.util.BooleanMapper;

import org.apache.commons.lang.Validate;

/**
 * This class manages actions related to a scenario.  It currently provides fetchScenario and updateScenario actions
 * to support editing of a scenario on the UI.
 *
 * @author Tareq.Nabeel
 */
public class ManageScenario extends BaseAction {

    private static final long serialVersionUID = 1L;

    // Used to retrieve the requested scenario via url
    private Integer id;

    // Used to store request values posted via form controls
    private Scenario scenario;

    // Each element in the list corresponds to one scenario case row
    public List<String> userName;

    // Each element in the list corresponds to one scenario case row
    public List<String> patientId;

    // Each element in the list corresponds to one scenario case row
    public List<String> documentIds;

    // Each element in the list corresponds to one scenario case row
    public List<String> participatingRIs;

    // We don't want to update changedTime and changeUser columns in database when there were no changes.
    // We'll use this to conditionally update the scenario depending on whether or not there were changes on the UI.
    private boolean scenarioModified;

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isScenarioModified() {
        return scenarioModified;
    }

    public void setScenarioModified(boolean scenarioModified) {
        this.scenarioModified = scenarioModified;
    }

    /**
     * This method retrieves the scenario corresponding to the requested scenario id
     *
     * @return Scenario instance
     * @throws ServiceException
     */
    private Scenario retrieveScenario() throws ServiceException {
        // Retrieve the service set using the setName parameter
        Validate.notNull(getId(),"Cannot retrieve Scenario id null");
        Scenario retrievedScenario = ScenarioManager.getInstance().findScenarioById(getId());
        log.info("ManageScenario.retrieveScenario() - "+ (retrievedScenario!=null? " found scenario '" : " cound not find scenario '")+ getId() + "'");
        return retrievedScenario;
    }

    /**
     * This method would get called when user navigates to Scenario e.g. via a link on the scenario list
     *
     * @return success if all went well; error otherwise
     */
    public String fetchScenario() {
        log.info("ManageScenario.fetchScenario()...");
        try {
            Scenario retrievedScenario = retrieveScenario();
            setScenario(retrievedScenario);
            // Retrieved scenario will have "Y"/"N" for initiatorInd and responderInd.
            // Checkbox will not be checked if value is "Y" (Using fieldValue attribute would solve submission for "Y" but not for "N").
            // So we need to convert "Y"/"N" to true/false before page is rendered.  Since these attributes are strings, we'll store as boolean string.
            // Boolean strings get correctly evaulated to booleans by Struts
            retrievedScenario.setInitiatorInd(BooleanMapper.convertYesNoToBooleanString(retrievedScenario.getInitiatorInd()));
            retrievedScenario.setResponderInd(BooleanMapper.convertYesNoToBooleanString(retrievedScenario.getResponderInd()));
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
    public String updateScenario() {
        log.info("ManageScenario.updateScenario() scenarioModified="+isScenarioModified() + "...");
        try {
            // Retrieve the session-managed scenario from database
            Scenario scenarioToUpdate = retrieveScenario();
            // Because scenarioCase does not have a changedtime column in database and to keep things simple
            // we're using scenarioModified for changes to both scenario and scenariocase for conditional updates
            if (isScenarioModified()) {
                // This is the scenario request created and initialized by Struts based on request params.
                Scenario scenarioRequest = getScenario();
                // Update the scenario based on submitted request parameters
                transferScenarioDataFromRequest(scenarioToUpdate,scenarioRequest);
                transferScenarioCaseDataFromRequest(scenarioToUpdate);
                ScenarioManager.getInstance().updateScenarioAndScenarioCases(scenarioToUpdate);

                this.addActionMessage(getText("admin.scenario.updated.successfully"));
            }
            // We need to use scenarioToUpdate for rendering instead of scenarioRequest as scenarioToUpdate will have the retrieved scenarioCases but scenarioRequest will not
            setScenario(scenarioToUpdate);
            // We need to convert Y/N to true/false so UI can display the values correctly
            scenarioToUpdate.setInitiatorInd(BooleanMapper.convertYesNoToBooleanString(scenarioToUpdate.getInitiatorInd()));
            scenarioToUpdate.setResponderInd(BooleanMapper.convertYesNoToBooleanString(scenarioToUpdate.getResponderInd()));
            setScenarioModified(false);
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }

    private void transferScenarioDataFromRequest(Scenario scenarioToUpdate, Scenario scenarioRequest) {
        // Retrieved scenario will have "Y"/"N" for initiatorInd and responderInd.
        // Checkbox will not be checked if value is "Y" (Using fieldValue attribute would solve submission for "Y" but not for "N").
        // So we need to convert "Y"/"N" to true/false before page is rendered.  Since these attributes are strings, we'll store as boolean string.
        // Boolean strings get correctly evaulated to booleans by Struts
        scenarioToUpdate.setInitiatorInd(BooleanMapper.convertBooleanStringToYN(scenarioRequest.getInitiatorInd()));
        scenarioToUpdate.setResponderInd(BooleanMapper.convertBooleanStringToYN(scenarioRequest.getResponderInd()));

        // Transfer other basic attributes of scenario from request to session-managed object
        scenarioToUpdate.setChangedtime(new Timestamp(System.currentTimeMillis()));
        scenarioToUpdate.setChangeduser(getProfile().getUsername());
        scenarioToUpdate.setDescription(scenarioRequest.getDescription());
        scenarioToUpdate.setQuickName(scenarioRequest.getQuickName());
        scenarioToUpdate.setConditionDescription(scenarioRequest.getConditionDescription());
        scenarioToUpdate.setScenarioName(scenarioRequest.getScenarioName());
        scenarioToUpdate.setSsnHandlingInd(scenarioRequest.getSsnHandlingInd());
        scenarioToUpdate.setStatus(scenarioRequest.getStatus());
    }

    private void transferScenarioCaseDataFromRequest(Scenario scenarioToUpdate) {
        if (this.userName==null || scenarioToUpdate.getScenariocases()==null || scenarioToUpdate.getScenariocases().size()==0) {
            log.info("No scenariocases to update for '" + scenarioToUpdate.getScenarioName() + "' scenario");
            return;
        }
        List<Scenariocase> scenarioCases = scenarioToUpdate.getScenariocases();
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
