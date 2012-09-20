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

package com.predic8.membrane.core.exchangestore;

import groovy.transform.Synchronized;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.*;

import com.predic8.io.IOUtil;
import com.predic8.membrane.core.Constants;
import com.predic8.membrane.core.exchange.AbstractExchange;
import com.predic8.membrane.core.http.Message;
import com.predic8.membrane.core.interceptor.statistics.util.JDBCUtil;
import com.predic8.membrane.core.rules.Rule;
import com.predic8.membrane.core.rules.RuleKey;
import com.predic8.membrane.core.statistics.RuleStatistics;
import com.predic8.membrane.core.util.TextUtil;


public class MsgExchangeStore extends AbstractExchangeStore {

	private static Log log = LogFactory.getLog(MsgExchangeStore.class
			.getName());

	private String dir;

	private boolean raw;

	private static int counter = 0;


	public static final String MESSAGE_FILE_PATH = "message.file.path";

	private boolean saveBodyOnly = false;


	public void add(AbstractExchange exc) {
		if (exc.getResponse() == null)
			counter++;

		/* skb */
		if (exc.getRequest() != null) {
			log.info("MsgExchangeStore add rq start.");

			try {
				Message msg = exc.getRequest();
				
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ByteArrayOutputStream header = new ByteArrayOutputStream();
				
				msg.writeStartLine(header);
				msg.getHeader().write(header);
				header.write((Constants.CRLF).getBytes());
			
				exc.setProperty(JDBCUtil.MSG_HEADER, header.toByteArray());	
				
				if (msg.isBodyEmpty())
					return;

				os.write(msg.getBody().getRaw());
				
				exc.setProperty(JDBCUtil.MSG, os.toByteArray());
				
			} catch (Exception ex) {
				log.error("MsgExchangeStore add rq error: "+ex.toString());
			}
			
		}
		

		/* skb */
	}

	public AbstractExchange[] getExchanges(RuleKey ruleKey) {
		throw new RuntimeException(
				"Method getExchanges() is not supported by FileExchangeStore");
	}

	public int getNumberOfExchanges(RuleKey ruleKey) {
		throw new RuntimeException(
				"Method getNumberOfExchanges() is not supported by FileExchangeStore");
	}

	public void remove(AbstractExchange exchange) {
		throw new RuntimeException(
				"Method remove() is not supported by FileExchangeStore");
	}

	public void removeAllExchanges(Rule rule) {
		throw new RuntimeException(
				"Method removeAllExchanges() is not supported by FileExchangeStore");
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public boolean isRaw() {
		return raw;
	}

	public void setRaw(boolean raw) {
		this.raw = raw;
	}

	public RuleStatistics getStatistics(RuleKey ruleKey) {

		return null;
	}

	public Object[] getAllExchanges() {
		return null;
	}

	public Object[] getLatExchanges(int count) {
		return null;
	}

	public List<AbstractExchange> getAllExchangesAsList() {
		return null;
	}

	public void removeAllExchanges(AbstractExchange[] exchanges) {
		// ignore
	}

	public boolean isSaveBodyOnly() {
		return saveBodyOnly;
	}

	public void setSaveBodyOnly(boolean saveBodyOnly) {
		this.saveBodyOnly = saveBodyOnly;
	}

}
