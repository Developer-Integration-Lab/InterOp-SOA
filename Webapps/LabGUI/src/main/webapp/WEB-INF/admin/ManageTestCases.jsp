<%-- 
    Document   : Manage Test Cases
    Created on : Oct 29, 2010, 03:28:34 PM
    Author     : Tareq.Nabeel
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="tf" uri="http://www.aegis.net/testlab/func" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ManageTestCases" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:text name="admin.testcase.managetestcases" /></span></td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <div class="section-title">
                <s:text name="admin.testcase.testcases" />
            </div>
            <s:if test="testCases.size()>0" >
                <table id="testcasesTable" class="search-results">
                    <tr class="search-results">
                    <th><s:text name="admin.common.field.name" /></th>
                    <th><s:text name="admin.common.field.description" /></th>
                    <%--<th>Message Type</th>--%>
                    <%--<th>Message Name</th>--%>
                    <th><s:text name="admin.testcase.field.message" /></th>
                    <th><s:text name="admin.testcase.field.testtype" /></th>
                    <th><s:text name="admin.common.field.executeType" /></th>
                    <th><s:text name="admin.common.field.initiator" /></th>
                    <th><s:text name="admin.common.field.responder" /></th>
                    <th><s:text name="admin.common.field.ssnHandling" /></th>
                    <th><s:text name="admin.testcase.field.expectedTestResults" /></th>
                    <th><s:text name="admin.common.field.createdBy" /></th>
                    <th><s:text name="admin.common.field.createdAt" /></th>
                    <th><s:text name="admin.common.field.changedBy" /></th>
                    <th><s:text name="admin.common.field.changedAt" /></th>
                    </tr>
                    <s:iterator value="testCases" status="setStatus">
                        <tr class="<s:if test="#setStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"  >
                            <td style="white-space:nowrap;">
                                <a href="<%=request.getContextPath()%>/admin/ManageTestCase?id=<s:property value="caseId" />"> <s:property value="name" /></a>
                            </td>
                            <td style="white-space:nowrap;">
                                <t:tooltip completeValue="${description}" threshold="15"/>
                            </td>
                            <td style="white-space:nowrap;">
                                <s:property value="messageName" />
                            </td>
                            <td>
                                <s:property value="testType" />
                            </td>
                            <td>
                                <s:property value="executeType" />
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
                            <td style="white-space:nowrap;">
                                <t:tooltip completeValue="${expectedTestResults}" threshold="15"/>
                            </td>
                            <td>
                                <s:property value="createuser" />
                            </td>
                            <td style="white-space:nowrap;">
                                <s:date name="createtime" format="%{getText('dateFormatPattern')}" />
    <%--                            <s:set name="createtime" value="@net.aegis.lab.web.util.DateUtil@formatDate(createtime,getText('dateFormatPattern'))"/>
                                <s:component template="tooltip" >
                                    <s:param name="completeValue" value="#createtime"/>
                                    <s:param name="shortValue" value="#createtime.substring(0,10)"/>
                                </s:component>--%>
                            </td>
                            <td>
                                <s:property value="changeduser" />
                            </td>
                            <td style="white-space:nowrap;">
                                <s:date name="changedtime" format="%{getText('dateFormatPattern')}" />
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