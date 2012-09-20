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

package com.predic8.membrane.core.config;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.predic8.membrane.core.Constants;
import com.predic8.membrane.core.Router;


public class Path extends AbstractConfigElement {

	public Path(Router router) {
		super(router);
	}

	public static final String ELEMENT_NAME = "path";
	
	private String value;
	
	private Boolean regExp;
	
	@Override
	protected String getElementName() {
		return ELEMENT_NAME;
	}
	
	@Override
	protected void parseCharacters(XMLStreamReader token) throws XMLStreamException {
		value = token.getText();
	}
	
	@Override
	protected void parseAttributes(XMLStreamReader token) {
		regExp = Boolean.parseBoolean(token.getAttributeValue(Constants.NS_UNDEFINED, "isRegExp"));
	}

	
	@Override
	public void write(XMLStreamWriter out) throws XMLStreamException {
		out.writeStartElement(ELEMENT_NAME);
		out.writeAttribute("isRegExp", regExp.toString());
		out.writeCharacters(value);
		out.writeEndElement();
	}

	public boolean isRegExp() {
		return regExp;
	}

	public void setRegExp(boolean regExp) {
		this.regExp = regExp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
