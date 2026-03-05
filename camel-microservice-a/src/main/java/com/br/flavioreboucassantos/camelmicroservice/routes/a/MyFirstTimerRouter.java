package com.br.flavioreboucassantos.camelmicroservice.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.annotations.Component;

@Component(value = "myFirstTimerRouter")
public class MyFirstTimerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// timer
		// transformation
		// log

		from("timer:first-timer").to("log:first-timer");
	}

}