<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/date.format.js"></script>
<%
            User user = ((User) session.getAttribute("userProfile"));
            LabType labType = user.getLabType();
            if (labType == null) {
                labType = LabType.LAB;
            }
            int labMode = labType.getId();
            String testContainerLabel = "Test " + labType.getType(labMode);
            if (LabType.CONFORMANCE == labType) {
                testContainerLabel = labType.getType(labMode);
            }
            String contentMessage = (testContainerLabel.contains("Group") ? testContainerLabel + "/Case" : testContainerLabel);
            request.setAttribute("labType", labType);
            request.setAttribute("headerType", labType.getType(labMode));
%>

<div id="content-pleasewait" style="visibility: hidden; height: 0">
    <p><b><s:property value="testScenario.scenario.scenarioName" /> - <%=contentMessage%> Execution in progress!</b></p>
    <hr/>
    <p class="errorMessage"><b>This page will automatically refresh to show <%=contentMessage%> results upon completion of the <%=contentMessage%> execution.</b></p>
    <p class="actionMessage"><b><i>Note: Attempting to press the Execute button again now, before the results of this ongoing execution have been determined, may corrupt the <%=contentMessage%> results.</i></b></p>
</div>

<%-- <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
    <div id="content-pleasewait" style="visibility: hidden; height: 0">
        <p><b><s:property value="testScenario.scenario.scenarioName" /> - <%=contentMessage%> Execution in progress!</b></p>
        <hr/>
        <p class="errorMessage"><b>This page will automatically refresh to show <%=contentMessage%> results upon completion of the <%=contentMessage%> execution.</b></p>
        <p class="actionMessage"><b><i>Note: Attempting to press the Execute button again now, before the results of this ongoing execution have been determined, may corrupt the <%=contentMessage%> results.</i></b></p>
    </div>
</s:if> --%>
<s:form action="ActiveScenarioResults" theme="simple">
    <s:hidden key="selectedScenarioExecutionId" />
    <s:hidden key="selectedScenarioId" />
    <s:hidden key="selectedCaseId" />
    <s:hidden name="caseExecutionId" />
    <s:hidden name="scenarioLevelExecution" value="N"/>
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Active <s:property value="#attr.headerType"/> Results</span></td>
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
                <%=testContainerLabel%>
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
                        <i><%=testContainerLabel%> Execution is in progress, awaiting the resolution of the test cases below. Pressing
                            the Execute <%=testContainerLabel%> button again now, before the results of this ongoing execution have been
                            determined, will corrupt the test <%=testContainerLabel%> results. Press Refresh to view updated <%=testContainerLabel%> results.</i>
                    </div>
                </s:if>
                <div id="ActionMessagePlaceHolder"></div>
            </div>
            <s:if test="testScenario.scenario.initiatorInd == \"N\" and testScenario.executeOk">
                
                <div>
                    <table class="search-results">
                        <tr class="search-headers">
                        
                        	<s:if test="testScenario.scenarioHidden">
	                        	 
	                        	 <td align="left">
		                        	 <s:iterator value="testScenario.caseexecutions" status="testStatus">
		                        	 	<b> Note : The dependent test case <s:property value="depedentCaseexecution.testcase.name"/> in scenario <s:property value="dependentScenarioId"/> needs to be successfully executed.</b>
		                        	 </s:iterator>
	                        	</td>
	                        	<td class="title-button">
	                                <input type="button" class="inputButtonDisabled" name="execute" id="execute" disabled="disabled"
	                                       value="Execute <%=testContainerLabel%>"
	                                       title="Pressing Execute <%=testContainerLabel%> will cause the Lab to initiate the Test Cases below, automatically in sequence."
	                                       />
	                            </td>
                        	</s:if>
                        
	                         <s:else>
	                         <!--  <td align="left"><b>Select the target gateway type before executing the Scenario:</b></td>
	                              <td align="right">
	                                <select  title ="Please select the target gateway version" name="targetVersion" id="targetVersion" theme="simple">
	                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Axolotl" />">AXOLOTL 6</option>
	                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Epic" />">EPIC</option>
	                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_248" />" selected>CONNECT 2.4.8</option>
	                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_31" />">CONNECT 3.1</option>
	                                    <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_32" />">CONNECT 3.2</option>
	                                </select>
	                             </td> -->
								
	                            <td class="title-button">
	                                <input type="button" class="inputButton" name="execute" id="execute"
	                                       value="Execute <%=testContainerLabel%>"
	                                       title="Pressing Execute <%=testContainerLabel%> will cause the Lab to initiate the Test Cases below, automatically in sequence."
	                                       onclick="scenarioCaseExecution('<s:property value="testScenario.scenario.scenarioName" />', <s:property value="testScenario.scenario.scenarioId" />,<s:property value="selectedScenarioExecutionId"/>);" />
	                            </td>
	                            </s:else>
                        </tr>
                    </table>
                </div>
            </s:if>
            <s:if test="testScenario.scenario.initiatorInd == \"Y\"">
                <div class="actionMessage content-area">
                    <b>The below listed Test Cases must be initiated by the NHIN Participant. Refresh the page to view updated test results</b>
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
                            <td width="10%" align="center">
                                <s:if test="testcase.messageType == \"AD\"">

                                    <input type="button" class="inputButton" name="attach" id="attach" value="Attach Document"
                                           onclick="attachDocument('<s:property value="caseExecutionId" />', '<s:property value="testScenario.scenario.scenarioId" />');" />
                                    <input type="button" class="inputButton" name="show" id="show" value="Show Attachments"
                                           onclick="showAttachments('<s:property value="caseExecutionId" />', '<s:property value="testcase.name" />');" />
                                </s:if>
                                <s:elseif test="testcase.messageType == \"SP\"">
                                    <input type="button" class="inputButton" name="discover" id="discover" value="Discover"
                                           onclick="discoverSupplemental('<s:property value="testcase.name" />', <s:property value="testScenario.scenario.scenarioId" />, <s:property value="testcase.caseId" />);" />
                                </s:elseif>
                                <s:else>
                                    <input type="button" class="inputButton" name="execute" id="nhinspec" value="NHIN Spec"
                                           onclick="showTestCaseSpec(<s:property value="caseExecutionId" />);" />
                                </s:else>
                            </td>
                            <td nowrap="nowrap">
                                <s:if test="testScenario.scenario.initiatorInd == \"N\"">
                                    <s:if test="@net.aegis.lab.util.LabConstants$LabType@CONFORMANCE.equals(#attr.labType)">
                                        <input type="button" class="inputButton" name="executeTestCase" id="executeTestCase" value="Execute"
                                               title="Pressing Execute will cause the Lab to initiate the Test Case."
                                               onclick="testCaseExecution('<s:property value="testcase.name" />', <s:property value="testScenario.scenario.scenarioId" />, <s:property value="testcase.caseId" />, <s:property value="caseExecutionId" />,<s:property value="selectedScenarioExecutionId"/>);" />

                                    </s:if>
                                </s:if>

                                <s:if test="clearable" >
                                    <input type="button" class="inputButton" value="Clear Result" onclick="clearCaseResult('<s:property value="testcase.name" />',<s:property value="selectedScenarioExecutionId"/>, <s:property value="caseExecutionId" />);" />
                                </s:if>
                                <s:else>
                                    <input type="button" class="inputButtonDisabled" value="Clear Result" disabled="disabled" onclick="clearCaseResult('<s:property value="testcase.name" />',<s:property value="selectedScenarioExecutionId"/>, <s:property value="caseExecutionId" />);" />
                                </s:else>
                            </td>

                          <%--   <s:if test="@net.aegis.lab.util.LabConstants$LabType@CONFORMANCE.equals(#attr.labType) and testScenario.scenario.initiatorInd == \"Y\"">
                                <td>
                                    <input type="button" class="inputButton"  name="executeTestCase" id="executeTestCase" value="Start Timer"
                                           title="Pressing Initiate Timed Window will cause the Lab to Start a New Timed Window To Execute the Test Case."
                                           onclick="javascript:showTimerContent('<s:property value="caseExecutionId" />','<s:property value="testcase.name" />','<s:property value="delay" />','<s:property value="testScenario.scenario.scenarioName" />');" />
                                    <input type="button" class="inputButton" name="Search" id="Search" value="SearchMessages"
                                           onclick="showAuditMsg('<s:property value="caseExecutionId" />','<s:property value="testcase.name" />');" />
                                </td>
                            </s:if> --%>

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
   // var value = window.setInterval("autoRefresh()", 30000);
    function attachDocument(caseExecutionId, pScenarioId) {
        var urlString = "<%=request.getContextPath()%>/participant/AttachDocument?selectedCaseExecutionId=" + caseExecutionId + "&selectedScenarioId=" + pScenarioId;
        var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        attachDocWindow = window.open(urlString, "attachDocumentWindow" ,options);
        if (window.focus) {
            attachDocWindow.focus();
        }
    }

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
            document.getElementById("ActiveScenarioResults").action = "<%=request.getContextPath()%>/participant/ExecuteTestCase";
            document.getElementById("ActiveScenarioResults").selectedScenarioId.value = executeScenarioId;
            document.getElementById("ActiveScenarioResults").selectedCaseId.value = executeCaseId;
            document.getElementById("ActiveScenarioResults").submit();
        }
    }

    function clearCaseResult(testcaseName, selectedScenarioExecutionId, caseExecutionId) {
        if (confirm('Please confirm -- Clear Case Results for Test Case [' + testcaseName + ']?')) {
            var activeScenarioResultsForm = document.getElementById("ActiveScenarioResults");
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
      //  var selectedVersion = document.getElementById("targetVersion").value;
        if (confirm('Please confirm -- Execute Test Scenario [' + scenarioName + '] ?')) {
            clearActionMessagesList();
            addActionMessage('Test Scenario Execution is in progress. Pressing the Execute Scenario button again now, before the results of this ongoing execution have been determined, will corrupt the test scenario results. Press Refresh to view updated scenario results.');
            document.getElementById("ActiveScenarioResults").action = "<%=request.getContextPath()%>/participant/ExecuteTestCase";
            document.getElementById("ActiveScenarioResults").selectedScenarioId.value = executeScenarioId;
            document.getElementById("ActiveScenarioResults").selectedScenarioExecutionId.value = selectedScenarioExecutionId;
            document.getElementById("ActiveScenarioResults").selectedCaseId.value = "0";
            document.getElementById("ActiveScenarioResults").scenarioLevelExecution.value = "Y";

            switchContentPane();
            document.getElementById("ActiveScenarioResults").submit();
        }
    }

    function autoRefresh(){
       document.getElementById("ActiveScenarioResults").submit();
    }

    function testCaseExecution(testcaseName, executeScenarioId, executeCaseId, pCaseExecutionId, selectedScenarioExecutionId) {
        if (confirm('Please confirm -- Execute Test Case [' + testcaseName + ']?')) {
            clearActionMessagesList();

            document.getElementById("ActiveScenarioResults").action = "<%=request.getContextPath()%>/participant/ExecuteTestCase";
            document.getElementById("ActiveScenarioResults").selectedScenarioId.value = executeScenarioId;
            document.getElementById("ActiveScenarioResults").selectedScenarioExecutionId.value = selectedScenarioExecutionId;
            document.getElementById("ActiveScenarioResults").selectedCaseId.value = executeCaseId;
            document.getElementById("ActiveScenarioResults").scenarioLevelExecution.value = "N";

            switchContentPane();
            document.getElementById("ActiveScenarioResults").submit();

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

    function showAuditMsg(caseExecutionId, caseName){
        document.forms[0].action= "ShowActionMessages?selectedCaseExecutionId=" + caseExecutionId + "&selectedCaseName=" + caseName;
        document.forms[0].submit();
    }
    function returnToActiveScenarios() {
        document.getElementById("ActiveScenarioResults").action = "<%=request.getContextPath()%>/participant/ActiveTestResults";
        document.getElementById("ActiveScenarioResults").submit();
    }

    var displayDocumentsWindow;
    function displayDocuments() {
        var urlString = "<%=request.getContextPath()%>/participant/DisplayDocuments";
        var stdOptions = "resizable=yes,directories=no,left=80,top=80,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        displayDocumentsWindow = window.open(urlString, "displayDocumentsWindow" ,options);
        if (window.focus) {
            displayDocumentsWindow.focus();
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

</script>