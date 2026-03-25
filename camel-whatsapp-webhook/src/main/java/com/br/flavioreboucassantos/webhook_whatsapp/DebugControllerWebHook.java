package com.br.flavioreboucassantos.webhook_whatsapp;

import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

@Path("/debug")
public class DebugControllerWebHook {

	@GET
	public String getAllQueryParams(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

		return queryParams.entrySet().stream()
				.map(entry -> entry.getKey() + " = " + entry.getValue())
				.collect(Collectors.joining(", "));
	}

	@POST
	public String receiveAllBody(String rawBody) {
		return "Dados recebidos: " + rawBody;
	}

	@POST
	@Path("/json")
	public String receiveJson(JsonNode jsonNode) {
		return "Processado: " + jsonNode.toPrettyString();
	}

}
