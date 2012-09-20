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

package com.predic8.plugin.membrane.actions.rules;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;

import com.predic8.membrane.core.rules.ForwardingRule;
import com.predic8.membrane.core.rules.ProxyRule;
import com.predic8.membrane.core.rules.Rule;
import com.predic8.plugin.membrane.dialogs.rule.ForwardingRuleEditDialog;
import com.predic8.plugin.membrane.dialogs.rule.ProxyRuleEditDialog;
import com.predic8.plugin.membrane.dialogs.rule.RuleEditDialog;

public class RuleEditAction extends Action {

	private Rule selectedRule;

	public RuleEditAction() {
		super();
		setText("Edit Rule");
		setId("Rule Edit Action");
	}

	@Override
	public void run() {
		
		try {
			if (selectedRule instanceof ForwardingRule) {
				openRuleDialog(new ForwardingRuleEditDialog(Display.getCurrent().getActiveShell()), (ForwardingRule) selectedRule);

			} else if (selectedRule instanceof ProxyRule) {
				openRuleDialog(new ProxyRuleEditDialog(Display.getCurrent().getActiveShell()), (ProxyRule) selectedRule);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void openRuleDialog(RuleEditDialog dialog, Rule rule) {
		if (dialog.getShell() == null) {
			dialog.create();
		}
		dialog.setInput(rule);
		dialog.open();
	}

	public void setSelectedRule(Rule selectedRule) {
		this.selectedRule = selectedRule;
	}
	
}
