package com.br.flavioreboucassantos.camel_choice_from_activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelChoiceFromActiveMQ {

	static final private String activeMQ_QueueSource = "orders";
	static final private String activeMQ_QueueEnd1 = "orders_widget";
	static final private String activeMQ_QueueEnd2 = "orders_gadget";

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

		final RouteBuilder route_fromFile_toActiveMQ = new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				from("file:input_orders?delete=true") // delete=true
						.to("activemq:queue:" + activeMQ_QueueSource);

			}
		};

		final RouteBuilder route_fromActiveMQ_toActiveMQ = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				final Endpoint ep_orders = endpoint("activemq:queue:" + activeMQ_QueueSource);

				// ValueBuilder implements Expression, Predicate
				final ValueBuilder simple_fileNameContainsSecret = simple("${header.CamelFileName} contains '_secret'");
				final ValueBuilder xpath_order_product_widget = xpath("/order/product = 'widget'");

				final Endpoint ep_widget = endpoint("activemq:queue:" + activeMQ_QueueEnd1);
				final Endpoint ep_gadget = endpoint("activemq:queue:" + activeMQ_QueueEnd2);

				from(ep_orders)
						.log("CamelFileName = ${header.CamelFileName}")
						.choice()
						.when(simple_fileNameContainsSecret).log("(ON) order WITH secret").to("direct:order_with_secret")
						.otherwise().log("(OFF) order WITHOUT secret");

				from("direct:order_with_secret")
						.choice()
						.when(xpath_order_product_widget).log("Order is Widget").to(ep_widget)
						.otherwise().log("Order is Gadget").to(ep_gadget);
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(route_fromFile_toActiveMQ);
		defaultCamelContext.addRoutes(route_fromActiveMQ_toActiveMQ);

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
