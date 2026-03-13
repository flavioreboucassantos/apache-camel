package com.br.flavioreboucassantos.camel.saga.microservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel.saga.microservice.dto.DtoBookHotelService;
import com.br.flavioreboucassantos.camel.saga.microservice.entity.EntityBookHotelService;
import com.br.flavioreboucassantos.camel.saga.microservice.service.ServiceBookHotelService;
import com.br.flavioreboucassantos.camel.saga.microservice.service.TryResultPersist;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Flávio Rebouças Santos - flavioReboucasSantos@gmail.com
 */
@Path("/hotel-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class ControllerBookHotelService extends BaseController {

	private final Logger LOG = LoggerFactory.getLogger(ControllerBookHotelService.class);

	private final ServiceBookHotelService serviceBookHotelService;

	@Inject
	public ControllerBookHotelService(final ServiceBookHotelService serviceBookHotelService) {
		this.serviceBookHotelService = serviceBookHotelService;
	}

	@POST
	@Path("/book")
	public Response createBookHotelService(final DtoBookHotelService dtoBookHotelService) {

		LOG.info("--- Entrando createBookHotelService ---");

//		sleep(5);

		/*
		 * tryPersist
		 */
		TryResultPersist<EntityBookHotelService> tryResultPersist;
		if ((tryResultPersist = serviceBookHotelService.tryPersist(new EntityBookHotelService(dtoBookHotelService))).persistedEntity() == null)
			return disappointedPersist().build();

		return Response.status(Response.Status.CREATED).entity(tryResultPersist.persistedEntity()).build();
	}

}
