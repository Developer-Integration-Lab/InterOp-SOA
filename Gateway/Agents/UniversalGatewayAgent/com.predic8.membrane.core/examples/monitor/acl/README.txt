ACCESS CONTROL INTERCEPTOR

With the AccessControlInterceptor you can restrict access to services and resources.

An ACL file allows a fine grained configuration of permissions. Access can be restricted based on the ip address,  hostname and the URI of the requested resource.



RUNNING THE EXAMPLE

In this example we will make an HTTP GET request call to secured resources.

To run example execute the following steps:

1. Start the Membrane Monitor.
2. 


HOW IT IS DONE

The following part describes the example in detail.  

First take a look at the rules.xml file.


<configuration>
  <rules>
    <forwarding-rule name="predic8.com" port="2000">
      <targetport>80</targetport>
      <targethost>predic8.com</targethost>
    </forwarding-rule>
  </rules>
</configuration>

The rule forwards calls to the port 2000 to predic8.com:80. 


Next take a look at the bean configuration of the interceptor in the examples/acl/acl-beans.xml file.

<bean class="com.predic8.membrane.core.interceptor.acl.AccessControlInterceptor">
    <property name="aclFilename" value="acl.xml" />
</bean>

The value of the property 'aclFilename' is the name of the access control list XML file. 
Before processing the first request the ACL file is read and the access control component is initialized. 


Next take a look at acl.xml file located under the examples/acl directory:

<accessControl>
	
	<resource uri="/open-source/*">
    <clients>
	    <ip>192.168.2.*</ip>
	  </clients>
  </resource>
    
  <resource uri="/contact/*">
    <clients>
	    <hostname>localhost</hostname>
	  </clients>
  </resource>
    
  <resource uri="*">
	  <clients>
	    <any/>
	  </clients>
  </resource>
    
</accessControl>   


For each resource a list of authorized clients can be specified.  The resource is referred by its URI and 
clients can be referred by hostname or IP address, therefore you can use <hostname> and <ip> XML elements respectively.
The element <any> can be used to grant permission to all clients.

The access permissions are scanned from top to bottom, therefore the order of the rules is significant.


