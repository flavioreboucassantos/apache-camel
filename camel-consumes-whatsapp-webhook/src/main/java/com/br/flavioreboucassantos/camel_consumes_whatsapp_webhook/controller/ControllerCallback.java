package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.service.ServiceConsumerFromRouteWebHookCallback;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Flávio Rebouças Santos - flavioReboucasSantos@gmail.com
 */
@Path("/callback")
public class ControllerCallback {

	private final Logger LOG = LoggerFactory.getLogger(ControllerCallback.class);

	final ServiceConsumerFromRouteWebHookCallback serviceConsumerFromRouteWebHookCallback;

	@Inject
	public ControllerCallback(final ServiceConsumerFromRouteWebHookCallback serviceConsumerFromRouteWebHookCallback) {
		this.serviceConsumerFromRouteWebHookCallback = serviceConsumerFromRouteWebHookCallback;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCallback() {
		return Response.ok(serviceConsumerFromRouteWebHookCallback.getListProcessedJSONWebHookCallback()).status(Response.Status.ACCEPTED).build();
	}

}
