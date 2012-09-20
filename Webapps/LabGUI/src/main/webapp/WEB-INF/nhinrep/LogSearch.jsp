<%-- 
    Document   : LogSearch
    Created on : May 8, 2010, 14:39:33
    Author     : richard.ettema
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%
            Participant objParticipantThatNhinRepWorksWith = null;
            String strParticipantThatNhinRepWorksWith = "";

            objParticipantThatNhinRepWorksWith = ((User) session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
            if (null == objParticipantThatNhinRepWorksWith) {
                strParticipantThatNhinRepWorksWith = "none";
            } else {
                strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName();
            }
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/datetimepicker_css.js"></script>
<s:form action="LogSearch" theme="simple">
    <s:hidden key="doSearch" value="yes" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - Test Harness Audit Log Entry Search</span></td>
                    <td class="title-button">
                        <input type="submit" class="inputButton" name="search" id="search" value="Search"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                Search Criteria
            </div>
            <table>
                <tr>
                    <td colspan="2"><font style="font-style:italic; font-size:0.8em;">*Required Criteria</font></td>
                </tr>
                <tr>
                    <td width="50%">
                        <table>
                            <tr>
                                <td nowrap width="20%"><s:label cssClass="long" key="log.search.start"/></td>
                                <td width="80%">
                                    <s:textfield cssClass="inputText" value="%{startDatetime}" name="startDatetime" size="30" maxlength="30" title="Format: yyyy-MM-dd HH:mm:ss" />
                                    <a href="javascript: NewCssCal('LogSearch_startDatetime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="50%">
                        <table>
                            <tr>
                                <td nowrap width="20%"><s:label cssClass="long" key="log.search.end"/></td>
                                <td width="80%">
                                    <s:textfield cssClass="inputText" value="%{endDatetime}" name="endDatetime" size="30" maxlength="30" title="Format: yyyy-MM-dd HH:mm:ss" />
                                    <a href="javascript: NewCssCal('LogSearch_endDatetime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <div class="section-title">
                Search Results
            </div>
            <!-- action error message(s) -->
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
            <div class="content-subtitle">Test Harness RI 1 - Audit Logs</div>
            <s:if test="ri1AuditLogList.size() > 0">
                <div class="scrollable-area-200">
                    <table class="search-results" >
                        <tr class="search-headers">
                            <th>Id</th>
                            <th>Execute Time </th>
                            <th>Event Id</th>
                            <th>User Id</th>
                            <th>Type Code</th>
                            <th>Type Code Role</th>
                            <th>ID Type Code</th>
                            <th>Receiver Patient Id</th>
                            <th>Sender Patient Id</th>
                            <th>Community Id</th>
                            <th>Message Type</th>
                            <th>Message</th>
                        </tr>
                        <s:iterator value="ri1AuditLogList" status="ri1AuditStatus">
                            <tr class="<s:if test="#ri1AuditStatus.odd == true ">odd-row</s:if>">
                                <td><s:property value="id" /></td>
                                <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><s:property value="eventId" /></td>
                                <td><s:property value="userId" /></td>
                                <td><s:property value="participationTypeCode" /></td>
                                <td><s:property value="participationTypeCodeRole" /></td>
                                <td><s:property value="participationIDTypeCode" /></td>
                                <td><s:property value="receiverPatientId" /></td>
                                <td><s:property value="senderPatientId" /></td>
                                <td><s:property value="communityId" /></td>
                                <td><s:property value="messageType" /></td>
                                <td>
                                    <input type="button" class="inputButton" name="audit1message" id="audit1message" value="Download"
                                           onclick="downloadAuditLogMessage('1', '<s:property value="id" />');" />
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </s:if>
            <s:else>
                <div>No Log Entries found.</div>
            </s:else>
            <div class="content-subtitle">Test Harness RI 2 - Audit Logs</div>
            <s:if test="ri2AuditLogList.size() > 0">
                <div class="scrollable-area-200">
                    <table class="search-results" >
                        <tr class="search-headers">
                            <th>Id</th>
                            <th>Execute Time </th>
                            <th>Event Id</th>
                            <th>User Id</th>
                            <th>Type Code</th>
                            <th>Type Code Role</th>
                            <th>ID Type Code</th>
                            <th>Receiver Patient Id</th>
                            <th>Sender Patient Id</th>
                            <th>Community Id</th>
                            <th>Message Type</th>
                            <th>Message</th>
                        </tr>
                        <s:iterator value="ri2AuditLogList" status="ri2AuditStatus">
                            <tr class="<s:if test="#ri2AuditStatus.odd == true ">odd-row</s:if>">
                                <td><s:property value="id" /></td>
                                <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><s:property value="eventId" /></td>
                                <td><s:property value="userId" /></td>
                                <td><s:property value="participationTypeCode" /></td>
                                <td><s:property value="participationTypeCodeRole" /></td>
                                <td><s:property value="participationIDTypeCode" /></td>
                                <td><s:property value="receiverPatientId" /></td>
                                <td><s:property value="senderPatientId" /></td>
                                <td><s:property value="communityId" /></td>
                                <td><s:property value="messageType" /></td>
                                <td>
                                    <input type="button" class="inputButton" name="audit2message" id="audit2message" value="Download"
                                           onclick="downloadAuditLogMessage('2', '<s:property value="id" />');" />
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </s:if>
            <s:else>
                <div>No Log Entries found.</div>
            </s:else>

            <div class="content-subtitle">Test Harness RI 3 - Audit Logs</div>
            <s:if test="ri3AuditLogList.size() > 0">
                <div class="scrollable-area-200">
                    <table class="search-results" >
                        <tr class="search-headers">
                            <th>Id</th>
                            <th>Execute Time </th>
                            <th>Event Id</th>
                            <th>User Id</th>
                            <th>Type Code</th>
                            <th>Type Code Role</th>
                            <th>ID Type Code</th>
                            <th>Receiver Patient Id</th>
                            <th>Sender Patient Id</th>
                            <th>Community Id</th>
                            <th>Message Type</th>
                            <th>Message</th>
                        </tr>
                        <s:iterator value="ri3AuditLogList" status="ri3AuditStatus">
                            <tr class="<s:if test="#ri3AuditStatus.odd == true ">odd-row</s:if>">
                                <td><s:property value="id" /></td>
                                <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><s:property value="eventId" /></td>
                                <td><s:property value="userId" /></td>
                                <td><s:property value="participationTypeCode" /></td>
                                <td><s:property value="participationTypeCodeRole" /></td>
                                <td><s:property value="participationIDTypeCode" /></td>
                                <td><s:property value="receiverPatientId" /></td>
                                <td><s:property value="senderPatientId" /></td>
                                <td><s:property value="communityId" /></td>
                                <td><s:property value="messageType" /></td>
                                <td>
                                    <input type="button" class="inputButton" name="audit3message" id="audit3message" value="Download"
                                           onclick="downloadAuditLogMessage('3', '<s:property value="id" />');" />
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </s:if>
            <s:else>
                <div>No Log Entries found.</div>
            </s:else>
        </div>
    </div>
</s:form>
<script type="text/javascript">
    function downloadAuditLogMessage(pTestHarnessId, pAuditRepositoryId) {
        document.getElementById("LogSearch").action = "<%=request.getContextPath()%>/help/DownloadAuditLogMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
        document.getElementById("LogSearch").submit();
        document.getElementById("LogSearch").action = "<%=request.getContextPath()%>/nhinrep/LogSearch";
    }
</script>
