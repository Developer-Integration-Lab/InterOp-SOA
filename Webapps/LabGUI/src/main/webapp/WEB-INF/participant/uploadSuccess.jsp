<%-- 
    Document   : uploadSuccess
    Created on : Apr 13, 2010, 2:19:55 PM
    Author     : ram.ghattu
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>Showcase</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
</head>

<body>
<table class="wwFormTable">
<tr>

<td colspan="2"><h1>File Upload</h1></td>
</tr>

<tr>
<td class="tdLabel"><label for="doUpload_upload" class="label">Content Type:</label></td>
<td><s:property value="uploadContentType" /></td>
</tr>

<tr>
<td class="tdLabel"><label for="doUpload_upload" class="label">File Name:</label></td>
<td ><s:property value="uploadFileName" /></td>
</tr>


<tr>
<td class="tdLabel"><label for="doUpload_upload" class="label">File:</label></td>
<td><s:property value="upload" /></td>
</tr>

<tr>
<td class="tdLabel"><label for="doUpload_upload" class="label">File description:</label></td>
<td><s:property value="description" /></td>
</tr>


</table>

</body>
</html>
