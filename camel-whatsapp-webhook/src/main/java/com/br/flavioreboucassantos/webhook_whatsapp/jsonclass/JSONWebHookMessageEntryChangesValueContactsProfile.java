package com.br.flavioreboucassantos.webhook_whatsapp.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueContactsProfile(
		@JsonProperty(value = "name") String name) {

}
