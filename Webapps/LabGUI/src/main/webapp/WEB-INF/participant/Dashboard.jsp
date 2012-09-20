<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%--
<%
    String text = "A new version of Lab will be released on 03/23/2011.";
%>
--%>
<s:form action="ParticipantDashboard" theme="simple">
    <%--<s:hidden key="buttonName" />--%>
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Dashboard</span></td>
                    <td class="title-button">
                        <input type="submit" class="inputButton" name="buttonName" id="submission" value="Submit for Validation"
                               onclick="return (chkServiceSetIndicator() && confirm('Please confirm if you would like to Submit the active Service Set for Validation.'))"/>
                        <%--<input type="button" class="inputButton" value="Submit for Validation"--%>
                               <%--onclick="submitServiceSetForValidation();"/>--%>
                        <input type="submit" class="inputButton" name="buttonName" id="refresh" value="Refresh"/>
                        <%--<input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh"--%>
                               <%--onclick="reloadCurrentPage();" />--%>
                    </td>
                </tr>
            </table>
        </div>
        <div class="content-area">
           <%-- <s:if test="false">
                <MARQUEE DIRECTION=LEFT><font size="2">News Flash : <%=text%></font></MARQUEE>
            </s:if>--%>
            <div class="section-title">
                Connection Information
            </div>
            <table>
                <tr>
                    <td width="50%">
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
                    </td>
                   <%-- <td>
                        <s:if test="false">
                        <table>
                            <tr>
                                <td>
                                    <fieldset>
                                        <legend>News Flash</legend>
                                        <p><font size="2"><%=text%></font></p>
                                    </fieldset>
                                </td>
                            </tr>
                        </table>
                        </s:if>
                    </td>--%>
                </tr>
            </table>
        </div>
        

        <div class="content-area">
            <div class="section-title">
                Current Service Set Selections
            </div>
            <!-- action error message(s) -->
            <div>
                <s:actionerror/>
            </div>
            <s:if test="activeServiceSets.size() > 0">
            <table class="search-results">
                <tr class="search-results">
                    <th>Select</th>
                    <th>Name</th>
                    <th>Progress</th>
                    <th>Results</th>
                    <th>Initiated</th>
                    <th>Status</th>
                </tr>
                <s:iterator value="activeServiceSets" status="setStatus">
                    <tr class="<s:if test="#setStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td><s:checkbox cssClass="inputCheckbox" name="serviceSetIndicator" value="%{serviceSetIndicator[#setStatus.index]}" /></td>
                        <td align="left">
                            <a href="<%=request.getContextPath()%>/participant/ActiveTestResults">
                                <s:property value="serviceset.setName" />
                            </a>
                        </td>
                        <td width="10%"><s:property escape="false" value="resultsMeterHtml" /></td>
                        <td align="left"><s:property value="resultsString" /></td>
                        <td align="left"><s:property value="initiatedString" /></td>
                        <td align="left"><s:property value="completedString" /></td>
                    </tr>
                </s:iterator>
            </table>
            </s:if>
            <s:else>
                <s:if test="actionMessages == null || actionMessages.size == 0">
                  No Active Service Sets Selected.
                </s:if>
            </s:else>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>

        </div>

    </div>
</s:form>
<script type="text/javascript">

    function chkServiceSetIndicator() {
        var opts=document.getElementsByName('serviceSetIndicator');
        if (opts==null||opts=='') return false;
        for (var cx=0; cx< opts.length; cx++) {
            if (opts[cx].checked) return true;
        }
        alert('Please select at least one Service Set to be submitted for validation!');
        return false;
    }


    /*  function  submitForValidation(){
        alert(" I am in ")
        var chklength = document.getElementsByName("serviceSetInd").length;
        var toBeSubmitted= false;
        if(chklength != undefined || chklength !=0 ){
            for(cnt=0; cnt<chklength; cnt++){
                if(objForm.serviceSetInd[cnt].checked){
                   toBeSubmitted = true;
                    alert(" I am in checked")
                }
            }
        }
        if(toBeSubmitted );
        return toBeSubmitted;
       return false;
      }*/

<%--    function submitServiceSetForValidation() {
        //if (confirm('This action will submit the currently active service set. Please confirm this action.')) {
            document.getElementById("ParticipantDashboard").buttonName.value = "Submit for Validation";
            document.getElementById("messageAfterSubmittingActiveServiceSet").innerHTML = 'Service Set successfully submitted for Validation review.';
            document.getElementById("ParticipantDashboard").submit();
        //}
    }

    function reloadCurrentPage() {
        history.go(0);
        return;
    }--%>


</script>