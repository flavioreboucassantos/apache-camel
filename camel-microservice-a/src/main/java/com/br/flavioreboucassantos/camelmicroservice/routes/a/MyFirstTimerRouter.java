package com.br.flavioreboucassantos.camelmicroservice.routes.a;

import org.apache.camel.builder.RouteBuilder;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class MyFirstTimerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// timer
		// transformation
		// log

		from("timer:first-timer").to("log:first-timer").log("Mensagem recebida: ${body}");

	}

	void onStart(@Observes StartupEvent ev) {
		System.out.println("Serviço iniciado!");
	}

}