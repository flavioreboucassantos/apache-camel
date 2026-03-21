package com.br.flavioreboucassantos.camel_xml;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ValueBuilder;
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

				final ValueBuilder xpath_payment_type_credit = xpath("//payment[@type='credit']");
				final ValueBuilder xpath_payment_type_pix = xpath("//payment[@type='pix']");

				from("file:file-orders?include=.*\\.xml&noop=true") // Lê o arquivo XML da pasta file-orders sem mover ou remover
						.log("Lendo arquivo: ${file:name}")
						.split().xpath("/orders/order") // Divide o XML por cada tag <order>
						.choice()

						.when(xpath_payment_type_credit) // rota para payment type credit
						.log("ORDER PAYMENT TYPE CREDIT")
						.to("seda:processOrder")

						.when(xpath_payment_type_pix)
						.log("ORDER PAYMENT TYPE PIX") // rota para payment type pix
						.to("seda:processOrder")

						.otherwise()
						.log("ORDER PAYMENT TYPE UNKNOWN") // rota para payment type unknown
						.to("seda:processOrder");
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

		printWipe("Recebe arquivos por 50 segundos.");

		while ((receiveBody = consumerTemplate.receiveBody("seda:processOrder", 50000, String.class)) != null) {

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

		printWipe("aguarda 2 segundos... (1)");
		Thread.sleep(2000);

		printWipe2("CLOSES ALL");
		producerTemplate.close();
		consumerTemplate.close();
		defaultCamelContext.close();

	}
}
