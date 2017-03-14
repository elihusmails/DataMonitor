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

7. Change the protocol to Google protobuf so that its more standard and it opens up the opportunity for more applications, written in other languages, to interact with this tool.


User Guide 

1. The DataMonitor UI starts up a UDP listener on 127.0.0.1 port 12000.  You can send messages to this host/port in the following format:

		<source> <metadata> <value> <milliseconds since epoch>
		
2. The sensor that is configured in the UI must have values that match the source and metadata that comes in over the host/port.  If the values don't match, the message won't show up on the display.


== Test ==
1. Start DataMonitor UI
2. Select Sensors -> Add New Sensor
3. Set title to "Monitor Test Thread"
4. Add Source "stage1" and metadata "processed"
5. Add Source "stage1" and metadata "received"
6. Add Source "stage2" and metadata "saturation"
7. Add Source "stage2" and metadata "unknown"
8. Click "Add Sensor".  The chart will show up in the UI
9. Start the test class org.markwebb.datamonitor.test.MonitorTester


