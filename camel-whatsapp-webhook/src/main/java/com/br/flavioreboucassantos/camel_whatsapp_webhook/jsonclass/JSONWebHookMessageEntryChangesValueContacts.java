package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueContacts(
		@JsonProperty(value = "wa_id") String waId,
		@JsonProperty(value = "profile") JSONWebHookMessageEntryChangesValueContactsProfile profile) {

}
