package com.br.flavioreboucassantos.camelmicroservice.routes.a;

import java.time.LocalDateTime;

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

		from("timer:first-timer")
			.transform()
			.constant("Time now is " + LocalDateTime.now())
			.to("log:first-timer");

	}

	void onStart(@Observes StartupEvent ev) {
		System.out
			.println("Serviço iniciado!");
	}

}