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

package com.predic8.membrane.core;

import java.util.ArrayList;
import java.util.List;

import com.predic8.membrane.core.exchangestore.ForgetfulExchangeStore;
import com.predic8.membrane.core.interceptor.DispatchingInterceptor;
import com.predic8.membrane.core.interceptor.Interceptor;
import com.predic8.membrane.core.interceptor.RuleMatchingInterceptor;
import com.predic8.membrane.core.io.ConfigurationFileStore;
import com.predic8.membrane.core.transport.http.HttpTransport;

public class HttpRouter extends Router {

	public HttpRouter() {
		ruleManager = new RuleManager();
		ruleManager.setRouter(this);
		exchangeStore = new ForgetfulExchangeStore();
		transport = new HttpTransport();
		transport.setRouter(this);
		configurationManager = new ConfigurationManager();
		configurationManager.setConfigurationStore(new ConfigurationFileStore());
		configurationManager.setRouter(this);
		List<Interceptor> interceptors = new ArrayList<Interceptor>();
		RuleMatchingInterceptor ruleMatcher = new RuleMatchingInterceptor();
		ruleMatcher.setRouter(this);
		
		interceptors.add(ruleMatcher);
		
		interceptors.add(new DispatchingInterceptor());
		
		transport.setInterceptors(interceptors);
	}
	
	@Override
	public HttpTransport getTransport() {
		return (HttpTransport)transport;
	}
	
}
