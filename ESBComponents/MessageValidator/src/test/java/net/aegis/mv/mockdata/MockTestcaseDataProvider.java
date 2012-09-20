package net.aegis.mv.mockdata;

import java.io.BufferedInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.gateway.agent.dao.pojo.Gateway;
import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.jaxb.msg.NhinMessage;
import net.aegis.mv.jaxb.qd.DocumentInfo;
import net.aegis.mv.jaxb.qd.QDNhinRequest;
import net.aegis.mv.jaxb.qd.QDNhinResponse;
import net.aegis.mv.jaxb.rd.RdNhinDocumentRequest;
import net.aegis.mv.jaxb.rd.RdNhinDocumentResponse;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetRequest;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetResponse;

public class MockTestcaseDataProvider {

	/** 
	 * Data cache by scenario.
	 */
	private Map<TestScenario, List<TestcaseData>> cacheDataByScenario = new HashMap<TestScenario, List<TestcaseData>>();
	
	/**
	 * Data cache by testId.
	 */
	private Map<String, TestcaseData> cacheDataByTestId = new HashMap<String, TestcaseData>();
	
	/**
	 * Inventory of all the documents loaded from CSV file. Key holds the documentUniqueId. 
	 */
	private final Map<String, ExpectedDocument> documentCache = new HashMap<String, ExpectedDocument>();
		
	public List<TestcaseData> getTestcaseDataByScenario(TestScenario testcaseType) {
		return cacheDataByScenario.get(testcaseType);		
	}
	
	/**
	 * Returns testcase data by test id which is the record identifier in the mock data CSV file.
	 * 
	 * @param testId
	 * @return
	 */
	public TestcaseData getTestcaseDataByTestId(String testId) {
		return cacheDataByTestId.get(testId);
	}
	
	/**
	 * Returns ExpectedDocument by document unique id.
	 * @param documentUniqueId
	 * @return
	 */
	public ExpectedDocument getDocumentByDocumentUniqueId(String documentUniqueId) {
		return documentCache.get(documentUniqueId);
	}
	
	private enum MessageType {
		PD, QD, RD;
	}
	
	private static MockTestcaseDataProvider factory = new MockTestcaseDataProvider();
	
	private MockTestcaseDataProvider() {
		loadData();
	}

	public static MockTestcaseDataProvider getInstance() {
		return factory;
	}
	
	private void loadData() {
		CSVDataLoader loader = new CSVDataLoader();

		//A. Load PD Mock data
		//TODO	

		//B. Load Documents into Cache. These documents later are attached to QD and RD request/response messages.
		documentCache.putAll(loadDocumentDetails(loader));
		
		//C. Load QD Mock data
		List<TransactionMetaData> transactionMetaData_QD = loader.load(TransactionMetaData.class, "testdata/qd/QD_mockdata.csv");
		updateTransactionCache(transactionMetaData_QD, MessageType.QD);
		
		//D. Load QD expected data
		List<QDExpectedMessage> qdExpectedData = loader.load(QDExpectedMessage.class, "testdata/qd/QD_Parser_expected_data.csv");
		updateQDExpectedDataCache(qdExpectedData, documentCache.values());
		
		//E. Load RD Mock data
		List<TransactionMetaData> transactionMetaData_RD = loader.load(TransactionMetaData.class, "testdata/rd/RD_mockdata.csv");
		updateTransactionCache(transactionMetaData_RD, MessageType.RD);
		
		//F. Load RD expected data.
		List<RDExpectedMessage> rdExpectedData = loader.load(RDExpectedMessage.class, "testdata/rd/RD_expected_data.csv");
		updateRDExpectedDataCache(rdExpectedData, documentCache.values());
		
	}
	
	/**
	 * Creates RD request and response messages based on the data loaded the CSV file. 
	 * @param rdExpectedData
	 */
	private void updateRDExpectedDataCache(List<RDExpectedMessage> rdExpectedData, Collection<ExpectedDocument> documents) {
		for(RDExpectedMessage expectedData : rdExpectedData) {
			
			//Create the NhinMessage from the CSV data record.
			NhinMessage rdMessage = getRDMessage(expectedData, documents);
			String testId = expectedData.getCaseId();
			TestcaseData testData = cacheDataByTestId.get(testId);
			if(testData != null) {
				if(expectedData.getMessageType().equalsIgnoreCase("REQUEST")) {
					testData.setExpectedRequestMessage(rdMessage);
				}else if(expectedData.getMessageType().equalsIgnoreCase("RESPONSE")) {
					testData.setExpectedResponseMessage(rdMessage);
				} 
			}
		}
	}
	
	private NhinMessage getRDMessage(RDExpectedMessage expectedData, Collection<ExpectedDocument> documents) {
		//Get the list of expected documents from the cache using the referenced IDs.
		List<ExpectedDocument> rdExpectedDocs = getExpectedDocuments(expectedData.getDocList(), documents);

		NhinMessage rdMessage = null;
		if(expectedData.getMessageType().equalsIgnoreCase("REQUEST")) {
			RdNhinDocumentSetRequest requestSet = new RdNhinDocumentSetRequest();
			requestSet.setMessageID(expectedData.getMessageId());
			for(ExpectedDocument rdExpectedDoc : rdExpectedDocs) {
				RdNhinDocumentRequest request = new RdNhinDocumentRequest();
				request.setHomeCommunityId(expectedData.getHcid());
				request.setDocumentUniqueId(rdExpectedDoc.getDocumentUniqueId());
				request.setRepositoryId(rdExpectedDoc.getRepositoryUniqueId());
				requestSet.getRequestSet().add(request);
			}				
			rdMessage = requestSet;
		}else if(expectedData.getMessageType().equalsIgnoreCase("RESPONSE")) {		
			RdNhinDocumentSetResponse responseSet = new RdNhinDocumentSetResponse();
			responseSet.setMessageID(expectedData.getMessageId());
			for(ExpectedDocument rdExpectedDoc : rdExpectedDocs) {
				RdNhinDocumentResponse responseObj = new RdNhinDocumentResponse();
				responseObj.setHomeCommunityId(expectedData.getHcid());
				responseObj.setDocumentUniqueId(rdExpectedDoc.getDocumentUniqueId());
				responseObj.setRepositoryId(rdExpectedDoc.getRepositoryUniqueId());
				responseSet.getResponseSet().add(responseObj);	
			}
			rdMessage = responseSet;				
		}
		return rdMessage;
	}
	
	private void updateQDExpectedDataCache(List<QDExpectedMessage> qdExpectedData, Collection<ExpectedDocument> documents) {
		//Populate the candidate and server data.		
		for(QDExpectedMessage expectedData : qdExpectedData) {			
			//Create the NhinMessage from the CSV data record.
			NhinMessage qdMessage = getQDMessage(expectedData, documents);
			String testId = expectedData.getCaseId();
			TestcaseData testData = cacheDataByTestId.get(testId);
			if(testData != null) {
				if(expectedData.getMessageType().equalsIgnoreCase("REQUEST")) {
					testData.setExpectedRequestMessage(qdMessage);
				}else if(expectedData.getMessageType().equalsIgnoreCase("RESPONSE")) {
					testData.setExpectedResponseMessage(qdMessage);
				} 
			}
		}
	}
	
	private NhinMessage getQDMessage(QDExpectedMessage expectedData, Collection<ExpectedDocument> documents) {
		NhinMessage qdMessage = null;
		if(expectedData.getMessageType().equalsIgnoreCase("REQUEST")) {
			QDNhinRequest request = new QDNhinRequest();
			request.setMessageID(expectedData.getMessageId());
			request.setResponderPatientID((expectedData.getPatientId()));
			request.setResponderHCID(expectedData.getHcid());
			request.setResponderAAID(expectedData.getAaId());
			request.setReturnType(expectedData.getReturnType());
			request.setServiceStartTimeFrom(expectedData.getServiceStartTimeFrom());
			request.setServiceStartTimeTo(expectedData.getServiceStartTimeTo());
			request.setServiceStopTimeFrom(expectedData.getServiceStopTimeFrom());
			request.setServiceStopTimeTo(expectedData.getServiceStopTimeTo());
			String [] entryList = expectedData.getDocEntryStatus().split(LabConstants.SPLITTER_TRIPPLE_CARET);
			for(String docEntry : entryList){
				request.getDocEntryStatusList().add(docEntry);
			}				
			qdMessage = request;
		}else if(expectedData.getMessageType().equalsIgnoreCase("RESPONSE")) {		
			QDNhinResponse response = new QDNhinResponse();
			response.setRelatesTo(expectedData.getMessageId());
			response.setResponderPatientID(expectedData.getPatientId());
			response.setResponderHCID(expectedData.getHcid());
			response.setResponderAAID(expectedData.getAaId());
		//	reponse.setResponseStatusType(expectedData.getReturnType());
			
			//Get the list of expected documents from the cache using the referenced IDs.
			List<ExpectedDocument> qdExpectedDocs = getExpectedDocuments(expectedData.getDocList(), documents);
			for(ExpectedDocument qdExpectedDoc : qdExpectedDocs) {
				if(getQDDocument(qdExpectedDoc) != null){
					response.getDocumentList().add(getQDDocument(qdExpectedDoc));
				}
			}
			qdMessage = response;
		}
		return qdMessage;
	}
	
	private Map<String, ExpectedDocument> loadDocumentDetails(CSVDataLoader loader)	{
		List<ExpectedDocument> documents = loader.load(ExpectedDocument.class, "testdata/document_details.csv");
		
		Map<String, ExpectedDocument> documentMap = new HashMap<String, ExpectedDocument>();
		for(ExpectedDocument doc : documents) {
			documentMap.put(doc.getDocumentUniqueId(), doc);
		}
		return documentMap;
	}
	
	private List<ExpectedDocument> getExpectedDocuments(String docIds, Collection<ExpectedDocument> documents) {
		String[] docIdList = (docIds != null && docIds.length() > 0) 
								? docIds.split(LabConstants.SPLITTER) 
								: new String[0];
		List<ExpectedDocument> rdExpectedDocs = new ArrayList<ExpectedDocument>();
		for(String docId : docIdList) {
			for(ExpectedDocument document : documents) {
				if(document.getId().equals(docId)) {
					rdExpectedDocs.add(document);
				}
			}
		}
		return rdExpectedDocs;
	}
	
	private DocumentInfo getQDDocument(ExpectedDocument expectedDoc) {
		DocumentInfo docInfo = new DocumentInfo();		
		docInfo.setClassCode(expectedDoc.getClassCode());
		docInfo.setDocumentId(expectedDoc.getDocumentUniqueId());
		docInfo.setExtrinsicObjectStatus(expectedDoc.getExtrinsicObjectStatus());
		docInfo.setHash(expectedDoc.getHash());
		docInfo.setSize(expectedDoc.getSize());
		docInfo.setRepositoryUniqueId(expectedDoc.getRepositoryUniqueId());
		return docInfo;
	}
	
	/**
	 * Loads the Transaction data into the main cache for each CSV record of given message type: PD, QD or RD.
	 * 
	 * @param transactionMetaData
	 * 			Transaction data record from the CSV file.
	 * 
	 * @param messageType
	 * 			PD, QD or RD
	 */
	private void updateTransactionCache(List<TransactionMetaData> transactionMetaData, MessageType messageType) {
		
		//Stand up the transaction data from the CSV data records.
		Collection<TestcaseData> testDataObjects = createTransactionData(transactionMetaData,messageType);
		
		//Now add them to the cache.
		for(TestcaseData testData : testDataObjects) {
			
			//Add to cache by scenario
			TestScenario testScenario = testData.getTestcaseScenario();			
			List<TestcaseData> testDataList = cacheDataByScenario.get(testScenario);
			if(testDataList == null) {
				testDataList = new ArrayList<TestcaseData>();
				cacheDataByScenario.put(testScenario, testDataList);
			}
			testDataList.add(testData);
			
			//Add to cache by testId.
			cacheDataByTestId.put(testData.getTestcaseId(), testData);			
		}
	}
	
	private Collection<TestcaseData> createTransactionData(List<TransactionMetaData> transactionsMetaData, MessageType messageType) {
		
		//We have a pair of request/response records associated by the caseId.
		//Identify those pair and add their translated Transaction objects to  
		//a TestcaseData object.
		Map<String, TestcaseData> dataCache = new HashMap<String, TestcaseData>();
		for(TransactionMetaData metaData : transactionsMetaData) {
			
			//Get the Testcase data for a given case id. If doesn't exist, create an empty one and
			//add it to the testDataCache and we will populate it with the transactions later.
			String caseId = metaData.getCaseId();
			TestcaseData testcaseData = dataCache.get(caseId); 
			if(testcaseData == null) {
				testcaseData = new TestcaseData();
				testcaseData.setTestcaseId(caseId);
				dataCache.put(caseId, testcaseData);
			}
			
			//Create transaction form meta data.
			Transaction transaction = createTransaction(metaData);
			
			//Add the type of scenario whether success or failure.
			TestScenario testcaseScenario = null;
			switch(messageType) {
			case PD:
				testcaseScenario = metaData.isSuccessScenario() 
									? TestScenario.PD_SUCCESS_SCENARIO
									: TestScenario.PD_FAILURE_SCENARIO;
				break;
			case QD:
				testcaseScenario = metaData.isSuccessScenario() 
				? TestScenario.QD_SUCCESS_SCENARIO
				: TestScenario.QD_FAILURE_SCENARIO;
				break;
			case RD:
				testcaseScenario = metaData.isSuccessScenario() 
				? TestScenario.RD_SUCCESS_SCENARIO
				: TestScenario.RD_FAILURE_SCENARIO;
				break;
			default:
				//Shouldn't occur.				
			}			
			testcaseData.setTestcaseScenario(testcaseScenario);
			
			//Now, populates the Testcase data with the transaction.
			if(transaction.getMessageType().equalsIgnoreCase("REQUEST")) {
				testcaseData.setRequestTransaction(transaction);
			} else if(transaction.getMessageType().equalsIgnoreCase("RESPONSE")) {
				testcaseData.setResponseTransaction(transaction);
			} 
		}		
		return dataCache.values();
	}
	
	private Transaction createTransaction(TransactionMetaData metaData) {
		Transaction transaction = new Transaction();
		transaction.setId(IdGenerator.getNextId());
		transaction.setClient(metaData.getClient());
		transaction.setContentLength(metaData.getContentLength());
		transaction.setContentType(metaData.getContentType());
		transaction.setDuration(metaData.getDuration());		
		transaction.setMessage(readFile(metaData.getMessage()));
		transaction.setMessageHeader(readFile(metaData.getMessageHeader()));
		transaction.setMessageType(metaData.getMessageType());
		transaction.setMethod(metaData.getMethod());
		transaction.setPath(metaData.getPath());
		transaction.setRule(metaData.getRule());		
		transaction.setStatusCode(metaData.getStatusCode());
		transaction.setTime(new Timestamp(System.currentTimeMillis()));
		
		//Populate the candidate and server data.		
		GatewayPropertyFactory factory = GatewayPropertyFactory.getInstance(); 
		
		//1. Sender
		String senderProperty = stripOffPrefix(metaData.getSender());
		String sender = factory.getProperty(senderProperty);
		transaction.setSender(createGateway(sender));
		
		//2. receiver
		String receiverProperty = stripOffPrefix(metaData.getReceiver());
		String receiver = factory.getProperty(receiverProperty);
		transaction.setReceiver(createGateway(receiver));
		
		//3. Server
		String serverProperty = stripOffPrefix(metaData.getServer());
		String server = factory.getProperty(serverProperty);
		transaction.setServer(server);
		
		//4. Client
		String clientProperty = stripOffPrefix(metaData.getClient());
		String client = factory.getProperty(clientProperty);
		transaction.setClient(client);
		
		return transaction;
	}
	
	private String stripOffPrefix(String property) {
		return (property.startsWith("$") ? property.substring(1) : property);		
	}
	
	private Gateway createGateway(String hcid) {
		Gateway gateway = new Gateway();
		gateway.setHCID(hcid);
		
		//TODO: Does this gateway need any other data?.
		return gateway;		
	}
	
	private byte[] readFile(String relativePath) {
		BufferedInputStream bis   = null;
		try
		{	
			bis = new BufferedInputStream(MockTestcaseDataProvider.class.getResourceAsStream(relativePath));	
			byte[] bMessage = new byte[100*1024];
			int i = bis.read(bMessage);
			return new String(bMessage).trim().getBytes();
			//System.out.println("#" + new String(bMessage) + "#");		
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		finally{
			try {if(bis != null) { bis.close(); } }catch(Exception ex){}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		MockTestcaseDataProvider factory = MockTestcaseDataProvider.getInstance();
		List<TestcaseData> data = factory.getTestcaseDataByScenario(TestScenario.RD_SUCCESS_SCENARIO);
		
		System.out.println("data: " + data.size());
		for(TestcaseData testcaseData : data) {
			//Get Request transaction
			Transaction requestTransaction = testcaseData.getRequestTransaction();			
						
			//Get Expected Request Data
			RdNhinDocumentSetRequest expectedRequestData = (RdNhinDocumentSetRequest)testcaseData.getExpectedRequestMessage();
			
			//Print it
			factory.print(testcaseData.getTestcaseId(), requestTransaction, expectedRequestData);
			
			//Get Response transaction
			Transaction responseTransaction = testcaseData.getResponseTransaction();
			
			//Get Expected Response Data
			RdNhinDocumentSetResponse expectedResponseData = (RdNhinDocumentSetResponse)testcaseData.getExpectedResponseMessage();
			
			//Print it
			factory.print(testcaseData.getTestcaseId(), responseTransaction, expectedResponseData);
						
			//Print cache by test IDs
			System.out.println(factory.cacheDataByTestId.keySet());

		}
	}
	
	private void print(String testcaseId, Transaction transaction, RdNhinDocumentSetRequest expectedData) {
		System.out.println("Transaction Data\n=============================");
		System.out.println("Testcase ID         : " + testcaseId);
		print(transaction);
		
		System.out.println("RdNhinDocumentSetRequest Data\n=============================");
		print(expectedData);
	}
	
	private void print(String testcaseId, Transaction transaction, RdNhinDocumentSetResponse expectedData) {
		System.out.println("Transaction Data\n=============================");
		System.out.println("Testcase ID         : " + testcaseId);
		print(transaction);
		
		System.out.println("RdNhinDocumentSetResponse Data\n=============================");
		print(expectedData);
	}
	
	private void print(Transaction transaction) {
		System.out.println("Message Type        : " + transaction.getMessageType());
		System.out.println("Content Type        : " + transaction.getContentType());
		System.out.println("Content Length      : " + transaction.getContentLength());						
		//System.out.println("Message size        : " + transaction.getMessage());
		//System.out.println("Message Header size : " + transaction.getMessageHeader());
		System.out.println("method              : " + transaction.getMethod());
		System.out.println("Path                : " + transaction.getPath());
		System.out.println("Server              : " + transaction.getServer());
		System.out.println("Client              : " + transaction.getClient());
		System.out.println("Duration            : " + transaction.getDuration());
		System.out.println("Rule                : " + transaction.getRule());
		System.out.println("Status code         : " + transaction.getStatusCode());
		System.out.println("Time                : " + transaction.getTime() + "\n");
	}
	
	private void print(RdNhinDocumentSetRequest expectedData) {
		System.out.println("Message ID          : " + expectedData.getMessageID());
		List<RdNhinDocumentRequest> requests = expectedData.getRequestSet();
		for(int i=0; i< requests.size(); i++) {
			System.out.println("HCID(" + i + ")            : " + requests.get(i).getHomeCommunityId());
			System.out.println("Document ID(" + i + ")     : " + requests.get(i).getDocumentUniqueId());		
			System.out.println("Repository ID(" + i + ")   : " + requests.get(i).getRepositoryId() + "\n");
		}
	}
	
	private void print(RdNhinDocumentSetResponse expectedData) {
		System.out.println("Message ID          : " + expectedData.getMessageID());
		List<RdNhinDocumentResponse> responses = expectedData.getResponseSet();
		for(int i=0; i< responses.size(); i++) {
			System.out.println("HCID(" + i + ")            : " + responses.get(i).getHomeCommunityId());
			System.out.println("Document ID(" + i + ")     : " + responses.get(i).getDocumentUniqueId());		
			System.out.println("Repository ID(" + i + ")   : " + responses.get(i).getRepositoryId() + "\n");
		}
	}
	
	private void print(String testcaseId, List<TransactionMetaData> transactionMetaData ) {
		System.out.println("\n\n==================Transaction Data-START=============================");
		for(TransactionMetaData transaction : transactionMetaData) {			
			System.out.println("Content Type        : " + transaction.getContentType());
			System.out.println("Content Length      : " + transaction.getContentLength());
			System.out.println("Message Type        : " + transaction.getMessageType());			
			System.out.println("Message Path        : " + transaction.getMessage());
			System.out.println("Message Header      : " + transaction.getMessageHeader());
			System.out.println("method              : " + transaction.getMethod());
			System.out.println("Path                : " + transaction.getPath());
			System.out.println("Server              : " + transaction.getServer());
			System.out.println("Client              : " + transaction.getClient());
			System.out.println("Duration            : " + transaction.getDuration());
			System.out.println("Rule                : " + transaction.getRule());
			System.out.println("Status code         : " + transaction.getStatusCode());
			System.out.println("Initiator scenario? : " + transaction.isInitiatorScenario());
			System.out.println("Success scenario?   : " + transaction.isSuccessScenario());
			System.out.println("Status code         : " + transaction.getStatusCode());
			System.out.println("Sender              : " + transaction.getSender());
			System.out.println("Receiver            : " + transaction.getReceiver());
			System.out.println("Server              : " + transaction.getServer());
			System.out.println("Client              : " + transaction.getClient());
			
			System.out.println("Time                : " + transaction.getTime() + "\n");
			
		}
		System.out.println("\n==================Transaction Data-END=============================\n");		
	}
}
