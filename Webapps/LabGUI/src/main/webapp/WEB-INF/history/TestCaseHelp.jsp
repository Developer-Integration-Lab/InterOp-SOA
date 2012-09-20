<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%
    response.addHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
    response.addHeader("Cache-Control","pre-check=0,post-check=0");
    response.setDateHeader("Expires",0);
%>
<%
    int iTheRoleOfTheLoggedInUser = -1;
    Participant objParticipantThatNhinRepWorksWith = null;
    String strParticipantThatNhinRepWorksWith = "";

    // Reference: See implementation of determineRoleForward() method in Login.java.
    iTheRoleOfTheLoggedInUser = ((User)session.getAttribute("userProfile")).getPrimaryRole();

    if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {   // avoid any NPE further.
        objParticipantThatNhinRepWorksWith = ((User)session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
        if(null == objParticipantThatNhinRepWorksWith) { strParticipantThatNhinRepWorksWith = "none"; }
        else { strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName(); }
    }
%>
<html>
    <head>
        <title>Test Case Help</title>
        <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/prototype.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/scriptaculous.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/standard.js"></script>
        <script type="text/javascript">
            var helpWindow;
            function showTestCaseSpec(caseExecutionId) {
                var urlString = "<%=request.getContextPath()%>/history/TestCaseSpec?selectedCaseExecutionId=" + caseExecutionId;
                var stdOptions = "resizable=yes,directories=no,left=40,top=40,toolbar=no,location=no";
                var options = stdOptions + ",width=750,height=550,scrollbars=yes";
                helpWindow = window.open(urlString, "testCaseSpecWindow" ,options);
                if (window.focus) {
                    helpWindow.focus();
                }
            }
        </script>
    </head>
    <body>
        <s:form action="TestCaseHelp" theme="simple">
            <div id="content-no-menu">
                <div class="content-title">
                    <table>
                        <tr>
                            <td><span class="content-title-text">Test Scenario:&nbsp;<s:property value="submittedScenario.scenario.scenarioName" /></span></td>
                            <td class="title-button">
                                <input type="button" class="inputButton" name="close" id="close" value="Close"
                                       onclick="window.close();" />
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="content-area">
                    <div class="section-title">
                        Comprised of the following Test Cases:
                    </div>
                    <table class="test-results">
    <s:iterator value="submittedScenario.caseexecutions" status="testStatus">
                        <tr class="testable-event <s:if test="#testStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                            <td><strong><s:property value="testcase.name" /></strong></td>
                            <td><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                            <td class="title-button">
                                <input type="button" class="inputButton" name="execute" id="nhinspec" value="NHIN Spec"
                                       onclick="showTestCaseSpec(<s:property value="caseExecutionId" />);" />
                            </td>
                        </tr>
    </s:iterator>
                    </table>
                </div>
            </div>
        </s:form>
    </body>
</html>

