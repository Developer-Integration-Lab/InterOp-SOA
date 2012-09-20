<%-- 
    Document   : ConnectionStatusChecks
    Created on : Jun 9, 2011, 4:25:09 PM
    Author     : Jyoti.Devarakonda
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%@ page import="net.aegis.lab.manager.ParticipantManager" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>


<s:form action="ConnectionStatusChecks" theme="simple">
 <div id="content">
     <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Connection Status Checks</span></td>
                </tr>
            </table>
        </div>
    <div id="maindiv" class="content-area">
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>

            <div class="section-title">
                Connection Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.communityId"/></td>
                    <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.assigningAuthorityId"/></td>
                    <td><span class="long-display"><s:property value="participant.assigningAuthorityId" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.ipAddress"/></td>
                    <td><span class="long-display"><s:property value="participant.ipAddress" /></span></td>
                </tr>
            </table>
        </div>

        <%--
         begin comment old Verify UI pre 1.3.X
         <div class="content-area">
             <div class="section-title">
                 Connection Status Checks
             </div>
             <table>
                 <tr>
                     <td><s:label cssClass="long-right" key="participant.setup.test.verify.connection.status"/></td>
                     <td><input type="submit" class="inputButton" name="buttonName" value="Verify" />
                     </td>
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
                     <td><s:label cssClass="long-right" key="participant.setup.test.verify.connection.timestamp"/></td>
                     <td colspan="2"><span class="long-left"><s:date name="participant.commVerifyTimestamp" format="d MMM, yyyy hh:mm a" /></span></td>
                 </tr>
             </table>
         </div>
       end comment old Verify UI pre 1.3.X
        --%>
        <div class="content-area">
            <div class="section-title">
                Connection Status Checks
            </div>
            <table class="content-data" border="0">
                <tr>
                    <td colspan="2"> <span class="long-display">Use the following Verify buttons to perform a basic Connection Status Check (Lab requests a WSDL from Participant) for any of the following services.</span></td>
                </tr>
                <s:iterator value="statusList" status="idx">
                    <tr class="<s:if test="#idx.odd == true">odd-row</s:if><s:else>even-row</s:else>">
                        <td colspan="2"><span  class="thSz"><s:property value="requestType"/></span></td>
                    </tr>
                    <tr class="<s:if test="#idx.odd == true">odd-row</s:if><s:else>even-row</s:else>">
                        <td width="25%">Home Community ID from "My Information": </td>
                        <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                    </tr>
                    <tr class="<s:if test="#idx.odd == true">odd-row</s:if><s:else>even-row</s:else>">
                        <td>Web service end point from the UDDI for this Home Community ID:</td>
                        <td>
                            <s:if test="wsdl !=null" >
                                <s:a cssClass="fontSmall" href="#" target="_self" onclick="javascript:window.open('?buttonName=Verify.%{requestTypeCode}.Wsdl');">
                                    <s:property value="%{wsdl}" />
                                </s:a>
                                <br/>
                                (Note: Clicking this link will display the WSDL returned by this end point.)
                            </s:if>
                            <s:else>No End Points are found for <s:property value="requestType"/> in UDDI </s:else>
                        </td>
                    </tr>
                    <tr class="<s:if test="#idx.odd == true">odd-row</s:if><s:else>even-row</s:else>">
                        <td>Test this end point</td>
                        <td>
                            <table>
                                <tr><td>
                                        <input type="button" class="inputButton" name="buttonName" value="Verify" onclick="doVerify('<s:property value="requestTypeCode"/>')"/>
                                        &nbsp;
                                    </td>
                                    <td>
                                        <span class="status-<s:property value="classCode"/>"><s:property value="statusUFStr"/></span>
                                    </td><td>
                                        Last verified on <span class="long-left"><s:property value="datetime"/></span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <s:if test="classCode.contains(\"failed\")">
                        <tr class="<s:if test="#idx.odd == true">odd-row</s:if><s:else>even-row</s:else>">
                            <td>&nbsp;</td>
                            <td>
                                <span class="fontSmall"><s:property value="errorMessage"/></span>
                                &nbsp;
                            </td>
                        </tr>
                    </s:if>
                </s:iterator>
            </table>
        </div>


        <div class="content-area">
            <div class="section-title">
                NHIN Participant service endpoints per the current UDDI
            </div>

            <table>
                <tr>
                    <td colspan="2">
                        NHIN Test Platform's UDDI Entries for connectivity self-diagnosis:<a href="#" onclick="displayEndPoints()">
                            <s:text name="Lab's UDDI"/>
                        </a>

                    </td>
                </tr>

            </table>
        </div>
</div>
</s:form>
<script type="text/javascript">

function displayEndPoints() {
            window.open("getEndPoints","myWindow", "status = 1, height = 500, width = 950, resizable = 1,scrollbars=1");
        }

function doVerify(vType) {
            if (confirm('Please confirm if you would like to Verify a '+vType+' connection.')) {
                document.getElementById("ConnectionStatusChecks").action += "?buttonName=Verify." + vType;
                document.getElementById("ConnectionStatusChecks").submit();
            }
        }


 </script>

