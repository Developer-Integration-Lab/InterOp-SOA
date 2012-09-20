package net.aegis.lab.web.action.participant;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.aegis.lab.connect.ConnectionCacheManager;
import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.ParticipantCnxVerificationStatus;
import net.aegis.lab.dao.pojo.Questionnaire;
import net.aegis.lab.dao.pojo.Questionnaireresponse;
import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.manager.QuestionnaireManager;
import net.aegis.lab.manager.QuestionnaireresponseManager;
import net.aegis.lab.manager.ServiceSetExecutionManager;
import net.aegis.lab.manager.ServiceSetManager;
import net.aegis.lab.manager.TestHarnessriManager;
import net.aegis.lab.manager.ValidationManager;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.LabConstants.LabType;
import net.aegis.lab.web.action.BaseAction;

import org.apache.commons.lang.StringUtils;

import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 *
 *
 * @author jyoti.devarakonda
 *
 * modified by jyoti.devarakonda @ 03/30/2011
 * added two methods getLabUddiEndPoints and populateUDDICandiateEndpoints
 */
public class SetUpTest extends BaseAction {
    public static final String DOT_AEGIS_DOT_NET = ".aegis.net";
    protected static final String LAB_CONNECTIVITY_PROBE_AEGISNET_INC = "Lab Connectivity Probe [AEGIS.net, Inc.]";
    //private Mapper mapper = (Mapper) ContextUtil.getLabContext().getBean("mapper");
    private static final long serialVersionUID = 1L;
    private Participant participant;
    private List<Servicesetexecution> mergedServiceSets = new ArrayList<Servicesetexecution>();
    private List<Servicesetexecution> needToBeActiveServiceSet = null;
    private List<Servicesetexecution> needToBeUpdatedServiceSetExec = null;
    // checkbox handling
    //private List<Boolean> serviceSetInd;
//    private List<Boolean> initiatorIndicator;
//    private List<Boolean> responderIndicator;
//    private List<String> ssnHandlingIndicator;
    private String initiatorIndSet = "false";
    private String responderIndSet = "false";
    private List<String> ri1Endpoints;
    private List<String> ri2Endpoints;
    private String patientDiscoveryEndPoint;
    private String queryForDocEndPoint;
    private String documentRetriveEndPoint;
    //private String[] serviceSetIndChkbox ;
    private String buttonName;
    private String status = "";
    private String errorMessage = "";
    private List<ParticipantCnxVerificationStatus> statusList;
    private String testUrl;
    private InputStream oStream;
    private String submitFlag = "";
    // temp only to store as hidden in jsp .
    private List<Integer> questionIdHidden= new ArrayList<Integer>();

    @Override
    public String execute() throws Exception {
        log.info("SetUpTest.execute() - INFO");
        try {

            if (this.getProfile() != null && this.getProfile().getParticipants() != null && this.getProfile().getParticipants().size() > 0) {
                setParticipant(this.getProfile().getParticipants().get(0));
                log.warn("SetUpTest.execute() - participant is " + participant.getParticipantName());
                needToBeActiveServiceSet = new ArrayList<Servicesetexecution>();
                ri1Endpoints = new ArrayList<String>();
                ri2Endpoints = new ArrayList<String>();
                Date currentTime = new Date();
                Long time = currentTime.getTime();
                statusList = new ArrayList<ParticipantCnxVerificationStatus>();

                if (participant.getIpAddress()!=null && participant.getIpAddress().endsWith(DOT_AEGIS_DOT_NET))
                    populateUDDICandiateEndpoints("2.16.840.1.113883.0.101");
                else
                    populateUDDICandiateEndpoints(participant.getCommunityId());
                

                  if (buttonName!=null && !"".equals(buttonName)) {
                    if ("Save & Submit".equals(buttonName) || "Save & Close".equals(buttonName)) {
                        log.info("The Save Changes button is clicked-->>");
                        log.info("The service set Ind is not null-->>");

                        // Load checkbox, radio button values first
                        //mergedServiceSets = (List<Servicesetexecution>) this.getSession().getAttribute("mergedServiceSets");
                        needToBeUpdatedServiceSetExec = new ArrayList<Servicesetexecution>();
                        for (Servicesetexecution servicesetexec : mergedServiceSets) {
                            log.info(">>>>>>>>The mergedServiceSets initiatorInd picked up from the session is " + servicesetexec.getInitiatorInd());
                            log.info(">>>>>>>>The mergedServiceSets responderInd picked up from the session is " + servicesetexec.getResponderInd());
                              servicesetexec.setParticipant(this.participant);

                            if (StringUtils.isNotEmpty(servicesetexec.getStatus()) && servicesetexec.getStatus().equals(LabConstants.STATUS_SERVICESETEXEC_ACTIVE)) {
                                needToBeUpdatedServiceSetExec.add(servicesetexec);
                            }
                        }
                        // load the mergedServiceSets with modified initiator and responder values
//                        this.loadMergedServiceSets();
                        for (Servicesetexecution servicesetexecution : mergedServiceSets) {
                            log.info("The servicesetInd:" + servicesetexecution.getServiceSetInd());
                            if(servicesetexecution.getServiceset().getSetId() == 4){
                            	servicesetexecution.setVersion(LabConstants.Connect_Version_32);
                            }else{
                            	servicesetexecution.setVersion(LabConstants.Connect_Version_248);
                            }
                            // Default value for all non-primitive instance variable is null
                            if (servicesetexecution.getServiceSetInd()!=null && servicesetexecution.getServiceSetInd()) {
                                needToBeActiveServiceSet.add(servicesetexecution);
                                log.info("****>>>>>****needToBeActiveServiceSet.add(mergedServiceSets.initiatorInd) is " + servicesetexecution);
                                log.info("****>>>>>****needToBeActiveServiceSet.add(mergedServiceSets.responderInd) is " + servicesetexecution);
                            }
                        }

                        if (needToBeUpdatedServiceSetExec.size() == 0) {
                            // If no active service set(s) found for this labType, need to check for any ACTIVE service sets across all labTypes
                            log.info("No active service set(s) found for this labType, now checking all labTypes.");
                            needToBeUpdatedServiceSetExec = ServiceSetExecutionManager.getInstance().participantActiveServiceSets(participant.getParticipantId());
                        }

                        if (needToBeUpdatedServiceSetExec.size() != 0) {
                            log.info("Now closing all active service set(s) found.");
                            if(submitFlag.equalsIgnoreCase("false")){
                                ServiceSetExecutionManager.getInstance().closeServiceSetExecution(needToBeUpdatedServiceSetExec);
                            }else{
                                ValidationManager.getInstance().submitServiceSetForValidation(needToBeUpdatedServiceSetExec);
                            }
                        }
                        ServiceSetExecutionManager.getInstance().startServiceSetExecution(needToBeActiveServiceSet);

                        /* begin clear test case properties */
                        List<Applicationproperties> apList = ApplicatiopropertiesManager.getInstance().getKeysLike(participant.getParticipantId()+LabConstants.SPLITTER_CARET+ExecuteTestCase.QDR_KEY);
                        ApplicatiopropertiesManager.getInstance().removeKeys(apList);
                        /* end clear test case properties */

                        addActionMessage("Set Up Test complete.");
                    }
                    else if ("Refresh".equals(buttonName)) {
                        log.info("The service set Ind is null");
                        // Get Active Service Sets merged with possible Service Sets
                        this.populateMergedServiceSets();
                    }else if (buttonName.startsWith("Verify.")) {
                        log.info("when probe button clicked --->>" + buttonName);
                        String actionType[] = buttonName.split(LabConstants.SPLITTER_PERIOD);
                        String requestType = actionType[1];
                        /*
                                    // this block removes the https hostname wrong exception
                                    com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                                        new com.sun.net.ssl.HostnameVerifier() {
                                            public boolean verify(String urlHostname, String
                                            certHostname) {
                                            return true;
                                            }
                                        }
                                    );
                        */
                        WebConversation wc = new WebConversation();
                        HttpUnitOptions.setScriptingEnabled(false);

                        ClientProperties cltprops = wc.getClientProperties();
                        cltprops.setUserAgent(LAB_CONNECTIVITY_PROBE_AEGISNET_INC);
                        cltprops.setAcceptGzip(false);
                        cltprops.setAutoRedirect(true);
                        cltprops.setAcceptCookies(true);

                        WebRequest  wreq=null;
                        WebResponse wresp  = null;

                        String wsdlUrl = "";
                          try {
                              wsdlUrl = getWsdl(requestType);
                              /* don't use this for now
                              if (testUrl!=null && !"".equals(testUrl))
                                  wsdlUrl = testUrl;
                               */
                              wreq = new GetMethodWebRequest(wsdlUrl);
                              wresp  = wc.sendRequest(wreq);
                          } catch (Exception ex) {
                              errorMessage = ex.toString();
                              System.out.println(" Verify WSDL live test Error>>>################################### Start");
                              ex.printStackTrace();
                              System.out.println("Verify WSDL live test Error>>>################################### End");
                              if (errorMessage!=null && !"".equals(errorMessage)) {
                                  int idx = errorMessage.indexOf(LabConstants.SPLITTER_COLON);
                                  if (idx>-1 && idx<errorMessage.length())
                                      errorMessage = errorMessage.substring(idx+1);
                              }
                          }

                        //-1 FOR non html: log.info("\ncontent length is :  " + wresp.getContentLength());

                        boolean cnxVerified = false;
                        if (wresp==null) {
                            errorMessage += "Response is null: "+wsdlUrl;
                        } else if (wresp.getText()!=null && !"".equals(wresp.getText())) {
                            if (wresp.getText().contains(getRequestToken(requestType)))
                                cnxVerified = true;
                            else
                                errorMessage += ("Search token was not found in the WSDL: "+wsdlUrl);
                            //log.info("\ncontent text is :  " + wresp.getText());
                        } else errorMessage += ("Response is empty: "+wsdlUrl);

                        if (actionType.length == 3 && "Wsdl".equals(actionType[2])) {
                            if (wresp!=null)
                                setoStream(new ByteArrayInputStream(wresp.getText().getBytes()));
                            else
                                setoStream(new ByteArrayInputStream(errorMessage.getBytes()));
                            return "showOutput";
                        }

                        String apKey = participant.getParticipantId()  + LabConstants.SPLITTER_CARET + requestType;
                        List<Applicationproperties> apList = ApplicatiopropertiesManager.getInstance().findByKey(apKey);
                        Applicationproperties ap = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("d MMM, yyyy hh:mm a");
                        String verifyState = LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_FAILED;
                        if (cnxVerified)
                            verifyState = LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_GOOD;
                        verifyState+=LabConstants.SPLITTER_CARET+(sdf.format(currentTime));

                        if (apList!=null && !apList.isEmpty()) {
                            ap = apList.get(0);
                            ap.setPropertyvalue(verifyState);
                        } else {
                            ap = new Applicationproperties();
                            ap.setPropertykey(apKey);
                        }
                        ap.setPropertyvalue(verifyState);
                        ap.setDescription(errorMessage);

                        ApplicatiopropertiesManager.getInstance().update(ap,null);


                    } else if (buttonName.startsWith("CoordinatorCheck")) {
//                        CoordinatorManager.getInstance().wakeUp(0);
                    }
                } //else {
                    log.info("The service set Ind is null and called when the link in the menu has clicked");
                    //if (serviceSetInd == null) {
                        // Get Active Service Sets merged with possible Service Sets
                        this.populateMergedServiceSets();
                    //}
               // }
              resultTransformer(statusList);
            }
        } catch (Throwable e) {
            log.error("Throwable", e);           
            return this.processException( e );
        }

        return SUCCESS;
    }

    protected String getRequestToken(String requestType) {
        try {
            return ApplicatiopropertiesManager.getInstance().getPropertyvalueByKey(requestType);
        } catch (Exception ex) {
            log.info(ex.toString());
        }
        return "";
    }
    

    protected String getWsdl(String requestType) {
        String wsdl = "";
          if (LabConstants.PATIENTDISCOVERY.equals(requestType))
              wsdl = this.getPatientDiscoveryEndPoint();
          else if (LabConstants.LAB_DOCQUERY.equals(requestType))
              wsdl = this.getQueryForDocEndPoint();
          else if (LabConstants.LAB_DOCRETRIEVE.equals(requestType))
              wsdl = this.getDocumentRetriveEndPoint();    
        return wsdl + "?wsdl";
    }

    /*
     * Venkat :  TODO : Need to clean up later if we have more than one active service set  ..
     */
    public void populateMergedServiceSets() throws ServiceException {
        List<Servicesetexecution> results = new ArrayList<Servicesetexecution>();

        LabType labType = this.getProfile().getLabType();
        List<Servicesetexecution> activeServiceSets = ServiceSetExecutionManager.getInstance().participantActiveServiceSets(participant.getParticipantId(), labType);

        boolean matched = false;
        List<Serviceset> serviceSets = ServiceSetManager.getInstance().getServicesets(labType);

        log.info("***serviceSets**" + serviceSets.size());
        log.info("populateMergedServiceSets***activeServiceSets.size()********** :" + activeServiceSets.size());
        if (serviceSets != null && serviceSets.size() > 0) {
//            if (initiatorIndicator == null) {
//                initiatorIndicator = new ArrayList<Boolean>();
//            }
//            if (responderIndicator == null) {
//                responderIndicator = new ArrayList<Boolean>();
//            }

            for (Serviceset serviceSet : serviceSets) {
                matched = false;
                // Attempt to find matching activeServiceSet to current serviceSet
                if (activeServiceSets != null && activeServiceSets.size() > 0) {
                    for (Servicesetexecution activeServiceSet : activeServiceSets) {
                        if (serviceSet.getSetId().intValue() == activeServiceSet.getServiceset().getSetId().intValue()) {
                            matched = true;
                            List<Questionnaireresponse> questionnaireresponseList = QuestionnaireresponseManager.getInstance().findBySetExecutionId(activeServiceSet.getSetExecutionId());
                            splitQuestionnaireExeList(questionnaireresponseList, activeServiceSet);
                            results.add(activeServiceSet);
//                            if (activeServiceSet.getInitiatorInd().equals("Y")) {
//                                initiatorIndicator.add(true);
//                                log.info("results.add(activeServiceSet)getInitiatorInd:true");
//                            } else if (activeServiceSet.getInitiatorInd().equals("N")) {
//                                initiatorIndicator.add(false);
//                                log.info("results.add(activeServiceSet)getInitiatorInd:false");
//                            }
//                            if (activeServiceSet.getResponderInd().equals("Y")) {
//                                responderIndicator.add(true);
//                                log.info("results.add(activeServiceSet)getResponderInd:true");
//                            } else if (activeServiceSet.getResponderInd().equals("N")) {
//                                responderIndicator.add(false);
//                                log.info("results.add(activeServiceSet)getResponderInd:false");
//                            }
                            log.info("***********results.add(activeServiceSet)1********** :");
                            break;
                        }
                    }
                }

                if (!matched) {
                    Servicesetexecution notActiveServiceSet = new Servicesetexecution();
                    notActiveServiceSet.setParticipant(participant);
                    notActiveServiceSet.setInitiatorInd(participant.getInitiatorInd());
                    notActiveServiceSet.setResponderInd(participant.getResponderInd());
                    notActiveServiceSet.setSsnHandlingInd(participant.getSsnHandlingInd());
                    notActiveServiceSet.setServiceset(serviceSet);
                    List<Questionnaire> questionnaireList = QuestionnaireManager.getInstance().findBySetId(serviceSet.getSetId());
                    prepareQuestionnaireExeListFromQuestionnaires(questionnaireList,notActiveServiceSet);
                    notActiveServiceSet.setStatus(Servicesetexecution.STATUS_NEW);
                    results.add(notActiveServiceSet);

//                    if (participant.getInitiatorInd().equals("Y")) {
//                        initiatorIndicator.add(true);
//                    } else if (participant.getInitiatorInd().equals("N")) {
//                        initiatorIndicator.add(false);
//                    }
//                    if (participant.getResponderInd().equals("Y")) {
//                        responderIndicator.add(true);
//                    } else if (participant.getResponderInd().equals("N")) {
//                        responderIndicator.add(false);
//                    }
                }
            }
        }
//        for (Boolean initiatorid : initiatorIndicator) {
//            log.info("The initiatorid: " + initiatorid);
//        }
//
//        for (Boolean responderid : responderIndicator) {
//            log.info("The responderid: " + responderid);
//        }
        this.setMergedServiceSets(results);

       // this.getSession().setAttribute("mergedServiceSets", mergedServiceSets);

    }
    /**
     *  Note : Map default values of questionnaire table column "value" to Questionnaireresponse table column "value"
     * @param questionnaireList
     * @param notActiveServiceSet
     */
    private void prepareQuestionnaireExeListFromQuestionnaires(List<Questionnaire> questionnaireList, Servicesetexecution notActiveServiceSet) {

        List<Questionnaireresponse> pdQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        List<Questionnaireresponse> qdQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        List<Questionnaireresponse> rdQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        List<Questionnaireresponse> cdaQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        Questionnaireresponse questionnaireresponse = null;
        for (Questionnaire questionnaire : questionnaireList) {
            questionnaireresponse = new Questionnaireresponse();
            questionnaireresponse.setQuestionnaire(questionnaire);
            // the only one colum "value" is changing when we store each question radio button value
            questionnaireresponse.setAnswer(questionnaire.getAnswer());
            if (questionnaire.isCDA()) {
                cdaQuestionnaireresponseList.add(questionnaireresponse);
            } else if (questionnaire.isPD()) {
                pdQuestionnaireresponseList.add(questionnaireresponse);
            } else if (questionnaire.isQD()) {
                qdQuestionnaireresponseList.add(questionnaireresponse);
            } else if (questionnaire.isRD()) {
                rdQuestionnaireresponseList.add(questionnaireresponse);
            }
        }
        notActiveServiceSet.setPdQuestionnaireresponses(pdQuestionnaireresponseList);
        notActiveServiceSet.setQdQuestionnaireresponses(qdQuestionnaireresponseList);
        notActiveServiceSet.setRdQuestionnaireresponses(rdQuestionnaireresponseList);
        notActiveServiceSet.setCdaQuestionnaireresponses(cdaQuestionnaireresponseList);
    }

    private void splitQuestionnaireExeList(List<Questionnaireresponse> questionnaireresponseList, Servicesetexecution activeServiceSet) {

        List<Questionnaireresponse> pdQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        List<Questionnaireresponse> qdQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        List<Questionnaireresponse> rdQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        List<Questionnaireresponse> cdaQuestionnaireresponseList = new ArrayList<Questionnaireresponse>();
        
        for (Questionnaireresponse eachQuestionnaireresponse : questionnaireresponseList) {
            Questionnaire  eachQuestionnaire = eachQuestionnaireresponse.getQuestionnaire();
            if (eachQuestionnaire.isCDA()) {
                cdaQuestionnaireresponseList.add(eachQuestionnaireresponse);
            } else if (eachQuestionnaire.isPD()) {
                pdQuestionnaireresponseList.add(eachQuestionnaireresponse);
            } else if (eachQuestionnaire.isQD()) {
                qdQuestionnaireresponseList.add(eachQuestionnaireresponse);
            } else if (eachQuestionnaire.isRD()) {
                rdQuestionnaireresponseList.add(eachQuestionnaireresponse);
            }
        }
        activeServiceSet.setPdQuestionnaireresponses(pdQuestionnaireresponseList);
        activeServiceSet.setQdQuestionnaireresponses(qdQuestionnaireresponseList);
        activeServiceSet.setRdQuestionnaireresponses(rdQuestionnaireresponseList);
        activeServiceSet.setCdaQuestionnaireresponses(cdaQuestionnaireresponseList);
    }

    private void StatusLink(List<Applicationproperties> apList, List<ParticipantCnxVerificationStatus> statusList, String wsdl) {
        if (apList != null && !apList.isEmpty()) {
            for (Applicationproperties ap : apList) {
                String[] valStr = ap.getPropertykey().split(LabConstants.SPLITTER);
                ParticipantCnxVerificationStatus cnxStatus = new ParticipantCnxVerificationStatus();
                cnxStatus.setRequestTypeCode(valStr[1]);
                valStr = ap.getPropertyvalue().split(LabConstants.SPLITTER);
                cnxStatus.setStatus(valStr[0]);
                cnxStatus.setDatetime(valStr[1]);
                cnxStatus.setErrorMessage(ap.getDescription());
                cnxStatus.setWsdl(wsdl);
                statusList.add(cnxStatus);
                return;
            }
        }
    }

    private String getFmtKey(Integer id, String requestType) {
        return "" + id + LabConstants.SPLITTER_CARET + requestType;
    }

    /*
     * method to get the UDDI participantEndpoints
     *
     */
    private String populateUDDICandiateEndpoints(String homeCommunityID) {
        log.info("<<<<<<<<<Entered in method populateUDDICandiateEndpoints>>>>>>" + homeCommunityID);
        try {

            List<String> pdEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(homeCommunityID, "PatientDiscovery");

            if (pdEndpointURLs != null && pdEndpointURLs.size() > 0) {
                for (String pdurl : pdEndpointURLs) {
                    //don't need as per original requirements in story 113. see 117. pdurl = pdurl + "?wsdl";
                    log.info("URLs are: >>>>>>>" + pdurl);
                    this.setPatientDiscoveryEndPoint(pdurl);
                    break;
                }
            }
            else{
                  this.setPatientDiscoveryEndPoint(null);
            }
            List<String> qdEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(homeCommunityID, "QueryForDocument");
            if (qdEndpointURLs != null && qdEndpointURLs.size() > 0) {
                for (String qdurl : qdEndpointURLs) {
                    log.info("URLs are: >>>>>>>" + qdurl);
                    this.setQueryForDocEndPoint(qdurl);
                    break;
                }
            }
            else{
                  this.setQueryForDocEndPoint(null);
            }
            List<String> rdEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(homeCommunityID, "RetrieveDocument");
            if (rdEndpointURLs != null && rdEndpointURLs.size() > 0) {
                for (String rdurl : rdEndpointURLs) {
                    log.info("URLs are: >>>>>>>" + rdurl);
                    this.setDocumentRetriveEndPoint(rdurl);
                    break;
                }
            }
            else{
                  this.setDocumentRetriveEndPoint(null);
            }

            log.info("<<<<<<<<<<<End of the method populateUDDICandiateEndpoints>>>>>>>>>>>>");

        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
        return null;
    }

    public String getLabUddiEndPoints() {
        log.info("<<<<<<<<<Entered in method getLabUddiEndPoints>>>>>>");
        try {

            String sHomeCommunityIDRI1 = "2.16.840.1.113883.0.101";
            String sHomeCommunityIDRI2 = "2.16.840.1.113883.0.102";
            String version = LabConstants.Connect_Version_248;
            List<String> rI1EndpointURLs = new ArrayList<String>();
            List<String> rI2EndpointURLs = new ArrayList<String>();
            TestHarnessriManager testHarnessriManager = TestHarnessriManager.getInstance();
            Map<String, String> riCommids = testHarnessriManager.getTestHarnessriCommIds();

            sHomeCommunityIDRI1 = riCommids.get("1");
            sHomeCommunityIDRI2 = riCommids.get("2");
            System.out.println(">>>>>>>>>>>>sHomeCommunityIDRI1" + sHomeCommunityIDRI1);
            System.out.println(">>>>>>>>>>>>sHomeCommunityIDRI1" + sHomeCommunityIDRI2);
            //PD
            List<String> rI1PDEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(sHomeCommunityIDRI1, "PatientDiscovery");
            List<String> rI2PDEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(sHomeCommunityIDRI2, "PatientDiscovery");
            //QD
            List<String> rI1QDEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(sHomeCommunityIDRI1, "QueryForDocument");
            List<String> rI2QDEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(sHomeCommunityIDRI2, "QueryForDocument");

            //RD
            List<String> rI1RDEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(sHomeCommunityIDRI1, "RetrieveDocument");
            List<String> rI2RDEndpointURLs = ConnectionCacheManager.getInstance().getUddiServiceEndPointsByServiceName(sHomeCommunityIDRI2, "RetrieveDocument");

            //PD,QD,RD for RI1
            rI1EndpointURLs.add(rI1PDEndpointURLs.get(0));
            rI1EndpointURLs.add(rI1QDEndpointURLs.get(0));
            rI1EndpointURLs.add(rI1RDEndpointURLs.get(0));

            //PD,QD,RD for RI2
            rI2EndpointURLs.add(rI2PDEndpointURLs.get(0));
            rI2EndpointURLs.add(rI2QDEndpointURLs.get(0));
            rI2EndpointURLs.add(rI2RDEndpointURLs.get(0));

            this.setRi1Endpoints(rI1EndpointURLs);
            this.setRi2Endpoints(rI2EndpointURLs);

        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);

        }
        log.info("<<<<<<<<<Exit in method getLabUddiEndPoints>>>>>>");
        return SUCCESS;
    }

    private void resultTransformer(List<ParticipantCnxVerificationStatus> statusList) {
        try {
            List<Applicationproperties> ap = ApplicatiopropertiesManager.getInstance().findByKey(getFmtKey(participant.getParticipantId(),LabConstants.PATIENTDISCOVERY));
            if (ap != null && !ap.isEmpty()) {
                StatusLink(ap, statusList, getPatientDiscoveryEndPoint());
            } else {
                statusList.add(new ParticipantCnxVerificationStatus(LabConstants.PATIENTDISCOVERY, LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_NOT,getPatientDiscoveryEndPoint()));
            }
            ap = ApplicatiopropertiesManager.getInstance().findByKey(getFmtKey(participant.getParticipantId(),LabConstants.LAB_DOCQUERY));
            if (ap != null && !ap.isEmpty()) {
                StatusLink(ap, statusList, getQueryForDocEndPoint());
            } else {
                statusList.add(new ParticipantCnxVerificationStatus(LabConstants.LAB_DOCQUERY, LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_NOT,getQueryForDocEndPoint()));
            }
            ap = ApplicatiopropertiesManager.getInstance().findByKey(getFmtKey(participant.getParticipantId(),LabConstants.LAB_DOCRETRIEVE));
            if (ap != null && !ap.isEmpty()) {
                StatusLink(ap, statusList, getDocumentRetriveEndPoint());
            } else {
                statusList.add(new ParticipantCnxVerificationStatus(LabConstants.LAB_DOCRETRIEVE, LabConstants.CONNECTIVITY_VERIFY_STATUS_VERIFIED_NOT, getDocumentRetriveEndPoint()));
            }        
        } catch (Exception ex) {
            log.info(ex.toString());
        }
        
    }
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Servicesetexecution> getMergedServiceSets() {
        return mergedServiceSets;
    }

    public void setMergedServiceSets(List<Servicesetexecution> mergedServiceSets) {
        this.mergedServiceSets = mergedServiceSets;
    }

//    public List<String> getSsnHandlingIndicator() {
//        return ssnHandlingIndicator;
//    }
//
//    public void setSsnHandlingIndicator(List<String> ssnHandlingIndicator) {
//        this.ssnHandlingIndicator = ssnHandlingIndicator;
//    }

//    public List getServiceSetInd() {
//        return serviceSetInd;
//    }
//
//    public void setServiceSetInd(List serviceSetInd) {
//        this.serviceSetInd = serviceSetInd;
//    }

//    public List<Boolean> getInitiatorIndicator() {
//        return initiatorIndicator;
//    }
//
//    public void setInitiatorIndicator(List<Boolean> initiatorIndicator) {
//        this.initiatorIndicator = initiatorIndicator;
//        for (Boolean initiatorid : initiatorIndicator) {
//            log.info("The initiatorid: " + initiatorid);
//        }
//    }
//
//    public List<Boolean> getResponderIndicator() {
//        return responderIndicator;
//    }
//
//    public void setResponderIndicator(List<Boolean> responderIndicator) {
//        this.responderIndicator = responderIndicator;
//    }

    public String getInitiatorIndSet() {
        return initiatorIndSet;
    }

    public void setInitiatorIndSet(String initiatorIndSet) {
        this.initiatorIndSet = initiatorIndSet;
    }

    public String getResponderIndSet() {
        return responderIndSet;
    }

    public void setResponderIndSet(String responderIndSet) {
        this.responderIndSet = responderIndSet;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getRi1Endpoints() {
        return ri1Endpoints;
    }

    public void setRi1Endpoints(List<String> ri1Endpoints) {
        this.ri1Endpoints = ri1Endpoints;
    }

    public List<String> getRi2Endpoints() {
        return ri2Endpoints;
    }

    public void setRi2Endpoints(List<String> ri2Endpoints) {
        this.ri2Endpoints = ri2Endpoints;
    }

    public String getDocumentRetriveEndPoint() {
        return documentRetriveEndPoint;
    }

    public void setDocumentRetriveEndPoint(String documentRetriveEndPoint) {
        this.documentRetriveEndPoint = documentRetriveEndPoint;
    }

    public String getPatientDiscoveryEndPoint() {
        return patientDiscoveryEndPoint;
    }

    public void setPatientDiscoveryEndPoint(String patientDiscoveryEndPoint) {
        this.patientDiscoveryEndPoint = patientDiscoveryEndPoint;
    }

    public String getQueryForDocEndPoint() {
        return queryForDocEndPoint;
    }

    public void setQueryForDocEndPoint(String queryForDocEndPoint) {
        this.queryForDocEndPoint = queryForDocEndPoint;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public List<ParticipantCnxVerificationStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<ParticipantCnxVerificationStatus> statusList) {
        this.statusList = statusList;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    public InputStream getoStream() {
        return oStream;
    }

    public void setoStream(InputStream oStream) {
        this.oStream = oStream;
    }
    
    public List<Integer> getQuestionIdHidden() {
        return questionIdHidden;
    }

    public void setQuestionIdHidden(List<Integer> questionIdHidden) {
        this.questionIdHidden = questionIdHidden;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }
}
