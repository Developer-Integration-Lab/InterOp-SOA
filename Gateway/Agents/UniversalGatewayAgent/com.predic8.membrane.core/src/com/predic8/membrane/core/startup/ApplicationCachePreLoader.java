/**
 * 
 */
package com.predic8.membrane.core.startup;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

import com.predic8.membrane.core.util.IP;
import com.predic8.membrane.core.Constants;
import com.predic8.membrane.core.startup.dto.GatewayTag;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Sunil.Bhaskarla
 * see ILT-365 WBS reference code I.b.
 * 
 */
public class ApplicationCachePreLoader {

	private static final String SPLITTER_DOT = "\\.";


	private enum PreLoaderState {
		 ERROR, UNINITIALIZED, INITIALIZED
	}
	private static Log log = LogFactory.getLog(ApplicationCachePreLoader.class.getName());
	private static final Map<String, GatewayTag> gatewayList = new HashMap<String,GatewayTag>();
	private static DataSource dataSource;	
	private static String gatewayTagDataSetDef;
	private static PreLoaderState preLoaderState = PreLoaderState.UNINITIALIZED;

    
	public String getGatewayHostName(String ipAddress) {
		
		if (ipAddress==null) {
			log.error("getGatewayHostName: improper ip lookup request:"+ipAddress);
			return null;
		}
		
		IP ip = new IP();
		for (String key : gatewayList.keySet()) {
			if (ipAddress.equals(ip.getIP(key))) {
				return key;
			}
		}
		
		log.error("getGatewayHostName: ip lookup request not found:"+ipAddress);
		return null;
	}
	
	public GatewayTag getGatewayTagMap(String gatewayAddress) {
		if (gatewayAddress!=null)
			gatewayAddress = gatewayAddress.toLowerCase();
		
		if (preLoaderState==PreLoaderState.INITIALIZED) {
			GatewayTag gTag = gatewayList.get(gatewayAddress);
			
			if (gTag==null) {//DNSCache.java: canonical returned short name
				for (String key : gatewayList.keySet()) {
					String[] fqdn = key.split(SPLITTER_DOT);
					if (fqdn.length>1) {
						if (fqdn[0].equalsIgnoreCase(gatewayAddress)) {
							return gatewayList.get(key);
						} 
					} else {
						log.error("Setup error: Gateway view does not have a FQDN host name:"+key);
					}					
				}
					
			} else 
				return gTag;
		} 
		
		log.error("getGatewayTagMap lookup failed for "+gatewayAddress);
		return null;
    }
    
	
	
	
	/*
	 * init() method
	 * see ILT-365 WBS reference code I.b.i.
	 */
	public static void init() {
		log.info("Enter init() for ApplicationCachePreLoader"); 
		Connection con = null;
		
		if (preLoaderState==PreLoaderState.UNINITIALIZED) {			
			try {
				con = dataSource.getConnection();
	
				 Statement stmt = con.createStatement();
				    ResultSet rs = stmt.executeQuery(getGatewayTagDataSetDef());
				    while (rs.next()) {
				    	String gatewayAddress = rs.getString("gatewayAddress");
				        if (gatewayAddress!=null && !"".equals(gatewayAddress)){
				        	GatewayTag gTag = new GatewayTag();
				        	gTag.setGatewayAddress(gatewayAddress);
					    	gTag.setHostedBy(rs.getString("hostedBy"));
					        gTag.setHCID(rs.getString("HCID"));
				        	gatewayList.put(gatewayAddress, gTag);			        	
				        	log.debug("Loaded gatewayTagDataSetDef for gatewayAddress:"+ gatewayAddress);		        		
				        }			        
				    }
				
			} catch (Exception ex) {
				preLoaderState = PreLoaderState.ERROR;
				throw new RuntimeException("Init for GatewayTagMap@"+ ApplicationCachePreLoader.class.getName() +" failed: " + ex.getMessage());
			} finally {
				if (preLoaderState!=PreLoaderState.ERROR && preLoaderState==PreLoaderState.UNINITIALIZED)
					preLoaderState = PreLoaderState.INITIALIZED;
				closeConnection(con);
			}
		}
		log.info("preLoader state is:"+preLoaderState);
		log.info("Exit init() for ApplicationCachePreLoader");
    }

	protected DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	private static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected static String getGatewayTagDataSetDef() {
		return gatewayTagDataSetDef;
	}


	public void setGatewayTagDataSetDef(String gatewayTagDataSetDef) {
		this.gatewayTagDataSetDef = gatewayTagDataSetDef;
	}






}
