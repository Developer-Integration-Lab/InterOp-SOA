<%--
    Document   : waitDisplay
    Created on : Apr 12, 2011, 10:54:08 AM
    Author     : Jyoti.Devarakonda
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
    <head>
        <meta http-equiv="refresh" content="5;url=<s:url includeParams="all" />"/>
    </head>

    <body>     
        <div id="content-pleasewait-startTimer" style="visibility: hidden; height: 0">
            <p><b><s:property value="testScenario.scenario.scenarioName" /> -Message Search results Timer for Duration 2 mins is On!</b></p>
            <hr/>
            <p class="errorMessage"><b>This page will automatically refresh to show Message Searc results Timer Messages if any upon completion of theMessage Searc results Timerexecution.</b></p>
            <p class="actionMessage"><b><i>Note: Attempting to press the Start Timer button again now,Will Reset the Timer Window.</i></b></p>
        </div>

    </body>

</html>