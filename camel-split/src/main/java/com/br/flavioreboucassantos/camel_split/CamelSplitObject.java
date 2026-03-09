package com.br.flavioreboucassantos.camel_split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import com.br.flavioreboucassantos.camel_split.objs.CustomerOrders;
import com.br.flavioreboucassantos.camel_split.objs.MyOrder;

public class CamelSplitObject {

	static final List<MyOrder> listOrder = new ArrayList();
	static final CustomerOrders customerOrders;

	static {
		listOrder.add(new MyOrder("0100", Arrays.asList("I100", "I101", "I102")));
		listOrder.add(new MyOrder("0200", Arrays.asList("I200", "I201", "I202")));
		customerOrders = new CustomerOrders("AxisCustomer", listOrder);
	}

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
				from("direct:customerOrder")
						.log("Customer Id ---> ${body.customerId}")
						.log("List of Orders ---> ${body.listOrder}")
						.to("seda:end")
						.split(simple("${body.listOrder}"))
						.log("From BODY > Order ---> ${body}")
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
		producerTemplate.sendBody("direct:customerOrder", customerOrders);

		CustomerOrders receiveBody = consumerTemplate.receiveBody("seda:end", 2000, CustomerOrders.class);

		printWipe("Recebendo Object CustomerOrders");
		printWipe(receiveBody.toString());

		printWipe("Recebendo Orders From Body");
		MyOrder receiveBody2;
		ArrayList<MyOrder> listOrder = new ArrayList<>();

		while ((receiveBody2 = consumerTemplate.receiveBody("seda:end", 2000, MyOrder.class)) != null)
			listOrder.add(receiveBody2);

		printWipe("Fila vazia. Consumo finalizado.");

		printWipe2(listOrder.toString());

		consumerTemplate.stop();
		defaultCamelContext.stop();
		defaultCamelContext.close();
	}

}
