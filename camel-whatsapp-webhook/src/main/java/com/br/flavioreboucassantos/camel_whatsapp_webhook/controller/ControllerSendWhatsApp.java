package com.br.flavioreboucassantos.camel_whatsapp_webhook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_whatsapp_webhook.dto.DTOSendWhatsApp;
import com.br.flavioreboucassantos.camel_whatsapp_webhook.service.ServiceCamelSendWhatsApp;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/send")
public class ControllerSendWhatsApp {

	private final Logger LOG = LoggerFactory.getLogger(ControllerSendWhatsApp.class);

	final ServiceCamelSendWhatsApp serviceCamelSendWhatsApp;

	@Inject
	public ControllerSendWhatsApp(final ServiceCamelSendWhatsApp serviceCamelSendWhatsApp) {
		this.serviceCamelSendWhatsApp = serviceCamelSendWhatsApp;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendWhatsApp(final DTOSendWhatsApp dtoSendWhatsApp) {
		serviceCamelSendWhatsApp.sendWhatsApp(dtoSendWhatsApp.to(), dtoSendWhatsApp.textOfMessage());
		return Response.ok().status(Response.Status.ACCEPTED).build();
	}

}
