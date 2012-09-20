<%--
    Document   : ChangePassword
    Created on : May 13, 2010, 10:25:39 AM
    Author     : SreeHari.Devineni
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

  <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text">Change Password</span></td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>
            <s:form action="ChangePassword" theme="simple">
                 <s:hidden key="userOption" />
                <table>
                    <s:if test="#session.userProfile.primaryRole eq 1 || #session.userProfile.primaryRole eq 2" >
                         <tr>
                            <td><s:label cssClass="short" key="username" /></td>
                            <td><s:select cssClass="inputSelect" name="username" list="allUsers" listKey="username" listValue="username" value="username"/></td>
                         </tr>
                    </s:if>
                     <s:else>
                         <input type="hidden" name="username" value="<s:property value="username" />"/>
                     </s:else>
                     <tr>
                        <td><s:label cssClass="short" key="currentpassword"/></td>
                        <td><s:password cssClass="inputPassword" key="currentpassword" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="newpassword"/></td>
                        <td><s:password cssClass="inputPassword" key="newpassword" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="confirmpassword"/></td>
                        <td><s:password cssClass="inputPassword" key="confirmpassword" /></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: right;">
                             <s:submit cssClass="inputButton" name="ChangePassword" id="ChangePassword" value="Change Password"
                                   title="Update password" onclick="updatePassword();"/>  
                         </td>
                    </tr>
                </table>
            </s:form>
      </div>
  </div>
            <script type="text/javascript">

          function updatePassword() {

           var curPass=new String(document.getElementById("ChangePassword").currentpassword.value);
           var newPass=new String(document.getElementById("ChangePassword").newpassword.value);
           var confnewPass=new String(document.getElementById("ChangePassword").confirmpassword.value);


            if((document.getElementById("ChangePassword").currentpassword.value== '') || (document.getElementById("ChangePassword").newpassword.value== '')|| (document.getElementById("ChangePassword").confirmpassword.value == '')) {
                alert("Please fill in all the information on this form.");
                return;
            }
            if(document.getElementById("ChangePassword").newpassword.value!=document.getElementById("ChangePassword").confirmpassword.value ) {
                alert("New Password does not match Confirmed password.");
                return;
            }

            if((document.getElementById("ChangePassword").currentpassword.value==document.getElementById("ChangePassword").newpassword.value)) {
                alert("New password cannot be same as your existing password.");
                return;
            }
              document.getElementById("ChangePassword").userOption.value="submit";
              document.getElementById("ChangePassword").submit();

        }
    </script>