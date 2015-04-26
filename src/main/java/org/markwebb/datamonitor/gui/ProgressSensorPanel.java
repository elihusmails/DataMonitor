package org.markwebb.datamonitor.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;

import org.markwebb.datamonitor.sensor.SensorData;

public class ProgressSensorPanel extends AbstractSensorPanel {
	private static final long serialVersionUID = -3971795602977585145L;
	private DataMonitorProgressBar progressbar;

	public ProgressSensorPanel(String title) {
		state = RUNNING;
		this.title = title;

		add(new JLabel(title), BorderLayout.NORTH);

		progressbar = new DataMonitorProgressBar(
				DataMonitorProgressBar.REDGREEN);
		add(progressbar, BorderLayout.CENTER);

		add(new SensorPanelButtons(this), BorderLayout.SOUTH);
	}

	public String getTitle() {
		return title;
	}

	public Dimension getSize() {
		return new Dimension(400, 200);
	}

	@Override
	public void receiveUpdate(SensorData data) {
		if (state == RUNNING)
			progressbar.setOtherValue((int) data.getTime());
	}

}
