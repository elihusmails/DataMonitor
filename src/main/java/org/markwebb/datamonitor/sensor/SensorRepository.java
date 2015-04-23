package org.markwebb.datamonitor.sensor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//TODO: make this class thread-safe
public class SensorRepository {
	private static final SensorRepository repository = new SensorRepository();

	private HashMap<String, AbstractSensorPanel> sensors;
	private int nextId;
	private ArrayList<Sensor> sensorList;

	private SensorRepository() {
		sensors = new HashMap<String, AbstractSensorPanel>();
		sensorList = new ArrayList<Sensor>();
		nextId = 0;
	}

	public static SensorRepository getInstance() {
		return repository;
	}

	public synchronized int getNextId() {
		return nextId++;
	}

	public AbstractSensorPanel addSensor(Sensor sensor) {
		sensorList.add(sensor);

		if (sensor.getType() == AbstractSensorPanel.TYPE_CHART) {
			ChartSensorPanel panel = new ChartSensorPanel(sensor.getTitle(),
					sensor.getMetaData());
			sensors.put(sensor.getSource(), panel);
			return panel;
		} else {
			ProgressSensorPanel panel = new ProgressSensorPanel(sensor
					.getTitle());
			sensors.put(sensor.getSource(), panel);
			return panel;
		}
	}

	public void deleteSensor(Sensor sensor) {
		sensorList.remove(sensor);
	}

	public Iterator<Sensor> sensors() {
		return sensorList.iterator();
	}

	public void storeSensors(File file) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(file));
			oos.writeObject(sensorList);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Sensor> loadSensors(File file) {
		ArrayList<Sensor> list = null;

		try {
			ObjectInputStream oos = new ObjectInputStream(new FileInputStream(
					file));
			list = (ArrayList<Sensor>) oos.readObject();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void updateSensor(SensorData data) {
		
		System.out.println("Sensors: " + sensors );
		System.out.println("Data [" + data + "]");
		
		AbstractSensorPanel amp = sensors.get(data.getSource());
		if (amp != null){
			System.out.println("Updating Abstract Sensor Panel -- " + amp.getTitle());
			amp.receiveUpdate(data);
		}else {
			System.err.println("Unknown source --> " + data.getSource());
		}
	}
}
