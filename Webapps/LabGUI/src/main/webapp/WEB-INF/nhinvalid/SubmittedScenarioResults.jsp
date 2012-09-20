<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>

<%
  request.setAttribute("labMode",LabType.getType(LabType.LAB.getId()));
%>
<s:form action="SubmittedScenarioResults" theme="simple">
    <s:hidden key="selectedScenarioId" />
    <s:hidden key="selectedCaseId" />
    <s:hidden name="scenarioLevelExecution" value="N"/>

    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text">
                            <%-- <s:property value="submittedScenario.executionUniqueId" /> - --%>Submitted <s:property value="#attr.nhinvalidHeaderType"/> Results</span>
                    </td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="return" id="return" value="Return"
                               onclick="returnToSubmittedScenarios();" />
                        <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                               onclick="reloadCurrentPage(); return false;" />
                        <input type="button" class="inputButton" name="export" id="export" value="Export"
                               title="Export Request/Response messages for submitted scenario results"
                               onclick="ExportFiles();" />
                    </td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <div class="section-title">
                    <s:if test="#attr.nhinvalidHeaderType == #attr.labMode">
                    Test <s:property value="#attr.nhinvalidHeaderType"/></s:if>
                    <s:else><s:property value="#attr.nhinvalidHeaderType"/></s:else>
            </div>
            <table class="content-data">
                <tr>
                    <td><label class="short-display">Name:</label></td>
                    <td><span class="long-display">
                            <s:property value="submittedScenario.scenario.scenarioName" />&nbsp;&minus;&nbsp;<u><s:property value="submittedScenario.scenario.description" /></u>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td><label class="short-display">Execution Unique Identifier:</label></td>
                    <td><span class="long-display"><s:property value="submittedScenario.executionUniqueId" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Activated on:</label></td>
                    <td><span class="long-display"><s:property value="submittedScenario.scheduleString" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Type:</label></td>
                    <td><span class="long-display">
                        <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(submittedScenario.scenario.serviceset.labAccessFilter)"/>
                        </span>
                    </td>
               </tr>
            </table>
        </div>
        <div class="collapsible" id="collapsible-areas">
            <div class="section-title">
                Test Results
            </div>
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
            <s:if test="submittedScenario.scenario.initiatorInd == \"N\"">
                <div>
                    <table class="search-results">
                        <tr class="search-headers">
                            <td class="title-button">
                                <input type="button" class="inputButton" name="execute" id="execute" value="Execute Scenario"
                                       disabled="true"
                                       title="Pressing Execute Scenario will cause the Lab to initiate the Test Cases below, automatically in sequence." />
                            </td>
                        </tr>
                    </table>
                </div>
            </s:if>
            <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
            <div class="actionMessage content-area">
                <b>The below listed Test Cases must be initiated by the NHIN Participant.</b>
            </div>
            </s:if>
            <span class="collapsible-content">
                <table class="search-results">
                    <tr class="search-headers">
                        <th>&nbsp;</th>
                        <th>Test&nbsp;Case</th>
                        <th>Current&nbsp;Result</th>
                        <th>Description</th>
                        <th align="center">Action</th>
                    </tr>
<s:iterator value="submittedScenario.caseexecutions" status="testStatus">
                        <tr class="testable-event <s:if test="#testStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                            <td>
                                <s:if test="submittedScenario.scenario.initiatorInd == \"Y\"">
                                    <img alt="Initiator" src="<%=request.getContextPath()%>/images/initiator-icon.gif" title="Initiator">
                                </s:if>
                                <s:else>
                                    <img alt="Responder" src="<%=request.getContextPath()%>/images/responder-icon.gif" title="Responder">
                                </s:else>
                            </td>

                            <td align="center">
                                <s:url id="url" action="LogEntries">
                                    <s:param name="selectedCaseName" value="%{testcase.name}" />
                                    <s:param name="scenarioExecutionId" value="%{submittedScenario.scenarioExecutionId}" />
                                    <s:param name="selectedCaseExecutionId" value="%{caseExecutionId}" />
                                </s:url>
                                <s:a href="%{url}"><s:property value="testcase.name" /></s:a>
                            </td>
                            <td class="value">
                                <s:a href="%{url}"><s:property escape="false" value="caseResultHtml" />
                                </s:a>
                            </td>
                            <td align="left"><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                            <s:if test="testcase.messageType == \"AD\"">
                            <td width="10%" align="left">
                                <input type="button" class="inputButton" name="attach" id="attach" value="Attach Document" disabled="true" />
                                <input type="button" class="inputButton" name="show" id="show" value="Show Attachments"
                                       onclick="showAttachments('<s:property value="caseExecutionId" />', '<s:property value="testcase.name" />');" />
                            </td>
                            </s:if>
                            <s:elseif test="testcase.messageType == \"SP\"">
                            <td width="10%" align="left">
                                <input type="button" class="inputButton" name="discover" id="discover" value="Discover" disabled="true"
                                       onclick="discoverSupplemental('<s:property value="testcase.name" />', <s:property value="submittedScenario.scenario.scenarioId" />, <s:property value="testcase.caseId" />);" />
                            </td>
                            </s:elseif>
                            <s:else>
                            <td width="10%" align="left">
                                <input type="button" class="inputButton" name="execute" id="nhinspec" value="NHIN Spec"
                                       onclick="showTestCaseSpec(<s:property value="caseExecutionId" />);" />
                            </td>
                            </s:else>
                        </tr>
</s:iterator>
                </table>
            </span>
        </div>

    </div>
</s:form>
<script type="text/javascript">
    var attachDocWindow;
    var showAttachmentWindow;

    function showAttachments(caseExecutionId, caseName) {
        var urlString = "<%=request.getContextPath()%>/participant/ShowAttachments?selectedCaseExecutionId=" + caseExecutionId + "&selectedCaseName=" + caseName;
        var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
        var options = stdOptions + ",width=900,height=450,scrollbars=yes";
        showAttachmentWindow = window.open(urlString, "showAttachmentWindow" ,options);
        if (window.focus) {
            showAttachmentWindow.focus();
        }
    }

    function discoverSupplemental(testcaseName, executeScenarioId, executeCaseId) {
        if (confirm('Please confirm -- Discover Supplemental Test Case [' + testcaseName + ']?')) {
            document.getElementById("SubmittedScenarioResults").action = "<%=request.getContextPath()%>/participant/ExecuteTestCase";
            document.getElementById("SubmittedScenarioResults").selectedScenarioId.value = executeScenarioId;
            document.getElementById("SubmittedScenarioResults").selectedCaseId.value = executeCaseId;
            document.getElementById("SubmittedScenarioResults").submit();
        }
    }

    var helpWindow;
    function showTestCaseSpec(caseExecutionId) {
        var urlString = "<%=request.getContextPath()%>/nhinvalid/TestCaseSpec?selectedCaseExecutionId=" + caseExecutionId;
        var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        helpWindow = window.open(urlString, "testCaseSpecWindow" ,options);
        if (window.focus) {
            helpWindow.focus();
        }
    }

    function scenarioCaseExecution(scenarioName, executeScenarioId) {
        if (confirm('Please confirm -- Execute Test Case [' + scenarioName + ']?')) {
            document.getElementById("SubmittedScenarioResults").action = "<%=request.getContextPath()%>/participant/ExecuteTestCase";
            document.getElementById("SubmittedScenarioResults").selectedScenarioId.value = executeScenarioId;
            document.getElementById("SubmittedScenarioResults").selectedCaseId.value = "0";
            document.getElementById("SubmittedScenarioResults").scenarioLevelExecution.value = "Y";
            document.getElementById("SubmittedScenarioResults").submit();
        }
    }

    function returnToSubmittedScenarios() {
        document.getElementById("SubmittedScenarioResults").action = '<%=request.getContextPath()%>/nhinvalid/ValidateSubmittedTestResults?executionUniqueId=<%=session.getAttribute("sessionobj.servicesetExecutionUniqueId")%>';
        document.getElementById("SubmittedScenarioResults").submit();
        return;
    }

    function ExportFiles() {
       var url ="<%=request.getContextPath()%>/nhinvalid/ExportLogFiles";
       var stdOptions = "resizable=yes,directories=no,left=40,top=40,menubar=yes,location=no";
       var options = stdOptions + ",width=750,height=550,scrollbars=yes";
       window.open(url,'ExportFilesWindow',options);
    }

    function reloadCurrentPage() {
        history.go(0);
        return;
    }
</script>