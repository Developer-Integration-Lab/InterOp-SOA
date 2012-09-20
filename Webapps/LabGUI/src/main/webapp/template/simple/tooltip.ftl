<#if parameters.completeValue??>
    <@s.set name="completeValue" value="%{'${parameters.completeValue}'}" />
    <#if parameters.shortValue??>
        <@s.set name="shortValue" value="%{'${parameters.shortValue}'}" />
        <@s.if test="%{${completeValue?length}>${shortValue?length}}">
            <span class="hoverText" onmouseover="showTblColumnTooltip(event,this);" onmouseout="removeAllToolTips();">${shortValue}...</span>
            <span class="tooltip" style="display:none;" >${completeValue}</span>
        </@s.if>
        <@s.else>
            ${completeValue}
        </@s.else>
    </#if>
</#if>
