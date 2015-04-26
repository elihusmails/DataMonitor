package org.markwebb.datamonitor.config;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Simple Table Model for showing the sensor data when creating a new sensor for
 * the system.
 * 
 * @author mark
 *
 */
public class AddInputTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 6759268773299643739L;

	private static final int METADATA = 0;

	private String[] columns = { "Metadata" };

	private ArrayList<String> data;

	public AddInputTableModel() {
		data = new ArrayList<String>();
	}

	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return columns.length;
	}

	public void addRow(String source) {
		data.add(source);
		fireTableRowsInserted(0, getRowCount());
	}

	public void deleteRow(int i) {
		data.remove(i);
		fireTableRowsDeleted(getRowCount() - 1, getRowCount());
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

	public Object getValueAt(int row, int col) {
		switch (col) {
		case METADATA:
			return data.get(row);
		default:
			return "";
		}
	}

	public ArrayList<String> getMetadata() {
		return data;
	}
}
