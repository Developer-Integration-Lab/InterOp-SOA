
function loadPage(resultValue,caseExecId,caseName )
{
    //alert("I am here in load page "+ resultValue+ caseName+caseExecId);
    if(resultValue=="true"){
        displayTimedWindowMessage(caseExecId,caseName);
    }
    // collapse all collapsible areas
    if ($('collapsible-areas') != null)
    {
        var collapsibleAreas = $('collapsible-areas').getElementsByClassName('collapsible-area');

        for(i = 0; i < collapsibleAreas.length; i++)
        {
            $(collapsibleAreas[i].id).hide();
        }
    }
}


function panelClick(titlebar, panelName)
{
    speed = 0.2;
	
    if ($(panelName).visible())
    {
        Effect.BlindUp(panelName, {
            duration: speed
        });
        titlebar.className = "collapsible-title collapsed"
    }
    else
    {
        Effect.BlindDown(panelName, {
            duration: speed
        });
        titlebar.className = "collapsible-title expanded"
    }
}

function masterCheckboxClick(parent, containerId, childrenClass)
{
    var children = $(containerId).getElementsByClassName(childrenClass);
	
    for(i = 0; i < children.length; i++)
    {
        $(children[i].id).checked = parent.checked;
    }
}

function generateTestMeter (pass, fail, inProgress, pending)
{
    document.writeln("<table class='test-meter'><tr>");
    if (pass > 0){
        document.writeln("	<td width='" + pass + "%' class='test-pass'>&nbsp;</td>");
    }
    if (fail > 0){
        document.writeln("	<td width='" + fail + "%' class='test-fail'>&nbsp;</td>");
    }
    if (inProgress > 0){
        document.writeln("	<td width='" + inProgress + "%' class='test-in-progress'>&nbsp;</td>");
    }
    if (pending > 0){
        document.writeln("	<td width='" + pending + "%' class='test-pending'>&nbsp;</td>");
    }
    document.writeln("</tr></table>");
}

function generateFooter()
{
    document.writeln("<div id=\"footer\">");
    document.writeln("<td id=\"copyright\">&copy; " + new Date().getFullYear() + " Nationwide Health Information Network</td>");
    document.writeln("</div>");
}

function showTblColumnTooltip(event,element) {
    var browserEvent = getBrowserEvent(event);
    var pageX = getEventX(browserEvent) + 8;
    var pageY = getEventY(browserEvent) + 8;
    var width = window.document.body.clientWidth - pageX - 10;
    var sibling = findNextSibling(element);
    if (sibling) {
        sibling.style.position = 'absolute';
        sibling.style.left = pageX;
        sibling.style.top = pageY;
        sibling.style.maxWidth = width;
        sibling.style.cursor = 'pointer';
        sibling.style.display = 'inline';
    }
}

function findNextSibling(element) {
    do {
        element = element.nextSibling;
    } while(element && element.nodeType !== 1); // 1 == Node.ELEMENT_NODE
    return (element && element.nodeType==1 ? element : null);
}

function getBrowserEvent(event) {
    if (window.event) {
        return window.event;
    } else {
        return event;
    }
}

function removeAllToolTips() {
    var elements = getElementsByClassName('tooltip')
    if (elements && elements.length>0) {
        for (var i=0; i<elements.length; i++) {
            elements[i].style.display = 'none';
        }
    }
}

function getEventX(event) {
    if (event.x) {
        return event.x;
    } else {
        return event.pageX;
    }
}

function getEventY(event) {
    if (event.y) {
        return event.y;
    } else {
        return event.pageY;
    }
}

function getElementsByClassName(className) {
    var hasClassName = new RegExp("(?:^|\\s)" + className + "(?:$|\\s)");
    var allElements = document.getElementsByTagName("*");
    var results = [];

    var element;
    for (var i = 0; (element = allElements[i]) != null; i++) {
        var elementClass = element.className;
        if (elementClass && elementClass.indexOf(className) != -1 && hasClassName.test(elementClass))
            results.push(element);
    }
    return results;
}
