package org.markwebb.datamonitor.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.markwebb.datamonitor.sensor.SensorData;

public abstract class AbstractSensorPanel extends JPanel implements
		ActionListener {
	public final int STOPPED = 0;
	public final int PAUSED = 1;
	public final int RUNNING = 2;

	public final static int TYPE_CHART = 0;
	public final static int TYPE_PROGRESSBAR = 1;

	protected int state;
	protected String title;

	public abstract void receiveUpdate(SensorData data);

	public abstract String getTitle();

	public abstract Dimension getSize();

	public void setStateStopped() {
		this.state = STOPPED;
	}

	public void setStatePaused() {
		this.state = PAUSED;
	}

	public void setStateStarted() {
		this.state = RUNNING;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(SensorPanelButtons.STOP))
			state = STOPPED;
		else if (e.getActionCommand().equals(SensorPanelButtons.PAUSE))
			state = PAUSED;
		else if (e.getActionCommand().equals(SensorPanelButtons.START))
			state = RUNNING;
		else if (e.getActionCommand().equals(SensorPanelButtons.RESTART))
			state = RUNNING;
	}
}
