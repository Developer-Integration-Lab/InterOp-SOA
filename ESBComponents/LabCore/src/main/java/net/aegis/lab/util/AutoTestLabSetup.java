package net.aegis.lab.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.SecurityManager;
import net.aegis.lab.util.LabConstants.LabType;

import org.apache.commons.lang.Validate;

/**
 * This class is used to for automated testing.  It should be arguably part of the test directory instead of src.
 *
 * It's configuration is driven by autoTestLabSetup.properties.
 *
 * @author Tareq.Nabeel
 */
public class AutoTestLabSetup {

    private static final AutoTestLabSetup me = new AutoTestLabSetup();
    //private static final String SCENARIO = "Scenario";
    private AutoTestLogger logger = AutoTestLogger.instance();
    private boolean executeCoordinator = true;
    private boolean logCoordinatorOutput = false;
    private boolean enabled = false;

    // You can override this from JUnit test and call load() if you haven't loaded yet or reload() with different participantId
    private Integer participantId = 2;
    // You can override this from JUnit test and call load() if you haven't loaded yet or reload() with different userName
    private String userName = "test1";
    // You can override this from JUnit test and call load() if you haven't loaded yet or reload() with different password
    private String password = "test1";

    private boolean loaded;
    private User profile;
    private Map<String,Scenarioexecution> responderScenarioExecutionMap = new HashMap<String,Scenarioexecution>();
    private Map<String,Scenarioexecution> initiatorScenarioExecutionMap = new HashMap<String,Scenarioexecution>();
    //Default lab type is Lab
    private LabType selectedLabType = LabType.LAB;


    private AutoTestLabSetup() {}

    public static AutoTestLabSetup instance() {
        return instance(LabType.LAB);
    }
    public static AutoTestLabSetup instance(LabType labType) {
        if(labType==null || labType.equals("")){
          labType=  LabType.LAB;
        }
        me.setSelectedLabType(labType);
        return me;
    }
    public AutoTestLabSetup load() throws ServiceException {
        synchronized (me) {
            Validate.notNull(participantId);
            if (!loaded) {
                loadProfile();
                loadScenarioExecutionMaps();
            }
            loaded = true;
        }
        return this;
    }

    public AutoTestLabSetup reload() throws ServiceException {
        this.loaded = false;
        return load();
    }

    public Scenarioexecution getInitiatorScenarioExecution(int scenarioNumber) throws ServiceException {
        return load().getInitiatorScenarioExecutionMap().get(String.valueOf(scenarioNumber));
    }

    public Scenarioexecution getResponderScenarioExecution(int scenarioNumber) throws ServiceException {
        return load().getResponderScenarioExecutionMap().get( String.valueOf(scenarioNumber));
    }

    public Scenarioexecution getInitiatorScenarioExecution(String scenarioName) throws ServiceException {
        return load().getInitiatorScenarioExecutionMap().get(scenarioName);
    }

    public Scenarioexecution getResponderScenarioExecution(String scenarioName) throws ServiceException {
        return load().getResponderScenarioExecutionMap().get(scenarioName);
    }

    public Scenarioexecution getScenarioExecution(int scenarioNumber) throws ServiceException {
        return getScenarioExecution(String.valueOf(scenarioNumber));
    }

    public Scenarioexecution getScenarioExecution(String scenarioName) throws ServiceException {
        Scenarioexecution scenarioExecution = getInitiatorScenarioExecution(scenarioName);
        if (scenarioExecution!=null) {
            return scenarioExecution;
        } else {
            return getResponderScenarioExecution(scenarioName);
        }
    }

    public Map<String, Scenarioexecution> getInitiatorScenarioExecutionMap() {
        return initiatorScenarioExecutionMap;
    }

    public Map<String, Scenarioexecution> getResponderScenarioExecutionMap() {
        return responderScenarioExecutionMap;
    }

    private void loadProfile() throws ServiceException {
        if (profile==null) {
            profile = SecurityManager.getInstance().authenticate(userName, password);
        }
    }

    private void loadScenarioExecutionMaps() throws ServiceException {
        long timeStarted = System.currentTimeMillis();
        Validate.notNull(participantId);
        List<Scenarioexecution> scenarioExecutions = ParticipantManager.getInstance().activeScenarioExecutions(participantId, selectedLabType);
        Validate.notNull(scenarioExecutions);
        Validate.isTrue(scenarioExecutions.size()>0);
        for (Scenarioexecution scenarioexecution : scenarioExecutions) {
            Scenario scenario = scenarioexecution.getScenario();
            if (scenario.isResponder()) {
                if (scenario.isInitiator()) {
                    throw new IllegalStateException(scenario.getScenarioName() + " is both initiator and responder");
                }
                responderScenarioExecutionMap.put(scenarioexecution.getScenario().getScenarioId().toString(), scenarioexecution);
            } else if (scenario.isInitiator()) {
                if (scenario.isResponder()) {
                    throw new IllegalStateException(scenario.getScenarioName() + " is both initiator and responder");
                }
                initiatorScenarioExecutionMap.put(scenarioexecution.getScenario().getScenarioId().toString(), scenarioexecution);
            }
        }
        String elapsedMessage = DateUtil.getElapsedTime(timeStarted, System.currentTimeMillis(), false);
        //logger.info("Loaded active scenarios for participantId="+participantId + " in "+elapsedMessage + " responderScenarios="+responderScenarioExecutionMap.keySet()+" initiatorScenarios="+initiatorScenarioExecutionMap.keySet());
        logger.info("Loaded active scenarios for participantId="+participantId + " in "+elapsedMessage);
    }

    public String getPassword() {
        return password;
    }

    public AutoTestLabSetup setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public AutoTestLabSetup setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public AutoTestLabSetup setParticipantId(Integer participantId) {
        this.participantId = participantId;
        return this;
    }

    public User getProfile() {
        return profile;
    }

    public AutoTestLabSetup setProfile(User profile) {
        this.profile = profile;
        return this;
    }

    public boolean isExecuteCoordinator() {
        return executeCoordinator;
    }

    public AutoTestLabSetup setExecuteCoordinator(boolean executeCoordinator) {
        this.executeCoordinator = executeCoordinator;
        return this;
    }

    public boolean logCoordinatorOutput() {
        return logCoordinatorOutput;
    }

    public void setLogCoordinatorOutput(boolean logCoordinatorOutput) {
        this.logCoordinatorOutput = logCoordinatorOutput;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public LabType getSelectedLabType() {
        return selectedLabType;
    }

    public void setSelectedLabType(LabType selectedLabType) {
        this.selectedLabType = selectedLabType;
    }

    


}
