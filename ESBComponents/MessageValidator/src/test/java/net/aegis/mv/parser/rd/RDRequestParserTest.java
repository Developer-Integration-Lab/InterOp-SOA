package net.aegis.mv.parser.rd;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.rd.RdNhinDocumentRequest;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetRequest;
import net.aegis.mv.mockdata.MockTestcaseDataProvider;
import net.aegis.mv.mockdata.TestScenario;
import net.aegis.mv.mockdata.TestcaseData;

import org.junit.Test;

public class RDRequestParserTest {
	private MockTestcaseDataProvider mockOjectFactory = MockTestcaseDataProvider.getInstance();
		
	@Test
	public void testRDRequestParser() throws Exception {
		
		//Prepare the test data.
		TestcaseData testcaseData = mockOjectFactory.getTestcaseDataByScenario(TestScenario.RD_SUCCESS_SCENARIO).get(0);
		Transaction transaction = testcaseData.getRequestTransaction();
		
		/*System.out.println("Receiver HCID: Expected 2.16.840.1.113883.0.101 <==> Actual: " + transaction.getReceiver().getHCID());
		System.out.println("Sender HCID: Expected 2.16.840.1.113883.0.213 <==> Actual: " + transaction.getSender().getHCID());
		System.out.println("Server: Expected d33rkgh1.aegis.net <==> Actual: " + transaction.getServer());
		System.out.println("Client: Expected d7z0s3q1.aegis.net <==> Actual: " + transaction.getClient());*/
		
		RdNhinDocumentSetRequest expectedData = (RdNhinDocumentSetRequest)testcaseData.getExpectedRequestMessage();
				
		//Process RD Request messages.		
		RdNhinDocumentSetRequest docsetRequest = RDRequestParser.getInstance().parse(transaction);
		/*
		System.out.println("\n\n============START:Extracted request data for transaction ID: " + transaction.getId() + "========================");
		if(docsetRequest != null) {
			printDocsetRequest(docsetRequest);
		}
		System.out.println("============END: Extracted request data for transaction ID: " + transaction.getId() + "========================\n\n");
		*/
		validateRDRequest(expectedData, docsetRequest);
	}
	
	private void validateRDRequest(RdNhinDocumentSetRequest expectedData, RdNhinDocumentSetRequest docsetRequest) {
		assertNotNull("DocumentSetRequest object is null", docsetRequest);
		assertEquals("Message ID associated with the Request is null.", expectedData.getMessageID(), docsetRequest.getMessageID());
		
		RdNhinDocumentRequest requestExpected = expectedData.getRequestSet().get(0);
		
		List<RdNhinDocumentRequest> requests = docsetRequest.getRequestSet();
		assertTrue("Missing request", (requests != null && requests.size() == 1));
		
		RdNhinDocumentRequest requestReceived = requests.get(0);
		
		assertEquals("HCID Not matching", requestExpected.getHomeCommunityId(), requestReceived.getHomeCommunityId());
		
		assertEquals("Repository not matching", requestExpected.getRepositoryId(), requestReceived.getRepositoryId());
		
		assertEquals("Document unique ID not matching", requestExpected.getDocumentUniqueId(), requestReceived.getDocumentUniqueId());
	}
		
	private void printDocsetRequest(RdNhinDocumentSetRequest docsetRequest) {
		System.out.println("MessageID: " + docsetRequest.getMessageID());
		
		List<RdNhinDocumentRequest> requests = docsetRequest.getRequestSet();
		if(requests == null || requests.size() == 0) {
			System.out.println("No requests found.");
		}
		
		for(RdNhinDocumentRequest request : requests) {
			System.out.println("Home Community ID: " + request.getHomeCommunityId());
			System.out.println("Repository ID: " + request.getRepositoryId());
			System.out.println("Document Unique ID: " + request.getDocumentUniqueId());
		}
	}

}
