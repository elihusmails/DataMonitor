package org.markwebb.datamonitor.sensor.displaysensors;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import org.markwebb.datamonitor.DataMonitor;
import org.markwebb.datamonitor.config.AddMonitorFrame;
import org.markwebb.datamonitor.sensor.Sensor;
import org.markwebb.datamonitor.sensor.SensorRepository;

public class DisplaySensors extends JFrame implements ActionListener {
	private static final long serialVersionUID = 7126549099638795156L;

	private static final String ADD = "add";
	private static final String DELETE = "delete";
	private static final String CLOSE = "close";

	private DisplaySensorsTableModel tablemodel;
	private JTable table;

	public DisplaySensors() {
		super("Sensor Information");

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		tablemodel = new DisplaySensorsTableModel();
		loadData();

		table = new JTable(tablemodel);
		JScrollPane scrollpane = new JScrollPane(table);
		add(scrollpane, BorderLayout.CENTER);

		add(buttons(), BorderLayout.SOUTH);

		pack();
	}

	private void loadData() {
		SensorRepository sr = SensorRepository.getInstance();
		for (Iterator<Sensor> it = sr.sensors(); it.hasNext();) {
			Sensor s = it.next();
			tablemodel.addRow(s);
		}
	}

	private void reloadData() {
		tablemodel.clear();
		loadData();
	}

	private JPanel buttons() {
		JPanel panel = new JPanel();

		JButton add = new JButton(ADD);
		add.addActionListener(this);
		panel.add(add);
		JButton delete = new JButton(DELETE);
		delete.addActionListener(this);
		panel.add(delete);
		JButton close = new JButton(CLOSE);
		close.addActionListener(this);
		panel.add(close);

		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ADD)) {
			AddMonitorFrame amd = new AddMonitorFrame();
			amd.setVisible(true);

			reloadData();
		} else if (e.getActionCommand().equals(DELETE)) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Sensor sensor = tablemodel.getSensorAt(selectedRow);
				SensorRepository sr = SensorRepository.getInstance();
				tablemodel.deleteSensorAt(selectedRow);
				sr.deleteSensor(sensor);
				DataMonitor.getInstance().removeSensor(sensor);
			} else
				JOptionPane.showMessageDialog(null, "Please select a row",
						"Error", JOptionPane.ERROR_MESSAGE);
		} else if (e.getActionCommand().equals(CLOSE)) {
			setVisible(false);
		}
	}

}
