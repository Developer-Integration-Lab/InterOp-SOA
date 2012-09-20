<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%
            response.addHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
            response.addHeader("Cache-Control", "pre-check=0,post-check=0");
            response.setDateHeader("Expires", 0);
%>
<%
            int iTheRoleOfTheLoggedInUser = -1;
            Participant objParticipantThatNhinRepWorksWith = null;
            String strParticipantThatNhinRepWorksWith = "";

            // Reference: See implementation of determineRoleForward() method in Login.java.
            iTheRoleOfTheLoggedInUser = ((User) session.getAttribute("userProfile")).getPrimaryRole();

            if (iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {   // avoid any NPE further.
                objParticipantThatNhinRepWorksWith = ((User) session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
                if (null == objParticipantThatNhinRepWorksWith) {
                    strParticipantThatNhinRepWorksWith = "none";
                } else {
                    strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName();
                }
            }

            User user = ((User) session.getAttribute("userProfile"));
            LabType labType = user.getLabType();
            if (labType == null) {
                //  labType = LabType.LAB;
                request.setAttribute("headerType", session.getAttribute("nhinrepHeaderType"));
            } else {
                int labMode = labType.getId();
                request.setAttribute("headerType", labType.getType(labMode));
            }
%>
<s:form action="LogEntries" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td>
                        <span class="content-title-text">
                            <% if (iTheRoleOfTheLoggedInUser == LabConstants.ROLE_PARTICIPANT) {%>
                            <s:property value="#session.userProfile.participant.participantName" /> - Test Case Result Summary
                            <%} else if (iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {%>
                            <s:property value="#session.userProfile.nhinrep.name" /> - Test Case Result Summary
                            <%}%>
                        </span>
                    </td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="return" id="return" value="Return"
                               onclick="returnToSubmittedTestCases();" />
                        <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                               onclick="reloadCurrentPage();" />
                    </td>
                </tr>
                <% if (iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {%>
                <tr>
                    <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                </tr>
                <%}%>
            </table>
        </div>

        <div id="maindiv" class="content-area">
            <table width="55%" align="center">
                <tr>
                    <td align="left">
                        <s:fielderror cssClass="errorstyle"/>
                    </td>
                </tr>
            </table>
        </div>

        <%--<div class="collapsible" id="collapsible-areas">--%>
        <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-1'); return false;">
            <s:property value="#attr.headerType"/> Execution Details
        </div>
        <div class="collapsible-area" id="collapsible-panel-1">
            <span class="collapsible-content">
                <div class="content-area">
                    <table>
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
                        <% if (iTheRoleOfTheLoggedInUser != LabConstants.ROLE_PARTICIPANT) {%>
                        <tr>
                            <td><label class="short-display">Type:</label></td>
                            <td><span class="long-display">
                                    <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(submittedScenario.scenario.serviceset.labAccessFilter)"/>
                                </span>
                            </td>
                        </tr>
                        <% }%>
                    </table>


                </div>
            </span>
        </div>

        <div class="content-area">
            <div class="section-title">
                Case Execution Details
            </div>
            <table>
                <tr>
                    <td><label class="short-display">Test Case Name:</label></td>
                    <td><span class="long-display"><s:property value="%{selectedjspCaseName}" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Executed on:</label></td>
                    <td><span class="long-display"><s:date name="currentCaseResult.executeTime" format="yyyy-MM-dd HH:mm:ss" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Updated At:</label></td>
                    <td><span class="long-display"><s:date name="currentCaseResult.updatedAt" format="yyyy-MM-dd HH:mm:ss" /></span></td>
                </tr>
                <tr>
                    <td><label class="short-display">Result:</label></td>
                    <td><span class="long-display"><s:property escape="false" value="testCase.caseResultHtml" /></span></td>
                </tr>
            </table>

            <div class="scroll">
                <table class="search-results">
                    <tr class="search-headers">
                        <th colspan="2">TestCase Required And Optional Parameters</th>
                    </tr>
                    <s:if test="scenariocaseparamList != null and scenariocaseparamList.size() > 0">
                        <tr class="search-headers">
                            <th>Required Parameter</th>
                            <th>Expected Value</th>
                        </tr>
                        <s:iterator value="scenariocaseparamList" status="scenariocaseparamStatus">
                            <s:if test = "required == \"Y\"">
                                <tr class="<s:if test="#scenariocaseparamStatus.odd == true ">odd-row</s:if>">
                                    <td align ="left" width="50%"><s:property value="displayParamName" /></td>
                                    <td align ="left" width="50%"><s:property value="paramValue" /></td>
                                </tr>
                            </s:if>
                        </s:iterator>

                        <tr class="search-headers">
                            <th>Optional Parameter</th>
                            <th>Expected Value</th>
                        </tr>
                        <s:iterator value="scenariocaseparamList" status="scenariocaseparamStatus">
                            <s:if test = "required == \"N\"">
                                <tr class="<s:if test="#scenariocaseparamStatus.odd == true ">odd-row</s:if>">
                                    <td align ="left" width="50%"><s:property value="displayParamName" /></td>
                                    <td align ="left" width="50%"><s:property value="paramValue" /></td>
                                </tr>
                            </s:if>
                        </s:iterator>
                    </s:if>
                    <s:else>
                        <tr><td>TestCase Parameters are not available.</td></tr>
                    </s:else>
                </table>
            </div>
        </div>

        <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-2'); return false;">
            Case Result Details
        </div>
        <div class="collapsible-area" id="collapsible-panel-2">
            <span class="collapsible-content">
                <div class="content-area">
                    <table>
                        <tr>
                            <th align="left" colspan="<s:if test="testCase.messageType == \"RD\"">2</s:if><s:else>1</s:else>"
                                title="<s:if test="testCase.messageType == \"QD\" or testCase.messageType == \"RD\"">Documents queried and retrieved are automatically captured for responder scenarios only</s:if>">
                                <s:property escape="false" value="%{resultMessage}" />
                            </th>
                        </tr>
                        <s:if test="currentCaseResult.result != null && currentCaseResult.result == \"ERROR\" && currentCaseResult.stacktrace!=null ">
                            <tr>
                                <th align="left">
                                	 <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-5'); return false;">
           								 Error message details
        							</div>
                                	<div class="collapsible-area" id="collapsible-panel-5" style="display:none;">
                                		<span class="collapsible-content">
                                			<s:property escape="false" value="%{currentCaseResult.stacktrace}" />
                                		</span>
                                	</div>
                                </th>
                            </tr>
                        </s:if>
                         <s:if test ="currentCaseResult.resultdocuments.size() > 0">
	                        
	                        <s:if test="testCase.messageType == \"QD\"">
	                        	<table class="search-results" style="width:35%;">
	                        	<tr>
				                    <th>Document Type</th>
				                    <th>Document UniqueID</th>
				                </tr>
		                        <s:iterator value="currentCaseResult.resultdocuments" status="documentStatus">
		                            <tr class="<s:if test="#documentStatus.odd == true ">odd-row</s:if>">
	                                    <td>
		                            		<s:if test="%{classCode in @net.aegis.lab.util.LabConstants@PRIVACYPOLICY_CLASSCODE_LIST}"><s:property value="@net.aegis.lab.util.LabConstants@DOCUMENT_TYPE_POLICY"/></s:if>
			                            	<s:else><s:property value="@net.aegis.lab.util.LabConstants@DOCUMENT_TYPE_PATIENT"/></s:else>
	                            		</td>
		                            	<td ><s:property value="documentUniqueId" /></td>
		                            </tr>
		                        </s:iterator>
	                        </table>
	                        </s:if>
	                        <s:if test="testCase.messageType == \"RD\"">
	                        	<table class="search-results">
		                        	<tr>
					                   <th width="20%">Document UniqueID</th>
					                   <th width="20%">Document</th>
					                   <th width="60%">Comment</th>
					                </tr>
		                        	<s:iterator value="currentCaseResult.resultdocuments" status="documentStatus">
			                            <tr class="<s:if test="#documentStatus.odd == true ">odd-row</s:if>">
			                            	<td ><s:property value="documentUniqueId" /></td>
			                            	<td>
			                                  	<input type="button" class="inputButton" name="download%{resultDocumentId}" id="download%{resultDocumentId}" value="Download" onclick="downloadResultDocument('<s:property value="resultDocumentId" />');" />
			                                 </td>
		                                    <td><s:property value="message"/></td>
			                            </tr>
		                        	</s:iterator>
	                        	</table>
	                        </s:if>
                        </s:if>         
                    </table>
                    <%-- 
                    <s:if test="submittedScenario.scenario.initiatorInd == \"Y\"">
                        <div class="scroll">
                            <table class="search-results">
                                <tr class="search-headers">
                                    <th colspan="2">Case Result Required And Optional Parameters</th>
                                </tr>
                                <s:if test="caseResultParamList != null and caseResultParamList.size() > 0">
                                    <tr class="search-headers">
                                        <th>Required Parameter</th>
                                        <th>Actual Value</th>
                                    </tr>
                                    <s:iterator value="caseResultParamList" status="caseResultParamStatus">
                                        <s:if test = "required == \"Y\"">
                                            <tr class="<s:if test="#caseResultParamStatus.odd == true ">odd-row</s:if>">
                                                <td align ="left" width="50%"><s:property value="displayParamName" /></td>
                                                <td align ="left" width="50%"><s:property value="paramValue" /></td>
                                            </tr>
                                        </s:if>
                                    </s:iterator>

                                    <tr class="search-headers">
                                        <th>Optional Parameter</th>
                                        <th>Actual Value</th>
                                    </tr>
                                    <s:iterator value="caseResultParamList" status="caseResultParamStatus">
                                        <s:if test = "required == \"N\"">
                                            <tr class="<s:if test="#caseResultParamStatus.odd == true ">odd-row</s:if>">
                                                <td align ="left" width="50%"><s:property value="displayParamName" /></td>
                                                <td align ="left" width="50%"><s:property value="paramValue" /></td>
                                            </tr>
                                        </s:if>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <tr><td>Case Result Parameters are not available.</td></tr>
                                </s:else>
                            </table>
                        </div>
                    </s:if>
                	--%>
               </div>
            </span>
        </div>

		<%@ include file="/WEB-INF/common/CaseLogSummary.jsp" %>
        <%-- 
        <div class="content-area">
            <div class="section-title">
                Case Log Summary - <s:property value="%{selectedjspCaseName}" />
            </div>
            <s:if test="auditSummaryList.size() > 0">
                <span class="collapsible-content">
                    <div class="scrollable-area-400">
                        <table class="search-results" >
                            <tr class="search-headers">
                                <th>Execute<br>Time </th>
                                <th>Test<br/>HarnessId</th>
                                <th>Type</th>
                                <th>Network<br>Access<br>PointId</th>
                                <th>Enterprise<br>SourceId</th>
                                <th>Enterprise<br>SourceSite</th>
                                <th>Type<br>Code</th>
                                <th>Message<br>Type</th>
                                <th>Action<br>Code</th>
                                <th>Patient<br>Id</th>
                                <th>Outcome<br>Indicator</th>
                                <th>User<br>Id</th>
                                <th>User<br>FName</th>
                            </tr>
                            <s:iterator value="auditSummaryList" status="summaryStatus">
                                <tr class="<s:if test="#summaryStatus.odd == true ">odd-row</s:if>">
                                    <td class="value"><s:property value="executeTime" /></td>
                                    <td class="value"><s:property value="testharnessri.testharnessId" /></td>
                                    <td class="value"><s:property value="type" /></td>
                                    <td class="value"><s:property value="networkAccessPointId" /></td>
                                    <td class="value"><s:property value="enterpriseSourceId" /></td>
                                    <td class="value"><s:property value="enterpriseSourceSite" /></td>
                                    <td class="value"><s:property value="typeCode" /></td>
                                    <td class="value"><s:property value="messageType" /></td>
                                    <td class="value"><s:property value="actionCode" /></td>
                                    <td class="value"><s:property value="patientId" /></td>
                                    <td class="value"><s:property value="outcomeIndicator" /></td>
                                    <td class="value"><s:property value="userId" /></td>
                                    <td class="value"><s:property value="userName" /></td>
                                </tr>
                            </s:iterator>
                        </table>
                    </div>
                </span>
            </s:if>
            <s:else>
                No Log Entries exist for this test case.
            </s:else>
        </div>
        --%>
        <%-- 
        <div class="content-area">
            <div class="section-title">
                Case Log Details - <s:property value="%{selectedjspCaseName}" />
            </div>
            <div class="content-subtitle">Test Harness RI 1 - Audit Logs</div>
            <s:if test="ri1DetailedAuditLogList != null and ri1DetailedAuditLogList.size() > 0">
                <div class="collapsible-area">
                    <div class="scrollable-area-200">
                        <table class="search-results">
                            <tr class="search-headers">
                                <th>Id</th>
                                <th>Execute Time </th>
                                <th>Event Id</th>
                                <th>User Id</th>
                                <th>Type Code</th>
                                <th>Type Code Role</th>
                                <th>ID Type Code</th>
                                <th>Receiver Patient Id</th>
                                <th>Sender Patient Id</th>
                                <th>Community Id</th>
                                <th>Message Type</th>
                                <th>Audit Message</th>
                                <th>Request / Response</th>
                            </tr>
                            <s:iterator value="ri1DetailedAuditLogList" status="ri1DetailedAuditStatus">
                                <tr class="<s:if test="#ri1DetailedAuditStatus.odd == true ">odd-row</s:if>">
                                    <td><s:property value="id" /></td>
                                    <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td><s:property value="eventId" /></td>
                                    <td><s:property value="userId" /></td>
                                    <td><s:property value="participationTypeCode" /></td>
                                    <td><s:property value="participationTypeCodeRole" /></td>
                                    <td><s:property value="participationIDTypeCode" /></td>
                                    <td><s:property value="receiverPatientId" /></td>
                                    <td><s:property value="senderPatientId" /></td>
                                    <td><s:property value="communityId" /></td>
                                    <td><s:property value="messageType" /></td>
                                    <td>
                                        <input type="button" class="inputButton" name="auditlog1message" id="auditlog1message" value="Download"
                                               onclick="downloadAuditLogMessage('1', '<s:property value="id" />');" />
                                    </td>
                                    <td>
                                        <s:if test="extensionMessagePresent">
                                            <input type="button" class="inputButton" name="auditext1message" id="auditext1message" value="Download"
                                                   onclick="downloadAuditExtensionMessage('1', '<s:property value="id" />');" />
                                        </s:if>
                                        <s:else>N/A</s:else>
                                    </td>
                                </tr>
                            </s:iterator>
                        </table>
                    </div>
                </div>
            </s:if>
            <s:if test="ri1DetailedAuditLogList != null and ri1DetailedAuditLogList.size() == 0">
                <table>
                    <tr>
                        <td>
                            No Case Log details found on RI-1 for this test case.
                        </td>
                    </tr>
                </table>
            </s:if>
            <div class="content-subtitle">Test Harness RI 2 - Audit Logs</div>
            <s:if test="ri2DetailedAuditLogList != null and ri2DetailedAuditLogList.size() > 0">
                <div class="collapsible-area">
                    <div class="scrollable-area-200">
                        <table class="search-results">
                            <tr class="search-headers">
                                <th>Id</th>
                                <th>Execute Time </th>
                                <th>Event Id</th>
                                <th>User Id</th>
                                <th>Type Code</th>
                                <th>Type Code Role</th>
                                <th>ID Type Code</th>
                                <th>Receiver Patient Id</th>
                                <th>Sender Patient Id</th>
                                <th>Community Id</th>
                                <th>Message Type</th>
                                <th>Audit Message</th>
                                <th>Request / Response</th>
                            </tr>
                            <s:iterator value="ri2DetailedAuditLogList" status="ri2DetailedAuditStatus">
                                <tr class="<s:if test="#ri2DetailedAuditStatus.odd == true ">odd-row</s:if>">
                                    <td><s:property value="id" /></td>
                                    <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td><s:property value="eventId" /></td>
                                    <td><s:property value="userId" /></td>
                                    <td><s:property value="participationTypeCode" /></td>
                                    <td><s:property value="participationTypeCodeRole" /></td>
                                    <td><s:property value="participationIDTypeCode" /></td>
                                    <td><s:property value="receiverPatientId" /></td>
                                    <td><s:property value="senderPatientId" /></td>
                                    <td><s:property value="communityId" /></td>
                                    <td><s:property value="messageType" /></td>
                                    <td>
                                        <input type="button" class="inputButton" name="auditlog2message" id="auditlog2message" value="Download"
                                               onclick="downloadAuditLogMessage('2', '<s:property value="id" />');" />
                                    </td>
                                    <td>
                                        <s:if test="extensionMessagePresent">
                                            <input type="button" class="inputButton" name="auditext2message" id="auditext2message" value="Download"
                                                   onclick="downloadAuditExtensionMessage('2', '<s:property value="id" />');" />
                                        </s:if>
                                        <s:else>N/A</s:else>
                                    </td>
                                </tr>
                            </s:iterator>
                        </table>
                    </div>
                </div>
            </s:if>
            <s:if test="ri2DetailedAuditLogList != null and ri2DetailedAuditLogList.size() == 0">
                <table>
                    <tr><td>No Case Log details found on RI-2 for this test case.</td></tr>
                </table>
            </s:if>
            <div class="content-subtitle">Test Harness RI 3 - Audit Logs</div>
            <s:if test="ri3DetailedAuditLogList != null and ri3DetailedAuditLogList.size() > 0">
                <div class="collapsible-area">
                    <div class="scrollable-area-200">
                        <table class="search-results">
                            <tr class="search-headers">
                                <th>Id</th>
                                <th>Execute Time </th>
                                <th>Event Id</th>
                                <th>User Id</th>
                                <th>Type Code</th>
                                <th>Type Code Role</th>
                                <th>ID Type Code</th>
                                <th>Receiver Patient Id</th>
                                <th>Sender Patient Id</th>
                                <th>Community Id</th>
                                <th>Message Type</th>
                                <th>Audit Message</th>
                                <th>Request / Response</th>
                            </tr>
                            <s:iterator value="ri3DetailedAuditLogList" status="ri3DetailedAuditStatus">
                                <tr class="<s:if test="#ri3DetailedAuditStatus.odd == true ">odd-row</s:if>">
                                    <td><s:property value="id" /></td>
                                    <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td><s:property value="eventId" /></td>
                                    <td><s:property value="userId" /></td>
                                    <td><s:property value="participationTypeCode" /></td>
                                    <td><s:property value="participationTypeCodeRole" /></td>
                                    <td><s:property value="participationIDTypeCode" /></td>
                                    <td><s:property value="receiverPatientId" /></td>
                                    <td><s:property value="senderPatientId" /></td>
                                    <td><s:property value="communityId" /></td>
                                    <td><s:property value="messageType" /></td>
                                    <td>
                                        <input type="button" class="inputButton" name="auditlog3message" id="auditlog3message" value="Download"
                                               onclick="downloadAuditLogMessage('3', '<s:property value="id" />');" />
                                    </td>
                                    <td>
                                        <s:if test="extensionMessagePresent">
                                            <input type="button" class="inputButton" name="auditext3message" id="auditext3message" value="Download"
                                                   onclick="downloadAuditExtensionMessage('3', '<s:property value="id" />');" />
                                        </s:if>
                                        <s:else>N/A</s:else>
                                    </td>
                                </tr>
                            </s:iterator>
                        </table>
                    </div>
                </div>
            </s:if>
            <s:if test="(ri3DetailedAuditLogList == null) or ri3DetailedAuditLogList != null and ri3DetailedAuditLogList.size() == 0">
                <table>
                    <tr><td>No Case Log details found on RI-3 for this test case.</td></tr>
                </table>
            </s:if>
        </div>
        --%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
</s:form>
<script type="text/javascript">
    function returnToSubmittedTestCases() {
        document.getElementById("LogEntries").action = '<%=request.getContextPath()%>/history/TestHistoryScenarioResults?scenarioExecutionId=<%=session.getAttribute("sessionobj.scenarioExecutionId")%>';
        document.getElementById("LogEntries").submit();
        return;
    }
    function downloadResultDocument(pResultDocumentId) {
        document.getElementById("LogEntries").action = "<%=request.getContextPath()%>/participant/DownloadDocument?resultDocumentId=" + pResultDocumentId;
        document.getElementById("LogEntries").submit();
        document.getElementById("LogEntries").action = "<%=request.getContextPath()%>/history/LogEntries";
    }
    function downloadAuditLogMessage(pTestHarnessId, pAuditRepositoryId) {
        document.getElementById("LogEntries").action = "<%=request.getContextPath()%>/help/DownloadAuditLogMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
        document.getElementById("LogEntries").submit();
        document.getElementById("LogEntries").action = "<%=request.getContextPath()%>/participant/LogEntries";
    }
    function downloadAuditExtensionMessage(pTestHarnessId, pAuditRepositoryId) {
        document.getElementById("LogEntries").action = "<%=request.getContextPath()%>/help/DownloadAuditExtensionMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
        document.getElementById("LogEntries").submit();
        document.getElementById("LogEntries").action = "<%=request.getContextPath()%>/participant/LogEntries";
    }
    function reloadCurrentPage() {
        history.go(0);
        return;
    }
</script>