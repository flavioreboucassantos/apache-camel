package com.br.flavioreboucassantos.camel_async_rest_call;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AsyncProcessor implements Processor {

	final String uriHttps;
	final String param1;

	public AsyncProcessor(final String uriHttps, final String param1) {
		this.uriHttps = uriHttps;
		this.param1 = param1;
	}

	@Override
	public void process(final Exchange exchange) throws Exception {
		exchange
				.getIn()
				.setBody("ENTRADA DO CORPO PELO AsyncProcessor");
		exchange
				.getIn()
				.setHeader("uriHttps", uriHttps);
		exchange
				.getIn()
				.setHeader("param1", param1);

	}

}
