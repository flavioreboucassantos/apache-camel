package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookCallbackEntryChangesValueContacts(
		@JsonProperty(value = "wa_id") String waId,
		@JsonProperty(value = "profile") JSONWebHookCallbackEntryChangesValueContactsProfile profile) {

}
