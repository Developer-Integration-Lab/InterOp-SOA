package net.aegis.mv.parser.rd;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.rd.RdNhinDocumentResponse;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetResponse;
import net.aegis.mv.jaxb.rd.RdNhinRegistryError;
import net.aegis.mv.mockdata.MockTestcaseDataProvider;
import net.aegis.mv.mockdata.TestScenario;
import net.aegis.mv.mockdata.TestcaseData;

import org.junit.Test;

public class RDResponseParserTest {
	private MockTestcaseDataProvider mockOjectFactory = MockTestcaseDataProvider.getInstance();
	
	@Test
	public void testWithValidResponse() throws Exception {
		
		//Prepare the test data.
		TestcaseData testcaseData = mockOjectFactory.getTestcaseDataByScenario(TestScenario.RD_SUCCESS_SCENARIO).get(0);
		Transaction transaction = testcaseData.getResponseTransaction();
		RdNhinDocumentSetResponse expectedData = (RdNhinDocumentSetResponse)testcaseData.getExpectedResponseMessage();
				
		//Process RD Response message.		
		RdNhinDocumentSetResponse docsetResponse = RDResponseParser.getInstance().parse(transaction);
				
		System.out.println("\n\n============START:Extracted request data for transaction ID: " + transaction.getId() + "========================");
		if(docsetResponse != null) {
			printDocsetResponse(docsetResponse);
		}
		System.out.println("============END: Extracted request data for transaction ID: " + transaction.getId() + "========================\n\n");
				
		assertNotNull("DocumentSetResponse object is null", docsetResponse);
		assertNotNull("Message ID associated with the Response is null. Transaction ID: " + transaction.getId(), docsetResponse.getMessageID());
		assertTrue("Message ID not matching.", (docsetResponse.getMessageID().equals(expectedData.getMessageID())));
		
		assertTrue("Response registry status is not Success", docsetResponse.getResponseStatusType().contains("Success"));		
		List<RdNhinDocumentResponse> responseList = docsetResponse.getResponseSet();
		assertTrue("Missing response", (responseList != null && responseList.size() == 1));
		
		RdNhinDocumentResponse response = responseList.get(0);
		RdNhinDocumentResponse responseExpected = expectedData.getResponseSet().get(0);
		assertNotNull("Missing HCID", response.getHomeCommunityId());
		assertTrue("HCID not matching", (response.getHomeCommunityId().equalsIgnoreCase(responseExpected.getHomeCommunityId())));		
		assertTrue("Repository not matching", response.getRepositoryId().equals(responseExpected.getRepositoryId()));		
		assertTrue("Document unique ID not matching", response.getDocumentUniqueId().equalsIgnoreCase(responseExpected.getDocumentUniqueId()));		
		assertTrue("Document is missing", response.getDocument() != null);
		assertTrue("Empty Document", (response.getDocument() != null && response.getDocument().length() > 0));
	}
	
	@Test
	public void testWithErrorResponse() throws Exception {
		
		//Prepare the test data.
		Transaction transaction = new Transaction();
		byte[] bMessage = readFile("/testdata/rd/RD2_Axolotl_ResponseMsg_RepoError.txt");		
		byte[] bMessageHeader = readFile("/testdata/rd/RD2_Axolotl_ResponseMsgHeader_RepoError.txt");
		
		transaction.setMessage(bMessage);
		transaction.setMessageHeader(bMessageHeader);
		transaction.setMessageType("RESPONSE");
		transaction.setContentType("multipart/related");
		transaction.setId(3);
				
		//Process RD Response message.		
		RdNhinDocumentSetResponse docsetResponse = RDResponseParser.getInstance().parse(transaction);
				
		System.out.println("\n\n============START:Extracted request data for transaction ID: " + transaction.getId() + "========================");
		if(docsetResponse != null) {
			printDocsetResponse(docsetResponse);
		}
		System.out.println("============END: Extracted request data for transaction ID: " + transaction.getId() + "========================\n\n");
				
		assertNotNull("DocumentSetResponse object is null", docsetResponse);
		assertNotNull("Message ID associated with the Response is null. Transaction ID: " + transaction.getId(), docsetResponse.getMessageID());
		assertTrue("Message ID not matching.", (docsetResponse.getMessageID().equals("urn:uuid:f88d5d61-a165-4d34-9d62-a810c02089a6")));
		
		assertTrue("Response registry status is expected as Failure", docsetResponse.getResponseStatusType().contains("Failure"));		
		List<RdNhinDocumentResponse> responseList = docsetResponse.getResponseSet();
		assertTrue("Document response is not expected", (responseList != null && responseList.size() == 0));
		
		List<RdNhinRegistryError> errors = docsetResponse.getRegistryErrors();
		assertTrue("Error list should contain 1 error", (errors != null && errors.size() == 1));
		
		RdNhinRegistryError error = errors.get(0);
		assertEquals("Error code is not matching", "XDSRepositoryError", error.getErrorCode());
		assertEquals("Code context is not matching", "Error while performing Retrieve Document Set-b operation for XDS Repository 2.16.840.1.113883.3.1125.1.2.5.12 at responding gateway urn:oid:2.16.840.1.113883.3.1125.1.2.3", error.getCodeContext());
		assertEquals("Location is not matching", "urn:oid:2.16.840.1.113883.3.1125.1.2.3", error.getLocation());
		assertEquals("Severity is not matching", "urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error", error.getSeverity());		
	}
	
	private byte[] readFile(String relativePath) throws Exception {
		BufferedInputStream bis   = null;
		try
		{	
			bis = new BufferedInputStream(this.getClass().getResourceAsStream(relativePath));	
			byte[] bMessage = new byte[100*1024];
			int i = bis.read(bMessage);
			return bMessage;
			//System.out.println("#" + new String(bMessage) + "#");		
		}finally{
			try {if(bis != null) { bis.close(); } }catch(Exception ex){}
		}
	}
	
	private void printDocsetResponse(RdNhinDocumentSetResponse docsetResponse) {
		System.out.println("MessageID         : " + docsetResponse.getMessageID());
		System.out.println("Relates To        : " + docsetResponse.getRelatesTo());
		System.out.println("Response status   : " + docsetResponse.getResponseStatusType());
		
		if(docsetResponse.getResponseStatusType().endsWith("Success")) {
			List<RdNhinDocumentResponse> responseSet = docsetResponse.getResponseSet();
			if(responseSet == null || responseSet.size() == 0) {
				System.out.println("No response found.");
			}else {
				for(RdNhinDocumentResponse response : responseSet) {
					System.out.println("Home Community ID : " + response.getHomeCommunityId());
					System.out.println("Repository ID     : " + response.getRepositoryId());
					System.out.println("Document Unique ID: " + response.getDocumentUniqueId());
					System.out.println("Document size     : " + response.getDocument().length());
					System.out.println("Mime type         : " + response.getMimeType());
				}
			}			
		}else if(docsetResponse.getResponseStatusType().endsWith("Failure")) {
			List<RdNhinRegistryError> registryErrors = docsetResponse.getRegistryErrors();
			if(registryErrors == null || registryErrors.size() == 0) {
				System.out.println("No errors found.");
			}
			for(RdNhinRegistryError registryError : registryErrors) {
				System.out.println("Error code        : " + registryError.getErrorCode());
				System.out.println("severity          : " + registryError.getSeverity());
				System.out.println("Code context      : " + registryError.getCodeContext());
				System.out.println("Location          : " + registryError.getLocation());
				System.out.println("Description       : " + registryError.getDescription());
			}	
		}
	}
}
