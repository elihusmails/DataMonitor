This is a generic tool that can receive data in a specific format and graph the data over time.  I've used this many times in testing of network-based applications.  The code is very old and needs to be cleaned up, but at least its finally here and I can update it as I get free time to do so.


Updates:
26Apr - Underlying networking has been changed from Apache MINA to Apache Camel (Netty Component)

Tasks:
1. Wire in the Camel routes to so that DataMonitor is not a single point for all data to pass through.

2. Add a mechanism for adding host/port information to listen on.

3. Better organize the code
	a. Put code into proper java packages
	b. Sort out naming (sensor, monitor..etc)
	
4. Javadoc

5. Unit Tests

6. Configuration should not be a serialized object.  Change to JSON/XML.


