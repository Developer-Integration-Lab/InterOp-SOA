<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>

<div class="content-area">
    <div class="section-title">
        Case Log Summary - <s:property value="%{selectedjspCaseName}" />
    </div>
    <s:if test="currentCaseResult.resultsummarys.size() > 0" >
    	<div class="collapsible-area">
            <div class="scrollable-area-200">
                <table class="search-results">
                    <tr class="search-headers">
                        <th>Execute<br>Time </th>
                        <th>Sender HCID</th>
                        <th>Receiver HCID</th>
                        <th>Msg Type</th>
                        <th>HTTP<br/>Status Code</th>
                        <th>Request/Response</th>
                        
                    </tr>
                    <s:iterator value="transactions" status="transactionStatus">
                        <tr class="<s:if test="#transactionStatus.odd == true ">odd-row</s:if>">
                            <td><s:date name="time" format="yyyy-MM-dd HH:mm:ss" /></td>
                            <td class="value"><s:property value="senderHCID" /></td>
                            <td class="value"><s:property value="receiverHCID" /></td>
                            <td class="value"><s:property value="messageType" /></td>
                            <td class="value"><s:property value="statusCode" /></td>                                    
                            <td>
                         <s:url id="url" action="../gatewayagent/DownloadMessage">
                              <s:param name="transactionId" value="id" />
                              <s:param name="filename" value="time" />
                              <s:param name="contentType" value="contentType" />
                          </s:url>
                				<s:a href="%{url}">Download</s:a>                    
                            </td>                                    
                        </tr>
                    </s:iterator>
                </table>
            </div>
        </div>
    </s:if>
    <s:else>
        No Log Entries exist for this test case.
    </s:else>
 </div>