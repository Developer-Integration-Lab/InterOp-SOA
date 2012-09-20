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
package com.predic8.plugin.membrane.filtering;

import java.util.HashMap;
import java.util.Map;

import com.predic8.membrane.core.exchange.AbstractExchange;

public class FilterManager {

	private Map<Class<? extends ExchangesFilter>, ExchangesFilter> filters = new HashMap<Class<? extends ExchangesFilter>, ExchangesFilter>();
	
	public ExchangesFilter getFilterForClass(Class<? extends ExchangesFilter> clazz) {
		return filters.get(clazz);
	}
	
	public void addFilter(ExchangesFilter filter) {
		filters.put(filter.getClass(), filter);
	}
	
	public void removeFilter(Class<? extends ExchangesFilter> clazz) {
		filters.remove(clazz);
	}
	
	
	
	public boolean isEmpty() {
		return filters.size() == 0;
	}
	
	
	public boolean filter(AbstractExchange exc) {
		for (ExchangesFilter filter : filters.values()) {
			if (!filter.filter(exc)) {
				return false;
			}
		} 
		return true;
	}
	
	
	@Override
	public String toString() {
		if (isEmpty())
			return "are deactivated:   ";
		return "are activated:   ";
	}

	public void removeAllFilters() {
		filters.clear();
	}
}
