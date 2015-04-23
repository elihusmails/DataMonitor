package org.markwebb.datamonitor.net.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class SensorDataProtocolFactory implements ProtocolCodecFactory {

	private ProtocolEncoder encoder;
    private ProtocolDecoder decoder;
    
    public SensorDataProtocolFactory() {
    	
    	encoder = new SensorDataEncoder();
    	decoder = new SensorDataDecoder();
    }
	
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		
		return encoder;
	}
}
