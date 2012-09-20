/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.caseresultparams.service;

import java.util.List;

import net.aegis.lab.caseresult.service.CaseResultService;
import net.aegis.lab.dao.CaseresultparametersDAO;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Caseresultparameters;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Venkat Keesara
 */
@Service("caseResultParamsService")
public class CaseResultParamsServiceImpl implements CaseResultParamsService {

    @Autowired
    private CaseresultparametersDAO caseResultParamsDAO;

    @Autowired
    private CaseResultService caseResultService ;

    private static final Log log = LogFactory.getLog(CaseResultParamsServiceImpl.class);

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Caseresultparameters newInstance) throws ServiceException {
        log.info("CaseResultParamsServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = caseResultParamsDAO.create(newInstance);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return id;
    }

    @Override
    public boolean saveCaseResultParametersList(List<Caseresultparameters> caseresultparametersList) throws ServiceException {
        
        boolean isSuccess = false;
        for(Caseresultparameters newInstance : caseresultparametersList)
        {
            Testcase testCase = getTestCaseByresultId(newInstance.getCaseresult().getResultId());
            Integer caseResultParamID = this.create(newInstance);
            if(caseResultParamID!=null)
            {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    @Override
    public Testcase getTestCaseByresultId(Integer resultId) throws ServiceException {
    log.info("CaseResultParamsServiceImpl.getTestCaseByresultId() - Start");
        Testcase testCase=null;
        Caseresult caseResult = caseResultService.findByResultId(resultId);
        if(caseResult!=null )
        {
             Caseexecution caseexecution = caseResult.getCaseexecution();
             caseexecution.getScenarioexecution().getScenario().getScenarioId();
             if(caseexecution!=null)
             {
                 testCase= caseexecution.getTestcase();
                
             }
        }
        log.info("CaseResultParamsServiceImpl.getTestCaseByresultId() testCaseId===" + testCase.getCaseId());
        log.info("CaseResultParamsServiceImpl.getTestCaseByresultId() - Exit");
        return testCase;
    }

    @Override
    public Caseresultparameters read(Integer id) throws ServiceException {
        log.info("CaseResultParamsServiceImpl.read() - INFO");
        Caseresultparameters Caseresultparameters = null;
        try {
            Caseresultparameters = caseResultParamsDAO.read(id);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return Caseresultparameters;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Caseresultparameters transientObject) throws ServiceException {
        log.info("CaseResultParamsServiceImpl.update() - Start INFO");
        try {
            caseResultParamsDAO.update(transientObject);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
        log.info("CaseResultParamsServiceImpl.update() - End INFO");
    }

    /*
     *  Some how the update is not working from here and this method logic is moved to CaseResultParamsManager and it is working
        from manager and public class CaseResultParamsManagerTest
 
     */
    @Deprecated
    @Override
    public void updateResultIdByResultId( Integer newResultId , Integer oldResultId )throws ServiceException
    {
         log.info("CaseResultParamsServiceImpl.updateResultIdByResultId() Start- INFO");
         log.info("CaseResultParamsServiceImpl.updateResultIdByResultId() ::newResultId== " + newResultId + "::::oldResultId==" + oldResultId);
         
        try {
            List<Caseresultparameters> caseResultParamsList = findByResultId(oldResultId);
            Caseresult fetchedNewResult = caseResultService.findByResultId(newResultId);
            if(fetchedNewResult!=null && fetchedNewResult.getResultId()!=null)
            {
                for (Caseresultparameters transientObject : caseResultParamsList) {
                    transientObject.setCaseresult(fetchedNewResult);
                    log.info("CaseResultParamsServiceImpl.updateResultIdByResultId() Before Update calling- INFO");
                    this.update(transientObject);
                }
            }
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
         log.info("CaseResultParamsServiceImpl.updateResultIdByResultId() End- INFO");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Caseresultparameters persistentObject) throws ServiceException {
        log.info("CaseResultParamsServiceImpl.delete(persistentObject) - INFO");
        try {
            caseResultParamsDAO.delete(persistentObject);
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("CaseResultParamsServiceImpl.delete(id) - INFO");
        try {
            caseResultParamsDAO.delete(read(id));
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }


    /*
     * Finder methods
     */
    @Override
    public Caseresultparameters findByCaseResultParamsId(int caseResultParamsId) throws ServiceException {
        log.info("CaseResultParamsServiceImpl.findByCaseResultParamsId() - INFO");
        return caseResultParamsDAO.read(caseResultParamsId);
    }

    @Override
    public List<Caseresultparameters> findByResultId(Integer resultId) throws ServiceException {
        log.info("CaseResultParamsServiceImpl.findByResultId() - INFO");
        return caseResultParamsDAO.findByResultId(resultId);
    }
}
