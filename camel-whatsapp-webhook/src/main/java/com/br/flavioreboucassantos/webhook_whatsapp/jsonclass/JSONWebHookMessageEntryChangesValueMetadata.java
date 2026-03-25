package com.br.flavioreboucassantos.webhook_whatsapp.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueMetadata(
		@JsonProperty(value = "display_phone_number") String displayPhoneNumber,
		@JsonProperty(value = "phone_number_id") String phoneNumberId) {

}
