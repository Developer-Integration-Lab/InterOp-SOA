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

package com.predic8.plugin.membrane.actions.views;

import org.eclipse.jface.action.Action;

import com.predic8.membrane.core.rules.Rule;
import com.predic8.plugin.membrane.PluginUtil;
import com.predic8.plugin.membrane.views.RuleDetailsView;

public class ShowRuleDetailsViewAction extends Action {

	private Rule selectedRule;

	public ShowRuleDetailsViewAction() {
		setText("Show Rule Details");
		setId("Show Rule Details Action");
	}

	public void run() {
		RuleDetailsView ruleView = (RuleDetailsView)PluginUtil.showView(RuleDetailsView.VIEW_ID);
		ruleView.setRuleToDisplay(selectedRule);
	}

	public void setSelectedRule(Rule selectedRule) {
		this.selectedRule = selectedRule;
	}

}
