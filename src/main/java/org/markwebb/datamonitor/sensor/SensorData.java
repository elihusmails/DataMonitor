package org.markwebb.datamonitor.sensor;

public class SensorData {

	private String source;
	private String metadata;
	private long time;
	private double data;

	public SensorData() {

	}

	public SensorData(String source, String metadata, long time, double data) {
		this.source = source;
		this.metadata = metadata;
		this.time = time;
		this.data = data;
	}

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String toString() {
		return getSource() + " " + getMetadata() + " " + getTime() + " "
				+ getData();
	}
}
