package org.markwebb.datamonitor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.markwebb.datamonitor.camel.CamelVmDataMonitorRoute;
import org.markwebb.datamonitor.camel.net.UdpCamelRoute;
import org.markwebb.datamonitor.gui.AbstractSensorPanel;
import org.markwebb.datamonitor.gui.CascadeAction;
import org.markwebb.datamonitor.gui.ConstrainDesktopMgr;
import org.markwebb.datamonitor.gui.InternalSensorFrame;
import org.markwebb.datamonitor.gui.TileAction;
import org.markwebb.datamonitor.gui.addsensor.AddSensorFrame;
import org.markwebb.datamonitor.gui.displaysensor.DisplaySensors;
import org.markwebb.datamonitor.sensor.Sensor;
import org.markwebb.datamonitor.sensor.SensorData;
import org.markwebb.datamonitor.sensor.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataMonitor extends JDesktopPane implements ActionListener,
		Processor {

	private static final Logger log = LoggerFactory
			.getLogger(DataMonitor.class);
	private static final long serialVersionUID = -8532711716522750910L;

	private static final DataMonitor monitor = new DataMonitor();
	private static final String FILE = "File";
	private static final String CLOSE = "Close";
	private static final String CONFIG = "Config";
	private static final String ADDSENSOR = "Add sensor...";
	private static final String LOADCONFIG = "Load...";
	private static final String SAVECONFIG = "Save...";
	private static final String SHOWSENSORS = "Show sensors...";
	private static final String SENSORS = "Sensors";
	private static final String STARTALL = "Start all";
	private static final String STOPALL = "Stop all";
	private static final String PAUSEALL = "Pause all";
	private static final String REPLAY = "Replay...";
	private static final String WINDOW = "Window";
	private static final String CASCADE = "Cascade";
	private static final String TILE_FRAMES = "Tile Frames";

	public static final String CAMEL_VM_ENDPOINT = "vm:datamonitor";

	private DefaultCamelContext camelContext;

	private HashMap<String, InternalSensorFrame> sensorFrames;
	private HashMap<String, AbstractSensorPanel> sensorPanels;

	private DataMonitor() {
	}

	public void init() {

		camelContext = new DefaultCamelContext();

		try {
			camelContext.start();
			camelContext.addRoutes(new CamelVmDataMonitorRoute(this));
		} catch (Exception e) {
			e.printStackTrace();
		}

		sensorFrames = new HashMap<String, InternalSensorFrame>();
		sensorPanels = new HashMap<String, AbstractSensorPanel>();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		setPreferredSize(new Dimension(screenSize.width / 2,
				screenSize.height / 2));

		setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		setDesktopManager(new ConstrainDesktopMgr());

		setVisible(true);
	}

	public static DataMonitor getInstance() {
		return monitor;
	}

	public void addUdpListener(String host, int port) throws Exception {

		RouteBuilder routeBuilder = new UdpCamelRoute(host, port);
		camelContext.addRoutes(routeBuilder);
	}

	public JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();

		JMenu file = new JMenu(FILE);
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem close = new JMenuItem(CLOSE, KeyEvent.VK_C);
		close.addActionListener(this);
		file.add(close);
		menubar.add(file);

		JMenu config = new JMenu(CONFIG);
		config.setMnemonic(KeyEvent.VK_C);
		JMenuItem saveconfig = new JMenuItem(SAVECONFIG, KeyEvent.VK_S);
		saveconfig.addActionListener(this);
		config.add(saveconfig);
		JMenuItem loadconfig = new JMenuItem(LOADCONFIG, KeyEvent.VK_L);
		loadconfig.addActionListener(this);
		config.add(loadconfig);

		JMenu sensors = new JMenu(SENSORS);
		sensors.setMnemonic(KeyEvent.VK_S);
		JMenuItem addmonitor = new JMenuItem(ADDSENSOR, KeyEvent.VK_A);
		addmonitor.addActionListener(this);
		sensors.add(addmonitor);
		JMenuItem showsensors = new JMenuItem(SHOWSENSORS, KeyEvent.VK_S);
		showsensors.addActionListener(this);
		sensors.add(showsensors);
		JMenuItem replay = new JMenuItem(REPLAY, KeyEvent.VK_R);
		replay.addActionListener(this);
		sensors.add(replay);
		sensors.add(new JSeparator());
		JMenuItem startall = new JMenuItem(STARTALL);
		startall.addActionListener(this);
		sensors.add(startall);
		JMenuItem stopall = new JMenuItem(STOPALL);
		stopall.addActionListener(this);
		sensors.add(stopall);
		JMenuItem pauseall = new JMenuItem(PAUSEALL);
		pauseall.addActionListener(this);
		sensors.add(pauseall);

		menubar.add(config);
		menubar.add(sensors);

		JMenu window = new JMenu(WINDOW);
		window.setMnemonic(KeyEvent.VK_W);
		window.add(new TileAction(this, TILE_FRAMES));
		window.add(new CascadeAction(this, CASCADE));

		menubar.add(window);
		return menubar;
	}

	public void addMonitorPanel(AbstractSensorPanel asp) {

		InternalSensorFrame imf = new InternalSensorFrame(asp.getTitle());
		imf.add(asp, BorderLayout.CENTER);
		imf.setFrameSize(asp.getSize());
		imf.setVisible(true);
		sensorFrames.put(asp.getTitle(), imf);
		sensorPanels.put(asp.getTitle(), asp);
		add(imf);
		try {
			imf.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public void removeSensor(Sensor sensor) {
		InternalSensorFrame imf = sensorFrames.remove(sensor.getTitle());
		imf.setVisible(false);
		imf = null;

		AbstractSensorPanel asp = sensorPanels.remove(sensor.getTitle());
		asp = null;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CLOSE))
			System.exit(0);
		else if (e.getActionCommand().equals(ADDSENSOR)) {
			AddSensorFrame amp = new AddSensorFrame();
			amp.setVisible(true);
		} else if (e.getActionCommand().equals(LOADCONFIG)) {
			JFileChooser filechooser = new JFileChooser(
					System.getProperty("user.dir"));
			int retval = filechooser.showDialog(this, "Load");
			if (retval == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				SensorRepository sr = SensorRepository.getInstance();
				ArrayList<Sensor> list = sr.loadSensors(file);
				for (Sensor s : list) {
					AbstractSensorPanel amp = sr.addSensor(s);
					addMonitorPanel(amp);
				}
			}
		} else if (e.getActionCommand().equals(SAVECONFIG)) {
			JFileChooser filechooser = new JFileChooser(
					System.getProperty("user.dir"));
			int retval = filechooser.showDialog(this, "Save");
			if (retval == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				SensorRepository sr = SensorRepository.getInstance();
				sr.storeSensors(file);
			}
		} else if (e.getActionCommand().equals(SHOWSENSORS)) {
			DisplaySensors ds = new DisplaySensors();
			ds.setVisible(true);
		} else if (e.getActionCommand().equals(REPLAY)) {
			JFileChooser filechooser = new JFileChooser(
					System.getProperty("user.home"));
			int retval = filechooser.showDialog(this, "Select");
			if (retval == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();

				try {
					MonitorReplay mr = new MonitorReplay(file);
					mr.start();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getActionCommand().equals(STARTALL)) {
			Collection<AbstractSensorPanel> panels = sensorPanels.values();
			for (AbstractSensorPanel asp : panels) {
				asp.setStateStarted();
			}
		} else if (e.getActionCommand().equals(STOPALL)) {
			Collection<AbstractSensorPanel> panels = sensorPanels.values();
			for (AbstractSensorPanel asp : panels) {
				asp.setStateStopped();
			}
		} else if (e.getActionCommand().equals(PAUSEALL)) {
			Collection<AbstractSensorPanel> panels = sensorPanels.values();
			for (AbstractSensorPanel asp : panels) {
				asp.setStatePaused();
			}
		}
	}

	@Override
	public void process(Exchange exchange) throws Exception {

		String message = exchange.getIn().getBody(String.class);

		log.debug(exchange.getIn().getBody(String.class));

		String fields[] = message.split(" ");

		if (fields.length != 4) {
			log.error("UDPMonitorInputServer: Illegal data message [" + "]");
		}

		double d = Double.parseDouble(fields[3]);
		long l = Long.parseLong(fields[2]);

		SensorData sd = new SensorData(fields[0], fields[1], l, d);

		SensorRepository sensorRep = SensorRepository.getInstance();
		sensorRep.updateSensor(sd);
	}
}
