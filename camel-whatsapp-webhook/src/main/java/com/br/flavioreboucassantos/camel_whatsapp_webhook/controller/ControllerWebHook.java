package com.br.flavioreboucassantos.camel_whatsapp_webhook.controller;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass.JSONWebHookMessage;
import com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass.JSONWebHookMessageEntryChangesValue;
import com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass.JSONWebHookMessageEntryChangesValueStatuses;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/webhook")
public class ControllerWebHook {

	private final Logger LOG = LoggerFactory.getLogger(ControllerWebHook.class);

	final String myVerifyToken;

	public ControllerWebHook(@ConfigProperty(name = "whatsapp_webhook.my_verify_token") final String myVerifyToken) {
		this.myVerifyToken = myVerifyToken;
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
	public Response getMessage(JSONWebHookMessage jsonWebHookMessage) {
		JSONWebHookMessageEntryChangesValue value = jsonWebHookMessage.entry().getFirst().changes().getFirst().value();

		if (value.messages() == null) {

			final List<JSONWebHookMessageEntryChangesValueStatuses> statuses = value.statuses();
			LOG.info("\n\n> Statuses of your Message\n> recipient_id: " + statuses.getFirst().recipient_id() + "\n> status: " + statuses.getFirst().status());

		} else {

			final String contact = value.contacts().getFirst().profile().name();
			final String message = value.messages().getFirst().text().body();
			LOG.info("\n\n> New Message From: " + contact + "\n> Message:\n" + message + "\n");
		}

//		LOG.info("\n" + jsonWebHookMessage);

		return Response.status(Response.Status.OK).build();
	}

}
