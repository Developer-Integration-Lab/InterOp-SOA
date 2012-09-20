<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%
    response.addHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
    response.addHeader("Cache-Control","pre-check=0,post-check=0");
    response.setDateHeader("Expires",0);
%>
<%
    int iTheRoleOfTheLoggedInUser = -1;
    Participant objParticipantThatNhinRepWorksWith = null;
    String strParticipantThatNhinRepWorksWith = "";

    // Reference: See implementation of determineRoleForward() method in Login.java.
    iTheRoleOfTheLoggedInUser = ((User)session.getAttribute("userProfile")).getPrimaryRole();

    if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {   // avoid any NPE further.
        objParticipantThatNhinRepWorksWith = ((User)session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
        if(null == objParticipantThatNhinRepWorksWith) { strParticipantThatNhinRepWorksWith = "none"; }
        else { strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName(); }
    }
%>
<s:form action="TestHistory" theme="simple">
    <s:hidden key="userAction" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td>
                        <span class="content-title-text">
                            <% if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_PARTICIPANT) { %>
                            <s:property value="#session.userProfile.participant.participantName" /> - Test History
                            <%} else if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {%>
                            <s:property value="#session.userProfile.nhinrep.name" /> - Test History
                            <%}%>
                        </span>
                    </td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                               title="Refresh the list of previous service set(s)"
                               onclick="refreshListOfPreviousServiceSets();"/>
                    </td>
                </tr>
                <% if(iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) {%>
                <tr>
                    <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                </tr>
                <%}%>
            </table>
        </div>
        <% if (iTheRoleOfTheLoggedInUser == LabConstants.ROLE_PARTICIPANT) { %>
        <div class="content-area">
            <div class="section-title">
                Connection Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.communityId"/></td>
                    <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.assigningAuthorityId"/></td>
                    <td><span class="long-display"><s:property value="participant.assigningAuthorityId" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.ipAddress"/></td>
                    <td><span class="long-display"><s:property value="participant.ipAddress" /></span></td>
                </tr>
            </table>
        </div>
        <% } %>
        <% if (iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) { %>
        <div class="content-area">
            <div class="section-title">
                <s:property value="#session.userProfile.nhinrep.name" /> Contact Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short-display" key="nhinrep.contactName"/></td>
                    <td><span class="long-display"><s:property value="nhinrep.contactName" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="nhinrep.contactPhone"/></td>
                    <td><span class="long-display"><s:property value="nhinrep.contactPhone" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="nhinrep.contactEmail"/></td>
                    <td><span class="long-display"><s:property value="nhinrep.contactEmail" /></span></td>
                </tr>
            </table>
        </div>
        <div class="collapsible-title expanded" onclick="thisPagePanelClick(this, 'collapsible-panel-1'); return false;">
            <s:property value="participant.participantName" /> Connection Information <span style="font-size:8pt; font-style:italic">(click to expand/collapse)</span>
        </div>
        <div class="collapsible-area" id="collapsible-panel-1">
            <span class="collapsible-content">
                <div class="content-area">
                    <table>
                        <tr>
                            <td><s:label cssClass="short-display" key="participant.communityId"/></td>
                            <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                        </tr>
                        <tr>
                            <td><s:label cssClass="short-display" key="participant.assigningAuthorityId"/></td>
                            <td><span class="long-display"><s:property value="participant.assigningAuthorityId" /></span></td>
                        </tr>
                        <tr>
                            <td><s:label cssClass="short-display" key="participant.ipAddress"/></td>
                            <td><span class="long-display"><s:property value="participant.ipAddress" /></span></td>
                        </tr>
                    </table>
                </div>
            </span>
        </div>
        <% } %>

        <div class="content-area">
            <div class="section-title">
                Previous Service Sets
            </div>
            <!-- action error message(s) -->
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
            <s:if test="previousServiceSets.size() > 0">
            <table class="search-results">
                <tr class="search-results">
                   <% if(iTheRoleOfTheLoggedInUser != LabConstants.ROLE_PARTICIPANT) { %>
                    <th>Type</th>
                    <% } %>
                    <th>Execution Unique Identifier</th>
                    <th>Current Status</th>
                    <th>Results</th>
                    <th>Submitted</th>
                    <th>&nbsp;</th>
                </tr>
<s:iterator value="previousServiceSets" status="setStatus">
                    <tr class="<s:if test="#setStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                       <% if(iTheRoleOfTheLoggedInUser != LabConstants.ROLE_PARTICIPANT) { %>
                        <td>
                            <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(serviceset.labAccessFilter)"/>                            
                        </td>
                        <% } %>
                        <td>
                            <a href="<%=request.getContextPath()%>/history/HistoryTestResults?executionUniqueId=<s:property value="executionUniqueId" />" title="<s:property value="" />">
                                <s:property value="executionUniqueId" />
                            </a>
                        </td>
                        <td><s:property value="status" /></td>
                        <td align="center"><s:property escape="false" value="resultsMeterHtml" /></td>
                        <td>
                             <s:property value="completedString" />
                        </td>
                        <td>
                            <input type="button" class="inputButton" name="scenarioComments" id="execute" value="Display Comments <s:property value="" />"
                                   onclick="displayScenarioComments('<s:property value="executionUniqueId" />');" />
                        </td>
                    </tr>
</s:iterator>
            </table>
            </s:if>
            <s:else>
                No Previous Service Sets found.
                <% if (iTheRoleOfTheLoggedInUser == LabConstants.ROLE_NHIN_REP) { %>
                <br><span style="font-size:8pt; font-style:italic">(Are you <a href="<%=request.getContextPath()%>/nhinrep/NhinrepDashboard">working with</a> a participant?)</span>
                <% } %>
            </s:else>
        </div>

    </div>
</s:form>
<script type="text/javascript">
        // *********************************************************************
        // Get the latest list of historical service sets for the logged in user
        // *********************************************************************
        function refreshListOfPreviousServiceSets() {
            document.getElementById("TestHistory").userAction.value = "refresh";
            document.getElementById("TestHistory").submit();
        }

        function thisPagePanelClick(titlebar, panelName) {
            speed = 0.2;

            if ($(panelName).visible()) {
                    Effect.BlindUp(panelName, {duration: speed});
                    //titlebar.className = "collapsible-title collapsed"
            }
            else {
                    Effect.BlindDown(panelName, {duration: speed});
                    //titlebar.className = "collapsible-title expanded"
            }
        }

        function displayScenarioComments(pExecutionUniqueId) {

            var urlString = "<%=request.getContextPath()%>/history/DisplayComments?executionUniqueId=" + pExecutionUniqueId;
            var stdOptions = "resizable=yes,directories=no,left=80,top=80,toolbar=no,location=no";
            var options = stdOptions + ",width=1100,height=500,scrollbars=yes";
            displayCommentsWindow = window.open(urlString, "displayCommentsWindow" ,options);
            if (window.focus) {
                displayCommentsWindow.focus();
            }
        }

</script>