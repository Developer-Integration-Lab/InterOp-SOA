<%-- 
    Document   : AssignSubmission
    Created on : Apr 21, 2010, 1:59:39 PM
    Author     : Jyoti.Devarakonda
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="AssignSubmission" theme="simple">

    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="nhinValidator.name" /> - Assign Submissions</span></td>
                    <td class="title-button">
                        <input type="submit" class="inputButton" name="buttonName" value="Assign"/>
                        <input type="submit" class="inputButton" name="buttonName" value="Refresh"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <!-- action error message(s) -->
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
             
            <table class="search-results">
                <tr class="search-headers">
                    <th>Select</th>
                    <th>Type</th>
                    <th>Execution Unique Id</th>
                    <th>Service Set</th>
                    <th>Submitted On</th>
                    <th>Assigned To</th>
                </tr>

                <s:iterator value="submittedValidationRecs" status="valStatus">
                     <tr class="<s:if test="#valStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td nowrap>
                            <s:if test="assignedTo == nhinValidator.name">
                              <s:checkbox cssClass="inputCheckbox" name="validationRecs[%{#valStatus.index}]"
                                          value="%{validationRecs[#valStatus.index]}" disabled="false" checked="true" /><br>&nbsp;
                            </s:if>
                            <s:else>
                                <s:if test="assignedTo == null">   <!-- validation Record is assignable -->
                                   <s:checkbox cssClass="inputCheckbox" name="validationRecs[%{#valStatus.index}]"
                                          value="false" disabled="false"  /><br>&nbsp;
                                </s:if>
                                <s:else>    <!-- participant is NOT assignable unless the Nhin Rep releases him/her first :) -->
                                    <s:checkbox cssClass="inputCheckbox" name="validationRecs[%{#valStatus.index}]"
                                          value="false" disabled="true"   /><br>&nbsp;
                                </s:else>

                            </s:else>
                        </td>
                        <td>
                            <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(labAccessFilter)"/>
                        </td>
                        <td>
                            <s:property value="executionUniqueId" />
                        </td>
                        <td><s:property value="serviceSet" /></td>
                        <td><s:property value="submittedOn" /></td>
                        <td><s:property value="assignedTo" /></td>

                    </tr>
                </s:iterator>

            </table>
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
  
</script>