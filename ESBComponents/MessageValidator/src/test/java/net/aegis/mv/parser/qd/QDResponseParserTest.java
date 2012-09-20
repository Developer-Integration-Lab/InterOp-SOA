package net.aegis.mv.parser.qd;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.BufferedInputStream;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.qd.DocumentInfo;
import net.aegis.mv.jaxb.qd.QDNhinRequest;
import net.aegis.mv.jaxb.qd.QDNhinResponse;
import net.aegis.mv.mockdata.GatewayPropertyFactory;
import net.aegis.mv.mockdata.MockTestcaseDataProvider;
import net.aegis.mv.mockdata.TestScenario;
import net.aegis.mv.mockdata.TestcaseData;
import net.aegis.mv.util.IOUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QDResponseParserTest
{

	private static final Log log = LogFactory.getLog(QDResponseParserTest.class);
	private MockTestcaseDataProvider mockOjectFactory = MockTestcaseDataProvider.getInstance();

	public QDResponseParserTest() {
		log.info("QDResponseParserTest empty constructor - INFO");
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		log.info("QDResponseParserTest.setUpClass() - INFO");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		log.info("QDResponseParserTest.tearDownClass() - INFO");
	}

	@Before
	public void setUp() {
		log.info("QDResponseParserTest.setUp() - INFO");
	}

	@After
	public void tearDown() {
		log.info("QDResponseParserTest.tearDown() - INFO");
	}

//	@Test	
//	public void parseQDRequestXML()
//	{
//		BufferedInputStream stream   = null;
//		try
//		{	
//			stream = new BufferedInputStream(getClass().getResourceAsStream("/Scenario18_QD_Response.xml"));	
//			String xmlContent = IOUtil.getContentsFromInputStream(stream);
//			Transaction transaction = new Transaction();
//			transaction.setMessage(xmlContent.getBytes());
//			QDResponseParser parser = QDResponseParser.getInstance();
//			parser.parse(transaction);		
//		}catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try { if (stream != null) { stream.close(); } }catch(Exception ex) {}
//		}		
//	}	

	@Test
	public void testQDResponseParserCSV() throws Exception {

		//Prepare the test data.
		TestcaseData testcaseData = mockOjectFactory.getTestcaseDataByScenario(TestScenario.QD_SUCCESS_SCENARIO).get(0);
		Transaction transaction = testcaseData.getResponseTransaction();
		QDNhinResponse expectedData = (QDNhinResponse)testcaseData.getExpectedResponseMessage();

		//Process QD Response messages.		
		QDNhinResponse qdNhinResponse = QDResponseParser.getInstance().parse(transaction);
		
		System.out.println("\n============START:Extracted response data for transaction ID: " + transaction.getId() + "========================");
		if(qdNhinResponse != null) {
			printqdNhinResponse(qdNhinResponse);
		}
		System.out.println("============END: Extracted response data for transaction ID: " + transaction.getId() + "========================\n");
		if(qdNhinResponse != null) {
			printExpectedValues(expectedData);
		}
		validateQDResponseCSV(expectedData, qdNhinResponse);
	}

	private void validateQDResponseCSV(QDNhinResponse expectedData, QDNhinResponse qdNhinResponse) {
		assertNotNull("Missing response is null", qdNhinResponse);

		assertNotNull("getRelatesTo ID associated with the Response is null. " + qdNhinResponse.getRelatesTo());
		assertTrue("getRelatesTo ID not matching.", (qdNhinResponse.getRelatesTo().equalsIgnoreCase(expectedData.getRelatesTo())) );

		assertNotNull("Response missing patient id is null", qdNhinResponse.getResponderPatientID());
		assertTrue("Patient id not matching", (qdNhinResponse.getResponderPatientID().equalsIgnoreCase(expectedData.getResponderPatientID())));

		if(qdNhinResponse.getResponderHCID() == null){
			qdNhinResponse.setResponderHCID("");
		}
		assertTrue("Home Community Id not matching", (qdNhinResponse.getResponderHCID().equalsIgnoreCase(expectedData.getResponderHCID())));
		
		assertNotNull("Response missing Assigning Authority Id is null", qdNhinResponse.getResponderAAID());
		assertTrue("Assigning Authority Id not matching", (qdNhinResponse.getResponderAAID().equalsIgnoreCase(expectedData.getResponderAAID())));

		for(DocumentInfo doc : qdNhinResponse.getDocumentList()){
			boolean matchingDocFound = false;
			for(DocumentInfo expectedDoc : expectedData.getDocumentList()){
				if(doc.getDocumentId().equalsIgnoreCase(expectedDoc.getDocumentId())){
					matchingDocFound = true;
					if(doc.getHash() == null){
						doc.setHash("");
					}
					assertTrue("Hash not matching", (doc.getHash().equalsIgnoreCase(expectedDoc.getHash())));

					if(doc.getSize() == null){
						doc.setSize("");
					}
					assertTrue("Size not matching", (doc.getSize().equalsIgnoreCase(expectedDoc.getSize())));

					assertNotNull("Response missing repositoryUniqueId is null", doc.getRepositoryUniqueId());
					assertTrue("RepositoryUniqueId not matching", (doc.getRepositoryUniqueId().equalsIgnoreCase(expectedDoc.getRepositoryUniqueId())));

					assertNotNull("Response missing classcode is null", doc.getClassCode());
					assertTrue("Classcode not matching", (doc.getClassCode().equalsIgnoreCase(expectedDoc.getClassCode())));

					assertNotNull("Response missing extrinsicObjectStatus is null", doc.getExtrinsicObjectStatus());
					assertTrue("ExtrinsicObjectStatus not matching", (doc.getExtrinsicObjectStatus().equalsIgnoreCase(expectedDoc.getExtrinsicObjectStatus())));
				}
			}
			assertTrue("Matching entry not found for this document in the expected data : " + doc.getDocumentId(), matchingDocFound);
		}
		assertNotNull("Response document list is null", qdNhinResponse.getDocumentList());
		assertTrue("Response documents and expected documents count did not match", qdNhinResponse.getDocumentList().size() == expectedData.getDocumentList().size());
	}
	
	private void printqdNhinResponse(QDNhinResponse qDNhinResponse) {
		if(qDNhinResponse == null) {
			System.out.println("No requests found.");
		}else{
			System.out.println("\n************ QDNhinResponse Parameters Start ******************");
			System.out.println("ResponderHCID : "+ qDNhinResponse.getResponderHCID());
			System.out.println("ResponderAAID : "+ qDNhinResponse.getResponderAAID());
			System.out.println("RelatesTo Id : "+ qDNhinResponse.getRelatesTo());
			System.out.println("ResponderPatientID : "+ qDNhinResponse.getResponderPatientID());
			for(DocumentInfo doc : qDNhinResponse.getDocumentList()){
				System.out.println("DocumentUniqueId : "+ doc.getDocumentId());
				System.out.println("RepositoryUniqueId : "+ doc.getRepositoryUniqueId());
				System.out.println("Doc_Hash : "+ doc.getHash());
				System.out.println("Doc_Size : "+ doc.getSize());
				System.out.println("ClassCode : "+ doc.getClassCode());
				System.out.println("ExtrinsicObjectStatus : "+ doc.getExtrinsicObjectStatus());
			}
			System.out.println("************** QDNhinResponse Parameters End ****************\n");
		}	
	}
	
	private void printExpectedValues(QDNhinResponse expectedData) {
		if(expectedData == null) {
			System.out.println("No requests found.");
		}else{
			System.out.println("\n************ Expected Parameters Start ******************");
			System.out.println("ResponderHCID : "+ expectedData.getResponderHCID());
			System.out.println("ResponderAAID : "+ expectedData.getResponderAAID());
			System.out.println("RelatesTo Id : "+ expectedData.getRelatesTo());
			System.out.println("ResponderPatientID : "+ expectedData.getResponderPatientID());
			for(DocumentInfo doc : expectedData.getDocumentList()){
				System.out.println("\n************ Document "+doc.getDocumentId()+" start ******************");
				System.out.println("DocumentUniqueId : "+ doc.getDocumentId());
				System.out.println("RepositoryUniqueId : "+ doc.getRepositoryUniqueId());
				System.out.println("Doc_Hash : "+ doc.getHash());
				System.out.println("Doc_Size : "+ doc.getSize());
				System.out.println("ClassCode : "+ doc.getClassCode());
				System.out.println("ExtrinsicObjectStatus : "+ doc.getExtrinsicObjectStatus());
				System.out.println("\n************ Document Info end ******************");
			}
			System.out.println("************** Expected Parameters End ****************\n");
		}	
	}
	
	/*private String getPatientId(String property){
		if(property == null){
			return null;
		}
		GatewayPropertyFactory factory = GatewayPropertyFactory.getInstance(); 
		String [] propertyArray = property.split("\\$");
		String prefix = factory.getProperty((propertyArray.length > 1)? propertyArray[1] : property);
		String patientId = (propertyArray.length > 1)? prefix + propertyArray[2] : property;
		return patientId;
	}
	
	private String getDocumentId(String property){
		if(property == null){
			return null;
		}
		GatewayPropertyFactory factory = GatewayPropertyFactory.getInstance(); 
		String [] propertyArray = property.split("\\$");
		String prefix = factory.getProperty((propertyArray.length > 1)? propertyArray[1] : property);
		String documentId = (propertyArray.length > 1)? prefix + propertyArray[2] : property;
		return documentId;
	}
	
	private String getHCID(String property) {
		if(property == null){
			return null;
		}
		GatewayPropertyFactory factory = GatewayPropertyFactory.getInstance(); 
		String HCID = factory.getProperty((property.startsWith("$") ? property.substring(1) : property));
		return HCID;		
	}*/
}