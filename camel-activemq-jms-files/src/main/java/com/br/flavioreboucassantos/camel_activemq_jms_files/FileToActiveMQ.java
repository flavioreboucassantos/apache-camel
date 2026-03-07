package com.br.flavioreboucassantos.camel_activemq_jms_files;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class FileToActiveMQ {

	static final private String activeMQ_Queue = "my_queue";

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

				from("file:input_box?noop=true") // noop=true is to USE FILE Read-only
						.to("activemq:queue:" + activeMQ_Queue);

			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		/*
		 * STRUCTURING ACTIVEMQ & JMS
		 */
		printWipe2("STRUCTURING ACTIVEMQ & JMS");

		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();

		defaultCamelContext.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(activeMQConnectionFactory));

		// MANTÉM A APLICAÇÃO RODANDO
		while (true)
			Thread.sleep(200);
	}
}
