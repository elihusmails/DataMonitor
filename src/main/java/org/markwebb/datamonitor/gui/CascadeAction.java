package org.markwebb.datamonitor.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class CascadeAction extends AbstractAction {
	private static final long serialVersionUID = -510196679913338836L;

	private JDesktopPane desktop;

	public CascadeAction(JDesktopPane desktop, String label) {
		super(label);
		this.desktop = desktop;
	}

	public void actionPerformed(ActionEvent e) {
		JInternalFrame[] frames = desktop.getAllFrames();
		if (frames.length == 0)
			return;

		int margin = frames.length * 24 + 24;
		int width = desktop.getBounds().width - margin;
		int height = desktop.getBounds().height - margin;
		for (int i = 0; i < frames.length; i++) {
			frames[i].setBounds(24 + desktop.getBounds().x + i * 24, 24
					+ desktop.getBounds().y + i * 24, width, height);
		}
	}
}
