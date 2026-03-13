package com.br.flavioreboucassantos.camel.saga.producer;

import org.apache.camel.ProducerTemplate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProducerBookTrip {

	@Inject
	ProducerTemplate producerTemplate;

	public void sendBody(final String message) {
		// Envia para uma rota "direct:input"
		producerTemplate.sendBody("direct:bookTrip", message);
	}
}
