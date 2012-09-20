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

public abstract class CharactersElement extends AbstractXmlElement {

	protected String value;

	public CharactersElement() {

	}

	public CharactersElement(String value) {
		this.value = value;
	}

	@Override
	protected void parseCharacters(XMLStreamReader token) throws XMLStreamException {
		value = token.getText();
	}

	public String getValue() {
		return value;

	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void write(XMLStreamWriter out) throws XMLStreamException {
		out.writeStartElement(getElementName());
		if (value != null)
			out.writeCharacters(value);
		out.writeEndElement();
	}

}
