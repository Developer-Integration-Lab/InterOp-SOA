<%-- 
    Document   : RegisterNhinRep.jsp
    Created on : May 10, 2010, 10:08:30 AM
    Author     : SreeHari.Devineni
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <s:form action="RegisterNhinRep" theme="simple">
        <s:hidden key="registerAction" />
        <s:hidden key="selectedreprole" />
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> Admin - Register NHIN Representative </span></td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="clear" id="clear" value="Clear"
                                   title="Clear this Nhin Representative's Registration Information"
                                   onclick="clearRegistration();"/>
                            <input type="button" class="inputButton" name="save" id="save" value="Save"
                                   title="Save this Nhin Representative`s Registration Information"
                                   onclick="saveRegistration();"/>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <!-- action error message(s) -->
                <div>
                    <s:actionerror/>
                </div>
                <!-- action message(s) -->
                <div>
                    <strong style="color: #00f"><s:actionmessage/></strong>
                </div>
                <table border="0">
                    <tr>
                        <td><i><s:label cssClass="long" key="required.fields"/></i></td>
                    </tr>
                </table>
                <div class="section-title">
                    Login Information
                </div>
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="user.username"/></td>
                        <td><s:textfield cssClass="inputText" key="userName" size="60" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="user.password"/></td>
                        <td><s:password cssClass="inputText" key="password" size="60" maxlength="255" /></td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    NHIN Representative Information
                </div>
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="nhinrep.name"/></td>
                        <td><s:textfield cssClass="inputText" key="name" size="60" maxlength="255" /></td>
                    </tr>
                                   
                </table>
                <hr />
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="nhinrep.contactName"/></td>
                        <td><s:textfield cssClass="inputText" key="contactName" size="60" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="nhinrep.contactPhone"/></td>
                        <td><s:textfield cssClass="inputText" key="contactPhone" size="60" maxlength="45" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="nhinrep.contactEmail"/></td>
                        <td><s:textfield cssClass="inputText" key="contactEmail" size="60" maxlength="225" /></td>
                    </tr>
                </table>
            </div>

               <div class="content-area">
                <div class="section-title">
                    NHIN Representative Attributes
                </div>
                <table>

                    <tr class="even-row">
                        <td class="test-name"><s:label cssClass="short" key="reproleind"/></td>
                        <td class="test-options"><INPUT TYPE="RADIO" id="reprole" NAME="reprole" VALUE="3">NHIN Representative</td>
                    </tr>
                    <tr>
                        <td class="test-name">&nbsp;</td>
                        <td class="test-options"><INPUT TYPE="RADIO" id="reprole" NAME="reprole" VALUE="4">NHIN Validating Body Representative </td>
                    </tr>
                </table>
            </div>

        </div>
    </s:form>
    <script type="text/javascript">
        //
        // Trim is a useful function available in languages like Java & PHP  which
        // removes the leading and traling whitespace(s) from a String. Unfortunately
        // Javascript doesn't natively provide trim functionality to the String object.
        // Fortunately there is a simple solution as below.
        //
        String.prototype.trim = function() {
                return this.replace(/^\s+|\s+$/g,"");
        };

        function clearRegistration() {
            if (confirm('This action will clear all data entry fields. Please confirm this action.')) {
                document.getElementById("RegisterNhinRep").registerAction.value = "clear";
                document.getElementById("RegisterNhinRep").submit();
            }
        }
        function saveRegistration() {

            var uName = new String(document.getElementById("RegisterNhinRep").userName.value);

            var uPwd = new String(document.getElementById("RegisterNhinRep").password.value);
            var repName = new String(document.getElementById("RegisterNhinRep").name.value);
            //var user = new String(document.getElementById("RegisterNhinRep").user.value);
            var contName = new String(document.getElementById("RegisterNhinRep").contactName.value);
            var contPh = new String(document.getElementById("RegisterNhinRep").contactPhone.value);
            var contEmail = new String(document.getElementById("RegisterNhinRep").contactEmail.value);
            //var contRole=  new String(document.getElementById("RegisterNhinRep").reprole.value);
             //alert(contRole);
            var choosen=GetSelectedItem();
            //alert("choosen:"+choosen);

             
            // alert(uName,uPwd, repName,contName,contPh, contEmail);
            if((uName.trim() == '') || (uPwd.trim() == '') || (repName.trim() == '') ||
                (contName.trim() == '') || (contPh.trim() == '') || (contEmail.trim() == '')) {
                alert("Please fill in all the required(*) information on this form.");
                // do not submit the form.  server-side validation not implemented yet.
                return;
            }

            if (confirm('This action will sumbit the registration information for this NHIN Rep. Ensure the information is correct before proceeding.')) {
                document.getElementById("RegisterNhinRep").registerAction.value = "save";
                if(choosen ==""){
                              alert("Representative Role is not selected. ");
                              return;
                }
                else{
                    
                               // alert("select rep type confirmed "+choosen);
                                document.getElementById("RegisterNhinRep").selectedreprole.value = choosen;
                         
                 
                          
                 }

                document.getElementById("RegisterNhinRep").submit();
               
            }
        }

        function GetSelectedItem() {

                        chosen = ""
                        len = document.RegisterNhinRep.reprole.length

                        for (i = 0; i <len; i++) {
                        if (document.RegisterNhinRep.reprole[i].checked) {
                        chosen = document.RegisterNhinRep.reprole[i].value
                        }
                        }

                        return chosen;
                        }
    </script>