package com.br.flavioreboucassantos.webhook_whatsapp.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueContacts(
		@JsonProperty(value = "wa_id") String waId,
		@JsonProperty(value = "profile") JSONWebHookMessageEntryChangesValueContactsProfile profile) {

}
