<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:form action="ActiveScenarios" theme="simple">
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Active <s:property value="#attr.headerType"/> List</span></td>
                        <td class="title-button"><input type="submit" class="inputButton" name="refresh" id="refresh" value="Refresh"/></td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <table class="dashboard-table" id="participant-dashboard">
                    <tr>
                        <th><s:property value="#attr.headerType"/></th>
                        <th>Schedule</th>
                        <th>Description</th>
                    </tr>

<s:iterator value="testScenarios" status="testStatus">
                    <tr class="<s:if test="#testStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td nowrap><a href="<%=request.getContextPath()%>/test/ScenarioCases?scenarioExecutionId=<s:property value="scenarioExecutionId" />"><s:property value="scenario.scenarioName" /></a></td>
                        <td><s:property value="scheduleString" /></td>
                        <td><font style="font-size: 0.8em"><s:property value="scenario.description" /></font></td>
                    </tr>
</s:iterator>

                </table>
            </div>

        </div>
    </s:form>