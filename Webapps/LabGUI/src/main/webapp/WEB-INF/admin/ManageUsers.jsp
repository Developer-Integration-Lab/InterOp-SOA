<%-- 
    Document   : Manage Users
    Created on : May 11, 2010, 11:44:27 AM
    Author     : SreeHari.Devineni
    Maintenance: Abhay.Bakshi@AEGIS
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ManageUsers" theme="simple">
    <s:hidden key="changedPropertyInfo" />
    <s:hidden key="initialPropertylist" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"> Admin - Manage Users</span></td>
                </tr>
            </table>
        </div>
         
        <div class="content-area">
            <div class="section-title">
                Users List
            </div>
            
            <!-- action error message(s) -->
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
                        
            <table class="search-results">
                <tr class="search-results">
                    <th>User Name </th>
                    <th>Status</th>
                    <th>User Role</th>
                    <th>Invalid attempts</th>
                    <th>Comments</th>
                    <th>Security Reminder</th>
                </tr>
<s:iterator value="userMapList" status="propStatus">
                <tr class="<s:if test="#propStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                    <td align="left">
                        <s:if test="status == \"L\" or status == \"T\"">
                            <span style="color: #ff0000;">
                                <a href="<%=request.getContextPath()%>/admin/ManageSelectedUser?username=<s:property value="username" />"> <s:property value="username" /></a>
                            </span>
                        </s:if>
                        <s:else>
                            <a href="<%=request.getContextPath()%>/admin/ManageSelectedUser?username=<s:property value="username" />"> <s:property value="username" /></a>
                        </s:else>
                    </td>
                    <td align="left"> <s:property value="status"  /> </td>
                    <td align="left">
                        <s:if test="primaryRole == 1">
                            <s:if test="status == \"L\" or status == \"T\"">
                                <span style="color: #ff0000;">
                                    <%=LabConstants.ROLENAME_LAB_ADMIN%>
                                </span>
                            </s:if>
                            <s:else>
                                <%=LabConstants.ROLENAME_LAB_ADMIN%>
                            </s:else>
                        </s:if>
                        <s:if test="primaryRole == 2">
                            <s:if test="status == \"L\" or status == \"T\"">
                                <span style="color: #ff0000;">
                                    <%=LabConstants.ROLENAME_NHIN_ADMIN%>
                                </span>
                            </s:if>
                            <s:else>
                                <%=LabConstants.ROLENAME_NHIN_ADMIN%>
                            </s:else>
                        </s:if>
                        <s:if test="primaryRole == 3">
                            <s:if test="status == \"L\" or status == \"T\"">
                                <span style="color: #ff0000;">
                                    <%=LabConstants.ROLENAME_NHIN_REP%>
                                </span>
                            </s:if>
                            <s:else>
                                <%=LabConstants.ROLENAME_NHIN_REP%>
                            </s:else>
                        </s:if>
                        <s:if test="primaryRole == 4">
                            <s:if test="status == \"L\" or status == \"T\"">
                                <span style="color: #ff0000;">
                                    <%=LabConstants.ROLENAME_NHIN_VALIDATING_BODY_REP%>
                                </span>
                            </s:if>
                            <s:else>
                                <%=LabConstants.ROLENAME_NHIN_VALIDATING_BODY_REP%>
                            </s:else>
                        </s:if>
                        <s:if test="primaryRole == 5">
                            <s:if test="status == \"L\" or status == \"T\"">
                                <span style="color: #ff0000;">
                                    <%=LabConstants.ROLENAME_PARTICIPANT%>
                                </span>
                            </s:if>
                            <s:else>
                                <%=LabConstants.ROLENAME_PARTICIPANT%>
                            </s:else>
                        </s:if>
                    </td>
                    <td align="left" width="5%"> <s:property value="invalidAttempts" /> </td>
                    <td align="left"> <s:property value="comments" /> </td>
                    <td align="left"> <s:property value="securityReminder" /> </td>
                </tr>
</s:iterator>
            </table>

            <table class="dashboard-table" id="participant-dashboard">
                <tr>
                    <td>
                        <font size="2" color="red"> Status Legend:</font>
                        <font size="2" color="green"><b>A</b> : Active </font> ,
                        <font size="2" color="orange"><b>L</b> : Locked </font> ,
                        <font size="2" color="red"><b>T</b> : Terminated </font>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</s:form>


<script type="text/javascript">


</script>