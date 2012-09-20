/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Caseresultparameters;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Venkat.Keesara
 */
public class AuditMessageUtil {

    private static final Log log = LogFactory.getLog(AuditMessageUtil.class);
    private static final String DELIMITER = "-";
    public static final String REQUIRED_FLAG = "Y";
    public static final String OPTIONAL_FLAG = "N";

    // get lab message type from audit message

   

    public static boolean isLabPatientDiscovery(String messageType) {
        boolean isMatch = false;
        if (StringUtils.isNotEmpty(messageType)) {
            isMatch = messageType.equalsIgnoreCase(LabConstants.PATIENTDISCOVERY);
        }
        return isMatch;
    }

    public static boolean isLabDocQuery(String messageType) {
        boolean isMatch = false;
        if (StringUtils.isNotEmpty(messageType)) {
            isMatch = messageType.equalsIgnoreCase(LabConstants.LAB_DOCQUERY);
        }
        return isMatch;
    }

    public static boolean isLabDocRetrieve(String messageType) {
        boolean isMatch = false;
        if (StringUtils.isNotEmpty(messageType)) {
            isMatch = messageType.equalsIgnoreCase(LabConstants.LAB_DOCRETRIEVE);
        }
        return isMatch;
    }

    

    public static void addCaseResultParameterToList(List<Caseresultparameters> caseresultparametersList, String prefix, String suffix, String Value, Integer caseResultId, String required) {

        if (StringUtils.isNotEmpty(Value)) {
            caseresultparametersList.add(getCaseResultParameter(prefix, suffix, Value, caseResultId, required));
        }

    }

    public static Caseresultparameters getCaseResultParameter(String prefix, String suffix, String value, Integer caseResultId, java.lang.String required) {
        Caseresultparameters caseResultParams = new Caseresultparameters();
        Caseresult caseResult = new Caseresult();
        caseResult.setResultId(caseResultId);
        caseResultParams.setCaseresult(caseResult);
        if (StringUtils.isNotEmpty(prefix) && StringUtils.isNotEmpty(suffix)) {
            caseResultParams.setParamName(prefix + DELIMITER + suffix);
        } else if (StringUtils.isNotEmpty(prefix) && StringUtils.isEmpty(suffix)) {
            caseResultParams.setParamName(prefix);
        } else if (StringUtils.isNotEmpty(suffix) && StringUtils.isEmpty(prefix)) {
            caseResultParams.setParamName(suffix);
        } else {
            caseResultParams.setParamName("");
        }
        caseResultParams.setDisplayParamName(caseResultParams.getParamName());
        caseResultParams.setParamValue(value);
        caseResultParams.setRequired(required);
        return caseResultParams;

    }

    /**
     * Venkat Keesara
     * @param processedAuditRepoList
     * @param currentAuditRepo
     * @return
     */
     public static boolean   isAuditRepositoryMessageProcessedForRD(List<Auditrepository> processedAuditRepoList, Auditrepository currentAuditRepo) {

        boolean isMessageProcessed = false;

        for (Auditrepository eachtAuditRepo : processedAuditRepoList) {
            String passedMessageType = currentAuditRepo.getMessageType();
            if (StringUtils.isNotEmpty(passedMessageType) && StringUtils.isNotEmpty(eachtAuditRepo.getMessageType()) && passedMessageType.equals(eachtAuditRepo.getMessageType())) {
                isMessageProcessed = true;
                break;
            }
        }
        return isMessageProcessed;
    }

    public static String getContents(File aFile) {
        //...checks on aFile are elided
        StringBuilder contents = new StringBuilder();

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; //not declared within while loop
        /*
                 * readLine is a bit quirky :
                 * it returns the content of a line MINUS the newline.
                 * it returns null only for the END of the stream.
                 * it returns an empty String if two newlines appear in a row.
                 */
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }

    public static String getPatientIdFromCompositePatientId(String compositPatientId) {
        String sValue = null;
        if(compositPatientId!=null && compositPatientId.contains("^^^&"))
        {
            sValue = compositPatientId.substring(0,compositPatientId.indexOf("^^^&"));
        }
        return sValue;
    }

    public static String getContentsFromInputStream(InputStream is) {
        //...checks on aFile are elided
        StringBuilder contents = new StringBuilder();

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader input = new BufferedReader(reader);
            try {
                String line = null; //not declared within while loop
        /*
                 * readLine is a bit quirky :
                 * it returns the content of a line MINUS the newline.
                 * it returns null only for the END of the stream.
                 * it returns an empty String if two newlines appear in a row.
                 */
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }
}
