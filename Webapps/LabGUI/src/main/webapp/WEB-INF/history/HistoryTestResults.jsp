<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%
    response.addHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
    response.addHeader("Cache-Control","pre-check=0,post-check=0");
    response.setDateHeader("Expires",0);
%>
<%
    int iTheRoleOfTheLoggedInUser = -1;
    Participant objParticipantThatNhinRepWorksWith = null;
    String strParticipantThatNhinRepWorksWith = "";

    // Reference: See implementation of determineRoleForward() method in Login.java.
    iTheRoleOfTheLoggedInUser = ((User)session.getAttribute("userProfile")).getPrimaryRole();

    if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {   // avoid any NPE further.
        objParticipantThatNhinRepWorksWith = ((User)session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
        if(null == objParticipantThatNhinRepWorksWith) { strParticipantThatNhinRepWorksWith = "none"; }
        else { strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName(); }
    }

    User user = ((User)session.getAttribute("userProfile"));
    LabType labType = user.getLabType();
    if (labType == null) {
      //  labType = LabType.LAB;
        request.setAttribute("headerType",session.getAttribute("nhinrepHeaderType"));
    }else{
        int labMode = labType.getId();
        request.setAttribute("headerType",labType.getType(labMode));
    }
%>
<s:form action="HistoryTestResults" theme="simple">
    <s:hidden key="validateAction" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td>
                        <span class="content-title-text">
                            <% if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_PARTICIPANT) { %>
                            <s:property value="#session.userProfile.participant.participantName" /> - <%--History Test Results--%> Service Set History Results
                            <%} else if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {%>
                            <s:property value="#session.userProfile.nhinrep.name" /> - <%--History Test Results--%> Service Set History Results
                            <%}%>
                        </span>
                    </td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                               title="Refresh associated scenario(s)"
                               onclick="refreshAssociatedScenarios('<s:property value="executionUniqueId" />');" />
                    </td>
                </tr>
                <% if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {%>
                <tr>
                    <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                </tr>
                <%}%>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                Service Set Details
            </div>
<s:iterator value="serviceSetExecution" status="setExecutionStatus">    <%-- note - there will be only one in DB !! --%>
            <table class="content-data">
                <tr>
                    <td><label class="short-display">Execution Unique Identifier:</label></td>
                    <td><span class="long-display"><s:property value="executionUniqueId" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Submitted on:</label></td>
                    <td><span class="long-display"><s:property value="completedString" /></span></td>
                </tr>
                <% if(iTheRoleOfTheLoggedInUser != LabConstants.ROLE_PARTICIPANT){ %>
                <tr>
                    <td><label class="short-display">Type:</label></td>
                    <td><span class="long-display">
                            <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(serviceset.labAccessFilter)"/>
                        </span>
                    </td>
               </tr>
               <% } %>
            </table>
</s:iterator>
        </div>
        <div class="content-area">
            <div class="section-title">
                History <s:property value="#attr.headerType"/> Details
            </div>
            <table class="search-results">
                <tr class="search-results">
                   <th><s:property value="#attr.headerType"/></th>
                    <th>Results</th>
                    <th>Submitted</th>
                    <th>&nbsp;</th>
                    <th>Action</th>
                </tr>

<s:iterator value="scenarioexecutions" status="scenarioExecStatus">
                <tr class="<s:if test="#scenarioExecStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                    <td align="left" width="10%">
                        <a href="<%=request.getContextPath()%>/history/TestHistoryScenarioResults?scenarioExecutionId=<s:property value="scenarioExecutionId" />" title="<s:property value="scenario.description" />">
                            Scenario <s:property value="scenario.scenarioId" />
                        </a>
                    </td>
                    <td align="left" width="10%">
                        <s:property escape="false" value="resultsMeterHtml" />
                    </td>
                    <td align="left" width="15%">
                        <s:property value="completedString" />
                    </td>
                    <td width="5%">
                        <input type="button" class="inputButton" name="definition" id="definition" value="Definition"
                               title="<s:property value="scenario.description" />"
                               onclick="showTestCaseHelp('<s:property value="scenarioExecutionId" />');" />
                    </td>
                    <td width="10%" align="left">
<s:iterator value="caseexecutions" status="testStatus">
                       <s:if test="testcase.messageType == \"AD\"">
                            <input type="button" class="inputButton" name="show" id="show" value="Show Attachments"
                                   onclick="showAttachments('<s:property value="caseExecutionId" />', '<s:property value="testcase.name" />');" />
                        </s:if>
</s:iterator>
                    </td>
                </tr>
                <tr class="<s:if test="#scenarioExecStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                    <td colspan="1" align="left">
                        <div style="font-size:8pt;" id="validationCmnts_<s:property value="#scenarioExecStatus.index" />">
                            Validation comments: <%--<br /> <i>(limit to 255 chars)</i>--%>
                        </div>
                    </td>
                    <td colspan="4">
                        <s:textfield name="validateComments" style="width:99%;" maxlength="255"
                                     value="%{validationComments}" readonly="true" />
                    </td>
                </tr>
</s:iterator>

            </table>
        </div>

    </div>
</s:form>
<script type="text/javascript">
    var helpWindow;
    //
    // *********************************************************************
    // For every scenario, there is help function available.  Show it.
    // *********************************************************************
    function showTestCaseHelp(scenarioExecutionId) {
        var urlString = "<%=request.getContextPath()%>/history/TestCaseHelp?scenarioExecutionId=" + scenarioExecutionId;
        var stdOptions = "resizable=yes,directories=no,left=1,top=1,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        helpWindow = window.open(urlString, "testCaseHelpWindow" ,options);
        if (window.focus) {
            helpWindow.focus();
        }
    }

    // *********************************************************************
    // Get the list of attached documents.
    // *********************************************************************
    function showAttachments(pCaseExecutionId, pCaseName) {
        var urlString = "<%=request.getContextPath()%>/participant/ShowAttachments?selectedCaseExecutionId=" + pCaseExecutionId + "&selectedCaseName=" + pCaseName + "&enableDeleteButton=no";
        var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
        var options = stdOptions + ",width=900,height=450,scrollbars=yes";
        showAttachmentWindow = window.open(urlString, "showAttachmentWindow" ,options);
        if (window.focus) {
            showAttachmentWindow.focus();
        }
    }

    // *********************************************************************
    // Get the latest list of associated scenarios for this service set
    // execution.
    // *********************************************************************
    function refreshAssociatedScenarios(pExecutionUniqueId) {
        document.getElementById("HistoryTestResults").validateAction.value = "refresh";
        document.getElementById("HistoryTestResults").action = "<%=request.getContextPath()%>/history/HistoryTestResults?executionUniqueId=" + pExecutionUniqueId;
        document.getElementById("HistoryTestResults").submit();
    }
</script>