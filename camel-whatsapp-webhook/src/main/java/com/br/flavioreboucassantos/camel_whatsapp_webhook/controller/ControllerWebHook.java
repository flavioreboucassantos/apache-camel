package com.br.flavioreboucassantos.camel_whatsapp_webhook.controller;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/")
public class ControllerWebHook {

	private final Logger LOG = LoggerFactory.getLogger(ControllerWebHook.class);

	final String myVerifyToken;

	public ControllerWebHook(@ConfigProperty(name = "webhook_whatsapp.my_verify_token") final String myVerifyToken) {
		this.myVerifyToken = myVerifyToken;
	}

	@GET
	public String recuse() {
		return "Olá";
	}

	@GET
	@Path("/webhook")
	public Response getSubscribe(@QueryParam("hub_mode") final String hubMode,
			@QueryParam("hub_challenge") final String hubChallenge,
			@QueryParam("hub_verify_token") final String hubVerifyToken) {

		LOG.info("getSubscribe ENTROU hub_mode = " + hubMode + " , hub_challenge = " + hubChallenge + " , hub_verify_token = " + hubVerifyToken);

		if (hubMode.equals("subscribe") && hubVerifyToken.equals(myVerifyToken))
			return Response.ok(hubChallenge).status(Response.Status.OK).build();

		LOG.info("getSubscribe FALHOU");

		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}

	@POST
	public Response getMessage(String rawBody) {
		LOG.info(rawBody);
		return Response.status(Response.Status.OK).build();
	}

}
