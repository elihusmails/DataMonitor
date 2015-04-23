package org.markwebb.datamonitor.net.protocol;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.markwebb.datamonitor.sensor.SensorData;

/**
 * Decoder that transforms {@link SensorData} into raw
 * data that can be sent to the server.
 */
public class SensorDataDecoder extends CumulativeProtocolDecoder {

	private static final Charset CHARSET = Charset.defaultCharset();
	private static final String DECODER_STATE_KEY = SensorDataDecoder.class.getName() + ".STATE";
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {

		SensorData sensorData = (SensorData)session.getAttribute(DECODER_STATE_KEY);
		
		if( sensorData == null ){
			sensorData = new SensorData();
			session.setAttribute(DECODER_STATE_KEY, sensorData);
		}
		
		if( in.prefixedDataAvailable(4)){
			int size = in.getInt();
			if( in.prefixedDataAvailable(size)){
				sensorData.setSource(in.getString(size, CHARSET.newDecoder()));
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		if( in.prefixedDataAvailable(4)){
			int size = in.getInt();
			if( in.prefixedDataAvailable(size)){
				sensorData.setMetadata(in.getString(size, CHARSET.newDecoder()));
			} else {
				return false;
			}
		} else {
			return false;
		}
		
		if( in.prefixedDataAvailable(8)){
			sensorData.setTime(in.getLong());
		} else {
			return false;
		}
		
		if( in.prefixedDataAvailable(8)){
			sensorData.setTime(in.getLong());
		} else {
			return false;
		}
		
		return true;
	}
}
