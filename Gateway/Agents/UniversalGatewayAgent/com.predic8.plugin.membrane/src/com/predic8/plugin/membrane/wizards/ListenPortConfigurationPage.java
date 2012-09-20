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

package com.predic8.plugin.membrane.wizards;

import java.io.IOException;
import java.net.ServerSocket;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.predic8.membrane.core.Router;
import com.predic8.membrane.core.rules.ForwardingRuleKey;
import com.predic8.membrane.core.transport.http.HttpTransport;
import com.predic8.plugin.membrane.listeners.PortVerifyListener;

public class ListenPortConfigurationPage extends AbstractRuleWizardPage {

	public static final String PAGE_NAME = "Listen Port Configuration";
	
	private Text listenPortText;
	
	protected ListenPortConfigurationPage() {
		super(PAGE_NAME);
		setTitle("Simple Rule");
		setDescription("Specify Listen Port");
	}

	public void createControl(Composite parent) {
		Composite composite = createComposite(parent, 2);
		
		createFullDescriptionLabel(composite, "A rule is listenening on a TCP port for incomming connections.\n" + "The port number can be any integer between 1 and 65535.");
		
		Label listenPortLabel = createListenPortLabel(composite);
		listenPortLabel.setText("Listen Port:");

		listenPortText = createListenPortText(composite);
		
		setControl(composite);
	}

	private Label createListenPortLabel(Composite composite) {
		Label listenPortLabel = new Label(composite, SWT.NONE);
		GridData gridData4ListenPortLabel = new GridData();
		gridData4ListenPortLabel.horizontalSpan = 1;
		listenPortLabel.setLayoutData(gridData4ListenPortLabel);
		return listenPortLabel;
	}

	private Text createListenPortText(Composite composite) {
		final Text text = new Text(composite,SWT.BORDER);
		text.addVerifyListener(new PortVerifyListener());
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setText("" + Router.getInstance().getRuleManager().getDefaultListenPort());
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (text.getText().trim().equals("")) {
					setPageComplete(false);
					setErrorMessage("Listen port must be specified");
				} else if (text.getText().trim().length() >= 5) {
					try {
						if (Integer.parseInt(text.getText()) > 65535) {
							setErrorMessage("Listen port number has an upper bound 65535.");
							setPageComplete(false);
						}
					} catch (NumberFormatException nfe) {
						setErrorMessage("Specified listen port must be in decimal number format.");
						setPageComplete(false);
					}
				} else {
					setErrorMessage(null);
					setPageComplete(true);
				}
			}
		});
		return text;
	}

	@Override
	public boolean canFlipToNextPage() {
		if (!isPageComplete())
			return false;
		try {
			if (((HttpTransport) Router.getInstance().getTransport()).isAnyThreadListeningAt(Integer.parseInt(listenPortText.getText()))) {
				return true;
			}
			new ServerSocket(Integer.parseInt(listenPortText.getText())).close();
			return true;
		} catch (IOException ex) {
			setErrorMessage("Port is already in use. Please choose a different port!");
			return false;
		} 
	}
	
	@Override
	public IWizardPage getNextPage() {
		IWizardPage page = getWizard().getPage(TargetConfigurationPage.PAGE_NAME);
		//page is used in simple and advanced configuration, therefore title must be adjusted
		page.setTitle("Simple Rule"); 
		return page;
	}
	
	public String getListenPort() {
		return listenPortText.getText();
	}

	protected boolean performFinish(AddRuleWizard wizard) throws IOException {
		ForwardingRuleKey ruleKey = new ForwardingRuleKey("*", "*", ".*", Integer.parseInt(getListenPort()));
		
		if (Router.getInstance().getRuleManager().exists(ruleKey)) {
			wizard.openWarningDialog("You've entered a duplicated rule key.");
			return false;
		}
		
		
		wizard.createForwardingRule(ruleKey);
		return true;
	}
	
}
