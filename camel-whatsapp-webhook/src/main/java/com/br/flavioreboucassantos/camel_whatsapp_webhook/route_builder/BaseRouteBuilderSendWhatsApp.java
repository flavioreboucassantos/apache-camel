package com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder;

import org.apache.camel.builder.RouteBuilder;

public abstract class BaseRouteBuilderSendWhatsApp extends RouteBuilder {

	public abstract String getRouteUriFrom();
	public abstract String getRouteUriTo();
}
