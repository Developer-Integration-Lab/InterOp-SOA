package net.aegis.lab.scenarioexecution;

/**
 * This class is used by automated tests to execute scenarios and verify results
 *
 * @author Tareq.Nabeel
 */
public class AltScenarioExecutor extends ScenarioExecutor{

   public AltScenarioExecutor(){

       testAltScenarioCase = true;
       System.out.println("testAltScenarioCase =" + testAltScenarioCase);
   }

}
