package org.markwebb.datamonitor;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

class TileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private JDesktopPane desk; // the desktop to work with

	public TileAction(JDesktopPane desk, String label) {
		super(label);
		this.desk = desk;
	}

	public static void tile(JDesktopPane desktopPane, int layer) {
		JInternalFrame[] frames = desktopPane.getAllFramesInLayer(layer);
		if (frames.length == 0)
			return;

		tile(frames, desktopPane.getBounds());
	}

	public static void tile(JDesktopPane desktopPane) {
		JInternalFrame[] frames = desktopPane.getAllFrames();
		if (frames.length == 0)
			return;

		tile(frames, desktopPane.getBounds());
	}

	private static void tile(JInternalFrame[] frames, Rectangle dBounds) {
		int cols = (int) Math.sqrt(frames.length);
		int rows = (int) (Math.ceil(((double) frames.length) / cols));
		int lastRow = frames.length - cols * (rows - 1);
		int width, height;

		if (lastRow == 0) {
			rows--;
			height = dBounds.height / rows;
		} else {
			height = dBounds.height / rows;
			if (lastRow < cols) {
				rows--;
				width = dBounds.width / lastRow;
				for (int i = 0; i < lastRow; i++) {
					frames[cols * rows + i].setBounds(i * width, rows * height,
							width, height);
				}
			}
		}

		width = dBounds.width / cols;
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				frames[i + j * cols].setBounds(i * width, j * height, width,
						height);
			}
		}
	}

	public void actionPerformed(ActionEvent ev) {
		tile(desk);
		// // How many frames do we have?
		// JInternalFrame[] allframes = desk.getAllFrames();
		// int count = allframes.length;
		// if (count == 0)
		// return;
		//
		// // Determine the necessary grid size
		// int sqrt = (int) Math.sqrt(count);
		// int rows = sqrt;
		// int cols = sqrt;
		// if (rows * cols < count) {
		// cols++;
		// if (rows * cols < count) {
		// rows++;
		// }
		// }
		//
		// // Define some initial values for size & location.
		// Dimension size = desk.getSize();
		//
		// int w = size.width / cols;
		// int h = size.height / rows;
		// int x = 0;
		// int y = 0;
		//
		// // Iterate over the frames, deiconifying any iconified frames and
		// then
		// // relocating & resizing each.
		// for (int i = 0; i < rows; i++) {
		// for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
		// JInternalFrame f = allframes[(i * cols) + j];
		//
		// if (!f.isClosed() && f.isIcon()) {
		// try {
		// f.setIcon(false);
		// } catch (PropertyVetoException ignored) {
		// }
		// }
		//
		// desk.getDesktopManager().resizeFrame(f, x, y, w, h);
		// x += w;
		// }
		// y += h; // start the next row
		// x = 0;
		// }
	}
}
