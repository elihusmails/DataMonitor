package org.markwebb.datamonitor;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * This is the main class for the Application
 * 
 * @author mark
 */
public class MonitorFrame extends JFrame {

	private static final long serialVersionUID = 7007678678467152343L;

	private DataMonitor monitor;

	public MonitorFrame(String title, boolean visible) {
		super(title);

		monitor = DataMonitor.getInstance();
		monitor.init();
		setJMenuBar(monitor.createMenuBar());

		setVisible(visible);
	}

	public DataMonitor getMonitor() {
		return monitor;
	}

	public static void main(String[] args) {
		MonitorFrame hmf = new MonitorFrame("Test Client", true);

		try {
			hmf.getMonitor().addUdpListener("127.0.0.1", 12000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		hmf.add(hmf.getMonitor(), BorderLayout.CENTER);
		hmf.pack();
	}
}
