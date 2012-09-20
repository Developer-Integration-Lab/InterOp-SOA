<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <s:form action="AddApplicationProperties" theme="simple">
        <s:hidden key="addPropertyAction" />
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> - Add New Property </span></td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="clear" id="clear" value="Clear"
                                   title="Clear this Application Property key value Information"
                                   onclick="clearAddPropForm();"/>
                            <input type="button" class="inputButton" name="save" id="save" value="Save"
                                   title="Save this Application Property Information"
                                   onclick="saveAddPropForm();"/>
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
                    New Application Property Settings
                </div>
                <table>
                    <tr>
                        <td><s:label cssClass="short" key="user.propkey"/></td>
                        <td><s:textfield cssClass="inputText" key="key" size="60" maxlength="255" /></td>
                    </tr>
                    <tr>
                        <td><s:label cssClass="short" key="user.propvalue"/></td>
                        <td><s:password cssClass="inputText" key="value" size="60" maxlength="255" /></td>
                    </tr>
                </table>
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

        function clearAddPropForm() {
            if (confirm('This action will clear all data entry fields. Please confirm this action.')) {
                document.getElementById("AddApplicationProperties").addPropertyAction.value = "clear";
                document.getElementById("AddApplicationProperties").submit();
            }
        }
        function saveAddPropForm() {

            var uKey = new String(document.getElementById("AddApplicationProperties").key.value);
            var uValue = new String(document.getElementById("AddApplicationProperties").value.value);
            alert(uKey);
            alert(uValue);
            if((uKey.trim() == '') || (uValue.trim() == '')) {
                alert("Please fill in all the required(*) information on this form.");
                // do not submit the form.  server-side validation not implemented yet.
                return;
            }

            if (confirm('This action will sumbit the New Application property , Ensure the information is correct before proceeding.')) {
                document.getElementById("AddApplicationProperties").addPropertyAction.value = "save";
                document.getElementById("AddApplicationProperties").submit();
            }
        }
    </script>