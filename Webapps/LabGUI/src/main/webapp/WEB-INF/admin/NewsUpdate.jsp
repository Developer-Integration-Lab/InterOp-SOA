<%-- 
    Document   : NewsUpdate
    Created on : Feb 15, 2011, 2:42:40 PM
    Author     : Naresh.Jaganathan
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/datetimepicker_css.js"></script>
<s:form action="ManageNewsUpdates" theme="simple">
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.nhinrep.name" /> Admin - Manage News Updates </span></td>
                    <td class="title-button">
                        <input type="button" class="inputButton" name="clear" id="clear" value="Clear" onclick=""/>
                        <input type="button" class="inputButton" name="save" id="save" value="Save" onclick=""/>
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
                News Display Criteria
            </div>
            <table>
                <tr>
                    <td nowrap width="20%"><s:label cssClass="short"  value= "*Begin Date :"/> </td>
                    <td width="80%">
                        <s:textfield cssClass="inputText" value="%{startDatetime}" id="startDatetime" size="30" maxlength="30" title="Format: yyyy-MM-dd HH:mm:ss" />
                        <a href="javascript: NewCssCal('startDatetime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
                    </td>
                </tr>
                <tr>
                    <td nowrap width="20%"><s:label cssClass="short" value ="*End Date :"/> </td>
                    <td width="80%">
                        <s:textfield cssClass="inputText" value="%{endDatetime}" id="endDatetime" size="30" maxlength="30" title="Format: yyyy-MM-dd HH:mm:ss" />
                        <a href="javascript: NewCssCal('endDatetime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
                    </td>
                </tr>
            </table>
        </div>

        <div class="content-area">
            <div class="section-title">
                News Update Information
            </div>
            <table>
                <tr>
                  <td colspan="2"><font style="font-style:italic; font-size:0.8em;">Please enter a short description of the news update to be displayed as a scrolling message in the Dashboard (maximum 255 characters).</font></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" value="*News Description :"/></td>
                    <td><s:textfield cssClass="inputText" key="name" size="60" maxlength="255" /></td>
                </tr>

            </table>
            <hr />
            <table>
                <tr>
                  <td><s:label cssClass="short" value= "*News Content :  "/></td>
                  <td colspan="2"><font style="font-style:italic; font-size:0.8em;">Use either of the two methods below, <b>File Upload</b> or <b>Text Entry</b>, to provide the news content.</font></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" value= "File Upload :"/></td>
                    <td><s:file cssClass="short" name="upload" size="60"/></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short" value="Text Entry :"/></td>
                    <td><s:textarea cssClass="inputText" cols="60" rows="6" /></td>
                </tr>
            </table>
        </div>
    </div>
</s:form>
