<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants" %>
<%@ page import="net.aegis.lab.dao.pojo.Participant" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>

<%
    response.addHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
    response.addHeader("Cache-Control","pre-check=0,post-check=0");
    response.setDateHeader("Expires",0);

    int iNhinRepId = -999;
    Participant objParticipantThatNhinRepWorksWith = null;
    String strParticipantThatNhinRepWorksWith = "";

    iNhinRepId = ((User)session.getAttribute("userProfile")).getNhinrep().getNhinRepId();
    objParticipantThatNhinRepWorksWith = ((User)session.getAttribute("userProfile")).getNhinrep().getWorkingParticipant();
    if(null == objParticipantThatNhinRepWorksWith) { strParticipantThatNhinRepWorksWith = "none"; }
    else { strParticipantThatNhinRepWorksWith = objParticipantThatNhinRepWorksWith.getParticipantName(); }
%>
    <s:form action="AssignParticipants" theme="simple">
        <s:hidden key="assignAction" />
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - Assign Participants</span></td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="save" id="save" value="Assign"
                                   title="Assign all selected participant(s) with the logged Nhin Rep"
                                   onclick="assignParticipants(<%=iNhinRepId%>);"/>
                            <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"
                                   title="Refresh the list of participant(s)"
                                   onclick="refreshListOfParticipants();"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><span style="font-size: small">Working with - <%=strParticipantThatNhinRepWorksWith%></span></td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    Currently Active Participants
                </div>
                <table class="search-results">
                    <tr class="search-results">
                        <th nowrap>&nbsp;</th>
                        <th nowrap>Name</th>
                        <th nowrap>Contact</th>
                        <th nowrap>Assigned To</th>
                        <%--<th>Associate This With Me</th>--%>
                    </tr>
<s:iterator value="nhinrep.participants" status="canStatus">
                    <tr class="<s:if test="#canStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td valign="top" align="left" width="2%">

                            <%--<s:if test="nhinrep.name == #session.userProfile.nhinrep.name">--%>
                            <s:if test="nhinRepId == #session.userProfile.nhinrep.nhinRepId">
                                <s:checkbox cssClass="inputCheckbox"
                                            name="assignedInd"
                                            fieldValue="~%{participantId}_%{participantName}"
                                            disabled="false"
                                            checked="checked" />&nbsp;
                            </s:if>
                            <s:else>
                                <s:if test="nhinRepName == \"\"">   <!-- participant is assignable -->
                                    <s:checkbox cssClass="inputCheckbox"
                                                name="assignedInd"
                                                fieldValue="~%{participantId}_%{participantName}"
                                                disabled="false" />&nbsp;
                                </s:if>
                                <s:else>    <!-- participant is NOT assignable unless the Nhin Rep releases him/her first :) -->
                                    <s:checkbox cssClass="inputCheckbox"
                                                name="assignedInd"
                                                fieldValue="~%{participantId}_%{participantName}"
                                                disabled="true" />&nbsp;
                                </s:else>
                                
                            </s:else>

                        </td>
                        <td valign="top" align="left" width="20%"><s:property value="participantName" /></td>
                        <td valign="top" align="left" width="30%"><s:property value="contactName" /></td>
                        <td valign="top" align="left" width="30%">
                            <s:if test="nhinRepId == #session.userProfile.nhinrep.nhinRepId">
                                <font color="green">
                                    <s:property value="nhinRepName"/>
                                </font>
                            </s:if>
                            <s:else>
                                <s:property value="nhinRepName"/>
                            </s:else>
                            
                        </td>
                    </tr>
</s:iterator>
                </table>
            </div>

        </div>
    </s:form>
    <script type="text/javascript">
        var checked_participants_long_list = "";
        var checked_participants_names_for_alert_display_only = "";   // not used yet
        var msg_for_display = "";

        // create a list of participants chosen by the logged in nhin rep for assigning to itself.
        function create_checked_participants_long_list() {
            var c_value = "\n";
            for (var i=0; i < document.AssignParticipants.assignedInd.length; i++) {
               if (document.AssignParticipants.assignedInd[i].checked) {
                  c_value = c_value + document.AssignParticipants.assignedInd[i].value + "\n";
               }
            }
            return c_value;
        }

        // name of this function is self-explanatory.
        function create_checked_participants_names_for_alert_display_only() {
            var c_fordisplay = "";
            var c_temp1 = "";
            var checked_participants_parsed_arr = "";
            
            checked_participants_parsed_arr = checked_participants_long_list.split('_');
            for(var i=0; i<checked_participants_parsed_arr.length; i++) {
                c_temp1 += checked_participants_parsed_arr[i];
            }
            
            c_temp1arr = c_temp1.split('\n');           // for ex - c_temp1arr=,~1AEGIS.net,Inc.,~7Jyoti,~10NewParticipant
            for(var i=0; i<c_temp1arr.length; i++) {
                c_fordisplay += c_temp1arr[i].substring(2)+"\n";
            }
            return c_fordisplay;
        }

        // *********************************************************************
        // Get confirmation and submit the form for assigning the chosen
        // participants to the logged in nhin rep.
        // *********************************************************************
        function assignParticipants(pNhinRepId) {
            
            //var numSelectedParticipants = 0;

            //for (var i=0; i < document.AssignParticipants.assignedInd.length; i++) {
            //   if (document.AssignParticipants.assignedInd[i].checked) {
            //      numSelectedParticipants++;
            //   }
            //}
            //if(numSelectedParticipants <= 0) {
            //    alert("Please select participant(s) to assign to you.");
            //    return;
            //}

            checked_participants_long_list = create_checked_participants_long_list();
            checked_participants_names_for_alert_display_only = create_checked_participants_names_for_alert_display_only(); // not used yet
            msg_for_display = "Checked participants will be assigned to you. \n Unchecked participants will be deassigned. \n Proceed? ";

            if (confirm(msg_for_display)) {
                document.getElementById("AssignParticipants").action = "<%=request.getContextPath()%>/nhinrep/AssignParticipants?nhinRepId="+pNhinRepId+"&checkedParticipantsList=" + checked_participants_long_list;
                document.getElementById("AssignParticipants").assignAction.value = "assign";
                document.getElementById("AssignParticipants").submit();
            }

        }

        // *********************************************************************
        // Get the latest list of participants for this page after
        // submitting the form.
        // *********************************************************************
        function refreshListOfParticipants() {
            document.getElementById("AssignParticipants").assignAction.value = "refresh";
            document.getElementById("AssignParticipants").submit();
        }
    </script>
