package net.aegis.lab.caseexecution.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.CaseexecutionDAO;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenario;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.participantpatientmap.service.ParticipantPatientMapService;
import net.aegis.lab.util.AuditMessageUtil;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.ri.docrepository.dao.pojo.Document;
import net.aegis.ri.docrepository.service.DocumentService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("caseExecutionService")
public class CaseExecutionServiceImpl implements CaseExecutionService {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(CaseExecutionServiceImpl.class);
    @Autowired
    private CaseexecutionDAO caseExecutionDao;
    @Autowired
    private ParticipantPatientMapService participantPatientMapService;
//    @Autowired
//    private DocumentService documentService;
    ApplicationContext RI1ApplicationContext = ContextUtil.getRIDocRepositoryAppContext();
    ApplicationContext RI2ApplicationContext = ContextUtil.getRI2DocRepositoryAppContext();
    ApplicationContext RI3ApplicationContext = ContextUtil.getRI3DocRepositoryAppContext();
    ApplicationContext RI4ApplicationContext = ContextUtil.getRI4DocRepositoryAppContext();
    
   

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Caseexecution caseExecution) throws ServiceException {
        log.info("saving CaseExecution..");
        try {
            return caseExecutionDao.create(caseExecution);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Caseexecution caseExecution) throws ServiceException {
        log.info("updating CaseExecution..");
        try {
            caseExecutionDao.update(caseExecution);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Caseexecution persistentObject) throws ServiceException {
        log.info("delete CaseExecution..");
        try {
            caseExecutionDao.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(int caseExecutionId) throws ServiceException {
        log.info("deleteById CaseExecution..");
        try {
            caseExecutionDao.delete(findByCaseExecutionId(caseExecutionId));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public Caseexecution read(int caseExecutionId) throws ServiceException {
        log.info("read CaseExecution..");
        try {
            return caseExecutionDao.read(caseExecutionId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

     @Override
    public Caseexecution getCaseExecByMsgTypeAndPatientId(String messageType, String participantObjectID, Participant participant, String userName, Timestamp execTime) throws ServiceException {
        return this.getCaseExecByMsgTypeAndPatientId(messageType, participantObjectID, participant, userName, execTime,null) ;
     }

    /**
     * Attempt to find a case execution exact match within the participant's active scenario exeuctions
     * based on the standard criteria of patient id and message type.  Use userName value if needed
     * to qualify multiple matches.
     * @param messageType
     * @param patientId
     * @param participant
     * @param userName
     * @param execTime
     * @return
     * @throws ServiceException
     */
    @Override
    public Caseexecution getCaseExecByMsgTypeAndPatientId(String messageType, String participantObjectID, Participant participant, String userName, Timestamp execTime,Integer testHarnessId) throws ServiceException {
        log.info("CaseExecutionServiceImpl.getCaseExecByMsgTypeAndPatientId()");
        Caseexecution result = null;

        // check for patient id format and get patient id from message
        String partialPatientId = getPatientIdFromParticipantObjectID(participantObjectID, participant.getParticipantId(),messageType,testHarnessId);
        List<Caseexecution> foundCaseExecutions = new ArrayList<Caseexecution>();
        try {
            // Find all case execution matches based on standard criteria: patient id and message type
            for (Scenarioexecution scenExec : participant.getScenarioexecutions()) {
                for (Caseexecution caseExec : scenExec.getCaseexecutions()) {
                    if (caseExec != null && partialPatientId != null && caseExec.getStatus().equals(Caseexecution.STATUS_ACTIVE) && partialPatientId.equals(caseExec.getPatientId()) && messageType.equals(caseExec.getMessageType()) && caseExec.getBeginTime().before(execTime)) {
                        foundCaseExecutions.add(caseExec);
                    }
                }
            }
            // If one case execution found, set the return value
            if (foundCaseExecutions.size() == 1) {
                result = foundCaseExecutions.get(0);
                log.info("found case execution id --->> " + foundCaseExecutions.get(0).getCaseExecutionId());
            }
            // Else if multiple found, use the provided userName (if not null) to qualify the match
            else if (foundCaseExecutions.size() > 1) {
                // Check for not null userName
                if (userName != null) {
                    int findCount = 0;
                    for (Caseexecution foundExec : foundCaseExecutions) {
                        if (foundExec.getUserName() != null && userName.equals(foundExec.getUserName())) {
                            findCount++;
                            if (findCount == 1) {
                                result = foundExec;
                            } else {
                                result = null;   // Multiple match on userName found! Return no match.
                                log.info("Multiple match on userName found! Return no match. ---->> " + foundExec.getCaseExecutionId());
                                break;
                            }
                        }
                    }
                }
            }
            // Else NO MATCH FOUND on standard criteria
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return result;
    }

    private String getPatientIdFromParticipantObjectID(String participantObjectID, Integer participantId,String messageType) throws ServiceException {
     
       return  this.getPatientIdFromParticipantObjectID(participantObjectID, participantId, messageType,null);
    }
    /**
     * This method is used to extract patient id from audit log patient id
     *
     * @param patientId
     * @return
     */
    private String getPatientIdFromParticipantObjectID(String participantObjectID, Integer participantId, String messageType, Integer testharnessId) throws ServiceException {
        log.info("<--- start getPatientIdFromParticipantObjectID --->");
        String returnPatientId = null;
        String tmpPatientId = null;
        String splitPatientId = null;
        log.info("<--- getPatientIdFromParticipantObjectID ---> participantObjectID " + participantObjectID);
        if (participantObjectID != null) {

            // way to find patient id for PD request , PD response , QD request
            tmpPatientId = participantObjectID.split(LabConstants.SPLITTER_TRIPPLE_CARET)[0];
            // way to find patient id for QD response , RD request , RD response
            tmpPatientId = tmpPatientId.split(LabConstants.SPLITTER_DOUBLE_DOLLER)[0];

            /* if patient ID is always available in PD request , PD response , QD request , QD response
            and is not available in RD request , RD response if resouce id is not passed as part of PD , QD , RD request
             *   fetch patient ID from document repository by using document unique id
             */
            if (!tmpPatientId.contains(LabConstants.SPLITTER_DOUBLE_DOLLER) && messageType.equals(LabConstants.LAB_DOCRETRIEVE)) {
                // if patient id is not found , then get document unique id from "1.RI1.101.00037.33333^^^&2.16.840.1.113883.0.101&ISO"
                if (participantObjectID.contains(LabConstants.SPLITTER_TRIPPLE_CARET)) {
                    String documentUniqueId = AuditMessageUtil.getPatientIdFromCompositePatientId(participantObjectID);
                    if (testharnessId != null) {
                        Document document = null;
                        if (testharnessId == LabConstants.RI1_ID) {
                        	if(RI1ApplicationContext!=null){
                        		DocumentService ri1documentService = (DocumentService)RI1ApplicationContext.getBean("documentService");
                            document = ri1documentService.findDocumentByDocumentUniqueId(documentUniqueId);
                        	}
                        } else if (testharnessId == LabConstants.RI2_ID) {
                        	if(RI2ApplicationContext!=null){
                        		DocumentService ri2documentService = (DocumentService)RI2ApplicationContext.getBean("documentService");
                            document = ri2documentService.findDocumentByDocumentUniqueId(documentUniqueId);
                        	}
                        } else if (testharnessId == LabConstants.RI3_ID) {
                        	if(RI3ApplicationContext!=null){
                        		DocumentService ri3documentService = (DocumentService)RI3ApplicationContext.getBean("documentService");
                            document = ri3documentService.findDocumentByDocumentUniqueId(documentUniqueId);
                        	}
                        }
                        else if (testharnessId == LabConstants.RI4_ID) {
                        	if(RI4ApplicationContext!=null){
                        		DocumentService ri4documentService = (DocumentService)RI4ApplicationContext.getBean("documentService");
                            document = ri4documentService.findDocumentByDocumentUniqueId(documentUniqueId);
                        	}
                        }
                        if (document != null) {
                            returnPatientId = document.getPatientId();
                        }
                    }
                }

            }

            if (tmpPatientId != null) {
                log.info("<--- getPatientIdFromParticipantObjectID ---> tmpPatientId " + tmpPatientId);
                if (tmpPatientId.split(LabConstants.SPLITTER_PERIOD).length >= 2) {
                    // not sure when this format comes
                    if (tmpPatientId.split(LabConstants.SPLITTER_PERIOD).length == 5) {
                        returnPatientId = tmpPatientId.split(LabConstants.SPLITTER_PERIOD)[3];
                    }// Modified by Venkat:::: format may be :::: RI2.102.00008
                    else {
                        returnPatientId = tmpPatientId.split(LabConstants.SPLITTER_PERIOD)[2];
                    }
                }
            }
        }
        // find partial patient id by participant and participant patient id  "CAN20000037"
        log.info("<--- getPatientIdFromParticipantObjectID ---> patientId " + returnPatientId);
        if (returnPatientId == null && participantObjectID != null) {
            // try getting patient id from participant patient map
            log.info("<--- getPatientIdFromParticipantObjectID ---> tmpPatientId " + tmpPatientId);
            if (tmpPatientId != null && tmpPatientId.split(LabConstants.SPLITTER_QUOTE).length >= 2) {
                splitPatientId = tmpPatientId.split(LabConstants.SPLITTER_QUOTE)[1];
            } else {
                splitPatientId = tmpPatientId;
            }
            returnPatientId = participantPatientMapService.getPatientIdFromParticipantPatientMap(splitPatientId, participantId);
        }
        log.info("<--- getPatientIdFromParticipantObjectID ---> returnPatientId " + returnPatientId);
        log.info("<--- end getPatientIdFromParticipantObjectID --->");
        return returnPatientId;
    }

    @Override
    public List<Caseexecution> findByScenarioExecutionId(int scenarioExecutionId) throws ServiceException {
        log.info("findByScenarioExecutionId CaseExecution..");
        try {
            return caseExecutionDao.findByScenarioExecutionId(scenarioExecutionId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Override
    public Caseexecution findByCaseExecutionId(int caseExecutionId) throws ServiceException {
        log.info("findByCaseExecutionId CaseExecution..");
        try {
            return caseExecutionDao.read(caseExecutionId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public int[] getParticipatingRIs(int caseExecId) throws ServiceException {
        log.info("getParticipatingRIs CaseExecution..");
        int[] participatingRIArr = null;
        String[] participatingRIs = null;
        Caseexecution caseExec = null;
        try {
            caseExec = this.read(caseExecId);
            if (caseExec != null && caseExec.getParticipatingRIs() != null) {
                participatingRIs = caseExec.getParticipatingRIs().split(LabConstants.SPLITTER);
                if (participatingRIs != null && participatingRIs.length > 0) {
                    participatingRIArr = new int[participatingRIs.length];
                    for (int i = 0; i < participatingRIs.length; i++) {
                        participatingRIArr[i] = Integer.parseInt(participatingRIs[i]);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return participatingRIArr;
    }

    @Override
    public Caseexecution getCaseExecByScenarioExecIdAndCaseName(Scenarioexecution scenarioExec, String caseName) throws ServiceException {
        Caseexecution caseExecution = null;
        try {
            List<Caseexecution> caseExecutions = this.findByScenarioExecutionId(scenarioExec.getScenarioExecutionId());
            log.debug("****************caseExecutions" + caseExecutions);
            for (Caseexecution caseExec : caseExecutions) {
                if (caseExec.getTestcase().getName().equals(caseName)) {
                    caseExecution = caseExec;
                    break;
                }
            }

        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }


        return caseExecution;
    }

    @Override
    public List<Caseexecution> searchByCriteria(Caseexecution criteria, List<Criterion> criterion) throws ServiceException {
        log.info("CaseExecutionServiceImpl.searchByCriteria() - INFO");
        try {
            return caseExecutionDao.searchByCriteria(criteria, criterion);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Override
    // write a method to get the participantPatientId if we pass scenarioExecutionId
    public String getParticipantPatientId(int scenarioExecId, int caseId) throws ServiceException {

        String participantPatientId = null;
        try {
            Caseexecution criteria = new Caseexecution();
            List<Criterion> criterionList = new ArrayList<Criterion>();
            criterionList.add(Restrictions.eq("scenarioexecution.scenarioExecutionId", scenarioExecId));
            criterionList.add(Restrictions.eq("testcase.caseId", caseId));
            List<Caseexecution> caseExecs = this.searchByCriteria(criteria, criterionList);
            log.info("The case Execution size" + caseExecs.size());
            for (Caseexecution execs : caseExecs) {
                participantPatientId = execs.getParticipantpatientId();
                break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
        return participantPatientId;
    }

    @Override
    public Caseexecution findByParticipantForActiveScenarioCase(Integer participantId, Integer scenarioId, Integer caseId) throws ServiceException {
        try {
            List<Object[]> caseExecutionObjectList = new ArrayList<Object[]>();

            caseExecutionObjectList = caseExecutionDao.findByParticipantForActive(participantId);

            Caseexecution caseexecution = null;
            if (caseExecutionObjectList != null && caseExecutionObjectList.size() > 0) {
                for (Object[] caseExecutionObject: caseExecutionObjectList) {
                    caseexecution = populateCaseexecution(caseExecutionObject);
                    if (caseexecution.getScenarioexecution().getScenario().getScenarioId().compareTo(scenarioId) == 0
                            && caseexecution.getTestcase().getCaseId().compareTo(caseId) == 0) {
                        break;
                    }
                    caseexecution = null;
                }
            }
            return caseexecution;
        }
        catch (Exception e) {
            log.error("Exception", e);
            throw new ServiceException(e);
        }
    }

    private Caseexecution populateCaseexecution(Object[] foundRecord) {
        Caseexecution caseexecution = new Caseexecution();
        int columnCount = foundRecord.length;

        if (columnCount > 0) {
            caseexecution.setCaseExecutionId((Integer)foundRecord[0]);
        }
        if (columnCount > 1) {
            caseexecution.setScenarioexecution(new Scenarioexecution());
            caseexecution.getScenarioexecution().setScenarioExecutionId((Integer)foundRecord[1]);
        }
        if (columnCount > 2) {
            caseexecution.getScenarioexecution().setScenario(new Scenario());
            caseexecution.getScenarioexecution().getScenario().setScenarioId((Integer)foundRecord[2]);
        }
        if (columnCount > 3) {
            caseexecution.setTestcase(new Testcase());
            caseexecution.getTestcase().setCaseId((Integer)foundRecord[3]);
        }
        if (columnCount > 4) {
            caseexecution.setUserName((String)foundRecord[4]);
        }
        if (columnCount > 5) {
            caseexecution.setPatientId((String)foundRecord[5]);
        }
        if (columnCount > 6) {
            caseexecution.setParticipantpatientId((String)foundRecord[6]);
        }
        if (columnCount > 7) {
            caseexecution.setDocumentIds((String)foundRecord[7]);
        }
        if (columnCount > 8) {
            caseexecution.setParticipatingRIs((String)foundRecord[8]);
        }
        if (columnCount > 9) {
            caseexecution.setSuccessCriteria((String)foundRecord[9]);
        }
        if (columnCount > 10) {
            caseexecution.setStatus((String)foundRecord[10]);
        }
        if (columnCount > 11) {
            caseexecution.setBeginTime((Timestamp)foundRecord[11]);
        }
        if (columnCount > 12) {
            caseexecution.setMessageType((String)foundRecord[12]);
        }
        if (columnCount > 13) {
            caseexecution.setDependentScenarioId((Integer)foundRecord[13]);
        }
        if (columnCount > 14) {
            caseexecution.setDependentCaseId((Integer)foundRecord[14]);
        }
        if (columnCount > 15) {
         caseexecution.getTestcase().setName((String)foundRecord[15]);
        }

        return caseexecution;
    }

     @Override
    public java.sql.Timestamp findByMaxStartTimer() throws ServiceException {
        log.info("findByMaxStartTimer CaseExecution..");
        try {
            return caseExecutionDao.findByMaxStartTimer();
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }
     
     @Override
     public String getParticipantPatientIdForRD(Integer participantId , String communityId,String documentUniqueId)throws ServiceException {
     	Caseexecution caseExecutionForRD = getCaseExecutionForRD(participantId, communityId, documentUniqueId);
     	if(caseExecutionForRD!=null ){
     		return caseExecutionForRD.getParticipantpatientId();
     	}
     	return null;
     }
     
     @Override
     public Caseexecution getCaseExecutionForRD(Integer participantId , String communityId,String documentUniqueId)throws ServiceException{
    	 Caseexecution caseexecution  = null;
    	 log.info("findByDocIdAndHCIDAndMaxResultId..");
         try {
        	 
        	List<Object[]> caseExecutionObjectList =  caseExecutionDao.findByParticipantIdAndCommIdAndDocUniqueId(participantId,communityId,documentUniqueId);
			if (caseExecutionObjectList != null	&& caseExecutionObjectList.size() > 0) {
				Object[] eachRow = caseExecutionObjectList.get(0);
				int columnCount = eachRow.length;
				caseexecution = new Caseexecution(); 
				if (columnCount > 0) {
					caseexecution.setCaseExecutionId((Integer) eachRow[0]);
				}
				if (columnCount > 1) {
					caseexecution.setParticipantpatientId((String) eachRow[1]);
				}

			}
         } catch (Exception e) {
             throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
         } 
         return caseexecution ; 
     }
     
     
}