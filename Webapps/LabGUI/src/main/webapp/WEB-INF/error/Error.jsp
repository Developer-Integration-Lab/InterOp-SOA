<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <s:form action="Error" theme="simple">
        <div id="content">
            <div class="error-title">
                <span class="error-title-text">An Error Has Occurred</span>
            </div>

            <div class="content-area">
                <p><strong>An error has occurred in the application.  Please do not panic.</strong></p>

                <p>Please contact your on boarding lead for any support. If you would like to see the technical details of this error, please expand the "Technical Details" section below.  Thank you!</p>
            </div>

            <%--<div class="collapsible" id="collapsible-areas">--%>
                <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-1'); return false;">
                    Error Information
                </div>
                <div class="collapsible-area" id="collapsible-panel-1">
                    <span class="collapsible-content">
                        <!-- action message(s) -->
                        <s:actionmessage theme="simple"/>
                    </span>
                </div>

                <div class="collapsible-title expanded" onclick="panelClick(this, 'collapsible-panel-2'); return false;">
                    Technical Details
                </div>
                <div class="collapsible-area" id="collapsible-panel-2">
                    <span class="collapsible-content">
                        <!-- action error message(s) -->
                        <s:actionerror theme="errorStackTrace"/>
                    </span>
                </div>
            <%--</div>--%>
        </div>
    </s:form>