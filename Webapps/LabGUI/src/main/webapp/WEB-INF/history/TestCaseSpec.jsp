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
        <title>Test Case Specification</title>
        <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/prototype.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/scriptaculous.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/standard.js"></script>
    </head>
    <body>
        <s:form action="TestCaseSpec" theme="simple">
            <div id="content-no-menu">
                <div class="content-title">
                    <table>
                        <tr>
                            <td><span class="content-title-text">Test Case:&nbsp;<s:property value="submittedTestcase.name" /></span></td>
                            <td class="title-button">
                                <input type="button" class="inputButton" name="close" id="close" value="Close"
                                       onclick="window.close();" />
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="content-area">
                    <div class="section-title">
                        Draws on the following NHIN Specification:
                    </div>
                    <table class="content-data">
                        <tr>
                            <td>
                                <s:property escape="false" value="submittedTestcase.nhinSpecHtml" />
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </s:form>
    </body>
</html>
