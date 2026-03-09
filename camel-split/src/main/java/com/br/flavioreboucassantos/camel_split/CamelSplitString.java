package com.br.flavioreboucassantos.camel_split;

import java.util.ArrayList;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelSplitString {

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
		printWipe2("STRUCTURING ROUTE");

		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start")
						.log("Body Before Split Line ---> ${body}")
						.split(body(), "\\")
						.log("Split Line -> ${body}")
						.to("seda:end");
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		final ProducerTemplate producerTemplate = defaultCamelContext.createProducerTemplate();
		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();

		/*
		 * USING TIME
		 */
		producerTemplate.sendBody("direct:start", "src\\main\\java\\com\\br\\flavioreboucassantos\\camel_split");

		String receiveBody;
		ArrayList<String> arrayListConsumed = new ArrayList<>();

		while ((receiveBody = consumerTemplate.receiveBody("seda:end", 2000, String.class)) != null)
			arrayListConsumed.add(receiveBody);

		printWipe("Fila vazia. Consumo finalizado.");

		printWipe2(arrayListConsumed.toString());

		consumerTemplate.stop();
		defaultCamelContext.stop();
		defaultCamelContext.close();
	}
}
