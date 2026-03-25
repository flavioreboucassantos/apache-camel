package com.br.flavioreboucassantos.webhook_whatsapp;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.webhook_whatsapp.jsonclass.JSONWebHookMessage;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/webhook")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ControllerWebHook {

	private final Logger LOG = LoggerFactory.getLogger(ControllerWebHook.class);

	final String myVerifyToken;

	public ControllerWebHook(@ConfigProperty(name = "webhookWhatsApp.my_verify_token") final String myVerifyToken) {
		this.myVerifyToken = myVerifyToken;
	}

	@GET
	public Response getSubscribe(@QueryParam("hub_mode") final String hubMode,
			@QueryParam("hub_challenge") final String hubChallenge,
			@QueryParam("hub_verify_token") final String hubVerifyToken) {

		if (hubMode == "subscribe" && hubVerifyToken == myVerifyToken)
			return Response.ok(hubChallenge).status(Response.Status.OK).build();

		return Response.status(Response.Status.NOT_ACCEPTABLE).build();
	}
	
	@POST
	public Response getMessage(JSONWebHookMessage jsonWebHookMessage) {
		return Response.status(Response.Status.OK).build();
	}

}
