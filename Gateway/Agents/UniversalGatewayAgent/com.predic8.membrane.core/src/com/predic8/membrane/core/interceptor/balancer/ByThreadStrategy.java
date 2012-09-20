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

import java.util.*;

import com.predic8.membrane.core.exchange.AbstractExchange;

public class ByThreadStrategy implements DispatchingStrategy {

	private int maxNumberOfThreadsPerEndpoint = 5;

	private Map<String, Integer> endpointCount = new Hashtable<String, Integer>();

	private int retryTimeOnBusy = 1000;

	public void done(AbstractExchange exc) {
		String endPoint = exc.getOriginalRequestUri();
		if (endpointCount.containsKey(endPoint)) {
			Integer counter = endpointCount.get(endPoint);
			counter--;
			if (counter == 0) {
				endpointCount.remove(endPoint);
			} else {
				endpointCount.put(endPoint, counter);
			}
		}
	}

	public Node dispatch(LoadBalancingInterceptor interceptor) {
		for (int j = 0; j < 5; j++) {
			for (Node ep : interceptor.getEndpoints()) {
				if (!endpointCount.containsKey(getHostColonPort(ep))) {
					endpointCount.put(getHostColonPort(ep), 1);
					return ep;
				}

				Integer counter = endpointCount.get(ep);
				if (counter < maxNumberOfThreadsPerEndpoint) {
					counter++;
					endpointCount.put(getHostColonPort(ep), counter);
					return ep;
				} else {
					continue;
				}

			}
			try {
				Thread.sleep(retryTimeOnBusy);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		throw new RuntimeException("All available servers are busy.");
	}

	public void setMaxNumberOfThreadsPerEndpoint(int maxNumberOfThreadsPerEndpoint) {
		this.maxNumberOfThreadsPerEndpoint = maxNumberOfThreadsPerEndpoint;
	}

	public void setRetryTimeOnBusy(int retryTimeOnBusy) {
		this.retryTimeOnBusy = retryTimeOnBusy;
	}
	
	private String getHostColonPort(Node ep) {
		return ep.getHost()+":"+ep.getPort();
	}
	

}
