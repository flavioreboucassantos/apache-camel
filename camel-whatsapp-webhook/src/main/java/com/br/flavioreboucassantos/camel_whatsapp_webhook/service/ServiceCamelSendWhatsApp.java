package com.br.flavioreboucassantos.camel_whatsapp_webhook.service;

import java.io.IOException;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass.JSONWhatsAppMessage;
import com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder.BaseRouteBuilderSendWhatsApp;

public class ServiceCamelSendWhatsApp {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceCamelSendWhatsApp.class);

	final BaseRouteBuilderSendWhatsApp baseRouteBuilderSendWhatsApp;
	final ProducerTemplate producerTemplate;

	public ServiceCamelSendWhatsApp(final BaseRouteBuilderSendWhatsApp baseRouteBuilderSendWhatsApp) {
		this.baseRouteBuilderSendWhatsApp = baseRouteBuilderSendWhatsApp;
		producerTemplate = baseRouteBuilderSendWhatsApp.getCamelContext().createProducerTemplate();
	}

	public ServiceCamelSendWhatsApp(final BaseRouteBuilderSendWhatsApp baseRouteBuilderSendWhatsApp, final ProducerTemplate producerTemplate) {
		this.baseRouteBuilderSendWhatsApp = baseRouteBuilderSendWhatsApp;
		this.producerTemplate = producerTemplate;
	}

	public void sendWhatsApp(final String to, final String textOfMessage) {
		try {
			producerTemplate.sendBody(baseRouteBuilderSendWhatsApp.getRouteUri(), new JSONWhatsAppMessage(to, textOfMessage));
		} catch (Exception e) {
			LOG.info("ServiceCamelSendWhatsApp -> direct:sendWhatsApp Falhou: " + e.getMessage());
		} finally {
		}

	}

	public void close() throws IOException {
		producerTemplate.close();
	}

}
