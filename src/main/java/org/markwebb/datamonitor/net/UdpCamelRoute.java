package org.markwebb.datamonitor.net;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.impl.PropertyPlaceholderDelegateRegistry;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.markwebb.datamonitor.DataMonitor;

/**
 * This class is responsible for receiving events over a UDP network connection
 * and sending the data to a Camel VM endpoint. The DataMonitor class will be
 * listening on the VM endpoint and passing events to the DataMonitor class
 * which is acting as a Camel Processor, where the data will be passed to the
 * Sensor monitor(s) for placing on a graph.
 * 
 * @author mark
 *
 */
public class UdpCamelRoute extends RouteBuilder {

	private String host;
	private int port;

	public UdpCamelRoute(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void configure() throws Exception {

		PropertyPlaceholderDelegateRegistry delRegistry = (PropertyPlaceholderDelegateRegistry) getContext()
				.getRegistry();
		JndiRegistry registry = (JndiRegistry) delRegistry.getRegistry();

		registry.bind("encoder", new StringEncoder());
		registry.bind("decoder", new StringDecoder());

		from(
				"netty:udp://"
						+ host
						+ ":"
						+ port
						+ "?sync=false&decoder=#decoder&allowDefaultCodec=false")
				.to("log:UDP_IN").to(DataMonitor.CAMEL_VM_ENDPOINT);
	}
}
