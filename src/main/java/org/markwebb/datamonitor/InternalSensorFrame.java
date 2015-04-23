package org.markwebb.datamonitor;

import java.awt.Dimension;

import javax.swing.JInternalFrame;

public class InternalSensorFrame extends JInternalFrame {
	private static final long serialVersionUID = -2323748661938193041L;

	static int openFrameCount = 0;

	static final int xOffset = 30, yOffset = 30;

	public InternalSensorFrame(String title) {
		super(title, true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable

		// ...Then set the window size or call pack...
		// setSize(300,300);

		// Set the window's location.
		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
	}

	public void setFrameSize(Dimension d) {
		setSize(d);
	}
}
