<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

    <div id="login-outer-box">
        <div id="login-box">
            <h1>Login</h1>
            <div>&nbsp;</div>
            <s:form action="Login" theme="simple">
                <table>
                    <tr>
                        <td><s:label key="username"/></td>
                        <td><s:textfield cssClass="inputText" key="username" /></td>
                    </tr>
                    <tr>
                        <td><s:label key="password"/></td>
                        <td>
                            <s:password cssClass="inputText" key="password" />
                            <s:submit cssClass="inputButton" value="Sign In"/>
                        </td>
                    </tr>
                </table>
            </s:form>
            <!-- action error message(s) -->
            <div class="error-title-text">
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <s:actionmessage/>
            </div>
        </div>
    </div>