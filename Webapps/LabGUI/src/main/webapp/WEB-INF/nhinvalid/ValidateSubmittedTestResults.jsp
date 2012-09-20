<%-- 
    Document    : ValidateSubmittedTestResults.jsp
    Requirement : Nhin Validator can validate submitted test results (scenarios).
                  This is a decision making ability of the Nhin Validator.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ValidateSubmittedTestResults" theme="simple">
        <s:hidden key="validateAction" />
        <s:hidden key="entireValidationCommentsString" />
        <s:hidden key="checkedListOfScenariosValidatedByNhinValidator" />
        <s:hidden key="checkedListOfScenariosFailedByNhinValidator" />
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td>
                            <span class="content-title-text">
                                <%-- Service Set:
                                <s:property value="executionUniqueId" /> - --%>Validate Submitted Test Results
                            </span>
                        </td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="save" id="save" value="Save Changes"
                                   title="Validate or fail the selected scenario(s) along with any validation comments"
                                   onclick="validateOrFailScenarios('<s:property value="executionUniqueId" />');"/>
                            <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                                   title="Refresh associated scenario(s)"
                                   onclick="refreshAssociatedScenarios('<s:property value="executionUniqueId" />');" />
                            <input type="button" class="inputButton" name="export" id="export" value="Export"
                                   title="Export Request/Response messages for submitted test results"
                                   onclick="ExportFiles();" />
                        </td>
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
                    <s:property value="#attr.nhinvalidHeaderType"/> Details
                </div>
                <table class="search-results">
                    <tr class="search-results">
                        <th><font style="font-size:10pt; color:green;">Valid</font></th>
                        <th><font style="font-size:10pt; color:red;">Fail</font></th>
                        <th><s:property value="#attr.nhinvalidHeaderType"/></th>
                        <th>Results</th>
                        <th>Submitted</th>
                        <th>&nbsp;</th>
                    </tr>

<s:iterator value="scenarioexecutions" status="scenarioExecStatus">
                    <tr class="<s:if test="#scenarioExecStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td width="5%">
                            <s:if test="status == \"VALIDATED\"">
                                <s:checkbox cssClass="inputCheckbox"
                                            name="validInd"
                                            fieldValue="~%{scenarioExecutionId}"
                                            disabled="false"
                                            title="Validate this scenario %{scenario.scenarioId}"
                                            checked="checked"
                                            onclick="confirmEitherValidOrFail(%{scenarioExecutionId}, 'valid');" />&nbsp;
                            </s:if>
                            <s:else>
                                <s:checkbox cssClass="inputCheckbox"
                                            name="validInd"
                                            fieldValue="~%{scenarioExecutionId}"
                                            disabled="false"
                                            title="Validate this scenario %{scenario.scenarioId}"
                                            onclick="confirmEitherValidOrFail(%{scenarioExecutionId}, 'valid');" />&nbsp;
                            </s:else>
                        </td>
                        <td width="5%">
                            <s:if test="status == \"FAILED\"">
                                <s:checkbox cssClass="inputCheckbox"
                                            name="failInd"
                                            fieldValue="~%{scenarioExecutionId}"
                                            disabled="false"
                                            title="Fail this scenario %{scenario.scenarioId}"
                                            checked="checked"
                                            onclick="confirmEitherValidOrFail(%{scenarioExecutionId}, 'fail');" />&nbsp;
                            </s:if>
                            <s:else>
                                <s:checkbox cssClass="inputCheckbox"
                                            name="failInd"
                                            fieldValue="~%{scenarioExecutionId}"
                                            disabled="false"
                                            title="Fail this scenario %{scenario.scenarioId}"
                                            onclick="confirmEitherValidOrFail(%{scenarioExecutionId}, 'fail');" />&nbsp;
                            </s:else>
                        </td>
                        <td align="left" width="10%">
                            <a href="<%=request.getContextPath()%>/nhinvalid/SubmittedScenarioResults?scenarioExecutionId=<s:property value="scenarioExecutionId" />" title="<s:property value="scenario.description" />">
                                Scenario <s:property value="scenario.scenarioId" />
                            </a>
                        </td>
                        <td align="center" width="10%">
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
                    </tr>
                    <tr class="<s:if test="#scenarioExecStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td colspan="2" align="left">
                            <div style="font-size:8pt;" id="validationCmnts_<s:property value="#scenarioExecStatus.index" />">
                                Validation comments: <br /> <i>(limit to 255 chars)</i>
                            </div>
                        </td>
                        <td colspan="4">
                            <s:textfield name="validateComments" style="width:99%;" maxlength="255"
                                         value="%{validationComments}"
                                         onfocus="checkValidationComments(%{scenarioExecutionId});" />
                        </td>
                    </tr>
</s:iterator>

                </table>
            </div>

        </div>
    </s:form>
    <script type="text/javascript">
        var helpWindow;
        function showTestCaseHelp(scenarioExecutionId) {
            var urlString = "<%=request.getContextPath()%>/nhinvalid/TestCaseHelp?scenarioExecutionId=" + scenarioExecutionId;
            var stdOptions = "resizable=yes,directories=no,left=1,top=1,toolbar=no,location=no";
            var options = stdOptions + ",width=750,height=550,scrollbars=yes";
            helpWindow = window.open(urlString, "testCaseHelpWindow" ,options);
            if (window.focus) {
                helpWindow.focus();
            }
        }

        // *********************************************************************
        // Make a list of scenarios that are validated by the nhin validator.
        // Note: This list will NOT overlap with the list of failed scenarios.
        // *********************************************************************
        function create_list_of_validated_scenarios() {
            var vs_value = "";
            for (var i=0; i < document.ValidateSubmittedTestResults.validInd.length; i++) {
                if (document.ValidateSubmittedTestResults.validInd[i].checked) {
                    vs_value = vs_value + document.ValidateSubmittedTestResults.validInd[i].value;
                }
            }
            return vs_value;
        }

        // *********************************************************************
        // Make a list of scenarios that are failed by the nhin validator.
        // Note: This list will NOT overlap with the list of validated scenarios.
        // *********************************************************************
        function create_list_of_failed_scenarios() {
            var fs_value = "";
            for (var i=0; i < document.ValidateSubmittedTestResults.failInd.length; i++) {
                if (document.ValidateSubmittedTestResults.failInd[i].checked) {
                    fs_value = fs_value + document.ValidateSubmittedTestResults.failInd[i].value;
                }
            }
            return fs_value;
        }


        // *********************************************************************
        // Create an entire validation comments string for this page.
        // *********************************************************************
        function create_entire_validation_comments_string() {
            var v_value = "";
            for (var i=0; i < document.ValidateSubmittedTestResults.validateComments.length; i++) {
                v_value = v_value + document.ValidateSubmittedTestResults.validateComments[i].value + document.ValidateSubmittedTestResults.validInd[i].value + "|";
            }
            return v_value;
        }

        // *********************************************************************
        // Comments will make sense only when a decision of either valid or
        // fail is made by the logged in nhin validator.
        // *********************************************************************
        function checkValidationComments(pExecutionUniqueId) {
            var isValidChecked = false;
            var isFailChecked = false;

            //for (var i=0; i < document.ValidateSubmittedTestResults.validInd.length; i++) {
            //    if ((document.ValidateSubmittedTestResults.validInd[i].checked) &&
            //        (document.ValidateSubmittedTestResults.validInd[i].value == ("~"+pExecutionUniqueId))) {
            //        isValidChecked = true;
            //        break;
            //    }
            //}
            //for (var i=0; i < document.ValidateSubmittedTestResults.failInd.length; i++) {
            //    if ((document.ValidateSubmittedTestResults.failInd[i].checked) &&
            //        (document.ValidateSubmittedTestResults.failInd[i].value == ("~"+pExecutionUniqueId))) {
            //        isFailChecked = true;
            //        break;
            //    }
            //}
            //
            //if((isValidChecked == false) & (isFailChecked == false)) {  // no short-circuit
            //    alert("Comments will be saved only if the scenario " + pExecutionUniqueId + " is decided whether it is either valid or fail.");
            //    // try to give the corresponding valid indicator an html focus.
            //    for (var i=0; i < document.ValidateSubmittedTestResults.validInd.length; i++) {
            //        if (document.ValidateSubmittedTestResults.validInd[i].value == ("~"+pExecutionUniqueId)) {
            //            document.ValidateSubmittedTestResults.validInd[i].focus();
            //            break;
            //        }
            //    }
            //}
            return;
        }

        // *********************************************************************
        // The user needs to select either valid or fail option for a scenario
        // execution -- and NOT both options at the same time.
        // *********************************************************************
        function confirmEitherValidOrFail(pExecutionUniqueId, pCkbxname) {
            if(pCkbxname == 'fail') {
                // make the corresponding valid checkbox unchecked
                for (var i=0; i < document.ValidateSubmittedTestResults.validInd.length; i++) {
                    if ((document.ValidateSubmittedTestResults.validInd[i].checked) &&
                        (document.ValidateSubmittedTestResults.validInd[i].value == ("~"+pExecutionUniqueId))) {
                        document.ValidateSubmittedTestResults.validInd[i].checked = false;
                        break;
                    }
                }

                // now change the color of the corresponding div style
                for (var i=0; i < document.ValidateSubmittedTestResults.failInd.length; i++) {
                    if (document.ValidateSubmittedTestResults.failInd[i].value == ("~"+pExecutionUniqueId)) {
                        if(document.ValidateSubmittedTestResults.failInd[i].checked) {
                            document.getElementById("validationCmnts_"+i).style.color = "red";
                        } else {
                            document.getElementById("validationCmnts_"+i).style.color = "#FF8040";    // orange: indicates undecided
                        }
                        break;
                    }
                }
            }
            
            if(pCkbxname == 'valid') {
                // make the corresponding fail checkbox unchecked
                for (var i=0; i < document.ValidateSubmittedTestResults.failInd.length; i++) {
                    if ((document.ValidateSubmittedTestResults.failInd[i].checked) &&
                        (document.ValidateSubmittedTestResults.failInd[i].value == ("~"+pExecutionUniqueId))) {
                        document.ValidateSubmittedTestResults.failInd[i].checked = false;
                        break;
                    }
                }

                // now change the color of the corresponding div style
                for (var i=0; i < document.ValidateSubmittedTestResults.validInd.length; i++) {
                    if (document.ValidateSubmittedTestResults.validInd[i].value == ("~"+pExecutionUniqueId)) {
                        if(document.ValidateSubmittedTestResults.validInd[i].checked) {
                            document.getElementById("validationCmnts_"+i).style.color = "green";
                        } else {
                            document.getElementById("validationCmnts_"+i).style.color = "#FF8040";  // orange: indicates undecided
                        }
                        break;
                    }
                }
            }
        }



        // *********************************************************************
        // Final confirmation whether nhin validator did choose any
        // scenarios for validation or failure.
        // *********************************************************************
        function final_confirmation_if_any_checkboxes_are_checked() {
            var isAnyValidChecked = false;
            var isAnyFailChecked = false;

            for (var i=0; i < document.ValidateSubmittedTestResults.validInd.length; i++) {
                if (document.ValidateSubmittedTestResults.validInd[i].checked) {
                    isAnyValidChecked = true;
                    break;
                }
            }
            for (var i=0; i < document.ValidateSubmittedTestResults.failInd.length; i++) {
                if (document.ValidateSubmittedTestResults.failInd[i].checked) {
                    isAnyFailChecked = true;
                    break;
                }
            }

            if((isAnyValidChecked == false) & (isAnyFailChecked == false)) {  // no short-circuit
                return false;   // no choices are made by the nhin validator yet
            }
            else {
                return true;    // some choices are made by the nhin validator
            }
        }

        // *********************************************************************
        // Get the latest list of associated scenarios for this service set
        // execution.
        // *********************************************************************
        function refreshAssociatedScenarios(pExecutionUniqueId) {
            document.getElementById("ValidateSubmittedTestResults").validateAction.value = "refresh";
            document.getElementById("ValidateSubmittedTestResults").action = "<%=request.getContextPath()%>/nhinvalid/ValidateSubmittedTestResults?executionUniqueId=" + pExecutionUniqueId;
            document.getElementById("ValidateSubmittedTestResults").submit();
        }

        // *********************************************************************
        // Submit this page after confirming the selection of valid/fail
        // scenarios along with any validation comments.
        // *********************************************************************
        function validateOrFailScenarios(pExecutionUniqueId) {
            document.getElementById("ValidateSubmittedTestResults").validateAction.value = "save_changes";
            document.getElementById("ValidateSubmittedTestResults").entireValidationCommentsString.value = create_entire_validation_comments_string();
            document.getElementById("ValidateSubmittedTestResults").checkedListOfScenariosValidatedByNhinValidator.value = create_list_of_validated_scenarios();
            document.getElementById("ValidateSubmittedTestResults").checkedListOfScenariosFailedByNhinValidator.value = create_list_of_failed_scenarios();
            document.getElementById("ValidateSubmittedTestResults").action = "<%=request.getContextPath()%>/nhinvalid/ValidateSubmittedTestResults?executionUniqueId=" + pExecutionUniqueId;
            
            if(final_confirmation_if_any_checkboxes_are_checked() == false) {
                alert("None of the scenarios are selected for validation or failure yet.  Please make choice(s) and then save changes.");
                return false;
            }
            else {
                // proceed to submit
                document.getElementById("ValidateSubmittedTestResults").submit();
            }
        }

       function ExportFiles() {
           var url ="<%=request.getContextPath()%>/nhinvalid/ExportLogFiles";
           var stdOptions = "resizable=yes,directories=no,left=40,top=40,menubar=yes,location=no";
           var options = stdOptions + ",width=750,height=550,scrollbars=yes";
           window.open(url,'ExportFilesWindow',options);
        }

    </script>