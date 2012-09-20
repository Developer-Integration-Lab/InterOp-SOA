package net.aegis.lab.scenarioexecution;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This class can be used to execute all of the scenarios as if they were being executed on LabParticipant UI.
 *
 * @author Tareq.Nabeel
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({InitiatorScenarioTests.class, ResponderScenarioTests.class})
public class AllScenarios {}