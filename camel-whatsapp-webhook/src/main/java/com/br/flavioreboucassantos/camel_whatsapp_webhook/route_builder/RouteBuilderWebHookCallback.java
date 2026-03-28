package com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class RouteBuilderWebHookCallback extends BaseRouteBuilderSendWhatsApp {

	@Override
	public String getRouteUriFrom() {
		return "seda:webHookCallback";
	}

	@Override
	public String getRouteUriTo() {
		return "jms:queue:webhook_callback";
	}

	@Override
	public void configure() throws Exception {
		from(getRouteUriFrom())
				.log("\n> Camel (From " + getRouteUriFrom() + " To " + getRouteUriTo() + ") With Body:\n${body}\n")
				.to(getRouteUriTo());
	}

}
