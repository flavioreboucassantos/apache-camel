package com.br.flavioreboucassantos.camel_simplefile;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CopyFilesCamelMultiRoute {

	public static void main(String[] args) {
		/*
		 * STRUCTURING
		 */
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:data/input?noop=true")
//					.to("log:?level=INF0&showBody=true&showHeaders=true")
					.to("file:data/output");

				from("file:data/input1?noop=true")
					.to("file:data/output1");
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext
			.start();

		try {
			defaultCamelContext
				.addRoutes(routeBuilder);

			Thread
				.sleep(5000);

			defaultCamelContext
				.stop();
		} catch (Exception e) {
			System.out
				.println("Inside Exception " + e);
		}

	}
}
