package org.markwebb.datamonitor.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.markwebb.datamonitor.DataMonitor;

/**
 * This class wire's up the Apache Camel VM endpoint to the DataMonitor class,
 * which is acting as the processor to receive all the data and pass it out to
 * the Monitor GUI's
 * 
 * @author mark
 *
 */
public class CamelVmDataMonitorRoute extends RouteBuilder {

	private Processor processor;

	public CamelVmDataMonitorRoute(Processor processor) {
		this.processor = processor;
	}

	@Override
	public void configure() throws Exception {

		from(DataMonitor.CAMEL_VM_ENDPOINT).process(processor);
	}
}
