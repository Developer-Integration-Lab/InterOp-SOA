/* Copyright 2009 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package com.predic8.plugin.membrane.components;

import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.predic8.membrane.core.Router;
import com.predic8.plugin.membrane.listeners.PortVerifyListener;

public class RuleTargetGroup {

	private Text textTargetPort;

	private Text textTargetHost;

	Pattern pHost = Pattern.compile("^([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])(\\.([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9]))*$");

	Pattern pIp = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$");
	
	public RuleTargetGroup(Composite parent, int style) {
		Group group = createGroup(parent, style);

		new Label(group, SWT.NONE).setText("Host");

		textTargetHost = createTargetHostText(group);

		createDummyLabel(group).setText(" ");
		
		createDummyLabel(group).setText(" ");
		
		new Label(group, SWT.NONE).setText("Port");

		textTargetPort = createTargetPortText(group);
		
		createDummyLabel(group).setText(" ");
		createDummyLabel(group).setText(" ");
		
	}

	private Text createTargetHostText(Group group) {
		Text text = new Text(group, SWT.BORDER);
		text.setText(Router.getInstance().getRuleManager().getDefaultTargetHost());
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Text createTargetPortText(Group group) {
		Text text = new Text(group,SWT.BORDER);
		text.setText("" + Router.getInstance().getRuleManager().getDefaultTargetPort());
		text.addVerifyListener(new PortVerifyListener());
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Group createGroup(Composite parent, int style) {
		Group group = new Group(parent, style);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		group.setLayout(layout);
		return group;
	}

	private Label createDummyLabel(Composite parent) {
		Label lb = new Label(parent, SWT.NONE);
		lb.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return lb;
	}

	public void clear() {
		textTargetHost.setText("");
		textTargetPort.setText("");
	}

	public String getTargetHost() {
		return textTargetHost.getText().trim();
	}

	public String getTargetPort() {
		return textTargetPort.getText().trim();
	}

	public void setTargetHost(String host) {
		textTargetHost.setText(host);
	}

	public void setTargetPort(String port) {
		textTargetPort.setText(port);
	}

	public void setTargetPort(int port) {
		textTargetPort.setText(Integer.toString(port));
	}
	
	public Text getTextTargetHost() {
		return textTargetHost;
	}
	
	public Text getTextTargetPort() {
		return textTargetPort;
	}
	
	public boolean isValidHostNameInput() {
		String txt = textTargetHost.getText();
		if ("".equals(txt))
			return false;

		if ("localhost".equals(txt))
			return true;

		return pHost.matcher(txt).matches() || pIp.matcher(txt).matches();
	}
}