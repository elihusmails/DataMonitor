package org.markwebb.datamonitor.sensor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SensorPanelButtons extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3381024637559055248L;

	protected static final String START = "start";
	protected static final String PAUSE = "pause";
	protected static final String STOP = "stop";
	protected static final String RESTART = "restart";

	private JButton start, stop, pause;

	public SensorPanelButtons(ActionListener listener) {
		start = new JButton(START);
		start.addActionListener(listener);
		start.addActionListener(this);
		add(start);

		pause = new JButton(PAUSE);
		pause.addActionListener(listener);
		pause.addActionListener(this);
		add(pause);

		stop = new JButton(STOP);
		stop.addActionListener(listener);
		stop.addActionListener(this);
		add(stop);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(START)) {
			start.setEnabled(false);
			stop.setEnabled(true);
			pause.setEnabled(true);
		} else if (e.getActionCommand().equals(STOP)) {
			start.setEnabled(true);
			stop.setEnabled(false);
			pause.setEnabled(false);
		} else if (e.getActionCommand().equals(PAUSE)) {
			pause.setText(RESTART);
			start.setEnabled(false);
			stop.setEnabled(true);
			pause.setEnabled(true);
		} else if (e.getActionCommand().equals(RESTART)) {
			pause.setText(PAUSE);
			start.setEnabled(false);
			stop.setEnabled(true);
			pause.setEnabled(true);
		}
	}
}
