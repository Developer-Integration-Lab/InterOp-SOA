package net.aegis.mv.handler.qd;

import java.io.BufferedInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Resultdocument;
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
import net.aegis.mv.mockdata.TestScenario;
import net.aegis.mv.mockdata.TestcaseData;
import net.aegis.mv.parser.INhinMsgParser;
import net.aegis.mv.parser.qd.QDRequestParser;
import net.aegis.mv.parser.qd.QDResponseParser;
import net.aegis.mv.parser.rd.RDRequestParser;
import net.aegis.mv.parser.rd.RDResponseParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class QDIMsgHandlerTest {
	private MockTestcaseDataProvider mockOjectFactory = MockTestcaseDataProvider.getInstance();
	private DBMockUtil dbMockUtil = DBMockUtil.getInstance();
	private GatewayPropertyFactory gatewayFactory = GatewayPropertyFactory.getInstance();
	private Caseresult qdCaseresult;
	private TestcaseData qdTestData;
//	private Caseresult rdCaseresult;

//	@Before
//	public void prepareData() {
//		try {
//			//Get the RD test data.
//			qdTestData = mockOjectFactory.getTestcaseDataByScenario(TestScenario.QD_SUCCESS_SCENARIO).get(0);
//			
//			//Get CaseExecution ID for a Scenario-1 QD case for the candidate.
//			Candidate candidate = gatewayFactory.getCandidate();
//			Integer qdCaseExecutionId = dbMockUtil.getCaseExecutionId(candidate.getName(), ScenarioName.SCENARIO_1, DBMockUtil.MessageType.QD);
//			
//			//Prepare and insert QD case result as a prerequisite.
//			RdNhinDocumentSetRequest rdDocsetRequest = (RdNhinDocumentSetRequest)rdTestData.getExpectedRequestMessage();		
//			List<RdNhinDocumentRequest> rdDocRequests = rdDocsetRequest.getRequestSet();
//			qdCaseresult = createQDCaseresult(qdCaseExecutionId, rdDocRequests);
//			
//			//Insert QD case result data into database. 
//			dbMockUtil.insert(qdCaseresult);
//			
//			//Insert RD request and response transaction objects into database.
//			dbMockUtil.insert(rdTestData.getRequestTransaction());
//			dbMockUtil.insert(rdTestData.getResponseTransaction());
//		}catch(Exception ex) {
//			fail(ex.getMessage());
//		}
//	}
	
//	@After
//	public void cleanup() {
//		//Delete QD case result data
//		if(qdCaseresult != null) {
//			dbMockUtil.delete(qdCaseresult);
//		}
//		
//		//Delete RD Transaction data
//		if(rdTestData != null) {
//			dbMockUtil.delete(rdTestData.getRequestTransaction());
//			dbMockUtil.delete(rdTestData.getResponseTransaction());
//		}
//		
//		//Delete RD case result data created by the testcase execution.
//		if(rdCaseresult != null) {
//			dbMockUtil.delete(rdCaseresult);
//		}
//	}
	
	@Test
	public void testProcessMessage() {
		try {
			for(TestcaseData testcaseData : mockOjectFactory.getTestcaseDataByScenario(TestScenario.QD_SUCCESS_SCENARIO)){
				Transaction transaction = testcaseData.getRequestTransaction();
				//1. Process QD Request transaction.
				if(transaction != null && testcaseData.getTestcaseId().equals("QD2")){
					QDRequestMsgHandler qdRequestHandler = new QDRequestMsgHandler();
					transaction.setId(1);
					qdRequestHandler.processMessage(transaction, QDRequestParser.getInstance(), MsgTypeEnum.QD_REQUEST);
				}
				
				transaction = testcaseData.getResponseTransaction();
				//1. Process QD Request transaction.
				if(transaction != null && testcaseData.getTestcaseId().equals("QD2")){
					QDResponseMsgHandler qdResponseHandler = new QDResponseMsgHandler();
					transaction.setId(2);
					qdResponseHandler.processMessage(transaction, QDResponseParser.getInstance(), MsgTypeEnum.QD_RESPONSE);
				}
			}
			
			assertTrue("Case Result status didn't match ",getCaseResultStatus().equalsIgnoreCase("PASS"));
					
			//2. Validate the QD Request transaction.
			//TODO:

			//6. Process QD Response transaction.
//			QDResponseMsgHandler qdResponseHandler = new QDResponseMsgHandler();
//			qdResponseHandler.processMessage(qdTestData.getRequestTransaction(), RDResponseParser.getInstance(), MsgTypeEnum.QD_RESPONSE);

			//7. Process RD Response transaction.
			//TODO:
		}catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	public String getCaseResultStatus() {
		Integer qdCaseExecutionId = 0;
		String status = null;
		try {
			//Get CaseExecution ID for a Scenario-1 QD case for the candidate.
			Candidate candidate = gatewayFactory.getCandidate();
		//	qdCaseExecutionId = dbMockUtil.getCaseExecutionId(candidate.getName(), ScenarioName.SCENARIO_1, DBMockUtil.MessageType.QD);
			status = dbMockUtil.getCaseResult(1).getResult();
		}catch(Exception ex) {
			fail(ex.getMessage());
			}
		return status;
	}
}
