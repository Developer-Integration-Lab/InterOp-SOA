package net.aegis.mv.mockdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.mv.mockdata.DBMockUtil.MessageType;
import net.aegis.mv.mockdata.DBMockUtil.ScenarioName;

public class DBMockUtilValidator {
	
	DBMockUtil dbUtil = DBMockUtil.getInstance();
	
	private Caseresult createCaseresult() {
		Caseresult caseresult = new Caseresult();
		caseresult.setResultId(10000);
		caseresult.setResult("Success");
		Caseexecution caseexecution = new Caseexecution();
		caseexecution.setCaseExecutionId(4010);
		caseresult.setCaseexecution(caseexecution);
		
		List<Resultdocument> resultDocs = new ArrayList<Resultdocument>();
		for(int i=0; i<3; i++) {
			Resultdocument resultDoc = new Resultdocument();
			resultDoc.setCaseresult(caseresult);
			resultDoc.setClassCode("3433-9");
			resultDoc.setCommunityId("2.16.840.1.113883.0.101");
			resultDoc.setDocumentHash("82e55c4b5e1c2a6d724b0310df0a0c6d583615b7-" + i);
			resultDoc.setDocumentSize(12679);
			resultDoc.setDocumentUniqueId("1.RI1.101.00037.11111-" + i);
			resultDoc.setRepositoryId("1");
			resultDoc.setResultDocumentId(10000 + i);
			resultDocs.add(resultDoc);			
		}
		caseresult.setResultdocuments(resultDocs);
		return caseresult;
	}
		
	private Properties getCandidateProperties() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("testdata/candidate.properties"));
		}catch(Exception ex) {
			ex.printStackTrace();			
		}
		return properties;
	}
	
	private void printScenarioDetails() {
		Properties candidateProperties = getCandidateProperties();
		System.out.println("Candidate properties: " + candidateProperties);
		
		String participantName = candidateProperties.getProperty("candidate.name");
		
		//Get all the scenario executions for the participant.
		Map<Integer, String> scenarioExecutions = dbUtil.getScenarioExecutions(participantName);
		Set<Integer> scenarioExecIds = scenarioExecutions.keySet();
		
		System.out.println("Scenario Execution details for the participant: " + participantName);		
		for(Integer scenarioExecId : scenarioExecIds) {
			String scenarioName = scenarioExecutions.get(scenarioExecId);
			
			//=========START: This is just to verify the following method works.		
			Integer scenarioExecIdByName = dbUtil.getScenarioExecutionId(participantName, ScenarioName.SCENARIO_1);
			if(scenarioExecIdByName == null || !scenarioExecId.equals(scenarioExecIdByName)) {
				System.out.println("ERROR: Method DBMockUtil.getScenarioExecutionId(participantName, scenarioName) " +
						" returned a different scenario execution Id than expected: " +
						" ScnarioName: " + scenarioName + 
						" Expected ID: " + scenarioExecId +
						" Returned ID: " + scenarioExecIdByName);
				continue;
			}
			//=========END
			System.out.println("\nScenario Name: " + scenarioName + "; Scenario Execution ID: " + scenarioExecId);
			for(MessageType messageType : MessageType.values()) {
				Integer testcaseId = dbUtil.getCaseExecutionId(scenarioExecId, messageType);
				System.out.println("Testcase ID for " + messageType + ": " + testcaseId);
			}
		}
	}
	
	private List<Transaction> createTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		for(int i=0; i<3; i++) {
			Transaction transaction = new Transaction();
			transaction.setId(70000 + i);
			transaction.setClient("dummy-client-" + i);
			transaction.setServer("dummy-server-" + i);
			transaction.setStatusCode(200);
			transaction.setContentLength(123);
			transaction.setDuration(10);
			transactions.add(transaction);
		}
		return transactions;
	}
	
	public static void main(String[] args) {
		
		DBMockUtilValidator validator = new DBMockUtilValidator(); 
		DBMockUtil dbUtil = validator.dbUtil;
		
		//Resultsummary resultSummary = dbUtil.getResultSummaryByMessageId("uuid:eb3e5a9a-8d0c-4e7b-8f75-95449179e911");
		
		//Resultsummary resultSummary = dbUtil.getResultSummaryByMaxId();
		dbUtil.getCaseResult(82);
		
		//Print Case result details.
		//Caseresult caseresult = validator.createCaseresult();
		//dbUtil.insert(caseresult);
		//dbUtil.delete(caseresult);
		
		//Print Scenario properties.
		//validator.printScenarioDetails();
		
		//Test Transaction details.
		//List<Transaction> transactions = validator.createTransactions();
		//dbUtil.insert(transactions);
		//dbUtil.delete(transaction);
			
		
	}
		
}
