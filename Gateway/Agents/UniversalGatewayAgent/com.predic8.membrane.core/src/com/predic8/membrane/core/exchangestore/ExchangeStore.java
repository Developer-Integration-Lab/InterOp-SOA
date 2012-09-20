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

package com.predic8.membrane.core.exchangestore;

import java.util.List;

import com.predic8.membrane.core.exchange.AbstractExchange;
import com.predic8.membrane.core.model.IExchangesStoreListener;
import com.predic8.membrane.core.rules.Rule;
import com.predic8.membrane.core.rules.RuleKey;
import com.predic8.membrane.core.statistics.RuleStatistics;

public interface ExchangeStore {
	
	
	public void addExchangesViewListener(IExchangesStoreListener viewer);	
	
	public void removeExchangesViewListener(IExchangesStoreListener viewer);	
	
	public void refreshExchangeStoreViewers();
	
	public void notifyListenersOnExchangeAdd(Rule rule, AbstractExchange exchange);
	
	public void notifyListenersOnExchangeRemoval(AbstractExchange exchange);
	
	public void notifyListenersOnRuleAdd(Rule rule);
	
	public void notifyListenersOnRuleRemoval(Rule rule, int rulesLeft);
	
	
	
	public void add(AbstractExchange exchange);
	
	public void remove(AbstractExchange exchange);

	public void removeAllExchanges(Rule rule);
	
	public void removeAllExchanges(AbstractExchange[] exchanges);
	
	public AbstractExchange[] getExchanges(RuleKey ruleKey);
	
	public int getNumberOfExchanges(RuleKey ruleKey);
	
	
	public RuleStatistics getStatistics(RuleKey ruleKey);
	
	
	public Object[] getAllExchanges();
	
	public List<AbstractExchange> getAllExchangesAsList();
	
	
}
