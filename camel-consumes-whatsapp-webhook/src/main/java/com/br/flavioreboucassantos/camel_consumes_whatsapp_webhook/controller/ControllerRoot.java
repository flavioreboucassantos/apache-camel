package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class ControllerRoot {

	@GET
	public String recuse() {
		return "Olá";
	}

}
