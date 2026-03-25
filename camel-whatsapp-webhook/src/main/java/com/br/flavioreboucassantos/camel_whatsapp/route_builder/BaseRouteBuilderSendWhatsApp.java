package com.br.flavioreboucassantos.camel_whatsapp.route_builder;

import org.apache.camel.builder.RouteBuilder;

public abstract class BaseRouteBuilderSendWhatsApp extends RouteBuilder {

	public abstract String getRouteUri();
}
