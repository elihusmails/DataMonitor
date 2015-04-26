package org.markwebb.datamonitor.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MonitorTestThread implements Runnable {
	private Thread thread;
	private boolean running = true;
	private InetAddress address;
	private DatagramSocket socket;

	private int port;
	private int wait;
	private double minvalue;

	private String packetString;
	private double range;

	public MonitorTestThread(String host, int port, String source, int wait,
			String metadata, double minvalue, double maxvalue)
			throws UnknownHostException, SocketException {
		address = InetAddress.getByName(host);
		socket = new DatagramSocket();

		this.port = port;
		this.wait = wait;
		this.minvalue = minvalue;

		packetString = source + " " + metadata + " ";
		range = Math.abs(maxvalue - minvalue);
	}

	public void start() {
		if (thread == null)
			thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			double d = (Math.random() * range) + minvalue;

			String data = packetString + System.currentTimeMillis() + " " + d;

			// System.out.println( data );

			DatagramPacket packet = new DatagramPacket(data.getBytes(),
					data.getBytes().length, address, port);

			try {
				socket.send(packet);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
