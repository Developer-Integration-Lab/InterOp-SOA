<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants"%>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%@ page import="net.aegis.lab.manager.ParticipantManager" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<h1 class="page-title" title="Version <s:property value="#session.applicationVersionNumber" /> Build <s:property value="#session.applicationBuildNumber" /> [<s:property value="#session.applicationBuildTimestamp" />]">NHIN <s:property value="%{#attr.labType.toString()}"/> Platform</h1>

<s:if test="@net.aegis.lab.util.LabConstants@ROLE_PARTICIPANT.equals(#session.userProfile.getPrimaryRole())">
<div id="switch-platform">
    <s:if test="@net.aegis.lab.util.LabConstants$LabType@LAB.equals(#attr.labType)">
    <a id="switch-conformance" href="<%=request.getContextPath()%>/facade/SwitchDashboard">Switch to Conformance</a>
    </s:if>
    <s:else>
        <% String btnOnClickEvent = "";
        if (!ParticipantManager.getInstance().isParticipantOfTestLab(((User)session.getAttribute("userProfile")).getParticipant().getCommunityId(), LabType.LAB)){
            btnOnClickEvent = "onclick=\"alert('Please note: You may not execute a Lab test phase until an Initial Connectivity service set has been validated.')\"";
        }%>
        <a id="switch-lab" href="<%=request.getContextPath()%>/facade/SwitchDashboard" <%=btnOnClickEvent%>>Switch to Lab</a>
    </s:else>
</div>
    
</s:if> 