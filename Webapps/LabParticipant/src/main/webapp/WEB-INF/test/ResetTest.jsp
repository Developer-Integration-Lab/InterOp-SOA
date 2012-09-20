<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <s:form action="ResetTest" theme="simple">
        <s:hidden key="executeResetInd" />
        <div id="content">
            <div class="content-title">
                <table>
                    <tr>
                        <td><span class="content-title-text">Reset Test</span></td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="reset" id="reset" value="Execute Reset"
                                   onclick="executeReset();" />
                        </td>
                    </tr>
                </table>
            </div>

            <div class="content-area">
                <div class="section-title">
                    <strong>Warning</strong>
                </div>
                <p class="status-warning"><strong>
                    Selecting the `Execute Reset` button above will remove ALL recorded test scenario and case execution results!
                    The Lab and Participant Demo RI database environments will be returned to their pre-demo states!
                </strong></p>
            </div>

        </div>
    </s:form>
    <script type="text/javascript">
        function executeReset() {
            document.getElementById("ResetTest").executeResetInd.value = "reset";
            document.getElementById("ResetTest").submit();
        }
    </script>