package com.br.flavioreboucassantos.camel_filter;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelFilter {
	static final private void printWipe(final Object o) {
		System.out.println("");
		System.out.println(o);
	}

	static final private void printWipe2(final Object o) {
		System.out.println("");
		System.out.println(o);
		System.out.println("");
	}

	public static void main(String[] args) throws Exception {
		/*
		 * STRUCTURING CAMEL ROUTE & CONTEXT
		 */
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start")
						.filter(header("orderType").isEqualTo("urgent"))
						.log("U R G E N T E ::: ${body} ::: header.orderType=${header.orderType}")
						.end();
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		final ProducerTemplate producerTemplate = defaultCamelContext.createProducerTemplate();
		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();

		/*
		 * USING PRODUCER
		 */
		printWipe2("USING PRODUCER");

		final Map<String, Object> headers = new HashMap<>();

		headers.put("orderType", "normal");
		producerTemplate.sendBodyAndHeaders("direct:start", "Messagem Tipo Normal", headers);

		headers.put("orderType", "urgent");
		producerTemplate.sendBodyAndHeaders("direct:start", "Messagem Tipo Urgente", headers);

		printWipe("aguarda 10 segundos... (1)");
		Thread.sleep(10000);

		printWipe2("CLOSES ALL");
		producerTemplate.close();
		consumerTemplate.close();
		defaultCamelContext.close();

	}
}
