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
package com.predic8.membrane.core.interceptor.balancer;

import com.predic8.membrane.core.exchange.AbstractExchange;


public class RoundRobinStrategy implements DispatchingStrategy {

	private int last = -1;
	
	public void done(AbstractExchange exc) {
		
	}

	public synchronized Node dispatch(LoadBalancingInterceptor interceptor) throws EmptyNodeListException { //TODO should be synchronized
		if (interceptor.getEndpoints().size() == 0 ) throw new EmptyNodeListException();
		
		last ++;
		if (last >= interceptor.getEndpoints().size())
			last = 0;
		
		return interceptor.getEndpoints().get(last);
	}

}
