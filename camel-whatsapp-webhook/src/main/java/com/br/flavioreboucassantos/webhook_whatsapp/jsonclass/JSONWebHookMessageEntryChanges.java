package com.br.flavioreboucassantos.webhook_whatsapp.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChanges(
		@JsonProperty(value = "field") String field,
		@JsonProperty(value = "value") JSONWebHookMessageEntryChangesValue value) {
}
