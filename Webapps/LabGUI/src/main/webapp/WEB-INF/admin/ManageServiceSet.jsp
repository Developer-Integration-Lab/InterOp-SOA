<%--
    Document   : Manage Service Set
    Created on : Oct 29, 2010, 03:28:34 PM
    Author     : Tareq.Nabeel
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<s:form action="ManageServiceSet" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:text name="admin.serviceset.manageserviceset" /></span></td>
                    <td class="title-button">
                        <s:submit action="UpdateServiceSet" cssClass="inputButton" value="Save Changes" />
                        <s:submit action="ManageServiceSets" cssClass="inputButton" value="Close" />
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
            <div class="section-title">
                <s:text name="admin.serviceset.attributes" /> - <s:property value="serviceSet.setName" />
            </div>
            <table id="serviceSetTable">
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.name" for="serviceSetName"/></td>
                    <td><s:textfield id="serviceSetName" cssClass="inputText" name="serviceSet.setName" size="60" maxlength="255" onchange="serviceSetDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.participation"/></td>
                    <td>
                        <s:checkbox cssClass="inputCheckbox" name="serviceSet.initiatorAllowedInd" onchange="serviceSetDataModified();"/><s:text name="admin.common.field.participation.initiator"/>
                        <s:checkbox cssClass="inputCheckbox" name="serviceSet.responderAllowedInd" onchange="serviceSetDataModified();"/><s:text name="admin.common.field.participation.responder"/>
                    </td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.ssnHandling" for="ssnHandling"/></td>
                    <td><s:radio id="ssnHandling" cssClass="inputRadio" name="serviceSet.ssnHandlingInd" list="#{'Y':getText('admin.common.field.ssnHandling.allowed'),'N':getText('admin.common.field.ssnHandling.notallowed')}" onchange="serviceSetDataModified();"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="admin.common.field.status" for="serviceSetStatus"/></td>
                    <td><s:radio id="status" cssClass="inputRadio" name="serviceSet.status" list="#{'ACTIVE':getText('admin.common.field.status.active'),'DISABLED':getText('admin.common.field.status.inactive')}" onchange="serviceSetDataModified();"/></td>
                </tr>
            </table>

        </div>
    </div>
    <s:hidden name="id" />
    <s:hidden id="serviceSetModified"  name="serviceSetModified" />
</s:form>

<script type="text/javascript">

    function serviceSetDataModified(){
        document.getElementById("serviceSetModified").value="true";
    }

</script>