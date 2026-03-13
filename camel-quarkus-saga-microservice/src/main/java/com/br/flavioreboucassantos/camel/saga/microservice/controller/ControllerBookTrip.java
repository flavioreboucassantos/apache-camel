package com.br.flavioreboucassantos.camel.saga.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel.saga.producer.ProducerBookTrip;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Flávio Rebouças Santos - flavioReboucasSantos@gmail.com
 */
@Path("/book-trip")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ControllerBookTrip extends BaseController {

	private final Logger LOG = LoggerFactory.getLogger(ControllerBookHotelService.class);

	private final ProducerBookTrip producerBookTrip;

	@Inject
	public ControllerBookTrip(final ProducerBookTrip producerBookTrip) {
		this.producerBookTrip = producerBookTrip;
	}

	@GET
	public Response createBookTrip() {

		LOG.info("--- Entrando createBookTrip ---");

		producerBookTrip.sendBody("Book Trip Data Here");

		return Response.status(Response.Status.ACCEPTED).build();
	}
}
