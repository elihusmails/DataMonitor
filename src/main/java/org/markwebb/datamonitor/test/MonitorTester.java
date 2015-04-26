package org.markwebb.datamonitor.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MonitorTester {

	public MonitorTester() {
		try {
			MonitorTestThread mtt1 = new MonitorTestThread("localhost", 12000,
					"stage1", 1000, "processed", 10000, 20000);
			mtt1.start();

			MonitorTestThread mtt2 = new MonitorTestThread("localhost", 12000,
					"stage1", 1000, "received", 10000, 20000);
			mtt2.start();

			MonitorTestThread mtt3 = new MonitorTestThread("localhost", 12000,
					"stage2", 1000, "saturation", 0, 100);
			mtt3.start();

			MonitorTestThread mtt4 = new MonitorTestThread("localhost", 12000,
					"stage2", 1000, "unknown", 0, 100);
			mtt4.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 1) {
			File output = new File(args[0]);
			PrintStream ps = null;
			try {
				ps = new PrintStream(output);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			System.out.println("Writing all output to "
					+ output.getAbsolutePath());
			System.setOut(ps);
		}

		new MonitorTester();

	}

}
