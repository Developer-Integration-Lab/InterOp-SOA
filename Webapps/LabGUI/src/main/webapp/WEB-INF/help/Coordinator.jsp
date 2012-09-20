<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <s:form action="Coordinator" theme="simple">
        <s:hidden key="coordinatorAction" />
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text">Help - Coordinator</span></td>
                            <td class="title-button">
                                <input type="button" class="inputButton" name="start" id="start" value="Start"
                                       onclick="doCoordinatorAction('start');" />&nbsp;
                                <input type="button" class="inputButton" name="stop" id="stop" value="Stop"
                                       onclick="doCoordinatorAction('stop');" />&nbsp;
                                <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                                       onclick="doCoordinatorAction('refresh');" />
                            </td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    Coordinator Status
                </div>
                <p class="status-warning"><strong><s:property value="coordinatorStatus" /></strong></p>
            </div>
<%-- REMOVED - this was only for the HIMMS Conference Demo
            <div class="collapsible" id="collapsible-areas">
                <div class="collapsible-title collapsed" onclick="panelClick(this, 'collapsible-panel-1'); return false;">
                    Test Environment
                </div>
                <div class="collapsible-area" id="collapsible-panel-1">
                    <span class="collapsible-content">
                        <table>
                            <tr>
                                <td align="center">
                                    <strong>
                                        Selecting the `Execute Reset` button below will remove ALL recorded test scenario and case execution results!<br>
                                        The Lab and Participant Demo RI database environments will be returned to their pre-demo states!
                                    </strong>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <input type="button" class="inputButton" name="reset" id="reset" value="Execute Reset"
                                           onclick="doCoordinatorAction('reset');" />
                                </td>
                            </tr>
                        </table>
                    </span>
                </div>
            </div>
--%>
        </div>
    </s:form>
    <script type="text/javascript">
        function doCoordinatorAction(cAction) {
            document.getElementById("Coordinator").coordinatorAction.value = cAction;
            document.getElementById("Coordinator").submit();
        }
    </script>