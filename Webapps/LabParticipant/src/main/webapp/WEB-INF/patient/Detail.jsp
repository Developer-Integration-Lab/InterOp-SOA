<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text">My Patient Search - Detail</span></td>
                    <td class="title-button">
                        <s:form action="Search" theme="simple">
                        <input type="submit" class="inputButton" name="search" id="search" value="Search"/>
                        </s:form>
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-subtitle">
            Patient Detail
        </div>
        <div class="content-area">
            <table>
                <tr>
                    <td><s:label theme="simple" cssClass="short-display" key="patient.patientId"/></td>
                    <td><span class="long-display"><s:property value="patient.patientId" /></span></td>
                </tr>
                <tr>
                    <td><s:label theme="simple" cssClass="short-display" key="patient.firstName"/></td>
                    <td><span class="long-display"><s:property value="patient.firstName" /></span></td>
                </tr>
                <tr>
                    <td><s:label theme="simple" cssClass="short-display" key="patient.lastName"/></td>
                    <td><span class="long-display"><s:property value="patient.lastName" /></span></td>
                </tr>
            </table>
        </div>

        <div class="collapsible" id="collapsible-areas">
            <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-1'); return false;">
                Documents
            </div>
            <div class="collapsible-area" id="collapsible-panel-1">
                <span class="collapsible-content">
                    <table class="search-results">
                        <tr class="search-headers">
                            <th>Document ID</th>
                            <th>Creation Date</th>
                            <th>Title</th>
                            <th>Document Type</th>
                            <th>Institution</th>
                        </tr>
<s:iterator value="documents" status="documentStatus">
                        <tr class="<s:if test="#documentStatus.odd == true ">odd-row</s:if>">
                            <td class="value"><s:property value="documentid" /></td>
                            <td class="value"><s:property value="creationTimeStr" /></td>
                            <td class="value"><s:property value="documentTitle" /></td>
                            <td class="value"><s:property value="typeCodeDisplayName" /></td>
                            <td class="value"><s:property value="facilityCodeDisplayName" /></td>
                        </tr>
</s:iterator>
                    </table>
                </span>
            </div>
        </div>

    </div>