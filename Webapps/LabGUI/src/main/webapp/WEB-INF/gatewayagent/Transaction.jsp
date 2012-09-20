<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/datetimepicker_css.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/script/pagination.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-1.6.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery.pagination.js"></script>


     <style type="text/css">
         <!--
        * {
            padding: 0;
            margin: 0;
        }
        
        #Searchresult dt {
            font-weight:bold;
        }
        
        #Searchresult dd {
            margin-left:25px;
        }
		
		color:#15B;
		}
		
		label {
			float:left;
			width:250px;
			display:block;
		}
		
		form p {
			clear:both;
		}
	.shaded {background-color:#F0F0F0}
	.tbheader {background-color:#EAECF2}
	
        -->
        </style>


<s:form action="TransactionList" theme="simple">
    
    <div id="content" style="overflow:auto;display:block;">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> </span></td>
                    <td class="title-button">&nbsp;</td>
                </tr>
            </table>
        </div>

        <%--<div class="content-area">--%>
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>

            <div class="content-area" style="overflow:auto">
                <div class="section-title">
                    Search
                </div>
   		 

         
                <table style="font-size:8pt;overflow:auto;display:table;" width="100%" align="left" border="0">
                	<tr>
                    <td width="10%"><strong>Start Time</strong></td>
                    <td width="2%">&nbsp;</td>    
                    <td width="10%"><strong>Sender</strong></td>
                    <td width="10%"><strong>Receiver</strong></td>                                        
                    <td width="65%">&nbsp;</td>                    
                    </tr>
                	<tr>
                    <td valign="top">
					<table valign="top"><tr valign="top"><td valign="top">
					<s:textfield name="startTime"/></td><td><a href="javascript:NewCssCal('TransactionList_startTime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
					</td></tr></table>
					</td>
                    <td >&nbsp;</td>                        
                    <td rowspan="3" ><s:select name="senderSel" list="sender"  cssStyle="width:100px" listValue="hostedBy" listKey="HCID" multiple="true" size="5" /></td>
                    <td  rowspan="3" ><s:select name="receiverSel" list="receiver" cssStyle="width:100px" listValue="hostedBy" listKey="HCID" multiple="true" size="5" /></td>                             
                    <td >&nbsp;</td>                    
                    </tr>
                	<tr>
                    <td ><strong>End Time</strong></td>
                    <td >&nbsp;</td>    
                    <td >&nbsp;</td>                    
                    </tr>
                	<tr>
                    <td valign="top">
					<table valign="top"><tr valign="top"><td valign="top">
					<s:textfield name="endTime"/></td><td><a href="javascript:NewCssCal('TransactionList_endTime','yyyymmdd','arrow',true,'24',false)"><img src="<%=request.getContextPath()%>/images/cal.gif" width="16" height="16" alt="Pick a date"></a>
					</td></tr></table>
					</td>
					 <td >&nbsp;</td>    
                    <td ><s:submit name="cmdButton" value="Search" onclick="return doVerify()"/> &nbsp; <input type='button' onclick="window.location='<%=request.getContextPath()%>/gatewayagent/TransactionList'" value='Reset'/> 
						 </td>
                    </tr>
				</table>
		</div>

  <%--
                <div xclass="section-title1" style="display:table-row">
                    
                </div>
--%>

 <div class="content-area" style="overflow:auto">
                <div class="section-title">
                  Gateway Transaction List
                </div>
			    
			    
			    <s:if test="transactionList == null or (transactionList !=null and transactionList.size() eq 0)">
			    	<span style="font-size:14px"><s:property value="userMessage"/></span>
			    </s:if>
			    
		<%--	               
                <table style="display:none;font-size:8pt" width="100%" align="left" border="0">
                 <s:iterator value="transactionList" status="idx">
                     <s:if test="#idx.index eq 0">
                        <tr>
                            <!--
                    <th>Id</th>
                            -->
                    <th align="left" width="12%">Time</th>
                      <th align="left" width="7%">Type</th>
                    <th align="left" width="10%">Sender</th>
                    <th align="left" width="10%">Receiver</th>                    
                    <!--
                    <th>Rule</th>
                    <th>Method</th>
                    -->
                    <th align="left">WS Endpoint</th>


                    <th align="left" >Message</th>
                    </tr>
                    </s:if>                    
                    <tr>
                        <!--
                    <td><s:property value="id"/></td>
                        -->                    
                    <td><s:date name="time" format="yyyy-MM-dd HH:mm:ss" /></td>
                    <td><span title="HTTP Status: <s:property value="statusCode"/>"><s:property value="messageType"/>&nbsp;</span></td>
                    <td><span title="HCID: <s:property value="sender.HCID"/>"><s:property value="sender.hostedBy"/>&nbsp;</span></td>
                    <td><span title="HCID: <s:property value="receiver.HCID"/>"><s:property value="receiver.hostedBy"/>&nbsp;</span></td>
                    <!--
                    <td><s:property value="rule"/></td>
                    <td><s:property value="method"/></td>
                    -->
                    <td><s:property value="path"/></td>

    
       
                    <td>
                               <s:url id="url" action="DownloadMessage">
                                    <s:param name="transactionId" value="id" />
                                    <s:param name="filename" value="time" />
                                    <s:param name="contentType" value="contentType" />
                                </s:url>
                        <s:a href="%{url}">Download</s:a>
                    </td>
                    </tr>				
                </s:iterator>                        
                <s:if test="transactionList !=null and transactionList.size() gt 0">
                <tr>
                <td colspan="6"><span style="font-size:14px"><s:property value="userMessage"/></span>
                </td>
                </tr>
                </s:if>                
                </table>
                --%>
                
<!--  pagination table -->
 <script type="text/javascript">
var transactionArray = [
    /* ['start time', 'type', 'sender', 'receiver', 'endpoint', 'message'] */
                 <s:iterator value="transactionList" status="idx">
					 <s:url id="url" action="DownloadMessage">
					    <s:param name="transactionId" value="id" />
					    <s:param name="filename" value="time" />
					    <s:param name="contentType" value="contentType" />
					</s:url>
                                                                              
                    ['<s:date name="time" format="yyyy-MM-dd HH:mm:ss" />'
                    ,'<span title="HTTP Status: <s:property value="statusCode"/>"><s:property value="messageType"/>&nbsp;</span>'
                    ,'<span title="HCID: <s:property value="sender.HCID"/>"><s:property value="sender.hostedBy"/>&nbsp;</span>'
                    ,'<span title="HCID: <s:property value="receiver.HCID"/>"><s:property value="receiver.hostedBy"/>&nbsp;</span>'
                    ,'<s:property value="path"/>'
                    ,'<s:a href="%{url}">Download</s:a>'
                    ]	    <s:if test="(#idx.index lt transactionList.size()-1)">,</s:if>	
                    
                </s:iterator>  
                    ];
</script>
<script type="text/javascript">
            
            // This file demonstrates the different options of the pagination plugin
            // It also demonstrates how to use a JavaScript data structure to 
            // generate the paginated content and how to display more than one 
            // item per page with items_per_page.
                    
            /**
             * Callback function that displays the content.
             *
             * Gets called every time the user clicks on a pagination link.
             *
             * @param {int}page_index New Page index
             * @param {jQuery} jq the container with the pagination links as a jQuery object
             */
			function pageselectCallback(page_index, jq){
                // Get number of elements per pagionation page from form
                var items_per_page = $('#items_per_page').val();
                var max_elem = Math.min((page_index+1) * items_per_page, transactionArray.length);
                var newcontent = '<table id="transactionList" style="font-size:8pt" width="100%" align="left" border="0">'
                       + '<tr class="tbheader">'
	                   + '<th align="left" width="12%">Time</th>'
	                   + '<th align="left" width="7%">Type</th>'
	                   + '<th align="left" width="10%">Sender</th>'
	                   + '<th align="left" width="10%">Receiver</th>'                    
	                   + '<th align="left">WS Endpoint</th>'
	                   + '<th align="left" >Message</th>'
	                   + '</tr>';                                  
                
                // Iterate through a selection of the content and build an HTML string
                for(var i=page_index*items_per_page;i<max_elem;i++)
                {
                    newcontent += '<tr '+ ((i%2)?'class="shaded"':'') +'>';
                    newcontent += '<td>' + transactionArray[i][0] + '</td>';
                    newcontent += '<td>' + transactionArray[i][1] + '</td>';
                    newcontent += '<td>' + transactionArray[i][2] + '</td>';
                    newcontent += '<td>' + transactionArray[i][3] + '</td>';
                    newcontent += '<td>' + transactionArray[i][4] + '</td>';
                    newcontent += '<td>' + transactionArray[i][5] + '</td>';
                    newcontent += '</tr>';

                }
                newcontent += '</table>'; 
                
                // Replace old content with new content
                $('#Searchresult').html(newcontent);
                
                // Prevent click eventpropagation
                return false;
            }
            
            // The form contains fields for many pagiantion optiosn so you can 
            // quickly see the resuluts of the different options.
            // This function creates an option object for the pagination function.
            // This will be be unnecessary in your application where you just set
            // the options once.
            function getOptionsFromForm(){
                var opt = {callback: pageselectCallback};
                // Collect options from the text fields - the fields are named like their option counterparts
                $("input:text").each(function(){
                    opt[this.name] = this.className.match(/numeric/) ? parseInt(this.value) : this.value;
                });
                // Avoid html injections in this demo
                var htmlspecialchars ={ "&":"&amp;", "<":"&lt;", ">":"&gt;", '"':"&quot;"}
                $.each(htmlspecialchars, function(k,v){
                    opt.prev_text = opt.prev_text.replace(k,v);
                    opt.next_text = opt.next_text.replace(k,v);
                })
                return opt;
            }
			
            // When document has loaded, initialize pagination and form 
            $(document).ready(function(){
				// Create pagination element with options from form
                var optInit = getOptionsFromForm();
                $("#Pagination").pagination(transactionArray.length, optInit);
                
				// Event Handler for for button
				$("#setoptions").click(function(){
                    var opt = getOptionsFromForm();
                    // Re-create pagination content with new parameters
                    $("#Pagination").pagination(transactionArray.length, opt);
                }); 

            });
            
</script>
        
<!-- skb -->        
<s:if test="transactionList !=null and transactionList.size() gt 0">
   <div id="Pagination" class="pagination">
        </div>
		

<div id="Searchresult">&nbsp;</div>
</s:if>			

    
<!-- skb -->    
<div style="display:none;visibility:hidden;"> 
<p><label for="items_per_page">Number of items per page</label><input type="text" value="<s:property value="maxLimit"/>" name="items_per_page" id="items_per_page" class="numeric" /></p>
			<p><label for="num_display_entries">Number of pagination links shown</label><input type="text" value="20" name="num_display_entries" id="num_display_entries" class="numeric" /></p>
			<p><label for="num">Number of start and end points</label><input type="text" value="2" name="num_edge_entries" id="num_edge_entries" class="numeric" /></p>
			<p><label for="prev_text">"Previous" label</label><input type="text" value="Prev" name="prev_text" id="prev_text" /></p>
			<p><label for="next_text">"Next" label</label><input type="text" value="Next" name="next_text" id="next_text" /></p>
			<input type="button" id="setoptions" value="Set options" style="display:none"/>    
</div>			
<!-- skb -->
                
			</div>
    
       
    </div>
</s:form>

<script type="text/javascript">
function doVerify() {
	var dtObj1 = document.getElementById('TransactionList_startTime');
	var dtObj2 = document.getElementById('TransactionList_endTime');
		if (dtObj1!=null && dtObj1.value.length>0) {
			if (dtObj2!=null && dtObj2.value.length==0) {
				alert('Please enter an end time to search by a date range.');
				return false;
			}	
		}
		if (dtObj2!=null && dtObj2.value.length>0) {
			if (dtObj1!=null && dtObj1.value.length==0) {
				alert('Please enter a start time to search by a date range.');
				return false;
			}	
		}
		return true;
}
</script>