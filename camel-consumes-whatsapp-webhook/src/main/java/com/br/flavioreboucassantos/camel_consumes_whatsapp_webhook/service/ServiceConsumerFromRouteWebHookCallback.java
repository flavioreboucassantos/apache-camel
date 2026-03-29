package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.ConsumerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass.JSONWebHookCallback;
import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass.JSONWebHookCallbackEntryChangesValue;
import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass.JSONWebHookCallbackEntryChangesValueMessages;
import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass.JSONWebHookCallbackEntryChangesValueStatuses;
import com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.route_builder.RouteBuilderFrom_Earliest_WebHookCallback;

import io.quarkus.scheduler.Scheduler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public final class ServiceConsumerFromRouteWebHookCallback {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceConsumerFromRouteWebHookCallback.class);

	private final RouteBuilderFrom_Earliest_WebHookCallback routeBuilderFrom_Earliest_WebHookCallback;
	private final ConsumerTemplate consumerTemplate;

	public final List<JSONWebHookCallback> listJSONWebHookCallback = new ArrayList<JSONWebHookCallback>();
	public final List<String> listProcessedJSONWebHookCallback = new ArrayList<String>();

	@Inject
	Scheduler scheduler;

	private String processJSONWebHookCallback(final JSONWebHookCallback jsonWebHookCallback) {
		String output = "";

		JSONWebHookCallbackEntryChangesValue value = jsonWebHookCallback.entry().getFirst().changes().getFirst().value();

		if (value.messages() != null) {
			final List<JSONWebHookCallbackEntryChangesValueMessages> messages = value.messages();

			if (messages.getFirst().text() != null) {
				final String contact = value.contacts().getFirst().profile().name();
				final String message = value.messages().getFirst().text().body();
				output += "\n> New Message From: " + contact
						+ "\n> Message:"
						+ "\n" + message + "\n";
			}

			if (messages.getFirst().reaction() != null) {
				final String messageId = messages.getFirst().reaction().messageId();
				final String emoji = messages.getFirst().reaction().emoji();
				output += "\n> Reaction For: " + messageId
						+ "\n> Emoji: " + emoji;
			}
		}

		if (value.statuses() != null) {
			final List<JSONWebHookCallbackEntryChangesValueStatuses> statuses = value.statuses();
			output += "\n> Statuses of your Message"
					+ "\n> recipient_id: " + statuses.getFirst().recipient_id()
					+ "\n> status: " + statuses.getFirst().status() + "\n";
		}

		return output;
	}

	private void consumeFromAny(
			final String uri,
			final long timeout,
			final List<JSONWebHookCallback> listJSONWebHookCallback,
			final List<String> listProcessedJSONWebHookCallback) {
		JSONWebHookCallback receiveBody;
		while ((receiveBody = consumerTemplate.receiveBody(uri, timeout, JSONWebHookCallback.class)) != null) {
			listJSONWebHookCallback.add(receiveBody);
			listProcessedJSONWebHookCallback.add(processJSONWebHookCallback(receiveBody));
		}
	}

	@Inject
	public ServiceConsumerFromRouteWebHookCallback(
			final RouteBuilderFrom_Earliest_WebHookCallback routeBuilderFrom_Earliest_WebHookCallback,
			final ConsumerTemplate consumerTemplate) throws Exception {
		this.routeBuilderFrom_Earliest_WebHookCallback = routeBuilderFrom_Earliest_WebHookCallback;
		this.consumerTemplate = consumerTemplate;
	}

	public void process(JSONWebHookCallback jsonWebHookCallback) {
		listJSONWebHookCallback.add(jsonWebHookCallback);
		listProcessedJSONWebHookCallback.add(processJSONWebHookCallback(jsonWebHookCallback));
	}

	public List<String> getListProcessedJSONWebHookCallback() {
		return listProcessedJSONWebHookCallback;
	}

//	@Scheduled(every = "2s")
//	void everySecond() throws Exception {
//		consumeFromAny(routeBuilderFrom_Earliest_WebHookCallback.getUriTo(), 1000, listJSONWebHookCallback, listProcessedJSONWebHookCallback);
//	}
}
