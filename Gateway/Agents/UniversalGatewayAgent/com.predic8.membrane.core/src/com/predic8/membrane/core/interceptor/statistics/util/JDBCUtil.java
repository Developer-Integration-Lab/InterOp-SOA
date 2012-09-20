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
/* skb 10/2011
 * introduce database persistence 
 * and transaction (request/response) recording
 */
package com.predic8.membrane.core.interceptor.statistics.util;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;
import javax.jms.DeliveryMode;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp.Headers.Send;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.predic8.membrane.core.Constants;
import com.predic8.membrane.core.exchange.AbstractExchange;
import com.predic8.membrane.core.exchange.ExchangesUtil;
import com.predic8.membrane.core.exchangestore.FileExchangeStore;
import com.predic8.membrane.core.exchangestore.MsgExchangeStore;
import com.predic8.membrane.core.http.Message;
import com.predic8.membrane.core.startup.ApplicationCachePreLoader;
import com.predic8.membrane.core.startup.dto.GatewayTag;

public class JDBCUtil {
	private static Log log = LogFactory.getLog(JDBCUtil.class
			.getName());
	
	public static final String SEQUENCE_STATISTIC = "stat_seq"; 
	public static final String TRIGGER_STATISTIC = "stat_seq_trigger"; 
	
	public static final String TABLE_NAME = "transaction"; //"statistic"

	public static final String ID = "id";
	
	public static final String STATUS_CODE = "statuscode";
	
	public static final String MSG_TYPE = "msgtype"; //skb
	
	public static final String TIME = "time";
	
	public static final String RULE = "rule";
	
	public static final String METHOD = "method";
	
	public static final String PATH = "path";
	
	public static final String CLIENT = "client";
	
	public static final String SERVER = "server";
	
	public static final String CONTENT_TYPE = "contenttype";
	
	public static final String CONTENT_LENGTH = "contentlength";
	
	public static final String DURATION = "duration";
	
	public static final String MSG_FILE_PATH = "msgfilepath";
	
	public static final String MSG_HEADER = "msgheader"; //skb
	
	public static final String MSG = "msg"; //skb
	
	public static final String SENDER_HCID = "sender"; //skb

	public static final String RECEIVER_HCID = "receiver"; //skb
	
	public static String getCreateTableStatementForOracle() {
		return getCreateTableStatement("id INT PRIMARY KEY");
	}
	
	public static String getCreateTableStatementForMySQL() {
		return getCreateTableStatement("id INT NOT NULL AUTO_INCREMENT PRIMARY KEY");
	}
	
	public static String getCreateTableStatementForDerby() {
		return getCreateTableStatement("id INT GENERATED ALWAYS AS IDENTITY");
	}
	
	public static String getCreateTableStatementForOther() {
		return getCreateTableStatement("id BIGINT NOT NULL PRIMARY KEY");
	}
	
	public static String getCreateTableStatement(String idPart) {
		return "CREATE TABLE " + TABLE_NAME + " ( " + idPart + ", " +

		STATUS_CODE + " INT, " +

		MSG_TYPE + " VARCHAR(64), " +
		
		TIME + " DATETIME, " +

		RULE + " VARCHAR(255), " +

		METHOD + " VARCHAR(50), " +

		PATH + " VARCHAR(1000), " +

		CLIENT + " VARCHAR(255), " +

		SERVER + " VARCHAR(255), " +

		SENDER_HCID + " VARCHAR(255), " +

		RECEIVER_HCID + " VARCHAR(255), " +
		
		CONTENT_TYPE + " VARCHAR(100), " +

		CONTENT_LENGTH + " INT, " +

		DURATION + " INT, " +

		MSG_FILE_PATH + " VARCHAR(255), " + 
		
		MSG_HEADER + " BLOB, " + 
		
		MSG + " LONGBLOB " + 
		
		")";
	}
	
	public static final String CREATE_SEQUENCE = "CREATE SEQUENCE " + SEQUENCE_STATISTIC;
	public static final String CREATE_TRIGGER = "CREATE TRIGGER " + TRIGGER_STATISTIC + " BEFORE INSERT ON " + TABLE_NAME + " FOR EACH ROW BEGIN IF (:new.id IS NULL) THEN SELECT " + SEQUENCE_STATISTIC + ".nextval INTO :new.id FROM DUAL; END IF; END; ";
	

	public static String getPreparedInsertStatement(boolean idGenerated){
		
		return "INSERT INTO " + TABLE_NAME + " ( " + getPreparedInsertIntro(idGenerated) +

		STATUS_CODE + "," +
		
		MSG_TYPE + "," + //skb

		TIME + "," +

		RULE + "," +

		METHOD + "," +

		PATH + "," +

		CLIENT + "," +

		SERVER + "," +

		SENDER_HCID + "," +

		RECEIVER_HCID + "," +

		CONTENT_TYPE + "," +

		CONTENT_LENGTH + "," +

		/*
		RESPONSE_CONTENT_TYPE + "," +

		RESPONSE_CONTENT_LENGTH + "," +
		 */
		
		DURATION + "," +

		MSG_FILE_PATH 	+ "," +

		MSG_HEADER  + "," +

		MSG   +
		
		
		") " +  getPreparedInsertProlog(idGenerated);
	}

	
	private static String getPreparedInsertIntro(boolean idGenerated) {
		if (idGenerated)
			return "";
		
		return ID + ",";
	}
	
	private static String getPreparedInsertProlog(boolean idGenerated) {
		String head = "VALUES(";
		String tail = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (idGenerated)
			return head + tail;
		
		return head + "?," + tail;
	}
	
	public static boolean isIdGenerated(DatabaseMetaData metaData) throws Exception {
		return isDerbyDatabase(metaData) || isMySQLDatabase(metaData) ||isOracleDatabase(metaData); 
	}


	public static void setData(AbstractExchange exc, PreparedStatement prepSt, boolean idGenerated, int flag, String tId, String[] gatewayHCIDs) throws SQLException {
		int startIndex = 0;
		if (!idGenerated) {
			UUID id = UUID.randomUUID();
			prepSt.setLong(++ startIndex, id.getLeastSignificantBits());
		}
		boolean isReq = (exc.getResponse()==null);
		
		log.info("Handling interceptor id is: "+tId);
		log.info((isReq)?"logging for request" :"logging for response");
		
		prepSt.setInt(++ startIndex,  (isReq)?200: exc.getResponse().getStatusCode() ) ;
		prepSt.setString(++ startIndex, (flag==0)?"REQUEST":"RESPONSE");		
		prepSt.setTimestamp(++ startIndex, new Timestamp(ExchangesUtil.getDate(exc).getTime()));//skb
		prepSt.setString(++ startIndex, exc.getRule().toString());
		prepSt.setString(++ startIndex, exc.getRequest().getMethod());
		prepSt.setString(++ startIndex, exc.getRequest().getUri());
		prepSt.setString(++ startIndex, (gatewayHCIDs!=null && !"".equals(gatewayHCIDs[2]))?gatewayHCIDs[2]:exc.getSourceHostname());
		prepSt.setString(++ startIndex, (gatewayHCIDs!=null && !"".equals(gatewayHCIDs[3]))?gatewayHCIDs[3]:exc.getServer());
		
		if (gatewayHCIDs!=null) {
			prepSt.setString(++ startIndex, gatewayHCIDs[0]); 
			prepSt.setString(++ startIndex, gatewayHCIDs[1]);						
		} else {
			prepSt.setString(++ startIndex, exc.getSourceHostname()); 
			prepSt.setString(++ startIndex, exc.getServer());			
		}

		prepSt.setString(++ startIndex, (isReq)?exc.getRequestContentType():exc.getResponseContentType());
		prepSt.setInt(++ startIndex, (isReq)?exc.getRequestContentLength():exc.getResponseContentLength());
		/*
		prepSt.setString(++ startIndex, (isReq)?null:exc.getResponseContentType());
		prepSt.setInt(++ startIndex, (isReq)?null:exc.getResponseContentLength());
		*/
		prepSt.setLong(++ startIndex, (isReq)?0:(exc.getTimeResReceived() - exc.getTimeReqSent()));
		
		prepSt.setString(++ startIndex, (String)getExProperty(exc, FileExchangeStore.MESSAGE_FILE_PATH));
		
		
		/* skb */
		String[] colList = {JDBCUtil.MSG_HEADER, JDBCUtil.MSG};
		
	   if (isReq) {	
		for (String col : colList) {
			log.info("processing col:" + col);			
			
			++ startIndex;
			try {
				byte[] os = (byte[])getExProperty(exc, col); 
				if (os!= null) {
					prepSt.setBytes(startIndex,os ); 	
				} else
					prepSt.setBytes(startIndex,null );			
				
			} catch (Exception ex) {
				prepSt.setBytes(startIndex,null );
			}			
		}
		} else {
	
			for (String col : colList) {
				log.info("processing col:" + col);
				
				++ startIndex;
				try {
					byte[] os = null; 
						if (col.equals(JDBCUtil.MSG)) {
							try {
								 os = IOUtils.toByteArray((exc.getResponse().getBodyAsStream()));	
							} catch (Exception ex) {
								log.info(ex.toString());
							}							
						} else if (col.equals(JDBCUtil.MSG_HEADER)) {
							Message msg2 = exc.getResponse();
							
							ByteArrayOutputStream header2 = new ByteArrayOutputStream();
							
							msg2.writeStartLine(header2);
							msg2.getHeader().write(header2);
							header2.write((Constants.CRLF).getBytes());
						
							os = header2.toByteArray();	
						}
					if (os!= null) {
						prepSt.setBytes(startIndex,os ); 	
					} else
						prepSt.setBytes(startIndex,null );			
					
				} catch (Exception ex) {
					prepSt.setBytes(startIndex,null );
				}			
			}
			
		}
	   
	
	}
	
  
	
	private static Object getExProperty(AbstractExchange exc, String pty) {
		if (pty!=null && exc.getProperty(pty) != null)
			return (Object)exc.getProperty(pty);
		
		return null;
	}

	public static boolean isOracleDatabase(DatabaseMetaData metaData) throws SQLException {
		return metaData.getDatabaseProductName().startsWith("Oracle");
	}
	
	public static boolean isMySQLDatabase(DatabaseMetaData metaData) throws SQLException {
		return metaData.getDatabaseProductName().startsWith("MySQL");
	}
	
	public static boolean isDerbyDatabase(DatabaseMetaData metaData) throws SQLException {
		return metaData.getDatabaseProductName().startsWith("Derby");
	}
	
	public static boolean tableExists(Connection con, String table) throws SQLException {
		DatabaseMetaData meta = con.getMetaData();
		ResultSet rs = meta.getTables("", null, table.toUpperCase(), null);
		return rs.next();
	}
	
}
