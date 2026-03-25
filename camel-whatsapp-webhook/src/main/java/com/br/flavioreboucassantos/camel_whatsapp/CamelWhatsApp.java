package com.br.flavioreboucassantos.camel_whatsapp;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

import com.br.flavioreboucassantos.camel_whatsapp.route_builder.RouteBuilderSendWhatsApp;
import com.br.flavioreboucassantos.camel_whatsapp.service.ServiceCamelSendWhatsApp;

public class CamelWhatsApp {
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

	static final private void printWipe3(final Object o) {
		System.out.println(o);
		System.out.println("");
	}

	static public void main(String[] args) throws Exception {
		/*
		 * STRUCTURING CAMEL ROUTE & CONTEXT
		 */

		final ConfigLoader loader = new ConfigLoader();

		final String phoneNumberId = loader.getProperty("sendWhatsApp.phoneNumberId");
		final String whatsAppToken = loader.getProperty("sendWhatsApp.token");
		final RouteBuilderSendWhatsApp routeBuilderSendWhatsApp = new RouteBuilderSendWhatsApp(phoneNumberId, whatsAppToken);

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilderSendWhatsApp);

		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();

		/*
		 * USING PRODUCER
		 */
		final ServiceCamelSendWhatsApp serviceCamelSendWhatsApp = new ServiceCamelSendWhatsApp(routeBuilderSendWhatsApp);
		serviceCamelSendWhatsApp.sendWhatsApp("553185868479", "(3) Teste de envio de mensagem para WhatsApp");

		/*
		 * USING CONSUMER
		 */
//		final String receiveBody = consumerTemplate.receiveBody("seda:end", String.class);

		printWipe("aguarda 2 segundos... (1)");
		Thread.sleep(2000);

		printWipe2("CLOSES ALL");
		serviceCamelSendWhatsApp.close();
		consumerTemplate.close();
		defaultCamelContext.close();
	}
}
