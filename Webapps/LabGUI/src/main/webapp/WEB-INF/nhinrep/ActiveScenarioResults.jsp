<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<%
            response.addHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
            response.addHeader("Cache-Control", "pre-check=0,post-check=0");
            response.setDateHeader("Expires", 0);

            int iNhinRepId = -999;
            Participant objParticipantThatNhinRepWorksWith = null;
            String strParticipantThatNhinRepWorksWith = "";

            iNhinRepId = ((User) session.getAttribute("userProfile")).getNhinrep().getNhinRepId();
            objParticipantThatNhinRepWorksWith = ((User) session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
            if (null == objParticipantThatNhinRepWorksWith) {
                strParticipantThatNhinRepWorksWith = "none";
            } else {
                strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName();
            }
            request.setAttribute("participant_gateway_type",objParticipantThatNhinRepWorksWith.getVersion());
            request.setAttribute("labMode", LabType.getType(LabType.LAB.getId()));
%>

<s:form action="ActiveScenarioResults" theme="simple">
    <s:hidden key="selectedScenarioExecutionId" />
    <s:hidden key="selectedScenarioId" />
    <s:hidden key="selectedCaseId" />
    <s:hidden name="scenarioLevelExecution" value="N"/>
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - <%=strParticipantThatNhinRepWorksWith%> - Active <s:property value="#attr.nhinrepHeaderType"/> Results</span></td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="return" id="return" value="Return"
                               onclick="returnToActiveScenarios();" />
                        <input type="submit" class="inputButton" name="refresh" id="refresh" value="Refresh"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                </tr>
            </table>
        </div>

        <%--<div class="collapsible" id="collapsible-areas">--%>
        <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-1'); return false;">
            Connection Information
        </div>
        <div class="collapsible-area" id="collapsible-panel-1">
            <span class="collapsible-content">
                <div class="content-area">
                    <table>
                        <tr>
                            <td><s:label cssClass="short-display" key="nhinrep.participant.communityId"/></td>
                            <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                        </tr>
                        <tr>
                            <td><s:label cssClass="short-display" key="nhinrep.participant.assigningAuthorityId"/></td>
                            <td><span class="long-display"><s:property value="participant.assigningAuthorityId" /></span></td>
                        </tr>
                        <tr>
                            <td><s:label cssClass="short-display" key="nhinrep.participant.ipAddress"/></td>
                            <td><span class="long-display"><s:property value="participant.ipAddress" /></span></td>
                        </tr>
                    </table>
                </div>
            </span>
        </div>
        <%--</div>--%>

        <div class="content-area">
            <div class="section-title">
                <s:if test="#attr.nhinrepHeaderType == #attr.labMode">
                    Test <s:property value="#attr.nhinrepHeaderType"/></s:if>
                <s:else><s:property value="#attr.nhinrepHeaderType"/></s:else>
            </div>
            <table>
                <tr>
                    <td valign="top"><label class="short-display">Name:</label></td>
                    <td><span class="long-display">
                            <s:property value="testScenario.scenario.scenarioName" />&nbsp;&minus;&nbsp;<u><s:property value="testScenario.scenario.description" /></u>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td><label class="short-display">Execution Unique Identifier:</label></td>
                    <td><span class="long-display"><s:property value="testScenario.executionUniqueId" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Activated On:</label></td>
                    <td><span class="long-display"><s:property value="testScenario.scheduleString" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Last Executed On:</label></td>
                    <td><span class="long-display"><s:property value="testScenario.lastExecutionString" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Type:</label></td>
                    <td><span class="long-display">
                            <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(testScenario.scenario.serviceset.labAccessFilter)"/>
                        </span>
                    </td>
                </tr>
            </table>
        </div>

        <s:if test="testScenario.scenario.initiatorInd == \"Y\" and #attr.nhinrepHeaderType != #attr.labMode">
            <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-3'); return false;">
                Messages Received between Timed Window
            </div>
            <div class="collapsible-area" id="collapsible-panel-3">
                <span class="collapsible-content">
                    <s:if test="messagesbtwTMWindow != null and messagesbtwTMWindow.size() > 0" >
                        <div class="scrollable-area-200">
                            <table class="search-results">
                                <tr class="search-headers">
                                    <th>Timestamp </th>
                                    <th>Service </th>
                                    <th>Direction</th>
                                    <th>From HCID</th>
                                    <th>TO HCID</th>
                                    <th>Message</th>
                                </tr>
                                <s:iterator value="messagesbtwTMWindow" status="msgTMWStatus">
                                    <tr class="<s:if test="#msgTMWStatus.odd == true">odd-row</s:if>">
                                        <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                        <td><s:property value="service" /></td>
                                        <td><s:property value="direction" /></td>
                                        <td><s:property value="fromHCID" /></td>
                                        <td><s:property value="toHCID" /></td>
                                        <td>
                                            <input type="button" class="inputButton" name="audit1message" id="audit1message" value="Download"
                                                   onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                        </td>
                                    </tr>
                                </s:iterator>
                            </table>
                        </div>
                    </s:if>
                    <s:else>
                        <table class="search-results">
                            <tr><td align ="left">No Log Messages found.</td></tr>
                        </table>
                    </s:else>

                </span>
            </div>
        </s:if>
        
         <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-2'); return false;">
            Exchange Environment
        </div>
        <div class="collapsible-area" id="collapsible-panel-2">
            <span class="collapsible-content">
                <div class="content-area">
                    <table>
                        <tr>
                            <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
                           		<td><s:label cssClass="short-display" key="participant.initiator.gateway"/></td>
                            	<td><span class="long-display"><s:property value="#attr.participant_gateway_type" /></span></td>
                            </s:if>
                            <s:else>
                            	<td><s:label cssClass="short-display" key="lab.initiator.gateway"/></td>
                            	<td><span class="long-display"><s:property value="testScenario.caseexecutions[0].version" /></span></td>
                            </s:else>
                        </tr>
                        <tr>
                             <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
                             	<td><s:label cssClass="short-display" key="lab.responder.gateway"/></td>
                            	<td><span class="long-display"><s:property value="testScenario.caseexecutions[0].version" /></span></td>
                            </s:if>
                            <s:else>
                           		<td><s:label cssClass="short-display" key="participant.responder.gateway"/></td>
                            	<td><span class="long-display"><s:property value="#attr.participant_gateway_type" /></span></td>
                            </s:else>                            
                        </tr>
                    </table>
                </div>
            </span>
        </div>

        <div class="content-area">
            <div class="section-title">
                Test Results
            </div>
            <div id="ActionMessagesContainer">
                <div id="ActionMessagesList">
                    <strong style="color: #00f"><s:actionmessage/></strong>
                </div>
                <s:if test="testScenario.scenario.initiatorInd == \"N\" and testScenario.progressPending">
                    <div id="AddedActionMessage">
                        <i>Test Scenario Execution is in progress, awaiting the resolution of the test cases below. <%-- Pressing
                        the Execute Scenario button again now, before the results of this ongoing execution have been
                        determined, will corrupt the test scenario results. --%> Press Refresh to view updated scenario results.</i>
                    </div>
                </s:if>
                <div id="ActionMessagePlaceHolder"></div>
            </div>
            <s:if test="testScenario.scenario.initiatorInd == \"N\" and testScenario.executeOk">
                <div>
                    <table class="search-results">
                        <tr class="search-headers">
                            <td class="title-button">
                                <input type="button" class="inputButton" name="execute" id="execute" value="Execute Scenario" disabled="disabled"
                                       <%--title="Pressing Execute Scenario will cause the Lab to initiate the Test Cases below, automatically in sequence." />--%>
                                       title="NHIN Participant can press Execute Scenario which causes the Lab to initiate the Test Cases below, automatically in sequence." />
                            </td>
                        </tr>
                    </table>
                </div>
            </s:if>
            <%-- ***************************************************************
            <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
            <div class="actionMessage content-area">
                <b>The below listed Test Cases must be initiated by the NHIN Participant.</b>
            </div>
            </s:if>
             *************************************************************** --%>
            <span class="collapsible-content">
                <table class="search-results">
                    <tr class="search-headers">
                        <th>&nbsp;</th>
                        <th>Test&nbsp;Case</th>
                        <th>Current&nbsp;Result</th>
                        <th>Description</th>
                        <th align="center">Action</th>
                    </tr>
                    <s:iterator value="testScenario.caseexecutions" status="testStatus">
                        <tr class="testable-event <s:if test="#testStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                            <td>
                                <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
                                    <img alt="Initiator" src="<%=request.getContextPath()%>/images/initiator-icon.gif" title="Initiator">
                                </s:if>
                                <s:else>
                                    <img alt="Responder" src="<%=request.getContextPath()%>/images/responder-icon.gif" title="Responder">
                                </s:else>
                            </td>

                            <td align="center">
                                <s:url id="url" action="LogEntries">
                                    <s:param name="selectedCaseExecutionId" value="%{caseExecutionId}" />
                                    <s:param name="selectedCaseName" value="%{testcase.name}" />
                                </s:url>
                                <s:a href="%{url}" title="Navigate to current result summary"><s:property value="testcase.name" /></s:a>
                            </td>
                            <td class="value">
                                <s:a href="%{url}" title="Navigate to current result summary"><s:property escape="false" value="caseResultHtml" /></s:a>
                            </td>
                            <td align="left"><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                            <s:if test="testcase.messageType == \"AD\"">
                                <td width="10%" align="left">
                                    <input type="button" class="inputButton" name="attach" id="attach" value="Attach Document" disabled="disabled" />
                                    <input type="button" class="inputButton" name="show" id="show" value="Show Attachments"
                                           onclick="showAttachments('<s:property value="caseExecutionId" />', '<s:property value="testcase.name" />');" />
                                </td>
                            </s:if>
                            <s:elseif test="testcase.messageType == \"SP\"">
                                <td width="10%" align="left">
                                    <input type="button" class="inputButton" name="discover" id="discover" value="Discover" disabled="disabled" />
                                </td>
                            </s:elseif>
                            <s:else>
                                <td width="10%" align="left">
                                    <input type="button" class="inputButton" name="execute" id="nhinspec" value="NHIN Spec"
                                           onclick="showTestCaseSpec(<s:property value="caseExecutionId" />);" />
                                    <s:if test="testScenario.scenario.initiatorInd == \"Y\" and #attr.nhinrepHeaderType != #attr.labMode">
                                        <input type="button" class="inputButton" name="Search" id="Search" value="SearchMessages"
                                               onclick="showAuditMsg('<s:property value="caseExecutionId" />', '<s:property value="testcase.name" />');" />
                                    </s:if>
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

    var helpWindow;
    function showTestCaseSpec(caseExecutionId) {
        var urlString = "<%=request.getContextPath()%>/help/TestCaseSpec?selectedCaseExecutionId=" + caseExecutionId;
        var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        helpWindow = window.open(urlString, "testCaseSpecWindow" ,options);
        if (window.focus) {
            helpWindow.focus();
        }
    }

    function returnToActiveScenarios() {
        document.getElementById("ActiveScenarioResults").action = "<%=request.getContextPath()%>/nhinrep/ActiveTestResults?executionUniqueId=<%=session.getAttribute("sessionobj.servicesetExecUniqueId")%>";
        document.getElementById("ActiveScenarioResults").submit();
    }

    function addActionMessage(messageText) {
        var actionMessagesPlaceHolderDiv = document.getElementById('ActionMessagePlaceHolder');
        var msgDiv = document.createElement('div');
        msgDiv.setAttribute('id', 'AddedActionMessage');
        msgDiv.innerHTML = '<i>' + messageText + '</i>';
        actionMessagesPlaceHolderDiv.parentNode.insertBefore(msgDiv, actionMessagesPlaceHolderDiv);
    }


    function showAuditMsg(caseExecutionId, caseName){
        document.forms[0].action= "ShowActionMessages?selectedCaseExecutionId=" + caseExecutionId + "&selectedCaseName=" + caseName;
        document.forms[0].submit();
    }

    function downloadLogMessage(pTestHarnessId, pAuditRepositoryId){
        var urlString = "<%=request.getContextPath()%>/help/DownloadAuditExtensionMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
        var stdOptions = "resizable=yes,directories=no,left=100,top=40,menubar=yes,location=no";
        var options = stdOptions + ",width=800,height=600,scrollbars=yes";
        window.open(urlString, "MessageContentWindow" ,options);
        return false;
    }
</script>