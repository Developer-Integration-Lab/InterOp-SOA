/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.ri.ws.scenario;

import net.aegis.tp.ws.ProcessTestCaseRequestType;
import net.aegis.tp.ws.ProcessTestCaseResponseType;

/**
 *
 * @author Naresh.Jaganathan
 */
public class TestPorvider {

     public static void main(String[] args){
        ServiceProcessor pd = new ServiceProcessor();
        ProcessTestCaseRequestType requestType = new ProcessTestCaseRequestType();
        ProcessTestCaseResponseType result = new ProcessTestCaseResponseType();
        String serviceEndpointURL = "http://soademo0.aegis.net:7001/TestCaseProcessor/processTestCase";
        String targetVersion = "Connect_2.4.8";
        int candidateId = 8;
        int scenarioExecutionId = 467;
        int caseExecutionId = 23;
        String scenarioLevelExecution = "Y";
        result = pd.executeRequest(requestType, serviceEndpointURL);
        System.out.println("Result : "+ result.getStatus());
     }
}
