<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Retrieved Documents</title>
    <%--<meta http-equiv="Content-type" content="text/html;charset=ISO-8859-1"/>--%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/style/client.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/prototype.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/scriptaculous.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/standard.js"></script>
</head>
<body>
        <div id="content-no-menu">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text">Retrieved Documents for Test Case</span></td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="close" id="close" value="Close"
                                   onclick="window.close();" />
                        </td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <s:iterator value="#session.retrievedDocumentContents" status="rdocStatus">
                    <div class="section-title">
                        <s:property value="#session.retrievedDocuments[#rdocStatus.index].documentUniqueId" />
                    </div>
                    <div>
                        <s:property escape="false" value="patientInfo" />
                    </div>
                    <hr />
                </s:iterator>
            </div>
        </div>
</body>
</html>
