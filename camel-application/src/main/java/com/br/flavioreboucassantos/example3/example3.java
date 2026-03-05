package com.br.flavioreboucassantos.example3;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class example3 {

	public static void main(String[] args) throws Exception {

		/*
		 * STRUCTURING
		 */		
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				from("direct:start").to("seda:end");

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
		producerTemplate.sendBody("direct:start", "This is a Message at direct:start");
		final String receiveBody = consumerTemplate.receiveBody("seda:end", String.class);		
		defaultCamelContext.close();
		
		System.out.println(receiveBody);
	}

}