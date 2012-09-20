<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<%
    response.addHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
    response.addHeader("Cache-Control","pre-check=0,post-check=0");
    response.setDateHeader("Expires",0);

    int iNhinRepId = -999;
    Participant objParticipantThatNhinRepWorksWith = null;
    String strParticipantThatNhinRepWorksWith = "";

    iNhinRepId = ((User)session.getAttribute("userProfile")).getNhinrep().getNhinRepId();
    objParticipantThatNhinRepWorksWith = ((User)session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
    if(null == objParticipantThatNhinRepWorksWith) { strParticipantThatNhinRepWorksWith = "none"; }
    else { strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName(); }
%>

<s:form action="ActiveTestResults" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - <%=strParticipantThatNhinRepWorksWith%> - Active Test Results</span></td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                               onclick="refreshNhinrepActiveTestResults();"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                </tr>
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
                    <tr>
                        <td><label class="short-display">Type:</label></td>
                        <td><span class="long-display">
                              <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(serviceset.labAccessFilter)"/>
                            </span>
                        </td>
                    </tr>
                </table>
            </s:iterator>
        </div>         
        <div class="content-area">
            <div class="section-title">
                Active <s:property value="#attr.nhinrepHeaderType"/>
            </div>
            <s:if test="testScenarios.size() > 0">
            <table class="search-results" id="participant-scenario-results">
                <tr class="search-headers">
                    <th>&nbsp;</th>
                    <th><s:property value="#attr.nhinrepHeaderType"/></th>
                    <th>Progress</th>
                    <th>Results</th>
                    <th>Last Execution</th>
                    <th>&nbsp;</th>
                </tr>

<s:iterator value="testScenarios" status="testStatus">
                <tr class="<s:if test="#testStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                    <td width="2%">
                        <s:if test="scenario.initiatorInd == \"Y\"">
                            <img alt="Initiator" src="<%=request.getContextPath()%>/images/initiator-icon.gif" title="Initiator">
                        </s:if>
                        <s:else>
                            <img alt="Responder" src="<%=request.getContextPath()%>/images/responder-icon.gif" title="Responder">
                        </s:else>
                    </td>
                    <td align="left">
                        <a href="<%=request.getContextPath()%>/nhinrep/ActiveScenarioResults?scenarioExecutionId=<s:property value="scenarioExecutionId" />" title="<s:property value="scenario.description" />">
                            <s:property value="scenario.scenarioName" />
                        </a>
                    </td>
                    <td width="10%"><s:property escape="false" value="resultsMeterHtml" /></td>
                    <td align="left"><s:property value="resultsString" /></td>
                    <td align="left"><s:property value="lastExecutionString" /></td>
                    <td class="title-button" width="5%">
                        <input type="button" class="inputButton" name="definition" id="definition" value="Definition"
                               title="<s:property value="scenario.description" />"
                               onclick="showTestCaseHelp('<s:property value="scenarioExecutionId" />');" />
                    </td>
                </tr>
</s:iterator>
            </table>
            </s:if>
            <s:else>
                No Active Service Sets Selected.
            </s:else>
        </div>
    </div>
</s:form>
<script type="text/javascript">
        var helpWindow;
        function showTestCaseHelp(scenarioExecutionId) {
            var urlString = "<%=request.getContextPath()%>/help/TestCaseHelp?scenarioExecutionId=" + scenarioExecutionId;
            var stdOptions = "resizable=yes,directories=no,left=1,top=1,toolbar=no,location=no";
            var options = stdOptions + ",width=750,height=550,scrollbars=yes";
            helpWindow = window.open(urlString, "testCaseHelpWindow" ,options);
            if (window.focus) {
                helpWindow.focus();
            }
        }

        // *********************************************************************
        // Get the latest list of Active Test Results for this page.
        // *********************************************************************
        function refreshNhinrepActiveTestResults() {
            history.go(0);
            return;
        }
</script>