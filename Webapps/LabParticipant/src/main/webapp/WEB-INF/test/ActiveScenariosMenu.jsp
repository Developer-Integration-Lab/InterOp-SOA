<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<div id="left-menu">
    <div class="menu">
        <div class="menu-title">Dashboard</div>
        <div class="current">Active Scenarios</div>
        <div><a href="<%=request.getContextPath()%>/patient/Search">My Patient Search</a></div>
        <div><a href="<%=request.getContextPath()%>/info/MyInfo">My Information</a></div>
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