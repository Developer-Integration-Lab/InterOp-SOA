package labcoretest;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.ScenarioExecutionManager;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.ws.pd.TestCaseExecutionHelperPD;
import net.aegis.ws.qd.TestCaseExecutionHelperQD;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("LabCoreTest Started!!");
		Thread.sleep(120000);
		ApplicationContext applicationContext = ContextUtil.getLabContext();
		System.out.println("applicationContext=== " + applicationContext);
		if (applicationContext != null) {
			String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
			System.out.println("applicationContext.getBeanDefinitionNames=== "
					+ beanDefinitionNames.toString());
			/*for(int index = 0 ; index < applicationContext.getBeanDefinitionCount();index++){
				System.out.println("Each bean Name::::" + beanDefinitionNames[index]);
				if(beanDefinitionNames[index].equalsIgnoreCase("sessionFactory")){
					printSessionFactoryInfo(beanDefinitionNames, applicationContext, index);
				}
			}*/
			
			test1();
			
		}

	}
	
	/**
	 * 
	 * @param beanDefinitionNames
	 * @param applicationContext
	 * @param index
	 */
	private void printSessionFactoryInfo(String[] beanDefinitionNames,ApplicationContext applicationContext,int index){
		
			Object bean = applicationContext.getBean("sessionFactory");
			if(bean!=null && bean instanceof SessionFactory){
				SessionFactory sessionFactory = (SessionFactory) bean;
				if(sessionFactory!=null){
					Map<ClassMetadata, String> allClassMetadata = sessionFactory.getAllClassMetadata();
					System.out.println("sessionFactory.getAllClassMetadata()=="+ allClassMetadata.toString());
					if(allClassMetadata!=null){
						for (index = 0; index < allClassMetadata.size(); index++) {
							Set<Entry<ClassMetadata, String>> set = allClassMetadata.entrySet(); 
							// Get an iterator 
							Iterator<Entry<ClassMetadata, String>> i = set.iterator(); 
							// Display elements 
							while(i.hasNext()) { 
								Map.Entry me = (Map.Entry)i.next(); 
								Object key = me.getKey();
								System.out.print("EachKey===" + key + ": "); 
								System.out.print("EachValue===" + me.getValue() + ": "); 
								if(key!=null && key instanceof ClassMetadata ){
									ClassMetadata classMetadata = (ClassMetadata)key ; 
									System.out.println("classMetadata===" + classMetadata.toString()); 
									System.out.println("classMetadata.getEntityName===" + classMetadata.getEntityName());
								}
							} 
						}
					
					}
					System.out.println("sessionFactory.getAllCollectionMetadata()=="+ sessionFactory.getAllCollectionMetadata().toString());
					
				}
			}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("LabCoreTest Stoped!!");
	}

	private void test1() throws Exception {

		ScenarioExecutionManager scenarioExecutionManager = ScenarioExecutionManager
				.getInstance();
		// Get the scenario configuration information from the testlab database
		Scenarioexecution scenarioExecution = scenarioExecutionManager
				.findByScenarioExecutionId(1845);
		
		System.out.println("scenarioExecution==" + scenarioExecution);
		if(scenarioExecution!=null){
			
			
		int scenarioId = scenarioExecution.getScenario().getScenarioId();
		System.out.println("scenarioExecution|scenarioId==" + scenarioId);
			Participant participant = scenarioExecution.getParticipant();

			for (int i = 0; i < scenarioExecution.getCaseexecutions().size(); i++) {
				Caseexecution caseExecution = scenarioExecution
						.getCaseexecutions().get(i);
				String messageType = caseExecution.getTestcase()
						.getMessageType();
				int caseId = caseExecution.getTestcase().getCaseId();
				System.out.println("Executing testCase usin helper : " + caseId);
				if (messageType.equals(LabConstants.PATIENTDISCOVERY)) {
					TestCaseExecutionHelperPD testCaseExecutionHelperPD = new TestCaseExecutionHelperPD();				
					String result = testCaseExecutionHelperPD.executeTestCasePD(participant, caseExecution, scenarioId, caseId);
					System.out.println("Result of Executing PD testcase : " + result);
				 }else if (messageType.equals(LabConstants.LAB_DOCQUERY)) {
	                    TestCaseExecutionHelperQD testCaseExecutionHelperQD = new TestCaseExecutionHelperQD();
	                    testCaseExecutionHelperQD.executeTestCaseHelperQD(participant, caseExecution, scenarioId, caseId);
				 }
				
			}
		}
	}

}
