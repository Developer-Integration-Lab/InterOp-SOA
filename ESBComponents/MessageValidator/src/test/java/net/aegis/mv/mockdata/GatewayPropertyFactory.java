package net.aegis.mv.mockdata;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Loads the Candidate and Gateway candidateProperties from a candidateProperties file which will be used by the
 * mock data.
 *  
 * @author padmanabha.ketha
 *
 */
public class GatewayPropertyFactory {
	 
	private static final GatewayPropertyFactory factory = new GatewayPropertyFactory();
	private Candidate candidate;
	private List<GatewayServer> gatewayServers = new ArrayList<GatewayServer>();
	private Properties candidateProperties = new Properties();
	
	private GatewayPropertyFactory() {
		//Load properties
		loadProperties("testdata/candidate.properties");
		
		//Load override properties
		loadProperties("testdata/candidate.override.properties");
		
		//Stand up the Candidate and Server objects
		loadObjects();
		
	}

	public static GatewayPropertyFactory getInstance() {
		return factory;
	}
	
	public String getProperty(String name) {
		return candidateProperties.getProperty(name);
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public GatewayServer getServerByName(String name) {
		for(GatewayServer server : gatewayServers) {
			if(server.name.equals(name)) {
				return server;
			}
		}
		return null;
	}
			
	private void loadProperties(String file) {
		InputStream is = null;
		try {			
			URL uri = getClass().getClassLoader().getResource(file);
			if(uri != null) {
				is = uri.openStream();
				Properties props = new Properties();
				props.load(is);
				candidateProperties.putAll(props);
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		finally {
			try { if(is != null) {
					is.close();
				}
			}catch(Exception ex) {}
		}
	}
	
	private void loadObjects() {
		try {
			//Load Candidate object
			candidate = new Candidate(candidateProperties.getProperty("candidate.name"), 
										candidateProperties.getProperty("candidate.hcid"), 
										candidateProperties.getProperty("candidate.hostname"));
			
			//Load server Objects.
			int serverCount = Integer.parseInt(candidateProperties.getProperty("servercount"));		
			for(int i = 1; i <= serverCount ; i++) {
				String name = candidateProperties.getProperty("server." + i + ".name");
				String hcid = candidateProperties.getProperty("server." + i + ".hcid");
				String hostName = candidateProperties.getProperty("server." + i + ".hostname");
				gatewayServers.add(new GatewayServer(name, hcid, hostName));
			}
		}catch(Exception ex) {			
			throw new RuntimeException(ex);
		}
	}
		
	public class Candidate {		
		public String getName() {
			return name;
		}
		public String getHcid() {
			return hcid;
		}
		public String getHostName() {
			return hostName;
		}
		private Candidate(String name, String hcid, String hostName) {
			this.name = name;
			this.hcid = hcid;
			this.hostName = hostName;
		}
		private String name;
		private String hcid;
		private String hostName;
		
	}
	
	public class GatewayServer {
		private String name;
		private String hcid;
		private String hostName;
		public String getName() {
			return name;
		}
		public String getHcid() {
			return hcid;
		}
		public String getHostName() {
			return hostName;
		}

		private GatewayServer(String name, String hcid, String hostName) {
			this.name = name;
			this.hcid = hcid;
			this.hostName = hostName;
		}
	}
	
	public static void main(String[] args) {
		GatewayPropertyFactory factory = GatewayPropertyFactory.getInstance();
		Candidate candidate = factory.getCandidate();
		System.out.println("Candidate Details: \n==================");
		System.out.println("name: " + candidate.name + "; HCID: " + candidate.hcid + "; Host Name: " + candidate.hostName);
		
		GatewayServer server1 = factory.getServerByName("ri1");
		System.out.println("\nServer ri1 Details: \n====================");
		System.out.println("name: " + server1.name + "; HCID: " + server1.hcid + "; Host Name: " + server1.hostName);
		
		GatewayServer server2 = factory.getServerByName("ri2");
		System.out.println("\nServer ri2 Details: \n====================");
		System.out.println("name: " + server2.name + "; HCID: " + server2.hcid + "; Host Name: " + server2.hostName);
	}

}
