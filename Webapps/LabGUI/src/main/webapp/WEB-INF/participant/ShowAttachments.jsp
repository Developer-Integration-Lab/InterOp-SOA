<%-- 
    Document   : ShowAttachments
    Created on : Apr 20, 2010, 1:38:34 PM
    Author     : ram.ghattu
--%>
<html>
<head>
    <title>Attach Document</title>
    <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/prototype.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/scriptaculous.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/standard.js"></script>
</head>
<body>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.util.LabConstants" %>

<%
    int iTheRoleOfTheLoggedInUser = -1;
    // Reference: See implementation of determineRoleForward() method in Login.java.
    iTheRoleOfTheLoggedInUser = ((User)session.getAttribute("userProfile")).getPrimaryRole();

%>
    <s:form action="ShowAttachments" theme="simple">
        <div id="content-attachment-menu">
            <div class="content-title">
                <table>
                    <tr>
                        <td>
                            <span class="content-title-text">
                                Show Attachments -
                                    
                                <s:if test="#request.executionUniqueId == null || \"\".equals(#request.executionUniqueId)">
                                    Scenario <s:property value="#session.scenarioId" />
                                </s:if>
                                <s:else>
                                    Service Set <s:property value="#request.executionUniqueId" />
                                </s:else>
                            </span>
                        </td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="close" id="close" value="Close"
                                   onclick="window.close();" />
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content-area">
                <!-- action error message(s) -->
                <div>
                    <s:actionerror/>
                </div>
                <!-- action message(s) -->
                <div>
                    <strong style="color: #00f"><s:actionmessage/></strong>
                </div>
                <div class="scrollable-area" style="height:320px;">
                    <table class="search-results">
                        <s:if test="attachments.size() > 0">
                        <tr class="search-headers">
                            <th>&nbsp;</th>
                            <th>Filename</th>
                            <th>Description</th>
                            <th>Content Type</th>
                            <th>Download Link</th>
                            <th>Delete</th>
                        </tr>
<s:iterator value="attachments" status="attachStatus">
                        <tr class="<s:if test="#attachStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                            <td valign="top" align="left"><s:property value="#attachStatus.count" /></td>
                            <td valign="top" align="left"><s:property value="filename" /></td>
                            <td width="50%" valign="top" align="left"><s:property value="description" /></td>
                            <td valign="top" align="left"><s:property value="contentType" /></td>
                            <td valign="top" align="left">
                                <s:url id="url" action="DownloadAttachment">
                                    <s:param name="attachmentId" value="attachmentId" />
                                    <s:param name="filename" value="filename" />
                                    <s:param name="contentType" value="contentType" />
                                </s:url>
                                <s:a href="%{url}"><s:property value="filename" /></s:a>
                            </td>
                            <td valign="top" align="left">
                                <%--
                                <s:url id="url" action="DeleteAttachment">
                                    <s:param name="attachmentId" value="attachmentId" />
                                    <s:param name="filename" value="filename" />
                                </s:url>
                                --%>
                                <%-- <s:a href="%{url}">Delete</s:a> --%>

                                <%
                                if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_PARTICIPANT
                                //|| iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_VALIDATING_BODY_REP
                                )
                                {
                                    if(!("no".equalsIgnoreCase(request.getParameter("enableDeleteButton"))))
                                    {
                                %>
                                        <input type="button" class="inputButton" name="delete" id="delete" value="Delete"
                                               onclick="deleteAttachment('<s:property value="attachmentId" />', '<s:property value="filename" />');" />
                                <%
                                    }
                                    else
                                    {
                                %>
                                        <input type="button" class="inputButton" name="delete" id="delete" value="Delete" disabled="true" />
                                <%
                                    }
                                %>
                                <%
                                }
                                else    // logged role is NOT Participant
                                {
                                %>
                                    <input type="button" class="inputButton" name="delete" id="delete" value="Delete" disabled="true" />
                                <%
                                }
                                %>
                            </td>
                        </tr>
</s:iterator>
                        </s:if>
                        <s:else>
                            <tr><td>No Attachments found.</td></tr>
                        </s:else>
                    </table>
                </div>
            </div>
        </div>
    </s:form>
    <script type="text/javascript">
        function deleteAttachment(pAttachmentId, pFileName) {
            if (confirm('Please confirm -- Do you want to delete [' + pFileName + ']?')) {
                document.getElementById("ShowAttachments").action = "<%=request.getContextPath()%>/participant/DeleteAttachment?attachmentId=" + pAttachmentId + "&filename=" + pFileName;
                document.getElementById("ShowAttachments").submit();
            }
        }
    </script>


</body>
</html>
