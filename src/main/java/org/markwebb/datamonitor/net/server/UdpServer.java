package org.markwebb.datamonitor.net.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

public class UdpServer {

	private NioDatagramAcceptor acceptor;
	
	public UdpServer( InetSocketAddress address ){
		
		acceptor = new NioDatagramAcceptor();
		
		acceptor.setDefaultLocalAddress( address );
		
		UdpServerHandler handler = new UdpServerHandler();
		acceptor.setHandler(handler);
	}
	
	public void startupServer() throws IOException{
		acceptor.bind();
	}
	
}
