package com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class RouteBuilderWebHookCallback extends BaseRouteBuilderSendWhatsApp {

//	int counter = 0;

	@Override
	public String getUriFrom() {
		return "seda:webHookCallback";
	}

	@Override
	public String getUriTo() {
		return "kafka:webhookCallback";
	}

	@Override
	public void configure() throws Exception {
		from(getUriFrom())
//				.process(exchange -> exchange.getIn().setBody("Body" + counter++))
				.log("\n> Camel (From " + getUriFrom() + " To " + getUriTo() + ") With Body:\n${body}\n")
				.to(getUriTo());
	}
}
