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

package com.predic8.membrane.core.transport.http;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.predic8.membrane.core.Constants;
import com.predic8.membrane.core.config.Proxy;
import com.predic8.membrane.core.exchange.Exchange;
import com.predic8.membrane.core.http.Request;
import com.predic8.membrane.core.http.Response;
import com.predic8.membrane.core.rules.ForwardingRule;
import com.predic8.membrane.core.util.EndOfStreamException;
import com.predic8.membrane.core.util.HttpUtil;

public class HttpClient {

	private static Log log = LogFactory.getLog(HttpClient.class.getName());
	
	private int timeBetweenTries = 250;

	private int maxRetries = 5;
	
	private ConnectionManager conMgr = new ConnectionManager();
	
	private boolean adjustHostHeader;
	
	private Proxy proxy;
	
	private InetAddress host;
	
	private int port;
	
	private boolean tls;
	
	private String localHost;
	
	private boolean useProxy() {
		if (proxy == null)
			return false;
		return proxy.useProxy();
	}
	
	private void setRequestURI(Request req, String dest) throws MalformedURLException {
		if (useProxy() || req.isCONNECTRequest())
			req.setUri(dest);
		else
			req.setUri(HttpUtil.getPathAndQueryString(dest));
	}
	
	private void setHostAndPort(boolean connect, String dest) throws MalformedURLException, UnknownHostException {
		if (useProxy()) {
			port = proxy.getProxyPort();
			setHost(proxy.getProxyHost());
			return;
		}
		
		if (connect) {
			HostColonPort hcp = new HostColonPort(dest);
			port = hcp.port;
			setHost(hcp.host);
			return;
		} 
		
		URL destination = new URL(dest);
		port = HttpUtil.getPort(destination);
		setHost(destination.getHost());
		
	}
	
	private void init(Exchange exc, String dest) throws UnknownHostException, IOException, MalformedURLException {
		setRequestURI(exc.getRequest(), dest);
		setHostAndPort(exc.getRequest().isCONNECTRequest(), dest);
		
		if (useProxy() && proxy.isUseAuthentication()) {
			exc.getRequest().getHeader().setProxyAutorization(proxy.getCredentials());
		} 
		
		tls = getOutboundTLS(exc);
		localHost = exc.getRule().getLocalHost();
		
		if (adjustHostHeader && exc.getRule() instanceof ForwardingRule) {
			URL destination = new URL(dest); //duplicate
			exc.getRequest().getHeader().setHost(destination.getHost() + ":" + port);
		}
	}

	private boolean getOutboundTLS(Exchange exc) {
		return exc.getRule().isOutboundTLS();
	}

	public Response call(Exchange exc) throws Exception {
		if (exc.getDestinations().size() == 0)
			throw new IllegalStateException("List of destinations is empty. Please specify at least one destination.");
		
		int counter = 0;
		Exception exception = null;
		while (counter < maxRetries) {
			Connection con = null;
			String dest = getDestination(exc, counter);
			try {
				log.debug("try # " + counter + " to " + dest);
				init(exc, dest);
				con = conMgr.getConnection(host, port, localHost, tls);
				exc.setTargetConnection(con);
				return doCall(exc, con);
				// java.net.SocketException: Software caused connection abort: socket write error
			} catch (ConnectException e) {
				exception = e;
				log.info("Connection to " + dest + " on port " + port + " refused.");
			} catch(SocketException e){
				if ( e.getMessage().contains("Software caused connection abort")) {
					log.info("Connection to " + dest + "was aborted externally. Maybe by the server or the OS Membrane is running on.");
				} else if (e.getMessage().contains("Connection reset") ) {
					log.info("Connection to " + dest + "was reset externally. Maybe by the server or the OS Membrane is running on.");
 				} else {
 					logException(exc, counter, e);
 				}
				exception = e;
			} catch (UnknownHostException e) {
				log.info("Unknown host: " + host);
				exception = e;
				if (exc.getDestinations().size() < 2) {
					break; 
				}
			} catch (ErrorReadingStartLineException e) {
				log.info("Server connection to " + dest + " terminated before start line was read. Start line so far: " + e.getStartLine());
				exception = e;
			} catch (Exception e) {
				logException(exc, counter, e);
				exception = e;
			}
			counter++;
			closeConnection(con);
			Thread.sleep(timeBetweenTries);
		}
		throw exception;
	}

	private String getDestination(Exchange exc, int counter) {
		return exc.getDestinations().get(counter % exc.getDestinations().size());
	}

	private void closeConnection(Connection con) {
		try {
			close(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void logException(Exchange exc, int counter, Exception e) throws IOException {
		log.debug("try # " + counter + " failed");
		exc.getRequest().writeStartLine(System.out);
		exc.getRequest().getHeader().write(System.out);
		e.printStackTrace();
	}

	private Response doCall(Exchange exc, Connection con) throws IOException, SocketException, EndOfStreamException {
		if (exc.getRequest().isCONNECTRequest()) {
			handleConnectRequest(exc, con);
			return Response.createOKResponse();
		}
		
		exc.getRequest().write(con.out);
		exc.setTimeReqSent(System.currentTimeMillis());
		
		if (exc.getRequest().isHTTP10()) {
			shutDownSourceSocket(exc, con);
		}

		Response res = new Response();
		res.read(con.in, !exc.getRequest().isHEADRequest());

		if (res.getStatusCode() == 100) {
			do100ExpectedHandling(exc, res, con);
		}

		exc.setReceived();
		exc.setTimeResReceived(System.currentTimeMillis());
		return res;
	}

	private void handleConnectRequest(Exchange exc, Connection con) throws IOException, EndOfStreamException {
		if (useProxy()) {
			log.debug("host: " + host);
			log.debug("port: " + port);
			
			exc.getRequest().write(con.out);
			Response response = new Response();
			response.read(con.in, false);
			log.debug("Status code response on CONNECT request: " + response.getStatusCode());
		}
		exc.getRequest().setUri(Constants.N_A);
		new TunnelThread(con.in, exc.getServerThread().getSrcOut(), "Onward Thread").start();
		new TunnelThread(exc.getServerThread().getSrcIn(), con.out, "Backward Thread").start();
	}

	private void do100ExpectedHandling(Exchange exc, Response response, Connection con) throws IOException, EndOfStreamException {
		response.write(exc.getServerThread().srcOut);
		exc.getRequest().readBody();
		exc.getRequest().getBody().write(con.out);
		response.read(con.in, !exc.getRequest().isHEADRequest());
	}

	private void shutDownSourceSocket(Exchange exc, Connection con) throws IOException {
		//SSLSocket does not implement shutdown input and output
		if (!(exc.getServerThread().sourceSocket instanceof SSLSocket))
			exc.getServerThread().sourceSocket.shutdownInput();
		
		if (!con.socket.isOutputShutdown() && !(con.socket instanceof SSLSocket)) {
			log.info("Shutting down socket outputstream");
			con.socket.shutdownOutput();
		}
		// TODO close ?
	}

	public void close(Connection con) throws IOException {
		if (con == null)
			return;
		
		con.close();
	}
	
	public void setAdjustHostHeader(boolean adjustHostHeader) {
		this.adjustHostHeader = adjustHostHeader;
	}
	
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
	public void setHost(String hostname) throws UnknownHostException {
		host = InetAddress.getByName(hostname);
	}

	public int getTimeBetweenTries() {
		return timeBetweenTries;
	}

	public void setTimeBetweenTries(int timeBetweenTries) {
		this.timeBetweenTries = timeBetweenTries;
	}

	public int getMaxTries() {
		return maxRetries;
	}

	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}		
}
