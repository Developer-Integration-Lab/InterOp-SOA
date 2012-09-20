<%-- 
    Document   : AppProperties
    Created on : May 6, 2010, 11:44:27 AM
    Author     : SreeHari.Devineni
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:form action="ApplicationProperties" theme="simple">
    <s:hidden key="changedPropertyInfo" />
    <s:hidden key="initialPropertylist" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"> Admin - Application Properties</span></td>
                    <td class="title-button">
                  <!--  <input type="submit" class="inputButton" name="buttonName" id="buttonName" value="Save Changes" />-->
                     <input type="button" class="inputButton" name="save" id="save" value="Update"
                                   title="Update Properties recently changed."
                                   onclick="updateProperties();"/>
                    <!-- <input type="button" class="inputButton" name="refresh" id="refresh" value="Refresh" title="Refresh the properties list"                                    onclick="refreshAppPropertiesList();"/>-->

                   <!-- <input type="submit" class="inputButton" name="buttonName"  value="AddNew"/>-->
                    </td>
                </tr>
            </table>

        </div>
         
            <div class="content-area">
               <div class="section-title">
                    Apllication Properties Key & Value
                </div>
                <table class="dashboard-table" id="participant-dashboard">
                    <tr >
                        <td><s:actionerror/></td>
                        <td><s:actionmessage/></td>
                    </tr>
                    <tr class="search-results">
                        <th nowrap>Property Key </th>
                        <th nowrap>Property Value</th>
                        <th nowrap>Property Description</th>
                    </tr>
                    <s:iterator value="applicationpropertiesMapList" status="propStatus">
                           <tr class="<s:if test="#propStatus.odd == true ">odd-row</s:if><s:else>even-row</s:else>">


                             <td class="value"> <s:property value="propertykey" /> </td>
                             <td class="value">
                                        <s:textfield cssClass="inputText" name="propertyvalue" readonly="false" key="propertyvalue" size="30" maxlength="225" onchange="javascript:dataModified();"/>
                                    </td>
                             <td class="value"> <s:property value="description" /> </td>

                         
                          
                    
                        </tr>
                    </s:iterator>
                </table>
            </div>
   

    </div>
</s:form>


<script type="text/javascript">


    function updateProperties(){
        document.getElementById("ApplicationProperties").changedPropertyInfo.value="update";
        //document.getElementById("ApplicationProperties").initialPropertylist.value=document.getElementById("ApplicationProperties").applicationpropertiesMapList;
        //alert("ress::"+create_modified_appprop_key_long_list());
        document.getElementById("ApplicationProperties").submit();
        //verifychanges();


    }
        // *********************************************************************
        // Get the latest application properties list after submitting the form.
        // *********************************************************************
   function refreshAppPropertiesList() {
            document.getElementById("ApplicationProperties").changedPropertyInfo.value = "refresh";
            document.getElementById("ApplicationProperties").submit();
        }

        var modified_appprop_key_long_list = "";
        var msg_for_display = "";

        // create a list
    function create_modified_appprop_key_long_list() {
            var c_value = "\n";
             alert("value::"+document.applicationpropertiesMapList.propertyvalue.length());
            for (var i=0; i < document.applicationpropertiesMapList.length; i++) {
                alert("value::"+applicationpropertiesMapList.value(i));
              // if (document.AssignParticipants.assignedInd[i].checked) {
                  c_value = c_value + "\n"; //document.AssignParticipants.assignedInd[i].value + "\n";
               //}
            }
            return c_value;
        }
</script>