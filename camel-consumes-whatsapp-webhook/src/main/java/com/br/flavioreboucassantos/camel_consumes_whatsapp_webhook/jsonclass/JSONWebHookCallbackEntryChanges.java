package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookCallbackEntryChanges(
		@JsonProperty(value = "field") String field,
		@JsonProperty(value = "value") JSONWebHookCallbackEntryChangesValue value) {
}
