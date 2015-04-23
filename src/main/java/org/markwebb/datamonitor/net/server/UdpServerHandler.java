package org.markwebb.datamonitor.net.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.markwebb.datamonitor.sensor.SensorData;
import org.markwebb.datamonitor.sensor.SensorRepository;

public class UdpServerHandler extends IoHandlerAdapter {

	private SensorRepository sensorRep = SensorRepository.getInstance();
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		SensorData sensorData = (SensorData)message;
		
		System.out.println("SENSOR DATA = " + sensorData);
		
		sensorRep.updateSensor( sensorData );
	}
}
