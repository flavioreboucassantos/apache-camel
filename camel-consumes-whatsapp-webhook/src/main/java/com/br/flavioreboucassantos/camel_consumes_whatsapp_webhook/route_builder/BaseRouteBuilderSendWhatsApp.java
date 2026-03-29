package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.route_builder;

import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;

public abstract class BaseRouteBuilderSendWhatsApp extends RouteBuilder {

	static final String groupId = UUID.randomUUID().toString();

	public abstract String getRouteId();

	public abstract String getUriFrom();

	public abstract String getUriTo();

}
