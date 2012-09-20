/* Copyright 2011 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
package com.predic8.membrane.core.interceptor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.predic8.membrane.core.Router;
import com.predic8.membrane.core.http.Header;
import com.predic8.membrane.core.rules.ForwardingRule;
import com.predic8.membrane.core.rules.ForwardingRuleKey;
import com.predic8.membrane.core.rules.Rule;

public class RegExReplaceInterceptorTest {

	private Router router;
	
	@Before
	public void setUp() throws Exception {
		router = Router.init("resources/regex-monitor-beans.xml");
		Rule serverRule = new ForwardingRule(new ForwardingRuleKey("localhost", "*", ".*", 7000), "predic8.de", 80);
		router.getRuleManager().addRuleIfNew(serverRule);
	}
	
	@After
	public void tearDown() throws Exception {
		router.getTransport().closeAll();
	}
	
	@Test
	public void testReplace() throws Exception {
		HttpClient client = new HttpClient();
		
		GetMethod method = new GetMethod("http://localhost:7000");
		method.setRequestHeader(Header.CONTENT_TYPE, "text/xml;charset=UTF-8");
		method.setRequestHeader(Header.SOAP_ACTION, "");
		
		assertEquals(200, client.executeMethod(method));
		
		assertTrue(new String(method.getResponseBody()).contains("Membrane RegEx Replacement Is Cool"));
	}
	
}
