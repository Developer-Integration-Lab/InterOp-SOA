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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.predic8.membrane.core.exchange.Exchange;
import com.predic8.membrane.core.exchange.ExchangesUtil;
import com.predic8.membrane.core.interceptor.AbstractInterceptor;
import com.predic8.membrane.core.interceptor.Outcome;

public class StatisticsCSVInterceptor extends AbstractInterceptor {

	private static Log log = LogFactory.getLog(StatisticsCSVInterceptor.class.getName());
	
	private File csvFile;

	public StatisticsCSVInterceptor() {
		priority = 510;
	}
	
	@Override
	public Outcome handleResponse(Exchange exc) throws Exception {
		log.debug("logging statistics to " + csvFile.getAbsolutePath());
		writeExchange(exc);
		return Outcome.CONTINUE;
	}

	private void writeExchange(Exchange exc) throws Exception {
		FileWriter w = new FileWriter(csvFile,true);
		
		writeCSV(ExchangesUtil.getStatusCode(exc),w);
		writeCSV(ExchangesUtil.getTime(exc),w);
		writeCSV(exc.getRule().toString(),w);
		writeCSV(exc.getRequest().getMethod(),w);
		writeCSV(exc.getRequest().getUri(),w);
		writeCSV(exc.getSourceHostname(),w);
		writeCSV(exc.getServer(),w);
		writeCSV(exc.getRequestContentType(),w);
		writeCSV(ExchangesUtil.getRequestContentLength(exc),w);
		writeCSV(ExchangesUtil.getResponseContentType(exc),w);
		writeCSV(ExchangesUtil.getResponseContentLength(exc),w);
		writeCSV(ExchangesUtil.getTimeDifference(exc),w);
		writeNewLine(w);
		
		w.close();
	}

	public void setFileName(String fileName) throws Exception {
		csvFile = new File(fileName);
		
		csvFile.createNewFile();
		
		if (!csvFile.canWrite())
			throw new IOException("File " + fileName + " is not writable.");
		
		if (csvFile.length() == 0)
			writeHeaders();
	}

	private void writeCSV(String value, FileWriter w) throws IOException {
		w.append(value+";");
	}
	
	private void writeNewLine(FileWriter w) throws IOException {
		w.append(System.getProperty("line.separator"));
	}
	
	private void writeHeaders() throws Exception {
		FileWriter w = new FileWriter(csvFile);
		
		writeCSV("Status Code",w);
		writeCSV("Time",w);
		writeCSV("Rule",w);
		writeCSV("Method",w);
		writeCSV("Path",w);
		writeCSV("Client",w);
		writeCSV("Server",w);
		writeCSV("Request Content-Type",w);
		writeCSV("Request Content Length",w);
		writeCSV("Response Content-Type",w);
		writeCSV("Response Content Length",w);
		writeCSV("Duration",w);
		writeNewLine(w);
		
		w.close();
	}
}
