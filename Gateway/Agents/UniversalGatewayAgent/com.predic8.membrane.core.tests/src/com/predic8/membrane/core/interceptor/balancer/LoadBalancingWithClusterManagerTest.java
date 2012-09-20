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

import static com.predic8.membrane.core.util.URLUtil.createQueryString;
import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.params.HttpProtocolParams;
import org.junit.Test;

import com.predic8.membrane.core.HttpRouter;
import com.predic8.membrane.core.http.Header;
import com.predic8.membrane.core.rules.ForwardingRule;
import com.predic8.membrane.core.rules.ForwardingRuleKey;
import com.predic8.membrane.core.services.DummyWebServiceInterceptor;

public class LoadBalancingWithClusterManagerTest {

	private HttpRouter lb;

	@Test 
	public void nodesTest() throws Exception {
		DummyWebServiceInterceptor service1 = startNode(2000);
		DummyWebServiceInterceptor service2 = startNode(3000);
		DummyWebServiceInterceptor service3 = startNode(4000);
		
		startLB();
		
		sendNotification("up", 2000);
		sendNotification("up", 3000);
				
		assertEquals(200, post("/getBankwithSession555555.xml"));// goes to service one
		assertEquals(1, service1.counter);
		assertEquals(0, service2.counter);

		assertEquals(200, post("/getBankwithSession555555.xml"));// goes to service 1 again
		assertEquals(2, service1.counter);
		assertEquals(0, service2.counter);

		assertEquals(200, post("/getBankwithSession444444.xml")); // goes to service 2
		assertEquals(2, service1.counter);
		assertEquals(1, service2.counter);
		
		sendNotification("down", 2000);

		assertEquals(200, post("/getBankwithSession555555.xml")); // goes to service 2 because service 1 is down
		assertEquals(2, service1.counter);
		assertEquals(2, service2.counter);

		sendNotification("up", 4000);

		assertEquals(0, service3.counter);
		
		assertEquals(200, post("/getBankwithSession666666.xml")); // goes to service 3
		assertEquals(2, service1.counter);
		assertEquals(2, service2.counter);
		assertEquals(1, service3.counter);
		
		assertEquals(200, post("/getBankwithSession555555.xml")); // goes to service 2
		assertEquals(200, post("/getBankwithSession444444.xml")); // goes to service 2
		assertEquals(200, post("/getBankwithSession666666.xml")); // goes to service 3
		assertEquals(2, service1.counter);
		assertEquals(4, service2.counter);
		assertEquals(2, service3.counter);
		
		lb.getTransport().closeAll();
	}

	private void startLB() throws IOException {

		LoadBalancingInterceptor lbi = new LoadBalancingInterceptor();
		XMLElementSessionIdExtractor extractor = new XMLElementSessionIdExtractor();
		extractor.setLocalName("session");
		extractor.setNamespace("http://predic8.com/session/");
		lbi.setSesssionIdExtractor(extractor);

		ForwardingRule lbiRule = new ForwardingRule(new ForwardingRuleKey("localhost", "*", ".*", 5000), "thomas-bayer.com", 80);
		lbiRule.getInterceptors().add(lbi);
		
		ClusterNotificationInterceptor cni = new ClusterNotificationInterceptor();
		
		ForwardingRule cniRule = new ForwardingRule(new ForwardingRuleKey("localhost", "*", ".*", 6000), "thomas-bayer.com", 80);
		cniRule.getInterceptors().add(cni);
		
		lb = new HttpRouter();
		lbi.setRouter(lb);
		cni.setRouter(lb);
		lb.setClusterManager(new ClusterManager());
		lb.getRuleManager().addRuleIfNew(lbiRule);
		lb.getRuleManager().addRuleIfNew(cniRule);
	}

	private DummyWebServiceInterceptor startNode(int port) throws IOException {
		HttpRouter node1 = new HttpRouter();
		DummyWebServiceInterceptor service1 = new DummyWebServiceInterceptor();
		node1.getTransport().getInterceptors().add(service1);
		node1.getRuleManager().addRuleIfNew(new ForwardingRule(new ForwardingRuleKey("localhost", "POST", ".*", port), "thomas-bayer.com", 80));
		return service1;
	}

	private HttpClient getClient() {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpProtocolParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		return client;
	}

	private PostMethod getPostMethod(String request) {
		PostMethod post = new PostMethod("http://localhost:5000/axis2/services/BLZService");
		post.setRequestEntity(new InputStreamRequestEntity(this.getClass().getResourceAsStream(request)));
		post.setRequestHeader(Header.CONTENT_TYPE, "text/xml;charset=UTF-8");
		post.setRequestHeader(Header.SOAP_ACTION, "");

		return post;
	}
	
	private void sendNotification(String cmd, int port) throws UnsupportedEncodingException, IOException,
			HttpException {
		PostMethod post = new PostMethod("http://localhost:6000/clustermanager/"+cmd+"?"+
				   createQueryString("host", "localhost",
						   			 "port", String.valueOf(port)));
		new HttpClient().executeMethod(post);
	}
	
	private int post(String req) throws IOException, HttpException {
		return getClient().executeMethod(getPostMethod(req));
	}
	
}
