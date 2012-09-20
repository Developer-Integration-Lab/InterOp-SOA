<%-- 
    Document   : DisplayLabUDDIEndpoints
    Created on : Mar 29, 2011, 2:16:43 PM
    Author     : Jyoti.Devarakonda
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%@ page import="net.aegis.lab.manager.ParticipantManager" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<html>
    <head>
        <title>Labb's UDDI</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
        <style type="text/css">
            .content-title table
            {
                /* width:90%; */
            }
            #content
            {
                margin:0 10px 0 10px;
            }
            h1.page-title
            {
                margin:0.6em auto 0.6em 10px;
            }
            .content-area
            {
                padding-top:7px;
                padding-left:7px;
                border-style:solid;
                border-width:1px;
                border-color:#5D6997;
                margin:0.6em auto 0.6em 10px;
               /* height:650px; */
                /* width:92%; */
                overflow:auto;
            }
        </style>
    </head>
    <body>
        <H1 class="page-title"> Lab's UDDI -End Points</H1>
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text"> Lab's UDDI End Points</span></td>
                        <td class="title-button" >
                            <input type="button" class="inputButton" name="close" id="close" value="Close"
                                   onclick="window.close();" />
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content-area">
                <s:form action="getEndPoints" theme="simple">                      
                    <div>
                        <s:actionerror/>
                    </div>
                    <!-- action message(s) -->
                    <div>
                        <strong style="color: #00f"><s:actionmessage/></strong>
                    </div>
                    <table id="endPointsDisplayTable" class="search-results">
                        <tr class="search-headers">
                            <th >RI End Points</th>
                        </tr>
                        <tr>
                            <td>
                                <table>
                                    <tr>
                                        <th class="search-headers">RI1</th>
                                    </tr>
                                    <s:iterator value="ri1Endpoints" id="ri1EndpointURL">
                                        <tr class="odd-row">
                                            <td align ="left"> <s:property value="ri1EndpointURL" /></td>
                                        </tr>
                                    </s:iterator>
                                </table>
                            </td></tr>
                            <tr><td>
                                <table>
                                    <tr>
                                        <th class="search-headers">RI2</th>
                                    </tr>
                                    <s:iterator value="ri2Endpoints" id="ri2EndpointURL">
                                        <tr class="odd-row">
                                            <td align ="left">  <s:property value="ri2EndpointURL" /></td>
                                        </tr>
                                    </s:iterator>
                                </table>
                            </td>
                        </tr>
                    </table>
                </s:form>
            </div>
        </div>
    </body>
</html>

<script type="text/javascript">


</script>
