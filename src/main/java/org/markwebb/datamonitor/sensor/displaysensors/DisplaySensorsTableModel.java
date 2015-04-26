package org.markwebb.datamonitor.sensor.displaysensors;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.markwebb.datamonitor.sensor.Sensor;

public class DisplaySensorsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 2381941200647881807L;

	private ArrayList<Sensor> data;
	private String[] columns = { "Name", "Source", "metadata" };

	private static final int NAME = 0;
	private static final int SOURCE = 1;
	private static final int METADATA = 2;

	public DisplaySensorsTableModel() {
		data = new ArrayList<Sensor>();
	}

	public void addRow(Sensor sensor) {
		data.add(sensor);
	}

	public int getRowCount() {
		return data.size();
	}

	public Sensor getSensorAt(int row) {
		return data.get(row);
	}

	public void deleteSensorAt(int row) {
		data.remove(row);
		fireTableRowsDeleted(row, getRowCount());
	}

	public void clear() {
		data.clear();
		fireTableRowsDeleted(0, getRowCount());
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Object getValueAt(int row, int col) {
		Sensor sensor = data.get(row);
		switch (col) {
		case NAME:
			return sensor.getTitle();
		case SOURCE:
			return sensor.getSource();
		case METADATA:
			return sensor.getMetaData();
		default:
			return "";
		}
	}

	public String getColumnName(int i) {
		return columns[i];
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
