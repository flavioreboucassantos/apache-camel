package com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class RouteBuilderWebHookCallback extends BaseRouteBuilderSendWhatsApp {

	@Override
	public String getRouteUri() {
		return "seda:webHookCallback";
	}

	@Override
	public void configure() throws Exception {
		from(getRouteUri())
				.log("\n\n> Enviando para jms:queue:webhook_callback:\n${body}\n")
				.to("jms:queue:webhook_callback");
	}

}
