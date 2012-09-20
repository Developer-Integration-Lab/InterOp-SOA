<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<script type="text/javascript">

    function open_popup_window(url) {
        window.open(url,'helpwin','scrollbars=yes,resizable=yes,left=200,top=100,width=1200,height=700');
    }
    function popup_window(url) {
        window.open(url,'newswin','scrollbars=yes,resizable=yes,left=200,top=100,width=800,height=500');
    }

</script>
<div id="left-menu">

    <s:if test="#session.userProfile.primaryRole eq 1">
        <div class="menu">
            <div class="menu-title">Dashboard</div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/ManageUsers">Manage Users</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/ManageServiceSets">Manage Service Sets</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/ManageScenarios">Manage Scenarios</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/ManageTestCases">Manage Test Cases</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/ApplicationProperties">Manage Application Properties</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/RegisterNhinRep">Register NHIN User</a></div>

            <%--<div class="menu-item"><a href="<%=request.getContextPath()%>/admin/LogSearch">Audit Log Search</a></div>            
            <div><a href="#">Audit Log Search</a></div>--%>

            <!--<div class="menu-item"><a href="<%=request.getContextPath()%>/help/Coordinator">Coordinator</a></div>-->
             <div class="menu-item"><a href="#" title="This feature is temporarily unavailable"><font color="black">Coordinator</font></a></div>

            <%-- <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/MessageSearch">Message Search</a></div>
            <div><a href="#">Message Search</a></div>--%>


        </div>
    </s:if>
    <s:elseif test="#session.userProfile.primaryRole eq 2">
        <div class="menu">
            <div class="menu-title">Dashboard</div>
            <div class="menu-disabled"><a href="#">Manage Users</a></div>
            <div class="menu-disabled"><a href="#">My Information</a></div>
        </div>
    </s:elseif>
    <s:elseif test="#session.userProfile.primaryRole eq 3">
        <div class="menu">
            <div class="menu-title"><a href="<%=request.getContextPath()%>/nhinrep/NhinrepDashboard">Dashboard</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/nhinrep/AssignParticipants">Assign Participants</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/nhinrep/RegisterParticipant">Register Participant</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/history/TestHistory">Test History</a></div>

            <%--<div class="menu-item"><a href="<%=request.getContextPath()%>/nhinrep/LogSearch">Audit Log Search</a></div>
            <div><a href="#">Audit Log Search</a></div>--%>


            <div class="menu-item"><a href="<%=request.getContextPath()%>/nhinrep/MyInfo">My Information</a></div>            

            <%--<div class="menu-item"><a href="<%=request.getContextPath()%>/nhinrep/MessageSearch">Message Search</a></div>
            <div><a href="#">Message Search</a></div>--%>           
        </div>
    </s:elseif>
    <s:elseif test="#session.userProfile.primaryRole eq 4">
        <div class="menu">
            <div class="menu-title"><a href="<%=request.getContextPath()%>/nhinvalid/NhinvalidDashboard">Dashboard</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/nhinvalid/AssignSubmission">Assign Submissions</a></div>
            <%-- <div class="menu-disabled"><a href="#">Submission History</a></div> --%>

            <%--<div class="menu-item"><a href="<%=request.getContextPath()%>/nhinvalid/LogSearch">Audit Log Search</a></div>
            <div><a href="#">Audit Log Search</a></div>--%>

            <div class="menu-item"><a href="<%=request.getContextPath()%>/nhinvalid/MyInfo">My Information</a></div>

            <%--<div class="menu-item"><a href="<%=request.getContextPath()%>/nhinvalid/MessageSearch">Message Search</a></div>
            <div><a href="#">Message Search</a></div>--%>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/gatewayagent/TransactionList">Gateway Transactions</a></div>

        </div>
    </s:elseif>
    <s:elseif test="#session.userProfile.primaryRole eq 5">
        <div class="menu">
            <div class="menu-title"><a href="<%=request.getContextPath()%>/participant/ParticipantDashboard">Dashboard</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/participant/ConnectionStatusChecks">Connection Status Checks</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/participant/SetUpTest">Set Up Test</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/participant/ActiveTestResults">Active Test Results</a></div>
            <div class="menu-item"><a href="<%=request.getContextPath()%>/history/TestHistory">Test History</a></div>

            <%--<div class="menu-item"><a href="<%=request.getContextPath()%>/participant/LogSearch">Audit Log Search</a></div>
            <div><a href="#">Audit Log Search</a></div>--%>

            <div class="menu-item"><a href="<%=request.getContextPath()%>/participant/MyInfo">My Information</a></div>           

            <%--<div class="menu-item"><a href="<%=request.getContextPath()%>/participant/MessageSearch">Message Search</a></div>
            <div><a href="#">Message Search</a></div>--%>

            <div class="menu-item"><a href="<%=request.getContextPath()%>/gatewayagent/TransactionList">Gateway Transactions</a></div>                      
        </div>
    </s:elseif>

    <div class="menu">
        <div class="menu-title">Help</div>
        <div class="menu-item"><a href="<%=request.getContextPath()%>/help/Guidance">Guidance</a></div>
        <div class="menu-item"><a href="javaScript:open_popup_window('<%=request.getContextPath()%>/help/helpIndex.html')">Lab Analyzer</a></div>
        <!--div class="menu-item"><a href="/lab/help/helpIndex.html" target="_blank">Lab Analyzer</a></div-->        
    </div>
  <%--  <div class="menu">
        <div class="menu-title">News</div>
        <div id ="news" class="menu-item"><a href="javaScript:popup_window('<%=request.getContextPath()%>/help/news.html')">
                <s:if test="true">
                    Latest News
                </s:if><s:else>Latest News</s:else></a></div>
        <!--div class="menu-item"><a href="/Lab/help/helpIndex.html" target="_blank">Lab Analyzer</a></div-->
    </div>
    <s:if test="#session.userProfile.primaryRole eq 1">
        <div class="menu-item"><a href="<%=request.getContextPath()%>/admin/ManageNewsUpdates">Manage News Updates</a></div>
    </s:if>--%>
    <div class="menu">
        <div class="menu-item"><a href="<%=request.getContextPath()%>/security/ChangePassword">Change Password</a></div>
    </div>
    <div class="menu">
        <div class="menu-item"><a href="<%=request.getContextPath()%>/security/Logoff">Sign Out</a></div>
    </div>
</div>