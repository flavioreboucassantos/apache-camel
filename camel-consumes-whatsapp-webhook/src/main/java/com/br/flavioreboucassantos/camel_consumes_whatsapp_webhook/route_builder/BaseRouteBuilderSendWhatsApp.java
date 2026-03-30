package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.route_builder;

import java.util.UUID;

import org.apache.camel.builder.RouteBuilder;

public abstract class BaseRouteBuilderSendWhatsApp extends RouteBuilder {

	final String groupIdShared = "group-shared";
	final String groupIdNotShared = UUID.randomUUID().toString();	
	final String groupId;

	public BaseRouteBuilderSendWhatsApp() {
		if (Boolean.parseBoolean(System.getProperty("kafka.client.isSharedGroup", "true")))
			groupId = groupIdShared;
		else
			groupId = groupIdNotShared;
	}

	public abstract String getRouteId();

	public abstract String getUriFrom();

	public abstract String getUriTo();

}
