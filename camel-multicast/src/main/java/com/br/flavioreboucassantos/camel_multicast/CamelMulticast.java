package com.br.flavioreboucassantos.camel_multicast;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelMulticast {

	static private final AtomicInteger orderId = new AtomicInteger(100);

	static private final Processor processorProduceByTimer = (exchange) -> {
		exchange.getIn().setBody("{order-id:'" + orderId.getAndIncrement() + "', price: 'R$ 5000.00'}");
	};

	public static void main(String[] args) throws Exception {
		/*
		 * STRUCTURING CAMEL ROUTE & CONTEXT
		 */
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				from("timer:orders?period=2000")
						.process(processorProduceByTimer)
						.to("direct:payment", "direct:stock");

				from("direct:payment").log("FROM direct:payment ---> ${body}");

				from("direct:stock").log("FROM direct:stock ---> ${body}");

			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		// MANTÉM A APLICAÇÃO RODANDO
		while (true)
			Thread.sleep(200);
	}
}
