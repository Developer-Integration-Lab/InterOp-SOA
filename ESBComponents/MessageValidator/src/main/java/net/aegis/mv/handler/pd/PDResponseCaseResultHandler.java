package net.aegis.mv.handler.pd;

import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Patientcorrelation;
import net.aegis.lab.dao.pojo.Resultsummary;
import net.aegis.lab.manager.PatientcorrelationManager;
import net.aegis.lab.util.DateUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.handler.CaseResultHandler;
import net.aegis.mv.jaxb.msg.NhinMessage;
import net.aegis.mv.jaxb.pd.PDNhinResponse;
import net.aegis.mv.jaxb.pd.Patient;
import net.aegis.mv.jaxb.pd.Receiver;
import net.aegis.mv.jaxb.pd.Sender;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class PDResponseCaseResultHandler extends CaseResultHandler {
	private static final Log log = LogFactory.getLog(PDResponseCaseResultHandler.class);

	protected void updateSingleRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
	
		PDNhinResponse pDNhinResponse = (PDNhinResponse)nhinMessage;
		if (caseresult != null	&& (LabConstants.STATUS_PROGRESS.equals(caseresult.getResult()))) {
			Resultsummary matchedResultsummary = getResultSummaryByRelatesToSenderHCIDAndReceiverHCID(pDNhinResponse.getRelatesTo(), transaction.getSenderHCID(), transaction.getReceiverHCID());
			Integer processedRIs = (caseresult.getProcessedRIsCount() != null)? (caseresult.getProcessedRIsCount() + 1) : 1 ;
			String successCriteria = caseexecution.getSuccessCriteria();
			StringBuilder statusMessage= new StringBuilder();
			String status ="";
			if(matchedResultsummary!=null){

				if(transaction.isError()){
					status=LabConstants.STATUS_ERROR;
					statusMessage.append("[One RI Scenario] Patient Discovery - " + status +   " : Received error response.");
					// look for empty success criteria 
				}else if(StringUtils.isNotEmpty(successCriteria) && successCriteria.equalsIgnoreCase(LabConstants.SUCCESS_CRITERIA_EMPTY)){
					// handle empty success criteria
					if(!pDNhinResponse.isCorrelationFound()){
						status=LabConstants.STATUS_PASS;
						statusMessage.append("[One RI Scenario] Patient Discovery - " +  status  + " : Received empty response.");
					}else if(pDNhinResponse.isCorrelationFound()){
						status=LabConstants.STATUS_FAIL;
						statusMessage.append("[One RI Scenario] Patient Discovery - "  +  status  + " : Patient correlation found but not expected.");

					}else{
						status=LabConstants.STATUS_ERROR;
						statusMessage.append("[One RI Scenario] Patient Discovery - Unexpected Error:") ;
					}
					// normal flow	
				}else{
					if(pDNhinResponse.isCorrelationFound()){
						status=LabConstants.STATUS_PASS;
						statusMessage.append("[One RI Scenario] Patient Discovery - " +  status  + " : Patient correlation found");		    			
					}else{
						status=LabConstants.STATUS_FAIL;
						statusMessage.append("[One RI Scenario] Patient Discovery - " + status + " : No patient correlation found");		    				
					}
				}
				Patientcorrelation currentPatientcorrelation = preparePatientCorrelation(pDNhinResponse, caseresult, transaction, status);
				statusMessage.append("<br>" + currentPatientcorrelation.getMessage());

				// update both case result and summary
				updateCaseResultAndResultsummary(transaction, pDNhinResponse.getRelatesTo(), clientServerDTO.getParticipantGatewayInfo(), caseresult, 
						matchedResultsummary, status, statusMessage.toString(),processedRIs);
				// insert patientcorrelation		    		
				PatientcorrelationManager.getInstance().create(currentPatientcorrelation);

			}else{

				status=LabConstants.STATUS_FAIL;
				statusMessage.append("[One RI Scenario] Patient Discovery - " + status + " : No matching request found in resultsummary for  relatesTo " + pDNhinResponse.getRelatesTo() + " when response processed");
				updateCaseResult(clientServerDTO.getParticipantGatewayInfo(), caseresult, status,  
						statusMessage.toString());
			}
		}
	}
	
	protected void updateMultiRICaseResult(Caseexecution caseexecution,  Transaction transaction, 
			MsgTypeEnum msgTypeEnum, NhinMessage nhinMessage, ClientServerDTO clientServerDTO, Caseresult caseresult) {
	
		PDNhinResponse pDNhinResponse = (PDNhinResponse)nhinMessage;
		if (caseresult != null	&& (LabConstants.STATUS_PROGRESS.equals(caseresult.getResult()))) {
			StringBuilder statusMessage= new StringBuilder(); 
			Resultsummary matchedResultsummary = getResultSummaryByRelatesToSenderHCIDAndReceiverHCID(pDNhinResponse.getRelatesTo(),transaction.getSenderHCID(), transaction.getReceiverHCID());
			Integer processedRIs = (caseresult.getProcessedRIsCount() != null)? (caseresult.getProcessedRIsCount() + 1) : 1 ;
			// look for number of ri,s processed 
			int numberOfRIs = super.getNumberOfRIs(caseexecution.getParticipatingRIs());
			String status = LabConstants.STATUS_PROGRESS;

			//prepare correlation messages Start
			String patientcorrelationStatus = "";	

			StringBuilder previousPatientcorrelationMessage = new StringBuilder();
			List<String> previousPatientcorrelationStatusList = new ArrayList<String>();
			List<String> allPatientcorrelationStatusList = new ArrayList<String>();

			List<Patientcorrelation> retrievedPatientcorrelations = caseresult.getPatientcorrelations();
			if(retrievedPatientcorrelations!=null && retrievedPatientcorrelations.size() > 0){

				for(Patientcorrelation eachPatientcorrelation : retrievedPatientcorrelations){
					previousPatientcorrelationMessage.append(eachPatientcorrelation.getMessage());
					previousPatientcorrelationStatusList.add(eachPatientcorrelation.getStatus());
				}
			}
			//prepare correlation messages End

			if(matchedResultsummary!= null){

				if(processedRIs==numberOfRIs){

					if(transaction!=null && transaction.isError()){
						patientcorrelationStatus=LabConstants.STATUS_ERROR;
						//statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + patientcorrelationStatus + " : Error occured when processing " + (processedRIs==1? " First RI response " : (processedRIs) + " RI response " ) );
					}else if(pDNhinResponse.isCorrelationFound()){
						patientcorrelationStatus=LabConstants.STATUS_PASS;
						//statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + patientcorrelationStatus + " : Response received from " + (processedRIs ) + " RI's ");
					}else{
						patientcorrelationStatus=LabConstants.STATUS_FAIL;
						//statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + patientcorrelationStatus + " : No patient correlation happened when processing " + (processedRIs==1? " First RI response " : (processedRIs) + " RI response " ) );
					}
					// append current patientcorrelationStatus  and previous patientcorrelationStatus list for final validation
					allPatientcorrelationStatusList.addAll(previousPatientcorrelationStatusList);
					allPatientcorrelationStatusList.add(patientcorrelationStatus);

					if(allPatientcorrelationStatusList.contains(LabConstants.STATUS_ERROR)){
						status=LabConstants.STATUS_ERROR;
						statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + status + " : Received error from one or more RI's."  );
					}else if(allPatientcorrelationStatusList.contains(LabConstants.STATUS_FAIL)){
						status=LabConstants.STATUS_FAIL;
						statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + status + " : No patient correlation found in one or more RI's." );
					}else{
						status=LabConstants.STATUS_PASS;
						statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + status + " : Patient correlation found for " + (processedRIs ) + " RI's ");
					}

					Patientcorrelation currentPatientcorrelation = preparePatientCorrelation(pDNhinResponse, caseresult, transaction, patientcorrelationStatus);
					statusMessage.append("<br>" + previousPatientcorrelationMessage) ;
					statusMessage.append(currentPatientcorrelation.getMessage());

					updateCaseResultAndResultsummary(transaction, pDNhinResponse.getRelatesTo(), clientServerDTO.getParticipantGatewayInfo(), caseresult, 
							matchedResultsummary, status, statusMessage.toString(),processedRIs);
					//
					PatientcorrelationManager.getInstance().create(currentPatientcorrelation);

				}else{
					if(transaction!=null && transaction.isError()){
						patientcorrelationStatus=LabConstants.STATUS_ERROR;
						statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + status + " : Received error when processing " + (processedRIs==1? " First RI response " : (processedRIs) + " RI response " ) );
					}else if(pDNhinResponse.isCorrelationFound()){
						patientcorrelationStatus=LabConstants.STATUS_PASS;
						statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + status + " : Patient correlation found when processing " + (processedRIs==1? " First RI response " : (processedRIs) + " RI response " ) );
					}else{
						patientcorrelationStatus=LabConstants.STATUS_FAIL;
						statusMessage.append("[Multi RI Scenario] Patient Discovery - "  + status + " : No patient correlation found when processing " + (processedRIs==1? " First RI response " : (processedRIs) + " RI response " ) );
					}
					// create patient correlation from current PD response 
					Patientcorrelation currentPatientcorrelation = preparePatientCorrelation(pDNhinResponse, caseresult, transaction, patientcorrelationStatus);
					statusMessage.append("<br>" + previousPatientcorrelationMessage) ;
					statusMessage.append(currentPatientcorrelation.getMessage());

					updateCaseResultAndResultsummary(transaction, pDNhinResponse.getRelatesTo(), clientServerDTO.getParticipantGatewayInfo(), caseresult, 
							matchedResultsummary, status, statusMessage.toString(),processedRIs);
					// create patient correlation
					PatientcorrelationManager.getInstance().create(currentPatientcorrelation);

				}

			}else{
				statusMessage.append("[Multi RI Scenario] Patient Discovery - No matching request found in resultsummary for  repsonse relatesTo element " + pDNhinResponse.getRelatesTo() + "  when last/second RI response processed");
				log.info(statusMessage);
				status=LabConstants.STATUS_FAIL;
				updateCaseResult(clientServerDTO.getParticipantGatewayInfo(), caseresult, status,  statusMessage.toString() );
			}
		}
	}
	
	private Patientcorrelation preparePatientCorrelation(PDNhinResponse pDNhinResponse ,Caseresult caseresult , Transaction transaction, String status){
		
		Patientcorrelation patientcorrelation = new Patientcorrelation();
		patientcorrelation.setCaseresult(caseresult);
		
		Sender sender = pDNhinResponse.getSender();
		Receiver receiver = pDNhinResponse.getReceiver();
		if(sender!=null ){
			Patient patient = sender.getPatient();
			patientcorrelation.setCorrelatedPatientId((patient.getPatientId()!=null)? patient.getPatientId(): "");
			patientcorrelation.setCorrelatedPatientHomeCommunityId((patient.getHCID()!=null)? patient.getHCID(): "");
			patientcorrelation.setCorrelatedPatientAssignAuthId((patient.getAssigningAuthority()!=null)? patient.getAssigningAuthority(): "");
		}
		if(receiver!=null ){
			Patient patient = receiver.getPatient();
			patientcorrelation.setPatientId((patient.getPatientId()!=null)? patient.getPatientId(): "");
			patientcorrelation.setPatientHomeCommunityId((patient.getHCID()!=null)? patient.getHCID(): "");
			patientcorrelation.setPatientAssigningAuthorityId((patient.getAssigningAuthority()!=null)? patient.getAssigningAuthority(): "");
		}
		if(transaction!=null && transaction.isError()){
			patientcorrelation.setError(LabConstants.YES_INDICATOR);
		}else{
			patientcorrelation.setCorrelated(pDNhinResponse.isCorrelationFound()? LabConstants.YES_INDICATOR : LabConstants.NO_INDICATOR);
		}
		patientcorrelation.setCreatedAt(DateUtil.getTodayDateTime());
		patientcorrelation.setCreatedBy(LabConstants.MV_USER);		
		patientcorrelation.setStatus(status);
		patientcorrelation.setMessage(prepareCorrelatedMessageFromPatientcorrelation(patientcorrelation, transaction));
		return patientcorrelation;
	}
	
	private String prepareCorrelatedMessageFromPatientcorrelation(Patientcorrelation patientcorrelation,Transaction transaction){
		StringBuilder correlatedMessage = new StringBuilder();
		if(patientcorrelation!=null){
			
			if(transaction!=null && !transaction.isError()){
				String correlatedPatientAssignAuthId = patientcorrelation.getCorrelatedPatientAssignAuthId()!=null?patientcorrelation.getCorrelatedPatientAssignAuthId() : "";
				String correlatedPatientId = patientcorrelation.getCorrelatedPatientId()!=null?patientcorrelation.getCorrelatedPatientId() : "";
				correlatedMessage.append(" Correlated Patient Id: ");
				correlatedMessage.append(correlatedPatientId);
				correlatedMessage.append(" | Correlated Patient Assigning Authority Id: ");
				correlatedMessage.append(correlatedPatientAssignAuthId);
				correlatedMessage.append("<br>");
			}
		}
		return correlatedMessage.toString();
	}
}
