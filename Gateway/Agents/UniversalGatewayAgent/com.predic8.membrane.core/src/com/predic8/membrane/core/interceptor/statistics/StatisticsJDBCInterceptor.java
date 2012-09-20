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
package com.predic8.membrane.core.interceptor.statistics;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.jms.DeliveryMode;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.util.InetAddressUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.predic8.membrane.core.exchange.Exchange;
import com.predic8.membrane.core.http.MimeType;
import com.predic8.membrane.core.http.Request;
import com.predic8.membrane.core.interceptor.AbstractInterceptor;
import com.predic8.membrane.core.interceptor.Outcome;
import com.predic8.membrane.core.interceptor.statistics.util.JDBCUtil;
import com.predic8.membrane.core.startup.ApplicationCachePreLoader;
import com.predic8.membrane.core.startup.dto.GatewayTag;

public class StatisticsJDBCInterceptor extends AbstractInterceptor {
	private static final int CNX_WAIT = 5000;

	private static Log log = LogFactory.getLog(StatisticsJDBCInterceptor.class.getName());
	
	private DataSource dataSource;	
	private PreparedStatement stat;	
	private boolean postMethodOnly = false;	
	private boolean soapOnly = false;	
	private boolean idGenerated;	
	private String statString;	
	private Connection con; 
	private ApplicationCachePreLoader appCache;
	
	//skb
    private static final String DEFAULT_BROKER_NAME = "tcp://localhost:61616";
    private static final String DEFAULT_USER_NAME = ActiveMQConnection.DEFAULT_USER;
    private static final String DEFAULT_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String DEFAULT_Queue = "Queue-Agent-MessageSink";
	
	
	public StatisticsJDBCInterceptor() {
		priority = 500;
	}
	
	public void init() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
			idGenerated = JDBCUtil.isIdGenerated(con.getMetaData());
			statString = JDBCUtil.getPreparedInsertStatement(idGenerated);
			createTableIfNecessary(con);
		} catch (Exception e) {
			throw new RuntimeException("Init for StatisticsJDBCInterceptor failed: " + e.getMessage());
		} finally {
			closeConnection(con);
		}
	}

	
	/* skb */
	@Override
	public Outcome handleRequest(Exchange exc) throws Exception {

		int retryAttempt = 0;
		do  {			
			if (con == null || con.isClosed())
				con = dataSource.getConnection();
			
			try {
				createPreparedStatement(con);
				saveExchange(con, exc, 0);
				if (retryAttempt>0) {
					log.info("retry success");
				}
			} catch (TransactionSaveException tse) {
				retryAttempt++;
				log.info("retry attempt "+ retryAttempt +" for a new connection in " + CNX_WAIT + "ms");
				Thread.sleep(CNX_WAIT);
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				con.close();
			}
			break;
		} while (retryAttempt<3);
		return Outcome.CONTINUE;
	}

	
	@Override
	public Outcome handleResponse(Exchange exc) throws Exception {

		int retryAttempt = 0;
		do  {				
			if (con == null || con.isClosed())
				con = dataSource.getConnection();		
			
			try {
				createPreparedStatement(con);
				saveExchange(con, exc, 1);
				if (retryAttempt>0) {
					log.info("retry success");
				}				
			} catch (TransactionSaveException tse) {				
					retryAttempt++;
					log.info("retry attempt "+ retryAttempt +" for a new connection in " + CNX_WAIT + "ms");
					Thread.sleep(CNX_WAIT);
					continue;
			} catch (Exception e) {
				e.printStackTrace();
				con.close();
			}
			break;
		} while (retryAttempt<3);
		return Outcome.CONTINUE;
	}
	
	/**
	 * see ILT-365 WBS reference code I.c.
	 * @param flag
	 * @param exc
	 * @return
	 */
	private String[] getGatewayHCID(final int flag,Exchange exc) {
		if (appCache!=null && exc!=null && exc.getSourceHostname()!=null) {
			String origin = null;
			String dest = null;
			
			
			if (flag==0) {//see ILT-365 WBS reference code I.c.i.
				origin = exc.getSourceHostname();
				dest =  exc.getServer();
			} else {//see ILT-365 WBS reference code I.c.ii.
				origin = exc.getServer();
				dest =  exc.getSourceHostname();				
			}

			String[] hcidDirection = new String[]{origin,dest,"",""};	
			 final GatewayTag gTagOrigin = appCache.getGatewayTagMap(origin);
			 final GatewayTag gTagDest = appCache.getGatewayTagMap(dest);
			 if (gTagOrigin!=null && gTagOrigin.getHCID()!=null) {
				 hcidDirection[0] = gTagOrigin.getHCID();
			 }
			 if (gTagDest!=null && gTagDest.getHCID()!=null) {
				 hcidDirection[1] = gTagDest.getHCID();
			 }
			 
			 if (gTagOrigin != null && gTagDest != null) {
				if (flag==0) {
						 hcidDirection[2] = gTagOrigin.getGatewayAddress();
						 hcidDirection[3] = gTagDest.getGatewayAddress(); 
	
				 } else {				
						 hcidDirection[3] = gTagOrigin.getGatewayAddress();
						 hcidDirection[2] = gTagDest.getGatewayAddress(); 
					 				 
				 }				 
			 }
			 
			 return hcidDirection;
		} else {
			log.error("Error in resolving HCIDs: appCache or exchange error!");
		}
		return null;
	}
	
	/**
	 * saveExchange
	 * skb
	 * @param con
	 * @param exc
	 * @param flag
	 * @throws Exception
	 */
	private void saveExchange(Connection con, Exchange exc, int flag) throws Exception {
		if ( ignoreGetMethod(exc) ) return;
		if ( ignoreNotSoap(exc) ) return;


		JDBCUtil.setData(exc, stat, idGenerated, flag, "", getGatewayHCID(flag, exc));
		
		int rc = 0;
		try {
			rc = stat.executeUpdate();	
		} catch (Exception ex) {
			log.error("Transaction saveExchange failed!" +ex.toString());
			throw new TransactionSaveException();
		}			
		
		if (rc>0) {
			   ResultSet rs = stat.getGeneratedKeys();
			   try {
					
			   if (rs!=null && rs.next()) {
		            sendMessage(DEFAULT_BROKER_NAME, DEFAULT_USER_NAME, DEFAULT_PASSWORD, DEFAULT_Queue, rs.getInt(1));
		        }
			   } catch (Exception ex) {
				   log.error("sendMessage Queue notification failed! Transaction record "+ rs.getInt(1) + " error: "+ex.toString());
			   }
		        try {
		        	rs.close();
		        } catch (Exception ex) {
		        	;
		        }
		} else {
			log.error("Alert: No transaction DB record written!");
		} 
			
	}

	/*
	 * skb
	 */
	private static void sendMessage(String broker, String username, String password, String sQueue, int transactionId) {
	       log.info("\n *************** Enter Agent's sendMessage()\n");
	        javax.jms.QueueConnection connect = null;
	        javax.jms.QueueSession session = null;
	        javax.jms.QueueSender sender = null;
	        try {
	            // Create a JMS connection
	            javax.jms.QueueConnectionFactory factory;
	            factory = new ActiveMQConnectionFactory(username, password, broker);
	            connect = factory.createQueueConnection(username, password);
	            session = connect.createQueueSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
	            // Create the Queue and QueueSender for sending requests.
	            javax.jms.Queue queue = null;
	            queue = session.createQueue(sQueue);
	            sender = session.createSender(queue);

	            // Now that all setup is complete, start the Connection and send the message.
	            connect.start();
	            log.info("\n *************** Before inserting into the queue for transactionId:" + transactionId);
	            if (transactionId>0) {
	                javax.jms.MapMessage mapMsg = session.createMapMessage();
	                mapMsg.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
	                mapMsg.setObject("transactionId", transactionId);
	               
	                sender.send(mapMsg);
	            }
	            log.info("\n *************** Exit Agent's sendMessage()\n");
	        } catch (Exception ex) {	            
	        	log.info("\n *************** Error inserting into the queue \n");
	        	log.error(ex.toString());
	                        
	        } 
	    }

	
	private boolean ignoreNotSoap(Exchange exc) {
		return soapOnly && 
			 !MimeType.SOAP.equals(exc.getRequestContentType()) &&
			 !MimeType.XML.equals(exc.getRequestContentType());
	}

	private boolean ignoreGetMethod(Exchange exc) {
		return postMethodOnly && !Request.METHOD_POST.equals(exc.getRequest().getMethod());
	}
	
	private void closeConnection(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTableIfNecessary(Connection con) throws Exception {
		if (JDBCUtil.tableExists(con, JDBCUtil.TABLE_NAME))
			return;

		Statement st = con.createStatement();
		
		if (JDBCUtil.isOracleDatabase(con.getMetaData())) {
			st.execute(JDBCUtil.getCreateTableStatementForOracle());
			st.execute(JDBCUtil.CREATE_SEQUENCE);
			st.execute(JDBCUtil.CREATE_TRIGGER);
		} else if (JDBCUtil.isMySQLDatabase(con.getMetaData())) {
			st.execute(JDBCUtil.getCreateTableStatementForMySQL());
		} else if (JDBCUtil.isDerbyDatabase(con.getMetaData())) {
			st.execute(JDBCUtil.getCreateTableStatementForDerby());
		} else {
			st.execute(JDBCUtil.getCreateTableStatementForOther());
		}
	}

	/*
	 * skb
	 */
	private void createPreparedStatement(Connection con) throws Exception {
		if (stat == null || stat.getConnection().isClosed() )
			stat = con.prepareStatement(statString, 
					Statement.RETURN_GENERATED_KEYS
					);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean isPostMethodOnly() {
		return postMethodOnly;
	}

	public void setPostMethodOnly(boolean postMethodOnly) {
		this.postMethodOnly = postMethodOnly;
	}
	
	public boolean isSoapOnly() {
		return soapOnly;
	}

	public void setSoapOnly(boolean soapOnly) {
		this.soapOnly = soapOnly;
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
