<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<div id="left-menu">
    <div class="menu">
        <div class="menu-title">Dashboard</div>
        <div><a href="<%=request.getContextPath()%>/test/ActiveScenarios">Active Scenarios</a></div>
        <div><a href="<%=request.getContextPath()%>/patient/Search">My Patient Search</a></div>
        <div class="current">My Information</div>
    </div>

    <div class="menu">
        <div class="menu-title">Help</div>
        <div class="menu-disabled">Guidance</div>
        <div class="menu-disabled">FAQ</div>
    </div>

    <div class="menu-admin">
        <div><a href="<%=request.getContextPath()%>/security/Logoff">Sign Out</a></div>
    </div>

</div>