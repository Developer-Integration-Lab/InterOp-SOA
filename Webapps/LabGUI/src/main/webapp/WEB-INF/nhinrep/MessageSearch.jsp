<%-- 
    Document   : MessageSearch
    Created on : Apr 7, 2011, 3:57:48 PM
    Author     : Jyoti.Devarakonda
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/script/datetimepicker_css.js"></script>
<s:form action="MessageSearch" theme="simple">
    <s:hidden key="doSearch" value="yes" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - Test Harness Message Search</span></td>
                    <td class="title-button">
                        <input type="submit" class="inputButton" name="search" id="search" value="Search" onclick="searchMsgs();"/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                Search Criteria
            </div>
            <table>
                <tr>
                    <td width="50%">
                        <table>
                            <tr>
                                <td nowrap width="20%"><s:label cssClass="long" key="log.search.start"/></td>
                                <td width="80%">
                                    <s:textfield cssClass="inputText" value="%{startDatetime}" name="startDatetime" size="30" maxlength="30" title="Format: yyyy-MM-dd HH:mm:ss" />
                                    <a href="javascript: NewCssCal('MessageSearch_startDatetime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
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
                                    <a href="javascript: NewCssCal('MessageSearch_endDatetime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <div class="section-title">
                Test Harness Message Search Results
            </div>
            <!-- action error message(s) -->
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>

            <div id="waitMsg" style="visibility: hidden; height: 0">
                <font color="red">Please wait while executing the search results..</font>
            </div>

            <!--s:if test="@net.aegis.lab.util.LabConstants$LabType@LAB.equals(#session.userProfile.getLabType())"-->
            <div class="content-subtitle">Test Harness RI1 - <s:if test="ri1AuditLogList.size() > 0"><s:property value="ri1AuditLogList.size()" /></s:if> Messages</div>
            <s:if test="ri1AuditLogList != null and ri1AuditLogList.size() > 0" >
                <div class="scrollable-area-200">
                    <table class="search-results">
                        <tr class="search-headers">
                            <th>Timestamp </th>
                            <th>ServiceSetId</th>
                            <th>Status</th>
                            <th>TestCase</th>
                            <th>Direction</th>
                            <th>From HCID</th>
                            <th>To HCID</th>                           
                            <th>Message</th>
                        </tr>
                        <s:iterator value="ri1AuditLogList" status="ri1Status">
                            <tr class="<s:if test="#ri1Status.odd == true">odd-row</s:if>">
                                <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><s:property value="serviceSetId" /></td>
                                <td><s:property value="status" /></td>
                                <s:if test="service =='PD' || service == 'QD' || service == 'RD'">
                                    <td><s:property value="service" /><sup>**</sup></td>
                                </s:if>
                                <s:else>
                                    <td><s:property value="service" /></td>
                                </s:else>
                                <td><s:property value="direction" /></td>
                                <td><s:property value="fromHCID" /></td>
                                <td><s:property value="toHCID" /></td>
                                <td>
                                    <input type="button" class="inputButton" name="audit1message" id="audit1message" value="Download"
                                           onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                </td>                            
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </s:if>
            <s:if test="ri1AuditLogList != null and ri1AuditLogList.size() == 0">
                <table class="search-results">
                    <tr><td>No RI1 Messages found.</td></tr>
                </table>
            </s:if>

            <div class="content-subtitle">Test Harness RI2 - <s:if test="ri2AuditLogList.size() > 0"><s:property value="ri2AuditLogList.size()" /></s:if> Messages</div>
            <s:if test="ri2AuditLogList != null and ri2AuditLogList.size() > 0" >
                <div class="scrollable-area-200">
                    <table class="search-results">
                        <tr class="search-headers">
                            <th>Timestamp </th>
                            <th>ServiceSetId</th>
                            <th>Status</th>
                            <th>TestCase </th>
                            <th>Direction</th>
                            <th>From HCID</th>
                            <th>To HCID</th>
                            <th>Message</th>
                        </tr>
                        <s:iterator value="ri2AuditLogList" status="ri2TMWStatus">
                            <tr class="<s:if test="#ri2TMWStatus.odd == true">odd-row</s:if>">
                                <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><s:property value="serviceSetId" /></td>
                                <td><s:property value="status" /></td>
                                <s:if test="service =='PD' || service == 'QD' || service == 'RD'">
                                    <td><s:property value="service" /><sup>**</sup></td>
                                </s:if>
                                <s:else>
                                    <td><s:property value="service" /></td>
                                </s:else>
                                <td><s:property value="direction" /></td>
                                <td><s:property value="fromHCID" /></td>
                                <td><s:property value="toHCID" /></td>
                                <td>
                                    <input type="button" class="inputButton" name="audit1message" id="audit1message" value="Download"
                                           onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </s:if>
            <s:if test="ri2AuditLogList != null and ri2AuditLogList.size() == 0">
                <table class="search-results">
                    <tr><td>No RI2 Messages found.</td></tr>
                </table>
            </s:if>

            <div class="content-subtitle">Test Harness RI3 - <s:if test="ri3AuditLogList.size() > 0"><s:property value="ri3AuditLogList.size()" /></s:if> Messages</div>
            <s:if test="ri3AuditLogList != null and ri3AuditLogList.size() > 0" >
                <div class="scrollable-area-200">
                    <table class="search-results">
                        <tr class="search-headers">
                            <th>Timestamp </th>
                            <th>ServiceSetId</th>
                            <th>Status</th>
                            <th>TestCase</th>
                            <th>Direction</th>
                            <th>From HCID</th>
                            <th>To HCID</th>
                            <th>Message</th>
                        </tr>
                        <s:iterator value="ri3AuditLogList" status="ri3TMWStatus">
                            <tr class="<s:if test="#ri3TMWStatus.odd == true">odd-row</s:if>">
                                <td><s:date name="timestamp" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><s:property value="serviceSetId" /></td>
                                <td><s:property value="status" /></td>
                                <s:if test="service =='PD' || service == 'QD' || service == 'RD'">
                                    <td><s:property value="service" /><sup>**</sup></td>
                                </s:if>
                                <s:else>
                                    <td><s:property value="service" /></td>
                                </s:else>
                                <td><s:property value="direction" /></td>
                                <td><s:property value="fromHCID" /></td>
                                <td><s:property value="toHCID" /></td>
                                <td>
                                    <input type="button" class="inputButton" name="audit1message" id="audit1message" value="Download"
                                           onclick="downloadLogMessage('<s:property value="testHarnessId" />', '<s:property value="auditRepoId"/>');" />
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </s:if>
            <s:if test="ri3AuditLogList != null and ri3AuditLogList.size() == 0">
                <table class="search-results">
                    <tr><td>No RI3 Messages found.</td></tr>
                </table>
            </s:if>
            <!--/s:if-->
            <br><p><font style="font-style:italic; font-size:0.8em;">Legend:<br>
                    &nbsp; * Required Criteria<br>
                    ** Testcase name could not be identified. </font></p>
        </div>
    </div>
</s:form>
<script type="text/javascript">
    function downloadAuditLogMessage(pTestHarnessId, pAuditRepositoryId) {
        document.getElementById("PopTWMessages").action = "<%=request.getContextPath()%>/help/DownloadAuditLogMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
        document.getElementById("PopTWMessages").submit();
        document.getElementById("PopTWMessages").action = "<%=request.getContextPath()%>/participant/PopTWMessages";
    }

    function downloadLogMessage(pTestHarnessId, pAuditRepositoryId)
    {
        var urlString = "<%=request.getContextPath()%>/help/DownloadAuditExtensionMessage?testHarnessId=" + pTestHarnessId + "&auditRepositoryId=" + pAuditRepositoryId;
        var stdOptions = "resizable=yes,directories=no,left=100,top=40,menubar=yes,location=no";
        var options = stdOptions + ",width=800,height=600,scrollbars=yes";
        window.open(urlString, "ContentWindow" ,options);
        return false;
    }

    function searchMsgs(){
        document.getElementById("waitMsg").style.visibility="visible" ;
        document.getElementById("waitMsg").style.height="auto" ;
        document.forms[0].action="ValidMessageSearch";
        document.forms[0].submit();
    }
</script>
