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

package com.predic8.membrane.core.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.predic8.membrane.core.util.ByteUtil;

public class Body extends AbstractBody {

	private Log log = LogFactory.getLog(Body.class.getName());
	
	public Body(InputStream in, int length) throws IOException {
		this.inputStream = in;
		this.length = length;
	}
	
	public Body(String body) {
		chunks.add(new Chunk(body));
		read = true; // because we do not have something to read
		length = body.length();
	}
	
	/**
	 * the caller of this constructor is responsible to adjust the header accordingly
	 * e.g. the fields Transfer-Encoding and Content-Length
	 */
	public Body(byte[] content) {
		chunks.clear();
		chunks.add(new Chunk(content));
		read = true;
	}

	@Override
	protected void readLocal() throws IOException {
		chunks.add(new Chunk(ByteUtil.readByteArray(inputStream, length)));
	}
	
	@Override
	protected void writeAlreadyRead(OutputStream out) throws IOException {
		if (getLength() == 0)
			return;
		
		out.write(getContent(), 0, getLength());
	}
	
	protected void writeNotRead(OutputStream out) throws IOException {
		byte[] buffer = new byte[8192];

		int totalLength = 0;
		int length = 0;
		chunks.clear();
		while ((this.length > totalLength || this.length == -1) && (length = inputStream.read(buffer)) > 0) {
			totalLength += length;
			out.write(buffer, 0, length);
			byte[] chunk = new byte[length];
			System.arraycopy(buffer, 0, chunk, 0, length);
			chunks.add(new Chunk(chunk));
		}
		read = true;
	}

	@Override
	protected byte[] getRawLocal() throws IOException {
		if (chunks.isEmpty()) {
			log.debug("size of chunks list: " + chunks.size() + "  " + hashCode());
			log.debug("chunks size is: " + chunks.size() + " at time: " + System.currentTimeMillis());
			return new byte[0];
		}

		return getContent();
	}
}
