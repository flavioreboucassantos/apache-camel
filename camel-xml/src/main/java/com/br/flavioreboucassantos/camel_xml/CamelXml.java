package com.br.flavioreboucassantos.camel_xml;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

public class CamelXml {

	static final private void print(final Object o) {
		System.out.println(o);
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
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:file-orders?include=.*\\.xml&noop=true") // Lê o arquivo XML da pasta file-orders sem mover ou remover
						.log("Lendo arquivo: ${file:name}")
						.split().xpath("/orders/order") // Divide o XML por cada tag <order>
						.to("seda:processOrder"); // Envia cada item para outra etapa
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		final ProducerTemplate producerTemplate = defaultCamelContext.createProducerTemplate();
		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();

		Thread.sleep(1000);

		/*
		 * PREPARE JAXBContext
		 */
		JAXBContext context = JAXBContext.newInstance(XmlOrder.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		/*
		 * USING CONSUMER
		 */
		String receiveBody;
		ArrayList<XmlOrder> arrayListOrders = new ArrayList<>();

		while ((receiveBody = consumerTemplate.receiveBody("seda:processOrder", 2000, String.class)) != null) {

			final XmlOrder xmlOrder = (XmlOrder) unmarshaller.unmarshal(new StringReader(receiveBody));
			arrayListOrders.add(xmlOrder);

			printWipe("Criado xmlOrder.id = " + xmlOrder.id);
			print("listXmlItem.size = " + xmlOrder.items.listXmlItem.size());
			print("listXmlItem = " + xmlOrder.items.listXmlItem);
			print("payment ->");
			print("    type = " + xmlOrder.payment.type);
			print("    cardNumber = " + xmlOrder.payment.cardNumber);
			print("    installments = " + xmlOrder.payment.installments);
		}

		printWipe("aguarda 10 segundos... (1)");
		Thread.sleep(10000);

		printWipe2("CLOSES ALL");
		producerTemplate.close();
		consumerTemplate.close();
		defaultCamelContext.close();

	}
}
