<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:form action="MyInfo" theme="simple">
    <s:hidden key="changedPatientInfo" />
    <s:hidden key="changedParticipantInfo" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - My Information</span></td>
                    <td class="title-button"><input type="submit" class="inputButton" name="buttonName" id="save" value="Save Changes"/></td>
                </tr>
            </table>
        </div>

        <%--<div class="content-area">--%>
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>

            <div class="content-area">
            <div class="section-title">
                Connection Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short" key="participant.communityId"/></td>
                    <td><s:textfield cssClass="inputText" name="communityId" value="%{participant.communityId}" key="participant.communityId" size="60" maxlength="254" onchange="javascript:participantDataModified();"/></td>
                </tr>
                <%-- story 129
                <tr>
                    <td><s:label cssClass="short" key="participant.assigningAuthorityId"/></td>
                    <td><s:textfield cssClass="inputText" readonly="true" key="participant.assigningAuthorityId" size="60" maxlength="255" /></td>
                </tr>
                --%>
                <tr>
                    <td><s:label cssClass="short" key="participant.ipAddress"/></td>
                    <td><s:textfield cssClass="inputText" name="ipAddress" value="%{participant.ipAddress}" size="60" maxlength="45" onchange="javascript:participantDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="participant.gatewaytype"/></td>
                    <td><s:textfield cssClass="inputText" name="version" value="%{participant.version}" readonly="true" size="60" maxlength="45" onchange="javascript:participantDataModified();"/></td>
                </tr>
            </table>
            </div>

            <%--<div class="collapsible" id="collapsible-areas">--%>
            <%-- commented as per 10/May/11 3:14 PM in ILT-137
                <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-status'); return false;">
                    Connection Status
                </div>
                <div class="collapsible-area" id="collapsible-panel-status">
                    <span class="collapsible-content">
                        <div class="content-area">
                        <table>
                            <tr>
                                <td><s:label cssClass="long-right" key="participant.my.info.verify.connection.status"/></td>
                                <td>
                                    <s:if test="participant.commVerifyStatus == \"VERIFIED-GOOD\"">
                                        <span class="status-good">Verified</span>
                                    </s:if>
                                    <s:elseif test="participant.commVerifyStatus == \"VERIFIED-FAILED\"">
                                        <span class="status-bad">Failed</span>
                                    </s:elseif>
                                    <s:elseif test="participant.commVerifyStatus ==\"NOT VERIFIED\"">
                                        <span class="status-noAction">Not Verified</span>
                                    </s:elseif>
                                </td>
                            </tr>
                            <tr>
                                <td><s:label cssClass="long-right" key="participant.my.info.verify.connection.timestamp"/></td>
                                <td><span class="long-display"><s:date name="participant.commVerifyTimestamp" format="d MMM, yyyy hh:mm a" /></span></td>
                            </tr>
                        </table>
                        </div>
                    </span>
                </div>
               --%>
                <div class="content-area">
                <div class="section-title">
                    Account Information
                </div>
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="participant.participantName"/></td>
                        <td><s:textfield cssClass="inputText" name="participantName" value="%{participant.participantName}" size="60" maxlength="255" onchange="javascript:participantDataModified();"/></td>
                    </tr>
                </table>
                <hr />
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="participant.contactName"/></td>
                        <td><s:textfield cssClass="inputText" name="contactName" value="%{participant.contactName}" size="60" maxlength="255" onchange="javascript:participantDataModified();" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="participant.contactPhone"/></td>
                        <td><s:textfield cssClass="inputText" name="contactPhone" value="%{participant.contactPhone}" size="60" maxlength="45" onchange="javascript:participantDataModified();" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="participant.contactEmail"/></td>
                        <td><s:textfield cssClass="inputText" name="contactEmail" value="%{participant.contactEmail}" size="60" maxlength="225" onchange="javascript:participantDataModified();" /></td>
                    </tr>
                </table>
                </div>

                <div class="content-area">
                <div class="section-title">
                    Participant Attributes
                </div>
                <table class="content-data">
                    <tr class="odd-row">
                        <td><s:label key="nhin.participation"/></td>
                        <td nowrap>
                            <s:checkbox cssClass="inputCheckbox" disabled="false" name="initiatorInd" value="%{initiatorIndSet}" onchange="javascript:participantDataModified();" fieldValue="Y" /><s:text name="participant.initiatorInd"/><br />
                            <s:checkbox cssClass="inputCheckbox" disabled="false" name="responderInd" value="%{responderIndSet}" onchange="javascript:participantDataModified();" fieldValue="Y" /><s:text name="participant.responderInd"/>
                        </td>
                    </tr>
                    <tr class="even-row">
                        <td><s:label key="participant.ssnHandlingInd"/></td>
                        <td nowrap>
                            <s:radio cssClass="inputRadio" disabled="false" name="ssnHandlingInd" value="%{participant.ssnHandlingInd}"  onchange="javascript:participantDataModified();" theme="radiosimplevertical" list="#{'Y':'Allowed','N':'Not Allowed'}" />
                        </td>
                    </tr>
                    <tr class="odd-row">
                        <td><s:label key="participant.dynamicContentInd"/></td>
                        <td nowrap>
                            <s:radio cssClass="inputRadio" disabled="false" name="dynamicContentInd" value="%{participant.dynamicContentInd}"  onchange="javascript:participantDataModified();" theme="radiosimplevertical" list="#{'S':'Single','M':'Multiple'}" />
                        </td>
                    </tr>
                    <tr class="odd-row">
                        <td><s:label key="participant.resouceIdSendInd"/></td>
                        <td nowrap>
                            <s:radio cssClass="inputRadio" disabled="false" name="resouceIdSendInd"  value="%{participant.resouceIdSendInd}" onchange="javascript:participantDataModified();" theme="radiosimplevertical" list="#{'Y':'Yes','N':'No'}" />
                        </td>
                    </tr>

                </table>
                </div>

                <div class="content-area">
                <%--<div class="collapsible-title collapsed" onclick="panelClick(this, 'collapsible-panel-1'); return false;">--%>
                <div class="section-title">
                    Patient Id Setup
                </div>
                <%--<div class="collapsible-area" id="collapsible-panel-1">--%>
                    <span class="collapsible-content">
                        <div class="scrollable-area-400">
                            <table class="search-results" >
                                <tr class="search-headers">
                                    <th>Participant Patient Id</th>
                                    <th>Patient Id</th>
                                    <th>Name</th>
                                    <th>Address</th>
                                    <th>Date Of Birth</th>
                                    <th>Gender</th>
                                </tr>
                                <s:iterator value="participantPatientMapList" status="status">
                                    <tr class="<s:if test="#summaryStatus.odd == true ">odd-row</s:if>">
                                        <td class="value">
                                            <s:textfield cssClass="inputText" readonly="false" key="participantPatientId" size="30" maxlength="225" onchange="javascript:dataModified();"/>
                                        </td>
                                        <td align="left"><s:property value="patientId" /></td>
                                        <td align="left"><s:property value="patientName" /></td>
                                        <td align="left"><s:property value="patientAddress" escape="false" /></td>
                                        <td align="left"><s:property value="dateOfBirth" /></td>
                                        <td align="left"><s:property value="gender" /></td>
                                    </tr>
                                </s:iterator>
                            </table>
                        </div>
                    </span>
                </div>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>
</s:form>

<script type="text/javascript">

    function dataModified(){
        document.getElementById("MyInfo").changedPatientInfo.value="true";
    }

    function participantDataModified(){

       document.getElementById("MyInfo").changedParticipantInfo.value="true";

    }
</script>