<%--
    Document   : Manage Scenarios
    Created on : Oct 29, 2010, 03:28:34 PM
    Author     : Tareq.Nabeel
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tf" uri="http://www.aegis.net/testlab/func" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ManageScenarios" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:text name="admin.scenario.managescenarios" /></span></td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <div class="section-title">
                <s:text name="admin.scenario.scenarios" />
            </div>
            <s:if test="scenarios.size()>0" >
                <table id="scenariosTable" class="search-results">
                    <tr class="search-results">
                        <th><s:text name="admin.common.field.name" /></th>
                        <th><s:text name="admin.scenario.field.quickName" /></th>
                        <th><s:text name="admin.common.field.description" /></th>
                        <th><s:text name="admin.scenario.field.condition" /></th>
                        <th><s:text name="admin.common.field.status" /></th>
                        <th><s:text name="admin.common.field.initiator" /></th>
                        <th><s:text name="admin.common.field.responder" /></th>
                        <th><s:text name="admin.common.field.ssnHandling" /></th>
                        <th><s:text name="admin.common.field.createdBy" /></th>
                        <th><s:text name="admin.common.field.createdAt" /></th>
                        <th><s:text name="admin.common.field.changedBy" /></th>
                        <th><s:text name="admin.common.field.changedAt" /></th>
                    </tr>
                    <s:iterator value="scenarios" status="setStatus">
                        <tr class="<s:if test="#setStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"  >
                            <td>
                                <a href="<%=request.getContextPath()%>/admin/ManageScenario?id=<s:property value="scenarioId" />"> <s:property value="scenarioName" /></a>
                            </td>
                            <td>
                                <t:tooltip completeValue="${quickName}" threshold="15"/>
                            </td>
                            <td>
                                <t:tooltip completeValue="${description}" threshold="15"/>
                            </td>
                            <td>
                                <t:tooltip completeValue="${conditionDescription}" threshold="15" />
                            </td>
                            <td>
                                <s:property value="status== \"ACTIVE\" ? getText('admin.common.field.status.active') : getText('admin.common.field.status.inactive')" />
                            </td>
                            <td>
                                <s:property value="initiatorInd== \"Y\" ? getText('admin.common.field.participation.allowed') : getText('admin.common.field.participation.notallowed')" />
                            </td>
                            <td>
                                <s:property value="responderInd== \"Y\" ? getText('admin.common.field.participation.allowed') : getText('admin.common.field.participation.notallowed')" />
                            </td>
                            <td>
                                <s:property value="ssnHandlingInd== \"Y\" ? getText('admin.common.field.ssnHandling.allowed') : getText('admin.common.field.ssnHandling.notallowed')" />
                            </td>
                            <td>
                                <s:property value="createuser" />
                            </td>
                            <td style="white-space:nowrap;">
                                <t:tooltip completeValue="${tf:formatDate(createtime)}" threshold="10"/>
                            </td>
                            <td>
                                <s:property value="changeduser" />
                            </td>
                            <td style="white-space:nowrap;">
                                <t:tooltip completeValue="${tf:formatDate(changedtime)}" threshold="10"/>
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
</s:form>


<script type="text/javascript">


</script>