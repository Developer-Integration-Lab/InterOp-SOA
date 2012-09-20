<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    response.addHeader("Pragma","no-cache");
    response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
    response.addHeader("Cache-Control","pre-check=0,post-check=0");
    response.setDateHeader("Expires",0);
%>

<s:form action="MyInfo" theme="simple">
    <s:hidden key="saveAction" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - My Information</span></td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="save" id="save" value="Save"
                               title="Save this Nhin Validator's Contact Information"
                               onclick="saveNhinValidatorInfo();"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="content-area">
            <table border="0">
                <tr>
                    <td><i><s:label cssClass="long" key="required.fields"/></i></td>
                </tr>
            </table>
            <div class="section-title">
                Account Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short" key="nhinvalid.myinfo.name"/></td>
                    <td><s:textfield cssClass="inputText" value="%{nhinrep.name}" name="name" size="60" maxlength="255" /></td>
                </tr>
            </table>
            <hr />
            <table>
                <tr>
                    <td><s:label cssClass="short" key="nhinvalid.myinfo.contactName"/></td>
                    <td><s:textfield cssClass="inputText" value="%{nhinrep.contactName}" name="contactName" size="60" maxlength="255" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="nhinvalid.myinfo.contactPhone"/></td>
                    <td><s:textfield cssClass="inputText" value="%{nhinrep.contactPhone}" name="contactPhone" size="60" maxlength="45" /></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" key="nhinvalid.myinfo.contactEmail"/></td>
                    <td><s:textfield cssClass="inputText" value="%{nhinrep.contactEmail}" name="contactEmail" size="60" maxlength="225" /></td>
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

        // *********************************************************************
        // Validate the form before saving the information of the logged in
        // nhin validator.
        // *********************************************************************
        function validateForm(form_id) {
            var isFormInfoValid = true;

            var emailAddress = form_id.contactEmail.value;
            var uName = new String(form_id.name.value);
            var contName = new String(form_id.contactName.value);
            var contPh = new String(form_id.contactPhone.value);
            var contEmail = new String(form_id.contactEmail.value);

            if((uName.trim() == '') || (contName.trim() == '') ||
               (contPh.trim() == '') || (contEmail.trim() == '')) {
                alert("Please fill in all the required(*) information on this form.");
                // do not submit the form.  server-side validation is in place,
                // however have client-side validation too.
                isFormInfoValid = false;
                return isFormInfoValid;     // with return here, next alert message is not displayed.
            }

            var regEmail = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            if(regEmail.test(emailAddress) == false) {
                alert('Please enter valid email address.  For ex - john@domain.com');
                isFormInfoValid = false;
                return isFormInfoValid;
            }
            return true;
        }

        // *********************************************************************
        // Get confirmation and submit the form for saving the information
        // of the logged in nhin validator.
        // *********************************************************************
        function saveNhinValidatorInfo() {
            if (false == validateForm(document.getElementById("MyInfo"))) {
                return false;
            }

            if (confirm('This action will update the contact information for this NHIN Validator. Ensure the information is correct before proceeding.')) {
                document.getElementById("MyInfo").saveAction.value = "Save";
                document.getElementById("MyInfo").submit();
            }
        }
</script>
