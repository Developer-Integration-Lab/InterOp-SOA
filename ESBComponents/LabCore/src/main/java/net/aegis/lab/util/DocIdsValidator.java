/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * This class is used by TestCaseExecutionHelperQD and TestCaseExecutionHelperRD in both LabGUI and LabParticipant to
 * validate the actual document ids received against the expected doc ids.  It sets the status and case result message
 * accordingly
 *
 * @author Tareq.Nabeel
 */
public class DocIdsValidator {

    public static String setStatusAndMessage(String status, List<String> expectedDocIdList, List<String> actualDocIdList, StringBuffer resultMessage) {
        if (!CollectionUtils.isEmpty(expectedDocIdList) && CollectionUtils.isEmpty(actualDocIdList)) {
            status = LabConstants.STATUS_FAIL;
            resultMessage.append("Failed - Returned documents are less than expected!");
        } else if (CollectionUtils.isEmpty(expectedDocIdList) && !CollectionUtils.isEmpty(actualDocIdList)) {
            status = LabConstants.STATUS_FAIL;
            resultMessage.append("Failed - Returned documents are more than expected!");
        } else {
            if (expectedDocIdList==null || actualDocIdList==null) {
                return status;
            } else {
                if (expectedDocIdList.size()==actualDocIdList.size() && expectedDocIdList.containsAll(actualDocIdList)) {
                    return status;
                } else {
                    if (actualDocIdList.size() < expectedDocIdList.size()) {
                        status = LabConstants.STATUS_FAIL;
                        resultMessage.append("Failed - Returned documents are less than expected!");
                    } else {
                        // We received more actual docs than expected.  That is not bad if the extra ones were Policy documents.
                        // We need to make sure though that the document ids match
                        List<String> clonedActualDocIdList = new ArrayList<String>();
                        clonedActualDocIdList.addAll(actualDocIdList);
                        clonedActualDocIdList.removeAll(expectedDocIdList);
                        StringBuilder extraUnexpectedDocIds = new StringBuilder();
                        for (String extraDocId : clonedActualDocIdList) {
                            // Ignore extra Policy documents that are returned
                            if (!extraDocId.endsWith("POLICY")) {
                                extraUnexpectedDocIds.append(extraDocId);
                            }
                        }
                        if (extraUnexpectedDocIds.length()>0) {
                            status = LabConstants.STATUS_FAIL;
                            resultMessage.append("Failed - Unexpected return document(s): ");
                            resultMessage.append(extraUnexpectedDocIds.toString());
                        }
                    }
                }
            }
        }
        return status;
    }
}
