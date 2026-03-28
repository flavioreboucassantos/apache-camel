package com.br.flavioreboucassantos.camel_whatsapp_webhook.controller;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_whatsapp_webhook.service.ServiceProducerToRouteWebHookCallback;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * @author Flávio Rebouças Santos - flavioReboucasSantos@gmail.com
 */
@Path("/webhook")
public final class ControllerWebHook {

	private final Logger LOG = LoggerFactory.getLogger(ControllerWebHook.class);

	final String myVerifyToken;
	final ServiceProducerToRouteWebHookCallback serviceProducerToRouteWebHookCallback;

	@Inject
	public ControllerWebHook(
			@ConfigProperty(name = "whatsapp_webhook.my_verify_token") final String myVerifyToken,
			final ServiceProducerToRouteWebHookCallback serviceProducerToRouteWebHookCallback) {
		this.myVerifyToken = myVerifyToken;
		this.serviceProducerToRouteWebHookCallback = serviceProducerToRouteWebHookCallback;
	}

	@GET
	public Response getSubscribe(@QueryParam("hub_mode") final String hubMode,
			@QueryParam("hub_challenge") final String hubChallenge,
			@QueryParam("hub_verify_token") final String hubVerifyToken) {

		LOG.info("getSubscribe ENTROU hub_mode = " + hubMode + " , hub_challenge = " + hubChallenge + " , hub_verify_token = " + hubVerifyToken);

		if (hubMode != null && hubMode.equals("subscribe") &&
				hubVerifyToken != null && hubVerifyToken.equals(myVerifyToken))
			return Response.ok(hubChallenge).status(Response.Status.OK).build();

		LOG.info("getSubscribe FALHOU");

		return Response.status(Response.Status.EXPECTATION_FAILED).build();
	}

	@POST
	public Response getCallback(final String jsonWebHookCallback) {

		serviceProducerToRouteWebHookCallback.produces(jsonWebHookCallback);

		return Response.status(Response.Status.OK).build();
	}

}
