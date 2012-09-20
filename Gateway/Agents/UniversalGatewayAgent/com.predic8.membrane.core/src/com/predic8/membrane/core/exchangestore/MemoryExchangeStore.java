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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.predic8.membrane.core.exchange.AbstractExchange;
import com.predic8.membrane.core.exchange.ExchangeState;
import com.predic8.membrane.core.model.IExchangesStoreListener;
import com.predic8.membrane.core.rules.Rule;
import com.predic8.membrane.core.rules.RuleKey;
import com.predic8.membrane.core.statistics.RuleStatistics;

public class MemoryExchangeStore extends AbstractExchangeStore {

	private Map<RuleKey, List<AbstractExchange>> exchangesMap = new HashMap<RuleKey, List<AbstractExchange>>();

	//for synchronization purposes choose Vector class
	private List<AbstractExchange> totals = new Vector<AbstractExchange>();  
	
	public void add(AbstractExchange exc) {
		
		if (exc.getResponse() != null)
			return;
		
		if (isKeyInStore(exc)) {
			getKeyList(exc).add(exc);
		} else {
			List<AbstractExchange> list = new Vector<AbstractExchange>();
			list.add(exc);
			exchangesMap.put(exc.getRule().getKey(), list);
		}
		
		totals.add(exc);
		
		for (IExchangesStoreListener listener : exchangesStoreListeners) {
			exc.addExchangeStoreListener(listener);
			listener.addExchange(exc.getRule(), exc);
		}
	}

	private List<AbstractExchange> getKeyList(AbstractExchange exc) {
		return exchangesMap.get(exc.getRule().getKey());
	}

	private boolean isKeyInStore(AbstractExchange exc) {
		return exchangesMap.containsKey(exc.getRule().getKey());
	}

	public void remove(AbstractExchange exc) {
		removeWithoutNotify(exc);
		
		for (IExchangesStoreListener listener : exchangesStoreListeners) {
			listener.removeExchange(exc);
		}
	}
	
	private void removeWithoutNotify(AbstractExchange exc) {
		if (!isKeyInStore(exc)) {
			return;
		}

		getKeyList(exc).remove(exc);
		if (getKeyList(exc).isEmpty()) {
			exchangesMap.remove(exc.getRule().getKey());
		}
		totals.remove(exc);
		exc.informExchangeViewerOnRemoval();
	}


	public void removeAllExchanges(Rule rule) {
		AbstractExchange[] exchanges = getExchanges(rule.getKey());
		
		exchangesMap.remove(rule.getKey());
		totals.removeAll(Arrays.asList(exchanges));
		for (IExchangesStoreListener listener : exchangesStoreListeners) {
			listener.removeExchanges(rule, exchanges);
		}
	}

	public AbstractExchange[] getExchanges(RuleKey ruleKey) {
		List<AbstractExchange> exchangesList = exchangesMap.get(ruleKey);
		if (exchangesList == null) {
			return new AbstractExchange[0];
		}
		return exchangesList.toArray(new AbstractExchange[exchangesList.size()]);
	}

	public int getNumberOfExchanges(RuleKey ruleKey) {
		if (!exchangesMap.containsKey(ruleKey)) {
			return 0;
		}

		return exchangesMap.get(ruleKey).size();
	}

	public RuleStatistics getStatistics(RuleKey key) {
		RuleStatistics statistics = new RuleStatistics();
		statistics.setCountTotal(getNumberOfExchanges(key));
		List<AbstractExchange> exchangesList = exchangesMap.get(key);
		if (exchangesList == null || exchangesList.isEmpty())
			return statistics;

		int min = -1;
		int max = -1;
		long sum = 0;
		int count = 0;
		int errorCount = 0;
		long bytesSent = 0;
		long bytesReceived = 0;
		
		
		for (int i = 0; i < exchangesList.size(); i++) {
			if (exchangesList.get(i).getStatus() != ExchangeState.COMPLETED) {
				if (exchangesList.get(i).getStatus() == ExchangeState.FAILED)
					errorCount++;
				continue;
			}
			count++;
			int diff = (int) (exchangesList.get(i).getTimeResSent() - exchangesList.get(i).getTimeReqSent());
			sum += diff;
			if (min < 0 || diff < min) {
				min = diff;
			}

			if (diff > max) {
				max = diff;
			}
			
			try {
				bytesSent += exchangesList.get(i).getRequest().getBody().getLength();			
				bytesReceived += exchangesList.get(i).getResponse().getBody().getLength();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (count == 0)
			count++;

		statistics.setMin(min);
		statistics.setMax(max);
		statistics.setAvg(sum / count);
		statistics.setBytesSent(bytesSent);
		statistics.setBytesReceived(bytesReceived);
		return statistics;
	}

	public Object[] getAllExchanges() {
		if (totals.isEmpty()) 
			return null;
		
		return totals.toArray();
	}

	
	public List<AbstractExchange> getAllExchangesAsList() {
		return totals;
	}
	
	
	public void removeAllExchanges(AbstractExchange[] exchanges) {
		for (AbstractExchange exc : exchanges) {
			removeWithoutNotify(exc);
		}
		for (IExchangesStoreListener listener : exchangesStoreListeners) {
			listener.removeExchanges(exchanges);
		}
	}
	
}
