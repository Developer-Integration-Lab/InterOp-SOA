package net.aegis.mv.parser.qd;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.qd.QDNhinRequest;
import net.aegis.mv.mockdata.MockTestcaseDataProvider;
import net.aegis.mv.mockdata.TestScenario;
import net.aegis.mv.mockdata.TestcaseData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QDRequestParserTest
{

	private static final Log log = LogFactory.getLog(QDRequestParserTest.class);
	private MockTestcaseDataProvider mockOjectFactory = MockTestcaseDataProvider.getInstance();

	public QDRequestParserTest() {
		log.info("QDRequestParserTest empty constructor - INFO");
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		log.info("QDRequestParserTest.setUpClass() - INFO");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		log.info("QDRequestParserTest.tearDownClass() - INFO");
	}

	@Before
	public void setUp() {
		log.info("QDRequestParserTest.setUp() - INFO");
		//System.setProperty("Property1", "value1");
	}

	@After
	public void tearDown() {
		log.info("QDRequestParserTest.tearDown() - INFO");
	}


	/*	@Test
	public void testQDRequestParser() throws Exception {

		//Prepare the test data.
		Transaction transaction = new Transaction();
		transaction.setMessageType("REQUEST");
		byte[] bMessage = readFile("/testdata/qd/Scenario18_QD_Request.xml");
		byte[] bMessageHeader = readFile("/testdata/qd/Scenario18_QD_Request_header.txt");
		transaction.setMessage(bMessage);
		transaction.setMessageHeader(bMessageHeader);
		transaction.setMessageType("REQUEST");
		transaction.setContentType("application/soap+xml");
		transaction.setId(1);

		//Process QD Request messages.		
		QDNhinRequest qdNhinRequest = QDRequestParser.getInstance().parse(transaction);

		System.out.println("\n\n============START:Extracted request data for transaction ID: " + transaction.getId() + "========================");
		if(docsetRequest != null) {
			printDocsetRequest(docsetRequest);
		}
		System.out.println("============END: Extracted request data for transaction ID: " + transaction.getId() + "========================\n\n");

		validateQDRequest(transaction, qdNhinRequest);
	}

	private void validateQDRequest(Transaction transaction, QDNhinRequest qdNhinRequest) {
		assertNotNull("Missing request is null", qdNhinRequest);

		assertNotNull("Message ID associated with the Request is null. Transaction ID: " + transaction.getId(), qdNhinRequest.getMessageID());
		assertTrue("Message ID not matching.", (qdNhinRequest.getMessageID().equals("uuid:99d152e8-6587-44af-8e5c-09b93260ddd1") ) );

		assertNotNull("Request missing patient id is null", qdNhinRequest.getResponderPatientID());
		assertTrue("Patient id not matching", (qdNhinRequest.getResponderPatientID().equalsIgnoreCase("NAR20700042")));

		assertNotNull("Request missing HCID is null", qdNhinRequest.getResponderHCID());
		assertTrue("HCID not matching", (qdNhinRequest.getResponderHCID().equalsIgnoreCase("2.16.840.1.113883.0.207")));

		assertNotNull("Request missing Assigning Authority Id is null", qdNhinRequest.getResponderAAID());
		assertTrue("Assigning Authority Id not matching", (qdNhinRequest.getResponderHCID().equalsIgnoreCase("2.16.840.1.113883.0.207")));

		assertNotNull("Request missing DocEntryStatus is null", qdNhinRequest.getDocEntryStatusList());
		assertTrue("DocEntryStatus not matching", (qdNhinRequest.getDocEntryStatusList().get(0).equalsIgnoreCase("('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved','urn:oasis:names:tc:ebxml-regrep:StatusType:DeferredCreation')")));

		assertNotNull("Request missing return type is null", qdNhinRequest.getReturnType());
		assertTrue("Return Type not matching", (qdNhinRequest.getReturnType().equalsIgnoreCase("LeafClass")));

//		assertNotNull("Request missing Classcode is null", qdNhinRequest.getEntryClassCodeList());
//		assertTrue("Classcode not matching", (qdNhinRequest.getEntryClassCodeList().get(0).equalsIgnoreCase("34133-9")));

//		assertNotNull("Request missing EntryCreationTimeFrom is null", qdNhinRequest.getEntryCreationTimeFrom());
//		assertTrue("EntryCreationTimeFrom not matching", (qdNhinRequest.getEntryCreationTimeFrom().equalsIgnoreCase("20080120000000")));
//		
//		assertNotNull("Request missing EntryCreationTimeTo is null", qdNhinRequest.getEntryCreationTimeTo());
//		assertTrue("EntryCreationTimeTo not matching", (qdNhinRequest.getEntryCreationTimeTo().equalsIgnoreCase("20080220000000")));
//		
		assertNotNull("Request missing ServiceStartTimeFrom is null", qdNhinRequest.getServiceStartTimeFrom());
		assertTrue("ServiceStartTimeFrom not matching", (qdNhinRequest.getServiceStartTimeFrom().equalsIgnoreCase("20080120000000")));

		assertNotNull("Request missing ServiceStartTimeTo is null", qdNhinRequest.getServiceStartTimeTo());
		assertTrue("ServiceStartTimeTo not matching", (qdNhinRequest.getServiceStartTimeTo().equalsIgnoreCase("20080220000000")));

		assertNotNull("Request missing ServiceStopTimeFrom is null", qdNhinRequest.getServiceStopTimeFrom());
		assertTrue("ServiceStopTimeFrom not matching", (qdNhinRequest.getServiceStopTimeFrom().equalsIgnoreCase("20080220000000")));

		assertNotNull("Request missing getServiceStopTimeTo is null", qdNhinRequest.getServiceStopTimeTo());
		assertTrue("getServiceStopTimeTo not matching", (qdNhinRequest.getServiceStopTimeTo().equalsIgnoreCase("20080320000000")));

	}*/

	@Test
	public void testQDRequestParserCSV() throws Exception {

		//Prepare the test data.
		for(TestcaseData testcaseData : mockOjectFactory.getTestcaseDataByScenario(TestScenario.QD_SUCCESS_SCENARIO)){

			Transaction transaction = testcaseData.getRequestTransaction();
			QDNhinRequest expectedData = (QDNhinRequest)testcaseData.getExpectedRequestMessage();

			if(transaction != null){
				//Process QD Request messages.		
				QDNhinRequest qdNhinRequest = QDRequestParser.getInstance().parse(transaction);

				System.out.println("\n============START:Extracted request data for transaction ID: " + transaction.getId() + "========================");
				if(qdNhinRequest != null) {
					printqdNhinRequest(qdNhinRequest);
				}
				System.out.println("============END: Extracted request data for transaction ID: " + transaction.getId() + "========================\n");

				if(qdNhinRequest != null) {
					printExpectedValues(expectedData);
				}
				validateQDRequestCSV(expectedData, qdNhinRequest);
			}
		}
	}

	private void validateQDRequestCSV(QDNhinRequest expectedData, QDNhinRequest qdNhinRequest) {
		assertNotNull("Missing request is null", qdNhinRequest);

		assertNotNull("Message ID associated with the Request is null. " + qdNhinRequest.getMessageID());
		assertTrue("Message ID not matching.", (qdNhinRequest.getMessageID().equals(expectedData.getMessageID()) ) );

		assertNotNull("Request missing patient id is null", qdNhinRequest.getResponderPatientID());
		assertTrue("Patient id not matching", (qdNhinRequest.getResponderPatientID().equalsIgnoreCase(expectedData.getResponderPatientID())));

		if(qdNhinRequest.getResponderHCID() == null){
			qdNhinRequest.setResponderHCID("");
		}
		assertTrue("Home Community Id not matching", (qdNhinRequest.getResponderHCID().equalsIgnoreCase(expectedData.getResponderHCID())));

		assertNotNull("Request missing Assigning Authority Id is null", qdNhinRequest.getResponderAAID());
		assertTrue("Assigning Authority Id not matching", (qdNhinRequest.getResponderAAID().equalsIgnoreCase(expectedData.getResponderAAID())));

		assertNotNull("Request missing DocEntryStatus is null", qdNhinRequest.getDocEntryStatusList());
		assertTrue("DocEntryStatus not matching", (qdNhinRequest.getDocEntryStatusList().containsAll(expectedData.getDocEntryStatusList())));

		assertNotNull("Request missing return type is null", qdNhinRequest.getReturnType());
		assertTrue("Return Type not matching", (qdNhinRequest.getReturnType().equalsIgnoreCase(expectedData.getReturnType())));

		//		assertNotNull("Request missing Classcode is null", qdNhinRequest.getEntryClassCodeList());
		//		assertTrue("Classcode not matching", (qdNhinRequest.getEntryClassCodeList().containsAll(expectedData.getEntryClassCodeList())));
		//		
		//		assertNotNull("Request missing EntryCreationTimeFrom is null", qdNhinRequest.getEntryCreationTimeFrom());
		//		assertTrue("EntryCreationTimeFrom not matching", (qdNhinRequest.getEntryCreationTimeFrom().equalsIgnoreCase(expectedData.getEntryCreationTimeFrom())));
		//		
		//		assertNotNull("Request missing EntryCreationTimeTo is null", qdNhinRequest.getEntryCreationTimeTo());
		//		assertTrue("EntryCreationTimeTo not matching", (qdNhinRequest.getEntryCreationTimeTo().equalsIgnoreCase(expectedData.getEntryCreationTimeTo())));

		if(qdNhinRequest.getServiceStartTimeFrom() == null){
			qdNhinRequest.setServiceStartTimeFrom("");
		}
		assertTrue("ServiceStartTimeFrom not matching", (qdNhinRequest.getServiceStartTimeFrom().equalsIgnoreCase(expectedData.getServiceStartTimeFrom())));

		if(qdNhinRequest.getServiceStartTimeTo()== null){
			qdNhinRequest.setServiceStartTimeTo("");
		}
		assertTrue("ServiceStartTimeTo not matching", (qdNhinRequest.getServiceStartTimeTo().equalsIgnoreCase(expectedData.getServiceStartTimeTo())));

		if(qdNhinRequest.getServiceStopTimeFrom()== null){
			qdNhinRequest.setServiceStopTimeFrom("");
		}
		assertTrue("ServiceStopTimeFrom not matching", (qdNhinRequest.getServiceStopTimeFrom().equalsIgnoreCase(expectedData.getServiceStopTimeFrom())));

		if(qdNhinRequest.getServiceStopTimeTo()== null){
			qdNhinRequest.setServiceStopTimeTo("");
		}
		assertTrue("getServiceStopTimeTo not matching", (qdNhinRequest.getServiceStopTimeTo().equalsIgnoreCase(expectedData.getServiceStopTimeTo())));
	}

	private void printqdNhinRequest(QDNhinRequest qDNhinRequest) {
		if(qDNhinRequest == null) {
			System.out.println("No requests found.");
		}else{
			System.out.println("\n************ QDNhinRequest Parameters Start ******************");
			System.out.println("ResponderHCID : "+ qDNhinRequest.getResponderHCID());
			System.out.println("ResponderAAID : "+ qDNhinRequest.getResponderAAID());
			System.out.println("MessageID : "+ qDNhinRequest.getMessageID());
			System.out.println("ResponderPatientID : "+ qDNhinRequest.getResponderPatientID());
			System.out.println("DocEntryStatus : "+ qDNhinRequest.getDocEntryStatusList());
			System.out.println("ReturnType : "+ qDNhinRequest.getReturnType());
			System.out.println("ServiceStartTimeFrom : "+ qDNhinRequest.getServiceStartTimeFrom());
			System.out.println("ServiceStartTimeTo : "+ qDNhinRequest.getServiceStartTimeTo());
			System.out.println("ServiceStopTimeFrom : "+ qDNhinRequest.getServiceStopTimeFrom());
			System.out.println("ServiceStopTimeTo : "+ qDNhinRequest.getServiceStopTimeTo());
			System.out.println("************** QDNhinRequest Parameters End ****************\n");
		}	
	}

	private void printExpectedValues(QDNhinRequest expectedData) {
		if(expectedData == null) {
			System.out.println("No expectedData found.");
		}else{
			System.out.println("\n************ Expected Parameters Start ******************");
			System.out.println("ResponderHCID : "+ expectedData.getResponderHCID());
			System.out.println("ResponderAAID : "+ expectedData.getResponderAAID());
			System.out.println("MessageID : "+ expectedData.getMessageID());
			System.out.println("ResponderPatientID : "+ expectedData.getResponderPatientID());
			System.out.println("DocEntryStatus : "+ expectedData.getDocEntryStatusList());
			System.out.println("ReturnType : "+ expectedData.getReturnType());
			System.out.println("ServiceStartTimeFrom : "+ expectedData.getServiceStartTimeFrom());
			System.out.println("ServiceStartTimeTo : "+ expectedData.getServiceStartTimeTo());
			System.out.println("ServiceStopTimeFrom : "+ expectedData.getServiceStopTimeFrom());
			System.out.println("ServiceStopTimeTo : "+ expectedData.getServiceStopTimeTo());
			System.out.println("************** Expected Parameters End ****************\n");
		}	
	}

//	private String getPatientId(String property){
//		if(property == null){
//			return null;
//		}
//		GatewayPropertyFactory factory = GatewayPropertyFactory.getInstance(); 
//		String [] propertyArray = property.split("\\$");
//		String prefix = (propertyArray.length > 1) ? factory.getProperty(propertyArray[1]) : property;
//		String patientId = (propertyArray.length > 1)? prefix + propertyArray[2] : property;
//		return patientId;
//	}
//
//	private String getHCID(String property) {
//		if(property == null){
//			return null;
//		}
//		GatewayPropertyFactory factory = GatewayPropertyFactory.getInstance(); 
//		String HCID = (property.startsWith("$") )? factory.getProperty(property.substring(1)) : property;
//		return HCID;		
//	}
}