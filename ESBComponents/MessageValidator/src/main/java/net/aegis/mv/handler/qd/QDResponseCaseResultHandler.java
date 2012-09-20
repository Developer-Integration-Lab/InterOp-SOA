package net.aegis.mv.handler.qd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.manager.CaseResultManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.HexValidator;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.CaseResultHandler;
import net.aegis.mv.jaxb.msg.NhinMessage;
import net.aegis.mv.jaxb.qd.DocumentInfo;
import net.aegis.mv.jaxb.qd.QDNhinResponse;
import net.aegis.mv.util.DocumentTypeEnum;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class QDResponseCaseResultHandler extends CaseResultHandler {
	private static final Log log = LogFactory.getLog(QDResponseCaseResultHandler.class);

	protected void updateSingleRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
		
		QDNhinResponse qDNhinResponse = (QDNhinResponse)nhinMessage;
		
		if (caseresult != null	&& (LabConstants.STATUS_PROGRESS.equals(caseresult.getResult()))) {
			Resultsummary matchedResultsummary = getResultSummaryByRelatesToSenderHCIDAndReceiverHCID(qDNhinResponse.getRelatesTo(), transaction.getSenderHCID(), transaction.getReceiverHCID());
			if(matchedResultsummary!=null){
				String successCriteria = caseexecution.getSuccessCriteria();
				updateResponseFlag(matchedResultsummary, successCriteria, qDNhinResponse.getResponseStatusType());
				int docListSize = qDNhinResponse.getDocumentList().size();
				if(transaction.isError()){
					updateCaseResultAndResultsummary( transaction,  qDNhinResponse.getRelatesTo(), 
							clientServerDTO.getParticipantGatewayInfo(), caseresult, matchedResultsummary,LabConstants.STATUS_ERROR, 
							"[One RI Scenario] Query for Documents - " + LabConstants.STATUS_ERROR +" : Received a error response", 1);
				} else if(docListSize == 0){
					if(StringUtils.isNotEmpty(successCriteria) && successCriteria.equalsIgnoreCase(LabConstants.SUCCESS_CRITERIA_EMPTY)){					// look for empty success criteria 
						updateCaseResultAndResultsummary( transaction,  qDNhinResponse.getRelatesTo(), 
								clientServerDTO.getParticipantGatewayInfo(), caseresult, matchedResultsummary,LabConstants.STATUS_PASS, 
								"[One RI Scenario] Query for Documents - " + LabConstants.STATUS_PASS +" : Found "+ docListSize +" document(s)", 1);
					}else{
						// look for empty success criteria 
						String actualDocIds = getActualDocuments(caseexecution);
						updateCaseResultAndResultsummary( transaction,  qDNhinResponse.getRelatesTo(), 
								clientServerDTO.getParticipantGatewayInfo(), caseresult, matchedResultsummary,LabConstants.STATUS_FAIL, 
								"[One RI Scenario] Query for Documents - " + LabConstants.STATUS_FAIL +" : "+getQDErrorMessage(caseexecution, actualDocIds), 1);
						//updateCaseResult(caseexecution, transaction, msgTypeEnum,  clientServerDTO.getParticipantGatewayInfo(), caseresult, LabConstants.STATUS_FAIL, "[One RI Scenario] Query for Documents - Expected 0 documents but received "+ docListSize + " documents");
					}
				}else{
					if(qDNhinResponse.getResponseStatusType().equalsIgnoreCase(LabConstants.RESPONSE_SUCCESS) && docListSize > 0){
						if(qDNhinResponse.getResponderHCID() == null || "".equals(qDNhinResponse.getResponderHCID())){
							qDNhinResponse.setResponderHCID(clientServerDTO.getTransaction().getSenderHCID());
						}
						//Save the document id's to result document table for future references.
						saveQDResultDocument(qDNhinResponse.getDocumentList(), caseresult.getResultId(), qDNhinResponse.getResponderHCID(), null);

						// Compare the documents received with the expected documents. Ignore any policy documents received.
						String actualDocIds = getActualDocuments(caseexecution);
						boolean invalidHash = false;
						boolean result = compareQDDocuments(caseexecution, actualDocIds);
						String status = LabConstants.STATUS_PASS;
						String resultMsg = "[One RI Scenario] Query for Documents - " + status +" :  Found "+ docListSize +" document(s)";
						if(!result){
							status = LabConstants.STATUS_FAIL ;
							resultMsg = "[One RI Scenario] Query for Documents - " + status +" : "+getQDErrorMessage(caseexecution, actualDocIds);
						} else{
							/*
							 * 
							 *  Validate the hash and size values of the documents received in the response based on static or dynamic participant gateways.
							 *  This validation applies only for the responder scenarios since lab RI's doesn't support dynamic documents.
							 */
							if(caseexecution.getTestcase().isResponder()){
								invalidHash = validateHashAndSize(qDNhinResponse.getDocumentList(), caseexecution);
								if(invalidHash){
									status = LabConstants.STATUS_FAIL ;
									resultMsg = "[One RI Scenario] Query for Documents - " + status +" : Invalid document hash or size found in the response.";
								}
							}
						}
						if(caseexecution.getTestcase().isInitiator()){ 
							caseresult.setDocumentIds(actualDocIds);
						}else{
							caseresult.setLabDocumentIds(actualDocIds);
						}
						updateCaseResultAndResultsummary( transaction,  qDNhinResponse.getRelatesTo(), 
								clientServerDTO.getParticipantGatewayInfo(), caseresult, matchedResultsummary,status, resultMsg, 1);	    				
					}else{
						updateCaseResult( clientServerDTO.getParticipantGatewayInfo(), caseresult,  LabConstants.STATUS_FAIL, "[One RI Scenario] Query for Documents - " + LabConstants.STATUS_FAIL +" : Found 0 documents");
					}
				}
			}else{
				updateCaseResult( clientServerDTO.getParticipantGatewayInfo(), caseresult,  LabConstants.STATUS_FAIL, "[One RI Scenario] Query for Documents - No matching request found in resultsummary for this repsonse");
			}
		}
	}
	
	protected void updateMultiRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
		
		QDNhinResponse qDNhinResponse = (QDNhinResponse)nhinMessage;
		
		if (caseresult != null	&& (LabConstants.STATUS_PROGRESS.equals(caseresult.getResult()))) {
			String statusMessage=""; 
			Resultsummary matchedResultsummary = getResultSummaryByRelatesToSenderHCIDAndReceiverHCID(qDNhinResponse.getRelatesTo(), transaction.getSenderHCID(), transaction.getReceiverHCID());

			// look for number of ri's processed 
			int numberOfRIs = super.getNumberOfRIs(caseexecution.getParticipatingRIs());
			Integer processedRIs = (caseresult.getProcessedRIsCount() != null)? (caseresult.getProcessedRIsCount() + 1) : 1 ;
			String status = LabConstants.STATUS_PROGRESS;
			if(matchedResultsummary!=null){
				String successCriteria = getcaseExecutionSuccessCriteria(caseexecution, transaction);
				updateResponseFlag(matchedResultsummary, successCriteria, qDNhinResponse.getResponseStatusType());				
				int docListSize = qDNhinResponse.getDocumentList().size();				
//				if(transaction.isError()){
//					if(processedRIs == numberOfRIs){
//						updateCaseResultAndResultsummary(caseexecution, transaction, msgTypeEnum, qDNhinResponse.getRelatesTo(), 
//								clientServerDTO.getParticipantGatewayInfo(), caseresult, matchedResultsummary,LabConstants.STATUS_ERROR, 
//								"[Multi RI Scenario] Query for Documents - Received a error response from one or more RI's", processedRIs);
//					}
//				}else{
					if(docListSize > 0){
						//Save the document id's to result document table for future references.
						if(qDNhinResponse.getResponderHCID() == null || "".equals(qDNhinResponse.getResponderHCID())){
							qDNhinResponse.setResponderHCID(clientServerDTO.getTransaction().getSenderHCID());
						}
						saveQDResultDocument(qDNhinResponse.getDocumentList(), caseresult.getResultId(), qDNhinResponse.getResponderHCID(), null);
					}
					// Compare the documents received with the expected documents. Ignore any policy documents received.
					String actualDocIds = getActualDocuments(caseexecution);
					if(processedRIs == numberOfRIs){
						if(validateForErrorResponse(caseexecution)){
							status = LabConstants.STATUS_ERROR;
							statusMessage = "[Multi RI Scenario] Query for Documents - "  + status + " : Received a error response from one or more RI's";
						}else{
							boolean result = compareQDDocuments(caseexecution, actualDocIds);
							if(!result){
								status = LabConstants.STATUS_FAIL ;
								statusMessage = "[Multi RI Scenario] Query for Documents - "  + status + " : "+ getQDErrorMessage(caseexecution, actualDocIds);
							}else{
								status = LabConstants.STATUS_PASS;
								statusMessage = "[Multi RI Scenario] Query for Documents - "  + status + " : Received "+ caseexecution.getCurrentCaseresult().getResultdocuments().size() +" document(s)";
							}
						}
					}else{
						statusMessage="[Multi RI Scenario] Query for Documents - Response received from "+ processedRIs + " RI - In Progress";
					}				
					if(caseexecution.getTestcase().isInitiator()){ 
						caseresult.setDocumentIds(actualDocIds);
					}else{
						caseresult.setLabDocumentIds(actualDocIds);
					}						
					updateCaseResultAndResultsummary( transaction,  qDNhinResponse.getRelatesTo(), 
							clientServerDTO.getParticipantGatewayInfo(), caseresult, matchedResultsummary, status, statusMessage, processedRIs);	    				
			}else{
				updateCaseResult( clientServerDTO.getParticipantGatewayInfo(), caseresult,  LabConstants.STATUS_FAIL, "[Multi RI Scenario] Query for Documents - No matching request found in resultsummary for this repsonse");
			}
		}
	}	
	
	private void saveQDResultDocument(List<DocumentInfo> documents, int caseResultId, String remoteHCID, byte[] rawData) {
		log.info("TestCaseExecutionHelperQD - saveResultDocument : BEGIN");
		int docSize = LabConstants.INVALID_SIZE;
		int docResultId = -1;

		try {
			CaseResultManager caseResultManager = CaseResultManager.getInstance();
			Caseresult caseResult = caseResultManager.getCaseResult(caseResultId);
			if (documents != null && documents.size() > 0) {
				for(DocumentInfo eachDocument :  documents ) {
					String documentUniqueId = eachDocument.getDocumentId();
					String repositoryUniqueId = eachDocument.getRepositoryUniqueId();
					String hash = eachDocument.getHash();
					String classcode = eachDocument.getClassCode();
					docSize = extractNumValue(eachDocument.getSize(),docSize);
					log.info("The document Id is >>>>>>>" + documentUniqueId);
					log.info("The document repo Id is >>>>>>>" + repositoryUniqueId);
					log.info("The document size is >>>>>>>" + docSize);
					List<Resultdocument> resultDocs = caseResult.getResultdocuments();
					Resultdocument resultDocument = null ;
					boolean isDocAvailable = false;
					for(Resultdocument rsd : resultDocs){
						if(documentUniqueId.equalsIgnoreCase(rsd.getDocumentUniqueId())){
							isDocAvailable = true;
							resultDocument = rsd;
							break;
						}
					}
					if(documentUniqueId != null && !"".equals(documentUniqueId)){
						if(isDocAvailable){
							resultDocument.setDocumentUniqueId(documentUniqueId);
							resultDocument.setClassCode(classcode);
							resultDocument.setCommunityId(remoteHCID);
							resultDocument.setRepositoryId(repositoryUniqueId);
							resultDocument.setDocumentHash(hash);
							resultDocument.setDocumentSize(docSize);
							resultDocument.setUpdatedBy(LabConstants.MV_USER);
							resultDocument.setUpdatedAt(DateUtil.getTodayDateTime());
							caseResultManager.updateResultDocument(resultDocument);
						}else{
							docResultId = caseResultManager.saveResultDocument(caseResult, documentUniqueId, repositoryUniqueId, remoteHCID, rawData, classcode, hash, docSize,LabConstants.LAB_DOCQUERY, LabConstants.MV_USER);
						}
					}
					if (docResultId < 0) {
						log.error("Error: TestCaseExecutionHelperQD - saveResultDocument - ResultDocument not saved!!");
					}
				}
			}
			log.info("TestCaseExecutionHelperQD - saveResultDocument : END");
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	private int extractNumValue(String s, int defInt) {
		int intVal = 0;
		try {
			intVal = Integer.parseInt(s);
		} catch (Exception ex) {
			intVal = defInt;
		}
		return intVal;
	}
	
	private boolean compareQDDocuments(Caseexecution caseExec, String actualDocuments){
		boolean docMatch = false;
		String successCriteria = caseExec.getSuccessCriteria();
		List<String> expectedDocs = new ArrayList<String>(Arrays.asList(caseExec.getDocumentIds().split(LabConstants.SPLITTER_DOUBLE_TILDE)));
		List<String> actualDocsList = null;
		if("".equals(actualDocuments)){
			actualDocsList = new ArrayList<String>();
		}else{
			actualDocsList = new ArrayList<String>(Arrays.asList(actualDocuments.split(LabConstants.SPLITTER_DOUBLE_TILDE)));		
		}
		log.info("Expected document list : " + expectedDocs);
		log.info("Actual documents received :" + actualDocsList);
		if(caseExec.getTestcase().isInitiator()){ 
			//Initiator test cases
			if(StringUtils.isNotEmpty(successCriteria) && successCriteria.contains(LabConstants.SUCCESS_CRITERIA_EMPTY) && expectedDocs.contains(LabConstants.SUCCESS_CRITERIA_EMPTY)) {
				docMatch = validateEmptyCriteria(caseExec, actualDocsList, expectedDocs);
			} else if(actualDocsList.size() == expectedDocs.size()) {
				//Validate the the number of documents received with expected documents
				docMatch = true;
			}
		}else{	
			// Responder test cases		
			int expectedDocIdsNum = expectedDocs.size();        
			Participant participant = caseExec.getScenarioexecution().getParticipant();

			log.info("DocumentTypeEnum - DynamicDocInd **********: " + DocumentTypeEnum.DYNAMIC_DOCUMENT.getDynamicDocInd());
			log.info("DocumentTypeEnum - StaticDocInd ***********: " + DocumentTypeEnum.STATIC_DOCUMENT.getDynamicDocInd());
			log.info("DocumentTypeEnum - DynamicDocInd Id *******: " + DocumentTypeEnum.DYNAMIC_DOCUMENT.getId());
			log.info("Candidate - dynamicContentInd *************: " + participant.getDynamicContentInd());

			String dynamicContentInd = (participant.getDynamicContentInd() == null)?DocumentTypeEnum.STATIC_DOCUMENT.getDynamicDocInd():participant.getDynamicContentInd();
			
//			//Dynamic Document Test Simulation - Scenario 7219300
//			if(StringUtils.isNotEmpty(successCriteria) && successCriteria.equalsIgnoreCase("DYNAMIC_DOCUMENT_TEST")) {
//				if (caseExec.getScenarioexecution().getScenario().getScenarioId()==7219300) {//dynamic doc.
//					dynamicContentInd  = DocumentTypeEnum.DYNAMIC_DOCUMENT.getDynamicDocInd();
//				} else {
//					dynamicContentInd  = DocumentTypeEnum.STATIC_DOCUMENT.getDynamicDocInd();
//				}
//				participant.setDynamicContentInd(dynamicContentInd);
//			} 
			
			/*  DocumentTypeEnum DynamicDocInd is used to verify whether it's dynamic or static doc type
	            DocumentTypeEnum id is used to specify the number of documents received if it's dynamic (set to one currently)*/
			if(dynamicContentInd.equalsIgnoreCase(DocumentTypeEnum.DYNAMIC_DOCUMENT.getDynamicDocInd())){
				expectedDocIdsNum = (DocumentTypeEnum.DYNAMIC_DOCUMENT.getId()).intValue();
				if (actualDocsList.size() == expectedDocIdsNum) {
					docMatch = true;
				} 
			}else {
				if (actualDocsList.size() >= expectedDocIdsNum) {
					docMatch = true;
				} 
			}
		}
		return docMatch;
	}

	private boolean validateHashAndSize(List<DocumentInfo> docList, Caseexecution caseexecution ){
		String DEFERREDCREATION = "DeferredCreation";   
		boolean invalidDocProps = false;
		for(DocumentInfo doc : docList){

			//ILT-219
	//		// use cases:
	   //   Case 1: No dynamically generated docs, no policy doc
       //          Option 1 applies
       //   Case 2: Dynamically generated docs, no policy doc
       //           Option 2 applies
       //   Case 3: No dynamically generated docs, a policy doc
       //           Option 1 applies
       //   Case 4: Dynamically generated docs, a policy doc
       //           Option 2 applies to the dynamic docs
       //           Option 1 applies to the policy doc
		
			int docHash =  LabConstants.INVALID_HASH;
			String docHashStr = doc.getHash();
			log.info("docHashStr*****"+docHashStr);
			HexValidator hexValidator = new HexValidator();
			if (hexValidator.validate(docHashStr)) {
				docHash = LabConstants.VALID_HASH;
				log.info("Valid hash*****"+docHash);
			} else if (new Integer(LabConstants.DYNAMIC_HASH).toString().equals(docHashStr)) {
				docHash = LabConstants.DYNAMIC_HASH;
				log.info("Dynamic hash*****"+docHash);
			}
			int docSize = LabConstants.INVALID_SIZE;
			if (doc.getSize() == null || "".equals(doc.getSize())) {
				docSize = LabConstants.DYNAMIC_SIZE;
				log.info("Dynamic size*****"+docSize);
			} else {
				docSize = extractNumValue(doc.getSize(),docSize);
				log.info("Dynamic size*****"+docSize);
			}
			String docStatus = (doc.getExtrinsicObjectStatus());
			log.info("Dynamic size*****"+docStatus);

//			//Dynamic Document Test Simulation - Scenario 7219300
//			if (StringUtils.isNotEmpty(caseexecution.getSuccessCriteria()) && caseexecution.getSuccessCriteria().equalsIgnoreCase("DYNAMIC_DOCUMENT_TEST")) {
//				if (caseexecution.getScenarioexecution().getScenario().getScenarioId()==7219300) {//dynamic doc.
//					docStatus = DEFERREDCREATION;
//					log.info("Dynamic size*****"+docStatus);
//				}
//			} 

			boolean isPolDoc = (StringUtils.isNotEmpty(doc.getClassCode()) && LabConstants.PRIVACYPOLICY_CLASSCODE_LIST.contains(doc.getClassCode()));
			log.info("isPolDoc*****"+isPolDoc);

			//ILT-219
			//option1 :    Static or Dyanmic
			if (!invalidDocProps) {
				if ((docStatus!=null && !docStatus.endsWith(DEFERREDCREATION)) || isPolDoc) {
					log.info("docStatus***isPolDoc**"+docStatus+"******"+isPolDoc);
					if  (docHash==LabConstants.VALID_HASH && docSize>=LabConstants.VALID_SIZE) {
						log.info("static or dynamic size*****"+docHash+docSize);
						//	invalidDocProps=false;
					} else {
						log.info("Dynamic size*****In else ");
						invalidDocProps=true;
					}
				}

				//try option2 for Dynamic if option1 failed
				Participant participant = caseexecution.getScenarioexecution().getParticipant();
				if (!isPolDoc && (!invalidDocProps && participant.getDynamicContentInd().equalsIgnoreCase(DocumentTypeEnum.DYNAMIC_DOCUMENT.getDynamicDocInd()))) {
					if (docStatus.endsWith(DEFERREDCREATION)) {
						if ((docHash!=LabConstants.DYNAMIC_HASH)||(docSize!=LabConstants.DYNAMIC_SIZE)) {
							log.info("Dynamic hash***** "+docHash);
							log.info("Dynamic size***** "+docSize);
							invalidDocProps=true;
						}
					}
				}
			}		
		}
		return invalidDocProps;
	}
	
	private String getActualDocuments(Caseexecution caseExec){
		StringBuffer actualDocs = new StringBuffer();
		List<Resultdocument> resultDocList = caseExec.getCurrentCaseresult().getResultdocuments();
		int size = (resultDocList == null)? 0 : resultDocList.size();
		for(int i = 0; i < size; i++){
			Resultdocument docs = resultDocList.get(i);
			if(!LabConstants.PRIVACYPOLICY_CLASSCODE_LIST.contains(docs.getClassCode())){
				actualDocs.append(docs.getDocumentUniqueId());
				if(i < (size - 1)){
					actualDocs.append(LabConstants.SPLITTER_DOUBLE_TILDE);	
				}
			}			
		}		
		return actualDocs.toString();
	}
	
	private String getQDErrorMessage(Caseexecution caseExec, String actualDocuments){
		String resultMsg = "";
		List<String> expectedDocs = new ArrayList<String>(Arrays.asList(caseExec.getDocumentIds().split(LabConstants.SPLITTER_DOUBLE_TILDE)));
		List<String> actualDocsList = null;
		if("".equals(actualDocuments)){
			actualDocsList = new ArrayList<String>();
		}else{
			actualDocsList = new ArrayList<String>(Arrays.asList(actualDocuments.split(LabConstants.SPLITTER_DOUBLE_TILDE)));		
		}
		resultMsg = "Returned patient documents are less than expected: "+"Expected = "+expectedDocs.size()+"; Actual = "+actualDocsList.size();
		return resultMsg;
	}

}
