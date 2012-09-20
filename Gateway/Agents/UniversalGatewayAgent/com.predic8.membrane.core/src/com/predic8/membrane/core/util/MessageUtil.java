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
package com.predic8.membrane.core.util;

import com.predic8.membrane.core.Constants;
import com.predic8.membrane.core.http.Request;
import com.predic8.membrane.core.http.Response;

public class MessageUtil {

	public static Request getGetRequest(String uri) {
		Request req = getStandartRequest(Request.METHOD_GET);
		req.setUri(uri);
		return req;
	}
	
	public static Request getPostRequest(String uri) {
		Request req = getStandartRequest(Request.METHOD_POST);
		req.setUri(uri);
		return req;
	}
	
	public static Request getDeleteRequest(String uri) {
		Request req = getStandartRequest(Request.METHOD_DELETE);
		req.setUri(uri);
		return req;
	}
	
	private static Request getStandartRequest(String method) {
		Request request = new Request();
		request.setMethod(method);
		request.setVersion(Constants.HTTP_VERSION_11);
		
		return request;
	}
	
	public static Response getOKResponse(byte[] content, String contentType) {
		Response res = new Response();
		res.setBodyContent(content);
		res.setVersion("1.1");
		res.setStatusCode(200);
		res.setStatusMessage("OK");
		res.getHeader().setContentType(contentType);
		
		return res;
	}

	public static Response getEmptyResponse(int statusCode, String statusMessage) {
		Response res = new Response();
		res.setStatusCode(statusCode);
		res.setStatusMessage(statusMessage);
		return res;
	}
	
}
