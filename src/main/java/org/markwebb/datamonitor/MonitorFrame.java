package org.markwebb.datamonitor;

import java.awt.BorderLayout;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

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

		UDPMonitorInputServer umis = null;
		try {
			umis = new UDPMonitorInputServer("default", InetAddress.getByName("localhost"), 12000);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		hmf.getMonitor().addServer(umis);

		hmf.add(hmf.getMonitor(), BorderLayout.CENTER);
		hmf.pack();
	}
}
