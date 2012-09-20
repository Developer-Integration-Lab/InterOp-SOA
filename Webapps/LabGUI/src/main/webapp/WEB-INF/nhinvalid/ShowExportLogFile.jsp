<%-- 
    Document   : fileDisplay
    Created on : Dec 16, 2010, 6:46:50 PM
    Author     : Naresh.Jaganathan
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
    <head>
        <title><s:property value="fileName"/></title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
        <style type="text/css">
            .content-title table
            {
                width:94%;
            }
            #content
            {
                margin:0 10px 0 25px;
            }
            h1.page-title
            {
                margin:0.6em auto 0.6em 25px;
            }
        </style>
    </head>
    <body>
        <H1 class="page-title">Export Message</H1>
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text">Selected Log Message Content</span></td>
                        <td class="title-button" >
                            <input type="button" class="inputButton" name="close" id="close" value="Close"
                                   onclick="window.close();" />
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content-area">
                <br><b><label class="long-display">Description: <s:property value="fileName"/></label></b><br>
                <textarea name="filecontent" style="width:100%" rows="20"><s:property value="fileStream"/></textarea>
           </div>
        </div>
    </body>
</html>