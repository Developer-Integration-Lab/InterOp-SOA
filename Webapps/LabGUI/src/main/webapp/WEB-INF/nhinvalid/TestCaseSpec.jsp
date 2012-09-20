<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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
