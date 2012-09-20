/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.caseresult.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.auditsummary.service.AuditSummaryService;
import net.aegis.lab.dao.CaseresultDAO;
import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("caseResultService")
public class CaseResultServiceImpl implements CaseResultService {

    @Autowired
    private CaseresultDAO caseResultDAO;
    @Autowired
    private AuditSummaryService auditSummaryService;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(CaseResultServiceImpl.class);

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Caseresult newInstance) throws ServiceException {
        log.info("CaseResultServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = caseResultDAO.create(newInstance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return id;
    }

    @Override
    public Caseresult read(Integer id) throws ServiceException {
        log.info("CaseResultServiceImpl.read() - INFO");
        Caseresult caseResult = null;
        try {
            caseResult = caseResultDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return caseResult;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Caseresult transientObject) throws ServiceException {
        log.info("CaseResultServiceImpl.update() - INFO");
        try {
            caseResultDAO.update(transientObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Caseresult persistentObject) throws ServiceException {
        log.info("CaseResultServiceImpl.delete(persistentObject) - INFO");
        try {
            caseResultDAO.delete(persistentObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("CaseResultServiceImpl.delete(id) - INFO");
        try {
            caseResultDAO.delete(read(id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }


    /*
     * Finder methods
     */
    @Override
    public Caseresult findByResultId(int caseResultId) throws ServiceException {
        log.info("CaseResultServiceImpl.findByResultId() - INFO");
        return caseResultDAO.read(caseResultId);
    }

    @Override
    public Caseresult findByExecIdAndMaxExecTime(int caseExecutionId) throws ServiceException {
        log.info("CaseResultServiceImpl.findByExecIdAndMaxExecTime() - INFO");
        List<Caseresult> caseResultList = caseResultDAO.findByExecIdAndMaxExecTime(caseExecutionId);
        Caseresult caseResult = null;
        if (caseResultList != null && caseResultList.size() > 0) {
            caseResult = caseResultList.get(0);
        }
        return caseResult;
    }

    @Override
    public Caseresult findByExecIdAndMaxResultId(int caseExecutionId) throws ServiceException {
        log.info("CaseResultServiceImpl.findByExecIdAndMaxResultId() - INFO");
        List<Caseresult> caseResultList = caseResultDAO.findByExecIdAndMaxResultId(caseExecutionId);
        Caseresult caseResult = null;
        if (caseResultList != null && caseResultList.size() > 0) {
            caseResult = caseResultList.get(0);
        }
        return caseResult;
    }

    @Override
    public Caseresult findByParticipantForActivePass(int participantId, String initiatorInd, String responderInd) throws ServiceException {
        log.info("CaseResultServiceImpl.findByParticipantForActivePass() - INFO");
        try {
            List<Object[]> caseResultList = new ArrayList<Object[]>();
            if (initiatorInd != null && initiatorInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassInitiator(participantId);
            }
            else if (responderInd != null && responderInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassResponder(participantId);
            }
            Caseresult caseResult = null;
            if (caseResultList != null && caseResultList.size() > 0) {
                caseResult = populateCaseresult(caseResultList.get(0));
            }
            return caseResult;
        }
        catch (Exception e) {
            log.error("Exception", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Caseresult findByParticipantForActivePassPD(int participantId, String initiatorInd, String responderInd) throws ServiceException {
        log.info("CaseResultServiceImpl.findByParticipantForActivePassPD() - INFO");
        try {
            List<Object[]> caseResultList = new ArrayList<Object[]>();
            if (initiatorInd != null && initiatorInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassPDI(participantId);
            }
            else if (responderInd != null && responderInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassPDR(participantId);
            }
            Caseresult caseResult = null;
            if (caseResultList != null && caseResultList.size() > 0) {
                caseResult = populateCaseresult(caseResultList.get(0));
            }
            return caseResult;
        }
        catch (Exception e) {
            log.error("Exception", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Caseresult findByParticipantForActivePassQD(int participantId, String initiatorInd, String responderInd) throws ServiceException {
        log.info("CaseResultServiceImpl.findByParticipantForActivePassQD() - INFO");
        try {
            List<Object[]> caseResultList = new ArrayList<Object[]>();
            if (initiatorInd != null && initiatorInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassQDI(participantId);
            }
            else if (responderInd != null && responderInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassQDR(participantId);
            }
            Caseresult caseResult = null;
            if (caseResultList != null && caseResultList.size() > 0) {
                caseResult = populateCaseresult(caseResultList.get(0));
            }
            return caseResult;
        }
        catch (Exception e) {
            log.error("Exception", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Caseresult findByParticipantForActivePassRD(int participantId, String initiatorInd, String responderInd) throws ServiceException {
        log.info("CaseResultServiceImpl.findByParticipantForActivePassRD() - INFO");
        try {
            List<Object[]> caseResultList = new ArrayList<Object[]>();
            if (initiatorInd != null && initiatorInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassRDI(participantId);
            }
            else if (responderInd != null && responderInd.equals("Y")) {
                caseResultList = caseResultDAO.findByParticipantForActivePassRDR(participantId);
            }
            Caseresult caseResult = null;
            if (caseResultList != null && caseResultList.size() > 0) {
                caseResult = populateCaseresult(caseResultList.get(0));
            }
            return caseResult;
        }
        catch (Exception e) {
            log.error("Exception", e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    public Caseresult findMaxCaseresultForTestCase(Integer participantId , Integer scenarioId, Integer caseId)throws ServiceException{
    	log.info("findMaxCaseresultForTestCase - INFO");
    	List<Object[]> caseResultList = new ArrayList<Object[]>();
    	Caseresult caseResult = null;
    	try {
            
            if (participantId > 0  && scenarioId !=null &&caseId!=null ) {
                // Note : Passing arguments to DAO layer should not be changed and 
            	// these values are passed to query based on parameters order returned from namedQuery.getNamedParameters() in GenericDaoHibernateImpl class  
            	caseResultList = caseResultDAO.findMaxCaseresultForTestCase(scenarioId ,participantId,caseId);
                if (caseResultList != null && caseResultList.size() > 0) {
                    caseResult = populateCaseresult(caseResultList.get(0));
                }
            }       
        }catch (Exception e) {
	            log.error("findMaxCaseresultForTestCase : Exception", e);
	            throw new ServiceException(e);
	     }
        return caseResult;
    }
    
    
    private Caseresult populateCaseresult(Object[] foundRecord) {
        Caseresult caseResult = new Caseresult();
        int columnCount = foundRecord.length;
        if (columnCount > 0) {
            caseResult.setResultId((Integer)foundRecord[0]);
        }
        if (columnCount > 1) {
            caseResult.setCaseexecution(new Caseexecution());
            caseResult.getCaseexecution().setCaseExecutionId((Integer)foundRecord[1]);
        }
        if (columnCount > 2) {
            caseResult.setExecuteTime((Timestamp)foundRecord[2]);
        }
        if (columnCount > 3) {
            caseResult.setLabInd((String)foundRecord[3]);
        }
        if (columnCount > 4) {
            caseResult.setResult((String)foundRecord[4]);
        }
        if (columnCount > 5) {
            caseResult.setResultInfo((String)foundRecord[5]);
        }
        if (columnCount > 6) {
            caseResult.setDocumentIds((String)foundRecord[6]);
        }
        if (columnCount > 7) {
            caseResult.setLabDocumentIds((String)foundRecord[7]);
        }
        if (columnCount > 8) {
            caseResult.setSubmissionInd((String)foundRecord[8]);
        }
        if (columnCount > 9) {
            caseResult.setMessage((String)foundRecord[9]);
        }
        return caseResult;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer saveCaseResultAndSummary(Caseresult caseResult, Auditsummary auditSummary) throws ServiceException {
        log.info("CaseResultServiceImpl.saveCaseResultAndSummary() - INFO");
       Integer caseResultID = null;
        try {
            caseResultID = this.create(caseResult);
           //Commented for the time being 
          //  auditSummary.setCaseresult(findByResultId(this.create(caseResult)));
           // auditSummaryService.create(auditSummary);
        } catch (Exception ex) {
            throw new ServiceException(ex, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return caseResultID ;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void updateCaseResultAndSaveSummary(Caseresult caseResult, Auditsummary auditSummary) throws ServiceException {
        log.info("CaseResultServiceImpl.saveCaseResultAndSummary() - INFO");
        try {
            update(caseResult);
          //  auditSummary.setCaseresult(caseResult);
          //  auditSummaryService.create(auditSummary);
        } catch (Exception ex) {
            throw new ServiceException(ex, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }
}
