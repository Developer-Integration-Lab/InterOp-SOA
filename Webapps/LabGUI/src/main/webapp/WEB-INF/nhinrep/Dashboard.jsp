<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<%
    response.addHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
    response.addHeader("Cache-Control","pre-check=0,post-check=0");
    response.setDateHeader("Expires",0);

    Participant objParticipantThatNhinRepWorksWith = null;
    String strParticipantThatNhinRepWorksWith = "";

    objParticipantThatNhinRepWorksWith = ((User)session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
    if(null == objParticipantThatNhinRepWorksWith) { strParticipantThatNhinRepWorksWith = "none"; }
    else { strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName(); }
%>
    <s:form action="NhinrepDashboard" theme="simple">
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - Dashboard</span></td>
                        <td class="title-button">
                            <input type="submit" class="inputButton" name="refresh" id="refresh" value="Refresh"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    Contact Information
                </div>
                <!-- action error message(s) -->
                <div>
                    <s:actionerror/>
                </div>
                <!-- action message(s) -->
                <div>
                    <strong style="color: #00f"><s:actionmessage/></strong>
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

            <div class="content-area">
                <div class="section-title">
                    Current Assigned Participants
                </div>
                <s:if test="nhinrep.participants.size() > 0">
                <table class="content-data">
                    <tr>
                        <th align="left">Name</th>
                        <th align="left">Contact</th>
                        <%-- <th>Phone</th> --%>
                        <%-- <th>Email</th> --%>
                        <th>&nbsp;</th>
                    </tr>
<s:iterator value="nhinrep.participants" status="canStatus">
                    <tr class="<s:if test="#canStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td><s:property value="participantName" /></td>
                        <td><s:property value="contactName" /></td>
                        <td>
                            <input type="button" class="inputButton" name="workingCand" id="execute" value="Work With <s:property value="" />"
                                   onclick="chooseWorkingParticipant('<s:property value="participantId" />', '<s:property value="participantName" />');" />
                        </td>
                    </tr>

                    <s:if test="servicesetexecutions.size() > 0">
                    <tr class="<s:if test="#canStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td colspan="3">
                            <table id="participant-dashboard" style="font-size:7pt; color:#006; background-color:#D9D9F3;" border="2">
                                <tr>
                                    <th>Type</th>
                                    <th>Name</th>
                                    <th>Current Status</th>
                                    <th>Results</th>
                                    <th>Initiated</th>
                                    <th>Completed</th>
                                </tr>
                                <s:iterator value="servicesetexecutions" status="setStatus">
                                <tr class="<s:if test="#setStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                                    <td>
                                        <s:property value="@net.aegis.lab.util.LabConstants$LabType@valueOf(serviceset.labAccessFilter)"/>
                                    </td>
                                    <td>
                                        <s:if test="participant.participantName == #session.userProfile.nhinrep.workingParticipant.participantName">
                                            <a href="<%=request.getContextPath()%>/nhinrep/ActiveTestResults?executionUniqueId=<s:property value="executionUniqueId" />">
                                                <s:property value="serviceset.setName" />
                                            </a>
                                        </s:if>
                                        <s:else>
                                            <s:property value="serviceset.setName" />
                                        </s:else>
                                    </td>
                                    <td><s:property escape="false" value="resultsMeterHtml" /></td>
                                    <td><s:property value="resultsString" /></td>
                                    <td><s:property value="initiatedString" /></td>
                                    <td><s:property value="completedString" /></td>
                                </tr>
                                </s:iterator>
                            </table>
                        </td>
                    </tr>
                    </s:if>
                    <s:else>
                    <tr class="<s:if test="#canStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td colspan="3">
                            <table id="participant-dashboard" style="font-size:9pt; color:#006;" border="0">
                                <tr>
                                    <td>No active service sets are available currently.</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    </s:else>

</s:iterator>
                </table>
                </s:if>
                <s:else>
                    No currently assigned participants found.
                    <br><span style="font-size:8pt; font-style:italic">(You may click on <a href="<%=request.getContextPath()%>/nhinrep/AssignParticipants">Assign Participants</a> on the left menu.)</span>
                </s:else>
            </div>

        </div>
    </s:form>
    <script type="text/javascript">
        function chooseWorkingParticipant(pParticipantId, pParticipantName) {
            if (confirm('Set your working participant to -- [' + pParticipantName + '] ?')) {
                document.getElementById("NhinrepDashboard").action = "<%=request.getContextPath()%>/nhinrep/WorkingParticipant?participantId=" + pParticipantId;
                document.getElementById("NhinrepDashboard").submit();
            }
        }

        function setVisible(obj) {
          obj = document.getElementById(obj);
          obj.style.visibility = (obj.style.visibility == 'visible') ? 'hidden' : 'visible';
        }

    </script>