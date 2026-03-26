package com.br.flavioreboucassantos.camel_whatsapp_webhook;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CamelWhatsAppWebHook {

	static public void main(String[] args) throws Exception {
		Quarkus.run(args);

//		
//		final RouteBuilderSendWhatsApp routeBuilderSendWhatsApp = new RouteBuilderSendWhatsApp(phoneNumberId, whatsAppToken);
//
//		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
//		defaultCamelContext.start();
//		defaultCamelContext.addRoutes(routeBuilderSendWhatsApp);
//
//		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();
//
//				final ServiceCamelSendWhatsApp serviceCamelSendWhatsApp = new ServiceCamelSendWhatsApp(routeBuilderSendWhatsApp);
//		serviceCamelSendWhatsApp.sendWhatsApp("553185868479", "(3) Teste de envio de mensagem para WhatsApp");

	}
}
