package com.br.flavioreboucassantos.camel_whatsapp_webhook.service;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder.RouteBuilderWebHookCallback;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public final class ServiceWebHook {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceWebHook.class);

	final ProducerTemplate producerTemplate;
	final String routeUri;

	@Inject
	public ServiceWebHook(
			final RouteBuilderWebHookCallback routeBuilderWebHookCallback,
			final ProducerTemplate producerTemplate) throws Exception {

		routeUri = routeBuilderWebHookCallback.getRouteUri();
		this.producerTemplate = producerTemplate;
	}

	public void sendWebHookCallback(final String jsonWebHookCallback) {
		producerTemplate.sendBody(routeUri, jsonWebHookCallback);
	}

}
