<%--
    Document   : Manage Scenario
    Created on : Oct 29, 2010, 03:28:34 PM
    Author     : Tareq.Nabeel
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ManageScenario" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:text name="admin.scenario.managescenario" /></span></td>
                    <td class="title-button">
                        <s:submit action="UpdateScenario" cssClass="inputButton" value="Save Changes" />
                        <s:submit action="ManageScenarios" cssClass="inputButton" value="Close" />
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
                <s:text name="admin.scenario.attributes" /> - <s:property value="scenario.scenarioName" />
            </div>
            <table id="scenarioTable">
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.name" for="scenarioName"/></td>
                    <td><s:textfield id="scenarioName" cssClass="inputText" name="scenario.scenarioName" size="60" maxlength="255" onchange="scenarioDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.scenario.field.quickName" for="quickName"/></td>
                    <td><s:textfield id="quickName" cssClass="inputText" name="scenario.quickName" size="60" maxlength="255" onchange="scenarioDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.description" for="description"/></td>
                    <td><s:textarea id="description" cssClass="inputTextArea" name="scenario.description" rows="2" cols="100" onchange="scenarioDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.scenario.field.condition" for="conditionDescription"/></td>
                    <td><s:textarea id="conditionDescription" cssClass="inputTextArea" name="scenario.conditionDescription" rows="4" cols="100" onchange="scenarioDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.participation"/></td>
                    <td>
                        <s:checkbox cssClass="inputCheckbox" name="scenario.initiatorInd" onchange="scenarioDataModified();"/><s:text name="admin.common.field.participation.initiator"/>
                        <s:checkbox cssClass="inputCheckbox" name="scenario.responderInd" onchange="scenarioDataModified();"/><s:text name="admin.common.field.participation.responder"/>
                    </td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.ssnHandling" for="ssnHandling"/></td>
                    <td>
                        <s:radio id="ssnHandling" cssClass="inputRadio" name="scenario.ssnHandlingInd" list="#{'Y':getText('admin.common.field.ssnHandling.allowed'),'N':getText('admin.common.field.ssnHandling.notallowed')}" onchange="scenarioDataModified();"/>
                    </td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.status" for="status"/></td>
                    <td>
                        <s:radio id="status" cssClass="inputRadio" name="scenario.status" list="#{'ACTIVE':getText('admin.common.field.status.active'),'DISABLED':getText('admin.common.field.status.inactive')}" onchange="scenarioDataModified();"/>
                    </td>
                </tr>
            </table>
            <div class="section-title">
                <s:text name="admin.common.scenariocases" /> - <s:property value="scenario.scenarioName" />
            </div>
            <s:if test="scenario.scenariocases.size()>0" >
                <table id="scenariocasesTable" class="search-results">
                    <tr class="search-results">
                        <th><s:text name="admin.common.field.testcase" /></th>
                        <th><s:text name="admin.common.field.userName" /></th>
                        <th><s:text name="admin.common.field.patientId" /></th>
                        <th><s:text name="admin.common.field.documentIds" /></th>
                        <th><s:text name="admin.common.field.participatingRIs" /></th>
                    </tr>
                    <s:iterator value="scenario.scenariocases" status="setStatus">
                        <tr class="<s:if test="#setStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"  >
                            <td>
                               <a href="<%=request.getContextPath()%>/admin/ManageTestCase?id=<s:property value="testcase.caseId" />"> <s:property value="testcase.name" /></a>
                            </td>
                            <td>
                               <s:textfield cssClass="inputText" name="userName" size="25" maxlength="20" onchange="scenarioDataModified();"/>
                            </td>
                            <td>
                               <s:textfield cssClass="inputText" name="patientId" size="15" maxlength="20" onchange="scenarioDataModified();"/>
                            </td>
                            <td>
                               <s:textfield cssClass="inputText" name="documentIds" size="60" maxlength="255" onchange="scenarioDataModified();"/>
                            </td>
                            <td>
                                <s:textfield cssClass="inputText" name="participatingRIs" size="10" maxlength="20" onchange="scenarioDataModified();"/>
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
    <s:hidden id="scenarioModified" name="scenarioModified" />
</s:form>

<script type="text/javascript">

    function scenarioDataModified(){
        // Because scenarioCase does not have a changedtime column in database and to keep things simple
        // we're using scenarioModified for changes to both scenario and scenariocase for conditional updates
        document.getElementById("scenarioModified").value="true";
    }


</script>