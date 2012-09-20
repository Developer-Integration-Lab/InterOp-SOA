package com.predic8.membrane.core.util;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.predic8.membrane.core.interceptor.statistics.util.JDBCUtil;
import com.predic8.membrane.core.startup.ApplicationCachePreLoader;

/**
 * See InetAddress Caching of InetAddress class
 * 
 * Java 1.5 implementation of InetAddress Caching differs from Java 1.6 one.  
 * 
 * 
 * @author predic8
 *
 */
public class DNSCache {
	private Map<InetAddress, String> hostNames = new Hashtable<InetAddress, String>();	
	private Map<InetAddress, String> hostAddresses = new Hashtable<InetAddress, String>();
	private ApplicationCachePreLoader appCache;
	
	public String getHostName(InetAddress address) {
		
		if (address==null) return "";
		
		if (hostNames.containsKey(address))
			return hostNames.get(address);
	
		//override above with gateway cache lookup
		if (appCache!=null) {
			String hostName = appCache.getGatewayHostName(address.getHostAddress());
			if (hostName!=null && !"".equals(hostName)) {
				hostNames.put(address, hostName);
			}		
			//String hostName = address.getCanonicalHostName().toLowerCase();
			//hostNames.put(address, hostName);
			
		
			return hostName;
			
		}
		return address.getHostAddress();
	}
	
	public String getHostAddress(InetAddress address) {
		if (hostAddresses.containsKey(address))
			return hostAddresses.get(address);
		
		String hostAddress = address.getHostAddress();
		hostAddresses.put(address, hostAddress);
		return hostAddress;
	}
	
	public Collection<String> getCachedHostNames() {
		return hostNames.values();
	}
	
	public Collection<String> getCachedHostAddresses() {
		return hostAddresses.values();
	}

	/**
	 * @return the appCache
	 */
	public ApplicationCachePreLoader getAppCache() {
		return appCache;
	}

	/**
	 * @param appCache the appCache to set
	 */
	public void setAppCache(ApplicationCachePreLoader appCache) {
		this.appCache = appCache;
	}
	
}
