package net.aegis.lab.manager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.aegis.lab.auditsummary.service.AuditSummaryService;
import net.aegis.lab.caseresult.service.CaseResultService;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;
import net.aegis.ri.auditrepo.auditrepository.service.AuditrepoService;

import org.springframework.context.ApplicationContext;

/**
 *
 * @author Ram Ghattu
 */
public class ApplicationCleanupManager {

    // cleanup services for lab
    AuditSummaryService auditSummaryService = (AuditSummaryService) ContextUtil.getLabContext().getBean("auditSummaryService");
    CaseResultService caseResultService = (CaseResultService) ContextUtil.getLabContext().getBean("caseResultService");
    // cleanup services for RI
//    AuditrepoService auditRepoService = (AuditrepoService) ContextUtil.getRIAuditRepoAppContext().getBean("auditrepoService");
    ApplicationContext RI1AuditRepoAppContext =  ContextUtil.getRIAuditRepoAppContext();
//    DocumentService docService = (DocumentService) ContextUtil.getRIDocRepositoryAppContext().getBean("documentService");
//    PatientCorrelationService patientCorrelationService = (PatientCorrelationService) ContextUtil.getRIPatientCorrelationDbAppContext().getBean("correlationService");
    // cleanup services for Participant
    AuditrepoService auditRepoParticipantService = (AuditrepoService) ContextUtil.getParticipantAuditRepoAppContext().getBean("auditrepoService");
//    DocumentService docParticipantService = (DocumentService) ContextUtil.getParticipantDocRepositoryAppContext().getBean("documentService");
//    PatientCorrelationService patientCorrelationParticipantService = (PatientCorrelationService) ContextUtil.getParticipantPatientCorrelationDbAppContext().getBean("correlationService");

    // clean lab data
    public void cleanLabData() {
        try {
            // clean case result by getting distinct case results in summary
            List<Integer> caseResults = auditSummaryService.findUniqueCaseResults();
            // clean audit summary
            auditSummaryService.deleteAll();
            for (Integer caseResult : caseResults) {
                try {
                    caseResultService.deleteById(caseResult);
                } catch (ServiceException ex) {
                    Logger.getLogger(ApplicationCleanupManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ServiceException ex) {
            Logger.getLogger(ApplicationCleanupManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // clean RI data
    public void cleanRIData() {
        // clean RI audit repository
    	if(RI1AuditRepoAppContext!=null){
    		AuditrepoService ri1AuditrepoService = (AuditrepoService) RI1AuditRepoAppContext.getBean("auditrepoService");
        	if(ri1AuditrepoService!=null){
        		ri1AuditrepoService.cleanAuditRepo();
        	}
    	}
        // clean document repository
//        docService.cleanDocRepo();
        // clean patient correlation
//        patientCorrelationService.cleanCorrelationIdentifiers();
    }

    // clean Participant data
    public void cleanParticipantData() {
        // clean RI audit repository
        auditRepoParticipantService.cleanAuditRepo();
        // clean document repository
//        docParticipantService.cleanDocRepo();
        // clean patient correlation
//        patientCorrelationParticipantService.cleanCorrelationIdentifiers();
    }
}
