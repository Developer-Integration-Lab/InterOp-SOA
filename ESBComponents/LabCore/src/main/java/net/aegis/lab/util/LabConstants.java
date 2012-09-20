package net.aegis.lab.util;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ram.ghattu
 * Synopsis: This class is used to declare any constants for
 * Lab application.
 * Please mention/comment for which application the constants are used for and group them.
 *
 *
 */
public final class LabConstants {

    //
    // Declare constants for lab core application here
    //
    public static final String SPLITTER = "\\^";
    public static final String SPLITTER_QUOTE = "\\'";
    public static final String SPLITTER_AMPERSAND = "&";
    public static final String SPLITTER_CARET = "^";
    public static final String SPLITTER_PERIOD = "\\.";
    public static final String SPLITTER_DOUBLE_TILDE = "~~";
    public static final String SPLITTER_TRIPPLE_TILDE = "~~~";
    public static final String SPLITTER_DOUBLE_DOLLER = "\\$\\$";
    public static final String SPLITTER_TRIPPLE_CARET = "\\^\\^\\^";
    public static final String TRIPPLE_CARET = "^^^";
    public static final String SINGLE_QUOTE = "'";
    public static final String SPLITTER_EQUAL = "\\=";
    public static final String SPLITTER_COLON = ":";
    public static final String SPLITTER_COMMA = ",";
    public static final long TIMESLICE = 60000;
    public static final String PATIENTREQUEST = "PRQ";
    public static final String PATIENTRESPONSE = "PRS";
    public static final String PATIENTDISCOVERY = "PD";
    public static final String DOCQUERY = "110112";
    public static final String LAB_DOCQUERY = "QD";
    public static final String DOCRETRIEVE = "110107";
    public static final String DOCRETRIEVE1 = "110106";
    public static final String LAB_DOCRETRIEVE = "RD";
    public static final String STATUS_ACTIVE = "A";
    public static final String STATUS_PASS = "PASS";
    public static final String STATUS_MANUAL = "MANUAL";
    public static final String STATUS_FAIL = "FAIL";
    public static final String STATUS_ERROR = "ERROR";  
    public static final String STATUS_INITIATED = "INITIATED";
    public static final String STATUS_CLEARED = "CLEARED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_INCOMPLETE = "INCOMPLETE";
    public static final String SUCCESS_CRITERIA_EMPTY = "EMPTY";
    public static final String MESSAGE_TYPE = "Nhin";
    public static final String DOT_NOTATION = ".";
    public static final String LIKE_OPERATOR = "%";

    public static final Integer RI1_ID = 1;
    public static final Integer RI2_ID = 2;
    public static final Integer RI3_ID = 3;
    public static final Integer RI4_ID = 4;
    public static final String CURRENT_TIMESTAMP_ALIAS = "RI_Current_Time";
    public static final String CURRENT_TIMESTAMP_ON_RI_SRVR_SQL = "SELECT current_timestamp as " + LabConstants.CURRENT_TIMESTAMP_ALIAS;
    public static final String DATA_SOURCE_BEAN_FOR_AUDITREPO_ON_RI_SRVR = "dataSource";

    public static final String MV_USER ="mv_user";
    public static final String MD_USER ="md_user";
    
    // QD response message and test case processor  Start
    public static final String RESPONSE_SUCCESS = "SUCCESS";
    public static final String RESPONSE_FAILURE = "FAILURE";
    
   // QD response message and test case processor  Start
    //
    // Declare constants for lab (web) application here
    //

    // constants needed when logging on a user
    public static final Integer MAX_ALLOWED_INVALID_LOGIN_ATTEMPTS = 4;

    // constants needed when registering a participant (a.k.a. user)
    public static final Integer USER_DEFAULT_INVALID_ATTEMPTS = 0;
    public static final String CONNECTIVITY_VERIFY_STATUS_VERIFIED_GOOD = "VERIFIED-GOOD";
    public static final String CONNECTIVITY_VERIFY_STATUS_VERIFIED_NOT = "NOT VERIFIED";
    public static final String CONNECTIVITY_VERIFY_STATUS_VERIFIED_FAILED = "VERIFIED-FAILED";

    // constants needed when working with a participant
    public static final String PARTICIPANT_THAT_NHINREP_WORKS_WITH = "participantThatNhinRepWorksWith";

    // constants needed when dealing with scenario
    public static final String STATUS_SCENARIO_ACTIVE = "ACTIVE";
    public static final String STATUS_SCENARIO_DISABLED = "DISABLED";

    // constants needed when dealing with servicesetexecution
    public static final String STATUS_SERVICESETEXEC_ACTIVE = "ACTIVE";
    public static final String STATUS_SERVICESETEXEC_CLOSE = "CLOSED";
    public static final String STATUS_SERVICESETEXEC_SUBMITTED = "SUBMITTED";
    public static final String STATUS_SERVICESETEXEC_REVIEW = "REVIEW";
    public static final String STATUS_SERVICESETEXEC_PENDING = "PENDING";
    public static final String STATUS_SERVICESETEXEC_CERTIFIED = "CERTIFIED";

    // constants needed when dealing with validation
    public static final String STATUS_VALIDATION_CLOSED = "CLOSED";
    public static final String STATUS_VALIDATION_SUBMITTED = "SUBMITTED";
    public static final String STATUS_VALIDATION_REVIEW = "REVIEW";
    public static final String STATUS_VALIDATION_APPROVED = "APPROVED";
    public static final String STATUS_VALIDATION_DENIED = "DENIED";


    public static final String STATUS_PROGRESS = "PROGRESS";
    // for 2 RI scenario progress wait status is used
    public static final String STATUS_PROGRESS_WAIT = "PROGRESS_WAIT";
    public static final String STATUS_PROGRESS_INCOMPLETE = "PROGRESS_INCOMPLETE";
    public static final String STATUS_PROGRESS_FAIL_INCOMPLETE = "PROGRESS_FAIL_INCOMPLETE";
    public static final String INITIATOR_IND_Y = "Y";
    public static final String INITIATOR_IND_N = "N";
    public static final String STATUS_PROGRESS_PENDING = "PROGRESS_PENDING";
    public static final String RESPONDER_IND_Y = "Y";
    public static final String RESPONDER_IND_N = "N";
    public static final String SUBMISSION_IND_N = "N";

    // constants needed when choosing the role for a user
    // (For ex - we have user <=> participant as 1:1 relationship -- every participant is a user)
    public static final Integer ROLE_LAB_ADMIN = 1;
    public static final Integer ROLE_NHIN_ADMIN = 2;
    public static final Integer ROLE_NHIN_REP = 3;
    public static final Integer ROLE_NHIN_VALIDATING_BODY_REP = 4;
    public static final Integer ROLE_PARTICIPANT = 5;

    public static final String ROLENAME_LAB_ADMIN = "Lab Administrator";
    public static final String ROLENAME_NHIN_ADMIN = "NHIN Administrator";
    public static final String ROLENAME_NHIN_REP = "NHIN Representative";
    public static final String ROLENAME_NHIN_VALIDATING_BODY_REP = "NHIN Validating Body Representative";
    public static final String ROLENAME_PARTICIPANT = "Participant";

    // constants needed when dealing with scenario executions (viz. validation)
    public static final String STATUS_SCENARIOEXEC_NEW = "NEW";
    public static final String STATUS_SCENARIOEXEC_ACTIVE = "ACTIVE";
    public static final String STATUS_SCENARIOEXEC_SUBMITTED = "SUBMITTED";
    public static final String STATUS_SCENARIOEXEC_CLOSED = "CLOSED";
    public static final String STATUS_SCENARIOEXEC_PENDING = "PENDING";
    public static final String STATUS_SCENARIOEXEC_CERTIFIED = "CERTIFIED";
    public static final String STATUS_SCENARIOEXEC_VALIDATED = "VALIDATED";
    public static final String STATUS_SCENARIOEXEC_FAILED = "FAILED";
    public static final String MESSAGE_TYPE_INBOUND = "Nhin Inbound";
    public static final String MESSAGE_TYPE_OUTBOUND = "Nhin Outbound";
    public static final String TEST_CASE_PROCESSOR_ENDPOINT_URL_Key ="test.case.processor.endpoint.url";
    public static final String JMS_REQUEST_SUBMISSION_SUCESS_MSG = "The request has been successfully submitted for processing";
    public static final String JMS_REQUEST_SUBMISSION_FAILURE_MSG = "Request submission failed due to unexpected system error. Please contact the Administrator.";
    public static String Connect_Version_248 = "Connect_2.4.8";
    public static String Connect_Version_31 = "Connect_3.1";
    public static String Connect_Version_32 = "Connect_3.2";
    public static String Axolotl = "Axolotl";
    public static String Epic = "Epic";
    public static final String LAB_SHOW_ERROR_STACK_TRACE ="lab.showerrorstacktrace";
    // constants needed when dealing with patient (viz. validation)

     public static final String STATUS_PATIENT_ASSIGNED ="ASSIGNED";

     public static final String DOCUMENT_TYPE_POLICY ="POLICY";
     public static final String DOCUMENT_TYPE_PATIENT ="PATIENT";
     // ACP (policy ) class codes
     public static final List<String> PRIVACYPOLICY_CLASSCODE_LIST = Arrays.asList("57017-6" ,"57016-8") ;

     // constants dealing with hash and size of the document
      public static final int DYNAMIC_HASH = -1;
      public static final int DYNAMIC_SIZE = -1;
      public static final int INVALID_HASH = 0;
      public static final int INVALID_SIZE = 0;
      public static final int VALID_SIZE = 1;
      public static final int VALID_HASH = 1;
      public static final int COLONPROCESSORLEN = 5;
      public static final String REQUEST = "REQUEST";
      public static final String RESPONSE = "RESPONSE";
      public static final int LAB_NODE = 1;
      
      public static final String YES_INDICATOR = "Y";
      public static final String NO_INDICATOR = "N";
      public static final String LAB_INDICATOR_Y = YES_INDICATOR;
      public static final String LAB_INDICATOR_N = NO_INDICATOR;
      
      public static final String UI_TRANSACTION_DISPLAYLIMIT = "transaction.days";
      public static final String UI_TRANSACTION_DISPLAYMAX = "transaction.recordsperpage";
      public static final String DAY = "d";
      public static final String RD_DEPENDENT_VALIDATION="Dependent test case(Ex: Query for Documents-QD) should be executed successfully before executing current test case.";
      
    //
    // Declare constants for lab participant (Web Services) application here
    //

       //Application constants
    public static enum LabType {
        OPTION_ACTIVE_LAB_ONLY(-1){},
        OPTION_ALL(-2){},

        CONFORMANCE(1){
            @Override
            public String toString() {
                return "Conformance";
            }
        },
        LAB(2){
            @Override
            public String toString() {
                return "Lab";
            }
        };
        private int id;
        private  LabType(int id) {this.id = id;}
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public static String valueOf(int id) {
            if (id==CONFORMANCE.getId()) return "Conformance";
            if (id==LAB.getId())     return "Lab";
            return "";
        }
         public static String getType(int id) {
            if (id==CONFORMANCE.getId()) return "Test Group";
            if (id==LAB.getId())     return "Scenario";
            return "";
        }
        public static LabType fromTextId(int id) {
            for (LabType anEnum : values()) {
                if (anEnum.getId() == id) {
                    return anEnum;
                }
            }
            return null;
        }

    }
}
