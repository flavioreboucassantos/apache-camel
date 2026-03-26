package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueMessages(
		@JsonProperty(value = "from") String from,
		@JsonProperty(value = "id") String id,
		@JsonProperty(value = "timestamp") String timestamp,
		@JsonProperty(value = "type") String type,
		@JsonProperty(value = "text") JSONWebHookMessageEntryChangesValueMessagesText text) {

}
