package com.br.flavioreboucassantos.camel_whatsapp_webhook.service;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder.RouteBuilderWebHookCallback;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public final class ServiceProducerToRouteWebHookCallback {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceProducerToRouteWebHookCallback.class);

	final RouteBuilderWebHookCallback routeBuilderWebHookCallback;
	final ProducerTemplate producerTemplate;

	@Inject
	public ServiceProducerToRouteWebHookCallback(
			final RouteBuilderWebHookCallback routeBuilderWebHookCallback,
			final ProducerTemplate producerTemplate) throws Exception {

		this.routeBuilderWebHookCallback = routeBuilderWebHookCallback;
		this.producerTemplate = producerTemplate;
	}

	public void produces(final String jsonWebHookCallback) {
		producerTemplate.sendBody(routeBuilderWebHookCallback.getRouteUriFrom(), jsonWebHookCallback);
	}

}
