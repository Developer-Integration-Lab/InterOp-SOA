/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.md.ws.qd;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mastan.ketha
 */
public class TestCaseExecutionResultQD implements Serializable{

    List<String> actionMessagesList;
    Map<String, String> docsAndRepIds;
    String docIds;
    String classCodeList[]; //@bugfix ILT-117

    public String getDocIds() {
        return docIds;
    }

    public void setDocIds(String docIds) {
        this.docIds = docIds;
    }

    public List<String> getActionMessagesList() {
        return actionMessagesList;
    }

    public void setActionMessagesList(List<String> actionMessagesList) {
        this.actionMessagesList = actionMessagesList;
    }

    public Map<String, String> getDocsAndRepIds() {
        return docsAndRepIds;
    }

    public void setDocsAndRepIds(Map<String, String> docsAndRepIds) {
        this.docsAndRepIds = docsAndRepIds;
    }

    public String[] getClassCodeList() {
        return classCodeList;
    }

    public void setClassCodeList(String[] classCodeList) {
        this.classCodeList = classCodeList;
    }
}
