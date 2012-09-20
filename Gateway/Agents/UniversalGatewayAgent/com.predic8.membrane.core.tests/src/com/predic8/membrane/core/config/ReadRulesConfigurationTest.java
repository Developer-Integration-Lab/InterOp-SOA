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
package com.predic8.membrane.core.config;

import static org.junit.Assert.assertEquals;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.predic8.membrane.core.HttpRouter;
import com.predic8.membrane.core.Router;
import com.predic8.membrane.core.rules.ForwardingRule;
import com.predic8.membrane.core.rules.Rule;

public class ReadRulesConfigurationTest {

	private Router router;
	
	private List<Rule> rules;
	
	@Before
	public void setUp() throws Exception {
		router = new HttpRouter();
		router.getConfigurationManager().loadConfiguration("resources/rules.xml");
		rules = router.getRuleManager().getRules();
	}
	
	@Test
	public void testRulesSize() throws Exception {
	 	Assert.assertEquals(3, rules.size());
	}

	@Test
	public void testProxyRuleListenPort() throws Exception {
		Assert.assertEquals(9000, rules.get(0).getKey().getPort());
	}
	
	@Test
	public void testProxyRuleBlockRequest() throws Exception {
		Assert.assertEquals(true, rules.get(0).isBlockRequest());
	}
	
	@Test
	public void testProxyRuleBlockResponse() throws Exception {
		Assert.assertEquals(false, rules.get(0).isBlockResponse());
	}
	
	@Test
	public void testForwardingRuleListenPort() throws Exception {
		Assert.assertEquals(3000, ((ForwardingRule)rules.get(1)).getKey().getPort());
	}
	
	@Test
	public void testForwardingRuleTargetPort() throws Exception {
		Assert.assertEquals(80, ((ForwardingRule)rules.get(1)).getTargetPort());
	}
	
	@Test
	public void testForwardingRuleTargetHost() throws Exception {
		Assert.assertEquals("thomas-bayer.com", ((ForwardingRule)rules.get(1)).getTargetHost());
	}
	
	@Test
	public void testForwardingRuleDefaultMethod() throws Exception {
		Assert.assertEquals("*", ((ForwardingRule)rules.get(1)).getKey().getMethod());
	}
	
	@Test
	public void testForwardingRuleDefaultHost() throws Exception {
		Assert.assertEquals("*", ((ForwardingRule)rules.get(1)).getKey().getHost());
	}
	
	@Test
	public void testForwardingRuleBlockRequest() throws Exception {
		Assert.assertEquals(false, ((ForwardingRule)rules.get(1)).isBlockRequest());
	}
	
	@Test
	public void testForwardingRuleBlockResponse() throws Exception {
		Assert.assertEquals(true, ((ForwardingRule)rules.get(1)).isBlockResponse());
	}
	
	@Test
	public void testLocalForwardingRuleListenPort() throws Exception {
		Assert.assertEquals(2000, ((ForwardingRule)rules.get(2)).getKey().getPort());
	}
	
	@Test
	public void testLocalForwardingRuleTargetPort() throws Exception {
		assertEquals(8080, ((ForwardingRule)rules.get(2)).getTargetPort());
	}
	
	@Test
	public void testForwardingRuleMethodSet() throws Exception {
		assertEquals("GET", ((ForwardingRule)rules.get(2)).getKey().getMethod());
	}
	
	@Test
	public void testForwardingRuleHostSet() throws Exception {
		assertEquals("/abc/*", ((ForwardingRule)rules.get(2)).getKey().getHost());
	}
	
	@Test
	public void testLocalForwardingRuleInboundSSL() throws Exception {
		assertEquals(false, ((ForwardingRule)rules.get(2)).isInboundTLS());
	}
	
	@Test
	public void testLocalForwardingRuleOutboundSSL() throws Exception {
		assertEquals(false, ((ForwardingRule)rules.get(2)).isOutboundTLS());
	}
	
	@After
	public void tearDown() throws Exception {
		router.getTransport().closeAll();
	}

}
