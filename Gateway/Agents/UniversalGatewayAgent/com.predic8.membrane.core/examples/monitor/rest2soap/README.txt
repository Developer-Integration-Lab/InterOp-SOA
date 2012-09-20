SOAP 2 REST INTERCEPTOR

With the SOAP2RESTInterceptor you can make a SOAP Web Service as a REST resource accessible.




RUNNING THE EXAMPLE

In this example we will call a SOAP Web Service by using a simple HTTP GET request. We will use a service that identifies the name, zip code and region of a bank by its banking code. You can take a look at the WSDL at 

http://www.thomas-bayer.com/axis2/services/BLZService?wsdl

To run the example execute the following steps:

1. Start the Membrane Monitor.
2. Click on "File/Load Configuration" and open the file examples/rest2soap/rules.xml.
3. Open the URL http://localhost:2000/bank/37050198 in your browser.
4. Try it again with a different banking code e.g. http://localhost:2000/bank/66762332.




HOW IT IS DONE

The following part describes the example in detail.  

First take a look at the rules.xml file.


<configuration>
  <rules>
    <forwarding-rule name="BLZ REST 2 SOAP"
                      port="2000"
                      host="*"
                      method="*">
      <targetport>80</targetport>
      <targethost>www.thomas-bayer.com</targethost>
      <interceptors>
        <interceptor id="rest2SoapInterceptor"
                      name="REST 2 SOAP Interceptor" />
      </interceptors>
    </forwarding-rule>
  </rules>
</configuration>


You will see that there is a rule that directs calls to the port 2000 to www.thomas-bayer.com:80. Additionally the REST2SOAPInterceptor is set for the rule. The interceptor will be called during the processing of each request.

Now take a look at the bean configuration of the interceptor in the configuration/monitor-beans.xml file.


 <bean id="rest2SoapInterceptor" class="com.predic8.membrane.core.interceptor.rest.REST2SOAPInterceptor">
 <property name="displayName" value="REST 2 SOAP Interceptor" />
  <property name="mappings">
	<map>
		<entry key="/bank/.*">
			<map>
				<entry key="SOAPAction" value=""/>
				<entry key="SOAPURI" value="/axis2/services/BLZService" />
				<entry key="requestXSLT" value="examples/rest2soap/blz-httpget2soap-request.xsl" />
				<entry key="responseXSLT" value="examples/rest2soap/strip-soap-envelope.xsl" />          				
			</map>
		</entry>
	</map>
  </property>
</bean>


The mappings property contains a map. Each key will by matched against the URI of incoming requests. The data mapped by the key that matches the URI first will be taken for the following steps. If no match is found nothing will be done.

In this example the regular expression bank/.* will match /bank/37050198 so that the interceptor takes the data mapped by the key and continues with the next step.

If we now open the following URL in a browser:

http://localhost:2000/bank/37050198

this will sen a request like the following one to Membrane. 


GET /bank/37050198 HTTP/1.1
Host: www.thomas-bayer.com:80
User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: de-de,de;q=0.8,en-us;q=0.5,en;q=0.3
Accept-Encoding: gzip, deflate
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Keep-Alive: 115
Connection: keep-alive
X-Forwarded-For: 0:0:0:0:0:0:0:1


Now the REST2SOAP interceptor will transform the HTTP request into a temporary XML document. Usually the temporary document can't be seen. Depending on your logging configuration you can see the document dumped into the logfile.


<request method="GET" 
	 http-version="1.1">
  <uri value="/bank/37050198">
    <path>
      <component>bank</component>
      <component>37050198</component>
    </path>
  </uri>
  <headers>
    <header name="Host">localhost:2000</header>
    <header name="User-Agent">Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1</header>
    <header name="Accept">text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8</header>
    <header name="Accept-Language">de-de,de;q=0.8,en-us;q=0.5,en;q=0.3</header>
    <header name="Accept-Encoding">gzip, deflate</header>
    <header name="Accept-Charset">ISO-8859-1,utf-8;q=0.7,*;q=0.7</header>
    <header name="Keep-Alive">115</header>
    <header name="Connection">keep-alive</header>
    <header name="X-Forwarded-For">0:0:0:0:0:0:0:1</header>
  </headers>
</request>


As you see there are elements for the HTTP headers too. This allows an XSLT stylesheet to reference HTTP headers when constructing the SOAP envelope in the next step. The stylesheet that is used to transform the XML document into a SOAP message is referenced by the map entry "requestXSLT". In our example the stylesheet looks like the following:


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	                      xmlns:s11="http://schemas.xmlsoap.org/soap/envelope/">
	<xsl:template match="/">
		<s11:Envelope >
		  <s11:Body>
		    <blz:getBank xmlns:blz="http://thomas-bayer.com/blz/">
		      <blz:blz><xsl:value-of select="//path/component[2]"/></blz:blz>
		    </blz:getBank>
		  </s11:Body>
		</s11:Envelope>	
	</xsl:template>
</xsl:stylesheet>


After the transformation the SOAP message will look like the following:


<s11:Envelope xmlns:s11="http://schemas.xmlsoap.org/soap/envelope/" 	   
              xmlns:ns1="http://thomas-bayer.com/blz/">
  <s11:Body>
    <ns1:getBank>
      <ns1:blz>37050198</ns1:blz>
    </ns1:getBank>
  </s11:Body>
</s11:Envelope>


Next the interceptor will replace the body of the request with the SOAP message. With the map entries "SOAPAction" and "SOAPURI" the interceptor will finally create a new HTTP POST request.


POST /axis2/services/BLZService HTTP/1.1
Host: www.thomas-bayer.com:80
User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: de-de,de;q=0.8,en-us;q=0.5,en;q=0.3
Accept-Encoding: gzip, deflate
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7
Keep-Alive: 115
Connection: keep-alive
X-Forwarded-For: 0:0:0:0:0:0:0:1
Content-Length: 237
SOAPAction: 
Content-Type: text/xml;charset=UTF-8

<s11:Envelope xmlns:s11="http://schemas.xmlsoap.org/soap/envelope/" 	   
              xmlns:ns1="http://thomas-bayer.com/blz/">
  <s11:Body>
    <ns1:getBank>
      <ns1:blz>37050198</ns1:blz>
    </ns1:getBank>
  </s11:Body>
</s11:Envelope>


Membrane sends this HTTP message to the target service at www.thomas-bayer.com. 
The BLZService responses then with the following HTTP message:


HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: text/xml;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 12 May 2011 15:05:17 GMT

195
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
  <soapenv:Body>
    <ns1:getBankResponse xmlns:ns1="http://thomas-bayer.com/blz/">
      <ns1:details>
        <ns1:bezeichnung>Sparkasse K�lnBonn</ns1:bezeichnung>
        <ns1:bic>COLSDE33XXX</ns1:bic>
        <ns1:ort>K�ln</ns1:ort>
        <ns1:plz>50667</ns1:plz>
      </ns1:details>
    </ns1:getBankResponse>
  </soapenv:Body>
</soapenv:Envelope>
0


The interceptor than applies another XSLT stylesheet to the body of the response to strip the soap envelope anf soap body from the xml document. The stylesheet is referenced by the responseXSLT map entry. You will find a copy of the stylesheet below:


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
			      xmlns:s11="http://schemas.xmlsoap.org/soap/envelope/">
							  
	<xsl:template match="/">
		<xsl:apply-templates select="//s11:Body/*"/>
	</xsl:template>
	
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>	
	
</xsl:stylesheet>


The transformed body will show up in your browser:


<ns1:getBankResponse xmlns:ns1="http://thomas-bayer.com/blz/" 	
                     xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
  <ns1:details>
    <ns1:bezeichnung>Sparkasse K�lnBonn</ns1:bezeichnung>
    <ns1:bic>COLSDE33XXX</ns1:bic>
    <ns1:ort>K�ln</ns1:ort>
    <ns1:plz>50667</ns1:plz>
  </ns1:details>
</ns1:getBankResponse>

