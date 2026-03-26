package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChanges(
		@JsonProperty(value = "field") String field,
		@JsonProperty(value = "value") JSONWebHookMessageEntryChangesValue value) {
}
