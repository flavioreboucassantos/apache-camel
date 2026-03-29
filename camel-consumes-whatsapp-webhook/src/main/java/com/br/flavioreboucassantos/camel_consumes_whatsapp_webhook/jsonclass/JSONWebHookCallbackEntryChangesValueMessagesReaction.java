package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookCallbackEntryChangesValueMessagesReaction(
		@JsonProperty(value = "message_id") String messageId,
		@JsonProperty(value = "emoji") String emoji) {

}
