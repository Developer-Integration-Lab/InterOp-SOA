<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%
    User user = ((User)session.getAttribute("userProfile"));
    LabType labType = user.getLabType();
    if (labType == null) {
        labType = LabType.LAB;
    }
    int labMode = labType.getId();
    request.setAttribute("labType", labType);
    request.setAttribute("headerType",labType.getType(labMode));
%>
<s:form action="ActiveTestResults" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Active Test Results</span></td>
                    <td class="title-button"><input type="submit" class="inputButton" name="refresh" id="refresh" value="Refresh"/></td>
                </tr>
            </table>
        </div>

    <div class="content-area">
            <div class="section-title">
                Active <s:property value="#attr.headerType"/>
            </div>
            <s:if test="testScenarios.size() > 0">
            <table class="search-results" id="participant-scenario-results">
                <tr class="search-headers">
                    <th>&nbsp;</th>
                    <th><s:property value="#attr.headerType"/></th>
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
                    	 <a href="<%=request.getContextPath()%>/participant/ActiveScenarioResults?scenarioExecutionId=<s:property value="scenarioExecutionId" />" title="<s:property value="scenario.description" />">
                                <s:property value="scenario.scenarioName" />
                         </a>
                       <%--  <s:if test="@net.aegis.lab.util.LabConstants$LabType@CONFORMANCE.equals(#attr.labType)">
                           <a href="<%=request.getContextPath()%>/participant/ConfActiveScenarioResults?scenarioExecutionId=<s:property value="scenarioExecutionId" />" title="<s:property value="scenario.description" />">
                                <s:property value="scenario.scenarioName" />
                            </a>
                        </s:if>
                        <s:else>
                            <a href="<%=request.getContextPath()%>/participant/ActiveScenarioResults?scenarioExecutionId=<s:property value="scenarioExecutionId" />" title="<s:property value="scenario.description" />">
                                <s:property value="scenario.scenarioName" />
                            </a>
                        </s:else> --%>
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
   // var value = window.setInterval("autoRefresh()", 30000);
    function showTestCaseHelp(scenarioExecutionId) {
        var urlString = "<%=request.getContextPath()%>/help/TestCaseHelp?scenarioExecutionId=" + scenarioExecutionId;
        var stdOptions = "resizable=yes,directories=no,left=1,top=1,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        helpWindow = window.open(urlString, "testCaseHelpWindow" ,options);
        if (window.focus) {
            helpWindow.focus();
        }
    }

    function autoRefresh(){
       document.getElementById("ActiveTestResults").submit();
    }
</script>