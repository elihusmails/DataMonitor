package org.markwebb.datamonitor;

import java.net.InetAddress;

import org.markwebb.datamonitor.sensor.SensorData;
import org.markwebb.datamonitor.sensor.SensorRepository;

public abstract class AbstractMonitorInput implements Runnable {
	
	protected SensorRepository sensorRep = SensorRepository.getInstance();
	protected String alias;
	protected InetAddress host;
	protected int port;
	private Thread thread;
	private boolean running;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public InetAddress getAddress() {
		return host;
	}

	public void setAddress(InetAddress host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void start() {
		running = true;
		if (thread == null)
			thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			SensorData sd = readData();
			if (sd != null)
				updateSensor(sd);
		}
	}

	public void updateSensor(SensorData sd) {
		sensorRep.updateSensor(sd);
	}

	public abstract SensorData readData();
}
