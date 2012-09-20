<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <s:form action="MyInfo" theme="simple">
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text">My Information</span></td>
                        <!--td class="title-button"><button type="button" class="inputButton" disabled="true" name="save" id="save">Save Changes</button></td-->
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    Connection Information
                </div>
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="participant.communityId"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.communityId" size="60" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="participant.assigningAuthorityId"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.assigningAuthorityId" size="60" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="participant.ipAddress"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.ipAddress" size="60" maxlength="45" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="participant.gatewaytype"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.version" size="60" maxlength="45" /></td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td><strong>Verify connectivity between Participant System and Lab Test Platform:</strong></td>
                        <td>
                            <input type="button" class="inputButton" disabled="true" name="verify" id="verify" value="Verify"/>
                            <span class="status-good">Verified</span>
                            <!--span class="status-bad">Not Verified</span-->
                        </td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    Account Information
                </div>
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="participant.participantName"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.participantName" size="60" maxlength="255" /></td>
                    </tr>
                </table>
                <hr />
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="participant.contactName"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.contactName" size="60" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="participant.contactPhone"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.contactPhone" size="60" maxlength="45" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="participant.contactEmail"/></td>
                        <td><s:textfield cssClass="inputText" readonly="true" key="participant.contactEmail" size="60" maxlength="225" /></td>
                    </tr>
                </table>
                <hr />
                <table>
                    <tr class="odd-row">
                        <td class="test-name"><s:label cssClass="short" key="nhin.participation"/></td>
                        <td class="test-options">
                            <s:checkbox cssClass="inputCheckbox" disabled="true" name="participant.initiatorInd" value="%{initiatorIndSet}" fieldValue="Y" /><s:text name="participant.initiatorInd"/><br />
                            <s:checkbox cssClass="inputCheckbox" disabled="true" name="participant.responderInd" value="%{responderIndSet}" fieldValue="Y" /><s:text name="participant.responderInd"/>
                        </td>
                    </tr>
                    <tr class="even-row">
                        <td class="test-name"><s:label cssClass="short" key="participant.ssnHandlingInd"/></td>
                        <td class="test-options">
                            <s:radio cssClass="inputRadio" disabled="true" key="participant.ssnHandlingInd" theme="radiosimplevertical" list="#{'Y':'Allowed','N':'Not Allowed'}"/>
                        </td>
                    </tr>
                </table>
            </div>

        </div>
    </s:form>