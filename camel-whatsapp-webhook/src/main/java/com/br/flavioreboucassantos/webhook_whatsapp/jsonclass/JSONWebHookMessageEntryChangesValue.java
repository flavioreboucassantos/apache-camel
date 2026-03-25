package com.br.flavioreboucassantos.webhook_whatsapp.jsonclass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValue(
		@JsonProperty(value = "messaging_product") String messagingProduct,
		@JsonProperty(value = "metadata") JSONWebHookMessageEntryChangesValueMetadata metadata,
		@JsonProperty(value = "contacts") List<JSONWebHookMessageEntryChangesValueContacts> contacts,
		@JsonProperty(value = "messages") List<JSONWebHookMessageEntryChangesValueMessages> messages) {

}
