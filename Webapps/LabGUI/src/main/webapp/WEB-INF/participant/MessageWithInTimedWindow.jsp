<%--
    Document   : MessageWithInTimedWindow
    Created on : Apr 12, 2011, 3:32:52 PM
    Author     : Jyoti.Devarakonda
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%@ page import="net.aegis.lab.manager.ParticipantManager" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<html>
    <head>
        <title>Message between Timed Window</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/style/conformance.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
        <style type="text/css">
            .content-title table
            {
                width:95%;
            }
            #content
            {
                margin:0 10px 0 10px;
            }
            h1.page-title
            {
                margin:0.6em auto 0.6em 25px;
            }
            .content-area
            {
                padding-top:7px;
                padding-left:7px;
                border-style:solid;
                border-width:1px;
                border-color:#5D6997;
                margin:0.6em auto 0.6em 15px;
                height:650px;
                width:97%;
                overflow:auto;
            }
        </style>

        <script type="text/javascript">

            function downloadLogMessage(pTestHarnessId, pAuditRepositoryId)
            {
                var urlString = "<%=request.getContextPath()%>/help/DownloadAuditExtensionMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
                var stdOptions = "resizable=yes,directories=no,left=100,top=40,menubar=yes,location=no";
                var options = stdOptions + ",width=800,height=600,scrollbars=yes";
                window.open(urlString, "MessageContentWindow" ,options);
                return false;
            }
        </script>
    </head>
    <body>
        <H1 class="page-title"> Messages Received</H1>
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text"> Messages Received between the <s:date name="startWindowTime" format="yyyy-MM-dd HH:mm:ss" /> and <s:date name="endWindowTime"  format="yyyy-MM-dd HH:mm:ss"/> </span></td>
                        <td class="title-button" >
                            <input type="button" class="inputButton" name="close" id="close" value="Close"
                                   onclick="window.close();" />
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content-area">
                <s:form action="PopTWMessages" theme="simple">
                    <div>
                        <s:actionerror/>
                    </div>
                    <!-- action message(s) -->
                    <div>
                        <strong style="color: #00f"><s:actionmessage/></strong>
                    </div>
                    <s:if test="messagesbtwTMWindow != null and messagesbtwTMWindow.size() > 0" >
                        <br><b><label class="long-display">Note : To view the message please click the download button. The system opens a new window to display the message content.</label></b><br>
                        <div class="scrollable-area">
                            <table class="search-results">
                                <tr class="search-headers">
                                    <th>Timestamp </th>
                                    <th>TestCase</th>
                                    <th>Direction</th>
                                    <th>From HCID</th>
                                    <th>TO HCID</th>
                                    <th>Message</th>
                                </tr>
                                <s:iterator value="messagesbtwTMWindow" status="msgTMWStatus">
                                    <tr class="<s:if test="#msgTMWStatus.odd == true">odd-row</s:if>">
                                        <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                        <td><s:property value="service" /></td>
                                        <td><s:property value="direction" /></td>
                                        <td><s:property value="fromHCID" /></td>
                                        <td><s:property value="toHCID" /></td>
                                        <s:if test="%{fromHCID == participant.communityId || toHCID == participant.communityId }" >
                                            <td>
                                                <input type="button" class="inputButton" name="audit1message" id="audit1message" value="Download"
                                                       onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                            </td>

                                        </s:if>
                                        <s:else>
                                            <td>
                                                <input type="button" class="inputButtonDisabled" disabled="disabled" name="audit1message" id="audit1message" value="Download"
                                                       onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                            </td>
                                        </s:else>  
                                    </tr>
                                </s:iterator>
                            </table>
                        </div>
                    </s:if>
                   <s:if test="messagesbtwTMWindow != null and messagesbtwTMWindow.size() == 0" >
                        <table class="search-results">
                            <tr><td align ="left">No Audit Messages found.</td></tr>
                        </table>
                    </s:if>
                </s:form>
            </div>
        </div>
    </body>
</html>

