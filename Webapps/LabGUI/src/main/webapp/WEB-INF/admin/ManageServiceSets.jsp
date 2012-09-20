<%-- 
    Document   : Manage Service Sets
    Created on : Oct 29, 2010, 03:28:34 PM
    Author     : Tareq.Nabeel
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ManageServiceSets" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:text name="admin.serviceset.manageservicesets" /></span></td>
                    <td class="title-button">
                    </td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <div class="section-title">
                <s:text name="admin.serviceset.servicesets" />
            </div>
            <s:if test="serviceSets.size()>0" >
                <table id="serviceSetTable" class="search-results">
                    <tr class="search-results">
                        <th><s:text name="admin.common.field.serviceSet" /></th>
                        <th><s:text name="admin.common.field.initiator" /></th>
                        <th><s:text name="admin.common.field.responder" /></th>
                        <th><s:text name="admin.common.field.ssnHandling" /></th>
                        <th><s:text name="admin.common.field.status" /></th>
                        <th><s:text name="admin.common.field.createdBy" /></th>
                        <th><s:text name="admin.common.field.createdAt" /></th>
                        <th><s:text name="admin.common.field.changedBy" /></th>
                        <th><s:text name="admin.common.field.changedAt" /></th>
                    </tr>
                    <s:iterator value="serviceSets" status="setStatus">
                        <tr class="<s:if test="#setStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"  >
                            <td>
                                <a href="<%=request.getContextPath()%>/admin/ManageServiceSet?id=<s:property value="setId" />"> <s:property value="setName" /></a>
                            </td>
                            <td>
                                <s:property value="initiatorAllowedInd== \"Y\" ? getText('admin.common.field.participation.allowed') : getText('admin.common.field.participation.notallowed')" />
                            </td>
                            <td>
                                <s:property value="responderAllowedInd== \"Y\" ? getText('admin.common.field.participation.allowed') : getText('admin.common.field.participation.notallowed')" />
                            </td>
                            <td>
                                 <s:property value="ssnHandlingInd== \"Y\" ? getText('admin.common.field.ssnHandling.allowed') : getText('admin.common.field.ssnHandling.notallowed')" />
                            </td>
                            <td>
                                <s:property value="status== \"ACTIVE\" ? getText('admin.common.field.status.active') : getText('admin.common.field.status.inactive')" />
                            </td>
                            <td>
                                <s:property value="createuser" />
                            </td>
                            <td>
                                <s:date name="createtime" format="%{getText('dateFormatPattern')}" />
                            </td>
                            <td>
                                <s:property value="changeduser" />
                            </td>
                            <td>
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