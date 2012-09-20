<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-1.6.1.js"></script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="net.aegis.lab.util.LabConstants.LabType" %>
<%@ page import="net.aegis.lab.manager.ParticipantManager" %>
<%@ page import="net.aegis.lab.dao.pojo.User" %>
<%@ page import="net.aegis.lab.enums.MessageTypeEnum" %>

<%
    User user = ((User)session.getAttribute("userProfile"));
            LabType labType = user.getLabType();
            if (labType == null) {
                labType = LabType.LAB;
            }
            request.setAttribute("labType", labType);
%>
<s:form action="SetUpTest" theme="simple">
    <s:hidden key="submitFlag" />
    <div id="content">
        <div class="content-title">
            <table>
                <tr>
                    <td><span class="content-title-text"><s:property value="#session.userProfile.participant.participantName" /> - Set Up Test</span></td>
                    <td class="title-button">
                        <input type="submit" class="inputButton" name="buttonName" id="save" value="Save & Submit"
                               title="Submit the active service set for validation and create a new service set with the settings just selected"
                               onclick="return validateChkBoxes(true)"/>
                        <input type="submit" class="inputButton" name="buttonName" id="save" value="Save & Close"
                               title="Close the active service set and create a new service set with the settings just selected"
                               onclick="return validateChkBoxes(false)"/>
                        <input type="submit" class="inputButton" name="buttonName" id="refresh" value="Refresh" onclick="//"/>
                    </td>
                </tr>
            </table>
        </div>

        <div id="maindiv" class="content-area">
            <div>
                <s:actionerror/>
            </div>
            <!-- action message(s) -->
            <div>
                <strong style="color: #00f"><s:actionmessage/></strong>
            </div>

            <div class="section-title">
                Connection Information
            </div>
            <table>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.communityId"/></td>
                    <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.assigningAuthorityId"/></td>
                    <td><span class="long-display"><s:property value="participant.assigningAuthorityId" /></span></td>
                </tr>
                <tr>
                    <td><s:label cssClass="short-display" key="participant.ipAddress"/></td>
                    <td><span class="long-display"><s:property value="participant.ipAddress" /></span></td>
                </tr>
            </table>
        </div>

        <%--
         begin comment old Verify UI pre 1.3.X
         <div class="content-area">
             <div class="section-title">
                 Connection Status Checks
             </div>
             <table>
                 <tr>
                     <td><s:label cssClass="long-right" key="participant.setup.test.verify.connection.status"/></td>
                     <td><input type="submit" class="inputButton" name="buttonName" value="Verify" />
                     </td>
                     <td>
                         <s:if test="participant.commVerifyStatus == \"VERIFIED-GOOD\"">
                             <span class="status-good">Verified</span>
                         </s:if>
                         <s:elseif test="participant.commVerifyStatus == \"VERIFIED-FAILED\"">
                             <span class="status-bad">Failed</span>
                         </s:elseif>
                         <s:elseif test="participant.commVerifyStatus ==\"NOT VERIFIED\"">
                             <span class="status-noAction">Not Verified</span>
                         </s:elseif>
                     </td>
                 </tr>
                 <tr>
                     <td><s:label cssClass="long-right" key="participant.setup.test.verify.connection.timestamp"/></td>
                     <td colspan="2"><span class="long-left"><s:date name="participant.commVerifyTimestamp" format="d MMM, yyyy hh:mm a" /></span></td>
                 </tr>
             </table>
         </div>
       end comment old Verify UI pre 1.3.X
        --%>



        <div class="content-area">
            <div class="section-title">
                Select Service Set
            </div>
            <table id="serviceSetTable" class="content-data">
                <tr>
                    <th nowrap>Select</th>
                    <th nowrap>Service Set</th>
                    <th nowrap>Participation</th>
                    <th nowrap>SSN Handling</th>
                    <th nowrap>&nbsp;</th>
                </tr>
                <s:hidden id="mergedServiceSetsSize" name="mergedServiceSetsSize" value="%{mergedServiceSets.size()}"/>
                <s:iterator value="mergedServiceSets" status="setStatus">
                    <s:hidden name="mergedServiceSets[%{#setStatus.index}].serviceset.setId" value="%{serviceset.setId}"/>
                    <s:if test="serviceset.status == \"ACTIVE\"">
                        <tr class="<s:if test="#setStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>">
                            <td nowrap>
                                <s:if test="serviceset.status == \"ACTIVE\"">
                                    <s:checkbox cssClass="inputCheckbox" id="mergedServiceSets[%{#setStatus.index}].serviceSetIndId" name="mergedServiceSets[%{#setStatus.index}].serviceSetInd"  value="%{serviceSetInd[#setStatus.index]}"   disabled="false" onChange="javascript:dataModified();" /><br>&nbsp;
                                </s:if>
                                <s:else>
                                    <s:checkbox cssClass="inputCheckbox" id="mergedServiceSets[%{#setStatus.index}].serviceSetIndId" name="mergedServiceSets[%{#setStatus.index}].serviceSetInd"  value="false" disabled="true" onChange="javascript:dataModified();" /><br>&nbsp;
                                </s:else>
                            </td>
                            <td nowrap class="test-name">
                                <%-- Having InnerHTML behave correctly within IE and Firefox:           --%>
                                <%-- Good explanation at: http://www.kenvillines.com/archives/69.html   --%>
                                <div id="nameOfActiveServiceSet">
                                    <s:property value="serviceset.setName" />
                                </div>
                            </td>
                            <td nowrap class="test-options">
                                <s:checkbox cssClass="inputCheckbox" id="mergedServiceSets[%{#setStatus.index}].initiatorIndBooleanId" name="mergedServiceSets[%{#setStatus.index}].initiatorIndBoolean" fieldValue="%{initiatorIndBoolean}" value="%{initiatorIndBoolean}" onChange="javascript:dataModified();" onclick="javascript:hideOrShowInitiatorQuestions(this)" /><s:text name="participant.initiatorInd"/><br />
                                <s:checkbox cssClass="inputCheckbox" id="mergedServiceSets[%{#setStatus.index}].responderIndBooleanId" name="mergedServiceSets[%{#setStatus.index}].responderIndBoolean" value="%{responderIndBoolean}" fieldValue="%{responderIndBoolean}"  onChange="javascript:dataModified();" onclick="javascript:hideOrShowResponderQuestions(this)" /><s:text name="participant.responderInd"/>
                            </td>
                            <td nowrap class="test-options">
                                <s:radio cssClass="inputRadio" id="mergedServiceSets[%{#setStatus.index}].ssnHandlingIndId" name="mergedServiceSets[%{#setStatus.index}].ssnHandlingInd" value="%{ssnHandlingInd}" theme="radiosimplevertical" onchange="javascript:dataModified();" list="#{'Y':'Allowed','N':'Not Allowed'}"/>
                            </td>
                            <td nowrap>
                                <s:if test="status == \"ACTIVE\"">
                                    <%-- Having InnerHTML behave correctly within IE and Firefox:           --%>
                                    <%-- Good explanation at: http://www.kenvillines.com/archives/69.html   --%>
                                    <div id="whetherAnyActiveServiceSet">
                                        <span class="status-good">This service set is currently active.</span>
                                    </div>
                                </s:if>
                            </td>
                        </tr>
                        <s:if test="@net.aegis.lab.util.LabConstants$LabType@CONFORMANCE.equals(#attr.labType)">
                            <tr>
                                <td colspan="5">

                                    <%-- ILT-88, Story 147: --%>
                                    <div id="questionnaireDIvId">
                                        <div class="content-area">
                                            <div class="section-title">
                                                Participant Gateway Installation Conformance Questionnaire
                                            </div>
                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@MESSAGE_PAYLOAD.getDefaultDescription()" />
                                                </legend>
                                                <table class="content-data">
                                                    <s:hidden id="mergedServiceSets[%{#setStatus.index}].cdaQuestionnaireresponsesSize" name="mergedServiceSets[%{#setStatus.index}].cdaQuestionnaireresponsesSize" value="%{cdaQuestionnaireresponses.size()}"/>
                                                    <s:set name="cdaQuestionnaireresponses" value="%{cdaQuestionnaireresponses}"/>
                                                    <s:iterator value="cdaQuestionnaireresponses" status="questionnaireresponseStatus">

                                                        <s:if test="%{@net.aegis.lab.enums.MessageTypeEnum@MESSAGE_PAYLOAD.getTextId().equals(questionnaire.serviceType)}">
                                                            <s:set name="questionarierow">
                                                                <s:if test="questionnaire.uiDisplay ==\"Y\"">
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row</s:elseif>
                                                                </s:if>
                                                                <s:else>
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row-no-display</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row-no-display</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row-no-display</s:elseif>
                                                                </s:else>
                                                            </s:set>
                                                            <tr id="<s:property value="%{questionarierow}"/>" class="<s:if test="#questionnaireresponseStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"
                                                                style="<s:if test="%{(initiatorInd==questionnaire.initiatorInd or responderInd==questionnaire.responderInd) and questionnaire.uiDisplay ==\"Y\"}">display:visible;</s:if><s:else>display:none;</s:else>"
                                                                >
                                                                <td width="100%">
                                                                    <s:set name="questionId" value="questionnaire.questionId"/>
                                                                    <s:hidden name="questionIdHidden" value="%{questionId}"/>
                                                                    <s:hidden name="mergedServiceSets[%{#setStatus.index}].cdaQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionId" value="%{questionnaire.questionId}"/>
                                                                    <s:property value="%{#questionnaireresponseStatus.index + 1}" />. <s:property value="questionnaire.description" />
                                                                </td>

                                                                <td nowrap>

                                                                    <s:radio  cssClass="mergedServiceSets[%{#setStatus.index}].cdaQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionInputRadio"
                                                                              name="mergedServiceSets[%{#setStatus.index}].cdaQuestionnaireresponses[%{#questionnaireresponseStatus.index}].answer"
                                                                              value="%{answer}"  theme="radiosimplevertical"  onchange="javascript:dataModified(); hideOrShowTestplan(this.value,'%{questionId}')"  list="#{'Y':'Yes','N':'No'}" />
                                                                </td>
                                                            </tr>

                                                        </s:if>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@PATIENT_DISCOVERY.getDefaultDescription()" />
                                                </legend>
                                                <table class="content-data">
                                                    <s:hidden id="mergedServiceSets[%{#setStatus.index}].pdQuestionnaireresponsesSize" name="mergedServiceSets[%{#setStatus.index}].pdQuestionnaireresponsesSize" value="%{pdQuestionnaireresponses.size()}"/>
                                                    <s:set name="pdQuestionnaireresponses" value="%{pdQuestionnaireresponses}"/>
                                                    <s:iterator value="pdQuestionnaireresponses" status="questionnaireresponseStatus">
                                                        <s:if test="%{@net.aegis.lab.enums.MessageTypeEnum@PATIENT_DISCOVERY.getTextId().equals(questionnaire.serviceType) }">
                                                            <s:set name="questionarierow">
                                                                <s:if test="questionnaire.uiDisplay ==\"Y\"">
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row</s:elseif>
                                                                </s:if>
                                                                <s:else>
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row-no-display</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row-no-display</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row-no-display</s:elseif>
                                                                </s:else>
                                                            </s:set>
                                                            <tr id="<s:property value="%{questionarierow}"/>" class="<s:if test="#questionnaireresponseStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"
                                                                style="<s:if test="%{(initiatorInd==questionnaire.initiatorInd or responderInd==questionnaire.responderInd) and questionnaire.uiDisplay ==\"Y\" }">display:visible;</s:if><s:else>display:none;</s:else>" >
                                                                <td width="100%">
                                                                    <s:set name="questionId" value="questionnaire.questionId"/>
                                                                    <s:hidden name="questionIdHidden" value="%{questionId}"/>
                                                                    <s:hidden name="mergedServiceSets[%{#setStatus.index}].pdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionId" value="%{questionnaire.questionId}"/>
                                                                    <s:property value="%{#questionnaireresponseStatus.index + 1}" />. <s:property value="questionnaire.description" />
                                                                </td>

                                                                <td nowrap>

                                                                    <s:radio  cssClass="mergedServiceSets[%{#setStatus.index}].pdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionInputRadio"
                                                                              name="mergedServiceSets[%{#setStatus.index}].pdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].answer"
                                                                              value="%{answer}" theme="radiosimplevertical"  onchange="javascript:dataModified(); hideOrShowTestplan(this.value,'%{questionId}')"  list="#{'Y':'Yes','N':'No'}" />
                                                                </td>
                                                            </tr>

                                                        </s:if>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@QUERY_FOR_DOCUMENTS.getDefaultDescription()" />
                                                </legend>
                                                <table class="content-data">
                                                    <s:hidden id="mergedServiceSets[%{#setStatus.index}].qdQuestionnaireresponsesSize" name="mergedServiceSets[%{#setStatus.index}].qdQuestionnaireresponsesSize" value="%{qdQuestionnaireresponses.size()}"/>
                                                    <s:set name="qdQuestionnaireresponses" value="%{qdQuestionnaireresponses}"/>
                                                    <s:iterator value="qdQuestionnaireresponses" status="questionnaireresponseStatus">
                                                        <s:if test="%{@net.aegis.lab.enums.MessageTypeEnum@QUERY_FOR_DOCUMENTS.getTextId().equals(questionnaire.serviceType) }">
                                                            <s:set name="questionarierow">
                                                                <s:if test="questionnaire.uiDisplay ==\"Y\"">
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row</s:elseif>
                                                                </s:if>
                                                                <s:else>
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row-no-display</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row-no-display</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row-no-display</s:elseif>
                                                                </s:else>
                                                            </s:set>
                                                            <tr id="<s:property value="%{questionarierow}"/>" class="<s:if test="#questionnaireresponseStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"
                                                                style="<s:if test="%{(initiatorInd==questionnaire.initiatorInd or responderInd==questionnaire.responderInd) and questionnaire.uiDisplay ==\"Y\"}">display:visible;</s:if><s:else>display:none;</s:else>" >
                                                                <td width="100%">
                                                                    <s:set name="questionId" value="questionnaire.questionId"/>
                                                                    <s:hidden name="questionIdHidden" value="%{questionId}"/>
                                                                    <s:hidden name="mergedServiceSets[%{#setStatus.index}].qdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionId" value="%{questionnaire.questionId}"/>
                                                                    <s:property value="%{#questionnaireresponseStatus.index + 1}" />. <s:property value="questionnaire.description" />
                                                                </td>

                                                                <td nowrap>

                                                                    <s:radio  cssClass="mergedServiceSets[%{#setStatus.index}].qdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionInputRadio"
                                                                              name="mergedServiceSets[%{#setStatus.index}].qdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].answer"
                                                                              value="%{answer}" theme="radiosimplevertical"  onchange="javascript:dataModified(); hideOrShowTestplan(this.value,'%{questionId}')"  list="#{'Y':'Yes','N':'No'}" />
                                                                </td>
                                                            </tr>

                                                        </s:if>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@RETRIEVE_DOCUMENTS.getDefaultDescription()" />
                                                </legend>
                                                <table class="content-data">
                                                    <s:hidden id="mergedServiceSets[%{#setStatus.index}].rdQuestionnaireresponsesSize" name="mergedServiceSets[%{#setStatus.index}].rdQuestionnaireresponsesSize" value="%{rdQuestionnaireresponses.size()}"/>
                                                    <s:set name="rdQuestionnaireresponses" value="%{rdQuestionnaireresponses}"/>
                                                    <s:iterator value="rdQuestionnaireresponses" status="questionnaireresponseStatus">
                                                        <s:if test="%{@net.aegis.lab.enums.MessageTypeEnum@RETRIEVE_DOCUMENTS.getTextId().equals(questionnaire.serviceType) }">
                                                            <s:set name="questionarierow">
                                                                <s:if test="questionnaire.uiDisplay ==\"Y\"">
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row</s:elseif>
                                                                </s:if>
                                                                <s:else>
                                                                    <s:if test="%{initiatorInd==questionnaire.initiatorInd and responderInd==questionnaire.responderInd}">both-questionnaire-row-no-display</s:if>
                                                                    <s:elseif test="%{initiatorInd==questionnaire.initiatorInd}">ini-qst-row-no-display</s:elseif>
                                                                    <s:elseif test="%{responderInd==questionnaire.responderInd}">res-qst-row-no-display</s:elseif>
                                                                </s:else>
                                                            </s:set>
                                                            <tr id="<s:property value="%{questionarierow}"/>" class="<s:if test="#questionnaireresponseStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>"
                                                                style="<s:if test="%{(initiatorInd==questionnaire.initiatorInd or responderInd==questionnaire.responderInd) and questionnaire.uiDisplay ==\"Y\"}">display:visible;</s:if><s:else>display:none;</s:else>" >
                                                                <td width="100%">
                                                                    <s:set name="questionId" value="questionnaire.questionId"/>
                                                                    <s:hidden name="questionIdHidden" value="%{questionId}"/>
                                                                    <s:hidden name="mergedServiceSets[%{#setStatus.index}].rdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionId" value="%{questionnaire.questionId}"/>
                                                                    <s:property value="%{#questionnaireresponseStatus.index + 1}" />. <s:property value="questionnaire.description" />
                                                                </td>

                                                                <td nowrap>

                                                                    <s:radio  cssClass="mergedServiceSets[%{#setStatus.index}].rdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionInputRadio"
                                                                              name="mergedServiceSets[%{#setStatus.index}].rdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].answer"
                                                                              value="%{answer}" theme="radiosimplevertical"  onchange="javascript:dataModified(); hideOrShowTestplan(this.value,'%{questionId}')"  list="#{'Y':'Yes','N':'No'}" />
                                                                </td>
                                                            </tr>

                                                        </s:if>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                        </div>
                                    </div>
                                    <div id="testplanDivId">
                                        <div class="content-area">
                                            <table width="100%">
                                                <tr>
                                                    <td width="100%" align="left" class="title-button">
                                                        <input type="button" class="inputButton" name="buttonName" id="printbutton" value="Print Test Plan" onclick="PrintElem('printtestplanDiv') ; return false ; "/>
                                                    </td>
                                                </tr>
                                            </table>

                                        </div>
                                        <div class="content-area" id="printtestplanDiv">
                                            <div class="section-title">
                                                Conformance Test Plan

                                                <table width="100%" id="DynamicContentForPrintId" class="content-data" style="display:none">
                                                    <tr>
                                                        <td><s:label cssClass="short-display" key="participant.participantName"/></td>
                                                        <td><span class="long-display"><s:property value="#session.userProfile.participant.participantName" /></span></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:label cssClass="short-display" key="participant.communityId"/></td>
                                                        <td><span class="long-display"><s:property value="participant.communityId" /></span></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:label cssClass="short-display" key="participant.assigningAuthorityId"/></td>
                                                        <td><span class="long-display"><s:property value="participant.assigningAuthorityId" /></span></td>
                                                    </tr>
                                                    <tr>
                                                        <td><s:label cssClass="short-display" key="participant.ipAddress"/></td>
                                                        <td><span class="long-display"><s:property value="participant.ipAddress" /></span></td>
                                                    </tr>
                                                </table>
                                            </div>


                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@MESSAGE_PAYLOAD.getDefaultDescription()" />
                                                </legend>

                                                <table class="content-data"  width="100%">
                                                    <tr>
                                                        <th  width="10%">&nbsp;</th>
                                                        <th  width="10%">Test&nbsp;Case</th>
                                                        <th>Description</th>
                                                    </tr>
                                                    <s:iterator value="cdaQuestionnaireresponses" status="questionnaireresponseStatus">
                                                        <s:set name="cdaquestionnairecases" value="%{questionnaire.questionnairecases}"/>
                                                        <s:iterator value="%{cdaquestionnairecases}" status="questionnairecasesStatus">
                                                            <s:set name="testplanquestionId">testplan_<s:property value="questionnaire.questionId"/></s:set>
                                                            <s:hidden name="mergedServiceSets[%{#setStatus.index}].cdaQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionnairecases[%{#questionnairecasesStatus.index}].testcase.caseId"/>
                                                            <tr id ="<s:property value="%{testplanquestionId}"/>" class="<s:property value="%{testplanquestionId}"/>" style="<s:if test="answer == \"Y\" "></s:if><s:else>display:none;</s:else>">
                                                                <td>
                                                                    <s:if test="testcase.initiatorInd == \"Y\"">
                                                                        <img alt="Initiator" src="<%=request.getContextPath()%>/images/initiator-icon.gif" title="Initiator">
                                                                    </s:if>
                                                                    <s:else>
                                                                        <img alt="Responder" src="<%=request.getContextPath()%>/images/responder-icon.gif" title="Responder">
                                                                    </s:else>
                                                                </td>

                                                                <td align="center">
                                                                    <s:property value="testcase.name" />
                                                                </td>
                                                                <td align="left"><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                                                            </tr>
                                                        </s:iterator>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@PATIENT_DISCOVERY.getDefaultDescription()" />
                                                </legend>

                                                <table class="content-data"  width="100%">
                                                    <tr>
                                                        <th  width="10%">&nbsp;</th>
                                                        <th  width="10%">Test&nbsp;Case</th>
                                                        <th>Description</th>
                                                    </tr>
                                                    <s:iterator value="pdQuestionnaireresponses" status="questionnaireresponseStatus">
                                                        <s:set name="pdquestionnairecases" value="%{questionnaire.questionnairecases}"/>
                                                        <s:iterator value="%{pdquestionnairecases}" status="questionnairecasesStatus">
                                                            <s:set name="testplanquestionId">testplan_<s:property value="questionnaire.questionId"/></s:set>
                                                            <s:hidden name="mergedServiceSets[%{#setStatus.index}].pdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionnairecases[%{#questionnairecasesStatus.index}].testcase.caseId"/>
                                                            <tr id ="<s:property value="%{testplanquestionId}"/>" class="<s:property value="%{testplanquestionId}"/>" style="<s:if test="answer == \"Y\" "></s:if><s:else>display:none;</s:else>">
                                                                <td>
                                                                    <s:if test="testcase.initiatorInd == \"Y\"">
                                                                        <img alt="Initiator" src="<%=request.getContextPath()%>/images/initiator-icon.gif" title="Initiator">
                                                                    </s:if>
                                                                    <s:else>
                                                                        <img alt="Responder" src="<%=request.getContextPath()%>/images/responder-icon.gif" title="Responder">
                                                                    </s:else>
                                                                </td>

                                                                <td align="center">
                                                                    <s:property value="testcase.name" />
                                                                </td>
                                                                <td align="left"><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                                                            </tr>
                                                        </s:iterator>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@QUERY_FOR_DOCUMENTS.getDefaultDescription()" />
                                                </legend>

                                                <table class="content-data" width="100%">
                                                    <tr>
                                                        <th  width="10%">&nbsp;</th>
                                                        <th  width="10%">Test&nbsp;Case</th>
                                                        <th>Description</th>
                                                    </tr>
                                                    <s:iterator value="qdQuestionnaireresponses" status="questionnaireresponseStatus">
                                                        <s:set name="qdquestionnairecases" value="%{questionnaire.questionnairecases}"/>
                                                        <s:iterator value="%{qdquestionnairecases}" status="questionnairecasesStatus">
                                                            <s:set name="testplanquestionId">testplan_<s:property value="questionnaire.questionId"/></s:set>
                                                            <s:hidden name="mergedServiceSets[%{#setStatus.index}].qdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionnairecases[%{#questionnairecasesStatus.index}].testcase.caseId"/>
                                                            <tr id ="<s:property value="%{testplanquestionId}"/>" class="<s:property value="%{testplanquestionId}"/>" style="<s:if test="answer == \"Y\" "></s:if><s:else>display:none;</s:else>">
                                                                <td>
                                                                    <s:if test="testcase.initiatorInd == \"Y\"">
                                                                        <img alt="Initiator" src="<%=request.getContextPath()%>/images/initiator-icon.gif" title="Initiator">
                                                                    </s:if>
                                                                    <s:else>
                                                                        <img alt="Responder" src="<%=request.getContextPath()%>/images/responder-icon.gif" title="Responder">
                                                                    </s:else>
                                                                </td>

                                                                <td align="center">
                                                                    <s:property value="testcase.name" />
                                                                </td>
                                                                <td align="left"><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                                                            </tr>
                                                        </s:iterator>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                            <fieldset>
                                                <legend style="font-style:bold">
                                                    <s:property value="@net.aegis.lab.enums.MessageTypeEnum@RETRIEVE_DOCUMENTS.getDefaultDescription()" />
                                                </legend>

                                                <table class="content-data" width="100%">
                                                    <tr>
                                                        <th  width="10%">&nbsp;</th>
                                                        <th  width="10%">Test&nbsp;Case</th>
                                                        <th>Description</th>
                                                    </tr>
                                                    <s:iterator value="rdQuestionnaireresponses" status="questionnaireresponseStatus">
                                                        <s:set name="rdquestionnairecases" value="%{questionnaire.questionnairecases}"/>
                                                        <s:iterator value="%{rdquestionnairecases}" status="questionnairecasesStatus">
                                                            <s:set name="testplanquestionId">testplan_<s:property value="questionnaire.questionId"/></s:set>
                                                            <s:hidden name="mergedServiceSets[%{#setStatus.index}].rdQuestionnaireresponses[%{#questionnaireresponseStatus.index}].questionnaire.questionnairecases[%{#questionnairecasesStatus.index}].testcase.caseId"/>
                                                            <tr id ="<s:property value="%{testplanquestionId}"/>" class="<s:property value="%{testplanquestionId}"/>" style="<s:if test="answer == \"Y\" "></s:if><s:else>display:none;</s:else>">
                                                                <td>
                                                                    <s:if test="testcase.initiatorInd == \"Y\"">
                                                                        <img alt="Initiator" src="<%=request.getContextPath()%>/images/initiator-icon.gif" title="Initiator">
                                                                    </s:if>
                                                                    <s:else>
                                                                        <img alt="Responder" src="<%=request.getContextPath()%>/images/responder-icon.gif" title="Responder">
                                                                    </s:else>
                                                                </td>

                                                                <td align="center">
                                                                    <s:property value="testcase.name" />
                                                                </td>
                                                                <td align="left"><font style="font-size: 0.8em"><s:property value="testcase.description" /></font></td>
                                                            </tr>
                                                        </s:iterator>
                                                    </s:iterator>
                                                </table>
                                            </fieldset>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </s:if>
                    </s:if>
                    <%-- TODO : not sure when this code will be executed :           --%>
                    <s:else>
                        <tr class="<s:if test="#setStatus.odd == true">odd-row</s:if><s:else>even-row</s:else>" >
                            <td nowrap>
                                <s:checkbox cssClass="inputCheckbox" name="mergedServiceSets[%{#setStatus.index}].serviceSetInd"  value="false" disabled="true" onChange="javascript:dataModified();" /><br>&nbsp;
                            </td>
                            <td nowrap class="test-name"><s:property value="serviceset.setName" /></td>
                            <td nowrap class="test-options">
                                <s:checkbox cssClass="inputCheckbox" name="initiatorIndicator" value="%{initiatorIndicator[#setStatus.index]}"  disabled="true" onChange="javascript:dataModified();" /><s:text name="participant.initiatorInd"/><br />
                                <s:checkbox cssClass="inputCheckbox" name="responderIndicator" value="%{responderIndicator[#setStatus.index]}"  disabled="true" onChange="javascript:dataModified();" /><s:text name="participant.responderInd"/>
                            </td>
                            <td nowrap class="test-options">
                                <s:radio cssClass="inputRadio" name="ssnHandlingIndicator[%{#setStatus.index}]" value="%{ssnHandlingInd}" theme="radiosimplevertical" disabled="true" onchange="javascript:dataModified();"  list="#{'Y':'Allowed','N':'Not Allowed'}" />
                            </td>
                        </tr>
                    </s:else>
                </s:iterator>
            </table>
        </div>

    </div>
</s:form>
<script type="text/javascript">
    $(document).ready(function() {

    });
    // may need to refactor in future ..
    // used to hide and show test cases
    var initiatorIndChecked ; //  global variable to whether initiator check box is checked or not  
    var responderIndChecked ; // global variable whether responder check box is checked or not
	
    //
    var objForm = document.forms[0];
    // if user trie to navigate away from page , then show alert ..
    // Note : make sure that this value as false in javascript method which makes call to server side. Ex : validateChkBoxes
    var beenModified=false;

    function dataModified(){
        beenModified=true;
    }

    function validateChkBoxes(submitFlag){
        beenModified=false;
        var toBeSubmitted = false;
        var  mergedServiceSetsSize = $("#mergedServiceSetsSize");
        for(var i = 0 ; i < mergedServiceSetsSize.val() ; i ++){
        	var serviceSetInd = document.getElementById("mergedServiceSets[" + i + "].serviceSetIndId");
            //alert("serviceSetInd===checked or not :::" + serviceSetInd.checked  + " value === "+ serviceSetInd.value);
            if(serviceSetInd != null && serviceSetInd.value == 'true' && !(serviceSetInd.checked) && !toBeSubmitted){
                 //alert("serviceSetInd===not checked:::" + serviceSetInd.checked );
                var e = document.getElementById("maindiv");
                var errorText="Please Select At least One Service Set Before Submitting";
                clearErrorMessages(objForm);
                addError(e,errorText);
                toBeSubmitted = false;
                continue;
            }else if(serviceSetInd != null && serviceSetInd.checked){
                   //alert("serviceSetInd===checked :::" + serviceSetInd.checked);
                var initiatorIndBoolean = document.getElementById("mergedServiceSets[" + i + "].initiatorIndBooleanId");
                var responderIndBoolean = document.getElementById("mergedServiceSets[" + i + "].responderIndBooleanId");
                clearErrorMessages(objForm);
                if(!(initiatorIndBoolean.checked || responderIndBoolean.checked)  ){
                    var e = document.getElementById("maindiv");
                    var errorText="Please select initiator or responder Indicator for Service Set";
                    //beenModified=true;                   
                    addError(e,errorText);
                    toBeSubmitted = false;
                    break;

                }else{
                	if(toBeSubmitted){
						alert("Current System doesn't support multiple active sets. Please select one service set and click Save & Submit or Save & Close button.");
                   		toBeSubmitted = false;
                    }else{
                		toBeSubmitted = true;
                	}
                }

            }else{
                continue;
            }
        }
        if(!toBeSubmitted){
            // alert("toBeSubmitted===" + toBeSubmitted );
            return toBeSubmitted;
        }
        <s:if test="@net.aegis.lab.util.LabConstants$LabType@CONFORMANCE.equals(#attr.labType)">
                if(toBeSubmitted && !validateQuestionaries()){
                    toBeSubmitted = false;
                }
        </s:if>
            // TODO: may need to refactor in future
            if( toBeSubmitted && (document.getElementById("mergedServiceSets[0].serviceSetIndId").checked) && ("undefined" != $("#whetherAnyActiveServiceSet")) ){
                <% String msgStr = "";
                if (!ParticipantManager.getInstance().isParticipantOfTestLab(((User) session.getAttribute("userProfile")).getParticipant().getCommunityId(), LabType.LAB)) {
                    msgStr = "alert('Please note: You may not execute an Lab test phase until an Initial Connectivity service set has been validated.')";
                %> <%=msgStr%>
                    toBeSubmitted=false;
                <%} else {%>
                var confirmMsg ="";
                if(submitFlag){
                    confirmMsg = "Do you want to submit the currently active service set for validation and create a new active service set?";
                }else{
                    confirmMsg = "This will close the currently active service set and create a new active service set. Do you want to proceed?";
                }
                //  if(confirm('Saving these changes will close/archive the currently active Service Set and create a new Active Service Set with the settings just entered.\n\nDo you want to continue with Saving Changes?  Select \"OK\" to continue, or \"Cancel\" to reset the settings to reflect the current Active Service Set?')) {
                if(confirm(confirmMsg)){
                    toBeSubmitted = true;
                }else{
                    toBeSubmitted = false;
                }
                <% } %>
            }
            //Set the submitFlag value to the hidden variable so it can be used in the java class.
            document.getElementById("SetUpTest").submitFlag.value = submitFlag;
           
       //    alert("submitFlag : "+ document.getElementById("SetUpTest").submitFlag.value);
       //    alert("End of validation method :  "+toBeSubmitted);
            return toBeSubmitted;
        }


        function  validateQuestionaries(){
            var toBeSubmitted = true;
            var objForm = document.forms[0];
            var cdaquestionsvalid = true;
            var pdquestionsvalid = true;
            var qdquestionsvalid = true;

            clearErrorMessages(objForm);

            var  mergedServiceSetsSize = $("#mergedServiceSetsSize");
            // alert("mergedServiceSetsSize==" + mergedServiceSetsSize.val());
            for(var i = 0 ; i < mergedServiceSetsSize.val() ; i ++){

                var cdaQuestionnaireresponsesSize = $("#mergedServiceSets\\[" + i + "\\]\\.cdaQuestionnaireresponsesSize");
                var pdQuestionnaireresponsesSize = $("#mergedServiceSets\\[" + i + "\\]\\.pdQuestionnaireresponsesSize");
                var qdQuestionnaireresponsesSize = $("#mergedServiceSets\\[" + i + "\\]\\.qdQuestionnaireresponsesSize");
                var rdQuestionnaireresponsesSize = $("#mergedServiceSets\\[" + i + "\\]\\.rdQuestionnaireresponsesSize");
                //alert("cdaQuestionnaireresponsesSize==" + cdaQuestionnaireresponsesSize.val());
                for(var j=0 ; j < cdaQuestionnaireresponsesSize.val() ; j++){
                    var questionInputRadiovar = "mergedServiceSets\\[" + i + "\\]\\.cdaQuestionnaireresponses\\[" + j + "\\]\\.questionInputRadio";
                    var questionInputRadio = $(":radio[class='" + questionInputRadiovar + "']");
                    //alert("cdaquestionInputRadio===" + $(questionInputRadio).is(":checked"));
                    if($(questionInputRadio).length && $(questionInputRadio).is(":visible") && !$(questionInputRadio).is(":checked")){
                        var e = document.getElementById("maindiv");
                        var errorText="Please answer CDA question#" + (j +1)  ;
                        clearErrorMessages(objForm);
                        addError(e,errorText);
                        toBeSubmitted=false;
                        cdaquestionsvalid = false;
                        break;
                    }
                }
                if(cdaquestionsvalid){
                    for(var j=0 ; j < pdQuestionnaireresponsesSize.val() ; j++){
                        var questionInputRadiovar = "mergedServiceSets\\[" + i + "\\]\\.pdQuestionnaireresponses\\[" + j + "\\]\\.questionInputRadio";
                        var questionInputRadio = $(":radio[class='" + questionInputRadiovar + "']");
                        //alert("pdquestionInputRadio  html== " + $(questionInputRadio).html()  + "::::checked ==" + $(questionInputRadio).is(":checked") + "::::== legnth==" + $(questionInputRadio).length);
                        if( $(questionInputRadio).length && $(questionInputRadio).is(":visible") && !$(questionInputRadio).is(":checked") ){
                            var e = document.getElementById("maindiv");
                            var errorText="Please answer PD question#" + (j + 1 ) ;
                            clearErrorMessages(objForm);
                            addError(e,errorText);
                            toBeSubmitted=false;
                            pdquestionsvalid= false;
                            return false;
                        }
                    }

                }
                if(cdaquestionsvalid && pdquestionsvalid){
                    for(var j=0 ; j < qdQuestionnaireresponsesSize.val() ; j++){
                        var questionInputRadiovar = "mergedServiceSets\\[" + i + "\\]\\.qdQuestionnaireresponses\\[" + j + "\\]\\.questionInputRadio";
                        var questionInputRadio = $(":radio[class='" + questionInputRadiovar + "']");
                        //alert("qdquestionInputRadio===" + $(questionInputRadio).is(":checked"));
                        if($(questionInputRadio).length && $(questionInputRadio).is(":visible") && !$(questionInputRadio).is(":checked")){
                            var e = document.getElementById("maindiv");
                            var errorText="Please answer QD question#" + (j +1) ;
                            clearErrorMessages(objForm);
                            addError(e,errorText);
                            toBeSubmitted=false;
                            qdquestionsvalid=false;
                            break;
                        }
                    }
                }
                if(cdaquestionsvalid && pdquestionsvalid && qdquestionsvalid){
                    for(var j=0 ; j < rdQuestionnaireresponsesSize.val() ; j++){
                        var questionInputRadiovar = "mergedServiceSets\\[" + i + "\\]\\.rdQuestionnaireresponses\\[" + j + "\\]\\.questionInputRadio";
                        var questionInputRadio = $(":radio[class='" + questionInputRadiovar + "']");
                        //alert("rdquestionInputRadio===" + $(questionInputRadio).is(":checked"));
                        if($(questionInputRadio).length && $(questionInputRadio).is(":visible") && !$(questionInputRadio).is(":checked")){
                            var e = document.getElementById("maindiv");
                            var errorText="Please answer RD question#" + (j +1) ;
                            clearErrorMessages(objForm);
                            addError(e,errorText);
                            toBeSubmitted=false;
                            qdquestionsvalid = false;
                            break;
                        }
                    }
                }
            }
            if(!(cdaquestionsvalid && cdaquestionsvalid&& cdaquestionsvalid && cdaquestionsvalid)){
                toBeSubmitted = false;
            }
            return toBeSubmitted  ;
        }


        function hideOrShowInitiatorQuestions(objId){
            clearErrorMessages(objForm);
            if(objId.checked){
                initiatorIndChecked = true;
                // show displayable (rendrable) questions and its test cases
                $("tr#ini-qst-row").each(function (){
                    $(this).show();
                    // alert("show ::: radio value== " + $(this).find('td input[type=radio]').val() );
                    $(this).find('td input[type=radio]').attr('checked', true);
                    //alert("show ::: $(this).find== " + $(this).find('input[name=questionIdHidden]').val() );
                    hideOrShowTestplan("Y" , $(this).find('input[name=questionIdHidden]').val() );
                });
                // set non displayable  questions to true  and show test cases
                $("tr#ini-qst-row-no-display").each(function (){
                    $(this).find('td input[type=radio]').attr('checked', true);
                    hideOrShowTestplan("Y" , $(this).find('input[name=questionIdHidden]').val() );
                });

            }else{
                initiatorIndChecked = false;
                // hide displayable (rendrable) questions and its test cases
                $("tr#ini-qst-row").each(function (){
                    $(this).hide();
                    // alert("hide ::: radio value== " + $(this).find('td input[type=radio]').val() );
                    $(this).find('td input[type=radio]').attr('checked', false);                   
                    //alert("hide ::: $(this).find== " +  $(this).find('input[name=questionIdHidden]').val() );
                    hideOrShowTestplan("N" , $(this).find('input[name=questionIdHidden]').val() );
                });
                // set non displayable  questions to false  and hide test cases
                $("tr#ini-qst-row-no-display").each(function (){
                    $(this).find('td input[type=radio]').attr('checked', false);
                    hideOrShowTestplan("N" , $(this).find('input[name=questionIdHidden]').val() );
                });
            }
            hideOrShowInitiatorAndResponderQuestionsAndTestplan();
        }
        

        function hideOrShowResponderQuestions(objId){
            clearErrorMessages(objForm);
            if(objId.checked){
                responderIndChecked = true;
                // show displayable (rendrable) questions and its test cases
                $("tr#res-qst-row").each(function (){
                    $(this).show();
                    $(this).find('td input[type=radio]').attr('checked', true);
                    hideOrShowTestplan("Y" , $(this).find('input[name=questionIdHidden]').val() );
                });
                // set non displayable  questions to true  and show test cases
                $("tr#res-qst-row-no-display").each(function (){
                    $(this).find('td input[type=radio]').attr('checked', true);
                    hideOrShowTestplan("Y" , $(this).find('input[name=questionIdHidden]').val() );
                });

            }else{
                responderIndChecked = false;
                // hide displayable (rendrable) questions and its test cases
                $("tr#res-qst-row").each(function (){
                    $(this).hide();
                    $(this).find('td input[type=radio]').attr('checked', false);
                    hideOrShowTestplan("N" , $(this).find('input[name=questionIdHidden]').val() );
                });
                // set non displayable  questions to false  and hide test cases
                $("tr#res-qst-row-no-display").each(function (){
                    $(this).find('td input[type=radio]').attr('checked', false);
                    hideOrShowTestplan("N" , $(this).find('input[name=questionIdHidden]').val() );
                });
            }
            hideOrShowInitiatorAndResponderQuestionsAndTestplan();
        }

        /*
         * thisObjValue = "y" meaning that show test cases
         */
        function hideOrShowTestplan(thisObjValue , questionId){
            clearErrorMessages(objForm);
            if(thisObjValue=="Y"){
                //alert("hideOrShowTestplan  questionId ==" + questionId + "::::checked== " + $("tr#testplan_" + questionId).html() );
                $("tr#testplan_" +questionId ).each(function (index){
                    //alert("hideOrShowTestplan checked==  each == " + $(this).html() );
                    //$("tr#testplan_" +questionId).show();
                    $(this).show();
                });

            }else if(thisObjValue=="N"){
                //alert("hideOrShowTestplan questionId ==" + questionId + "::::unchecked :: N:::: " + $("tr#testplan_" + questionId).html() );
                $("tr#testplan_" +questionId ).each(function (index){
                    //alert("hideOrShowTestplan unchecked :: N:::each == " + $(this).html() );
                    $(this).hide();
                });

            }

        }

        function hideOrShowInitiatorAndResponderQuestionsAndTestplan(){
            //alert("initiatorIndChecked==" + initiatorIndChecked + "::::responderIndChecked===" + responderIndChecked);
            if( (initiatorIndChecked!=undefined && !initiatorIndChecked) && (responderIndChecked!=undefined && !responderIndChecked) ){
                $("#questionnaireDIvId").hide();
                $("#testplanDivId").hide();
            }else{
                $("#questionnaireDIvId").show();
                $("#testplanDivId").show();
            }
        }

        function addError(e, errorText) {
            var errDiv = document.createElement('div');
            errDiv.setAttribute('errorFor', e.getAttribute('id'));
            errDiv.innerHTML = errorText;
            errDiv.className = 'error-title-text';
            e.parentNode.insertBefore(errDiv,e);

        }

        function clearErrorMessages(form) {
            var prevErrDivs = form.getElementsByTagName('div');
            if(prevErrDivs != undefined){
                for(var i=0; i<prevErrDivs.length; i++) {
                    if(prevErrDivs[i].getAttribute('errorFor')) {
                        prevErrDivs[i].parentNode.removeChild(prevErrDivs[i]);
                        i--; // prevErrDivs colection changes as elements removed
                    }
                }
            }
        }
        var checkval;

        window.onbeforeunload = function (evt) {
            if(beenModified){
                var message ='You have made changes to the Service Set parameters.' ;
                if (typeof evt == 'undefined') {//IE
                    evt = window.event;
                }
                if (evt) {
                    evt.returnValue = message;
                }
                return message;
            }
        }

        function diplayEndPoints() {
            window.open("getEndPoints","myWindow", "status = 1, height = 500, width = 950, resizable = 1,scrollbars=1");
        }

        function doVerify(vType) {
            if (confirm('Please confirm if you would like to Verify a '+vType+' connection.')) {
                document.getElementById("SetUpTest").action += "?buttonName=Verify." + vType;
                document.getElementById("SetUpTest").submit();
            }
        }

        function PrintElem(elem)
        {
            $("#DynamicContentForPrintId").show();            //
            Popup($("#" + elem).html());
        }

        function Popup(data)
        {
            var mywindow = window.open('', 'my div', 'height=400,width=600');
            mywindow.document.write('<html><head><title>print conformance test cases</title>');
            /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />');
            mywindow.document.write('</head><body >');
            mywindow.document.write(data);
            mywindow.document.write('</body></html>');
            mywindow.document.close();
            mywindow.print();
            $("#DynamicContentForPrintId").hide();
            return true;
        }


</script>