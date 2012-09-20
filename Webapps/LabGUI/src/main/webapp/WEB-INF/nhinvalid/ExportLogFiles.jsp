<%-- 
    Document   : form.jsp
    Created on : Dec 15, 2010, 10:32:14 AM
    Author     : Naresh.Jaganathan
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*" %>
<html>
    <head>
        <title>NHIN Lab Export Files Menu</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
        <style type="text/css">
            .content-title table
            {
                width:100%;
            }
            #content
            {
                margin:0 10px 0 25px;
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
                margin:0.6em auto 0.6em 25px;
                height:370px;
                width:92%;
                overflow:auto;
            }
        </style>
        <script type="text/javascript">
            function openNewWindow(filename, testHarnessId, pAuditRepositoryId)
            {
                var urlString = "<%=request.getContextPath()%>/nhinvalid/ShowExportLogFile?fileName="+filename+"&testHarnessId="+testHarnessId+"&auditRepositoryId="+ pAuditRepositoryId;
                var stdOptions = "resizable=yes,directories=no,left=100,top=40,menubar=yes,location=no";
                var options = stdOptions + ",width=750,height=560,scrollbars=yes";
                window.open(urlString, "ExportFileContentWindow" ,options);
                return false;
            }
        </script>
    </head>
    <body>
        <H1 class="page-title">Export Message List</H1>
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text">TestCase Log Messages</span></td>
                        <td class="title-button" >
                            <input type="button" class="inputButton" name="close" id="close" value="Close"
                                   onclick="window.close();" />
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content-area">
                <s:if test="auditLogFileList != null and auditLogFileList.size() > 0">
                    <s:iterator value="auditLogFileList">
                        <ul>
                            <label class="long-display"><li><a href="#" onclick="openNewWindow('<s:property value="filenameMap[summaryId]"/>',
                                '<s:property value="testharnessri.testharnessId" />',
                                '<s:property value="repositoryId" />')">                                        
                                 <s:property value="filenameMap[summaryId]"/></a><br></li>
                            </label>
                        </ul>
                    </s:iterator>
                </s:if>
                <s:else>
                    No log messages found for this test case.
                </s:else>

            </div>
        </div>
    </body>
</html>