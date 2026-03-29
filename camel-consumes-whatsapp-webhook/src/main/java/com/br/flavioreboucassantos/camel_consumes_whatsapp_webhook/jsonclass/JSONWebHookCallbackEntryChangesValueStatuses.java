package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookCallbackEntryChangesValueStatuses(@JsonProperty(value = "id") String id,
		@JsonProperty(value = "status") String status,
		@JsonProperty(value = "timestamp") String timestamp,
		@JsonProperty(value = "recipient_id") String recipient_id,
		@JsonProperty(value = "pricing") JSONWebHookCallbackEntryChangesValueStatusesPricing pricing) {
}
