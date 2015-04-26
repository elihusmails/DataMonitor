package org.markwebb.datamonitor.config;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.markwebb.datamonitor.DataMonitor;
import org.markwebb.datamonitor.sensor.AbstractSensorPanel;
import org.markwebb.datamonitor.sensor.Sensor;
import org.markwebb.datamonitor.sensor.SensorRepository;

public class AddMonitorFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4612743347701949973L;
	private static final String ADDMETADATA = "Add Metadata";
	private static final String ADDMONITOR = "Add Sensor";
	private static final String CANCEL = "Cancel";

	private JTextField title, source, metadata;
	private JRadioButton progressbar, chart;
	private AddInputPanel inputPanel;

	public AddMonitorFrame() {
		super("Add New Sensor");
		init();
	}

	private void init() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(new JLabel("Title : "), c);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		title = new JTextField(20);
		add(title, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		chart = new JRadioButton("Chart", true);
		add(chart, c);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 2;
		progressbar = new JRadioButton("ProgressBar", false);
		add(progressbar, c);
		ButtonGroup group = new ButtonGroup();
		group.add(chart);
		group.add(progressbar);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		add(new JLabel("Source : "), c);
		c.gridx = 1;
		source = new JTextField(10);
		add(source, c);
		c.gridx = 2;
		add(new JLabel("Metadata : "), c);
		c.gridx = 3;
		metadata = new JTextField(10);
		add(metadata, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 4;
		inputPanel = new AddInputPanel();
		add(inputPanel, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 4;
		add(createButtonPanel(), c);

		pack();
	}

	private JPanel createButtonPanel() {
		JPanel buttons = new JPanel();

		JButton addmetadata = new JButton(ADDMETADATA);
		addmetadata.addActionListener(this);
		buttons.add(addmetadata);

		JButton addmonitor = new JButton(ADDMONITOR);
		addmonitor.addActionListener(this);
		buttons.add(addmonitor);

		JButton cancel = new JButton(CANCEL);
		cancel.addActionListener(this);
		buttons.add(cancel);

		return buttons;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CANCEL))
			setVisible(false);
		else if (e.getActionCommand().equals(ADDMETADATA)) {
			inputPanel.addMetaData(metadata.getText());
		} else if (e.getActionCommand().equals(ADDMONITOR)) {
			SensorRepository sRep = SensorRepository.getInstance();

			int type;
			if (chart.isSelected())
				type = AbstractSensorPanel.TYPE_CHART;
			else
				type = AbstractSensorPanel.TYPE_PROGRESSBAR;

			Sensor monitor = new Sensor(title.getText(), source.getText(),
					sRep.getNextId(), type);
			ArrayList<String> metadata = inputPanel.getInputs();
			for (int i = 0; i < metadata.size(); i++)
				monitor.addMetaData(metadata.get(i));

			AbstractSensorPanel panel = sRep.addSensor(monitor);
			DataMonitor.getInstance().addMonitorPanel(panel);

			setVisible(false);
		}
	}

}
