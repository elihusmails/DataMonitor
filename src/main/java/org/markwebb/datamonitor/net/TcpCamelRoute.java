package org.markwebb.datamonitor.net;

import org.apache.camel.builder.RouteBuilder;
import org.markwebb.datamonitor.DataMonitor;

public class TcpCamelRoute extends RouteBuilder {

	private String host;
	private int port;
	
	public TcpCamelRoute( String host, int port ){
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void configure() throws Exception {

		from("netty4:tcp:" + host + ":" + port + "?textline=true" )
		.to(DataMonitor.CAMEL_VM_ENDPOINT);
	}
}
