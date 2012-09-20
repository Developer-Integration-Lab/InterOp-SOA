<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%@ page import="net.aegis.lab.manager.ApplicatiopropertiesManager" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/date.format.js"></script>
<%
            String timerWindowKey = "test.case.execution.timer.window.milli";
            String contentMessage = LabType.getType(LabType.CONFORMANCE.getId());
           String delay=ApplicatiopropertiesManager.getInstance().getPropertyvalueByKey(timerWindowKey).trim();
%>

<div id="content-pleasewait" style="visibility: hidden; height: 0">
    <p><b><s:property value="testScenario.scenario.scenarioName" /> - <%=contentMessage%> Execution in progress!</b></p>
    <hr/>
    <p class="errorMessage"><b>This page will automatically refresh to show <%=contentMessage%> results upon completion of the <%=contentMessage%> execution.</b></p>
    <p class="actionMessage"><b><i>Note: Attempting to press the Execute button again now, before the results of this ongoing execution have been determined, may corrupt the <%=contentMessage%> results.</i></b></p>
</div>

<div id="content-pleasewait-startTimer" style="visibility: hidden; height: 0"></div>

<s:form action="ConfActiveScenarioResults" theme="simple">
    <s:hidden key="selectedScenarioExecutionId" />
    <s:hidden key="selectedScenarioId" />
    <s:hidden key="selectedCaseId" />
    <s:hidden name="caseExecutionId" />
    <s:hidden name="scenarioLevelExecution" value="N"/>
    <s:hidden key="startTime" />
    <s:hidden key="endTime" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Active <%=contentMessage%> Results</span></td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="return" id="return" value="Return"
                               onclick="returnToActiveScenarios();" />
                        <input type="submit" class="inputButton" name="refresh" id="refresh" value="Refresh"/>
                    </td>
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
                            <td><s:label cssClass="short-display" key="participant.communityId"/></td>
                            <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                        </tr>
                        <tr>
                            <td><s:label cssClass="short-display" key="participant.assigningAuthorityId"/></td>
                            <td><span class="long-display"><s:property value="participant.assigningAuthorityId" /></span></td>
                        </tr>
                        <tr>
                            <td><s:label cssClass="short-display" key="participant.ipAddress"/></td>
                            <td><span class="long-display"><s:property value="participant.ipAddress" /></span></td>
                        </tr>
                    </table>
                </div>
            </span>
        </div>
        <%--</div>--%>

        <div class="content-area">
            <div class="section-title">
                <%=contentMessage%>
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
            </table>
        </div>
        <div class="content-area">
            <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
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
                                        <th>TestCase</th>
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
                                            <s:if test="%{fromHCID == participant.communityId || toHCID == participant.communityId }" >
                                                <td>
                                                    <input type="button" class="inputButton" name="audit1message" id="audit1message" value="Download"
                                                           onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                                </td>

                                            </s:if>
                                            <s:else>
                                                <td>
                                                    <input type="button" class="inputButtonDisabled" disabled="disabled" name="audit1message" id="audit1message" value="Download"
                                                           onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                                </td>
                                            </s:else>  
                                        </tr>
                                    </s:iterator>
                                </table>
                            </div>
                        </s:if>
                        <s:if test="messagesbtwTMWindow != null and messagesbtwTMWindow.size() == 0" >
                            <table class="search-results">
                                <tr><td align ="left">No Messages found.</td></tr>
                            </table>
                        </s:if>
                    </span>
                </div>
            </s:if>
        </div>
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
                            	<td><span class="long-display"><s:property value="participant.version" /></span></td>
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
                            	<td><span class="long-display"><s:property value="participant.version" /></span></td>
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
                        <i><%=contentMessage%> Execution is in progress, awaiting the resolution of the test cases below. Pressing
                            the Execute <%=contentMessage%> button again now, before the results of this ongoing execution have been
                            determined, will corrupt the test <%=contentMessage%> results. Press Refresh to view updated <%=contentMessage%> results.</i>
                    </div>
                </s:if>
                <div id="ActionMessagePlaceHolder"></div>
            </div>
            <s:if test="testScenario.scenario.initiatorInd == \"N\" and testScenario.executeOk">
                <div>
                    <table class="search-results">
                        <tr class="search-headers">
                         <!-- <td align="left"><b>Select the target gateway type before executing the Scenario:</b></td>
                              <td align="right">
                                <select  title ="Please select the target gateway version" name="targetVersion" id="targetVersion" theme="simple">
                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Axolotl" />">AXOLOTL 6</option>
                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Epic" />">EPIC</option>
                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_248" />" selected>CONNECT 2.4.8</option>
                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_31" />">CONNECT 3.1</option>
                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_32" />">CONNECT 3.2</option>
                                </select>
                             </td>  --> 
                            <td class="title-button">
                                <input type="button" class="inputButton" name="execute" id="execute"
                                       value="Execute <%=contentMessage%>"
                                       title="Pressing Execute <%=contentMessage%> will cause the Lab to initiate the Test Cases below, automatically in sequence."
                                       onclick="scenarioCaseExecution('<s:property value="testScenario.scenario.scenarioName" />', <s:property value="testScenario.scenario.scenarioId" />,<s:property value="selectedScenarioExecutionId"/>);" />
                            </td>
                        </tr>
                    </table>
                </div>
            </s:if>
            <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
                <div class="actionMessage content-area">
                    <b>The below listed Test Cases must be initiated by the NHIN Participant.
                        <s:if test="showRefreshWindowMsg != null" > <font color="red"><s:property value="showRefreshWindowMsg" /> </font> </s:if></b>
                </div>
            </s:if>
            <span class="collapsible-content">
                <table class="search-results">
                    <tr class="search-headers">
                        <th>&nbsp;</th>
                        <th>Test&nbsp;Case</th>
                        <th>Current&nbsp;Result</th>
                        <th>Description</th>
                        <th align="center" colspan="3">Action</th>
                    </tr>
                    <s:iterator value="testScenario.caseexecutions" status="testStatus">
                        <s:set name="testStatusIndex" value="#testStatus.index"/>
                        <s:hidden id ="testScenario.caseexecutionsSizeId" name="testScenario.caseexecutionsSize" value="%{testScenario.caseexecutions.size()}"/>
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
                                <input type="button" class="inputButton" name="execute" id="nhinspec" value="NHIN Spec"
                                       onclick="showTestCaseSpec(<s:property value="caseExecutionId" />);" />
                            </td>
                            <td class="value">
                                <s:a href="%{url}" title="Navigate to current result summary"><s:property escape="false" value="caseResultHtml" /></s:a>
                            </td>
                            <td align="left"><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                            <td width="10%" align="center" nowrap = "true">
                                <s:if test="testScenario.scenario.initiatorInd == \"N\" and testcase.messageType == \"QD\"">
                                    <s:label value="Parameters needed" /><br>
                                    <s:label name ="patientid" value="Patient ID:" />
                                    <s:if test="dependentCaseExecuted and responderPatientId != NULL and responderPatientId != ''">
                                        <s:textfield cssClass="inputText" id ="responderPatientId" name ="responderPatientId" size="20" maxlength="255" title="This value comes from %{testCaseNameMap[dependentCaseId]} execution." onclick="hideDependentCaseName('responderPatientId')" />
                                        <%-- <s:url id="url" action="LogEntries">
                                             <s:param name="selectedCaseExecutionId" value="%{depedentCaseexecution.caseExecutionId}" />
                                             <s:param name="selectedCaseName" value="%{depedentCaseexecution.testcase.name}" />
                                         </s:url>
                                         <span id ="dependentCaseName" >
                                             <font style="font-size: 1.8em"><sub>&#x21B5;</sub></font>
                                             <font style="font-size: 1.2em"><sup> <s:a href="%{url}" title="Navigate to %{testCaseNameMap[dependentCaseId]} testcase result summary.">From <s:property value="testCaseNameMap[dependentCaseId]"/></s:a></sup></font>
                                         </span>--%>
                                        <span id ="dependentCaseName" >
                                            <font style="font-size: 1.8em"><sub>&#x21B5;</sub></font>
                                            <font style="font-size: 1.2em"><sup><u>From <s:property value="testCaseNameMap[dependentCaseId]"/></u></sup></font>
                                        </span>
                                    </s:if>
                                    <s:elseif test = "responderPatientId != NULL and responderPatientId != ''" >
                                        <s:textfield cssClass="inputText" id ="responderPatientId" name ="responderPatientId" size="20" maxlength="255" title="This value comes from the previous execution." onclick="hideDependentCaseName('responderPatientId')" />
                                    </s:elseif>
                                    <s:else>
                                        <s:textfield cssClass="inputText" id ="responderPatientId" name ="responderPatientId" size="20" maxlength="255" title="Please enter the Patient ID or Execute %{testCaseNameMap[dependentCaseId]} test case first.Patient ID required format is: 'IDNumber^^&OIDofAA&ISO' An explicit example is: '543797436^^&1.2.840.113619.6.197&ISO'" />
                                    </s:else>
                                    <br>
                                </s:if>
                                <!-- added by Venkat for conformance RDR Start-->
                                <s:if test="testScenario.scenario.initiatorInd == \"N\" and testcase.messageType == \"RD\"">
                                    <s:set name="repositoryIdTitle" value="%{'Please enter the repository id.'}"/>
                                    <s:set name="documentIdTitle" value="%{'Please enter the document unique id.'}"/>
                                    <s:set name="prevExectitle" value="%{'This value comes from previous execution.'}"/>

                                    <table>
                                        <tr>
                                            <td>
                                                <table>
                                                    <tr><td align="center" colspan="2"><s:label value="Parameters needed" /></td></tr>
                                                    <s:hidden id="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocumentsSizeId" name="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocumentsSize" value="%{rdrResultdocuments.size()}"/>
                                                    <s:iterator value="rdrResultdocuments" status="resultStat">
                                                        <s:set name="resultIndex" value="#resultStat.index" />
                                                        <s:hidden  id="resultDocsCountId" name="resultDocsCount" value="%{resultStat.index}" />
                                                        <tr>
                                                            <td align="left">
                                                                <s:label name ="repositoryId" value="Repository ID:" />
                                                            </td>
                                                            <td>
                                                                <s:if test="dependentCaseExecuted">
                                                                    <s:textfield cssClass="inputText" id="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].repositoryId" name="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].repositoryId" value="%{repositoryId}" size="20" maxlength="255" title="This value comes from %{testCaseNameMap[dependentCaseId]} execution." onclick="hideDependentCaseName('repositoryId',this)"/>

                                                                </s:if>
                                                                <s:elseif test = "repositoryId != NULL and repositoryId != ''">
                                                                    <s:textfield cssClass="inputText" id="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].repositoryId" name="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].repositoryId" value="%{repositoryId}" size="20" maxlength="255" title="%{prevExectitle}" onclick="hideDependentCaseName('repositoryId',this)"/>
                                                                </s:elseif>
                                                                <s:else>
                                                                    <s:textfield cssClass="inputText" id="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].repositoryId" name="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].repositoryId" value="%{repositoryId}" size="20" maxlength="255" title="%{repositoryIdTitle}" onclick="hideDependentCaseName('repositoryId',this)"/>
                                                                </s:else>
                                                            </td>
                                                            <td align="left">
                                                                <s:if test="dependentCaseExecuted">
                                                                    <s:div id ="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].repositoryId.dependentCaseName" >
                                                                        <font style="font-size: 1.8em"><sub>&#x21B5;</sub></font>
                                                                        <font style="font-size: 1.2em"><sup> <u>From <s:property value="testCaseNameMap[dependentCaseId]"/></u></sup></font>
                                                                    </s:div>
                                                                </s:if>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td align="left">
                                                                <s:label name ="documentid" value="Document ID:" />
                                                            </td>
                                                            <td>
                                                                <s:if test="dependentCaseExecuted">
                                                                    <s:textfield cssClass="inputText"  id="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].documentUniqueId" name="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].documentUniqueId" value="%{documentUniqueId}" size="20" maxlength="255" title="This value comes from %{testCaseNameMap[dependentCaseId]} execution." onclick="hideDependentCaseName('documentUniqueId',this)"/>
                                                                </s:if>
                                                                <s:elseif test= "documentUniqueId != NULL and documentUniqueId != ''">
                                                                    <s:textfield cssClass="inputText"  id="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].documentUniqueId" name="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].documentUniqueId" value="%{documentUniqueId}" size="20" maxlength="255" title="%{prevExectitle}" onclick="hideDependentCaseName('documentUniqueId',this)" />
                                                                </s:elseif>
                                                                <s:else>
                                                                    <s:textfield cssClass="inputText"  id="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].documentUniqueId" name="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].documentUniqueId" value="%{documentUniqueId}" size="20" maxlength="255" title="%{documentIdTitle}" onclick="hideDependentCaseName('documentUniqueId',this)" />
                                                                </s:else>
                                                            </td>
                                                            <td align="left">
                                                                <s:if test="dependentCaseExecuted">
                                                                    <%-- <s:url id="url" action="LogEntries">
                                                                         <s:param name="selectedCaseExecutionId" value="%{depedentCaseexecution.caseExecutionId}" />
                                                                         <s:param name="selectedCaseName" value="%{depedentCaseexecution.testcase.name}" />
                                                                         <s:param name="selectedScenarioExecutionId" value="%{selectedScenarioExecutionId}" />
                                                                         <s:param name="conformanceDependent">true</s:param>
                                                                     </s:url>
                                                                     <span id ="dependentCaseName" >
                                                                         <font style="font-size: 1.8em"><sub>&#x21B5;</sub></font>
                                                                         <font style="font-size: 1.2em"><sup> <s:a href="%{url}" title="Navigate to %{testCaseNameMap[dependentCaseId]} testcase result summary.">From <s:property value="testCaseNameMap[dependentCaseId]"/></s:a></sup></font>
                                                                     </span>--%>

                                                                    <s:div id ="testScenario.caseexecutions[%{testStatusIndex}].rdrResultdocuments[%{resultIndex}].documentUniqueId.dependentCaseName" >
                                                                        <font style="font-size: 1.8em"><sub>&#x21B5;</sub></font>
                                                                        <font style="font-size: 1.2em"><sup> <u>From <s:property value="testCaseNameMap[dependentCaseId]"/></u></sup></font>
                                                                    </s:div>
                                                                </s:if>
                                                            </td>
                                                        </tr>
                                                    </s:iterator>

                                                </table>
                                            </td>

                                        </tr>
                                    </table>
                                </s:if>
                                <!-- added by Venkat for conformance RDR End -->
                                <s:if test="testScenario.scenario.initiatorInd == \"N\"">
                                    <input type="button" class="inputButton" name="executeTestCase" id="executeTestCase" value="Execute"
                                           title="Pressing Execute will cause the Lab to initiate the Test Case."
                                           onclick="testCaseExecution('<s:property value="testcase.name" />', <s:property value="testScenario.scenario.scenarioId" />, <s:property value="testcase.caseId" />, <s:property value="caseExecutionId" />,<s:property value="selectedScenarioExecutionId"/> , <s:property value="#testStatus.index"/>);" />
                                </s:if>
                                <s:if test="clearable" >
                                    <input type="button" class="inputButton" value="Clear Result" onclick="clearCaseResult('<s:property value="testcase.name" />',<s:property value="selectedScenarioExecutionId"/>, <s:property value="caseExecutionId" />);" />
                                </s:if>
                                <s:else>
                                    <input type="button" class="inputButtonDisabled" value="Clear Result" disabled="disabled" onclick="clearCaseResult('<s:property value="testcase.name" />',<s:property value="selectedScenarioExecutionId"/>, <s:property value="caseExecutionId" />);" />
                                </s:else>

                            </td>
                            <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
                                <td>
                                    <input type="button" class="inputButton"  name="executeTestCase" id="executeTestCase" value="Start Timer"
                                           title="Pressing Initiate Timed Window will cause the Lab to Start a New Timed Window To Execute the Test Case."
                                           onclick="javascript:showTimerContent('<s:property value="caseExecutionId" />','<s:property value="testcase.name" />','<s:property value="testScenario.scenario.scenarioName" />',<%=delay %>);" />
                                    <input type="button" class="inputButton" name="Search" id="Search" value="SearchMessages"
                                           onclick="showAuditMsg('<s:property value="caseExecutionId" />','<s:property value="testcase.name" />');" />
                                </td>
                            </s:if>

                        </tr>
                    </s:iterator>
                </table>
            </span>
        </div>

    </div>
</s:form>
<script type="text/javascript">
   // var value = window.setInterval("autoRefresh()", 30000);
    function clearCaseResult(testcaseName, selectedScenarioExecutionId, caseExecutionId) {
        if (confirm('Please confirm -- Clear Case Results for Test Case [' + testcaseName + ']?')) {
            var activeScenarioResultsForm = document.getElementById("ConfActiveScenarioResults");
            activeScenarioResultsForm.action = "<%=request.getContextPath()%>/participant/ClearCaseResult";
            activeScenarioResultsForm.selectedScenarioExecutionId.value = selectedScenarioExecutionId;
            activeScenarioResultsForm.caseExecutionId.value = caseExecutionId;
            activeScenarioResultsForm.submit();
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

    function scenarioCaseExecution(scenarioName, executeScenarioId, selectedScenarioExecutionId) {
        if(validateInputForTestGroup(scenarioName)){
           // var selectedVersion = document.getElementById("targetVersion").value;
            if (confirm('Please confirm - Execute Test Group [' + scenarioName + '] ?')) {
                clearActionMessagesList();
                addActionMessage('Test Group Execution is in progress. Pressing the Execute Scenario button again now, before the results of this ongoing execution have been determined, will corrupt the test scenario results. Press Refresh to view updated scenario results.');
                document.getElementById("ConfActiveScenarioResults").action = "<%=request.getContextPath()%>/participant/ExecuteTestCase";
                document.getElementById("ConfActiveScenarioResults").selectedScenarioId.value = executeScenarioId;
                document.getElementById("ConfActiveScenarioResults").selectedScenarioExecutionId.value = selectedScenarioExecutionId;
                document.getElementById("ConfActiveScenarioResults").selectedCaseId.value = "0";
                document.getElementById("ConfActiveScenarioResults").scenarioLevelExecution.value = "Y";

                switchContentPane();
                document.getElementById("ConfActiveScenarioResults").submit();
            }
        }
    }

    function testCaseExecution(testcaseName, executeScenarioId, executeCaseId, pCaseExecutionId, selectedScenarioExecutionId, testcaseIndex) {
        if(validateInputForEachTest(testcaseName,testcaseIndex)){
           // var selectedVersion = document.getElementById("targetVersion").value;
            if (confirm('Please confirm -- Execute Test Case [' + testcaseName + '] ?')) {
                clearActionMessagesList();

                document.getElementById("ConfActiveScenarioResults").action = "<%=request.getContextPath()%>/participant/ExecuteTestCase";
                document.getElementById("ConfActiveScenarioResults").selectedScenarioId.value = executeScenarioId;
                document.getElementById("ConfActiveScenarioResults").selectedScenarioExecutionId.value = selectedScenarioExecutionId;
                document.getElementById("ConfActiveScenarioResults").selectedCaseId.value = executeCaseId;
                document.getElementById("ConfActiveScenarioResults").scenarioLevelExecution.value = "N";

                switchContentPane();
                document.getElementById("ConfActiveScenarioResults").submit();
            }
        }
    }

    function switchContentPane() {
        document.getElementById("content").style.visibility="hidden" ;
        document.getElementById("content").style.height="0" ;
        document.getElementById("content").style.margin="0" ;
        document.getElementById("content").style.padding="0" ;
        document.getElementById("content-pleasewait").style.visibility="visible" ;
        document.getElementById("content-pleasewait").style.height="auto" ;
        document.getElementById("content-pleasewait").style.margin="0 10px 0 175px" ;
        document.getElementById("content-pleasewait").style.padding="20px" ;
    }


    function switchTimerPane(msg) {
        document.getElementById("content").style.visibility="hidden" ;
        document.getElementById("content").style.height="0" ;
        document.getElementById("content").style.margin="0" ;
        document.getElementById("content").style.padding="0" ;
        document.getElementById("content-pleasewait-startTimer").style.visibility="visible" ;
        document.getElementById("content-pleasewait-startTimer").style.height="auto" ;
        document.getElementById("content-pleasewait-startTimer").style.margin="0 10px 0 175px" ;
        document.getElementById("content-pleasewait-startTimer").style.padding="20px" ;
        document.getElementById("content-pleasewait-startTimer").innerHTML=msg;
    }

    function showTimerContent(caseExecutionId, caseName,scenarioName,timedelay){
        //alert(timedelay);
        var start = new Date();
        //var endtime =  parseInt(new Date().getTime())+ parseInt(delayTime) ;
        var endtime =  parseInt(new Date().getTime())+ parseInt(timedelay) ;
        start= start.format("yyyy-mm-dd HH:MM:ss");
        var end = new Date(endtime);
        end = end.format("yyyy-mm-dd HH:MM:ss");
       // delayTime = Math.round(delayTime*10/60000)/10;

       delayTime = Math.round(timedelay*10/60000)/10;
        var str1='<p><b>'+scenarioName+' - Test Case Timed Window <font color="red">'+ delayTime+' min(s) </font>is on!<BR/> The start time <font color="red">'+start+'</font> and End Time <font color="red">'+end+'</font></b></p>';
        var str2='<hr/><p class="errorMessage"><b>This page will automatically refresh to show  Messages if any upon completion of the Timed Window execution.</b></p>';
        switchTimerPane(str1+str2);
         
        document.forms[0].action= "StartTimedWindow?selectedCaseExecutionId=" + caseExecutionId + "&selectedCaseName=" + caseName ;
        document.forms[0].submit();
    }
    function showAuditMsg(caseExecutionId, caseName){
        document.forms[0].action= "ShowActionMessages?selectedCaseExecutionId=" + caseExecutionId + "&selectedCaseName=" + caseName ;
        document.forms[0].submit();
    }
    function returnToActiveScenarios() {
        document.getElementById("ConfActiveScenarioResults").action = "<%=request.getContextPath()%>/participant/ActiveTestResults";
        document.getElementById("ConfActiveScenarioResults").submit();
    }

    var displayTimedMessageWindow;
    function displayTimedWindowMessage(caseExecutionId,caseName){
        // alert("I am in displayTimedWindowMessage");
        var urlString = "<%=request.getContextPath()%>/participant/PopTWMessages?selectedCaseExecutionId=" + caseExecutionId + "&selectedCaseName=" + caseName;
        var stdOptions = "resizable=yes,directories=no,left=80,top=80,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        displayTimedMessageWindow = window.open(urlString, "displayTimedMessageWindow" ,options);
        if (window.focus) {
            displayTimedMessageWindow.focus();
        }

    }
    function addActionMessage(messageText) {
        var actionMessagesPlaceHolderDiv = document.getElementById('ActionMessagePlaceHolder');
        var msgDiv = document.createElement('div');
        msgDiv.setAttribute('id', 'AddedActionMessage');
        msgDiv.innerHTML = '<i>' + messageText + '</i>';
        actionMessagesPlaceHolderDiv.parentNode.insertBefore(msgDiv, actionMessagesPlaceHolderDiv);
    }

    function clearActionMessagesList() {
        var actionMessagesListDiv = document.getElementById('ActionMessagesList');
        if (actionMessagesListDiv != undefined) {
            actionMessagesListDiv.parentNode.removeChild(actionMessagesListDiv);
        }
        var addedActionMessageDiv = document.getElementById('AddedActionMessage');
        if (addedActionMessageDiv != undefined) {
            addedActionMessageDiv.parentNode.removeChild(addedActionMessageDiv);
        }
    }

    function downloadLogMessage(pTestHarnessId, pAuditRepositoryId){
        var urlString = "<%=request.getContextPath()%>/help/DownloadAuditExtensionMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
        var stdOptions = "resizable=yes,directories=no,left=100,top=40,menubar=yes,location=no";
        var options = stdOptions + ",width=800,height=600,scrollbars=yes";
        window.open(urlString, "MessageContentWindow" ,options);
        return false;
    }

    function hideDependentCaseName(id,thisObj) {
        //alert(thisObj.name);
        var titleValue ="";
        if(id == "responderPatientId"){
            titleValue = "Please enter the Patient ID. Patient ID required format is: 'IDNumber^^^&OIDofAA&ISO' An explicit example is: '543797436^^^&1.2.840.113619.6.197&ISO'";
        }else if(id == "documentUniqueId"){
            titleValue = "Please enter the document unique id.";
        }else{
            titleValue = "Please enter the repository id.";
        }
        if(thisObj!=null){
            thisObj.title = titleValue;
            var  depedentCaseName = document.getElementById("" + thisObj.name.toString() + ".dependentCaseName");
            if(depedentCaseName!=undefined && depedentCaseName!=null)
                depedentCaseName.style.visibility="hidden" ;
        }
        else{
            document.getElementById(id).title = titleValue;
            document.getElementById("dependentCaseName").style.visibility="hidden" ;
        }
        // document.getElementById("content-pleasewait-startTimer").style.visibility="visible" ;
    }

    function validateInputForTestGroup(scenarioName){
        //  alert(caseName);
        if(scenarioName == "Query for Documents Responder"){
            return validateInputForQD();
        }else if(scenarioName == "Retrieve Documents Responder"){
            return validateInputForRD();
        }else{
            return true;
        }
    }
	function validateInputForEachTest(caseName,testcaseIndex){
        //  alert(caseName);
        if(caseName.indexOf("QDR") >= 0 ){
            return validateInputForQD();
        }else if(caseName.indexOf("RDR") >= 0 ){
            return validateInputForRD(caseName,testcaseIndex);
        }else{
            return true;
        }
    }
	
	function validateInputForQD(){
        var responderPatientId = document.getElementById("responderPatientId");
        if(responderPatientId!=undefined && responderPatientId!=null && responderPatientId.value ==""){
            alert("Please enter the patient id or execute the dependent PDR testcase first.");
            return false;
        }else{
        	var inputTxt = new String(responderPatientId.value);
    	//	alert(inputTxt.split("&"));
    	//	alert("the size of array :" + inputTxt.split("&").length);
        	if(inputTxt.indexOf("^^^") != -1 && inputTxt.split("&").length == 3){        		
	            return true;
	        }else{
	        	alert("Patient Id '"+inputTxt+"' doesn't match the required format. Please enter the patient id in this format: 'IDNumber^^^&OIDofAA&ISO'");
	            return false;
	        }
        }
    }
    /*
     * used to validate all case execution or specific case execution for RD
     */
    function validateInputForRD(caseName , testcaseIndex ){
        
        var caseExeIndex= -1 ;
        var isQDRValidated = true;
		var  caseexecutionsSizeId = document.getElementById("testScenario.caseexecutionsSizeId");
        caseexecutionsSize = caseexecutionsSizeId.value ;
        if(testcaseIndex==null || testcaseIndex== undefined){ 
			caseExeIndex= 0 ;			
			for( caseExeIndex ; caseExeIndex < caseexecutionsSize ; caseExeIndex++){
				
				isQDRValidated = validateInputForRDTestcase(caseName,caseExeIndex);
				if(!isQDRValidated){
					break;
				}
			}
			 
        }
        else{
            caseExeIndex = testcaseIndex; 
			isQDRValidated = validateInputForRDTestcase(caseName,caseExeIndex);			
        }
        
        return isQDRValidated;
    }
	
	function validateInputForRDTestcase(caseName,caseExeIndex){
		var isQDRTestCaseValidated = true;
		

            var  rdrResultdocumentsSizeId = document.getElementById("testScenario.caseexecutions[" + caseExeIndex +  "].rdrResultdocumentsSizeId");
            for(var i = 0 ; i < rdrResultdocumentsSizeId.value ; i ++)
            {
                var repositoryId = document.getElementById("testScenario.caseexecutions[" + caseExeIndex +  "].rdrResultdocuments[" + i + "].repositoryId");
                var documentUniqueId = document.getElementById("testScenario.caseexecutions[" + caseExeIndex +  "].rdrResultdocuments[" + i + "].documentUniqueId");
                if(repositoryId!=undefined && repositoryId!=null && repositoryId.value==""  || documentUniqueId!=undefined && documentUniqueId!=null && documentUniqueId.value=="" )
                {
                    var alertMessage="Please enter repository id, document id or execute the dependent QDR testcase first." ; 
					if(caseName != null && caseName!= undefined){
						alertMessage = "Please enter repository id, document id for test case : " + caseName + " or execute the dependent QDR testcase first." ; 
					}
					alert(alertMessage);
                    isQDRTestCaseValidated = false;
                    break;
                }
            }
			return isQDRTestCaseValidated ; 
        }
	
     function autoRefresh(){
       document.getElementById("ConfActiveScenarioResults").submit();
    }

</script>
