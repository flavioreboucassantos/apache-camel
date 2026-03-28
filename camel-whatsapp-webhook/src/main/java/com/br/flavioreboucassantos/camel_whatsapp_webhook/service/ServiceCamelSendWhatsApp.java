package com.br.flavioreboucassantos.camel_whatsapp_webhook.service;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass.JSONWhatsAppMessage;
import com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder.RouteBuilderSendWhatsApp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ServiceCamelSendWhatsApp {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceCamelSendWhatsApp.class);

	final RouteBuilderSendWhatsApp routeBuilderSendWhatsApp;
	final ProducerTemplate producerTemplate;

	@Inject
	public ServiceCamelSendWhatsApp(
			final RouteBuilderSendWhatsApp routeBuilderSendWhatsApp,
			final ProducerTemplate producerTemplate) throws Exception {
		this.routeBuilderSendWhatsApp = routeBuilderSendWhatsApp;
		this.producerTemplate = producerTemplate;
	}

	public void sendWhatsApp(final String to, final String textOfMessage) {
		producerTemplate.sendBody(routeBuilderSendWhatsApp.getRouteUriFrom(), new JSONWhatsAppMessage(to, textOfMessage));
	}
}
