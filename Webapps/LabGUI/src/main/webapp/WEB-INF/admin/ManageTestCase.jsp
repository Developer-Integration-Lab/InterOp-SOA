<%--
    Document   : Manage Test Case
    Created on : Oct 29, 2010, 03:28:34 PM
    Author     : Tareq.Nabeel
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ManageTestCase" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:text name="admin.testcase.managetestcase" /></span></td>
                    <td class="title-button">
                        <s:submit action="UpdateTestCase" cssClass="inputButton" value="Save Changes" />
                        <s:submit action="ManageTestCases" cssClass="inputButton" value="Close" />
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
            <div class="section-title">
                <s:text name="admin.testcase.attributes" /> - <s:property value="testCase.name" />
            </div>
            <table id="testCaseTable">
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.name" for="testCaseName"/></td>
                    <td><s:textfield id="testCaseName" cssClass="inputText" name="testCase.name" size="60" maxlength="255" onchange="testCaseDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.description" for="description"/></td>
                    <td><s:textarea id="description" cssClass="inputTextArea" name="testCase.description" rows="4" cols="100" onchange="testCaseDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.testcase.field.message" for="messageType"/></td>
                    <%--cssClass="inputSelect" --%>
                    <td><s:select cssClass="inputSelect" name="testCase.messageType" list="#{'PD':getText('admin.testcase.field.messageType.PD'),'QD':getText('admin.testcase.field.messageType.QD'),'RD':getText('admin.testcase.field.messageType.RD'),'SP':getText('admin.testcase.field.messageType.SP'),'AD':getText('admin.testcase.field.messageType.AD')}" onchange="testCaseDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.testcase.field.testtype" for="testType"/></td>
                    <td><s:textfield id="testType" cssClass="inputTextArea" name="testCase.testType" onchange="testCaseDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.testcase.field.executeType" for="executeType"/></td>
                    <td><s:textfield id="executeType" cssClass="inputTextArea" name="testCase.executeType" onchange="testCaseDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.participation" /></td>
                    <td>
                        <s:checkbox cssClass="inputCheckbox" name="testCase.initiatorInd" onchange="testCaseDataModified();"/><s:text name="admin.common.field.participation.initiator"/>
                        <s:checkbox cssClass="inputCheckbox" name="testCase.responderInd" onchange="testCaseDataModified();"/><s:text name="admin.common.field.participation.responder"/>
                    </td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.ssnHandling" for="ssnHandling"/></td>
                    <td><s:radio id="ssnHandling" cssClass="inputRadio" name="testCase.ssnHandlingInd" list="#{'Y':getText('admin.common.field.ssnHandling.allowed'),'N':getText('admin.common.field.ssnHandling.notallowed')}" onchange="testCaseDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.testcase.field.expectedTestResults" for="expectedTestResults"/></td>
                    <td><s:textarea id="expectedTestResults" cssClass="inputTextArea" name="testCase.expectedTestResults" rows="4" cols="100" onchange="testCaseDataModified();"/></td>
                </tr>
            </table>
            <div class="section-title">
                <s:text name="admin.common.scenariocases" /> - <s:property value="testCase.name" />
            </div>
            <s:if test="testCase.scenariocases.size()>0" >
                <table id="scenariocasesTable" class="search-results">
                    <tr class="search-results">
                        <th><s:text name="admin.common.field.scenario" /></th>
                        <th><s:text name="admin.common.field.userName" /></th>
                        <th><s:text name="admin.common.field.patientId" /></th>
                        <th><s:text name="admin.common.field.documentIds" /></th>
                        <th><s:text name="admin.common.field.participatingRIs" /></th>
                    </tr>
                    <s:iterator value="testCase.scenariocases" status="setStatus">
                        <tr class="<s:if test="#setStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"  >
                            <td>
                               <a href="<%=request.getContextPath()%>/admin/ManageScenario?id=<s:property value="scenario.scenarioId" />"> <s:property value="scenario.scenarioName" /></a>
                            </td>
                            <td>
                               <s:textfield cssClass="inputText" name="userName" size="25" maxlength="20" onchange="testCaseDataModified();"/>
                            </td>
                            <td>
                               <s:textfield cssClass="inputText" name="patientId" size="15" maxlength="20" onchange="testCaseDataModified();"/>
                            </td>
                            <td>
                               <s:textfield cssClass="inputText" name="documentIds" size="60" maxlength="255" onchange="testCaseDataModified();"/>
                            </td>
                            <td>
                                <s:textfield cssClass="inputText" name="participatingRIs" size="10" maxlength="20" onchange="testCaseDataModified();"/>
                            </td>
                        </tr>
                    </s:iterator>
                </table>
            </s:if>
            <s:else>
                <s:text name="admin.common.norecords" />
            </s:else>
        </div>
    </div>
    <s:hidden name="id" />
    <s:hidden id="testCaseModified" name="testCaseModified" />
</s:form>


<script type="text/javascript">

    function testCaseDataModified(){
        document.getElementById("testCaseModified").value="true";
    }

</script>