package com.br.flavioreboucassantos.camel_whatsapp_webhook.service;

import java.io.IOException;

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

	final ProducerTemplate producerTemplate;
	final String routeUri;

	@Inject
	public ServiceCamelSendWhatsApp(
			final RouteBuilderSendWhatsApp routeBuilderSendWhatsApp,
			final ProducerTemplate producerTemplate) {

		routeUri = routeBuilderSendWhatsApp.getRouteUri();
		this.producerTemplate = producerTemplate;
	}

	public void sendWhatsApp(final String to, final String textOfMessage) {
		try {
			producerTemplate.sendBody(routeUri, new JSONWhatsAppMessage(to, textOfMessage));
		} catch (Exception e) {
			LOG.info("ServiceCamelSendWhatsApp -> direct:sendWhatsApp Falhou: " + e.getMessage());
		} finally {
		}

	}

	public void close() throws IOException {
		producerTemplate.close();
	}

}
