package org.markwebb.datamonitor;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelVmDataMonitorRoute extends RouteBuilder {

	private Processor processor;
	
	public CamelVmDataMonitorRoute( Processor processor ){
		this.processor = processor;
	}
	
	@Override
	public void configure() throws Exception {

		from(DataMonitor.CAMEL_VM_ENDPOINT)
			.process(processor);
	}
}
