/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.helper;

import java.text.SimpleDateFormat;

import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class UniqueExecutionIdHelper {


   public static final String getServiceSetUniqueExecutionId(Integer participantId, long time )throws ServiceException{

        SimpleDateFormat formatter =  new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
        String serviceSetUniqueExecId = participantId.toString()+"."+formatter.format(time);
        return serviceSetUniqueExecId ;
   }


   public static final String getScenarioExeUniqueExecutionId(String serviceSetUniqueExecId,Integer scenarioId)throws ServiceException {
     
        String scenarioExecUniqueExecutionId = serviceSetUniqueExecId+"."+scenarioId.toString();
        return scenarioExecUniqueExecutionId ;
   }

}
