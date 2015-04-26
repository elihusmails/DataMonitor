package org.markwebb.datamonitor.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

public class DataMonitorProgressBar extends JProgressBar {
	private static final long serialVersionUID = 1173329148235151557L;
	private int type = 0;
	private int typeColor = 0;
	public static final int INDICATOR = 0;
	public static final int COMPETITIVE = 1;
	public static final int CIRCULAR = 2;
	public static final int MULTICOLOR = 3;
	public static final int REDBLUE = 0;
	public static final int REDGREEN = 1;
	public static final int BLUEGREEN = 2;
	private int otherValue = 0;
	private Color otherForegroundColor = Color.red;

	// slider constructor
	public DataMonitorProgressBar(int type) {
		super();
		this.setForeground(Color.blue);
		this.setType(type);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	}

	// slider constructor
	public DataMonitorProgressBar() {
		super();
		this.setForeground(Color.blue);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	}

	// slider constructor
	public DataMonitorProgressBar(int min, int max) {
		super(min, max);
		this.setForeground(Color.blue);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
	}

	// graph ONLY OVERLOADED
	public void paint(Graphics g) {

		// controllo
		if (g == null)
			return;

		// delete paint
		g.setColor(this.getBackground());
		g.fill3DRect(0, 0, this.getWidth(), this.getHeight(), true);

		// draw select
		if (type == INDICATOR)
			this.drawIndicator(g);
		if (type == CIRCULAR)
			this.drawCircular(g);
		if (type == MULTICOLOR)
			this.drawMulticolor(g);
		if (type == COMPETITIVE)
			this.drawCompetitive(g);
	}

	public void setOtherValue(int value) {
		otherValue = value;
		this.repaint();
	}

	public int getOtherValue() {
		return otherValue;
	}

	public void setOtherForeground(Color value) {
		otherForegroundColor = value;
	}

	public Color getOtherForeground() {
		return otherForegroundColor;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setTypeColor(int typeColor) {
		this.typeColor = typeColor;
	}

	private void drawIndicator(Graphics g) {

		// prepare setting
		int h = this.getHeight();
		int w = this.getWidth();
		int ox = (getOtherValue() >= 0) ? w / 2 : 0;
		int oy = 0;

		// paint
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];

		// upper
		xPoints[0] = ox;
		yPoints[0] = oy;
		xPoints[1] = ox;
		yPoints[1] = h;
		xPoints[2] = ox
				+ ((int) (((double) w / 2) * ((double) this.getValue()) / ((double) (this
						.getMaximum() - this.getMinimum()))));
		xPoints[2] = (xPoints[2] > 0) ? xPoints[2] : 0;
		yPoints[2] = h / 2;
		xPoints[3] = ox;
		yPoints[3] = oy;

		// draw
		g.setColor(this.getForeground());
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(this.getForeground().darker());
		g.drawPolyline(xPoints, yPoints, 4);
		g.drawString(this.getValue() + " %", xPoints[2], yPoints[2] + 3);

		if (getOtherValue() >= 0) {
			// lower
			xPoints[0] = 0;
			yPoints[0] = oy;
			yPoints[1] = h;
			xPoints[0] = xPoints[1];
			xPoints[2] = ((int) (ox - ((double) ox)
					* ((double) this.getOtherValue())
					/ ((double) (this.getMaximum() - this.getMinimum()))));
			xPoints[2] = (xPoints[2] > 0) ? xPoints[2] : 0;
			yPoints[2] = h / 2;
			xPoints[3] = xPoints[0];
			yPoints[3] = yPoints[0];

			// draw
			g.setColor(this.getOtherForeground());
			g.fillPolygon(xPoints, yPoints, 4);
			g.setColor(this.getOtherForeground().darker());
			g.drawPolyline(xPoints, yPoints, 4);
			g.drawString(this.getOtherValue() + " %", xPoints[2] - 10,
					yPoints[2] + 3);
		}
	}

	private void drawCompetitive(Graphics g) {
		// prepare setting
		int h = this.getHeight();
		int w = this.getWidth();
		double a = (double) this.getValue() / (double) this.getMaximum();
		double b = (double) this.getOtherValue() / (double) this.getMaximum();
		double A = w * a / (a + b);

		// draw
		g.setColor(this.getForeground());
		g.fill3DRect(1, 1, (int) A, h - 1, true);
		g.setColor(this.getOtherForeground());
		g.fill3DRect((int) A, 1, w - 1, h - 1, true);

		// drw string
		g.setColor(Color.white);
		g.drawString("" + a * 100 + "%", (int) (A / 2), h / 2);
		g.drawString("" + b * 100 + "%", (int) ((w - A) / 2 + A), h / 2);

		g.drawLine((int) A, 1, (int) A, h);
	}

	private void drawCircular(Graphics g) {
		// prepare setting
		int h = this.getHeight();
		int w = this.getWidth();
		double a = 360 - this.getValue() * 360 / this.getMaximum();
		double bh = this.getOtherValue() * h / this.getMaximum() / 2;
		double bw = this.getOtherValue() * w / this.getMaximum() / 2;

		// draw
		g.setColor(this.getOtherForeground());
		g.fillArc(1, 1, w, h, (int) a, (int) (360 - a));
		g.setColor(this.getForeground());
		g.fillArc((int) bw, (int) bh, w - 2 * (int) bw, h - 2 * (int) bh,
				(int) a, (int) (360 - a));

		// drw string
		g.setColor(Color.white);
		g.drawString("" + this.getValue() + "%", w / 2, h / 2);
		g.drawString("" + this.getOtherValue() + "%", (int) bw, (int) bh);
	}

	private void drawMulticolor(Graphics g) {
		// prepare setting
		int h = this.getHeight();
		int w = this.getWidth();

		// value
		int value = this.getValue();
		int maxV = this.getMaximum();
		float perc = ((float) value) / ((float) maxV);

		int i = 0;
		for (i = 0; i < w; i++) {
			if (perc >= ((float) i) / ((float) w)) {
				g.setColor(this.getMColor(i, w));
				g.drawLine(i, 1, i, h - 1);
			} else {
				break;
			}
		}

		g.setColor(this.getBackground());
		g.drawString(this.getValue() + " %", w / 2, h / 2);
	}

	private Color getMColor(int i, int N) {
		float v = ((float) i) / ((float) N);
		if (this.typeColor == REDBLUE)
			return new Color((int) (254 * v + 1), 0, (int) (255 - 254 * v));
		if (this.typeColor == REDGREEN)
			return new Color((int) (254 * v + 1), (int) (255 - 254 * v), 0);
		if (this.typeColor == BLUEGREEN)
			return new Color(0, (int) (254 * v + 1), (int) (255 - 254 * v));
		return new Color((int) (254 * v + 1), (int) (254 * v + 1),
				(int) (254 * v + 1));
	}
}