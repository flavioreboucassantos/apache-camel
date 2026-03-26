package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntry(
		@JsonProperty(value = "id") String id,
		@JsonProperty(value = "changes") List<JSONWebHookMessageEntryChanges> changes) {
}
