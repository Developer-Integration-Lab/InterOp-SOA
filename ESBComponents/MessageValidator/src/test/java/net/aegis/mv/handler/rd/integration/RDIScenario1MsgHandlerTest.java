package net.aegis.mv.handler.rd.integration;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.rd.RDRequestMsgHandler;
import net.aegis.mv.handler.rd.RDResponseMsgHandler;
import net.aegis.mv.jaxb.rd.RdNhinDocumentRequest;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetRequest;
import net.aegis.mv.mockdata.DBMockUtil;
import net.aegis.mv.mockdata.DBMockUtil.ScenarioName;
import net.aegis.mv.mockdata.ExpectedDocument;
import net.aegis.mv.mockdata.GatewayPropertyFactory;
import net.aegis.mv.mockdata.GatewayPropertyFactory.Candidate;
import net.aegis.mv.mockdata.IdGenerator;
import net.aegis.mv.mockdata.MockTestcaseDataProvider;
import net.aegis.mv.mockdata.TestcaseData;
import net.aegis.mv.parser.rd.RDRequestParser;
import net.aegis.mv.parser.rd.RDResponseParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RDIScenario1MsgHandlerTest {
	private MockTestcaseDataProvider mockOjectFactory = MockTestcaseDataProvider.getInstance();
	private DBMockUtil dbMockUtil = DBMockUtil.getInstance();
	private GatewayPropertyFactory gatewayFactory = GatewayPropertyFactory.getInstance();
	private Caseresult qdCaseresult;
	private List<TestcaseData> rdTestDataList = new ArrayList<TestcaseData>();
	private Integer rdCaseExecutionId;

	@Before
	public void prepareData() {
		try {
			//Get the RD test data.
			for(int i=1; i<=3; i++) {
				TestcaseData rdTestData = mockOjectFactory.getTestcaseDataByTestId("RD_SCENARIO_1:" + i);
				rdTestDataList.add(rdTestData);
			}
			Candidate candidate = gatewayFactory.getCandidate();
			rdCaseExecutionId = dbMockUtil.getCaseExecutionId(candidate.getName(), ScenarioName.SCENARIO_1, DBMockUtil.MessageType.RD);
						
			//Insert QD testData
			insertQDCaseResult(rdTestDataList);
			
			//Insert RD transactions.
			insertRDTransactions(rdTestDataList);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}
	
	/**
	 * This is the pre-requite for the RD testcase execution. This method will insert 
	 * data into Case result and the expected result documents reflecting a successful QD. 
	 * 
	 * @param rdTestDataList
	 */
	private void insertQDCaseResult(List<TestcaseData> rdTestDataList) {
		//Get CaseExecution ID for a Scenario-1 QD case for the candidate.
		Candidate candidate = gatewayFactory.getCandidate();
		Integer qdCaseExecutionId = dbMockUtil.getCaseExecutionId(candidate.getName(), ScenarioName.SCENARIO_1, DBMockUtil.MessageType.QD);
		
		//Prepare and insert QD case result as a prerequisite.
		List<RdNhinDocumentRequest> rdDocRequests = new ArrayList<RdNhinDocumentRequest>();
		for(TestcaseData rdTestData : rdTestDataList) {
			RdNhinDocumentSetRequest rdDocsetRequest = (RdNhinDocumentSetRequest)rdTestData.getExpectedRequestMessage();		
			rdDocRequests.addAll(rdDocsetRequest.getRequestSet());
		}
		qdCaseresult = createQDCaseresult(qdCaseExecutionId, rdDocRequests);
		
		//Insert QD case result data into database. 
		dbMockUtil.insert(qdCaseresult);	
	}
	
	private void insertRDTransactions(List<TestcaseData> rdTestDataList) {
		List<Transaction> rdTransactions = new ArrayList<Transaction>();
		for(TestcaseData rdTestData : rdTestDataList) {
			//Insert RD request and response transaction objects into database.
			Transaction requestTransaction = rdTestData.getRequestTransaction();
			requestTransaction.setTime(new Timestamp(System.currentTimeMillis()));			
			rdTransactions.add(requestTransaction);
			
			Transaction responseTransaction = rdTestData.getResponseTransaction();
			responseTransaction.setTime(new Timestamp(System.currentTimeMillis()));
			rdTransactions.add(responseTransaction);			
		}
		dbMockUtil.insert(rdTransactions);
	}
	
	@After
	public void cleanup() {
		//Delete QD case result data
		if(qdCaseresult != null) {
			dbMockUtil.delete(qdCaseresult);
		}
		
		//Delete RD Transaction data
		if(rdTestDataList != null) {
			List<Transaction> transactions = new ArrayList<Transaction>();
			for(TestcaseData testData : rdTestDataList) {
				transactions.add(testData.getRequestTransaction());
				transactions.add(testData.getResponseTransaction());
			}
			dbMockUtil.delete(transactions);			
		}
		
		//====================================================================================================================
		//Delete the data from the tables populated by RD testcase execution.
		//1. ResultSummary
		//2. CaseResult
		//3. ResultDocument
		//
		//1. Get all ResultSummary records using messageIds of the expected NhinMessage from the TestcaseData.
		//2. Get ResultId from any of of the ResultSummary records (All result summary of a testcase will have same result id).
		//3. Get the CaseResult using the ResultId.
		//5. Delete ResultDocument records, ResultSummary records and the CaseResult respectively. 
		
		//1. Get message Ids
		List<String> messageIds = getRequestMessageIds(rdTestDataList);
		
		//2. Get result summary using a message id.
		//Question: Is it possible that a record is inserted without a message id because of a defect in the code?
		Resultsummary rdResultSummary = dbMockUtil.getResultSummaryByMessageId(messageIds.get(0));
		
		//3. Delete the result documents, result summary and case result.
		dbMockUtil.delete(rdResultSummary.getCaseresult());
		//====================================================================================================================
	}
	
	private List<String> getRequestMessageIds(List<TestcaseData> testDataList) {
		List<String> messageIds = new ArrayList<String>();
		for(TestcaseData testData : testDataList) {
			messageIds.add(testData.getExpectedRequestMessage().getMessageID());
		}
		return messageIds;
	}
	
	@Test
	public void testProcessMessage() {
		try {			
			RDRequestMsgHandler rdRequestHandler = new RDRequestMsgHandler();
			for(TestcaseData rdTestData : rdTestDataList) {
				//1. Process RD Request transaction.
				rdRequestHandler.processMessage(rdTestData.getRequestTransaction(), RDRequestParser.getInstance(), MsgTypeEnum.RD_REQUEST);
				
				//2. Process RD Response transaction.
				RDResponseMsgHandler rdResponseHandler = new RDResponseMsgHandler();
				rdResponseHandler.processMessage(rdTestData.getResponseTransaction(), RDResponseParser.getInstance(), MsgTypeEnum.RD_RESPONSE);
			}
			
			//Validate RD results from the database.
			validate();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}
	
	private void validate() {		
		
		Integer caseresultId = null;
		List<RdNhinDocumentRequest> expectedDocuments = new ArrayList<RdNhinDocumentRequest>();
		for(TestcaseData testData : rdTestDataList) {
			//1. Validate Result Summary records.
			String messageId = testData.getExpectedRequestMessage().getMessageID();
			Resultsummary rdResultSummary = dbMockUtil.getResultSummaryByMessageId(messageId);
			assertNotNull("ResultSummary is missing in database for the message ID: " + messageId, rdResultSummary);
			assertEquals("'messageId' and 'relatesTo' are not matching", rdResultSummary.getMessageId(), rdResultSummary.getRelatesTo());
			  
			Transaction requestTransaction = testData.getRequestTransaction();
			assertEquals("Request Sender's HCID is not matching", requestTransaction.getSender().getHCID(), rdResultSummary.getReqSenderHcid());
			assertEquals("Request Receiver's HCID is not matching", requestTransaction.getReceiver().getHCID(), rdResultSummary.getReqReceiverHcid());
			assertEquals("Request Transaction ID is not matching", requestTransaction.getId(), rdResultSummary.getReqTransactionid());
			
			Transaction responseTransaction = testData.getResponseTransaction();
			assertEquals("Response Sender's HCID is not matching", responseTransaction.getSender().getHCID(), rdResultSummary.getResSenderHcid());
			assertEquals("Response Receiver's HCID is not matching", responseTransaction.getReceiver().getHCID(), rdResultSummary.getResReceiverHcid());
			assertEquals("Response Transaction ID is not matching", responseTransaction.getId(), rdResultSummary.getResTransactionid());
			
			//Get caseresult id.
			caseresultId = rdResultSummary.getCaseresult().getResultId();
			
			RdNhinDocumentSetRequest docsetRequest = (RdNhinDocumentSetRequest)testData.getExpectedRequestMessage();
			List<RdNhinDocumentRequest> docRequests = docsetRequest.getRequestSet();
			if(docRequests != null) {
				expectedDocuments.addAll(docRequests);
			}
		}
		
		//2. Validate Caseresult
		Caseresult caseresult = dbMockUtil.getCaseResult(caseresultId);
		assertEquals("Caseresult attached to wrong case execution", rdCaseExecutionId, caseresult.getCaseexecution().getCaseExecutionId());
		assertEquals("Case result should represent a success scenario", "PASS", caseresult.getResult());
		List<Resultdocument> resultDocuments = caseresult.getResultdocuments();
		
		//3. Validate Documents.
		for(RdNhinDocumentRequest expectedDocument : expectedDocuments) {
			String expectedDocumentId = expectedDocument.getDocumentUniqueId();
			Resultdocument resultDocumentFound = null;
			for(Resultdocument resultDocument : resultDocuments) {
				if(expectedDocumentId.equals(resultDocument.getDocumentUniqueId())) {
					resultDocumentFound = resultDocument;
					break;
				}
			}
			
			assertNotNull("No document found for Document unique ID: " + expectedDocumentId, resultDocumentFound);
			assertEquals("Document ID not matching", expectedDocument.getDocumentUniqueId(), resultDocumentFound.getDocumentUniqueId());
			assertEquals("Repository ID not matching", expectedDocument.getRepositoryId(), resultDocumentFound.getRepositoryId());
			//assertEquals("Community ID not matching", expectedDocument.getHomeCommunityId(), resultDocumentFound.getCommunityId());
			
			//TODO: Validate Document Size,  DocumentHash, CommunityID???
		}
	}
		
	private Caseresult createQDCaseresult(int caseExecutionId, List<RdNhinDocumentRequest> rdDocRequests) {
		Caseresult caseresult = new Caseresult();
		caseresult.setResultId(IdGenerator.getNextId());
		caseresult.setResult(LabConstants.STATUS_PASS);
		caseresult.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		caseresult.setCreatedBy("RD_Scenario-1_Junit_testcase.");
		caseresult.setMessage("Inserted by RD Scenario_1 Junit testcase");		
		caseresult.setExecuteTime(new Timestamp(System.currentTimeMillis()));
		
		Caseexecution caseexecution = new Caseexecution();
		caseexecution.setCaseExecutionId(caseExecutionId);
		caseresult.setCaseexecution(caseexecution);
		
		List<Resultdocument> resultDocs = new ArrayList<Resultdocument>();
		for(RdNhinDocumentRequest rdDocRequest : rdDocRequests) {			
			Resultdocument resultDoc = new Resultdocument();
			resultDoc.setResultDocumentId(IdGenerator.getNextId());
			resultDoc.setCaseresult(caseresult);			
			resultDoc.setCommunityId(rdDocRequest.getHomeCommunityId());
			resultDoc.setDocumentUniqueId(rdDocRequest.getDocumentUniqueId());
			resultDoc.setRepositoryId(rdDocRequest.getRepositoryId());
			resultDoc.setMessageType("QD");
			resultDoc.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			resultDoc.setCreatedBy("RD_Scenario-1_Junit_testcase.");
			resultDoc.setMessage("Inserted by RD Scenario_1 Junit testcase");
			
			ExpectedDocument expectedDoc = mockOjectFactory.getDocumentByDocumentUniqueId(rdDocRequest.getDocumentUniqueId());
			resultDoc.setClassCode(expectedDoc.getClassCode());
			resultDoc.setDocumentHash(expectedDoc.getHash());
			resultDoc.setDocumentSize(Integer.parseInt(expectedDoc.getSize()));
			resultDocs.add(resultDoc);
		}
		caseresult.setResultdocuments(resultDocs);
		return caseresult;
	}	
}
