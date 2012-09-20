
function loadPage()
{	
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
		Effect.BlindUp(panelName, {duration: speed});
		titlebar.className = "collapsible-title collapsed"
	}
	else
	{
		Effect.BlindDown(panelName, {duration: speed});
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
	if (pass > 0){ document.writeln("	<td width='" + pass + "%' class='test-pass'>&nbsp;</td>"); }
	if (fail > 0){ document.writeln("	<td width='" + fail + "%' class='test-fail'>&nbsp;</td>"); }
	if (inProgress > 0){ document.writeln("	<td width='" + inProgress + "%' class='test-in-progress'>&nbsp;</td>"); }
	if (pending > 0){ document.writeln("	<td width='" + pending + "%' class='test-pending'>&nbsp;</td>"); }
	document.writeln("</tr></table>");
}

function generateFooter()
{
	document.writeln("<div id=\"footer\">");
	document.writeln("<td id=\"copyright\">&copy; " + new Date().getFullYear() + " Nationwide Health Information Network</td>");
	document.writeln("</div>");
}