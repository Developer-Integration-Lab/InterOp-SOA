<%-- 
    Document   : upload
    Created on : Apr 13, 2010, 1:57:50 PM
    Author     : ram.ghattu
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
    <head>
        <title>Attach Document</title>
        <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/style/master.css" type="text/css" media="screen" title="no title" charset="utf-8"/>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/prototype.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/scriptaculous.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/script/standard.js"></script>
    </head>
    <body onload="showErrorMsg();">
        <div id="content-no-menu">
            <div class="content-title">
                <table>
                    <tr>
                        <td>                        
                            <span class="content-title-text">
                                Attach Document - 
                                <s:if test="executionUniqueId == null || \"\".equals(executionUniqueId)">
                                    Scenario <s:property value="#session.scenarioId" />
                                </s:if>
                                <s:else>
                                    Service Set <s:property value="executionUniqueId" />
                                </s:else>
                            </span>
                        </td>
                        <td class="title-button">
                            <input type="button" class="inputButton" name="close" id="close" value="Close"
                                   onclick="window.close();" />
                        </td>
                    </tr>
                </table>
            </div>
            <div class="content-area">
                <s:form action="AttachDocumentExecute" method="POST" enctype="multipart/form-data" >
                    <s:hidden key="caseExecId" />
                    <s:hidden key="executionUniqueId"/>
                    <s:hidden key="setExecutionId"/>
                    <!-- ILT-246 Jyoti Devarakonda -->
                    <div id="staticMsg"> Please note: The attachment file size may not exceed 4 MB.</div>
                    <table id="attacDoc">
                        <tr>
                            <td><s:actionerror/></td>
                            <td><s:actionmessage/></td>
                        </tr>                  
                        <tr>
                            <td class="title-button">                                
                                <s:file name="upload" label="Attach File"/>
                            </td>
                            <td>
                                <s:textarea name="description" label="File Description"  value="" rows="5"  cols="60"/>
                            </td>
                            <td align="center">
                                <s:submit/>
                            </td>
                        </tr>
                    </table>
                </s:form>   
            </div>
        </div>
    </body>
</html>


<script type="text/javascript">
    //Jyoti Devarakonda ILT-246
    function showErrorMsg() {       
        var rowsInAction=document.getElementById('attacDoc').rows;
        for  (var i=0; i<rowsInAction.length; i++){
            var cellsInAction=rowsInAction[i].cells;
            var cellsLength=rowsInAction[i].cells.length;
            for  (var k=0; k<cellsLength; k++){
                var str = cellsInAction[k].innerHTML ;
                if(str.match("<span class=\"errorMessage\">")){                    
                    document.getElementById("staticMsg").style.visibility="hidden";                   
                    var newString = str.replace("Please", "<BR/>Please");
                    newString = newString.replace("size.", "size.<BR/><BR/>");
                    cellsInAction[k].innerHTML=newString;
                    cellsInAction[k].align="left";
                }
                if(str.match("<ul class=\"actionMessage\">")){
                    document.getElementById("staticMsg").style.visibility="hidden";
                }
                if(str.match("<ul class=\"errorMessage\">")){
                    document.getElementById("staticMsg").style.visibility="hidden";
                }
            }
        }        
    }
</script>