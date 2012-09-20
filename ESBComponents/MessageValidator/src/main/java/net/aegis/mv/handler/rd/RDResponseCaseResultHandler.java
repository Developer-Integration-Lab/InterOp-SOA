package net.aegis.mv.handler.rd;

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
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.CaseResultHandler;
import net.aegis.mv.jaxb.msg.NhinMessage;
import net.aegis.mv.jaxb.rd.RdNhinDocumentResponse;
import net.aegis.mv.jaxb.rd.RdNhinDocumentSetResponse;
import net.aegis.mv.util.DocumentTypeEnum;
import net.aegis.mv.util.MessageValidatorUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class RDResponseCaseResultHandler extends CaseResultHandler {
	private static final Log log = LogFactory.getLog(RDResponseCaseResultHandler.class);

	protected void updateSingleRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
		
		log.info("RDResponseCaseResultHandler| updateSingleRICaseResult() called");
		RdNhinDocumentSetResponse docsetResponse = (RdNhinDocumentSetResponse)nhinMessage;
		
		if(docsetResponse==null ){
			return;
		}
		String rIInfoMessage="[Single RI Scenario] ";
		updateCaseResultStatus(caseexecution, transaction, clientServerDTO, caseresult, docsetResponse,rIInfoMessage);
		
	}
	
	protected void updateMultiRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
		
		log.info("RDResponseCaseResultHandler| updateMultiRICaseResult() called");
		RdNhinDocumentSetResponse docsetResponse = (RdNhinDocumentSetResponse)nhinMessage;
		if(docsetResponse==null ){
			return;
		}
		String rIInfoMessage="[Multi RI Scenario] ";
		updateCaseResultStatus(caseexecution, transaction, clientServerDTO, caseresult, docsetResponse,rIInfoMessage);
		
	}
	
	
	
	private void updateCaseResultStatus(Caseexecution caseexecution,  Transaction transaction,ClientServerDTO clientServerDTO, 
			Caseresult caseresult,RdNhinDocumentSetResponse rdNhinDocumentSetResponse ,String rIInfoMessage){
		
		
		if (caseresult != null	&& (LabConstants.STATUS_PROGRESS.equals(caseresult.getResult()))) {
			
			String relatesTo = rdNhinDocumentSetResponse.getRelatesTo();
			Resultsummary matchedResultsummary = null;
			if(StringUtils.isNotEmpty(relatesTo)){
				matchedResultsummary = getResultSummaryByRelatesToSenderHCIDAndReceiverHCID(relatesTo, transaction.getSenderHCID(), transaction.getReceiverHCID());
			}
			
			StringBuilder caseresultStatusMessage= new StringBuilder();
			StringBuilder currentResultDocumentStatusMessage= new StringBuilder();
			String caseresultStatus = LabConstants.STATUS_PROGRESS;
			String currentResultDocumentStatus = null;
			List<String> expectedPatientDocuments = new ArrayList<String>(); 
			List<String> actualPatientDocuments = new ArrayList<String>();
			List<String> dbResultDocumentStatusList = new ArrayList<String>();
			List<String> allResultDocumentStatusList = new ArrayList<String>();
			List<String> dbPatientDocuments = new ArrayList<String>();
			int actualDocsSize = -1;
			
			if(caseexecution.getDocumentIds()!=null){
				expectedPatientDocuments = Arrays.asList(caseexecution.getDocumentIds().split(LabConstants.SPLITTER_DOUBLE_TILDE));
			}
			// look for dynamic documents configuration in participant myInfo page
			Participant participant = caseexecution.getScenarioexecution().getParticipant();
			String dynamicContentInd=null;
			if(participant!=null){
				dynamicContentInd= (participant.getDynamicContentInd() == null)?DocumentTypeEnum.STATIC_DOCUMENT.getDynamicDocInd():participant.getDynamicContentInd();
			}
			int expectedDocsSize = expectedPatientDocuments.size();
			if(matchedResultsummary!=null){
				// get resultdocuments from database and add current document to list
				// look whether you have received all documents(i.e number of document) including current documents or not
				// if received all documents , then update to success, else progress
				
				getRDResultDocumentsInfo(caseresult.getResultdocuments() , dbPatientDocuments , dbResultDocumentStatusList);
				actualPatientDocuments.addAll(dbPatientDocuments);
				allResultDocumentStatusList.addAll(dbResultDocumentStatusList);
				
				List<RdNhinDocumentResponse> responseSet = rdNhinDocumentSetResponse.getResponseSet();
				
				if(transaction!=null && transaction.isError()){
					currentResultDocumentStatus=LabConstants.STATUS_ERROR;
					currentResultDocumentStatusMessage.append("Retrieve Documents(RD) received with " + currentResultDocumentStatus);
					
				// if documents received , then update status to progress/pass
				}else if(rdNhinDocumentSetResponse.getResponseStatusType()!=null && 
						rdNhinDocumentSetResponse.getResponseStatusType().toUpperCase().contains(LabConstants.RESPONSE_SUCCESS) 
						&& responseSet!=null && responseSet.size() > 0){
					
					for(RdNhinDocumentResponse eachRdNhinDocumentResponse : responseSet){
						String documentUniqueId = eachRdNhinDocumentResponse.getDocumentUniqueId();
						actualPatientDocuments.add(documentUniqueId);
						
					/*
					 *   Initiator scenarios 
					 *  #1 : Lab knows the expected document IDs for each test case and we can validate on both " number of documents and documentId
					 *  Responder scenarios 
					 *  #1 : Lab knows the expected number of documents for each test case and we can validate on " number of documents "
					 */
					
					if(clientServerDTO.isInitiatorFlag()){
						currentResultDocumentStatus =LabConstants.STATUS_PASS ; 
						// responder scenarios
					}else{
						// fetch qd resultdocuments
						List<Resultdocument> qDResultDocuments = CaseResultManager.getInstance().getDocumentsForTestCase(participant.getParticipantId(), caseexecution.getDependentScenarioId(), caseexecution.getDependentCaseId());
						
						if(qDResultDocuments==null || qDResultDocuments.size()==0){
						 // create caseresult record with fail status saying that  " QD  should  executed successfully before executing RD "
							Caseresult caseResult = new Caseresult();
							caseResult.setCaseexecution(caseexecution);
							caseResult.setResult(LabConstants.STATUS_FAIL);
							caseResult.setMessage(LabConstants.RD_DEPENDENT_VALIDATION);
							caseResult.setCreatedAt(DateUtil.getTodayDateTime());
							caseResult.setCreatedBy(LabConstants.MV_USER);
							CaseResultManager.getInstance().saveCaseResult(caseResult);
							return;
						}	
						// for dynamic documents
							if(dynamicContentInd.equalsIgnoreCase(DocumentTypeEnum.DYNAMIC_DOCUMENT.getDynamicDocInd())){
								expectedDocsSize = 1 ; 
								currentResultDocumentStatus =LabConstants.STATUS_PASS ; 
							 // static documents
							}else{
								 currentResultDocumentStatus =LabConstants.STATUS_PASS ; 
								 Resultdocument matchedQDResultDocument = null;
								 for(Resultdocument eachResultdocument : qDResultDocuments){
									  if(eachResultdocument.getDocumentUniqueId().equalsIgnoreCase(documentUniqueId)){
										  matchedQDResultDocument= eachResultdocument;
										  break;
									  }
								 }
								 
								 if(matchedQDResultDocument!=null){
									 currentResultDocumentStatus = LabConstants.STATUS_PASS;
									 log.info("Retrieve Documents(RD) documentUniqueId is matched with one of Document Query(QD)  documentUniqueId.");
									// currentResultDocumentStatusMessage.append("Retrieve Documents(RD) documentUniqueId is matched with one of Document Query(QD)  documentUniqueId.");
								 }else{
									 currentResultDocumentStatus = LabConstants.STATUS_FAIL;
									 log.info("Retrieve Documents(RD) documentUniqueId is not matched with any one of Document Query(QD)  documentUniqueId.");
									 currentResultDocumentStatusMessage.append("Retrieve Documents(RD) documentUniqueId is not matched with any one of Document Query(QD)  documentUniqueId.");
								 }
								//validate hash value and size and store status and validation result message in resultdocument table
								String hashSizeValidationStatus = compareQDRDDocHashAndSize(eachRdNhinDocumentResponse.getDocument(), dynamicContentInd, currentResultDocumentStatusMessage, matchedQDResultDocument);
								currentResultDocumentStatus = hashSizeValidationStatus;
							}
							
						}
						allResultDocumentStatusList.add(currentResultDocumentStatus);
						// insert/update each document in to result document table with valadation status and message 
						updateResultDocument(eachRdNhinDocumentResponse, caseresult.getResultId(), clientServerDTO.getTransaction().getSenderHCID(), currentResultDocumentStatus, currentResultDocumentStatusMessage);
					
					}
				}else if(rdNhinDocumentSetResponse.getResponseStatusType()!=null && 
					rdNhinDocumentSetResponse.getResponseStatusType().toUpperCase().contains(LabConstants.RESPONSE_FAILURE)){
					currentResultDocumentStatus = LabConstants.STATUS_FAIL;
					currentResultDocumentStatusMessage.append("Retrieve Documents(RD) received with " + currentResultDocumentStatus);
					// if we received empty document/error , then mark ResultDocument:Status as Fail/Error
					createResultDocument(caseresult, clientServerDTO.getTransaction().getSenderHCID(), null, null, null, currentResultDocumentStatus, currentResultDocumentStatusMessage);
				}
				
				actualDocsSize = actualPatientDocuments.size();
				if(actualDocsSize== expectedDocsSize){
					if(allResultDocumentStatusList.contains(LabConstants.STATUS_ERROR)){
						caseresultStatus=LabConstants.STATUS_ERROR;
						caseresultStatusMessage.append(rIInfoMessage + "Retrieve Documents - "  + caseresultStatus + " : received error for one or more documents."  );
					}else if(allResultDocumentStatusList.contains(LabConstants.STATUS_FAIL)){
						caseresultStatus=LabConstants.STATUS_FAIL;
						caseresultStatusMessage.append(rIInfoMessage + "Retrieve Documents - "  + caseresultStatus + " : validation failed for one or more documents." );
					}else{
						caseresultStatus=LabConstants.STATUS_PASS;
						caseresultStatusMessage.append(rIInfoMessage + "Retrieve Documents - "  + caseresultStatus + " : all documents received.");
					}
				}
				else{
					// mark status as progress
					caseresultStatusMessage.append(rIInfoMessage + "Retrieve Documents - "  + caseresultStatus + " : documents received and waiting for other documents");
				}
				
				updateCaseResultAndResultsummary(transaction, relatesTo, clientServerDTO.getParticipantGatewayInfo(), caseresult, matchedResultsummary, caseresultStatus, caseresultStatusMessage.toString(), null);
			}else{
				caseresultStatus=LabConstants.STATUS_FAIL;
				caseresultStatusMessage.append("[One RI Scenario] Retrieve Documents - " + caseresultStatus + " : No matching request found in resultsummary for  relatesTo " + relatesTo + " when response processed");
				updateCaseResult( clientServerDTO.getParticipantGatewayInfo(), caseresult, caseresultStatus, caseresultStatusMessage.toString());
			}
		}
	}
	
	private void updateResultDocument(RdNhinDocumentResponse eachRdNhinDocumentResponse, int caseResultId, String documentSenderHCID, String status, StringBuilder message) {
		log.info("updateResultDocument : BEGIN");
//		int docSize = LabConstants.INVALID_SIZE;
		int docResultId = -1;

		try {
			CaseResultManager caseResultManager = CaseResultManager.getInstance();
			Caseresult caseResult = caseResultManager.getCaseResult(caseResultId);
			String documentUniqueId = eachRdNhinDocumentResponse.getDocumentUniqueId();
			String repositoryUniqueId = eachRdNhinDocumentResponse.getRepositoryId();
			String document = eachRdNhinDocumentResponse.getDocument();
			
			log.info("RD|DocumentUniqueId ==" + documentUniqueId);
			log.info("RD|repositoryUniqueId == " + repositoryUniqueId);
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
					resultDocument.setCommunityId(documentSenderHCID);
					resultDocument.setRepositoryId(repositoryUniqueId);
					resultDocument.setStatus(status);
					resultDocument.setMessage(message.toString());
					resultDocument.setMessageType(LabConstants.LAB_DOCRETRIEVE);
					resultDocument.setUpdatedAt(DateUtil.getTodayDateTime());
					resultDocument.setUpdatedBy(LabConstants.MV_USER);
					
					caseResultManager.updateResultDocument(resultDocument);
				}else{
					createResultDocument(caseResult, documentSenderHCID, documentUniqueId, repositoryUniqueId, document, status,message);
				}
			}
			if (docResultId < 0) {
				log.error("Error: updateResultDocument - ResultDocument not saved!!");
			}
				
			
			log.info("updateResultDocument : END");
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	private void createResultDocument(Caseresult caseresult , String documentSenderHCID , String documentUniqueId, String repositoryUniqueId , String document ,   String status , StringBuilder message ){
		Resultdocument resultDocument = new Resultdocument();
		resultDocument.setCaseresult(caseresult);
		resultDocument.setCommunityId(documentSenderHCID);
		resultDocument.setDocumentUniqueId(documentUniqueId);
		resultDocument.setMessage(message.toString());
		resultDocument.setMessageType(LabConstants.LAB_DOCRETRIEVE);
		resultDocument.setRawData(document.getBytes());
		resultDocument.setRepositoryId(repositoryUniqueId);
		resultDocument.setStatus(status);					
		resultDocument.setCreatedAt(DateUtil.getTodayDateTime());
		resultDocument.setCreatedBy(LabConstants.MV_USER);
		
		int docResultId = CaseResultManager.getInstance().saveResultDocument(resultDocument);
		if (docResultId < 0) {
			log.error("Error: updateResultDocument - ResultDocument not saved!!");
		}
	}
	
	private void getRDResultDocumentsInfo(List<Resultdocument> resultDocuments , List<String> dbPatientDocuments ,   List<String> dbResultDocumentStatusList){
	
		if(resultDocuments!=null && resultDocuments.size() > 0){
			for (Resultdocument resultDocument : resultDocuments) {
        		dbPatientDocuments.add(resultDocument.getDocumentUniqueId());
                log.info(" DocumentUniqueId :" + resultDocument.getDocumentUniqueId());
                log.info(" RepositoryId : "+  resultDocument.getRepositoryId());
                dbResultDocumentStatusList.add(resultDocument.getStatus());
	        	
	        }
		}
		
	}
	
	private String compareQDRDDocHashAndSize(String document, String dynamicContentInd, StringBuilder resultMessage, Resultdocument resultDoc) {
        log.info("compareQDRDDocHashAndSize.. Start ");
        String status = LabConstants.STATUS_FAIL;
        //  Integer dependentCaseId = caseExecution.getDependentCaseId();
        // Integer dependentScenarioId = caseExecution.getDependentScenarioId();
        // log.info("TestCaseExecutionHelperRD.compareQDRDDocHashAndSize.. The dependentCaseId and dependentScenarioId" + dependentCaseId + ".." + dependentScenarioId);
        // List<Resultdocument> resultDocs = CaseResultManager.getInstance().getResultDocumentsForCurrentCandidateScenarioCase(candidate, dependentScenarioId, dependentCaseId);
        int length = 0;
        String hashhex = null;
        // log.info("TestCaseExecutionHelperRD.compareQDRDDocHashAndSize.resultDocs.." + resultDocs.size());
        String classCode = resultDoc.getClassCode();
        // know if its a policy doc
        boolean isPolDoc = (StringUtils.isNotEmpty(classCode) && LabConstants.PRIVACYPOLICY_CLASSCODE_LIST.contains(classCode));
        log.info("Is policy doc" + isPolDoc);
        log.info("HashValueFromDatabase:" + resultDoc.getDocumentHash() + "DocSizeFromDatabase:" + resultDoc.getDocumentSize());
        if (document != null) {
            // dynamic document only
            if (!isPolDoc && dynamicContentInd.equalsIgnoreCase(DocumentTypeEnum.DYNAMIC_DOCUMENT.getDynamicDocInd())) {
                if (!resultDoc.getDocumentHash().equals(new Integer(LabConstants.DYNAMIC_HASH).toString())) {
                    log.info("dynamic doc hash not matched..");
                    resultMessage.append("DocumentUniqueId:" + resultDoc.getDocumentUniqueId() + ": docHash is not " + LabConstants.DYNAMIC_HASH);
                    return status;
                } else if (resultDoc.getDocumentSize() != LabConstants.DYNAMIC_SIZE) {
                    log.info("dynamic doc size not matched..");
                    resultMessage.append("DocumentUniqueId:" + resultDoc.getDocumentUniqueId() + ": docSize is not " + LabConstants.DYNAMIC_SIZE);
                    return status;
                } else if (resultDoc.getDocumentHash().equals(new Integer(LabConstants.DYNAMIC_HASH).toString()) && resultDoc.getDocumentSize() == LabConstants.DYNAMIC_SIZE) {
                    log.info("dynamic doc hash and size matched..");
                    return status = LabConstants.STATUS_PASS;
                }

            } else { //static or policy doc   
                //calculate hash
                hashhex = MessageValidatorUtil.getInstance().getSHAsum(document);
                //calculate size
                length = document.length();
                log.info("CalculatedHex: " + hashhex + "CalculatedLength:" + length);
                if (resultDoc.getDocumentHash().equals(hashhex) && resultDoc.getDocumentSize() == document.length()) {
                    log.info("The DocHex and Size values are matched with expected values");
                    return status = LabConstants.STATUS_PASS;
                } else if(!resultDoc.getDocumentHash().equals(hashhex) && resultDoc.getDocumentSize() == document.length()){
                	log.info("In valid document Hash  for document UniqueId=" + resultDoc.getDocumentUniqueId());
                    resultMessage.append("Calculated Hash value= "  + hashhex + " of RD does not match with expected hash value = "  + resultDoc.getDocumentHash() +  " of QD."   );
                    return status;
                }
                else if(resultDoc.getDocumentHash().equals(hashhex) && resultDoc.getDocumentSize() != document.length()){
                    log.info("In valid docSize for document UniqueId=" + resultDoc.getDocumentUniqueId() );
                    resultMessage.append("Calculated Size = "  + document.length() + " of RD does not match with expected size = " +  resultDoc.getDocumentSize() +  " of QD." );
                    return status;
                }
                else {
                	log.info("In valid docHash  and docSize for document UniqueId=" + resultDoc.getDocumentUniqueId());
                    resultMessage.append("In valid docHash  and docSize for document UniqueId:" + resultDoc.getDocumentUniqueId() + ".");
                    resultMessage.append("Calculated Hash value= "  + hashhex + " of RD does not match with expected hash value = "  + resultDoc.getDocumentHash() +  " of QD."   );
                    resultMessage.append("Calculated Size = "  + document.length() + " of RD does not match with expected size = " +  resultDoc.getDocumentSize() +  " of QD." );
                    return status;
                }
            }

        }
        log.info("compareQDRDDocHashAndSize.. End ");
        return status;
    }
	
	

		

}
