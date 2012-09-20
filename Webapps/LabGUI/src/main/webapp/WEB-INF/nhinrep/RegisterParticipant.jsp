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
<s:form action="RegisterParticipant" theme="simple">
    <s:hidden key="registerAction" />
    <s:hidden key="changedPatientInfo" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - Register Participant</span></td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="clear" id="clear" value="Clear"
                               title="Clear this Participant`s Registration Information"
                               onclick="clearRegistration();"/>
                        <input type="button" class="inputButton" name="save" id="save" value="Save"
                               title="Save this Participant`s Registration Information"
                               onclick="saveRegistration();"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <!-- action error message(s) -->
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
            <table border="0">
                <tr>
                    <td><i><s:label cssClass="long" key="required.fields"/></i></td>
                </tr>
            </table>
            <div class="section-title">
                Login Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short" key="user.username"/></td>
                    <td><s:textfield cssClass="inputText" key="userName" size="60" maxlength="255" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="user.password"/></td>
                    <td><s:password cssClass="inputText" key="password" size="60" maxlength="255" /></td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                NHIN Participant Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short" key="participant.participantName"/></td>
                    <td><s:textfield cssClass="inputText" key="participantName" size="60" maxlength="255" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="participant.communityId"/></td>
                    <td><s:textfield cssClass="inputText" key="communityId" size="60" maxlength="255" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="participant.assigningAuthorityId"/></td>
                    <td><s:textfield cssClass="inputText" key="assigningAuthorityId" size="60" maxlength="255" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="participant.ipAddress"/></td>
                    <td><s:textfield cssClass="inputText" key="ipAddress" size="60" maxlength="45" /></td>
                </tr>
                 <tr>
                    <td><s:label cssClass="short" key="participant.gatewaytype"/></td>
                    <td align="left">
                         <select class="inputSelect" title ="Please select the participant gateway version" name="version" id="version" theme="simple" onchange="javascript:participantDataModified();">
                             <option value="<s:property value="@net.aegis.lab.util.LabConstants@Axolotl" />">AXOLOTL 6</option>
                             <option value="<s:property value="@net.aegis.lab.util.LabConstants@Epic" />">EPIC</option>
                             <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_248" />" selected>CONNECT 2.4.8</option>
                             <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_31" />">CONNECT 3.1</option>
                             <option value="<s:property value="@net.aegis.lab.util.LabConstants@Connect_Version_32" />">CONNECT 3.2</option>
                         </select>
                     </td>
                </tr>
            </table>
            <hr />
            <table>
                <tr>
                    <td><s:label cssClass="short" key="participant.contactName"/></td>
                    <td><s:textfield cssClass="inputText" key="contactName" size="60" maxlength="255" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="participant.contactPhone"/></td>
                    <td><s:textfield cssClass="inputText" key="contactPhone" size="60" maxlength="45" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="participant.contactEmail"/></td>
                    <td><s:textfield cssClass="inputText" key="contactEmail" size="60" maxlength="225" /></td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                NHIN Representative Assignment
            </div>
        </div>

        <div class="content-area">
            <div class="section-title">
                NHIN Participant Attributes
            </div>
            <table>
                <tr class="odd-row">
                    <td class="test-name"><s:label cssClass="short" key="nhin.participation"/></td>
                    <td class="test-options">
                        <s:checkbox cssClass="inputCheckbox" name="initiatorInd" value="%{initiatorIndSet}" fieldValue="Y" /><s:text name="participant.initiatorInd"/><br />
                        <s:checkbox cssClass="inputCheckbox" name="responderInd" value="%{responderIndSet}" fieldValue="Y" /><s:text name="participant.responderInd"/>
                    </td>
                </tr>
                <tr class="even-row">
                    <td class="test-name"><s:label cssClass="short" key="participant.ssnHandlingInd"/></td>
                    <td class="test-options">
                        <s:radio cssClass="inputRadio" key="ssnHandlingInd" theme="radiosimplevertical" list="#{'Y':'Allowed','N':'Not Allowed'}"/>
                    </td>
                </tr>
                <tr class="odd-row">
                    <td class="test-name"><s:label cssClass="short" key="participant.dynamicContentInd"/></td>
                    <td class="test-options">
                        <s:radio cssClass="inputRadio" key="dynamicContentInd" theme="radiosimplevertical" list="#{'S':'Single','M':'Multiple'}"/>
                    </td>
                </tr>
            </table>
        </div>

        <%--<div class="collapsible" id="collapsible-areas">--%>
            <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-1'); return false;">
                Patient Id Setup
            </div>
            <div class="collapsible-area" id="collapsible-panel-1">
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
                            <s:iterator value="participantPatientMapList" status="setStatus">
                                <tr class="<s:if test="#setStatus.odd == true ">odd-row</s:if>">
                                    <td class="value">
                                        <s:textfield cssClass="inputText" name="participantPatientText[%{#setStatus.index}]"  value="%{participantPatientText[#setStatus.index]}"  size="30" maxlength="225" onchange="javascript:dataModified(); "/>
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
    </div>
</s:form>


<script type="text/javascript">
    //
    // Trim is a useful function available in languages like Java & PHP  which
    // removes the leading and traling whitespace(s) from a String. Unfortunately
    // Javascript doesn't natively provide trim functionality to the String object.
    // Fortunately there is a simple solution as below.
    //
 
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g,"");
    };

    function clearRegistration() {
        if (confirm('This action will clear all data entry fields. Please confirm this action.')) {
            document.getElementById("RegisterParticipant").registerAction.value = "clear";
            document.getElementById("RegisterParticipant").submit();
        }
    }
    function saveRegistration() {

        var uName = new String(document.getElementById("RegisterParticipant").userName.value);
        var uPwd = new String(document.getElementById("RegisterParticipant").password.value);
        var candName = new String(document.getElementById("RegisterParticipant").participantName.value);
        var commId = new String(document.getElementById("RegisterParticipant").communityId.value);
        var aAId = new String(document.getElementById("RegisterParticipant").assigningAuthorityId.value);
        var ipaddr = new String(document.getElementById("RegisterParticipant").ipAddress.value);
        var contName = new String(document.getElementById("RegisterParticipant").contactName.value);
        var contPh = new String(document.getElementById("RegisterParticipant").contactPhone.value);
        var contEmail = new String(document.getElementById("RegisterParticipant").contactEmail.value);
        var ssnRadio1 = document.getElementById("RegisterParticipant").ssnHandlingInd[0].checked;
        var ssnRadio2 = document.getElementById("RegisterParticipant").ssnHandlingInd[1].checked;
        var intInd =  document.getElementById("RegisterParticipant").initiatorInd.checked;
        var resInd =  document.getElementById("RegisterParticipant").responderInd.checked;
       
      
        if((uName.trim() == '') || (uPwd.trim() == '') || (candName.trim() == '') ||
            (commId.trim() == '') || (aAId.trim() == '') || (ipaddr.trim() == '') ||
            (contName.trim() == '') || (contPh.trim() == '') || (contEmail.trim() == '') || ((ssnRadio1 ==false) && (ssnRadio2 == false))|| ((intInd ==false) && (resInd == false)) ){
            alert("Please fill in all the required(*) information on this form.");
            // do not submit the form.  server-side validation not implemented yet.
            return;
        }

        if (confirm('This action will sumbit the registration information for this NHIN Participant. Ensure the information is correct before proceeding.')) {
            document.getElementById("RegisterParticipant").registerAction.value = "save";
            document.getElementById("RegisterParticipant").submit();
        }
    }
    function dataModified(){
        document.getElementById("RegisterParticipant").changedPatientInfo.value="true";
     }
</script>