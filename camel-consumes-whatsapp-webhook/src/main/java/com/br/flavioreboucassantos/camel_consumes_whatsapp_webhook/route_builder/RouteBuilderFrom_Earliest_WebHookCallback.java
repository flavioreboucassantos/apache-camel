package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.route_builder;

import org.apache.camel.component.jackson.JacksonDataFormat;

import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass.JSONWebHookCallback;
import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.service.ServiceConsumerFromRouteWebHookCallback;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RouteBuilderFrom_Earliest_WebHookCallback extends BaseRouteBuilderSendWhatsApp {

	final ServiceConsumerFromRouteWebHookCallback serviceConsumerFromRouteWebHookCallback;

	final String topicName = "webhookCallback";
	final String topicParameters = "?autoOffsetReset=earliest"
			+ "&groupId=" + groupId;

	@Override
	public String getRouteId() {
		return "earliest-webhook-callback";
	}

	@Override
	public String getUriFrom() {
		return "kafka:" + topicName + topicParameters;
	}

	@Override
	public String getUriTo() {
		return "direct:webHookCallback";
	}

	@Inject
	public RouteBuilderFrom_Earliest_WebHookCallback(final ServiceConsumerFromRouteWebHookCallback serviceConsumerFromRouteWebHookCallback) {
		this.serviceConsumerFromRouteWebHookCallback = serviceConsumerFromRouteWebHookCallback;
	}

	@Override
	public void configure() throws Exception {
		final JacksonDataFormat jsonDataFormat = new JacksonDataFormat(JSONWebHookCallback.class);

		from(getUriFrom() + topicParameters)
				.log("\n> Camel (From " + getUriFrom() + " To " + getUriTo() + ") Offset: ${header[kafka.OFFSET]} With Body:\n${body}\n")
				.unmarshal(jsonDataFormat)
				.process(ex -> {
					serviceConsumerFromRouteWebHookCallback.process(ex.getIn().getBody(JSONWebHookCallback.class));
				})
				.end();
	}
}
