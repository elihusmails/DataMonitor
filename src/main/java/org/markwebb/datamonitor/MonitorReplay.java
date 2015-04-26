package org.markwebb.datamonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.markwebb.datamonitor.sensor.SensorData;
import org.markwebb.datamonitor.sensor.SensorRepository;

/**
 * Reads in a data file and pushes the data to the monitor(s)
 * 
 * @author mark
 *
 */
public class MonitorReplay implements Runnable {
	private File file;

	private Thread thread;

	public MonitorReplay(File file) throws FileNotFoundException {
		this.file = file;

		if (!file.exists())
			throw new FileNotFoundException("Could not find the file " + file);
	}

	public void start() {
		if (thread == null)
			thread = new Thread(this);
		thread.start();
	}

	public void run() {
		performReplay();
	}

	public void performReplay() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;

		SensorRepository sr = SensorRepository.getInstance();

		try {
			while ((line = reader.readLine()) != null) {
				String fields[] = line.split(" ");

				double d = Double.parseDouble(fields[3]);
				long l = Long.parseLong(fields[2]);

				SensorData data = new SensorData(fields[0], fields[1], l, d);
				sr.updateSensor(data);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
