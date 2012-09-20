<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <s:form action="Search" theme="simple">
        <s:hidden key="doSearch" value="Y" />
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text">My Patient Search</span></td>
                        <td class="title-button"><input type="submit" class="inputButton" name="search" id="search" value="Search"/></td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    Search Criteria
                </div>
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="patient.patientId"/></td>
                        <td><s:textfield cssClass="inputText" key="patient.patientId" size="20" maxlength="64" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="patient.firstName"/></td>
                        <td><s:textfield cssClass="inputText" key="patient.firstName" size="30" maxlength="45" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="patient.lastName"/></td>
                        <td><s:textfield cssClass="inputText" key="patient.lastName" size="30" maxlength="45" /></td>
                    </tr>
                </table>
            </div>

            <div class="collapsible" id="collapsible-areas">
                <!--div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-1'); return false;"-->
                <div class="section-title">
                    Search Results
                </div>
                <!--div class="collapsible-area" id="collapsible-panel-1">
                    <span class="collapsible-content"-->
                        <table class="search-results">
                            <tr class="search-headers">
                                <th>Patient ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Address</th>
                                <th>Phone</th>
                                <th>Birth Date</th>
                                <th>Gender</th>
                                <th>SSN</th>
                            </tr>
<s:iterator value="patients" status="patientStatus">
                            <tr class="<s:if test="#patientStatus.odd == true ">odd-row</s:if>">
                                <td class="value">
                                    <a href="<%=request.getContextPath()%>/patient/Detail?selectedPatientId=<s:property value="patientId" />"
                                       title="Patient Detail">
                                        <s:property value="patientId" />
                                    </a>
                                </td>
                                <td class="value"><s:property value="firstName" /></td>
                                <td class="value"><s:property value="lastName" /></td>
                                <td class="value">
                                    <s:property value="addressLine1" /><br><s:property value="city" />,&nbsp;<s:property value="state" />&nbsp;<s:property value="zipCode" />
                                </td>
                                <td class="value"><s:property value="workPhone" /></td>
                                <td class="value"><s:property value="dateOfBirth" /></td>
                                <td class="value"><s:property value="gender" /></td>
                                <td class="value"><s:property value="ssn" /></td>
                            </tr>
</s:iterator>
                        </table>
                    <!--/span>
                </div-->
            </div>

        </div>
    </s:form>