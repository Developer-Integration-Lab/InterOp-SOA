package net.aegis.lab.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import net.aegis.lab.auditsummary.service.AuditSummaryService;
import net.aegis.lab.caseexecution.service.CaseExecutionService;
import net.aegis.lab.caseresult.service.CaseResultService;
import net.aegis.lab.caseresultparams.service.CaseResultParamsService;
import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.resultdocument.service.ResultDocumentService;
import net.aegis.lab.testharnessri.service.TestHarnessriService;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.Format;
import net.aegis.lab.util.LabConstants;
import net.aegis.ri.auditrepo.auditrepository.service.AuditrepoService;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;
import net.aegis.ri.patientcorrelationdb.dao.pojo.Correlatedidentifiers;
import net.aegis.ri.patientcorrelationdb.service.PatientCorrelationService;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;




/**
 *
 * @author mastan.ketha
 */
public class CaseResultManager {

    private static final Log log = LogFactory.getLog(CaseResultManager.class);
    CaseExecutionService caseExecutionService = (CaseExecutionService) ContextUtil.getLabContext().getBean("caseExecutionService");
    CaseResultService caseResultService = (CaseResultService) ContextUtil.getLabContext().getBean("caseResultService");
    ResultDocumentService resultDocumentService = (ResultDocumentService) ContextUtil.getLabContext().getBean("resultDocumentService");
   /* private AuditrepoService ri1AuditrepoService = (AuditrepoService) ContextUtil.getRIAuditRepoAppContext().getBean("auditrepoService");
    private AuditrepoService ri2AuditrepoService = (AuditrepoService) ContextUtil.getRI2AuditRepoAppContext().getBean("auditrepoService");
    private AuditrepoService ri3AuditrepoService = (AuditrepoService) ContextUtil.getRI3AuditRepoAppContext().getBean("auditrepoService");
    private AuditrepoService ri4AuditrepoService = (AuditrepoService) ContextUtil.getRI4AuditRepoAppContext().getBean("auditrepoService");*/
    
    private ApplicationContext RI1AuditRepoAppContext =  ContextUtil.getRIAuditRepoAppContext();
    private  ApplicationContext RI2AuditRepoAppContext =  ContextUtil.getRI2AuditRepoAppContext();
    private  ApplicationContext RI3AuditRepoAppContext =  ContextUtil.getRI3AuditRepoAppContext();
    private  ApplicationContext RI4AuditRepoAppContext =  ContextUtil.getRI4AuditRepoAppContext();
    
    private TestHarnessriService testHarnessriService = (TestHarnessriService) ContextUtil.getLabContext().getBean("testHarnessriService");
    private AuditSummaryService auditSummaryService = (AuditSummaryService) ContextUtil.getLabContext().getBean("auditSummaryService");
    private CaseResultParamsService caseResultParamsService = (CaseResultParamsService) ContextUtil.getLabContext().getBean("caseResultParamsService");
    private PatientCorrelationService patientCorrelationService = null;
    private static CaseResultManager instance;

    private CaseResultManager() {
    }

    /**
     * @return CaseResultManager
     */
    public static CaseResultManager getInstance() {
        if (instance == null) {
            instance = new CaseResultManager();
        }
        return instance;
    }
    
	public int saveCaseResult(Caseresult caseResult) throws ServiceException {
		int id = -1;
		try {
			id = caseResultService.create(caseResult);
		} catch (ServiceException se) {
			log.error("ERROR: Failed to create CaseResult.", se);
			throw se;
		}
		return id;
	}
    
    public int saveCaseResult(Caseexecution caseExecution, String labInd, String result, String labDocumentIds, String submissionInd, String message, String userName) throws ServiceException {
        int id = -1;
        Caseresult caseResult = new Caseresult();
        try {
            caseResult.setCaseexecution(caseExecution);
            log.info("caseExecutionID: " + caseExecution.getCaseExecutionId());
            caseResult.setExecuteTime(new Timestamp(new Date().getTime()));
            caseResult.setLabInd(labInd);
            caseResult.setResult(result);
            caseResult.setLabDocumentIds(labDocumentIds);
            caseResult.setSubmissionInd(submissionInd);
            caseResult.setMessage(message);
            caseResult.setCreatedAt(DateUtil.getTodayDateTime()); 
            caseResult.setCreatedBy(userName);
            id = caseResultService.create(caseResult);
        } catch (ServiceException se) {
            log.error("ERROR: Failed to create CaseResult.", se);
            throw se;
        } catch (Exception e) {
            log.error("ExecuteTestCase - exception occured in saveCaseResult while saving caseresult");
            log.error(e);
        }

        return id;
    }
    
    /**
     * @deprecated
     * @param caseresult
     * @param documentUniqueInd
     * @param repositoryUniqueInd
     * @param remoteCommunityId
     * @param rawData
     * @return
     * @throws ServiceException
     */
    public int saveResultDocument(Caseresult caseresult, String documentUniqueInd, String repositoryUniqueInd, String remoteCommunityId, byte[] rawData) throws ServiceException {
        return this.saveResultDocument(caseresult, documentUniqueInd, repositoryUniqueInd, remoteCommunityId, rawData, null, null, 0,null, null);
    }
    
    /**
     * 
     * @param caseresult
     * @param documentUniqueInd
     * @param repositoryUniqueInd
     * @param remoteCommunityId
     * @param rawData
     * @param classCode
     * @param documentHash
     * @param documentSize
     * @param messageType
     * @param userName 
     * @return
     * @throws ServiceException
     */
    public int saveResultDocument(Caseresult caseresult, String documentUniqueInd, String repositoryUniqueInd, String remoteCommunityId, byte[] rawData, String classCode, String documentHash, int documentSize, String messageType, String userName) throws ServiceException {
        int id = -1;
        Resultdocument resultDocument = new Resultdocument();
        try {
            resultDocument.setCaseresult(caseresult);
            log.info("caseresultId: " + caseresult.getResultId());
            resultDocument.setDocumentUniqueId(documentUniqueInd);
            resultDocument.setRepositoryId(repositoryUniqueInd);
            resultDocument.setCommunityId(remoteCommunityId);
            resultDocument.setRawData(rawData);
            resultDocument.setClassCode(classCode);
            resultDocument.setDocumentHash(documentHash);
            resultDocument.setDocumentSize(new Integer(documentSize));
            resultDocument.setMessageType(messageType);
            resultDocument.setCreatedAt(DateUtil.getTodayDateTime());
    		resultDocument.setCreatedBy(userName);
            id = resultDocumentService.create(resultDocument);
        } catch (ServiceException se) {
            log.error("ERROR: Failed to create resultDocument.", se);
            throw se;
        } /*catch (Exception e) {
            log.error("ExecuteTestCase - exception occured in saveResultDocument while saving resultDocument");
        }*/

        return id;
    }
    
    /**
     * 
     * @param resultDocument
     * @return
     * @throws ServiceException
     */
    public int saveResultDocument(Resultdocument resultDocument) throws ServiceException {
        int id = -1;
        try {
        	id = resultDocumentService.create(resultDocument);
	    } catch (ServiceException se) {
	        log.error("ERROR: Failed to create resultDocument.", se);
	        throw se;
	    } 
	    return id;
    }
    
    public void updateResultDocument(Resultdocument resultDocument) throws ServiceException {
    	 Validate.notNull(resultDocument);
         log.info("Updating resultDocument="+resultDocument.getResultDocumentId()+"'");
         resultDocumentService.update(resultDocument);
    }

    public Caseresult getCaseResult(int caseResultId) throws ServiceException {
        Caseresult caseResult = new Caseresult();
        try {
            caseResult = caseResultService.read(caseResultId);
        } catch (ServiceException se) {
            log.error("ERROR: Failed to read CaseResult.", se);
            throw se;
        } catch (Exception e) {
            log.error("ExecuteTestCase - exception occured in retrieving caseresult");
            log.error(e);
        }

        return caseResult;
    }

    /**
     * @deprecated
     * @see #getResultDocumentsForActivePass
     * @param participant
     * @param rdCaseExecution
     * @param dependentScenarioId
     * @param dependentCaseId
     * @return List<Resultdocument>
     * @throws ServiceException
     */
    
    public List<Resultdocument> getResultDocumentsForCurrentParticipantScenarioCase(Participant participant, Integer dependentScenarioId, Integer dependentCaseId) throws ServiceException {
        List<Resultdocument> resultDocuments = new ArrayList<Resultdocument>();

        try {
            if (participant != null && dependentScenarioId != null && dependentCaseId != null) {
                Caseexecution caseexecution = caseExecutionService.findByParticipantForActiveScenarioCase(participant.getParticipantId(), dependentScenarioId, dependentCaseId);
                if (caseexecution != null) {
                    Caseresult caseresult = caseResultService.findByExecIdAndMaxResultId(caseexecution.getCaseExecutionId());
                    if (caseresult != null && caseresult.getResultdocuments() != null && caseresult.getResultdocuments().size() > 0) {
                        resultDocuments = caseresult.getResultdocuments();
                    }
                }
            }
        } catch (Exception e) {
            log.error("getResultDocumentsForCurrentParticipantScenarioCase - Exception", e);
            throw new ServiceException(e);
        }

        return resultDocuments;
    }
    
    public List<Resultdocument> getDocumentsForTestCase(Integer participantId, Integer dependentScenarioId, Integer dependentCaseId) throws ServiceException {
    	log.debug("getDocumentsForTestCase - Start");
    	List<Resultdocument> resultDocuments = new ArrayList<Resultdocument>();
        try {
            Caseresult caseresult = caseResultService.findMaxCaseresultForTestCase(participantId,dependentScenarioId, dependentCaseId);
            if(caseresult!=null ){
	            // Note : the associated resultdocuments are not fetched as part of caseresult . Find resultDocuments By caseresultId
	            Caseresult findByResultId = caseResultService.findByResultId(caseresult.getResultId());
	            if (findByResultId != null && findByResultId.getResultdocuments() != null && findByResultId.getResultdocuments().size() > 0) {
	                resultDocuments = findByResultId.getResultdocuments();
	            }
            }
            
        } catch (Exception e) {
            log.error("getDocumentsForTestCase - Exception", e);
            throw new ServiceException(e);
        }

        return resultDocuments;
    }

   
    
    // update audit repo list with test harness ids
    private void updateAuditRepoList(List<Auditrepository> ri1AuditRepoList, Integer testharnessId) throws ServiceException {
        for (Auditrepository auditRepo : ri1AuditRepoList) {
            auditRepo.setTestHarnessId(testharnessId);
        }
    }

    

    private boolean checkDuplicate(Auditrepository auditLog) {
        log.info("<--- start checkDuplicate --->");
        List<Auditsummary> summaryList = null;
        try {
            summaryList = auditSummaryService.findByRepositoryIdAndTestharnessId(auditLog.getId(), auditLog.getTestHarnessId());
            log.debug("check duplicate --->>" + summaryList);
        } catch (ServiceException ex) {
            log.error("Service level exception occured " + ex.getExceptionMessage());
        }
        if (summaryList != null && summaryList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param riId
     * @param participantAA
     * @param participantPatientId
     * @return
     */
    public Timestamp getCorrelationExpirationDate(String riId, String participantAA, String participantPatientId) {
        //riId is to find out which data base to go to (RI1/RI2...)
        //participantAA and participantPatientId are query params
        //return null if no record found
        //return CorrelationExpirationDate if match found
        //Note: Assuming participant AA as participant HCID, otherwise we will never know the participant AA before correlation
        //Note: As we discussed yesterday, participantAA and participantPatientId combination can be on either side of the table
        Timestamp correlationExpDate = null;
        if (riId!=null) {
            if (riId.trim().equals(LabConstants.RI1_ID.toString())) {
                ApplicationContext riPatientCorrelationDbAppContext = ContextUtil.getRIPatientCorrelationDbAppContext();
                if(riPatientCorrelationDbAppContext!=null){
                	patientCorrelationService = (PatientCorrelationService) riPatientCorrelationDbAppContext.getBean("correlationService");
                }
            } else if ((riId.trim().equals(LabConstants.RI2_ID.toString()))) {
                ApplicationContext ri2PatientCorrelationDbAppContext = ContextUtil.getRI2PatientCorrelationDbAppContext();
				if (ri2PatientCorrelationDbAppContext!=null) {
					patientCorrelationService = (PatientCorrelationService) ri2PatientCorrelationDbAppContext
							.getBean("correlationService");
				}
            } else if ((riId.trim().equals(LabConstants.RI3_ID.toString()))) {
                ApplicationContext ri3PatientCorrelationDbAppContext = ContextUtil.getRI3PatientCorrelationDbAppContext();
                if(ri3PatientCorrelationDbAppContext!=null){
                	patientCorrelationService = (PatientCorrelationService) ri3PatientCorrelationDbAppContext.getBean("correlationService");
                }
            }
            else if ((riId.trim().equals(LabConstants.RI4_ID.toString()))) {
                ApplicationContext ri4PatientCorrelationDbAppContext = ContextUtil.getRI4PatientCorrelationDbAppContext();
                if(ri4PatientCorrelationDbAppContext!=null){
                	patientCorrelationService = (PatientCorrelationService) ri4PatientCorrelationDbAppContext.getBean("correlationService");
                }
            }
        }
        if (patientCorrelationService==null) {
            return null;
        }
        if (patientCorrelationService != null && patientCorrelationService.findAllCorrelation() != null && patientCorrelationService.findAllCorrelation().size() > 0) {
            for (Correlatedidentifiers correlation : patientCorrelationService.findAllCorrelation()) {
                if ((correlation.getPatientAssigningAuthorityId().equals(participantAA) && correlation.getPatientId().equals(participantPatientId)) ||
                        (correlation.getCorrelatedPatientAssignAuthId().equals(participantAA) && correlation.getCorrelatedPatientId().equals(participantPatientId))) {
                    correlationExpDate = correlation.getCorrelationExpirationDate();
                    break;
                }
            }
        }
        return correlationExpDate;
    }

    /**
     * 
     * @param riId
     * @param participantAA
     * @param participantPatientId
     * @param correlationExpirationDate
     * @return
     */
    public boolean isCorrelationExpirationDateSameAsBefore(Integer riId, String participantAA, String participantPatientId, Timestamp correlationExpirationDate) {
        //riId is to find out which data base to go to (RI1/RI2)
        //return true if expiration date is same as before, else return false
        boolean checkExpDate = false;
        if (riId == 1) {
            ApplicationContext riPatientCorrelationDbAppContext = ContextUtil.getRIPatientCorrelationDbAppContext();
            if(riPatientCorrelationDbAppContext!=null){
            	patientCorrelationService = (PatientCorrelationService) riPatientCorrelationDbAppContext.getBean("correlationService");
            }
        } else if (riId == 2) {
            ApplicationContext ri2PatientCorrelationDbAppContext = ContextUtil.getRI2PatientCorrelationDbAppContext();
            if(ri2PatientCorrelationDbAppContext!=null){
            	patientCorrelationService = (PatientCorrelationService) ri2PatientCorrelationDbAppContext.getBean("correlationService");
            }
        }
        if (patientCorrelationService != null && patientCorrelationService.findAllCorrelation().size() > 0) {
            for (Correlatedidentifiers correlation : patientCorrelationService.findAllCorrelation()) {
                if ((correlation.getPatientAssigningAuthorityId().equals(participantAA) && correlation.getPatientId().equals(participantPatientId)) ||
                        (correlation.getCorrelatedPatientAssignAuthId().equals(participantAA) && correlation.getCorrelatedPatientId().equals(participantPatientId))) {
                    if (correlationExpirationDate.equals(correlation.getCorrelationExpirationDate())) {
                        checkExpDate = true;
                        break;
                    }
                }
            }
        }
        return checkExpDate;
    }

    public boolean addCaseResultPatientId(String patientAssigningAuthorityId, String patientId, String correlatedPatientAssignAuthId) {
        log.info("addCaseResultPatientId - START");
        patientCorrelationService = (PatientCorrelationService) ContextUtil.getRI3PatientCorrelationDbAppContext().getBean("correlationService");
        boolean rs = patientCorrelationService.addCorrelationPatientId(patientAssigningAuthorityId, patientId, correlatedPatientAssignAuthId);
        if (rs==true) {
            log.info("addCaseResultPatientId - SUCCESS");
        } else
            log.info("addCaseResultPatientId - FAILED");
        return rs;
    }

    public Timestamp getStartTimeForCaseExecution(Caseexecution caseexecution) throws ServiceException {
        log.info("getStartTimeForCaseExecution - START");

        Timestamp startTime = null;

        try {
            if (caseexecution != null && caseexecution.getParticipatingRIs() != null && caseexecution.getParticipatingRIs().split(LabConstants.SPLITTER) != null) {
                // Check the time on each participating ri and take the earliest one
                log.info("Check the time on each participating ri and take the earliest one");
                String[] ris = caseexecution.getParticipatingRIs().split(LabConstants.SPLITTER);
                Timestamp tempTime = null;
                for (String ri : ris) {
                    if (Format.isNumericString(ri)) {
                    	if(RI1AuditRepoAppContext!=null){
	                    	AuditrepoService ri1AuditrepoService = (AuditrepoService) RI1AuditRepoAppContext.getBean("auditrepoService");
	                        tempTime = ri1AuditrepoService.getCurrentTimeOnRI(new Integer(ri));
	                        log.info("ri [" + ri + "] - time is " + Format.getFormattedDate("yyyy-MM-dd HH:mm:ss.SSS", tempTime));
                    	}
                    }
                    if (tempTime != null) {
                        if (startTime == null || tempTime.before(startTime)) {
                            startTime = tempTime;
                        }
                    }
                }
            }

            if (startTime == null) {
                startTime = new Timestamp(new Date().getTime());
            }
        } catch (Exception e) {
            log.error("getStartTimeForCaseExecution - Exception", e);
            throw new ServiceException(e);
        }

        return startTime;
    }

    /**
     * Returns the most recent case result for a given case execution
     * This is not a reliable way to get latest case result as we sometimes have two records with the same executeTime (tracked up to the second only)
     * for the same caseExecutionId.  Use findByExecIdAndMaxResultId instead
     *
     * @param caseExecutionId
     * @return
     * @throws ServiceException
     * @deprecated
     */
    public Caseresult findByExecIdAndMaxExecTime(int caseExecutionId) throws ServiceException {
        return caseResultService.findByExecIdAndMaxExecTime(caseExecutionId);
    }

    /**
     * Returns the most recent case result for a given case execution
     *
     * @param caseExecutionId
     * @return
     * @throws ServiceException
     */
    public Caseresult findByExecIdAndMaxResultId(int caseExecutionId) throws ServiceException {
        return caseResultService.findByExecIdAndMaxResultId(caseExecutionId);
    }

    /**
     * Updates the given caseresult
     *
     * @param transientObject
     * @throws ServiceException
     */
    public void update(Caseresult caseresult) throws ServiceException {
        Validate.notNull(caseresult);
        log.info("Updating caseresult="+caseresult.getResultId()+"'");
        caseResultService.update(caseresult);
    }
    
    private void processCaseresultparametersForResponderScenario( List<Auditrepository>  processedAuditRepoList ,Auditrepository auditRepo , Integer resultId , String messageType ) throws ServiceException, JAXBException
    {
        // create new record in to caseresultparameters
        //List<Caseresultparameters> caseresultparametersList = AuditMessageExtract.extractCaseResultParametersFromAuditExtension(auditRepo, resultId, messageType);
        // Jyoti Devarakonda This code has been commneted on behalf of ILT-176
        //caseResultParamsService.saveCaseResultParametersList(caseresultparametersList);
        processedAuditRepoList.add(auditRepo);
    }
}
