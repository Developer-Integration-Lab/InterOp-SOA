<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:form action="ScenarioCases" theme="simple">
    <s:hidden key="selectedScenarioExecutionId" />
    <s:hidden key="selectedScenarioId" />
    <s:hidden key="selectedCaseId" />
    <s:hidden key="scenarioLevelExecution" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Lab Test Case Execution</span></td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="return" id="return" value="Return"
                               onclick="returnToActiveScenarios();" />
                        <input type="submit" class="inputButton" name="refresh" id="refresh" value="Refresh"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                <s:if test="#attr.headerType == \"Scenario\" "> Test <s:property value="#attr.headerType"/></s:if>
                <s:else><s:property value="#attr.headerType"/></s:else>
            </div>
            <table class="content-data">
                <tr>
                    <td><label class="short">Name:</label></td>
                    <td>
                        <s:property value="testScenario.scenario.scenarioName" />&nbsp;&minus;&nbsp;<u><s:property value="testScenario.scenario.description" /></u>
                    </td>
                </tr>
                <tr>
                    <td><label class="short">Execution Unique Identifier:</label></td>
                    <td><s:property value="testScenario.executionUniqueId" /></td>
                </tr>
                <tr>
                    <td><label class="short">Activated on:</label></td>
                    <td><s:property value="testScenario.scheduleString" /></td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                Test Results
            </div>
            <div id="ActionMessagesContainer">
                <div id="ActionMessagesList">
                    <strong style="color: #00f"><s:actionmessage/></strong>
                </div>
                <table class="search-results">
                    <tr class="search-headers">
                     <!--   <td align="left"><b>Select the target gateway type before executing the Scenario:</b></td>
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
                                   value="Execute Scenario"
                                   title="Pressing Execute Scenario will cause the Lab to initiate the Test Cases below, automatically in sequence."
                                   onclick="scenarioCaseExecution('<s:property value="testScenario.scenario.scenarioName" />', <s:property value="testScenario.scenario.scenarioId" />,<s:property value="selectedScenarioExecutionId"/>);" />
                        </td>
                    </tr>
                </table>
            </div>
            <table class="test-results">
                <tr>
                    <th>Test&nbsp;Case</th>
                    <th>Description</th>
                    <th>Configuration</th>
                    <th>&nbsp;</th>
                </tr>
                <s:iterator value="testScenario.caseexecutions" status="testStatus" var="case_execs">
                    <tr class="testable-event <s:if test="#testStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td><strong><s:property value="testcase.name" /></strong></td>
                        <td><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                        <td>
                            <s:select name="%{#case_execs.caseExecutionId}"
                                      list="altscenariocases"
                                      listKey="altscenariocaseId"
                                      listValue="alternateName"
                                      theme="simple" />
                        </td>
                        <td class="title-button">
                            <s:if test="testcase.messageType == \"AD\"">
                                &nbsp;
                            </s:if>
                            <s:elseif test="testcase.messageType == \"SP\"">
                                <input type="button" class="inputButton" name="discover" id="discover" value="Discover"
                                       onclick="discoverSupplemental('<s:property value="testcase.name" />', <s:property value="testScenario.scenario.scenarioId" />, <s:property value="testcase.caseId" />);" />
                            </s:elseif>
                            <s:else>
                                <input type="button" class="inputButton" name="execute" id="execute" value="Execute"
                                       onclick="caseExecution('<s:property value="testcase.name" />', <s:property value="testScenario.scenario.scenarioId" />, <s:property value="testcase.caseId" />, <s:property value="caseExecutionId" />);" />
                            </s:else>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>

    </div>
</s:form>
<script type="text/javascript">
    function discoverSupplemental(testcaseName, executeScenarioId, executeCaseId) {
        if (confirm('Please confirm -- Discover Supplemental Test Case [' + testcaseName + ']?')) {
            document.getElementById("ScenarioCases").action = "<%=request.getContextPath()%>/test/ExecuteTestCase";
            document.getElementById("ScenarioCases").selectedScenarioId.value = executeScenarioId;
            document.getElementById("ScenarioCases").selectedCaseId.value = executeCaseId;
            document.getElementById("ScenarioCases").submit();
        }
    }

    function scenarioCaseExecution(scenarioName, executeScenarioId, selectedScenarioExecutionId) {
        //var selectedVersion = document.getElementById("targetVersion").value;
        if (confirm('Please confirm -- Execute Test Scenario [' + scenarioName + '] ?')) {
            //  clearActionMessagesList();
            //  addActionMessage('Test Scenario Execution is in progress. Pressing the Execute Scenario button again now, before the results of this ongoing execution have been determined, will corrupt the test scenario results. Press Refresh to view updated scenario results.');
            document.getElementById("ScenarioCases").action = "<%=request.getContextPath()%>/test/ExecuteTestCase";
            document.getElementById("ScenarioCases").selectedScenarioId.value = executeScenarioId;
            document.getElementById("ScenarioCases").selectedScenarioExecutionId.value = selectedScenarioExecutionId;
            document.getElementById("ScenarioCases").selectedCaseId.value = "0";
            document.getElementById("ScenarioCases").scenarioLevelExecution.value = "Y";

            //  switchContentPane();
            document.getElementById("ScenarioCases").submit();
        }
    }

    function caseExecution(testcaseName, executeScenarioId, executeCaseId, pCaseExecutionId) {
        // Note 1: The string "ScenarioCases_" is prepended by the struts2 framework.
        // Note 2: The parameter pCaseExecutionId is used because the correct drop-down list can then be used.
        var objConfigDropDownSelectedByUser = document.getElementById("ScenarioCases_"+pCaseExecutionId);

        // Note: The following value can be, 0 or 1 or 2 or 3, ... and so on.
        // If the following value is 0, "happy path" scenariocase config file will be read from database in the later part of code.
        // Otherwise If the following value is not 0, the corresponding altscenariocase config file will be read from database in the later part of code.
        var valueOf_objConfigDropDownSelectedByUser = objConfigDropDownSelectedByUser.options[objConfigDropDownSelectedByUser.selectedIndex].value;
        // the following 'text' is redundant to read here. Being added here as code, only because if debugging becomes necessary.
        var textOf_objConfigDropDownSelectedByUser = objConfigDropDownSelectedByUser.options[objConfigDropDownSelectedByUser.selectedIndex].text;
           
      //  var selectedVersion = document.getElementById("targetVersion").value;
        if (confirm('Please confirm -- Execute Test Case [' + testcaseName + '] ?')) {
            clearActionMessagesList();
            document.getElementById("ScenarioCases").action = "<%=request.getContextPath()%>/test/ExecuteTestCase?selectedAltScenarioCaseId="+valueOf_objConfigDropDownSelectedByUser;
            document.getElementById("ScenarioCases").selectedScenarioId.value = executeScenarioId;
            document.getElementById("ScenarioCases").selectedCaseId.value = executeCaseId;
            document.getElementById("ScenarioCases").scenarioLevelExecution.value = "N";
            document.getElementById("ScenarioCases").submit();
        }
    }

    function returnToActiveScenarios() {
        document.getElementById("ScenarioCases").action = "<%=request.getContextPath()%>/test/ActiveScenarios";
        document.getElementById("ScenarioCases").submit();
    }

    var displayDocumentsWindow;
    function displayDocuments() {
        var urlString = "<%=request.getContextPath()%>/test/DisplayDocuments";
        var stdOptions = "resizable=yes,directories=no,left=80,top=80,toolbar=no,location=no";
        var options = stdOptions + ",width=750,height=550,scrollbars=yes";
        displayDocumentsWindow = window.open(urlString, "displayDocumentsWindow" ,options);
        if (window.focus) {
            displayDocumentsWindow.focus();
        }
    }

    function clearActionMessagesList() {
        var actionMessagesListDiv = document.getElementById('ActionMessagesList');
        if (actionMessagesListDiv != undefined) {
            actionMessagesListDiv.parentNode.removeChild(actionMessagesListDiv);
        }
    }
</script>