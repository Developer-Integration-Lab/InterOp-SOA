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

package com.predic8.plugin.membrane.dialogs.rule;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;

import com.predic8.membrane.core.rules.ProxyRuleKey;
import com.predic8.membrane.core.rules.Rule;
import com.predic8.plugin.membrane.dialogs.rule.composites.ProxyRuleKeyTabComposite;
import com.predic8.plugin.membrane.dialogs.rule.composites.RuleActionsTabComposite;
import com.predic8.plugin.membrane.dialogs.rule.composites.RuleGeneralInfoTabComposite;
import com.predic8.plugin.membrane.dialogs.rule.composites.RuleInterceptorTabComposite;

public class ProxyRuleEditDialog extends RuleEditDialog {

	private ProxyRuleKeyTabComposite ruleKeyComposite;

	public ProxyRuleEditDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public String getTitle() {
		return "Edit Proxy Rule";
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Control comp = super.createDialogArea(parent);

		generalInfoComposite = new RuleGeneralInfoTabComposite(tabFolder);
		TabItem generalTabItem = new TabItem(tabFolder, SWT.NONE);
		generalTabItem.setText("General");
		generalTabItem.setControl(generalInfoComposite);

		ruleKeyComposite = new ProxyRuleKeyTabComposite(tabFolder);
		TabItem keyTabItem = new TabItem(tabFolder, SWT.NONE);
		keyTabItem.setText("Rule Key");
		keyTabItem.setControl(ruleKeyComposite);

		actionsComposite = new RuleActionsTabComposite(tabFolder);
		TabItem actionsTabItem = new TabItem(tabFolder, SWT.NONE);
		actionsTabItem.setText("Actions");
		actionsTabItem.setControl(actionsComposite);

		interceptorsComposite = new RuleInterceptorTabComposite(tabFolder);
		TabItem interceptorsTabItem = new TabItem(tabFolder, SWT.NONE);
		interceptorsTabItem.setText("Interceptors");
		interceptorsTabItem.setControl(interceptorsComposite);

		return comp;
	}

	@Override
	public void setInput(Rule rule) {
		super.setInput(rule);
		ruleKeyComposite.setInput(rule.getKey());
	}

	@Override
	public void onOkPressed() {
		int port = 0;
		try {
			port = Integer.parseInt(ruleKeyComposite.getListenPort());
		} catch (NumberFormatException nfe) {
			openErrorDialog("Illeagal input! Please check listen port again");
			return;
		}

		ProxyRuleKey ruleKey = new ProxyRuleKey(port);
		doRuleUpdateRule(ruleKey);
		
	}	

}
