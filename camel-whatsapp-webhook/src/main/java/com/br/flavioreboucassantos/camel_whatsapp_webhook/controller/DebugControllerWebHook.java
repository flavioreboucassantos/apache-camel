package com.br.flavioreboucassantos.camel_whatsapp_webhook.controller;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

@Path("/debug")
public class DebugControllerWebHook {

	private final Logger LOG = LoggerFactory.getLogger(ControllerWebHook.class);

	@GET
	public String getAllQueryParams(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

		final String collect = queryParams.entrySet().stream()
				.map(entry -> entry.getKey() + " = " + entry.getValue())
				.collect(Collectors.joining(", "));

		LOG.info("\nGET queryParams: " + collect);
		return "";
	}

	@POST
	public String receiveAllBody(String rawBody) {
		LOG.info("\nDados recebidos: " + rawBody);
		return "";
	}

	@POST
	@Path("/json")
	public String receiveJson(JsonNode jsonNode) {
		LOG.info("\nProcessado: " + jsonNode.toPrettyString());
		return "";
	}

	@POST
	@Path("/form")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String receiveAllForm(MultivaluedMap<String, String> formParams) {
		LOG.info("\n" + formParams.toString());
		return "";
	}

}
