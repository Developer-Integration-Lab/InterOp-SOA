/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.dao.pojo.VwGateway;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.manager.ParticipantManager;
import net.aegis.lab.manager.ParticipantPatientMapManager;
import net.aegis.lab.manager.ResultsummaryManager;
import net.aegis.lab.manager.ScenarioExecutionManager;
import net.aegis.lab.manager.VwGatewayManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.labcommons.util.StringUtil;
import net.aegis.mv.dto.ClientServerDTO;
import net.aegis.mv.enums.MsgTypeEnum;
import net.aegis.mv.parser.ParserHelper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * Venkat.Keesara Feb 15, 2012
 */
public class MessageValidatorUtil {
	private static final Log log = LogFactory.getLog(MessageValidatorUtil.class);
	
	private static MessageValidatorUtil instance = new MessageValidatorUtil();

	private MessageValidatorUtil() {
	}

	public static MessageValidatorUtil getInstance() {
		return instance;
	}

	

	// check if one RI or two RIs
	public boolean isOneRI(int caseExecId) {
		log.info("<--- start isOneRI(int caseExecId)--->");
		String[] participatingRIs = null;
		boolean riFlag = true;
		try {
			ParticipantCaseExecutionManager caseExecutionManager = ParticipantCaseExecutionManager.getInstance();
			Caseexecution caseExecution = caseExecutionManager.getCaseExecutionById(caseExecId);
			participatingRIs = caseExecution.getParticipatingRIs().split(LabConstants.SPLITTER);
			if (participatingRIs != null && participatingRIs.length >= 2) {
				riFlag = false;
			}
		} catch (ServiceException ex) {
			log.error(ex.getMessage());
		}
		log.info("<--- End isOneRI(int caseExecId)--->");
		return riFlag;
	}
	
	/**
	 *  contains client, server gateway information 
	 * @param transaction
	 * @return
	 */
	public ClientServerDTO getClientServerDTO(Transaction transaction){
		String client = transaction.getClient();
		String server = transaction.getServer();
		VwGateway clientGatewayInfo= null;
		VwGateway serverGatewayInfo= null;
		VwGateway participantGatewayInfo = null;
		ClientServerDTO clientServerDTO = new ClientServerDTO();
		clientServerDTO.setTransaction(transaction);
		if(StringUtils.isNotEmpty(client) && StringUtils.isNotEmpty(server)){
			clientGatewayInfo = VwGatewayManager.getInstance().findByGatewayAddress(client);
			serverGatewayInfo = VwGatewayManager.getInstance().findByGatewayAddress(server);
			clientServerDTO.setClientGatewayInfo(clientGatewayInfo);
			clientServerDTO.setServerGatewayInfo(serverGatewayInfo);
			if(clientGatewayInfo!=null && serverGatewayInfo!=null){					
				if(clientGatewayInfo!=null && clientGatewayInfo.getLabNode()==LabConstants.LAB_NODE){
					participantGatewayInfo = serverGatewayInfo;

				}else{
					participantGatewayInfo = clientGatewayInfo;
					clientServerDTO.setInitiatorFlag(true);
				}
				clientServerDTO.setParticipantGatewayInfo(participantGatewayInfo);
			}

		}
		return clientServerDTO ; 
	}

	public Participant getParticipantInfo(Transaction transaction,VwGateway participantGatewayInfo){
		// get participant info 
		Participant participant =null;

		List<Participant> participantList = ParticipantManager.getInstance().getActiveCandiateByCommunityId(participantGatewayInfo.getHcid(), LabConstants.STATUS_ACTIVE);
		if (participantList != null && participantList.size() > 0 ) {
			participant = participantList.get(0);
			log.info("participant is --- >> " + participant.getParticipantId());
		}
		return participant ; 
	}
	
	public Caseexecution findCaseExecutionByParticipantAndPatientId(Transaction transaction , MsgTypeEnum msgTypeEnum ,ClientServerDTO clientServerDTO, String patientId, Boolean labPatientId){

		VwGateway participantGatewayInfo = clientServerDTO.getParticipantGatewayInfo(); 
		List<Participant> participantList = ParticipantManager.getInstance().getActiveCandiateByCommunityId(participantGatewayInfo.getHcid(), LabConstants.STATUS_ACTIVE);
		Participant participant =null;
		Caseexecution matchedCaseExecution =null;
		if (participantList != null && participantList.size() > 0) {
			participant = participantList.get(0);
			log.info("participant is --- >> " + participant.getParticipantId());

			// Get the all Scenario Executions
			ScenarioExecutionManager scenarioexecutionManager = ScenarioExecutionManager.getInstance();
			List<Scenarioexecution> scenarioExecList = scenarioexecutionManager.findByParticipantIdAndStatus(participant.getParticipantId(),	LabConstants.STATUS_SCENARIO_ACTIVE);
			
			List<Caseexecution> caseExecList = associateCaseExecToMessage(scenarioExecList, patientId, participant.getParticipantId(), msgTypeEnum.getMsgType(),  clientServerDTO,labPatientId);

			if(caseExecList !=null && caseExecList.size() >0){
				log.info("\n********************************\n");
				log.info("caseExecList.size() : "+ caseExecList.size() +" \n caseExecutionId :" +caseExecList.get(0).getCaseExecutionId());
				log.info("\n********************************\n");
				// TODO : scenario #30 request case execution needs to be found by userName field and response case execution can be found by relates field only
				
				matchedCaseExecution = caseExecList.get(0);
			}
		}
		return matchedCaseExecution ; 
	}  

	public Caseexecution findCaseExecutionByRelatesToAndReceiverHCID(String relatesTo, String senderHCID, String receiverHCID){
		return ResultsummaryManager.getInstance().findCaseExecutionByRelatesToAndReceiverHCID(relatesTo, senderHCID, receiverHCID);
	}
	
	/**
	 * 
	 * @param transaction
	 * @return
	 */
    public MsgTypeEnum findMessageType(Transaction transaction) {

		String contentType = transaction.getContentType();
		if(StringUtils.isEmpty(contentType)){
			return null;
		}
		
		Node envelopeNode = ParserHelper.getInstance().getEnvelopeNode(transaction); 
		if(envelopeNode == null) {
			System.out.println("Envelope node is null for " + transaction.getId());
		}		
		return (envelopeNode != null  
				? findMessageType(envelopeNode)
				: null);		
	}
	
	private MsgTypeEnum findMessageType(Node envelope) {
		//Get header and extract messageId and resource ID from it
		Node headerNode = ParserHelper.getInstance().getElementByTagNameDirect(envelope, MVConstants.HEADER);
		Element actionElement = ParserHelper.getInstance().getElementByTagNameDirect(headerNode, MVConstants.ACTION);
		
		return (actionElement != null 
				? MsgTypeEnum.getByActionUrn(actionElement.getTextContent())
				: null);
	}
	
	private List<Caseexecution> associateCaseExecToMessage(List<Scenarioexecution> scenarioExecList, String splitPatientId, Integer participantId,String messageType,ClientServerDTO clientServerDTO , Boolean labPatientId) throws ServiceException {
		String actualpatientId = null;

		Transaction transaction = clientServerDTO.getTransaction();
		if (labPatientId!=null && labPatientId.booleanValue() &&  StringUtils.isNotEmpty(splitPatientId) &&  splitPatientId.split(LabConstants.SPLITTER_PERIOD).length >= 2) {
			// :::: format may be :::: RI2.102.00008
			if (splitPatientId.split(LabConstants.SPLITTER_PERIOD).length == 3) {
				actualpatientId = splitPatientId.split(LabConstants.SPLITTER_PERIOD)[2];
			}else{
				//extract last 5 characters
				actualpatientId = StringUtil.getLastnCharacters(splitPatientId, 5);
			}
		}else{// extract actual patient ID
			ParticipantPatientMapManager participantPatientMapManager = ParticipantPatientMapManager.getInstance();
			actualpatientId = participantPatientMapManager.getPatientIdFromParticipantPatientMap(splitPatientId, participantId);
		}
		List<Caseexecution> foundCaseExecutions = new ArrayList<Caseexecution>();
		for (Scenarioexecution scenExec : scenarioExecList) {
			for (Caseexecution caseExec : scenExec.getCaseexecutions()) {
				if (caseExec != null && actualpatientId != null && caseExec.getStatus().equals(Caseexecution.STATUS_ACTIVE) && actualpatientId.equals(caseExec.getPatientId()) && messageType.equals(caseExec.getMessageType()) && caseExec.getBeginTime().before(transaction.getTime())) {
					foundCaseExecutions.add(caseExec);
				}
			}
		}
		return foundCaseExecutions;
	}
	
	public String trimPrefixForHomeCommunityId(String communityId) {
        // Set the Audit Source Id (community id)
        if (communityId != null) {
            log.debug("communityId prior to remove urn:oid" + communityId);
            if (communityId.startsWith("urn:oid:")) {
                communityId = communityId.substring(8);
            }
        }
        return communityId;
    }
	
	public String getSHAsum(String text) {
		MessageDigest md;
		byte[] sha1hash;
		try {
			md = MessageDigest.getInstance("SHA-1");
			sha1hash = new byte[40];
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
			sha1hash = md.digest();
			return convertToHex(sha1hash);
		} catch (NoSuchAlgorithmException e) {
			log.error("Error in getSHAsum() method\n" + e.toString());
		} catch (UnsupportedEncodingException e) {
			log.error("Error in getSHAsum() method\n" + e.toString());

		}
		return null;

	}
	
	private String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

}
