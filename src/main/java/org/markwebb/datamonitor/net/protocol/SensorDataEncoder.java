package org.markwebb.datamonitor.net.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.markwebb.datamonitor.sensor.SensorData;

/**
 * Encoder that is used by the server in order to transform
 * raw data into {@link SensorData} objects.
 */
public class SensorDataEncoder implements ProtocolEncoder {

	public void dispose(IoSession session) throws Exception {
		// nothing to do here...
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		
		SensorData sensorData = (SensorData)message;
		
		IoBuffer buffer = IoBuffer.allocate(16);
		buffer.setAutoExpand(true);
		
		byte[] source = sensorData.getSource().getBytes();
		buffer.putInt(source.length);
		buffer.put(source);
		
		byte[] metadata = sensorData.getMetadata().getBytes();
		buffer.putInt(metadata.length);
		buffer.put(metadata);
		
		buffer.putLong( sensorData.getTime());
		
		buffer.putDouble( sensorData.getData() );

		buffer.flip();
		out.write( buffer );
	}
}
