package net.aegis.lab.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Ram Ghattu
 * Synopsis: This util class is used to get
 * application contexts from spring files in the classpath
 *
 */
public final class ContextUtil {

	private static ApplicationContext riAuditRepoAppContext;
    private static ApplicationContext riDocRepositoryAppContext;
    private static ApplicationContext riPatientCorrelationDbAppContext;
    private static ApplicationContext ri2AuditRepoAppContext;
    private static ApplicationContext ri2DocRepositoryAppContext;
    private static ApplicationContext ri2PatientCorrelationDbAppContext;
    private static ApplicationContext ri3AuditRepoAppContext;
    private static ApplicationContext ri3DocRepositoryAppContext;
    private static ApplicationContext ri3PatientCorrelationDbAppContext;
    private static ApplicationContext ri4AuditRepoAppContext;
    private static ApplicationContext ri4DocRepositoryAppContext;
    private static ApplicationContext ri4PatientCorrelationDbAppContext;
    private static ApplicationContext participantAuditRepoAppContext;
    private static ApplicationContext participantDocRepositoryAppContext;
    private static ApplicationContext participantPatientCorrelationDbAppContext;
    private static ApplicationContext riLabContext;
    private static ApplicationContext gatewayAgentContext; //ILT-300
    
    /*
     * 
     */
    private static final String RI1_AUDIT_REPO_XML_CONFIG = "META-INF/config/springRIAuditRepoXMLConfig.xml";
    private static final String RI2_AUDIT_REPO_XML_CONFIG = "META-INF/config/springRI2AuditRepoXMLConfig.xml";
    private static final String RI3_AUDIT_REPO_XML_CONFIG = "META-INF/config/springRI3AuditRepoXMLConfig.xml";
    private static final String RI4_AUDIT_REPO_XML_CONFIG = "META-INF/config/springRI4AuditRepoXMLConfig.xml";
    
    private static final String  RI1_DOC_REPOSITORY_XML_CONFIG = "META-INF/config/springRIDocRepositoryXMLConfig.xml";
    private static final String  RI2_DOC_REPOSITORY_XML_CONFIG = "META-INF/config/springRI2DocRepositoryXMLConfig.xml";
    private static final String  RI3_DOC_REPOSITORY_XML_CONFIG = "META-INF/config/springRI3DocRepositoryXMLConfig.xml";
    private static final String  RI4_DOC_REPOSITORY_XML_CONFIG = "META-INF/config/springRI4DocRepositoryXMLConfig.xml";
    
    private static final String  RI1_PATIENTCORRELATION_DB_XML_CONFIG = "META-INF/config/springRIPatientCorrelationDbXMLConfig.xml";
    private static final String  RI2_PATIENTCORRELATION_DB_XML_CONFIG = "META-INF/config/springRI2PatientCorrelationDbXMLConfig.xml";
    private static final String  RI3_PATIENTCORRELATION_DB_XML_CONFIG = "META-INF/config/springRI3PatientCorrelationDbXMLConfig.xml";
    private static final String  RI4_PATIENTCORRELATION_DB_XML_CONFIG = "META-INF/config/springRI4PatientCorrelationDbXMLConfig.xml";
    
    // participant 
    private static final String  PARTICIPANT_AUDIT_REPO_XML_CONFIG = "META-INF/config/springParticipantAuditRepoXMLConfig.xml";
    private static final String  PARTICIPANT_DOC_REPOSITORY_XML_CONFIG = "META-INF/config/springParticipantDocRepositoryXMLConfig.xml";
    private static final String  PARTICIPANT_PATIENTCORRELATION_DB_XML_CONFIG = "META-INF/config/springParticipantPatientCorrelationDbXMLConfig.xml";
    
    //LAB
    private static final String  SPRING_LAB_XML_CONFIG = "META-INF/common/springLabXMLConfig.xml";
    //AGENT    
    private static final String SPRING_GATEWAY_AGENT_XML_CONFIG_XML = "META-INF/agent/springGatewayAgentXMLConfig.xml";
    
    private static final Log log = LogFactory.getLog(ContextUtil.class);
   
   
    

    /*
     * Test Harness RI1 Application Context Definitions
     */
    public static ApplicationContext getRIAuditRepoAppContext() {
        try {
			if (riAuditRepoAppContext == null) {
			    riAuditRepoAppContext = new ClassPathXmlApplicationContext(RI1_AUDIT_REPO_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//riAuditRepoAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return riAuditRepoAppContext;
    }

    public static ApplicationContext getRIDocRepositoryAppContext() {
        try {
			if (riDocRepositoryAppContext == null) {
			    riDocRepositoryAppContext = new ClassPathXmlApplicationContext(RI1_DOC_REPOSITORY_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//riDocRepositoryAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return riDocRepositoryAppContext;
    }

    public static ApplicationContext getRIPatientCorrelationDbAppContext() {
        try {
			if (riPatientCorrelationDbAppContext == null) {
			    riPatientCorrelationDbAppContext = new ClassPathXmlApplicationContext(RI1_PATIENTCORRELATION_DB_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//riPatientCorrelationDbAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return riPatientCorrelationDbAppContext;
    }

    /*
     * Test Harness RI2 Application Context Definitions
     */
    public static ApplicationContext getRI2AuditRepoAppContext() {
        try {
			if (ri2AuditRepoAppContext == null) {
			    ri2AuditRepoAppContext = new ClassPathXmlApplicationContext(RI2_AUDIT_REPO_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri2AuditRepoAppContext = ApplicationConfigurationContext.getApplicationContext();
			
		}
        return ri2AuditRepoAppContext;
    }

    public static ApplicationContext getRI2DocRepositoryAppContext() {
        try {
			if (ri2DocRepositoryAppContext == null) {
			    ri2DocRepositoryAppContext = new ClassPathXmlApplicationContext(RI2_DOC_REPOSITORY_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri2DocRepositoryAppContext = ApplicationConfigurationContext.getApplicationContext();
			
		}
        return ri2DocRepositoryAppContext;
    }

    public static ApplicationContext getRI2PatientCorrelationDbAppContext() {
        try {
			if (ri2PatientCorrelationDbAppContext == null) {
			    ri2PatientCorrelationDbAppContext = new ClassPathXmlApplicationContext(RI2_PATIENTCORRELATION_DB_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri2PatientCorrelationDbAppContext = ApplicationConfigurationContext.getApplicationContext();
			
		}
        return ri2PatientCorrelationDbAppContext;
    }

    /*
     * Test Harness RI3 Application Context Definitions
     */
    public static ApplicationContext getRI3AuditRepoAppContext() {
        try {
			if (ri3AuditRepoAppContext == null) {
			    ri3AuditRepoAppContext = new ClassPathXmlApplicationContext(RI3_AUDIT_REPO_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri3AuditRepoAppContext = ApplicationConfigurationContext.getApplicationContext();
			
		}
        return ri3AuditRepoAppContext;
    }

    public static ApplicationContext getRI3DocRepositoryAppContext() {
        try {
			if (ri3DocRepositoryAppContext == null) {
			    ri3DocRepositoryAppContext = new ClassPathXmlApplicationContext(RI3_DOC_REPOSITORY_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri3DocRepositoryAppContext = ApplicationConfigurationContext.getApplicationContext();
			
		}
        return ri3DocRepositoryAppContext;
    }

    public static ApplicationContext getRI3PatientCorrelationDbAppContext() {
        try {
			if (ri3PatientCorrelationDbAppContext == null) {
			    ri3PatientCorrelationDbAppContext = new ClassPathXmlApplicationContext(RI3_PATIENTCORRELATION_DB_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri3PatientCorrelationDbAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return ri3PatientCorrelationDbAppContext;
    }

    /*
     * Test Harness RI4 Application Context Definitions
     */
    public static ApplicationContext getRI4AuditRepoAppContext() {
        try {
			if (ri4AuditRepoAppContext == null) {
			    ri4AuditRepoAppContext = new ClassPathXmlApplicationContext(RI4_AUDIT_REPO_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri4AuditRepoAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return ri4AuditRepoAppContext;
    }

    public static ApplicationContext getRI4DocRepositoryAppContext() {
        try {
			if (ri4DocRepositoryAppContext == null) {
			    ri4DocRepositoryAppContext = new ClassPathXmlApplicationContext(RI4_DOC_REPOSITORY_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri4DocRepositoryAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return ri4DocRepositoryAppContext;
    }

    public static ApplicationContext getRI4PatientCorrelationDbAppContext() {
        try {
			if (ri4PatientCorrelationDbAppContext == null) {
			    ri4PatientCorrelationDbAppContext = new ClassPathXmlApplicationContext(RI4_PATIENTCORRELATION_DB_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//ri4PatientCorrelationDbAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return ri4PatientCorrelationDbAppContext;
    }

    /*
     * Test Participant RI Application Context Definitions
     */
    public static ApplicationContext getParticipantAuditRepoAppContext() {
        try {
			if (participantAuditRepoAppContext == null) {
			    participantAuditRepoAppContext = new ClassPathXmlApplicationContext(PARTICIPANT_AUDIT_REPO_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//participantAuditRepoAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return participantAuditRepoAppContext;
    }

    public static ApplicationContext getParticipantDocRepositoryAppContext() {
        try {
			if (participantDocRepositoryAppContext == null) {
			    participantDocRepositoryAppContext = new ClassPathXmlApplicationContext(PARTICIPANT_DOC_REPOSITORY_XML_CONFIG);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//participantDocRepositoryAppContext = ApplicationConfigurationContext.getApplicationContext();
		}
        return participantDocRepositoryAppContext;
    }

    public static ApplicationContext getParticipantPatientCorrelationDbAppContext() {
        try {
			if (participantPatientCorrelationDbAppContext == null) {
			    participantPatientCorrelationDbAppContext = new ClassPathXmlApplicationContext(PARTICIPANT_PATIENTCORRELATION_DB_XML_CONFIG);
			}
		} catch (Exception e) {
			//participantPatientCorrelationDbAppContext = ApplicationConfigurationContext.getApplicationContext();
			
		}
        return participantPatientCorrelationDbAppContext;
    }

    /*
     * Lab Application Context Definitions
     */
    public static ApplicationContext getLabContext() {
        log.debug("riLabContext== " + riLabContext);
        
    	try {
			if (riLabContext == null) {
				log.info("Using NONOSGI ApplicationConfigurationContext:::");
			    riLabContext = new ClassPathXmlApplicationContext(SPRING_LAB_XML_CONFIG);
			}
		} catch (Exception e) {
			//log.error("Using OSGI ApplicationConfigurationContext:::"+e.getMessage());
			log.info("Using OSGI ApplicationConfigurationContext:::");
			riLabContext = ApplicationContextProvider.getApplicationContext();
		}
		if(riLabContext!=null){
			try {
				String[] beanDefinitionNames = riLabContext.getBeanDefinitionNames();
				int beanDefinitionCount = riLabContext.getBeanDefinitionCount();
				log.debug("riLabContext>>>getBeanDefinitionNames" + beanDefinitionNames);
				log.debug("riLabContext>>>beanDefinitionCount===" + beanDefinitionCount);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("In getLabContext():::"+e.getMessage());
			}
			/*if(beanDefinitionNames!=null ){
				for(int index = 0 ; index < beanDefinitionCount;index++){
					System.out.println("Each bean Name::::" + beanDefinitionNames[index]);
				}
			}*/
		}
        return riLabContext;
    }
}
