package net.aegis.mv.mockdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.dao.pojo.Resultsummary;

public class DBMockUtil {
	private static String url = "jdbc:mysql://localhost:3306/lab";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "nhincuser";
	private static String password = "nhincpass";
	public static enum MessageType {
		PD, QD, RD;
	}
	public static enum ScenarioName {
		SCENARIO_1("Scenario 1"), 
		SCENARIO_2("Scenario 2"),
		SCENARIO_3("Scenario 3"),
		SCENARIO_4("Scenario 4"),
		SCENARIO_5("Scenario 5"),
		SCENARIO_6("Scenario 6"),
		SCENARIO_29("Scenario 29"),
		SCENARIO_30("Scenario 30");
				
		private String textName;
		
		private ScenarioName(String name) {
			this.textName = name;
		}
		
		public String toString() {
			return textName;
		}		
	}
	
	private static final String QRY_SCENARIO_EXECUTION = "select scenarioexecution.scenarioExecutionId " +
						"from scenarioexecution, servicesetexecution, participant " +
						" where servicesetexecution.participantId = participant.participantId" +
						" and scenarioExecution.executionUniqueId like concat(servicesetexecution.executionUniqueId, '%') " +
						"and participant.status='A' " +
						"and servicesetexecution.status='ACTIVE' " +
						"and participant.participantName=? " +
						"and scenarioexecution.scenarioId in (" +
						"select scenarioId from scenario where scenarioName=?)";
	
	private static final String QRY_ALL_SCENARIO_EXECUTIONS = "select scenarioexecution.scenarioExecutionId, scenario.scenarioName " +
					"from scenarioexecution, servicesetexecution, participant, scenario " +
					" where servicesetexecution.participantId = participant.participantId" +
					" and scenarioExecution.executionUniqueId like concat(servicesetexecution.executionUniqueId, '%') " +
					"and participant.status='A' " +
					"and servicesetexecution.status='ACTIVE' " +
					"and participant.participantName=? " +
					"and scenarioexecution.scenarioId = scenario.scenarioId";
	
	private static final String QRY_TESTCASE = "select scenariocase.caseId from scenariocase, scenarioexecution, testcase " +
    				"where testcase.messageType=? " + 
    				"and scenariocase.caseId=testcase.caseId " +  
    				"and scenariocase.scenarioId=scenarioexecution.scenarioId " +
    				"and scenarioexecution.scenarioexecutionId=?";
	
	private static final String QRY_CASE_EXECUTION = "select caseexecution.* from caseexecution, scenariocase, scenarioexecution, testcase " +
					" where testcase.messageType=? " +
					" and scenariocase.caseId = testcase.caseId " +
					" and caseexecution.caseId = testcase.caseId" +
					" and caseexecution.caseId = scenariocase.caseId" +
					" and caseexecution.scenarioExecutionId = scenarioexecution.scenarioexecutionId" +
					" and scenariocase.scenarioId=scenarioexecution.scenarioId and scenarioexecution.scenarioexecutionId=?" ;
	
	private Connection getConnection() {		
		try {
			Class.forName(driver).newInstance();
			return DriverManager.getConnection(url, userName, password);
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns the scenario execution Id for a given scenario name for a given participant.
	 * 
	 * @param participantName
	 * @param scenarioName
	 * @return
	 */
	public Integer getScenarioExecutionId(String participantName, ScenarioName scenarioName) {
		Connection conn = getConnection();
		PreparedStatement statement = null;
		Integer execId = null;
		try {
			statement = conn.prepareStatement(QRY_SCENARIO_EXECUTION);
			statement.setString(1, participantName);
			statement.setString(2, scenarioName.toString());
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				execId = rs.getInt(1);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}
		}
		return execId;
	}
	
	public Integer getCaseExecutionId(String candidate, ScenarioName scenarioName, MessageType messageType) {
		Integer scenarioExecutionId = getScenarioExecutionId(candidate, scenarioName);
		return scenarioExecutionId == null ? null : getCaseExecutionId(scenarioExecutionId, messageType);
	}
	
	public Integer getCaseExecutionId(Integer scenarioExecutionId, MessageType messageType) {
		Connection conn = getConnection();
		PreparedStatement statement = null;
		Integer testcaseId = null;
		try {
			statement = conn.prepareStatement(QRY_CASE_EXECUTION);
			statement.setString(1, messageType.name());
			statement.setInt(2, scenarioExecutionId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				testcaseId = rs.getInt(1);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}
		}
		return testcaseId;
	}
	
	public Map<Integer, String> getScenarioExecutions(String participantName) {
		Connection conn = getConnection();
		PreparedStatement statement = null;
		Map<Integer, String> scenarios = null;
		try {
			statement = conn.prepareStatement(QRY_ALL_SCENARIO_EXECUTIONS);
			statement.setString(1, participantName);
			ResultSet rs = statement.executeQuery();
			scenarios = new HashMap<Integer, String>();
			while(rs.next()) {
				scenarios.put(rs.getInt(1), rs.getString(2));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}			
		}
		return scenarios;
	}
	
	/**
	 * Returns the Caseresult object populated with Caseresult details, Result summaries and Result Documents. 
	 * @param caseresultid
	 * @return
	 */
	public Caseresult getCaseResult(int caseresultid) {
		Connection conn = getConnection();
		PreparedStatement statement = null;
		Caseresult caseresult = null;
		try {
			statement = conn.prepareStatement("select caseExecutionId, executeTime, labInd, result, resultInfo," +
					"documentIds, submissionInd, message, errorCode, stacktrace, processedRIsCount, createdAt," +
					"createdBy, updatedAt, updatedBy from caseresult where resultid = ?");
			statement.setInt(1, caseresultid);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				caseresult = new Caseresult();
				caseresult.setResultId(caseresultid);
				Caseexecution caseexecution = new Caseexecution();
				caseexecution.setCaseExecutionId(rs.getInt("caseExecutionId"));
				caseresult.setCaseexecution(caseexecution);
				
				caseresult.setExecuteTime(rs.getTimestamp("executeTime"));
				caseresult.setLabInd(rs.getString("labInd"));
				caseresult.setResult(rs.getString("result"));
				caseresult.setResultInfo(rs.getString("resultInfo"));
				caseresult.setDocumentIds(rs.getString("documentIds"));
				caseresult.setSubmissionInd(rs.getString("submissionInd"));
				caseresult.setMessage(rs.getString("message"));
				caseresult.setErrorCode(rs.getString("errorCode"));
				caseresult.setStacktrace(rs.getString("stacktrace"));
				caseresult.setProcessedRIsCount(rs.getInt("processedRIsCount"));
				caseresult.setCreatedAt(rs.getTimestamp("createdAt"));
				caseresult.setCreatedBy(rs.getString("createdBy"));
				caseresult.setUpdatedAt(rs.getTimestamp("updatedAt"));
				caseresult.setUpdatedBy(rs.getString("updatedBy"));
				
				//Add Result summaries
				caseresult.setResultsummarys(getResultSummary(caseresult, conn));
				
				//Add Result Documents.
				caseresult.setResultdocuments(getResultDocumets(caseresult, conn));
				
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}			
		}
		return caseresult;
	}
	
	public boolean insert(Caseresult caseresult) {
		Connection conn = getConnection();
		boolean autoCommit = false; 
		try {
			autoCommit = conn.getAutoCommit();
			
			//Start the transaction.
			conn.setAutoCommit(false);
			
			//Insert data into Caseresult table.
			String strStatment = "insert into caseresult (resultid, caseExecutionId, executeTime, result, message, createdAt, createdBy) " +
									"values (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = conn.prepareStatement(strStatment);
			statement.setInt(1, caseresult.getResultId());
			statement.setInt(2, caseresult.getCaseexecution().getCaseExecutionId());
			statement.setTimestamp(3,caseresult.getExecuteTime());
			statement.setString(4, caseresult.getResult());
			statement.setString(5, caseresult.getMessage());
			statement.setTimestamp(6, caseresult.getCreatedAt());
			statement.setString(7, caseresult.getCreatedBy());
			statement.executeUpdate();
			statement.close();
			
			//Insert ResultDocuments			
			List<Resultdocument> resultDocs = caseresult.getResultdocuments();
			if(resultDocs != null) {
				String strResultDocStmt = "insert into resultdocument (resultDocumentId, resultid, documentUniqueId, " +
											"classCode, repositoryId, communityId, documentHash, documentSize) " +
											"values(?, ?, ?, ?, ?, ?, ?, ?)";
				statement = conn.prepareStatement(strResultDocStmt);
				for(Resultdocument resultDoc : resultDocs) {
					statement.setInt(1, resultDoc.getResultDocumentId());
					statement.setInt(2, resultDoc.getCaseresult().getResultId());
					statement.setString(3, resultDoc.getDocumentUniqueId());
					statement.setString(4, resultDoc.getClassCode());
					statement.setString(5, resultDoc.getRepositoryId());
					statement.setString(6, resultDoc.getCommunityId());
					statement.setString(7, resultDoc.getDocumentHash());
					statement.setInt(8, resultDoc.getDocumentSize());
					statement.executeUpdate();
				}
				statement.close();
			}			
			conn.commit();
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			try { conn.rollback();
			}catch(SQLException sqlEx) {
				ex.printStackTrace();
			}
			return false;
		}
		finally {			
			try { conn.setAutoCommit(autoCommit); }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}			
		}
	}
	
	/**
	 * 
	 * @param caseresult
	 * @return
	 */
	public boolean delete(Caseresult caseresult) {
		//Delete records from result summary, case result, result document and patient correlation.
		Connection conn = getConnection();
		boolean autoCommit = false; 
		try {
			autoCommit = conn.getAutoCommit();
			
			//Start the transaction.
			conn.setAutoCommit(false);
			
			//Delete ResultDocuments
			String strResultDocStmt = "delete from resultdocument where resultid = ?";
			PreparedStatement statement = conn.prepareStatement(strResultDocStmt);
			statement.setInt(1, caseresult.getResultId());
			statement.executeUpdate();
			statement.close();
			
			//Delete Result summary
			String strResultSummaryStmt = "delete from resultsummary where resultid = ?";
			statement = conn.prepareStatement(strResultSummaryStmt);
			statement.setInt(1, caseresult.getResultId());
			statement.executeUpdate();
			statement.close();
			
			//Delete ResultDocs
			String strStatment = "delete from caseresult where resultid = ?";
			statement = conn.prepareStatement(strStatment);
			statement.setInt(1, caseresult.getResultId());
			statement.executeUpdate();
			statement.close();
				
			conn.commit();
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			try { conn.rollback();
			}catch(SQLException sqlEx) {
				ex.printStackTrace();
			}
			return false;
		}
		finally {			
			try { conn.setAutoCommit(autoCommit); }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}			
		}
	}

	public boolean insert(List<Transaction> transactions) {
		Connection conn = getConnection();
		boolean autoCommit = false; 
		try {
			autoCommit = conn.getAutoCommit();
			
			//Start the transaction.
			conn.setAutoCommit(false);
			
			//Insert data into Caseresult table.

			String strStatment = "insert into transaction (id, statuscode, msgtype, time, rule, method, path, client, " +
								"server, sender, contenttype, contentlength, duration, msgfilepath, msgheader, msg, receiver) " +
								"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement statement = conn.prepareStatement(strStatment);
			for(Transaction transaction : transactions) {
				statement.setInt(1, transaction.getId());
				statement.setInt(2, transaction.getStatusCode());
				statement.setString(3, transaction.getMessageType());
				statement.setTimestamp(4, transaction.getTime());
				statement.setString(5, transaction.getRule());
				statement.setString(6, transaction.getMethod());
				statement.setString(7, transaction.getPath());
				statement.setString(8, transaction.getClient());
				statement.setString(9, transaction.getServer());
				statement.setString(10, transaction.getSender() != null? transaction.getSender().getHCID() : null);
				statement.setString(11, transaction.getContentType());
				statement.setInt(12, transaction.getContentLength());
				statement.setInt(13, transaction.getDuration());
				statement.setString(14, transaction.getMessageFilePath());
				statement.setBytes(15, transaction.getMessageHeader());
				statement.setBytes(16, transaction.getMessage());
				statement.setString(17, transaction.getReceiver() != null ? transaction.getReceiver().getHCID() : null);
				statement.executeUpdate();
			}			
			statement.close();
			
			conn.commit();
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			try { conn.rollback();
			}catch(SQLException sqlEx) {
				ex.printStackTrace();
			}
			return false;
		}
		finally {			
			try { conn.setAutoCommit(autoCommit); }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}			
		}
	}
	
	public boolean delete(List<Transaction> transactions) {
		Connection conn = getConnection();
		boolean autoCommit = false; 
		try {
			autoCommit = conn.getAutoCommit();
			
			//Start the transaction.
			conn.setAutoCommit(false);
			
			//Delete Transaction
			String strStmt = "delete from transaction where id = ?";
			PreparedStatement statement = conn.prepareStatement(strStmt);
			for(Transaction transaction : transactions) {
				statement.setInt(1, transaction.getId());
				statement.executeUpdate();
			}
			statement.close();
				
			conn.commit();
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			try { conn.rollback();
			}catch(SQLException sqlEx) {
				ex.printStackTrace();
			}
			return false;
		}
		finally {			
			try { conn.setAutoCommit(autoCommit); }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}			
		}
	}
	
	/**
	 * Returns the ResultSummary using the message ID.
	 * @param messageId
	 * @return
	 */
	public Resultsummary getResultSummaryByMessageId(String messageId) {
		Connection conn = getConnection();
		PreparedStatement statement = null;		
		try {
			statement = conn.prepareStatement("select resultsummaryId, resultId, messageId, relatesTo, " +
					"responseFlag, req_transactionId, res_transactionId, req_sender_hcid, " +
					"req_receiver_hcid, res_sender_hcid, res_receiver_hcid from resultsummary where messageId=?");

			statement.setString(1, messageId);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				Resultsummary resultsummary = new Resultsummary();
				resultsummary.setResultsummaryId(rs.getInt("resultsummaryId"));
				
				Caseresult caseresult = new Caseresult();
				caseresult.setResultId(rs.getInt("resultId"));
				resultsummary.setCaseresult(caseresult);
				
				resultsummary.setMessageId(rs.getString("messageId"));
				resultsummary.setRelatesTo(rs.getString("relatesTo"));
				resultsummary.setResponseFlag(rs.getString("responseFlag"));
				resultsummary.setReqTransactionid(rs.getInt("req_transactionId"));
				resultsummary.setResTransactionid(rs.getInt("res_transactionId"));
				resultsummary.setReqSenderHcid(rs.getString("req_sender_hcid"));
				resultsummary.setReqReceiverHcid(rs.getString("req_receiver_hcid"));
				resultsummary.setResSenderHcid(rs.getString("res_sender_hcid"));
				resultsummary.setResReceiverHcid(rs.getString("res_receiver_hcid"));
				return resultsummary;
			} else {
				return null;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}
		}		
	}
	
	/**
	 * Returns the ResultSummary using the message ID.
	 * @param messageId
	 * @return
	 */
	public List<Resultsummary> getResultSummary(Caseresult caseresult) {
		Connection conn = getConnection();
		try {
			return getResultSummary(caseresult, conn);			
		}finally {			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}
		}		
	}
	
	/**
	 * Returns the ResultSummary using the message ID.
	 * @param messageId
	 * @return
	 */
	private List<Resultsummary> getResultSummary(Caseresult caseresult, Connection conn) {
		PreparedStatement statement = null;
		List<Resultsummary> summaries = new ArrayList<Resultsummary>();
		try {
			statement = conn.prepareStatement("select resultsummaryId, resultId, messageId, relatesTo, " +
					"responseFlag, req_transactionId, res_transactionId, req_sender_hcid, " +
					"req_receiver_hcid, res_sender_hcid, res_receiver_hcid from resultsummary where resultId=?");

			statement.setInt(1, caseresult.getResultId());
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Resultsummary resultsummary = new Resultsummary();
				resultsummary.setResultsummaryId(rs.getInt("resultsummaryId"));
				
				resultsummary.setCaseresult(caseresult);
				
				resultsummary.setMessageId(rs.getString("messageId"));
				resultsummary.setRelatesTo(rs.getString("relatesTo"));
				resultsummary.setResponseFlag(rs.getString("responseFlag"));
				resultsummary.setReqTransactionid(rs.getInt("req_transactionId"));
				resultsummary.setResTransactionid(rs.getInt("res_transactionId"));
				resultsummary.setReqSenderHcid(rs.getString("req_sender_hcid"));
				resultsummary.setReqReceiverHcid(rs.getString("req_receiver_hcid"));
				resultsummary.setResSenderHcid(rs.getString("res_sender_hcid"));
				resultsummary.setResReceiverHcid(rs.getString("res_receiver_hcid"));
				summaries.add(resultsummary);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}
		}
		return summaries;
	}
	
	/**
	 * Returns the ResultSummary using the message ID.
	 * @param messageId
	 * @return
	 */
	private List<Resultdocument> getResultDocumets(Caseresult caseresult, Connection conn) {
		PreparedStatement statement = null;
		List<Resultdocument> documents = new ArrayList<Resultdocument>();
		try {
			statement = conn.prepareStatement("select resultDocumentId, resultId, documentUniqueId, classCode, " +
					" repositoryId, communityId, rawData, messageType, status, message, " +
					" createdAt, createdBy, updatedAt, updatedBy, documentHash, documentSize" +
					" from resultdocument where resultId=?");

			statement.setInt(1, caseresult.getResultId());
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Resultdocument document = new Resultdocument();
				document.setResultDocumentId(rs.getInt("resultDocumentId"));
				
				document.setCaseresult(caseresult);
				
				document.setDocumentUniqueId(rs.getString("documentUniqueId"));
				document.setClassCode(rs.getString("classCode"));
				document.setRepositoryId(rs.getString("repositoryId"));
				document.setCommunityId(rs.getString("communityId"));
				document.setRawData(rs.getBytes("rawData"));
				document.setMessageType(rs.getString("messageType"));
				document.setStatus(rs.getString("status"));
				document.setMessage(rs.getString("message"));
				document.setCreatedAt(rs.getTimestamp("createdAt"));
				document.setCreatedBy(rs.getString("createdBy"));
				document.setUpdatedAt(rs.getTimestamp("updatedAt"));
				document.setUpdatedBy(rs.getString("updatedBy"));
				document.setDocumentHash(rs.getString("documentHash"));
				document.setDocumentSize(rs.getInt("documentSize"));
				documents.add(document);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}
		}
		return documents;
	}
	
	public Resultsummary getResultSummaryByMaxId() {
		Connection conn = getConnection();
		PreparedStatement statement = null;		
		try {
			statement = conn.prepareStatement("select max(resultsummaryId), resultId, messageId, relatesTo, " +
					"responseFlag, req_transactionId, res_transactionId, req_sender_hcid, " +
					"req_receiver_hcid, res_sender_hcid, res_receiver_hcid from resultsummary ");
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				Resultsummary resultsummary = new Resultsummary();
				resultsummary.setResultsummaryId(rs.getInt("resultsummaryId"));
				
				Caseresult caseresult = new Caseresult();
				caseresult.setResultId(rs.getInt("resultId"));
				resultsummary.setCaseresult(caseresult);
				
				resultsummary.setMessageId(rs.getString("messageId"));
				resultsummary.setRelatesTo(rs.getString("relatesTo"));
				resultsummary.setResponseFlag(rs.getString("responseFlag"));
				resultsummary.setReqTransactionid(rs.getInt("req_transactionId"));
				resultsummary.setResTransactionid(rs.getInt("res_transactionId"));
				resultsummary.setReqSenderHcid(rs.getString("req_sender_hcid"));
				resultsummary.setReqReceiverHcid(rs.getString("req_receiver_hcid"));
				resultsummary.setResSenderHcid(rs.getString("res_sender_hcid"));
				resultsummary.setResReceiverHcid(rs.getString("res_receiver_hcid"));
				return resultsummary;
			} else {
				return null;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		finally {
			try { if(statement != null) { statement.close(); } }catch(Exception ex) {}			
			try { if(conn != null) { conn.close(); } }catch(Exception ex) {}
		}		
	}
	
	private static DBMockUtil instance = new DBMockUtil();
	private DBMockUtil() {}
	public static DBMockUtil getInstance() {
		return instance;
	}		
}
