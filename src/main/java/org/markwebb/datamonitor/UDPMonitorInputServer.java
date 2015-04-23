package org.markwebb.datamonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.markwebb.datamonitor.sensor.SensorData;
import org.markwebb.datamonitor.sensor.SensorRepository;

public class UDPMonitorInputServer extends AbstractMonitorInput {
	private DatagramSocket udpSocket;

	private byte[] buffer = new byte[256];

	public UDPMonitorInputServer(String alias, InetAddress address, int port)
			throws SocketException {
		sensorRep = SensorRepository.getInstance();
		udpSocket = new DatagramSocket(port, address);
		setPort(port);
		setAlias(alias);
		setAddress(address);
	}
	
	public int getPort()
	{
		return udpSocket.getLocalPort();
	}

	/**
	 * This is the method that processes all incoming UDP packets for the
	 * Monitor. The format of the message in the UDP packet is:
	 * 
	 * source metadata time value
	 * 
	 * Source is the name of the point to where the information came from
	 * Metadata is the descriptor for the value field. Time is the number of
	 * milliseconds from UTC. Value is the data that will be plotted.
	 * 
	 */
	public SensorData readData() {
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		try {
			udpSocket.receive(packet);
			System.out.println("Received UDP Packet");

			byte[] data = packet.getData();
			String xxx = new String(data);
			String fields[] = xxx.split(" ");

			if( fields.length != 4 )
			{
				System.err.println("UDPMonitorInputServer: Illegal data message [" + "]" );
				return new SensorData( "", "", 0, 0 );
			} 			
			
			double d = Double.parseDouble(fields[3]);
			long l = Long.parseLong(fields[2]);

			SensorData sd = new SensorData(fields[0], fields[1], l, d);
			System.out.println(sd);
			return sd;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NumberFormatException nfe) {
			return new SensorData("error", "NumberFormatException", System.currentTimeMillis(), 0.0d);			
		}
	}
}
