package com.predic8.membrane.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IP {
	private static Log log = LogFactory.getLog(IP.class.getName());
	
		 public String getIP(String hostname) {
			  try {
			   String ipaddr = InetAddress.getByName(hostname).getHostAddress() ;
			   return ipaddr ;
			   }
			  catch(UnknownHostException uhe) {
				  log.error("IP: Unknown host:"+hostname);
			  }
			  return null;
		 }		
}

