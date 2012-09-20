<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<tiles:importAttribute name="title" scope="request"/>
<html>
<head>
    <title><tiles:getAsString name="title"/></title>
    <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/prototype.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/scriptaculous.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/standard.js"></script>
</head>

<body onLoad="loadPage(); return false;">
    <!-- page logo -->
    <div id="logo">&nbsp;</div>

    <!-- page header -->
    <tiles:insertAttribute name="header" ignore="true"/>

    <!-- navigation (menu) -->
    <tiles:insertAttribute name="menu" ignore="true"/>

    <!-- body (page) content -->
    <tiles:insertAttribute name="body" ignore="true"/>

    <!-- page footer -->
    <tiles:insertAttribute name="footer" ignore="true"/>
</body>
</html>