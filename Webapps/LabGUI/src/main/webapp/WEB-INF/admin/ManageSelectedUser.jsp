<%-- 
    Document   : Manage Users
    Created on : May 11, 2010, 11:44:27 AM
    Author     : SreeHari.Devineni
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:form action="ManageSelectedUser" theme="simple">
    <s:hidden key="userOption" />
    <s:hidden key="userStatus" />
    <s:hidden key="userupdatedComments" />

    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"> Admin - Manage Selected User</span></td>
                    <td class="title-button"></td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                Users List
            </div>
            <table class="dashboard-table" id="participant-dashboard">
                <tr >
                    <td><s:actionerror/></td>
                    <td><s:actionmessage/></td>
                </tr>
            </table>
            <table class="dashboard-table" id="participant-dashboard">
                <tr class="search-results">
                    <th nowrap>UserName </th>
                    <th nowrap>User Status</th>
                    <th nowrap>Invalid attempts</th>
                    <th nowrap>Comments</th>
                    <th nowrap>Security Reminder</th>
                    <th nowrap>&nbsp;</th> <th nowrap>&nbsp;</th>
                </tr>
                <s:iterator value="userMapList" status="propStatus">
                    <tr class="<s:if test="#propStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">
                        <td class="value"><s:property value="username" />  </a>
                            <input type="hidden" name="upduserName" value="<s:property value="username" />"/>
                        </td>
                        <td class="value">
                            <s:select name="status" list="#{'A':'Active','L':'Locked','T':'Terminated'}"  value="status" onchange="statuschanged();" theme="simple" />
                        </td>
                        <td class="value"> <s:property value="invalidAttempts" /> </td>
                        <td class="value"> <input type="textarea" name="comments" value="<s:property value="comments" />" disabled="true"> </td>
                        <td class="value"> <s:property value="securityReminder" /> </td>
                        <td class="value"><input type="button" name="update" value="Update" onclick="updateUser();"> </td>
                        <td class="value"> <input type="button" name="resetpass" value="Reset Password" onclick="resetPass();"> </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </div>
</s:form>
<script type="text/javascript">
    function resetPass(){
        if (confirm('This action will reset user password with default password , You might have to communicate this to the User , Please confirm this action.')) {
            document.getElementById("ManageSelectedUser").userOption.value="resetpass";
            //document.getElementById("ApplicationProperties").initialPropertylist.value=document.getElementById("ApplicationProperties").applicationpropertiesMapList;
            //alert("ress::"+create_modified_appprop_key_long_list());
            document.getElementById("ManageSelectedUser").submit();
            //verifychanges();
        }

    }

    function statuschanged(){

        document.getElementById("ManageSelectedUser").userOption.value="update";
        //alert("status changed"+document.getElementById("ManageSelectedUser").status.value);
        document.getElementById("ManageSelectedUser").userStatus.value=document.getElementById("ManageSelectedUser").status.value;
        if(document.getElementById("ManageSelectedUser").status.value=="T"){
            document.getElementById("ManageSelectedUser").comments.disabled=false;
        }
        else{
            document.getElementById("ManageSelectedUser").comments.disabled=true;
        }
        document.getElementById("ManageSelectedUser").update.disabled=false;

    }
    function updateUser(){

        document.getElementById("ManageSelectedUser").userOption.value="update";
        // alert("status changed"+document.getElementById("ManageSelectedUser").status.value);
        document.getElementById("ManageSelectedUser").userStatus.value=document.getElementById("ManageSelectedUser").status.value;
        // document.getElementById("ManageSelectedUser").updusername.value=document.getElementById("ManageSelectedUser").username.value;
        // alert("username"+document.getElementById("ManageSelectedUser").updusername.value);
        if(document.getElementById("ManageSelectedUser").status.value=="T"){
            var updatedcomments="";
            updatedcomments=document.getElementById("ManageSelectedUser").comments.value;
            alert("updated comments"+ updatedcomments);
            document.getElementById("ManageSelectedUser").userupdatedComments.value=updatedcomments;
        }
        //alert("status changed"+document.getElementById("ManageSelectedUser").userStatus.value);
        if (confirm('This action will change the user accessability to the NHIN System, You might have to communicate this to the User , Please confirm this action.')) {
            document.getElementById("ManageSelectedUser").submit();
        }
    }

</script>