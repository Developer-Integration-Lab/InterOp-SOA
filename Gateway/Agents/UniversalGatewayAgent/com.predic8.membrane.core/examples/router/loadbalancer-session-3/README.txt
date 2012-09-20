LOAD BALANCING WITH STICKY SESSIONS

The LoadBalancerInterceptor can be configured to look for session ids in requests and responses. When the LoadBalancerInterceptor finds a new session id, the balancer will associated the session with the node that has received or send the message. If the session id is detected again in another message the message will be forwards to the associated node.
 
 
RUNNING THE EXAMPLE

In this example we will set up a load balancer, two nodes and one client. The communication between the node and client will be session based. The client will send eleven requests. With the first request the client gets a session id that will be used for the following 10 requests. 

To run the example execute the following steps:

1. Go to the examples/loadbalancer-session-3 directory.

2. First we start the router with the following command:

start router.bat

This will initialize the LoadBalancingInterceptor and associate it with a rule.

3. Now start two nodes:

start ant run-node1
start ant run-node2

4. Open the URL http://localhost:9000/admin/

5. Click on the LoadBalancer tab.

6. Nodes are identified by host name and port. Fill in the formular with "localhost" as host and 4000 as port and press "Add Node".

7. Add another node with host name "localhost" and port 4001.

8. Go to the command line and run the following command:

start ant run-client -Dlogin=jim

9. Take a look at the console output of node 1 and node 2. You will notice that only node 1 received requests. Thats because of the session id. Membrane sends messages with the same session id to the same node.  

10. Run the client again. You will notice that this time all requests will be send to node 2. 
     
     







