<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<%
            response.addHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
            response.addHeader("Cache-Control", "pre-check=0,post-check=0");
            response.setDateHeader("Expires", 0);
%>
<s:form action="NhinvalidDashboard" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - Dashboard</span></td>
                    <td class="title-button">
                        <input type="submit" class="inputButton" name="refresh" id="refresh" value="Refresh"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                Service Sets Submitted for Validation
            </div>
            <s:if test="serviceSetExecutions.size() > 0">
                <table class="search-results">
                    <tr class="search-results">
                        <th>Type</th>
                        <th>Execution Unique Identifier</th>
                        <th>Current Status</th>
                        <th>Results</th>
                        <th>Submitted</th>
                        <th>&nbsp;</th>
                    </tr>
                    <s:iterator value="serviceSetExecutions" status="setExecutionStatus">
                        <tr class="<s:if test="#setExecutionStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                            <td>
                                <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(serviceset.labAccessFilter)"/>
                            </td>
                            <td>
                                <a href="<%=request.getContextPath()%>/nhinvalid/ValidateSubmittedTestResults?executionUniqueId=<s:property value="executionUniqueId" />" title="<s:property value="" />">
                                    <s:property value="executionUniqueId" />
                                </a>
                            </td>
                            <td><s:property value="status" /></td>
                            <td align="center"><s:property escape="false" value="resultsMeterHtml" /></td>
                            <td>
                                <s:property value="completedString" />
                            </td>
                            <td width="10%" align="left">
                                <input type="button" class="inputButton" name="attach" id="attach" value="Attach Document"
                                       onclick="attachDocument(<s:property value="setExecutionId" />,'<s:property value="executionUniqueId" />');" />
                                <input type="button" class="inputButton" name="show" id="show" value="Show Attachments"
                                       onclick="showAttachments(<s:property value="setExecutionId" />,'<s:property value="executionUniqueId" />');" />

                                <input type="button" class="inputButton" name="scenarioComments" id="execute" value="Display Comments <s:property value="" />"
                                       onclick="displayScenarioComments('<s:property value="executionUniqueId" />');" />

                            </td>
                        </tr>
                    </s:iterator>
                </table>
            </s:if>
            <s:else>
                No Submitted Service Sets Currently Assigned to this Validator.
            </s:else>
        </div>

    </div>
</s:form>
<script type="text/javascript">
    function displayScenarioComments(pExecutionUniqueId) {

        var urlString = "<%=request.getContextPath()%>/nhinvalid/DisplayComments?executionUniqueId=" + pExecutionUniqueId;
        var stdOptions = "resizable=yes,directories=no,left=80,top=80,toolbar=no,location=no";
        var options = stdOptions + ",width=1100,height=500,scrollbars=yes";
        displayCommentsWindow = window.open(urlString, "displayCommentsWindow" ,options);
        if (window.focus) {
            displayCommentsWindow.focus();
        }
    }

    var attachDocWindow;
    var showAttachmentWindow;
    function attachDocument(setExecutionId,executionUniqueId) {
        var urlString = "<%=request.getContextPath()%>/participant/AttachDocument?setExecutionId="+setExecutionId+"&executionUniqueId=" + executionUniqueId;
        var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        attachDocWindow = window.open(urlString, "attachDocumentWindow" ,options);
        if (window.focus) {
            attachDocWindow.focus();
        }
    }

    function showAttachments(setExecutionId,executionUniqueId) {
        var urlString = "<%=request.getContextPath()%>/participant/ShowAttachments?setExecutionId="+setExecutionId+"&executionUniqueId=" + executionUniqueId;
        var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
        var options = stdOptions + ",width=900,height=450,scrollbars=yes";
        showAttachmentWindow = window.open(urlString, "showAttachmentWindow" ,options);
        if (window.focus) {
            showAttachmentWindow.focus();
        }
    }

</script>