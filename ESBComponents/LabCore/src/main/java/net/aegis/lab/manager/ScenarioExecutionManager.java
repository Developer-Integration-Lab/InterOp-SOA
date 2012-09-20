/**
 * 
 */
package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.scenarioexecution.service.ScenarioExecutionService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
/**
 * @author Naresh.Jaganathan
 *
 */
public class ScenarioExecutionManager {

    private static final Log log = LogFactory.getLog(ScenarioExecutionManager.class);
    public static ScenarioExecutionManager instance;
    private ScenarioExecutionService scenarioExecutionService = (ScenarioExecutionService) ContextUtil.getLabContext().getBean("scenarioExecutionService");

    public ScenarioExecutionManager() {
    }
     /**
     * @return ScenarioExecutionManager
     */
    public static ScenarioExecutionManager getInstance(){
        if(instance == null){
            instance = new ScenarioExecutionManager();
        }
        
        return instance;
    }

     /**
     * Return scenarioexecution by id
     * @param scenarioExecutionId
     * @return
     * @throws ServiceException
     */
    public Scenarioexecution findByScenarioExecutionId(int scenarioExecutionId) throws ServiceException {
        log.info("ScenarioExecutionManager.findByScenarioExecutionId() id="+scenarioExecutionId);
        try {
        	List<Scenarioexecution> scenarioExecutions= scenarioExecutionService.findByScenarioExecutionId(scenarioExecutionId);
        	if(scenarioExecutions!=null && scenarioExecutions.size() > 0 ){
        		return (Scenarioexecution)scenarioExecutions.get(0);
        	}
        } catch (ServiceException se) {
            log.error("ERROR: failure finding scenarioexecution: '"+scenarioExecutionId+"'", se);
            se.setErrorCode("errors.find.scenarioexecution.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenarioexecution: '"+scenarioExecutionId+"'", e);
            ServiceException se = new ServiceException("Failure finding scenarioexecution: '"+scenarioExecutionId+"'", "errors.find.scenarioexecution.failed", e);
            se.setLogged();
            throw se;
        }
        return null;
    }

      /**
     * this a finder of Scenarioexecution based on executionUniqueExecId
     * @param executionUniqueExecId
     * @return List<Scenarioexection>
     * @throws ServiceException
     */
    public List<Scenarioexecution> findByUniqueExecutionId(String executionUniqueExecId) throws ServiceException {
        log.info("ScenarioExecutionManager.findByUniqueExecutionId() id="+executionUniqueExecId);
        List<Scenarioexecution> scenarioExecutionList = null;
        try {
            scenarioExecutionList = scenarioExecutionService.findByUniqueExecutionId(executionUniqueExecId);
        }  catch (ServiceException se) {
            log.error("ERROR: failure finding scenarioexecution: '"+executionUniqueExecId+"'", se);
            se.setErrorCode("errors.find.scenarioexecution.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenarioexecution: '"+executionUniqueExecId+"'", e);
            ServiceException se = new ServiceException("Failure finding scenarioexecution: '"+executionUniqueExecId+"'", "errors.find.scenarioexecution.failed", e);
            se.setLogged();
            throw se;
        }
        return scenarioExecutionList;
    }

     /**
     * @param pstrUniqueExecId  
     * @param pstrStatus       
     * @return                  all matching scenario executions
     * @throws ServiceException
     */
    public List<Scenarioexecution> findByUniqueExecutionIdAndStatus(String pstrUniqueExecId, String pstrStatus) throws ServiceException {

        log.info("ScenarioExecutionServiceImpl.findByUniqueExecutionIdAndStatus() - INFO");
        List<Scenarioexecution> scenarioExecutionList = null;
        try {
            scenarioExecutionList = scenarioExecutionService.findByUniqueExecutionIdAndStatus(pstrUniqueExecId, pstrStatus);
        }  catch (ServiceException se) {
            log.error("ERROR: failure finding scenarioexecution: '"+pstrUniqueExecId+"' & '"+pstrStatus+"'", se);
            se.setErrorCode("errors.find.scenarioexecution.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenarioexecution: '"+pstrUniqueExecId+"'", e);
            ServiceException se = new ServiceException("Failure finding scenarioexecution: '"+pstrUniqueExecId+"' & '"+pstrStatus+"'", "errors.find.scenarioexecution.failed", e);
            se.setLogged();
            throw se;
        }
        return scenarioExecutionList;
    }
    
    
  
    /**
     * @param participantId
     * @param status
     * @return
     * @throws ServiceException
     */
    public List<Scenarioexecution> findByParticipantIdAndStatus(int participantId, String status) throws ServiceException {

        log.info("ScenarioExecutionManager.findByParticipantIdAndStatus() - INFO");
        List<Scenarioexecution> scenarioExecutionList = null;
        try {
            scenarioExecutionList = scenarioExecutionService.findByParticipantIdAndStatus(participantId, status);
        }  catch (ServiceException se) {
            log.error("ERROR: Failure finding scenarioexecution: '"+participantId+"' & '"+status+"'", se);
            se.setErrorCode("errors.find.scenarioexecution.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenarioexecution: '"+participantId+"'", e);
            ServiceException se = new ServiceException("Failure finding scenarioexecution: '"+participantId+"' & '"+status+"'", "errors.find.scenarioexecution.failed", e);
            se.setLogged();
            throw se;
        }
        return scenarioExecutionList;
    }

     /**
     * This method is get Scenarioexecutions for given executionUniqueId
     * @param executionUniqueId
     * @return List<Scenarioexecution>
     * @throws ServiceException
     */
    public List<Scenarioexecution> getScenarExecsByServiceSetUniqueExecId(String executionUniqueId) throws ServiceException {
        List<Scenarioexecution> scenarioExecutions = null;
         try {
            scenarioExecutions = scenarioExecutionService.getScenarExecsByServiceSetUniqueExecId(executionUniqueId);
        }  catch (ServiceException se) {
            log.error("ERROR: failure finding scenarioexecution: '"+executionUniqueId+"'", se);
            se.setErrorCode("errors.find.scenarioexecution.failed");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding scenarioexecution: '"+executionUniqueId+"'", e);
            ServiceException se = new ServiceException("Failure finding scenarioexecution: '"+executionUniqueId+"'","errors.find.scenarioexecution.failed", e);
            se.setLogged();
            throw se;
        }
        return scenarioExecutions;
    }
}
